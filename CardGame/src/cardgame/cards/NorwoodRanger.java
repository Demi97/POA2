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
public class NorwoodRanger implements Card {
    
    private class NorwoodRangerEffect extends AbstractCreatureCardEffect {
        public NorwoodRangerEffect(Player p, Card c) { super(p,c); }
        @Override
        protected Creature createCreature() { return new NorwoodRanger.NorwoodRangerCreature(owner); }
    }
    @Override
    public Effect getEffect(Player p) { return new NorwoodRangerEffect(p,this); }
        
    @Override
    public String name() { return "Norwood Ranger"; }
    @Override
    public String type() { return "Creature"; }
    @Override
    public String ruleText() { return "Put in play a creature Norwood Ranger (1/2)"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
    
        private class NorwoodRangerCreature extends AbstractCreature {

        NorwoodRangerCreature(Player owner) { 
            super(owner,false);
        }
        
        @Override
        public String name() { return "Norwood Ranger"; }
        
       
        @Override
        public int getPower() { return 1; }
        
        @Override
        public int getToughness() { return 2; }
    }
}
