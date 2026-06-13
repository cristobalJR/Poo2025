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
 * Shows the operation summary, waits for the credit card, charges the price
 * through the bank, prints the ticket and records the payment in the daily log.
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

        configureButtons();
        kiosk.setMode(0);
        kiosk.setTitle(translator.translate("Introduzca tarjeta de crédito"));
        kiosk.setDescription(operationContext.getDescription());
        kiosk.setOption(CANCEL, translator.translate("Cancelar"));

        while (true) {
            char event = kiosk.waitEvent(30);
            if (event == CANCEL) {
                return new WelcomeScreen(kiosk);
            }
            if (event == CARD_INSERTED) {
                BigDecimal price = operationContext.getPrice();
                int amount = price.multiply(BigDecimal.valueOf(100)).intValueExact(); // céntimos
                long cardNumber = kiosk.getCardNumber();

                boolean done;
                try {
                    done = bank.doOperation(cardNumber, -amount); // un cobro es negativo
                } catch (CommunicationException e) {
                    return new CommunicationErrorScreen(kiosk, bank);
                }

                if (done) {
                    printTicket(operationContext);
                    writePaymentToLog(operationContext);
                    return new SuccessScreen(kiosk);
                }

                // tarjeta no válida o saldo insuficiente: avisamos y seguimos esperando
                kiosk.setDescription(
                        translator.translate("Pago rechazado. Inténtelo de nuevo o cancele.")
                        + "\n\n" + operationContext.getDescription());
            }
        }
    }

    private void printTicket(OperationContext operationContext) {
        Translator translator = operationContext.getTranslator();
        List<String> ticket = new ArrayList<>();
        ticket.add(translator.translate("Billete de Cercanías"));
        ticket.addAll(Arrays.asList(operationContext.getDescription().split("\n")));
        ticket.add(translator.translate("Fecha") + ": " + LocalDate.now());
        getSelfOrderKiosk().print(ticket);
    }

    private void writePaymentToLog(OperationContext operationContext) {
        new File(LOG_DIRECTORY).mkdirs();
        String fileName = LOG_DIRECTORY + "/" + LocalDate.now() + ".txt";
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8, true)) {
            writer.write(operationContext.getDescription());
            writer.write(System.lineSeparator());
            writer.write("----" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
