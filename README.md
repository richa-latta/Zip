# CI/CD Pipeline Demo

[![CI](https://github.com/richa-latta/Zip/actions/workflows/ci.yml/badge.svg)](https://github.com/richa-latta/Zip/actions/workflows/ci.yml)

A demonstration of CI/CD practices for automated quality assurance, showcasing enterprise-grade automation, testing, and continuous integration workflows.

## Overview

This project demonstrates a complete CI/CD pipeline implementation for a Java REST API client (sample), featuring:

- **Automated Linting**: Checkstyle for code style enforcement
- **Static Analysis**: SpotBugs for bug pattern detection
- **Type Checking**: Java compilation ensures type safety
- **Unit Testing**: JUnit 5 with Mockito for comprehensive test coverage
- **Code Coverage**: JaCoCo with 80% coverage requirement
- **Branch Protection**: Enforced PR reviews and passing checks
- **Squash & Rebase**: Clean commit history on merge

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 17 |
| Build Tool | Maven 3.8+ |
| Linter | Checkstyle 10.12 |
| Static Analysis | SpotBugs 4.8 |
| Testing Framework | JUnit 5 |
| Mocking Framework | Mockito 5 |
| Coverage Tool | JaCoCo 0.8 |
| CI/CD Platform | GitHub Actions |
| API Under Test | JSONPlaceholder REST API |

## Project Structure

```
Zip/
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions CI pipeline
├── src/
│   ├── main/java/com/demo/
│   │   ├── JsonPlaceholderClient.java # REST API client
│   │   ├── model/
│   │   │   ├── User.java             # User entity model
│   │   │   └── Post.java             # Post entity model
│   │   └── exception/
│   │       └── ApiException.java     # Custom exception
│   └── test/java/com/demo/
│       ├── JsonPlaceholderClientTest.java # API client tests
│       └── model/
│           ├── UserTest.java         # User model tests
│           └── PostTest.java         # Post model tests
├── docs/
│   └── CI_CD_PROCESS.md              # Detailed process documentation
├── pom.xml                            # Maven configuration
├── checkstyle.xml                     # Checkstyle rules
├── .gitignore                         # Git ignore rules
└── README.md                          # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- Git
- GitHub account (for CI/CD)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/Zip.git
cd Zip
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run Individual Checks

#### Checkstyle (Linting)
```bash
mvn checkstyle:check
```

#### Compile (Type Checking)
```bash
mvn clean compile
```

#### SpotBugs (Static Analysis)
```bash
mvn spotbugs:check
```

#### Unit Tests
```bash
mvn test
```

#### Code Coverage Report
```bash
mvn jacoco:report
# View report at: target/site/jacoco/index.html
```

## CI/CD Pipeline

### Pipeline Workflow

The CI pipeline runs automatically on:
- Every push to `main`/`master` branch
- Every pull request to `main`/`master` branch

### Pipeline Stages

1. **Code Checkout**: Clone the repository
2. **Setup Environment**: Configure JDK 17 and Maven
3. **Linting**: Run Checkstyle to enforce code standards
4. **Type Checking**: Compile code to verify type safety
5. **Static Analysis**: Run SpotBugs to detect potential bugs
6. **Unit Testing**: Execute JUnit tests with Mockito
7. **Coverage Check**: Verify 80% code coverage requirement
8. **Build Verification**: Package the application
9. **Artifact Upload**: Store JAR, coverage reports, and test results



### Quality Gates

All PRs must pass the following before merging:

- ✅ Checkstyle: Zero violations
- ✅ Compilation: No type errors
- ✅ SpotBugs: No bug patterns detected
- ✅ Unit Tests: 100% pass rate
- ✅ Code Coverage: Minimum 80%
- ✅ PR Review: At least 1 approval required

## Branch Protection Rules

To configure branch protection on GitHub:

1. Go to **Settings** → **Branches**
2. Add rule for `main` branch:
   - ☑️ Require pull request reviews before merging
   - ☑️ Require status checks to pass before merging
     - Select: `quality-checks`
   - ☑️ Require branches to be up to date before merging
   - ☑️ Require linear history (enforces squash & rebase)
   - ☑️ Do not allow bypassing the above settings

## Testing the Pipeline

### Creating a Test PR

1. Create a new branch:
```bash
git checkout -b feature/test-pipeline
```

2. Make a small change (e.g., add a comment)

3. Commit and push:
```bash
git add .
git commit -m "Test CI pipeline"
git push origin feature/test-pipeline
```

4. Create a pull request on GitHub

5. Observe the CI pipeline running automatically

### Simulating Failures

#### Checkstyle Failure
Add a line longer than 120 characters without proper formatting.

#### Compilation Failure
Introduce a type error:
```java
String number = 123; // Type mismatch
```

#### Test Failure
Modify a test assertion to fail:
```java
assertEquals(2, 1 + 1); // Change to assertEquals(3, 1 + 1);
```

#### Coverage Failure
Comment out test methods to drop below 80% coverage.

## Notifications

The pipeline sends notifications on:

- **PR Comments**: Automatic comment on PRs with check results
- **Email**: GitHub sends emails on workflow failures (to commit author)
- **Status Checks**: PR shows green/red status badges

In a production environment, additional notifications would include:
- Slack channel alerts
- JIRA ticket updates
- Custom email notifications via SendGrid
- PagerDuty alerts for critical failures

## Continuous Improvement

This pipeline demonstrates the foundation for continuous learning and improvement:

### Current Automation Tools
- Checkstyle (code quality)
- SpotBugs (static analysis)
- JUnit 5 (testing)
- Mockito (mocking)
- JaCoCo (coverage)

### Future Enhancements
- Integration testing with Testcontainers
- Performance testing with JMeter
- Security scanning with OWASP Dependency-Check
- Mutation testing with PIT
- API contract testing with Pact
- Parallel test execution

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Ensure all CI checks pass locally
5. Submit a pull request
6. Wait for review approval
7. Merge using squash & rebase

## Documentation

For detailed process documentation, failure scenarios, and stakeholder information, see:
- [CI/CD Process Documentation](docs/CI_CD_PROCESS.md)

## License

This project is created for demonstration purposes as part of a QA Engineer take-home exercise.

## Contact

For questions or feedback about this demonstration, please open an issue on GitHub.
