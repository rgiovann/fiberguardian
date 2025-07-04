(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.TelaCadastroRecebimento = (function () {
    function init() {
      console.log("Módulo TelaRecebimento inicializado.");

      // Adicionar listeners de forma segura
      const quantRecebidaInput = document.getElementById("quantRecebida");
      const numeroCaixasInput = document.getElementById("numeroCaixas");
      const valorNotaInput = document.getElementById("valorNota");

      if (quantRecebidaInput) {
        quantRecebidaInput.addEventListener("input", this.updateCalculations);
      }
      if (numeroCaixasInput) {
        numeroCaixasInput.addEventListener("input", this.updateCalculations);
      }
      if (valorNotaInput) {
        valorNotaInput.addEventListener("input", this.updateCalculations);
      }

      // Executar cálculos iniciais
      this.updateCalculations();
    }

    function updateCalculations() {
      const quantRecebida =
        parseInt(document.getElementById("quantRecebida")?.value) || 0;
      const numeroCaixas =
        parseInt(document.getElementById("numeroCaixas")?.value) || 0;
      const valorNota =
        parseFloat(document.getElementById("valorNota")?.value) || 0;

      const rochasPorCaixa = 300;

      const quantRochas = numeroCaixas * rochasPorCaixa;
      document
        .getElementById("quantRochas")
        ?.setAttribute("value", quantRochas);

      const pesoMedio =
        quantRecebida > 0 && quantRochas > 0
          ? (quantRecebida / quantRochas).toFixed(2)
          : 0;
      document.getElementById("pesoMedio")?.setAttribute("value", pesoMedio);

      const valorUnit =
        quantRecebida > 0 ? (valorNota / quantRecebida).toFixed(2) : 0;
      document.getElementById("valorUnit")?.setAttribute("value", valorUnit);

      const pesoMedioCaixa =
        numeroCaixas > 0 ? (quantRecebida / numeroCaixas).toFixed(2) : 0;
      document
        .getElementById("pesoMedioCaixa")
        ?.setAttribute("value", pesoMedioCaixa);
    }

    function voltarParaInicio() {
      document.getElementById("conteudo-principal").innerHTML = `
                <h3>Bem-vindo ao FiberGuardian</h3>
                <p>Selecione uma opção no menu lateral para começar.</p>
            `;
    }

    // Revealing: expõe apenas as funções públicas
    return {
      init: init,
      updateCalculations: updateCalculations,
      voltarParaInicio: voltarParaInicio,
    };
  })();
})();
