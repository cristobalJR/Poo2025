package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Gestor principal del sistema de venta de billetes.
 * Coordina la inicialización y el bucle principal de pantallas.
 */
public class TicketDispenserManager {
    private final TrainNetwork trainNetwork;
    private final TranslatorManager translatorManager;
    private final SelfOrderKiosk kiosk;
    
    /**
     * Constructor que inicializa todos los componentes del sistema.
     */
    public TicketDispenserManager() {
        // Cargar la red de trenes y tarifas
        this.trainNetwork = new TrainNetwork();
        
        // Cargar los traductores
        this.translatorManager = new TranslatorManager();
        
        // Crear el kiosco
        this.kiosk = new SelfOrderKiosk();
    }
    
    /**
     * Inicia el sistema y entra en el bucle principal de pantallas.
     */
    public void start() {
        // Crear contexto de operación inicial
        OperationContext context = new OperationContext(
            translatorManager.getCurrentTranslator(),
            trainNetwork
        );
        
        // Crear pantalla de bienvenida inicial
        Screen currentScreen = new WelcomeScreen(kiosk);
        
        // Bucle principal: cada pantalla devuelve la siguiente pantalla
        while (currentScreen != null) {
            currentScreen = currentScreen.show(context);
            
            // Si la pantalla devuelve null, terminar el programa
            if (currentScreen == null) {
                break;
            }
        }
    }
}

//import sienens.SelfOrderKiosk;
//
//public class TicketDispenserManager {
//
//    private TrainNetwork trainNetwork;
//    private TranslatorManager translatorManager;
//    private SelfOrderKiosk kiosk;
//    private OperationContext operationContext;
//
//    public TicketDispenserManager() {
//        // Constructor vacío: la inicialización real se hace en start()
//    }
//
//    public void start() {
//
//        // 1. Inicializar red de trenes y tarifas
//        trainNetwork = new TrainNetwork();
//        trainNetwork.loadStationGraph("config/stations.txt");
//
//        // 2. Inicializar gestor de traducciones
//        translatorManager = new TranslatorManager();
//        translatorManager.loadTranslators();
//        translatorManager.loadDefaultTranslator();
//
//        // 3. Inicializar kiosco (librería proporcionada)
//        kiosk = new SelfOrderKiosk();
//        kiosk.setMode(0); // modo de pocos botones por defecto
//
//        // 4. Crear contexto de operación
//        operationContext = new OperationContext(
//                translatorManager.getCurrentTranslator(),
//                trainNetwork
//        );
//
//        // 5. Pantalla inicial
//        Screen currentScreen = new WelcomeScreen(kiosk);
//
//        // 6. Bucle principal de la aplicación
//        while (currentScreen != null) {
//            currentScreen = currentScreen.show(operationContext);
//        }
//    }
//}