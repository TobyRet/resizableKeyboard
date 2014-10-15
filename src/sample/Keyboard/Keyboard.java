package sample.Keyboard;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

class Keyboard extends VBox {
    private static final String BACKSPACE_UNICODE = "\u2190";
    private static final String SHIFT_UNICODE = "\u21E7";
    private DoubleProperty fontSize = new SimpleDoubleProperty(Font.getDefault().getSize());
    private Window stage;
    private Stage parentStage;
    private TextField targetTextField;

    String[] keyRows = {
            "1234567890"+BACKSPACE_UNICODE,
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm"
    };

    public Keyboard(Stage stage, Stage parentStage) {
        this(stage, parentStage, null);
    }

    public Keyboard(Stage stage, Stage parentStage, TextField targetTextField) {
        this.stage = stage;
        this.parentStage = parentStage;
        if (targetTextField == null) {
            this.targetTextField = (TextField) parentStage.getScene().getFocusOwner();
        } else {
            this.targetTextField = targetTextField;
        }

        stage.initStyle(StageStyle.UNDECORATED);
        Slider fontSizeSlider = createSlider();

        getChildren().addAll(fontSizeSlider);

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
            if(Character.toString(c).equals(BACKSPACE_UNICODE)) {
                BackspaceButton backspaceButton = new BackspaceButton(this, Character.toString(c));
                keyRow.getChildren().add(backspaceButton);
            } else {
                KeyButton key = new KeyButton(this, Character.toString(c));
                keyRow.getChildren().add(key);
            }
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

    private void onFontSizeChange(Number newValue) {
        setStyle("-fx-font-size: " + newValue + "px;");
        stage.sizeToScene();
    }

    public void appendText(String text) {
        targetTextField.setText(targetTextField.getText() + text);
    }

    public void deleteOneCharacter() {
        if (targetTextField.getText().length()>0) {
            targetTextField.setText(removeLastCharacter(targetTextField));
        }
    }

    private String removeLastCharacter(TextField data) {
        return data.getText().substring(0, data.getText().length()-1);
    }

    public void setTarget(TextField newTarget) {
        this.targetTextField = newTarget;
    }
}
