/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Card;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.Player;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.AbstractEnchantment;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author diletta
 */
public class Abduction implements Card {
    Creature target;
    private class AbductionEffect extends AbstractEnchantmentCardEffect{
        public AbductionEffect(Player p, Card c){
            super(p,c);
        }

        @Override
        protected Enchantment createEnchantment() {
            return new AbductionEnchantment(owner);
        }

        @Override
        public boolean play() {
            int index, i=0;
            Scanner scan = new Scanner(System.in);
            List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
            for(Creature c : temp){
                System.out.println(""+(i+1)+c.name());
                i++;
            }
            System.out.println("Choose your target");
            do{
               try{ 
                index = scan.nextInt();
               }catch(Exception e){index = -1;}
            }while(index < 0 || index > temp.size()-1);
            target = temp.get(index-1);
            return super.play(); 
        }       
    }
    
    @Override
    public Effect getEffect(Player owner) {
        return new AbductionEffect(owner, this);
    }
    
    private class AbductionEnchantment extends AbstractEnchantment{
        public AbductionEnchantment(Player owner){
            super(owner);
        }
        @Override
        public String name() {
            return "Abduction";
        }
        
        AbductionEnchantment temp = this;
        
        private final TriggerAction ControlChangeAction = new TriggerAction(){
            @Override
            public void execute(Object args){
                if(args == temp){
                    List<Creature> temp = CardGame.instance.getAdversary(owner).getCreatures();
                    target.untap();
                    temp.remove(target);
                    owner.getCreatures().add(target);
                    CardGame.instance.getTriggers().deregister(this);
                }
            }
        };
        
        /*private final TriggerAction ReturnOnDeathAction = new TriggerAction(){
            @Override
            public void execute(Object args) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        }*/
        
    }

    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String type() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String ruleText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isInstant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
