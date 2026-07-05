# NutriTrack – Backend

## Projektbeschreibung
Im Rahmen des Moduls Webtechnologien entwickeln wir NutriTrack, eine browserbasierte Webanwendung zur Erfassung und Auswertung der täglichen Kalorienzufuhr. Nutzer können Mahlzeiten anlegen, ihren Gerichten Makronährstoffe zuordnen und erhalten eine automatische Berechnung der aufgenommenen Kalorien sowie der Makronährstoffe Kohlenhydrate, Proteine und Fette. Häufig konsumierte Gerichte lassen sich als Favoriten speichern, um die tägliche Erfassung zu beschleunigen.

Dieses Repository enthält das **Backend** (REST-API). Das Frontend liegt unter [NutriTrack-Frontend](https://github.com/Michelinchen/NutriTrack-Frontend).

- **Live-Backend:** https://nutritrack-1-gqy2.onrender.com
- **Live-Frontend:** https://nutritrack-frontend-q0wc.onrender.com
- Modul: Webtechnologien · HTW Berlin · Prof. Dr. Arif Wider

## Autor
- Michael Kecker – Matrikelnummer: 600971

## Tech-Stack
- **Java 21**, **Spring Boot 4.0.5**
- Spring Web (REST), **Spring Data JPA / Hibernate**
- **PostgreSQL** (Produktion), **H2 in-memory** (Tests)
- Gradle als Build-Tool
- Docker (Deployment auf Render)

## Architektur
Klassische Schichtentrennung:

```
MealController  ->  MealService  ->  MealRepository  ->  PostgreSQL
 (REST/HTTP)        (Geschäftslogik)   (Spring Data JPA)
```

- **MealController** – REST-Endpunkte
- **MealService** – Geschäftslogik (u. a. Owner-Zuweisung, Update-Logik, 404 bei unbekannter ID)
- **MealRepository** – `JpaRepository<MealEntry, Long>` mit `findByOwner(String owner)`

### Datenmodell
- **MealEntry**: `id`, `name`, `macro` (eingebettet), `favorite`, `owner`
- **Macronutrient** (`@Embeddable`): `countFat`, `countCarbs`, `countProteins`, `getTotalCalories()`

## REST-Endpunkte
Alle Endpunkte erwarten den Query-Parameter `Owner` (E-Mail des eingeloggten Nutzers) zur Mandantentrennung.

| Methode | Pfad | Beschreibung | Status |
|---|---|---|---|
| `GET` | `/meals?Owner={email}` | Alle Mahlzeiten des Nutzers | 200 |
| `POST` | `/meals?Owner={email}` | Neue Mahlzeit anlegen | 200 |
| `PUT` | `/meals/{id}?Owner={email}` | Mahlzeit aktualisieren | 200 / 404 |
| `DELETE` | `/meals/{id}?Owner={email}` | Mahlzeit löschen | 204 |

## Lokale Entwicklung

### Voraussetzungen
- JDK 21 (z. B. Eclipse Temurin), `JAVA_HOME` entsprechend gesetzt
- Zugriff auf eine PostgreSQL-Datenbank

### Umgebungsvariablen
Die Datenbank-Zugangsdaten werden über Umgebungsvariablen gesetzt (keine Klartext-Credentials im Code):

```bash
DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>   # muss mit "jdbc:" beginnen!
DATASOURCE_USERNAME=<user>
DATASOURCE_PASSWORD=<password>
```

### Starten
```bash
./gradlew bootRun
```
Die API läuft dann auf `http://localhost:8080`.

## Tests
```bash
./gradlew test
```
Die Tests laufen gegen eine **H2 In-Memory-Datenbank** (`src/test/resources/application.properties`), es wird keine echte Datenbank benötigt.

Enthaltene Tests:
- **Entity-Test** – `Macronutrient.getTotalCalories()`
- **Service-Tests** – `MealService` mit gemocktem Repository (`@MockitoBean`)
- **Controller-Tests** – `@WebMvcTest` + `MockMvc`
- **Integrationstest** – `@SpringBootTest` + `@AutoConfigureMockMvc` (POST → GET über die gesamte Kette bis zur DB)

## CI/CD
Bei jedem Push auf `master` führt **GitHub Actions** (`.github/workflows/gradle.yml`) automatisch alle Tests aus.

## Deployment
Deployment als **Docker Web Service** auf [Render](https://render.com). Der Build (`Dockerfile`) überspringt die Tests (`gradle build -x test`), da zur Build-Zeit keine DB-Zugangsdaten vorliegen. Die Zugangsdaten werden als Umgebungsvariablen im Render-Dashboard gesetzt.
