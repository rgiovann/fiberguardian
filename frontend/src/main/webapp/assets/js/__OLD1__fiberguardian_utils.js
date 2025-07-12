(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.Utils = (function () {
    console.log("FiberGuardian.Utils inicializado corretamente");

    function normalizarEmail(email) {
      return email.trim().toLowerCase();
    }

    function isEmailValido(email) {
      email = normalizarEmail(email);
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

    return {
      isEmailValido,
    };
  })();
})();
