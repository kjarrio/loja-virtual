# Online Store API

API for an online store. It features user registration, products and purchases. It is documented using Swagger and
tested using Postman. Created as a tech challenge for **Br----prev :)**

## Stack
- Java 8
- [Spring Boot](https://spring.io/projects/spring-boot), [Spring Security](https://spring.io/projects/spring-security), etc...
- [PostgreSQL](https://www.postgresql.org/) + *H2 for testing*
- [Docker](https://www.docker.com/) 

## Features
- Users, Products and Purchases
- Authentication & authorization
- Documented with [Swagger](https://swagger.io/) and easily testable with [Postman](https://www.postman.com/)
- Unit tests with ~75% coverage
- Basic validation
- Written and documented in English

## Building and running

This project can be built with Docker. Just run everything at once with docker-compose:

    docker-compose up -d

This will run two containers, **store-api** and **store-db**.

To stop the containers, run:

    docker-compose down
    
## Usage:

For demonstration purposes, when you launch the project it will prepopulate the database with a couple of users and few
products. 

These are the test credentials: 
- admin@admin.com / password
- user@user.com / password

You can login here: http://localhost:8080/login/

### Endpoints

- **Users** 
    - **GET** http://localhost:8080/users/ - *List of all users*
    - **GET** http://localhost:8080/users/1 - *Retrieve a user*
    - **POST** http://localhost:8080/users/ - *Create a user*
    - **DELETE** http://localhost:8080/users/1 - *Delete a user*
    
- **Products** 
    - **GET** http://localhost:8080/products/ - *List of all products*
    - **GET** http://localhost:8080/products/1 - *Retrieve a product*
    - **POST** http://localhost:8080/products/ - *Create a product*
    - **DELETE** http://localhost:8080/products/1 - *Delete a product*
    
- **Purchases** 
    - **GET** http://localhost:8080/purchases/ - *List of all purchases*
    - **POST** http://localhost:8080/purchases/ - *Create a purchase*
    - **GET** http://localhost:8080/purchases/1 - *Retrieve a purchase*
    - **GET** http://localhost:8080/purchases/1/user/ - *Retrieve user from purchase*
    - **GET** http://localhost:8080/purchases/1/products/ - *Retrieve products from purchase*
    - **POST** http://localhost:8080/purchases/1/products/ - *Add a product to a purchase*
    - **DELETE** http://localhost:8080/purchases/1 - *Delete a purchase*

For better documentation on usage, fields, etc... the Postman collection is a good reference alongside Swagger.

### Other endpoints
    
- http://localhost:8080/ - **Default endpoint with links**
- http://localhost:8080/postman.json - **Postman Collection for testing**
- http://localhost:8080/swagger-ui.html - **Swagger Docs & UI**
- http://localhost:8080/login/ - **Login** 
- http://localhost:8080/logout/ - **Logout** 
    
## Roadmap & missing features

- Better authentication
- JavaDocs
- Put and Patch API endpoints for updating content
- Better validation and tests for that
- Cloud deployment and configuration
- Logging
    
## License
Licensed under the [GNU General Public License v2.0](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html).
