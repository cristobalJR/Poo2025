package neartrainnetwork;

/**
 * Clase principal: arranca la aplicación.
 */
public class CommuterTicketDispenser {

    public static void main(String[] args) {
        TicketDispenserManager manager = new TicketDispenserManager();
        manager.start();
    }
}
