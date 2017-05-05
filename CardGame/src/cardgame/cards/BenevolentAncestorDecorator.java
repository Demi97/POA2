/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Creature;
import cardgame.CreatureDecorator;
import cardgame.Visitor;

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

    @Override
    public boolean isAttackable() {
        return decoratedCreature.isAttackable();
    }

    @Override
    public void acceptVisit(Visitor visitor) {
        visitor.visit(this);
    }

}
