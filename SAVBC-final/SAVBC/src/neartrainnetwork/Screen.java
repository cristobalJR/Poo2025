package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Clase base de TODAS las pantallas del kiosco.
 *
 * Idea central de la práctica: cada pantalla sabe pintarse y, al terminar,
 * devuelve la pantalla que debe mostrarse a continuación. El gestor solo
 * encadena esas devoluciones. Por eso el método clave es show(), que es
 * abstracto: cada pantalla concreta (bienvenida, pago...) lo implementa a su
 * manera.
 *
 * Para crear una pantalla nueva: hereda de esta clase (o de CarruselScreen si
 * necesita una lista con paginación), implementa show() y, desde la pantalla
 * que le dé paso, devuelve una instancia de la nueva.
 */
public abstract class Screen {

    // Referencia a la pantalla física del kiosco (la librería). Es de paquete
    // (sin modificador) para que las subclases del mismo paquete la usen
    // directamente; también está getSelfOrderKiosk() por comodidad.
    SelfOrderKiosk kiosk;

    public Screen(SelfOrderKiosk kiosk) {
        this.kiosk = kiosk;
    }

    /**
     * Borra todos los botones (de la 'A' a la 'U', que son los posibles del
     * kiosco). Cada pantalla la llama al principio de su show() para partir de
     * una pantalla limpia y que no queden botones de la pantalla anterior.
     */
    protected void configureButtons() {
        for (char option = 'A'; option <= 'U'; option++) {
            kiosk.setOption(option, null); // null borra el botón
        }
    }

    /**
     * Pinta la pantalla, atiende al usuario y devuelve la SIGUIENTE pantalla.
     * Lo implementa cada subclase. Recibe el OperationContext para leer y
     * escribir los datos de la compra (origen, destino, idioma, precio...).
     */
    public abstract Screen show(OperationContext operationContext);

    public SelfOrderKiosk getSelfOrderKiosk() {
        return kiosk;
    }
}
