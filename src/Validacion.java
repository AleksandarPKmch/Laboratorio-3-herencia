import java.util.Locale;

final class Validacion {
    private Validacion() {
    }

    static String normalizarCodigo(String valor) {
        return valor == null ? "" : valor.trim().toUpperCase();
    }

    static String codigo(String valor, String campo) {
        String normalizado = normalizarCodigo(valor);
        if (normalizado.isEmpty()) {
            throw new OperacionInvalida(campo + ": no puede estar vacío.");
        }
        return normalizado;
    }

    static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new OperacionInvalida(campo + ": no puede estar vacío.");
        }
        return valor.trim();
    }

    static double positivo(double valor, String campo) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor <= 0) {
            throw new OperacionInvalida(campo + ": debe ser un número positivo.");
        }
        return valor;
    }

    static int enteroPositivo(int valor, String campo) {
        if (valor <= 0) {
            throw new OperacionInvalida(campo + ": debe ser un entero positivo.");
        }
        return valor;
    }

    static int enteroEnRango(int valor, int minimo, int maximo, String campo) {
        if (valor < minimo || valor > maximo) {
            throw new OperacionInvalida(String.format(Locale.US, "%s: debe estar entre %,d y %,d.", campo, minimo, maximo));
        }
        return valor;
    }
}
