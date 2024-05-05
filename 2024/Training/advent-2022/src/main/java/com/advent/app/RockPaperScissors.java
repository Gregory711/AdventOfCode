package com.advent.app;

import java.util.ArrayList;

public class RockPaperScissors {

    private enum Hand {
        ROCK,
        PAPER,
        SCISSORS
    }

    private class Round {
        public Hand opponentHand, myHand;
    }

    private ArrayList<Round> rounds;

    public RockPaperScissors(ArrayList<String> input) {
        rounds = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            //
        }
    }
}
