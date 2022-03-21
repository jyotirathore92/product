Product Application

To run the code see below. You can of course also build and run the example in your IDE.
1.Build the code base mvn clean install
2.Run via Maven using Boot: mvn spring-boot:run

Once the API is running, it can be consumed:

GET localhost:8080/products - to get all products

GET localhost:8080/products/1 - to get a specific product

POST localhost:8080/products - POST to add a new product to embedded DB.

All tests will be executed as part of build but can also be run independently if need be.
