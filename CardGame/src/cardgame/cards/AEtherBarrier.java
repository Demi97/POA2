/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

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

      /*  @Override
        public boolean play() {
            int index, size, choice, i=0;
            Scanner scan = new Scanner(System.in);
            List<Creature> creatures = CardGame.instance.getAdversary(owner).getCreatures();
            List<Enchantment> enchantments = CardGame.instance.getAdversary(owner).getEnchantments();
            System.out.println("Creatures on the field:");
            for(Creature c : creatures){
                System.out.println(""+(i+1)+c.name());
                i++;
            }
            i=0;
            System.out.println("Enchantements on the  field");
            for(Enchantment e : enchantments){
                System.out.println(""+(i+1)+e.name());
                i++;
            }
            System.out.println("Do you want to sacrifice a creature (press 1) or an enchantment (press 2)? ");
            do{
                try{
                    choice = scan.nextInt();
                }catch(Exception e){choice = -1;}
            }while(choice!=1 || choice!=2);
            System.out.println("Choose the permanent to sacrifice");
            if(choice == 1){
                for(Creature c : creatures){
                    System.out.println(""+(i+1)+c.name());
                    i++;
                }
                size = creatures.size();
            }else{
                for(Enchantment e : enchantments){
                    System.out.println(""+(i+1)+e.name());
                    i++;
                }
                size = enchantments.size();
            }
            do{
                try{
                    index = scan.nextInt();
                }catch(Exception e){index = -1;}
            }while(index<0 || index>size);
            return super.play();
            if(choice == 1){
                c 
                owner.destroy(c);
            }
        }*/
        
        
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
