/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.cards;

import cardgame.AbstractEnchantment;
import cardgame.AbstractEnchantmentCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.CreatureDecorator;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Enchantment;
import cardgame.MagicPrinter;
import cardgame.Player;
import cardgame.Targets;
import cardgame.TriggerAction;
import cardgame.Triggers;
import cardgame.Visitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author diletta
 */
public class AncestralMask implements Card {

    @Override
    public String name() {
        return "Ancestral Mask";
    }

    @Override
    public String type() {
        return "Enchantment";
    }

    @Override
    public String ruleText() {
        return "Enchant creature. Enchanted creature gets +2/+2 for each other enchantment on the battlefield";
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public String toString() {
        return name() + " (" + type() + ") [" + ruleText() + "]";
    }

    @Override
    public Effect getEffect(Player owner) {
        return new AncestralMaskEffect(owner, this);
    }

    Creature target;

    private class AncestralMaskEffect extends AbstractEnchantmentCardEffect implements Targets {

        public AncestralMaskEffect(Player p, Card c) {
            super(p, c);
        }

        @Override
        public boolean play() {
            checkTarget();
            return super.play();
        }

        @Override
        public void checkTarget() {
            int i = 0, index;
            Scanner scan = new Scanner(System.in);
            List<Creature> creatures = owner.getCreatures();
            if (creatures.isEmpty()) {
                System.out.println("No creatures on field");
                target = null;
            } else {
                MagicPrinter.instance.printCreatures(owner.getCreatures());
                do {
                    try {
                        index = scan.nextInt() - 1;
                    } catch (Exception e) {
                        index = -2;
                    }
                } while (index < 0 || index - 1 >= creatures.size());
                target = creatures.get(index);
            }
        }

        @Override
        protected Enchantment createEnchantment() {
            return new AncestralMaskEnchantment(owner);
        }
    }

    private class AncestralMaskEnchantment extends AbstractEnchantment {

        AncestralMaskDecorator dec;

        public AncestralMaskEnchantment(Player owner) {
            super(owner);
        }

        @Override
        public String name() {
            return "Ancestral Mask";
        }

        @Override
        public void insert() {
            if (target != null) {
                if (target.isRemoved()) {
                    System.out.println("Target already removed");
                } else {
                    dec = new AncestralMaskDecorator(target);
                    target.addDecorator(dec);
                    dec.activation(owner);
                }
            }
            super.insert();
        }

        @Override
        public void remove() {
            target.removeDecorator(dec);
            super.remove();
        }

        @Override
        public void acceptVisit(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public class AncestralMaskDecorator extends CreatureDecorator {

        int numEnchantment;

        public AncestralMaskDecorator(Creature decoratedCreature) {
            super(decoratedCreature);
        }

        @Override
        public int getPower() {
            return decoratedCreature.getPower() + ((numEnchantment - 1) * 2);
        }

        @Override
        public int getToughness() {
            return decoratedCreature.getToughness() + ((numEnchantment - 1) * 2);
        }

        public void activation(Player me) {
            CardGame.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER, AddOnEntranceAction);
            CardGame.instance.getTriggers().register(Triggers.EXIT_ENCHANTMENT_FILTER, SubtractOnExitAction);
            numEnchantment = me.getEnchantments().size() + CardGame.instance.getAdversary(me).getEnchantments().size();
        }

        private final TriggerAction AddOnEntranceAction = new TriggerAction() {
            @Override
            public void execute(Object args) {
                numEnchantment++;
            }

        };

        private final TriggerAction SubtractOnExitAction = new TriggerAction() {
            @Override
            public void execute(Object args) {
                numEnchantment--;
            }

        };

        @Override
        public boolean isAttackable() {
            return decoratedCreature.isAttackable();
        }

        @Override
        public void acceptVisit(Visitor visitor) {
            visitor.visit(this);
        }
    }
}
