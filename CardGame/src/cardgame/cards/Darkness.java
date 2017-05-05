package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.AbstractStrategyPlayer;
import cardgame.Card;
import cardgame.Effect;
import cardgame.Player;
import cardgame.CardGame;
import cardgame.CombatStrategy;
import cardgame.Creature;
import cardgame.DarknessDecorator;
import cardgame.DefaultCombatPhase;
import cardgame.Phase;
import cardgame.Phases;
import cardgame.PreventAllDamages;
import cardgame.Strategy;
import cardgame.StrategyDecorator;
import cardgame.TriggerAction;
import cardgame.Triggers;
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

            Phase temp = CardGame.instance.getCurrentPlayer().getPhase(Phases.COMBAT);
            if (temp instanceof DefaultCombatPhase) {
                DefaultCombatPhase phase = (DefaultCombatPhase) temp;
                CombatStrategy old = phase.getCs();
                phase.setCs(new CombatStrategy() {
                    @Override
                    public void resolution(List<DefaultCombatPhase.Duel> duel) {

                    }

                });

                CardGame.instance.getTriggers().register(Triggers.END_FILTER, new TriggerAction() {
                    DefaultCombatPhase phase;
                    CombatStrategy old;

                    @Override
                    public void execute(Object args) {
                        phase.setCs(old);
                        CardGame.instance.getTriggers().deregister(this);
                    }

                    public TriggerAction start(DefaultCombatPhase phase, CombatStrategy old) {
                        this.phase= phase;
                        this.old = old;
                        return this;
                    }
                }.start(phase, old));
            }

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
