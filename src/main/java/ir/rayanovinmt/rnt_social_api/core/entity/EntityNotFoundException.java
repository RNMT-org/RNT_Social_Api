package ir.rayanovinmt.rnt_social_api.core.entity;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, String field, String value) {
        super(String.format("%s not found with %s '%s'", entity, field, value));
    }
}
