/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.Creature;
import cardgame.Effect;
import java.util.List;

/**
 *
 * @author diletta
 */
public abstract class CreatureDecorator implements Creature {

    protected Creature decoratedCreature;

    public CreatureDecorator(Creature decoratedCreature) {
        this.decoratedCreature = decoratedCreature;
    }

    @Override
    public boolean tap() {
        return decoratedCreature.tap();
    }

    @Override
    public boolean untap() {
        return decoratedCreature.untap();
    }

    @Override
    public boolean isTapped() {
        return decoratedCreature.isTapped();
    }

    @Override
    public void attack(Creature c) {
        decoratedCreature.attack(c);
    }

    @Override
    public void defend(Creature c) {
        decoratedCreature.defend(c);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean isDefender() {
        return decoratedCreature.isDefender();
    }

    @Override
    public boolean isRemoved() {
        return decoratedCreature.isRemoved();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void inflictDamage(int dmg) {
        decoratedCreature.inflictDamage(dmg);
    }

    @Override
    public void resetDamage() {
        decoratedCreature.resetDamage();
    }

    @Override
    public int getPower() {
        return decoratedCreature.getPower();
    }

    @Override
    public int getToughness() {
        return decoratedCreature.getToughness();
    }

    @Override
    public List<Effect> effects() {
        return decoratedCreature.effects();
    }

    @Override
    public List<Effect> avaliableEffects() {
        return decoratedCreature.avaliableEffects();
    }

    @Override
    public String name() {
        return decoratedCreature.name();
    }

    @Override
    public void insert() {
        decoratedCreature.insert();
    }

    @Override
    public void remove() {
        decoratedCreature.remove();
    }

    public Creature getDecoratedCreature() {
        return decoratedCreature;
    }

    public void setDecoratedCreature(Creature c) {
        decoratedCreature = c;
    }

    @Override
    public void addDecorator(CreatureDecorator cd) {
        decoratedCreature.addDecorator(cd);
    }

    @Override
    public void removeDecorator(CreatureDecorator cd) {
        decoratedCreature.removeDecorator(cd);
    }

    @Override
    public Creature getDecoratorHead() {
        return decoratedCreature.getDecoratorHead();
    }

}
