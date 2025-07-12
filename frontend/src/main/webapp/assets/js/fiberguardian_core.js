(function () {
  // Garante que o namespace FiberGuardian exista no escopo global
  window.FiberGuardian = window.FiberGuardian || {};

  // Define o submódulo CORE
  FiberGuardian.Core = FiberGuardian.Core || {};

  /**
   * Mapeia páginas HTML para seus scripts JS correspondentes.
   * Cada página pode ter múltiplas dependências que serão carregadas em ordem.
   */
  const pageToScriptMap = {
    "tela_cadastro_recebimento.html": [
      "assets/js/fiberguardian_utils.js",
      "assets/js/tela_cadastro_recebimento.js"
    ],
    "tela_cadastro_usuario.html": [
      "assets/js/fiberguardian_utils.js",
      "assets/js/tela_cadastro_usuario.js"
    ],
    // Adicione outras páginas conforme necessário
    // "tela_listagem_usuarios.html": [
    //   "assets/js/fiberguardian_utils.js",
    //   "assets/js/fiberguardian_api.js",
    //   "assets/js/tela_listagem_usuarios.js"
    // ]
  };

  /**
   * Cache de scripts já carregados para evitar recarregamentos desnecessários.
   */
  const scriptsCarregados = new Set();

  /**
   * Carrega dinamicamente uma página HTML e seus scripts JS associados.
   *
   * @param {string} pagina - Caminho relativo do HTML a ser carregado.
   */
  FiberGuardian.Core.carregarPagina = function (pagina) {
    console.log(`Carregando página: ${pagina}`);

    fetch(pagina)
      .then((resposta) => {
        if (!resposta.ok) {
          throw new Error(`Erro HTTP ${resposta.status}: ${resposta.statusText}`);
        }
        return resposta.text();
      })
      .then((html) => {
        // Injeta o HTML carregado no elemento principal da aplicação
        document.getElementById("conteudo-principal").innerHTML = html;

        // Carrega os scripts associados à página
        const scriptsAssociados = pageToScriptMap[pagina];
        
        if (scriptsAssociados && scriptsAssociados.length > 0) {
          carregarScriptsSequencial(scriptsAssociados, pagina);
        } else {
          console.warn(`Nenhum script associado encontrado para: ${pagina}`);
        }
      })
      .catch((erro) => {
        console.error("Falha ao carregar conteúdo:", erro);
        document.getElementById("conteudo-principal").innerHTML = 
          `<div class="alert alert-danger">
            <i class="fas fa-exclamation-triangle"></i> 
            Erro ao carregar conteúdo: ${erro.message}
          </div>`;
      });
  };

  /**
   * Carrega scripts sequencialmente (um após o outro) para garantir dependências.
   * 
   * @param {string[]} scripts - Array de caminhos dos scripts
   * @param {string} paginaOrigem - Nome da página que originou o carregamento
   */
  function carregarScriptsSequencial(scripts, paginaOrigem) {
    console.log(`Carregando ${scripts.length} script(s) para: ${paginaOrigem}`);
    
    let indiceAtual = 0;

    function carregarProximoScript() {
      if (indiceAtual >= scripts.length) {
        // Todos os scripts foram carregados, inicializar módulo principal
        inicializarModuloPrincipal(paginaOrigem);
        return;
      }

      const scriptPath = scripts[indiceAtual];
      
      // Verifica se o script já foi carregado
      if (scriptsCarregados.has(scriptPath)) {
        console.log(`Script já carregado (cache): ${scriptPath}`);
        indiceAtual++;
        carregarProximoScript();
        return;
      }

      console.log(`Carregando script: ${scriptPath}`);
      
      const script = document.createElement("script");
      script.src = scriptPath;

      script.onload = function () {
        console.log(`Script carregado com sucesso: ${scriptPath}`);
        scriptsCarregados.add(scriptPath);
        indiceAtual++;
        carregarProximoScript();
      };

      script.onerror = function () {
        console.error(`Erro ao carregar script: ${scriptPath}`);
        // Continua tentando carregar os próximos scripts mesmo com erro
        indiceAtual++;
        carregarProximoScript();
      };

      // Adiciona o script ao DOM
      document.head.appendChild(script);
    }

    carregarProximoScript();
  }

  /**
   * Inicializa o módulo principal da página após todos os scripts serem carregados.
   * 
   * @param {string} paginaOrigem - Nome da página para identificar o módulo
   */
  function inicializarModuloPrincipal(paginaOrigem) {
    /**
     * Transforma 'tela_cadastro_usuario.html' em 'TelaCadastroUsuario':
     * - Remove extensão '.html'
     * - Substitui underscores por espaço
     * - Capitaliza palavras (camel-case)
     * - Remove espaços
     */
    const nomeModulo = paginaOrigem
      .replace(".html", "") // 'tela_cadastro_usuario'
      .replace(/_/g, " ") // 'tela cadastro usuario'
      .replace(/(^\w|\s\w)/g, (m) => m.toUpperCase()) // 'Tela Cadastro Usuario'
      .replace(/\s+/g, ""); // 'TelaCadastroUsuario'

    console.log(`Tentando inicializar módulo: [${nomeModulo}]`);

    // Verifica se o módulo existe e possui função init
    if (
      window.FiberGuardian &&
      FiberGuardian[nomeModulo] &&
      typeof FiberGuardian[nomeModulo].init === "function"
    ) {
      console.log(`Inicializando módulo: [${nomeModulo}]`);
      FiberGuardian[nomeModulo].init();
    } else {
      console.warn(
        `Módulo [${nomeModulo}] não encontrado ou não possui método init().`
      );
      console.log("Módulos disponíveis:", Object.keys(FiberGuardian));
    }
  }

  /**
   * Função utilitária para limpar cache de scripts (útil para desenvolvimento).
   */
  FiberGuardian.Core.limparCacheScripts = function () {
    scriptsCarregados.clear();
    console.log("Cache de scripts limpo");
  };

  /**
   * Função utilitária para verificar quais scripts estão em cache.
   */
  FiberGuardian.Core.verificarCacheScripts = function () {
    console.log("Scripts em cache:", Array.from(scriptsCarregados));
    return Array.from(scriptsCarregados);
  };

})();