package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Crea las piezas de la aplicación y ejecuta el bucle que va mostrando las pantallas.
 */
// EXTENSION ->
/**
 * Gestor del kiosco. Hace dos cosas:
 *
 *   1) Crear y "enchufar" entre sí las piezas grandes de la aplicación:
 *        - TrainNetwork      -> red de estaciones y cálculo de precios
 *        - TranslatorManager -> idiomas disponibles
 *        - SelfOrderKiosk    -> la pantalla física (la librería del kiosco)
 *        - OperationContext  -> "carpeta" con los datos de la compra en curso
 *
 *   2) Ejecutar el bucle de control: muestra la pantalla actual y se queda con
 *      la pantalla que esa devuelve como "siguiente". Así, cada pantalla decide
 *      a dónde se va después. Este es el patrón central de toda la práctica.
 *
 * Para añadir una pieza compartida por muchas pantallas, créala aquí en start()
 * y pásala a través del OperationContext (el objeto que viaja por todas ellas).
 */
public class TicketDispenserManager {

    private TrainNetwork trainNetwork;
    private TranslatorManager translatorManager;
    private SelfOrderKiosk kiosk;
    private OperationContext operationContext;

    public TicketDispenserManager() {
    }

    /** Crea los objetos y lanza el bucle:cada pantalla se muestra y devuelve la siguiente. */
    // EXTENSION ->
    /** Monta la aplicación y arranca el bucle que va mostrando las pantallas. */
    public void start() {
        // EXTENSION -> 1) Creamos las piezas. El contexto necesita la red y el gestor de
        //    idiomas ya creados, así que va el último.
        trainNetwork = new TrainNetwork();
        translatorManager = new TranslatorManager();
        kiosk = new SelfOrderKiosk();
        operationContext = new OperationContext(translatorManager, trainNetwork);

        // EXTENSION -> 2) Bucle de control. Empezamos en la bienvenida. Cada show() pinta la
        //    pantalla, espera al usuario y devuelve la siguiente. Si alguna
        //    devolviera null, el bucle terminaría (en la práctica no pasa: el
        //    kiosco no "acaba", siempre vuelve a la bienvenida).
        Screen currentScreen = new WelcomeScreen(kiosk);
        while (currentScreen != null) {
            currentScreen = currentScreen.show(operationContext);
        }
    }
}
