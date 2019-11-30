package benchmarks;

import GUI.MainWindowController;
import engine.exceptions.AlgorithmException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;

import static java.lang.System.exit;

public class ROTFXBenchmark {

    @State(Scope.Thread)
    public static class ControllerState {
        MainWindowController controller = new MainWindowController();

        @Setup
        public void init() {
            String pwd = System.getProperty("user.dir");
            String dir = "\\src\\test\\benchmarks\\";
            System.out.println("akaua");
            String resourcePath = "\\src\\main\\GUI\\mainWindow.fxml";
            //PlatformImpl.startup(() -> {
            System.out.println("lsls");
            //controller = new MainWindowController();
            System.out.println("null?");
            TextField keyInput = new TextField("42");
            System.out.println("null2?");
            controller.setKeyInput(keyInput);
            System.out.println("null3?");
            ComboBox<String> combo = new ComboBox<>();
            combo.getItems().addAll(
                    "AES w trybie ECB",
                    "AES w trybie CBC",
                    "AES w trybie CFB",
                    "DES w trybie ECB",
                    "DES w trybie CBC",
                    "DES w trybie CFB",
                    "RC2 w trybie ECB",
                    "RC2 w trybie CBC",
                    "RC2 w trybie CFB",
                    "Blowfish w trybie ECB",
                    "Blowfish w trybie CBC",
                    "Blowfish w trybie CFB",
                    "ROT"
            );
            controller.setAlgoComboBox(combo);
            controller.getAlgoComboBox().getSelectionModel().select(12);
            TextField directoryField = new TextField(pwd + dir);
            controller.setDirectoryTextFieldEnc(directoryField);
            TextField fileField = new TextField(pwd + dir + "test_file.docx");
            controller.setFilePathTextFieldEnc(fileField);
            System.out.println("lol");
            //});
        }
    }

    //    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ROTControllerBenchmark(ControllerState state) throws AlgorithmException, IOException {
        if (state == null) {
            System.err.println("state");
            exit(2);
        }
        if (state.controller == null) {
            System.err.println("contorlle");
            exit(1);
        }
        if (state.controller.getAlgoComboBox() == null) {
            System.err.println("Guwno");
            exit(3);
        }
        state.controller.startEnc();
    }

}
