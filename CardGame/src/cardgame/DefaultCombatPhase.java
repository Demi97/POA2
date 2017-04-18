/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;
import java.util.HashMap;
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
        System.out.println("");
    }
    
    public  void resolution(Duel duel) {
        int i = 0;
        while(duel.getDefenders().isEmpty() == false)
        {
           System.out.println(duel.getAttackers().get(0).name() + " attacks!");
           for(Creature c: duel.getDefenders()) {
               System.out.println(c + " defends");
           }
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
        // Lista degli attaccanti
        ArrayList<Creature> attackers = new ArrayList<Creature>();
        /**
         * Lista di supporto per attaccanti e difensori
         * adversaryDefenderList: contiene le creature già utilizzate per difendere
         * playerAttackerList: contiene le creature già utilizzate per attaccare
         */
        
        ArrayList<Creature> adversaryDefenderList = new ArrayList<Creature>();
        ArrayList<Creature> playerAttackerList = new ArrayList<Creature>();
        
        // Setto chi può attaccare e chi difendere
        ArrayList<Creature> effectiveAttacker = new ArrayList<Creature>();
        canAttack(effectiveAttacker);
        List<Creature> effectiveDefender = currentAdversary.getCreatures();

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        if(effectiveAttacker.size() != 0) {
            Duel duel = new Duel();
            System.out.println("These creatures can attack: ");
            printPlayerField(effectiveAttacker);
            //Decido quale può attaccare
            System.out.println("Attack with: (0 to pass)");
            // Variabili indice
            int attack_index = reader.nextInt()-1;
            int defend_index;
            // Inizio il ciclo: per ogni attaccante scelgo, con un ciclo interno, i difensori
            while(attack_index != 0) { 
                Creature attacker = null;
                // Controllo che l'attaccante scelto: non sia tappato, sfori l'indice, sia già stato scelto
                if(attack_index < 0 && attack_index > effectiveAttacker.size())
                    System.out.println("This creature does not exists ");
                else {
                    // In caso soddisfi la condizione lo metto in una variabile di supporto
                    attacker = effectiveAttacker.get(attack_index-1);
                    duel.getAttackers().add(attacker);
                    effectiveAttacker.remove(attack_index-1);
                }

                // Chiedo all'avversario chi vuole utilizzare come difensore
                System.out.println("Select defenders: (select the index or 0 to pass)");
                printPlayerField(currentAdversary.getCreatures());

                // Faccio selezionare i difensorei
                defend_index = reader.nextInt()-1;
                while(defend_index != 0 && attacker != null){
                    // controllo come prima
                    if(defend_index < 0 && defend_index > effectiveDefender.size())
                        System.out.println("This creature is tapped or does not exists");
                    else {
                        Creature defender = currentAdversary.getCreatures().get(defend_index-1);
                        duel.getDefenders().add(defender);
                        effectiveDefender.remove(defend_index-1);
                    }
                }

            }
            // Combatto
            resolution(duel);
        }
        else
            System.out.println("... no creatures on field");
    }
    
    public class Duel {
        private ArrayList<Creature> attackers = new ArrayList<>();
        private ArrayList<Creature> defenders = new ArrayList<>();
        
        public ArrayList<Creature> getAttackers() {
            return attackers;
        }

        public ArrayList<Creature> getDefenders() {
            return defenders;
        }
        
    }
}
