package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de operación exitosa.
 * Solicita al usuario que retire su tarjeta y billete.
 */
public class SuccessScreen extends Screen {
    
    public SuccessScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    protected void configureButtons() {
        super.configureButtons();
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        
        Translator t = context.getTranslator();
        
        // Configurar modo de pantalla reducida
        kiosk.setMode(0);
        
        // Configurar título
        kiosk.setTitle(t.translate("Operación completada"));
        
        // Configurar descripción
        String description = t.translate("Operación realizada con éxito") + "\n\n" +
                           t.translate("Por favor, retire:") + "\n" +
                           "- " + t.translate("Su tarjeta de crédito") + "\n" +
                           "- " + t.translate("Su billete");
        
        kiosk.setDescription(description);
        
        // Esperar 10 segundos o que se retire la tarjeta
        char event = kiosk.waitEvent(10);
        
        if (event == '2') { // Tarjeta retirada
            // Esperar un poco más para que el usuario vea el mensaje
            kiosk.waitEvent(2);
        }
        
        // Volver a la pantalla de bienvenida
        return new WelcomeScreen(kiosk);
    }
}