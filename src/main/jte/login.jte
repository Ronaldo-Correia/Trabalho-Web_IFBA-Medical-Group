<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" th:href="@{/css/cadastroProfissional.css}">
    <link rel="icon" type="image/x-icon" href="/favicon.ico">


</head>
<body>


    <div class="login-container">

        <form action="/login" method="post" class="form-section">


            <div class="titulo"> 
                <h1>LOGIN</h1>
                <div class="barra-horizontal"></div>
            </div>   
            
            <div class="espaco-input">
                <label for="usuario">Usuário*</label>
                <input type="text" id="usuario" name="username" placeholder="Digite seu nome de usuário" required /> 
            </div>
        
            <div class="espaco-input">
                <label for="senha">Senha*</label>
                <input type="password" id="senha" name="password" placeholder="Digite sua senha" required />
            </div>
        
            <div class="lembrar-me">
                <input type="checkbox" id="lembrar-me" name="lembrar-me"/>
                <label for="lembrar-me">Lembrar-me</label>
            </div>
        
            <button>Entrar</button>

            <p>Não tem cadastro? <a href="/cadastro">Clique aqui!</a></p>
        
        </form>
          

        <!-- Imagem -->
        <div class="image-section">
            <div class="alinharSegundaParte">
                <div>
                <h2>Seja Bem-Vindo</h2>
                <img src="/img/imagemLogin.png" alt="Imagem de login">
            </div>
        </div>
    </div>
    </div>
</body>
</html>

