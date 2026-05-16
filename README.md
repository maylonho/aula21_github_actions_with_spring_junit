# Guia Completo — CI/CD com Spring Boot, JUnit, Github Actions e Docker Hub

## Objetivo

Neste guia vamos configurar:

- Projeto Spring Boot
- Testes com JUnit
- Docker
- Github Actions
- Docker Hub
- Docker Compose

Fluxo final:

```txt
Push no Github
      ↓
Github Actions
      ↓
JUnit
      ↓
Build Maven
      ↓
Docker Build
      ↓
Push Docker Hub
      ↓
Servidor faz pull da imagem
      ↓
Docker Compose sobe aplicação
```

---

# 1. Estrutura do Projeto

Exemplo:

```txt
projeto/
 ├── src/
 ├── pom.xml
 ├── Dockerfile
 ├── docker-compose.yml
 └── .github/
      └── workflows/
           └── ci.yml
```

---

# 2. Dockerfile

Crie um arquivo chamado:

```txt
Dockerfile
```

Conteúdo:

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# 3. Testando Docker Localmente

Gerar o JAR:

```bash
mvn clean package
```

Build da imagem:

```bash
docker build -t aula_iec_automacao:latest .
```

Executar:

```bash
docker run -p 8080:8080 aula_iec_automacao:latest
```

---

# 4. Criando Conta no Docker Hub

Acesse:

https://hub.docker.com

Crie sua conta.

Exemplo:

```txt
Usuário: maylonho
```

---

# 5. Gerando Token do Docker Hub

Acesse:

```txt
Docker Hub
  → Account Settings
      → Security
          → New Access Token
```

Exemplo:

```txt
Name: github-actions
Permissions: Read, Write, Delete
```

Copie o token gerado.

---

# 6. Configurando Secrets no Github

Entre no repositório do Github.

Depois:

```txt
Repository
  → Settings
      → Secrets and variables
          → Actions
```

Clique em:

```txt
New repository secret
```

---

## Secret 1

### Name

```txt
DOCKER_USERNAME
```

### Secret

Seu usuário do Docker Hub.

Exemplo:

```txt
maylonho
```

---

## Secret 2

### Name

```txt
DOCKER_PASSWORD
```

### Secret

Cole o token gerado no Docker Hub.

Exemplo:

```txt
dckr_pat_xxxxxxxxxxxxx
```

---

# 7. Github Actions Workflow

Crie:

```txt
.github/workflows/ci.yml
```

Conteúdo:

```yaml
name: Continuous Integration with Github Actions

on:
  push:
    branches:
      - main

jobs:

  build:
    runs-on: ubuntu-latest

    steps:

      - name: Checkout do código
        uses: actions/checkout@v4

      - name: Configurar JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: maven

      - name: Run Tests
        run: mvn test

      - name: Build Application JAR
        run: mvn clean package -DskipTests

      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build Docker Image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/aula_iec_automacao:latest .

      - name: Tag Docker Image
        run: |
          docker tag ${{ secrets.DOCKER_USERNAME }}/aula_iec_automacao:latest \
          ${{ secrets.DOCKER_USERNAME }}/aula_iec_automacao:${{ github.run_id }}

      - name: Push Docker Images
        run: |
          docker push ${{ secrets.DOCKER_USERNAME }}/aula_iec_automacao:latest

          docker push ${{ secrets.DOCKER_USERNAME }}/aula_iec_automacao:${{ github.run_id }}
```

---

# 8. Funcionamento do Workflow

Toda vez que fizer:

```bash
git push
```

O Github Actions irá:

```txt
1. Fazer checkout do código
2. Configurar Java 21
3. Rodar testes JUnit
4. Gerar JAR Maven
5. Fazer login no Docker Hub
6. Gerar imagem Docker
7. Publicar imagem no Docker Hub
```

---

# 9. Docker Compose

Crie:

```txt
docker-compose.yml
```

Conteúdo:

```yaml
version: '3.9'

services:

  db:
    image: mysql:8.0.29

    command: mysqld --default-authentication-plugin=mysql_native_password

    restart: always

    environment:
      TZ: America/Sao_Paulo
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_USER: docker
      MYSQL_PASSWORD: 123456
      MYSQL_DATABASE: aula_iec_automacao
      MYSQL_ROOT_HOST: '%'
      MYSQL_TCP_PORT: 3306

    ports:
      - 3307:3306

    expose:
      - 3306

    networks:
      - oliveira-network

  aula_iec_automacao:
    image: maylonho/aula_iec_automacao:latest

    restart: always

    environment:
      TZ: America/Sao_Paulo
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/aula_iec_automacao?useTimezone=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123456

    ports:
      - 81:8080

    depends_on:
      - db

    networks:
      - oliveira-network

networks:
  oliveira-network:
    driver: bridge
```

---

# 10. Subindo Aplicação

Executar:

```bash
docker compose up -d
```

---

# 11. Acessando Aplicação

Aplicação:

```txt
http://localhost:81
```

Banco MySQL:

```txt
localhost:3307
```

---

# 12. Atualizando Aplicação

Quando uma nova imagem for publicada:

```bash
docker compose pull

docker compose up -d
```

---

# 13. Conceitos Aprendidos

Este projeto utiliza:

- CI/CD
- DevOps
- Github Actions
- Docker
- Docker Compose
- Maven
- JUnit
- Spring Boot
- Docker Hub
- Integração Contínua
- Deploy Automatizado
- Containers
- Redes Docker

---

# 14. Fluxo Profissional

```txt
Desenvolvedor faz push
          ↓
Github Actions executa pipeline
          ↓
JUnit executa testes
          ↓
Maven gera JAR
          ↓
Docker cria imagem
          ↓
Imagem vai para Docker Hub
          ↓
Servidor baixa imagem
          ↓
Docker Compose sobe containers
```

---

# 15. Comandos Úteis

## Ver containers

```bash
docker ps
```

## Ver logs

```bash
docker compose logs
```

## Parar containers

```bash
docker compose down
```

## Remover volumes

```bash
docker compose down -v
```

## Rebuild completo

```bash
docker compose up --build
```

---

# 16. Observações Importantes

## Dentro do Docker

Containers se comunicam pelo nome do serviço.

Exemplo:

```txt
db:3306
```

---

## Fora do Docker

Acesso usando localhost.

Exemplo:

```txt
localhost:3307
```

---

## Variáveis do Spring

No Docker Compose prefira:

```txt
SPRING_DATASOURCE_URL
```

em vez de:

```txt
SPRING.DATASOURCE.URL
```

---

# Final

Com essa estrutura você possui um ambiente completo de:

```txt
Spring Boot + JUnit + Docker + Github Actions + Docker Hub + Docker Compose
```
