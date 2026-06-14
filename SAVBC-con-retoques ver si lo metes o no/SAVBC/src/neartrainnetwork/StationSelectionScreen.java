package neartrainnetwork;

import java.util.Arrays;
import sienens.SelfOrderKiosk;

/**
 * Pantalla de selección de estación. La MISMA clase sirve para el origen y para
 * el destino: por eso recibe el título en el constructor.
 *
 * ¿Cómo sabe si pide origen o destino? Mira el contexto: si todavía no hay
 * origen, lo que elija el usuario es el origen (y pasa a pedir el destino); si
 * ya hay origen, lo elegido es el destino (y pasa al pago). Por eso es
 * importante que la bienvenida llame a reset() al empezar una compra.
 */
public class StationSelectionScreen extends CarruselScreen {

    private final String screenTitle; // título a mostrar (origen o destino)

    public StationSelectionScreen(String screenTitle, SelfOrderKiosk kiosk) {
        super(kiosk);
        this.screenTitle = screenTitle;
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        // Lista completa de estaciones (únicas y ordenadas), de la red de trenes.
        TrainStation[] stations = operationContext.getTrainNetwork().getStationArray();

        configureButtons();
        kiosk.setTitle(translator.translate(screenTitle));
        configureNavigationButtons(stations.length);              // muestra << y >> (hay muchas)
        kiosk.setOption(CANCEL, translator.translate("Cancelar")); // botón 'P'

        index = 0;
        while (true) {
            int end = Math.min(index + PAGE_SIZE, stations.length);
            configureSelectionButtons(Arrays.copyOfRange(stations, index, end));

            char event = kiosk.waitEvent(30);
            if (event == PREVIOUS || event == NEXT) {
                updateIndex(event, stations.length);    // cambiar de página
            } else if (event == CANCEL) {
                return new WelcomeScreen(kiosk);         // cancelar -> inicio
            } else {
                int selected = event - 'A';              // posición dentro de la página
                if (selected >= 0 && selected < end - index) {
                    return select(operationContext, stations[index + selected]);
                }
            }
        }
    }

    /**
     * Guarda la estación elegida donde toca y devuelve la siguiente pantalla:
     * si aún no había origen, esto era el origen -> pedimos destino; si ya había
     * origen, esto era el destino -> vamos al pago.
     */
    private Screen select(OperationContext operationContext, TrainStation station) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        if (operationContext.getOrigin() == null) {
            operationContext.setOrigin(station);
            return new StationSelectionScreen("Seleccione la estación de destino", kiosk);
        }
        operationContext.setDestination(station);
        return new PaymentScreen(kiosk);
    }
}
