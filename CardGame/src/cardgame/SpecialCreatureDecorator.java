/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import cardgame.Creature;
import cardgame.Effect;
import java.util.List;
/**
 *
 * @author diletta
 */
public class SpecialCreatureDecorator extends CreatureDecorator{
    
    //private Creature decoratedCreature;
    public SpecialCreatureDecorator(Creature decoratedCreature) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        super(decoratedCreature);
        //this.decoratedCreature = decoratedCreature;
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    
    @Override
    public Creature getDecoratorHead(){
        return this;
    }
    
    @Override
    public void addDecorator(CreatureDecorator cd){
        cd.setDecoratedCreature(this.getDecoratedCreature());
        this.setDecoratedCreature(cd);
    }

    @Override
    public void removeDecorator(CreatureDecorator cd) {
        Creature next = this.getDecoratedCreature();
        CreatureDecorator prec = this;
        boolean flag = false;
        boolean found = false;
        while(!flag && !found){
            if(next instanceof CreatureDecorator){
                if(next == cd){
                    found = true;
                }else{
                    prec = ((CreatureDecorator)next);
                    next = ((CreatureDecorator)next).getDecoratedCreature();
                }
            }else{
                flag = true;
            }
        }
        if(found){
            prec.setDecoratedCreature(((CreatureDecorator)next).getDecoratedCreature());
            ((CreatureDecorator)next).setDecoratedCreature(null);
        }
    }
        
    @Override
    public boolean isRemoved(){
        return decoratedCreature.isRemoved();
    }
    /*
    @Override
    public boolean tap() {
       return decoratedCreature.tap();
    }

    @Override
    public boolean untap() {
        return decoratedCreature.untap();
    }

    @Override
    public boolean isTapped() {
        return decoratedCreature.isTapped();
    }

    @Override
    public void attack(Creature c) {
        decoratedCreature.attack(c);
    }

    @Override
    public void defend(Creature c) {
        decoratedCreature.defend(c);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean canAttack(){
        return decoratedCreature.canAttack();
    }
    
    @Override
    public boolean isDefender(){
        return decoratedCreature.isDefender();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void inflictDamage(int dmg) {
        decoratedCreature.inflictDamage(dmg);
    }

    @Override
    public void resetDamage() {
        decoratedCreature.resetDamage();
    }

    @Override
    public int getPower() {
        return decoratedCreature.getPower();
    }

    @Override
    public int getToughness() {
        return decoratedCreature.getToughness();
    }

    @Override
    public List<Effect> effects() {
        return decoratedCreature.effects();
    }

    @Override
    public List<Effect> avaliableEffects() {
        return decoratedCreature.avaliableEffects();
    }

    @Override
    public String name() {
        return decoratedCreature.name();
    }

    @Override
    public void insert() {
        decoratedCreature.insert();
    }

    @Override
    public void remove() {
        decoratedCreature.remove();
    }
    */

    @Override
    public boolean isAttackable() {
        return decoratedCreature.isAttackable();
    }
}


