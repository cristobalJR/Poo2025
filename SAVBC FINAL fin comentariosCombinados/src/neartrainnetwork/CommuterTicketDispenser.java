package neartrainnetwork;

/**
 * ----------------------------------------------------------------------------------------------
 * Comentario para los profesores: No he podido corregir el problema con los caracteres del catalán. 
 * Supongo que tiene que ver con el uso de Unicode, pero no he podido solventarlo.
 * -----------------------------------------------------------------------------------------------
 * 
 * Clase principal: arranca la aplicación.
 * 
 */
public class CommuterTicketDispenser {

    public static void main(String[] args) {
        TicketDispenserManager manager = new TicketDispenserManager();
        manager.start();
    }
}
