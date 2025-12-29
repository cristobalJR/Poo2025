package neartrainnetwork;


public class TrainStation {
    
    private String name;
    private String zone;
    private String line;
    
    //constructor
    public TrainStation(String name, String zone, String line){
        
    }
    
    //getters
    public String getName(){
        return name;
    }
    public String getZone(){
        return zone;
    }
    
    
    public int compareTo(TrainStation trainStation){
        return 1;
    }
    
    //sobrescribimos o sobrecargamos toString
    @Override
    public String toString(){
        return this.name+this.zone+this.line;
    }
    
    @Override
    public int hashCode(){
        return 1;
    }
    
    public boolean equals(TrainStation trainstation){
        return true;
    }
    
}
