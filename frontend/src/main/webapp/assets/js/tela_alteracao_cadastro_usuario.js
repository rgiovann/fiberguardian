(function () {
  window.FiberGuardian = window.FiberGuardian || {};
  FiberGuardian.TelaAlteracaoCadastroUsuario = (function () {
    const URL_BASE = "/usuarios";
    const URL_BUSCAR_ALTERAR_NOME = `${URL_BASE}/me/nome`;

    async function configurarEventos() {
      await preencherCampos();
      alterarFormularioNome();
      configurarFormularioSenha();
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

    function configurarFormularioSenha() {
      const form = document.getElementById("formAlterarSenha");
      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const senhaAtual = document.getElementById("senhaAtual").value;
        const novaSenha = document.getElementById("novaSenha").value;
        const confirmar = document.getElementById("confirmarSenha").value;

        if (novaSenha !== confirmar) {
          document.getElementById("senhaMismatchError").style.display = "block";
          return;
        } else {
          document.getElementById("senhaMismatchError").style.display = "none";
        }

        try {
          const csrf = await FiberGuardian.Utils.obterNovoToken();

          const resposta = await fetch(`${URL_BASE}/senha`, {
            method: "PUT",
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrf,
            },
            body: JSON.stringify({ senhaAtual, novaSenha }),
          });

          if (resposta.ok) {
            FiberGuardian.Utils.exibirMensagem(
              "Senha alterada com sucesso.",
              "success"
            );
            form.reset();
          } else if (resposta.status === 401) {
            FiberGuardian.Utils.exibirMensagem(
              "Senha atual incorreta.",
              "danger"
            );
          } else {
            FiberGuardian.Utils.exibirMensagem(
              "Erro ao atualizar a senha.",
              "danger"
            );
          }
        } catch (erro) {
          console.error(erro);
          FiberGuardian.Utils.exibirMensagem(
            "Erro ao enviar requisição.",
            "danger"
          );
        }
      });
    }
    return { init: configurarEventos };
  })();
})();
