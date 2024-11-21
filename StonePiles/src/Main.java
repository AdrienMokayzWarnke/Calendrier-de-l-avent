import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
     int a = 30;
     int b = 35;
     int c = 4;
     //35 - 30 = 5 -> 30
     //12 - 7 = 5 -> 37
    //Retirer un caillou de deux piles en même temps rapporte un point
     System.out.println(getScore(a,b,c));
    }

    public static int getScore(int a,int b, int c){
        int score = 0;
        int[] piles = {a,b,c};
        Arrays.sort(piles);
        score = piles[1] + (Integer.min(piles[2] - piles[1], piles[0]));
        return score;
    }
}