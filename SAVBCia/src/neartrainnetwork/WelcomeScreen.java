package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de bienvenida inicial del sistema.
 * Ofrece dos opciones: comprar billete o cambiar idioma.
 */
public class WelcomeScreen extends Screen {
    
    public WelcomeScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    protected void configureButtons() {
        super.configureButtons();
        // Esta pantalla no necesita configuración adicional de botones
        // ya que usa el modo 0 (pantalla reducida)
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        
        Translator t = context.getTranslator();
        
        // Configurar modo de pantalla reducida
        kiosk.setMode(0);
        
        // Configurar título
        kiosk.setTitle(t.translate("Sistema de Venta de Billetes"));
        
        // Configurar imagen del mapa
        kiosk.setImage("config/TrainNetwork.png");
        
        // Configurar descripción
        kiosk.setDescription(t.translate("Bienvenido al sistema de venta automática de billetes de Cercanías"));
        
        // Configurar botones
        kiosk.setOption('A', t.translate("Cambiar idioma"));
        kiosk.setOption('B', t.translate("Comprar billete"));
        
        // Esperar evento
        char event = kiosk.waitEvent(60);
        
        // Procesar evento
        switch (event) {
            case 'A': // Cambiar idioma
                return new LanguageSelectionScreen(kiosk);
                
            case 'B': // Comprar billete
                return new StationSelectionScreen(
                    t.translate("Seleccione estación de origen"), 
                    kiosk, 
                    true
                );
                
            case '0': // Timeout
                return this; // Volver a mostrar la misma pantalla
                
            default:
                return this;
        }
    }
}