package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Base para las pantallas que muestran una LISTA más larga de lo que cabe en
 * los botones y por tanto necesitan paginar (idiomas y estaciones).
 *
 * Usa el modo de muchos botones del kiosco (setMode(1), 21 botones 'A'..'U') y
 * reparte así las teclas:
 *
 *     'A'..'O'  -> hasta 15 elementos de la página actual (los de la lista)
 *     'R'       -> página anterior  ("<<")
 *     'U'       -> página siguiente (">>")
 *     'P'       -> cancelar
 *
 * El campo 'index' es la posición (dentro de la lista completa) del primer
 * elemento de la página que se está viendo; avanza/retrocede de 15 en 15.
 *
 * Si quieres cambiar cuántos elementos se ven por página, toca PAGE_SIZE (con
 * el tope de 15, porque van de la 'A' a la 'O'). Para mover las teclas de
 * navegación o de cancelar, cambia las constantes de abajo.
 */
public abstract class CarruselScreen extends Screen {

    protected static final char PREVIOUS = 'R'; // botón "<<"
    protected static final char NEXT = 'U';      // botón ">>"
    protected static final char CANCEL = 'P';    // botón "Cancelar"
    protected static final int PAGE_SIZE = 15;   // elementos por página ('A'..'O')

    protected int index = 0; // primer elemento visible de la página actual

    protected CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    /** Pone el modo de 21 botones y los dos botones de paginación. */
    protected void configureNavigationButtons() {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        kiosk.setMode(1);
        kiosk.setOption(PREVIOUS, "<<");
        kiosk.setOption(NEXT, ">>");
    }

    /**
     * Coloca una página de elementos en los botones de selección, empezando por
     * la 'A'. En el botón se ve el texto de item.toString() (para una estación
     * es su nombre; para un idioma, el nombre del idioma).
     */
    protected void configureSelectionButtons(Object[] page) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        // Limpiamos primero los 15 huecos de selección, por si la página
        // anterior tenía más elementos que esta (la última suele ir incompleta).
        for (char option = 'A'; option < 'A' + PAGE_SIZE; option++) {
            kiosk.setOption(option, null);
        }
        char option = 'A';
        for (Object item : page) {
            kiosk.setOption(option, item.toString());
            option++;
        }
    }

    /**
     * Mueve la página una hacia delante o hacia atrás, sin salirse de la lista.
     * 'total' es el número de elementos de la lista completa.
     */
    protected void updateIndex(char event, int total) {
        if (event == NEXT && index + PAGE_SIZE < total) {
            index += PAGE_SIZE; // hay más elementos: avanzamos una página
        } else if (event == PREVIOUS && index - PAGE_SIZE >= 0) {
            index -= PAGE_SIZE; // no estamos en la primera: retrocedemos
        }
        // En los extremos no hacemos nada (no hay página anterior/siguiente).
    }
}
