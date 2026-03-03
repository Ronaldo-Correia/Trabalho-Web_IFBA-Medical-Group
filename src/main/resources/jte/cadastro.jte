@import com.system.clinic.dto.UsuarioDTO
@param UsuarioDTO usuario
@param String erro

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Usuário - IFBA Medical</title>
    <link rel="stylesheet" href="/css/cadastro.css">
</head>
<body>
    <div class="CadastroContainer">
        <form action="/cadastro" method="post" class="Formulario">
            <div class="TituloCad">
                <h1>Cadastrar Usuário</h1>
                <div class="barra-horizontal"></div>
            </div>

            <div class="espaco-input">
                <label for="nome">Nome*</label>
                <input type="text" id="nome" name="nome" value="${usuario.getNome() != null ? usuario.getNome() : ""}" placeholder="Digite o nome completo" required />
            </div>

            <div class="espaco-input">
                <label for="email">E-mail*</label>
                <input type="email" id="email" name="email" value="${usuario.getEmail() != null ? usuario.getEmail() : ""}" placeholder="Digite o e-mail" required />
                @if(erro != null)
                    <div class="mensagem-erro" style="color: red; font-size: 0.8rem;">${erro}</div>
                @endif
            </div>

            <div class="espaco-input">
                <label for="senha">Senha*</label>
                <input type="password" id="senha" name="senha" placeholder="Mínimo 6 caracteres" required minlength="6" />
            </div>

            <div class="espaco-input">
                <label for="role">Tipo de Usuário*</label>
                <select id="role" name="role" required style="width: 100%; padding: 10px; border-radius: 5px;">
                    <option value="ROLE_USER">Usuário Comum (Médico/Recepcionista)</option>
                    <option value="ROLE_ADMIN">Administrador</option>
                </select>
            </div>

            <button type="submit">Salvar Usuário</button>
            <p>Deseja voltar? <a href="/home">Voltar para Home</a></p>
        </form>

        <div class="image-section2">
            <div class="alinharSegundaParte">
                <div>
                    <h2>Gestão de Acessos</h2>
                    <img src="/img/tel.png" alt="Imagem de cadastro">
                </div>
            </div>
        </div>
    </div>
</body>
</html>