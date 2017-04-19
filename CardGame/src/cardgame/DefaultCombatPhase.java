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
    
    public  void resolution(ArrayList<Duel> duel) {
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
    
    
    public void canAttack(ArrayList<Creature> attacker) {
        for(Creature c: CardGame.instance.getCurrentPlayer().getCreatures()){
            if(!c.isTapped())
                attacker.add(c);
        }
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
        ArrayList<Creature> effectiveAttacker = new ArrayList<>();
        canAttack(effectiveAttacker);
        
        List<Creature> effectiveDefender = currentAdversary.getCreatures();

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        int attack_index;
        int defend_index;
        int i = 0;
        
        if(!effectiveAttacker.isEmpty()) {
            ArrayList<Duel> duel = new ArrayList<>();
            System.out.println("Queste creature possono attaccare: ");
            printPlayerField(effectiveAttacker);
            do{
                System.out.println("AAttacca con: (0 to pass)");
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
            }
            if(!effectiveDefender.isEmpty()) {
                System.out.println("Scegli i difensori: ");
                for(Duel d : duel) {
                    System.out.println("Chi difende per " + d.getAttackers().name());
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
            resolution(duel);
            }
            else
                System.out.println("... no creatures can defend");
        }
        else
            System.out.println("... no creatures on field");
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
