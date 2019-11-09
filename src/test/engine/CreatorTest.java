package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    private Creator creator;

    @BeforeEach
    void setUp() {
        creator = new Creator();
    }

    //template
    //TODO: delete before presenting the project
    void createEncryptor__Should() {
        //given
        String algorithm = "";
        String mode = "";
        String key = "";

        //when
        Encryptor result = null;
        try {
            result = creator.createEncryptor(algorithm, mode, key);
        } catch (NoSuchAlgorithmException | AlgorithmException e) {
            e.printStackTrace();
        }

        //then
        assertEquals(result.getAlgorithmMode(), algorithm, "");
        assertEquals(result.getKey(), key, "");
    }

    @Test
    void createEncryptor_InvalidAlgorithm_ShouldThrowException() {
        //given
        String algorithm = "Lorem ipsum";
        String mode = "CBC";
        String key = "123";

        //when & then
        assertThrows(NoSuchAlgorithmException.class, () -> {
            creator.createEncryptor(algorithm, mode, key);
        }, "Didn't throw NoSuchAlgorithmException");
    }

    @Test
    void createEncryptor_AesInvalidAlgorithmMode_ShouldThrowException() {
        //given
        String algorithm = "AES";
        String mode = "Lorem ipsum";
        String key = "123";

        //when & then
        assertThrows(AlgorithmException.class, () -> {
            creator.createEncryptor(algorithm, mode, key);
        }, "Didn't throw AlgorithmException");
    }

    @Test
    void createEncryptor_DesInvalidAlgorithmMode_ShouldThrowException() {
        //given
        String algorithm = "DES";
        String mode = "Lorem ipsum";
        String key = "123";

        //when & then
        assertThrows(AlgorithmException.class, () -> {
            creator.createEncryptor(algorithm, mode, key);
        }, "Didn't throw AlgorithmException");
    }

    @Test
    void createEncryptor_AesCbc_ShouldReturnAesInstance() {
        //given
        String algorithm = "AES";
        String mode = "CBC";
        String key = "123";

        //when
        Encryptor result = null;
        try {
            result = creator.createEncryptor(algorithm, mode, key);
        } catch (AlgorithmException | NoSuchAlgorithmException e) {
            fail("The method has thrown an exception while all the provided data is correct.");

            //then
            assertTrue(result instanceof AES, "The algorithms do not match.");
            assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
            assertEquals(result.getKey(), key, "The keys do not match.");
        }
    }
}
