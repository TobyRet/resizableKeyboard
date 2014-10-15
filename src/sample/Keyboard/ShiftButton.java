package sample.Keyboard;

import javafx.scene.control.Button;

public class ShiftButton extends Button{

    private Keyboard keyboard;

    public ShiftButton(Keyboard keyboard, String text) {
        super(text);
        this.keyboard = keyboard;
        getStyleClass().add("key");

        setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        setOnAction(event -> {
            keyboard.setCapitalisedCharacter();
            if (keyboard.shiftIsPressed()) {
                this.setStyle("-fx-base: gray;");
            } else {
                this.setStyle("-fx-base: antiquewhite;");
            }
        });
    }
}
