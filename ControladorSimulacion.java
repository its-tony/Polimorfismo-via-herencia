/*
 * Autor: Antony Portillo
 * Proyecto: Simulador de procesos (Controlador de Simulación)
 * Descripción: Orquesta la simulación creando procesos en español y usando
 *              el Planificador con una única lista general. Permite cargar
 *              servicios base, iniciar sesión de usuario, disparar eventos
 *              y correr la simulación por N ticks.
 */

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ControladorSimulacion {

    
    // Campo 
    
    protected Planificador planificador;  
    // Constructor
    public ControladorSimulacion(Planificador planificador) {
        if (planificador == null) {
            throw new IllegalArgumentException("El planificador no puede ser null");
        }
        this.planificador = planificador;
    }

    // Métodos principales de la simulación
    // Crea y agrega servicios base del "sistema"
    public void cargarServiciosBase() {
        planificador.agregar(
            new ProcesoServicio(siguientePid(), "RegistroEventos", "Registro de eventos", 1),
            new ProcesoServicio(siguientePid(), "InicioSesion", "Gestor de inicio de sesión", 1),
            new ProcesoServicio(siguientePid(), "HostDeServicios", "Host de servicios", 1)
        );
    }

    //  Simula procesos típicos de usuario al iniciar sesión
    public void iniciarSesionUsuario() {
        int ciclosExplorador = aleatorio(2, 5);
        planificador.agregar(
            new ProcesoCpu(siguientePid(), "ExploradorArchivos", ciclosExplorador),
            new ProcesoEntradaSalida(siguientePid(), "SincronizacionNube", "Red"),
            new ProcesoServicio(siguientePid(), "ProteccionActiva", "Protección en tiempo real", 1)
        );
    }

    /**
     * Dispara eventos del sistema/usuario para crear nuevos procesos
     * Eventos soportados (insensible a may/min): "usb", "descarga", "actualizacion", "indexar"
     */
    public void dispararEvento(String evento) {
        if (evento == null) return;
        String e = evento.trim().toLowerCase();

        switch (e) {
            case "usb":
                planificador.agregar(
                    new ProcesoEntradaSalida(siguientePid(), "VigilanteUSB", "USB")
                );
                break;
            case "descarga":
                planificador.agregar(
                    new ProcesoEntradaSalida(siguientePid(), "DescargaNavegador", "Red")
                );
                break;
            case "actualizacion":
                planificador.agregar(
                    new ProcesoEntradaSalida(siguientePid(), "ActualizacionSistema", "Disco")
                );
                break;
            case "indexar":
                planificador.agregar(
                    new ProcesoCpu(siguientePid(), "Indexador", aleatorio(3, 7))
                );
                break;
            default:
                // Evento no reconocido: no hace nada y no imprime nada 
                break;
        }
    }

    // Ejecuta N ticks de la simulación (sin while(true))
    public void correrTicks(int n) {
        if (n <= 0) return;
        planificador.ejecutar(n);
    }

    // Devuelve una copia de la lista general de procesos
    public List<Proceso> listar() {
        return planificador.listar();
    }

    
    // Auxiliares internos (privados)
    

    // Calcula el siguiente PID como (máximo PID actual + 1)
    private int siguientePid() {
        int max = 0;
        for (Proceso p : planificador.listar()) {
            if (p.getPid() > max) max = p.getPid();
        }
        return max + 1;
    }

    // Entero aleatorio inclusivo en [min, max]
    private int aleatorio(int minIncl, int maxIncl) {
        if (minIncl > maxIncl) {
            int t = minIncl; minIncl = maxIncl; maxIncl = t;
        }
        return ThreadLocalRandom.current().nextInt(minIncl, maxIncl + 1);
    }
}
