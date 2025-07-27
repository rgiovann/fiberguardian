(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaEsqueciSenha = (function () {
        'use strict';

        function configurarEventos() {
            const formAdmin = document.getElementById('formAdmin');
            const formNovaSenha = document.getElementById('formNovaSenha');

            console.log('Entrou na funcao ConfigurarEventos...');

            if (!formAdmin || !formNovaSenha) {
                console.error('Formulários não encontrados.');
                return;
            }

            formAdmin.addEventListener('submit', async function (e) {
                e.preventDefault();

                const email = formAdmin.adminEmail?.value.trim();
                const senha = formAdmin.adminSenha?.value;

                if (!FiberGuardian.Utils.isEmailValido(email)) {
                    FiberGuardian.Utils.exibirMensagemModal(
                        'E-mail inválido.',
                        'danger'
                    );
                    return;
                }

                if (!senha) {
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Informe a senha do supervisor.',
                        'danger'
                    );
                    return;
                }

                try {
                    const csrfToken = await FiberGuardian.Utils.obterNovoToken();

                    const resposta = await fetch('/api/usuarios/validar-admin', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include',
                        body: JSON.stringify({ email, senha }),
                    });

                    if (resposta.ok) {
                        //FiberGuardian.Utils.exibirMensagemModal('Acesso reset senha autorizado.', 'success');
                        formNovaSenha.classList.remove('d-none');
                        formAdmin
                            .querySelectorAll('input, button')
                            .forEach((el) => (el.disabled = true));
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            'Erro ao validar supervisor.'
                        );
                    }
                } catch (erro) {
                    console.error('Falha na requisição:', erro);
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Erro de rede ao validar supervisor.',
                        'danger'
                    );
                }
            });

            formNovaSenha.addEventListener('submit', async function (e) {
                console.log('📝 Evento submit acionado');
                e.preventDefault();

                const nova = formNovaSenha.novaSenha?.value;
                const repetir = formNovaSenha.confirmarSenha?.value;
                const emailUsuario = formNovaSenha.usuarioEmail?.value?.trim();

                console.log('📦 Dados do formulário:', {
                    nova,
                    repetir,
                    emailUsuario,
                });

                if (!nova || !repetir || !emailUsuario) {
                    console.warn('⚠️ Campos obrigatórios não preenchidos');
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Preencha todos os campos.',
                        'danger'
                    );
                    return;
                }

                if (!FiberGuardian.Utils.isEmailValido(emailUsuario)) {
                    console.warn('⚠️ Email inválido:', emailUsuario);
                    FiberGuardian.Utils.exibirMensagemModal(
                        'E-mail do usuário inválido.',
                        'danger'
                    );
                    return;
                }

                if (nova !== repetir) {
                    console.warn('⚠️ Senhas não coincidem');
                    FiberGuardian.Utils.exibirMensagemModal(
                        'As senhas não coincidem.',
                        'danger'
                    );
                    return;
                }

                try {
                    console.log('🔑 Solicitando token CSRF...');
                    const csrfToken = await FiberGuardian.Utils.obterNovoToken();
                    console.log('✅ Token CSRF obtido:', csrfToken);

                    console.log('📤 Enviando requisição fetch...');
                    const resposta = await fetch('/api/usuarios/reset-senha', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include',
                        body: JSON.stringify({
                            email: emailUsuario,
                            senha: nova,
                            repeteSenha: repetir,
                        }),
                    });

                    console.log('📥 Resposta recebida:', resposta);

                    if (resposta.ok) {
                        console.log('🎉 Senha redefinida com sucesso');
                        FiberGuardian.Utils.exibirMensagemModal(
                            'Senha redefinida com sucesso.',
                            'success'
                        );
                        setTimeout(() => {
                            window.location.href = 'login.html';
                        }, 100);
                    } else {
                        console.warn('❌ Requisição falhou, tratando erro');
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            'Erro ao redefinir senha. Tente novamente.'
                        );
                    }
                } catch (erro) {
                    console.error('🔥 Erro inesperado durante o envio:', erro);
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Erro de rede ao tentar redefinir a senha.',
                        'danger'
                    );
                }
            });
        }

        return {
            init: configurarEventos,
        };
    })();
})();
