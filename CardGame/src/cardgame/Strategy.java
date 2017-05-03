/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import cardgame.Player;

/**
 *
 * @author diletta
 */
public interface Strategy {
    void damageOperation(int pts);
    void addDecorator(StrategyDecorator sd);
    void removeDecorator(StrategyDecorator sd);
    Strategy getDecoratorHead();
}
