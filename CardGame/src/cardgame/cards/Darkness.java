package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.AbstractStrategyPlayer;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Player;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.DarknessDecorator;
import cardgame.Phases;
import cardgame.PreventAllDamages;
import cardgame.Strategy;
import cardgame.StrategyDecorator;
import cardgame.TriggerAction;
import java.util.List;

/**
 *
 * @author simonescaboro
 */
public class Darkness implements Card {

    @Override
    public Effect getEffect(Player owner) {
        return new DarknessEffect(owner, this);
    }

    private class DarknessEffect extends AbstractCardEffect {

        public DarknessEffect(Player p, Card c) {
            super(p, c);
        }

        @Override
        public void resolve() {
            DarknessDecorator dec;
            List<Creature> temp1 = owner.getCreatures();
            List<Creature> temp2 = CardGame.instance.getAdversary(owner).getCreatures();
            StrategyDecorator decP = new PreventAllDamages(owner.getStrategy());
            StrategyDecorator decA = new PreventAllDamages(CardGame.instance.getAdversary(owner).getStrategy());
            owner.getStrategy().addDecorator(decP);
            CardGame.instance.getAdversary(owner).getStrategy().addDecorator(decA);

            for (Creature c : temp1) {
                dec = new DarknessDecorator(c);
                c.addDecorator(dec);
            }
            for (Creature c : temp2) {
                dec = new DarknessDecorator(c);
                c.addDecorator(dec);
            }


        /*  Strategy stp = new DarknessStrategy();
            CardGame.instance.getCurrentPlayer().inflictDamage(0);
            CardGame.instance.getAdversary(owner).inflictDamage(0);

            TriggerAction dt = new DarknessTrigger((StrategyDecorator) stp);
            CardGame.instance.getTriggers().register(Phases.COMBAT.getIdx(), dt);*/
    }

    /* public class DarknessStrategy extends AbstractStrategyPlayer {

            public DarknessStrategy() {
                super();
            }

        }*/

 /*  private class DarknessTrigger implements TriggerAction {

            StrategyDecorator stp;

            private DarknessTrigger(StrategyDecorator stp) {
                this.stp = stp;
            }

            @Override
            public void execute(Object args) {
                CardGame.instance.getCurrentPlayer().removeStrategy(stp);
                CardGame.instance.getCurrentAdversary().removeStrategy(stp);
            }
        }*/
}

@Override
        public String name() {
        return "Darkness";
    }

    @Override
        public String type() {
        return "Instant";
    }

    @Override
        public String ruleText() {
        return "Prevent all combat damage that would be deal this turn";
    }

    @Override
        public String toString() {
        return name() + " (" + type() + ") [" + ruleText() + "]";
    }

    @Override
        public boolean isInstant() {
        return true;
    }
}
