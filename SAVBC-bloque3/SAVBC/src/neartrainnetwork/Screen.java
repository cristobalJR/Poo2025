package neartrainnetwork;

import sienens.SelfOrderKiosk;

/** Base class for every kiosk screen. */
public abstract class Screen {

    SelfOrderKiosk kiosk;

    public Screen(SelfOrderKiosk kiosk) {
        this.kiosk = kiosk;
    }

    /** Clears every button so subclasses start from a clean screen in show(). */
    protected void configureButtons() {
        for (char option = 'A'; option <= 'U'; option++) {
            kiosk.setOption(option, null);
        }
    }

    public abstract Screen show(OperationContext operationContext);

    public SelfOrderKiosk getSelfOrderKiosk() {
        return kiosk;
    }
}
