package benchmarks;

import engine.Blowfish;
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

public class BlowfishSimpleBenchmark {
    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data_toEnc;
        byte[] data_toDecECB;
        byte[] data_toDecCBC;
        byte[] iv_CBC = Base64.getDecoder().decode("xkjzNdR9KpA=");
        byte[] data_toDecCFB;
        byte[] iv_CFB = Base64.getDecoder().decode("S2nsfCJDLNQ=");

        @Setup
        public void init() throws IOException, ParseException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data_toEnc = Files.readAllBytes(Paths.get(pwd + dir + "test_file.docx"));

            FileReader file = new FileReader(pwd + dir + "encrypted_Blowfish_01.12.2019-212434.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecECB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_Blowfish_01.12.2019-212437.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCBC = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_Blowfish_01.12.2019-212440.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCFB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishECBEncryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("ECB", "klucz", null, Cipher.ENCRYPT_MODE);
            blow.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishECBDecryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("ECB", "klucz", null, Cipher.DECRYPT_MODE);
            blow.decrypt(state.data_toDecECB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishCBCEncryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("CBC", "klucz", null, Cipher.ENCRYPT_MODE);
            blow.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishCBCDecryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("CBC", "klucz", state.iv_CBC, Cipher.DECRYPT_MODE);
            blow.decrypt(state.data_toDecCBC);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishCFBEncryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("CFB", "klucz", null, Cipher.ENCRYPT_MODE);
            blow.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void BlowfishCFBDecryptBenchmark(SimpleState state) {
        try {
            Blowfish blow = new Blowfish("CFB", "klucz", state.iv_CFB, Cipher.DECRYPT_MODE);
            blow.decrypt(state.data_toDecCFB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }
}
