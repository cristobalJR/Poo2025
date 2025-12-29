package neartrainnetwork;


import sienens.SelfOrderKiosk;

public class TicketDispenserManager {
    private SelfOrderKiosk kiosk;

    public TicketDispenserManager() {
        // Constructor vacío: la inicialización real se hace en start()
    }

    public void start() {
        kiosk = new SelfOrderKiosk();
        kiosk.setMode(0);
        kiosk.setOption('A', "Cambiar Idioma");
        kiosk.setOption('B', "Iniciar Compra");

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