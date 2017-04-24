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
public class VolcanicHammer implements Card {
    Player owner2;
    Scanner reader = new Scanner(System.in);
    int target;
    
    private class VolcanicHammerEffect extends AbstractCardEffect {
        public VolcanicHammerEffect(Player p, Card c) { 
            super(p,c); 
            owner2 = CardGame.instance.getAdversary(owner);
        }
        
        public void select_creature() {
            int i = 0;
            int choose;
            System.out.println(owner.name() + " creatures:");
            if(owner.getCreatures().isEmpty())
                System.out.println("Nessuna creatura da selezionare");
            else {
                for(Creature c : owner.getCreatures()) {
                    System.out.println((i+1) + ")" + c.name());
                    i++;
                }
                do{
                    System.out.println("Choose creature:");
                    choose = reader.nextInt();
                }while(choose < 1 || choose > i-1);

                owner.getCreatures().get(choose-1).inflictDamage(3);
            }
        }
        
        public void select_player() {
            int choose;
            do{
                System.out.println(owner.name() + " (0) or " + owner2.name() + " (1)");
                choose = reader.nextInt();
            }while(choose != 1 && choose != 0);
            
            if(choose == 0)
                owner.inflictDamage(3);
            else
                owner2.inflictDamage(3);
            System.out.println("Danno inflitto con successo a " + ((choose == 0) ? owner.name() : owner2.name()));
        }
        
        public void effect_choose() {
            int choose;
            do{
                System.out.println("Inflict to player (0) or creature (1):");
                choose = reader.nextInt();
            }while(choose > 1 || choose < 0);
            if(choose == 1)
                select_creature();
            else
                select_player();
            
        }
        
        @Override
        public void resolve() {
            System.out.println("Effect actived");
            effect_choose();
        }
    }

    @Override
    public Effect getEffect(Player owner) { 
        return new VolcanicHammerEffect(owner, this); 
    }
    
    @Override
    public String name() { return "Volcanic Hammer"; }
    @Override
    public String type() { return "Sorcery"; }
    @Override
    public String ruleText() { return name() + " deals 3 damage to any one creature or player"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return false; }
}