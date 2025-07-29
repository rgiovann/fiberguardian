(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.TelaEsqueciSenha = (function () {
    "use strict";

    function configurarEventos() {
      const formAdmin = document.getElementById("formAdmin");
      const formNovaSenha = document.getElementById("formNovaSenha");

      if (!formAdmin || !formNovaSenha) {
        console.error("Formulários não encontrados.");
        return;
      }

      formAdmin.addEventListener("submit", async function (e) {
        e.preventDefault();

        const email = formAdmin.adminEmail?.value.trim();
        const senha = formAdmin.adminSenha?.value;

        if (!FiberGuardian.Utils.isEmailValido(email)) {
          FiberGuardian.Utils.exibirMensagemModal("E-mail inválido.", "danger");
          return;
        }

        if (!senha) {
          FiberGuardian.Utils.exibirMensagemModal(
            "Informe a senha do supervisor.",
            "danger"
          );
          return;
        }

        try {
          const csrfToken = await FiberGuardian.Utils.obterNovoToken();

          const resposta = await fetch("/api/usuarios/validar-admin", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrfToken,
            },
            credentials: "include",
            body: JSON.stringify({ email, senha }),
          });

          if (resposta.ok) {
            formNovaSenha.classList.remove("d-none");

            // 1. Força o foco para sair do campo atual
            document.activeElement.blur();

            // 2. Aplica readonly após remover o foco
            formAdmin.adminEmail.readOnly = true;
            formAdmin.adminSenha.readOnly = true;

            // 3. (opcional, ideal) Foca no próximo campo do passo seguinte
            formNovaSenha.usuarioEmail?.focus();

            formAdmin
              .querySelectorAll("button")
              .forEach((btn) => (btn.disabled = true));
          } else {
            await FiberGuardian.Utils.tratarErroFetch(
              resposta,
              "Erro ao validar supervisor."
            );
          }
        } catch (erro) {
          console.error("Falha na requisição:", erro);
          FiberGuardian.Utils.exibirMensagemModal(
            "Erro de rede ao validar supervisor.",
            "danger"
          );
        }
      });

      formNovaSenha.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nova = formNovaSenha.novaSenha?.value;
        const repetir = formNovaSenha.confirmarSenha?.value;
        const emailUsuario = formNovaSenha.usuarioEmail?.value?.trim();

        const campoNova = formNovaSenha.querySelector('[name="novaSenha"]');
        const campoConfirmar = formNovaSenha.querySelector(
          '[name="confirmarSenha"]'
        );
        const campoEmailUsuario = formNovaSenha.querySelector(
          '[name="usuarioEmail"]'
        );

        if (nova !== repetir) {
          // Referência ao campo

          // Limpa os campos antes de exibir o modal
          campoNova.value = "";
          campoConfirmar.value = "";

          FiberGuardian.Utils.exibirMensagemModalComFoco(
            "As senhas não coincidem.",
            "danger",
            campoNova
          );

          return;
        }

        try {
          const csrfToken = await FiberGuardian.Utils.obterNovoToken();

          const resposta = await fetch("/api/usuarios/reset-senha", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrfToken,
            },
            credentials: "include",
            body: JSON.stringify({
              email: emailUsuario,
              senha: nova,
              repeteSenha: repetir,
            }),
          });

          if (resposta.ok) {
            FiberGuardian.Utils.exibirMensagemModal(
              "Senha redefinida com sucesso.",
              "success"
            );
            setTimeout(() => {
              window.location.href = "login.html";
            }, 100);
            return;
          }
          // resposta para expiracao de sessao deve ser a parte
          if (resposta.status === 403) {
            exibirMensagemModal(
              "Sua sessão expirou. Por favor, faça login novamente.",
              "danger"
            );
            setTimeout(() => (window.location.href = "login.html"), 2000);
            return;
          } else {
            if (resposta.status === 404) {
              campoNova.value = "";
              campoConfirmar.value = "";
              campoEmailUsuario.value = "";
              await FiberGuardian.Utils.tratarErroFetch(
                resposta,
                "Erro ao redefinir senha. Tente novamente.",
                campoEmailUsuario
              );
            } else {
              campoNova.value = "";
              campoConfirmar.value = "";
              await FiberGuardian.Utils.tratarErroFetch(
                resposta,
                "Erro ao redefinir senha. Tente novamente.",
                campoNova
              );
            }
            return;
          }
        } catch (erro) {
          campoNova.value = "";
          campoConfirmar.value = "";
          campoEmailUsuario.value = "";
          FiberGuardian.Utils.exibirMensagemModalComFoco(
            "Erro de rede ao tentar redefinir a senha.",
            "danger",
            campoEmailUsuario
          );
        }
      });
    }

    return {
      init: configurarEventos,
    };
  })();
})();
