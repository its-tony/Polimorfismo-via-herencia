/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Proceso de Entrada/Salida)
 * Descripción: Clase que modela un proceso de E/S con nombre, dispositivo y latencia,
 *              alterna entre LISTO/BLOQUEADO según disponibilidad del recurso
 * Notas:
 *  - ejecutar() es polimórfica y simula espera/avance por ticks.
 */

import java.util.concurrent.ThreadLocalRandom;

public class ProcesoEntradaSalida extends Proceso {

    
    // Campos
    
    protected String dispositivo;  
    protected int latenciaMs;      
    protected boolean bloqueado;   

    
    // Constructor
    
    public ProcesoEntradaSalida(int pid, String nombre, String dispositivo) {
        super(pid, nombre);
        this.dispositivo = (dispositivo == null || dispositivo.isEmpty()) ? "Dispositivo" : dispositivo;
        this.latenciaMs = 0;
        this.bloqueado = false;
    }

    
    // Comportamiento polimórfico
    
    @Override
    public void ejecutar() {
        // Si ya terminó, no hace nada (lo mismo basicly)
        if (estado == EstadoProceso.TERMINADO) return;

        // Primer paso: de NUEVO a LISTO
        if (estado == EstadoProceso.NUEVO) {
            estado = EstadoProceso.LISTO;
        }

        // Si estaba bloqueado esperando E/S, descontamos latencia.
        if (bloqueado) {
            if (latenciaMs > 0) {
                latenciaMs--;
                estado = EstadoProceso.BLOQUEADO; // sigue esperando
                return;
            } else {
                // Llegaron los datos/terminó la E/S
                bloqueado = false;
                estado = EstadoProceso.LISTO;
                return;
            }
        }

        // No está bloqueado, inicia una operación de E/S en este tick
        // Simulamos un pequeño uso de CPU (inicio de la solicitud) y luego se bloquea.
        estado = EstadoProceso.EJECUTANDO;

        // Inicia bloqueo por E/S entre 1 y 3 ticks (latencia fake JAJAJA)
        this.latenciaMs = ThreadLocalRandom.current().nextInt(1, 4); 
        this.bloqueado = true;

        // Tras iniciar la solicitud, queda bloqueado esperando al dispositivo.
        estado = EstadoProceso.BLOQUEADO;
    }

    
    // Getters auxiliares (tmb opcionales)
    
    public String getDispositivo() { return dispositivo; }

    public int getLatenciaMs() { return latenciaMs; }

    public boolean isBloqueado() { return bloqueado; }
}
