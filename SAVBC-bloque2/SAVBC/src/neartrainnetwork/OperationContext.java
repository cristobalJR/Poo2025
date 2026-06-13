package neartrainnetwork;

import java.math.BigDecimal;

/**
 * Holds the details of the purchase in progress. Screens pass this object
 * around and fill it in. It also gives access to the shared services
 * (translator manager and train network) that the screens need.
 */
public class OperationContext {

    private TrainStation origin;
    private TrainStation destination;
    private Translator translator;
    private final TranslatorManager translatorManager;
    private final TrainNetwork trainNetwork;

    public OperationContext(TranslatorManager translatorManager, TrainNetwork trainNetwork) {
        this.translatorManager = translatorManager;
        this.trainNetwork = trainNetwork;
        this.translator = translatorManager.getCurrentTranslator();
    }

    public TrainStation getOrigin() {
        return origin;
    }

    public TrainStation getDestination() {
        return destination;
    }

    public Translator getTranslator() {
        return translator;
    }

    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }

    public TrainNetwork getTrainNetwork() {
        return trainNetwork;
    }

    public void setOrigin(TrainStation origin) {
        this.origin = origin;
    }

    public void setDestination(TrainStation destination) {
        this.destination = destination;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    /** Clears the selected stations to start a new purchase. */
    public void reset() {
        this.origin = null;
        this.destination = null;
    }

    /** Price of the current journey, delegated to the train network. */
    public BigDecimal getPrice() {
        return trainNetwork.getPrice(origin, destination);
    }

    /** Human-readable, translated summary of the operation. */
    public String getDescription() {
        BigDecimal price = getPrice();
        return translator.translate("Origen") + ": " + origin.getName() + "\n"
             + translator.translate("Destino") + ": " + destination.getName() + "\n"
             + translator.translate("El precio de la compra es") + " " + price + " " + "\u20AC";
    }
}
