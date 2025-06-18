(function () {
    window.FiberGuardian = window.FiberGuardian || {};

    FiberGuardian.TelaLogin = {
        // Propriedades para armazenar tokens
        csrfToken: null,
        sessionId: null,

        /**
         * Obtém o token CSRF do cookie ou via endpoint.
         * @returns {Promise<string|null>} O token CSRF ou null em caso de erro.
         */
        getCsrfToken: async function () {
            try {
                // Tenta obter o token CSRF do cookie XSRF-TOKEN
                const cookie = document.cookie
                    .split('; ')
                    .find(row => row.startsWith('XSRF-TOKEN='));
                if (cookie) {
                    this.csrfToken = cookie.split('=')[1];
                    return this.csrfToken;
                }

                // Caso o cookie não esteja disponível, faz uma requisição ao endpoint
                const response = await fetch('https://localhost:8080/fiberguardian/csrf-token', {
                    method: 'GET',
                    credentials: 'include' // Inclui cookies para JSESSIONID
                });

                if (response.ok) {
                    const data = await response.json();
                    this.csrfToken = data._csrf; // Assume que o endpoint retorna o token no campo _csrf
                    return this.csrfToken;
                } else {
                    console.error('Erro ao obter token CSRF:', response.statusText);
                    return null;
                }
            } catch (error) {
                console.error('Erro na requisição do token CSRF:', error);
                return null;
            }
        },

        /**
         * Realiza o login enviando email e senha ao backend.
         * @param {string} email - Email do usuário.
         * @param {string} senha - Senha do usuário.
         * @returns {Promise<object>} Dados da resposta do login.
         * @throws {Error} Em caso de erro na autenticação.
         */
        login: async function (email, senha) {
            try {
                // Garante que o token CSRF esteja disponível
                if (!this.csrfToken) {
                    await this.getCsrfToken();
                }

                if (!this.csrfToken) {
                    throw new Error('Token CSRF não disponível');
                }

                const response = await fetch('https://localhost:8080/fiberguardian/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-XSRF-TOKEN': this.csrfToken // Envia o token CSRF
                    },
                    body: JSON.stringify({ email, senha }),
                    credentials: 'include' // Inclui JSESSIONID
                });

                if (response.ok) {
                    const data = await response.json();
                    this.sessionId = data.sessionId; // Armazena JSESSIONID
                    this.csrfToken = data.csrfToken || this.csrfToken; // Atualiza o token CSRF, se retornado
                    console.log('Login bem-sucedido:', data);
                    return data;
                } else {
                    const error = await response.json();
                    console.error('Erro no login:', error.error);
                    throw new Error(error.error);
                }
            } catch (error) {
                console.error('Erro na requisição de login:', error);
                throw error;
            }
        }
    };
})();