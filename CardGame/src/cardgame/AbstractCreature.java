/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import cardgame.cards.CreatureDecorator;
import cardgame.cards.SpecialCreatureDecorator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author atorsell
 */
public abstract class AbstractCreature implements Creature {
    private final CreatureDecorator head;
    protected Player owner;
    protected boolean isTapped=false;
    protected boolean defender; ////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected int damageLeft = getToughness();
        
        protected AbstractCreature(Player owner, boolean canDefend) { 
            this.owner=owner; 
            this.head = new SpecialCreatureDecorator(this);
            this.defender = canDefend;  /////////////////////////////////////////////////////////////////////////////////////////////////
            
        }
        
    @Override
        public boolean tap() { 
            if (isTapped) {
                System.out.println("creature " + name() + " already tapped");
                return false;
            }
            
            System.out.println("tapping creature " + name());
            isTapped=true; 
            return true; 
        }
        
    @Override
        public boolean untap() { 
            if (!isTapped) {
                System.out.println("creature " + name() + " not tapped");
                return false;
            }
            
            System.out.println("untapping creature " + name());
            isTapped=false; 
            return true; 
        }

    @Override
        public boolean isTapped() { return isTapped; }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
        public void attack(Creature c) {}
        
    @Override
        public void defend(Creature c) {}
        
    @Override
    public boolean canAttack() {
        return !(isTapped || defender);
    }
    @Override
    public boolean isDefender() {
        return defender;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
        public void inflictDamage(int dmg) { 
            damageLeft -= dmg;
            System.out.println("Infliggo " + dmg + " a " + this.name());
            if (damageLeft<=0) {
                System.out.println(this.name() + " muore!");
                owner.destroy(this);  
            }
        }
        
    @Override
        public void resetDamage() { damageLeft = getToughness(); }
    
    @Override
        public void insert() {
            CardGame.instance.getTriggers().trigger(Triggers.ENTER_CREATURE_FILTER,this);
        }
    
    @Override
        public void remove() {
            owner.getCreatures().remove(this);
            CardGame.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER,this);
        }
    
    @Override
        public String toString() {
            return name() + " (Creature)";
        }
    
    @Override
    public Creature getDecoratorHead(){
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
    public List<Effect> avaliableEffects(){
        return new ArrayList<>();
    }
}
