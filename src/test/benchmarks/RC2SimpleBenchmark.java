package benchmarks;

import engine.RC2;
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
@Measurement(iterations = 5)
@Timeout(time = 1, timeUnit = TimeUnit.MINUTES)
public class RC2SimpleBenchmark {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data_toEnc;
        byte[] data_toDecECB;
        byte[] data_toDecCBC;
        byte[] iv_CBC = Base64.getDecoder().decode("PlRohjupljp");
        byte[] iv_CFB = Base64.getDecoder().decode("jGERakLnzb3");
        byte[] data_toDecCFB;

        @Setup
        public void init() throws IOException, ParseException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data_toEnc = Files.readAllBytes(Paths.get(pwd + dir + "test_image.jpg"));

            FileReader file = new FileReader(pwd + dir + "encrypted_RC2_01.12.2019-205144.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecECB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_RC2_01.12.2019-205231.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCBC = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));

            file = new FileReader(pwd + dir + "encrypted_RC2_01.12.2019-205301.json");
            jsonObject = (JSONObject) new JSONParser().parse(file);
            data_toDecCFB = Base64.getDecoder().decode((String) jsonObject.get("Encrypted"));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2ECBEncryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("ECB", "klucz", null, Cipher.ENCRYPT_MODE);
            rc2.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2ECBDecryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("ECB", "klucz", null, Cipher.DECRYPT_MODE);
            rc2.decrypt(state.data_toDecECB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2CBCEncryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("CBC", "klucz", null, Cipher.ENCRYPT_MODE);
            rc2.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2CBCDecryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("CBC", "klucz", state.iv_CBC, Cipher.DECRYPT_MODE);
            rc2.decrypt(state.data_toDecCBC);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2CFBEncryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("CFB", "klucz", null, Cipher.ENCRYPT_MODE);
            rc2.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void RC2CFBDecryptBenchmark(SimpleState state) {
        try {
            RC2 rc2 = new RC2("CFB", "klucz", state.iv_CFB, Cipher.DECRYPT_MODE);
            rc2.decrypt(state.data_toDecCFB);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }
}
