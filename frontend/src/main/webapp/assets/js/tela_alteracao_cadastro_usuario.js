(function () {
  window.FiberGuardian = window.FiberGuardian || {};
  FiberGuardian.TelaAlteracaoCadastroUsuario = (function () {
    const URL_BASE = "/usuarios";
    const URL_BUSCAR_ALTERAR_NOME = `${URL_BASE}/me/nome`;
    const URL_ALTERAR_SENHA = `${URL_BASE}/me/senha`;

    let nomeOriginal = "";

    async function configurarEventos() {
      await preencherCampos();
      alterarFormularioNome();
      alterarFormularioSenha();
    }

    async function preencherCampos() {
      try {
        const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

        const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME, {
          method: "GET",
          headers: { "X-XSRF-TOKEN": csrfToken },
          credentials: "include",
        });

        if (!resposta.ok) {
          const tipo = resposta.headers.get("Content-Type") || "";
          const mensagemErro = tipo.includes("application/json")
            ? (await resposta.json()).erro || "Erro inesperado."
            : await resposta.text();

          throw new Error(mensagemErro);
        }

        const usuario = await resposta.json();
        nomeOriginal = usuario.nome || "";
        document.getElementById("nome").value = usuario.nome || "";
        document.getElementById("email").value = usuario.email || "";
      } catch (erro) {
        console.error(erro);
        FiberGuardian.Utils.exibirMensagem(
          "Erro ao carregar seus dados: " + erro.message,
          "danger"
        );
      }
    }

    function alterarFormularioNome() {
      const form = document.getElementById("formAlterarNome");
      const botaoSubmit = form.querySelector("button[type=submit]");
      const campoNome = document.getElementById("nome");

      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nome = campoNome.value.trim();
        if (!nome) {
          FiberGuardian.Utils.exibirMensagem(
            "O nome não pode estar vazio.",
            "danger"
          );
          return;
        }

        // Verifica se o nome não mudou
        if (nome === nomeOriginal) {
          FiberGuardian.Utils.exibirMensagem(
            "O nome informado já é o mesmo cadastrado.",
            "info"
          );
          return;
        }

        botaoSubmit.disabled = true;

        try {
          const csrf = await FiberGuardian.Utils.obterTokenCsrf();

          const resposta = await fetch(URL_BUSCAR_ALTERAR_NOME, {
            method: "PUT",
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrf,
            },
            body: JSON.stringify({ nome }),
          });

          if (resposta.ok) {
            const dados = await resposta.json();
            // Reflete o novo nome retornado no campo de input
            campoNome.value = dados.nome || "";
            nomeOriginal = dados.nome || "";

            FiberGuardian.Utils.exibirMensagem(
              "Nome alterado com sucesso.",
              "success"
            );
          } else {
            const tipo = resposta.headers.get("Content-Type") || "";
            const mensagemErro = tipo.includes("application/json")
              ? (await resposta.json()).erro || "Erro inesperado."
              : await resposta.text();

            FiberGuardian.Utils.exibirMensagem(
              "Erro ao atualizar o nome: " + mensagemErro,
              "danger"
            );
          }
        } catch (erro) {
          console.error(erro);
          FiberGuardian.Utils.exibirMensagem(
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
      const erroMismatch = document.getElementById("senhaMismatchError");

      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const senhaAtual = campoSenhaAtual.value;
        const novaSenha = campoNovaSenha.value;
        const confirmar = campoConfirmar.value;

        // Verifica se nova senha e confirmação coincidem
        if (novaSenha !== confirmar) {
          erroMismatch.style.display = "block";
          return;
        } else {
          erroMismatch.style.display = "none";
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
            FiberGuardian.Utils.exibirMensagem("Senha alterada com sucesso.", "success");
            form.reset(); // limpa os campos por segurança
          } else if (resposta.status === 400 || resposta.status === 403) {
            const tipo = resposta.headers.get("Content-Type") || "";
            const mensagemErro = tipo.includes("application/json")
              ? (await resposta.json()).userMessage || "Erro ao alterar a senha."
              : await resposta.text();

            FiberGuardian.Utils.exibirMensagem(mensagemErro, "danger");
          } else {
            FiberGuardian.Utils.exibirMensagem("Erro inesperado ao alterar senha.", "danger");
          }
        } catch (erro) {
          console.error(erro);
          FiberGuardian.Utils.exibirMensagem("Erro ao enviar requisição: " + erro.message, "danger");
        }
      });
    }

    return { init: configurarEventos };
  })();
})();
