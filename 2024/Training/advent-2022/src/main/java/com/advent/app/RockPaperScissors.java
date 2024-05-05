package com.advent.app;

import java.util.ArrayList;

public class RockPaperScissors {

    private enum Hand {
        ROCK,
        PAPER,
        SCISSORS
    }

    private Hand getHand(char hand) {
        switch(hand) {
            case 'A':
            case 'X':
                return Hand.ROCK;
            case 'B':
            case 'Y':
                return Hand.PAPER;
            default:
                return Hand.SCISSORS;
        }
    }

    private int getHandValue(Hand hand) {
        switch(hand) {
            case ROCK:
                return 1;
            case PAPER:
                return 2;
            default:
                return 3;
        }
    }

    private class Round {
        public Hand opponentHand, myHand;
    }

    private ArrayList<Round> rounds;

    public RockPaperScissors(ArrayList<String> input) {
        rounds = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            Round r = new Round();
            String h = input.get(i);
            r.opponentHand = getHand(h.charAt(0));
            r.myHand = getHand(h.charAt(2));
            rounds.add(r);
        }
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (int i = 0; i < rounds.size(); i++) {
            //
        }
        return totalScore;
    }
}
