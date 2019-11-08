package engine;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AES implements Encryptor {
    private String algorithmName;
    private String algorithmMode;
    private Key key;

    private Cipher cipher;

    public AES(String mode, Key key){
        //TODO null & arguments checking
        algorithmName = "AES";
        algorithmMode = mode;
        this.key = key;

        try{
            this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
            this.cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e){
            //TODO suggest more suiting exception type or message
            throw new IllegalStateException("Unable to create a cipher object.");
        }
    }

    public byte[] encrypt(byte[] data){
        //TODO null checking
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
