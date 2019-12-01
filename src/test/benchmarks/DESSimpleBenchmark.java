package benchmarks;

import engine.DES;
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

@Fork(value = 1)
@Warmup(iterations = 1)
public class DESSimpleBenchmark {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data_toEnc;
        byte[] data_toDecECB;
        byte[] data_toDecCBC;
        byte[] iv_CBC = Base64.getDecoder().decode("eiRSHcgc7Kk=");
        byte[] data_toDecCFB;
        byte[] iv_CFB = Base64.getDecoder().decode("chbwAG4qrEY=");

        @Setup
        public void init() throws IOException, ParseException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data_toEnc = Files.readAllBytes(Paths.get(pwd + dir + "test_image.jpg"));

            FileReader file = new FileReader(pwd + dir + "encrypted_DES_01.12.2019-211235.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecECB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_DES_01.12.2019-211240.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCBC = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_DES_01.12.2019-211253.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCFB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESECBEncryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("ECB", "klucz", null, Cipher.ENCRYPT_MODE);
            des.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESECBDecryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("ECB", "klucz", null, Cipher.DECRYPT_MODE);
            des.decrypt(state.data_toDecECB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESCBCEncryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("CBC", "klucz", null, Cipher.ENCRYPT_MODE);
            des.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESCBCDecryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("CBC", "klucz", state.iv_CBC, Cipher.DECRYPT_MODE);
            des.decrypt(state.data_toDecCBC);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESCFBEncryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("CFB", "klucz", null, Cipher.ENCRYPT_MODE);
            des.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @Measurement(iterations = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void DESCFBDecryptBenchmark(SimpleState state) {
        try {
            DES des = new DES("CFB", "klucz", state.iv_CFB, Cipher.DECRYPT_MODE);
            des.decrypt(state.data_toDecCFB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

}
