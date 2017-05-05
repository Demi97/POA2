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

    CombatStrategy cs = new CombatStrategy();
    
    public void printResolution(List<Duel> duel) {
        System.out.println("=====BATTLE SITUATION=====");
        for(Duel d : duel) {
            if(!d.getAttackers().isRemoved()){
                System.out.println(d.getAttackers().name() + " attacks!");
                boolean someDefenders = false;
                for(Creature c : d.getDefenders()) {
                    someDefenders = true;
                    if(!c.isRemoved())
                        System.out.println(c.name() + " defends!");
                }
                if(!someDefenders)
                    System.out.println("Direct attack!");
            }
            System.out.println("- - - - - - - - - - - - -");
        }
        System.out.println("==========================");
    }
    // restituisce le creature che possono effettivamente attaccare
    public ArrayList<Creature> canAttack(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for (Creature c : player.getCreatures()) {
            // se è tappato, difensore o rimosso non può attaccare
            if (!c.getDecoratorHead().isTapped() && !c.getDecoratorHead().isDefender() && !c.getDecoratorHead().isRemoved()) {
                tmp.add(c);
            }
        }
        return tmp;
    }

    // restituisce le creature che possono effettivamente difendere
    public ArrayList<Creature> canDefend(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for (Creature c : player.getCreatures()) {
            // se è tappato o è rimossono non può difedendere
            if (!c.getDecoratorHead().isTapped() && !c.getDecoratorHead().isRemoved()) {
                tmp.add(c);
            }
        }
        return tmp;
    }

    private boolean playAvailableEffect(Player activePlayer, boolean isMain) {
        //collect and display available effects...
        ArrayList<Effect> availableEffects = new ArrayList<>();
        Scanner reader = CardGame.instance.getScanner();

        //...cards first
        System.out.println(activePlayer.name() + " select card/effect to play, 0 to pass");
        int i = 0;
        for (Card c : activePlayer.getHand()) {
            if (c.isInstant()) {
                availableEffects.add(c.getEffect(activePlayer));
                System.out.println(Integer.toString(i + 1) + ") " + c);
                ++i;
            }
        }

        //...creature effects last
        for (Creature c : activePlayer.getCreatures()) {
            for (Effect e : c.avaliableEffects()) {
                availableEffects.add(e);
                System.out.println(Integer.toString(i + 1) + ") " + c.name()
                        + " [" + e + "]");
                ++i;
            }
        }

        //get user choice and play it
        int idx = reader.nextInt() - 1;
        if (idx < 0 || idx >= availableEffects.size()) {
            return false;
        }

        availableEffects.get(idx).play();
        return true;
    }

    private void stackResolution() {
        int numberPasses = 0;
        Player currentPlayer = CardGame.instance.getCurrentPlayer();
        int responsePlayerIdx = ((CardGame.instance.getPlayer(0) == currentPlayer) ? 1 : 0);
        if (!playAvailableEffect(currentPlayer, true)) {
            ++numberPasses;
        }

        while (numberPasses < 2) {
            if (playAvailableEffect(CardGame.instance.getPlayer(responsePlayerIdx), false)) {
                numberPasses = 0;
            } else {
                ++numberPasses;
            }

            responsePlayerIdx = (responsePlayerIdx + 1) % 2;
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
        ArrayList<Creature> effectiveAttacker = canAttack(currentPlayer);
        ArrayList<Creature> effectiveDefender = canDefend(currentAdversary);

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat

        List<Duel> duel = new ArrayList<>();

        //DICHIARAZIONE ATTACCANTI
        if (effectiveAttacker.isEmpty()) {
            System.out.println("... no creatures on field");
        }
        if (!effectiveAttacker.isEmpty()) {
            int attack_index = 1;
            System.out.println("These creatures can attack: ");
            while (attack_index != 0 && effectiveAttacker.size() > 0) {
                do {
                    MagicPrinter.instance.printCreatures(effectiveAttacker);
                    System.out.println("Attack with: (0 to pass)");
                    attack_index = reader.nextInt();
                } while (attack_index < 0 || attack_index - 1 >= effectiveAttacker.size());
                if (attack_index != 0) {
                    Duel d = new Duel();
                    d.attackers = effectiveAttacker.get(attack_index - 1);
                    duel.add(d);
                    effectiveAttacker.remove(attack_index - 1);
                }
            }
            System.out.println("###### FINE DICHIARAZIONE ATTACCANTI ######");
            System.out.println();
            stackResolution();
            System.out.println("Stack's resolution");
            CardGame.instance.getStack().resolve();
        }

        // DICHIARAZIONE DIFENSORI
        if (effectiveDefender.isEmpty()) {
            System.out.println("... no creatures can defend");
        }
        if (duel.isEmpty()) {
            System.out.println("... no creatures da cui difendersi");
        }
        if (!effectiveDefender.isEmpty() && !duel.isEmpty()) {
            System.out.println(currentAdversary.name() + " choose defenders: ");
            for (Duel d : duel) {
                int defend_index = 1;
                if (!d.getAttackers().getDecoratorHead().isRemoved()) {
                    System.out.println("Who defends for " + d.getAttackers().name() + "?");
                    while (defend_index != 0 && effectiveDefender.size() > 0) {
                        do {
                            MagicPrinter.instance.printCreatures(effectiveDefender);
                            System.out.println("Defend with: (0 to pass)");
                            defend_index = reader.nextInt();
                        } while (defend_index < 0 || defend_index - 1 >= effectiveDefender.size());
                        if (defend_index != 0) {
                            d.getDefenders().add(effectiveDefender.get(defend_index - 1));
                            effectiveDefender.remove(defend_index - 1);
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("###### FINE DICHIARAZIONE DIFENSORI ######");
            stackResolution();
            System.out.println("Stack's resolution");
            CardGame.instance.getStack().resolve();
        }
        printResolution(duel);
        cs.resolution(duel);
    }

    public CombatStrategy getCs() {
        return cs;
    }

    public void setCs(CombatStrategy cs) {
        this.cs = cs;
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
