package Client.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adrian on 13.05.2017.
 */
public class AbonamentsTimer {

    private static ArrayList<ValidationPair> validationPairs = new ArrayList<>();

    public static void addValidationPair(ValidationPair vp){
        validationPairs.add(vp);
    }

    public static int checkAbonament(Abonament abonament){
        for (ValidationPair x: validationPairs){
            if (x.getAbonament().getId() == abonament.getId())
                return validationPairs.indexOf(x);
        }
        return -1;
    }

    public synchronized static void refreshEntries (){
        Date currDate = new Date();
        if (validationPairs.size() > 0)
            for (ValidationPair vp: validationPairs){
                if (currDate.after(vp.getDate())){
                    validationPairs.remove(vp);
                }
            }
    }






}
