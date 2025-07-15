(function () {
  window.FiberGuardian = window.FiberGuardian || {};
  FiberGuardian.TelaAlteracaoCadastroUsuario = (function () {
    const URL_BASE_BUSCAR_POR_EMAIL = "/usuarios/buscar-por-email";

    async function configurarEventos() {
      await preencherCampos();
      configurarFormularioNome();
      configurarFormularioSenha();
    }

    async function preencherCampos() {
      try {
        const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();
        const emailUsuario = FiberGuardian.UsuarioLogado?.email;

        if (!emailUsuario) {
          throw new Error("Email do usuário não encontrado. Faça login novamente.");
        }
        const resposta = await fetch(`${URL_BASE_BUSCAR_POR_EMAIL}?email=${encodeURIComponent(emailUsuario)}`, {
          method: "GET",
          headers: { "X-XSRF-TOKEN": csrfToken },
          credentials: "include",
        });

        if (!resposta.ok) throw new Error("Erro ao obter dados");

        const usuario = await resposta.json();
        document.getElementById("nome").value = usuario.nome || "";
        document.getElementById("email").value = usuario.email || "";
      } catch (erro) {
        exibirMensagem("Erro ao carregar seus dados.", "danger");
      }
    }

    function configurarFormularioNome() {
      const form = document.getElementById("formAlterarNome");
      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const nome = document.getElementById("nome").value.trim();
        if (!nome) {
          exibirMensagem("O nome não pode estar vazio.", "danger");
          return;
        }

        try {
          const csrf = await FiberGuardian.Utils.obterNovoToken();

          const resposta = await fetch(`${URL_BASE}/nome`, {
            method: "PUT",
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
              "X-XSRF-TOKEN": csrf,
            },
            body: JSON.stringify({ nome }),
          });

          if (resposta.ok) {
            exibirMensagem("Nome alterado com sucesso.", "success");
          } else {
            exibirMensagem("Erro ao atualizar o nome.", "danger");
          }
        } catch (erro) {
          console.error(erro);
          exibirMensagem("Erro ao enviar requisição.", "danger");
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
            exibirMensagem("Senha alterada com sucesso.", "success");
            form.reset();
          } else if (resposta.status === 401) {
            exibirMensagem("Senha atual incorreta.", "danger");
          } else {
            exibirMensagem("Erro ao atualizar a senha.", "danger");
          }
        } catch (erro) {
          console.error(erro);
          exibirMensagem("Erro ao enviar requisição.", "danger");
        }
      });
    }

    function exibirMensagem(texto, tipo) {
      const msg = document.getElementById("mensagemSistema");
      msg.classList.remove("d-none", "alert-success", "alert-danger");
      msg.classList.add(`alert-${tipo}`);
      msg.textContent = texto;

      setTimeout(() => {
        msg.classList.add("d-none");
      }, 5000);
    }

    return { init: configurarEventos };
  })();
})();
