package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        assertArrayEquals(aes.getIv(), iv, "The IVs do not match");
        assertEquals(aes.getOperationMode(), operationMode, "The operationMode value is incorrect.");
    }

    @Test
    void testInitialization_NullData_ShouldThrowException() {
        //given
        String mode = "CBC";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        AES aes = null;

        //then
        String errorMessage = "The class didn't throw an IllegalArgumentException indicating that it recognized a mode is null.";
        assertThrows(IllegalArgumentException.class, () -> new AES(null, key, iv, operationMode), errorMessage);
        assertThrows(IllegalArgumentException.class, () -> new AES(mode, null, iv, operationMode), errorMessage);
    }

    @Test
    void testInitialization_InvalidMode_ShouldThrowException() {
        //given
        String mode = "Lorem ipsum";
        String key = "123";
        byte[] iv = new byte[16];
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        AES aes = null;

        //then
        String errorMessage = "The class didn't throw an IllegalArgumentException indicating that it recognized a mode is null.";
        assertThrows(AlgorithmException.class, () -> new AES(mode, key, iv, operationMode), errorMessage);
    }

    @Test
    void testInitialization_CBCNullIV_ShouldThrowException() {
        //given
        List<String> modes = new ArrayList<>(Arrays.asList("CBC", "CFB"));
        String key = "123";
        byte[] iv = null;
        int operationMode = Cipher.DECRYPT_MODE;

        //when
        AES aes = null;

        //then
        for (String mode : modes) {
            assertThrows(AlgorithmException.class, () -> new AES(mode, key, iv, operationMode), "The class didn't throw an AlgorithmException indicating that it recognized an IV vector is null.");
        }
    }

    @Test
    void testInitialization_CBCIncorrectIVLength_ShouldThrowException() {
        //given
        List<String> modes = new ArrayList<>(Arrays.asList("CBC", "CFB"));
        String key = "123";
        byte[] iv = new byte[15];
        int operationMode = Cipher.DECRYPT_MODE;

        //when
        AES aes = null;

        //then
        for (String mode : modes) {
            assertThrows(AlgorithmException.class, () -> new AES(mode, key, iv, operationMode), "The class didn't throw an ArgumentException indicating that it recognized the length of IV vector is inappropriate.");
        }
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
    void testSuccessDecrypt() throws AlgorithmException {
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
        assertArrayEquals(decryptedData, plainData, "Decrypted message is not equal to encrypted.");
    }

    @Test
    void testDecryptionWithIncorrectKey_ShouldThrowException() {
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
        String badkey = "321";
        aes = createAesInstance_ValidData_HelperMethod(mode, badkey, iv, operationMode);
        AES finalAes = aes;
        byte[] finalEncryptedData = encryptedData;

        //then
        assertThrows(AlgorithmException.class, () -> finalAes.decrypt(finalEncryptedData));
    }
}