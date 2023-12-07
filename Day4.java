package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    public static FileReader file;
    public static List<Ticket> tickets = new ArrayList<>();

    static {
        try {
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay4.txt");
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay4.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static int solution(boolean problem2) throws IOException {

        BufferedReader br = new BufferedReader(file);

        tickets = parseTickets(br);

        if(!problem2) {
            System.out.println(solvePartOne(tickets));
        } else {
            System.out.println(solvePartTwo(tickets));
        }

        br.close();

        return 0;
    }

    public static int solvePartOne(List<Ticket> tickets) {
        return tickets.stream().mapToInt(ticket -> calculateScorePartOne(ticket.getWinningNumbers(),ticket.getNumbers())).sum();
    }

    public static int solvePartTwo(List<Ticket> tickets) {
        int score = 0;
        int maxIndex = tickets.size();
        int[] ticketsNumber = new int[tickets.size()];
        Arrays.fill(ticketsNumber,1);
        for (int i=0;i<maxIndex;i++) {
            score += ticketsNumber[i];
            Ticket ticket = tickets.get(i);
            int ticketScore = calculateScorePartTwo(ticket.getWinningNumbers(),ticket.getNumbers());
            for (int j = i+1; j < i + 1 + ticketScore; j++){
                if(j<maxIndex) {
                    ticketsNumber[j]+=ticketsNumber[i];
                }
            }
        }
        return score;
    }

    public static int calculateScorePartOne(List<Integer> winningNumbers, List<Integer> numbers) {
        int score = (int) Math.pow(2,winningNumbers.stream().filter(numbers::contains).collect(Collectors.toList()).size()-1);
        return score >= 1 ? score : 0;
    }

    public static int calculateScorePartTwo(List<Integer> winningNumbers, List<Integer> numbers) {
        return winningNumbers.stream().filter(numbers::contains).collect(Collectors.toList()).size();
    }

    public static List<Ticket> parseTickets(BufferedReader br){
        List<Ticket> tickets = new ArrayList<>();
        List<String> stringInput = br.lines().collect(Collectors.toList());
        for (String s : stringInput){
            String[] splitIdNumbers = s.split(":");
            int id = Integer.parseInt(splitIdNumbers[0].replace("Card","").trim());
            String[] splitNumbers = splitIdNumbers[1].split("\\|");
            List<Integer> winningNumbers = Arrays.stream(splitNumbers[0].replace("  "," ").trim().split(" "))
                    .collect(Collectors.toList())
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            List<Integer> numbers = Arrays.stream(splitNumbers[1].replace("  "," ").trim().split(" "))
                    .collect(Collectors.toList())
                        .stream().map(Integer::parseInt)
                        .collect(Collectors.toList());
            Ticket ticket = new Ticket(id, winningNumbers, numbers);
            System.out.println(ticket.toString());
            tickets.add(ticket);
        }
        return  tickets;
    }

    static class Ticket {

        int id;
        List<Integer> winningNumbers;
        List<Integer> numbers;
        int score = 0;

        public Ticket(int id, List<Integer> winningNumbers, List<Integer> numbers){
            this.id = id;
            this.winningNumbers = winningNumbers;
            this.numbers = numbers;
        }


        public List<Integer> getWinningNumbers() {
            return winningNumbers;
        }


        public List<Integer> getNumbers() {
            return numbers;
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "id=" + id +
                    ", winningNumbers=" + winningNumbers +
                    ", numbers=" + numbers +
                    ", score=" + score +
                    '}';
        }
    }
}
