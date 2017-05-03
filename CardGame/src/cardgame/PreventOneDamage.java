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
    protected Strategy decoratedStrategy;
    
    public PreventOneDamage(Strategy sd){
        super(sd);
        this.decoratedStrategy=sd;
    }
    
    @Override
    public void damageOperation(int pts) {
        pts = pts-1;
        decoratedStrategy.damageOperation(pts);
    }

     public Strategy getDecoratedCreature(){
        return decoratedStrategy;
    }
    
    public void setDecoratedCreature(Strategy s){
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
    
}
