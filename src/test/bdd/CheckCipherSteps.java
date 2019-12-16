package bdd;

import engine.*;
import engine.exceptions.AlgorithmException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class CheckCipherSteps {
    private byte[] file;
    private Encryptor encryptor;

    @Given("a $path file")
    public void getTheFile(String path) {
        String pwd = System.getProperty("user.dir");
        try {
            file = Files.readAllBytes(Paths.get(pwd + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("I choose the $algoType algorithm with $mode mode and $password password and $iv iv")
    public void chooseAlgo(String algoType, String mode, String password, String iv) throws AlgorithmException {
        switch (algoType) {
            case "AES":
                encryptor = new AES(mode, password, Base64.getDecoder().decode(iv), Cipher.ENCRYPT_MODE);
                break;
            case "DES":
                encryptor = new DES(mode, password, Base64.getDecoder().decode(iv), Cipher.ENCRYPT_MODE);
                break;
            case "Blowfish":
                encryptor = new Blowfish(mode, password, Base64.getDecoder().decode(iv), Cipher.ENCRYPT_MODE);
                break;
            case "RC2":
                encryptor = new RC2(mode, password, Base64.getDecoder().decode(iv), Cipher.ENCRYPT_MODE);
                break;
        }
    }

    @When("I choose the ROT algorithm with $shift shift")
    public void chooseRotAlgo(String shift) throws AlgorithmException {
        encryptor = new ROT(Integer.parseInt(shift), Cipher.ENCRYPT_MODE);
    }

    @When("I choose the $algoType algorithm with $mode mode and $password password")
    public void chooseAlgo(String algoType, String mode, String password) throws AlgorithmException {
        switch (algoType) {
            case "AES":
                encryptor = new AES(mode, password, null, Cipher.ENCRYPT_MODE);
                break;
            case "DES":
                encryptor = new DES(mode, password, null, Cipher.ENCRYPT_MODE);
                break;
            case "Blowfish":
                encryptor = new Blowfish(mode, password, null, Cipher.ENCRYPT_MODE);
                break;
            case "RC2":
                encryptor = new RC2(mode, password, null, Cipher.ENCRYPT_MODE);
                break;
        }
    }

    @Then("I get the proper cipheredText ($ciphered)")
    public void getCipher(String ciphered) {
        byte[] ans = encryptor.encrypt(file);
        assertEquals(ciphered, Base64.getEncoder().encodeToString(ans));
    }
}
