package view;

import controller.GameController;

public interface GameViewable {
    public void promptForPlayerName();
    public void promptForFlip();
    public void promptForNewGame();
    public void showWinner(String winnerName);
    public void showFaceDownCardForPlayer(int index, String name);
    public void showCardForPlayer(int index, String name, String rank, String suit);
    public void showPlayerName(int playerIndex, String playerName);
    public void setController(GameController gc);
}
