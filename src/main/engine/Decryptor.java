package engine;

import engine.exceptions.AlgorithmException;

public interface Decryptor {
    byte[] decrypt(byte[] data) throws AlgorithmException;

    String getAlgorithmName();

    String getAlgorithmMode();

    String getKey();

    byte[] getIv();

    int getOperationMode();
}
