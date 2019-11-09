package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    private Creator creator;

    @BeforeEach
    void setUp() {
        creator = new Creator();
    }

    //template
    //TODO: delete before presenting the project
    void createEncryptor__Should() throws AlgorithmException {
        //given
        String algorithm = "";
        String mode = "";
        String key = "";

        //when
        Encryptor result = creator.createEncryptor(algorithm, mode, key);

        //then
        assertEquals(result.getAlgorithmMode(), algorithm, "");
        assertEquals(result.getKey(), key, "");
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
        } catch (AlgorithmException e) {
            fail("The method has thrown an exception while all the provided data is correct.");
        }

        //then
        assertTrue(result instanceof AES, "The algorithms do not match.");
        assertEquals(result.getAlgorithmMode(), mode, "The encryption modes do not match.");
        assertEquals(result.getKey(), key, "The keys do not match.");
    }
}