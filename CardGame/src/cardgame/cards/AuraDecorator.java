/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Creature;
import cardgame.Effect;
import java.util.List;

/**
 *
 * @author diletta
 */
public class AuraDecorator extends CreatureDecorator{
    int defense, attack;
    int numEnchantment;
    
    private void setAttack(){
        attack = decoratedCreature.getPower()+(numEnchantment*2);
    }
    
    private void setDefense(){
        defense = decoratedCreature.getToughness()+(numEnchantment*2);
    }

    @Override
    public int getPower() {
        setAttack();
        return attack;
    }

    @Override
    public int getToughness() {
        setDefense();
        return defense;
    }
    
    public void setNumEnchantment(int k){
        numEnchantment=k;
    }
    
    public AuraDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
    }

    
}
