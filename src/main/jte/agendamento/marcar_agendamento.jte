@template.layout.main(content = @`
<main>
<div class="agendamento-container">
    <div class="agendamento-icon-top">
        <img src="/img/icon.png" alt="">
    </div>
    <div class="agendamento-icon-right">
        <img src="/img/icon2.png" alt="">
    </div>
    <header>
    <div class="title">AGENDAR CONSULTA</div>
    </header>
    <main>
        <div class="agendamento-form-container">
            <label for="CNS">Número do CNS:</label>
            <input type="text" id="CNS" name="sus" placeholder="Digite o CNS" oninput="buscarPaciente()"> 
            <label for="search-patient">Paciente:</label>
            <input type="text" id="search-patient" placeholder="Nome do paciente" readonly> 
            <label for="telefone">Telefone:</label>
            <input type="tel" id="telefone" name="telefone" placeholder="(00) 00000-0000" readonly> 
            <label for="medico">Médico:</label>
            <input type="text" id="medico" name="medico" placeholder="Nome do médico" oninput="buscarMedicio()"> 
            <label for="search-medico">Especialidade:</label>
            <select id="especialidade" name="especialidade">
                <option value="vaizo"></option>
                <option value="cardiologia">Cardiologia</option>
                <option value="dermatologia">Dermatologia</option>
                <option value="ortopedia">Ortopedia</option>
                <option value="pediatria">Pediatria</option>
            </select>            
            <label for="data">Data:</label>
            <input type="date" id="data" name="data">
            <label for="horario">Horário:</label>
            <input type="time" id="horario" name="horario">
            <label for="motivo">Motivo da Consulta:</label>
            <textarea id="motivo" name="motivo" rows="3" placeholder="Descreva o motivo da consulta"></textarea>

            <button class="agendamento-btn" onclick="agendarconsulta()">Agendar</button>
            <div class="agendamento-button1">
                 <input type="button" value="Voltar" onclick="window.location.href='/listarAgendamentos'">
            </div>
        </div>

        
            
        <div class="agendamento-image-container">
            <img src="/img/image50.png" alt="Imagem de agendamento">
        </div>
    </main>

    <script>
        function agendarconsulta() {
            alert("Função de agendar consulta ainda não implementada!");
        }
    </script>
</div>
`)