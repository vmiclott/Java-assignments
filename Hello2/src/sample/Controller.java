package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {

    public Label label;

    public  void handleButtonAction(ActionEvent event) {
        label.setText("Hallo Wereld!");
    }

}
