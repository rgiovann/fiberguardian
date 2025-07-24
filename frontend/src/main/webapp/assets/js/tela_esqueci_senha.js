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
                console.log('Antes do try catch...');

                try {
                    console.log('Obtendo novo token csrf...');

                    const csrfToken = await FiberGuardian.Utils.obterNovoToken();

                    console.log('Email supervisor: ' + email);
                    console.log('Senha supervisor: ' + senha);

                    const resposta = await fetch('/api/usuarios/validar-admin', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrfToken,
                        },
                        credentials: 'include', //  enviar o cookie de sessão
                        body: JSON.stringify({ email, senha }),
                    });

                    if (resposta.ok) {
                        FiberGuardian.Utils.exibirMensagem(
                            'Acesso reset senha autorizado.',
                            'success',
                            'mensagemSupervisor'
                        );

                        formNovaSenha.classList.remove('d-none');
                        formAdmin
                            .querySelectorAll('input, button')
                            .forEach((el) => (el.disabled = true));
                    } else if (resposta.status === 401 || resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagem(
                            'Credenciais do supervisor inválidas.',
                            'danger',
                            'mensagemSupervisor'
                        );
                    } else {
                        FiberGuardian.Utils.exibirMensagem(
                            'Erro inesperado ao validar supervisor.',
                            'danger',
                            'mensagemSupervisor'
                        );
                        console.error(
                            'Erro HTTP:',
                            resposta.status,
                            await resposta.text()
                        );
                    }
                } catch (erro) {
                    console.error('Falha na requisição:', erro);
                    FiberGuardian.Utils.exibirMensagem(
                        'Erro de rede ao validar supervisor.',
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

                // Simulação (substituir por fetch real)
                if (emailUsuario === 'usuario@exemplo.com' && nova === '123456') {
                    FiberGuardian.Utils.exibirMensagem(
                        'Senha redefinida com sucesso (simulado).',
                        'success',
                        'mensagemRedefinicao'
                    );
                }
                window.location.href = 'login.html';
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
