package controller;

public interface CipherController {
    void encryptFile(String inputFile, String outputFile, String algorithm, String mode, String key);
    void decryptFile(String inputFile, String outputFile, String key);
}
