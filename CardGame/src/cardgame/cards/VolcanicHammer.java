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
import cardgame.Targets;

/**
 *
 * @author simonescaboro
 */
public class VolcanicHammer implements Card {
    
    // Definisco l'avversario come "l'avversario del giocatore"
    Player currentAdversary = CardGame.instance.getCurrentAdversary();
    Scanner reader = new Scanner(System.in);
    Player player_target = null;
    Creature creature_target = null;
    
    private class VolcanicHammerEffect extends AbstractCardEffect implements Targets {
        public VolcanicHammerEffect(Player p, Card c) { 
            super(p,c); 
            //adversary = CardGame.instance.getAdversary(owner);
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
                creature_target = owner.getCreatures().get(choose-1);
            }
        }
        
        /***
         * Chiedo al giocatore a quale gicatore infliggere il danno
         */
        public void select_player() {
            int choose;
            do{
                System.out.println(owner.name() + " (1) or " + currentAdversary.name() + " (2)");
                try{
                    choose = reader.nextInt()-1;
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 0);
            
            // infliggo il danno al giocatore selezionato
            if(choose == 0)
                player_target = owner;
            else
                player_target = currentAdversary;
        }
        
        /***
         * Chiedo al giocatore se vuole infliggere ad una creatura oppure ad un giocatore
         */
        public void checkTarget() {
            int choose;
            do{
                System.out.println("Inflict to player (0) or creature (1):");
                try{
                    choose = reader.nextInt();
                }catch(Exception e) { choose = -1; }
            }while(choose != 1 && choose != 0);
            // attivo l'effetto sulla creatura
            if(choose == 1)
                select_creature();
            // attivo l'effetto su un giocatore
            else
                select_player();
            
        }
        @Override
        public boolean play(){
            checkTarget();
            return super.play();
        }
        /***
         * Risolvo l'effetto chiamando effect_choose(); 
         */
        @Override
        public void resolve() {
            if(player_target != null)  {
                player_target.inflictDamage(3);
                // comunico di aver inflitto il danno al giocatore
                System.out.println("Damage successfully inflicted to " + player_target.name());
            } else {
                if(creature_target.isRemoved())
                    System.out.println("Creature removed");
                else {
                    creature_target.getDecoratorHead().inflictDamage(3);
                    // comunico di aver inflitto il danno alla creatura
                    System.out.println("Damage successfully inflicted to " + creature_target.name());
                }
            }
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