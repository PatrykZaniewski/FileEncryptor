package benchmarks;

import engine.ROT;
import engine.exceptions.AlgorithmException;
import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Fork(value = 2)
@Warmup(iterations = 2)
public class CipherBenchmarks {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class MyState {
        byte[] data;

        @Setup
        public void init() throws IOException {
            String cwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data = Files.readAllBytes(Paths.get(cwd + dir + "test_file.docx"));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTEncryptBenchmark(MyState state) {
        try {
            ROT rot = new ROT(42, Cipher.ENCRYPT_MODE);
            rot.encrypt(state.data);
        } catch (AlgorithmException e) {
            e.printStackTrace();
        }

    }
}
