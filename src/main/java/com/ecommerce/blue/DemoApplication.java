package com.ecommerce.blue;

import com.ecommerce.blue.dao.ProductRepository;
import com.ecommerce.blue.entity.Product;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class DemoApplication {

	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void insertSomeProductAtBeginningOfTheExecution(){
		List<Product> products=List.of(
				new Product(0,"Pencil set","Pencil gtx hddjhd",23.2),
				new Product(0,"D set","D gtx hddjhd",8.0),
				new Product(0,"A set","A gtx hddjhd",58.0),
				new Product(0,"E set","E gtx hddjhd",2.2),
				new Product(0,"ert set","ert gtx hddjhd",28.2),
				new Product(0,"ft set","ft gtx hddjhd",62.0)
				);
		productRepository.saveAll(products);
		log.info("Products saved at beginning");
	}

}
