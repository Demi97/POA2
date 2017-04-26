/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractCreature;
import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.MagicPrinter;
import cardgame.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diletta
 */
public class BenevolentAncestor implements Card{
    
    private class BenevolentAncestorEffect extends AbstractCreatureCardEffect{
       
        public BenevolentAncestorEffect(Player p, Card c){
            super(p,c);
        }

        @Override
        protected Creature createCreature() {
            return new BenevolentAncestorCreature(owner);
        }         
    }
    
    @Override
    public Effect getEffect(Player p) {
        return new BenevolentAncestorEffect(p, this);
    }

    private class BenevolentAncestorCreature extends AbstractCreature{
        ArrayList<Effect> all_effects=new ArrayList<>();
        ArrayList<Effect> tap_effects=new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        
        BenevolentAncestorCreature(Player owner){
            super(owner);
            all_effects.add(new Effect(){
                @Override
                public boolean play() {
                    int choice, index;
                    System.out.println("Your target is a creature (press 1) or a player (press 2)?");
                     do{
                        try{
                            choice = scan.nextInt();
                        }catch(Exception e){
                            choice = -1;
                        }
                    }while(choice!=1 && choice!=2);
                    if(choice==1){
                        MagicPrinter.instance.printCreatures(owner.getCreatures());
                    }
                    CardGame.instance.getStack().add(this);
                    return tap();
                }

                @Override
                public void resolve() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            }
        }

        @Override
        public int getPower() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getToughness() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<Effect> effects() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<Effect> avaliableEffects() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String name() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    @Override
    public String name() {
        return "Benevolent Ancestor";
    }

    @Override
    public String type() {
        return "Creature";
    }

    @Override
    public String ruleText() {
        return "Defender (This creature can't attack). Prevent the next 1 damage that would be dealt to target creature or player this turn";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
    
}
