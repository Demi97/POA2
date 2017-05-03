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
public abstract class DefaultDamage implements Strategy {
    private final StrategyDecorator head;
    
    public DefaultDamage(){
        head = new SpecialStrategyDecorator(this);
    }

    @Override
    public void addDecorator(StrategyDecorator sd) {
        head.addDecorator(sd);
    }

    @Override
    public void removeDecorator(StrategyDecorator sd) {
        head.removeDecorator(sd);
    }

    @Override
    public Strategy getDecoratorHead() {
        return head;
    }
    
}
