package sample;


import java.io.UnsupportedEncodingException;

/**
 * Created by mohammedissa on 12/2/16.
 */
public class Converter {


    public static void main(String[] args){


        try {
            System.out.println(new String("\u1571\u1588".getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
