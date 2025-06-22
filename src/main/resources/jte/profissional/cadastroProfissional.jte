@template.layout.main(content = @`
    
    <main>
        <div class="profissional-container">
            
            <div class="profissional-form-img">
                <img class="img1" src="/img/Phisiotherapy-amico 1.png" alt="">
            </div>

<form action="/profissional/salvar" method="POST">

                <div class="profissional-grid-container">
                    <h2>Cadastro de Funcionários</h2>
                    <div class="form-group">
                        <label>Nome Completo:</label>
                        <input type="text" id="nome" name="nome" placeholder="Digite seu nome completo" required>
                    </div>
                    <div class="form-group">
                        <label for="data-nasc">Data de Nascimento:</label>
                        <input type="date" id="dataNascimento" name="dataNascimento" required>
                    </div>
                    <div class="form-group">
                        <label for="email">E-mail:</label>
                        <input type="email" id="email" name="email" placeholder="seu@email.com" required>
                    </div>
                  
                    <div class="form-group">
                        <label for="cpf">CPF:</label>
                    <input type="text" id="cpf" name="cpf" maxlength="14" oninput="mascaraCPF(this)" placeholder="Digite seu CPF" required>
                    </div>
                    <div class="form-group">
                        <label for="cns">Número do CNS:</label>
                        <input type="text" id="cns" name="cns" placeholder="0000 0000 0000" required>
                    </div>
                    <div class="form-group">
                        <label for="tel">Telefone:</label>
                       <input type="tel" id="telefone" name="telefone" maxlength="15" placeholder="Digite seu telefone" required>
                       
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
                        <label for="especialidade">Especialidade:</label>
                        
                        <select name="especialidade" id="especialidade" required>
                            <option value="" disabled selected>Selecione sua especialidade</option>
                            <option value="clinico">Clínico(a) Geral</option>
                            <option value="cardiologista">Cardiologista</option>
                            <option value="pediatra">Pediatra</option>
                            <option value="psiquiatra">Psiquiatra</option>
                        </select>
                    </div>
<div class="form-group cep-box">
    <label for="cep">CEP:</label>
    <div class="cep-container">
        <input type="text" name="cep" placeholder="Digite seu CEP" maxlength="9" pattern="^\d{5}-?\d{3}$" required>
        <button type="button" class="search-cep">Pesquisar</button>
    </div>
                </div>
                    <input type="button" class="cancel-btn" value="Voltar" onclick="window.location.href='/listarProfissional'">
                    <input type="submit" class="save-btn" value="Cadastrar">
                </div>
            </form>
        </div>
    </main>  
    <script>
function mascaraCPF(input) {
    let value = input.value.replace(/\D/g, '');
    if (value.length > 11) value = value.slice(0, 11);
    input.value = value.replace(/(\d{3})(\d{3})(\d{3})(\d{0,2})/, '$1.$2.$3-$4');
}
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