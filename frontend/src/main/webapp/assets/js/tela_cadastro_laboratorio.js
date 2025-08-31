(function () {
    window.FiberGuardian = window.FiberGuardian || {};
    let cnpjFornecedorSelecionado = null;
    let codigoProdutoSelecionado = null;
    let nrNotaFiscalSelecionado = null;

    let itensRecebimento = [];

    FiberGuardian.TelaCadastroLaboratorio = (function() {
        function configurarEventos() {
            console.log('Módulo Tela Cadastro Laboratorio inicializado.');

            const formLaboratorio = document.getElementById('laboratorioForm'); // Obtém o elemento do formulário
            const inputFornecedor = document.getElementById('fornecedor');
            const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
            const dropdownFornecedor = document.getElementById('dropdownFornecedor');
            const btnTrocarFornecedor = document.getElementById('btnTrocarFornecedor');

            const inputNrNotFiscal = document.getElementById('nrNotaFiscal');
            const btnBuscarNrNotaFiscal = document.getElementById('btnBuscarNrNotaFiscal');
            const dropdownNrNotaFiscal = document.getElementById('dropdownNrNotaFiscal');

            const inputProduto = document.getElementById('produto');
            const btnBuscarProduto = document.getElementById('btnBuscarProduto');
            const dropdownProduto = document.getElementById('dropdownProduto');
            const btnTrocarProduto = document.getElementById('btnTrocarProduto');

            const btnSair = document.getElementById('btnSair');

            if (!btnBuscarFornecedor || !inputFornecedor || !dropdownFornecedor || !btnTrocarFornecedor) {
                console.error('Elementos da busca de Fornecedor não encontrados.');
                return;
            }

            if (!btnBuscarProduto || !inputProduto || !dropdownProduto || !btnTrocarProduto) {
                console.error('Elementos da busca de Produto não encontrados.');
                return;
            }

            if (!btnBuscarNrNotaFiscal || !inputNrNotFiscal || !dropdownNrNotaFiscal) {
                console.error('Elementos da busca de Nota Fiscal não encontrados.');
                return;
            }

            // Função para manipular o envio dos dados do formulário
            async function handleFormSubmit(event) {
                event.preventDefault(); // Impede o envio padrão do formulário

                const formData = {
                    cnpjFornecedor: cnpjFornecedorSelecionado,
                    nrNotaFiscal: nrNotaFiscalSelecionado,
                    codigoProduto: codigoProdutoSelecionado,
                    numeroLote: document.getElementById('numeroLote').value,
                    cvm: document.getElementById('cvm').value,
                    pontosFinos: document.getElementById('pontosFinos').value,
                    pontosGrossos: document.getElementById('pontosGrossos').value,
                    neps: document.getElementById('neps').value,
                    hPilosidade: document.getElementById('hPilosidade').value,
                    resistencia: document.getElementById('resistencia').value,
                    alongamento: document.getElementById('alongamento').value,
                    tituloNe: document.getElementById('tituloNe').value,
                    torcaoTm: document.getElementById('torcaoTm').value,
                    status: document.getElementById('status').value,
                    liberacaoPor: document.getElementById('liberacaoPor').value,
                    infoRecebimento: document.getElementById('infoRecebimento').value,
                };

                // Valida se um número de lote, produto e nota fiscal foram selecionados/preenchidos
                if (!cnpjFornecedorSelecionado || !nrNotaFiscalSelecionado || !codigoProdutoSelecionado || !formData.numeroLote) {
                     FiberGuardian.Utils.exibirMensagemModalComFoco('Por favor, preencha todos os campos obrigatórios na seção "Dados do Teste".', 'warning', formLaboratorio);
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const resposta = await fetch('/api/laboratorio/save', { // Ajuste o endpoint conforme necessário
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken
                        },
                        body: JSON.stringify(formData),
                        credentials: 'include',
                    });

                    if (resposta.ok) {
                        FiberGuardian.Utils.exibirMensagemModalComFoco('Dados do laboratório salvos com sucesso!', 'success', formLaboratorio);
                        // Opcionalmente, você pode redefinir o formulário ou redirecionar o usuário aqui
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta, formLaboratorio);
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede('Erro de rede ao salvar os dados do laboratório.', formLaboratorio, erro);
                }
            }


            // Função auxiliar para gerenciar o estado dos campos e botões
            function alternarEstadoCampos(input, btnBuscar, btnTrocar, isSelecionado) {
                input.readOnly = isSelecionado;
                input.classList.toggle('campo-desabilitado', isSelecionado);
                btnBuscar.disabled = isSelecionado;
                btnBuscar.classList.toggle('campo-desabilitado', isSelecionado);
                btnTrocar.disabled = !isSelecionado;
                btnTrocar.classList.toggle('campo-desabilitado', !isSelecionado);
            }

            // Funções de reset
            function resetarFornecedor() {
                cnpjFornecedorSelecionado = null;
                inputFornecedor.value = '';
                alternarEstadoCampos(inputFornecedor, btnBuscarFornecedor, btnTrocarFornecedor, false);
            }

            function resetarProduto() {
                codigoProdutoSelecionado = null;
                inputProduto.value = '';
                alternarEstadoCampos(inputProduto, btnBuscarProduto, btnTrocarProduto, false);
            }

            function resetarNotaFiscal() {
                nrNotaFiscalSelecionado = null;
                inputNrNotFiscal.value = '';
                // A nota fiscal não tem botão de 'Trocar', mas a lógica de reset é útil.
            }

            FiberGuardian.Utils.fecharQualquerDropdownAberto(
                [dropdownFornecedor, dropdownNrNotaFiscal, dropdownProduto],
                [inputFornecedor, inputProduto, inputNrNotFiscal],
                [btnBuscarFornecedor, btnBuscarProduto, btnBuscarNrNotaFiscal]
            );

            // Event listener para a busca de fornecedor
            btnBuscarFornecedor.addEventListener('click', async function () {
                const codigoParcial = inputFornecedor.value.trim();
                if (!codigoParcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco('Digite parte do nome do fornecedor para buscar.', 'warning', inputFornecedor);
                    return;
                }
                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const resposta = await fetch(`/api/fornecedores/list/recebimento?nome=${encodeURIComponent(codigoParcial)}`, {
                        method: 'GET',
                        headers: { 'Content-Type': 'application/json', 'X-XSRF-TOKEN': csrfToken },
                        credentials: 'include',
                    });
                    if (resposta.ok) {
                        const listaFornecedores = await resposta.json();
                        const { item } = await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                            input: inputFornecedor,
                            dropdown: dropdownFornecedor,
                            lista: listaFornecedores,
                            camposExibir: ['nome', 'cnpj'],
                            titulosColunas: ['Fornecedor', 'CNPJ'],
                            msgVazio: 'Nenhum fornecedor encontrado.',
                        });
                        if (item) {
                            cnpjFornecedorSelecionado = item.cnpj;
                            alternarEstadoCampos(inputFornecedor, btnBuscarFornecedor, btnTrocarFornecedor, true);
                            resetarNotaFiscal();
                            resetarProduto();
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta, inputFornecedor);
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede('Erro de rede ao buscar fornecedores.', inputFornecedor, erro);
                }
            });

            // Event listener para o botão de troca de fornecedor
            btnTrocarFornecedor.addEventListener('click', () => {
                resetarFornecedor();
                resetarNotaFiscal();
                resetarProduto();
            });

            // Event listener para a busca de nota fiscal
            btnBuscarNrNotaFiscal.addEventListener('click', async function () {
                const codigoParcial = inputNrNotFiscal.value.trim();
                if (!cnpjFornecedorSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco('É necessário selecionar um fornecedor antes de buscar a nota fiscal.', 'warning', inputFornecedor);
                    return;
                }
                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const url = new URL('/api/produtos/list/notasfiscais', window.location.origin);
                    url.searchParams.append('cnpj', cnpjFornecedorSelecionado);
                    if (codigoParcial) {
                        url.searchParams.append('descricao', codigoParcial);
                    }
                    const resposta = await fetch(url.toString(), {
                        method: 'GET',
                        headers: { 'Content-Type': 'application/json', 'X-XSRF-TOKEN': csrfToken },
                        credentials: 'include',
                    });
                    if (resposta.ok) {
                        const listaNotasFiscais = await resposta.json();
                        const { item } = await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                            input: inputNrNotFiscal,
                            dropdown: dropdownNrNotaFiscal,
                            lista: listaNotasFiscais,
                            camposExibir: ['codigoNf', 'cnpj', 'descricao'],
                            titulosColunas: ['Nota Fiscal', 'CNPJ', 'Empresa'],
                            msgVazio: 'Nenhum produto encontrado.',
                        });
                        if (item) {
                            nrNotaFiscalSelecionado = item.codigoNf;
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta, inputNrNotFiscal);
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede('Erro de rede ao buscar notas fiscais.', inputNrNotFiscal, erro);
                }
            });

            // Event listener para a busca de produto
            btnBuscarProduto.addEventListener('click', async function () {
                const codigoParcial = inputProduto.value.trim();
                if (!cnpjFornecedorSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco('É necessário selecionar o fornecedor antes de selecionar o produto.', 'warning', inputFornecedor);
                    return;
                }
                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const url = new URL('/api/produtos/list/recebimento', window.location.origin);
                    url.searchParams.append('cnpj', cnpjFornecedorSelecionado);
                    if (codigoParcial) {
                        url.searchParams.append('descricao', codigoParcial);
                    }
                    const resposta = await fetch(url.toString(), {
                        method: 'GET',
                        headers: { 'Content-Type': 'application/json', 'X-XSRF-TOKEN': csrfToken },
                        credentials: 'include',
                    });
                    if (resposta.ok) {
                        const listaProdutos = await resposta.json();
                        const { item } = await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                            input: inputProduto,
                            dropdown: dropdownProduto,
                            lista: listaProdutos,
                            camposExibir: ['descricao', 'codigo'],
                            titulosColunas: ['Produto', 'Código'],
                            msgVazio: 'Nenhum produto encontrado.',
                        });
                        if (item) {
                            codigoProdutoSelecionado = item.codigo;
                            alternarEstadoCampos(inputProduto, btnBuscarProduto, btnTrocarProduto, true);
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta, inputProduto);
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede('Erro de rede ao buscar produtos.', inputProduto, erro);
                }
            });

            // Event listener para o botão de troca de produto
            btnTrocarProduto.addEventListener('click', () => {
                resetarProduto();
            });

            // Event listener para o botão de sair
            if (btnSair) {
              btnSair.addEventListener('click', async () => {
              const confirmacao = await FiberGuardian.Utils.confirmarAcaoAsync('Tem certeza que deseja sair?', 'Sair do Sistema');
                 if (confirmacao) {
                        FiberGuardian.Utils.voltarMenuPrincipal();
                    }
                });
            }

            // Event listener para o envio do formulário
            if (formLaboratorio) {
                formLaboratorio.addEventListener('submit', handleFormSubmit);
            }
        }

        return {
            init: configurarEventos,
        };
    })();
})();
