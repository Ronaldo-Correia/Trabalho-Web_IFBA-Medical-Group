<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <link rel="stylesheet" href="/css/cadastro.css">
    <link rel="icon" type="image/x-icon" href="/favicon.ico">
</head>
<body>


    <div class="CadastroContainer">
        <form action="/cadastro" method="post" class="Formulario">
            <div class="TituloCad">
                <h1>Cadastro</h1>
                <div class="barra-horizontal"></div>
            </div>

            <div class="espaco-input">
                <label for="nome">Nome*</label>
                <input type="text" id="nome" name="nome" placeholder="Digite seu nome" required />
            </div>

            <div class="espaco-input">
                <label for="email">E-mail*</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required />
            </div>

            <div class="espaco-input">
                <label for="senha">Senha*</label>
                <input type="password" id="senha" name="senha" placeholder="Digite sua senha" required />
            </div>

            <button type="submit">Salvar</button>

            <p>Voltar a tela de Login?<a href="/login">Clique aqui!</a></p>

        </form>

        <!-- Imagem -->
        <div class="image-section2">
            <div class="alinharSegundaParte">
                <div>
                    <h2>Seja Bem-Vindo</h2>
                    <img src="/img/tel.png" alt="Imagem de login">
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>
