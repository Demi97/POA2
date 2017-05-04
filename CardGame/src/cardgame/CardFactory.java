/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import cardgame.cards.AEtherBarrier;
import cardgame.cards.AEtherFlash;
import cardgame.cards.Abduction;
import cardgame.cards.Afflict;
import cardgame.cards.AggressiveUrge;
import cardgame.cards.AncestralMask;
import cardgame.cards.ArgothianEnchantress;
import cardgame.cards.AuraBlast;
import cardgame.cards.BenevolentAncestor;
import cardgame.cards.BoilingEarth;
import cardgame.cards.BronzeSable;
import cardgame.cards.CalmingVerse;
import cardgame.cards.Cancel;
import cardgame.cards.Darkness;
import cardgame.cards.DayOfJudgment;
import cardgame.cards.Deflection;
import cardgame.cards.FalsePeace;
import cardgame.cards.Fatigue;
import cardgame.cards.NorwoodRanger;
import cardgame.cards.SavorTheMoment;
import cardgame.cards.VolcanicHammer;
import cardgame.cards.WorldAtWar;

/**
 *
 * @author simonescaboro
 */
public class CardFactory {
    public void menu() {
        System.out.println("======== Cards ========");
        System.out.println("1.  Abduction");
        System.out.println("2.  AEther Barrier");
        System.out.println("3.  AEther Flash");
        System.out.println("4.  Afflict");
        System.out.println("5.  Aggressive Urge");
        System.out.println("6.  Ancestral Mask");
        System.out.println("7.  Argothian Enchantress");
        System.out.println("8.  Aura Blast");
        System.out.println("9.  Benevolent Ancestor");
        System.out.println("10. Boiling Earth");
        System.out.println("11. Bronze Sable");
        System.out.println("12. Calming Verse");
        System.out.println("13. Cancel");
        System.out.println("14. Darkness");
        System.out.println("15. Day of Judgment");
        System.out.println("16. Deflection");
        System.out.println("17. False Peace");
        System.out.println("18. Fatigue");
        System.out.println("19. Norwood Ranger");
        System.out.println("20. Savor the Moment");
        System.out.println("21. Volcanic Hammer");
        System.out.println("22. World at War");
        System.out.println("======================");
        
        
    }
    public Card getCard(int card){
        switch(card) {
            case 1:
                return new Abduction();
            case 2:
                return new AEtherBarrier();
            case 3:
                return new AEtherFlash();
            case 4:
                return new Afflict();
            case 5:
                return new AggressiveUrge();
            case 6:
                return new AncestralMask();
            case 7:
                return new ArgothianEnchantress();
            case 8:
                return new AuraBlast();
            case 9:
                return new BenevolentAncestor();
            case 10:
                return new BoilingEarth();
            case 11:
                return new BronzeSable();
            case 12:
                return new CalmingVerse();
            case 13:
                return new Cancel();
            case 14:
                return new Darkness();
            case 15:
                return new DayOfJudgment();
            case 16:
                return new Deflection();
            case 17:
                return new FalsePeace();
            case 18:
                return new Fatigue();
            case 19:
                return new NorwoodRanger();
            case 20:
                return new SavorTheMoment();
            case 21:
                return new VolcanicHammer();
            case 22:
                return new WorldAtWar();
        }
        return new WorldAtWar();
    }
}
