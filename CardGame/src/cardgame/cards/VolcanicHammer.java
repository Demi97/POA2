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
import cardgame.MagicPrinter;
/**
 * ciao
 */
/**
 *
 * @author simonescaboro
 */
public class VolcanicHammer implements Card {
    
    // Definisco l'avversario come "l'avversario del giocatore"
    Player adversary;
    Scanner reader = new Scanner(System.in);
    int target;
    
    private class VolcanicHammerEffect extends AbstractCardEffect {
        public VolcanicHammerEffect(Player p, Card c) { 
            super(p,c); 
            adversary = CardGame.instance.getAdversary(owner);
        }
        /***
         * Metodo per la selezione della creatura
         */
        public void select_creature() {
            int i = 0;
            int choose;
            System.out.println(owner.name() + " creatures:");
            // se non ci sono creature lo comunica al giocatore
            if(owner.getCreatures().isEmpty())
                System.out.println("... no creatures to select");
            else {
                MagicPrinter.instance.printCreatures(owner.getCreatures());
                do{
                    System.out.println("Choose creature:");
                    try{
                        choose = reader.nextInt();
                    }catch(Exception e) { choose = -1; }
                }while(choose < 1 || choose > i-1);
                // infligge il danno alla creatura selezionata
                owner.getCreatures().get(choose-1).inflictDamage(3);
            }
        }
        
        /***
         * Chiedo al giocatore a quale gicatore infliggere il danno, sai mai
         */
        public void select_player() {
            int choose;
            do{
                System.out.println(owner.name() + " (1) or " + adversary.name() + " (2)");
                try{
                    choose = reader.nextInt()-1;
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 0);
            // infliggo il danno al giocatore selezionato
            if(choose == 0)
                owner.inflictDamage(3);
            else
                adversary.inflictDamage(3);
            // comunico di aver inflitto il danno al giocatore
            System.out.println("Damage successfully inflicted to " + ((choose == 0) ? owner.name() : adversary.name()));
        }
        
        /***
         * Chiedo al giocatore se vuole infliggere ad una creatura oppure ad un giocatore
         */
        public void effect_choose() {
            int choose;
            do{
                System.out.println("Inflict to player (0) or creature (1):");
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            }while(choose > 1 || choose < 0);
            
            if(choose == 1)
                select_creature();
            else
                select_player();
            
        }
        
        /***
         * Risolvo l'effetto chiamando effect_choose(); 
         */
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