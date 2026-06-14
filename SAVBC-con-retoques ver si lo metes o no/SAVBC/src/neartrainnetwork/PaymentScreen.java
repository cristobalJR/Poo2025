package neartrainnetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.naming.CommunicationException;
import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de pago. Resume la compra, espera la tarjeta, cobra a través del
 * banco, imprime el billete y guarda el pago en el registro (log) del día.
 *
 * Puntos delicados, por si la modificas:
 *  - El banco (UrjcBankServer) cobra en CÉNTIMOS y en NEGATIVO (es un cargo).
 *    Por eso pasamos el precio en euros (BigDecimal) a céntimos y le cambiamos
 *    el signo: doOperation(tarjeta, -céntimos).
 *  - doOperation puede lanzar CommunicationException (el banco simula cortes de
 *    comunicación de forma aleatoria). Si ocurre, vamos a la pantalla de error.
 *  - doOperation devuelve true (cobrado) o false (tarjeta no válida o saldo
 *    insuficiente).
 *  - Eventos del kiosco aquí: '1' = tarjeta insertada, 'F' = botón Cancelar.
 */
public class PaymentScreen extends Screen {

    private static final char CANCEL = 'F';            // botón Cancelar (modo 0)
    private static final char CARD_INSERTED = '1';     // evento "tarjeta insertada"
    private static final String LOG_DIRECTORY = "log"; // carpeta del registro diario

    // Conexión con el banco. Creamos una por pantalla de pago; el estado del
    // corte de comunicación es global, así que da igual qué instancia se use.
    private final UrjcBankServer bank;

    public PaymentScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.bank = new UrjcBankServer();
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Introduzca tarjeta de crédito"));
        // La descripción es el resumen de la compra (origen, destino y precio).
        kiosk.setDescription(operationContext.getDescription());
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(30);
            if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            }
            if (event == CARD_INSERTED) {
                // Precio en euros -> céntimos (entero). intValueExact avisaría si
                // por error no fuese un entero exacto (no pasa: son 2 decimales).
                BigDecimal price = operationContext.getPrice();
                int amount = price.multiply(BigDecimal.valueOf(100)).intValueExact();
                long cardNumber = kiosk.getCardNumber();

                boolean done;
                try {
                    done = bank.doOperation(cardNumber, -amount); // un cargo es NEGATIVO
                } catch (CommunicationException e) {
                    // Se cayó la comunicación con el banco: pantalla de espera.
                    return new CommunicationErrorScreen(kiosk, bank);
                }

                if (done) {
                    printTicket(operationContext);       // imprime el billete
                    writePaymentToLog(operationContext); // lo apunta en el log diario
                    return new SuccessScreen(kiosk);
                }

                // Cobro rechazado (tarjeta no válida o sin saldo): avisamos y
                // seguimos esperando otra tarjeta (o que pulse Cancelar).
                kiosk.setDescription(
                        translator.translate("Pago rechazado. Inténtelo de nuevo o cancele.")
                        + "\n\n" + operationContext.getDescription());
            }
        }
    }

    /**
     * Imprime el billete. El kiosco imprime una LISTA de líneas de texto, así
     * que montamos las líneas (cabecera, resumen de la compra y fecha).
     */
    private void printTicket(OperationContext operationContext) {
        Translator translator = operationContext.getTranslator();
        List<String> ticket = new ArrayList<>();
        ticket.add(translator.translate("Billete de Cercanías"));
        // Reutilizamos el resumen, partiéndolo en líneas (separadas por saltos).
        ticket.addAll(Arrays.asList(operationContext.getDescription().split("\n")));
        ticket.add(translator.translate("Fecha") + ": " + LocalDate.now());
        getSelfOrderKiosk().print(ticket);
    }

    /**
     * Guarda el resumen del pago en un fichero de texto. Se usa un fichero por
     * día (log/AAAA-MM-DD.txt) y se va añadiendo al final (cada compra, una
     * entrada). Crea la carpeta 'log' si no existe.
     */
    private void writePaymentToLog(OperationContext operationContext) {
        new File(LOG_DIRECTORY).mkdirs();
        String fileName = LOG_DIRECTORY + "/" + LocalDate.now() + ".txt";
        // try-with-resources: el fichero se cierra solo al terminar.
        // El tercer parámetro 'true' del FileWriter es para AÑADIR (no borrar).
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8, true)) {
            writer.write(operationContext.getDescription());
            writer.write(System.lineSeparator());
            writer.write("----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
