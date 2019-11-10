package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    private Creator creator;

    @BeforeEach
    void setUp() {
        creator = new Creator();
    }

    @Test
    void createEncryptor_InvalidAlgorithm_ShouldThrowException() {
        //given
        String algorithm = "Lorem ipsum";
        String mode = "CBC";
        String key = "123";

        //when & then
        assertThrows(NoSuchAlgorithmException.class, () -> creator.createEncryptor(algorithm, mode, key), "Didn't throw NoSuchAlgorithmException");
    }

    @Test
    void createEncryptor_InvalidAlgorithmMode_ShouldThrowException() {
        //given
        List<String> algorithms = new ArrayList<>(Arrays.asList("DES", "AES"));
        String mode = "Lorem ipsum";
        String key = "123";

        //when & then
        for (String algorithm : algorithms) {
            assertThrows(AlgorithmException.class, () -> creator.createEncryptor(algorithm, mode, key), "Algorithm " + algorithm + "didn't throw AlgorithmException");
        }
    }

    private Encryptor createEncryptor_ValidData_HelperMethod(String algoName, String mode, String key, int operationMode) {
        //when
        Encryptor result = null;
        try {
            result = creator.createEncryptor(algoName, mode, key);
        } catch (AlgorithmException | NoSuchAlgorithmException e) {
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getOperationMode(), operationMode, "The operation mode is incorrect.");
        assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
        assertEquals(result.getKey(), key, "The keys do not match.");

        return result;
    }

    @Test
    void createEncryptor_AesCbc_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "CBC";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_AesEcb_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "ECB";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_AesCfb_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "CFB";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_DesCbc_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "CBC";
        //String key = new String(new char[16]).replace('\0', 'a');
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_DesEcb_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "ECB";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_DesCfb_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "CFB";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    private Decryptor createDecryptor_ValidData_HelperMethod(String algoName, String mode, String key, int operationMode) {
        //when
        Decryptor result = null;
        try {
            result = creator.createDecryptor(algoName, mode, key);
        } catch (AlgorithmException | NoSuchAlgorithmException e) {
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getOperationMode(), operationMode, "The operation mode is incorrect.");
        assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
        assertEquals(result.getKey(), key, "The keys do not match.");

        return result;
    }

    @Test
    void createDecryptor_AesCbc_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "CBC";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_AesEcb_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "ECB";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_AesCfb_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "CFB";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_DesCbc_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "CBC";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_DesEcb_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "ECB";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_DesCfb_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        String mode = "CFB";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, mode, key, operationMode);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }
}
