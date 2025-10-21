@param boolean serverError = false

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clínica Médica IFBA SAJ</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" />
    <style>
        .error-container {
          max-width: 600px;
          padding: 2rem;
        }
        .error-code {
          font-size: 6rem;
          color:rgb(50, 99, 233);
          margin-bottom: 0.5rem;
        }
        .error-title {
          margin-bottom: 1rem;
        }
        .error-description {
          font-size: 1.2rem;
          margin-bottom: 2rem;
        }
        .error-link {
          text-decoration: none;
          color: white;
          background-color:rgb(71, 107, 224);
          padding: 0.75rem 1.5rem;
          border-radius: 6px;
          font-weight: bold;
          transition: background-color 0.3s ease;
        }
        .error-link:hover {
          background-color:rgb(71, 107, 224);
        }
        .error-icon {
          width: 200px;
          margin-top: 2rem;
        }
    </style>
</head>
<body>

    <div class="wrapper">
        <main class="main container">
            <div class="error-container">
<script src="https://unpkg.com/@dotlottie/player-component@2.7.12/dist/dotlottie-player.mjs" type="module"></script>
<dotlottie-player src="https://lottie.host/2ed1b020-12a3-495f-89f9-c70b014ae333/gr9oZ83Pmu.lottie" background="transparent" speed="1" style="width: 300px; height: 300px" loop autoplay></dotlottie-player>
                @if (serverError)
                    <h2 class="error-title">Erro no Sistema!</h2>
                    <p class="error-description">Desculpe, mas ocorreu um erro inesperado no nosso sistema. Estamos trabalhando para corrigir isso.</p>
                @else
                    <h1 class="error-code">404</h1>
                    <h2 class="error-title">Página Não Encontrada</h2>
                    <p class="error-description">A página que você está procurando não foi encontrada. Verifique o endereço ou volte à página inicial.</p>
                @endif

                <a href="/" class="error-link">Voltar à página inicial</a>
            </div>
        </main>

        @template.layout.footer()
    </div>

</body>
</html>