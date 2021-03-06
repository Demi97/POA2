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
import cardgame.Enchantment;
import cardgame.Player;
import cardgame.TriggerAction;
import cardgame.Triggers;

/**
 *
 * @author diletta
 */
public class ArgothianEnchantress implements Card {

    @Override
    public String name() {
        return "Argothian Enchantress";
    }

    @Override
    public String type() {
        return "Creature";
    }

    @Override
    public String ruleText() {
        return "Shroud (This creature can't be the target of spells or abilities). Whenever you cast an enchantment spell, draw a card";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new ArgothianEnchantressEffect(owner, this);
    }
    
    private class ArgothianEnchantressEffect extends AbstractCreatureCardEffect {

        public ArgothianEnchantressEffect(Player p, Card c) {
            super(p, c);
        }

        @Override
        protected Creature createCreature() {
            return new ArgothianEnchantressCreature(owner);
        }

    }

    private class ArgothianEnchantressCreature extends AbstractCreature {
        
        ArgothianEnchantressCreature(Player owner) {
            super(owner);
        }
        
        @Override
        public void insert() {
            super.insert();
            if(!this.isRemoved())
                CardGame.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER, drawCard);

        }
        @Override
        public void remove(){
            CardGame.instance.getTriggers().deregister(drawCard);
            super.remove();

        }

        private final TriggerAction drawCard = new TriggerAction(){
            @Override
            public void execute(Object args) {
                if (args instanceof Enchantment) {
                    if (owner.getEnchantments().contains((Enchantment) args))
                        owner.draw();
                }
            }     
        };

        @Override
        public int getPower() {
            return 0;
        }

        @Override
        public int getToughness() {
            return 1;
        }

        @Override
        public String name() {
            return "Argorthian Enchantress";
        }

        @Override
        public boolean isAttackable() {
            return false;
        }
        @Override
        public String toString() {
            return name() + " (" + type() + ") [" + ruleText() +"]";
        }
    }
}