package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static FileReader file;

    public static BufferedReader br;

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay2.txt");
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem2) throws IOException {

        List<Game> games = br.lines().map( line -> new Game(line)).collect(Collectors.toList());

        int solution;

        if (problem2) {

            solution = games.stream()
                    .mapToInt(game -> game.getPower())
                    .sum();

        } else {

            solution = games.stream().mapToInt(game -> game.getId()).sum();

        }

        System.out.println(solution);

        br.close();

        return 0;
    }

};

class Set {

    static final String COLOR_DELIMITER = ",";
    final static String RED_COLOR = "red";
    final static String GREEN_COLOR = "green";
    final static String BLUE_COLOR = "blue";
    int redCubesNumber = 0;
    int greenCubesNumber = 0;
    int blueCubesNumber = 0;
    boolean isValid;

    public int getRedCubesNumber() {
        return redCubesNumber;
    }

    public int getGreenCubesNumber() {
        return greenCubesNumber;
    }

    public int getBlueCubesNumber() {
        return blueCubesNumber;
    }

    public Set(String s, int redValidNumber, int greenValidNumber, int blueCubesNumber) {
        String[] splitColors = s.split(COLOR_DELIMITER);

        for(int i=0;i<splitColors.length;i++) {

            if(splitColors[i].contains(RED_COLOR)){
                this.redCubesNumber = Integer.parseInt(splitColors[i].replace(RED_COLOR,"").trim());
            }
            if(splitColors[i].contains(GREEN_COLOR)){
                this.greenCubesNumber = Integer.parseInt(splitColors[i].replace(GREEN_COLOR,"").trim());
            }
            if(splitColors[i].contains(BLUE_COLOR)){
                this.blueCubesNumber = Integer.parseInt(splitColors[i].replace(BLUE_COLOR,"").trim());
            }
        }

        this.isValid = isValid(redValidNumber,greenValidNumber,blueCubesNumber);

    }

    boolean isValid(int redValidNumber, int greenValidNumber, int blueValidNumber){
        return (this.redCubesNumber <= redValidNumber
                && this.greenCubesNumber <= greenValidNumber
                && this.blueCubesNumber <= blueValidNumber);
    }
}

class  Game {

    static final String GAME_DELIMITER = ":";
    static final String SET_DELIMITER = ";";
    static final String GAME_PREFIX = "Game";

    int redValidNumber = 12;
    int greenValidNumber = 13;
    int blueCubesNumber = 14;

    int id;
    private List<Set> sets = new ArrayList<>();

    public Game(String inputString){
        String[] idSets = inputString.split(GAME_DELIMITER);
        this.id = parseId(idSets[0]);
        String[] sets = idSets[1].split(SET_DELIMITER);
        for (int i=0;i<sets.length;i++){
            Set s = new Set(sets[i],redValidNumber,greenValidNumber,blueCubesNumber);
            this.sets.add(s);
        }
    }

    public int getId(){
        boolean isValid = true;
        for(Set s : this.sets){
            if(!s.isValid){
                isValid = false;
            }
        }
        return isValid ? this.id : 0;
    }


    public int getPower(){

        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        for(Set set: this.sets) {

            if(set.getRedCubesNumber() > maxRed) maxRed=set.getRedCubesNumber();
            if(set.getGreenCubesNumber() > maxGreen) maxGreen=set.getGreenCubesNumber();
            if(set.getBlueCubesNumber() > maxBlue) maxBlue=set.getBlueCubesNumber();

        }

        return maxRed*maxGreen*maxBlue;
    }
    public int parseId(String s){
        return Integer.parseInt(s.replace(GAME_PREFIX,"").trim());
    }

}