(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.Utils = (function () {
    const API_BASE_URL = "/fiberguardian";

    function getApiUrl(path) {
      return `${API_BASE_URL}${path}`;
    }

    // 🔒 Função privada (não será exportada)
    function normalizarEmail(email) {
      return email.trim().toLowerCase();
    }

    // ✅ Função pública (exportada)
    function getCookie(nome) {
      const cookies = document.cookie.split("; ");
      for (const cookie of cookies) {
        const [chave, valor] = cookie.split("=");
        if (chave === nome) {
          return decodeURIComponent(valor);
        }
      }
      return null;
    }

    // ✅ Outra função pública
    async function obterTokenCsrf() {
      try {
        const resposta = await fetch(getApiUrl("/csrf-token"), {
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

    // ✅ Outra função pública
    function isEmailValido(email) {
      email = normalizarEmail(email); // usando função privada
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

    // Exporta apenas as funções públicas necessárias
    return {
      getCookie: getCookie,
      isEmailValido: isEmailValido,
      obterTokenCsrf: obterTokenCsrf,
      getApiUrl: getApiUrl,
    };
  })();
})();
