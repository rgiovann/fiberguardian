(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    // O nome do módulo deve ser 'TelaTesteReprovados' para corresponder ao arquivo 'tela_teste_reprovados.html'
    FiberGuardian.TelaTesteReprovados = (function () {
        const URL_LISTAR_TESTES_LABORATORIO = '/api/testes-laboratorio/list';

        const resultadosTableBody = document.getElementById('resultadosTableBody');

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
                let lista = Array.isArray(dados) ? dados : (dados.content || []);
                return lista;
            } catch (erro) {
                FiberGuardian.Utils.exibirErroDeRede('Erro de rede na requisição.', null, erro);
                return null;
            }
        }

        async function carregarTestesReprovados() {
            resultadosTableBody.innerHTML = '<tr><td colspan="5">Buscando testes reprovados...</td></tr>';
            
            const url = `${URL_LISTAR_TESTES_LABORATORIO}?status=Reprovado`;
            const testes = await fetchData(url);

            resultadosTableBody.innerHTML = '';

            if (!testes || testes.length === 0) {
                resultadosTableBody.innerHTML = '<tr><td colspan="5">Nenhum teste reprovado encontrado.</td></tr>';
                return;
            }

            testes.forEach(teste => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${new Date(teste.dataTeste).toLocaleDateString()}</td>
                    <td>${teste.fornecedor.nome}</td>
                    <td>${teste.notaFiscal.numero}</td>
                    <td>${teste.produto.codigo}</td>
                    <td>
                        <button class="btn btn-primary btn-sm btn-parecer" data-id="${teste.id}">
                            <i class="fas fa-file-alt"></i> Parecer Engenharia
                        </button>
                    </td>
                `;
                resultadosTableBody.appendChild(row);
            });

            document.querySelectorAll('.btn-parecer').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const testeId = e.currentTarget.getAttribute('data-id');
                    window.location.href = `tela_cadastro_parecer_engenharia.html?id=${testeId}`;
                });
            });
        }
        
        function inicializar() {
            carregarTestesReprovados();
        }

        return {
            init: inicializar
        };
    })();

    document.addEventListener('DOMContentLoaded', function () {
        if (window.FiberGuardian && FiberGuardian.TelaTesteReprovados && typeof FiberGuardian.TelaTesteReprovados.init === 'function') {
            FiberGuardian.TelaTesteReprovados.init();
        } else {
            console.error('Módulo [TelaTesteReprovados] não encontrado ou sem método init(). O script fiberguardian_core.js pode não ter sido carregado corretamente.');
        }
    });

})();