import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Jornada {

    private static final double TOLERANCIA = 1e-8;

    private final String codigo;
    private final Cuadrilla cuadrilla;
    private final List<OrdenServicio> ordenes;
    private final Map<OrdenServicio, ResultadoVisita> resultados;
    private EstadoJornada estado;

    Jornada(String codigo, Cuadrilla cuadrilla) {
        this.codigo = Validacion.codigo(codigo, "Codigo de la jornada");
        if (cuadrilla == null) {
            throw new OperacionInvalida("La cuadrilla es obligatoria");
        }
        cuadrilla.ocupar();
        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<>();
        this.resultados = new LinkedHashMap<>();
        this.estado = EstadoJornada.EN_PREPARACION;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public EstadoJornada getEstado() {
        return estado;
    }

    public List<OrdenServicio> getOrdenes() {
        return Collections.unmodifiableList(ordenes);
    }

    public boolean estaCerrada() {
        return estado == EstadoJornada.CERRADA;
    }

    public double horasAsignadas() {
        double total = 0.0;
        for (OrdenServicio orden : ordenes) {
            total += orden.getHorasEstimadas();
        }
        return total;
    }

    public double capacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - horasAsignadas();
    }

    public boolean tieneResultado(OrdenServicio orden) {
        return resultados.containsKey(orden);
    }

    public ResultadoVisita getResultado(OrdenServicio orden) {
        return resultados.get(orden);
    }

    public List<OrdenServicio> ordenesSinResultado() {
        List<OrdenServicio> sinResultado = new ArrayList<>();
        for (OrdenServicio orden : ordenes) {
            if (!resultados.containsKey(orden)) {
                sinResultado.add(orden);
            }
        }
        return sinResultado;
    }

    void asignar(OrdenServicio orden) {
        exigirEstado(EstadoJornada.EN_PREPARACION, "Asignar las ordenes");
        if (orden == null) {
            throw new OperacionInvalida("La orden obligatoria");
        }
        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            throw new OperacionInvalida(String.format("La orden %s se encuentra en estado %s; solo las ordenes pendientes pueden asignarse.",orden.getCodigo(), orden.getEstado()));
        }
        if (!orden.admiteCuadrilla(cuadrilla)) {
            throw new OperacionInvalida(String.format("La orden %s (%s) no es compatible con la cuadrilla %s.", orden.getCodigo(), orden.getCategoria(), cuadrilla.getCodigo()));
        }
        double horasConOrden = horasAsignadas() + orden.getHorasEstimadas();
        if (horasConOrden > cuadrilla.getCapacidadHoras() + TOLERANCIA) {
            throw new OperacionInvalida(String.format("La orden %s excede la capacidad de la cuadrilla %s: %s h con la orden, capacidad %s h.", orden.getCodigo(), cuadrilla.getCodigo(), Formato.horas(horasConOrden), Formato.horas(cuadrilla.getCapacidadHoras())));
        }
        orden.asignar();
        ordenes.add(orden);
    }

    void retirar(OrdenServicio orden) {
        exigirEstado(EstadoJornada.EN_PREPARACION, "retirar todas las ordenes");
        if (orden == null || !ordenes.contains(orden)) {
            throw new OperacionInvalida("Esta orden no aparece en la jornada= " + codigo + ".");
        }
        orden.retirar();
        ordenes.remove(orden);
    }

    double iniciar() {
        exigirEstado(EstadoJornada.EN_PREPARACION, "Iniciacion");
        if (ordenes.isEmpty()) {
            throw new OperacionInvalida("No puedes iniciar una orden inexistente o vacia");
        }
        double cargos = 0.0;
        for (OrdenServicio orden : ordenes) {
            cargos += orden.iniciarVisita();
        }
        estado = EstadoJornada.EN_ATENCION;
        return cargos;
    }

    void registrarResultado(OrdenServicio orden, ResultadoVisita resultado) {
        exigirEstado(EstadoJornada.EN_ATENCION, "registrar resultados");
        if (resultado == null) {
            throw new OperacionInvalida("El resultado es obligatorio.");
        }
        if (orden == null || !ordenes.contains(orden)) {
            throw new OperacionInvalida("La orden no pertenece a la jornada " + codigo + ".");
        }
        if (resultados.containsKey(orden)) {
            throw new OperacionInvalida(String.format("Esta orden %s ya esta registrado en la jornada %s.", orden.getCodigo(), codigo));
        }
        orden.registrarResultado(resultado);
        resultados.put(orden, resultado);
        if (resultados.size() == ordenes.size()) {
            estado = EstadoJornada.CERRADA;
            cuadrilla.liberar();
        }
    }

    private void exigirEstado(EstadoJornada esperado, String accion) {
        if (estado != esperado) {
            throw new OperacionInvalida(String.format("Esta jornada %s esta en estado %s y no admite %s.", codigo, estado, accion));
        }
    }
}
