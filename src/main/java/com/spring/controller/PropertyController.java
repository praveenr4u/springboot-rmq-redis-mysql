package com.spring.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Product;
import com.spring.entity.Property;
import com.spring.repository.ProductRepository;
import com.spring.service.PropertyService;
import com.spring.service.RedisPropertyService;

@RestController
public class PropertyController {
	
	@Autowired
	private RedisPropertyService propertyService;
	
	@Autowired
	private ProductRepository productrepo;
	
	@Autowired
	private com.spring.service.QueueService queueService;

	@GetMapping("/property")
	public List<Property> retriveAllProperties() {
		return propertyService.retriveAllProperties();
	}
	
	@PostMapping("/property")
	public ResponseEntity<Object> addProperty(@RequestBody Property property) {
		queueService.sendQueue(property);
	     return propertyService.addProperty(property);
	  }
	
	@GetMapping("/property/{id}")
	@Cacheable(value = "properties",key = "#propertyId")
	public Optional<Property> getPropertyById(@PathVariable Integer id) {
		return propertyService.getPropertyById(id);
	}
	
	@DeleteMapping("/property/{id}")
	@CacheEvict(value = "employees", allEntries = true)
	public void deletePropertyById(@PathVariable Integer id) {
		propertyService.deletePropertyById(id);
		
	}
	@PatchMapping("/property")
	@CachePut(value = "properties",key = "#propertyId")
	public ResponseEntity<Object> updateProperty(@RequestBody Property property) {
	     return propertyService.updateProperty(property);
	  }
	
	// This part is using Redis cache as primary DB
	
	
	@PostMapping("/product")
	public Product save(@RequestBody Product product) {
	   return productrepo.save(product);
	}
	   
	@GetMapping("/product")
    public List<Product> getAllProducts() {
        return productrepo.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return productrepo.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
        return productrepo.deleteProduct(id);
    }
}
