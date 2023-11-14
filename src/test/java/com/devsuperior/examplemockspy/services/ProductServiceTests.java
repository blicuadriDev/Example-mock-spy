package com.devsuperior.examplemockspy.services;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import com.devsuperior.examplemockspy.services.exceptions.InvalidDataException;
import com.devsuperior.examplemockspy.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
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
	
	
	//INSERT TESTS
	
	@Test
	public void insertShouldReturnProductDTOWhenValidData() {
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);
		
		ProductDTO result = serviceSpy.insert(productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getName(), "PlayStation");
	}
	
	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductNameIsBlank() {
		productDTO.setName("");
		
		ProductService serviceSpy = Mockito.spy(service);
		
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, ()-> {
			@SuppressWarnings("unused")
			ProductDTO result = service.insert(productDTO);
		});
	}
	
	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductProceIsZeroOrNegative() {
		productDTO.setPrice(-10.00);
		
		ProductService serviceSpy = Mockito.spy(service);
		
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, ()-> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.insert(productDTO);
		});
	}
	
	
	//UPDATE TESTS
	@Test
	public void updateShouldReturnProductDTOWhenIdExistsAndValidData() {
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);
		
		ProductDTO result = serviceSpy.update(existingId, productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductNameIsBlank() {
		productDTO.setName("");
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () ->{
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
	
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductPriceIsZeroOrNegative() {
		productDTO.setPrice(-10.00);
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () ->{
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExistsAndValidData() {
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(unexistingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdDoesNotExistAndProductNameIsBlank() {
		
		productDTO.setName("");
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, ()->{
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(unexistingId, productDTO);
		});
	}
	
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdDoesNotExistAndProductPriceIsZeroOrNegative() {
		
		productDTO.setPrice(-10.00);
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, ()->{
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(unexistingId, productDTO);
		});
	}

}
