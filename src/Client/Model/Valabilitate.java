package Client.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Adrian on 15.04.2017.
 */
public class Valabilitate implements Serializable {
    private Date start, end;

    public Valabilitate(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public boolean isValid(Date data){
        if (data.before(end) && data.after(start))
            return true;
        return false;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
