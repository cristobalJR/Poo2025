
package neartrainnetwork;

import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pantalla de pago con tarjeta de crédito.
 * Gestiona el cobro y la impresión del billete.
 */
public class PaymentScreen extends Screen {
    private final UrjcBankServer bankServer;
    
    public PaymentScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.bankServer = new UrjcBankServer();
    }
    
    @Override
    protected void configureButtons() {
        super.configureButtons();
    }
    
    @Override
    public Screen show(OperationContext context) {
        configureButtons();
        
        Translator t = context.getTranslator();
        
        // Configurar modo de pantalla reducida
        kiosk.setMode(0);
        
        // Configurar título
        kiosk.setTitle(t.translate("Introduzca tarjeta de crédito"));
        
        // Configurar descripción con resumen de la operación
        String description = context.getDescription();
        kiosk.setDescription(description);
        
        // Configurar botón de cancelar
        kiosk.setOption('F', t.translate("Cancelar"));
        
        // Esperar inserción de tarjeta o cancelación (30 segundos)
        char event = kiosk.waitEvent(30);
        
        if (event == 'F') { // Cancelar
            // Volver a pantalla de bienvenida
            return new WelcomeScreen(kiosk);
            
        } else if (event == '1') { // Tarjeta insertada
            // Obtener número de tarjeta
            long cardNumber = kiosk.getCardNumber();
            
            // Obtener precio y convertir a céntimos (int)
            BigDecimal price = context.getPrice();
            int amountInCents = price.multiply(new BigDecimal("100")).intValue();
            
            try {
                // Intentar realizar el cobro
                boolean success = bankServer.doOperation(cardNumber, amountInCents);
                
                if (success) {
                    // Cobro exitoso
                    // Imprimir billete
                    printTicket(context);
                    
                    // Registrar en log
                    writePaymentToLog(context);
                    
                    // Mostrar pantalla de éxito
                    return new SuccessScreen(kiosk);
                    
                } else {
                    // Saldo insuficiente
                    kiosk.setDescription(t.translate("Saldo insuficiente. Intente con otra tarjeta."));
                    kiosk.waitEvent(5);
                    
                    // Volver a intentar
                    return this;
                }
                
            } catch (Exception e) {
                // Error de comunicación con el banco
                return new CommunicationErrorScreen(kiosk);
            }
            
        } else if (event == '0') { // Timeout
            // Volver a pantalla de bienvenida
            return new WelcomeScreen(kiosk);
        }
        
        return this;
    }
    
    /**
     * Imprime el billete con los detalles de la operación.
     */
    private void printTicket(OperationContext context) {
        java.util.List<String> ticketLines = new java.util.ArrayList<>();
        
        Translator t = context.getTranslator();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);
        
        ticketLines.add("================================");
        ticketLines.add("  " + t.translate("BILLETE CERCANIAS MADRID"));
        ticketLines.add("================================");
        ticketLines.add("");
        ticketLines.add(t.translate("Fecha:") + " " + dateTime);
        ticketLines.add("");
        ticketLines.add(t.translate("Origen:") + " " + context.getOrigin().getName());
        ticketLines.add(t.translate("Zona:") + " " + context.getOrigin().getZone());
        ticketLines.add("");
        ticketLines.add(t.translate("Destino:") + " " + context.getDestination().getName());
        ticketLines.add(t.translate("Zona:") + " " + context.getDestination().getZone());
        ticketLines.add("");
        ticketLines.add(t.translate("Precio:") + " " + String.format("%.2f €", context.getPrice()));
        ticketLines.add("================================");
        ticketLines.add(t.translate("Gracias por viajar con nosotros"));
        ticketLines.add("================================");
        
        kiosk.print(ticketLines);
    }
    
    /**
     * Registra la venta en el archivo de log del día.
     */
    private void writePaymentToLog(OperationContext context) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        String fileName = "logs/ventas_" + now.format(dateFormatter) + ".txt";
        String timestamp = now.format(timeFormatter);
        
        String logLine = String.format("%s | %s (%s) -> %s (%s) | %.2f€%n",
            timestamp,
            context.getOrigin().getName(),
            context.getOrigin().getZone(),
            context.getDestination().getName(),
            context.getDestination().getZone(),
            context.getPrice()
        );
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(logLine);
        } catch (IOException e) {
            System.err.println("Error escribiendo en log: " + e.getMessage());
        }
    }
}