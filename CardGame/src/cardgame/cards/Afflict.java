/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.CreatureDecorator;
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
public class Afflict implements Card {
    
    @Override
    public String name() {
        return "Afflict";
    }

    @Override
    public String type() {
        return "Instant";
    }

    @Override
    public String ruleText() {
        return "Target creatures gets -1/-1 until end of turn";
    }

    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new AfflictEffect(owner,this);
    }

    private class AfflictEffect extends AbstractCardEffect implements Targets{
        
        private Creature target;
        
        public AfflictEffect(Player p, Card c) {
            super(p,c);
        }
        
        @Override
        public boolean play() {
            checkTarget();
            return super.play();
        }

        @Override
        public void resolve() {
            if(target == null)
                System.out.println("No target");
            else {
                if(target.isRemoved())
                    System.out.println("Target already removed");
                else {
                    final AfflictDecorator decorator = new AfflictDecorator(target);
                    TriggerAction action = new TriggerAction() {
                        @Override
                        public void execute(Object args) {
                            System.out.println("Triggered removal " + target.getDecoratorHead());
                            target.removeDecorator(decorator);
                        }
                    };
                    System.out.println("Ataching to " + target.name() + " and registering end of turn trigger");
                    CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);

                    decorator.setRemoveAction(action);
                    target.addDecorator(decorator);
                }
            }
        }

        @Override
        public void checkTarget() {
            int choose;
            List<Creature> creatures = new ArrayList<>();
            Scanner reader = new Scanner(System.in);
            System.out.println("Afflict to" +  owner.name() +" (1) or"+ CardGame.instance.getAdversary(owner).name() +" (2) creature?");
            do{
                try{
                    choose = reader.nextInt();
                }catch(Exception e){ choose = -1; }
            }while(choose != 1 && choose != 2);
            System.out.println("Choose creature to afflict: ");
            if(choose == 1) { 
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
    
    private class AfflictDecorator extends CreatureDecorator {
        TriggerAction dec;
        
        public AfflictDecorator(Creature decoratedCreature) {
            super(decoratedCreature);
            decoratedCreature.getDecoratorHead().inflictDamage(1);
        }
        
        public void setRemoveAction(TriggerAction a){
            dec = a;
        }
        
        public void remove(){
            System.out.println("Removing " + name());
            if (dec != null)
                CardGame.instance.getTriggers().deregister(dec);
            super.remove();
        }

        @Override
        public int getPower() {
            return decoratedCreature.getPower()-1;
        } 
        @Override
        public int getToughness() {
            return decoratedCreature.getToughness()-1;
        }
        @Override
        public boolean isRemoved(){
            if(decoratedCreature.getToughness()-1 <= 0){
                return true;
            }
            else
                return false;
        }

        @Override
        public boolean isAttackable() {
            return decoratedCreature.isAttackable();
        }
    }
}
