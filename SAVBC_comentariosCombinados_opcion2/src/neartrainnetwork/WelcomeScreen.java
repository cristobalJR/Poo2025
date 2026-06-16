package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de inicio: deja elegir entre cambiar el idioma o iniciar la compra.
 * Es la pantalla a la que se vuelve al cancelar o al terminar una compra.
 */
// EXTENSION ->
/**
 * Pantalla de inicio. Muestra dos botones: cambiar idioma o iniciar compra.
 * Es la pantalla "casa": a ella se vuelve al cancelar o al terminar una compra.
 *
 * Usa el modo de 6 botones (setMode(0)), que además muestra una descripción.
 */
public class WelcomeScreen extends Screen {

    public WelcomeScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        // EXTENSION -> Cogemos el traductor actual: si el usuario cambió el idioma, los
        // textos de esta pantalla saldrán ya en ese idioma.
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Venta de billetes de Cercanías"));
        kiosk.setDescription(translator.translate("Bienvenido. Seleccione una opción."));
        kiosk.setOption('A', translator.translate("Cambiar idioma"));
        kiosk.setOption('B', translator.translate("Iniciar compra"));

        // EXTENSION -> Esperamos a que pulse 'A' o 'B'. waitEvent devuelve el botón pulsado,
        // o '0' si pasa el tiempo sin tocar nada (seguimos esperando), o '1'/'2'
        // si mete/saca una tarjeta (aquí lo ignoramos).
        while (true) {
            char event = kiosk.waitEvent(30);
            switch (event) {
                case 'A' -> {
                    return new LanguageSelectionScreen(kiosk);
                }
                case 'B' -> {
                    // EXTENSION -> Empieza una compra nueva: limpiamos origen y destino de
                    // una posible compra anterior para no arrastrar datos viejos.
                    operationContext.reset();
                    return new StationSelectionScreen(
                            "Seleccione la estación de origen", kiosk);
                }
                default -> {
                    // EXTENSION -> '0' (sin pulsación) o eventos de tarjeta: no hacemos nada.
                }
            }
        }
    }
}
