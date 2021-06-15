# ProductService

# ProductService is a Spring MVC and REST API Web Service for managing a product.

## Installation

mvn clean install  ##  install the application

cd target	## change to target directory


java -jar ProductServiceApplication-1.0-SNAPSHOT.jar   ## run the application
```

## Usage

`-----``REST API  

Used Postman to test all REST APIs

import com.seiduabbas.controller

productRestController.productNotFound(ProductNotFoundException e)# returns 'HttpStatus.NOT_FOUND'

productRestController.restGetList() # list of products

productRestController.restUpdate(Product product, Long id)  # updates product the id and returns 'HttpStatus.OK '

productRestController.restSaveProduct(Product product) # saves the product and returns  HttpStatus.CREATED 

productRestController.restDeleteProduct(Long id) # deletes product with id and returns  HttpStatus.NO_CONTENT 

productRestController.restFindProductById(Long id) # returns HttpStatus.NOT_FOUND if id is incorrect.
											# else returns the product and a  HttpStatus.OK 	
											
----------Spring MVC

# Wrote JUnit and Integration for the Spring MVC part of the Program
# I could have done a lot more testing and applied ther testing mechanisms
# such as argument capture, JSON Assert, Spying etc								  

productController.list() # returns a list of products

productController.saveProduct(product) # returns URL of the saved product

productController.findProduct(id) # returns product with id if id is correct
									# ekse returns ProductNotFoundException with the incorrect id

productController.processUpdateProductForm(product, id) # returns URL of the update product and 

productController.deleteProduct( id) # deletes the product with id if id is correct 
									# else returns 	product with id  not found message  


----------Uses embedded database. H2  with Spring Data JPA and Hibernate specifically


## Contributing


## License
