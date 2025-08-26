(function () {
    window.FiberGuardian = window.FiberGuardian || {};
    // Variáveis globais dentro do escopo do módulo FiberGuardian.TelaCadastroRecebimento
    //  (não vaza para window).
    let cnpjFornecedorSelecionado = null;
    let codigoProdutoSelecionado = null;
    let nrNotaFiscalSelecionado = null;

    // Armazena, em memória, os itens da nota fiscal enquanto o usuário trabalha na tela.
    let itensRecebimento = [];

    FiberGuardian.TelaConsultaRecebimento = (function () {
        function configurarEventos() {
            console.log('Módulo Tela Pesquisa Recebimento inicializado.');

            const inputFornecedor = document.getElementById('fornecedor');
            const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
            const dropdownFornecedor = document.getElementById('dropdownFornecedor');
            const btnTrocarFornecedor = document.getElementById('btnTrocarFornecedor');

            const inputNrNotFiscal = document.getElementById('nrNotaFiscal');
            const btnBuscarNrNotaFiscal = document.getElementById(
                'btnBuscarNrNotaFiscal'
            );
            const dropdownNrNotaFiscal =
                document.getElementById('dropdownNrNotaFiscal');

            const inputProduto = document.getElementById('produto');
            const btnBuscarProduto = document.getElementById('btnBuscarProduto');
            const dropdownProduto = document.getElementById('dropdownProduto');
            const btnTrocarProduto = document.getElementById('btnTrocarProduto');
            const btnSair = document.getElementById('btnSair');

            if (!btnSair) {
                console.error('Botão Sair não encontrado!');
                return;
            }
            /*
            const btnAvancar = document.getElementById('btnAvancarItens');
            const btnSalvarItem = document.getElementById('btnSalvarItem');
            */

            if (
                !btnBuscarFornecedor ||
                !inputFornecedor ||
                !dropdownFornecedor ||
                !btnTrocarFornecedor
            ) {
                console.error('Elementos da busca de Fornecedor não encontrados.');
                return;
            }

            if (
                !btnBuscarProduto ||
                !inputProduto ||
                !dropdownProduto ||
                !btnTrocarProduto
            ) {
                console.error('Elementos da busca de Produto não encontrados.');
                return;
            }

            if (!btnBuscarNrNotaFiscal || !inputNrNotFiscal || !dropdownNrNotaFiscal) {
                console.error('Elementos da busca de Nota Fiscal não encontrados.');
                return;
            }

            const dataInicial = document.getElementById('dataInicial');
            const dataFinal = document.getElementById('dataFinal');

            /*
            document
                .getElementById('btnMenuPrincipal')
                .addEventListener('click', async () => {
                    const confirmado = await FiberGuardian.Utils.confirmarAcaoAsync(
                        'Deseja realmente voltar ao Menu Principal?',
                        'Sair do Sistema'
                    );

                    if (confirmado) {
                        window.location.href = 'index.html';
                    }
                });
            */

            /*
            LEMBRAR === VALOR TOTAL
                const valorInput = document.getElementById('valorTotal').value; // "12,34"
                const valorParaJson = valorInput.replace(',', '.');             // "12.34"

                const json = {
                valorTotal: Number(valorParaJson), // ou parseFloat(valorParaJson)
                // outros campos...
                };

                fetch('/api/notas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(json)
                });

            */

            FiberGuardian.Utils.fecharQualquerDropdownAberto(
                [dropdownFornecedor, dropdownNrNotaFiscal, dropdownProduto],
                [inputFornecedor, inputProduto, inputNrNotFiscal],
                [btnBuscarFornecedor, btnBuscarProduto, btnBuscarNrNotaFiscal]
            );

            btnBuscarFornecedor.addEventListener('click', async function () {
                const codigoParcial = inputFornecedor.value.trim();

                // Validação defensiva
                if (!codigoParcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Digite parte do nome do fornecedor para buscar.',
                        'warning',
                        inputFornecedor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    const resposta = await fetch(
                        `/api/fornecedores/list/recebimento?nome=${encodeURIComponent(
                            codigoParcial
                        )}`,
                        {
                            method: 'GET',
                            headers: {
                                'Content-Type': 'application/json',
                                'X-XSRF-TOKEN': csrfToken,
                            },
                            credentials: 'include',
                        }
                    );

                    if (resposta.ok) {
                        const listaFornecedores = await resposta.json();

                        const { index, item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputFornecedor,
                                dropdown: dropdownFornecedor,
                                lista: listaFornecedores,
                                camposExibir: ['nome', 'cnpj'],
                                titulosColunas: ['Fornecedor', 'CNPJ'],
                                msgVazio: 'Nenhum fornecedor encontrado.',
                            });

                        cnpjFornecedorSelecionado = item.cnpj;
                        // trava campo fornecedor
                        inputFornecedor.readOnly = true;
                        inputFornecedor.classList.add('campo-desabilitado');

                        // desabilita botão Buscar
                        btnBuscarFornecedor.disabled = true;
                        btnBuscarFornecedor.classList.add('campo-desabilitado');

                        // habilita botão Trocar
                        btnTrocarFornecedor.disabled = false;
                        btnTrocarFornecedor.classList.remove('campo-desabilitado');

                        // Quando clicar em "Trocar fornecedor"
                        btnTrocarFornecedor.addEventListener('click', () => {
                            cnpjFornecedorSelecionado = null;
                            inputFornecedor.value = '';
                            inputFornecedor.readOnly = false;
                            inputFornecedor.classList.remove('campo-desabilitado');

                            btnBuscarFornecedor.disabled = false;
                            btnBuscarFornecedor.classList.remove('campo-desabilitado');

                            btnTrocarFornecedor.disabled = true;
                            btnTrocarFornecedor.classList.add('campo-desabilitado');
                        });
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputFornecedor
                        );
                    }
                } catch (erro) {
                    console.error('Erro ao buscar fornecedores:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar fornecedores.',
                        inputFornecedor,
                        erro
                    );
                }
            });

            btnBuscarProduto.addEventListener('click', async function () {
                const codigoParcial = inputProduto.value.trim();

                if (!cnpjFornecedorSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'É necessario selecionar o fornecedor antes de selecionar o produto.',
                        'warning',
                        inputFornecedor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    // Monta a URL com os dois parâmetros
                    const url = new URL(
                        '/api/produtos/list/recebimento',
                        window.location.origin
                    );
                    url.searchParams.append('cnpj', cnpjFornecedorSelecionado);

                    if (codigoParcial) {
                        url.searchParams.append('descricao', codigoParcial);
                    }

                    const resposta = await fetch(url.toString(), {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include',
                    });

                    if (resposta.ok) {
                        const listaProdutos = await resposta.json();

                        const { index, item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputProduto,
                                dropdown: dropdownProduto,
                                lista: listaProdutos,
                                camposExibir: ['descricao', 'codigo'],
                                titulosColunas: ['Produto', 'Código'],
                                msgVazio: 'Nenhum produto encontrado.',
                            });

                        // Armazena do objeto recebido o código ou descrição
                        codigoProdutoSelecionado = item.codigo;

                        // trava campo Produto
                        inputProduto.readOnly = true;
                        inputProduto.classList.add('campo-desabilitado');

                        // desabilita botão Buscar
                        btnBuscarProduto.disabled = true;
                        btnBuscarProduto.classList.add('campo-desabilitado');

                        // habilita botão Trocar
                        btnTrocarProduto.disabled = false;
                        btnTrocarProduto.classList.remove('campo-desabilitado');

                        // evento de trocar
                        btnTrocarProduto.addEventListener('click', () => {
                            codigoProdutoSelecionado = null;
                            inputProduto.value = '';
                            inputProduto.readOnly = false;
                            inputProduto.classList.remove('campo-desabilitado');

                            btnBuscarProduto.disabled = false;
                            btnBuscarProduto.classList.remove('campo-desabilitado');

                            btnTrocarProduto.disabled = true;
                            btnTrocarProduto.classList.add('campo-desabilitado');
                        });
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputProduto
                        );
                    }
                } catch (erro) {
                    console.error('Erro ao buscar produtos:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar produtos.',
                        inputProduto,
                        erro
                    );
                }
            });

            btnBuscarNrNotaFiscal.addEventListener('click', async function () {
                const codigoParcial = inputNrNotFiscal.value.trim();

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    // Monta a URL com os dois parâmetros
                    const url = new URL(
                        '/api/produtos/list/notasfiscais',
                        window.location.origin
                    );
                    url.searchParams.append('cnpj', cnpjFornecedorSelecionado);

                    if (codigoParcial) {
                        url.searchParams.append('descricao', codigoParcial);
                    }

                    const resposta = await fetch(url.toString(), {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include',
                    });

                    if (resposta.ok) {
                        const listaNotasFiscais = await resposta.json();

                        const { index, item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputNrNotFiscal,
                                dropdown: dropdownNrNotaFiscal,
                                lista: listaNotasFiscais,
                                camposExibir: ['codigoNf', 'cnpj', 'descricao'],
                                titulosColunas: ['Nota Fiscal', 'CNPJ', 'Empresa'],
                                msgVazio: 'Nenhum produto encontrado.',
                            });

                        // Armazena do objeto recebido o código ou descrição
                        nrNotaFiscalSelecionado = item.codigo;
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputNrNotFiscal
                        );
                    }
                } catch (erro) {
                    console.error('Erro ao buscar notas fiscais:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar notas fiscais.',
                        nrNotaFiscalSelecionado,
                        erro
                    );
                }
            });

            btnSair.addEventListener('click', async () => {
                const confirmado = await FiberGuardian.Utils.confirmarAcaoAsync(
                    'Deseja realmente voltar ao Menu Principal?',
                    'Sair do Sistema'
                );

                if (confirmado) {
                    FiberGuardian.Utils.voltarMenuPrincipal();
                } else {
                    // Se não confirmou, volta o foco para o campo inicial
                    dataInicial.focus();
                }
            });
        }

        return {
            init: configurarEventos,
        };
    })();
})();
