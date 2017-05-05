/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Player;
import cardgame.Enchantment;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.AbstractEnchantment;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Permanent;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import cardgame.MagicPrinter;
import cardgame.Visitor;

/**
 *
 * @author diletta
 */
public class AEtherBarrier implements Card {
    private class AEtherBarrierEffect extends AbstractEnchantmentCardEffect{
        public AEtherBarrierEffect(Player p, Card c){
            super(p,c);
        }
        
        protected Enchantment createEnchantment(){
            return new AEtherBarrierEnchantment(owner);
        }

        @Override
        public boolean play() {
            return super.play();
        }
        
        
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new AEtherBarrierEffect(owner, this);
    }
    
    private class AEtherBarrierEnchantment extends AbstractEnchantment{
        public AEtherBarrierEnchantment(Player owner){
            super(owner);
        }
 
        @Override
        public String name() {
            return "AEther Barrier";
        }
        
        public void insert(){
            CardGame.instance.getTriggers().register(Triggers.ENTER_EFFECT_STACK_FILTER, SacrificeOnCreatureEntranceAction);
            super.insert();
        }
        
        private final TriggerAction SacrificeOnCreatureEntranceAction = new TriggerAction(){
            
            public Permanent choose_permanent(Player p) {
                int choice,index, size, i=0;
                Scanner scan = new Scanner(System.in);
                List<Creature> creatures = p.getCreatures();
                List<Enchantment> enchantments = p.getEnchantments();
                System.out.println("Creatures on the field:");
                MagicPrinter.instance.printCreatures(creatures);
                System.out.println("Enchantements on the  field");
                MagicPrinter.instance.printEnchantments(enchantments);
                System.out.println("Do you want to sacrifice a creature (press 1) or an enchantment (press 2)? ");
                do{
                    try{
                        choice = scan.nextInt();
                    }catch(Exception e){
                        choice = -1;
                    }
                }while(choice!=1 && choice!=2);
                System.out.println("Choose the permanent to sacrifice");
                if(choice == 1){
                    MagicPrinter.instance.printCreatures(creatures);
                    size = creatures.size();
                }else{
                    MagicPrinter.instance.printEnchantments(enchantments);
                    size = enchantments.size();
                }
                if(size == 0)
                    System.out.println("No permanents on field to sacrifice");
                else {
                    do{
                        try{
                            index = scan.nextInt()-1;
                        }catch(Exception e){index = -1;}
                    }while(index < 0 || index >= size);
                    if(choice == 1){
                        return creatures.get(index);
                    }else{
                        return enchantments.get(index);
                    }
                }
                return null;
            }
            
            @Override
            public void execute(Object args) {
                Permanent choosen;
                if(args instanceof AbstractCreatureCardEffect && args!=null){
                    choosen = choose_permanent(((AbstractCreatureCardEffect)args).getPlayer());
                    if(choosen == null)
                        System.out.println("Effect not activated");
                    else
                        choosen.remove();
                }
            }
        };

        @Override
        public void acceptVisit(Visitor visitor) {
            visitor.visit(this);
        }
        
    }

    @Override
    public String name() {
        return "AEther Barrier";
    }

    @Override
    public String type() {
        return "Enchantment";
    }

    @Override
    public String ruleText() {
        return "Whenever a player plays a creature spell, that player sacrifices a permanent";
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() +"]";
    }
    
    
    
}
