package ch.epfl.sweng.swissaffinity.utilities;

/**
 * Created by Joel on 10/28/2015.
 */
public class DeepCopyNotSupportedException extends Exception {
    public DeepCopyNotSupportedException(){
        super();
    }
    public DeepCopyNotSupportedException(Exception e){
        super(e);
    }
    public DeepCopyNotSupportedException(String message){
        super(message);
    }

}
