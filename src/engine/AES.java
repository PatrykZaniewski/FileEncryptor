package engine;

import engine.exceptions.AlgorithmException;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AES implements Encryptor {
    private final String algorithmName = "AES";
    private final String algorithmMode = "none";
    private Key key;

    private Cipher cipher;

    public AES(String mode, Key key){
        this.key = key;
        try{
            this.cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");
            this.cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e){
            throw new IllegalStateException("Unable to create a cipher object.");
        }
    }

    public byte[] encrypt(byte[] data){
        try{
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e){
            //TODO suggest more suiting exception type or message
            throw new IllegalStateException("Cannot encrypt given data.");
        }
    }

    public String getAlgorithmName(){
        return algorithmName;
    }

    public String getAlgorithmMode(){
        return algorithmMode;
    }
    public Key getKey(){
        return key;
    }
}
