package neartrainnetwork;

/**
 * Clase principal del Sistema Automático de Venta de Billetes de Cercanías (SAVBC).
 * Punto de entrada de la aplicación.
 */
public class CommuterTicketDispenser {
    
    /**
     * Método main que inicia la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Crear directorio de logs si no existe
        java.io.File logsDir = new java.io.File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        
        // Crear y ejecutar el gestor del dispensador
        TicketDispenserManager manager = new TicketDispenserManager();
        manager.start();
    }
}

//public class CommuterTicketDispenser {
//
//    public static void main(String[] args) {
//        
//        SelfOrderKiosk kiosk = new SelfOrderKiosk();
//        kiosk.setTitle("Venta de tickets de Cercanías");
//        kiosk.setDescription("Vaya texto largo que estoy poniendo aquí\nAdemás, puedo poner retornos de carro.\n\nY más cosas.");
//        kiosk.setOption('A', "Pulsame para dibujar una imagen");
//        kiosk.setOption('B', "Pulsame para cambiar a modo de muchos botones");
//        kiosk.setOption('C', "Lee tarjeta");
//        kiosk.setOption('E', null);
//        kiosk.setOption('F', "Pulsame para volver al modo de pocos botones");
//        while (true) {
//            char option = kiosk.waitEvent(30);
//            switch (option) {
//                case 'A' -> kiosk.setImage("./config/TrainNetwork.png");
//                case 'B' -> kiosk.setMode(1);
//                case 'F' -> kiosk.setMode(0);
//                case '1' -> kiosk.setImage(null);
//                case 'C' -> kiosk.setDescription(Long.toString(kiosk.getCardNumber()));
//                case 'U' -> kiosk.setTitle("Has pulsado el último botón");
//            }
//        }
//    }
//    
//}
