package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Base for screens that show a list longer than the available buttons and need
 * paging. In the kiosk's "many buttons" mode (21 buttons, 'A'..'U') the layout
 * used here is:
 *   - 'A'..'O' : up to 15 selection buttons (one page of items)
 *   - 'R'      : previous page  ("<<")
 *   - 'U'      : next page      (">>")
 *   - 'P'      : cancel
 */
public abstract class CarruselScreen extends Screen {

    protected static final char PREVIOUS = 'R';
    protected static final char NEXT = 'U';
    protected static final char CANCEL = 'P';
    protected static final int PAGE_SIZE = 15;

    protected int index = 0;

    protected CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    /** Switches to the many-buttons mode and sets the two paging buttons. */
    protected void configureNavigationButtons() {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(1);
        kiosk.setOption(PREVIOUS, "<<");
        kiosk.setOption(NEXT, ">>");
    }

    /** Puts one page of items on the selection buttons, starting at 'A'. */
    protected void configureSelectionButtons(Object[] page) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        for (char option = 'A'; option < 'A' + PAGE_SIZE; option++) {
            kiosk.setOption(option, null); // clear in case the last page was shorter
        }
        char option = 'A';
        for (Object item : page) {
            kiosk.setOption(option, item.toString());
            option++;
        }
    }

    /** Moves one page forward/backward without leaving the valid range. */
    protected void updateIndex(char event, int total) {
        if (event == NEXT && index + PAGE_SIZE < total) {
            index += PAGE_SIZE;
        } else if (event == PREVIOUS && index - PAGE_SIZE >= 0) {
            index -= PAGE_SIZE;
        }
    }
}
