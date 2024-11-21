import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[][] mat =
        {{7, 9, 2, 1, 5, 4, 3, 8, 6},
        {6, 4, 3, 8, 2, 7, 1, 5, 9},
        {8, 5, 1, 3, 9, 6, 7, 2, 4},
        {2, 6, 5, 9, 7, 3, 8, 4, 1},
        {4, 8, 9, 5, 6, 1, 2, 7, 3},
        {3, 1, 7, 4, 8, 2, 9, 6, 5},
        {1, 3, 6, 7, 4, 8, 5, 9, 2},
        {9, 7, 4, 2, 1, 5, 6, 3, 8},
        {5, 2, 8, 6, 3, 9, 4, 1, 7}};


        System.out.println(resolveMatrix(mat));
    }

    public static boolean resolveMatrix(int[][] input){
        int n = input[0].length;
        int target = ((n+1)*n)/2;
        boolean answer = true;
        for (int i =0;i<n;i++){
            //column
            int[] col = input[i];
            int sumRow = 0;
            int sumCol = 0;
            for (int j=0;j<n;j++){
                //check rows
                sumRow += input[i][j];
                //check columns
                sumCol += col[j];
            }
            if(sumRow!=target ||sumCol !=target)
                answer = false;
        }
        return answer;
    }
}