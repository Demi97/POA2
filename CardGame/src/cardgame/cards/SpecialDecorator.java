/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Creature;
import cardgame.cards.CreatureDecorator;
/**
 *
 * @author diletta
 */
public class SpecialDecorator extends CreatureDecorator{
    
    public SpecialDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
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
}


