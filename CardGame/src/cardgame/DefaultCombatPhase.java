/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author atorsell
 */
public class DefaultCombatPhase implements Phase {
    int i;
    
    public void printPlayerField(List<Creature> creatures) {
        for(Creature creature: creatures) {
            System.out.println(Integer.toString(i+1)+") " + creature.name() + " ["+ creature + "]" );
            ++i;
        }
    }
    
    public  void printResolution(ArrayList<Duel> duel) {
        for(Duel d : duel) {
            System.out.println(d.getAttackers().name() + " attacks!");
            if(!d.getDefenders().isEmpty()) {
                for(Creature c: d.getDefenders()) {
                    System.out.println(c + " defends");
                }
            }
            else
                System.out.println("No defenders");
        }
    }
    
    public void resolution(ArrayList<Duel> duel) {
        for(Duel d : duel) {
            System.out.print(d.getAttackers().name() + " attacks!");
            if(!d.getDefenders().isEmpty()) {
                for(Creature c: d.getDefenders()) {
                    System.out.println(c + " defends");
                    d.getAttackers().attack(c);
                }
            }
            else {
                System.out.println("No defenders");
                CardGame.instance.getCurrentAdversary().inflictDamage(d.getAttackers().getPower());
            }
        }  
    }
    
    public ArrayList<Creature> canAttackDefend(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for(Creature c: player.getCreatures()){
            if(!c.isTapped())
                tmp.add(c);
        }
        return tmp;
    }
    @Override
    public void execute() {
        /**
         * Attuale giocatore e avversario
         */
        Player currentPlayer = CardGame.instance.getCurrentPlayer();
        Player currentAdversary = CardGame.instance.getCurrentAdversary();
        // variabile per prendere in input
        Scanner reader = CardGame.instance.getScanner();
        
        // Setto chi può attaccare e chi difendere
        ArrayList<Creature> effectiveAttacker = canAttackDefend(currentPlayer);
        ArrayList<Creature> effectiveDefender = canAttackDefend(currentAdversary);

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        int attack_index;
        int defend_index;
        int i = 0;
        ArrayList<Duel> duel = new ArrayList<>();
        
        if(!effectiveAttacker.isEmpty()) {
            System.out.println("Queste creature possono attaccare: ");
            printPlayerField(effectiveAttacker);
            do{
                System.out.println("Attacca con: (0 to pass)");
                attack_index = reader.nextInt()-1;
            }while(attack_index < 0 || attack_index > effectiveAttacker.size());
            
            while(attack_index != 0) {
                duel.get(i).setAttackers(effectiveAttacker.get(attack_index));
                effectiveAttacker.remove(attack_index);
                do{
                    System.out.println("Attacca con: (0 to pass)");
                    printPlayerField(effectiveAttacker);
                    attack_index = reader.nextInt()-1;
                }while(attack_index < 0 || attack_index > effectiveAttacker.size());
                i++;
            }
        System.out.println("###### FINE DICHIARAZIONE ATTACCANTI #####");
        System.out.println();
        }
        else
            System.out.println("... no creatures on field");
        
        System.out.println("Risoluzione stack");
        CardGame.instance.getStack().resolve();
        
        if(!effectiveDefender.isEmpty() || !duel.isEmpty()) {
            System.out.println(currentAdversary.name() + " scegli i difensori: ");
            for(Duel d : duel) {
                System.out.println("Chi difende per " + d.getAttackers().name() + "?");
                printPlayerField(effectiveDefender);
                do{
                    System.out.println("Defend with: ");
                    defend_index = reader.nextInt()-1;
                }while(defend_index < 0 || defend_index > effectiveDefender.size());

                while(defend_index != 0) {
                    d.getDefenders().add(effectiveDefender.get(defend_index));
                    effectiveDefender.remove(defend_index);
                    do{
                        System.out.println("Defend (" + d.getAttackers().name() + ") with: (0 to pass)");
                        printPlayerField(effectiveDefender);
                        defend_index = reader.nextInt()-1;
                    }while(defend_index < 0 || defend_index > effectiveDefender.size());
                }
            }
        System.out.println("###### FINE DICHIARAZIONE DIFENSORI #####");
        System.out.println("Risoluzione stack");
        CardGame.instance.getStack().resolve();
        printResolution(duel);
        resolution(duel);
        }
        else {
            if(duel.isEmpty())
               System.out.println("... no creatures da cui difendersi");
            else
               System.out.println("... no creatures can defend");
        }
        
    }
    
    public class Duel {
        Creature attackers;
        private ArrayList<Creature> defenders = new ArrayList<>();
        
        public void setAttackers(Creature attackers) {
            this.attackers = attackers;
        }
       
        public Creature getAttackers() {
            return attackers;
        }

        public ArrayList<Creature> getDefenders() {
            return defenders;
        }
        
    }
}