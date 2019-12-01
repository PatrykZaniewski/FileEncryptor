package benchmarks;

import engine.ROT;
import engine.exceptions.AlgorithmException;
import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Fork(value = 1)
@Warmup(iterations = 1)
public class ROTSimpleBenchmark {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data_toEnc;
        byte[] data_toDec;

        @Setup
        public void init() throws IOException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data_toEnc = Files.readAllBytes(Paths.get(pwd + dir + "aes_test.jpg"));
            data_toDec = Files.readAllBytes(Paths.get(pwd + dir + "encrypted_ROT_01.12.2019-212445.json"));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTEncryptBenchmark(SimpleState state) {
        try {
            ROT rot = new ROT(42, Cipher.ENCRYPT_MODE);
            rot.encrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTDecryptBenchmark(SimpleState state) {
        try {
            ROT rot = new ROT(42, Cipher.DECRYPT_MODE);
            rot.decrypt(state.data_toEnc);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }
    }
}
