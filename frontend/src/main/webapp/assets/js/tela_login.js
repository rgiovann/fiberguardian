(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaLogin = (function () {
        'use strict';
        let formLogin;

        async function autenticar(email, senha) {
            try {
                const csrfToken = await FiberGuardian.Utils.obterNovoToken();
                console.log('Token CSRF a ser enviado:', csrfToken);

                const resposta = await fetch('/api/fg-login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': csrfToken,
                    },
                    credentials: 'include',
                    body: JSON.stringify({ email, senha }),
                });

                if (resposta.ok) {
                    formLogin
                        .querySelectorAll('input, button')
                        .forEach((el) => (el.disabled = true));

                    const usuario = await resposta.json();

                    FiberGuardian.UsuarioLogado = {
                        nome: usuario.nome,
                        email: usuario.email,
                        role: usuario.role,
                    };
                    sessionStorage.setItem(
                        'usuario',
                        JSON.stringify(FiberGuardian.UsuarioLogado)
                    );

                    FiberGuardian.Utils.exibirMensagemModal(
                        'Login realizado com sucesso!',
                        'success'
                    );

                    setTimeout(() => {
                        window.location.href = 'index.html';
                    }, 700);
                } else if (resposta.status === 401) {
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Credenciais inválidas.',
                        'danger'
                    );
                    formLogin.reset();
                    return;
                } else {
                    console.error('Erro ao autenticar:', await resposta.text());
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Erro inesperado ao autenticar. Tente novamente mais tarde.',
                        'danger'
                    );
                    formLogin.reset();
                    return;
                }
            } catch (erro) {
                console.error('Erro no processo de login:', erro);
                FiberGuardian.Utils.exibirMensagemModal(
                    'Erro inesperado ao autenticar. Tente novamente mais tarde.',
                    'danger'
                );
                formLogin.reset();
            }
        }

        function configurarEventos() {
            formLogin = document.querySelector('form');
            if (!formLogin) {
                console.error('Formulário de login não encontrado.');
                return;
            }

            formLogin.addEventListener('submit', function (event) {
                event.preventDefault();

                const email = document.getElementById('email').value.trim();
                const senha = document.getElementById('senha').value;

                if (!FiberGuardian.Utils.isEmailValido(email)) {
                    FiberGuardian.Utils.exibirMensagemModal(
                        'E-mail inválido.',
                        'danger'
                    );
                    return;
                }

                if (!senha) {
                    FiberGuardian.Utils.exibirMensagemModal(
                        'Informe a senha.',
                        'danger'
                    );
                    return;
                }

                autenticar(email, senha);
            });
        }

        return {
            init: configurarEventos,
        };
    })();
})();
