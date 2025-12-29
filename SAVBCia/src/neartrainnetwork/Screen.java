package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Clase base abstracta para todas las pantallas del sistema.
 * Define la interfaz común que deben implementar todas las pantallas.
 */
public abstract class Screen {
    protected final SelfOrderKiosk kiosk;
    
    /**
     * Constructor de la pantalla.
     * @param kiosk Referencia al kiosco físico
     */
    public Screen(SelfOrderKiosk kiosk) {
        this.kiosk = kiosk;
    }
    
    /**
     * Configura los botones de la pantalla.
     * Este método borra todos los botones para que las clases derivadas
     * puedan configurar solo los que necesitan.
     */
    protected void configureButtons() {
        // Limpiar todos los botones
        for (char c = 'A'; c <= 'U'; c++) {
            kiosk.setOption(c, null);
        }
    }
    
    /**
     * Muestra la pantalla y gestiona la interacción con el usuario.
     * @param context Contexto de la operación actual
     * @return La siguiente pantalla a mostrar, o null para terminar
     */
    public abstract Screen show(OperationContext context);
    
    /**
     * Obtiene la referencia al kiosco.
     */
    protected SelfOrderKiosk getSelfOrderKiosk() {
        return kiosk;
    }
}