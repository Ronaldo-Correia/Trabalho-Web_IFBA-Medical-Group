@import com.system.clinic.dto.ProfissionalDTO
@import java.util.List

@param List<ProfissionalDTO> profissionais
@param boolean erroCpf = false

@template.layout.main(content = @`
    <h2 class="title-page">Profissionais Cadastrados</h2>
    

     <form method="get" action="/profissional" class="form-busca-profissional">
        <label for="nome">CPF:</label>
        <input type="text" id="cpf" name="cpf" pattern="[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}">
        <button type="profissional-submit">Buscar</button>
    </form>

    @if(erroCpf)
        <p style="color: red;">Nenhum profissional encontrado com o CPF informado.</p>
    @endif

    <a href="/cadastroProfissional" class="btn-cadastrar">+ Cadastrar Profissional</a>

<div class="tabela-wrapper">

    <table class="tabela">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Especialidade</th>
                <th>Telefone</th>
                <th>CNS</th>
                <th>CPF</th>
                <th>Data Nasc.</th>
                <th>Email</th>
                <th>Município</th>
                <th>CEP</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
           @if(profissionais != null && !profissionais.isEmpty())
    @for(var profissional : profissionais)
        <tr>
            <td>${profissional.getId()}</td>
            <td>${profissional.getNome()}</td>
            <td>${profissional.getEspecialidade()}</td>
            <td>${profissional.getTelefone()}</td>
            <td>${profissional.getCns()}</td>
            <td>${profissional.getCpf()}</td>
            <td>${profissional.getDataNascimentoFormatada()}</td>
            <td>${profissional.getEmail()}</td>
            <td>${profissional.getMunicipio()}</td>
            <td>${profissional.getCep()}</td>
            <td>
                <div class="botoes-container1">
                    <a href="/profissional/editar/${profissional.getId()}" class="btn-editar">Editar</a>
                    <a href="/profissional/excluir/${profissional.getId()}" class="btn-excluir" 
                       onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                </div>
            </td>
        </tr>
    @endfor
@else
    <tr><td colspan="11">Nenhum profissional encontrado.</td></tr>
@endif

            
        </tbody>
    </table>
    </div>

`)