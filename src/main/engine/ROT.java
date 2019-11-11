package engine;

import engine.exceptions.AlgorithmException;

import javax.crypto.Cipher;

public class ROT implements Decryptor, Encryptor {
    private final String algorithmName = "ROT";
    private int shift;
    private int operationMode;

    public ROT(int shift, int operationMode) throws AlgorithmException {
        if (operationMode != Cipher.ENCRYPT_MODE && operationMode != Cipher.DECRYPT_MODE) {
            throw new AlgorithmException("Unsupported operation mode.");
        }
        if (shift < -128 || shift > 127) {
            throw new AlgorithmException("Incorrect value of shift. It has to be between -128 and 127.");
        }
        if (shift == 0) {
            throw new AlgorithmException("Incorrect value of shift. Passed value is 0.");
        }

        this.shift = shift;
        this.operationMode = operationMode;
    }

    @Override
    public byte[] decrypt(byte[] data) throws AlgorithmException {
        if (operationMode == Cipher.ENCRYPT_MODE) {
            throw new IllegalStateException("Cannot use Cipher in encryption mode to decrypt data.");
        }
        if (data == null) {
            return null;
        }

        byte shiftByte = (byte) (shift & 0x7F);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] - shiftByte);
        }

        return data;
    }

    @Override
    public byte[] encrypt(byte[] data) {
        if (operationMode == Cipher.DECRYPT_MODE) {
            throw new IllegalStateException("Cannot use Cipher in decryption mode to encrypt data.");
        }
        if (data == null) {
            return null;
        }

        byte shiftByte = (byte) (shift & 0x7F);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] + shiftByte);
        }

        return data;
    }


    @Override
    public String getAlgorithmName() {
        return algorithmName;
    }

    @Override
    public String getAlgorithmMode() {
        return "ROT cipher object does not have modes.";
    }

    @Override
    public String getKey() {
        return "" + shift;
    }

    @Override
    public byte[] getIv() {
        return null;
    }

    @Override
    public int getOperationMode() {
        return operationMode;
    }
}