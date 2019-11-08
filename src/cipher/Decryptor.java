package cipher;

public interface Decryptor {
    byte[] decrypt(byte[] data);

    String getAlgorithmName();
    String getAlgorithmMode();
    byte[] getKey();
}
