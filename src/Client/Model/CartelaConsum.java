package Client.Model;

import Client.Exceptions.OutOfIDsException;

/**
 * Created by Adrian on 15.04.2017.
 */
public class CartelaConsum extends Cartela {

    private Type tip;
    private int nrCalatorii;

    public CartelaConsum(int nrCalatorii){
        this.nrCalatorii = nrCalatorii;
        if (nrCalatorii == 2)
            tip = Type.Two;
        else
            tip = Type.Ten;
        setId();
    }

    public Type getTip() {
        return tip;
    }

    public int getNrCalatorii() {
        return nrCalatorii;
    }

    public void setNrCalatorii(int nrCalatorii) {
        this.nrCalatorii = nrCalatorii;
    }

    @Override
    protected void setId() {
        try {
            id = IDCounters.getID(tip);
        } catch (OutOfIDsException e) {
            e.printStackTrace();
        }
    }

    public boolean useOne (){
        if (nrCalatorii > 0){
            nrCalatorii -- ;
            return true;
        }
        else {
            return false;
        }
    }

}
