/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import cardgame.StrategyDecorator;
import cardgame.Strategy;
import cardgame.Creature;
import cardgame.Player;

/**
 *
 * @author diletta
 */
public class PreventOneDamage extends StrategyDecorator{
    
    public PreventOneDamage(Strategy sd){
        super(sd);
        this.decoratedStrategy=sd;
    }
    
    @Override
    public void damageOperation(int pts) {
        pts = pts-1;
        decoratedStrategy.damageOperation(pts);
    }

    @Override
     public Strategy getDecoratedStrategy(){
        return decoratedStrategy;
    }
    
    @Override
    public void setDecoratedStrategy(Strategy s){
        this.decoratedStrategy=s;
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
    
}
