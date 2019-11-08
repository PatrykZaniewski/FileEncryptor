package cipher;

public interface Encryptor {
    byte[] encrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    byte[] getKey();
}
