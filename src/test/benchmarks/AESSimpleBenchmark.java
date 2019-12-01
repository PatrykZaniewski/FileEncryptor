package benchmarks;

import engine.AES;
import engine.exceptions.AlgorithmException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class AESSimpleBenchmark {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data_toEnc;
        byte[] data_toDecECB;
        byte[] data_toDecCBC;
        byte[] iv_CBC = Base64.getDecoder().decode("PlRohjupljpl1uJu49tn8Q==");
        byte[] data_toDecCFB;
        byte[] iv_CFB = Base64.getDecoder().decode("jGERakLnzb3sgYEwp+1XdA==");

        @Setup
        public void init() throws IOException, ParseException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data_toEnc = Files.readAllBytes(Paths.get(pwd + dir + "aes_test.jpg"));

            FileReader file = new FileReader(pwd + dir + "encrypted_AES_01.12.2019-192643.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecECB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_AES_01.12.2019-192704.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCBC = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_AES_01.12.2019-192722.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCFB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESECBEncryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("ECB", "klucz", null, Cipher.ENCRYPT_MODE);
            aes.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESECBDecryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("ECB", "klucz", null, Cipher.DECRYPT_MODE);
            aes.decrypt(state.data_toDecECB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESCBCEncryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("CBC", "klucz", null, Cipher.ENCRYPT_MODE);
            aes.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESCBCDecryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("CBC", "klucz", state.iv_CBC, Cipher.DECRYPT_MODE);
            aes.decrypt(state.data_toDecCBC);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESCFBEncryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("CFB", "klucz", null, Cipher.ENCRYPT_MODE);
            aes.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void AESCFBDecryptBenchmark(SimpleState state) {
        try {
            AES aes = new AES("CFB", "klucz", state.iv_CFB, Cipher.DECRYPT_MODE);
            aes.decrypt(state.data_toDecCFB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }
}
