@param boolean logado
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro Realizado</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .card {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            text-align: center;
            max-width: 400px;
        }

        h1 {
            color: #2c7be5;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            color: #333;
            margin-bottom: 30px;
        }

        .btn {
            background-color: #2c7be5;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #1b5ecf;
        }
    </style>
</head>
<body>
    <div class="card">
        <h1>Cadastro Realizado!</h1>
        <p>Seu cadastro foi concluído com sucesso.</p>
        <a href="/login" class="btn">Ir para Login</a>
    </div>
</body>
</html>
