/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCreature;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.Player;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.AbstractEnchantment;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.MagicPrinter;
import cardgame.SpecialCreatureDecorator;
import cardgame.Targets;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author diletta
 */
public class Abduction implements Card {
    Creature target;
    SpecialCreatureDecorator target_copy;
    
    private class AbductionEffect extends AbstractEnchantmentCardEffect implements Targets{
        List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
        
        public AbductionEffect(Player p, Card c){
            super(p,c);
        }

        @Override
        protected Enchantment createEnchantment() {
            return new AbductionEnchantment(owner);
        }
        
        @Override
        public void checkTarget(){
            int index, i;
            i = 0;
            Scanner scan = new Scanner(System.in);
            if(temp.isEmpty()) {
                target = null;
                System.out.println("No creatures on field");
            }
            else {
                MagicPrinter.instance.printCreatures(temp);
                System.out.println("Choose your target");
                do{
                   try{ 
                    index = scan.nextInt();
                   }catch(Exception e){index = -1;}
                }while(index <= 0 || index-1 >= temp.size());
               
                target = temp.get(index-1);
            }
        }
        @Override
        public boolean play() {
            checkTarget();
            if(target == null)
                System.out.println("No target for " + name());
            else {
                target_copy = new SpecialCreatureDecorator(target);
                target.untap();
                temp.remove(target);
                owner.getCreatures().add(target);
                target = owner.getCreatures().get(owner.getCreatures().size()-1);
            }
            return super.play(); 
        }       
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new AbductionEffect(owner, this);
    }
    
    private class AbductionEnchantment extends AbstractEnchantment{
        public AbductionEnchantment(Player owner){
            super(owner);
        }
        @Override
        public String name() {
            return "Abduction";
        }
        
        AbductionEnchantment temp = this;
        
        @Override
        public void insert(){
            super.insert();
            CardGame.instance.getTriggers().register(Triggers.EXIT_CREATURE_FILTER, ReturnOnDeathAction);
        }
        
        @Override
        public void remove(){
            super.remove();
            CardGame.instance.getTriggers().deregister(ReturnOnDeathAction);
        }
        
        private final TriggerAction ReturnOnDeathAction = new TriggerAction(){
            @Override
            public void execute(Object args) {
                if(args == target){
                    CardGame.instance.getAdversary(owner).getCreatures().add(target_copy);
                    owner.getCreatures().remove((Creature)target);
                    
                }
            }     
        };
    }

    @Override
    public String name() {
        return "Abduction";
}

    @Override
    public String type() {
        return "Enchantment";
    }

    @Override
    public String ruleText() {
        return "When" + name() + "comes into play, untap enchanted creature. You control enchanted creature. When enchanted creature is put into a graveyard, return that creature to play under it's owner control.";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
    
    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
}
