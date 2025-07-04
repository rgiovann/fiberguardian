(function () {
  //const API_BASE_URL = "https://localhost:8443/fiberguardian";
  //const API_BASE_URL = "/fiberguardian"; // caddy

  window.FiberGuardian = window.FiberGuardian || {};
  FiberGuardian.TelaLogin = (function () {
    /*
    async function obterTokenCsrf() {
      try {
        const resposta = await fetch(`${API_BASE_URL}/csrf-token`, {
          method: "GET",
          credentials: "include",
        });

        if (!resposta.ok) {
          throw new Error(`Erro ao obter token CSRF: ${resposta.statusText}`);
        }

        const dados = await resposta.json();
        if (!dados.token) {
          throw new Error("Token CSRF não retornado pelo servidor.");
        }

        return dados.token;
      } catch (erro) {
        console.error("Falha ao obter token CSRF:", erro);
        throw erro;
      }
    }
*/
    async function autenticar(email, senha) {
      try {
        const csrfToken = await FiberGuardian.Utils.obterNovoToken();
        console.log("Token CSRF a ser enviado:", csrfToken); // Log para depuração
        const resposta = await fetch(FiberGuardian.Utils.getApiUrl("/login"), {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": csrfToken,
          },
          credentials: "include",
          body: JSON.stringify({ email, senha }),
        });

        if (resposta.ok) {
          const usuario = await resposta.json();
          console.log("Login bem-sucedido:", usuario);
          window.location.href = "index.html";
        } else if (resposta.status === 401) {
          alert("Credenciais inválidas.");
        } else {
          console.error("Erro ao autenticar:", await resposta.text());
          alert("Erro inesperado ao autenticar.");
        }
      } catch (erro) {
        console.error("Erro no processo de login:", erro);
        alert("Falha ao autenticar. Tente novamente mais tarde.");
      }
    }

    function configurarEventos() {
      const form = document.querySelector("form");
      if (!form) {
        console.error("Formulário de login não encontrado.");
        return;
      }

      form.addEventListener("submit", function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const senha = document.getElementById("senha").value;

        if (!FiberGuardian.Utils.isEmailValido(email)) {
          alert("E-mail inválido.");
          return;
        }

        if (!senha) {
          alert("Informe a senha.");
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
