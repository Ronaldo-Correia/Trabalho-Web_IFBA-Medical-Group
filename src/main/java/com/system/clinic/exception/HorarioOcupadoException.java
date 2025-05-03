package com.system.clinic.exception;

// HorarioOcupadoException.java
public class HorarioOcupadoException extends RuntimeException {
    public HorarioOcupadoException() {
        super("Já existe uma consulta agendada para este horário");
    }
}