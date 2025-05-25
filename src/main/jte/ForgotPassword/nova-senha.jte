@param String token
@param String erro

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Senha</title>
    <link rel="stylesheet" href="/css/cadastro.css">
</head>
<body>
    <div class="CadastroContainer">
        <form action="/redefinir-senha" method="post" class="Formulario">
            <div class="TituloCad">
                <h1>Nova Senha</h1>
                <div class="barra-horizontal"></div>
            </div>

            @if (erro != null && !erro.isEmpty())
                <div class="alert alert-danger">${erro}</div>
            @endif

            <input type="hidden" name="token" value="${token}">
            
            <div class="espaco-input">
                <label for="novaSenha">Nova Senha*</label>
                <input type="password" id="novaSenha" name="novaSenha" placeholder="Mínimo 8 caracteres" required minlength="8">
            </div>

            <div class="espaco-input">
                <label for="confirmacaoSenha">Confirme a Senha*</label>
                <input type="password" id="confirmacaoSenha" name="confirmacaoSenha" placeholder="Digite novamente" required minlength="8">
            </div>

            <button type="submit">Salvar Nova Senha</button>
        </form>

        <div class="image-section2">
            <div class="alinharSegundaParte">
                <div>
                    <h2>Redefinição de Senha</h2>
                    <script src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs" type="module"></script>
<dotlottie-player src="https://lottie.host/1e1bdafd-9d96-4909-9862-b21f2c5f6172/4C2XHtUxwS.lottie" background="transparent" speed="1" style="width: 300px; height: 300px" loop autoplay></dotlottie-player>
                </div>
            </div>
        </div>
    </div>
</body>
</html>