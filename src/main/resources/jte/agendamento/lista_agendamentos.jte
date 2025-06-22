@import com.system.clinic.dto.AgendamentoDTO
@import java.util.List

@param List<AgendamentoDTO> agendamentos

@template.layout.main(content = @`
    <h2 class="title-page">Consultas Cadastradas</h2>

    <a href="/marcarAgendamento" class="btn-cadastrar">+ Marcar novo agendamento</a>
<div class="tabela-wrapper">

    <table class="tabela">
        <thead>
            <tr>
                <th>ID</th>
                <th>Paciente CNS</th>
                <th>Médico</th>
                <th>Especialidade</th>
                <th>Data</th>
                <th>Hora</th>
                <th>Motivo</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>

        <tbody>
            @if(agendamentos != null && !agendamentos.isEmpty())
                @for(AgendamentoDTO agendamento : agendamentos)
                    <tr>
                        <td>${agendamento.getId()}</td>
                        <td>${agendamento.getCns()}</td>
                        <td>${agendamento.getNomeProfissional()}</td>
                        <td>${agendamento.getEspecialidade()}</td>
                        <td>${agendamento.getDataConsultaFormatada()}</td>
                        <td>${agendamento.getHoraConsultaFormatada()}</td>
                        <td>${agendamento.getMotivoConsulta()}</td>
                        <td class="status-${agendamento.getStatus().toLowerCase()}">
                            ${agendamento.getStatusTexto()}
                        </td>
                        <td>
                           

                               <div class="botoes-container1">
                            <a href="/agendamento/editar/${agendamento.getId()}" class="btn-editar">Editar</a>
                            <a href="/agendamento/excluir/${agendamento.getId()}" class="btn-excluir" 
                               onclick="return confirm('Tem certeza que deseja cancelar o agendamento?')">Cancelar</a>
                            </div>
                        </td>
                    </tr>
                @endfor
            @else
                <tr>
                    <td colspan="9">Nenhum agendamento encontrado.</td>
                </tr>
            @endif
        </tbody>
    </table>
</div>
`)