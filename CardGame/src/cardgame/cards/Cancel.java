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
import cardgame.CardStack;
import cardgame.MagicPrinter;
import cardgame.Targets;
import java.util.Scanner;

/**
 *
 * @author Davide
 */
public class Cancel implements Card {
    Scanner reader = new Scanner(System.in);
    
    private class CancelEffect extends AbstractCardEffect implements Targets {
        Effect target;
        
        public CancelEffect(Player p, Card c) { 
            super(p,c); 
        }
        
        public void checkTarget() {
            int i = 1;
            int choose;
            
            CardStack stack = CardGame.instance.getStack();
            for (Effect e : stack) {
                System.out.println(i + ") " + e.toString());
                ++i;
            }
            
            do{
                System.out.println(owner.name() + ": choose target for " + name());
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            } while(choose < 1 || choose > i);
            
            i = 1;
            for (Effect e : stack) {
                if(i == choose)
                    target = e;
                i++;
            }
        }
        
        @Override
        public void resolve() {
            checkTarget();
            
            if (target == null) {
                System.out.println(card + " has no target");
            }
            else {
                System.out.println(card + " removing " + target + " from stack");
                CardGame.instance.getStack().remove(target);
            }
        }
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new CancelEffect(owner, this); 
    }
    
    @Override
    public String name() { 
        return "Cancel"; 
    }
    
    @Override
    public String type() { 
        return "Instant"; 
    }
    
    @Override
    public String ruleText() { 
        return "Counter target spell"; 
    }
    
    @Override
    public String toString() { 
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
    @Override
    public boolean isInstant() { 
        return true; 
    }
}
