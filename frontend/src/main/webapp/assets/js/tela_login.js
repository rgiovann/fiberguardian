(function () {
    //const API_BASE_URL = "https://localhost:8443/fiberguardian";
    //const API_BASE_URL = "/fiberguardian"; // caddy

    window.FiberGuardian = window.FiberGuardian || {};
    FiberGuardian.TelaLogin = (function () {
        'use strict';
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
                    const usuario = await resposta.json();
                    FiberGuardian.Utils.exibirMensagem(
                        'Login realizado com sucesso!',
                        'success'
                    );
                    // Armazenar dados do usuário autenticado globalmente
                    FiberGuardian.UsuarioLogado = {
                        nome: usuario.nome,
                        email: usuario.email,
                        role: usuario.role,
                    };
                    // Persiste no sessionStorage para uso futuro (nova aba, F5 etc.)
                    sessionStorage.setItem(
                        'usuario',
                        JSON.stringify(FiberGuardian.UsuarioLogado)
                    );
                    window.location.href = 'index.html';
                } else if (resposta.status === 401) {
                    FiberGuardian.Utils.exibirMensagem(
                        'Credenciais inválidas.',
                        'danger'
                    );
                    document.getElementById('senha').value = '';
                    document.getElementById('senha').focus();
                } else {
                    console.error('Erro ao autenticar:', await resposta.text());
                    FiberGuardian.Utils.exibirMensagem(
                        'Erro inesperado ao autenticar.',
                        'danger'
                    );
                }
            } catch (erro) {
                console.error('Erro no processo de login:', erro);
                FiberGuardian.Utils.exibirMensagem(
                    'Falha ao autenticar. Tente novamente mais tarde.',
                    'danger'
                );
            }
        }

        function configurarEventos() {
            const form = document.querySelector('form');
            if (!form) {
                console.error('Formulário de login não encontrado.');
                return;
            }

            form.addEventListener('submit', function (event) {
                event.preventDefault();

                const email = document.getElementById('email').value.trim();
                const senha = document.getElementById('senha').value;

                if (!FiberGuardian.Utils.isEmailValido(email)) {
                    alert('E-mail inválido.');
                    return;
                }

                if (!senha) {
                    alert('Informe a senha.');
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
