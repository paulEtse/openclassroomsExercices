package controller;

import games.GameEvaluator;
import model.Deck;
import model.Player;
import model.PlayingCard;
import view.CommandLineView;
import view.GameViewable;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    enum  GameState {
        AddingPlayers, CardsDealt, WinnerRevealed
    }
    Deck deck;
    List<Player> players;
    Player winner;
    GameViewable view;
    GameEvaluator gameEvaluator;

    GameState gameState;
    public GameController(Deck deck, GameViewable view, GameEvaluator gameEvaluator){
        this.deck = deck;
        this.players = new ArrayList<Player>();
        this.view = view;
        view.setController(this);
        this.gameState = GameState.AddingPlayers;
        this.gameEvaluator = gameEvaluator;
    }

    public void run(){
        while(gameState == GameState.AddingPlayers){
            view.promptForPlayerName();
        }
        switch (gameState){
            case CardsDealt:
                view.promptForFlip();
                break;
            case WinnerRevealed:
                view.promptForNewGame();
                break;
        }
    }

    public void addPlayer(String playerName){
        this.players.add(new Player(playerName));
        view.showPlayerName(players.size(), playerName);
    }

    public void nextAction(String nextChoice){
        if (nextChoice.equals("+q")){
            exitGame();
        }
        else{
            startGame();
        }

    }
    public void exitGame(){
        System.exit(0);
    }

    public void startGame(){
        if(gameState != GameState.CardsDealt){
            deck.shuffle();
            int playerIndex =1;
            for (Player player: players){
                player.addCardToHand(deck.removeTopCard());
                view.showFaceDownCardForPlayer(playerIndex++, player.getName()) ;
            }
            gameState = GameState.CardsDealt;
        }
        this.run();
    }
    public void flipCards(){
        int playerIndex = 1;
        for(Player player: players){
            PlayingCard pc = player.getCard(0);
            pc.flip();
            view.showCardForPlayer(playerIndex++, player.getName(), pc.getRank().toString(), pc.getSuit().toString());
        }
        evaluateWinner();
        displayWinner();
        rebuildDeck();
        gameState = GameState.WinnerRevealed;
        this.run();
    }

    void displayWinner(){
        view.showWinner(winner.getName());
    }

    void rebuildDeck(){
        for(Player player: players){
            deck.returnCardToDeck(player.removeCard());
        }
    }
    void evaluateWinner(){
        winner = gameEvaluator.evaluateWinner(players);
    }
}
