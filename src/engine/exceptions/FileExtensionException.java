package engine.exceptions;
/*
Accept only *.rsa files
 */
public class FileExtensionException extends Exception {
    public FileExtensionException(String message){
        super(message);
    }
}
