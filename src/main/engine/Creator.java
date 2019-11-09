package engine;

import engine.exceptions.AlgorithmException;

import javax.crypto.Cipher;
import java.security.Key;

public class Creator implements CipherCreator {
    public Encryptor createEncryptor(String algorithm, String mode, String key) throws AlgorithmException{
        //TODO null & arguments checking
        //TODO more algorithms and modes to implement
        //TODO possibly more elegant way of parsing arguments
        switch (algorithm){
            case "AES":{
                switch(mode){
                    case "CBC":
                        return new AES(mode, key, Cipher.ENCRYPT_MODE);
                    case "ECB":

                    default:
                        //TODO suggest more suiting exception type or message
                        throw new AlgorithmException("Not supported mode!");
                }
            }
            case "DES":{
                switch(mode){
                    case "CBC":
                        break;
                    case "ECB":
                        break;
                    case "CFB":
                        break;
                }
            }
            default:{
                //TODO suggest more suiting exception type or message
                throw new AlgorithmException("Not supported algorithm!");
            }

        }
    }

    public Decryptor createDecryptor(String algorithm, String mode, String key) throws AlgorithmException{
        //TODO decryption
        throw new UnsupportedOperationException();
    }
}
