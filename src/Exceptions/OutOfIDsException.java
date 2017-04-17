package Exceptions;

/**
 * Created by Adrian on 15.04.2017.
 */
public class OutOfIDsException extends Exception {
    public OutOfIDsException(){}
    public OutOfIDsException(String message){
        super(message);
    }

}
