@import com.system.clinic.dto.AgendamentoDTO
@import java.time.format.DateTimeFormatter

@param AgendamentoDTO agendamento

@template.layout.main(content = @`
<main>
    <div class="agendamento-container">
        
        <div class="profissional-form-img">
            <img class="img1" src="/img/Agendamento1.png" alt="Agendamento">
        </div>

        <form id="formAgendamento" action="/agendamento/salvar" method="POST">
            <div class="agendamento-grid-container">
                <h2>Cadastro de Agendamento</h2>
               
                <input type="hidden" id="pacienteId" name="pacienteId" value="">
                <input type="hidden" id="profissionalId" name="profissionalId" value=""> 
                <div class="form-group">
                    <label for="cns">Buscar CNS Paciente:</label>
                    <input type="text" id="cns" name="cns" placeholder="Digite o CNS" onchange="buscarPaciente()" required>
                </div>

                <div class="form-group">
                    <label for="nomePaciente">Paciente:</label>
                    <input type="text" id="nomePaciente" placeholder="Nome do paciente" readonly>
                </div>

                <div class="form-group">
                    <label for="telefone">Telefone:</label>
                    <input type="tel" id="telefone" name="telefone" placeholder="(00) 00000-0000" readonly>
                </div>

                <div class="form-group">
                    <label for="medico">Médico:</label>
                    <input type="text" id="medico" name="profissionalNome" placeholder="Digite o nome do médico" onchange="buscarMedico()" required>
                </div>

                <div class="form-group">
                    <label for="especialidade">Especialidade:</label>
                    <input type="text" id="especialidade" name="especialidade" readonly>
                </div>

                <div class="form-group">
                    <label for="data">Data da Consulta:</label>
                    <input type="date" id="data" name="dataConsulta" required>
                </div>

                <div class="form-group">
                    <label for="horario">Horário da Consulta:</label>
                    <input type="time" id="horario" name="horaConsulta" required>
                </div>

                <div class="form-group">
                    <label for="motivo">Motivo da Consulta:</label>
                    <textarea id="motivo" name="motivoConsulta" rows="3" placeholder="Descreva o motivo da consulta" required></textarea>
                </div>


                <div class="btn-group">
    <input type="button" class="cancel-btn" value="Voltar" onclick="window.location.href='/listarAgendamentos'">
    <input type="submit" class="saveConsulta-btn" value="Agendar">
</div>


                
            </div>
        </form>
    </div>
</main>

<script>
function verificarHorariosDisponiveis(profissionalId, data) {
    fetch('/horarios-disponiveis?profissional=' + profissionalId + '&data=' + data)
        .then(response => response.json())
        .then(horarios => {
            const timeInput = document.getElementById('horario');
            timeInput.innerHTML = '';
            
            if (horarios.length === 0) {
                const option = document.createElement('option');
                option.value = '';
                option.textContent = 'Nenhum horário disponível';
                timeInput.appendChild(option);
            } else {
                horarios.forEach(horario => {
                    const option = document.createElement('option');
                    option.value = horario;
                    option.textContent = horario;
                    timeInput.appendChild(option);
                });
            }
        })
        .catch(error => {
            console.error('Erro ao buscar horários:', error);
        });
}

document.getElementById('data').addEventListener('change', function() {
    const profissionalId = document.getElementById('profissionalId').value;
    if (profissionalId && this.value) {
        verificarHorariosDisponiveis(profissionalId, this.value);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const dataInput = document.getElementById('data');
    const horaInput = document.getElementById('horario');
    
    // Data mínima = hoje
    dataInput.min = new Date().toISOString().split('T')[0];
    
    // Atualiza hora mínima quando a data muda
    dataInput.addEventListener('change', function() {
        const hoje = new Date().toISOString().split('T')[0];
        if (this.value === hoje) {
            const agora = new Date();
            horaInput.min = agora.getHours().toString().padStart(2, '0') + ':' + 
                          agora.getMinutes().toString().padStart(2, '0');
        } else {
            horaInput.min = '00:00';
        }
        
        // Atualiza horários disponíveis se já tiver profissional selecionado
        const profissionalId = document.getElementById('profissionalId').value;
        if (profissionalId) {
            verificarHorariosDisponiveis(profissionalId, this.value);
        }
    });
});

function buscarPaciente() {
    const cns = document.getElementById('cns').value;
    if (cns.length < 5) return;
    
    fetch('/buscarPacientePorCns/' + cns)
        .then(response => {
            if (!response.ok) throw new Error('Paciente não encontrado');
            return response.json();
        })
        .then(paciente => {
            document.getElementById('nomePaciente').value = paciente.nome;
            document.getElementById('telefone').value = paciente.telefone;
            document.getElementById('pacienteId').value = paciente.id;
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Paciente não encontrado!');
        });
}

function buscarMedico() {
    const nome = document.getElementById('medico').value;
    if (nome.length < 3) return;
    
    fetch('/buscarMedicoPorNome/' + nome)
        .then(response => {
            if (!response.ok) throw new Error('Médico não encontrado');
            return response.json();
        })
        .then(medico => {
            document.getElementById('especialidade').value = medico.especialidade;
            document.getElementById('profissionalId').value = medico.id;
            
            // Atualiza horários disponíveis se já tiver data selecionada
            const data = document.getElementById('data').value;
            if (data) {
                verificarHorariosDisponiveis(medico.id, data);
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Médico não encontrado!');
        });
}
</script>
`)