package med.voll.api.infra.errores;

public class ValidacionExistencia extends RuntimeException {

    public ValidacionExistencia(String message) {
        super(message);
    }
}
