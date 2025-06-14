(function() {
    console.log('Script tela_recebimento.js carregado e executado.');

    function updateCalculations() {
        const quantRecebida = parseInt(document.getElementById("quantRecebida")?.value) || 0;
        const numeroCaixas = parseInt(document.getElementById("numeroCaixas")?.value) || 0;
        const valorNota = parseFloat(document.getElementById("valorNota")?.value) || 0;

        const rochasPorCaixa = 300;

        const quantRochas = numeroCaixas * rochasPorCaixa;
        document.getElementById("quantRochas")?.setAttribute('value', quantRochas);

        const pesoMedio = (quantRecebida > 0 && quantRochas > 0)
            ? (quantRecebida / quantRochas).toFixed(2)
            : 0;
        document.getElementById("pesoMedio")?.setAttribute('value', pesoMedio);

        const valorUnit = (quantRecebida > 0)
            ? (valorNota / quantRecebida).toFixed(2)
            : 0;
        document.getElementById("valorUnit")?.setAttribute('value', valorUnit);

        const pesoMedioCaixa = (numeroCaixas > 0)
            ? (quantRecebida / numeroCaixas).toFixed(2)
            : 0;
        document.getElementById("pesoMedioCaixa")?.setAttribute('value', pesoMedioCaixa);
    }

    // Adicionar listeners de forma segura
    document.getElementById("quantRecebida")?.addEventListener("input", updateCalculations);
    document.getElementById("numeroCaixas")?.addEventListener("input", updateCalculations);
    document.getElementById("valorNota")?.addEventListener("input", updateCalculations);

})();
