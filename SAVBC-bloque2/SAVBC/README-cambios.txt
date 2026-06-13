SAVBC — Bloque 2: carrusel real (selección de estación e idioma)
================================================================

QUÉ HACER EN NETBEANS
- Sustituye tu paquete neartrainnetwork por el de src/neartrainnetwork/.
- El paquete debe contener EXACTAMENTE estos 16 ficheros (nada más):
  CommuterTicketDispenser, TicketDispenserManager, Screen, CarruselScreen,
  WelcomeScreen, LanguageSelectionScreen, StationSelectionScreen,
  PaymentScreen, SuccessScreen, CommunicationErrorScreen, TrainStation,
  TrainNetwork, TariffCalculator, Translator, TranslatorManager,
  OperationContext.
- dictionaries/ va en la raíz del proyecto (junto a config/).

NOVEDADES DE ESTE BLOQUE
- CarruselScreen: implementado de verdad (modo 21 botones).
    'A'..'O' = hasta 15 elementos por página, 'R'=<<, 'U'=>>, 'P'=Cancelar.
- StationSelectionScreen: real. Misma clase para origen y destino
    (pone origen si aún no hay; si ya hay, pone destino y va a pago).
- LanguageSelectionScreen: real. Ya funciona "Cambiar idioma"
    (cambia el idioma actual y vuelve a la bienvenida traducida).
- WelcomeScreen: al pulsar "Iniciar compra" reinicia la operación
    (operationContext.reset()) para empezar limpio cada compra.
- OperationContext: nuevo método reset().

PRUÉBALO
- "Cambiar idioma" -> elige English -> la bienvenida sale en inglés.
- "Iniciar compra" -> navega estaciones con << y >>, elige origen,
    luego destino; al elegir destino pasa a la pantalla de pago
    (todavía provisional). "Cancelar" vuelve a la bienvenida.

SIGUE PROVISIONAL (siguiente bloque)
- PaymentScreen, SuccessScreen, CommunicationErrorScreen.
