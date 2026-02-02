Dev-II (Entrega 1) — Locadora Full Stack (Spring Boot + React)

Aplicação full stack desenvolvida para a disciplina Desenvolvimento Web II.
O projeto possui um backend em Java/Spring Boot com API REST e um frontend em React (Vite) consumindo essa API.

Backend: Java 21, Spring Boot, Spring Data JPA, Validation, H2 (memória), Swagger/OpenAPI

Frontend: React (Vite), React Router, Axios, MUI (Material UI)

✨ Funcionalidades

CRUD de entidades da locadora (ex.: Atores, Diretores, Classes, Clientes, Títulos, Itens, Locações)

API REST organizada em camadas (Controller / Service / Repository)

Swagger/OpenAPI para documentação e testes dos endpoints

Banco H2 em memória (facilita rodar localmente sem instalar BD)

DataLoader para inserir dados de exemplo automaticamente (quando o banco está vazio)

Frontend com rotas e telas por módulo (menu superior)

📁 Estrutura de pastas
entrega_1/
  BackEnd/     -> Spring Boot (API REST)
  FrontEnd/    -> React + Vite (UI)

✅ Pré-requisitos
Backend

Java 21 (compatível com o build.gradle)

(Opcional) Gradle instalado — ou use o Gradle Wrapper (./gradlew)

Frontend

Node.js 18+ e npm

▶️ Como rodar o projeto

Recomendo abrir dois terminais: um para o backend e outro para o frontend.

1) Rodando o Backend (Spring Boot)

Entre na pasta do backend:

cd entrega_1/BackEnd


Execute com Gradle Wrapper:

./gradlew bootRun


O backend sobe em:

http://localhost:8081

2) Rodando o Frontend (React + Vite)

Em outro terminal, entre na pasta do frontend:

cd entrega_1/FrontEnd


Instale dependências e rode:

npm install
npm run dev


O frontend sobe em:

http://localhost:5173

🔌 Integração Front ↔ Back (Proxy)

O frontend utiliza Axios com baseURL: /api e o Vite está configurado com proxy para:

/api → http://localhost:8081

Isso permite chamar endpoints assim no front:

/api/clientes, /api/titulos, etc.

Sem precisar colocar a URL completa no código.

📚 Swagger / OpenAPI (Documentação da API)

Com o backend rodando, acesse:

Swagger UI: http://localhost:8081/swagger-ui.html

OpenAPI JSON: http://localhost:8081/v3/api-docs

🗄️ Banco H2 (Console)

O projeto usa H2 em memória:

H2 Console: http://localhost:8081/h2-console

Configuração (padrão do projeto):

JDBC URL: jdbc:h2:mem:locadora

User: sa

Password: (vazio)

🧭 Endpoints principais

Base paths expostos (REST):

/api/atores

/api/diretores

/api/classes

/api/clientes

/api/titulos

/api/itens

/api/locacoes

(também existem) /api/socios e /api/dependentes

Para ver todos os endpoints e testar requests, use o Swagger UI.

🧪 Testes / Build (opcional)
Rodar testes do backend
cd entrega_1/BackEnd
./gradlew test

Build do frontend
cd entrega_1/FrontEnd
npm run build
npm run preview

🛠️ Troubleshooting rápido

Erro de porta ocupada (8081 ou 5173):

Feche o processo usando a porta, ou altere em:

Backend: BackEnd/src/main/resources/application.yml (server.port)

Frontend: FrontEnd/vite.config.js (server.port)

Frontend não chama o backend:

Confirme se o backend está rodando em http://localhost:8081

Confirme o proxy no vite.config.js e as chamadas em /api

📌 Observação

O backend inclui um DataLoader que insere dados de exemplo quando o banco está vazio.
Isso ajuda a testar rapidamente a aplicação sem precisar cadastrar tudo manualmente.
