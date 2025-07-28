(function () {
  "use strict";

  window.FiberGuardian = window.FiberGuardian || {};
  FiberGuardian.TelaAlteracaoCadastroUsuario = (function () {
    const URL_BASE = "/api/usuarios";
    const URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR = `${URL_BASE}/me/nome`;
    const URL_ALTERAR_SENHA = `${URL_BASE}/me/senha`;

    let nomeOriginal = "";
    let setorOriginal = "";
    let turnoOriginal = "";

    async function configurarEventos() {
      await preencherCampos();
      alterarFormularioNome();
      alterarFormularioSenha();
    }

    async function preencherCampos() {
      try {
        const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

        const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR, {
          method: "GET",
          headers: { "X-XSRF-TOKEN": csrfToken },
          credentials: "include",
        });

        if (!resposta.ok) {
          await FiberGuardian.Utils.tratarErroFetch(
            resposta,
            "Erro ao alterar cadastro usuário."
          );
        }

        const usuario = await resposta.json();

        nomeOriginal = usuario.nome || "";
        setorOriginal = usuario.setor || "";
        turnoOriginal = usuario.turno || "";

        document.getElementById("nome").value = nomeOriginal;
        document.getElementById("email").value = usuario.email || "";
        document.getElementById("setor").value = setorOriginal;
        document.getElementById("turno").value = turnoOriginal;
      } catch (erro) {
        console.error(erro);
        FiberGuardian.Utils.exibirMensagemModal(
          "Erro ao carregar seus dados: " + erro.message,
          "danger"
        );
        setTimeout(() => {
          window.location.href = "login.html";
        }, 100);
        return;
      }
    }

    function alterarFormularioNome() {
      const form = document.getElementById("formAlterarNome");
      const botaoSubmit = form.querySelector("button[type=submit]");

      const campoNome = document.getElementById("nome");
      const campoSetor = document.getElementById("setor");
      const campoTurno = document.getElementById("turno");

      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nome = campoNome.value.trim();
        const setor = campoSetor.value;
        const turno = campoTurno.value;

        if (!nome || !setor || !turno) {
          FiberGuardian.Utils.exibirMensagemModal(
            "Todos os campos devem ser preenchidos.",
            "danger"
          );
          return;
        }

        const nomeSemMudanca = nome === nomeOriginal;
        const setorSemMudanca = setor === setorOriginal;
        const turnoSemMudanca = turno === turnoOriginal;

        if (nomeSemMudanca && setorSemMudanca && turnoSemMudanca) {
          FiberGuardian.Utils.exibirMensagemModal(
            "Nenhum dado foi alterado.",
            "info"
          );
          return;
        }

        botaoSubmit.disabled = true;

        try {
          const csrf = await FiberGuardian.Utils.obterTokenCsrf();

          const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME_TURNO_SETOR, {
            method: "PUT",
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrf,
            },
            body: JSON.stringify({ nome, setor, turno }),
          });

          if (resposta.ok) {
            const dados = await resposta.json();

            nomeOriginal = dados.nome || "";
            setorOriginal = dados.setor || "";
            turnoOriginal = dados.turno || "";

            campoNome.value = nomeOriginal;
            campoSetor.value = setorOriginal;
            campoTurno.value = turnoOriginal;

            FiberGuardian.Utils.exibirMensagemModal(
              "Dados atualizados com sucesso.",
              "success"
            );

            // Atualiza o formulário consultando o backend novamente
            await preencherCampos();
          } else {
            console.error(
              "Erro ao alterar dados do usuario:",
              await resposta.text()
            );
            await FiberGuardian.Utils.tratarErroFetch(
              resposta,
              "Erro ao alterar cadastro usuário."
            );
          }
        } catch (erro) {
          console.error(erro);
          FiberGuardian.Utils.exibirMensagemModal(
            "Erro ao enviar requisição: " + erro.message,
            "danger"
          );
        } finally {
          botaoSubmit.disabled = false;
        }
      });
    }

    function alterarFormularioSenha() {
      const form = document.getElementById("formAlterarSenha");
      const campoSenhaAtual = document.getElementById("senhaAtual");
      const campoNovaSenha = document.getElementById("novaSenha");
      const campoConfirmar = document.getElementById("confirmarSenha");

      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const senhaAtual = campoSenhaAtual.value;
        const novaSenha = campoNovaSenha.value;
        const confirmar = campoConfirmar.value;

        // Verifica se nova senha e confirmação coincidem
        if (novaSenha !== confirmar) {
          FiberGuardian.Utils.exibirMensagemModal(
            "As senhas não coincidem.",
            "danger"
          );
        }

        try {
          const csrf = await FiberGuardian.Utils.obterTokenCsrf();

          const resposta = await fetch(URL_ALTERAR_SENHA, {
            method: "PUT",
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrf,
            },
            body: JSON.stringify({
              senhaAtual: senhaAtual,
              novaSenha: novaSenha,
            }),
          });

          if (resposta.ok) {
            FiberGuardian.Utils.exibirMensagemModal(
              "Senha alterada com sucesso.",
              "success"
            );
          } else {
            await FiberGuardian.Utils.tratarErroFetch(
              resposta,
              "Erro ao alterar cadastro usuário."
            );
          }
        } catch (erro) {
          console.error(erro);
          FiberGuardian.Utils.exibirMensagemModal(
            "Erro ao enviar requisição: " + erro.message,
            "danger"
          );
        }
        form.reset();
      });
    }

    document.addEventListener("DOMContentLoaded", function () {
      FiberGuardian.TelaAlteracaoCadastroUsuario.init();
    });

    return { init: configurarEventos };
  })();
})();
