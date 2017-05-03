/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Player;
import cardgame.TriggerAction;
import cardgame.Triggers;

/**
 *
 * @author diletta
 */
public class BenevolentAncestorDecorator extends CreatureDecorator {

    public BenevolentAncestorDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
    }

    @Override
    public void inflictDamage(int dmg) {
        dmg = dmg - 1;
        super.inflictDamage(dmg);
    }

}
