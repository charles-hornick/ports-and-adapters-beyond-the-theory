# Article 2 — Organizing the Application with Package-by-Component

> Part of the **"Ports & Adapters: Beyond the Theory"** series.
> Previous: [Article 1 — Runtime Adapter Hot-Swapping](https://dev.to/charleshornick)

## What this article explores

Cockburn's Ports & Adapters pattern protects the application from the outside world, but says nothing about how to organize its internals. This article fills that gap using Simon Brown's package-by-component approach, where Java's `package-private` becomes the primary isolation mechanism.

Three key positions are taken and defended:

1. **The port is not a Java `interface`** - Cockburn defines it as a protocol taking the form of an API, not a language keyword. The primary ports in this codebase are concrete `final` classes.
2. **"Domain" is DDD vocabulary, not Cockburn's** - the interior of the hexagon is the *application*. There is no `domain/` package.
3. **The Step Builder enforces Cockburn's protocol at compile time** - multi-step ports use inner interfaces to guarantee the interaction order. Single-step ports don't need it.

## The application

An RPG character creation system with real business rules:

- Create a character by name (unique)
- Define a race - carries a creation point cost and base characteristics
- Define a profession - constrained by race, invested points, and creation points
- Invest points in primary characteristics - bounded by race-dependent limits per characteristic, per category, and globally

Every action produces an immutable `Snapshot` of the character's state. The `Result<T>` type from [Pragmatica](https://github.com/pragmaticalabs/pragmatica) replaces exceptions everywhere (a dedicated article on this choice is coming.)

## Package structure

```
be.charleshornick.supra
├── ErrorCause.java                         # cross-cutting error constants
├── ForStoringSnapshot.java                 # secondary port, shared across use cases
│
├── creation/                               # use case: create a character
│   ├── CreateCharacter.java                # primary port (public final)
│   ├── ForCheckingNameUnicity.java         # secondary port (specific)
│   ├── Character.java                      # package-private
│   └── CharacterNameValidator.java         # package-private
│
├── define/                                 # use cases: modify an existing character
│   ├── ForLoadingSnapshot.java             # secondary port, shared within define/
│   ├── ToCharacter.java                    # step interface, shared within define/
│   ├── race/
│   │   ├── DefineRace.java                 # primary port (public final)
│   │   ├── ForLoadingRace.java             # secondary port (specific)
│   │   ├── DefineRaceStep.java             # package-private
│   │   └── Character.java                  # package-private
│   ├── profession/
│   │   ├── DefineProfession.java           # primary port (public final)
│   │   ├── ForLoadingProfession.java       # secondary port (specific)
│   │   ├── DefineProfessionStep.java       # package-private
│   │   └── Character.java                  # package-private
│   └── characteristic/
│       ├── DefineCharacteristic.java        # primary port (public final)
│       ├── AddOnePoint.java                 # package-private (step builder)
│       ├── RemoveOnePoint.java              # package-private (step builder)
│       └── Character.java                   # package-private
│
├── race/                                    # vocabulary: what a race IS
│   ├── Race.java
│   └── RaceName.java
├── profession/                              # vocabulary: what a profession IS
│   ├── Profession.java
│   ├── ProfessionName.java
│   ├── ProfessionType.java
│   └── Prerequisite.java
├── characteristic/                          # vocabulary: what a characteristic IS
│   ├── PrimaryCharacteristic.java
│   └── PrimaryCharacteristicName.java
└── snapshot/                                # state management internals
    ├── Snapshot.java
    ├── SnapshotBuilder.java
    ├── Action.java
    ├── Recorder.java
    ├── CreationPoint.java
    ├── CreationPointConsumer.java
    └── InvestedPoint.java
```

Actions (`creation/`, `define/`) are separated from vocabulary (`race/`, `profession/`, `characteristic/`). The former describe what you *do*, the latter describe what things *are*.

## How the ports read

Single-step - no protocol to enforce:

```java
createCharacter.named("Borgrim");
```

Multi-step - the Step Builder enforces the protocol at compile time:

```java
defineRace.named(RaceName.DWARF).toCharacterNamed("Borgrim");

defineProfession.named(ProfessionName.GUERRIER).toCharacterNamed("Borgrim");

defineCharacteristic.byAddingOnePoint()
    .toCharacteristicNamed(PrimaryCharacteristicName.COURAGE)
    .toCharacterNamed("Borgrim");
```

No other call sequence compiles.

## The honest trade-off

The constructors of primary ports are `public`, a composition root (not in this branch) will need them. But this means any adapter could also call `new CreateCharacter(portA, portB)` and bypass the intended wiring.

`package-private` covers ~80% of the isolation. Java has no mechanism to say "this constructor is visible only to that module." This is a language limitation, not a pattern limitation.

**→ This is the subject of Article 3: JPMS.**

## Tech stack

- Java 25
- [Pragmatica](https://github.com/pragmaticalabs/pragmatica) (`Result<T>`, `Option<T>`)
- JUnit 6
- AssertJ
- Maven

No Spring, no framework, no annotations in the application. This branch contains only the `supra` module (adapters and composition root are introduced in Article 3.)

## Run the tests

```bash
cd supra
mvn test
```

## Series overview

| # | Article | Branch | Focus |
|---|---------|--------|-------|
| 1 | [Runtime Adapter Hot-Swapping](https://dev.to/charleshornick) | Separate repo | Automatic failover between adapters at runtime |
| 2 | **Organizing the Application** | `article/2-package-by-component` | Structuring the application's internals with Brown |
| 3 | Isolating with JPMS (coming soon) | `article/3-jpms-isolation` | Enforcing boundaries beyond package-private |
| 4 | Testing with Result (coming soon) | — | Shared test scenarios, fakes over mocks |
| 5 | Adapter Switching Strategies (coming soon) | — | Compile-time, config-time, runtime switching |
| 6 | Spring Modulith + P&A (coming soon) | — | Bounded contexts and hexagonal internals |

## Acknowledgements

This series exists because [Alistair Cockburn](https://en.wikipedia.org/wiki/Alistair_Cockburn) shared Article 1 and described the approach as *"an amazing use of Hexagonal Architecture."* The architectural decisions in this codebase are grounded in [his original text](https://alistair.cockburn.us/hexagonal-architecture).

Package-by-component is Simon Brown's concept, detailed in his work on the [C4 model](https://c4model.com/) and software architecture.

## License

MIT — see [LICENSE](LICENSE).