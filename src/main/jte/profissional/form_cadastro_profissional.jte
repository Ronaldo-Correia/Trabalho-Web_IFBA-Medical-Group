@import com.system.clinic.dto.ProfissionalDTO
@import java.util.List
@import com.system.clinic.exception.ValidationErrorDTO

@param ProfissionalDTO profissional = new ProfissionalDTO()
@param ValidationErrorDTO errors = new ValidationErrorDTO()
@param boolean edicao = false
@param boolean visualizacao = false
@param boolean logado

@template.layout.main(content = @`
    <main class="agendamento-edit-container">

      @if (edicao)
            <div class="success-box">
                <i class="fas fa-check-circle"></i> Profissional editado com sucesso!
            </div>
        @endif
    <div class="form-container">

        @if (!visualizacao)
            <h2 class="title-page">${edicao ? "Edição" : "Cadastro"} de Profissional</h2>
        @endif

        <form id="formprofissional" method="post" action="/profissional/editar/${profissional.getId()}" >
            <input type="hidden" id="id" name="id" value="${profissional.getId()}">

            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" required value="${profissional.getNome() != null ? profissional.getNome() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("nome"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="dataNascimento">Data de Nascimento:</label>
            <input type="date" id="dataNascimento" name="dataNascimento" value="${profissional.getDataNascimento() != null ? profissional.getDataNascimento().toString() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("dataNascimento"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="especialidade">Especialidade:</label>
            <input type="text" id="especialidade" name="especialidade" required value="${profissional.getEspecialidade() != null ? profissional.getEspecialidade() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("especialidade"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="telefone">Telefone:</label>
            <input type="text" id="telefone" name="telefone" required value="${profissional.getTelefone() != null ? profissional.getTelefone() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("telefone"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="cns">CNS:</label>
            <input type="text" id="cns" name="cns" required value="${profissional.getCns() != null ? profissional.getCns() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("cns"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name="cpf" required value="${profissional.getCpf() != null ? profissional.getCpf() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("cpf"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required value="${profissional.getEmail() != null ? profissional.getEmail() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("email"))
                <div class="error-message">${error.message()}</div>
            @endfor

             <label for="municipio">Municipio:</label>
            <input type="text" id="municipio" name="municipio" required value="${profissional.getMunicipio() != null ? profissional.getMunicipio() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("municipio"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="bairro">Bairro:</label>
            <input type="text" id="bairro" name="bairro" value="${profissional.getBairro() != null ? profissional.getBairro() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("bairro"))
                <div class="error-message">${error.message()}</div>
            @endfor

            <label for="cep">CEP:</label>
            <input type="text" id="cep" name="cep" required value="${profissional.getCep() != null ? profissional.getCep() : ""}" disabled="${visualizacao}">
            @for(var error : errors.getErrorsByField("cep"))
                <div class="error-message">${error.message()}</div>
            @endfor

  <div class="form-actions">
            <a href="/listarProfissional" class="btn btn-voltar">
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
