package vieroprij;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;

// Manage a playing board.
public final class Board {

    // Implement the board using a grid layout.
    private final GridPane gridPane = new GridPane();

    // Player color.
    private final Player player;

    // Game state and rules.
    private final Model model;

    /**
     * Create the playing board.
     * @param player
     * @param model
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Board(Player player, Model model) {
        this.player = player;
        this.model = model;

        // Place board 120 pixels down from the top border.
        gridPane.setTranslateY(120);
        gridPane.setAlignment(Pos.TOP_CENTER);

        // Configure grid for a 7 by 6 layout.
        for (int column = 0; column < Config.COLUMNS; ++column) {
            gridPane.getColumnConstraints().add(
                    new ColumnConstraints(Config.CELL, Config.CELL, Double.MAX_VALUE));
        }
        for (int row = 0; row < Config.ROWS; ++row) {
            gridPane.getRowConstraints().add(
                    new RowConstraints(Config.CELL, Config.CELL, Double.MAX_VALUE));
        }

        // For each cell on the 7 by 6 board create one cell.
        for (int row = 0; row < Config.ROWS; row++) {//
            for (int column = 0; column < Config.COLUMNS; column++) {
                Cell.createAndPlace(this, row, column);
            }
        }
    }
    public void resetBoard(){
        getModel().resetModel();
        for (int row = 0; row < Config.ROWS; row++) {
            for (int column = 0; column < Config.COLUMNS; column++) {
                Cell.createAndPlace(this, row, column);
            }
        }
    }

    public GridPane getPane() {
        return gridPane;
    }

    // Show one candidate disk when player hovers over the board.
    private Circle candidate;

    /**
     * @return the existing candidate, which is reused,
     * until a new move is finally played.
     */
    public Circle getCandidate() {
        return candidate;
    }

    /**
     * Create a new candidate disk, after the previous one has been played.
     * @return the new disk.
     */
    public Circle createCandidate() {
        // Create a disk which can be played.
        candidate = new Circle(Config.DISK);
        candidate.setFill(player.getColor());
        candidate.setOpacity(.9);

        // Add a bit of 3D-ish effect
        InnerShadow shadow = new InnerShadow();
        shadow.setOffsetX(1.0f);
        shadow.setOffsetY(1.0f);
        candidate.setEffect(shadow);
        candidate.setCache(true);
        
        // Set center alignment.
        GridPane.setHalignment(candidate, HPos.CENTER);
        GridPane.setValignment(candidate, VPos.CENTER);
        return candidate;
    }

    /**
     * Destructively remove the current candidate disk
     * when it is going to be used to complete a new move.
     * @return the disk
     */
    public Circle takeCandidate() {
        Circle disk = candidate;
        candidate = null;
        return disk;
    }

    public Model getModel() {
        return model;
    }

    public Player getPlayer() {
        return player;
    }
}
