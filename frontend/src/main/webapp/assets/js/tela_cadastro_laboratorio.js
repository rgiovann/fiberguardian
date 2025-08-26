(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaCadastroLaboratorio = (function () {
        const URL_API_FORNECEDOR = '/api/fornecedores/list/recebimento';
        const URL_API_NOTA_FISCAL = '/api/notas-fiscais/list';
        const URL_API_PRODUTO = '/api/produtos/list';
        const URL_API_TESTE = '/api/testes-laboratorio';

        let fornecedorSelecionado = null;
        let notaFiscalSelecionada = null;
        let produtoSelecionado = null;
        let testeIdParaEdicao = null;

        const form = document.getElementById('laboratorioForm');
        const inputFornecedor = document.getElementById('fornecedor');
        const dropdownFornecedor = document.getElementById('dropdownFornecedor');
        const btnBuscarFornecedor = document.getElementById('btnBuscarFornecedor');
        const btnTrocarFornecedor = document.getElementById('btnTrocarFornecedor');
        const inputNotaFiscal = document.getElementById('notaFiscal');
        const dropdownNotaFiscal = document.getElementById('dropdownNotaFiscal');
        const btnBuscarNotaFiscal = document.getElementById('btnBuscarNotaFiscal');
        const btnTrocarNotaFiscal = document.getElementById('btnTrocarNotaFiscal');
        const inputCodigoProduto = document.getElementById('codigoProduto');
        const dropdownCodigoProduto = document.getElementById('dropdownCodigoProduto');
        const btnBuscarCodigoProduto = document.getElementById('btnBuscarCodigoProduto');
        const btnTrocarCodigoProduto = document.getElementById('btnTrocarCodigoProduto');
        const inputNumeroLote = document.getElementById('numeroLote');
        const inputCvm = document.getElementById('cvm');
        const inputPontosFinos = document.getElementById('pontosFinos');
        const inputPontosGrossos = document.getElementById('pontosGrossos');
        const inputNeps = document.getElementById('neps');
        const inputHPilosidade = document.getElementById('hPilosidade');
        const inputResistencia = document.getElementById('resistencia');
        const inputAlongamento = document.getElementById('alongamento');
        const inputTituloNe = document.getElementById('tituloNe');
        const inputTorcaoTm = document.getElementById('torcaoTm');
        const selectStatus = document.getElementById('status');
        const inputLiberacaoPor = document.getElementById('liberacaoPor');
        const textareaInfoRecebimento = document.getElementById('infoRecebimento');

        async function fetchData(url, method = 'GET', body = null) {
            try {
                const csrf = await FiberGuardian.Utils.obterTokenCsrf();
                if (!csrf) {
                    FiberGuardian.Utils.exibirMensagemModal('Erro: Token CSRF não encontrado.', 'danger');
                    return [];
                }

                const opcoes = {
                    method: method,
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': csrf,
                    },
                };

                if (body) {
                    opcoes.body = JSON.stringify(body);
                }

                const resposta = await fetch(url, opcoes);
                if (!resposta.ok) {
                    if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(resposta);
                    }
                    return null;
                }
                const dados = await resposta.json();
                return Array.isArray(dados) ? dados : (dados.content || dados);
            } catch (erro) {
                FiberGuardian.Utils.exibirErroDeRede('Erro de rede na requisição.', null, erro);
                return null;
            }
        }

        function configurarDropdownGenerico(inputElement, dropdownElement, btnBuscar, btnTrocar, urlApi, paramsKey, itemDisplay) {
            btnBuscar.addEventListener('click', async () => {
                dropdownElement.innerHTML = '';
                dropdownElement.classList.remove('show');
                const valorParcial = inputElement.value.trim();
                if (!valorParcial) {
                    dropdownElement.innerHTML = `<div class="dropdown-item-text">Digite para buscar.</div>`;
                    dropdownElement.classList.add('show');
                    return;
                }
                const lista = await fetchData(`${urlApi}?${paramsKey}=${encodeURIComponent(valorParcial)}`);
                if (!lista || lista.length === 0) {
                    dropdownElement.innerHTML = `<div class="dropdown-item-text">Nenhum resultado encontrado.</div>`;
                    dropdownElement.classList.add('show');
                    return;
                }
                lista.forEach(item => {
                    const dropdownItem = document.createElement('a');
                    dropdownItem.className = 'dropdown-item';
                    dropdownItem.href = '#';
                    dropdownItem.innerHTML = itemDisplay(item);
                    dropdownItem.addEventListener('click', (e) => {
                        e.preventDefault();
                        inputElement.value = item.nome || item.numero || item.codigo;
                        if (inputElement === inputFornecedor) fornecedorSelecionado = item;
                        if (inputElement === inputNotaFiscal) notaFiscalSelecionada = item;
                        if (inputElement === inputCodigoProduto) produtoSelecionado = item;
                        dropdownElement.classList.remove('show');
                        btnTrocar.disabled = false;
                        btnBuscar.disabled = true;
                        inputElement.disabled = true;
                        verificarHabilitacaoBotoes();
                    });
                    dropdownElement.appendChild(dropdownItem);
                });
                dropdownElement.classList.add('show');
            });
            btnTrocar.addEventListener('click', () => {
                inputElement.value = '';
                inputElement.disabled = false;
                btnTrocar.disabled = true;
                btnBuscar.disabled = false;
                dropdownElement.classList.remove('show');
                if (inputElement === inputFornecedor) {
                    fornecedorSelecionado = null;
                    notaFiscalSelecionada = null;
                    produtoSelecionado = null;
                    inputNotaFiscal.value = '';
                    inputNotaFiscal.disabled = true;
                    btnBuscarNotaFiscal.disabled = true;
                    inputCodigoProduto.value = '';
                    inputCodigoProduto.disabled = true;
                    btnBuscarCodigoProduto.disabled = true;
                }
                if (inputElement === inputNotaFiscal) {
                    notaFiscalSelecionada = null;
                    produtoSelecionado = null;
                    inputCodigoProduto.value = '';
                    inputCodigoProduto.disabled = true;
                    btnBuscarCodigoProduto.disabled = true;
                }
                if (inputElement === inputCodigoProduto) {
                    produtoSelecionado = null;
                }
                verificarHabilitacaoBotoes();
            });
            document.addEventListener('click', (event) => {
                if (!dropdownElement.contains(event.target) && event.target !== inputElement && event.target !== btnBuscar && event.target !== btnTrocar) {
                    setTimeout(() => {
                        if (!dropdownElement.contains(document.activeElement)) {
                            dropdownElement.classList.remove('show');
                        }
                    }, 100);
                }
            });
        }

        function verificarHabilitacaoBotoes() {
            if (fornecedorSelecionado) {
                inputNotaFiscal.disabled = false;
                btnBuscarNotaFiscal.disabled = false;
            } else {
                inputNotaFiscal.disabled = true;
                btnBuscarNotaFiscal.disabled = true;
            }
            if (notaFiscalSelecionada) {
                inputCodigoProduto.disabled = false;
                btnBuscarCodigoProduto.disabled = false;
            } else {
                inputCodigoProduto.disabled = true;
                btnBuscarCodigoProduto.disabled = true;
            }
        }

        async function carregarDadosParaEdicao(testeId) {
            FiberGuardian.Utils.exibirMensagemModal('Carregando dados do teste...', 'info');
            const teste = await fetchData(`${URL_API_TESTE}/${testeId}`);
            if (teste) {
                testeIdParaEdicao = teste.id;
                // Popular campos de busca
                fornecedorSelecionado = teste.fornecedor;
                inputFornecedor.value = teste.fornecedor.nome;
                inputFornecedor.disabled = true;
                btnBuscarFornecedor.disabled = true;
                btnTrocarFornecedor.disabled = false;

                notaFiscalSelecionada = teste.notaFiscal;
                inputNotaFiscal.value = teste.notaFiscal.numero;
                inputNotaFiscal.disabled = true;
                btnBuscarNotaFiscal.disabled = true;
                btnTrocarNotaFiscal.disabled = false;

                produtoSelecionado = teste.produto;
                inputCodigoProduto.value = teste.produto.codigo;
                inputCodigoProduto.disabled = true;
                btnBuscarCodigoProduto.disabled = true;
                btnTrocarCodigoProduto.disabled = false;

                // Popular demais campos
                inputNumeroLote.value = teste.numeroLote;
                inputCvm.value = teste.cvm;
                inputPontosFinos.value = teste.pontosFinos;
                inputPontosGrossos.value = teste.pontosGrossos;
                inputNeps.value = teste.neps;
                inputHPilosidade.value = teste.hPilosidade;
                inputResistencia.value = teste.resistencia;
                inputAlongamento.value = teste.alongamento;
                inputTituloNe.value = teste.tituloNe;
                inputTorcaoTm.value = teste.torcaoTm;
                selectStatus.value = teste.status;
                inputLiberacaoPor.value = teste.liberacaoPor;
                textareaInfoRecebimento.value = teste.infoRecebimento;

                FiberGuardian.Utils.esconderMensagemModal();
                document.getElementById('submitButton').textContent = 'Atualizar';
            } else {
                FiberGuardian.Utils.exibirMensagemModal('Erro ao carregar dados do teste.', 'danger');
            }
        }

        function configurarSubmissaoDoFormulario() {
            form.addEventListener('submit', async function (e) {
                e.preventDefault();

                if (!fornecedorSelecionado || !notaFiscalSelecionada || !produtoSelecionado) {
                    FiberGuardian.Utils.exibirMensagemModal('Preencha todos os campos de busca.', 'warning');
                    return;
                }

                const testeData = {
                    id: testeIdParaEdicao, // Será null para criação e terá valor para edição
                    fornecedor: fornecedorSelecionado,
                    notaFiscal: notaFiscalSelecionada,
                    produto: produtoSelecionado,
                    numeroLote: inputNumeroLote.value,
                    cvm: parseFloat(inputCvm.value),
                    pontosFinos: parseInt(inputPontosFinos.value),
                    pontosGrossos: parseInt(inputPontosGrossos.value),
                    neps: parseInt(inputNeps.value),
                    hPilosidade: parseFloat(inputHPilosidade.value),
                    resistencia: parseFloat(inputResistencia.value),
                    alongamento: parseFloat(inputAlongamento.value),
                    tituloNe: parseFloat(inputTituloNe.value),
                    torcaoTm: parseFloat(inputTorcaoTm.value),
                    status: selectStatus.value,
                    liberacaoPor: inputLiberacaoPor.value,
                    infoRecebimento: textareaInfoRecebimento.value,
                };
                
                const method = testeIdParaEdicao ? 'PUT' : 'POST';
                const url = testeIdParaEdicao ? `${URL_API_TESTE}/${testeIdParaEdicao}` : URL_API_TESTE;

                const resultado = await fetchData(url, method, testeData);
                if (resultado) {
                    FiberGuardian.Utils.exibirMensagemModal('Teste de laboratório salvo com sucesso!', 'success');
                    if (!testeIdParaEdicao) {
                        form.reset();
                        window.location.reload();
                    }
                }
            });
        }
        
        function inicializar() {
            const urlParams = new URLSearchParams(window.location.search);
            const testeId = urlParams.get('id');
            if (testeId) {
                carregarDadosParaEdicao(testeId);
            }
            
            configurarDropdownGenerico(inputFornecedor, dropdownFornecedor, btnBuscarFornecedor, btnTrocarFornecedor, URL_API_FORNECEDOR, 'nome', item => `<div><strong>${item.nome}</strong></div><small class="text-muted">${item.cnpj}</small>`);
            configurarDropdownGenerico(inputNotaFiscal, dropdownNotaFiscal, btnBuscarNotaFiscal, btnTrocarNotaFiscal, URL_API_NOTA_FISCAL, 'numero', item => `<div><strong>${item.numero}</strong></div><small class="text-muted">Fornecedor: ${item.fornecedorNome}</small>`);
            configurarDropdownGenerico(inputCodigoProduto, dropdownCodigoProduto, btnBuscarCodigoProduto, btnTrocarCodigoProduto, URL_API_PRODUTO, 'codigo', item => `<div><strong>${item.codigo}</strong></div><small class="text-muted">Descrição: ${item.descricao}</small>`);

            configurarSubmissaoDoFormulario();
            verificarHabilitacaoBotoes();
        }

        return {
            init: inicializar
        };

    })();

    document.addEventListener('DOMContentLoaded', function () {
        if (window.FiberGuardian && FiberGuardian.TelaCadastroLaboratorio && typeof FiberGuardian.TelaCadastroLaboratorio.init === 'function') {
            FiberGuardian.TelaCadastroLaboratorio.init();
        } else {
            console.error('Módulo [TelaCadastroLaboratorio] não encontrado ou sem método init(). O script fiberguardian_core.js pode não ter sido carregado corretamente.');
        }
    });

})();