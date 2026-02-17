# Ports & Adapters: Beyond the Theory

A practical exploration of the Ports & Adapters pattern using an RPG character creation system as domain example.

This repository is the companion code for the **"Ports & Adapters: Beyond the Theory"** article series, where each article tackles a real challenge that Alistair Cockburn's original pattern intentionally left open.

## The Domain

An RPG character creation system with real business rules: race and profession choices carry bidirectional constraints (an elf can't be a miner, choosing miner first excludes elf), and every decision can be undone or redone thanks to the Memento pattern.

Simple enough to understand in minutes. Complex enough to demonstrate real design decisions.

## Article Series

| # | Article | Code | Key Concept |
|---|---------|------|-------------|
| 1 | [Runtime Adapter Hot-Swapping with Ports & Adapters](https://dev.to/charleshornick) | [Separate repo](https://github.com/charles-hornick/TODO) | Automatic failover between adapters at runtime |
| 2 | Organizing the Hexagon (coming soon) | `article/2-package-by-component` | Structuring the hexagon's internals using Simon Brown's package-by-component approach |
| 3 | Isolating the Hexagon with JPMS (coming soon) | `article/3-jpms-isolation` | Using Java modules to enforce boundaries beyond package-private |
| 4 | Adapter Switching Strategies (coming soon) | `article/4-switching-strategies` | Compile-time, config-time, and runtime switching — the full spectrum |

> Article 1 uses a different domain and lives in its own repository. Articles 2+ use the RPG character creation system in this repo.

> Each article branch has its own README with setup instructions, how to run, and how to test.

## Tech Stack

- Java 21+
- Spring Boot
- Maven (multi-module)
- JUnit 5

## About

This project is maintained by [Charles Hornick](https://charles-hornick.be), a Java expert with 15 years of experience specializing in refactoring and legacy rescue.

The series was initiated after [Alistair Cockburn](https://en.wikipedia.org/wiki/Alistair_Cockburn), creator of the Ports & Adapters pattern and co-author of the Agile Manifesto, shared the first article and described the approach as "an amazing use of Hexagonal Architecture."

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.