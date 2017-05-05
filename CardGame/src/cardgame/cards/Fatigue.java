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
public class Fatigue implements Card{

    @Override
    public String name() { return "Fatigue"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return "Target player skips his or her next draw step"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
    
    @Override
    public Effect getEffect(Player owner) { 
        return new FatigueEffect(owner, this); 
    }
    
    private class FatigueEffect extends AbstractCardEffect implements Targets{
        Player target;
        // definisco l'avversario
        private Player currentAdversary = CardGame.instance.getAdversary(owner);
        
        public FatigueEffect(Player p, Card c) { 
            super(p,c);
        }
        
        @Override
        public boolean play() {
            checkTarget();
            return super.play();
        }
    
        @Override
        public void resolve() {
            // gli skippo la draw phase
            target.setPhase(Phases.DRAW,new SkipPhase(Phases.DRAW));
            System.out.println(target.name() + "'ll skips his/her draw phase");
        }
        
        /***
         * Metodo per scegliere il giocatore
         * @return il giocatore al quale voglio rivolgere l'effetto
         */
        public void checkTarget() {
            int choose;
            Scanner reader = new Scanner(System.in);
            do{
                System.out.println(owner.name() + " (1) or " + currentAdversary.name() + " (2)");
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 2);
            target = ( (choose == 1) ? owner : currentAdversary);
        }
    }
}