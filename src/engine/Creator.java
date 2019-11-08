package engine;

import engine.exceptions.AlgorithmException;
import java.security.Key;

public class Creator implements CipherCreator {
    public Encryptor createEncryptor(String algorithm, String mode, Key key) throws AlgorithmException{
        //TODO null checking
        //TODO more algorithms and modes
        //TODO possibly more elegant way of parsing arguments
        switch (algorithm){
            case "AES":{
                switch(mode){
                    case "CBC":
                        return new AES(mode, key);
                    default:
                        throw new AlgorithmException("Not supported mode!");
                }
            }
            default:{
                throw new AlgorithmException("Not supported algorithm!");
            }

        }
    }
    //TODO decryption
    public Decryptor createDecryptor(String algorithm, String mode, Key key) throws AlgorithmException{
        throw new UnsupportedOperationException();
    }
}
