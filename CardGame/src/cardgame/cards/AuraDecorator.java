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
    
    @Override
    public int getPower() {
        attack = decoratedCreature.getPower()+(numEnchantment*2);
        return attack;
    }

    @Override
    public int getToughness() {
        defense = decoratedCreature.getToughness()+(numEnchantment*2);
        return defense;
    }
    
    public void setNumEnchantment(int k){
        numEnchantment+=k;
    }
    
    public AuraDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
    }

    
}
