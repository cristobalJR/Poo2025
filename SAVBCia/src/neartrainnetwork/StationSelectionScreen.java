package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de selección de estación.
 * Se utiliza tanto para seleccionar la estación de origen como la de destino.
 */
public class StationSelectionScreen extends CarruselScreen {
    private final String screenTitle;
    private final boolean isOriginSelection;
    
    /**
     * Constructor de la pantalla de selección de estación.
     * @param screenTitle Título de la pantalla (traducido)
     * @param kiosk Referencia al kiosco
     * @param isOriginSelection true si es selección de origen, false si es destino
     */
    public StationSelectionScreen(String screenTitle, SelfOrderKiosk kiosk, boolean isOriginSelection) {
        super(kiosk);
        this.screenTitle = screenTitle;
        this.isOriginSelection = isOriginSelection;
    }
    
    @Override
    public Screen show(OperationContext context) {
        Translator t = context.getTranslator();
        TrainNetwork tn = context.getTrainNetwork();
        
        // Obtener todas las estaciones
        TrainStation[] stations = tn.getStationArray();
        
        // Configurar modo de pantalla expandida
        kiosk.setMode(1);
        
        // Configurar título traducido
        kiosk.setTitle(t.translate(screenTitle));
        
        // Bucle de navegación
        while (true) {
            // Configurar botones de navegación
            configureNavigationButtons();
            
            // Obtener estaciones de la página actual
            TrainStation[] pageStations = getCurrentPageItems(stations);
            
            // Configurar botones de selección
            configureSelectionButtons(pageStations);
            
            // Botón de cancelar
            kiosk.setOption('P', t.translate("Cancelar"));
            
            // Esperar evento
            char event = kiosk.waitEvent(60);
            
            // Procesar evento
            if (event == 'P') { // Cancelar
                // Crear nuevo contexto y volver a pantalla de bienvenida
                OperationContext newContext = new OperationContext(
                    context.getTranslator(),
                    context.getTrainNetwork()
                );
                return new WelcomeScreen(kiosk);
                
            } else if (event == 'R' || event == 'U') { // Navegación
                updateIndex(stations.length, event);
                
            } else if (isSelectionButton(event)) { // Selección de estación
                int index = eventToIndex(event);
                if (index < pageStations.length) {
                    TrainStation selectedStation = pageStations[index];
                    
                    if (isOriginSelection) {
                        // Seleccionar origen y pasar a selección de destino
                        context.setOrigin(selectedStation);
                        return new StationSelectionScreen(
                            t.translate("Seleccione estación de destino"),
                            kiosk,
                            false
                        );
                    } else {
                        // Seleccionar destino y pasar a pantalla de pago
                        context.setDestination(selectedStation);
                        return new PaymentScreen(kiosk);
                    }
                }
                
            } else if (event == '0') { // Timeout
                OperationContext newContext = new OperationContext(
                    context.getTranslator(),
                    context.getTrainNetwork()
                );
                return new WelcomeScreen(kiosk);
            }
        }
    }
}