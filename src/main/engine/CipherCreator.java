package engine;

import engine.exceptions.AlgorithmException;

import java.security.NoSuchAlgorithmException;

public interface CipherCreator {
    Encryptor createEncryptor(String algorithm, String mode, String key) throws AlgorithmException, NoSuchAlgorithmException;

    Decryptor createDecryptor(String algorithm, String mode, String key) throws AlgorithmException, NoSuchAlgorithmException;
}
