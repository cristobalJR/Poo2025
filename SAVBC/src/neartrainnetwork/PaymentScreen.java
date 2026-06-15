package neartrainnetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sienens.SelfOrderKiosk;
import urjc.UrjcBankServer;

/**
 * Pantalla de pago: muestra el resumen de la compra, espera la tarjeta, cobra a
 * traves del banco, imprime el billete y guarda el pago en el registro del dia.
 */
public class PaymentScreen extends Screen {

    private static final char CANCEL = 'F';
    private static final char CARD_INSERTED = '1';
    private static final String LOG_DIRECTORY = "log";

    private final UrjcBankServer bank;

    public PaymentScreen(SelfOrderKiosk kiosk) {
        super(kiosk);
        this.bank = new UrjcBankServer();
    }

    @Override
    public Screen show(OperationContext operationContext) {
        SelfOrderKiosk kiosk = getSelfOrderKiosk();
        Translator translator = operationContext.getTranslator();

        String worksNotice = "";
        if (operationContext.getTrainNetwork()
                .isStationUnderWorks(operationContext.getDestination())) {
            worksNotice = "\n\n" + translator.translate("La estación de destino está en obras");
        }

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Introduzca tarjeta de crédito"));
        kiosk.setDescription(operationContext.getDescription() + worksNotice);
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(30);
            if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            }
            if (event == CARD_INSERTED) {
                BigDecimal price = operationContext.getPrice();
                int amount = price.multiply(BigDecimal.valueOf(100)).intValueExact();
                long cardNumber = kiosk.getCardNumber();

                boolean done;
                try {
                    done = bank.doOperation(cardNumber, -amount);
                } catch (Exception e) {
                    return new CommunicationErrorScreen(kiosk, bank);
                }

                if (done) {
                    printTicket(operationContext);
                    writePaymentToLog(operationContext);
                    return new SuccessScreen(kiosk);
                }

                kiosk.setDescription(
                        translator.translate("Pago rechazado. Inténtelo de nuevo o cancele.")
                        + "\n\n" + operationContext.getDescription() + worksNotice);
            }
        }
    }

    /** Monta el billete (cabecera, resumen y fecha) y lo manda imprimir. */
    private void printTicket(OperationContext operationContext) {
        Translator translator = operationContext.getTranslator();
        List<String> ticket = new ArrayList<>();
        ticket.add(translator.translate("Billete de Cercanías"));
        ticket.addAll(Arrays.asList(operationContext.getDescription().split("\n")));
        ticket.add(translator.translate("Fecha") + ": " + LocalDate.now());
        getSelfOrderKiosk().print(ticket);
    }

    /** Apunta el pago en el log del día (log/AAAA-MM-DD.txt), añadiéndolo al final. */
    private void writePaymentToLog(OperationContext operationContext) {
        new File(LOG_DIRECTORY).mkdir();
        String fileName = LOG_DIRECTORY + "/" + LocalDate.now() + ".txt";
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, true);
            writer.write(operationContext.getDescription());
            writer.write(System.lineSeparator());
            writer.write("----" + System.lineSeparator());
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
