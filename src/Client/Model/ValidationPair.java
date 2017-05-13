package Client.Model;

import java.util.Date;

/**
 * Created by Adrian on 13.05.2017.
 */
public class ValidationPair {

    private Abonament abonament;
    private Date date;

    public ValidationPair(Abonament abonament, Date date) {
        this.abonament = abonament;
        this.date = date;
    }

    public Abonament getAbonament() {
        return abonament;
    }

    public void setAbonament(Abonament abonament) {
        this.abonament = abonament;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
