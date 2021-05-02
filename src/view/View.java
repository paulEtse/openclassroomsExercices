package view;

import controller.GameController;

import java.util.Scanner;

public class View{
    GameController controller;
    Scanner keyboard = new Scanner(System.in);
    public void promptForPlayerName(){
        System.out.println("Enter Player Name: ");
        String name = keyboard.nextLine();
        System.out.println(name.isEmpty());
        if(name.isEmpty()){
            controller.startGame();
        }
        else {
            controller.addPlayer(name);
        }
    }
    public void promptForFlip(){
        System.out.println("Press enter to reveal cards");
        keyboard.nextLine();
        controller.flipCards();
    }
    public void promptForNewGame(){
        System.out.println("Press enter to deal again: ");
        keyboard.nextLine();
        controller.startGame();
    }
    public void showWinner(String winnerName){
        System.out.println("Winner is "+winnerName+" !");
    }
    public void showFaceDownCardForPlayer(int index, String name){
        System.out.println("["+index+"]["+name+"][x][x]");
    }
    public  void showCardForPlayer(int index, String name, String rank, String suit){
        System.out.println("["+index+"]["+name+"]["+rank+"]["+suit+"]");
    }
    public  void showPlayerName(int playerIndex, String playerName){
        System.out.println("["+playerIndex+"]["+playerName+"]");
    }
    public void setController(GameController gc){
        controller = gc;
    }
}
