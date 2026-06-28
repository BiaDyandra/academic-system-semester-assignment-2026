<a id="top"></a>

# Sistema Acadêmico

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Docker](https://img.shields.io/badge/Docker-habilitado-blue)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-habilitado-blue)
![Status](https://img.shields.io/badge/status-concluído-green)

Sistema Acadêmico é uma aplicação Java desenvolvida como trabalho semestral da disciplina de Orientação a Objetos ministrada pelo professor Rodrigo Martins Pagliares no curso de Bacharelado em Ciência da Computação da Universidade Federal de Alfenas (UNIFAL-MG).

O sistema suporta o gerenciamento de turmas e avaliações acadêmicas, exercitando de forma incremental conceitos de programação orientada a objetos, engenharia de software, testes, logging, segurança, persistência, containerização, desenvolvimento de interface gráfica e práticas de CI/CD.

---

## Equipe

| Nome | GitHub |
|------|--------|
| Bianca Dyandra | [@BiaDyandra](https://github.com/BiaDyandra) |
| Gabriela Mazon | — |
| Leticia Luthor | — |
| Isabela Mageste | — |

---

## Índice

- [Visão Geral](#visão-geral)
- [Objetivos do Projeto](#objetivos-do-projeto)
- [Principais Conceitos Praticados](#principais-conceitos-praticados)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Modelo de Segurança](#modelo-de-segurança)
- [Estrutura do Repositório](#estrutura-do-repositório)
- [Como Baixar e Rodar](#como-baixar-e-rodar)
- [Como Testar](#como-testar)
- [Modelo de Domínio](#modelo-de-domínio)
- [Evolução da Persistência](#evolução-da-persistência)
- [Funcionalidades de Relatório](#funcionalidades-de-relatório)
- [Roadmap de Histórias de Usuário](#roadmap-de-histórias-de-usuário)
- [Melhorias Futuras](#melhorias-futuras)
- [Finalidade Educacional](#finalidade-educacional)

---

<a id="visão-geral"></a>

## Visão Geral

O Sistema Acadêmico permite que professores e administradores gerenciem turmas e avaliações por meio de uma interface de linha de comando e uma interface gráfica JavaFX.

O projeto evoluiu de forma incremental por meio de histórias de usuário inspiradas em metodologias ágeis. Cada história de usuário introduz ou refina uma funcionalidade específica, decisão arquitetural, conceito de orientação a objetos, mecanismo de persistência, prática de segurança, testes ou capacidade de implantação.

O sistema suporta atualmente:

- Cadastro de turmas acadêmicas
- Cadastro de avaliações
- Persistência em TXT, XML e JSON
- Estratégias de persistência configuráveis
- Autenticação e autorização
- Controle de Acesso Baseado em Papéis (RBAC)
- Gerenciamento de sessão e logout
- Menus de linha de comando específicos por papel
- Validação de domínio
- Tratamento de exceções
- Geração de relatórios
- Logging de aplicação e auditoria
- Testes automatizados com JUnit Jupiter e Mockito
- Execução em contêiner Docker
- Telas JavaFX
- Workflows do GitHub Actions
- Validação de pull requests e proteção de branch

[↑ Voltar ao topo](#top)

---

<a id="objetivos-do-projeto"></a>

## Objetivos do Projeto

- Praticar conceitos de programação orientada a objetos
- Exercitar os princípios SOLID
- Aplicar conceitos de engenharia de software de forma incremental
- Simular a evolução ágil de software por meio de histórias de usuário
- Praticar estratégias de persistência
- Explorar conceitos de arquitetura em camadas
- Implementar mecanismos de autenticação e autorização
- Aplicar técnicas de validação e tratamento de exceções
- Melhorar a manutenibilidade por meio de refatoração
- Desenvolver habilidades em testes automatizados
- Explorar práticas de logging e auditoria
- Explorar estratégias de implantação e containerização
- Introduzir o desenvolvimento de interface gráfica com JavaFX
- Introduzir automação de CI/CD com GitHub Actions

[↑ Voltar ao topo](#top)

---

<a id="principais-conceitos-praticados"></a>

## Principais Conceitos Praticados

- Abstração, Herança, Polimorfismo, Encapsulamento
- Associação entre objetos e identidade de objetos
- Igualdade com equals e hashCode
- Padrões de projeto: Singleton, Repository, Strategy
- Autenticação, Autorização e RBAC
- Hierarquias de exceções
- Jakarta Bean Validation
- Separação inspirada em MVC e arquitetura em camadas
- Persistência em XML, JSON e TXT
- Geração de relatórios
- Renderização de menu baseada em papel
- Desenvolvimento de GUI com JavaFX
- Lombok
- Testes automatizados com JUnit Jupiter e Mockito
- Logging de aplicação e auditoria
- Docker e GitHub Actions

[↑ Voltar ao topo](#top)

---

<a id="tecnologias"></a>

## Tecnologias

| Tecnologia | Finalidade |
|------------|-----------|
| Java SE 17 | Linguagem de programação principal |
| Maven | Gerenciamento de dependências e automação de build |
| Lombok | Redução de código boilerplate |
| Jakarta Bean Validation | Validação de domínio |
| JUnit Jupiter | Testes unitários automatizados |
| Mockito | Framework de mocks para testes |
| SLF4J + Logback | API e implementação de logging |
| XML / JSON / TXT | Formatos de persistência |
| JavaFX | Interface gráfica do usuário |
| Docker | Entrega da aplicação em contêiner |
| GitHub Actions | Automação de CI/CD |

[↑ Voltar ao topo](#top)

---

<a id="arquitetura"></a>

## Arquitetura

O sistema segue uma arquitetura em camadas que promove separação de responsabilidades, manutenibilidade e extensibilidade.

Componentes arquiteturais principais:

- Camada de Modelo de Domínio
- Camada de Persistência
- Camada de Segurança
- Camada de Validação
- Camada de Serviços
- Camada de Controller
- Camada de Interface com o Usuário
- Camada de Logging
- Infraestrutura de Testes
- Camada de Automação CI/CD

[↑ Voltar ao topo](#top)

---

<a id="modelo-de-segurança"></a>

## Modelo de Segurança

O sistema implementa Controle de Acesso Baseado em Papéis (RBAC), autenticação baseada em sessão, verificações de autorização e auditoria de segurança por meio de logging.

### Papéis Suportados

| Papel | Permissões Principais |
|-------|----------------------|
| ADMIN | Cadastrar turmas, salvar dados, configurar persistência, gerar relatórios administrativos |
| PROFESSOR | Cadastrar avaliações, gerar relatórios acadêmicos, visualizar dados |

### Credenciais de Teste

| Usuário | Senha | Papel |
|---------|-------|-------|
| admin | admin123 | ADMIN |
| professor | prof123 | PROFESSOR |

[↑ Voltar ao topo](#top)

---

<a id="estrutura-do-repositório"></a>

## Estrutura do Repositório

```
.
├── .github
│   └── workflows
│       ├── ci.yml
│       ├── docker-publish.yml
│       ├── pr-validation.yml
│       └── release.yml
├── academic-system
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── org.example.academic.system
│   │   │   │       ├── controller
│   │   │   │       ├── exception
│   │   │   │       ├── model
│   │   │   │       ├── repository
│   │   │   │       ├── security
│   │   │   │       ├── service
│   │   │   │       ├── validation
│   │   │   │       └── view
│   │   │   └── resources
│   │   │       └── logback.xml
│   │   └── test
│   ├── Dockerfile
│   └── pom.xml
└── README.md
```

[↑ Voltar ao topo](#top)

---

<a id="como-baixar-e-rodar"></a>

## Como Baixar e Rodar

### Requisitos

- Java 17 ou superior
- Maven 3.9 ou superior
- Docker (opcional)

### Clonar o Repositório

```bash
git clone https://github.com/BiaDyandra/academic-system-semester-assignment-2026.git
cd academic-system-semester-assignment-2026/academic-system
```

### Compilar o Projeto

```bash
mvn clean install
```

### Rodar a Interface de Linha de Comando

```bash
mvn exec:java
```

### Rodar a Interface Gráfica (JavaFX)

```bash
mvn javafx:run
```

### Rodar com Docker

```bash
docker build -t academic-system .
docker run -it academic-system
```

[↑ Voltar ao topo](#top)

---

<a id="como-testar"></a>

## Como Testar

### Testes Automatizados

```bash
mvn test
```

Os testes cobrem: validação de domínio, autenticação, autorização, repositórios de persistência, serviços e controller.

### Testes Manuais da Interface Gráfica

Execute `mvn javafx:run` e siga os passos abaixo:

**1. Login inválido**
- Digite usuário/senha incorretos → mensagem de erro vermelha deve aparecer

**2. Login como ADMIN** (`admin` / `admin123`)
- Tela principal deve exibir: Cadastrar Turma, Configurar Persistência, Salvar Dados, Cadastrar Avaliação, Relatórios, Visualizar, Logout

**3. Login como PROFESSOR** (`professor` / `prof123`)
- Tela principal NÃO deve exibir: Cadastrar Turma, Configurar Persistência, Salvar Dados

**4. Cadastrar Turma** (ADMIN)
- Preencha código e nome → mensagem verde de sucesso deve aparecer

**5. Cadastrar Avaliação**
- Informe código de turma existente, nome e peso (ex: 0.4) → mensagem verde de sucesso

**6. Visualizar Turmas e Avaliações**
- Lista de turmas e avaliações cadastradas deve aparecer

**7. Relatórios**
- Cada botão de relatório deve exibir o conteúdo correspondente na tela

**8. Logout**
- Deve retornar à tela de login

### Testes Manuais da Linha de Comando

Execute `mvn exec:java`, faça login com `admin`/`admin123` e navegue pelo menu numérico para testar cada operação.

[↑ Voltar ao topo](#top)

---

<a id="modelo-de-domínio"></a>

## Modelo de Domínio

O sistema modela as principais entidades do gerenciamento acadêmico:

- Turmas acadêmicas
- Avaliações (com nome e peso)
- Usuários com papéis (ADMIN e PROFESSOR)

[↑ Voltar ao topo](#top)

---

<a id="evolução-da-persistência"></a>

## Evolução da Persistência

| Versão | Tipo de Persistência |
|--------|---------------------|
| v1 | Somente console |
| v2 | TXT |
| v3 | XML |
| v4 | JSON |

[↑ Voltar ao topo](#top)

---

<a id="funcionalidades-de-relatório"></a>

## Funcionalidades de Relatório

- Relatório de resumo de avaliações por turma
- Relatório de validação de peso das avaliações
- Relatório de configuração de persistência (somente ADMIN)

[↑ Voltar ao topo](#top)

---

<a id="roadmap-de-histórias-de-usuário"></a>

## Roadmap de Histórias de Usuário

### Funcionalidades Acadêmicas

| ID | História de Usuário | Status |
|----|---------------------|--------|
| US-2361 | Cadastrar avaliações em turmas | ✅ |
| US-2363 | Cadastrar turmas por entrada de teclado | ✅ |
| US-2364 | Gerenciar sistema acadêmico por menu de linha de comando | ✅ |
| US-2375 | Gerar relatório de resumo de avaliações por turma | ✅ |
| US-2376 | Gerar relatório de peso das avaliações | ✅ |

### Funcionalidades de Persistência

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2362 | Persistir avaliações em arquivo TXT | ✅ |
| US-2372 | Configurar tipo de persistência como administrador | ✅ |
| US-2373 | Salvar dados acadêmicos em arquivo XML | ✅ |
| US-2374 | Salvar dados acadêmicos em arquivo JSON | ✅ |
| US-2377 | Gerar relatório de configuração de persistência | ✅ |

### Funcionalidades de Segurança

| ID | História de Usuário | Status |
|----|---------------------|--------|
| US-2366 | Autenticar usuários e autorizar ações por papel | ✅ |
| US-2369 | Tratar erros de autenticação e autorização com exceções customizadas | ✅ |
| US-2378 | Renderização dinâmica de menu baseada em papel | ✅ |
| US-2379 | Logout | ✅ |
| US-2380 | Exibir menus sequenciais específicos por papel | ✅ |

### Validação e Tratamento de Exceções

| ID | História de Usuário | Status |
|----|---------------------|--------|
| US-2367 | Tratar erros de domínio acadêmico com exceções customizadas | ✅ |
| US-2368 | Tratar erros de entrada de teclado com exceções customizadas | ✅ |
| TUS-2371 | Validar objetos de domínio com Jakarta Bean Validation | ✅ |

### Arquitetura e Refatoração

| ID | História de Usuário | Status |
|----|---------------------|--------|
| US-0000 | Iniciar sistema acadêmico | ✅ |
| TUS-2365 | Refatorar modelo de domínio com Lombok | ✅ |
| TUS-2370 | Refatorar operações de menu para AcademicSystemController | ✅ |
| TUS-2382 | Definir igualdade para objetos de domínio identificáveis | ✅ |
| TUS-2396 | Introduzir ClassService | ✅ |
| TUS-2397 | Introduzir AssessmentService | ✅ |
| TUS-2398 | Introduzir PersistenceService | ✅ |
| TUS-2399 | Introduzir ReportService | ✅ |
| TUS-2400 | Simplificar AcademicSystemController | ✅ |
| TUS-2414 | Introduzir AuthenticationController para login JavaFX | ✅ |

### Docker e Implantação

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2381 | Entregar sistema acadêmico com Docker | ✅ |

### Infraestrutura de Testes e Testes Automatizados

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2383 | Configurar infraestrutura de testes automatizados | ✅ |
| TUS-2384 | Testar igualdade de objetos de domínio identificáveis | ✅ |
| TUS-2385 | Testar validação de domínio acadêmico | ✅ |
| US-2386 | Testar comportamento de autenticação | ✅ |
| US-2387 | Testar comportamento de autorização | ✅ |
| US-2388 | Testar geração de relatórios | ✅ |
| US-2389 | Testar repositórios de persistência | ✅ |
| TUS-2395 | Verificar comportamento da infraestrutura de logging | ✅ |
| TUS-2401 | Testar comportamento do ClassService | ✅ |
| TUS-2402 | Testar comportamento do AssessmentService | ✅ |
| TUS-2403 | Testar comportamento do PersistenceService | ✅ |
| TUS-2404 | Testar comportamento do ReportService | ✅ |
| TUS-2405 | Testar comportamento de delegação do AcademicSystemController | ✅ |

### Logging e Auditoria

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2390 | Configurar infraestrutura de logging | ✅ |
| TUS-2391 | Registrar eventos de autenticação e logout | ✅ |
| TUS-2392 | Registrar falhas de autorização | ✅ |
| TUS-2393 | Registrar operações de persistência | ✅ |
| TUS-2394 | Registrar geração de relatórios | ✅ |

### Interface Gráfica (JavaFX)

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2406 | Configurar infraestrutura JavaFX | ✅ |
| TUS-2407 | Criar tela de login JavaFX | ✅ |
| TUS-2408 | Criar tela principal JavaFX baseada em papel | ✅ |
| TUS-2409 | Criar tela de cadastro de turmas JavaFX | ✅ |
| TUS-2410 | Criar tela de cadastro de avaliações JavaFX | ✅ |
| TUS-2411 | Criar tela de relatórios JavaFX | ✅ |
| TUS-2412 | Criar tela de configuração de persistência JavaFX | ✅ |
| TUS-2413 | Criar tela de visualização de turmas e avaliações JavaFX | ✅ |

### CI/CD e Automação

| ID | História de Usuário | Status |
|----|---------------------|--------|
| TUS-2415 | Configurar pipeline de CI com GitHub Actions | ✅ |
| TUS-2416 | Gerar relatórios de cobertura de testes | ✅ |
| TUS-2417 | Publicar imagem Docker automaticamente | ✅ |
| TUS-2418 | Configurar workflow de validação de pull requests | ✅ |
| TUS-2419 | Configurar workflow de release | ✅ |
| TUS-2420 | Configurar proteção de branch para pull requests | ✅ |

[↑ Voltar ao topo](#top)

---

<a id="melhorias-futuras"></a>

## Melhorias Futuras

- API REST
- Persistência em banco de dados
- Testes de integração
- Migração para Spring Boot
- Persistência com Hibernate/JPA
- Frontend web
- Gerenciamento avançado de papéis
- Gerenciamento de estudantes
- Cálculo de notas e relatórios de nota final
- Criptografia de senhas
- Exportação de relatórios para PDF e Excel
- Suporte a múltiplos usuários simultâneos
- Implantação em nuvem

[↑ Voltar ao topo](#top)

---

<a id="finalidade-educacional"></a>

## Finalidade Educacional

Este projeto foi desenvolvido para fins educacionais no contexto da disciplina de Orientação a Objetos do curso de Ciência da Computação da UNIFAL-MG, abordando na prática: programação orientada a objetos, engenharia de software ágil, princípios SOLID, refatoração, arquitetura em camadas, persistência, segurança, logging, testes automatizados, containerização, desenvolvimento de GUI e automação de CI/CD.

[↑ Voltar ao topo](#top)
