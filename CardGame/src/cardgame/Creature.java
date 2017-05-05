/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.List;

/**
 *
 * @author atorsell
 */
public interface Creature extends Permanent {
    
    boolean tap();
    boolean untap();
    boolean isTapped();
    void attack(Creature c);
    void defend(Creature c);
    void inflictDamage(int dmg);
    void resetDamage();
    int getPower();
    int getToughness();
    boolean isDefender();
    boolean isAttackable();
    Creature getDecoratorHead();
    void addDecorator(CreatureDecorator cd);
    void removeDecorator(CreatureDecorator cd);
    
    // returns all the effects associated to this creature
    List<Effect> effects();
    
    // returns only the effects that can be played currently
    // depending on state, e.g., tapped/untapped
    List<Effect> avaliableEffects();
}
