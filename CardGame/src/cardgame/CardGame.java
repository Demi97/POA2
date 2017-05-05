/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;
/**
 *
 * @author atorsell
 */
public class CardGame {

    /**
     * @param args the command line arguments
     */
        
    //Signleton and instance access
    public static final CardGame instance = new CardGame();
    
    public static void main(String[] args) {
        //create decks
        ArrayList<Card> deck = new ArrayList<>();
        System.out.println("~~~~~~~~~~~~~STARTING GAME~~~~~~~~~~~~~");
        CardFactory factory = new CardFactory();
        Scanner reader = new Scanner(System.in);
        System.out.println("Deck's creation\n1) From file\n2) From factory");
        int index;
        do{
            System.out.print("Choose type of input: ");
            try{
                index = reader.nextInt();
            }catch(Exception e ) { index = -1; }
        }while(index != 1 && index != 2);
        if(index == 2) {
            factoryFromKeyboard(factory, deck);
        }
        else {
            try{
            factoryFromFile(factory,deck);
            }catch(Exception e) { System.out.println("Errore apertura file");}
        }
        instance.getPlayer(0).setDeck(deck.iterator());
        instance.getPlayer(1).setDeck(deck.iterator());
        
        instance.run();
    }
    
    private static void factoryFromFile(CardFactory factory, ArrayList<Card> deck) throws FileNotFoundException{
        //factoryFromKeyboard(factory, deck);
        File file = new File("deck.txt");
        try{
            Scanner input = new Scanner(file);
            while(input.hasNext()){
                String n = input.nextLine();
                deck.add(factory.getCard(n));
            }
        }catch(Exception e){System.out.println("Errore nell'apertura del file");}
    }
    private static void factoryFromKeyboard(CardFactory factory, ArrayList<Card> deck){
        factory.menu();    
        int choose;
        for(int i = 0; i < 15; i++) {
            System.out.print("Select card to add: ");
            do{
                try{
                    choose = CardGame.instance.getScanner().nextInt();
                }catch(Exception e) {choose = -1;}
            }while(choose < 0 || choose > 25);
            
            deck.add(factory.getCard(factory.cardString(choose)));
        }
    }

    
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
