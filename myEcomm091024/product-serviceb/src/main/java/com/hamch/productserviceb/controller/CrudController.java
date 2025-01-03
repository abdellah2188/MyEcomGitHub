/*
package com.hamch.productserviceb.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

//import javax.validation.constraints.Size;

import com.hamch.productserviceb.entities.Category;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.services.ICrudService;
//import net.minidev.json.JSONObject;
//import org.apache.tomcat.util.json.ParseException;
//import org.apache.tomcat.util.json.JSONParser;
//import org.apache.tomcat.util.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin("*")
//@RestController
public class CrudController<T, ID> {
	@Autowired
	private ICrudService<T, ID> service;


	@PostMapping
	public void add(@RequestBody T entity) {
		service.add(entity);
		// System.out.println(entity);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable ID id) {
		System.out.println("kkkkkkkkkkkkkkkkkkk");
		service.delete(id);
	}

	@PostMapping(path="/add/")
	public void addWithFile(@ModelAttribute("product") Object  prod,
							@RequestParam("file") MultipartFile file) throws IOException, org.apache.tomcat.util.json.ParseException, ParseException {

		System.out.println(prod+"RRRRRRRRR"+file.getBytes());
		Product product= new Product();
		JSONParser jsonP =new JSONParser();
		System.out.println("SSSS"+jsonP.parse(String.valueOf(prod)));
		JSONObject pro=(JSONObject) jsonP.parse(String.valueOf(prod));
		System.out.println("CCCCC"+pro.get("category"));

		JSONObject  categ=(JSONObject) pro.get("category");

		JSONObject cat = new JSONObject((Map) categ);

		System.out.println("WWWWW"+cat);
*/
/*
		JSONObject pro=(JSONObject) jsonP.parse(); pro.get("category");
		System.out.println("SSSS"+pro.get("category"));
		JSONObject categ=(JSONObject) pro.get("category");
		//System.out.println("SSSS"+prod.getCategory());
		//JSONObject categ = (JSONObject)  prod.get("category");
	//	Category categ=prod.getCategory();
		System.out.println("CCCCCCCCC"+categ);

		JSONObject cat = new JSONObject((Map) categ);
		System.out.println("FFFFFFFFFFFF"+cat.get("id"));

		Category category =new Category();
		category.setName((String) cat.get("name"));
		category.setId(Long.valueOf((Long) cat.get("id")));
		category.setDescription((String) cat.get("description"));
		category.setProducts((Collection<Product>) cat.get("products"));

		System.out.println("uuuuuuuuuu"+ category);
*//*

*/
/*

		var name= pro.get("name");
		var description= pro.get("description");
		var available= pro.get("available");
		var currentPrice= pro.get("currentPrice");
		var promotion= pro.get("promotion");
		var photoName= pro.get("photoName");
		var selected= pro.get("selected");


		product.setName((String) name);
		product.setDescription((String) description);
		product.setAvailable((Boolean) available);
		product.setPrice((Double) currentPrice);
		product.setPromotion((Boolean) promotion);
		product.setPhotoName((String) photoName);
		product.setSelected((Boolean) selected);
		product.setCategory(category);

		System.out.println("eeeeee"+ product);

		service.addWithFile((Product) product, (MultipartFile) file);
*//*
	}
}
*/
