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
public class DefaultDamage implements Strategy {
    protected Strategy decoratedStrategy;
    
    public DefaultDamage(Strategy sd){
        this.decoratedStrategy=sd;
    }

    @Override
    public void damageOperation(Player p, int pts) {
        p.inflictDamage(pts);
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
