/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.List;

/**
 *
 * @author diletta&simonescaboro
 */
public class CombatStrategy {
    // CALCOLO DEI DANNI

    public void resolution(List<DefaultCombatPhase.Duel> duel) {
        int damageAttacker;
        boolean action = false;
        for (DefaultCombatPhase.Duel d : duel) {
            damageAttacker = 0;
            if (!d.getAttackers().getDecoratorHead().isRemoved()) {
                int attackerPower = d.getAttackers().getDecoratorHead().getPower();
                for (Creature c : d.getDefenders()) {
                    if (!c.getDecoratorHead().isRemoved()) {
                        if (damageAttacker < d.getAttackers().getDecoratorHead().getToughness()) {
                            action = true;
                            damageAttacker += Math.max(0, c.getDecoratorHead().getPower());
                            c.getDecoratorHead().inflictDamage(Math.max(0, attackerPower));
                            attackerPower -= c.getDecoratorHead().getToughness();
                            d.getAttackers().getDecoratorHead().inflictDamage(Math.max(0, c.getDecoratorHead().getPower()));
                        }
                    }
                }
                if (action == false) {
                    CardGame.instance.getCurrentAdversary().inflictDamage(Math.max(0, d.getAttackers().getDecoratorHead().getPower()));
                    System.out.println(CardGame.instance.getCurrentAdversary().name() + "'s life: " + CardGame.instance.getCurrentAdversary().getLife());
                }
                if (damageAttacker < d.getAttackers().getDecoratorHead().getToughness()) {
                    d.getAttackers().getDecoratorHead().tap();
                }
            }
        }
    }
}
