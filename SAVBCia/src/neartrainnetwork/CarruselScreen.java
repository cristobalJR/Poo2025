package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Clase abstracta para pantallas con navegación tipo carrusel.
 * Permite mostrar listas de elementos con navegación por páginas.
 */
public abstract class CarruselScreen extends Screen {
    protected int currentIndex;
    protected static final int ITEMS_PER_PAGE = 15; // Botones de A a O (15 opciones)
    
    public CarruselScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.currentIndex = 0;
    }
    
    /**
     * Configura los botones de navegación (anterior/siguiente).
     * R: << (anterior), U: >> (siguiente)
     */
    protected void configureNavigationButtons() {
        kiosk.setOption('R', "<<");
        kiosk.setOption('U', ">>");
    }
    
    /**
     * Configura los botones de selección con los objetos del array.
     * Usa los botones de A a O (15 posiciones).
     * @param items Array de objetos a mostrar (solo el rango visible)
     */
    protected void configureSelectionButtons(Object[] items) {
        // Limpiar botones de selección
        for (char c = 'A'; c <= 'O'; c++) {
            kiosk.setOption(c, null);
        }
        
        // Configurar botones con los items
        char button = 'A';
        for (Object item : items) {
            if (button > 'O') break; // Máximo 15 opciones
            kiosk.setOption(button, item.toString());
            button++;
        }
    }
    
    /**
     * Actualiza el índice actual según la navegación.
     * @param totalItems Número total de items
     * @param event Evento recibido ('R' para anterior, 'U' para siguiente)
     */
    protected void updateIndex(int totalItems, char event) {
        if (event == 'R') { // Anterior
            currentIndex -= ITEMS_PER_PAGE;
            if (currentIndex < 0) {
                currentIndex = 0;
            }
        } else if (event == 'U') { // Siguiente
            currentIndex += ITEMS_PER_PAGE;
            if (currentIndex >= totalItems) {
                currentIndex = Math.max(0, totalItems - ITEMS_PER_PAGE);
            }
        }
    }
    
    /**
     * Calcula el rango de items a mostrar en la página actual.
     * @param items Array completo de items
     * @return Subarray con los items de la página actual
     */
    protected <T> T[] getCurrentPageItems(T[] items) {
        int startIndex = currentIndex;
        int endIndex = Math.min(currentIndex + ITEMS_PER_PAGE, items.length);
        
        java.util.Arrays.copyOfRange(items, startIndex, endIndex);
        
        return java.util.Arrays.copyOfRange(items, startIndex, endIndex);
    }
    
    /**
     * Convierte un evento de botón (A-O) a un índice relativo (0-14).
     */
    protected int eventToIndex(char event) {
        return event - 'A';
    }
    
    /**
     * Verifica si un evento corresponde a un botón de selección (A-O).
     */
    protected boolean isSelectionButton(char event) {
        return event >= 'A' && event <= 'O';
    }
}