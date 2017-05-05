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
import cardgame.cards.Homeopathy;
import cardgame.cards.NorwoodRanger;
import cardgame.cards.Reflexologist;
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
        System.out.println("Creatures:");
        System.out.println("    1.  Norwood Ranger");
        System.out.println("    2.  Bronze Sable");
        System.out.println("    3.  Argothian Enchantress");
        System.out.println("    4.  Benevolent Ancestor");
        System.out.println("    5.  Reflexologist");
        System.out.println("Sorcery");
        System.out.println("    6.  Volcanic Hammer");
        System.out.println("    7.  Boiling Earth");
        System.out.println("    8.  Day Of Judgment");
        System.out.println("    9.  World at War");
        System.out.println("    10. Savor the Moment");
        System.out.println("    11. False Peace");
        System.out.println("    12. Fatigue");
        System.out.println("    13. Calming Verse");
        System.out.println("Instants:");
        System.out.println("    14. Cancel");
        System.out.println("    15. Deflection");
        System.out.println("    16. Darkness");
        System.out.println("    17. Aggressive Urge");
        System.out.println("    18. Afflict");
        System.out.println("    19. Aura Blast");
        System.out.println("    20. Homeopathy");
        System.out.println("Enchantments:");
        System.out.println("    21. Abduction");
        System.out.println("    22. AEther Barrier");
        System.out.println("    23. AEther Flash");
        System.out.println("    24. Ancentral Mask");
        System.out.println("    25. Friendly Environment");
        System.out.println("======================");
        
        
    }
    public String cardString(int card) {
        switch(card) {
            case 1:
                return "NorwoodRanger";
            case 2:
                return "BronzeSable";
            case 3:
                return "Argothian Enchantress";
            case 4:
                return "BenevolentAncestor";
            case 5:
                return "Reflexologist";
            case 6:
                return "VolcanicHammer";
            case 7:
                return "BoilingEarth";
            case 8:
                return "DayOfJudgment";
            case 9:
                return "WorldAtWar";
            case 10:
                return "SavorTheMoment";
            case 11:
                return "FalsePeace";
            case 12:
                return "Fatigue";
            case 13:
                return "CalmingVerse";
            case 14:
                return "Cancel";
            case 15:
                return "Deflection";
            case 16:
                return "Darkness";
            case 17:
                return "AggressiveUrge";
            case 18:
                return "Afflict";
            case 19:
                return "AuraBlast";
            case 20:
                return "Homeopathy";
            case 21:
                return "Abduction";
            case 22:
                return "AEtherBarrier";
            case 23:
                return "AEtherFlash";
            case 24:
                return "AncestralMask";
            case 25:
                return "AncestralMask";
        }
        return "";
    }
    
    public Card getCard(String card){
        switch(card) {
            case "NorwoodRanger":
                return new NorwoodRanger();
            case "BronzeSable":
                return new BronzeSable();
            case "ArgothianEnchantress":
                return new ArgothianEnchantress();
            case "BenevolentAncestor":
                return new BenevolentAncestor();
            case "Reflexologist":
                return new Reflexologist();
            case "VolcanicHammer":
                return new VolcanicHammer();
            case "BoilingEarth":
                return new BoilingEarth();
            case "DayOfJudgment":
                return new DayOfJudgment();
            case "WorldAtWar":
                return new WorldAtWar();
            case "SavorTheMoment":
                return new SavorTheMoment();
            case "FalsePeace":
                return new FalsePeace();
            case "Fatigue":
                return new Fatigue();
            case "CalmingVerse":
                return new CalmingVerse();
            case "Cancel":
                return new Cancel();
            case "Deflection":
                return new Deflection();
            case "Darkness":
                return new Darkness();
            case "AggressiveUrge":
                return new AggressiveUrge();
            case "Afflict":
                return new Afflict();
            case "AuraBlast":
                return new AuraBlast();
            case "Homeopathy":
                return new Homeopathy();
            case "Abduction":
                return new Abduction();            
            case "AEtherBarrier":
                return new AEtherBarrier();
            case "AEtherFlash":
                return new AEtherFlash();
            case "AncestralMask":
                return new AncestralMask();
            case "FriendlyEnvironment":
                return new FriendlyEnvironment();
                
        }
        return new FriendlyEnvironment();
    }
}
