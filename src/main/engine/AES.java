package engine;

import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

public class AES implements Encryptor, Decryptor {
    private final String algorithmName = "AES";
    private String algorithmMode;
    private Key secretKey;
    private String key;
    private byte[] iv;
    private int operationMode;

    private Cipher cipher;

    private final String[] supportedModesArray = {"ECB", "CBC", "CFB"};

    public AES(String mode, String key, byte[] iv, int operationMode) {
        // "iv" argument is checked for null only if mode=="CBC"
        if (mode == null || key == null) {
            throw new IllegalArgumentException("Null value has been passed.");
        }
        if (operationMode != Cipher.DECRYPT_MODE && operationMode != Cipher.ENCRYPT_MODE) {
            throw new IllegalArgumentException("Unsupported operation mode.");
        }
        if (((mode.equals("CBC") || mode.equals("CFB")) && operationMode == Cipher.DECRYPT_MODE) && (iv == null || iv.length != 16)) {
            throw new IllegalArgumentException("AES CBC/CFB Decryption requires 16-bit iv vector.");
        }
        if ((mode.equals("CBC") || mode.equals("CFB")) && operationMode == Cipher.ENCRYPT_MODE && iv != null) {
            throw new IllegalArgumentException("AES CBC/CFB Encryption does not accept non-null value in IV.");
        }
        List<String> supportedModes = Arrays.asList(supportedModesArray);
        if (!supportedModes.contains(mode)) {
            throw new IllegalArgumentException("Unsupported mode.");
        }

        this.algorithmMode = mode;
        this.key = key;
        setSecretKey(this.key);
        this.iv = iv;
        this.operationMode = operationMode;

        switch (operationMode) {
            case 1: { // 1 stands for Cipher.ENCRYPT_MODE
                try {
                    if ("CBC".equals(mode) || mode.equals("CFB")) {
                        this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
                        byte[] byteIv = new byte[16];
                        SecureRandom random = new SecureRandom();
                        random.nextBytes(byteIv);
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(byteIv);

                        this.cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

                        this.iv = ivParameterSpec.getIV();
                    } else {
                        this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
                        this.cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    }
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Unable to create a cipher object.");
                }
                break;
            }
            case 2: { // 2 stands for Cipher.DECRYPT_MODE
                try {
                    if (mode.equals("CBC") || mode.equals("CFB")) {
                        this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv);
                        this.cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

                        this.iv = ivParameterSpec.getIV();
                    } else {
                        this.cipher = Cipher.getInstance(algorithmName + "/" + mode + "/PKCS5Padding");
                        this.cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    }
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Unable to create a cipher object.");
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported operation mode.");
        }
    }

    public byte[] encrypt(byte[] data) {
        if (operationMode == Cipher.DECRYPT_MODE) {
            throw new IllegalStateException("Cannot use Cipher in decryption mode to encrypt data.");
        }
        if (data == null) {
            return null;
        }

        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot encrypt given data.");
        }
    }

    public byte[] decrypt(byte[] data) {
        if (operationMode == Cipher.ENCRYPT_MODE) {
            throw new IllegalStateException("Cannot use Cipher in encryption mode to decrypt data.");
        }
        if (data == null) {
            return null;
        }

        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot decrypt given data.");
        }
    }

    private void setSecretKey(String myKey) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secRandom = new SecureRandom(myKey.getBytes());
            keyGen.init(secRandom);
            Key key = keyGen.generateKey();
            this.secretKey = key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot generate a secret key.");
        }
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public String getAlgorithmMode() {
        return algorithmMode;
    }

    public String getKey() {
        return key;
    }

    public byte[] getIv() {
        return iv;
    }

    public int getOperationMode() {
        return operationMode;
    }
}
