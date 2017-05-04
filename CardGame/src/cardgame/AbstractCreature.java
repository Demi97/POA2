/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author atorsell
 */
public abstract class AbstractCreature implements Creature {

    private final CreatureDecorator head;
    protected Player owner;
    protected boolean isTapped = false;
    protected boolean removed = false;
    protected int damageLeft = getToughness();

    protected AbstractCreature(Player owner) {
        this.owner = owner;
        this.head = new SpecialCreatureDecorator(this);
    }

    @Override
    public boolean tap() {
        if (isTapped) {
            System.out.println("creature " + name() + " already tapped");
            return false;
        }

        System.out.println("tapping creature " + name());
        isTapped = true;
        return true;
    }

    @Override
    public boolean untap() {
        if (!isTapped) {
            System.out.println("creature " + name() + " not tapped");
            return false;
        }

        System.out.println("untapping creature " + name());
        isTapped = false;
        return true;
    }

    @Override
    public boolean isTapped() {
        return isTapped;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void attack(Creature c) {
    }

    @Override
    public void defend(Creature c) {
    }

    /* @Override
    public boolean canAttack() {
        return !(isTapped || defender);
    }*/
    
    @Override
    public boolean isDefender() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void inflictDamage(int dmg) {
        damageLeft -= dmg;
        System.out.println("Infliggo " + dmg + " a " + this.name());
        if (damageLeft <= 0) {
            System.out.println(this.name() + " muore!");
            this.removed = true;
            System.out.println("STA MORENDO");
            CardGame.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER, head);
            owner.destroy(this.getDecoratorHead());
        }
    }

    @Override
    public void resetDamage() {
        damageLeft = getToughness();
    }

    @Override
    public void insert() {
        owner.getCreatures().add(head);
        CardGame.instance.getTriggers().trigger(Triggers.ENTER_CREATURE_FILTER, head);
    }

    @Override
    public void remove() {
        owner.getCreatures().remove(head);
        System.out.println("STA MORENDO");
        CardGame.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER, head);
    }

    @Override
    public String toString() {
        return name() + " (Creature)";
    }

    @Override
    public Creature getDecoratorHead() {
        return head;
    }

    @Override
    public void addDecorator(CreatureDecorator cd) {
        getDecoratorHead().addDecorator(cd);
    }

    @Override
    public void removeDecorator(CreatureDecorator cd) {
        getDecoratorHead().removeDecorator(cd);
    }

    @Override
    public List<Effect> effects() {
        return new ArrayList<>();
    }

    @Override
    public List<Effect> avaliableEffects() {
        return new ArrayList<>();
    }
}
