package cn.deliver.water.util;
import java.util.Random;

public class RandomUtils {
//    public static void main(String[] args) {

//    }
    public static String Random(){
//        for (int i = 0; i < 1; i++) {
//
//        }
        int maxNum = 36;
        int i ;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        while (count<8){
            i = Math.abs(random.nextInt(maxNum));
            if (i>0 && i<str.length){
                code.append(str[i]);
                count++;
            }
        }
        return code.toString();
    }
    public static String RandomMath(){
        int maxNum = 36;
        int i ;
        int count = 0;
        char[] str = { '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        while (count<6){
            i = Math.abs(random.nextInt(maxNum));
            if (i>0 && i<str.length){
                code.append(str[i]);
                count++;
            }
        }
        return code.toString();
    }
}
