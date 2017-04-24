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
import java.util.Scanner;
/**
 * ciao
 */
/**
 *
 * @author atorsell
 */
public class CalmingVerse implements Card {
    
    private class CalmingVerseEffect extends AbstractCardEffect {
        public CalmingVerseEffect(Player p, Card c) { super(p,c); }
        
        public void clearEnchantments(Player player) {
            if(player.getEnchantments().isEmpty())
                System.out.println("There are no enchantments to destroy");
            else {
                player.getEnchantments().clear();
                System.out.println("All enchantments destroied");
            }
            
        }
        @Override
        public void resolve() {
            clearEnchantments(CardGame.instance.getAdversary(owner));
        }
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new CalmingVerseEffect(owner, this); 
    }
    
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
}