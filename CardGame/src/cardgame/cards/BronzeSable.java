/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCreature;
import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simonescaboro
 */
public class BronzeSable implements Card {
    
    private class BronzeSableEffect extends AbstractCreatureCardEffect {
        public BronzeSableEffect(Player p, Card c) { super(p,c); }
        @Override
        protected Creature createCreature() { return new BronzeSable.BronzeSableCreature(owner); }
    }
    @Override
    public Effect getEffect(Player p) { return new BronzeSableEffect(p,this); }
        
    @Override
    public String name() { return "Bronze Sable"; }
    @Override
    public String type() { return "Creature"; }
    @Override
    public String ruleText() { return "Put in play a creature Bronze Sable (2/1)"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
    
        private class BronzeSableCreature extends AbstractCreature {

        BronzeSableCreature(Player owner) { 
            super(owner);
        }
        
        @Override
        public String name() { return "Bronze Sable"; }
        
        @Override
        public int getPower() { return 2; }
        
        @Override
        public int getToughness() { return 1; }
    }
}
