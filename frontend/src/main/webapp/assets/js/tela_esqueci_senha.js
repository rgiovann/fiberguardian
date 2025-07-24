(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaEsqueciSenha = (function () {
        'use strict';

        function configurarEventos() {
            const formAdmin = document.getElementById('formAdmin');
            const formNovaSenha = document.getElementById('formNovaSenha');

            if (!formAdmin || !formNovaSenha) {
                console.error('Formulários não encontrados.');
                return;
            }

            formAdmin.addEventListener('submit', async function (e) {
                e.preventDefault();

                const email = formAdmin.adminEmail?.value.trim();
                const senha = formAdmin.adminSenha?.value;

                if (!FiberGuardian.Utils.isEmailValido(email)) {
                    FiberGuardian.Utils.exibirMensagem(
                        'E-mail inválido.',
                        'danger',
                        'mensagemSupervisor'
                    );
                    return;
                }

                if (!senha) {
                    FiberGuardian.Utils.exibirMensagem(
                        'Informe a senha do supervisor.',
                        'danger',
                        'mensagemSupervisor'
                    );
                    return;
                }

                // Simulação de validação (substituir com chamada real)
                if (email === 'admin@exemplo.com' && senha === '123456') {
                    FiberGuardian.Utils.exibirMensagem(
                        'Senha redefinida com sucesso.',
                        'success',
                        'mensagemSupervisor'
                    );

                    formNovaSenha.classList.remove('d-none');
                    formAdmin
                        .querySelectorAll('input, button')
                        .forEach((el) => (el.disabled = true));
                } else {
                    FiberGuardian.Utils.exibirMensagem(
                        'Credenciais do supervisor inválidas.',
                        'danger',
                        'mensagemSupervisor'
                    );
                }
            });

            formNovaSenha.addEventListener('submit', function (e) {
                e.preventDefault();

                const nova = formNovaSenha.novaSenha?.value;
                const repetir = formNovaSenha.confirmarSenha?.value;
                const emailUsuario = formNovaSenha.usuarioEmail?.value;
                console.log('Lendo valores de email e senha...');
                console.log('Nova senha :' + nova);
                console.log('Nova senha :' + repetir);
                console.log('Email usuario :' + emailUsuario);

                if (!nova || !repetir) {
                    FiberGuardian.Utils.exibirMensagem(
                        'Preencha os dois campos de senha.',
                        'danger',
                        'mensagemRedefinicao'
                    );
                    return;
                }

                if (nova !== repetir) {
                    FiberGuardian.Utils.exibirMensagem(
                        'As senhas não coincidem.',
                        'danger',
                        'mensagemRedefinicao'
                    );
                    return;
                }
                console.log('Checando valores simulados...');

                // Simulação (substituir por fetch real)
                if (emailUsuario === 'usuario@exemplo.com' && nova === '123456') {
                    console.log('Deveria exibir mensagem...');
                    FiberGuardian.Utils.exibirMensagem(
                        'Senha redefinida com sucesso (simulado).',
                        'success',
                        'mensagemRedefinicao'
                    );
                }

                // Aqui seria a chamada real usando:
                // const csrfToken = await FiberGuardian.Utils.obterNovoToken();
                // fetch('/api/redefinir-senha', { ... })
            });
        }

        return {
            init: configurarEventos,
        };
    })();
})();
