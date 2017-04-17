package Model;

/**
 * Created by Adrian on 15.04.2017.
 */
public abstract class Cartela {
    protected long id;

    protected abstract void setId();

    public  long getId() {
        return id;
    }
}
