package vieroprij;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

// Encapsulate player state, like color
public class Player {

    // Two colors: one color for either player.
    private Color colors[] = {Color.YELLOW, Color.RED};

    // Color property for current player.
    private final SimpleObjectProperty<Color> colorProperty
            = new SimpleObjectProperty<>(colors[0]);

    /**
     * @return the color of the current player.
     */
    Color getColor() {
        return colorProperty.get();
    }

    /**
     * @return the color of the opposite player
     */
    Color invertedColor() {
        return colors[getColor() == colors[0] ? 1 : 0];
    }

    /**
     * Change playing sides by switching colors between yellow and red.
     */
    void switchSides() {
        colorProperty.set(invertedColor());
    }
}

