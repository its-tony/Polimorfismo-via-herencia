/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Planificador)
 * Descripción: Planificador con una ÚNICA lista general de procesos. Ejecuta
 *              por "ticks" invocando polimórficamente Proceso.ejecutar() sin
 *              distinguir el subtipo (CPU/E/S/Servicio).
 * Notas:
 *  - Overloading: agregar(p) y agregar(p...), ejecutar() y ejecutar(ticks).
 *  - Política por defecto "FIFO" (informativa).
 *  - Mantiene la ÚNICA List<Proceso> fiel al UML.
 */

import java.util.ArrayList;
import java.util.List;

public class Planificador {

    
    // Campos 
    
    protected List<Proceso> procesos;  
    protected String politica;         
    
    // Constructor
    
    public Planificador() {
        this.procesos = new ArrayList<>();
        this.politica = "FIFO";
    }

    
    // Agregar procesos (overloading)
    
    public void agregar(Proceso p) {
        if (p == null) return;
        // Evitar duplicados por PID (equals/hashCode en Proceso)
        if (!procesos.contains(p)) {
            procesos.add(p);
        }
    }

    public void agregar(Proceso... ps) {
        if (ps == null) return;
        for (Proceso p : ps) {
            agregar(p);
        }
    }

    
    // Ejecución por ticks (overloading)
    
    /** Ejecuta UN tick para todos los procesos (orden FIFO). */
    public void ejecutar() {
        // Itera sobre una copia para evitar problemas si alguna subclase cambiara la lista
        List<Proceso> snapshot = new ArrayList<>(procesos);
        for (Proceso p : snapshot) {
            if (p.getEstado() != EstadoProceso.TERMINADO) {
                p.ejecutar(); // Polimorfismo real :o
            }
        }
    }

    // Ejecuta 'ticks' veces seguidas (sin while(true))
    public void ejecutar(int ticks) {
        if (ticks <= 0) return;
        for (int i = 0; i < ticks; i++) {
            ejecutar();
        }
    }

    
    // Consulta
    
    // Devuelve una copia para proteger la lista interna.
    public List<Proceso> listar() {
        return new ArrayList<>(procesos);
    }
}
