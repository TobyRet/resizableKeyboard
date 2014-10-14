package sample.Keyboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResizableKeyboardSample extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage stage) throws Exception {


        Keyboard keyboard = new Keyboard(stage);
        Scene scene = new Scene(keyboard);
        style(scene);
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
