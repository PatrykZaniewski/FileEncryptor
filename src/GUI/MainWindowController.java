package GUI;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainWindowController {

    @FXML
    private VBox stepTwoEncVBox;
    @FXML
    private VBox stepThreeEncVBox;
    @FXML
    private VBox stepFourEncVBox;

    @FXML
    private Button savePathButton;
    @FXML
    private Button startEncButton;
    @FXML
    private TextField filePathTextField;
    @FXML
    private TextField directoryTextField;
    @FXML
    private ComboBox<String> algoComboBox;
    @FXML
    private TextField keyInput;

    @FXML
    public void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage mainStage = (Stage) savePathButton.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(mainStage);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            directoryTextField.setText(path);
            animateNode(startEncButton);
        }
    }

    @FXML
    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        Stage mainStage = (Stage) savePathButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            filePathTextField.setText(path);
            animateNode(stepTwoEncVBox);
        }
    }

    @FXML
    public void setAlgorithm() {
        String algoName = algoComboBox.getValue();
        System.out.println("Algo: " + algoName);
        animateNode(stepThreeEncVBox);
    }

    @FXML
    public void setKey() {
        String key = keyInput.getText();
        System.out.println("Klucz " + key);
        animateNode(stepFourEncVBox);
    }

    @FXML
    public void startEnc() {
        //TODO przygotuj odpowiedni szyfr -> osobna klasa
        String filePath = filePathTextField.getText();
        String savePath = directoryTextField.getText();
        String key = keyInput.getText();
        String algorithm = algoComboBox.getValue();
        int checkFiles = checkFilePaths(filePath, savePath);
        if (checkFiles == 1) {
            showFileError(1);
            return;
        } else if (checkFiles == 2) {
            showFileError(2);
            return;
        }
    }

    private int checkFilePaths(String filePath, String savePath) {
        Path inputFile = new File(filePath).toPath();
        Path saveDir = new File(savePath).toPath();
        if (Files.isWritable(saveDir)) {
            if (Files.exists(inputFile)) {
                return 0;
            } else {
                return 2;
            }
        } else {
            return 1;
        }
    }

    private void showFileError(int code) {
        String mes;
        switch (code) {
            case 1:
                mes = "Nie można zapisać pliku w podanej lokalizacji";
                break;
            case 2:
                mes = "Podany plik nie istnieje";
                break;
            default:
                mes = "Wystąpił bład";
                break;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setHeaderText(mes);
        alert.initOwner(savePathButton.getScene().getWindow());
        alert.showAndWait();
    }

    private void animateNode(Node toAnimate) {
        if (toAnimate.getOpacity() == 0) {
            FadeTransition ft = new FadeTransition(Duration.millis(1500), toAnimate);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        }
    }
}
