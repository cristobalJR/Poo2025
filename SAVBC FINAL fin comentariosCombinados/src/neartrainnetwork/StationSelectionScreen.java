package neartrainnetwork;

import java.util.Arrays;
import sienens.SelfOrderKiosk;

/**
 * Pantalla para elegir una estación. La misma clase sirve para el origen y para
 * el destino, el título se le pasa en el constructor.
 */
public class StationSelectionScreen extends CarruselScreen {

    private final String screenTitle;

    public StationSelectionScreen(String screenTitle, SelfOrderKiosk kiosk) {
        super(kiosk);
        this.screenTitle = screenTitle;
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();
        TrainStation[] stations = operationContext.getTrainNetwork().getStationArray();

        configureButtons();
        kiosk.setTitle(translator.translate(screenTitle));
        configureNavigationButtons(stations.length);
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        index = 0;
        while (true) {
            int end = Math.min(index + PAGE_SIZE, stations.length);
            configureSelectionButtons(Arrays.copyOfRange(stations, index, end));

            char event = kiosk.waitEvent(30);
            if (event == PREVIOUS || event == NEXT) {
                updateIndex(event, stations.length);
            } else if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            } else {
                int selected = event - 'A';
                if (selected >= 0 && selected < end - index) {
                    return select(operationContext, stations[index + selected]);
                }
            }
        }
    }

    /** Si aun no hay origen, lo elegido es el origen (y se pide el destino) si ya lo hay, es el destino (y se pasa al pago). */
    private Screen select(OperationContext operationContext, TrainStation station) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        if (operationContext.getOrigin() == null) {
            operationContext.setOrigin(station);
            return new StationSelectionScreen("Seleccione la estación de destino", kiosk);
        }
        operationContext.setDestination(station);
        return new FamilyDiscountScreen(kiosk);
    }
}
