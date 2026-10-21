  # Arcondicionado Lima
---

| | |
|---|---|
| **Aluno(a)** | Ana Vitória de Lima e Silva |
| **Turma** | TEC-N-001788/2026 |
| **Opção escolhida** |· Agendamento de Serviços · |
| **Versão atual** | 0.1.0 |

---

## 1. Visão geral

### 1.1 Problema
<!-- Que problema o sistema resolve? Quem sofre com esse problema hoje e como ele é resolvido (planilha, papel, WhatsApp...)? 3 a 5 linhas. -->

### 1.2 Canvas do projeto

| Bloco | Resposta |
|---|---|
| **Usuários** (quem usa o sistema) | Clientes, Atendentes, Técnicos e Administrador |
| **Problema** (dor atual) | Controle manual em planilhas e WhatsApp → conflito de horários, confusão entre os tipos de serviço, dificuldade de acompanhar atendimentos |
| **Proposta de valor** (o que melhora com o sistema) | Agenda unificada; validação automática de horários; cadastro fixo dos 3 serviços; cálculo de duração e valor; histórico e relatórios |
| **Funcionalidades principais** |Agendamento de Instalação, Manutenção e Limpeza; verificação de disponibilidade; agenda por técnico; cancelamento com registro; relatórios |
| **Informações que o sistema guarda** | Usuários; serviços (nome, preço, duração); agendamentos (cliente, técnico, serviço, data, horários, endereço, status) |
| **Indicadores** (o que o gestor quer acompanhar) |Quantidade e faturamento por serviço; taxa de cancelamento; atendimentos por técnico; status dos agendamentos |
| **Restrições** (prazo, tecnologia, equipe) | Projeto individual · Java 21 · Spring Boot 4 · entrega v1.0 em 11/11 |

---

## 2. Requisitos

### 2.1 Requisitos funcionais (o que o sistema FAZ)

| ID | Requisito | Nível |
|---|---|---|
| RF01 |O sistema deve permitir selecionar os serviços: Instalação, Manutenção e Limpeza, cada um com preço e duração próprios.|	Essencial |
| RF02 |O sistema deve permitir agendar um serviço informando cliente, técnico, tipo de serviço, data, horário e endereço.| Essencial
| RF03 | O sistema deve bloquear agendamento se o horário já estiver ocupado para o mesmo técnico.| Essencial|
| RF04 | O sistema deve exibir a agenda diária/semanal com distinção visual entre Instalação, Manutenção e Limpeza.|	Importante|
| RF05 | Permitir cancelar ou reagendar, registrando responsável e motivo.|	Importante|
| RF06 | Gerenciar usuários com perfis: Administrador, Atendente, Técnico e Cliente.|Essencial|
| RF07 |	Administrador visualiza relatório com total e valor por tipo de serviço.|	Desejável|	

### 2.2 Requisitos não funcionais (COMO o sistema deve ser)

| ID | Requisito |
|---|---|
|RNF01|Acesso via navegador (aplicação web).
|RNF02|Login obrigatório; senhas criptografadas.
|RNF03|Verificação de horário em até 2 segundos.
|RNF04|Funciona em celular e computador.
|RNF05|Banco H2 (desenvolvimento) / MySQL (produção).
|RNF06|Duração padrão: Instalação — 180 min; Manutenção — 120 min; Limpeza — 90 min.

### 2.3 Regras de negócio (as REGRAS do negócio que o sistema precisa respeitar)

| ID | Regra |
|---|---|
| RN01| Mesmo técnico não pode ter dois atendimentos que se sobreponham no tempo.
|RN02	|Atendimentos apenas: seg–sex 08h–18h / sáb 08h–12h.
|RN03|	Cancelamento com no mínimo 4h de antecedência.
|RN04|	Serviços disponíveis: Instalação (R$ 350,00 · 3h), Manutenção (R$ 200,00 · 2h), Limpeza (R$ 150,00 · 1h30).
|RN05	|Apenas Administrador e Atendente podem criar/editar agendamentos; Cliente só visualiza e solicita alteração.
---

## 3. Histórias de usuário

 HU01 — Agendar Instalação
 Como atendente, quero agendar uma Instalação, para registrar o serviço com duração de 3h e valor correto, sem conflito de horário.
 Exibe valor e duração ao selecionar "Instalação"
 - Bloqueia horário + próximos 180 minutos para o técnico
 - Salva com status "Agendado"

HU02 — Agendar Manutenção
Como atendente, quero agendar Manutenção, para registrar atendimento preventivo/corretivo com duração de 2h.
 - Dados do cliente, endereço e técnico
 - Sistema calcula horário de término automaticamente
 - Confirmação visual na agenda

HU03 — Agendar Limpeza
Como cliente/atendente, quero agendar Limpeza, para solicitar o serviço mais rápido com duração de 45m.
 - Opção clara entre os 3 serviços
 - Valor e duração visíveis antes de confirmar
 - Confirmação disponível imediatamente

HU04 — Técnico vê agenda
Como técnico, quero ver minha agenda, para saber se é Instalação, Manutenção ou Limpeza, horário e endereço.
 - Lista só meus atendimentos
 - Cor diferenciada por tipo de serviço
 - Status: Agendado / Em Andamento / Concluído / Cancelado

---

## 4. Modelo de dados

```mermaid
classDiagram
    direction LR
    class Cliente {
        Long id
        String nome
        String telefone
        String email
    }
    class Servico {
        Long id
        String nome
        String descricao
        Integer duracaoMinutos
        BigDecimal preco
    }
    class Agendamento {
        Long id
        LocalDate data
        LocalTime horario
        String status
    }
    Cliente "1" --> "*" Agendamento : marca
    Servico "1" --> "*" Agendamento : refere-se
```
}
### Acesso
| Usuário | Senha | Perfil |
|---|---|---|
| admin | admin123 | Administrador |
| operador | operador123 | Operador |

*(o login passa a ser exigido a partir do Encontro 7)*

---

## 6. Tecnologias
Java 21 · Spring Boot 4 · Spring MVC · Thymeleaf · Bootstrap 5 · Spring Data JPA · H2 (desenvolvimento) · MySQL (produção) · Spring Security · Git/GitHub

## 7. Uso de inteligência artificial
<!-- Registre aqui, de forma resumida, quando e como você usou ferramentas de IA de forma relevante no projeto. -->

## 8. Histórico de versões
| Versão | Data | Descrição |
|---|---|---|
| 0.1.0 | | Projeto inicial criado a partir do repositório modelo |
