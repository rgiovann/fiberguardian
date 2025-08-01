(function () {
    'use strict';

    window.FiberGuardian = window.FiberGuardian || {};
    FiberGuardian.TelaAlteracaoCadastroUsuario = (function () {
        const URL_BASE = '/api/usuarios';
        const URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR = `${URL_BASE}/me/nome`;
        const URL_ALTERAR_SENHA = `${URL_BASE}/me/senha`;

        let nomeOriginal = '';
        let setorOriginal = '';
        let turnoOriginal = '';

        async function configurarEventos() {
            await preencherCampos();
            alterarFormularioDadosUsuario();
            alterarFormularioSenha();
        }

        async function preencherCampos() {
            try {

                const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

                const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR, {
                    method: 'GET',
                    headers: { 'X-XSRF-TOKEN': csrfToken },
                    credentials: 'include',
                });
                if (resposta.status === 403) {
                    FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                }
                if (!resposta.ok) {
                    await FiberGuardian.Utils.tratarErroFetch(resposta);
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 1500);
                    return;
                }

                const usuario = await resposta.json();

                nomeOriginal = usuario.nome || '';
                setorOriginal = usuario.setor || '';
                turnoOriginal = usuario.turno || '';

                document.getElementById('nome').value = nomeOriginal;
                document.getElementById('email').value = usuario.email || '';
                document.getElementById('setor').value = setorOriginal;
                document.getElementById('turno').value = turnoOriginal;
            } catch (erro) {
                console.error('Falha na requisição:', erro);
                FiberGuardian.Utils.exibirErroDeRede(
                    'Erro de rede ao alterar cadastro  usuário',
                    campoNome,
                    erro
                );
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 1500);
                return;
            }
        }

        function alterarFormularioDadosUsuario() {
            const formAlterarDados = document.getElementById('formAlterarDados');
            const campoSetor = document.getElementById('setor');
            const campoTurno = document.getElementById('turno');

            const campoNome = FiberGuardian.Utils.obterCampo(formAlterarDados, 'nome');

            requestAnimationFrame(() => {
                campoNome.focus();
            });

            formAlterarDados.addEventListener('submit', async function (e) {
                e.preventDefault();

                const nome = campoNome.value.trim();
                const setor = campoSetor.value;
                const turno = campoTurno.value;

                const nomeSemMudanca = nome === nomeOriginal;
                const setorSemMudanca = setor === setorOriginal;
                const turnoSemMudanca = turno === turnoOriginal;

                if (nomeSemMudanca && setorSemMudanca && turnoSemMudanca) {
                    FiberGuardian.Utils.exibirMensagemModalComFoco(
                        'Nenhum dado foi alterado.',
                        'info',
                        campoNome
                    );
                    return;
                }

                try {
                    const csrf = await FiberGuardian.Utils.obterTokenCsrf();

                    const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR, {
                        method: 'PUT',
                        credentials: 'include',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrf,
                        },
                        body: JSON.stringify({ nome, setor, turno }),
                    });

                    if (resposta.ok) {
                        const dados = await resposta.json();

                        nomeOriginal = dados.nome || '';
                        setorOriginal = dados.setor || '';
                        turnoOriginal = dados.turno || '';

                        campoNome.value = nomeOriginal;
                        campoSetor.value = setorOriginal;
                        campoTurno.value = turnoOriginal;

                        FiberGuardian.Utils.exibirMensagemModalComFoco(
                            'Cadastro usuário realizado com sucesso.',
                            'success',
                            campoNome
                        );

                        // Atualiza o formulário consultando o backend novamente
                        await preencherCampos();
                        return;
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        //alert('Passei no else!');
                        console.error(
                            'Erro ao alterar dados do usuario:',
                            await resposta.text()
                        );
                        await FiberGuardian.Utils.tratarErroFetch(resposta, campoNome);
                        //alert('Entrando no preencher campos!');
                        await preencherCampos();
                        //alert('Saindo do preencher campos!');
                    }
                    return;
                } catch (erro) {
                    console.error('Falha na requisição:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao alterar cadastro  usuário',
                        campoNome,
                        erro
                    );
                    alert('Passei no catch!');
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 1500);
                }
            });
        }

        function alterarFormularioSenha() {
            const formAlterarSenha = document.getElementById('formAlterarSenha');
            const campoNovaSenha = document.getElementById('novaSenha');
            const campoConfirmar = document.getElementById('confirmarSenha');

            const campoSenhaAtual = FiberGuardian.Utils.obterCampo(
                formAlterarSenha,
                'senhaAtual'
            );

            requestAnimationFrame(() => {
                campoSenhaAtual.focus();
            });
            formAlterarSenha.addEventListener('submit', async function (e) {
                e.preventDefault();

                const senhaAtual = campoSenhaAtual.value;
                const novaSenha = campoNovaSenha.value;
                const confirmar = campoConfirmar.value;

                try {
                    const csrf = await FiberGuardian.Utils.obterTokenCsrf();

                    const resposta = await fetch(URL_ALTERAR_SENHA, {
                        method: 'PUT',
                        credentials: 'include',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-XSRF-TOKEN': csrf,
                        },
                        body: JSON.stringify({
                            senhaAtual: senhaAtual,
                            novaSenha: novaSenha,
                            repeteNovaSenha: confirmar,
                        }),
                    });

                    if (resposta.ok) {
                        FiberGuardian.Utils.exibirMensagemModalComFoco(
                            'Senha alterada com sucesso.',
                            'success',
                            campoSenhaAtual
                        );
                    } else if (resposta.status === 403) {
                        FiberGuardian.Utils.exibirMensagemSessaoExpirada();
                    } else {
                        await FiberGuardian.Utils.tratarErroFetch(
                            resposta,
                            campoSenhaAtual
                        );
                    }
                    formAlterarSenha.reset();
                    return;
                } catch (erro) {
                    console.error('Falha na requisição:', erro);
                    FiberGuardian.Utils.exibirErroDeRede(
                        'Erro de rede ao alterar cadastro (senha) usuário',
                        campoNome,
                        erro
                    );
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 1500);
                }
            });
        }

        document.addEventListener('DOMContentLoaded', function () {
            FiberGuardian.TelaAlteracaoCadastroUsuario.init();
        });

        return { init: configurarEventos };
    })();
})();
