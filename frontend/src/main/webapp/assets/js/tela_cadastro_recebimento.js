(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaCadastroRecebimento = (function () {
        function configurarEventos() {
            console.log('Módulo TelaRecebimento inicializado.');

            const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
            const inputFornecedor = document.getElementById('fornecedor');
            const dropdownFornecedor = document.getElementById('dropdownFornecedor');

            if (!btnBuscarFornecedor || !inputFornecedor || !dropdownFornecedor) {
                console.error('Elementos da busca de Fornecedor não encontrados.');
                return;
            }

            //document.addEventListener('click', (event) => {
            //    // Se o clique não for no input nem dentro do dropdown
            //    if (
            //        !dropdownFornecedor.contains(event.target) &&
            //        event.target !== inputFornecedor
            //    ) {
            //        dropdownFornecedor.classList.remove('show');
            //        inputFornecedor.focus();
            //    }
            //});

            FiberGuardian.Utils.fecharDropdownSeAberto(
                dropdownFornecedor,
                inputFornecedor,
                btnBuscarFornecedor
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
                        `/api/fornecedores/list?nome=${encodeURIComponent(
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
                        //renderizarDropdownFornecedores(listaFornecedores);
                        FiberGuardian.Utils.renderizarDropdownGenerico({
                            input: inputFornecedor,
                            dropdown: dropdownFornecedor,
                            lista: listaFornecedores,
                            campoExibir: 'nomeFornecedor',
                            titulo: 'Fornecedor',
                            msgVazio: 'Nenhum fornecedor encontrado.',
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
                    console.error('Erro ao buscar notas fiscais:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar notas fiscais.',
                        inputFornecedor,
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
            const valorNota =
                parseFloat(document.getElementById('valorNota')?.value) || 0;

            const rochasPorCaixa = 300;

            const quantRochas = numeroCaixas * rochasPorCaixa;
            document.getElementById('quantRochas')?.setAttribute('value', quantRochas);

            const pesoMedio =
                quantRecebida > 0 && quantRochas > 0
                    ? (quantRecebida / quantRochas).toFixed(2)
                    : 0;
            document.getElementById('pesoMedio')?.setAttribute('value', pesoMedio);

            const valorUnit =
                quantRecebida > 0 ? (valorNota / quantRecebida).toFixed(2) : 0;
            document.getElementById('valorUnit')?.setAttribute('value', valorUnit);

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
            //updateCalculations: updateCalculations,
            //voltarParaInicio: voltarParaInicio,
        };
    })();
})();
