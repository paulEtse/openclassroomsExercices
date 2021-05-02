package controller;

import model.Deck;
import model.Player;
import model.PlayingCard;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    enum  GameState {
        AddingPlayers, CardsDealt, WinnerRevealed
    }
    Deck deck;
    List<Player> players;
    Player winner;
    View view;

    GameState gameState;
    public GameController(Deck deck, View view){
        this.deck = deck;
        this.players = new ArrayList<Player>();
        this.view = view;
        view.setController(this);
        this.gameState = GameState.AddingPlayers;

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
        Player bestPlayer = null;
        int bestSuit = -1;
        int bestRank = -1;
        for(Player player: players){
            boolean newBestPlayer = false;
            if(bestPlayer == null){
                newBestPlayer = true;
            }
            else{
                PlayingCard pc = player.getCard(0);
                int thisRank = pc.getRank().value();
                if(thisRank >= bestRank){
                    if(thisRank > bestRank){
                        newBestPlayer = true;
                    }
                    else if (pc.getSuit().value() > bestSuit){
                        newBestPlayer = true;
                    }
                }
            }
            if (newBestPlayer){
                bestPlayer = player;
                PlayingCard pc = player.getCard(0);
                bestRank = pc.getRank().value();
                bestSuit = pc.getSuit().value();

            }
        }
        winner = bestPlayer;
    }
}
