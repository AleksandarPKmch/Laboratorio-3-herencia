public class Instalacion extends OrdenServicio {

    private static final double HORAS_POR_PUNTO = 0.75;
    private static final double TARIFA_POR_HORA = 90.0;

    private final int puntos;

    public Instalacion(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona, int puntos) {
        super(codigo, cliente, direccion, horasEstimadas, zona);
        this.puntos = Validacion.enteroPositivo(puntos, "Numero de puntos");
    }

    public int getPuntos() {
        return puntos;
    }

    public double horasPorPuntos() {
        return puntos * HORAS_POR_PUNTO;
    }

    public double horasFacturables() {
        return Math.max(getHorasEstimadas(), horasPorPuntos());
    }

    public double horasCobradas() {
        double tolerancia = 1e-9;
        return Math.ceil(horasFacturables() - tolerancia);
    }

    @Override
    public double calcularCosto() {
        return tarifaBase() + TARIFA_POR_HORA * horasCobradas();
    }

    @Override
    public int calcularDiasAtencion() {
        return esMetropolitana() ? 3 : 5;
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }

    @Override
    public String getCategoria() {
        return "Instalacion";
    }

    @Override
    public String detalle() {
        return "Puntos: " + puntos + ", horas por puntos: " + Formato.horas(horasPorPuntos());
    }
}
