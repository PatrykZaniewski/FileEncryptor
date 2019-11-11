package GUI;

import engine.Creator;
import engine.Decryptor;
import engine.Encryptor;
import engine.exceptions.AlgorithmException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class MainWindowController {

    @FXML
    public Label keyLabel;
    @FXML
    private VBox stepTwoEncVBox;
    @FXML
    private VBox stepThreeEncVBox;
    @FXML
    private VBox stepFourEncVBox;
    @FXML
    private VBox stepTwoDecVBox;
    @FXML
    private VBox stepThreeDecVBox;

    @FXML
    private Button savePathButtonEnc;
    @FXML
    private Button startEncButton;
    @FXML
    private Button startDecButton;
    @FXML
    private TextField filePathTextFieldEnc;
    @FXML
    private TextField filePathTextFieldDec;
    @FXML
    private TextField directoryTextFieldEnc;
    @FXML
    private TextField directoryTextFieldDec;
    @FXML
    private ComboBox<String> algoComboBox;
    @FXML
    private TextField keyInput;

    @FXML
    public void openDirectoryChooserEnc() {
        openDirecChooser(directoryTextFieldEnc, startEncButton);
    }

    @FXML
    public void openStartButtonEnc() {
        animateNode(startEncButton);
    }

    @FXML
    public void openDirectoryChooserDec() {
        openDirecChooser(directoryTextFieldDec, startDecButton);
    }

    @FXML
    public void openStartButtonDec() {
        animateNode(startDecButton);
    }

    @FXML
    public void openFileChooserEnc() {
        openFileChooser(filePathTextFieldEnc, stepTwoEncVBox);
    }

    @FXML
    public void openStepTwoEnc() {
        animateNode(stepTwoEncVBox);
    }

    @FXML
    public void openFileChooserDec() {
        openFileChooser(filePathTextFieldDec, stepTwoDecVBox);
    }

    @FXML
    public void openStepTwoDec() {
        animateNode(stepTwoDecVBox);
    }

    @FXML
    public void setAlgorithm() {
        animateNode(stepThreeEncVBox);
        if (algoComboBox.getSelectionModel().getSelectedIndex() == 6) {
            keyLabel.setText("Przesunięcie:");
            keyLabel.setPrefWidth(165);
        } else {
            keyLabel.setText("Klucz:");
            keyLabel.setPrefWidth(46);
        }
    }

    @FXML
    public void setKeyEnc() {
        animateNode(stepFourEncVBox);
    }

    @FXML
    public void setKeyDec() {
        animateNode(stepThreeDecVBox);
    }

    @FXML
    public boolean startEnc() throws AlgorithmException, IOException {
        //TODO przygotuj odpowiedni szyfr -> osobna klasa
        String filePath = filePathTextFieldEnc.getText();
        String savePath = directoryTextFieldEnc.getText();
        String key = keyInput.getText();
        String algorithm = null;
        String mode = null;
        int index = algoComboBox.getSelectionModel().getSelectedIndex();

        if (index < 3) {
            if ((key.length() != 16) && (key.length() != 32)) {
                showError(4);
                //return false;
            }
        } else if (index < 6) {
            if (key.length() != 8) {
                showError(5);
                //return false;
            }
        }

        if (index < 6) {
            String[] splitted = algoComboBox.getValue().split("\\s+");
            algorithm = splitted[0];
            mode = splitted[3];
        }
        int checkFiles = checkFilePaths(filePath, savePath);
        if (checkFiles == 1) {
            showError(1);
            return false;
        } else if (checkFiles == 2) {
            showError(2);
            return false;
        } else if (key.equals("")) {
            showError(3);
            return false;
        } else {
            Creator creator = new Creator();
            Encryptor encryptor = creator.createEncryptor(algorithm, mode, key, null);
            String msg = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println(msg);
            byte[] encodedMsg = encryptor.encrypt(msg.getBytes());
            byte[] iv = encryptor.getIv();
            System.out.println(iv);
            String test = Base64.getEncoder().encodeToString(iv);
            System.out.println(test);
            byte[] iv2 = Base64.getDecoder().decode(test);
            System.out.println(iv2);
            System.out.println(Base64.getEncoder().encodeToString(iv2));

            JSONObject obj = new JSONObject();
            obj.put("Algorithm", algorithm);
            obj.put("Mode", mode);
            obj.put("Key", key);
            obj.put("Iv", Base64.getEncoder().encodeToString(iv));
            obj.put("Encrypted", Base64.getEncoder().encodeToString(encodedMsg));

            FileWriter file = new FileWriter(savePath + "/cipherOut.json");
            file.write(obj.toJSONString());
            file.close();
            return true;
        }
    }

    public boolean startDec() throws IOException, ParseException, AlgorithmException {

        //TODO Potrzebuje tutaj sciezki do wczytania, zapisu i klucza

        Object obj = new JSONParser().parse(new FileReader("C:\\Users\\Unknown\\Desktop\\CipherOut.json"));
        JSONObject jsonObject = (JSONObject) obj;

        String algorithm = (String) jsonObject.get("Algorithm");
        String mode = (String) jsonObject.get("Mode");
        String key = (String) jsonObject.get("Key");
        String iv = (String) jsonObject.get("Iv");
        String enc = (String) jsonObject.get("Encrypted");

       /* int checkFiles = checkFilePaths(filePath, savePath);
        if (checkFiles == 1) {
            showError(1);
            return false;
        } else if (checkFiles == 2) {
            showError(2);
            return false;
        } else if (key.equals("")) {
            showError(3);
            return false;
        }*/

        Creator creator = new Creator();
        Decryptor decryptor = creator.createDecryptor(algorithm, mode, key, Base64.getDecoder().decode(iv));
        byte[] decoded_msg = decryptor.decrypt(Base64.getDecoder().decode(enc));
        FileWriter file = new FileWriter("C:\\Users\\Unknown\\Desktop\\CipherOut2.txt");
        file.write(new String(decoded_msg));
        file.close();
        return true;
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

    private void showError(int code) {
        String mes;
        switch (code) {
            case 1:
                mes = "Nie można zapisać pliku w podanej lokalizacji";
                break;
            case 2:
                mes = "Podany plik nie istnieje";
                break;
            case 3:
                mes = "Podaj klucz, any kontynuować";
                break;
            case 4:
                mes = "Nieprawidłowa długość klucza. Dla trybu AES wynosi ona 16 lub 32 znaków.";
                break;
            case 5:
                mes = "Nieprawidłowa długość klucza. Dla trybu AES wynosi ona 8 znaków.";
                break;
            default:
                mes = "Wystąpił bład";
                break;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setHeaderText(mes);
        alert.initOwner(savePathButtonEnc.getScene().getWindow());
        alert.showAndWait();
    }

    private void animateNode(Node toAnimate) {
        if (toAnimate.getOpacity() == 0) {
            FadeTransition ft = new FadeTransition(Duration.millis(1500), toAnimate);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            toAnimate.setDisable(false);
        }
    }

    private void openDirecChooser(TextField pathField, Button startButton) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage mainStage = (Stage) savePathButtonEnc.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(mainStage);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            pathField.setText(path);
            animateNode(startButton);
        }
    }

    private void openFileChooser(TextField pathField, VBox toAnimate) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki TXT (*.txt)", "*.txt", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage mainStage = (Stage) savePathButtonEnc.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            pathField.setText(path);
            animateNode(toAnimate);
        }
    }
}