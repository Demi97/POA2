/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

import cardgame.cards.Homeopathy;
import cardgame.cards.Reflexologist;
import cardgame.cards.FriendlyEnvironment;
import cardgame.cards.NorwoodRanger;
import cardgame.cards.VolcanicHammer;
import cardgame.cards.BronzeSable;
import cardgame.cards.AEtherBarrier;
import cardgame.cards.AEtherFlash;
import cardgame.cards.Abduction;
import cardgame.cards.Darkness;
import cardgame.cards.AncestralMask;
import cardgame.cards.WorldAtWar;
import cardgame.cards.Cancel;
import cardgame.cards.Afflict;
import cardgame.cards.Deflection;

/**
 *
 * @author atorsell
 */
public class CardGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //create decks
        ArrayList<Card> deck = new ArrayList<>();
        //for (int i=0; i!=5; ++i) deck.add(new Homeopathy());
        //for (int i=0; i!=5; ++i) deck.add(new Reflexologist());
        //for (int i=0; i!=5; ++i) deck.add(new FriendlyEnvironment());
        //for (int i=0; i!=5; ++i) deck.add(new NorwoodRanger());
        //for (int i=0; i!=5; ++i) deck.add(new BronzeSable());
        //for (int i=0; i!=5; ++i) deck.add(new AncestralMask());
        //for (int i=0; i!=5; ++i) deck.add(new Afflict());
        //for (int i=0; i!=5; ++i) deck.add(new Deflection());
        //for (int i=0; i!=5; ++i) deck.add(new Abduction());
        //for (int i=0; i!=5; ++i) deck.add(new Darkness());
        CardFactory factory = new CardFactory();
        factory.menu();
        Scanner reader = new Scanner(System.in);
        int choose;
        for(int i = 0; i < 10; i++) {
            System.out.println("Select card to add: ");
            do{
                try{
                    choose = reader.nextInt();
                }catch(Exception e) {choose = -1;}
            }while(choose < 0 || choose > 22);
            deck.add(factory.getCard(i));
        }
        instance.getPlayer(0).setDeck(deck.iterator());
        instance.getPlayer(1).setDeck(deck.iterator());
        
        instance.run();
    }
    
    //Signleton and instance access
    public static final CardGame instance = new CardGame();
    
    //game setup 
    private CardGame() { 
        turnManagerStack.push( new DefaultTurnManager(Players) );
        
        Players[0]=new Player();
        Players[0].setName("Player 1");
        Players[0].setPhase(Phases.DRAW,new SkipPhase(Phases.DRAW));
        
        
        Players[1]=new Player();
        Players[1].setName("Player 2");
    }
    
    //execute game
    public void run() {
        Players[0].getDeck().shuffle();
        Players[1].getDeck().shuffle();
                
        for (int i=0; i!=5; ++i) Players[0].draw();
        for (int i=0; i!=5; ++i) Players[1].draw();
        
        try {
            while (true) { instance.nextPlayer().executeTurn(); }
        } catch(EndOfGame e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    // Player and turn management
    private final Player[] Players = new Player[2];
    private final Deque<TurnManager>  turnManagerStack = new ArrayDeque<>();
    public void setTurnManager(TurnManager m) { turnManagerStack.push(m); }
    public void removeTurnManager(TurnManager m) { turnManagerStack.remove(m); }
    
    public Player getPlayer(int i) { return Players[i]; }    
    public Player getCurrentPlayer() { return turnManagerStack.peek().getCurrentPlayer(); }
    public Player getCurrentAdversary() { return turnManagerStack.peek().getCurrentAdversary(); }
    public Player getAdversary(Player p){ 
        if(p == Players[0]){
            return Players[1];
        }else{
            return Players[0];
        }
    }
    Player nextPlayer() { return turnManagerStack.peek().nextPlayer(); }
    
    
    // Stack access
    private final CardStack stack = new CardStack();
    public CardStack getStack() { return stack; }
    
    
    //Trigger access
    private final Triggers triggers=new Triggers();
    public Triggers getTriggers() { return triggers; }
    
    
    //IO resources  to be dropped in final version
    private final Scanner reader = new Scanner(System.in);
    Scanner getScanner() { return reader; }
}
