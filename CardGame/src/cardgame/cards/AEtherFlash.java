/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Card;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.Player;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.AbstractEnchantment;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;

/**
 *
 * @author diletta
 */
public class AEtherFlash implements Card{
    Creature target1, target2;
    @Override
    public Effect getEffect(Player owner) {
        return new AEtherFlashEffect(owner, this);
    }

    @Override
    public String name() {
        return "AEther Flash";
    }

    @Override
    public String type() {
        return "Enchantment";
    }

    @Override
    public String ruleText() {
        return "Whenever a creature comes into play"+name()+"deals 2 damage to it";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
    private class AEtherFlashEffect extends AbstractEnchantmentCardEffect{
        public AEtherFlashEffect(Player p, Card c){
            super(p,c);
        }

        @Override
        protected Enchantment createEnchantment() {
            return new AEtherFlashEnchantment(owner);
        }
    }
    
    private class AEtherFlashEnchantment extends AbstractEnchantment{
        public AEtherFlashEnchantment(Player owner){
            super(owner);
        }

        @Override
        public void insert() {
            List<Creature> creatures1 = CardGame.instance.getAdversary(owner).getCreatures();
            List<Creature> creatures2 = owner.getCreatures();
            target1 = creatures1.get(creatures1.size()-1);
            target2 = creatures2.get(creatures2.size()-1);
            CardGame.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, DamageOnEntranceAction);
            super.insert();
        }
        
        private final TriggerAction DamageOnEntranceAction = new TriggerAction(){
            @Override
            public void execute(Object args) {
                target1.inflictDamage(2);
                target2.inflictDamage(2);
            }
                
        };
        
        
        
        @Override
        public String name() {
            return "AEtherFlashEnchantment";
        }
        
        @Override
        public String toString() {
            return name() + " (" + type() + ") [" + ruleText() +"]";
        }
        
    }
}
