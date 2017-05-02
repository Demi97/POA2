/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Creature;

/**
 *
 * @author diletta
 */
public class SpecialStrategyDecorator extends StrategyDecorator{
    
    public SpecialStrategyDecorator(Strategy decoratedStrategy){
        super(decoratedStrategy);
    }
    
    @Override
    public Strategy getDecoratorHead(){
        return this;
    }
    
    @Override
    public void addDecorator(StrategyDecorator sd){
        sd.setDecoratedStrategy(this.getDecoratedStrategy());
        this.setDecoratedStrategy(decoratedStrategy);
    }
    
    @Override
    public void removeDecorator (StrategyDecorator sd){
        Strategy next = this.getDecoratedStrategy();
        StrategyDecorator prec = this;
        boolean flag = false;
        boolean found = false;
        while(!flag && !found){
            if(next instanceof CreatureDecorator){
                if(next == sd){
                    found = true;
                }else{
                    prec = ((StrategyDecorator)next);
                    next = ((StrategyDecorator)next).getDecoratedStrategy();
                }
            }else{
                flag = true;
            }
        }
        if(found){
            prec.setDecoratedStrategy(((StrategyDecorator)next).getDecoratedStrategy());
            ((CreatureDecorator)next).setDecoratedCreature(null);
        }
    }
}
