package Client.Model;

import java.io.Serializable;

/**
 * Created by Adrian on 15.04.2017.
 */
public abstract class Cartela implements Serializable{
    protected long id;
    private String message = new String ();

    protected abstract void setId();

    public  long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
