@param String erro
@param String sucesso

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redefinir Senha</title>
    <link rel="stylesheet" href="/css/cadastro.css">
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            form.addEventListener('submit', async function(e) {
                e.preventDefault();
                
                const button = form.querySelector('button[type="submit"]');
                button.disabled = true;
                button.textContent = 'Enviando...';
                
                try {
                    const response = await fetch(form.action, {
                        method: 'POST',
                        body: new FormData(form),
                        headers: {
                            'Accept': 'application/json'
                        }
                    });
                    
                    if (response.redirected) {
                        window.location.href = response.url;
                    } else {
                        const result = await response.json();
                        if (result.error) {
                            alert(result.error);
                        }
                        button.disabled = false;
                        button.textContent = 'Enviar Link de Redefinição';
                    }
                } catch (error) {
                    console.error('Error:', error);
                    button.disabled = false;
                    button.textContent = 'Enviar Link de Redefinição';
                }
            });
        });
    </script>
</head>
<body>
    <div class="CadastroContainer">
        <form action="/solicitar-redefinicao" method="post" class="Formulario">
            <div class="TituloCad">
                <h1>Redefinir Senha</h1>
                <div class="barra-horizontal"></div>
            </div>

            @if (erro != null && !erro.isEmpty())
                <div class="alert alert-danger">${erro}</div>
            @endif
            
            @if (sucesso != null && !sucesso.isEmpty())
                <div class="alert alert-success">${sucesso}</div>
            @endif

            <div class="espaco-input">
                <label for="email">E-mail cadastrado*</label>
                <input type="email" id="email" name="email" placeholder="Digite seu e-mail" required>
            </div>

            <button type="submit">Enviar Link de Redefinição</button>

            <p>Lembrou sua senha? <a href="/login">Faça login</a></p>
        </form>

        <div class="image-section2">
            <div class="alinharSegundaParte">
                <div>
                    <script src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs" type="module"></script>
<dotlottie-player src="https://lottie.host/1e1bdafd-9d96-4909-9862-b21f2c5f6172/4C2XHtUxwS.lottie" background="transparent" speed="1" style="width: 300px; height: 300px" loop autoplay></dotlottie-player>
                </div>
            </div>
        </div>
    </div>
</body>
</html>