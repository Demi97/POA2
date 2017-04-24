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
 * ciao
 */
/**
 *
 * @author atorsell
 */
public class AggressiveUrge implements Card {
    Scanner reader = new Scanner(System.in);
    private class AggressiveUrgeEffect extends AbstractCardEffect {
        public AggressiveUrgeEffect(Player p, Card c) { super(p,c); }
        
        public int select_creature() {
            int i = 0;
            int choose = 0;
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

            }
            return choose-1;
        }
        @Override
        public void resolve() {
            int target = select_creature();
            if(target == -1) {
                System.out.println("No creatures on field");
            }
            else {
                //owner.getCreatures().get(target).addPower(2)
                //owner.getCreatures().get(target).addToughness(2)
                CardGame.instance.getTriggers().register(Triggers.END_FILTER, resetValues);
            

            }
        }
    }

    private final TriggerAction resetValues = new TriggerAction(){
        @Override
        public void execute(Object args) {
            //owner.getCreatures().get(target).addPower(-2)
            //owner.getCreatures().get(target).addToughness(-2)  
        }     
    };
    @Override
    public Effect getEffect(Player owner) { 
        return new AggressiveUrgeEffect(owner, this); 
    }
    
    @Override
    public String name() { return "Aggressive Urge"; }
    @Override
    public String type() { return "Instant"; }
    @Override
    public String ruleText() { return "Target creature gets +2/+2 until end of turn"; }
    @Override
    public String toString() { return name() + " (" + type() + ") [" + ruleText() +"]";}
    @Override
    public boolean isInstant() { return true; }
}