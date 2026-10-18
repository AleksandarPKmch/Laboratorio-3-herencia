public enum Zona {
    Metropolitana("Metropolitana"),
    Periferica("Periférica");

    private final String etiqueta;

    Zona(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
