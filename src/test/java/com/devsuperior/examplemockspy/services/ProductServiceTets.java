package com.devsuperior.examplemockspy.services;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTets {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	private Product product;
	private ProductDTO productDTO;
	private Long existingId,  unexistingId;


	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		unexistingId = 2L;
		
		product = new Product( 1L, "PlayStation", 2000.00);
		productDTO = new ProductDTO(product);
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(unexistingId)).thenThrow(EntityNotFoundException.class);
		
	}

}
