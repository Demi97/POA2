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
    private Player adversary;
    
    private class FatigueEffect extends AbstractCardEffect implements Targets{
        Player target;
        public FatigueEffect(Player p, Card c) { 
            super(p,c);
            // definisco l'avversario
            adversary = CardGame.instance.getAdversary(owner);
        }
    
     /***
      * Metodo per scegliere il giocatore
      * @return il giocatore al quale voglio rivolgere l'effetto
      */
     public void checkTarget() {
        int choose;
        Scanner reader = new Scanner(System.in);
        do{
            System.out.println(owner.name() + " (1) or " + adversary.name() + " (2)");
            try{
                choose = reader.nextInt()-1;
            }catch(Exception e) { choose = -1; }
        }while(choose != 1 && choose != 0);
        target = ( (choose == 0) ? owner : adversary);
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
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new FatigueEffect(owner, this); 
    }
    
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
}