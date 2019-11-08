package cipher;

import cipher.exceptions.AlgorithmException;
import cipher.exceptions.FileExtensionException;
import cipher.exceptions.FileSizeException;

public interface Cipher {
    Encryptor createEncryptor(String algorithm, String mode, String key) throws AlgorithmException;
    Decryptor createDecryptor(String algorithm, String mode, String key) throws AlgorithmException;
}
