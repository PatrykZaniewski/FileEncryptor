package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    private Creator creator;

    @BeforeEach
    void setUp() {
        creator = new Creator();
    }

    //ENCRYPTOR

    @Test
    void createEncryptor_InvalidAlgorithm_ShouldThrowException() {
        //given
        String algorithm = "Lorem ipsum";

        int shift = 1;

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createEncryptor() didn't throw AlgorithmException while algorithm is unknown";

        assertThrows(AlgorithmException.class, () -> creator.createEncryptor(algorithm, shift), errorMessage);
        assertThrows(AlgorithmException.class, () -> creator.createEncryptor(algorithm, mode, key, iv), errorMessage);
    }

    @Test
    void createEncryptor_NullAlgorithm_ShouldThrowException() {
        //given
        String algorithm = null;

        int shift = 1;

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createEncryptor() didn't throw IllegalArgumentException while algorithm is null";
        assertThrows(IllegalArgumentException.class, () -> creator.createEncryptor(algorithm, shift), errorMessage);
        assertThrows(IllegalArgumentException.class, () -> creator.createEncryptor(algorithm, mode, key, iv), errorMessage);
    }

    @Test
    void createEncryptor_NullData_ShouldThrowException() {
        //given
        String algorithm = "AES";

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createEncryptor(mode) didn't throw IllegalArgumentException while mode is null";

        assertThrows(IllegalArgumentException.class, () -> creator.createEncryptor(algorithm, null, key, iv), errorMessage);
        assertThrows(IllegalArgumentException.class, () -> creator.createEncryptor(algorithm, mode, null, iv), errorMessage);
    }

    private Encryptor createEncryptor_ValidData_HelperMethod(String algoName, byte[] iv) {
        String mode = "CBC";
        String key = "123";
        int operationMode = Cipher.ENCRYPT_MODE;

        //when
        Encryptor result = null;
        try {
            result = creator.createEncryptor(algoName, mode, key, iv);
        } catch (AlgorithmException e) {
            e.printStackTrace();
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getAlgorithmName(), algoName, "The algorithm name is incorrect.");
        assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
        assertEquals(result.getKey(), key, "The keys do not match.");
        assertArrayEquals(result.getIv(), iv, "The IVs do not match.");
        assertEquals(result.getOperationMode(), operationMode, "The operation mode is incorrect.");

        return result;
    }

    private Encryptor createEncryptor_ValidData_HelperMethod(String algoName, int shift) {
        //when
        Encryptor result = null;
        try {
            result = creator.createEncryptor(algoName, shift);
        } catch (AlgorithmException e) {
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getAlgorithmName(), "ROT", "The algorithm name is incorrect.");
        assertEquals(result.getAlgorithmMode(), "ROT cipher object does not have modes.", "The encryption modes do not match.");
        assertEquals(result.getKey(), "" + shift, "The keys do not match.");
        assertNull(result.getIv(), "IV value is not null even though the algorithm doesn't use this variable.");
        assertEquals(result.getOperationMode(), Cipher.ENCRYPT_MODE, "The operation mode is incorrect.");

        return result;
    }

    @Test
    void createEncryptor_Rot_ShouldReturnRotInstance() {
        //given
        String algorithm = "ROT";
        int shift = 1;

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, shift);
        assertTrue(result instanceof ROT, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_Aes_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        byte[] iv = new byte[16];

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_Des_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        byte[] iv = new byte[8];

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_Rc2_ShouldReturnRc2Instance() {
        //given
        String algorithm = "RC2";
        byte[] iv = new byte[8];

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof RC2, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createEncryptor_Blowfish_ShouldReturnBlowfishInstance() {
        //given
        String algorithm = "Blowfish";
        byte[] iv = new byte[8];

        //when & then
        Encryptor result = createEncryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof Blowfish, "The algorithm is not an instance of " + algorithm + ".");
    }

    // DECRYPTOR

    @Test
    void createDecryptor_InvalidAlgorithm_ShouldThrowException() {
        //given
        String algorithm = "Lorem ipsum";

        int shift = 1;

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createDecryptor() didn't throw AlgorithmException while algorithm is unknown";

        assertThrows(AlgorithmException.class, () -> creator.createDecryptor(algorithm, shift), errorMessage);
        assertThrows(AlgorithmException.class, () -> creator.createDecryptor(algorithm, mode, key, iv), errorMessage);
    }

    @Test
    void createDecryptor_NullAlgorithm_ShouldThrowException() {
        //given
        String algorithm = null;

        int shift = 1;

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createDecryptor() didn't throw IllegalArgumentException while algorithm is null";
        assertThrows(IllegalArgumentException.class, () -> creator.createDecryptor(algorithm, shift), errorMessage);
        assertThrows(IllegalArgumentException.class, () -> creator.createDecryptor(algorithm, mode, key, iv), errorMessage);
    }

    @Test
    void createDecryptor_NullData_ShouldThrowException() {
        //given
        String algorithm = "AES";

        String mode = "CBC";
        String key = "123";
        byte[] iv = "iviviv".getBytes(StandardCharsets.UTF_8);

        //when & then
        String errorMessage = "Method createDecryptor(mode) didn't throw IllegalArgumentException while mode is null";

        assertThrows(IllegalArgumentException.class, () -> creator.createDecryptor(algorithm, null, key, iv), errorMessage);
        assertThrows(IllegalArgumentException.class, () -> creator.createDecryptor(algorithm, mode, null, iv), errorMessage);
    }

    private Decryptor createDecryptor_ValidData_HelperMethod(String algoName, byte[] iv) {
        //when
        String mode = "CBC";
        String key = "123";
        int operationMode = Cipher.DECRYPT_MODE;

        Decryptor result = null;
        try {
            result = creator.createDecryptor(algoName, mode, key, iv);
        } catch (AlgorithmException e) {
            e.printStackTrace();
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getAlgorithmName(), algoName, "The algorithm name is incorrect.");
        assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
        assertEquals(result.getKey(), key, "The keys do not match.");
        assertArrayEquals(result.getIv(), iv, "The IVs do not match");
        assertEquals(result.getOperationMode(), operationMode, "The operation mode is incorrect.");

        return result;
    }

    private Decryptor createDecryptor_ValidData_HelperMethod(String algoName, int shift) {
        //when
        Decryptor result = null;
        try {
            result = creator.createDecryptor(algoName, shift);
        } catch (AlgorithmException e) {
            e.printStackTrace();
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertEquals(result.getAlgorithmName(), "ROT", "The algorithm name is incorrect.");
        assertEquals(result.getAlgorithmMode(), "ROT cipher object does not have modes.", "The encryption modes do not match.");
        assertEquals(result.getKey(), "" + shift, "The keys do not match.");
        assertNull(result.getIv(), "IV value is not null even though the algorithm doesn't use this variable.");
        assertEquals(result.getOperationMode(), Cipher.DECRYPT_MODE, "The operation mode is incorrect.");

        return result;
    }

    @Test
    void createDecryptor_Rot_ShouldReturnRotInstance() {
        //given
        String algorithm = "ROT";
        int shift = 1;

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, shift);
        assertTrue(result instanceof ROT, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_Aes_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        byte[] iv = new byte[16];

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof AES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_Des_ShouldReturnDesInstance() {
        //given
        String algorithm = "DES";
        byte[] iv = new byte[8];

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof DES, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_Rc2_ShouldReturnRc2Instance() {
        //given
        String algorithm = "RC2";
        byte[] iv = new byte[8];

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof RC2, "The algorithm is not an instance of " + algorithm + ".");
    }

    @Test
    void createDecryptor_Blowfish_ShouldReturnBlowfishInstance() {
        //given
        String algorithm = "Blowfish";
        byte[] iv = new byte[8];

        //when & then
        Decryptor result = createDecryptor_ValidData_HelperMethod(algorithm, iv);
        assertTrue(result instanceof Blowfish, "The algorithm is not an instance of " + algorithm + ".");
    }
}
