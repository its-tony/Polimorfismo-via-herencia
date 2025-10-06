/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Clase base Proceso)
 * Descripción: Clase abstracta que modela un proceso con PID, nombre,
 *              estado y prioridad. Sirve como base para ProcesoCpu,
 *              ProcesoEntradaSalida y ProcesoServicio.
 * Notas:
 *  - ejecutar() es abstracta para polimorfismo.
 *  - Atributos # (protected) para herencia, fiel al UML.
 *  - equals/hashCode por PID (identidad).
 * Auxiliares: Kevin Villagran y Esteban Carcamo (los mejores :D)
 */


public abstract class Proceso {

   
    // Campos (visibilidad: protected)
   
    protected int pid;                 
    protected String nombre;           
    protected EstadoProceso estado;    
    protected Prioridad prioridad;     

   
    // Constructor protegido (uso por subclases)
   
    protected Proceso(int pid, String nombre) {
        this.pid = pid;
        this.nombre = nombre;
        this.estado = EstadoProceso.NUEVO;
        this.prioridad = Prioridad.MEDIA;    // prioridad por defecto
    }

   
    // Comportamiento polimórfico
   
    public abstract void ejecutar();

   
    // Getters / Setters (mínimos del UML)
   
    public int getPid() { return pid; }

    public String getNombre() { return nombre; }

    public EstadoProceso getEstado() { return estado; }

    public Prioridad getPrioridad() { return prioridad; }

    public void setEstado(EstadoProceso estado) { this.estado = estado; }

    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }

   
    
   
    @Override
    public String toString() {
        return getClass().getSimpleName() +
               "{pid=" + pid +
               ", nombre='" + nombre + '\'' +
               ", estado=" + estado +
               ", prioridad=" + prioridad +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proceso)) return false;
        Proceso that = (Proceso) o;
        return this.pid == that.pid; // identidad por PID (único en el sistema)
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(pid);
    }
}
