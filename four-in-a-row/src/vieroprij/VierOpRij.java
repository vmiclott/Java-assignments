/**
 TODO (Stap 5): Implementeer tenminste een van de volgende ideeen.

 Kondig de overwinning aan met een boodschap in een popup.

 Laat de computer na 2 seconden een random tegenzet kiezen.

 Laat de schijven achter de blauwe kozijnen naar beneden dalen.

 Voorkom het flikkeren van de kandidaat als je de muis verticaal beweegt.

 Alle kleurspecificaties moeten door middel van CSS plaatsvinden.

 Realiseer zoveel mogelijk van de rest van de styling ook in CSS.

 Laat ook de schijven in de onderste rij reflecteren in de ondergrond.

 Breid de reflectie uit naar andere rijen, inclusief hun schijven.

 Voeg een undo/redo-mechanisme toe met volledige geschiedenis.

 Exit als de gebruiker op de Escape-toets drukt of de letter 'q' in typt.
 */

package vieroprij;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static vieroprij.ModelEventType.*;

/**
 * Implement a JavaFX GUI for the Connect Four game.
 */



public class VierOpRij extends Application {
    private Board board;
    @Override
    public void start(final Stage primaryStage) {

        // Communicate options for outer window to the window manager.
        primaryStage.setTitle(Config.GAME_TITLE);
        primaryStage.setResizable(true);

        final BorderPane root = new BorderPane();
        Scene scene = new Scene(root, Config.SCENE_WIDTH, Config.SCENE_HEIGHT, true);
        // Default background is black.
        scene.setFill(Color.BLACK);

        // Load CSS resources
        URL resource = getClass().getResource(Config.CSS_STYLES);
        if (resource != null) {
            scene.getStylesheets().add(resource.toExternalForm());
        } else {
            System.err.println("Resource file " + Config.CSS_STYLES + " not found!");
        }

        // A board manages a grid of cells.
        board = new Board(new Player(), new Model());
        root.setCenter(board.getPane());

        // Add a button to the top left corner.
        Button newGameButton = createNewGameButton();
        root.setTop(newGameButton);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Listen to requests for a new game
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                startNewGame();
            }
        });

        String message = "New game has started";
        System.out.println(message);
        Text text = new Text(message);
        text.setId("fancytext");
        root.setBottom(text);

        // Get a notification when the game has ended.
        board.getModel().subscribe(new ModelEventListener() {
            @Override
            public void eventOccurred(ModelEvent e) {
                if (e.getEventType() == GameOver) {
                    String message = " You won! Game over. ";
                    System.out.println(message);
                    Text text = new Text(message);
                    text.setId("fancytext");
                    root.setBottom(text);
                }
                else if(e.getEventType() == NewGame){
                    String message = "New game has started";
                    System.out.println(message);
                    Text text = new Text(message);
                    text.setId("fancytext");
                    root.setBottom(text);
                }
            }
        });

    }

    /**
     * Create a button to reset the game and start all over.
     *
     * @return the created button
     */
    private Button createNewGameButton() {
        Button newGameButton = new Button(Config.NEW_GAME_BUTTON);
        newGameButton.setTranslateY(10);
        newGameButton.setTranslateX(10);

        // TODO: move stuff like this to the CSS resources.
        DropShadow effect = new DropShadow();
        effect.setColor(Color.BLUE);
        newGameButton.setEffect(effect);

        return newGameButton;
    }

    private void startNewGame() {
        board.resetBoard();
        board.getModel().gameOver=false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
