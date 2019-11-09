package engine;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AES implements Encryptor, Decryptor {
    private String algorithmName;
    private String algorithmMode;
    private int operationMode;
    private Key key;

    private Cipher cipher;

    public AES(String mode, Key key, int operationMode){
        //TODO null & arguments checking
        this.algorithmName = "AES";
        this.algorithmMode = mode;
        this.key = key;
        this.operationMode = operationMode;

        try{
            this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
            this.cipher.init(this.operationMode, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e){
            //TODO suggest more suiting exception type or message
            throw new IllegalStateException("Unable to create a cipher object.");
        }
    }

    public byte[] encrypt(byte[] data){
        if(operationMode == Cipher.DECRYPT_MODE){
            throw new IllegalStateException("Cannot use Cipher in decryption mode to encrypt data.");
        }

        //TODO null checking
        try{
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e){
            //TODO suggest more suiting exception type or message
            throw new IllegalStateException("Cannot encrypt given data.");
        }
    }

    public byte[] decrypt(byte[] data){
        if(operationMode == Cipher.ENCRYPT_MODE){
            throw new IllegalStateException("Cannot use Cipher in encryption mode to decrypt data.");
        }

        //TODO decryption
        throw new UnsupportedOperationException();
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

    public int getOperationMode(){
        return operationMode;
    }
}
