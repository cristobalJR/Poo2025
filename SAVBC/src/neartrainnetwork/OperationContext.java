package neartrainnetwork;

public class OperationContext {
    
    private TrainStation origin;
    private TrainStation destination;
    private Translator translator;
    private int price;
    private TrainNetwork trainNetwork;

    
    //constructor
    public OperationContext(Translator translator, TrainNetwork trainNetwork){
        
    } 
    //getters
    public TrainStation getOrigin(){
        return origin;
    }
    public TrainStation getDestination() {
        return destination;
    }
    public Translator getTranslator() {
        return translator;
    }
    public int getPrice() {       
        return price;
    }
    public TrainNetwork getTrainNetwork() {        
        return trainNetwork;
    }
    public String getDescription() {
        
        return "descripcion";
    }
    
    
    //setters
    public void setOrigin(TrainStation origin){
        
    }
    public void setDestination(TrainStation origin) {
        
    }
    public void setTranslator(Translator translator){
        
    }
}
