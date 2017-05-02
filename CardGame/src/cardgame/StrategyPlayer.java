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
public interface StrategyPlayer {
    public void inflictDamage(int pts);

    public void heal(int pts);
    
    public void lose(String s);

    public void draw();
    
    public Player getOwner();
    
    public void setOldStrategy(StrategyPlayer p);
    
    public StrategyPlayer getOldStrategy();

    public void inflictCombatDamageCreature(Creature def,int dmg);

    public int damageCreature(int dmg);
}
