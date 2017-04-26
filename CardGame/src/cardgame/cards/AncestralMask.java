/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractEnchantment;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.MagicPrinter;
import cardgame.Player;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diletta
 */
public class AncestralMask implements Card {
    Creature target;
    private class AncestralMaskEffect extends AbstractEnchantmentCardEffect{
        
        public AncestralMaskEffect(Player p, Card c){
            super(p,c);
        }
        
        @Override
        protected Enchantment createEnchantment() {
            return new AncestralMaskEnchantment(owner);
        }

        @Override
        public boolean play() {
            int i=0, index;
            Scanner scan = new Scanner(System.in);
            List<Creature> creatures = owner.getCreatures();
            if(creatures.isEmpty())
                System.out.println("No creatures on field");
            else {
                MagicPrinter.instance.printCreatures(owner.getCreatures());
                do{
                   try{ 
                     index = scan.nextInt();
                   }catch(Exception e){index = -2;}
                }while(index < 0 || index-1 >= creatures.size());
                target = creatures.get(index-1);
            }
            return super.play(); 
        }
        
        
                
    }
    @Override
    public Effect getEffect(Player owner) {
        return new AncestralMaskEffect(owner, this);
    }
    
    private class AncestralMaskEnchantment extends AbstractEnchantment{
        AuraDecorator dec;
        
        public AncestralMaskEnchantment(Player owner){
            super(owner);
        }
        
        @Override
        public String name() {
            return "Ancestral Mask";
        }

        @Override
        public void insert() {
            dec = new AuraDecorator(target);
            target.addDecorator(dec);
            dec.activation(owner);
            super.insert();
        }

        @Override
        public void remove() {
            target.removeDecorator(dec);
            super.remove();
        }
        
        
       
        
    }

    @Override
    public String name() {
        return "Ancestral Mask";
    }

    @Override
    public String type() {
        return "Enchantment";
    }

    @Override
    public String ruleText() {
        return "Enchant creature. Enchanted creature gets +2/+2 for each other enchantment on the battlefield";
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
