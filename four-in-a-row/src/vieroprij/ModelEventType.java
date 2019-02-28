package vieroprij;

/**
 * Differentiate model events.
 * For now we only implement the game-over event.
 * We might add more event types in the future,
 * like DiskDropped or MoveCompleted ...
 */
enum ModelEventType {
    GameOver, NewGame
}

