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
            document.addEventListener('click', (event) => {
                // Se o clique não for no input nem dentro do dropdown
                if (
                    !dropdownFornecedor.contains(event.target) &&
                    event.target !== inputFornecedor
                ) {
                    dropdownFornecedor.classList.remove('show');
                    inputFornecedor.focus();
                }
            });

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

            /*
            function renderizarDropdownFornecedores(listaFornecedores) {
                dropdownFornecedor.innerHTML = ''; // Limpa resultados anteriores

                const tabela = document.createElement('table');
                tabela.className = 'table table-sm table-hover mb-0'; // estilos Bootstrap
                tabela.style.border = '1px solid #b5d4f5';
                tabela.style.borderRadius = '4px';
                tabela.style.tableLayout = 'fixed';
                tabela.style.width = '100%';

                const thead = document.createElement('thead');
                thead.innerHTML = `
        <tr>
            <th class="col-drop-down-gen">Fornecedor</th>
        </tr>
    `;
                tabela.appendChild(thead);

                const tbody = document.createElement('tbody');

                if (
                    !Array.isArray(listaFornecedores) ||
                    listaFornecedores.length === 0
                ) {
                    const linha = document.createElement('tr');
                    const celula = document.createElement('td');
                    celula.colSpan = 1;
                    celula.className = 'text-muted text-center';
                    celula.textContent = 'Nenhum fornecedor encontrado.';
                    linha.appendChild(celula);
                    tbody.appendChild(linha);
                } else {
                    listaFornecedores.forEach((fornecedor) => {
                        const linha = document.createElement('tr');
                        linha.style.cursor = 'pointer';

                        const celula = document.createElement('td');
                        celula.textContent = fornecedor.nomeFornecedor;

                        linha.appendChild(celula);

                        linha.addEventListener('click', () => {
                            inputFornecedor.value = fornecedor.nomeFornecedor;
                            dropdownFornecedor.classList.remove('show');
                        });

                        tbody.appendChild(linha);
                    });
                }

                tabela.appendChild(tbody);
                dropdownFornecedor.appendChild(tabela);

                // Exibe dropdown
                dropdownFornecedor.classList.add('show');
            }


            function renderizarDropdownNotas(listaFornecedores) {
                dropdownFornecedor.innerHTML = ''; // Limpa resultados anteriores

                const tabela = document.createElement('table');
                tabela.className = 'table table-sm table-hover mb-0'; // Bootstrap estilos
                tabela.style.border = '1px solid #b5d4f5';
                tabela.style.borderRadius = '4px';
                tabela.style.tableLayout = 'fixed'; // CRÍTICO: força larguras fixas
                tabela.style.width = '100%';

                const thead = document.createElement('thead');
                thead.innerHTML = `
        <tr>
            <th class="col-nf">NF</th>
            <th class="col-cnpj">CNPJ</th>
            <th class="col-email">Recebido por</th>
        </tr>
    `;
                tabela.appendChild(thead);

                const tbody = document.createElement('tbody');

                if (!Array.isArray(listaFornecedores) || listaFornecedores.length === 0) {
                    const linha = document.createElement('tr');
                    const celula = document.createElement('td');
                    celula.colSpan = 3;
                    celula.className = 'text-muted text-center';
                    celula.textContent = 'Nenhuma nota encontrada.';
                    linha.appendChild(celula);
                    tbody.appendChild(linha);
                } else {
                    listaFornecedores.forEach((nf) => {
                        const linha = document.createElement('tr');
                        linha.style.cursor = 'pointer';

                        // Cria células individuais com classes CSS específicas
                        const celulaNf = document.createElement('td');
                        celulaNf.className = 'col-nf';
                        celulaNf.textContent = nf.codigoNf;

                        const celulaCnpj = document.createElement('td');
                        celulaCnpj.className = 'col-cnpj';
                        celulaCnpj.textContent = nf.fornecedor?.cnpj || '';

                        const celulaEmail = document.createElement('td');
                        celulaEmail.className = 'col-email';
                        celulaEmail.textContent = nf.recebidoPor?.email || '';

                        linha.appendChild(celulaNf);
                        linha.appendChild(celulaCnpj);
                        linha.appendChild(celulaEmail);

                        linha.addEventListener('click', () => {
                            inputFornecedor.value = nf.codigoNf;
                            dropdownFornecedor.classList.remove('show');
                        });
                        tbody.appendChild(linha);
                    });
                }

                tabela.appendChild(tbody);
                dropdownFornecedor.appendChild(tabela);

                // Exibe dropdown no mesmo lugar
                dropdownFornecedor.classList.add('show');
            }
            */
        }

        //function configurarEventos() {
        //  console.log("Módulo TelaRecebimento inicializado.");
        /*
      // Adicionar listeners de forma segura
      const quantRecebidaInput = document.getElementById("quantRecebida");
      const numeroCaixasInput = document.getElementById("numeroCaixas");
      const valorNotaInput = document.getElementById("valorNota");

      if (quantRecebidaInput) {
        quantRecebidaInput.addEventListener("input", this.updateCalculations);
      }
      if (numeroCaixasInput) {
        numeroCaixasInput.addEventListener("input", this.updateCalculations);
      }
      if (valorNotaInput) {
        valorNotaInput.addEventListener("input", this.updateCalculations);
      }

      // Executar cálculos iniciais
      this.updateCalculations();

*/
        // }

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
