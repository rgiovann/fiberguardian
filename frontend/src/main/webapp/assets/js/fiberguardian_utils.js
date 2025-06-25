(function () {
    // Inicializa o namespace FiberGuardian se não existir
    window.FiberGuardian = window.FiberGuardian || {};
  // Define o submódulo UTILS
  FiberGuardian.Utils = {
    
getCookie: function (nome) {
    // JSESSIONID ou XSRF-TOKEN
      const cookies = document.cookie.split("; ");
      for (const cookie of cookies) {
        const [chave, valor] = cookie.split("=");
        if (chave === nome) {
          return decodeURIComponent(valor);
        }
      }
      return null;
    },
 isEmailValido: function (email) {
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }
  };
    
})();