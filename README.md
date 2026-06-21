# Drug Interaction System

A Spring Boot REST API for managing doctors, patients, and prescriptions, with a built-in drug interaction checker. When a new medicine is prescribed to a patient, the system checks it against the patient's existing prescriptions by calling an external drug-interaction API (RxCheck) and flags any dangerous combinations.

## Features

- **Doctor management** — create, list, update, and delete doctor records
- **Patient management** — create, list, update, delete patients, and assign them to a doctor
- **Prescription management** — add prescriptions to a patient and look them up by patient or prescription ID
- **Drug interaction checking** — given a patient and a new medicine, checks it against every medicine currently prescribed to that patient via the RxCheck API and returns a status (`SAFE` / `DANGER`), severity, risk score, clinical significance, and suggested alternative

## Tech Stack

- **Java 26**
- **Spring Boot 4.0.6** (Web MVC, Spring Data JPA)
- **MySQL** (via `mysql-connector-j`)
- **Lombok**
- **Maven** (with the included Maven Wrapper, `mvnw` / `mvnw.cmd`)
- **RxCheck API** — external service used to evaluate drug-drug interactions

## Project Structure

```
src/main/java/com/druginteraction/main/
├── MainApplication.java          # Spring Boot entry point
├── RestTemplateConfig.java       # RestTemplate bean for external API calls
├── Service/
│   ├── InteractionService.java       # Orchestrates interaction checks
│   └── ExternalDrugAPIService.java   # Calls the RxCheck API
├── controller/
│   ├── DoctorController.java         # /doctors
│   ├── PatientController.java        # /patient
│   ├── PrescriptionController.java   # /prescriptions
│   └── InteractionController.java    # /interaction
├── entity/
│   ├── Doctor.java, Patient.java, Prescription.java   # JPA entities
│   └── Interaction*.java                              # DTOs for the interaction check flow
└── repository/
    ├── DoctorRepo.java, PatientRepo.java, PrescriptionRepo.java
```

> **Note:** the repo currently contains a duplicate, nested copy of the project under `main/` (its own `pom.xml`, `src/`, `mvnw`, etc.), identical in structure to the root project. You'll likely want to remove that duplicate folder so the build isn't ambiguous about which `pom.xml` is the real one.

## Prerequisites

- JDK 26
- Maven (or use the included `./mvnw`)
- A running MySQL server
- An API key for [RxCheck](https://api.rxcheck.dev) (or whichever interaction-data provider you're using)

## Setup

1. **Clone the repo**
   ```bash
   git clone https://github.com/AntoMullar13/Drug-Interaction-system.git
   cd Drug-Interaction-system
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE drug_interaction_db;
   ```

3. **Configure environment variables** instead of editing `application.properties` directly (see [Security](#security) below):
   ```bash
   export DB_URL=jdbc:mysql://localhost:3306/drug_interaction_db
   export DB_USERNAME=root
   export DB_PASSWORD=your_password
   export RXCHECK_API_URL=https://api.rxcheck.dev/v1/interactions
   export RXCHECK_API_KEY=your_api_key
   ```

   And update `application.properties` to reference them:
   ```properties
   spring.application.name=main
   server.port=8086

   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format-sql=true

   rxcheck.api.url=${RXCHECK_API_URL}
   rxcheck.api.key=${RXCHECK_API_KEY}
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   The API will start on `http://localhost:8086`.

## API Endpoints

### Doctors — `/doctors`
| Method | Path | Description |
|---|---|---|
| GET | `/doctors` | List all doctors |
| POST | `/doctors` | Add a doctor |
| PUT | `/doctors/{id}` | Update a doctor |
| DELETE | `/doctors/{id}` | Delete a doctor |

### Patients — `/patient`
| Method | Path | Description |
|---|---|---|
| GET | `/patient` | List all patients |
| GET | `/patient/{id}` | Get a patient by ID |
| POST | `/patient` | Add a patient |
| PUT | `/patient/{id}` | Update a patient |
| DELETE | `/patient/{id}` | Delete a patient |
| PUT | `/patient/{id}/doctor/{doctor_id}` | Assign a doctor to a patient |
| POST | `/patient/{patient_id}/prescriptions` | Add a prescription for a patient |

### Prescriptions — `/prescriptions`
| Method | Path | Description |
|---|---|---|
| GET | `/prescriptions` | List all prescriptions |
| GET | `/prescriptions/{id}` | Get a prescription by ID |
| GET | `/prescriptions/patient/{id}` | List prescriptions for a patient |
| PUT | `/prescriptions/{id}` | Update a prescription |
| DELETE | `/prescriptions/{id}` | Delete a prescription |

### Interaction Check — `/interaction`
| Method | Path | Description |
|---|---|---|
| POST | `/interaction/check` | Check a new medicine against a patient's existing prescriptions |

**Request body:**
```json
{
  "patientid": 1,
  "medicinename": "Warfarin",
  "dosage": 5
}
```

**Response body:**
```json
{
  "status": "DANGER",
  "Severity": "major",
  "RiskScore": 8,
  "Message": "Increased bleeding risk when combined with existing medication.",
  "Alternative_Medicine": "Consider an alternative anticoagulant or adjust dosage under supervision."
}
```

If no dangerous interaction is found, `status` is `SAFE` and a default "No interaction found." message is returned.

## Security

⚠️ **Before deploying or sharing this repo further:**

- `application.properties` currently has a database password and a **live** RxCheck API key committed in plain text. Treat that key as compromised — rotate/revoke it with the provider, then switch to environment variables (or a secrets manager) as shown above.
- Add `application.properties` (or a `application-local.properties` override) to `.gitignore` going forward, and commit an `application.properties.example` template with placeholder values instead.
- There's currently no authentication/authorization layer — all endpoints are open. Consider adding Spring Security if this will handle real patient data.

## Roadmap Ideas

- Add request validation (e.g., reject unknown patient IDs with a proper 404 instead of a null object)
- Add pagination for list endpoints
- Add automated tests beyond the default context-load test
- Remove the duplicate nested `main/` project folder

## License

No license file is currently included — add one (e.g., MIT, Apache 2.0) if you intend for others to use or contribute to this project.
