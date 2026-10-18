# project-aed-java
Animalario — Laboratory Mouse Population Manager
# Animalario — Laboratory Mouse Population Manager

A Java console application for managing populations of transgenic laboratory mice: register populations, add and edit mice, compute genetic traits from their chromosomal mutations, and save/load everything to CSV files.

Developed as the practical project for **Algorithms and Data Structures**, Biomedical Engineering — Universidad San Pablo CEU.

> **Status:** Milestone 1.

---

## 📋 Table of contents

- [Overview](#-overview)
- [Features](#-features)
- [Domain model](#-domain-model)
- [Genetic model](#-genetic-model)
- [CSV file format](#-csv-file-format)
- [Author](#-author)

---

## Overview

A company that breeds transgenic mice for research laboratories needs a tool to keep track of the populations housed in its facilities and the mice that belong to each one.

Each population is managed as an independent unit. The application works with **one active population at a time**, which can be saved to a CSV file and reopened later.

The project focuses on:

- Modelling entities with Java classes, encapsulation and single-responsibility methods.
- Choosing a suitable data structure to store, find and modify objects, and reasoning about the cost of those operations.
- Validating input and handling exceptional cases without ever leaving the data in an inconsistent state.
- Keeping the **domain logic**, the **user interface** and the **persistence layer** separate.
- Verifying behaviour with automated **JUnit** tests.

---

## Features

The application provides a text menu:
| `1` | **Open population** | Loads a population from a CSV file. If the file is invalid, the active population is kept untouched. |
| `2` | **Create population** | Creates a new, empty population that becomes the active one. |
| `3` | **Add mouse** | Rejects invalid data and reference codes already used in the population. |
| `4` | **List mice** | Shows the reference codes of all mice in the active population. |
| `5` | **Remove mouse** | Removes a mouse by its reference code. |
| `6` | **Edit mouse** | Edits any field, including the reference code (if the new one is unique) and the sex (if the resulting chromosomes are valid). |
| `7` | **View mouse** | Shows all data, mutations and the computed sterility / polygamy. |
| `8` | **Save** | Saves to the associated file, or asks for a path if there is none. |
| `9` | **Save as** | Saves to a new path, which becomes the associated file. |
| `0` | **Exit** | Warns before quitting if there are unsaved changes. |

Safety features:

- Warns about **unsaved changes** before opening, creating or exiting.
- A failed CSV load **never partially replaces** the active population.
- Detects missing active population, invalid menu options, empty collections and file I/O errors.
- No invalid edit can leave a mouse in an inconsistent state.

---

## Domain model

### Population

| Field | Constraint |
|---|---|
| Name | Non-empty text |
| Person in charge | Non-empty text |
| Days of stay | Integer between **1 and 269** (inclusive) |
| Mice | Collection (may be empty); reference codes are unique within the population |

### Mouse

| Field | Type / constraint |
|---|---|
| Reference code | Non-empty text, unique within the population |
| Date of birth | `LocalDate`, format `YYYY-MM-DD` |
| Weight | Positive integer (grams) |
| Sex | `enum` → `MALE` or `FEMALE` |
| Body temperature | Finite real number between **30.0 and 45.0 °C** |
| Notes | Free text (may be empty) |
| Chromosomal mutations | See [Genetic model](#-genetic-model) |

---

## Genetic model

A simplified model for teaching purposes, not a complete representation of real biology.

| | Chromosomes | Sterile when… | Polygamous when… |
|---|---|---|---|
| **Male** | X + Y | its X carries the sterility mutation | its Y carries the polygamy mutation |
| **Female** | X + X | **both** X carry the sterility mutation | never (not applicable) |

- Sterility and polygamy are **derived properties**: they are computed from the chromosomes and never stored separately, so they can never contradict them.
- Impossible configurations (a female with a Y chromosome, a male with two X) are rejected.

---

## CSV file format

Each population is stored in its own **UTF-8** CSV file with fields separated by **semicolons (`;`)**.

**Columns (in order):**

```
tipo;nombrePoblacion;responsable;dias;codigo;fechaNacimiento;peso;sexo;temperatura;observaciones;mutacionX1;mutacionX2;mutacionY
```

- The first row after the header is the single `POBLACION` row.
- It is followed by zero or more `RATON` rows, one per mouse.
- Fields that do not apply are left empty (for example `mutacionY` for females, or `mutacionX2` for males).
- Mutations use `true` / `false`, dates use `YYYY-MM-DD`, and decimals use a dot (`.`).
- Text containing `;`, quotes or line breaks is quoted following standard CSV rules.

On load, the file is fully validated before it becomes the active population: there must be exactly one population, no duplicate codes, and every mouse must satisfy the model constraints. Empty populations can also be saved and loaded.

A sample file is included in [`data/`](data/).


---

## Author

**Alex Martínez Fernández** — Biomedical Engineering student, Universidad San Pablo CEU
GitHub: [@almart-16](https://github.com/almart-16)

---

<sub>Academic project for the Algorithms and Data Structures course. The genetic model is simplified for teaching purposes.</sub>
