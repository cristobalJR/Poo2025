package neartrainnetwork;

import sienens.SelfOrderKiosk;

/**
 * Pantalla de selección de idioma.
 * Permite al usuario elegir el idioma de la interfaz.
 */
public class LanguageSelectionScreen extends CarruselScreen {
    
    public LanguageSelectionScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
    }
    
    @Override
    public Screen show(OperationContext context) {
        Translator t = context.getTranslator();
        TrainNetwork tn = context.getTrainNetwork();
        
        // Obtener todos los traductores disponibles
        Translator[] translators = getTranslatorsFromContext(context);
        
        // Configurar modo de pantalla expandida
        kiosk.setMode(1);
        
        // Configurar título
        kiosk.setTitle(t.translate("Seleccione idioma"));
        
        // Bucle de navegación
        while (true) {
            // Configurar botones de navegación
            configureNavigationButtons();
            
            // Obtener items de la página actual
            Translator[] pageTranslators = getCurrentPageItems(translators);
            
            // Configurar botones de selección
            configureSelectionButtons(pageTranslators);
            
            // Botón de cancelar
            kiosk.setOption('P', t.translate("Cancelar"));
            
            // Esperar evento
            char event = kiosk.waitEvent(60);
            
            // Procesar evento
            if (event == 'P') { // Cancelar
                // Volver a pantalla de bienvenida
                return new WelcomeScreen(kiosk);
                
            } else if (event == 'R' || event == 'U') { // Navegación
                updateIndex(translators.length, event);
                
            } else if (isSelectionButton(event)) { // Selección de idioma
                int index = eventToIndex(event);
                if (index < pageTranslators.length) {
                    // Cambiar el idioma
                    context.setTranslator(pageTranslators[index]);
                    
                    // Volver a pantalla de bienvenida
                    return new WelcomeScreen(kiosk);
                }
                
            } else if (event == '0') { // Timeout
                return new WelcomeScreen(kiosk);
            }
        }
    }
    
    /**
     * Obtiene el array de traductores del TranslatorManager.
     * Como no tenemos acceso directo al TranslatorManager desde el contexto,
     * necesitamos una forma de obtenerlo. Por simplicidad, lo reconstruimos.
     */
    private Translator[] getTranslatorsFromContext(OperationContext context) {
        // Crear un TranslatorManager temporal para obtener los traductores
        TranslatorManager tm = new TranslatorManager();
        return tm.getTranslatorArray();
    }
}