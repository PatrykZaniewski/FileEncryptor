package engine;

import engine.exceptions.AlgorithmException;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

public class Creator implements CipherCreator {
    public Encryptor createEncryptor(String algorithm, String mode, String key) throws AlgorithmException, NoSuchAlgorithmException {
        //TODO null & arguments checking
        //TODO more algorithms and modes to implement
        //TODO possibly more elegant way of parsing arguments
        switch (algorithm){
            case "AES":{
                switch(mode){
                    case "CBC":
                        return new AES(mode, key, Cipher.ENCRYPT_MODE);
                    case "ECB":
                        return new AES(mode, key, Cipher.ENCRYPT_MODE);
                    case "CFB":
                        return new AES(mode, key, Cipher.ENCRYPT_MODE);
                    default:
                        throw new AlgorithmException("Not supported mode!");
                }
            }
            case "DES":{
                switch(mode){
                    case "CBC":
                        return new DES(mode, key, Cipher.ENCRYPT_MODE);
                    case "ECB":
                        return new DES(mode, key, Cipher.ENCRYPT_MODE);
                    case "CFB":
                        return new DES(mode, key, Cipher.ENCRYPT_MODE);
                    default:
                        throw new AlgorithmException("Not supported mode!");
                }
            }
            default:{
                throw new NoSuchAlgorithmException("Not supported algorithm!");
            }

        }
    }

    public Decryptor createDecryptor(String algorithm, String mode, String key) throws AlgorithmException{
        //TODO decryption
        throw new UnsupportedOperationException();
    }
}
