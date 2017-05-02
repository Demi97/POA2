/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.AbstractStrategyPlayer;
import cardgame.Card;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Player;
import cardgame.CardGame;
import cardgame.MagicPrinter;
import cardgame.Phases;
import cardgame.StrategyPlayer;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author simonescaboro
 */
public class Darkness implements Card {

    @Override
    public Effect getEffect(Player owner) {
        return new DarknessEffect(owner,this);
    }
    private class DarknessEffect extends AbstractCardEffect {

        public DarknessEffect(Player p, Card c) {
            super(p, c);
        }

        @Override
        public void resolve() {
            StrategyPlayer stp = new DarknessStrategy();
            // applica lo strategy ad entrambi i giocatori
            CardGame.instance.getCurrentPlayer().addStrategy(stp);
            CardGame.instance.getCurrentAdversary().addStrategy(stp);
            TriggerAction dt = new DarknessTrigger(stp);
            // registra il trigger per la rimozione dello strategy
            CardGame.instance.getTriggers().register(Phases.COMBAT.getIdx(),dt);
        }
        public class DarknessStrategy extends AbstractStrategyPlayer {

            public DarknessStrategy() {
                super();
            }

            @Override
            public void inflictDamage(int dmg) {
                super.inflictDamage(0);
            }
            
            @Override
            public void inflictCombatDamageCreature(Creature c,int dmg) {
                super.inflictCombatDamageCreature(c,0);
            }
        }

        private class DarknessTrigger implements TriggerAction {

            StrategyPlayer stp;

            private DarknessTrigger(StrategyPlayer stp) {
                this.stp = stp;
            }

            @Override
            public void execute(Object args) {
                CardGame.instance.getCurrentPlayer().removeStrategy(stp);
                CardGame.instance.getCurrentAdversary().removeStrategy(stp);
            }
        }
    }
    
    
    
    @Override
    public String name() { return "Darkness"; }
    @Override
    public String type() { return "Instant"; }
    @Override
    public String ruleText() { return "Prevent all combat damage that would be deal this turn"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return true; }
}