package engine;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

public class DES implements Encryptor, Decryptor {
    private String algorithmName;
    private String algorithmMode;
    private int operationMode;
    private SecretKeySpec secretKey;
    private String key;


    private Cipher cipher;

    public DES(String mode, String key, int operationMode){
        //TODO null & arguments checking
        this.algorithmName = "DES";
        this.algorithmMode = mode;
        this.key = key;
        this.operationMode = operationMode;
        setKey(key);
        try{
            //TODO java.lang.IllegalStateException: Unable to create a cipher object.
            this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
            System.out.println("XDDDDDDDDD");
            if ("ECB".equals(algorithmMode)) {
                this.cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            else
            {
                int ivSize = 8;
                byte[] iv = new byte[ivSize];
                SecureRandom random = new SecureRandom();
                random.nextBytes(iv);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                this.cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e){
            //TODO suggest more suiting exception type or message
            throw new IllegalStateException("Unable to create a cipher object.");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private void setKey(String myKey)
    {
        MessageDigest sha = null;
        byte[] keyByte = null;
        try {
            keyByte = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            keyByte = sha.digest(keyByte);
            keyByte = Arrays.copyOf(keyByte, 8);
            secretKey = new SecretKeySpec(keyByte, "DES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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

    public String getKey(){
        return key;
    }

    public int getOperationMode(){
        return operationMode;
    }
}
