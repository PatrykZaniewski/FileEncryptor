package engine;

import java.security.Key;

public interface Decryptor {
    byte[] decrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    Key getKey();
}
