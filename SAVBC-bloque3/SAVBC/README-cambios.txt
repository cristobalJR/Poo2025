SAVBC — Bloque 3: pago, éxito y error de comunicación (camino completo)
=======================================================================

QUÉ HACER EN NETBEANS
- Sustituye tu paquete neartrainnetwork por el de src/neartrainnetwork/.
- dictionaries/ va en la raíz del proyecto (junto a config/).
- Al ejecutar y pagar, se creará una carpeta log/ con un fichero por día
  (log/AAAA-MM-DD.txt) con el resumen de cada compra.

NOVEDADES
- PaymentScreen (real):
    * Modo 0; título "Introduzca tarjeta de crédito"; botón 'F' = Cancelar.
    * Muestra el resumen (origen, destino, precio) como descripción.
    * Al insertar tarjeta (evento '1'): calcula el precio, lo pasa a céntimos
      EN NEGATIVO y llama a bank.doOperation(numeroTarjeta, -centimos).
    * doOperation lanza CommunicationException (aleatoria) -> pantalla de error.
    * true  -> imprime el billete (print(List<String>)) + graba el log -> éxito.
    * false -> "Pago rechazado" (tarjeta no válida o saldo insuficiente) y
      sigue esperando otra tarjeta o cancelar.
- SuccessScreen (real): mensaje de éxito, pide retirar tarjeta y billete, y
  vuelve al inicio.
- CommunicationErrorScreen (real): cada 5 s comprueba comunicationAvaiable();
  cuando vuelve la comunicación, regresa a la bienvenida. Recibe el banco
  desde PaymentScreen.

NOTAS
- Los errores de comunicación son ALEATORIOS (los simula el banco), así que
  puede que tengas que pagar varias veces para ver esa pantalla.
- Si una tarjeta sale rechazada por saldo, prueba con otra de las del panel.

ESTADO: camino completo funcional (R1-R25). Pendiente: limpieza final,
Javadoc mínimo y revisión.
