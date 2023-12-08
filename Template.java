package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Template {

    public static FileReader file;
    public static BufferedReader br;

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay6.txt");
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay6.txt");
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem2) throws IOException {


        if(!problem2) {
            System.out.println(solvePartOne());
        } else {
            System.out.println(solvePartTwo());
        }

        br.close();

        return 0;
    }

    private static int solvePartOne() {

        return 0;
    }

    private static int solvePartTwo() {

        return 0;
    }

    private static void parseData(){

    }

}
