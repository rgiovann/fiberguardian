(function () {
  window.FiberGuardian = window.FiberGuardian || {};

  FiberGuardian.Utils = (function () {
    "use strict";
    console.log("FiberGuardian.Utils carregado com sucesso.");

    // === Funções Privadas ===

    function normalizarEmail(email) {
      return email.trim().toLowerCase();
    }

    function exibirMensagemInterna(texto, tipo, elementId = "mensagemSistema") {
      const alerta = document.getElementById(elementId);
      if (!alerta) return;

      alerta.textContent = texto;
      alerta.classList.add(`alert-${tipo}`, "visivel");

      setTimeout(() => {
        alerta.classList.remove(`alert-${tipo}`, "visivel");
        alerta.textContent = "";
      }, 1500);
    }

    function desabilitarEntradas(formulario, desabilitar) {
      if (!formulario) return;
      formulario.querySelectorAll("input, button").forEach((el) => {
        el.disabled = desabilitar;
      });
    }

    async function verificarSessao() {
      try {
        const resposta = await fetch("/api/sessao/valida", {
          method: "GET",
          credentials: "include",
        });

        if (resposta.status === 401 || resposta.status === 403) {
          exibirMensagemInterna(
            "Sua sessão expirou. Você será redirecionado para o login.",
            "danger"
          );
          window.location.href = "tela_login.html";
        }
      } catch (erro) {
        console.warn("Erro ao verificar sessão:", erro);
      }
    }

    function iniciarMonitoramentoSessao() {
      setInterval(verificarSessao, 5 * 60 * 1000);
    }

    // === Funções Públicas ===

    function isEmailValido(email) {
      const emailNormalizado = normalizarEmail(email);
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailNormalizado);
    }

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
      const resposta = await fetch("/api/csrf-token", {
        method: "GET",
        credentials: "include",
      });

      if (resposta.status >= 500) {
        throw new Error(
          `HTTP ${resposta.status} - O sistema está temporariamente fora do ar.`
        );
      }
      if (!resposta.ok) {
        throw new Error(`Erro ao obter token CSRF : HTTP ${resposta.status}`);
      }

      const dados = await resposta.json();
      if (!dados.token) {
        throw new Error("Token CSRF não retornado pelo servidor.");
      }

      return dados.token;
    }

    async function obterTokenCsrf() {
      const tokenExistente = getCookie("XSRF-TOKEN");
      return tokenExistente || (await obterNovoToken());
    }

    function exibirMensagemModal(mensagem, tipo = "info") {
      const modalEl = document.getElementById("modalMensagemSistema");
      if (!modalEl) return;

      const tituloEl = modalEl.querySelector(".modal-title");
      const corpoEl = modalEl.querySelector(".modal-body");

      if (tituloEl) tituloEl.textContent = "Aviso";
      //if (corpoEl) corpoEl.textContent = mensagem;  //corpoEl.innerHTML = mensagem;
      // insiro mensagem com tag html para quebra de linha <br> por isso
      // preciso usar innerHTML
      if (corpoEl) corpoEl.innerHTML = mensagem;

      const headerEl = modalEl.querySelector(".modal-header");
      if (headerEl) {
        headerEl.className = "modal-header";
        const tipoCor = {
          danger: "bg-danger text-white",
          warning: "bg-warning text-dark",
          success: "bg-success text-white",
          info: "bg-info text-white",
          primary: "bg-primary text-white",
        };
        headerEl.className += " " + (tipoCor[tipo] || "bg-warning text-dark");
      }

      const modal = new bootstrap.Modal(modalEl);
      modal.show();
    }

    function escapeHTML(text) {
      const div = document.createElement("div");
      div.textContent = text;
      return div.innerHTML;
    }

    function exibirMensagemModalComFoco(mensagem, tipo, campoAlvo) {
      if (!mensagem || !campoAlvo) return;

      const modalEl = document.getElementById("modalMensagemSistema");
      if (!modalEl) {
        console.warn("Modal de mensagem não encontrado.");
        return;
      }

      function handler() {
        campoAlvo.focus();
        modalEl.removeEventListener("hidden.bs.modal", handler);
      }

      modalEl.addEventListener("hidden.bs.modal", handler);

      FiberGuardian.Utils.exibirMensagemModal(mensagem, tipo);
    }

    async function tratarErroFetch(resposta, titulo = "Erro", campoAlvo) {
      let mensagem = "Erro inesperado ao processar a requisição.";
      console.groupCollapsed(`↪️ tratarErroFetch: status ${resposta.status}`);

      try {
        const contentType = resposta.headers.get("Content-Type") || "";
        console.log("📦 Content-Type:", contentType);

        if (contentType.includes("application/json")) {
          console.log("📥 Tentando parsear JSON da resposta...");
          const json = await resposta.json();
          console.log("✅ JSON recebido:", json);

          if (json?.userMessage) {
            mensagem = json.userMessage;
            console.log("🟢 Mensagem principal extraída:", mensagem);

            if (Array.isArray(json.errorObjects)) {
              const detalhes = json.errorObjects
                .map(
                  (err) =>
                    `Campo: ${escapeHTML(err.name)} - Problema: ${escapeHTML(
                      err.userMessage
                    )}`
                )
                .join("<br>");
              mensagem += "<br>" + detalhes;
            }
          } else if (resposta.status === 403) {
            mensagem =
              "Acesso negado. Sua sessão expirou ou você não tem permissão.";
            console.warn("⚠️ Erro 403 sem userMessage.");
          } else {
            console.warn("JSON válido mas sem userMessage.");
          }
        } else {
          console.warn(
            "⚠️ Resposta não é JSON. Tentando exibir texto bruto..."
          );
          const texto = await resposta.text();
          console.log("📄 Conteúdo da resposta:", texto);
          mensagem = `Erro ${resposta.status} - ${resposta.statusText}`;
        }
      } catch (e) {
        console.error("❌ Erro ao interpretar a resposta:", e);

        if (resposta.status === 403) {
          mensagem =
            "Acesso negado. Sua sessão expirou ou você não tem permissão.";
          console.warn("⚠️ Erro 403 capturado no catch.");
        }
      }

      console.log("📢 Mensagem final ao usuário:", mensagem);
      console.groupEnd();

      FiberGuardian.Utils.exibirMensagemModalComFoco(
        mensagem,
        "danger",
        campoAlvo
      );
    }

    function iniciarWatcherDeSessao() {
      iniciarMonitoramentoSessao();
    }

    async function realizarLogout() {
      try {
        const csrfToken = await obterTokenCsrf();
        const resp = await fetch("/api/fg-logout", {
          method: "POST",
          credentials: "include",
          headers: {
            "X-XSRF-TOKEN": csrfToken,
          },
        });

        if (!resp.ok) throw new Error("Erro ao encerrar sessão");

        sessionStorage.removeItem("usuario");
        window.location.href = "login.html";
      } catch (e) {
        exibirMensagemModal("Erro no logout: " + e.message, "danger");
      }
    }

    // Exporta apenas o necessário
    return {
      isEmailValido,
      obterTokenCsrf,
      obterNovoToken,
      iniciarWatcherDeSessao,
      exibirMensagem: exibirMensagemInterna,
      exibirMensagemModal,
      tratarErroFetch,
      realizarLogout,
      desabilitarEntradas,
      exibirMensagemModalComFoco,
    };
  })();
})();
