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
        CardGame.instance.getTriggers().trigger(Triggers.DAMAGE_FILTER);
        int damageAttacker;
        boolean action = false;
        //System.out.println("__________COMBAT PHASE_________");
        for (DefaultCombatPhase.Duel d : duel) {
            damageAttacker = 0;
            if (d.getAttackers().getDecoratorHead().isRemoved()) {
                //System.out.println(d.getAttackers().name() + " can't attack, is dead!");
            } else {
                //System.out.println(d.getAttackers().name() + " attacks (" + d.getAttackers().getDecoratorHead().getPower() + "/" + d.getAttackers().getDecoratorHead().getToughness() + ")");
                for (Creature c : d.getDefenders()) {
                    if (c.getDecoratorHead().isRemoved()) {
                        //System.out.println(c.name() + " can't defend, is dead!");
                    } else if (damageAttacker < d.getAttackers().getDecoratorHead().getToughness()) {
                        action = true;
                        //System.out.println(c.name() + " (defender" + "(" + c.getDecoratorHead().getPower() + "/" + c.getDecoratorHead().getToughness() + "))");
                        damageAttacker += Math.max(0, c.getDecoratorHead().getPower());
                        c.getDecoratorHead().inflictDamage(Math.max(0, d.getAttackers().getDecoratorHead().getPower()));
                        d.getAttackers().getDecoratorHead().inflictDamage(Math.max(0, c.getDecoratorHead().getPower()));
                    }
                }

                if (action == false) {
                    //System.out.println("No defenders");
                    CardGame.instance.getCurrentAdversary().inflictDamage(Math.max(0, d.getAttackers().getDecoratorHead().getPower()));
                    //System.out.println("Direct attack!");
                    System.out.println(CardGame.instance.getCurrentAdversary().name() + "'s life: " + CardGame.instance.getCurrentAdversary().getLife());
                }
                if (damageAttacker < d.getAttackers().getDecoratorHead().getToughness()) {
                    d.getAttackers().getDecoratorHead().tap();
                }
            }
            //System.out.println("- - - - - - - - - - - - - - - -");
        }
        CardGame.instance.getTriggers().trigger(Triggers.END_DAMAGE_FILTER);
        //System.out.println("_________________________________");
    }
}
