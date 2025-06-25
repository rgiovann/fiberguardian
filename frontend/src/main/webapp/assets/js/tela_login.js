(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaLogin = {
        // Propriedades para armazenar tokens
        csrfToken: null,
        sessionId: null,

    autenticar: async function (email, senha) {
        const csrfToken = FiberGuardian.Utils.getCookie("XSRF-TOKEN");

        const resposta = await fetch("https://localhost:8443/fiberguardian/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-XSRF-TOKEN": csrfToken
          },
          credentials: "include",
          body: JSON.stringify({ email, senha })
        });

        if (resposta.ok) {
          const usuario = await resposta.json();
          console.log("Login bem-sucedido:", usuario);
        } else if (resposta.status === 401) {
          alert("Credenciais inválidas.");
        } else {
          alert("Erro ao autenticar.");
          console.error("Erro:", await resposta.text());
        }
      }
    };
})();