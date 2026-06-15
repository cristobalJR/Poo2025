package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Clase base de todas las pantallas. Cada pantalla se pinta y devuelve la
 * siguiente pantalla que hay que mostrar (método show).
 */
public abstract class Screen {

    SelfOrderKiosk kiosk;

    public Screen(SelfOrderKiosk kiosk) {
        this.kiosk = kiosk;
    }

    /** Borra todos los botones ('A'..'U'); se llama al empezar cada pantalla. */
    protected void configureButtons() {
        for (char option = 'A'; option <= 'U'; option++) {
            kiosk.setOption(option, null);
        }
    }

    /** Pinta la pantalla, atiende al usuario y devuelve la siguiente pantalla. */
    public abstract Screen show(OperationContext operationContext);

    public SelfOrderKiosk getSelfOrderKiosk() {
        return kiosk;
    }
}
