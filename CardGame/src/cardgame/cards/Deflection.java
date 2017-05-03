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
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Davide
 */
public class Deflection implements Card {
    Scanner reader = new Scanner(System.in);
    
    private class DeflectionEffect extends AbstractCardEffect implements Targets {
        Targets target;
        
        public DeflectionEffect(Player p, Card c) { 
            super(p,c); 
        }
        
        @Override
        public void checkTarget() {
            int i = 1;
            int choose;

            CardStack stack = CardGame.instance.getStack();
            for (Effect e : stack) {
                if (e instanceof Targets) {
                    System.out.println(i + ") " + e.toString());
                    ++i;
                }
            }
            i = 1;
            do{
                System.out.println(owner.name() + ": choose target for " + name());
                for(Effect e : stack) {
                    if(e instanceof Targets) {
                        System.out.println(i + ") " + e.toString());
                        i++;
                    }
                }
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { 
                    choose = -1; 
                }
            } while(choose < 1 || choose > i);
            
            i = 1;
            for (Effect e : stack) {
                if (e instanceof Targets) {
                    if(i == choose)
                        target = (Targets)e;
                    i++;
                }
            }
        }
        
        @Override
        public boolean play(){
            checkTarget();
            return super.play();
        }
        @Override
        public void resolve() {
            if (target == null) {
                System.out.println(card + " has no target");
            }
            else {
                target.checkTarget();
            }
        }
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new DeflectionEffect(owner, this); 
    }
    
    @Override
    public String name() { 
        return "Deflection"; 
    }
    @Override
    public String type() { 
        return "Instant"; 
    }
    @Override
    public String ruleText() { 
        return "Change the target of target spell with a single target"; 
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
