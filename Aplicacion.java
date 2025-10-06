/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Aplicación / main, modo "tiempo real")
 * Descripción: Punto de entrada con refresco por tick. Ejecuta la simulación
 *              paso a paso, imprime el estado en cada tick y dispara eventos
 *              aleatorios (usb/descarga/indexar/actualizacion) con baja prob.
 * Notas:
 *  - Sin while(true): se usa un for con un número fijo de ticks
 *  - No imprime directamente; delega todo a VistaConsola (MVC)
 *  - Polimorfismo: el planificador solo conoce Proceso.
 * Auxiliares: Kevin Villagran y Esteban Carcamo (los mejores :D)
 * 
 * Actualización: se agregó modo como que "tiempo real" por ticks en Aplicacion
 * Ahora la simulación refresca la vista en cada tick con una pequeña pausa
 * y puede disparar eventos aleatorios (usb/descarga/indexar/actualizacion)
 * Se evita while(true) y se mantiene el MVC, no se añadieron nuevas clases
 * (fiel al UML y al polimorfismo original)


 */

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Aplicacion {

    // Ajustes "en vivo"
    private static final int TICKS_TOTALES = 20;     // cantidad de "frames"
    private static final int PAUSA_MS = 500;         // milisegundos entre ticks
    private static final boolean PASO_A_PASO = false; // true = esperar ENTER cada tick

    public static void main(String[] args) {
        // MVC
        Planificador planificador = new Planificador();
        ControladorSimulacion controlador = new ControladorSimulacion(planificador);
        VistaConsola vista = new VistaConsola();
        Scanner in = new Scanner(System.in);

        vista.mostrarMensaje(" Simulador de procesos: tiempo real por ticks ");

        // 1) Servicios base
        controlador.cargarServiciosBase();
        vista.mostrarMensaje("[Servicios base cargados]");
        vista.mostrarProcesos(controlador.listar());

        // 2) Sesión de usuario
        controlador.iniciarSesionUsuario();
        vista.mostrarMensaje("[Sesión de usuario iniciada]");
        vista.mostrarProcesos(controlador.listar());

        // 3) Bucle de "tiempo real" por ticks (sin while(true))
        for (int t = 1; t <= TICKS_TOTALES; t++) {

            // Eventos aleatorios con prob. baja por tick
            int r = ThreadLocalRandom.current().nextInt(100);
            if (r < 10) controlador.dispararEvento("descarga");       // 10%
            else if (r < 15) controlador.dispararEvento("usb");       // 5% (acumulado 15)
            else if (r < 20) controlador.dispararEvento("indexar");   // 5% (acumulado 20)
            else if (r < 23) controlador.dispararEvento("actualizacion"); // 3% (acum 23)

            // Un tick de simulación
            controlador.correrTicks(1);

            // Mostrar estado por tick
            vista.mostrarMensaje(String.format("[Tick %d/%d]", t, TICKS_TOTALES));
            vista.mostrarProcesos(controlador.listar());

            // Pausa o paso a paso
            if (PASO_A_PASO) {
                vista.mostrarMensaje("Presiona ENTER para continuar...");
                in.nextLine(); // esperar entrada del usuario
            } else {
                try { Thread.sleep(PAUSA_MS); } catch (InterruptedException ignored) {}
            }
        }

        vista.mostrarMensaje("Fin de la simulación en tiempo real  :D");
        in.close();
    }
}
