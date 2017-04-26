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
import java.util.List;


/**
 *
 * @author davide
 */
public class BoilingEarth implements Card {
    
    private class BoilingEarthEffect extends AbstractCardEffect{
        public BoilingEarthEffect(Player p, Card c){
            super(p,c);
        }
        
        @Override
        public void resolve() {
            List<Creature> creaturesEnemy = CardGame.instance.getAdversary(owner).getCreatures();        
            List<Creature> creaturesMine = owner.getCreatures();
            // Infliggo 1 a tutte le creature sul terreno
            for (Creature c : creaturesEnemy) {
                c.inflictDamage(1);
            }
            for (Creature c : creaturesMine) {
                c.inflictDamage(1);
            }
            System.out.println("Damages succesfully afflicted");
        }
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new BoilingEarthEffect(owner, this);
    }
    
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
    
}
