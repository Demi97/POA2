/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.CardGame;
import cardgame.Card;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.MagicPrinter;
import cardgame.Player;
import cardgame.Targets;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author simonescaboro
 */
public class AggressiveUrge implements Card {
    
    @Override
    public Effect getEffect(Player owner) {
        return new AggressiveUrgeEffect(owner,this);
    }

    @Override
    public String name() {
        return "Aggressive Urge";
    }

    @Override
    public String type() {
        return "Instant";
    }

    @Override
    public String ruleText() {
        return "Target creatures gets +1/+1 until end of turn";
    }

    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
    private class AggressiveUrgeEffect extends AbstractCardEffect implements Targets{
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private Creature target;
        
        public AggressiveUrgeEffect(Player p, Card c) {
            super(p,c);
        }
 

        @Override
        public void resolve() {
            checkTarget();
            if(target == null)
                System.out.println("No target");
            else {
                final AggressiveUrgeDecorator decorator = new AggressiveUrgeDecorator(target);
                TriggerAction action = new TriggerAction() {
                    @Override
                    public void execute(Object args) {
                        System.out.println("Triggered removal of  from " + target.getDecoratorHead());
                        target.getDecoratorHead().removeDecorator(decorator);
                    }
                };
                System.out.println("Ataching "  + name() + " to " + target.name() + " and registering end of turn trigger");
                CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);

                decorator.setRemoveAction(action);
                target.getDecoratorHead().addDecorator(decorator);
            }
        }

        @Override
        public void checkTarget() {
            int choose;
            List<Creature> creatures = new ArrayList<>();
            Scanner reader = new Scanner(System.in);
            System.out.println("Afflict to player 1 (1) or player 2 (2) creature?");
            do{
                try{
                    choose = reader.nextInt()-1;
                }catch(Exception e){ choose = -1; }
            }while(choose != 0 && choose != 1);
            if(choose == 0) {
                MagicPrinter.instance.printCreatures(owner.getCreatures());
                creatures = owner.getCreatures();
            }
            else {
                MagicPrinter.instance.printCreatures(CardGame.instance.getCurrentAdversary().getCreatures());
                creatures = CardGame.instance.getCurrentAdversary().getCreatures();
            }
            if(creatures.isEmpty()) {
                target = null;
                System.out.println("No creatures on field");
            }
            else {
                do{
                    try{
                        choose = reader.nextInt()-1;
                    }catch(Exception e){ choose = -1; }
                }while(choose < 0 && choose >= creatures.size());
                target = creatures.get(choose);
            }
        }
        
    }
    
    private class AggressiveUrgeDecorator extends CreatureDecorator {
        TriggerAction dec;
        
        public AggressiveUrgeDecorator(Creature decoratedCreature) {
            super(decoratedCreature);
        }
        
        public void setRemoveAction(TriggerAction a){
            dec = a;
        }
        
        public void remove(){
            System.out.println("Removing " + name() + " and deregistering end of turn trigger");
            if (dec != null)
                CardGame.instance.getTriggers().deregister(dec);
            super.remove();
        }

        @Override
        public int getPower() {
            return decoratedCreature.getPower()+1;
        } 
        @Override
        public int getToughness() {
            return decoratedCreature.getToughness()+1;
        } 
    }
}
