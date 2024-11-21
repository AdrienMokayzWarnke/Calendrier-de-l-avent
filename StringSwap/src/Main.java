import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String A = "abcdefghijklm";
        String B = "abcjefghidklm";
        System.out.println(isSwap(A,B));
    }

    public static boolean isSwap(String a, String b) {
        boolean canSwamp = false;

        if(a.equals(b))
            return true;

        byte[] aC = a.getBytes();
        byte[] bC = b.getBytes();
        //sort arrays
        Arrays.sort(aC);
        Arrays.sort(bC);

        //check same chars are in both strings
        if(!Arrays.equals(aC, bC))
            return false;

        //check if a swap is possible when we find a different char
        for(int i = 0;i<a.length();i++){
            //are the char the same ?
            if(a.charAt(i)!=b.charAt(i)){
                //if not check the swap
                int indexCharDiff = b.indexOf(a.charAt(i));
                if(swap(a,i,indexCharDiff).equals(b)){
                    canSwamp = true;
                }
            }
        }

        return canSwamp;
    }

    public static String swap(String s, int i, int j) {
        StringBuilder strB = new StringBuilder(s);
        char l = strB.charAt(i);
        char r = strB.charAt(j);
        strB.setCharAt(i, r);
        strB.setCharAt(j, l);
        return strB.toString();
    }
}