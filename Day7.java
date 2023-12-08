package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {
    static List<Hand> hands = new ArrayList<>();

    static Map<Character,Integer> cardsValue = new HashMap<>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 11);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    static Map<Character,Integer> cardsValueProblem2 = new HashMap<>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 1);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};
    public static FileReader file;
    public static BufferedReader br;
    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay7.txt");
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay7.txt");
            br = new BufferedReader(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem1) throws IOException {

        parseData(problem1);

        System.out.println(solvePartOne(false));

        br.close();
        return 0;

    }

    private static long solvePartOne(boolean logs) {
        long result = 0;
        hands.sort(new HandComparator());
        for (int i=0;i<hands.size();i++){
            if(logs) System.out.println("Current result : " + result + ", now adding card " + Arrays.toString(hands.get(i).getCards()) + " at rank " + (i+1) + " with score: "+ hands.get(i).getScore() + " for a total of +" + hands.get(i).getScore() * (i+1));
            result += hands.get(i).getScore() * (i+1);
        }
        return result;
    }

    private static void parseData(boolean problem1){
        hands = br.lines().map(line -> new Hand(problem1,line.split(" ")[0],Integer.parseInt(line.split(" ")[1]))).collect(Collectors.toList());
    }

}

class HandComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand o1, Hand o2) {
        if(o1.getTotalValue() > o2.getTotalValue()){
            return 1;
        } else if (o1.getTotalValue() == o2.getTotalValue()){
            for (int i=0; i<o1.getCards().length; i++){
                if(o1.getCards()[i] > o2.getCards()[i]) {
                    return 1;
                } else if (o1.getCards()[i] < o2.getCards()[i]) {
                    return -1;
                }
            }
        } else if(o1.getTotalValue() < o2.getTotalValue()){
            return -1;
        }
        return 0;
    }
}

class Hand {
    int[] cards = new int[5];
    int totalValue;
    long score;
    public Hand(boolean problem1, String initString,int score) {


        for (int i=0;i<initString.length();i++){
            if(problem1) {
                cards[i] = Day7.cardsValue.get(initString.charAt(i));
                this.totalValue = calculateValue(cards);
            } else {
                cards[i] = Day7.cardsValueProblem2.get(initString.charAt(i));
                this.totalValue = calculateValueWithJokers(cards);
            }
        }
        this.score = score;
    }
    private int calculateValue(int[] cards){
        int r1 = 1;
        int r2 = 1;
        for (int value : Day7.cardsValue.values()) {
            int maxOccur = (int) Arrays.stream(cards).filter(card -> card == value).mapToLong(e -> 1L).sum();
            if(maxOccur >= r1){
                r2 = r1;
                r1 = maxOccur;
            } else if(maxOccur >= r2){
                r2 = maxOccur;
            }
        }
       return 2*r1 + r2;

    }

    private int calculateValueWithJokers(int[] cards){
        int maxScore = calculateValue(cards);
        if((int) Arrays.stream(cards).filter(card -> card == 1).mapToLong(e -> 1L).sum() > 0){
            for (int value : Day7.cardsValue.values()) {
                int modifiedScore = calculateValue(Arrays.stream(cards).map(updateValue -> updateValue==1 ? value : updateValue).toArray());
                if( modifiedScore > maxScore) {
                    maxScore = modifiedScore;
                }
            }
        }
        return maxScore;
    }
    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + Arrays.toString(cards) +
                ", totalValue=" + totalValue +
                '}';
    }

    public int[] getCards() {
        return cards;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public long getScore() {
        return score;
    }
}
