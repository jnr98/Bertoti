const URL = "http://localhost:8080/coffees";

// LISTAR (READ): Busca no Spring Boot e desenha na tela
async function listarCafes() {
    try {
        const resposta = await fetch(URL);
        const cafes = await resposta.json();
        const lista = document.getElementById('listaDeCafes');
        lista.innerHTML = ""; // Limpa a lista antes de carregar

        cafes.forEach(cafe => {
            lista.innerHTML += `
                <li>
                    <span><strong>${cafe.id}</strong> - ${cafe.name}</span>
                    <div>
                        <button onclick="prepararEdicao('${cafe.id}', '${cafe.name}')">Editar</button>
                        <button class="btn-excluir" onclick="deletarCafe('${cafe.id}')">Excluir</button>
                    </div>
                </li>`;
        });
    } catch (erro) {
        console.error("Erro ao listar:", erro);
    }
}

// SALVAR (CREATE / UPDATE): Envia para o MySQL via Spring Boot
async function salvarNoBanco() {
    const id = document.getElementById('idCafe').value;
    const name = document.getElementById('nomeCafe').value;

    if (!id || !name) {
        alert("Preencha todos os campos!");
        return;
    }

    await fetch(URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id, name })
    });

    // Limpa os campos após salvar
    document.getElementById('idCafe').value = "";
    document.getElementById('nomeCafe').value = "";
    document.getElementById('idCafe').disabled = false; // Reabilita o ID

    listarCafes(); // Atualiza a lista na hora
}

// PREPARAR EDIÇÃO: Joga os dados da lista para as caixas de texto
function prepararEdicao(id, name) {
    document.getElementById('idCafe').value = id;
    document.getElementById('nomeCafe').value = name;
    document.getElementById('idCafe').disabled = true; // Não deixa mudar o ID na edição
    document.getElementById('nomeCafe').focus(); // Coloca o cursor no nome
}

// EXCLUIR (DELETE): Remove o café pelo ID
async function deletarCafe(id) {
    if (confirm("Tem certeza que deseja excluir o café " + id + "?")) {
        await fetch(`${URL}/${id}`, { method: 'DELETE' });
        listarCafes();
    }
}

// Inicia a lista assim que a página abre
listarCafes();