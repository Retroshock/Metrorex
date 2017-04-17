package Model;

import Exceptions.OutOfIDsException;

import java.util.Calendar;
import java.util.Date;
import java.time.*;

/**
 * Created by Adrian on 15.04.2017.
 */
public class Abonament extends Cartela{

    private Type tip;
    private Valabilitate valabilitate;

    Abonament(Date start, Date end){
        valabilitate = new Valabilitate(start, end);
        int diffInDays =(int)( ( valabilitate.getEnd().getTime() - valabilitate.getStart().getTime() ) / (1000 * 60 * 60 * 24) );
        if (diffInDays > 1)
            tip = Type.Month;
        else
            tip = Type.Day;
        setId();
    }

    public Type getTip() {
        return tip;
    }

    public Valabilitate getValabilitate() {
        return valabilitate;
    }

    @Override
    protected void setId() {
        try {
            id = IDCounters.getID(tip);
        } catch (OutOfIDsException e) {
            e.printStackTrace();
        }

    }
}
