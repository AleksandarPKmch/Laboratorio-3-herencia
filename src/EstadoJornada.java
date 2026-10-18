public enum EstadoJornada {
    EN_PREPARACION("Estado en preparacion"),
    EN_ATENCION("Estado en atencion"),
    CERRADA("Estado cerrado");

    private final String etiqueta;

    EstadoJornada(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
