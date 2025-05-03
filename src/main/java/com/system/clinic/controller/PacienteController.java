package com.system.clinic.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.clinic.dto.PacienteDTO;
import com.system.clinic.service.PacienteService;

import jakarta.validation.Valid;

@Controller
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/listarPaciente")
    public String listarPaciente(Model model) {
        List<PacienteDTO> pacientes = pacienteService.findAll();
        model.addAttribute("pacientes", pacientes);
        return "paciente/lista_pacientes";
    }

    @GetMapping("/paciente")
    public String buscarOuListarPaciente(@RequestParam(name = "cpf", required = false) String cpf, Model model) {
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

    @GetMapping("/cadastroPaciente")
    public String cadastroPaciente(Model model) {
        if (!model.containsAttribute("paciente")) {
            PacienteDTO paciente = new PacienteDTO();
            // Inicialize campos que podem ser nulos
            paciente.setSexo("");
            model.addAttribute("paciente", paciente);
        }
        model.addAttribute("edicao", false);
        return "paciente/cadastroPaciente";
    }

    // @GetMapping("/paciente/editar/{id}")
    // public String editarPaciente(@PathVariable Long id, Model model) {
    // PacienteDTO paciente = pacienteService.findOne(id);
    // model.addAttribute("paciente", paciente);
    // model.addAttribute("edicao", true);
    // return "paciente/cadastroPaciente";
    // }

    // @PostMapping("/paciente/editar/{id}")
    // public String atualizarPaciente(@PathVariable Long id,
    // @ModelAttribute @Valid PacienteDTO pacienteDTO,
    // BindingResult result,
    // Model model) {
    // if (result.hasErrors()) {
    // model.addAttribute("edicao", true);
    // return "paciente/cadastroPaciente";
    // }

    // pacienteDTO.setId(id);
    // pacienteService.save(pacienteDTO);
    // return "redirect:/listarPaciente?success=PacienteAtualizado";
    // }

    @PostMapping("/paciente/salvar")
    public String salvarPaciente(@Valid @ModelAttribute PacienteDTO pacienteDTO) {
        pacienteService.save(pacienteDTO);

        return "redirect:/paciente";
    }

    @GetMapping("/paciente/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model) {
        PacienteDTO paciente = pacienteService.findOne(id);
        model.addAttribute("paciente", paciente);
        model.addAttribute("edicao", true);
        model.addAttribute("visualizacao", false); // Adicionando a flag visualizacao como false para permitir edição
        return "paciente/form_cadastro_paciente";
    }

    @PostMapping("/paciente/editar/{id}")
    public String atualizarPaciente(@PathVariable Long id,
            @ModelAttribute @Valid PacienteDTO pacienteDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("paciente", pacienteDTO);
            model.addAttribute("edicao", true);
            model.addAttribute("visualizacao", false); // Mantém a flag de visualização como false
            return "paciente/form_cadastro_paciente"; // Retorna ao formulário com erros
        }

        pacienteDTO.setId(id);
        pacienteService.save(pacienteDTO);
        return "redirect:/listarPaciente?success=PacienteAtualizado"; // Redireciona com sucesso
    }

    @GetMapping("/paciente/excluir/{id}")
    public String excluirPaciente(@PathVariable(name = "id") Long id) {
        if (pacienteService.findOne(id) != null) {
            pacienteService.remove(id);
        }
        return "redirect:/paciente";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("error", "Ocorreu um erro: " + ex.getMessage());
        return "error"; // Crie uma página de erro básica
    }
}
