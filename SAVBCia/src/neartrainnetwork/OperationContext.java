package neartrainnetwork;

import java.math.BigDecimal;

/**
 * Contexto de una operación de compra de billete.
 * Mantiene el estado actual: estaciones seleccionadas, traductor, precio, etc.
 */
public class OperationContext {
    private TrainStation origin;
    private TrainStation destination;
    private Translator translator;
    private BigDecimal price;
    private final TrainNetwork trainNetwork;
    
    /**
     * Constructor del contexto de operación.
     * @param translator Traductor actual
     * @param trainNetwork Red de trenes
     */
    public OperationContext(Translator translator, TrainNetwork trainNetwork) {
        this.translator = translator;
        this.trainNetwork = trainNetwork;
        this.origin = null;
        this.destination = null;
        this.price = null;
    }
    
    /**
     * Establece la estación de origen.
     */
    public void setOrigin(TrainStation origin) {
        this.origin = origin;
    }
    
    /**
     * Establece la estación de destino y calcula el precio automáticamente.
     */
    public void setDestination(TrainStation destination) {
        this.destination = destination;
        
        // Calcular precio automáticamente cuando se establece el destino
        if (origin != null && destination != null) {
            this.price = trainNetwork.getPrice(origin, destination);
        }
    }
    
    /**
     * Establece el traductor actual.
     */
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
    
    /**
     * Obtiene el traductor actual.
     */
    public Translator getTranslator() {
        return translator;
    }
    
    /**
     * Obtiene la red de trenes.
     */
    public TrainNetwork getTrainNetwork() {
        return trainNetwork;
    }
    
    /**
     * Obtiene el precio calculado.
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Obtiene la estación de origen.
     */
    public TrainStation getOrigin() {
        return origin;
    }
    
    /**
     * Obtiene la estación de destino.
     */
    public TrainStation getDestination() {
        return destination;
    }
    
    /**
     * Genera una descripción de la operación para mostrar al usuario.
     * @return Descripción traducida de la operación
     */
    public String getDescription() {
        if (origin == null || destination == null) {
            return "";
        }
        
        String template = translator.translate("Billete de {origen} a {destino}");
        String details = translator.translate("Detalles de la compra:");
        String priceLabel = translator.translate("Precio a cobrar:");
        String fromZone = translator.translate("Zona");
        String toZone = translator.translate("a Zona");
        
        // Reemplazar placeholders
        String description = template.replace("{origen}", origin.getName())
                                     .replace("{destino}", destination.getName());
        
        String priceStr = (price != null) ? String.format("%.2f €", price) : "0.00 €";
        
        return description + "\n\n" + 
               details + "\n" + 
               priceLabel + " " + priceStr + "\n" + 
               fromZone + " " + origin.getZone() + " " + toZone + " " + destination.getZone();
    }
}