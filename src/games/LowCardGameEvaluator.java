package games;

import model.Player;
import model.PlayingCard;

import java.util.List;

class LowCardGameEvaluator implements GameEvaluator{
    @Override
    public Player evaluateWinner(List<Player> players){
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
                if(thisRank <= bestRank){
                    if(thisRank < bestRank){
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
        return bestPlayer;
    }

}
