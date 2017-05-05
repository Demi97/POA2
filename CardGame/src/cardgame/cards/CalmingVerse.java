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
import cardgame.Enchantment;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author simonescaboro
 */
public class CalmingVerse implements Card {

    @Override
    public String name() { return "Calming Verse"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return "Destroy all enchantments you don't control"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
        
    @Override
    public Effect getEffect(Player owner) { 
        return new CalmingVerseEffect(owner, this); 
    }
    
    private class CalmingVerseEffect extends AbstractCardEffect {
        public CalmingVerseEffect(Player p, Card c) { super(p,c); }
        
        @Override
        public void resolve() {
            clearEnchantments(CardGame.instance.getAdversary(owner));
        }
        
        public void clearEnchantments(Player player) {
            // Controllo se ci sono incantamenti da distruggere
            if(player.getEnchantments().isEmpty())
                System.out.println("There are no enchantments to destroy");
            else {
                // se ci sono incantamenti li distruggo
                List<Enchantment> tmp = new ArrayList<>(player.getEnchantments());
                for(Enchantment e : tmp) {
                    if(!e.isRemoved())
                        e.remove();
                }
                System.out.println("All enchantments destroied");
            }
            
        }
    }
}