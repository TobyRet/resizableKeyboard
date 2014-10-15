package sample.Keyboard;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

class Keyboard extends VBox {
    private DoubleProperty fontSize = new SimpleDoubleProperty(Font.getDefault().getSize());
    private Window stage;
    private Stage parentStage;
    private Label typedData;

    String[] keyRows = {
            "1234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm"
    };

    public Keyboard(Stage stage, Stage parentStage) {
        this.stage = stage;
        this.parentStage = parentStage;
        typedData = new Label();
        Slider fontSizeSlider = createSlider();
//        lastKeyTextProperty().addListener((observable, oldText, newText) -> {
//                    typedData.setText(typedData.getText() + newText);
//
//                }
//        );
        getChildren().addAll(fontSizeSlider, typedData);
        initialiseStyling();
        initialiseFontSize();
        buildKeyboard();
    }

    private void buildKeyboard() {
        for (String row: keyRows) {
            addToKeyboard(createKeyRow(row));
        }
    }

    public Slider createSlider() {
        Slider fontSizeSlider = new Slider(8, 40, Font.getDefault().getSize());
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(2);
        fontSizeSlider.setMinorTickCount(0);
        fontSizeProperty().bind(fontSizeSlider.valueProperty());
        return fontSizeSlider;
    }
    private HBox createKeyRow(String row) {
        HBox keyRow = new HBox();
        keyRow.getStyleClass().add("key-row");
        keyRow.setAlignment(Pos.CENTER);

        for (char c: row.toCharArray()) {
            KeyButton key = new KeyButton(this, Character.toString(c));
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
        setPadding(new Insets(10));
        stage.setY(parentStage.getY() + parentStage.getHeight());
        stage.setX(parentStage.getX());

        setAlignment(Pos.BOTTOM_CENTER);
        setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
        getStyleClass().add("keyboard");
    }

    public DoubleProperty fontSizeProperty() {
        return fontSize;
    }
//
//    private ReadOnlyStringWrapper lastKeyText = new ReadOnlyStringWrapper();
//
//    public ReadOnlyStringWrapper getLastKeyText() {
//        return lastKeyText;
//    }
//
//    public ReadOnlyStringProperty lastKeyTextProperty() {
//        return lastKeyText.getReadOnlyProperty();
//    }

    private void onFontSizeChange(Number newValue) {
        setStyle("-fx-font-size: " + newValue + "px;");
        stage.sizeToScene();
    }

    public void appendText(String text) {
        typedData.setText(typedData.getText() + text);
    }
}
