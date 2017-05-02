/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

/**
 *
 * @author nikitabaldan
 */
public abstract class AbstractStrategyPlayer implements StrategyPlayer {

    private StrategyPlayer underStrategy;

    public AbstractStrategyPlayer() {
    }

    @Override
    public void inflictDamage(int pts) {
        underStrategy.inflictDamage(pts);
    }

    @Override
    public void heal(int pts) {
        underStrategy.heal(pts);
    }

    @Override
    public void lose(String s) {
        underStrategy.lose(s);
    }

    @Override
    public void draw() {
        underStrategy.draw();
    }

    @Override
    public void setOldStrategy(StrategyPlayer p) {
        underStrategy = p;
    }

    @Override
    public StrategyPlayer getOldStrategy() {
        return underStrategy;
    }

    @Override
    public Player getOwner() {
        return underStrategy.getOwner();
    }

    @Override
    public int damageCreature(int dmg) {
        return underStrategy.damageCreature(dmg);
    }

    @Override
    public void inflictCombatDamageCreature(Creature def, int dmg) {
        underStrategy.inflictCombatDamageCreature(def,dmg);
    }
}
