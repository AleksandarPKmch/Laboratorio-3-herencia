public enum EstadoOrden {
    PENDIENTE("Pendiente"),
    ASIGNADA("Asignada"),
    EN_ATENCION("En atencion"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    private final String etiqueta;

    EstadoOrden(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
