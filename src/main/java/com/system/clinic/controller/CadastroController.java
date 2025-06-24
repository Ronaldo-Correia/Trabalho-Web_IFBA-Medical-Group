package com.system.clinic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.clinic.dto.UsuarioDTO;
import com.system.clinic.exception.RecursoDuplicadoException;
import com.system.clinic.exception.ValidationErrorDTO;
import com.system.clinic.service.UsuarioService;
import com.system.clinic.util.ControllerUtils;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CadastroController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public ModelAndView cadastroForm() {
        var model = new ModelAndView();
        model.setViewName("cadastro");
        model.addObject("usuario", new UsuarioDTO());
        return model;
    }

    @GetMapping("/cadastroSucesso")
    public String mostrarTelaSucesso() {
        return "cadastroSucesso";
    }

    @PostMapping("/cadastro")
public ModelAndView cadastro(@Valid UsuarioDTO usuario, BindingResult bindingResult) {
    var model = new ModelAndView();
    var errors = ControllerUtils.createValidationErrorResponse(bindingResult);

    model.setViewName("cadastro");
    model.addObject("errors", errors);
    model.addObject("usuario", usuario);

    try {
        service.save(usuario); // remove passwordEncoder daqui
        model.setViewName("redirect:/cadastroSucesso");
        return model;
    } catch (RecursoDuplicadoException e) {
        errors.getErrors().add(new ValidationErrorDTO.FieldError("email", e.getMessage()));
    }

    return model;
}

}
