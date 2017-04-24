/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Player;
import cardgame.CardGame;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author simonescaboro
 */
public class Darkness implements Card {
    Scanner reader = new Scanner(System.in);
    private class DarknessEffect extends AbstractCardEffect {
        public DarknessEffect(Player p, Card c) { super(p,c); }
        

        @Override
        public void resolve() {
                //owner.getCreatures().get(target).addPower(2)
                //owner.getCreatures().get(target).addToughness(2)
                //CardGame.instance.getTriggers().register(Triggers., resetValues);
        }
    }

    private final TriggerAction resetValues = new TriggerAction(){
        @Override
        public void execute(Object args) {
            //owner.getCreatures().get(target).addPower(-2)
            //owner.getCreatures().get(target).addToughness(-2)  
            System.out.println("ciaoooo");
        }     
    };
    @Override
    public Effect getEffect(Player owner) { 
        return new DarknessEffect(owner, this); 
    }
    
    @Override
    public String name() { return "Darkness"; }
    @Override
    public String type() { return "Instant"; }
    @Override
    public String ruleText() { return "Target creature gets +2/+2 until end of turn"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return true; }
}