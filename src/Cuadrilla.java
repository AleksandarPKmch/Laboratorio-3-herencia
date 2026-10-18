public class Cuadrilla {

    private final String codigo;
    private final double capacidadHoras;
    private final boolean certificadaRefrigeracion;
    private boolean libre;

    public Cuadrilla(String codigo, double capacidadHoras, boolean certificadaRefrigeracion) {
        this.codigo = Validacion.codigo(codigo, "Codigo de la cuadrilla disponible");
        this.capacidadHoras = Validacion.positivo(capacidadHoras, "Capacidad maxima de la cuadrilla");
        this.certificadaRefrigeracion = certificadaRefrigeracion;
        this.libre = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getCapacidadHoras() {
        return capacidadHoras;
    }

    public boolean isCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }

    public boolean estaLibre() {
        return libre;
    }

    void ocupar() {
        if (!libre) {
            throw new OperacionInvalida(
                    "La cuadrilla " + codigo + " aun no cierra ua jornada");
        }
        libre = false;
    }

    void liberar() {
        libre = true;
    }
}
