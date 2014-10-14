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
    private boolean keyboardNotShown = true;
    private Keyboard keyboard;
    private Stage keyboardPopupWindow;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception {
        Button showKeyboardButton = createKeyboardButton(keyboardPopupWindow, primaryStage);
        VBox rootBox = createRootBox(showKeyboardButton); //dont like this

        primaryStage.setScene(new Scene(rootBox));
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private VBox createRootBox(Button showKeyboardButton) {
        VBox rootBox = new VBox(5);
        rootBox.setPadding(new Insets(10));
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        rootBox.getChildren().addAll(textField1, textField2, showKeyboardButton);
        return rootBox;
    }

    private Stage createPopupWindow(Stage primaryStage) {
        Stage keyboardPopupWindow = new Stage();
        keyboardPopupWindow.initModality(Modality.NONE);
        keyboardPopupWindow.initOwner(primaryStage);
        Keyboard popupKeyboard = new Keyboard(keyboardPopupWindow, primaryStage);
        Scene keyboardScene = new Scene(popupKeyboard);
        style(keyboardScene);
        keyboardPopupWindow.setScene(keyboardScene);
        return keyboardPopupWindow;
    }

    private Button createKeyboardButton(Stage keyboardPopupWindow, Stage primaryStage) {
        Button showKeyboardButton = new Button("Popup Keyboard");
        showKeyboardButton.setOnAction(event -> {
            if (keyboardNotShown) {
                ResizableKeyboardSample.this.keyboardPopupWindow = createPopupWindow(primaryStage);
                ResizableKeyboardSample.this.keyboardPopupWindow.show();
            } else {
                ResizableKeyboardSample.this.keyboardPopupWindow.close();
            }
            keyboardNotShown = !keyboardNotShown;
        });
        return showKeyboardButton;
    }

    private void style(Scene scene) {
        scene.getStylesheets().add(
                getClass().getResource(
                        "../keyboard.css"
                ).toExternalForm()
        );
    }
}
