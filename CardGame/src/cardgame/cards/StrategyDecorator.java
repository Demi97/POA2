/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Player;

/**
 *
 * @author diletta
 */
public class StrategyDecorator implements Strategy{
    protected Strategy decoratedStrategy;
    
    public StrategyDecorator(Strategy s){
        this.decoratedStrategy=s;
    }

    public Strategy getDecoratedStrategy(){
        return decoratedStrategy;
    }
    
    public void setDecoratedStrategy(Strategy s){
        decoratedStrategy=s;
    }

    @Override
    public void addDecorator(StrategyDecorator sd) {
        decoratedStrategy.addDecorator(sd);
    }

    @Override
    public void removeDecorator(StrategyDecorator sd) {
        decoratedStrategy.removeDecorator(sd);
    }

    @Override
    public Strategy getDecoratorHead() {
        return decoratedStrategy.getDecoratorHead();
    }

    @Override
    public void damageOperation(int pts) {
        decoratedStrategy.damageOperation(pts);
    }
    
}
