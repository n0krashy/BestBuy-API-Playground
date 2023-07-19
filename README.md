# BestBuy-API-Playground

###### BestBuy API playground is developed using Java programming language, RestAssured, TestNG and Lombok.

Test scripts can be found and run under [java](src%2Ftest%2Fjava) test sources root.

## **_Test plan:_**

### 1. Healthcheck

1. **Get**
    1. Get current Healthcheck status and print it

### 2. Products

1. **Create**
    1. Create a valid product
    2. Create an invalid product by sending a different value type
    3. Create an invalid product by sending an empty json and verifying that the list of required values errors is
       successfully received.
2. **Get**
    1. Get product by Id
    2. Get product by Id, selecting only name and price.
    3. Get invalid product by sending invalid Id
3. **Query**
    1. Get all products list
    2. Get products using pagination
    3. Get products using partial name
    4. Get products using category name
    5. Get products using category name alternative
    6. Get products using category id alternative
    7. Get products of a certain category and sorting products by price
    8. Select products using subset of Properties

### 3. Stores

1. **Create**
    1. Create a valid store
    2. Create an invalid store by sending an empty json and verifying that the list of required values errors is
       successfully received.
2. **Get**
    1. Get store by Id
    2. Get store by Id, selecting only name, hours and id.
    3. Get invalid store by sending invalid Id
3. **Query**
    1. Get all store list
    2. Get stores using pagination
    3. Get products using partial name
    4. Get stores using nearby filter
    5. Get stores using service name filter
    6. Get stores using service name alternative filter
    7. Get stores using service id
    8. Get stores using service id alternative
    9. Select stores using subset of Properties

### 4. Categories

1. **Create**
    1. Create a valid category
    2. Create an invalid category by sending an empty json and verifying that the list of required values errors is
       successfully received.
2. **Get**
    1. Get category by Id
    2. Get category by Id, selecting only name and id.
    3. Get invalid category by sending invalid Id
3. **Query**
    1. Get all categories list
    2. Get categories using pagination
    3. Get categories using partial name

### 5. Services

1. **Create**
    1. Create a valid service
    2. Create an invalid service by sending an empty json and verifying that the list of required values errors is
       successfully received.
2. **Get**
    1. Get service by Id
    2. Get service by Id, selecting only name and id.
    3. Get invalid service by sending invalid Id
3. **Query**
    1. Get all services list
    2. Get services using pagination
    3. Get services using partial name
