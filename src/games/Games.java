package games;

import controller.GameController;
import model.Deck;
import view.CommandLineView;
import view.GameSwingView;

public class Games {
    public static void main(String[] args){
        GameController gc = new GameController(new Deck(), new GameSwingView(), new LowCardGameEvaluator());
        gc.run();
    }
}
