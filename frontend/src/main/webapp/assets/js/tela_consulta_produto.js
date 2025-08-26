(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaConsultaProduto = (function () {
        const URL_PRODUTOS = '/api/produtos'; 
        const URL_LISTAR_PRODUTOS = '/api/produtos/paged'; 
        const URL_LISTAR_FORNECEDORES = '/api/fornecedores/list/recebimento';
        
        let currentPage = 0;
        const pageSize = 10;
        let totalPages = 0;
        
        let fornecedorSelecionado = null;

        function configurarEventos() {
            configurarDropdownFornecedor(); 
            configurarBotoesAlteracao(); 
            configurarBotaoSair();
            configurarPaginacao();
        }

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

                if (method === 'DELETE' || resposta.status === 204) {
                    return true;
                }

                const dados = await resposta.json();
                
                if (dados.content) {
                    totalPages = dados.totalPages;
                    return dados;
                }
                
                let lista = Array.isArray(dados) ? dados : (dados.content || []);
                if (!Array.isArray(lista)) {
                    FiberGuardian.Utils.exibirMensagemModal('Erro: Formato inválido dos dados recebidos.', 'danger');
                    return [];
                }
                return lista;
            } catch (erro) {
                FiberGuardian.Utils.exibirErroDeRede('Erro de rede na requisição.', null, erro);
                return null;
            }
        }

        async function buscarFornecedores(nomeParcial) {
            if (!nomeParcial) {
                FiberGuardian.Utils.exibirMensagemModal('Digite parte do nome do fornecedor para buscar.', 'warning');
                return [];
            }
            return await fetchData(`${URL_LISTAR_FORNECEDORES}?nome=${encodeURIComponent(nomeParcial)}`);
        }

        function configurarDropdownGenerico(btnBuscarId, btnTrocarId, inputId, dropdownId, buscarFuncao, onSelectCallback) {
            const btnBuscar = document.getElementById(btnBuscarId);
            const btnTrocar = document.getElementById(btnTrocarId);
            const input = document.getElementById(inputId);
            const dropdown = document.getElementById(dropdownId);

            if (!btnBuscar || !btnTrocar || !input || !dropdown) {
                console.error(`Elementos do dropdown ${inputId} não encontrados.`);
                return;
            }
            
            btnBuscar.addEventListener('click', async () => {
                dropdown.innerHTML = '';
                dropdown.classList.remove('show');

                const nomeParcial = input.value.trim();
                const lista = await buscarFuncao(nomeParcial);

                if (!lista || lista.length === 0) {
                    dropdown.innerHTML = `<div class="dropdown-item-text">Nenhum resultado encontrado para "${nomeParcial}".</div>`;
                    dropdown.classList.add('show');
                    return;
                }

                lista.forEach(item => {
                    const dropdownItem = document.createElement('a');
                    dropdownItem.className = 'dropdown-item';
                    dropdownItem.href = '#';
                    dropdownItem.innerHTML = `
                        <div><strong>${item.nome || item.codigo || 'Sem nome'}</strong></div>
                        <small class="text-muted">${item.codigo || ''} ${item.descricao || ''}</small>
                    `;

                    dropdownItem.addEventListener('click', (e) => {
                        e.preventDefault();
                        onSelectCallback(item, input, dropdown);
                        
                        btnTrocar.disabled = false;
                        btnBuscar.disabled = true;
                        input.disabled = true;
                    });

                    dropdown.appendChild(dropdownItem);
                });

                dropdown.classList.add('show');
            });
            
            btnTrocar.addEventListener('click', () => {
                input.value = '';
                input.disabled = false;
                btnTrocar.disabled = true;
                btnBuscar.disabled = false;
                dropdown.classList.remove('show');
                preencherTabelaProdutos([]);
                ocultarSecaoAlteracao();
            });

            document.addEventListener('click', (event) => {
                if (!dropdown.contains(event.target) && event.target !== input && event.target !== btnBuscar && event.target !== btnTrocar) {
                    setTimeout(() => {
                        if (!dropdown.contains(document.activeElement)) {
                            dropdown.classList.remove('show');
                        }
                    }, 100);
                }
            });
        }
        
        function configurarDropdownFornecedor() {
            configurarDropdownGenerico(
                'btnBuscarFornecedorBusca',
                'btnTrocarFornecedorBusca',
                'fornecedorBusca',
                'dropdownFornecedorBusca',
                buscarFornecedores,
                (item, input, dropdown) => {
                    input.value = item.nome;
                    fornecedorSelecionado = item;
                    dropdown.classList.remove('show');
                    
                    currentPage = 0; 
                    buscarProdutos(true);
                }
            );
        }

        function configurarBotaoSair() {
            const btnSair = document.getElementById('btnSair');
            if (btnSair) {
                btnSair.addEventListener('click', sair);
            }
        }
        
        function configurarPaginacao() {
            document.getElementById('prevPage').addEventListener('click', async (e) => {
                e.preventDefault();
                if (currentPage > 0) {
                    currentPage--;
                    await buscarProdutos(false, currentPage);
                }
            });

            document.getElementById('nextPage').addEventListener('click', async (e) => {
                e.preventDefault();
                if (currentPage < totalPages - 1) {
                    currentPage++;
                    await buscarProdutos(false, currentPage);
                }
            });
        }
        
        function configurarBotoesAlteracao() {
            document.getElementById('btnSalvarAlteracao').addEventListener('click', alterarProduto);
            document.getElementById('btnCancelarAlteracao').addEventListener('click', ocultarSecaoAlteracao);
        }

        async function buscarProdutos(mostrarMensagem = true, pageNumber = 0) {
            const fornecedorCnpj = fornecedorSelecionado ? fornecedorSelecionado.cnpj : '';

            if (!fornecedorCnpj) {
                preencherTabelaProdutos([]);
                return;
            }

            const params = new URLSearchParams({
                page: pageNumber,
                size: pageSize,
                sort: 'descricao,asc',
                cnpj: fornecedorCnpj,
            });

            const url = `${URL_LISTAR_PRODUTOS}?${params.toString()}`;
            const dados = await fetchData(url);

            if (dados === null) {
                return;
            }

            preencherTabelaProdutos(dados.content);
            atualizarPaginacao(dados.totalPages, dados.number);
            
            if (mostrarMensagem && dados.content.length > 0) {
                FiberGuardian.Utils.exibirMensagemModal(`Busca concluída. ${dados.totalElements} produto(s) encontrado(s) para o fornecedor selecionado.`, 'success');
            } else if (mostrarMensagem && dados.content.length === 0) {
                 FiberGuardian.Utils.exibirMensagemModal(`Nenhum produto encontrado para o fornecedor selecionado.`, 'warning');
            }
            
            ocultarSecaoAlteracao();
        }

        function preencherTabelaProdutos(produtos) {
            const produtosTableBody = document.getElementById('produtosTableBody');
            produtosTableBody.innerHTML = '';

            if (!produtos || produtos.length === 0) {
                const tr = document.createElement('tr');
                tr.id = 'noDataRow';
                tr.innerHTML = '<td colspan="4" class="text-center">Nenhum produto encontrado.</td>';
                produtosTableBody.appendChild(tr);
                return;
            }

            produtos.forEach(produto => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${produto.codigo}</td>
                    <td>${produto.descricao}</td>
                    <td>${produto.fornecedor.nome}</td> <td>
                        <button class="btn btn-sm btn-primary btn-custom alterar-produto me-2" data-codigo="${produto.codigo}" data-descricao="${produto.descricao}" data-fornecedor-cnpj="${fornecedorSelecionado.cnpj}">
                            <i class="fas fa-edit"></i> Alterar
                        </button>
                        <button class="btn btn-sm btn-danger btn-custom deletar-produto" data-codigo="${produto.codigo}" data-fornecedor-cnpj="${fornecedorSelecionado.cnpj}">
                            <i class="fas fa-trash-alt"></i> Deletar
                        </button>
                    </td>
                `;
                produtosTableBody.appendChild(tr);
            });
            
            document.querySelectorAll('.deletar-produto').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const codigo = e.currentTarget.getAttribute('data-codigo');
                    const fornecedorCnpj = e.currentTarget.getAttribute('data-fornecedor-cnpj');
                    const nome = e.currentTarget.closest('tr').querySelector('td:nth-child(1)').textContent;
                    confirmarExclusao(codigo, nome, fornecedorCnpj);
                });
            });

            document.querySelectorAll('.alterar-produto').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const codigo = e.currentTarget.getAttribute('data-codigo');
                    const descricao = e.currentTarget.getAttribute('data-descricao');
                    const fornecedorCnpj = e.currentTarget.getAttribute('data-fornecedor-cnpj');
                    
                    exibirSecaoAlteracao(codigo, descricao, fornecedorCnpj);
                });
            });
        }
        
        function atualizarPaginacao(totalPaginas, paginaAtual) {
            totalPages = totalPaginas;
            currentPage = paginaAtual;
            
            const prevBtn = document.getElementById('prevPage');
            const nextBtn = document.getElementById('nextPage');
            const currentPageBtn = document.getElementById('currentPage');

            prevBtn.classList.toggle('disabled', currentPage === 0);
            nextBtn.classList.toggle('disabled', currentPage === totalPages - 1);
            currentPageBtn.querySelector('a').textContent = currentPage + 1;
        }

        function confirmarExclusao(codigo, nome, fornecedorCnpj) {
            if (confirm(`Deseja realmente excluir o produto de código "${nome}"?`)) {
                excluirProduto(codigo, fornecedorCnpj);
            }
        }

        async function excluirProduto(codigo, fornecedorCnpj) {
            const url = `${URL_PRODUTOS}/${encodeURIComponent(fornecedorCnpj)}/${encodeURIComponent(codigo)}`;
            const resposta = await fetchData(url, 'DELETE');
            
            if (resposta) {
                FiberGuardian.Utils.exibirMensagemModal('Produto excluído com sucesso!', 'success');
                buscarProdutos(false, currentPage); 
            }
        }
        
        function exibirSecaoAlteracao(codigo, descricao, fornecedorCnpj) {
            document.getElementById('codigoProdutoAlterar').value = codigo;
            document.getElementById('descricaoProdutoAlterar').value = descricao;
            document.getElementById('btnSalvarAlteracao').dataset.codigo = codigo;
            document.getElementById('btnSalvarAlteracao').dataset.fornecedorCnpj = fornecedorCnpj;
            
            document.getElementById('secaoAlteracaoProduto').classList.remove('d-none');
            document.getElementById('descricaoProdutoAlterar').focus();
        }
        
        function ocultarSecaoAlteracao() {
            document.getElementById('secaoAlteracaoProduto').classList.add('d-none');
            document.getElementById('formAlteracaoProduto').reset();
            delete document.getElementById('btnSalvarAlteracao').dataset.codigo;
            delete document.getElementById('btnSalvarAlteracao').dataset.fornecedorCnpj;
        }

        async function alterarProduto() {
            const codigo = document.getElementById('btnSalvarAlteracao').dataset.codigo;
            const fornecedorCnpj = document.getElementById('btnSalvarAlteracao').dataset.fornecedorCnpj;
            const descricao = document.getElementById('descricaoProdutoAlterar').value.trim();

            if (!descricao) {
                 FiberGuardian.Utils.exibirMensagemModal('A descrição não pode ser vazia.', 'warning');
                 return;
            }

            const url = `${URL_PRODUTOS}/${encodeURIComponent(fornecedorCnpj)}/${encodeURIComponent(codigo)}`;
            const dadosAtualizados = {
                codigo: codigo,
                descricao: descricao,
            };
            
            const resposta = await fetchData(url, 'PUT', dadosAtualizados);
            
            if (resposta) {
                ocultarSecaoAlteracao();
                FiberGuardian.Utils.exibirMensagemModal('Produto alterado com sucesso!', 'success');
                buscarProdutos(false, currentPage);
            }
        }
        
        function sair() {
            window.location.href = 'index.html';
        }

        document.addEventListener('DOMContentLoaded', function () {
            FiberGuardian.TelaConsultaProduto.init();
        });

        return { init: configurarEventos };
    })();
})();
