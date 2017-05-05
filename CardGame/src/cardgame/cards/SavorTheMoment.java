/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.DefaultDrawPhase;
import cardgame.Effect;
import cardgame.Player;
import java.util.Scanner;
import cardgame.Phases;
import cardgame.SkipPhase;
import cardgame.TurnManager;
/**
 *
 * @author simonescaboro
 */
public class SavorTheMoment implements Card{

    @Override
    public String name() { return "Savor The Moment"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return "Take another turn after this one. Skip the untap step of that turn "; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
    
    @Override
    public Effect getEffect(Player owner) { 
        return new SavorTheMomentEffect(owner, this); 
    }
    
    private class SavorTheMomentEffect extends AbstractCardEffect {
        
        public SavorTheMomentEffect(Player p, Card c) { 
            super(p,c);
        }
        
        @Override
        public void resolve() {
            // rimuovo la fase di UNTAP del prossimo turno
            owner.setPhase(Phases.UNTAP, new SkipPhase(Phases.UNTAP));
            // aggiungo il turno extra
            CardGame.instance.setTurnManager(new Extra(owner));
        }
    }
    
    /***
     * Creo una classe che estende TurnManager per creare un turno exstra 
     */
    
    private class Extra implements TurnManager{
        private Player player;
        private Player adversary = CardGame.instance.getAdversary(player);;
        
        // definisco giocatore e avversario corrente
        public Extra(Player player) {
            this.player = player;
        }
        
       
        public Player getCurrentPlayer() {
            return player;
        }
        
        
        public Player getCurrentAdversary() {
            return adversary;
        }
        
        
        public Player nextPlayer() {
            CardGame.instance.removeTurnManager(this);
            return player;
        }
    }
    
    
}