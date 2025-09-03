(function () {
    window.FiberGuardian = window.FiberGuardian || {};
    let cnpjFornecedorSelecionado = null;
    let codigoProdutoSelecionado = null;
    let nrNotaFiscalSelecionado = null;
    let nrNotaFiscalSelecionadoId = null;
    let emailLiberacaoPor = null;

    FiberGuardian.TelaCadastroLaboratorio = (function () {
        function configurarEventos() {
            console.log('Módulo Tela Cadastro Laboratorio inicializado.');
            cnpjFornecedorSelecionado = null;
            codigoProdutoSelecionado = null;
            nrNotaFiscalSelecionado = null;
            nrNotaFiscalSelecionadoId = null;
            emailLiberacaoPor = null;
            const formLaboratorio = document.getElementById('laboratorioForm'); // Obtém o elemento do formulário
            const inputFornecedor = document.getElementById('fornecedor');
            const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
            const dropdownFornecedor = document.getElementById('dropdownFornecedor');
            const btnTrocarFornecedor = document.getElementById('btnTrocarFornecedor');
            const dateDataLaudoLab = document.getElementById('dataLaudo');
            const inputLiberacaoPor = document.getElementById('inputLiberacaoPor');
            const dropdownLiberacaoPor =
                document.getElementById('dropdownLiberacaoPor');
            const btnBuscarLiberacaoPor = document.getElementById(
                'btnBuscarLiberacaoPor'
            );
            const btnTrocarLiberacaoPor = document.getElementById(
                'btnTrocarLiberacaoPor'
            );

            const inputNrNotFiscal = document.getElementById('nrNotaFiscal');
            const btnBuscarNrNotaFiscal = document.getElementById(
                'btnBuscarNrNotaFiscal'
            );
            const dropdownNrNotaFiscal =
                document.getElementById('dropdownNrNotaFiscal');
            const btnTrocarNotaFiscal = document.getElementById(
                'btnTrocarNrNotaFiscal'
            );
            const btnDownloadNrNotaFiscal = document.getElementById(
                'btnDownloadNrNotaFiscal'
            );

            const inputProduto = document.getElementById('produto');
            const btnBuscarProduto = document.getElementById('btnBuscarProduto');
            const dropdownProduto = document.getElementById('dropdownProduto');
            const btnTrocarProduto = document.getElementById('btnTrocarProduto');

            const btnSair = document.getElementById('btnSair');
            if (
                !btnBuscarLiberacaoPor ||
                !inputLiberacaoPor ||
                !dropdownLiberacaoPor ||
                !btnTrocarLiberacaoPor
            ) {
                console.error(
                    'Elementos da busca de quem liberou o laudo não encontrados.'
                );
                return;
            }

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

            if (
                !btnBuscarNrNotaFiscal ||
                !inputNrNotFiscal ||
                !dropdownNrNotaFiscal ||
                !btnTrocarNotaFiscal ||
                !btnDownloadNrNotaFiscal
            ) {
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
                if (
                    !cnpjFornecedorSelecionado ||
                    !nrNotaFiscalSelecionado ||
                    !codigoProdutoSelecionado ||
                    !nrNotaFiscalSelecionadoId ||
                    !formData.numeroLote
                ) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Por favor, preencha todos os campos obrigatórios na seção "Dados do Teste".',
                        'warning',
                        formLaboratorio
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
                    const resposta = await fetch('/api/laboratorio/save', {
                        // Ajuste o endpoint conforme necessário
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        body: JSON.stringify(formData),
                        credentials: 'include',
                    });

                    if (resposta.ok) {
                        FiberGuardian.Utils.exibirMensagemModalComFoco(
                            'Dados do laboratório salvos com sucesso!',
                            'success',
                            formLaboratorio
                        );
                        // Opcionalmente, você pode redefinir o formulário ou redirecionar o usuário aqui
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            formLaboratorio
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao salvar os dados do laboratório.',
                        formLaboratorio,
                        erro
                    );
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
                alternarEstadoCampos(
                    inputFornecedor,
                    btnBuscarFornecedor,
                    btnTrocarFornecedor,
                    false
                );
            }

            function resetarProduto() {
                codigoProdutoSelecionado = null;
                inputProduto.value = '';
                alternarEstadoCampos(
                    inputProduto,
                    btnBuscarProduto,
                    btnTrocarProduto,
                    false
                );
            }

            function resetarNotaFiscal() {
                nrNotaFiscalSelecionado = null;
                nrNotaFiscalSelecionadoId = null;
                btnDownloadNrNotaFiscal.disabled = true;
                inputNrNotFiscal.value = '';

                alternarEstadoCampos(
                    inputNrNotFiscal,
                    btnBuscarNrNotaFiscal,
                    btnTrocarNotaFiscal,
                    false
                );
            }

            function resetarLiberacaoPor() {
                emailLiberacaoPor = null;
                inputLiberacaoPor.value = '';

                alternarEstadoCampos(
                    inputLiberacaoPor,
                    btnBuscarLiberacaoPor,
                    btnTrocarLiberacaoPor,
                    false
                );
            }

            FiberGuardian.Utils.fecharQualquerDropdownAberto(
                [
                    dropdownFornecedor,
                    dropdownNrNotaFiscal,
                    dropdownProduto,
                    dropdownLiberacaoPor,
                ],
                [inputFornecedor, inputProduto, inputNrNotFiscal, inputLiberacaoPor],
                [
                    btnBuscarFornecedor,
                    btnBuscarProduto,
                    btnBuscarNrNotaFiscal,
                    btnBuscarLiberacaoPor,
                ]
            );

            btnDownloadNrNotaFiscal.addEventListener('click', async function () {
                if (!nrNotaFiscalSelecionado) return;

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    const url = new URL(
                        `/api/pdf-notas-fiscais/${cnpjFornecedorSelecionado}/${nrNotaFiscalSelecionado}`,
                        window.location.origin
                    );

                    const resposta = await fetch(url.toString(), {
                        method: 'GET',
                        headers: {
                            Accept: 'application/pdf',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include',
                    });

                    if (resposta.ok) {
                        // Tratar resposta binária (PDF)
                        const contentType = resposta.headers.get('Content-Type');

                        if (contentType && contentType.includes('application/pdf')) {
                            // PDF retornado diretamente
                            const blob = await resposta.blob();
                            FiberGuardian.Utils.downloadArquivo(
                                blob,
                                `NF_${nrNotaFiscalSelecionado}.pdf`,
                                'application/pdf'
                            );
                        } else if (resposta.status === 302) {
                            // Redirecionamento para URL externa (S3, etc.)
                            const location = resposta.headers.get('Location');
                            if (location) {
                                window.open(location, '_blank');
                            } else {
                                throw new Error(
                                    'URL de redirecionamento não encontrada'
                                );
                            }
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputNrNotFiscal
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao baixar PDF da nota fiscal.',
                        inputNrNotFiscal,
                        erro
                    );
                }
            });

            // Event listener para a busca de fornecedor
            btnBuscarFornecedor.addEventListener('click', async function () {
                const codigoParcial = inputFornecedor.value.trim();
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
                        const { item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputFornecedor,
                                dropdown: dropdownFornecedor,
                                lista: listaFornecedores,
                                camposExibir: ['nome', 'cnpj'],
                                titulosColunas: ['Fornecedor', 'CNPJ'],
                                msgVazio: 'Nenhum fornecedor encontrado.',
                            });
                        if (item) {
                            cnpjFornecedorSelecionado = item.cnpj;
                            alternarEstadoCampos(
                                inputFornecedor,
                                btnBuscarFornecedor,
                                btnTrocarFornecedor,
                                true
                            );
                            resetarNotaFiscal();
                            resetarProduto();
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputFornecedor
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar fornecedores.',
                        inputFornecedor,
                        erro
                    );
                }
            });

            // Event listener para o botão de troca de fornecedor
            btnTrocarFornecedor.addEventListener('click', () => {
                resetarFornecedor();
                resetarNotaFiscal();
                resetarProduto();
            });

            // Event listener para o botão de troca de fornecedor
            btnTrocarProduto.addEventListener('click', () => {
                resetarProduto();
            });

            btnTrocarNotaFiscal.addEventListener('click', () => {
                resetarNotaFiscal();
                resetarProduto();
            });

            btnTrocarLiberacaoPor.addEventListener('click', () => {
                resetarLiberacaoPor();
            });

            function limparFormulario() {
                // Resetar variáveis internas
                cnpjFornecedorSelecionado = null;
                codigoProdutoSelecionado = null;
                nrNotaFiscalSelecionado = null;
                nrNotaFiscalSelecionadoId = null;
                emailLiberacaoPor = null;

                const dateDataLaudoLab = document.getElementById('dataLaudo');

                const hoje = new Date();
                const yyyy = hoje.getFullYear();
                const mm = String(hoje.getMonth() + 1).padStart(2, '0');
                const dd = String(hoje.getDate()).padStart(2, '0');
                dateDataLaudoLab.value = `${yyyy}-${mm}-${dd}`;

                // Resetar campos de texto do formulário
                const formLaboratorio = document.getElementById('laboratorioForm');
                if (formLaboratorio) {
                    formLaboratorio.reset();
                }

                // Fornecedor
                if (inputFornecedor && btnBuscarFornecedor && btnTrocarFornecedor) {
                    inputFornecedor.value = '';
                    alternarEstadoCampos(
                        inputFornecedor,
                        btnBuscarFornecedor,
                        btnTrocarFornecedor,
                        false
                    );
                }

                // Produto
                if (inputProduto && btnBuscarProduto && btnTrocarProduto) {
                    inputProduto.value = '';
                    alternarEstadoCampos(
                        inputProduto,
                        btnBuscarProduto,
                        btnTrocarProduto,
                        false
                    );
                }

                // Nota Fiscal
                if (inputNrNotFiscal && btnBuscarNrNotaFiscal && btnTrocarNotaFiscal) {
                    inputNrNotFiscal.value = '';
                    btnDownloadNrNotaFiscal.disabled = true;
                    alternarEstadoCampos(
                        inputNrNotFiscal,
                        btnBuscarNrNotaFiscal,
                        btnTrocarNotaFiscal,
                        false
                    );
                }

                // LiberacaoPor
                if (
                    inputLiberacaoPor &&
                    btnBuscarLiberacaoPor &&
                    btnTrocarLiberacaoPor
                ) {
                    inputLiberacaoPor.value = '';
                    alternarEstadoCampos(
                        inputLiberacaoPor,
                        btnBuscarLiberacaoPor,
                        btnTrocarLiberacaoPor,
                        false
                    );
                }
            }

            const btnLimpar = document.getElementById('btnLimpar');
            if (btnLimpar) {
                btnLimpar.addEventListener('click', limparFormulario);
            }

            btnBuscarNrNotaFiscal.addEventListener('click', async function () {
                const codigoParcial = inputNrNotFiscal.value.trim();
                if (!cnpjFornecedorSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'É necessário selecionar um fornecedor antes de buscar a nota fiscal.',
                        'warning',
                        inputFornecedor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    // Monta a URL com PathVariable para o CNPJ e query param para codigo_nf
                    const url = new URL(
                        `/api/notas-fiscais/list/por_fornecedor/${cnpjFornecedorSelecionado}`,
                        window.location.origin
                    );
                    if (codigoParcial) {
                        url.searchParams.append('codigo_nf', codigoParcial);
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

                        const { item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputNrNotFiscal,
                                dropdown: dropdownNrNotaFiscal,
                                lista: listaNotasFiscais,
                                camposExibir: [
                                    'codigoNf',
                                    'cnpj',
                                    'dataRecebimento',
                                    'valorTotal',
                                ],
                                titulosColunas: [
                                    'Nota Fiscal',
                                    'CNPJ',
                                    'Data Recebimento',
                                    'Valor Total NF',
                                ],
                                msgVazio: 'Nenhuma nota fiscal encontrada.',
                            });

                        if (item) {
                            nrNotaFiscalSelecionado = item.codigoNf;
                            btnDownloadNrNotaFiscal.disabled = false;
                            alternarEstadoCampos(
                                inputNrNotFiscal,
                                btnBuscarNrNotaFiscal,
                                btnTrocarNotaFiscal,
                                true
                            );
                            resetarProduto();
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputNrNotFiscal
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar notas fiscais.',
                        inputNrNotFiscal,
                        erro
                    );
                }
            });

            btnBuscarLiberacaoPor.addEventListener('click', async function () {
                const codigoParcial = inputLiberacaoPor.value.trim();
                if (!codigoParcial) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Digite parte do nome do nome do usuário para buscar.',
                        'warning',
                        inputLiberacaoPor
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                    // Monta a URL com PathVariable para o CNPJ e query param para codigo_nf
                    const url = new URL(
                        `/api/usuarios/list/recebimento`,
                        window.location.origin
                    );
                    if (codigoParcial) {
                        url.searchParams.append('nome', codigoParcial);
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
                        const listaUsuarios = await resposta.json();

                        const { item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputLiberacaoPor,
                                dropdown: dropdownLiberacaoPor,
                                lista: listaUsuarios,
                                camposExibir: ['nome', 'email', 'setor', 'turno'],
                                titulosColunas: ['Usuário', 'Email', 'Setor', 'Turno'],
                                msgVazio: 'Nenhum usuário encontrado.',
                            });

                        if (item) {
                            emailLiberacaoPor = item.email;
                            alternarEstadoCampos(
                                inputLiberacaoPor,
                                btnBuscarLiberacaoPor,
                                btnTrocarLiberacaoPor,
                                true
                            );
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputLiberacaoPor
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar notas fiscais.',
                        inputLiberacaoPor,
                        erro
                    );
                }
            });

            // Event listener para a busca de produto
            btnBuscarProduto.addEventListener('click', async function () {
                const codigoParcial = inputProduto.value.trim();
                if (!cnpjFornecedorSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'É necessário selecionar o fornecedor antes de selecionar o produto.',
                        'warning',
                        inputFornecedor
                    );
                    return;
                }
                try {
                    const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
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
                        const { item } =
                            await FiberGuardian.Utils.renderizarDropdownGenericoAsync({
                                input: inputProduto,
                                dropdown: dropdownProduto,
                                lista: listaProdutos,
                                camposExibir: ['descricao', 'codigo'],
                                titulosColunas: ['Produto', 'Código'],
                                msgVazio: 'Nenhum produto encontrado.',
                            });
                        if (item) {
                            codigoProdutoSelecionado = item.codigo;
                            alternarEstadoCampos(
                                inputProduto,
                                btnBuscarProduto,
                                btnTrocarProduto,
                                true
                            );
                        }
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            inputProduto
                        );
                    }
                } catch (erro) {
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao buscar produtos.',
                        inputProduto,
                        erro
                    );
                }
            });

            // Event listener para o botão de sair
            if (btnSair) {
                btnSair.addEventListener('click', async () => {
                    const confirmacao = await FiberGuardian.Utils.confirmarAcaoAsync(
                        'Tem certeza que deseja sair?',
                        'Sair do Sistema'
                    );
                    if (confirmacao) {
                        FiberGuardian.Utils.voltarMenuPrincipal();
                    }
                });
            }

            // Event listener para o envio do formulário
            if (formLaboratorio) {
                formLaboratorio.addEventListener('submit', handleFormSubmit);
            }
            //=================================================================================================
            //=================================================================================================
            /*
                        // Dentro de configurarEventos(), após bind dos outros botões:
            const btnFinalizarNota = document.getElementById('btnFinalizarNota');
            if (btnFinalizarNota) {
                btnFinalizarNota.addEventListener('click', async () => {
                    try {
                        // Verifica se há pelo menos 1 item antes de finalizar
                        if (!itensRecebimento || itensRecebimento.length === 0) {
                            FiberGuardian.Utils.exibirMensagemModalComFoco(
                                'Nota deve ter no mínimo 1 item de nota cadastrado',
                                'warning',
                                document.getElementById('produto')
                            );
                            return;
                        }
                        // === Coleta campos do cabeçalho ===
                        const inputNota = document.getElementById('notaFiscal');
                        const inputData = document.getElementById('dataRecebimento');
                        const inputValorTotal = document.getElementById('valorTotal');
                        const inputArquivo = document.getElementById('arquivoNota');

                        // Validação defensiva mínima (usuário pode burlar travas via console)
                        if (
                            !inputNota.value.trim() ||
                            !cnpjFornecedor ||
                            !emailUsuario ||
                            !inputArquivo.files?.length
                        ) {
                            FiberGuardian.Utils.exibirMensagemModalComFoco(
                                'Preencha todos os campos obrigatórios antes de finalizar a nota.',
                                'warning',
                                inputNota
                            );
                            return;
                        }

                        // === Monta objeto da nota + itens ===
                        const dadosNota = {
                            nota: {
                                codigoNf: inputNota.value.trim(),
                                cnpj: cnpjFornecedor,
                                recebidoPor: emailUsuario,
                                dataRecebimento: inputData.value,
                                valorTotal:
                                    parseFloat(
                                        inputValorTotal.value.replace(',', '.')
                                    ) || 0,
                            },
                            itens: itensRecebimento.map((it) => ({
                                codigoProduto: it.codigo,
                                qtdRecebida: it.quantRecebida,
                                nrCaixas: it.numeroCaixas,
                                precoUnitario: it.valorUnit,
                                observacao: it.observacao || '', // pode estar vazio
                            })),
                        };

                        // === Monta objeto de metadados do PDF ===
                        const pdfMeta = {
                            descricao: `Arquivo nota fiscal ${inputNota.value.trim()} relativa ao fornecedor ${cnpjFornecedor}`,
                        };

                        // === Prepara multipart/form-data ===
                        const formData = new FormData();
                        formData.append(
                            'dadosNota',
                            new Blob([JSON.stringify(dadosNota)], {
                                type: 'application/json',
                            })
                        );
                        formData.append(
                            'pdfMeta',
                            new Blob([JSON.stringify(pdfMeta)], {
                                type: 'application/json',
                            })
                        );
                        formData.append('arquivo', inputArquivo.files[0]);

                        // === CSRF token ===
                        const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                        const resposta = await fetch('/api/notas-fiscais', {
                            method: 'POST',
                            headers: {
                                'X-XSRF-TOKEN': csrfToken,
                            },
                            credentials: 'include',
                            body: formData,
                        });

                        if (resposta.ok) {
                            // Zera array global
                            itensRecebimento = [];
                            renderizarTabelaItens();
                            limpaCabecalhoNotaFiscal();
                            limpaItensNotaFiscal();

                            FiberGuardian.Utils.exibirMensagemModalComFoco(
                                'Nota fiscal gravada com sucesso.',
                                'success',
                                inputNota
                            );

                            // === Reset após sucesso ===
                            itensRecebimento = []; // limpa array global
                            renderizarTabelaItens(); // limpa tabela na tela

                            //document.getElementById('formNotaFiscal').reset(); // se você tiver um <form>
                            cnpjFornecedor = null;
                            emailUsuario = null;

                            return;
                        } else if (resposta.status === 403) {
                            FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                            FiberGuardian.Utils.voltarMenuPrincipal();
                        } else {
                            await FiberGuardian.Utils.tratarErroFetch(
                                resposta,
                                inputNota
                            );
                            return;
                        }
                    } catch (erro) {
                        console.error('Falha na requisição:', erro);
                        FiberGuardian.Utils.exibirErroDeRede(
                            'Erro de rede ao finalizar nota fiscal',
                            document.getElementById('notaFiscal'),
                            erro
                        );
                        FiberGuardian.Utils.voltarMenuPrincipal();
                    }
                });
            }
            */

            //=================================================================================================
        }

        return {
            init: configurarEventos,
        };
    })();
})();
