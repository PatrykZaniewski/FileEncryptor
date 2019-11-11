package engine;

import engine.exceptions.AlgorithmException;

import java.security.NoSuchAlgorithmException;

public interface CipherCreator {
    public Encryptor createEncryptor(String algorithm, int shift) throws AlgorithmException;
    Encryptor createEncryptor(String algorithm, String mode, String key, byte[] iv) throws AlgorithmException;

    Decryptor createDecryptor(String algorithm, String mode, String key, byte[] iv) throws AlgorithmException;
}
