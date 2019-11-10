package engine;

public interface Encryptor {
    byte[] encrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    String getKey();
    byte[] getIv();
    int getOperationMode();
}
