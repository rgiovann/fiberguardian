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

    async function obterNovoToken() {
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
        console.error("Falha ao obter novo token CSRF:", erro);
        throw erro;
      }
    }

    async function obterTokenCsrf() {
      const tokenExistente = getCookie("XSRF-TOKEN");

      if (tokenExistente) {
        return tokenExistente;
      }

      // Fallback defensivo
      return await obterNovoToken();
    }

    // ✅ Outra função pública
    function isEmailValido(email) {
      email = normalizarEmail(email); // usando função privada
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

    // 🔒 Função privada: verifica se a sessão ainda está ativa
    async function verificarSessao() {
      try {
        const resposta = await fetch(getApiUrl("/sessao/valida"), {
          method: "GET",
          credentials: "include",
        });

        if (resposta.status === 401 || resposta.status === 403) {
          alert("Sua sessão expirou. Você será redirecionado para o login.");
          window.location.href = "tela_login.html";
        }
      } catch (erro) {
        console.warn("Erro ao verificar sessão:", erro);
      }
    }

    // 🔒 Função privada: inicia verificação periódica
    function iniciarMonitoramentoSessao() {
      setInterval(verificarSessao, 5 * 60 * 1000); // a cada 5 minutos
    }

    // ✅ Função pública para inicializar o watcher
    function iniciarWatcherDeSessao() {
      iniciarMonitoramentoSessao();
    }

    // Exporta apenas as funções públicas necessárias
    return {
      getCookie: getCookie,
      isEmailValido: isEmailValido,
      obterTokenCsrf: obterTokenCsrf,
      obterNovoToken: obterNovoToken,
      getApiUrl: getApiUrl,
      iniciarWatcherDeSessao 
  })();
})();
