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
    
    public  void resolution(HashMap<Creature,Creature> conflict, ArrayList<Creature> defenders, ArrayList<Creature> attackers) {
        int i = 0;
        while(attackers.isEmpty() == false)
        {
           System.out.println(attackers.get(i).name().toString() + " attacks!");
           for(Creature c: defenders) {
               if(conflict.get(defenders) == attackers.get(i))
               System.out.println(attackers.get(i).name().toString() + " defends");
           }
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
        
        
        // Definisco una mappa nella quale ad ogni difensore (chiave) corrisponde un attaccante --> ogni difensore corrisponde ad un solo difensore
        HashMap<Creature,Creature> defenders = new HashMap<Creature,Creature>();
        
        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        // Printo il terreno del giocatore
        System.out.println("Creatures on field: ");
        printPlayerField(currentPlayer.getCreatures());
        
        //Decido quale può attaccare
        System.out.println("Which can attack? ");
        
        // Variabili indice
        int attack_index = reader.nextInt()-1;
        int defend_index;
        
        // Inizio il ciclo: per ogni attaccante scelgo, con un ciclo interno, i difensori
        while(attack_index != 0) { 
            Creature attacker = null;

            
            // Controllo che l'attaccante scelto: non sia tappato, sfori l'indice, sia già stato scelto
            if(attack_index < 0 || currentPlayer.getCreatures().get(attack_index-1).isTapped() || playerAttackerList.contains(currentPlayer.getCreatures().get(attack_index-1)))
                System.out.println("This creature is tapped, does not exists or already used");
            else {
                // In caso soddisfi la condizione lo metto in una variabile di supporto
                attacker = currentPlayer.getCreatures().get(attack_index-1);
                playerAttackerList.add(attacker);
            }
            
            // Chiedo all'avversario chi vuole utilizzare come difensore
            System.out.println("Select defenders: (select the index or 0 to pass)");
            printPlayerField(currentAdversary.getCreatures());
            
            // Faccio selezionare i difensorei
            defend_index = reader.nextInt()-1;
            while(defend_index != 0 && attacker != null){
                
                // controllo come prima
                if(defend_index < 0 || currentAdversary.getCreatures().get(attack_index-1).isTapped() || adversaryDefenderList.contains(currentAdversary.getCreatures().get(attack_index-1)))
                    System.out.println("This creature is tapped or does not exists or already used");
                else {
                    Creature defender = currentAdversary.getCreatures().get(defend_index-1);
                    defenders.put(defender,attacker); 
                }
            }
            
        }
        // Combatto
        resolution(defenders, adversaryDefenderList, playerAttackerList);
    }
}
