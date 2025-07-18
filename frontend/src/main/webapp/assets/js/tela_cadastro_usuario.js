(function () {
  "use strict";

  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.TelaCadastroUsuario = (function () {
    const form = document.getElementById("cadastroForm");
    const emailField = document.getElementById("email");
    const nomeField = document.getElementById("nome");
    const perfilField = document.getElementById("perfil");
    const senhaField = document.getElementById("senha");
    const confirmarSenhaField = document.getElementById("confirmarSenha");

    const emailInvalidError = document.getElementById("emailInvalidError");
    const emailUsedError = document.getElementById("emailUsedError");
    const senhaError = document.getElementById("senhaError");

    function validarEmail(email) {
      return FiberGuardian.Utils.isEmailValido(email);
    }

    function limparErros() {
      emailField.classList.remove("input-error");
      emailInvalidError.style.display = "none";
      emailUsedError.style.display = "none";

      confirmarSenhaField.classList.remove("input-error");
      senhaError.style.display = "none";
    }

    async function cadastrarUsuario(nome, email, senha, perfil) {
      try {
        const csrfToken = await FiberGuardian.Utils.obterTokenCsrf();

        const resposta = await fetch("/usuarios", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": csrfToken,
          },
          credentials: "include",
          body: JSON.stringify({
            nome: nome.trim(),
            email: email.trim().toLowerCase(),
            senha: senha,
            role: perfil,
          }),
        });

        if (resposta.ok) {
          FiberGuardian.Utils.exibirMensagem(
            "Login realizado com sucesso!",
            "success"
          );
          form.reset();
          limparErros();
          nomeField.focus();
        } else if (resposta.status === 409) {
          emailUsedError.style.display = "block";
          emailField.classList.add("input-error");
        } else {
          const erroTexto = await resposta.text();
          console.error("Erro ao cadastrar:", erroTexto);
          alert("Erro inesperado ao cadastrar usuário.");
        }
      } catch (erro) {
        console.error("Falha ao cadastrar usuário:", erro);
        alert("Falha ao conectar com o servidor.");
      }
    }

    function configurarEventos() {
      form.addEventListener("submit", async function (e) {
        e.preventDefault();
        limparErros();

        const nome = nomeField.value.trim();
        const email = emailField.value.trim();
        const senha = senhaField.value;
        const confirmarSenha = confirmarSenhaField.value;
        const perfil = perfilField.value;

        let valido = true;

        if (!validarEmail(email)) {
          emailInvalidError.style.display = "block";
          emailField.classList.add("input-error");
          valido = false;
        }

        if (senha !== confirmarSenha) {
          senhaError.style.display = "block";
          confirmarSenhaField.classList.add("input-error");
          valido = false;
        }

        if (!perfil) {
          alert("Por favor, selecione um perfil.");
          valido = false;
        }

        if (!valido) return;

        await cadastrarUsuario(nome, email, senha, perfil);
      });

      emailField.addEventListener("input", function () {
        if (validarEmail(emailField.value)) {
          emailInvalidError.style.display = "none";
          emailField.classList.remove("input-error");
          emailUsedError.style.display = "none";
        }
      });

      confirmarSenhaField.addEventListener("input", function () {
        if (senhaField.value === confirmarSenhaField.value) {
          senhaError.style.display = "none";
          confirmarSenhaField.classList.remove("input-error");
        }
      });
    }

    document.addEventListener("DOMContentLoaded", function () {
      FiberGuardian.TelaCadastroUsuario.init();
    });

    return {
      init: configurarEventos,
    };
  })();
})();
