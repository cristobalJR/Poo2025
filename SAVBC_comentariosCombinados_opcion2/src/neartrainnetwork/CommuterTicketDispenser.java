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
// EXTENSION ->
/**
 * Clase principal de la aplicación: su única misión es ARRANCAR el programa.
 *
 * Crea el gestor (TicketDispenserManager) y le cede el control. Toda la lógica
 * de verdad está en el gestor y en las pantallas, no aquí. Si algún día
 * necesitas cambiar cómo arranca todo, este es el punto de entrada (el main).
 */
public class CommuterTicketDispenser {

    public static void main(String[] args) {
        // EXTENSION -> start() no devuelve nunca: el kiosco funciona en bucle hasta que se
        // cierra la ventana.
        TicketDispenserManager manager = new TicketDispenserManager();
        manager.start();
    }
}
