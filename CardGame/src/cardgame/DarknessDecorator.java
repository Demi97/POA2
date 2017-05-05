/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

/**
 *
 * @author diletta
 */
public class DarknessDecorator extends CreatureDecorator{

    public DarknessDecorator(Creature decoratedCreature) {
        super(decoratedCreature);
    }

    @Override
    public void inflictDamage(int dmg) {
        dmg = 0;
        super.inflictDamage(dmg); 
    }
    

    @Override
    public boolean isAttackable() {
        return decoratedCreature.isAttackable();
    }

    @Override
    public void acceptVisit(Visitor visitor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
