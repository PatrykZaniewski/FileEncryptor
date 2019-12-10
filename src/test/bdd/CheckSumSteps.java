package bdd;

import engine.CheckSum;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class CheckSumSteps {
    private byte[] file;
    private CheckSum checkSum;

    @Given("a $path file")
    public void getTheFile(String path) {
        String pwd = System.getProperty("user.dir");
        try {
            file = Files.readAllBytes(Paths.get(pwd + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @When("I choose the $algoType hash algorithm")
    public void chooseAlgo(String algoType) {
        checkSum = new CheckSum(algoType);
    }

    @Then("I get the proper hash ($hash)")
    public void getHash(String hash) {
        String ans = checkSum.getHash(file);
        assertEquals(hash, ans);
    }
}
