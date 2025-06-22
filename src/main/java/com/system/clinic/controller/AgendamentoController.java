package com.system.clinic.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

import com.system.clinic.dto.AgendamentoDTO;
import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.entity.ProfissionalEntity;
import com.system.clinic.exception.BusinessException;
import com.system.clinic.repository.ProfissionalRepository;
import com.system.clinic.service.AgendamentoService;
import com.system.clinic.service.PacienteService;
import com.system.clinic.service.ProfissionalService;
import com.system.clinic.util.ControllerUtils;

import jakarta.validation.Valid;

@Controller
public class AgendamentoController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfissionalService profissionalService;
    
    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    DateTimeFormatter formatarData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatarHora = DateTimeFormatter.ofPattern("HH:mm");

    @GetMapping("/listarAgendamentos")
    public String listarAgendamentos(Model model) {
        try {
            List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentosCompletos();
            model.addAttribute("agendamentos", agendamentos);
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar agendamentos: " + e.getMessage());
        }
        return "agendamento/lista_agendamentos";
    }

    @GetMapping("/marcarAgendamento")
    public String marcarAgendamento(Model model) {
        model.addAttribute("agendamento", new AgendamentoDTO());
        return "agendamento/cadastro_agendamento";
    }

    @PostMapping("/agendamento/salvar") // Salva um novo
    public String salvarAgendamento(
            @RequestParam Long pacienteId,
            @RequestParam Long profissionalId,
            @RequestParam String cns,
            @RequestParam String dataConsulta,
            @RequestParam String horaConsulta,
            @RequestParam String motivoConsulta,
            RedirectAttributes redirectAttributes) {

        try {
            // Validações básicas
            if (pacienteId == null || profissionalId == null) {
                throw new BusinessException("Paciente e profissional são obrigatórios");
            }

            // Conversão de data/hora
            LocalDate data = LocalDate.parse(dataConsulta);
            LocalTime hora = LocalTime.parse(horaConsulta);

            // Cria DTO do agendamento
            AgendamentoDTO dto = new AgendamentoDTO();
            dto.setPacienteId(pacienteId);
            dto.setProfissionalId(profissionalId);
            dto.setCns(cns);
            dto.setDataConsulta(data);
            dto.setHoraConsulta(hora);
            dto.setMotivoConsulta(motivoConsulta);

            // Obtém especialidade do profissional
            ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                    .orElseThrow(() -> new BusinessException("Profissional não encontrado"));
            dto.setEspecialidade(profissional.getEspecialidade());
            dto.setStatus("AGENDADO");

            // Validações no service (incluindo intervalo de 1 hora)
            AgendamentoDTO agendamentoSalvo = agendamentoService.agendarConsulta(dto);

            // Mensagem de sucesso
            redirectAttributes.addFlashAttribute("sucesso", "Consulta agendada com sucesso para " +
                    data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às " +
                    hora.format(DateTimeFormatter.ofPattern("HH:mm")));

            return "redirect:/listarAgendamentos";

        } catch (BusinessException e) {
            // Trata erros de negócio (incluindo intervalo insuficiente)
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            // Mantém os dados digitados para não perder o preenchimento
            redirectAttributes.addFlashAttribute("dadosAgendamento", Map.of(
                    "pacienteId", pacienteId,
                    "profissionalId", profissionalId,
                    "cns", cns,
                    "dataConsulta", dataConsulta,
                    "horaConsulta", horaConsulta,
                    "motivoConsulta", motivoConsulta));

        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("erro", "Formato de data/hora inválido");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao agendar consulta: " + e.getMessage());
            e.printStackTrace(); // Log para debug
        }

        return "redirect:/marcarAgendamento";
    }


    @PostMapping("/agendamentoSalvar/{id}")    // MEU METODO PARA SALVAR EDICAO
    public ModelAndView salvarEdicaoAgendamento(
            @Valid AgendamentoDTO agendamento, BindingResult bindingResult, @PathVariable Long id, @RequestParam String dataConsulta,
            @RequestParam String horaConsulta,
            @RequestParam String motivoConsulta
            ){

        var model = new ModelAndView();
        var errors = ControllerUtils.createValidationErrorResponse(bindingResult);

        System.out.println("ID DO AGENDAMENTO ENTITY: " + agendamento.getId());
        System.out.println("DATA DA CONSULTA ENTITY: " + agendamento.getDataConsultaFormatada());
        System.out.println("ID DO PACIENTE ENTITY: " + agendamento.getPacienteId());
        System.out.println("CNS DO AGENDAMENTO ENTITY: " + agendamento.getCns());
        System.out.println(agendamento.getDataConsultaFormatada());

        agendamento.setId(id);

        LocalDate data = LocalDate.parse(dataConsulta);
        LocalTime hora = LocalTime.parse(horaConsulta);

        agendamento.setDataConsulta(data);

        agendamento.setHoraConsulta(hora);

        agendamento.setMotivoConsulta(motivoConsulta);
        
        /*agendamento.setId(id);
        agendamento.setD
        
        AgendamentoDTO busca = agendamentoService.findOne(id);
        
        AgendamentoDTO agendamentoDTO = agendamentoService.findOne(agendamento.getId());

        agendamento = agendamentoDTO;

        System.out.println("ID DO BUSCA ENTITY: " + busca.getId());
        System.out.println("DATA DA CONSULTA BUSCA ENTITY: " + busca.getDataConsultaFormatada());
        System.out.println("ID DO PACIENTE BUSCA ENTITY: " + busca.getPacienteId());
        System.out.println("CNS DO AGENDAMENTO BUSCA ENTITY: " + busca.getCns());

        System.out.println("ID DO AGENDAMENTO ENTITY: " + agendamento.getId());
        System.out.println("DATA DA CONSULTA ENTITY: " + agendamento.getDataConsultaFormatada());
        System.out.println("ID DO PACIENTE ENTITY: " + agendamento.getPacienteId());
        System.out.println("CNS DO AGENDAMENTO ENTITY: " + agendamento.getCns());

        System.out.println("ID DO AGENDAMENTO DTO: " + agendamentoDTO.getId());
        System.out.println("DATA DA CONSULTA DTO: " + agendamentoDTO.getDataConsultaFormatada());
        System.out.println("ID DO PACIENTE DTO: " + agendamentoDTO.getPacienteId());
        System.out.println("CNS DO AGENDAMENTO DTO: " + agendamentoDTO.getCns());*/

        if (!errors.hasErrors()) {

            agendamentoService.voidSave(agendamento);
            model.setViewName("redirect:/listarAgendamentos?success=AgendamentoAtualizado");
        } else {
            model.setViewName("agendamento/form_cadastro_agendamento");
            model.addObject("agendamento", agendamento);
            model.addObject("edicao", true);
            model.addObject("visualizacao", false); // Adicionando a flag visualizacao como false para permitir edição
            model.addObject("errors", errors);
            System.out.println(errors);
            System.out.println("ID DO AGENDAMENTO: " + agendamento.getId());
            System.out.println("DATA DA CONSULTA: " + agendamento.getDataConsultaFormatada());
            System.out.println("ID DO PACIENTE: " + agendamento.getPacienteId());
            System.out.println("CNS DO AGENDAMENTO: " + agendamento.getCns());
        }

        return model;
    }

    @GetMapping("/buscarPacientePorCns/{cns}")
    @ResponseBody
    public PacienteEntity buscarPacientePorCns(@PathVariable String cns) {
        try {
            return agendamentoService.buscarPacienteEntityPorCns(cns);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado");
        }
    }

    @GetMapping("/buscarMedicoPorNome/{nome}")
    @ResponseBody
    public ProfissionalEntity buscarMedicoPorNome(@PathVariable String nome) {
        try {
            return agendamentoService.buscarProfissionalEntityPorNome(nome);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado");
        }
    }

    @GetMapping("/agendamento/editar/{id}")  // ABRIR TELA DE EDICAO
    public String editarAgendamento(@PathVariable Long id, Model model) {
        AgendamentoDTO agendamento = agendamentoService.findOne(id);
        model.addAttribute("agendamento", agendamento);
        /*model.addAttribute("paciente", pacienteService.findOne(agendamentoDTO.getPacienteId()));
        model.addAttribute("profissional", profissionalService.findOne(agendamentoDTO.getProfissionalId()));*/
        model.addAttribute("edicao", true);
        model.addAttribute("visualizacao", false); // Adicionando a flag visualizacao como false para permitir edição
        return "agendamento/form_cadastro_agendamento";
    }

    /*@PostMapping("/agendamentoSalvar/{id}")  // METODO ORIGINAL DE POST DA EDICAO 
    public String atualizarAgendamento(@PathVariable Long id,
            @ModelAttribute @Valid AgendamentoDTO agendamentoDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("agendamento", agendamentoDTO);
            model.addAttribute("edicao", true);
            model.addAttribute("visualizacao", false); // Mantém a flag de visualização como false
            return "agendamento/form_cadastro_agendamento"; // Retorna ao formulário com erros
        }

        agendamentoDTO.setId(id);
        agendamentoService.save(agendamentoDTO);
        return "redirect:/lista_agendamento?success=AgendamentoAtualizado"; // Redireciona com sucesso
    }*/

    @GetMapping("/agendamento/excluir/{id}")
    public String excluirAgendamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            agendamentoService.cancelarAgendamento(id);
            redirectAttributes.addFlashAttribute("sucesso", "Agendamento cancelado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar agendamento");
        }
        return "redirect:/listarAgendamentos";
    }

    @GetMapping("/horarios-disponiveis")
    @ResponseBody
    public List<String> getHorariosDisponiveis(
            @RequestParam Long profissional,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<LocalTime> horariosOcupados = agendamentoService
                .findHorariosOcupados(profissional, data);

        List<String> horariosDisponiveis = new ArrayList<>();
        LocalTime hora = LocalTime.of(8, 0); // Início do expediente (8:00 AM)

        while (hora.isBefore(LocalTime.of(18, 0))) { // Fim do expediente (6:00 PM)
            final LocalTime horaFinal = hora;
            boolean conflito = horariosOcupados.stream()
                    .anyMatch(ocupado -> !horaFinal.plusHours(1).isBefore(ocupado) &&
                            !horaFinal.isAfter(ocupado.plusHours(1)));

            if (!conflito) {
                horariosDisponiveis.add(hora.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            hora = hora.plusMinutes(30); // Oferece horários a cada 30 minutos
        }

        return horariosDisponiveis;
    }
}