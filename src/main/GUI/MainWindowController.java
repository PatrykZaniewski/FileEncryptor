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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class MainWindowController {

    @FXML
    public Label keyLabel;
    @FXML
    public Label keyDecLabel;
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
    private TextField keyDecInput;

    private static final int MAX_KEY_LENGTH = 50;

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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki TXT, JPG, PNG (*.txt, *.jpg, *jpeg, *.png)", "*.txt", "*.jpg", "*.jpeg", "*.png");
        openFileChooser(filePathTextFieldEnc, stepTwoEncVBox, extFilter);
    }

    @FXML
    public void openStepTwoEnc() {
        animateNode(stepTwoEncVBox);
    }

    @FXML
    public void openFileChooserDec() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki JSON (*.json)", "*.json");
        openFileChooser(filePathTextFieldDec, stepTwoDecVBox, extFilter);

        String filePath = filePathTextFieldDec.getText();
        if (!filePath.isEmpty()) {
            if (filePath.contains("ROT")) {
                keyDecLabel.setText("Przesunięcie:");
                keyDecLabel.setPrefWidth(165);
            } else {
                keyDecLabel.setText("Klucz:");
                keyDecLabel.setPrefWidth(46);
            }
        } else {
            keyDecLabel.setText("Klucz:");
            keyDecLabel.setPrefWidth(46);
        }
    }

    @FXML
    public void openStepTwoDec() {
        animateNode(stepTwoDecVBox);
    }

    @FXML
    public void setAlgorithm() {
        animateNode(stepThreeEncVBox);
        if (algoComboBox.getSelectionModel().getSelectedIndex() == 12) {
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
        String filePath = filePathTextFieldEnc.getText();
        String savePath = directoryTextFieldEnc.getText();
        String key = keyInput.getText();
        int index = algoComboBox.getSelectionModel().getSelectedIndex();
        String[] nameSplitted = filePath.split("\\.");
        String ext = nameSplitted[nameSplitted.length - 1];
        ext = "." + ext;

        int checkFiles = checkFilePaths(filePath, savePath);
        if (checkFiles == 1) {
            showError(1);
            return false;
        } else if (filePath.isEmpty()) {
            showError(7);
            return false;
        } else if (savePath.isEmpty()) {
            showError(8);
            return false;
        } else if (checkFiles == 2) {
            showError(2);
            return false;
        } else if (key.equals("")) {
            showError(3);
            return false;
        } else if (key.length() > MAX_KEY_LENGTH) {
            showError(9);
            return false;
        } else {
            Creator creator = new Creator();
            byte[] msg = Files.readAllBytes(Paths.get(filePath));
            byte[] encodedMsg = null;
            String algorithm = null;
            String mode = null;
            JSONObject obj = new JSONObject();

            if (index < 12) {
                String[] splitted = algoComboBox.getValue().split("\\s+");
                algorithm = splitted[0];
                mode = splitted[3];

                Encryptor encryptor = creator.createEncryptor(algorithm, mode, key, null);
                encodedMsg = encryptor.encrypt(msg);
                byte[] iv = encryptor.getIv();

                if (iv == null) {
                    obj.put("Iv", "");
                } else {
                    obj.put("Iv", Base64.getEncoder().encodeToString(iv));
                }
                obj.put("Mode", mode);
            } else {
                algorithm = "ROT";

                if (!isNumeric(key)) {
                    showError(6);
                    return false;
                } else {
                    int shift = Integer.parseInt(key);
                    Encryptor encryptor;
                    try {
                        encryptor = creator.createEncryptor(algorithm, shift);
                    } catch (AlgorithmException e) {
                        showError(11);
                        return false;
                    }
                    encodedMsg = encryptor.encrypt(msg);
                }
            }
            obj.put("Algorithm", algorithm);
            obj.put("Encrypted", Base64.getEncoder().encodeToString(encodedMsg));
            obj.put("Extension", ext);

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy-HHmmss");

            FileWriter file = new FileWriter(savePath + "/encrypted_" + algorithm + "_" + formatter.format(date) + ".json");
            file.write(obj.toJSONString());
            file.close();

            showConfirmation("enc");

            return true;
        }
    }

    public boolean startDec() throws IOException, ParseException, AlgorithmException {
        String filePath = filePathTextFieldDec.getText();
        String savePath = directoryTextFieldDec.getText();
        String keyDec = keyDecInput.getText();

        int checkFiles = checkFilePaths(filePath, savePath);

        if (checkFiles == 1) {
            showError(1);
            return false;
        } else if (checkFiles == 2) {
            showError(2);
            return false;
        } else if (keyDec.equals("")) {
            showError(3);
            return false;
        } else if (filePath.isEmpty()) {
            showError(7);
            return false;
        } else if (savePath.isEmpty()) {
            showError(8);
            return false;
        } else if (keyDec.length() > MAX_KEY_LENGTH) {
            showError(9);
            return false;
        }

        FileReader file = new FileReader(filePath);
        Object obj = new JSONParser().parse(file);
        JSONObject jsonObject = (JSONObject) obj;

        String algorithm = (String) jsonObject.get("Algorithm");
        String enc = (String) jsonObject.get("Encrypted");
        Creator creator = new Creator();
        Decryptor decryptor;
        byte[] decoded_msg = null;

        if (algorithm.equals("ROT")) {
            if (!isNumeric(keyDec)) {
                showError(6);
                return false;
            } else {
                decryptor = creator.createDecryptor("ROT", Integer.parseInt(keyDec));
            }
        } else {
            String mode = (String) jsonObject.get("Mode");
            String iv = (String) jsonObject.get("Iv");
            byte[] ivByte = null;
            if (!iv.equals("")) {
                ivByte = Base64.getDecoder().decode(iv);
            }
            enc = (String) jsonObject.get("Encrypted");
            decryptor = creator.createDecryptor(algorithm, mode, keyDec, ivByte);
        }
        file.close();
        try {
            decoded_msg = decryptor.decrypt(Base64.getDecoder().decode(enc));
        } catch (AlgorithmException e) {
            showError(10);
            return false;
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy-HHmmss");

        String ext = (String) jsonObject.get("Extension");
        if (ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg")) {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded_msg));
            File out = new File(savePath + "/decrypted" + algorithm + "_" + formatter.format(date) + ext);
            ext = ext.substring(1);
            ImageIO.write(bufferedImage, ext, out);
        } else {
            FileWriter filereader = new FileWriter(savePath + "/decrypted_" + algorithm + "_" + formatter.format(date) + ".txt");
            filereader.write(new String(decoded_msg));
            filereader.close();
        }

        showConfirmation("dec");

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

    private void showConfirmation(String oprType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Koniec!");
        if (oprType.equals("dec")) {
            alert.setHeaderText("Ukończono deszfrację!");
        } else {
            alert.setHeaderText("Ukończono szyfrowanie!");
        }
        alert.initOwner(savePathButtonEnc.getScene().getWindow());
        alert.showAndWait();
    }

    private void showError(int code) {
        String mes;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (code) {
            case 1:
                mes = "Nie można zapisać pliku w podanej lokalizacji.";
                break;
            case 2:
                mes = "Podany plik nie istnieje.";
                break;
            case 3:
                mes = "Podaj klucz, any kontynuować.";
                break;
            /*case 4:
                mes = "Nieprawidłowa długość klucza. Dla trybu AES wynosi ona 16 lub 32 znaków.";
                break;
            case 5:
                mes = "Nieprawidłowa długość klucza. Dla trybu AES wynosi ona 8 znaków.";
                break; */
            case 6:
                mes = "Przesunięcie dla trybu ROT musi być liczbą.";
                break;
            case 7:
                mes = "Nie podano pliku.";
                break;
            case 8:
                mes = "Nie podano folderu docelowego.";
                break;
            case 9:
                mes = "Podany klucz jest za długi. Maksymalna długość wynosi 50 znaków.";
                break;
            case 10:
                mes = "Podany klucz jest nieprawidłowy.";
                break;
            case 11:
                mes = "Podane przesunięcie powinno być pomiędzy -127 a 128";
                alert.setContentText("Wartość nie może być 5");
                break;
            default:
                mes = "Wystąpił bład";
                break;
        }

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

    private void openFileChooser(TextField pathField, VBox toAnimate, FileChooser.ExtensionFilter extFilter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        Stage mainStage = (Stage) savePathButtonEnc.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            pathField.setText(path);
            animateNode(toAnimate);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setFilePathTextFieldEnc(TextField filePathTextFieldEnc) {
        this.filePathTextFieldEnc = filePathTextFieldEnc;
    }

    public void setDirectoryTextFieldEnc(TextField directoryTextFieldEnc) {
        this.directoryTextFieldEnc = directoryTextFieldEnc;
    }

    public void setAlgoComboBox(ComboBox<String> algoComboBox) {
        this.algoComboBox = algoComboBox;
    }

    public ComboBox<String> getAlgoComboBox() {
        return algoComboBox;
    }

    public void setKeyInput(TextField keyInput) {
        this.keyInput = keyInput;
    }
}