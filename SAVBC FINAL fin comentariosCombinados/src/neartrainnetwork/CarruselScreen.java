package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Base para las pantallas que muestran una lista que no cabe en una sola página
 * y hay que paginar (la selección de idioma y la de estación)*/
public abstract class CarruselScreen extends Screen {

    protected static final char PREVIOUS = 'R';
    protected static final char NEXT = 'U';
    protected static final char CANCEL = 'P';
    protected static final int PAGE_SIZE = 15;

    protected int index = 0;

    protected CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    /** Muestra los botones de anterior y siguiente solo si hay más elementos de los que caben en una pagina */
    protected void configureNavigationButtons(int total) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(1);
        if (total > PAGE_SIZE) {
            kiosk.setOption(PREVIOUS, "<<");
            kiosk.setOption(NEXT, ">>");
        }
    }

    /** Coloca una página de elementos en los botones, empezando por la 'A' */
    protected void configureSelectionButtons(Object[] page) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        for (char option = 'A'; option < 'A' + PAGE_SIZE; option++) {
            kiosk.setOption(option, null);
        }
        char option = 'A';
        for (Object item : page) {
            kiosk.setOption(option, item.toString());
            option++;
        }
    }

    /** Pasa a la página siguiente o anterior sin salirse de la lista. */
    protected void updateIndex(char event, int total) {
        if (event == NEXT && index + PAGE_SIZE < total) {
            index += PAGE_SIZE;
        } else if (event == PREVIOUS && index - PAGE_SIZE >= 0) {
            index -= PAGE_SIZE;
        }
    }
}
