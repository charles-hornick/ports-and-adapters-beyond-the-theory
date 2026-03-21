# Article 3 — Isolating the Application with JPMS

> Part of the **"Ports & Adapters: Beyond the Theory"** series.
> Previous: [Article 2 — Organizing the Application with Package-by-Component](https://dev.to/charleshornick/runtime-adapter-hot-swapping-with-ports-adapters-the-pattern-alistair-cockburn-didnt-document-56cg)

## What this article explores

Article 2 combined Ports & Adapters with Simon Brown's package-by-component to organize the application's internals. `package-private` covered 80% of the isolation, but `public` constructors and shared utility classes remained exposed.

This article closes that gap with JPMS. The Java Platform Module System adds a level of isolation above packages: **a module is closed by default**. If a package is not explicitly exported, it is invisible from outside the module, even if its classes are `public`.

Three key additions in this article:

1. **JPMS module definitions** enforce boundaries at compile time. Non-exported packages are invisible to adapters.
2. **A test console adapter** drives the application through its ports, following the approach Cockburn uses in *Hexagonal Architecture Explained*.
3. **A composition root** wires primary ports with fake secondary adapters. No Spring, no annotations, explicit wiring.

## Modules

```
ports-and-adapters-beyond-the-theory/
├── supra/                  # The application — JPMS module, exports only what adapters need
├── facade-test/            # Primary adapter — console test adapter driving the application
├── storage-test/           # Secondary adapter — in-memory fakes for persistence
└── bootstrap/              # Composition root — wires everything together, has the main()
```

### `supra` — The application

The application from article 2, now with a `module-info.java` that explicitly controls visibility. Internal packages (`state/`) are not exported. Vocabulary (`race/`, `profession/`, `characteristic/`) and ports (`create/`, `define/`, `retrieve/`) are exported.

The application has **zero** dependencies on any adapter module.

### `facade-test` — Test console adapter

A primary adapter that calls the ports and prints results to the console. It proves that:
- The JPMS boundaries work (non-exported packages are inaccessible)
- The ports are correctly wired through the composition root
- The protocol (Step Builder) works end-to-end

This adapter can only see what the application exports. If it tries to access `SnapshotBuilder` or `CreationPoint`, the compiler refuses.

### `storage-test` — In-memory fake adapter

A secondary adapter that implements the application's secondary ports with in-memory storage. Following Cockburn's approach in the book: a fake that allows testing the full flow without infrastructure.

### `bootstrap` — Composition root

The only module that knows about all others. It instantiates the fake adapters, creates the primary ports by injecting the secondary port implementations, and passes them to the test adapter.

```java
public class Application {
    void main(String[] args) {
        // Wire secondary adapters (fakes)
        // Wire primary ports with secondary implementations
        // Pass to primary adapter and run
    }
}
```

No Spring. No annotations. No framework. Explicit wiring, verified at compile time.

## Package structure — `supra` module

```
be.charleshornick.supra
├── ErrorCause.java                              # exported
├── ForStoringSnapshot.java                      # exported
│
├── create/                                      # exported
│   ├── CreateCharacter.java
│   ├── ForCheckingNameUnicity.java
│   ├── Character.java                           # package-private
│   └── CharacterNameValidator.java              # package-private
│
├── define/                                      # exported
│   ├── ForLoadingSnapshot.java
│   ├── ToCharacter.java
│   ├── race/                                    # exported
│   ├── profession/                              # exported
│   └── characteristic/                          # exported
│
├── retrieve/                                    # exported
│   ├── race/
│   ├── profession/
│   └── snapshot/
│
├── race/                                        # exported — vocabulary
├── profession/                                  # exported — vocabulary
├── characteristic/                              # exported — vocabulary
│
└── state/                                       # NOT exported — internal
    ├── CreationPoint.java
    ├── CreationPointConsumer.java
    ├── InvestedPoint.java
    ├── Recorder.java
    ├── SnapshotBuilder.java
    └── snapshot/                                # exported — Snapshot, Action
        ├── Snapshot.java
        └── Action.java
```

Two levels of isolation working together:
- **JPMS** controls what leaves the module. Non-exported packages are invisible even if classes are `public`.
- **`package-private`** controls what leaves the package. Internal classes stay invisible within the module itself.

## Key decisions

### Exports are not qualified

No `exports ... to` clauses. The application does not know which modules consume its ports. This preserves Cockburn's application ignorance.

### `opens ... to` is not used

The application does not declare `opens` for reflection. Deserialization (Jackson, Gson, etc.) is the adapter's responsibility, not the application's. The application cannot know about external technologies.

### `@Transactional` stays out of the application

Transaction management belongs to the adapter or the composition root, not to the application. The same port can be wired with a JDBC adapter (needs transactions) or an in-memory adapter (doesn't). The application cannot know.

## Tech stack

- Java 25
- [Pragmatica](https://github.com/pragmaticalabs/pragmatica) (`Result<T>`, `Option<T>`)
- JUnit 6
- AssertJ
- Maven (multi-module with JPMS)

No Spring. No framework. No annotations in the application.

## Run

```bash
cd bootstrap
mvn compile exec:java -Dexec.mainClass="be.charleshornick.supra.bootstrap.Application"
```

## Series overview

| # | Article | Branch | Focus |
|---|---------|--------|-------|
| 1 | [Runtime Adapter Hot-Swapping](https://dev.to/charleshornick/runtime-adapter-hot-swapping-with-ports-adapters-the-pattern-alistair-cockburn-didnt-document-56cg) | Separate repo | Automatic failover between adapters at runtime |
| 2 | [Organizing the Application](https://dev.to/charleshornick/ports-adapters-beyond-the-theory-organizing-the-application-with-package-by-component-49m3) | `article/2-package-by-component` | Structuring the application's internals with Brown |
| 3 | **Isolating with JPMS** | `article/3-jpms-isolation` | Enforcing boundaries beyond package-private |
| 4 | Testing with Result (coming soon) | — | Shared test scenarios, fakes over mocks |
| 5 | Adapter Switching Strategies (coming soon) | — | Compile-time, config-time, runtime switching |
| 6 | Spring Modulith + P&A (coming soon) | — | Bounded contexts and hexagonal internals |

## Acknowledgements

This series exists because [Alistair Cockburn](https://en.wikipedia.org/wiki/Alistair_Cockburn) shared Article 1 and described the approach as *"an amazing use of Hexagonal Architecture."* The architectural decisions in this codebase are grounded in [his original text](https://alistair.cockburn.us/hexagonal-architecture) and in [Hexagonal Architecture Explained](https://www.amazon.com/Hexagonal-Architecture-Explained-architecture-simplifies/dp/B0F5QSH28F) (Cockburn & Garrido de Paz, updated 1st edition, 2025).

Every line of code in this repository was written and tested by hand. No AI-generated code.

## License

MIT — see [LICENSE](LICENSE).