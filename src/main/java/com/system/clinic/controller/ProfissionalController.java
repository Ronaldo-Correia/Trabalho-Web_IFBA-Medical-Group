package com.system.clinic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.system.clinic.dto.PacienteDTO;
import com.system.clinic.dto.ProfissionalDTO;
import com.system.clinic.exception.ValidationErrorDTO;
import com.system.clinic.exception.ValidationErrorDTO1;
import com.system.clinic.service.ProfissionalService;

import jakarta.validation.Valid;

@Controller
public class ProfissionalController {

    @Autowired
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping("/listarProfissional")
    public String listarProfissional(Model model) {
        List<ProfissionalDTO> profissionais = profissionalService.findAll();
        model.addAttribute("profissionais", profissionais);
        return "profissional/listarProfissional";
    }

    @GetMapping("/cadastroProfissional")
    public String cadastroProfissional(Model model) {
        model.addAttribute("profissional", new ProfissionalDTO());
        return "profissional/cadastroProfissional";
    }

    @PostMapping("/profissional/salvar")
    public String salvarProfissional(@Valid @ModelAttribute ProfissionalDTO profissionalDTO, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "profissional/cadastroProfissional";
        }

        profissionalService.save(profissionalDTO);
        return "redirect:/listarProfissional";
    }

    @GetMapping("/profissional/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model) {
        ProfissionalDTO profissional = profissionalService.findOne(id);
        model.addAttribute("profissional", profissional);
        model.addAttribute("edicao", true);
        model.addAttribute("visualizacao", false); // Adicionando a flag visualizacao como false para permitir edição
        return "profissional/form_cadastro_profissional";
    }

    @PostMapping("/profissional/editar/{id}")
public String atualizarProfissional(@PathVariable Long id,
        @ModelAttribute @Valid ProfissionalDTO profissionalDTO,
        BindingResult result,
        Model model) {
    if (result.hasErrors()) {
        model.addAttribute("profissional", profissionalDTO);
        model.addAttribute("edicao", true);
        model.addAttribute("visualizacao", false); 
        return "profissional/form_cadastro_profissional"; 
    }

    profissionalDTO.setId(id);
    profissionalService.save(profissionalDTO);
    return "redirect:/listarProfissional?success=ProfissionalAtualizado"; 
}


    @GetMapping("/profissional/excluir/{id}")
    public String excluirProfissional(@PathVariable Long id) {
        profissionalService.remove(id);
        return "redirect:/listarProfissional";
    }

    @GetMapping("/profissional")
    public String buscarOuListarProfissional(@RequestParam(name = "cpf", required = false) String cpf, Model model) {
        List<ProfissionalDTO> profissionais;
        if (cpf != null && !cpf.isEmpty()) {
            profissionais = profissionalService.findByCpf(cpf);
            model.addAttribute("erroCpf", profissionais.isEmpty());
        } else {
            profissionais = profissionalService.findAll();
        }
        model.addAttribute("profissionais", profissionais);
        return "profissional/listarProfissional";
    }
}
