package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

    //81709807
    // 5667402
    //46765874

    public static FileReader file;
    private static final String regExSpecialChars = "-%!@#&()–[{}]:;',?/*~$^+=<>";
    private static final int leftLineLimit = 0;
    private static int rightLineLimit;
    private static int bottomLineLimit;
    private static List<Gear> gears = new ArrayList<>();

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay3.txt");
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay3.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem2) throws IOException {
        int sum = 0;
        String[] data = getData();

        for (int i=0; i<bottomLineLimit; i++) {
            for (int j=0; j<rightLineLimit; j++) {
                for (int k=j; k<rightLineLimit; k++){
                    String number = getNumber(data,i,j,k);
                    if(number != null && !problem2) {
                        if (isAdjacent(data, i, j, k)) {
                            sum += Math.abs(Integer.parseInt(number));
                        }
                    } else if (number != null && problem2) {
                        findGears(data,i,j,k);
                    }
                }
            }
        }

        if(problem2){
            for(Gear gear : gears) {
                if(gear.getSharedPiece()==2){
                    sum+=gear.getGearRatio();
                }
            }
        }
        System.out.println(sum);
        return 0;
    }

    public static boolean isAdjacent(String[] data, int i, int j, int k) {
        boolean adjacent = false;

        String number = data[i].substring(j,k+1);
        String lineBefore = i > leftLineLimit ? data[i-1] : null;
        String lineAfter = i < bottomLineLimit-1 ? data[i+1] : null;
        char charBefore = j > leftLineLimit ? data[i].charAt(j-1) : '\0';
        char charAfter = k < rightLineLimit-1 ? data[i].charAt(k+1) : '\0';

        if(lineBefore != null) {
            adjacent = adjacent || containsSpecialChar(lineBefore.substring(j > 0 ? j-1 : j,k < rightLineLimit-1 ? k+2 : k+1),false);
        }
        if(lineAfter != null) {
            adjacent = adjacent || containsSpecialChar(lineAfter.substring(j > 0 ? j-1 : j,k < rightLineLimit-1 ? k+2 : k+1),false);
        }
        if(charBefore != '\0' && isSpecialChar(charBefore)) adjacent = true;
        if(charAfter != '\0' && isSpecialChar(charAfter)) adjacent = true;

        return adjacent;
    }

    public static void findGears(String[] data, int i, int j, int k) {

        String number = data[i].substring(j,k+1);
        String lineBefore = i > leftLineLimit ? data[i-1] : null;
        String lineAfter = i < bottomLineLimit-1 ? data[i+1] : null;
        char charBefore = j > leftLineLimit ? data[i].charAt(j-1) : '\0';
        char charAfter = k < rightLineLimit-1 ? data[i].charAt(k+1) : '\0';
        int right = j > 0 ? j-1 : j;
        int left = k < rightLineLimit-1 ? k+1 : k;
        if(lineBefore != null) {
            for(int g=right;g<left;g++){
                if(isGear(lineBefore.charAt(g))){
                    addGear(i-1,g,number);
                }
            }
        }
        if(lineAfter != null) {
            for(int g=right;g<left;g++){
                if(isGear(lineAfter.charAt(g))){
                    addGear(i+1,g,number);
                }
            }
        }
        if(charBefore != '\0' && isGear(charBefore)) addGear(i,j-1,number);
        if(charAfter != '\0' && isGear(charAfter)) addGear(i,k+1,number);

    }

    public static void addGear(int i,int j,String number){
        Gear g = new Gear(i,j);
        boolean isCounted = false;
        for (Gear gear : gears){
            if(gear.equals(g)){
                gear.addPiece(number);
                isCounted = true;
            }
        }
        if(!isCounted){
            g.addPiece(number);
            gears.add(g);
        }
    }

    public static boolean isGear(char c){
        return c=='*';
    }

    public static String getNumber(String[] data, int i, int j, int k) {
        try {
            Integer.parseInt(data[i].substring(j,k+1));

            if(k==rightLineLimit-1){
                if(j>leftLineLimit && !isNumber(data[i].charAt(j-1))){
                    return data[i].substring(j,k+1);
                } else return null;
            } else if ( (j ==leftLineLimit && k==rightLineLimit)
                || (j==leftLineLimit && k<rightLineLimit && !isNumber( data[i].charAt(k+1)))
                || (j>leftLineLimit && !isNumber(data[i].charAt(j-1)) && k==rightLineLimit)
                || ((j>leftLineLimit && !isNumber(data[i].charAt(j-1)))
                    && (k<rightLineLimit && !isNumber( data[i].charAt(k+1)))))
            {
                return data[i].substring(j,k+1);
            } else return null;

        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static boolean isNumber(char c){
        try {
            Integer.parseInt(String.valueOf(c));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String[] getData() throws IOException {

        BufferedReader br = new BufferedReader(file);
        String[] data = br.lines().toArray(size -> new String[size]);

        rightLineLimit = data[0].length();
        bottomLineLimit = data.length;

        //printMatrix(data);

        br.close();

        return data;
    }
    
    public static void printMatrix(String[] matrix){
        for (int i=0; i<matrix.length; i++) {
            System.out.print("Line " + i + ": ");
            for (int j=0; j<matrix[0].length();j++){
                System.out.print(matrix[i].charAt(j));
            }
            System.out.println();
        }
    }

    public static boolean isSpecialChar(char c){
        if(regExSpecialChars.contains(String.valueOf(c))) return true;
        else return false;
    }

    public static boolean containsSpecialChar(String s, boolean problem2){

        for (int i=0; i<s.length();i++){
            if(!problem2) {
                if(isSpecialChar(s.charAt(i))) return true;
            } else {
                if(isGear(s.charAt(i))) return true;
            }
        }
        return false;
    }
}

class Gear {

    int sharedPiece = 0;
    int gearRatio = 1;
    int i;
    int j;

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Gear(int x, int y) {
        this.i = x;
        this.j = y;
    }

    public void addPiece(String s) {
        sharedPiece++;
        gearRatio *= Math.abs(Integer.parseInt(s));
    }

    public int getSharedPiece() {
        return sharedPiece;
    }

    public int getGearRatio() {
        return gearRatio;
    }

    @Override
    public boolean equals(Object obj) {
        Gear gear = (Gear) obj;
        return i == gear.getI() && j == gear.getJ();
    }
}

