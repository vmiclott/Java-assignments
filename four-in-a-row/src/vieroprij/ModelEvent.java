
package vieroprij;

/**
 * The event which occurs when the model changes.
 */
public class
        ModelEvent {
    
    private final ModelEventType type;
    
    /**
     * Create a new model event.
     * @param type The model event type.
     */
    public ModelEvent(ModelEventType type) {
        this.type = type;
    }
    
    /**
     * @return The current model event type.
     */
    public ModelEventType getEventType() {
        return type;
    }
}
