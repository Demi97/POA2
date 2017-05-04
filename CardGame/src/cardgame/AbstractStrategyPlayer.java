/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

/**
 *
 * @author nikitabaldan
 */
public abstract class AbstractStrategyPlayer implements Strategy {

    private Strategy underStrategy;

    @Override
    public void damageOperation(int pts) {
        underStrategy.damageOperation(pts);
    }

    @Override
    public void addDecorator(StrategyDecorator sd) {
        underStrategy.addDecorator(sd);
    }

    @Override
    public void removeDecorator(StrategyDecorator sd) {
        underStrategy.removeDecorator(sd);
    }

    @Override
    public Strategy getDecoratorHead() {
        return underStrategy;
    } 
    
}
