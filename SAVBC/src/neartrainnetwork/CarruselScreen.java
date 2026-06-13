package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Base for screens that show a list too long for the buttons and need paging.
 * The paging helpers (navigation/selection) are added in the next block.
 */
public abstract class CarruselScreen extends Screen {

    protected int index = 0;

    protected CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
}
