/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Vista de Consola)
 * Descripción: Única clase autorizada para imprimir en pantalla. Muestra
 *              el estado de la lista general de procesos en formato tabla
 *              y mensajes informativos de la simulación.
 * Notas:
 *  - Recibe List<Proceso> y usa polimorfismo para obtener los dats comunes
 */

import java.util.List;

public class VistaConsola {

    public void mostrarProcesos(List<Proceso> procesos) {
        if (procesos == null || procesos.isEmpty()) {
            System.out.println("[Sin procesos para mostrar]");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println(" PID | Nombre              | Tipo                 | Estado      | Prioridad");
        System.out.println("---------------------------------------------------------------");

        for (Proceso p : procesos) {
            String tipo = (p == null) ? "?" : p.getClass().getSimpleName();
            String nombre = (p == null) ? "?" : p.getNombre();
            String estado = (p == null || p.getEstado() == null) ? "?" : p.getEstado().name();
            String prioridad = (p == null || p.getPrioridad() == null) ? "?" : p.getPrioridad().name();
            int pid = (p == null) ? -1 : p.getPid();

            System.out.printf(" %3d | %-18s | %-20s | %-10s | %-9s%n",
                    pid, nombre, tipo, estado, prioridad);
        }
        System.out.println("---------------------------------------------------------------");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje == null ? "" : mensaje);
    }
}
