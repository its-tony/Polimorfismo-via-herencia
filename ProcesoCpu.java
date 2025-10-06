/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (ProcesoCpu)
 * Descripción: Proceso intensivo en CPU que consume "ciclos" de trabajo
 *              en cada tick de ejecución hasta finalizar.
 * Notas:
 *  - ejecutar() sobrescribe el método abstracto para polimorfismo.
 *  - Consume de 1 a 3 ciclos por tick (aleatorio) usando ThreadLocalRandom.
 */

import java.util.concurrent.ThreadLocalRandom;

public class ProcesoCpu extends Proceso {

    
    // Campo (visibilidad: protected)
    
    protected int ciclosPendientes; 

    
    // Constructor
    
    public ProcesoCpu(int pid, String nombre, int ciclosPendientes) {
        super(pid, nombre);
        if (ciclosPendientes < 0) ciclosPendientes = 0;
        this.ciclosPendientes = ciclosPendientes;
    }

    
    // Comportamiento polimórfico
    
    @Override
    public void ejecutar() {
        // Si ya terminó, no hace nada
        if (estado == EstadoProceso.TERMINADO) return;

        // Si es la primera vez, pasa a LISTO antes de ejecutar
        if (estado == EstadoProceso.NUEVO) {
            estado = EstadoProceso.LISTO;
        }

        // Simula el uso de CPU en este tick :o
        estado = EstadoProceso.EJECUTANDO;
        int consumo = ThreadLocalRandom.current().nextInt(1, 4); 
        if (consumo > ciclosPendientes) consumo = ciclosPendientes;
        ciclosPendientes -= consumo;

        // Decide siguiente estado 
        if (ciclosPendientes <= 0) {
            estado = EstadoProceso.TERMINADO;
            ciclosPendientes = 0;
        } else {
            estado = EstadoProceso.LISTO;
        }
    }

    
    // Getter auxiliar (esto lo puse opcional)
    
    public int getCiclosPendientes() {
        return ciclosPendientes;
    }
}
