#!/bin/bash

echo "==> 1. Obtendo token CSRF..."
CSRF_RESPONSE=$(curl -sv -k -c cookies.txt https://localhost:8443/fiberguardian/csrf-token 2>&1)

echo -e "\nResposta completa da requisição de token CSRF:"
echo "$CSRF_RESPONSE"

echo -e "\nConteúdo do arquivo cookies.txt:"
cat cookies.txt

# Extrai o token CSRF do JSON no body (assumindo resposta JSON padrão)
CSRF_TOKEN=$(echo "$CSRF_RESPONSE" | grep -oP '"token":"\K[^"]+')

echo -e "\nToken CSRF extraído: $CSRF_TOKEN"

echo "==> 2. Efetuando login..."
curl -sv -k -b cookies.txt -c cookies.txt \
  -X POST https://localhost:8443/fiberguardian/login \
  -H "Content-Type: application/json" \
  -H "X-XSRF-TOKEN: $CSRF_TOKEN" \
  -d '{"email": "ana.souza@fiberguardian.com", "senha": "senha123"}'

echo -e "\nFim do script."
