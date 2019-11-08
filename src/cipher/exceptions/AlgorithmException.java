package cipher.exceptions;
/*
Throw when unsupported algorithm or mode is passed
 */
public class AlgorithmException extends Exception {
    public AlgorithmException(String message){
        super(message);
    }
}
