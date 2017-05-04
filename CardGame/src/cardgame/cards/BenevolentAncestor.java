/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.PreventOneDamage;
import cardgame.StrategyDecorator;
import cardgame.Strategy;
import cardgame.AbstractCreature;
import cardgame.AbstractCreatureCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.CreatureDecorator;
import cardgame.MagicPrinter;
import cardgame.Player;
import cardgame.TriggerAction;
import cardgame.Triggers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diletta
 */
public class BenevolentAncestor implements Card {

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
                @Override
                public boolean play() {
                    System.out.println("Your target is:\n1.a creature of yours\n2.a creature of your adversary\n3.your adversary\n4.yourself?");
                    do {
                        try {
                            choice = scan.nextInt();
                        } catch (Exception e) {
                            choice = -1;
                        }
                    } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);
                    CardGame.instance.getStack().add(this);
                    return tap();

                }

                @Override
                public void resolve() {
                    BenevolentAncestorDecorator dec;
                    int index;
                    if (choice == 2 || choice == 3) {
                        targetPlayer = CardGame.instance.getAdversary(owner);
                    } else if (choice == 1 || choice == 4) {
                        targetPlayer = owner;
                    }
                    if (choice == 1 || choice == 2) {
                        Creature targetCreature;
                        if (targetPlayer.getCreatures().isEmpty()) {
                            System.out.println("No creatures on field");
                        } else {
                            System.out.println("Choose the creature you want as your target");
                            MagicPrinter.instance.printCreatures(targetPlayer.getCreatures());
                            do {
                                try {
                                    index = scan.nextInt();
                                } catch (Exception e) {
                                    index = -2;
                                }
                            } while (index < 0 || index - 1 >= targetPlayer.getCreatures().size());
                            targetCreature = targetPlayer.getCreatures().get(index - 1);
                            dec = new BenevolentAncestorDecorator(targetCreature);
                            targetCreature.addDecorator(dec);
                            CardGame.instance.getTriggers().register(Triggers.END_FILTER, new TriggerAction() {
                                Creature tC;
                                CreatureDecorator dec;

                                @Override
                                public void execute(Object args) {
                                    tC.removeDecorator(dec);
                                }

                                public TriggerAction start(Creature c, CreatureDecorator d) {
                                    this.tC = c;
                                    this.dec = d;
                                    return this;

                                }

                            }.start(targetCreature, dec));
                        }
                    } else {
                        StrategyDecorator decS = new PreventOneDamage(targetPlayer.getStrategy());
                        targetPlayer.getStrategy().addDecorator(decS);
                        CardGame.instance.getTriggers().register(Triggers.END_FILTER, new TriggerAction() {
                            Strategy s;
                            StrategyDecorator dec;

                            @Override
                            public void execute(Object args) {
                                s.removeDecorator(dec);
                            }

                            public TriggerAction start(Strategy s, StrategyDecorator sD) {
                                this.s = s;
                                this.dec = sD;
                                return this;
                            }

                        }.start(targetPlayer.getStrategy(), decS));
                    }
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

}
