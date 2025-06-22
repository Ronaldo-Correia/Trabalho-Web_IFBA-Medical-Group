@template.layout.main(content = @`

    <main>
        <div class="paciente-container">

            <header class="canto">
            <div class="alinharItem">
                 <img class="img1" src="/img/image50.png" alt="">
            </div>
            </header>
           <form action="/paciente/salvar" method="post">
    <div class="paciente-grid-container">
        <h2>Registro de Paciente</h2>

        <div class="form-group">
            <label class="details">Nome:</label>
            <input type="text" name="nome" placeholder="Digite seu Nome" required>
        </div>

        <div class="form-group">
            <label class="details">Nascimento:</label>
            <input type="date" name="dataNascimento" required>
        </div>

        <div class="form-group">
            <label class="details">Sexo:</label>
       <select name="sexo" required>
    <option value="" disabled selected>Selecione</option>
    <option value="Masculino">Masculino</option>
    <option value="Feminino">Feminino</option>
    <option value="Outro">Outro</option>
</select>

        </div>

        <div class="form-group">
            <label class="details">CPF:</label>
            <input type="text" name="cpf" placeholder="000.000.000-00" maxlength="14" required>
        </div>

        <div class="form-group">
            <label class="details">CNS:</label>
            <input type="text" name="cns" placeholder="Digite seu CNS" required>
        </div>

        <div class="form-group">
            <label class="details">Telefone:</label>
            <input type="text" name="telefone" placeholder="Digite seu Telefone" required>
        </div>

        <div class="form-group">
            <label class="details">Município:</label>
            <input type="text" name="municipio" placeholder="Digite seu Município" required>
        </div>

        <div class="form-group">
            <label class="details">Bairro:</label>
            <input type="text" name="bairro" placeholder="Digite seu Bairro" required>
        </div>

        <div class="form-group">
            <label class="details">E-mail:</label>
            <input type="email" name="email" placeholder="Digite seu E-mail" required>
        </div>

        <div class="form-group cep-box">
    <label for="cep">CEP:</label>
    <div class="cep-container">
        <input type="text" name="cep" placeholder="Digite seu CEP" maxlength="9" pattern="^\d{5}-?\d{3}$" required>
        <button type="button" class="search-cep">Pesquisar</button>
    </div>
</div>
                    <input type="button" class="cancel-btn" value="Voltar" onclick="window.location.href='/listarPaciente'">
                    <input type="submit" class="save-btn" value="Cadastrar">
                </div>
</form>

        </div>
    </main>
    <script>
       document.querySelector('.search-cep').addEventListener('click', async function () {
    const cepInput = document.querySelector('input[name="cep"]');
    let cep = cepInput.value.replace(/\D/g, ''); 

    if (cep.length !== 8) {
        alert('CEP inválido! Digite um CEP com 8 números.');
        return;
    }

    try {
        const response = await fetch("https://viacep.com.br/ws/" + cep + "/json/");
        if (!response.ok) {
            throw new Error('Erro ao buscar o CEP.');
        }

        const data = await response.json();

        if (data.erro) {
            alert('CEP não encontrado!');
            return;
        }

        // Preenche os campos Município e Bairro
        document.querySelector('input[name="municipio"]').value = data.localidade;
        document.querySelector('input[name="bairro"]').value = data.bairro;

    } catch (error) {
        console.error('Erro:', error);
        alert('Não foi possível buscar o CEP.');
    }
});
</script>
`)
   
 