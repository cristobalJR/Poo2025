package neartrainnetwork;

import sienens.SelfOrderKiosk;


public abstract class Screen {
    
    SelfOrderKiosk kiosk;
    
    public Screen(SelfOrderKiosk kiosk){
        this.kiosk = kiosk;
    }
    protected void configureButtons(){//solo lo ve esta clase y sus subclases
    
    }
    public abstract Screen show(OperationContext operationContext);

    public SelfOrderKiosk getSelfOrderKiosk(){
        return kiosk;
    }
    

}
