# Project: restful-web-service-car-adverts

# Description
  
  ###  RESTful web-service for CarAdverts written using Java Spring Boot
* have functionality to return list of all car adverts;
* support sorting by any field specified by query parameter, default sorting - by **id**;
* have functionality to return data for single car advert by id;
* have functionality to add car advert;
* have functionality to modify car advert by id;
* have functionality to delete car advert by id;
* have validation (see required fields and fields only for used cars);
* accept and return data in JSON format, use standard JSON date format for the **first registration** field.


# List of APIs/Endpoints:
   - GET `/cars` : return list of all car adverts
   - GET `/sortedcars/{sortedBy}` : sorting by any field specified by query parameter ,default sorting - by **id**
   - POST`/addcar` :  add car advert;
   - GET `/car/{id}`: return data for single car advert by id
   - PUT `/car/{id}`: update data for single car advert by id
   - Delete `/car/{id}`: delete data for single car advert by id


# Miscellaneous 
   1.  H2 database is used, because it's easily integrated with spring Boot (see poom.xml file) and configured in `application.properties` and I create simple query in `data.sql` to insert inital data into the DB.
   2.  I extends inteface of `PagingAndSortingRepository` which extends already interface of `CrudRepository` which allows me to have all Crud operations and Paging and sorting operations
   3.  no need to make implementation for  `CarRepository` interface, don't worry Spring Boot take care about it .
   4. first I handled the validation of input fields in `Service layer ` by throwing Exception handling, but I modified it lately by getting ValidationErrorMessages , but in `Controller` layer. the validation functionality should be moved in `Service` layer.and send    `ValidationErrorMessages` as a list of Json with field(key) andmessage(value).
   5. Unit testing for service/controller layers are done + integration test.
   6. integration test cover all cases, but validation is tested only in integration test. after moving validation into `service` layer,it could be tested also there into `service` layer


# Resources
 
   1. [Paging and sorting operations](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html)
   2. [Crud Operations](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
   3. [Spring Boot ](https://spring.io/projects/spring-boot)
   4. [Spring boot initializer  and import dependencies](https://start.spring.io/)
