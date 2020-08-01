## The Country Bus company (Spring project)

Create CRUD spring application based on the [ER model](#the-country-bus-company-er-model) you created in E-R Model case studies

Technology stack:

- Build tool: Maven.
- Application container: Spring IoC (Spring Framework version 5.x).
- Logging framework: SLF4J.
- Testing/Mocking frameworks: JUnit (4.x or 5.x), Mockito (usage of annotations: @Mock, @Spy, etc.), Mockmvc
- Database: PostgreSQL (version 9.x is preferred, but you can use 10.x).
- Database connection pooling: HikariCP.
- Database VCS: Flyway as VCS for database. Don't forget to add initial scripts to resources.
- Lombok

Tech requirements:

- Clear layered structure should be used with responsibilities of each application layer defined.
- All Spring-related configuration should be done using Java config.
- Use Lombok actively
- Spring JDBC Template should be used for data access. 
- Use Flyway to create database and apply following changes
- Repository layer should be tested using integration tests with an in-memory embedded database.
- Provide required controllers and views for application (UI framework choise is up to you).
- Controllers should be tested using Mockmvc.
- Implement generic exception handler which should redirect all controller exceptions to view, that just prints exception message.


## The Country Bus company ER model
For the complete ER model please refer to this repository -> [er-diagram](https://gitlab.com/BeshEater/er-diagram)

A Country bus Company owns a number of buses. Each bus is allocated to a particular route, although Some routes may have Several buses. Each route passes through a number of towns. One or more drivers are allocated to each stage of a route, which corresponds to a journey through some or all of the towns on a route. Some of the towns have a garage where buses are kept and each of the buses are identified by the registration number and can carry different numbers of passengers, since the vehicles vary in size and can be single or double-decked. Each route is identified by a route number and information is available on the average number of passengers carried per day for each route. Drivers have an employee number, name, address, and sometimes a telephone number.

Key entities:

- Bus: registration number, size (amount of passengers), deck
- Route: route-number, avg-passengers amount
- Driver: personal info(name, date of birth etc.)
- Town: name, address
- Stage: stage-number
- Garage: name, address