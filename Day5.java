package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day5 {

    public static FileReader file;

    static {
        try {
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay4.txt");
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay4.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem2) throws IOException {

        BufferedReader br = new BufferedReader(file);

        if(!problem2) {
            System.out.println();
        } else {
            System.out.println();
        }

        br.close();

        return 0;
    }

}
