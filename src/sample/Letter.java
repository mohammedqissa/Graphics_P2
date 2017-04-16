package sample;


/**
 * Created by mohammedissa on 11/21/16.
 */
public class Letter implements Comparable<Letter>{


    char aChar;
    byte[][][] cases ;
    boolean case1,case2,case3;



    public Letter(char aChar ){
        cases = new byte[3][16][16];
        this.aChar=aChar;
    }



    public String toString(){
        String s1="",s2="",s3="";

        for (int i = 0; i <16 ; i++) {
            for (int j = 0; j <16 ; j++) {
                s1+=cases[0][j][i];
                s2+=cases[1][j][i];
                s3+=cases[2][j][i];
            }

        }


        return aChar+":"+s1+'$'+s2+"$"+s3;

    }


    @Override
    public int compareTo(Letter o) {
        return this.aChar-o.aChar;
    }
}
