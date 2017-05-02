/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author atorsell
 */
public class DefaultCombatPhase implements Phase {
    
    public void printPlayerField(List<Creature> creatures) {
        int i = 0;
        for(Creature creature: creatures) {
            System.out.println(Integer.toString(i+1)+") " + creature.name() + " ["+ creature + "]" );
            ++i;
        }
    }
    
    public  void printResolution(List<Duel> duel) {
        System.out.println("#### SITUAZIONE BATTAGLIA ####");
        for(Duel d : duel) {
            System.out.println(d.getAttackers().name() + " attacks! (atk: " + d.getAttackers().getDecoratorHead().getPower() + ")");
            if(!d.getDefenders().isEmpty()) {
                for(Creature c: d.getDefenders()) {
                    System.out.println(c + " defends");
                }
            }
            else
                System.out.println("No defenders");
        }
        System.out.println("###############################");
    }
    
    public void resolution(List<Duel> duel) {
        CardGame.instance.getTriggers().trigger(Triggers.DAMAGE_FILTER);
        int damageAttacker;
        for(Duel d : duel) {
            damageAttacker = 0;
            System.out.print(d.getAttackers().name() + " attacks ");
            if(!d.getDefenders().isEmpty()) {
                for(Creature c: d.getDefenders()) {
                    if(damageAttacker < d.attackers.getToughness()) {
                        System.out.println(c.name() + " (defender)");
                        damageAttacker += Math.max(0, c.getDecoratorHead().getPower());
                        d.attackers.inflictDamage(Math.max(0,c.getDecoratorHead().getPower()));
                        c.inflictDamage(Math.max(0,d.attackers.getDecoratorHead().getPower()));
                    }   
                }
            }
            else {
                System.out.println(", no defenders");
                CardGame.instance.getCurrentAdversary().inflictDamage(Math.max(0,d.getAttackers().getDecoratorHead().getPower()));
                System.out.println("Direct attack!");
            }
            if(damageAttacker < d.attackers.getToughness())
                d.attackers.tap();
        }
        CardGame.instance.getTriggers().trigger(Triggers.END_DAMAGE_FILTER);
    }
    
    public ArrayList<Creature> canAttack(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for(Creature c: player.getCreatures()){
            if(!c.isTapped() && c.canAttack())
                tmp.add(c);
        }
        return tmp;
    }
    public ArrayList<Creature> canDefend(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for(Creature c: player.getCreatures()){
            if(!c.isTapped() && !c.isDefender())
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
        ArrayList<Creature> effectiveAttacker = canAttack(currentPlayer);
        ArrayList<Creature> effectiveDefender = canDefend(currentAdversary);

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        
        
        //int i = 0;
        List<Duel> duel = new ArrayList<>();
        //int i = 0;
        if(effectiveAttacker.isEmpty())
            System.out.println("... no creatures on field");
        if(!effectiveAttacker.isEmpty()) {
            int attack_index = 1;
            System.out.println("These creatures can attack: ");
            while(attack_index != 0 && effectiveAttacker.size() > 0) {
                do{
                    printPlayerField(effectiveAttacker);
                    System.out.println("Attack with: (0 to pass)");          
                    attack_index = reader.nextInt();
                }while(attack_index < 0 || attack_index-1 >= effectiveAttacker.size());
                if(attack_index != 0) {
                    Duel d = new Duel();
                    d.attackers = effectiveAttacker.get(attack_index-1);
                    duel.add(d);
                    effectiveAttacker.remove(attack_index-1);
                }
            }
        System.out.println("###### FINE DICHIARAZIONE ATTACCANTI ######");
        System.out.println();
        } 
        System.out.println("Stack's resolution");
        CardGame.instance.getStack().resolve();
        if(effectiveDefender.isEmpty())
            System.out.println("... no creatures can defend");
        if(duel.isEmpty())
            System.out.println("... no creatures da cui difendersi");
        if(!effectiveDefender.isEmpty() && !duel.isEmpty()) {     
            System.out.println(currentAdversary.name() + " scegli i difensori: "); 
            for(Duel d : duel) {
                int defend_index = 1;
                System.out.println("Who defends for " + d.getAttackers().name() + "?");    
                while(defend_index != 0 && effectiveDefender.size() > 0) {
                    do{
                        printPlayerField(effectiveDefender);
                        System.out.println("Defend (" + d.getAttackers().name() + ") with: (0 to pass)");
                        defend_index = reader.nextInt();
                    }while(defend_index < 0 || defend_index-1 >= effectiveDefender.size());
                    if(defend_index != 0) {
                        d.getDefenders().add(effectiveDefender.get(defend_index-1));
                        effectiveDefender.remove(defend_index-1);
                    }
                }
            }
        System.out.println();
        System.out.println("###### FINE DICHIARAZIONE DIFENSORI ######");
        }

        System.out.println("Stack's resolution");
        CardGame.instance.getStack().resolve();
        printResolution(duel);
        resolution(duel);
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