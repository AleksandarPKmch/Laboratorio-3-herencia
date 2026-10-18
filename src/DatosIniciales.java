public final class DatosIniciales {

    private DatosIniciales() {
    }

    public static void cargar(Empresa empresa) {
        empresa.cargarOrdenInicial(new Diagnostico(
                "H-001", "Harry Kane", "Lindenstraße 24", 1.5, Zona.Metropolitana));
        empresa.cargarOrdenInicial(new Diagnostico(
                "H-002", "Luis Diaz", "Gartenweg 18", 1.5, Zona.Periferica));

        empresa.cargarOrdenInicial(new Instalacion(
                "L-001", "Kylian Mbappe", "Rosenstraße 45", 2.5, Zona.Periferica, 4));
        empresa.cargarOrdenInicial(new Instalacion(
                "O-002", "Manuel Neuer", "Bahnhofstraße 12", 3.0, Zona.Periferica, 22));
        empresa.cargarOrdenInicial(new Instalacion(
                "P-003", "Joshua Kimmich", "Hauptstraße 78", 1.0, Zona.Periferica, 67));

        empresa.cargarOrdenInicial(new InstalacionElectricaCertificada(
                "R-001", "Osumane dembele", "Blumenweg 9", 2.5, Zona.Metropolitana, 5, 2000));
        empresa.cargarOrdenInicial(new InstalacionElectricaCertificada(
                "T-002", "N'golo Knate", "Schillerstraße 36", 4.0, Zona.Periferica, 3, 1500));

        empresa.cargarOrdenInicial(new MantenimientoAire(
                "MT-001", "Michael Olise", "Waldstraße 52", 2.0, Zona.Metropolitana, 12000));
        empresa.cargarOrdenInicial(new MantenimientoAire(
                "MT-002", "Lennart Karl", "Sonnenstraße 15", 1.5, Zona.Metropolitana, 24000));

        empresa.registrarCuadrilla(new Cuadrilla("H-01", 8.0, true));
        empresa.registrarCuadrilla(new Cuadrilla("H-02", 6.0, false));
        empresa.registrarCuadrilla(new Cuadrilla("H-03", 4.0, false));
    }
}
