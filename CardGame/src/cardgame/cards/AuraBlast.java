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
import cardgame.Targets;
import java.util.Scanner;

/**
 *
 * @author simonescaboro
 */
public class AuraBlast implements Card {
    
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
    
    @Override
    public Effect getEffect(Player owner) { 
        return new AuraBlastEffect(owner, this); 
    }

    Scanner reader = new Scanner(System.in);
    
    private class AuraBlastEffect extends AbstractCardEffect implements Targets{
        
        private Player adversary = CardGame.instance.getAdversary(owner);
        private Player target;
        private Enchantment enchantment_target;
        
        public AuraBlastEffect(Player p, Card c) { 
            super(p,c);  
        }
                    
        @Override
        public boolean play() {
            checkTarget();
            return super.play();
        }
        
        @Override
        public void resolve() {
            if(target == null) {
                System.out.println("No target for " + name());
            } else {
                if(enchantment_target.isRemoved()) 
                    System.out.println("Enchantment already removed");
                else {
                    enchantment_target.remove();
                    target.draw();
                }
            }
        }
        /***
         * Scelgo l'incantamento da distruggere
         * @param player
         */
        public void choose_enchantments(Player player) {
            int choose;
            do{
                System.out.println("Choose enchantment: ");
                try{
                    choose = reader.nextInt()-1;
                }catch(Exception e) { choose = -1; }
            }while(choose < 0 || choose >= player.getEnchantments().size());
            
            enchantment_target = player.getEnchantments().get(choose);
        }
        
        public void operation(Player player) {
            if(player.getEnchantments().isEmpty())
                    System.out.println(player.name() + " has no enchantments on field");
            else{
                MagicPrinter.instance.printEnchantments(player.getEnchantments());
                choose_enchantments(player);
            }
        }
        
        public void checkTarget() {
            int choose;
            do{
                System.out.println(owner.name() + " (1) or " + adversary.name() + " (2)");
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 2);
            if(choose == 1) {
                // svolgo l'operazione di rimozione 
                target = owner;
            }
            else {
                target = adversary;
            }
            operation(target);
        }
    }
}
