@import com.system.clinic.dto.PacienteDTO
@import java.util.List
@import com.system.clinic.exception.ValidationErrorDTO

@param PacienteDTO paciente = new PacienteDTO()
@param ValidationErrorDTO errors = new ValidationErrorDTO()
@param boolean edicao = false
@param boolean visualizacao = false
@param boolean logado

@template.layout.main(content = @`
    <main class="agendamento-edit-container">
         @if (edicao)
            <div class="success-box">
                <i class="fas fa-check-circle"></i> Paciente editado com sucesso!
            </div>
        @endif
        <div class="form-container">
            @if(!visualizacao)
                <h2 class="title-page">${edicao ? "Edição" : "Cadastro"} de Paciente</h2>
            @endif

            <form id="formpaciente" method="post" action="/paciente/editar/${paciente.getId()}">
                <input type="hidden" id="id" name="id" value="${paciente.getId()}">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" required value="${paciente.getNome() != null ? paciente.getNome() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("nome"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="dataNascimento">Data de Nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento" value="${paciente.getDataNascimento() != null ? paciente.getDataNascimento().toString() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("dataNascimento"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="sexo">Sexo:</label>
                <input type="text" id="sexo" name="sexo" value="${paciente.getSexo() != null ? paciente.getSexo() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("sexo"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" value="${paciente.getCpf() != null ? paciente.getCpf() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("cpf"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${paciente.getEmail() != null ? paciente.getEmail() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("email"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="cns">CNS:</label>
                <input type="text" id="cns" name="cns" value="${paciente.getCns() != null ? paciente.getCns() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("cns"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="telefone">Telefone:</label>
                <input type="text" id="telefone" name="telefone" value="${paciente.getTelefone() != null ? paciente.getTelefone() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("telefone"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="municipio">Município:</label>
                <input type="text" id="municipio" name="municipio" value="${paciente.getMunicipio() != null ? paciente.getMunicipio() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("municipio"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="bairro">Bairro:</label>
                <input type="text" id="bairro" name="bairro" value="${paciente.getBairro() != null ? paciente.getBairro() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("bairro"))
                    <div class="error-message">${error.message()}</div>
                @endfor

                <label for="cep">CEP:</label>
                <input type="text" id="cep" name="cep" value="${paciente.getCep() != null ? paciente.getCep() : ""}" disabled="${visualizacao}">
                @for(var error : errors.getErrorsByField("cep"))
                    <div class="error-message">${error.message()}</div>
                @endfor
                <div class="form-actions">
            <a href="/listarPaciente" class="btn btn-voltar">
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
