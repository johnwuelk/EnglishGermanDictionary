package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("DE<->EN Dictionary Dict.cc");
        primaryStage.setScene(new Scene(root, 700, 575));
        primaryStage.show();

        primaryStage.getIcons().add(new Image("sample/java-icon.png"));

        Controller myController = loader.getController();
        myController.searchField.requestFocus();
        myController.outputField.setPromptText("ALT + R: Spell correction via Google\r\rESC: Clear\r\rALT + H: History / Recent Searches\rALT + [1-9]: Fetch line from output field\r\rPrefix space \" \" to google\rALT + D: Insert \"definiton\" for semantic google search\r\rLetters are being changed to Umlaut automatically:\rae -> ä, oe -> ö, ue -> ü, Ae -> Ä, Oe -> Ö, Ue -> Ü, sS -> ß");

        root.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if (event.getCode() == KeyCode.R && event.isAltDown()) {
                try {
                    myController.spellCorrection(); //spellCheck via Google
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getCode() == KeyCode.DIGIT1 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+1");
            }
            if (event.getCode() == KeyCode.DIGIT2 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+2");
            }
            if (event.getCode() == KeyCode.DIGIT3 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+3");
            }
            if (event.getCode() == KeyCode.DIGIT4 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+4");
            }
            if (event.getCode() == KeyCode.DIGIT5 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+5");
            }
            if (event.getCode() == KeyCode.DIGIT6 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+6");
            }
            if (event.getCode() == KeyCode.DIGIT7 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+7");
            }
            if (event.getCode() == KeyCode.DIGIT8 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+8");
            }
            if (event.getCode() == KeyCode.DIGIT9 && event.isAltDown()) {
                myController.fetchItemFromOutputField("Alt+9");
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                myController.clear();
            }
            if (event.getCode() == KeyCode.H && event.isAltDown()) {
                myController.showHistoryItems();
            }
            if (event.getCode() == KeyCode.D && event.isAltDown()) {
                myController.addDefinition();
            }
        });
    }

        public static void main(String[] args) {
        launch(args);
    }
}
