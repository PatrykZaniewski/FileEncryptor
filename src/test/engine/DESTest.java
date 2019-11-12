package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class DESTest {

    private DES createDesInstance_ValidData_HelperMethod(String mode, String key, byte[] iv, int operationMode) {
        DES des = null;
        try {
            des = new DES(mode, key, iv, operationMode);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }
        return des;
    }

    @Test
    void testInitialization_ValidData_ShouldFillFieldsCorrectly() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        DES des = createDesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);

        //then
        assertEquals(des.getAlgorithmName(), "DES", "The algorithmName value is incorrect.");
        assertEquals(des.getAlgorithmMode(), mode, "The algorithmMode value is incorrect.");
        assertEquals(des.getKey(), key, "The key value is incorrect");
        assertNotNull(des.getIv(), "The IV parameter is null");
        assertEquals(des.getOperationMode(), operationMode, "The operationMode value is incorrect.");
    }

    @Test
    void testInitialization_NullMode_ShouldThrowException() {
        //given
        String mode = null;
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        DES des = null;

        //then
        assertThrows(IllegalArgumentException.class, () -> new DES(mode, key, iv, operationMode), "The class didn't throw an IllegalArgumentException indicating that it recognized a mode is null.");
    }

    @Test
    void testInitialization_NullKey_ShouldThrowException() {
        //given
        String mode = "CBC";
        String key = null;
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        DES des = null;

        //then
        assertThrows(IllegalArgumentException.class, () -> new DES(mode, key, iv, operationMode), "The class didn't throw an IllegalArgumentException indicating that it recognized a key is null.");
    }

    @Test
    void testInitialization_InvalidOperationMode_ShouldThrowException() {
        //given
        String mode = "DES";
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = -123;

        //when
        DES des = null;

        //then
        assertThrows(AlgorithmException.class, () -> new DES(mode, key, iv, operationMode), "The class didn't throw an AlgorithmException indicating that it recognized an operationMode is null.");
    }

    @Test
    void testEncrypt_ValidData_ShouldReturnEncryptedData() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        byte[] plainData = "alamakota".getBytes(StandardCharsets.UTF_8);

        //when
        DES des = createDesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] encryptedData = null;
        try {
            encryptedData = des.encrypt(plainData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        //then
        assertNotNull(des.encrypt(plainData), "The return object is null even though the data is correct.");
        assertNotEquals(encryptedData.length, 0, "The encrypted data vector is empty.");
    }

    @Test
    void testSuccessDecrypt() throws AlgorithmException {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        byte[] plainData = "alamakota".getBytes(StandardCharsets.UTF_8);
        DES des = createDesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] encryptedData = null;
        try {
            encryptedData = des.encrypt(plainData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        operationMode = Cipher.DECRYPT_MODE;

        //when
        des = createDesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] decryptedData = null;
        try {
            decryptedData = des.decrypt(encryptedData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        //then
        assertNotNull(des.decrypt(encryptedData), "The return object is null even though the data is correct.");
        assertNotEquals(decryptedData.length, 0, "The decrypted data vector is empty.");
        assertEquals(decryptedData.toString(), plainData.toString(), "Decrypted message isn not equal to encrypted.");
    }

    @Test
    void testDecryptionWithIncorrectKey_ShouldThrowException() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[8];
        int operationMode = Cipher.ENCRYPT_MODE;

        byte[] plainData = "alamakota".getBytes(StandardCharsets.UTF_8);
        DES des = createDesInstance_ValidData_HelperMethod(mode, key, iv, operationMode);
        byte[] encryptedData = null;
        try {
            encryptedData = des.encrypt(plainData);
        } catch (Exception e) {
            fail("Exception thrown even though all arguments are correct");
        }

        operationMode = Cipher.DECRYPT_MODE;

        //when
        String badkey = "321";
        des = createDesInstance_ValidData_HelperMethod(mode, badkey, iv, operationMode);
        DES finalDes = des;
        byte[] finalEncryptedData = encryptedData;

        //then
        assertThrows(AlgorithmException.class, () -> {
            finalDes.decrypt(finalEncryptedData);
        });
    }

    @Test
    void testDecrypt_ValidData_ShouldReturnDecryptedData() {
    }

    @Test
    void decrypt() {
    }
}