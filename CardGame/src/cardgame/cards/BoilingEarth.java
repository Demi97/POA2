/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Card;
import cardgame.Effect;
import cardgame.Player;
import cardgame.AbstractCardEffect;
import cardgame.CardGame;
import cardgame.Creature;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author davide
 */
public class BoilingEarth implements Card {
    @Override
    public String name() {
        return "Boiling Earth";
    }

    @Override
    public String type() {
        return "Sorcery";
    }

    @Override
    public String ruleText() {
        return name() + " deals 1 damage to each creature";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
            
    @Override
    public Effect getEffect(Player owner) {
        return new BoilingEarthEffect(owner, this);
    }
    
    private class BoilingEarthEffect extends AbstractCardEffect{
        public BoilingEarthEffect(Player p, Card c){
            super(p,c);
        }
        
        @Override
        public void resolve() {
            // inizializzo due nuove liste così da non creare problimi con l'iterazione del for each
            List<Creature> creaturesEnemy = new ArrayList<>(CardGame.instance.getAdversary(owner).getCreatures());        
            List<Creature> creaturesMine = new ArrayList<>(owner.getCreatures());
            
            // Infliggo 1 a tutte le creature sul terreno, se presenti
            
            if(creaturesEnemy.isEmpty())
                System.out.println("No creatures on field of " + CardGame.instance.getAdversary(owner).name());
            else{
                for (Creature c : creaturesEnemy)
                    c.inflictDamage(1);
            }
            if(creaturesMine.isEmpty())
                 System.out.println("No creatures on field of " + owner.name());
            else{
                for (Creature c : creaturesMine)
                    c.inflictDamage(1);
            }
        }
    }
}
