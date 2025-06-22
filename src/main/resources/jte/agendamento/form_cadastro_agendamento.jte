@import com.system.clinic.dto.AgendamentoDTO
@import com.system.clinic.exception.ValidationErrorDTO
@import java.time.format.DateTimeFormatter

@param AgendamentoDTO agendamento = new AgendamentoDTO()
@param ValidationErrorDTO errors = new ValidationErrorDTO()
@param boolean edicao = false
@param boolean visualizacao = false
@param boolean logado

@template.layout.main(content = @`
    <main class="agendamento-edit-container">
        @if (edicao)
            <div class="success-box">
                <i class="fas fa-check-circle"></i> Agendamento editado com sucesso!
            </div>
        @endif

        <div class="form-container">
            <h2 class="title-page">${edicao ? "Edição" : "Cadastro"} de Agendamento</h2>

            <!-- Bloco de visualização -->
            <div class="info-box">
                <h3>Dados do Atendimento</h3>
                <p><strong>Nome do Paciente:</strong> ${agendamento.getNomePaciente()}</p>
                <p><strong>Nome do Profissional:</strong> ${agendamento.getNomeProfissional()}</p>
                <p><strong>Especialidade:</strong> ${agendamento.getEspecialidade()}</p>
                <p><strong>Status:</strong> <span class="status-badge">${agendamento.getStatusTexto()}</span></p>
            </div>

            <!-- Formulário de edição -->
            <form id="formagendamento" method="post" action="/agendamentoSalvar/${agendamento.getId()}">
                <input type="hidden" id="id" name="id" value="${agendamento.getId()}">
                <input type="hidden" id="pacienteId" name="pacienteId" value="${agendamento.getPacienteId()}">
                <input type="hidden" id="profissionalId" name="profissionalId" value="${agendamento.getProfissionalId()}">
                <input type="hidden" id="especialidade" name="especialidade" value="${agendamento.getEspecialidade()}">
                <input type="hidden" id="nomePaciente" name="nomePaciente" value="${agendamento.getNomePaciente()}">
                <input type="hidden" id="nomeProfissional" name="nomeProfissional" value="${agendamento.getNomeProfissional()}">
                <input type="hidden" id="status" name="status" value="${agendamento.getStatusTexto()}">
                <input type="hidden" id="cns" name="cns" value="${agendamento.getCns()}">

                <div class="form-group">
                    <label for="dataConsulta">Data da Consulta:</label>
                    <input type="date" id="dataConsulta" name="dataConsulta" required 
                           value="${agendamento.getDataConsulta() != null ? agendamento.getDataConsulta().format(DateTimeFormatter.ISO_LOCAL_DATE) : ""}">
                    @for(var error : errors.getErrorsByField("dataConsulta"))
                        <div class="error-message">${error.message()}</div>
                    @endfor
                </div>

                <div class="form-group">
                    <label for="horaConsulta">Hora da Consulta:</label>
                    <input type="time" id="horaConsulta" name="horaConsulta" required 
                           value="${agendamento.getHoraConsulta() != null ? agendamento.getHoraConsulta().format(DateTimeFormatter.ofPattern("HH:mm")) : ""}">
                    @for(var error : errors.getErrorsByField("horaConsulta"))
                        <div class="error-message">${error.message()}</div>
                    @endfor
                </div>

                <div class="form-group">
                    <label for="motivoConsulta">Motivo da Consulta:</label>
                    <textarea id="motivoConsulta" name="motivoConsulta" rows="4" required
                              style="resize: vertical; min-height: 100px;">${agendamento.getMotivoConsulta() != null ? agendamento.getMotivoConsulta() : ""}</textarea>
                    @for(var error : errors.getErrorsByField("motivoConsulta"))
                        <div class="error-message">${error.message()}</div>
                    @endfor
                </div>

                <div class="form-actions">
                    <a href="/listarAgendamentos" class="btn btn-voltar">
                        <i class="fas fa-arrow-left"></i> Voltar
                    </a>
                    @if(!visualizacao)
                        <button type="submit" class="btn btn-salvar">
                            <i class="fas fa-save"></i> Salvar
                        </button>
                    @endif
                </div>
            </form>
        </div>
    </main>
`)
