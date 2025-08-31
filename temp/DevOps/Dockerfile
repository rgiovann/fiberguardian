# Etapa de build (só para copiar o JAR)
# precisa do jar file estar em /target

FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY target/*.jar app.jar

# Etapa final: runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/app.jar app.jar

# Área de storage de PDFs
VOLUME ["/app/storage/pdf"]

# Porta HTTPS exposta
EXPOSE 8443

ENTRYPOINT ["java", "-jar", "app.jar"]
