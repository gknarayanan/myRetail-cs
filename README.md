# myRetail RESTful service
This is a proof of concept RESTful API to provide product information and update product price.

    1. GET operation at '/products/{id}' -  Retrive product name from an external API, retrieve product price from Cassandra database and returns a JSON response
    2. PUT operation at '/products/{id}/price' - Updates the price of the product with the given id and returns the same response as the GET operation



# Technology used
    1. Java 11
    2. Spring boot 2.6.2
    3. Apache Maven 3.8.1
    4. JUnit/Mockito
    5. Apache Cassandra 4.0 using Docker



# Other Tools
    1. Eclipse IDE
    2. Postman for testing



# Running the application
    Pre-requisite : Download the project from the following git repository

## 1. Cassandra setup (Pre-requisite : Docker)
    a. Using cmd/terminal, navigate to the folder with 'docker-compose.yaml'
    b. Run the command 'docker-compose up -d'.
    c. Run the command 'docker-compose exec cassandra-1 bash'
    d. Run the script in 'cassandra_scripts' folder to create keyspace and table

## 2. Rest API
    a. Add api-key to the application.yaml file at 'product-information.url.key'
    b. Using cmd/terminal, navigate to the folder with 'pom.xml'
    c. mvn spring-boot:run. Pass the argument '-Dspring-boot.run.arguments=--spring.config.location=path/to/config/file' if using an external config file



# Testing the API using Postman
* GET operation at '/products/{id}' -
    * URI - http://localhost:8080/products/13264003

    * Response JSON - 

    ```
        {
            "id": 13264003,
            "name": "Jif Natural Creamy Peanut Butter - 40oz",
            "current_price": {
                "value": 79.99,
                "currency_code": "CAD"
            }
        }
    ```


* PUT operation at '/products/{id}/price' - 
    * URI - http://localhost:8080/products/13264003/price

    * Request JSON Body - 
    ```
        {
            "value": 79.99,
            "currency_code": "CAD"
        }
    ```

    * Response JSON - 
    ```
        {
            "id": 13264003,
            "name": "Jif Natural Creamy Peanut Butter - 40oz",
            "current_price": {
                "value": 79.99,
                "currency_code": "CAD"
            }
        }
    ```
    