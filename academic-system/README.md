# Academic System - US-0000 até US-2375

Esta versão contém as histórias implementadas até a **US-2375 - Generate class assessment summary report**.

## Histórias incluídas

- US-0000 - Start academic system
- US-2361 - Cadastrar avaliações em turmas
- TUS-2362 - Persistir avaliações em arquivo TXT
- US-2363 - Cadastrar turmas por entrada do teclado
- US-2364 - Gerenciar sistema por menu de linha de comando
- US-2365 - Refatorar modelo de domínio usando Lombok
- US-2366 - Autenticar usuários e autorizar ações por papéis
- US-2367 - Erros do domínio acadêmico com exceções customizadas
- US-2368 - Erros de entrada pelo teclado com exceções customizadas
- US-2369 - Erros de autenticação/autorização com exceções customizadas
- TUS-2370 - Refatorar operações do menu para AcademicSystemController
- TUS-2371 - Validar objetos do domínio com Jakarta Bean Validation
- US-2372 - Configurar tipo de persistência como administrador
- US-2373 - Salvar dados acadêmicos em XML
- US-2374 - Salvar dados acadêmicos em JSON
- US-2375 - Gerar relatório resumo das avaliações por turma

## Novidade desta etapa

Foi adicionada a classe:

```text
src/main/java/org/example/academic/system/service/ReportService.java
```

Também foi adicionada a opção no menu:

```text
7 - Gerar relatorio resumo de avaliacoes por turma
```

O relatório mostra:

```text
- total de turmas cadastradas;
- total de avaliações cadastradas;
- código e nome de cada turma;
- quantidade de avaliações por turma;
- peso total das avaliações da turma;
- lista das avaliações cadastradas em cada turma.
```

## Usuários para teste

```text
ADMIN
Usuário: admin
Senha: admin123
```

```text
PROFESSOR
Usuário: professor
Senha: prof123
```

## Como testar

Compile o projeto:

```bash
mvn clean compile
```

Execute:

```bash
java -cp target/classes org.example.academic.system.Main
```

Fluxo sugerido:

```text
Usuário: admin
Senha: admin123

2 - Cadastrar turma
Código: POO2026
Nome: Orientação a Objetos

3 - Cadastrar avaliação em uma turma
Código da turma: POO2026
Nome da avaliação: Prova 1
Peso da avaliação: 4

3 - Cadastrar avaliação em uma turma
Código da turma: POO2026
Nome da avaliação: Trabalho
Peso da avaliação: 2

7 - Gerar relatorio resumo de avaliacoes por turma
```

Saída esperada parecida com:

```text
===== Relatorio Resumo de Avaliacoes por Turma =====
Total de turmas cadastradas: 1
Total de avaliacoes cadastradas: 2

Turma: POO2026 - Orientação a Objetos
Quantidade de avaliacoes: 2
Peso total: 6.00
Avaliacoes:
  1. Prova 1 | Peso: 4.00
  2. Trabalho | Peso: 2.00
```

## Observação

A geração do relatório ficou em `ReportService`, mantendo a responsabilidade de relatório fora do menu e fora do controller. O menu apenas lê a opção do usuário, o controller delega a ação, e o service monta o texto do relatório.

## Docker

Para construir a imagem:
```bash
docker build -t academic-system .
```

Para executar o container (interativo):
```bash
docker run -it academic-system
```