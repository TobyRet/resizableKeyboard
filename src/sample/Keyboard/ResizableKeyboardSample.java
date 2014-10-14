package sample.Keyboard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ResizableKeyboardSample extends Application {
    private boolean keyboardNotShown;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage stage) throws Exception {
        keyboardNotShown = true; //does it make a difference?

        VBox rootBox = new VBox(5);
        rootBox.setPadding(new Insets(10));

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        Keyboard popupKeyboard = new Keyboard(stage);

        Stage keyboardPopupWindow = new Stage();
        keyboardPopupWindow.initModality(Modality.NONE);
        keyboardPopupWindow.initOwner(stage);
        Scene keyboardScene = new Scene(popupKeyboard);
        style(keyboardScene);
        keyboardPopupWindow.setScene(keyboardScene);

        Button showKeyboardButton = new Button("Popup Keyboard");
        showKeyboardButton.setOnAction(event -> {
            if(keyboardNotShown) {
                keyboardPopupWindow.show();
            } else {
                keyboardPopupWindow.close();
            }
            keyboardNotShown = !keyboardNotShown;
        });

        rootBox.getChildren().addAll(textField1, textField2, showKeyboardButton);

        Scene scene = new Scene(rootBox);
        displayScene(stage, scene);
    }

    private void displayScene(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void style(Scene scene) {
        scene.getStylesheets().add(
                getClass().getResource(
                        "../keyboard.css"
                ).toExternalForm()
        );
    }
}
