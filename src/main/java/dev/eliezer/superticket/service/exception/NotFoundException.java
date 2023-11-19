package dev.eliezer.superticket.service.exception;

public class NotFoundException extends BusinessException {
    //private static final long serialVersionUID = 1L;
    public NotFoundException(Long id) {
        super("Resource id " + id + " not found.");
    }
}
