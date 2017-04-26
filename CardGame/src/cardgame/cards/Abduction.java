/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Card;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.Player;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.AbstractEnchantment;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.MagicPrinter;
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
    private class AbductionEffect extends AbstractEnchantmentCardEffect{
        public AbductionEffect(Player p, Card c){
            super(p,c);
        }

        @Override
        protected Enchantment createEnchantment() {
            return new AbductionEnchantment(owner);
        }

        @Override
        public boolean play() {
            int index, i=0;
            Scanner scan = new Scanner(System.in);
            List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
            if(temp.isEmpty())
                System.out.println("No creatures on field");
            else {
                MagicPrinter.instance.printCreatures(temp);
                System.out.println("Choose your target");
                do{
                   try{ 
                    index = scan.nextInt();
                   }catch(Exception e){index = -1;}
                }while(index < 0 || index > temp.size()-1);
                target = temp.get(index-1);
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
            List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
            target.untap();
            temp.remove(target);
            owner.getCreatures().add(target);
            CardGame.instance.getTriggers().register(Triggers.EXIT_CREATURE_FILTER, ReturnOnDeathAction);
            super.insert();
        }
        
        private final TriggerAction ReturnOnDeathAction = new TriggerAction(){
            @Override
            public void execute(Object args) {
                if(args == target){
                    List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
                    owner.getCreatures().remove(target);
                    temp.add(target);
                    CardGame.instance.getTriggers().deregister(this);
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
