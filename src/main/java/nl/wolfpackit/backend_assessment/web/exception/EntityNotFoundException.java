package nl.wolfpackit.backend_assessment.web.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id,  String entity) {
        super("Could not find " + entity + " " + id);
    }
}
