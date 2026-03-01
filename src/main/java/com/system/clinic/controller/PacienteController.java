package com.system.clinic.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.system.clinic.dto.PacienteDTO;
import com.system.clinic.service.PacienteService;

import jakarta.validation.Valid;

@Controller
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // =========================
    // LISTAR
    // =========================
    @GetMapping("/listarPaciente")
    public String listarPaciente(Model model) {
        List<PacienteDTO> pacientes = pacienteService.findAll();
        model.addAttribute("pacientes", pacientes);
        return "paciente/lista_pacientes";
    }

    // =========================
    // BUSCAR POR CPF
    // =========================
    @GetMapping("/paciente")
    public String buscarPorCpf(@RequestParam(name = "cpf", required = false) String cpf,
                               Model model) {

        List<PacienteDTO> pacientes;

        if (cpf != null && !cpf.isEmpty()) {
            pacientes = pacienteService.findByCpf(cpf);
            model.addAttribute("erroCpf", pacientes.isEmpty());
        } else {
            pacientes = pacienteService.findAll();
        }

        model.addAttribute("pacientes", pacientes);
        return "paciente/lista_pacientes";
    }

    // =========================
    // TELA DE CADASTRO SIMPLES
    // =========================
    @GetMapping("/cadastroPaciente")
    public String abrirCadastroSimples(Model model) {
        model.addAttribute("paciente", new PacienteDTO());
        return "paciente/cadastroPaciente";
    }

    // =========================
    // SALVAR (CADASTRO SIMPLES)
    // =========================
    @PostMapping("/paciente/salvar")
    public String salvarPaciente(@Valid @ModelAttribute PacienteDTO pacienteDTO,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
    result.getAllErrors().forEach(e -> System.out.println(e.getDefaultMessage()));
    return "paciente/cadastroPaciente";
}

        pacienteService.save(pacienteDTO);
        return "redirect:/listarPaciente";
    }

    // =========================
    // ABRIR TELA DE EDIÇÃO
    // =========================
    @GetMapping("/paciente/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model) {

        PacienteDTO paciente = pacienteService.findOne(id);

        model.addAttribute("paciente", paciente);
        model.addAttribute("edicao", true);
        model.addAttribute("visualizacao", false);

        return "paciente/form_cadastro_paciente";
    }

    // =========================
    // ATUALIZAR PACIENTE
    // =========================
    @PostMapping("/paciente/editar/{id}")
    public String atualizarPaciente(@PathVariable Long id,
                                    @Valid @ModelAttribute PacienteDTO pacienteDTO,
                                    BindingResult result,
                                    Model model) {

        if (result.hasErrors()) {
            model.addAttribute("edicao", true);
            model.addAttribute("visualizacao", false);
            return "paciente/form_cadastro_paciente";
        }

        pacienteDTO.setId(id);
        pacienteService.save(pacienteDTO);

        return "redirect:/listarPaciente";
    }

    // =========================
    // EXCLUIR
    // =========================
    @GetMapping("/paciente/excluir/{id}")
    public String excluirPaciente(@PathVariable Long id) {

        if (pacienteService.findOne(id) != null) {
            pacienteService.remove(id);
        }

        return "redirect:/listarPaciente";
    }

    // =========================
    // TRATAMENTO GLOBAL DE ERRO
    // =========================
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("error", "Ocorreu um erro: " + ex.getMessage());
        return "error";
    }
}
