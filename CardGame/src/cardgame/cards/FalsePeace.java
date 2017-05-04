/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Effect;
import cardgame.Player;
import java.util.Scanner;
import cardgame.Phases;
import cardgame.SkipPhase;
import cardgame.Targets;

/**
 *
 * @author simonescaboro
 */
public class FalsePeace implements Card{
    
    @Override
    public String name() { return "False Peace"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return "Target player skips his or her next combat step"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
        
    @Override
    public Effect getEffect(Player owner) { 
        return new FalsePeaceEffect(owner, this); 
    }
    
    private class FalsePeaceEffect extends AbstractCardEffect implements Targets {
        Player target;
        private Player adversary = CardGame.instance.getAdversary(owner);
        
        public FalsePeaceEffect(Player p, Card c) { 
            super(p,c);
        }
        
        @Override
        public boolean play() {
            checkTarget();
            return super.play();
        }

        @Override
        public void resolve() {
            target.setPhase(Phases.COMBAT,new SkipPhase(Phases.COMBAT));
            System.out.println(target.name() + "'ll skips his/her combat phase");
        }
        
        public void checkTarget() {
            int choose;
            Scanner reader = new Scanner(System.in);
            do{
                System.out.println(owner.name() + " (1) or " + adversary.name() + " (2)");
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 2);
            target = ((choose == 1) ? owner : adversary);
        }
        
    }
}