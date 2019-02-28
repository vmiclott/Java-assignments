
package vieroprij;

/**
 * The interface which has to be implemented by listeners to model events.
 */
public interface ModelEventListener {
    
    /**
     * Callback for a listener which is called when a model event occurs.
     * @param event The current model event.
     */
    public void eventOccurred(ModelEvent event);
}
