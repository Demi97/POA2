/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Player;
import cardgame.CardGame;
import cardgame.Enchantment;
import cardgame.MagicPrinter;
import java.util.Scanner;

/**
 *
 * @author atorsell
 */
public class AuraBlast implements Card {
    private Player owner2;
    Scanner reader = new Scanner(System.in);
    private class AuraBlastEffect extends AbstractCardEffect {
        public AuraBlastEffect(Player p, Card c) { 
            super(p,c); 
            owner2 = CardGame.instance.getAdversary(owner);
        }
        
        
        public int choose_enchantments(Player player) {
            int choose;
            do{
                System.out.println("Choose enchantment: ");
                choose = reader.nextInt()-1;
            }while(choose < 0 || choose >= player.getEnchantments().size());
            
            return choose;
        }
        
        public void operation(Player player) {
            int target;
            if(player.getEnchantments().isEmpty())
                    System.out.println(player.name() + " has no enchantments on field");
            else{
                MagicPrinter.instance.printEnchantments(player.getEnchantments());
                target = choose_enchantments(player);
                player.getEnchantments().remove(target);
            }
        }
        
        public void select_enchantments() {
            int choose;
            do{
                System.out.println(owner.name() + " (1) or " + owner2.name() + " (2)");
                choose = reader.nextInt()-1;
            }while(choose != 0&& choose != 1);
            if(choose == 0)
                operation(owner);
            else
                operation(owner2);
        }
            
        
        
        @Override
        public void resolve() {
            select_enchantments();
        }
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new AuraBlastEffect(owner, this); 
    }
    
    @Override
    public String name() { return "Aura Blast"; }
    @Override
    public String type() { return "Instant"; }
    @Override
    public String ruleText() { return "Destroy target enchantment. Draw a card"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return true; }
}
