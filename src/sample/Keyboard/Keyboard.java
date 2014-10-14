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

    String[] keyRows = {
            "1234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm"
    };

    public Keyboard(Stage stage) {
        this.stage = stage;

        Slider fontSizeSlider = createSlider();
        fontSizeProperty().bind(fontSizeSlider.valueProperty());
        Label typedData = new Label();
        lastKeyTextProperty().addListener((observable, oldText, newText) ->
                        typedData.setText(typedData.getText() + newText)
        );

        getChildren().addAll(fontSizeSlider, typedData);
        setPadding(new Insets(10));
//        getChildren().setAll(
//                createControls(), this);
        initialiseStyling();
        initialiseFontSize();
        buildKeyboard();
    }

//    private Node createControls() {
//        Slider fontSizeSlider = createSlider();
//        fontSizeProperty().bind(fontSizeSlider.valueProperty());
//
//        Label typedData = new Label();
//        lastKeyTextProperty().addListener((observable, oldText, newText) ->
//                        typedData.setText(typedData.getText() + newText)
//        );
//
//        VBox layout = new VBox(10);
//        layout.getChildren().setAll(
//                new Label("Keyboard Size"),
//                fontSizeSlider,
//                typedData
//        );
//        layout.setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
//
//        return layout;
//    }

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

    //    private VBox layoutWith(Keyboard keyboard) {
//        VBox layout = new VBox(20);
////        layout.setPadding(new Insets(10));
////        layout.getChildren().setAll(
////                createControls(keyboard),
////                keyboard
////        );
//        return layout;
//    }


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

    public ReadOnlyStringWrapper getLastKeyText() {
        return lastKeyText;
    }

    public ReadOnlyStringProperty lastKeyTextProperty() {
        return lastKeyText.getReadOnlyProperty();
    }

    private void onFontSizeChange(Number newValue) {
        setStyle("-fx-font-size: " + newValue + "px;");
        this.stage.sizeToScene();
    }

}
