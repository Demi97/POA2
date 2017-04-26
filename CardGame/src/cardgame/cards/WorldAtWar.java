package cardgame.cards;

import cardgame.AbstractCardEffect;
import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.PhaseManager;
import cardgame.Player;
import cardgame.Phases;
/**
 *
 * @author don't mess with nikita
 */
public class WorldAtWar implements Card{
    private Player owner2;

    @Override
    public Effect getEffect(Player owner) {
        return new WorldAtWarEffect(owner,this);
    }

    @Override
    public String name() {
        return "World At War";
    }

    @Override
    public String type() {
        return "Sorcery";
    }

    @Override
    public String ruleText() {
        return "After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn";
    }

    @Override
    public boolean isInstant() {
        return false;
    }
    
    private class TurnChange implements PhaseManager {

        private int phaseidx = 0;

        @Override
        public Phases currentPhase() {
            Phases result;
            switch (phaseidx) {
                case 0:
                    result = Phases.MAIN;
                    break;
                case 1:
                    result = Phases.COMBAT;
                    break;
                case 2:
                    result = Phases.MAIN;
                    break;
                default:
                    System.out.println("World at War");
                    CardGame.instance.getCurrentPlayer().removePhaseManager(this);
                    result = CardGame.instance.getCurrentPlayer().currentPhaseId();
            }
            return result;
        }

        @Override
        public Phases nextPhase() {
            phaseidx++;
            if (phaseidx > 2) {
                System.out.println("Removing World at War turn changes");
                CardGame.instance.getCurrentPlayer().removePhaseManager(this);
                return CardGame.instance.getCurrentPlayer().currentPhaseId().next();
            }
            if (phaseidx == 1) {
                System.out.println("World at War untapping creatures before new combat phase");
                for (Creature c : CardGame.instance.getCurrentPlayer().getCreatures()) {
                    c.untap();
                }
            }
            return currentPhase();
        }

    }
    
    private class WorldAtWarEffect extends AbstractCardEffect {
        public WorldAtWarEffect(Player p, Card c) { 
            super(p,c);
            owner2 = CardGame.instance.getCurrentPlayer();
        }

        @Override
        public void resolve() {
            CardGame.instance.getCurrentPlayer().setPhaseManager(new TurnChange());
        }
    }
}
