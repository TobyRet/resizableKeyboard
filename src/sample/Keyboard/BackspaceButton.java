package sample.Keyboard;

import javafx.scene.control.Button;

public class BackspaceButton extends Button {

    private Keyboard keyboard;

    public BackspaceButton(Keyboard keyboard, String text) {
        super(text);
        this.keyboard = keyboard;
        getStyleClass().add("key");

        setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        setOnAction(event -> keyboard.deleteOneCharacter());
    }


}
