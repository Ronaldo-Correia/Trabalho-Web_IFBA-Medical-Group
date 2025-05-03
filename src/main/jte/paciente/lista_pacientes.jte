@import com.system.clinic.dto.PacienteDTO
@import java.util.List

@param List<PacienteDTO> pacientes
@param boolean erroCpf = false

@template.layout.main(content = @`
    <h2 class="title-page">Pacientes Cadastrados</h2>

    <form method="get" action="/paciente" class="form-busca-paciente">
        <label for="nome">CPF:</label>
        <input type="text" id="cpf" name="cpf" pattern="[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}">
        <button type="paciente-submit">Buscar</button>
    </form>

    @if(erroCpf)
        <p style="color: red;">Nenhum paciente encontrado com o CPF informado.</p>
    @endif

    <a href="/cadastroPaciente" class="btn-cadastrar">+ Cadastrar Paciente</a>
<div class="tabela-wrapper">

    <table class="tabela">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Data Nascimento</th>
                <th>Sexo</th>
                <th>CPF</th>
                <th class="emailtd">Email</th>
                <th>CNS</th>
                <th>Telefone</th>
                <th>Município</th>
                <th>CEP</th>
                <th>Ações</th>
            </tr>
        </thead>

        <tbody>
            @if(pacientes != null && !pacientes.isEmpty())
                @for(var paciente : pacientes)
                    <tr>
                        <td>${paciente.getId()}</td>
                        <td>${paciente.getNome()}</td>
                        <td>${paciente.getDataNascimentoFormatada()}</td>
                        <td>${paciente.getSexo()}</td>
                        <td>${paciente.getCpf()}</td>
                        <td class="emailtd">${paciente.getEmail()}</td>
                        <td>${paciente.getCns()}</td>
                        <td>${paciente.getTelefone()}</td>
                        <td>${paciente.getMunicipio()}</td>
                        <td>${paciente.getCep()}</td>
                        <td>
                            <div class="botoes-container1">
                            <a href="/paciente/editar/${paciente.getId()}" class="btn-editar">Editar</a>
                            <a href="/paciente/excluir/${paciente.getId()}" class="btn-excluir" onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                            </div>
                       </td>
                    </tr>
                @endfor
            @else
                <tr><td colspan="11">Nenhum paciente encontrado.</td></tr>
            @endif
        </tbody>
    </table>
</div>
`)
