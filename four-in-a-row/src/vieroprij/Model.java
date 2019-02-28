package vieroprij;

import java.util.Collection;
import java.util.HashSet;

/**
 * Encapsulate the game rules and the current game state.
 * Allow clients to subscribe to model changes.
 * Notify subscribers when changes to the model occur.
 */
public class Model {


    private int[] columns = new int[Config.COLUMNS];
    private boolean kleur = true;
    protected Boolean[][] bord = new Boolean[Config.ROWS][Config.COLUMNS]; //true:geel false:rood null:leeg.
    private int laatsteKol = -1;
    protected boolean gameOver;
    // Keep track of whether a move animation is currently playing.
    // When a move animation is busy then no new moves are allowed.
    private boolean moveInProgress = false;
    
    /**
     * @param progress the new move-in-progress state.
     */
    public void setMoveInProgress(boolean progress) {
        moveInProgress = progress;
    }
    
    /**
     * @return whether a move animation is currently in progress.
     */
    public boolean getMoveInProgress() {
        return moveInProgress;
    }
    
    // The set of event handlers for this model.
    private final Collection<ModelEventListener> subscribers = new HashSet<>();
    
    // Add a new subscriber.
    public void subscribe(ModelEventListener listener) {
        subscribers.add(listener);
    }
    
    // Unsubscribe an event listener.
    public void unsubscribe(ModelEventListener listener) {
        subscribers.remove(listener);
    }
    
    // Notify all subscribers about an event.
    private void notifySubscribers(ModelEvent event) {
        for (ModelEventListener listener: subscribers) {
            listener.eventOccurred(event);
        }
    }
    
    /**
     * Check if the game has ended. Warn subscribers of this fact.
     */
    public void checkGameOver() {
        int laatsteRij = moveDestination(laatsteKol)+1;
        if(laatsteRij<=Config.ROWS-4){
            if(langsteLijn(laatsteKol,laatsteRij,1,0)>=4){
                gameOver=true;
            }
        }
        if(!gameOver && langsteLijn(laatsteKol,laatsteRij,-1,-1)+langsteLijn(laatsteKol,laatsteRij,1,1)-1>=4){
            gameOver = true;
        }
        if(!gameOver && langsteLijn(laatsteKol,laatsteRij,-1,1)+langsteLijn(laatsteKol,laatsteRij,1,-1)-1>=4){
            gameOver = true;
        }
        if(!gameOver && langsteLijn(laatsteKol,laatsteRij,0,-1)+langsteLijn(laatsteKol,laatsteRij,0,1)-1>=4){
            gameOver = true;
        }
        if(gameOver){
            notifySubscribers(new ModelEvent(ModelEventType.GameOver));
        }
    }


    /**
     * Check if a new disk can be inserted in the current column.
     * @param column
     * @return true if and only if a move in this column is allowed.
     */
    public boolean playableMove(int column) {
        // No new moves are allowed when an animation is busy.
        if (getMoveInProgress()||columns[column]>=Config.ROWS || gameOver) {
            return false;
        }
        return true;
    }

    /**
     * Compute the final destination row of a candidate move.
     * @param column
     * @return the row.
     */
    public int moveDestination(int column) {
        int i = Config.ROWS-1;
        while(i>=0 && bord[i][column] != null){
            i--;
        }
        return i;
    }

    /**
     * Commit the insertion of a new disk in a given column.
     * @param column 
     */

    public void resetModel(){
        for(int i=0; i<Config.ROWS; i++){
            for(int j=0; j<Config.COLUMNS; j++){
                bord[i][j]=null;
            }
        }
        notifySubscribers(new ModelEvent(ModelEventType.NewGame));
    }

    public void playMove(int column) {
        if(playableMove(column)){
            bord[moveDestination(column)][column] = kleur;
            kleur = !kleur;
            laatsteKol = column;
        }
        checkGameOver();

        // TODO: Update the model to reflect the new move.

    }

    private int langsteLijn(int kolom, int rij, int verticaal, int horizontaal){
        int aantal=0;
        Boolean kleur = bord[rij][kolom];
        while(kolom<Config.COLUMNS && kolom>=0 && rij>=0 && rij<Config.ROWS && bord[rij][kolom]==kleur){
            aantal++;
            rij+=verticaal;
            kolom+=horizontaal;
        }
        return aantal;
    }
}
