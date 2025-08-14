(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.Utils = (function () {
        'use strict';
        console.log('FiberGuardian.Utils carregado com sucesso.');

        // === Funções Privadas ===
        /*
        function normalizarEmail(email) {
            return email.trim().toLowerCase();
        }
*/
        /*
        function desabilitarEntradas(formulario, desabilitar) {
            if (!formulario) return;
            formulario.querySelectorAll('input, button').forEach((el) => {
                el.disabled = desabilitar;
            });
        }
*/
        // === Funções Públicas ===
        /*
        function isEmailValido(email) {
            const emailNormalizado = normalizarEmail(email);
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailNormalizado);
        }
*/

        function getCookie(nome) {
            const cookies = document.cookie.split('; ');
            for (const cookie of cookies) {
                const [chave, valor] = cookie.split('=');
                if (chave === nome) {
                    return decodeURIComponent(valor);
                }
            }
            return null;
        }

        async function obterNovoToken() {
            const resposta = await fetch('/api/csrf-token', {
                method: 'GET',
                credentials: 'include',
            });

            if (resposta.status >= 500) {
                throw new Error(
                    `HTTP ${resposta.status} - O sistema está temporariamente fora do ar.`
                );
            }
            if (!resposta.ok) {
                throw new Error(`Erro ao obter token CSRF : HTTP ${resposta.status}`);
            }

            const dados = await resposta.json();
            if (!dados.token) {
                throw new Error('Token CSRF não retornado pelo servidor.');
            }

            return dados.token;
        }

        async function obterTokenCsrf() {
            const tokenExistente = getCookie('XSRF-TOKEN');
            return tokenExistente || (await obterNovoToken());
        }

        function obterCampo(form, nomeCampo) {
            return form.querySelector(`[name="${nomeCampo}"]`);
        }

        function exibirMensagemSessaoExpirada() {
            exibirMensagemModal(
                'Sua sessão expirou. Por favor, faça login novamente.',
                'danger'
            );
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
        }

        function exibirErroDeRede(
            mensagemPersonalizada,
            campoFoco = null,
            erroOriginal = null
        ) {
            const msgDetalhado =
                erroOriginal instanceof Error
                    ? `${mensagemPersonalizada} : ${erroOriginal.message}`
                    : mensagemPersonalizada;

            exibirMensagemModalComFoco(msgDetalhado, 'danger', campoFoco);
        }

        function exibirMensagemModal(mensagem, tipo = 'info') {
            const modalEl = document.getElementById('modalMensagemSistema');
            if (!modalEl) return;

            const tituloEl = modalEl.querySelector('.modal-title');
            const corpoEl = modalEl.querySelector('.modal-body');

            if (tituloEl) tituloEl.textContent = 'Aviso';
            //if (corpoEl) corpoEl.textContent = mensagem;  //corpoEl.innerHTML = mensagem;
            // insiro mensagem com tag html para quebra de linha <br> por isso
            // preciso usar innerHTML
            if (corpoEl) corpoEl.innerHTML = mensagem;

            const headerEl = modalEl.querySelector('.modal-header');
            if (headerEl) {
                headerEl.className = 'modal-header';
                const tipoCor = {
                    danger: 'bg-danger text-white',
                    warning: 'bg-warning text-dark',
                    success: 'bg-success text-white',
                    info: 'bg-info text-white',
                    primary: 'bg-primary text-white',
                };
                headerEl.className += ' ' + (tipoCor[tipo] || 'bg-warning text-dark');
            }

            const modal = new bootstrap.Modal(modalEl);
            modal.show();
        }

        function escapeHTML(text) {
            const div = document.createElement('div');
            div.textContent = text;
            return div.innerHTML;
        }

        function exibirMensagemModalComFoco(mensagem, tipo, campoAlvo) {
            if (!mensagem || !campoAlvo) return;

            const modalEl = document.getElementById('modalMensagemSistema');
            if (!modalEl) {
                console.warn('Modal de mensagem não encontrado.');
                return;
            }

            function handler() {
                campoAlvo.focus();
                modalEl.removeEventListener('hidden.bs.modal', handler);
            }

            modalEl.addEventListener('hidden.bs.modal', handler);

            FiberGuardian.Utils.exibirMensagemModal(mensagem, tipo);
        }

        // async function tratarErroFetch(resposta, titulo = 'Erro', campoAlvo = null) {
        async function tratarErroFetch(resposta, campoAlvo = null) {
            let mensagem = 'Erro inesperado ao processar a requisição.';
            console.groupCollapsed(`↪️ tratarErroFetch: status ${resposta.status}`);

            try {
                const contentType = resposta.headers.get('Content-Type') || '';
                console.log('📦 Content-Type:', contentType);

                if (contentType.includes('application/json')) {
                    console.log('📥 Tentando parsear JSON da resposta...');
                    const json = await resposta.json();
                    console.log('✅ JSON recebido:', json);

                    if (json?.userMessage) {
                        mensagem = json.userMessage;
                        console.log('🟢 Mensagem principal extraída:', mensagem);

                        if (Array.isArray(json.errorObjects)) {
                            const detalhes = json.errorObjects
                                .map(
                                    (err) =>
                                        `Campo: ${escapeHTML(
                                            err.name
                                        )} - Problema: ${escapeHTML(err.userMessage)}`
                                )
                                .join('<br>');
                            mensagem += '<br>' + detalhes;
                        }
                    } else if (resposta.status === 403) {
                        mensagem =
                            'Acesso negado. Sua sessão expirou ou você não tem permissão.';
                        console.warn('⚠️ Erro 403 sem userMessage.');
                    } else {
                        console.warn('JSON válido mas sem userMessage.');
                    }
                } else {
                    console.warn(
                        '⚠️ Resposta não é JSON. Tentando exibir texto bruto...'
                    );
                    const texto = await resposta.text();
                    console.log('📄 Conteúdo da resposta:', texto);
                    mensagem = `Erro ${resposta.status} - ${resposta.statusText}`;
                }
            } catch (e) {
                console.error('❌ Erro ao interpretar a resposta:', e);

                if (resposta.status === 403) {
                    mensagem =
                        'Acesso negado. Sua sessão expirou ou você não tem permissão.';
                    console.warn('⚠️ Erro 403 capturado no catch.');
                }
            }

            console.log('📢 Mensagem final ao usuário:', mensagem);
            console.groupEnd();
            if (campoAlvo !== null) {
                FiberGuardian.Utils.exibirMensagemModalComFoco(
                    mensagem,
                    'danger',
                    campoAlvo
                );
            } else {
                FiberGuardian.Utils.exibirMensagemModal(mensagem, 'danger');
            }
        }

        function iniciarWatcherDeSessao() {
            iniciarMonitoramentoSessao();
        }

        async function realizarLogout() {
            try {
                const csrfToken = await obterTokenCsrf();
                const resp = await fetch('/api/fg-logout', {
                    method: 'POST',
                    credentials: 'include',
                    headers: {
                        'X-XSRF-TOKEN': csrfToken,
                    },
                });

                if (!resp.ok) throw new Error('Erro ao encerrar sessão');

                sessionStorage.removeItem('usuario');
                window.location.href = 'login.html';
            } catch (e) {
                exibirMensagemModal('Erro no logout: ' + e.message, 'danger');
            }
        }

        function renderizarDropdownGenerico({
            input,
            dropdown,
            lista,
            campoExibir,
            titulo = 'Item',
            msgVazio = 'Nenhum item encontrado.',
        }) {
            dropdown.innerHTML = ''; // Limpa resultados anteriores

            const tabela = document.createElement('table');
            tabela.className = 'table table-sm table-hover mb-0';
            tabela.style.border = '1px solid #b5d4f5';
            tabela.style.borderRadius = '4px';
            tabela.style.tableLayout = 'fixed';
            tabela.style.width = '100%';

            const thead = document.createElement('thead');
            thead.innerHTML = `<tr class="col-drop-down-gen"><th>${titulo}</th></tr>`;
            tabela.appendChild(thead);

            const tbody = document.createElement('tbody');

            if (!Array.isArray(lista) || lista.length === 0) {
                const linha = document.createElement('tr');
                const celula = document.createElement('td');
                celula.colSpan = 1;
                celula.className = 'text-muted text-center';
                celula.textContent = msgVazio;
                linha.appendChild(celula);
                tbody.appendChild(linha);
            } else {
                lista.forEach((item) => {
                    const linha = document.createElement('tr');
                    linha.style.cursor = 'pointer';

                    const celula = document.createElement('td');
                    celula.textContent = item[campoExibir] || '';
                    linha.appendChild(celula);

                    linha.addEventListener('click', () => {
                        input.value = item[campoExibir] || '';
                        dropdown.classList.remove('show');
                        input.focus();
                    });

                    tbody.appendChild(linha);
                });
            }

            tabela.appendChild(tbody);
            dropdown.appendChild(tabela);
            dropdown.classList.add('show');
        }

        // Exporta apenas o necessário
        return {
            obterTokenCsrf,
            obterNovoToken,
            iniciarWatcherDeSessao,
            exibirMensagemModal,
            tratarErroFetch,
            realizarLogout,
            exibirMensagemModalComFoco,
            obterCampo,
            exibirMensagemSessaoExpirada,
            exibirErroDeRede,
            renderizarDropdownGenerico,
        };
    })();
})();
