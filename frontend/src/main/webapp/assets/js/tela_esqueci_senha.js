(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.TelaEsqueciSenha = (function () {
    "use strict";

    function configurarEventos() {
      const formAdmin = document.getElementById("formAdmin");
      const formNovaSenha = document.getElementById("formNovaSenha");

      console.log("Entrou na funcao ConfigurarEventos...");

      if (!formAdmin || !formNovaSenha) {
        console.error("Formulários não encontrados.");
        return;
      }

      formAdmin.addEventListener("submit", async function (e) {
        e.preventDefault();

        const email = formAdmin.adminEmail?.value.trim();
        const senha = formAdmin.adminSenha?.value;

        if (!FiberGuardian.Utils.isEmailValido(email)) {
          FiberGuardian.Utils.exibirMensagem(
            "E-mail inválido.",
            "danger",
            "mensagemSupervisor"
          );
          return;
        }

        if (!senha) {
          FiberGuardian.Utils.exibirMensagem(
            "Informe a senha do supervisor.",
            "danger",
            "mensagemSupervisor"
          );
          return;
        }
        console.log("Antes do try catch...");

        try {
          console.log("Obtendo novo token csrf...");

          const csrfToken = await FiberGuardian.Utils.obterNovoToken();

          console.log("Email supervisor: " + email);
          console.log("Senha supervisor: " + senha);

          const resposta = await fetch("/api/usuarios/validar-admin", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrfToken,
            },
            credentials: "include", //  enviar o cookie de sessão
            body: JSON.stringify({ email, senha }),
          });

          if (resposta.ok) {
            FiberGuardian.Utils.exibirMensagem(
              "Acesso reset senha autorizado.",
              "success",
              "mensagemSupervisor"
            );

            formNovaSenha.classList.remove("d-none");
            formAdmin
              .querySelectorAll("input, button")
              .forEach((el) => (el.disabled = true));
          } else if (resposta.status === 401 || resposta.status === 403) {
            FiberGuardian.Utils.exibirMensagem(
              "Credenciais do supervisor inválidas.",
              "danger",
              "mensagemSupervisor"
            );
          } else {
            FiberGuardian.Utils.exibirMensagem(
              "Erro inesperado ao validar supervisor.",
              "danger",
              "mensagemSupervisor"
            );
            console.error("Erro HTTP:", resposta.status, await resposta.text());
          }
        } catch (erro) {
          console.error("Falha na requisição:", erro);
          FiberGuardian.Utils.exibirMensagem(
            "Erro de rede ao validar supervisor.",
            "danger",
            "mensagemSupervisor"
          );
        }
      });
      formNovaSenha.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nova = formNovaSenha.novaSenha?.value;
        const repetir = formNovaSenha.confirmarSenha?.value;
        const emailUsuario = formNovaSenha.usuarioEmail?.value?.trim();

        if (!nova || !repetir || !emailUsuario) {
          FiberGuardian.Utils.exibirMensagem(
            "Preencha todos os campos.",
            "danger",
            "mensagemRedefinicao"
          );
          return;
        }

        if (!FiberGuardian.Utils.isEmailValido(emailUsuario)) {
          FiberGuardian.Utils.exibirMensagem(
            "E-mail do usuário inválido.",
            "danger",
            "mensagemRedefinicao"
          );
          return;
        }

        if (nova !== repetir) {
          FiberGuardian.Utils.exibirMensagem(
            "As senhas não coincidem.",
            "danger",
            "mensagemRedefinicao"
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
            FiberGuardian.Utils.exibirMensagem(
              "Senha redefinida com sucesso.",
              "success",
              "mensagemRedefinicao"
            );
            setTimeout(() => {
              window.location.href = "login.html";
            }, 100);
          } else if (resposta.status === 403) {
            FiberGuardian.Utils.exibirMensagem(
              "Acesso negado. Sua sessão expirou ou você não é administrador.",
              "danger",
              "mensagemRedefinicao"
            );
          } else {
            const erro = await resposta.text();
            console.error("Erro ao redefinir senha:", resposta.status, erro);
            FiberGuardian.Utils.exibirMensagem(
              "Erro ao redefinir senha. Tente novamente.",
              "danger",
              "mensagemRedefinicao"
            );
          }
        } catch (erro) {
          console.error("Erro de rede:", erro);
          FiberGuardian.Utils.exibirMensagem(
            "Erro de rede ao tentar redefinir a senha.",
            "danger",
            "mensagemRedefinicao"
          );
        }
      });
    }

    return {
      init: configurarEventos,
    };
  })();
})();
