package engine;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {

    private MessageDigest md;

    public CheckSum(String algoType) {
        try {
            md = MessageDigest.getInstance(algoType);
        } catch (NoSuchAlgorithmException e) {
            md = null;
        }
    }

    public String getHash(byte[] data) {
        if (md == null) {
            return null;
        }
        return Hex.encodeHexString(md.digest(data));
    }
}
