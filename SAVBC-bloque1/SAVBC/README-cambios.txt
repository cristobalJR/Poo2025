SAVBC — Bloque 1 (dominio) + demo en marcha
===========================================

QUÉ HACER EN NETBEANS
1. Copia el contenido de src/neartrainnetwork/ sobre tu paquete
   neartrainnetwork (sustituye los ficheros).
2. BORRA KioskScreen.java de tu proyecto (clase vacía sin uso; la base real
   es Screen). El ZIP no la incluye, pero tu proyecto aún la tiene.
3. Copia la carpeta dictionaries/ a la raíz del proyecto (al lado de config/).
4. Asegúrate de que solo queda UN paquete: neartrainnetwork. La clase de la
   demo (NearTrainNetworkDemo, paquete neartrainnetworkdemo) no forma parte
   de la entrega; puedes eliminarla.
5. Ejecuta el proyecto (main = neartrainnetwork.CommuterTicketDispenser).
   Debe abrirse el kiosco con la pantalla de bienvenida y dos botones:
   "Cambiar idioma" e "Iniciar compra".

ESTADO
- COMPLETO y probado: TrainStation, TariffCalculator, TrainNetwork
  (cálculo de precio por zonas validado con los ejemplos del manual),
  Translator, TranslatorManager, OperationContext, TicketDispenserManager,
  WelcomeScreen, Screen, CarruselScreen (constructor).
- PROVISIONAL (compila y navega, pendiente de implementar de verdad):
  LanguageSelectionScreen, StationSelectionScreen, PaymentScreen,
  SuccessScreen, CommunicationErrorScreen. Hoy solo muestran un texto y
  vuelven a la pantalla de bienvenida.

REQUISITOS
- Java 21. Librerías en lib/: AbsoluteLayout.jar, SelfOrderKioskSimulator.jar,
  UrjcBankServer.jar (no se tocan).
- Datos en config/stations.csv y config/tariffs.csv.
- Idiomas en dictionaries/<Idioma>.txt (pares "original<TAB>traducción").
  Español es el idioma por defecto (identidad, sin fichero).
