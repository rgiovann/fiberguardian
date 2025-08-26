(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaCadastroParecerEngenharia = (function () {
        const URL_CADASTRAR_PARECER = '/api/cadastro';

        // Função auxiliar para exibir mensagens no modal (fallback)
        function exibirMensagemModalFallback(mensagem, tipo, callback) {
            console.log(`Exibindo mensagem no modal: ${mensagem}, tipo: ${tipo}`);
            const modal = document.getElementById('modalMensagemSistema');
            const modalBody = modal?.querySelector('.modal-body');
            const modalHeader = modal?.querySelector('.modal-header');
            const modalTitle = modal?.querySelector('.modal-title');

            if (!modal || !modalBody || !modalHeader || !modalTitle) {
                console.error('Elementos do modal não encontrados. Usando alert nativo.');
                alert(mensagem);
                if (callback) callback();
                return;
            }

            modalBody.textContent = mensagem;
            modalHeader.className = `modal-header bg-${tipo} text-${tipo === 'success' ? 'dark' : 'light'}`;
            modalTitle.textContent = tipo === 'danger' ? 'Erro' : tipo === 'success' ? 'Sucesso' : 'Aviso';

            try {
                const modalInstance = new bootstrap.Modal(modal);
                modalInstance.show();
                if (callback) {
                    modal.addEventListener('hidden.bs.modal', callback, { once: true });
                }
            } catch (erro) {
                console.error('Erro ao exibir modal Bootstrap:', erro);
                alert(mensagem);
                if (callback) callback();
            }
        }

        function configurarEventos() {
            configurarFormulario();
            configurarBotaoImprimir();
            configurarBotaoSair();
        }

        function configurarFormulario() {
            const form = document.getElementById('parecerForm');
            if (!form) {
                console.error('Formulário com ID "parecerForm" não encontrado.');
                exibirMensagemModalFallback('Erro: Formulário não encontrado.', 'danger');
                return;
            }

            const campos = [
                'nomeEngenheiro', 'restricaoAdicional', 'observacao'
            ].map(id => {
                const campo = document.getElementById(id);
                console.log(`Campo ${id} encontrado:`, campo); // Log para depuração
                return campo;
            });

            // Mostrar/esconder textarea de restrição parcial
            const restricaoParcial = document.getElementById('restricaoParcial');
            const restricaoTotal = document.getElementById('restricaoTotal');
            const restricaoAdicional = document.getElementById('restricaoAdicional');

            if (restricaoParcial && restricaoTotal && restricaoAdicional) {
                restricaoParcial.addEventListener('change', () => {
                    console.log('Restrição Parcial selecionada, exibindo textarea');
                    restricaoAdicional.style.display = 'block';
                });
                restricaoTotal.addEventListener('change', () => {
                    console.log('Restrição Total selecionada, ocultando textarea');
                    restricaoAdicional.style.display = 'none';
                    restricaoAdicional.value = ''; // Limpar o campo ao selecionar restrição total
                });
            } else {
                console.warn('Campos de restrição não encontrados.');
            }

            // Focar no primeiro campo
            requestAnimationFrame(() => {
                console.log('Focando no primeiro campo');
                const primeiroCampo = campos.find(campo => campo);
                if (primeiroCampo) primeiroCampo.focus();
            });

            // Evento de submissão do formulário
            form.addEventListener('submit', async function (e) {
                e.preventDefault();
                console.log('Formulário submetido');

                // Coleta dos dados
                const tipoTeste = Array.from(document.querySelectorAll('input[name="tipoTeste"]:checked')).map(input => input.value);
                const restricao = document.querySelector('input[name="restricao"]:checked')?.value || '';

                const formData = {
                    nomeEngenheiro: document.getElementById('nomeEngenheiro')?.value.trim(),
                    tipoTeste: tipoTeste.length > 0 ? tipoTeste : null,
                    restricao: restricao,
                    restricaoAdicional: document.getElementById('restricaoAdicional')?.value.trim() || '',
                    observacao: document.getElementById('observacao')?.value.trim() || ''
                };

                // Validação do formulário
                let isValid = true;
                const camposObrigatorios = [document.getElementById('nomeEngenheiro')];

                camposObrigatorios.forEach(campo => {
                    if (!campo.value.trim()) {
                        campo.classList.add('is-invalid');
                        (FiberGuardian.Utils?.exibirMensagemModalComFoco || exibirMensagemModalFallback)(
                            `O campo ${campo.name || campo.id} é obrigatório.`,
                            'danger',
                            campo
                        );
                        isValid = false;
                    } else {
                        campo.classList.remove('is-invalid');
                    }
                });

                if (!tipoTeste.length) {
                    const campoTipoTeste = document.querySelector('input[name="tipoTeste"]');
                    (FiberGuardian.Utils?.exibirMensagemModalComFoco || exibirMensagemModalFallback)(
                        'Selecione pelo menos um Tipo de Teste.',
                        'danger',
                        campoTipoTeste
                    );
                    isValid = false;
                }

                if (!restricao) {
                    const campoRestricao = document.querySelector('input[name="restricao"]');
                    (FiberGuardian.Utils?.exibirMensagemModalComFoco || exibirMensagemModalFallback)(
                        'O campo Restrição é obrigatório.',
                        'danger',
                        campoRestricao
                    );
                    isValid = false;
                }

                if (!isValid) {
                    console.log('Validação do formulário falhou');
                    return;
                }

                try {
                    console.log('Enviando requisição para cadastrar parecer');
                    const csrf = await (FiberGuardian.Utils?.obterTokenCsrf?.() || Promise.resolve(''));
                    const resposta = await fetch(URL_CADASTRAR_PARECER, {
                        method: 'POST',
                        credentials: 'include',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrf,
                        },
                        body: JSON.stringify(formData),
                    });

                    if (resposta.ok) {
                        console.log('Cadastro realizado com sucesso');
                        (FiberGuardian.Utils?.exibirMensagemModalComFoco || exibirMensagemModalFallback)(
                            'Cadastro realizado com sucesso!',
                            'success',
                            campos[0]
                        );
                        form.reset();
                        // Resetar visibilidade do textarea
                        restricaoAdicional.style.display = 'none';
                        restricaoAdicional.value = '';
                    } else if (resposta.status === 403) {
                        console.log('Sessão expirada, redirecionando para login');
                        (FiberGuardian.Utils?.exibirMensagemSessaoExpirada || exibirMensagemModalFallback)(
                            'Sessão expirada. Redirecionando para login...',
                            'danger'
                        );
                        setTimeout(() => {
                            window.location.href = 'login.html';
                        }, 1500);
                    } else {
                        console.error('Erro na resposta do servidor:', resposta.status);
                        await (FiberGuardian.Utils?.tratarErroFetch || exibirMensagemModalFallback)(
                            `Erro ao cadastrar parecer: ${resposta.statusText}`,
                            campos[0]
                        );
                    }
                } catch (erro) {
                    console.error('Falha na requisição:', erro);
                    (FiberGuardian.Utils?.exibirErroDeRede || exibirMensagemModalFallback)(
                        'Erro de rede ao cadastrar parecer.',
                        campos[0],
                        erro
                    );
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 1500);
                }
            });
        }

        function configurarBotaoImprimir() {
            const btnImprimir = document.querySelector('button[data-action="imprimir"]');
            if (btnImprimir) {
                btnImprimir.addEventListener('click', () => {
                    console.log('Botão Imprimir clicado');
                    window.print();
                });
            } else {
                console.warn('Botão de imprimir não encontrado.');
            }
        }

        function configurarBotaoSair() {
            const btnSair = document.querySelector('button[data-action="sair"]');
            if (btnSair) {
                btnSair.addEventListener('click', sair);
            } else {
                console.error('Botão com data-action="sair" não encontrado.');
                exibirMensagemModalFallback('Erro: Botão Sair não encontrado.', 'danger');
            }

            // Função Sair simplificada
            function sair() {
                console.log('Botão Sair clicado');
                if (confirm('Deseja sair? Alterações não salvas serão perdidas.')) {
                    console.log('Redirecionando para index.html');
                    try {
                        window.location.href = 'index.html';
                        // Verifica se o redirecionamento foi bem-sucedido
                        setTimeout(() => {
                            if (window.location.pathname !== '/index.html') {
                                console.error('Falha ao redirecionar para index.html');
                                alert('Erro: Não foi possível redirecionar para a página inicial. Verifique se o arquivo index.html existe.');
                            } else {
                                console.log('Redirecionamento para index.html bem-sucedido');
                            }
                        }, 1000);
                    } catch (erro) {
                        console.error('Erro ao tentar redirecionar:', erro);
                        alert('Erro: Falha ao redirecionar para a página inicial.');
                    }
                } else {
                    console.log('Saída cancelada pelo usuário');
                }
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            console.log('DOM carregado, inicializando TelaCadastroParecerEngenharia');
            FiberGuardian.TelaCadastroParecerEngenharia.init();
        });

        return { init: configurarEventos };
    })();
})();