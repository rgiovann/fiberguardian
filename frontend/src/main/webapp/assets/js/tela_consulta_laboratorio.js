(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    // Variáveis internas para armazenar seleção
    let cnpjFornecedorSelecionado = null;
    let nrNotaFiscalSelecionado = null;

    FiberGuardian.TelaConsultaLaboratorio = (function () {
        const URL_LISTAR_TESTES_LABORATORIO = '/api/testes-laboratorio/list';

        async function consultarTestes(numeroNotaFiscal) {
            try {
                const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                if (!csrfToken) {
                    FiberGuardian.Utils.exibirMensagemModal('Erro: Token CSRF não encontrado.', 'danger');
                    return [];
                }

                const url = new URL(URL_LISTAR_TESTES_LABORATORIO, window.location.origin);
                url.searchParams.append("notaFiscal", numeroNotaFiscal);

                const resposta = await fetch(url.toString(), {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': csrfToken
                    },
                    credentials: 'include'
                });

                if (!resposta.ok) {
                    if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta);
                    }
                    return [];
                }

                const dados = await resposta.json();
                return Array.isArray(dados) ? dados : (dados.content || []);
            } catch (erro) {
                console.error("Erro na consulta:", erro);
                FiberGuardian.Utils.exibirErroDeRede("Erro de rede na consulta de testes.", null, erro);
                return [];
            }
        }

        async function configurarEventos() {
            console.log("Módulo Tela Consulta Laboratório inicializado.");

            const inputFornecedor = document.getElementById("fornecedor");
            const btnBuscarFornecedor = document.getElementById("btnBuscarFornecedor");
            const dropdownFornecedor = document.getElementById("dropdownFornecedor");
            const btnTrocarFornecedor = document.getElementById("btnTrocarFornecedor");

            const inputNrNotaFiscal = document.getElementById("nrNotaFiscal");
            const btnBuscarNrNotaFiscal = document.getElementById("btnBuscarNrNotaFiscal");
            const dropdownNrNotaFiscal = document.getElementById("dropdownNrNotaFiscal");

            const dataInicial = document.getElementById("dataInicial");
            const dataFinal = document.getElementById("dataFinal");

            const btnSair = document.getElementById("btnSair");
            const resultadosContainer = document.getElementById("resultadosContainer");
            const resultadosTableBody = document.getElementById("resultadosTableBody");

            // Fechar dropdowns quando clicar fora
            FiberGuardian.Utils.fecharQualquerDropdownAberto(
                [dropdownFornecedor, dropdownNrNotaFiscal],
                [inputFornecedor, inputNrNotaFiscal],
                [btnBuscarFornecedor, btnBuscarNrNotaFiscal]
            );

            // Buscar fornecedor
            btnBuscarFornecedor.addEventListener("click", async function () {
                const parcial = inputFornecedor.value.trim();
                if (!parcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        "Digite parte do nome do fornecedor para buscar.",
                        "warning",
                        inputFornecedor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const resposta = await fetch(`/api/fornecedores/list/laboratorio?nome=${encodeURIComponent(parcial)}`, {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "X-XSRF-TOKEN": csrfToken
                        },
                        credentials: "include"
                    });

                    if (resposta.ok) {
                        const lista = await resposta.json();

                        const { item } = await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                            input: inputFornecedor,
                            dropdown: dropdownFornecedor,
                            lista: lista,
                            camposExibir: ["nome", "cnpj"],
                            titulosColunas: ["Fornecedor", "CNPJ"],
                            msgVazio: "Nenhum fornecedor encontrado."
                        });

                        cnpjFornecedorSelecionado = item.cnpj;

                        inputFornecedor.readOnly = true;
                        btnBuscarFornecedor.disabled = true;
                        btnTrocarFornecedor.disabled = false;

                        btnTrocarFornecedor.addEventListener("click", () => {
                            cnpjFornecedorSelecionado = null;
                            inputFornecedor.value = "";
                            inputFornecedor.readOnly = false;
                            btnBuscarFornecedor.disabled = false;
                            btnTrocarFornecedor.disabled = true;
                        });
                    }
                } catch (erro) {
                    console.error("Erro ao buscar fornecedores:", erro);
                    FiberGuardian.Utils.exibirErroDeRede("Erro ao buscar fornecedores.", inputFornecedor, erro);
                }
            });

            // Buscar nota fiscal
            btnBuscarNrNotaFiscal.addEventListener("click", async function () {
                const parcial = inputNrNotaFiscal.value.trim();

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const resposta = await fetch(`/api/notasfiscais/list/laboratorio?cnpj=${cnpjFornecedorSelecionado || ""}&numero=${encodeURIComponent(parcial)}`, {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "X-XSRF-TOKEN": csrfToken
                        },
                        credentials: "include"
                    });

                    if (resposta.ok) {
                        const listaNotas = await resposta.json();

                        const { item } = await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                            input: inputNrNotaFiscal,
                            dropdown: dropdownNrNotaFiscal,
                            lista: listaNotas,
                            camposExibir: ["codigoNf", "descricao"],
                            titulosColunas: ["Nota Fiscal", "Descrição"],
                            msgVazio: "Nenhuma nota encontrada."
                        });

                        nrNotaFiscalSelecionado = item.codigoNf;

                        // Agora dispara a consulta de testes
                        resultadosTableBody.innerHTML = "<tr><td colspan='5'>Buscando testes...</td></tr>";
                        resultadosContainer.style.display = "block";

                        const testes = await consultarTestes(nrNotaFiscalSelecionado);

                        resultadosTableBody.innerHTML = "";
                        if (!testes || testes.length === 0) {
                            resultadosTableBody.innerHTML = "<tr><td colspan='5'>Nenhum teste de laboratório encontrado.</td></tr>";
                        } else {
                            testes.forEach(teste => {
                                const row = document.createElement("tr");
                                row.innerHTML = `
                                    <td>${new Date(teste.dataTeste).toLocaleDateString()}</td>
                                    <td>${teste.fornecedor.nome}</td>
                                    <td>${teste.produto.codigo}</td>
                                    <td>${teste.status}</td>
                                    <td>
                                        <button class="btn btn-primary btn-sm btn-editar me-2" data-id="${teste.id}">
                                            <i class="fas fa-edit"></i> Editar
                                        </button>
                                        <button class="btn btn-danger btn-sm btn-excluir" data-id="${teste.id}">
                                            <i class="fas fa-trash-alt"></i> Excluir
                                        </button>
                                    </td>
                                `;
                                resultadosTableBody.appendChild(row);
                            });
                        }
                    }
                } catch (erro) {
                    console.error("Erro ao buscar notas fiscais:", erro);
                    FiberGuardian.Utils.exibirErroDeRede("Erro ao buscar notas fiscais.", inputNrNotaFiscal, erro);
                }
            });

            // Botão sair
            btnSair.addEventListener("click", async () => {
                const confirmado = await FiberGuardian.Utils.confirmarAcaoAsync(
                    "Deseja realmente voltar ao Menu Principal?",
                    "Sair do Sistema"
                );

                if (confirmado) {
                    FiberGuardian.Utils.voltarMenuPrincipal();
                } else {
                    dataInicial.focus();
                }
            });
        }

        return {
            init: configurarEventos
        };
    })();

    document.addEventListener("DOMContentLoaded", function () {
        if (FiberGuardian.TelaConsultaLaboratorio && typeof FiberGuardian.TelaConsultaLaboratorio.init === "function") {
            FiberGuardian.TelaConsultaLaboratorio.init();
        } else {
            console.error("Módulo [TelaConsultaLaboratorio] não encontrado ou sem método init().");
        }
    });
})();
