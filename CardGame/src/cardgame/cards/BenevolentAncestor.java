/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.PreventOneDamage;
import cardgame.StrategyDecorator;
import cardgame.AbstractCreature;
import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.MagicPrinter;
import cardgame.Player;
import cardgame.TriggerAction;
import cardgame.Triggers;
import cardgame.Visited;
import cardgame.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diletta
 */
public class BenevolentAncestor implements Card {
    
    @Override
    public String name() {
        return "Benevolent Ancestor";
    }

    @Override
    public String type() {
        return "Creature";
    }

    @Override
    public String ruleText() {
        return "Defender (This creature can't attack). Prevent the next 1 damage that would be dealt to target creature or player this turn";
    }

    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() + "]";
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    private class BenevolentAncestorEffect extends AbstractCreatureCardEffect {

        public BenevolentAncestorEffect(Player p, Card c) {
            super(p, c);
        }

        @Override
        protected Creature createCreature() {
            return new BenevolentAncestorCreature(owner);
        }
    }

    @Override
    public Effect getEffect(Player p) {
        return new BenevolentAncestorEffect(p, this);
    }

    private class BenevolentAncestorCreature extends AbstractCreature {

        int choice;
        Player targetPlayer;
        ArrayList<Effect> all_effects;
        ArrayList<Effect> tap_effects;
        Scanner scan = new Scanner(System.in);

        BenevolentAncestorCreature(Player owner) {
            super(owner);
            tap_effects = new ArrayList<>();
        }

        @Override
        public void insert() {
            all_effects = new ArrayList<>();
            all_effects.add(new Effect() {
                Visited target;

                @Override
                public boolean play() {
                    System.out.println("Your target is a player (1) or a creature (2)?");
                    do {
                        try {
                            choice = scan.nextInt();
                        } catch (Exception e) {
                            choice = -1;
                        }
                    } while (choice != 1 && choice != 2);
                    if (choice == 1) {
                        System.out.println("Player taarget is your adversary (1) or yourself (2)?");
                        do {
                            try {
                                choice = scan.nextInt();
                            } catch (Exception e) {
                                choice = -1;
                            }

                        } while (choice != 1 && choice != 2);
                        if (choice == 1) {
                            target = CardGame.instance.getAdversary(owner);
                        } else {
                            target = owner;
                        }
                    } else {
                        int index;
                        System.out.println("Your target is a creature of yours (1) or of your adversary (2)?");
                        do{
                            try{
                                index = scan.nextInt();
                            }catch(Exception e ) { index = -1; }
                        }while(index != 2 && index != 1);
                        
                        if (index == 1) {
                            MagicPrinter.instance.printCreatures(owner.getCreatures());
                            do {
                                try {
                                    index = scan.nextInt();
                                } catch (Exception e) {
                                    index = -2;
                                }
                            } while (index < 0 || index - 1 >= owner.getCreatures().size());
                            target = owner.getCreatures().get(index - 1);
                        } else {
                            MagicPrinter.instance.printCreatures(CardGame.instance.getAdversary(owner).getCreatures());
                            do {
                                try {
                                    index = scan.nextInt();
                                } catch (Exception e) {
                                    index = -2;
                                }
                            } while (index < 0 || index - 1 >= owner.getCreatures().size());
                            target = CardGame.instance.getAdversary(owner).getCreatures().get(index - 1);
                        }
                    }
                    CardGame.instance.getStack().add(this);
                    return tap();

                }

                @Override
                public void resolve() {

                    target.acceptVisit(new Visitor() {
                        @Override
                        public void visit(Creature creature) {
                            BenevolentAncestorDecorator dec = new BenevolentAncestorDecorator(creature);
                            creature.addDecorator(dec);
                            CardGame.instance.getTriggers().register(Triggers.END_FILTER, new TriggerAction() {
                                Creature creature;
                                BenevolentAncestorDecorator dec;

                                @Override
                                public void execute(Object args) {
                                    creature.removeDecorator(dec);
                                    CardGame.instance.getTriggers().deregister(this);
                                }

                                public TriggerAction start(Creature c, BenevolentAncestorDecorator bad) {
                                    this.creature = c;
                                    this.dec = bad;
                                    return this;
                                }
                            }.start(creature, dec));
                        }

                        @Override
                        public void visit(Player player) {
                            StrategyDecorator decS = new PreventOneDamage(player.getStrategy());
                            player.getStrategy().addDecorator(decS);
                            CardGame.instance.getTriggers().register(Triggers.END_FILTER, new TriggerAction() {
                                Player player;
                                StrategyDecorator sd;

                                @Override
                                public void execute(Object args) {
                                    player.getStrategy().removeDecorator(sd);
                                    CardGame.instance.getTriggers().deregister(this);
                                }

                                public TriggerAction start(Player p, StrategyDecorator decS) {
                                    this.player = p;
                                    this.sd = decS;
                                    return this;
                                }
                            }.start(player, decS));
                        }

                        @Override
                        public void visit(Enchantment enchantment) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                    });
                }
            });
            super.insert();
        }

        @Override

        public boolean isDefender() {
            return true;
        }

        @Override
        public int getPower() {
            return 0;
        }

        @Override
        public int getToughness() {
            return 4;
        }

        @Override
        public List<Effect> effects() {
            return all_effects;
        }

        @Override
        public List<Effect> avaliableEffects() {
            return (isTapped) ? tap_effects : all_effects;
        }

        @Override
        public String name() {
            return "Benevolent Ancestor";
        }
    }

}
