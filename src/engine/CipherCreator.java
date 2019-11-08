package engine;

import engine.exceptions.AlgorithmException;
import java.security.Key;

public interface CipherCreator {
    Encryptor createEncryptor(String algorithm, String mode, Key key) throws AlgorithmException;
    Decryptor createDecryptor(String algorithm, String mode, Key key) throws AlgorithmException;
}
