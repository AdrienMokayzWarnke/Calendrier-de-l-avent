package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {

    public static FileReader file;
    public static BufferedReader br;

    private static List<Race> races = new ArrayList<>();

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay6.txt");
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay6.txt");
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem1) throws IOException {

        parseData(problem1);

        if(problem1) {
            System.out.println(solvePartOne());
        } else {
            System.out.println(solvePartTwo());
        }

        br.close();

        return 0;
    }

    private static long solvePartOne() {
        long max = 1;
        for (Race race : races){
            max = max*getScore(race);
        }
        return max;
    }

    private static long solvePartTwo() {
        return solvePartOne();
    }

    private static Long getScore(Race race){

        long maxLimit = Long.MAX_VALUE;
        long minLimit = 0;
        long pressedTime = 0;
        for (long i = race.getTime();i>0;i--){
            if (getDistance(race.getTime(),pressedTime)>race.getDistanceMax()){
                maxLimit = i;
                break;
            } else {
                pressedTime++;
            }
        }
        pressedTime = race.getTime();

        for (long i = 0; i<race.getTime(); i++){
            if (getDistance(race.getTime(),pressedTime)>race.getDistanceMax()){
                minLimit = i;
                break;
            } else {
                pressedTime--;
            }
        }

        return maxLimit-minLimit+1;
    }

    private static long getDistance(Long totalTime, Long timePressed){
        return (totalTime-timePressed)*timePressed;
    }

    private static void parseData(boolean problem1){

        List<String> input = br.lines().toList();

        List<String> timeString = input.stream().filter(string -> string.startsWith("Time")).toList();
        List<String> distanceString = input.stream().filter(string -> string.startsWith("Distance")).toList();


        if (problem1) {

        List<Integer> times;
        List<Integer> distances;

        times = timeString.stream().flatMap(line -> Arrays.stream(line.split(": ")[1].trim().split(" ")))
                .filter(s -> !"".equals(s))
                .map(time -> Integer.parseInt(time.trim())).toList();

        distances = distanceString.stream().flatMap(line -> Arrays.stream(line.split(": ")[1].trim().split(" ")))
                .filter(s -> !"".equals(s))
                .map(Integer::parseInt).toList();

            for(int i=0;i<times.size();i++){
                races.add(new Race(i,times.get(i),distances.get(i)));
            }

        } else {
            long times;
            long distances;

            times = Long.parseLong(String.join(" ", timeString).split(":")[1].replace(" ",""));
            distances = Long.parseLong(String.join(" ", distanceString).split(":")[1].replace(" ",""));

            races.add(new Race(0,times,distances));
        }

    }

}

class Race {

    int id;
    long time;
    long distanceMax;

    public Race(int id, long time, long distanceMax){
        this.id = id;
        this.time = time;
        this.distanceMax = distanceMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(int distanceMax) {
        this.distanceMax = distanceMax;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", time=" + time +
                ", distanceMax=" + distanceMax +
                '}';
    }
}