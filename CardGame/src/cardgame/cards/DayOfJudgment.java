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
/**
 *
 * @author simonescaboro
 */
public class DayOfJudgment implements Card {

    @Override
    public String name() { return "Day Of Judgment"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return "Destroy all creatures"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
        
    @Override
    public Effect getEffect(Player owner) { 
        return new DayOfJudgmentEffect(owner, this); 
    }
    
    private class DayOfJudgmentEffect extends AbstractCardEffect {
        public DayOfJudgmentEffect(Player p, Card c) { super(p,c); }
        
        @Override
        public void resolve() {
            clearCreatures(owner);
            clearCreatures(CardGame.instance.getAdversary(owner));
        }
        
        public void clearCreatures(Player player) {
            // verifico se il giocatore ha carte sul terreno, se le ha le rimuovo
            if(player.getCreatures().isEmpty())
                System.out.println(player.name() + " has no creatures to destroy!");
            else {
                player.getCreatures().clear();
                System.out.println(player.name() + "'s creatures destroyed!");
            }  
        }
    }
}