(function () {
  // Garante que o namespace FiberGuardian exista no escopo global
  window.FiberGuardian = window.FiberGuardian || {};

  // Define o submódulo CORE
  FiberGuardian.Core = FiberGuardian.Core || {};

  /**
   * Mapeia páginas HTML para seus scripts JS correspondentes.
   * A chave é o nome da página (parcial ou completo) e o valor é o caminho do script associado.
   */
  const pageToScriptMap = {
    "tela_cadastro_recebimento.html": "assets/js/tela_cadastro_recebimento.js",
    "tela_cadastro_usuario.html": "assets/js/tela_cadastro_usuario.js",
    // Ex: 'cadastro_usuario.html': 'assets/js/cadastro_usuario.js'
  };

  /**
   * Carrega dinamicamente uma página HTML e seu script JS associado.
   *
   * @param {string} pagina - Caminho relativo do HTML a ser carregado.
   */
  FiberGuardian.Core.carregarPagina = function (pagina) {
    fetch(pagina)
      .then((resposta) => {
        if (!resposta.ok) throw new Error("Erro ao carregar a página.");
        return resposta.text();
      })
      .then((html) => {
        // Injeta o HTML carregado no elemento principal da aplicação
        document.getElementById("conteudo-principal").innerHTML = html;

        // Verifica se há script associado à página
        const scriptPath = pageToScriptMap[pagina];

        if (scriptPath) {
          const script = document.createElement("script");
          script.src = scriptPath;

          // Após carregar o script, identifica e inicializa o módulo JS correto
          script.onload = function () {
            console.log(`✅ Script carregado: ${scriptPath}`);

            /**
             * Transforma 'assets/js/tela_recebimento.js' em 'TelaRecebimento':
             * - Remove o caminho do diretório
             * - Remove extensão '.js'
             * - Substitui underscores por espaço
             * - Capitaliza palavras (camel-case)
             * - Remove espaços
             */
            const nomeModulo = scriptPath
              .replace(/^.*\//, "") // 'tela_recebimento.js'
              .replace(".js", "") // 'tela_recebimento'
              .replace(/_/g, " ") // 'tela recebimento'
              .replace(/(^\w|\s\w)/g, (m) => m.toUpperCase()) // 'Tela Recebimento'
              .replace(/\s+/g, ""); // 'TelaRecebimento'

            console.log(`🔍 Módulo identificado: [${nomeModulo}]`);

            // Inicializa o módulo se estiver corretamente registrado no namespace
            if (
              FiberGuardian[nomeModulo] &&
              typeof FiberGuardian[nomeModulo].init === "function"
            ) {
              console.log(`🚀 Inicializando módulo: [${nomeModulo}]`);
              FiberGuardian[nomeModulo].init();
            } else {
              console.warn(
                `⚠️ Módulo [${nomeModulo}] não encontrado ou não possui init().`
              );
            }
          };

          // Anexa dinamicamente o script ao <body>
          document.body.appendChild(script);
        }
      })
      .catch((erro) => {
        console.error("❌ Falha ao carregar conteúdo:", erro);
        document.getElementById("conteudo-principal").innerHTML =
          "<p>Erro ao carregar conteúdo.</p>";
      });
  };
})();
