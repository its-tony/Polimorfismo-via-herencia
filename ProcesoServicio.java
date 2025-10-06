/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Proceso de Servicio/Daemon)
 * Descripción: Proceso de larga duración que realiza trabajo periódico por tick
 *              (en segundo plano) sin interfaz y normalmente sin finalizar
 */

public class ProcesoServicio extends Proceso {

   
    // Campos 
   
    protected String tarea;         
    protected int trabajoPorTick;      
    // Constructor
    public ProcesoServicio(int pid, String nombre, String tarea, int trabajoPorTick) {
        super(pid, nombre);
        this.tarea = (tarea == null || tarea.isEmpty()) ? "Servicio" : tarea;
        this.trabajoPorTick = (trabajoPorTick <= 0) ? 1 : trabajoPorTick;
    }

   
    // El polimorfismo en acción :D
   
    @Override
    public void ejecutar() {
        if (estado == EstadoProceso.TERMINADO) return;

        // Primer paso: de NUEVO a LISTO
        if (estado == EstadoProceso.NUEVO) {
            estado = EstadoProceso.LISTO;
        }

        // Trabajo periódico en segundo plano: entra y sale de ejecución
        estado = EstadoProceso.EJECUTANDO;

        // Simulación de "trabajoPorTick" sin efectos secundarios visibles
        // (No imprimimos, la vista se encarga de cualquier salida)

        // Los servicios continúan disponibles para próximos ticks
        estado = EstadoProceso.LISTO;
    }

   
    // Getters auxiliares 
    public String getTarea() { return tarea; }

    public int getTrabajoPorTick() { return trabajoPorTick; }
}
