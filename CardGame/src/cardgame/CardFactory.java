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
import cardgame.cards.FriendlyEnvironment;
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
    public String cardString(int card) {
        switch(card) {
            case 1:
                return "Abduction";
            case 2:
                return "AEtherBarrier";
            case 3:
                return "AEtherFlash";
            case 4:
                return "Afflict";
            case 5:
                return "AggressiveUrge";
            case 6:
                return "AncestralMask";
            case 7:
                return "ArgothianEnchantress";
            case 8:
                return "AuraBlast";
            case 9:
                return "BenevolentAncestor";
            case 10:
                return "BoilingEarth";
            case 11:
                return "BronzeSable";
            case 12:
                return "CalmingVerse";
            case 13:
                return "Cancel";
            case 14:
                return "Darkness";
            case 15:
                return "DayOfJudgment";
            case 16:
                return "Deflection";
            case 17:
                return "FalsePeace";
            case 18:
                return "Fatigue";
            case 19:
                return "NorwoodRanger";
            case 20:
                return "SavorTheMoment";
            case 21:
                return "VolcanicHammer";
            case 22:
                return "WorldAtWar";
        }
        return "";
    }
    
    public Card getCard(String card){
        switch(card) {
            case "Abduction":
                return new Abduction();
            case "AEtherBarrier":
                return new AEtherBarrier();
            case "AEtherFlash":
                return new AEtherFlash();
            case "Afflict":
                return new Afflict();
            case "AggressiveUrge":
                return new AggressiveUrge();
            case "AncestralMask":
                return new AncestralMask();
            case "ArgothianEnchantress":
                return new ArgothianEnchantress();
            case "AuraBlast":
                return new AuraBlast();
            case "BenevolentAncestor":
                return new BenevolentAncestor();
            case "BoilingEarth":
                return new BoilingEarth();
            case "BronzeSable":
                return new BronzeSable();
            case "CalmingVerse":
                return new CalmingVerse();
            case "Cancel":
                return new Cancel();
            case "Darkness":
                return new Darkness();
            case "DayOfJudgment":
                return new DayOfJudgment();
            case "Deflection":
                return new Deflection();
            case "FalsePeace":
                return new FalsePeace();
            case "Fatigue":
                return new Fatigue();
            case "NorwoodRanger":
                return new NorwoodRanger();
            case "SavorTheMoment":
                return new SavorTheMoment();
            case "VolcanicHammer":
                return new VolcanicHammer();
            case "WorldAtWar":
                return new WorldAtWar();
        }
        return new FriendlyEnvironment();
    }
}
