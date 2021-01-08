package cn.deliver.water;

import org.bouncycastle.util.encoders.Base64;

public class Test2 {

    public static void main(String[] args) {
        String a = "-3";
        double money =(Double.parseDouble(a) - Double.parseDouble(a) * 2)*100;
        String new_moeny = String.valueOf(money);
String aa = "890";
String bb = aa+new_moeny;
        System.out.println(bb);
    }
}
