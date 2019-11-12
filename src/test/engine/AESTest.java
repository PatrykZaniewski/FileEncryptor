package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {

    private AES createAesInstance_ValidData_HelperMethod(String mode, String key, byte[] iv, int operationMode) {
        AES aes = null;
        try {
            aes = new AES(mode, key, iv, operationMode);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }
        return aes;
    }

    @Test
    void testInitialization_ValidData_ShouldFillFieldsCorrectly() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        AES aes = createAesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);

        //then
        assertEquals(aes.getAlgorithmName(), "AES", "The algorithmName value is incorrect.");
        assertEquals(aes.getAlgorithmMode(), mode, "The algorithmMode value is incorrect.");
        assertEquals(aes.getKey(), key, "The key value is incorrect");
        assertNotNull(aes.getIv(), "The IV parameter is null");
        assertEquals(aes.getOperationMode(), operationMode, "The operationMode value is incorrect.");
    }

    @Test
    void testInitialization_NullMode_ShouldThrowException() {
        //given
        String mode = null;
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        AES aes = null;

        //then
        assertThrows(IllegalArgumentException.class, () -> new AES(mode, key, iv, operationMode), "The class didn't throw an IllegalArgumentException indicating that it recognized a mode is null.");
    }

    @Test
    void testInitialization_NullKey_ShouldThrowException() {
        //given
        String mode = "CBC";
        String key = null;
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        AES aes = null;

        //then
        assertThrows(IllegalArgumentException.class, () -> new AES(mode, key, iv, operationMode), "The class didn't throw an IllegalArgumentException indicating that it recognized a key is null.");
    }

    @Test
    void testInitialization_InvalidOperationMode_ShouldThrowException() {
        //given
        String mode = "AES";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = -123;

        //when
        AES aes = null;

        //then
        assertThrows(AlgorithmException.class, () -> new AES(mode, key, iv, operationMode), "The class didn't throw an AlgorithmException indicating that it recognized an operationMode is null.");
    }

    @Test
    void testEncrypt_ValidData_ShouldReturnEncryptedData() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        byte[] plainData = "alamakota".getBytes(StandardCharsets.UTF_8);

        //when
        AES aes = createAesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] encryptedData = null;
        try {
            encryptedData = aes.encrypt(plainData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        //then
        assertNotNull(aes.encrypt(plainData), "The return object is null even though the data is correct.");
        assertNotEquals(encryptedData.length, 0, "The encrypted data vector is empty.");
    }

    @Test
    void testDecrypt_ValidData_ShouldReturnDecryptedData() throws AlgorithmException {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        byte[] plainData = "alamakota".getBytes(StandardCharsets.UTF_8);
        AES aes = createAesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] encryptedData = null;
        try {
            encryptedData = aes.encrypt(plainData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        operationMode = Cipher.DECRYPT_MODE;

        //when
        aes = createAesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] decryptedData = null;
        try {
            decryptedData = aes.decrypt(encryptedData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        //then
        assertNotNull(aes.decrypt(encryptedData), "The return object is null even though the data is correct.");
        assertNotEquals(decryptedData.length, 0, "The decrypted data vector is empty.");
    }

    @Test
    void testEncryptDecrypt_ValidData_ShouldDecryptSelfEncryptedData() {

    }
}