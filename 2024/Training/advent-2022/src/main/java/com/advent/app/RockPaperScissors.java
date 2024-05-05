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

    private Hand getHandPart2(Hand opponentHand, char outcome) {
        if (outcome == 'X') {
            // Lose
            switch (opponentHand) {
                case ROCK:
                    return Hand.SCISSORS;
                case PAPER:
                    return Hand.ROCK;
                default:
                    return Hand.PAPER;
            }
        } else if (outcome == 'Y') {
            // Draw
            return opponentHand;
        } else {
            // Win
            switch (opponentHand) {
                case ROCK:
                    return Hand.PAPER;
                case PAPER:
                    return Hand.SCISSORS;
                default:
                    return Hand.ROCK;
            }
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

    private boolean myHandWins(Hand opponent, Hand mine) {
        switch(opponent) {
            case ROCK:
                return mine == Hand.PAPER;
            case PAPER:
                return mine == Hand.SCISSORS;
            default:
                return mine == Hand.ROCK;
        }
    }

    private class Round {
        public Hand opponentHand, myHand;
    }

    private ArrayList<Round> rounds;

    public RockPaperScissors(ArrayList<String> input, boolean part2) {
        rounds = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            Round r = new Round();
            String h = input.get(i);
            r.opponentHand = getHand(h.charAt(0));
            if (!part2) {
                r.myHand = getHand(h.charAt(2));
            } else {
                r.myHand = getHandPart2(r.opponentHand, h.charAt(2));
            }
            rounds.add(r);
        }
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (int i = 0; i < rounds.size(); i++) {
            Round r = rounds.get(i);
            totalScore += getHandValue(r.myHand);
            if (myHandWins(r.opponentHand, r.myHand)) {
                totalScore += 6;
            } else if (r.opponentHand == r.myHand) {
                totalScore += 3;
            }
        }
        return totalScore;
    }
}
