# Cash Machine - Simulador de Caixa Eletrônico Multibanco

O **Cash Machine** é uma aplicação de console desenvolvida em Java que simula o funcionamento de um terminal de caixa eletrônico multibanco. O grande diferencial do projeto é a simulação de um **Banco de Dados Relacional utilizando arquivos flat (CSV)**, garantindo persistência de dados, controle de chaves estrangeiras (`FK`) e gerador de chaves primárias (`PK`) via arquivos de sequência, além de segurança criptográfica para senhas.

## 🚀 Funcionalidades

1. **Seleção de Banco**: O usuário escolhe em qual instituição financeira deseja operar (dados pré-existentes e protegidos contra escrita).
2. **Cadastro de Titular (User)**: Fluxo de criação de dados pessoais no ecossistema.
3. **Abertura de Conta (Bank Account)**: Criação de conta corrente vinculada ao banco escolhido com geração automática de Agência e Conta, exigindo e-mail e senha.
4. **Segurança**: Armazenamento de senhas utilizando Hash Criptográfico (**BCrypt**).
5. **Autenticação**: Login seguro com mascaramento de senha no console (`System.console().readPassword()`).
6. **Operações Bancárias**:
   * Consulta de Saldo em tempo real.
   * Depósito.
   * Saque (com validação de saldo).
   * Transferência entre contas correntes via arquivo CSV.
7. **Logout**: Encerramento seguro da sessão atual.

---

## 🏗️ Arquitetura do Projeto

O sistema foi desenhado seguindo os padrões de arquitetura em camadas (**MVC** e **Repository Pattern**), isolando completamente as responsabilidades:

* **`domain.model`**: Entidades puras do sistema mapeadas via anotações do OpenCSV (`User`, `Bank`, `BankAccount`).
* **`repository`**: Camada responsável pelo isolamento de I/O com os arquivos CSV. Contém repositórios CRUD e sub-repositórios de Sequence para simular o auto-incremento de IDs.
* **`service`**: Concentra 100% das regras de negócio, validações de saldo, transferências e geração de dados.
* **`controller`**: Intermediário que recebe as requisições das Views, aciona os Services e gerencia os DTOs.
* **`controller.dto`**: Records imutáveis (`Request` / `Response`) para tráfego seguro de dados entre camadas.
* **`security`**: Componente de codificação de senhas com BCrypt.
* **`view`**: Interface de linha de comando (CLI) focada na interação com o usuário.

---

## 📂 Estrutura de Diretórios e Banco de Dados (CSV)

```text
├── data/                               # Tabela de entidades
│   ├── bank.csv                  
│   ├── bank_account.csv          
│   └── user.csv                  
├── sequence/                           # Tabela de sequences, o auto-incremento de ID's das entidades
│   ├── bank_account_sequence.csv
│   └── user_sequence.csv
└── src/
    └── main/java/
        ├── common/                     # Constantes e strings globais do sistema
        ├── controller/                 # Camada de controle de fluxo e DTOs
        ├── domain/                     # Entidades de Modelo e Interfaces
        ├── repository/                 # Persistência e leitura/escrita OpenCSV
        ├── service/                    # Regras de negócio
        ├── security/                   # Criptografia de senhas (BCrypt)
        ├── view/                       # Interface CLI (Telas do terminal)
        └── CashMachineApplication.java # Classe raiz (Método Main)
