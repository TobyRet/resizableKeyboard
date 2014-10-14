package sample.Keyboard;

import javafx.scene.control.Button;

class KeyButton extends Button {
    private Keyboard keyboard;

    public KeyButton(Keyboard keyboard, String text) {
        super(text);
        this.keyboard = keyboard;
        getStyleClass().add("key");

        setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        setOnAction(event -> keyboard.getLastKeyText().set(text));
    }
}
