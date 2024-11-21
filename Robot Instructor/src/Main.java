import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String orders = "up up down down down down right left right left left";

        List<String> orderList = Arrays.asList(orders.split(" "));

        String answer = orderList.stream()
                        .map(order -> switch (order) {
                            case "up" -> "^";
                            case "down" -> "v";
                            case "left" -> "<";
                            case "right" -> ">";
                            default -> "";
                        }).reduce((s1, s2) -> {

                             /* case 1 : last char of s1 is the first char of s2, example : s1 = "<^", s2 = "^"
                             then we add a "2" to s1 */
                            if(s1.charAt(s1.length()-1)==s2.charAt(0)) {
                                return s1.concat("2");
                            }

                            /*case 2 : the last char of s1 is a digit : [1,2,...,9] AND
                              the second to last char of s1 is the first char of s2, example : s1 = "<2", s2 = "<"
                              then we remove the last digit of s1 and replace it with the digit+1 (2 -> 3 -> 4 etc...)*/
                            if (isDigit(s1.charAt(s1.length()-1)) &&
                                s1.charAt(s1.length()-2)==s2.charAt(0)) {
                                return s1.substring(0,s1.length()-1)
                                        .concat(upTickChar(s1.charAt(s1.length()-1)));
                            }

                            return s1.concat(s2);

                        }).orElseGet(() -> "");
        System.out.println(answer);
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }
    public static String upTickChar(char c){
        return Integer.toString(Character.getNumericValue(c)+1);
    }
}