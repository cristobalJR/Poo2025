package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de error de comunicación con el banco.
 * Comprueba cada 5 segundos si la conexión se ha restablecido.
 */
public class CommunicationErrorScreen extends Screen {
    private final UrjcBankServer bankServer;
    
    public CommunicationErrorScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.bankServer = new UrjcBankServer();
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
        kiosk.setTitle(t.translate("Servicio no disponible"));
        
        // Configurar descripción
        String description = t.translate("Error de comunicación con el servidor bancario") + "\n\n" +
                           t.translate("Comprobando conexión...") + "\n\n" +
                           t.translate("Por favor, retire su tarjeta si está insertada");
        
        kiosk.setDescription(description);
        
        // Bucle de comprobación cada 5 segundos
        while (true) {
            // Esperar 5 segundos
            char event = kiosk.waitEvent(5);
            
            // Si se retira la tarjeta
            if (event == '2') {
                // Actualizar mensaje
                kiosk.setDescription(
                    t.translate("Error de comunicación con el servidor bancario") + "\n\n" +
                    t.translate("Comprobando conexión...")
                );
            }
            
            // Comprobar si la comunicación está disponible
            if (bankServer.comunicationAvaiable()) {
                // Conexión restablecida, volver a pantalla de bienvenida
                kiosk.setDescription(
                    t.translate("Conexión restablecida") + "\n\n" +
                    t.translate("Volviendo al inicio...")
                );
                kiosk.waitEvent(2);
                return new WelcomeScreen(kiosk);
            }
        }
    }
}