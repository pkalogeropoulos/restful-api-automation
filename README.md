![CI](../../actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17+-informational)
![Maven](https://img.shields.io/badge/Maven-3.9+-informational)
![RestAssured](https://img.shields.io/badge/RestAssured-API%20Testing-blue)
![TestNG](https://img.shields.io/badge/TestNG-Test%20Framework-brightgreen)
![Allure](https://img.shields.io/badge/Allure-Reporting-orange)

# REST API Automation Framework (RestAssured • Java • TestNG)

A scalable API test automation framework built with **RestAssured**, **TestNG**, and **Maven**.
It includes clean architecture (clients + endpoints), environment configuration, reusable request specs, and CI-ready execution.

## What are we trying to achieve?
- Build a maintainable API test framework from scratch
- Separate concerns: config → client → endpoints → assertions/tests
- Run locally and in CI with environment switching
- Generate readable reports (Allure) and coverage (JaCoCo)

## Features
✅ RestAssured + TestNG  
✅ Reusable `RequestSpec` / `ResponseSpec`  
✅ Environment configs (`-Denv=dev|qa|prod`)  
✅ Strong models (POJOs) + schema validation (optional)  
✅ Logging filters for debug and CI visibility  
✅ Allure reporting + attachments  
✅ GitHub Actions workflow (headless/CI-friendly)  
✅ Code quality gates (optional): Checkstyle/Spotless

## Project Structure
project-root/
├── pom.xml
├── README.md
├── .gitignore
├── .github/workflows/ci.yml
└── src
├── main
│ ├── java
│ │ └── com.example.api
│ │ ├── config
│ │ ├── client
│ │ ├── endpoints
│ │ ├── models
│ │ └── utils
│ └── resources
│ └── config-dev.properties
│ └── config-qa.properties
│ └── config-prod.properties
└── test
├── java
│ └── com.example.api.tests
│ ├── base
│ └── users
└── resources
├── testng.xml
├── schemas
└── testdata



## Configuration (env switching)
Config files live in:
`src/main/resources/config-*.properties`

Example:
- `config-dev.properties`
- `config-qa.properties`
- `config-prod.properties`

Run with:
```bash
mvn clean test -Denv=qa
