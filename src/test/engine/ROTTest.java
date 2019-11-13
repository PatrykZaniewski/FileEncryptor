package engine;

import engine.exceptions.AlgorithmException;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ROTTest {

    @Test
    void encryptionTest() throws AlgorithmException {
        ROT r = new ROT(125, Cipher.ENCRYPT_MODE);
        String msg = "Do zakodowania";
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);

        byte[] encrypted = r.encrypt(data);

        assertFalse(Arrays.equals(data, encrypted));
    }

    @Test
    void nullDataEncryptionTest() throws AlgorithmException {
        ROT r = new ROT(120, Cipher.ENCRYPT_MODE);

        byte[] data = r.encrypt(null);

        assertNull(data);
    }

    @Test
    void encryptionInDecryptionMode_shouldThrowException() throws AlgorithmException {
        ROT r = new ROT(123, Cipher.DECRYPT_MODE);
        byte[] data = "zły mode".getBytes();

        assertThrows(IllegalStateException.class, () -> r.encrypt(data));
    }

    @Test
    void nullDataDecryptionTest() throws AlgorithmException {
        ROT r = new ROT(45, Cipher.DECRYPT_MODE);

        byte[] data = r.decrypt(null);

        assertNull(data);
    }

    @Test
    void decryptionInEncryptionMode_shouldThrowExcetion() throws AlgorithmException {
        ROT r = new ROT(34, Cipher.ENCRYPT_MODE);
        byte[] data = "zly mode".getBytes();

        assertThrows(IllegalStateException.class, () -> r.decrypt(data));
    }

    @Test
    void decryptionTest() throws AlgorithmException {
        ROT r = new ROT(34, Cipher.ENCRYPT_MODE);
        String mes = "Do zakodowania";
        byte[] data = mes.getBytes(StandardCharsets.UTF_8);
        byte[] enc = r.encrypt(data);

        r = new ROT(34, Cipher.DECRYPT_MODE);
        byte[] dec = r.decrypt(enc);

        assertEquals(mes, new String(dec, StandardCharsets.UTF_8));
    }

    @Test
    void badShiftTest() {
        assertThrows(AlgorithmException.class, () -> new ROT(129, Cipher.DECRYPT_MODE));
    }

    @Test
    void zeroAsShiftTest() {
        assertThrows(AlgorithmException.class, () -> new ROT(0, Cipher.DECRYPT_MODE));
    }

    @Test
    void badModeTest() {
        assertThrows(AlgorithmException.class, () -> new ROT(34, Cipher.WRAP_MODE));
    }
}
