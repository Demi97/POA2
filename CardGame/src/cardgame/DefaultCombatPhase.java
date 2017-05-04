/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author atorsell
 */
public class DefaultCombatPhase implements Phase {
    // CALCOLO DEI DANNI
    public void resolution(List<Duel> duel) {
        CardGame.instance.getTriggers().trigger(Triggers.DAMAGE_FILTER);
        //checkRemoved(duel);
        int damageAttacker;
        boolean action = false;
        System.out.println("_________COMBAT PHASE________");
        for(Duel d : duel) {
            damageAttacker = 0;
            if(d.getAttackers().getDecoratorHead().isRemoved()) {
                System.out.println(d.getAttackers().name() + " can't attack, is dead!");
            }
            else {
                System.out.println(d.getAttackers().name() + " attacks ("+d.getAttackers().getDecoratorHead().getPower()+"/"+d.getAttackers().getDecoratorHead().getToughness()+")");
                for(Creature c: d.getDefenders()) {
                    if(c.getDecoratorHead().isRemoved())
                        System.out.println(c.name() + " can't defend, is dead!");
                    else{
                        if(damageAttacker < d.attackers.getToughness()) {
                            action = true;
                            System.out.println(c.name() + " (defender" +"(" + c.getDecoratorHead().getPower() + "/" + c.getDecoratorHead().getToughness() + "))");
                            damageAttacker += Math.max(0, c.getDecoratorHead().getPower());
                            c.getDecoratorHead().inflictDamage(Math.max(0,d.attackers.getDecoratorHead().getPower()));
                            d.attackers.getDecoratorHead().inflictDamage(Math.max(0,c.getDecoratorHead().getPower()));
                        }   
                    }
                }
                
                if(action == false){
                    System.out.println("No defenders");
                    CardGame.instance.getCurrentAdversary().inflictDamage(Math.max(0,d.getAttackers().getDecoratorHead().getPower()));
                    System.out.println("Direct attack!");
                    System.out.println("LIFE: " + CardGame.instance.getCurrentAdversary().getLife());
                }
                if(damageAttacker < d.attackers.getToughness())
                    d.attackers.tap();
            }
            System.out.println("- - - - - - - - - - - - - - - -");
        }
        CardGame.instance.getTriggers().trigger(Triggers.END_DAMAGE_FILTER);
        System.out.println("_________________________________");
    }
    
    public ArrayList<Creature> canAttack(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for(Creature c: player.getCreatures()){
            if(!c.isTapped() && !c.isDefender() && !c.isRemoved())
                tmp.add(c);
        }
        return tmp;
    }
    public ArrayList<Creature> canDefend(Player player) {
        ArrayList<Creature> tmp = new ArrayList<>();
        for(Creature c: player.getCreatures()){
            if(!c.isTapped() && !c.isRemoved())
                tmp.add(c);
        }
        return tmp;
    }
    
    private boolean playAvailableEffect(Player activePlayer, boolean isMain) {
        //collect and display available effects...
        ArrayList<Effect> availableEffects = new ArrayList<>();
        Scanner reader = CardGame.instance.getScanner();

        //...cards first
        System.out.println(activePlayer.name() + " select card/effect to play, 0 to pass");
        int i=0;
        for( Card c:activePlayer.getHand() ) {
            if (c.isInstant() ) {
                availableEffects.add( c.getEffect(activePlayer) );
                System.out.println(Integer.toString(i+1)+") " + c );
                ++i;
            }
        }
        
        //...creature effects last
        for ( Creature c:activePlayer.getCreatures()) {
            for (Effect e:c.avaliableEffects()) {
                availableEffects.add(e);
                System.out.println(Integer.toString(i+1)+") " + c.name() + 
                    " ["+ e + "]" );
                ++i;
            }
        }
        
        //get user choice and play it
        int idx= reader.nextInt()-1;
        if (idx<0 || idx>=availableEffects.size()) return false;

        availableEffects.get(idx).play();
        return true;
    }
    
    private void stackResolution() {
        int numberPasses=0;
        Player currentPlayer = CardGame.instance.getCurrentPlayer();
        int responsePlayerIdx = ((CardGame.instance.getPlayer(0) == currentPlayer)?1:0);
        if (!playAvailableEffect(currentPlayer, true))
            ++numberPasses;
        
        while (numberPasses<2) {
            if (playAvailableEffect(CardGame.instance.getPlayer(responsePlayerIdx),false))
                numberPasses=0;
            else ++numberPasses;
            
            responsePlayerIdx = (responsePlayerIdx+1)%2;
        }
    }
    
    @Override
    public void execute() {
        /**
         * Attuale giocatore e avversario
         */
        Player currentPlayer = CardGame.instance.getCurrentPlayer();
        Player currentAdversary = CardGame.instance.getCurrentAdversary();
        // variabile per prendere in input
        Scanner reader = CardGame.instance.getScanner();
        
        // Setto chi può attaccare e chi difendere
        ArrayList<Creature> effectiveAttacker = canAttack(currentPlayer);
        ArrayList<Creature> effectiveDefender = canDefend(currentAdversary);

        System.out.println(currentPlayer.name() + ": combat phase");
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
        // TODO combat
        
        
        
        //int i = 0;
        List<Duel> duel = new ArrayList<>();
        //int i = 0;
        
        //DICHIARAZIONE ATTACCANTI
        if(effectiveAttacker.isEmpty())
            System.out.println("... no creatures on field");
        if(!effectiveAttacker.isEmpty()) {
            int attack_index = 1;
            System.out.println("These creatures can attack: ");
            while(attack_index != 0 && effectiveAttacker.size() > 0) {
                do{
                    //printPlayerField(effectiveAttacker);
                    MagicPrinter.instance.printCreatures(effectiveAttacker);
                    System.out.println("Attack with: (0 to pass)");          
                    attack_index = reader.nextInt();
                }while(attack_index < 0 || attack_index-1 >= effectiveAttacker.size());
                if(attack_index != 0) {
                    Duel d = new Duel();
                    d.attackers = effectiveAttacker.get(attack_index-1);
                    duel.add(d);
                    effectiveAttacker.remove(attack_index-1);
                }
            }
        System.out.println("###### FINE DICHIARAZIONE ATTACCANTI ######");
        System.out.println();
        stackResolution();
        System.out.println("Stack's resolution");
        CardGame.instance.getStack().resolve();
        } 
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // DICHIARAZIONE DIFENSORI
        if(effectiveDefender.isEmpty())
            System.out.println("... no creatures can defend");
        if(duel.isEmpty())
            System.out.println("... no creatures da cui difendersi");
        if(!effectiveDefender.isEmpty() && !duel.isEmpty()) {     
            System.out.println(currentAdversary.name() + " choose defenders: ");
            //control(duel);
            for(Duel d : duel) {
                int defend_index = 1;
                if(!d.getAttackers().getDecoratorHead().isRemoved()) {
                    System.out.println("Who defends for " + d.getAttackers().name() + "?");    
                    while(defend_index != 0 && effectiveDefender.size() > 0) {
                        do{
                            //printPlayerField(effectiveDefender);
                            MagicPrinter.instance.printCreatures(effectiveDefender);
                            System.out.println("Defend with: (0 to pass)");
                            defend_index = reader.nextInt();
                        }while(defend_index < 0 || defend_index-1 >= effectiveDefender.size());
                        if(defend_index != 0) {
                            d.getDefenders().add(effectiveDefender.get(defend_index-1));
                            effectiveDefender.remove(defend_index-1);
                        }
                    }
                }
            }
        System.out.println();
        System.out.println("###### FINE DICHIARAZIONE DIFENSORI ######");
        stackResolution();
        System.out.println("Stack's resolution");
        CardGame.instance.getStack().resolve();
        }
        //printResolution(duel);
        resolution(duel);
    }
    
    public class Duel {
        Creature attackers;
        private ArrayList<Creature> defenders = new ArrayList<>();
        
        public void setAttackers(Creature attackers) {
            this.attackers = attackers;
        }
       
        public Creature getAttackers() {
            return attackers;
        }

        public ArrayList<Creature> getDefenders() {
            return defenders;
        }
        
    }
}