# Ports & Adapters: Beyond the Theory

A practical exploration of the Ports & Adapters pattern using an RPG character creation system as domain example.

This repository is the companion code for the **"Ports & Adapters: Beyond the Theory"** article series, where each article tackles a real challenge that Alistair Cockburn's original pattern intentionally left open.

## The Domain

An RPG character creation system with real business rules: race and profession choices carry bidirectional constraints (an elf can't be a miner, choosing miner first excludes elf), and every decision can be undone or redone thanks to the Memento pattern.

Simple enough to understand in minutes. Complex enough to demonstrate real design decisions.

## Article Series

| # | Article | Branch | Focus |
|---|---------|--------|-------|
| 1 | [Runtime Adapter Hot-Swapping](https://dev.to/charleshornick) | Separate repo | Automatic failover between adapters at runtime |
| 2 | Organizing the Application** | `article/2-package-by-component` | Structuring the application's internals with Brown |
| 3 | Isolating with JPMS (coming soon) | `article/3-jpms-isolation` | Enforcing boundaries beyond package-private |
| 4 | Testing with Result (coming soon) | — | Shared test scenarios, fakes over mocks |
| 5 | Adapter Switching Strategies (coming soon) | — | Compile-time, config-time, runtime switching |
| 6 | Spring Modulith + P&A (coming soon) | — | Bounded contexts and hexagonal internals |

> Article 1 uses a different domain and lives in its own repository. Articles 2+ use the RPG character creation system in this repo.

> Each article branch has its own README with setup instructions, how to run, and how to test.

## Tech Stack

- Java 25+
- Spring Boot 4
- Maven (multi-module)
- JUnit 6

## About

This project is maintained by [Charles Hornick](https://charles-hornick.be), a Java expert with 15 years of experience specializing in refactoring and legacy rescue.

The series was initiated after [Alistair Cockburn](https://en.wikipedia.org/wiki/Alistair_Cockburn), creator of the Ports & Adapters pattern and co-author of the Agile Manifesto, shared the first article and described the approach as "an amazing use of Hexagonal Architecture."

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.