package sample;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ResizableKeyboardSample extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    String[] keyRows = {
            "1234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm"
    };

    public void start(final Stage stage) throws Exception {
        Keyboard keyboard = new Keyboard(stage);
        Scene scene = new Scene(layoutWith(keyboard));
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
                        "keyboard.css"
                ).toExternalForm()
        );
    }

    private VBox layoutWith(Keyboard keyboard) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.getChildren().setAll(
                createControls(keyboard),
                keyboard
        );
        return layout;
    }

    private Node createControls(Keyboard keyboard) {
        Slider fontSizeSlider = createSlider();
        keyboard.fontSizeProperty().bind(fontSizeSlider.valueProperty());

        Label typedData = new Label();
        keyboard.lastKeyTextProperty().addListener((observable, oldText, newText) ->
                        typedData.setText(typedData.getText() + newText)
        );

        VBox layout = new VBox(10);
        layout.getChildren().setAll(
                new Label("Keyboard Size"),
                fontSizeSlider,
                typedData
        );
        layout.setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);

        return layout;
    }

    private Slider createSlider() {
        Slider fontSizeSlider = new Slider(8, 40, Font.getDefault().getSize());
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(2);
        fontSizeSlider.setMinorTickCount(0);
        return fontSizeSlider;
    }

    class Keyboard extends VBox {
        private DoubleProperty fontSize = new SimpleDoubleProperty(Font.getDefault().getSize());
        private Window stage;

        public Keyboard(Stage stage) {
            this.stage = stage;
            initialiseStyling();
            initialiseFontSize();
            buildKeyboard();
        }

        private void buildKeyboard() {
            for (String row: keyRows) {
                addToKeyboard(createKeyRow(row));
            }
        }

        private HBox createKeyRow(String row) {
            HBox keyRow = new HBox();
            keyRow.getStyleClass().add("key-row");
            keyRow.setAlignment(Pos.CENTER);

            for (char c: row.toCharArray()) {
                KeyButton key = new KeyButton(Character.toString(c));
                keyRow.getChildren().add(key);
            }
            return keyRow;
        }

        private void addToKeyboard(HBox keyRow) {
            getChildren().add(keyRow);
        }

        private void initialiseFontSize() {
            onFontSizeChange(fontSize.getValue());
            fontSize.addListener((observable, oldValue, newValue) ->
                            onFontSizeChange(newValue)
            );
        }

        private void initialiseStyling() {
            setAlignment(Pos.BOTTOM_CENTER);
            setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            getStyleClass().add("keyboard");
        }

        public double getFontSize() {
            return fontSize.get();
        }

        public DoubleProperty fontSizeProperty() {
            return fontSize;
        }

        public void setFontSize(double fontSize) {
            this.fontSize.set(fontSize);
        }

        private ReadOnlyStringWrapper lastKeyText = new ReadOnlyStringWrapper();

        public String getLastKeyText() {
            return lastKeyText.get();
        }

        public ReadOnlyStringProperty lastKeyTextProperty() {
            return lastKeyText.getReadOnlyProperty();
        }

        private void onFontSizeChange(Number newValue) {
            setStyle("-fx-font-size: " + newValue + "px;");
            this.stage.sizeToScene();
        }

        class KeyButton extends Button {
            public KeyButton(String text) {
                super(text);
                getStyleClass().add("key");

                setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
                setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

                setOnAction(event -> lastKeyText.set(text));
            }
        }
    }

}
