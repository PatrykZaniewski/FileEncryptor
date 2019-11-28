package benchmarks;

import GUI.MainWindowController;
import engine.ROT;
import engine.exceptions.AlgorithmException;
import javafx.fxml.FXMLLoader;
import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Fork(value = 2)
@Warmup(iterations = 2)
public class ROTBenchmarks {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class SimpleState {
        byte[] data;

        @Setup
        public void init() throws IOException {
            String pwd = System.getProperty("user.dir");
            String dir = "/src/test/benchmarks/";
            data = Files.readAllBytes(Paths.get(pwd + dir + "test_file.docx"));
        }
    }

    @State(Scope.Thread)
    public static class ControllerState {
        MainWindowController controller;

        @Setup
        public void init() throws IOException {
            String pwd = System.getProperty("user.dir");
            String dir = "\\src\\test\\benchmarks\\";
            String resourcePath = "\\src\\main\\GUI\\mainWindow.fxml";
            System.out.println(pwd + resourcePath);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(pwd + resourcePath));
            fxmlLoader.load();
            controller = new MainWindowController();
            controller.getKeyInput().setText("42");
            controller.getAlgoComboBox().getSelectionModel().select(12);
            controller.getDirectoryTextFieldEnc().setText(pwd + dir);
            controller.getFilePathTextFieldEnc().setText(pwd + dir + "test_file.docx");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTEncryptBenchmark(SimpleState state) throws AlgorithmException {
        ROT rot = new ROT(42, Cipher.ENCRYPT_MODE);
        rot.encrypt(state.data);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTControllerBenchmark(ControllerState state) throws AlgorithmException, IOException {
        state.controller.startEnc();
    }
}
