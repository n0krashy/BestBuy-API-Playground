# BestBuy-API-Playground

###### BestBuy API playground is developed using Java programming language, RestAssured, TestNG and Lombok.

Test scripts can be found and run under [java](src%2Ftest%2Fjava) test sources root.

## **_Test plan:_**

### 1. Healthcheck

1. [x] **Get**
   * Get current Healthcheck status and print it

### 2. Products

1. [x] **Create**
   * Create a valid product
   * Create an invalid product by sending a different value type
   * Create an invalid product by sending an empty json and verifying that the list of required values errors is
     successfully received.
2. [x] **Get**
   * Get product by Id
   * Get product by Id, selecting only name and price.
   * Get invalid product by sending invalid Id
3. [x] **Query**
   * Get all products list
   * Get products using pagination
   * Get products using partial name
   * Get products using category name
   * Get products using category name alternative
   * Get products using category id alternative
   * Get products of a certain category and sorting products by price
   * Select products using subset of Properties

### 3. Stores

1. [x] **Create**
   * Create a valid store
   * Create an invalid store by sending an empty json and verifying that the list of required values errors is
     successfully received.
2. [x] **Get**
   * Get store by Id
   * Get store by Id, selecting only name, hours and id.
   * Get invalid store by sending invalid Id
3. [x] **Query**
   * Get all store list
   * Get stores using pagination
   * Get products using partial name
   * Get stores using nearby filter
   * Get stores using service name filter
   * Get stores using service name alternative filter
   * Get stores using service id
   * Get stores using service id alternative
   * Select stores using subset of Properties

### 4. Categories

1. [x] **Create**
   * Create a valid category
   * Create an invalid category by sending an empty json and verifying that the list of required values errors is
     successfully received.
2. [x] **Get**
   * Get category by Id
   * Get category by Id, selecting only name and id.
   * Get invalid category by sending invalid Id
3. [x] **Query**
   * Get all categories list
   * Get categories using pagination
   * Get categories using partial name

### 5. Services

1. [x] **Create**
   * Create a valid service
   * Create an invalid service by sending an empty json and verifying that the list of required values errors is
     successfully received.
2. [x] **Get**
   * Get service by Id
   * Get service by Id, selecting only name and id.
   * Get invalid service by sending invalid Id
3. [x] **Query**
   * Get all services list
   * Get services using pagination
   * Get services using partial name
