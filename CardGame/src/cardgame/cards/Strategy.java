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
public interface Strategy {
    void damageOperation(Player p, int pts);
}
