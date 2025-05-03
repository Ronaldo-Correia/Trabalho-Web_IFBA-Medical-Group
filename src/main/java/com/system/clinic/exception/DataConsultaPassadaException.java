package com.system.clinic.exception;

public class DataConsultaPassadaException extends RuntimeException {
    public DataConsultaPassadaException() {
        super("Não é possível agendar consultas no passado");
    }
}
