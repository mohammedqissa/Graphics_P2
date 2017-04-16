package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammedissa on 11/24/16.
 */
public class Language {


    String langName;
    boolean rightToLeft;
    List<Letter> letters;

    public Language(String langName, boolean rightToLeft){
        this.langName=langName;
        this.rightToLeft=rightToLeft;
        letters = new ArrayList<>();
    }


}
