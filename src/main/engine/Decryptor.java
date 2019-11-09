package engine;

public interface Decryptor {
    byte[] decrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    String getKey();
}
