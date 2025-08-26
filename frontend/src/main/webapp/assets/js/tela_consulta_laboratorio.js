(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaConsultaLaboratorio = (function () {
        const URL_LISTAR_TESTES_LABORATORIO = '/api/testes-laboratorio/list';

        const inputNotaFiscal = document.getElementById('notaFiscal');
        const btnConsultarTestes = document.getElementById('btnConsultarTestes');
        const btnTrocarConsulta = document.getElementById('btnTrocarConsulta');
        const resultadosContainer = document.getElementById('resultadosContainer');
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

        async function consultarTestes() {
            const numeroNotaFiscal = inputNotaFiscal.value.trim();
            if (!numeroNotaFiscal) {
                FiberGuardian.Utils.exibirMensagemModal('Digite o número da Nota Fiscal para consultar.', 'warning');
                return;
            }

            // Desabilita o botão de consulta e habilita o de troca imediatamente
            btnConsultarTestes.disabled = true;
            btnTrocarConsulta.disabled = false;
            
            resultadosTableBody.innerHTML = '<tr><td colspan="5">Buscando testes...</td></tr>';
            resultadosContainer.style.display = 'block';

            const url = `${URL_LISTAR_TESTES_LABORATORIO}?notaFiscal=${encodeURIComponent(numeroNotaFiscal)}`;
            const testes = await fetchData(url);

            resultadosTableBody.innerHTML = '';

            if (!testes || testes.length === 0) {
                resultadosTableBody.innerHTML = '<tr><td colspan="5">Nenhum teste de laboratório encontrado para a nota fiscal informada.</td></tr>';
            } else {
                testes.forEach(teste => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${new Date(teste.dataTeste).toLocaleDateString()}</td>
                        <td>${teste.fornecedor.nome}</td>
                        <td>${teste.produto.codigo}</td>
                        <td>${teste.status}</td>
                        <td>
                            <button class="btn btn-primary btn-sm btn-editar me-2" data-id="${teste.id}">
                                <i class="fas fa-edit"></i> Editar
                            </button>
                            <button class="btn btn-danger btn-sm btn-excluir" data-id="${teste.id}">
                                <i class="fas fa-trash-alt"></i> Excluir
                            </button>
                        </td>
                    `;
                    resultadosTableBody.appendChild(row);
                });

                document.querySelectorAll('.btn-editar').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        const testeId = e.currentTarget.getAttribute('data-id');
                        window.location.href = `tela_cadastro_laboratorio.html?id=${testeId}`;
                    });
                });

                document.querySelectorAll('.btn-excluir').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        const testeId = e.currentTarget.getAttribute('data-id');
                        if (confirm('Tem certeza que deseja excluir este teste?')) {
                            console.log(`Excluir teste com ID: ${testeId}`);
                        }
                    });
                });
            }
        }

        function trocarConsulta() {
            inputNotaFiscal.value = '';
            resultadosContainer.style.display = 'none';
            btnConsultarTestes.disabled = false;
            btnTrocarConsulta.disabled = true;
        }
        
        function configurarEventos() {
            btnConsultarTestes.addEventListener('click', consultarTestes);
            btnTrocarConsulta.addEventListener('click', trocarConsulta);
        }

        return {
            init: configurarEventos
        };
    })();

    document.addEventListener('DOMContentLoaded', function () {
        if (window.FiberGuardian && FiberGuardian.TelaConsultaLaboratorio && typeof FiberGuardian.TelaConsultaLaboratorio.init === 'function') {
            FiberGuardian.TelaConsultaLaboratorio.init();
        } else {
            console.error('Módulo [TelaConsultaLaboratorio] não encontrado ou sem método init(). O script fiberguardian_core.js pode não ter sido carregado corretamente.');
        }
    });

})();