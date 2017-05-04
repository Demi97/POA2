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
import cardgame.CreatureDecorator;
import cardgame.Triggers;

/**
 *
 * @author diletta
 */
/*
public class AncestralMaskDecorator extends CreatureDecorator {

    int numEnchantment;

    @Override
    public int getPower() {
        return decoratedCreature.getPower() + ((numEnchantment - 1) * 2);
    }

    @Override
    public int getToughness() {
        return decoratedCreature.getToughness() + ((numEnchantment - 1) * 2);
    }

    public void activation(Player me) {
        CardGame.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER, AddOnEntranceAction);
        CardGame.instance.getTriggers().register(Triggers.EXIT_ENCHANTMENT_FILTER, SubtractOnExitAction);
        numEnchantment = me.getEnchantments().size() + CardGame.instance.getAdversary(me).getEnchantments().size();
    }

    private final TriggerAction AddOnEntranceAction = new TriggerAction() {
        @Override
        public void execute(Object args) {
            numEnchantment++;
        }

    };

    private final TriggerAction SubtractOnExitAction = new TriggerAction() {
        @Override
        public void execute(Object args) {
            numEnchantment--;
        }

    };

    public AncestralMaskDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
    }

    @Override
    public boolean isAttackable() {
        return decoratedCreature.isAttackable();
    }
}
*/