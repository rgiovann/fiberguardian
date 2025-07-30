(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaCadastroUsuario = (function () {
        const formCadastraUsuario = document.getElementById('cadastroForm');
        const emailField = document.getElementById('email');
        const nomeField = document.getElementById('nome');
        const perfilField = document.getElementById('perfil');
        const senhaField = document.getElementById('senha');
        const confirmarSenhaField = document.getElementById('confirmarSenha');

        const emailInvalidError = document.getElementById('emailInvalidError');
        const emailUsedError = document.getElementById('emailUsedError');
        const senhaError = document.getElementById('senhaError');
        const setorField = document.getElementById('setor');
        const turnoField = document.getElementById('turno');

        function limparErros() {
            emailField.classList.remove('input-error');
            emailInvalidError.style.display = 'none';
            emailUsedError.style.display = 'none';

            confirmarSenhaField.classList.remove('input-error');
            senhaError.style.display = 'none';
        }

        async function cadastrarUsuario(nome, email, senha, perfil, setor, turno) {
            const campoNome = FiberGuardian.Utils.obterCampo(
                formCadastraUsuario,
                'nome'
            );

            try {
                const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                const resposta = await fetch('/api/usuarios', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': csrfToken,
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        nome: nome.trim(),
                        email: email.trim().toLowerCase(),
                        senha: senha,
                        role: perfil,
                        setor: setor,
                        turno: turno,
                    }),
                });

                console.log('Verificando a resposta..');

                if (resposta.ok) {
                    console.log('✅ resposta.ok == true');
                    console.log('📌 campoNome:', campoNome);
                    console.log(
                        '📌 exibirMensagemModalComFoco existe?',
                        typeof FiberGuardian.Utils.exibirMensagemModalComFoco
                    );
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Senha redefinida com sucesso.',
                        'success',
                        campoNome
                    );
                    formCadastraUsuario.reset();
                    //limparErros();
                } else if (resposta.status === 403) {
                    FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                } else {
                    await FiberGuardian.Utils.tratarErroFetch(
                        resposta,
                        'Erro ao cadastrar usuário.',
                        campoNome
                    );
                }
                return;
            } catch (erro) {
                console.error('Falha na requisição:', erro);
                FiberGuardian.Utils.exibirErroDeRede('Erro de rede', campoNome, erro);
            }
        }

        function configurarEventos() {
            formCadastraUsuario.addEventListener('submit', async function (e) {
                e.preventDefault();
                limparErros();

                const nome = nomeField.value.trim();
                const email = emailField.value.trim();
                const senha = senhaField.value;
                const confirmarSenha = confirmarSenhaField.value;
                const perfil = perfilField.value;
                const setor = setorField.value;
                const turno = turnoField.value;

                let valido = true;

                if (senha !== confirmarSenha) {
                    senhaError.style.display = 'block';
                    confirmarSenhaField.classList.add('input-error');
                    valido = false;
                }

                if (!perfil) {
                    alert('Por favor, selecione um perfil.');
                    valido = false;
                }

                if (!setor) {
                    alert('Por favor, selecione um setor.');
                    valido = false;
                }

                if (!turno) {
                    alert('Por favor, selecione um turno.');
                    valido = false;
                }

                if (!valido) return;

                await cadastrarUsuario(nome, email, senha, perfil, setor, turno);
            });
        }

        document.addEventListener('DOMContentLoaded', function () {
            FiberGuardian.TelaCadastroUsuario.init();
        });

        return {
            init: configurarEventos,
        };
    })();
})();
