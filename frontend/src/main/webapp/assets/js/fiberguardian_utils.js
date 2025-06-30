(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.Utils = (function () {
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
    function isEmailValido(email) {
      email = normalizarEmail(email); // usando função privada
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

    // Exporta apenas o necessário
    return {
      getCookie: getCookie,
      isEmailValido: isEmailValido,
    };
  })();
})();
