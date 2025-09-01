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
            /*
            btnDownloadNrNotaFiscal.addEventListener('click', function () {
                if (!nrNotaFiscalSelecionado) return;

                // URL do endpoint que retorna o PDF
                const url = `/api/notas-fiscais/pdf/${nrNotaFiscalSelecionado}`;

                // forçar download abrindo em nova aba
                const link = document.createElement('a');
                link.href = url;
                link.target = '_blank'; // abre em nova aba
                link.download = `NotaFiscal-${nrNotaFiscalSelecionado}.pdf`;
                link.click();
            });
            */

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

            /*
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
            */

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
        }

        return {
            init: configurarEventos,
        };
    })();
})();
