package vieroprij;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

// A cell is a single coordinate on the 7 by 6 board.
public final class Cell {

    private final Board board;
    private final int row;
    private final int column;

    /**
     * Create an empty dummy square, only for event reception.
     *
     * @return the rectangle shape
     */
    private Rectangle createEmptyCell() {
        Rectangle rect = new Rectangle(Config.CELL, Config.CELL);
        return rect;
    }

    /**
     * Create blue square with an empty inner circle where a disk can be played
     * to.
     *
     * @param rect An existing rectangle
     * @return the new empty disk cell
     */
    private Shape createDiskCell(Rectangle rect) {

        // Create a circle at (50,50).
        Circle circ = new Circle(Config.DISK);
        circ.centerXProperty().set(Config.CELL / 2);
        circ.centerYProperty().set(Config.CELL / 2);

        // A cell is a square minus the circle
        Shape cell = Path.subtract(rect, circ);
        cell.setFill(Color.BLUE);
        cell.setStroke(Color.BLUE);
        // Give it a bit of a plastic look.
        cell.setOpacity(.8);

        // Give the blue cell a bit of a shadow to suggest 3D.
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0);
        shadow.setOffsetX(3.0);
        shadow.setColor(Color.BLUE);
        cell.setEffect(shadow);
        cell.setCache(true);

        return cell;
    }

    /**
     * Start a new animation to move a disk from its candidate position to its
     * final destination.
     *
     * @param disk
     */
    private void animateDisk(Circle disk) {
        // Prohibit further moves
        board.getModel().setMoveInProgress(true);
        
        // Create a new animation
        // TODO: make animation time dependent on distance to travel
        final TranslateTransition transition
                = new TranslateTransition(Duration.millis(Config.ANIM), disk);
        
        // Receive a notification when the animation ends.
        transition.setOnFinished(new EventHandler<ActionEvent>() {
           @Override 
           public void handle(ActionEvent arg0) {
              
               // Enable new future moves
               board.getModel().setMoveInProgress(false);
               
               // Confirm the model that this move has been played
               board.getModel().playMove(column);
           }
        });
        
        // Start slowly and stop at once.
        transition.setInterpolator(Interpolator.EASE_IN);
        // Set animation destination to the center of the cell.
        transition.setToY(0);
        
        // Animate our disk towards its destination cell.
        transition.play();
    }

    /**
     * The mouse entered a cell, which may trigger a disk-drop suggestion.
     */
    private void mouseEntered() {

        // Check if player can position a disk in the current column.
        if (board.getModel().playableMove(column)) {

            // Ask for the row where a falling disk will find its destination.
            int dest = board.getModel().moveDestination(column);

            // Ask for a candidate disk, which may already exist.
            Circle disk = board.getCandidate();
            if (disk == null) {
                disk = board.createCandidate();
            } else {
                // This is probably not needed.
                board.getPane().getChildren().remove(disk);
            }

            // Position disk above the playing board.
            disk.setTranslateY(-Config.CELL * (dest + 1));

            // Attach candidate to its intended final destination cell.
            board.getPane().add(disk, column, dest);
        }
    }

    /**
     * The mouse pointer left a grid cell.
     * Check if we have to undo the 'mouseEntered()' action.
     */
    private void mouseExited() {
        if (board.getModel().playableMove(column)) {
            // We may need to hide a candidate disk.
            Circle disk = board.getCandidate();
            if (disk != null) {
                board.getPane().getChildren().remove(disk);
                // TODO: stop the flickering.
            }
        }
    }

    /**
     * The mouse has clicked on a square:
     *     check if a move can be played.
     */
    private void mouseClicked() {
        if (board.getModel().playableMove(column)) {

            // Convert the candidate disk into a played disk.
            Circle disk = board.takeCandidate();
            if (disk == null) {
                // If no candidate yet, then simulate a mouse enter event.
                mouseEntered();
                disk = board.takeCandidate();
            }
            if (disk != null) {
                // Change colors
                board.getPlayer().switchSides();
                // Drop the disk.
                animateDisk(disk);
            }
        }
    }

    /**
     * Create a single cell on the playing board.
     *
     * @param board
     */
    private Cell(Board board, int row, int column) {
        this.board = board;
        this.row = row;
        this.column = column;
    }

    /**
     * Place the cell on its board
     */
    private void placeOnBoard() {

        // Create a square for mouse events.
        Rectangle rect = createEmptyCell();
        // Show a disk container.
        Shape cell = createDiskCell(rect);

        // Setup the mouse event handlers for this cell:

        // Mouse enters cell
        rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                mouseEntered();
            }
        });

        // Mouse leaves cell
        rect.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                mouseExited();
            }
        });

        // Mouse clicked.
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                mouseClicked();
            }
        });

        // Create a stack to position the blue cell on top of the rectangle.
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rect, cell);

        // Materialize everything in the current grid location.
        board.getPane().add(stack, this.column, this.row);

        // For 3D effects reflect the last row downwards.
        if (row == Config.ROWS - 1) {
            Reflection reflection = new Reflection();
            reflection.setFraction(0.9);
            reflection.setTopOffset(0);
            reflection.setTopOpacity(0.9);
            stack.setEffect(reflection);
        }
    }

    /**
     * Create a new cell at the given position and put it on the board.
     */
    public static void createAndPlace(Board board, int row, int column) {
        new Cell(board, row, column).placeOnBoard();
    }
}
