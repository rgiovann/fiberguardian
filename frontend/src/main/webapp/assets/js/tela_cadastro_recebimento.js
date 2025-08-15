(function () {
    window.FiberGuardian = window.FiberGuardian || {};
    // Variáveis globais dentro do escopo do módulo
    let cnpjFornecedor = null;
    let emailUsuario = null;

    FiberGuardian.TelaCadastroRecebimento = (function () {
        function configurarEventos() {
            console.log('Módulo TelaRecebimento inicializado.');

            const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
            const inputFornecedor = document.getElementById('fornecedor');
            const dropdownFornecedor = document.getElementById('dropdownFornecedor');

            const btnBuscarRecebidoPor =
                document.getElementById('btnBuscarRecebidoPor');
            const inputRecebidoPor = document.getElementById('recebidoPor');
            const dropdownRecebidoPor = document.getElementById('dropdownRecebidoPor');

            const btnBuscarProduto = document.getElementById('btnBuscarProduto');
            const inputProduto = document.getElementById('produto');
            const dropdownProduto = document.getElementById('dropdownProduto');

            if (!btnBuscarFornecedor || !inputFornecedor || !dropdownFornecedor) {
                console.error('Elementos da busca de Fornecedor não encontrados.');
                return;
            }

            if (!btnBuscarRecebidoPor || !inputRecebidoPor || !dropdownRecebidoPor) {
                console.error('Elementos da busca de Recebido por não encontrados.');
                return;
            }

            if (!btnBuscarProduto || !inputProduto || !dropdownProduto) {
                console.error('Elementos da busca de Produto não encontrados.');
                return;
            }

            const dateDataRecebimento = document.getElementById('dataRecebimento');

            if (dateDataRecebimento) {
                // Preenche com valor padrão só se estiver vazio
                if (!dateDataRecebimento.value) {
                    const hoje = new Date();
                    const yyyy = hoje.getFullYear();
                    const mm = String(hoje.getMonth() + 1).padStart(2, '0');
                    const dd = String(hoje.getDate()).padStart(2, '0');
                    dateDataRecebimento.value = `${yyyy}-${mm}-${dd}`;
                }
            }

            const camposMonetarios = document.querySelectorAll('.campo-monetario');

            camposMonetarios.forEach((campo) => {
                FiberGuardian.Utils.aplicarMascaraMonetaria(campo);
            });

            const camposCalculo = [
                document.getElementById('quantRecebida'),
                document.getElementById('numeroCaixas'),
                document.getElementById('valorUnit'),
            ];

            camposCalculo.forEach((campo) => {
                if (campo) {
                    campo.addEventListener('input', updateCalculations);
                }
            });

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
                [dropdownFornecedor, dropdownRecebidoPor],
                [inputFornecedor, inputRecebidoPor],
                [btnBuscarFornecedor, btnBuscarRecebidoPor]
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
                        // Armazena do objeto recebido o cnpj
                        cnpjFornecedor = item.cnpj;
                        //console.log('Index:', index);
                        //console.log('CNPJ: [cnpjFornecedor]', cnpjFornecedor);
                        // aqui você pode salvar em variável ou mandar para outra função
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

            btnBuscarRecebidoPor.addEventListener('click', async function () {
                const codigoParcial = inputRecebidoPor.value.trim();

                // Validação defensiva
                if (!codigoParcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Digite parte do nome para buscar.',
                        'warning',
                        inputRecebidoPor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    const resposta = await fetch(
                        `/api/usuarios/list/recebimento?nome=${encodeURIComponent(
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
                        const listaUsuarios = await resposta.json();

                        const { index, item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputRecebidoPor,
                                dropdown: dropdownRecebidoPor,
                                lista: listaUsuarios,
                                camposExibir: ['nome', 'email', 'setor', 'turno'],
                                titulosColunas: ['Usuário', 'Email', 'Setor', 'Turno'],
                                msgVazio: 'Nenhum usuário encontrado.',
                            });
                        // Armazena do objeto recebido o email
                        emailUsuario = item.email;
                        //console.log('Index:', index);
                        //console.log('Email: [emailUsuario]', emailUsuario);
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputRecebidoPor
                        );
                    }
                } catch (erro) {
                    console.error('Erro ao buscar usuários:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar usuários.',
                        inputRecebidoPor,
                        erro
                    );
                }
            });

            btnBuscarProduto.addEventListener('click', async function () {
                const codigoParcial = inputProduto.value.trim();

                // Validação defensiva
                /*
                if (!codigoParcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Digite parte do nome do produto para buscar.',
                        'warning',
                        inputProduto
                    );
                    return;
                } */

                if (!cnpjFornecedor) {
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
                    url.searchParams.append('cnpj', cnpjFornecedor);

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
                        //console.log('Index:', index);
                        //console.log('Código Produto:', codigoProdutoSelecionado);
                        // aqui você pode salvar em variável ou mandar para outra função
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
        }

        function updateCalculations() {
            const quantRecebida =
                parseInt(document.getElementById('quantRecebida')?.value) || 0;
            const numeroCaixas =
                parseInt(document.getElementById('numeroCaixas')?.value) || 0;
            const valorUnit =
                parseFloat(document.getElementById('valorUnit')?.value) || 0;

            const rochasPorCaixa = 300;
            const quantRochas = numeroCaixas * rochasPorCaixa;
            document.getElementById('quantRochas')?.setAttribute('value', quantRochas);

            const pesoMedio =
                quantRecebida > 0 && quantRochas > 0
                    ? (quantRecebida / quantRochas).toFixed(2)
                    : 0;
            document.getElementById('pesoMedio')?.setAttribute('value', pesoMedio);

            const valorTotalItem =
                quantRecebida > 0 ? (valorUnit * quantRecebida).toFixed(2) : 0;
            document
                .getElementById('valorTotalItem')
                ?.setAttribute('value', valorTotalItem);

            const pesoMedioCaixa =
                numeroCaixas > 0 ? (quantRecebida / numeroCaixas).toFixed(2) : 0;
            document
                .getElementById('pesoMedioCaixa')
                ?.setAttribute('value', pesoMedioCaixa);
        }

        function voltarParaInicio() {
            document.getElementById('conteudo-principal').innerHTML = `
                <h3>Bem-vindo ao FiberGuardian</h3>
                <p>Selecione uma opção no menu lateral para começar.</p>
            `;
        }

        // Revealing: expõe apenas as funções públicas
        return {
            init: configurarEventos,
        };
    })();
})();
