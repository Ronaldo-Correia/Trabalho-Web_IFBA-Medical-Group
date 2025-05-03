package com.system.clinic.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServico {
    public void enviarEmailAtivacao(String emailDestinatario, String tokenAtivacao) {
        System.out.println("Simulando envio de e-mail para: " + emailDestinatario);
        System.out.println("Link de ativação: http://localhost:8080/usuarios/ativar?token=" + tokenAtivacao);
    }
}