package engine;

import java.security.Key;

public interface Encryptor {
    byte[] encrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    Key getKey();
}
