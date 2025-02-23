package com.hamch.productserviceb.controller;

import com.hamch.productserviceb.entities.Category;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.repository.ProductRepository;
import com.hamch.productserviceb.services.impl.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/product")
//@RequiredArgsConstructor
@Slf4j
public class ProductController  {

    @Autowired
    private ProductService service;
    @Autowired
    private final ProductRepository productRepository;


    private ProductController(ProductRepository productRepository){
        this.productRepository= productRepository;
    }

    @GetMapping("/products/{id}")
    Product isInStock(@PathVariable Long id) {
        System.out.println("TTTTTTTTTTTTT");
        log.info("Checking stock for product with id - " + id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot Find Product by sku code " + id));
        System.out.println("MMMMMMMMMM"+ product.getStock());
        return product;
        //return inventory;
    }
    
    @GetMapping("/products/{id2})")
       Object fincCategory( @PathVariable("id2") Long id2) throws ParseException {
       System.out.println("GGGGGGGGGGG"+ id2);
     //   int ID =(id);
    //   Optional<Product> p = productRepository.findById(id);
        Optional<Product> p= productRepository.findById(id2);
        
        System.out.println(p+"JJJJJJJJJJJJJJ"+ p.get().getCategory().getName());
        
        JSONParser jsonP =new JSONParser();
        
        Category cat = p.get().getCategory();
          
        
        
        String name = ((Category) cat).getName();
        Long id = ((Category) cat).getId();
   //     Object cat2 = jsonP.stringify(cat);
        
  //       Object cat3 = ((Object) jsonP).stringify(cat);
        
      
      Object cat2= "{"+    "\"id\"" +":" +  id  +      ",\"name\"" +":" + "\""+  name + "\"" +"}";
      
      //Object cat3 =  jsonP.parse(String.valueOf(cat2));
       
      System.out.println("MMMMMMMMMMMMMMMMM"+cat+"WWWWWWWWWWWWWWWW"+ cat2);
     
      return   cat2;
   }
    
    @PostMapping(path="/add/")
    public void addWithFile(@ModelAttribute("product") Object  prod,
                            @RequestParam("file") MultipartFile file) throws IOException, org.apache.tomcat.util.json.ParseException, ParseException {

        System.out.println(prod+"RRRRRRRRR"+file.getBytes());
        Product  product=new Product();

        JSONParser jsonP =new JSONParser();
        System.out.println("SSSS"+jsonP.parse((String) prod));
        JSONObject pro=(JSONObject) jsonP.parse(String.valueOf(prod));
        System.out.println("CCCCC"+pro.get("available"));

        JSONObject  categ=(JSONObject) pro.get("category");
        System.out.println("GGGG"+categ.get("id"));

        JSONObject cat = new JSONObject((Map) categ);
        System.out.println("JJJJJ"+cat);

		Category category =new Category();
		category.setName((String) cat.get("name"));
		category.setId((java.lang.Long) cat.get("id"));
	//	category.setDescription((String) cat.get("description"));
		category.setProducts((Collection<com.hamch.productserviceb.entities.Product>) cat.get("products"));

		System.out.println("uuuuuuuuuu"+ category);

	    var name= pro.get("name");
		var description= pro.get("description");
        var   available= pro.get("available");
        if(available =="")  available= false;
       // var available = false;
		var currentPrice= pro.get("currentPrice");
		var promotion= pro.get("promotion");
        if(promotion =="")  promotion= false;
		var photoName= pro.get("photoName");
		var selected= pro.get("selected");
        if(selected =="")  selected= false;


		product.setName((String) name);
		product.setDescription((String) description);
		product.setAvailable((Boolean) available);
		product.setPrice((long) currentPrice);
		product.setPromotion((Boolean) promotion);

		product.setPhotoName((String) photoName);
		product.setSelected((Boolean) selected);
		product.setCategory(category);

		System.out.println("eeeeee"+ product);

		service.addWithFile((Product) product, (MultipartFile) file);
	}

    @DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id")  long id) {
		System.out.println("kkkkkkkkkkkkkkkkkkk");
		service.delete(id);
	}
    
    @PutMapping("/upProduct")
    public void upProduct(@ModelAttribute("product") Object  prod) throws IOException, org.apache.tomcat.util.json.ParseException, ParseException {

		
		  System.out.println(id +"AAAAAAAAAAAAAAA"+ prod); 
		  Product product=new  Product();
		  
		  JSONParser jsonP =new JSONParser();
		  JSONObject jsonObject ;
		  
		//   Product pr= (Product) (jsonP.parse((String) prod));
	//	   jsonObject=(org.json.simple.JSONObject)pr;
		  
		  System.out.println("SSSS"+jsonP.parse((String) prod));
		  
		  JSONObject pro=(JSONObject) jsonP.parse(String.valueOf(prod));
		  System.out.println(pro+"CCCCC"+pro.get("available"));
		  
		  JSONObject categ=(JSONObject) pro.get("category");
		  System.out.println("GGGG"+categ.get("id"));
		  
		  JSONObject cat = new JSONObject((Map) categ);
		  System.out.println("JJJJJ"+cat);
		  
		  Category category =new Category(); category.setName((String)
		  cat.get("name")); category.setId((java.lang.Long) cat.get("id"));
	//	  category.setDescription((String) cat.get("description"));
		  category.setProducts((Collection<com.hamch.productserviceb.entities.Product>)
		  cat.get("products"));
		  
		  System.out.println("uuuuuuuuuu"+ category);
		  
		  var id  = pro.get("id");
		  var name= pro.get("name"); 
		  var description= pro.get("description"); 
		  var available= pro.get("available"); 
		  if(available =="") available= false; 
		  // var  available = false; 
		  var currentPrice= pro.get("currentPrice");
		  var stock= pro.get("stock"); 
		  var promotion= pro.get("promotion"); 
		  if(promotion =="")  promotion= false;
	        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+ pro.get("photoName"));
		  var photoName= pro.get("photoName");
		  var selected= pro.get("selected"); 
		  if(selected =="") selected= false;
		  
		  product.setId((long) id); 
		  product.setName((String) name); 
		  product.setDescription((String) description);
		  product.setAvailable((Boolean) available);
		  product.setPrice((long) currentPrice); 
		  product.setStock((long) stock); 
		  product.setPromotion((Boolean) promotion);
	       
		  product.setPhotoName((String) photoName); 
		  System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx"+ product.getPhotoName());
		  product.setSelected((Boolean) selected); 
		  product.setCategory(category);
		  
		  System.out.println("eeeeee"+ product);
		 
		service.upProduct(   (Product) product);
		
	}
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        System.out.println("hhhhhhhhhhh");
        return (List<Product>) productRepository.findAll();
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {

        productRepository.save(product);
    }*/

    @GetMapping(path="/photoProduct/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") long id) throws Exception{
        System.out.println("EEEEEEEEEEEEEEEEEEE");
        Product p=(Product) productRepository.findById(id).get();
        System.out.println("NNNNNNNNNNNN"+ p);
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecommerce/products/"+ p.getPhotoName()));
    }
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
    	
    	Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.SECOND)+"RRRRRRRRRRRXXXXXXXXRRRRRRRRRRRRR"+ file);
        com.hamch.productserviceb.entities.Product p=productRepository.findById((java.lang.Long) id).get();
        
        //p.setPhotoName(file.getOriginalFilename());
        
       
        ((com.hamch.productserviceb.entities.Product) p).setPhotoName(id+".png");
        
        Files.write(Paths.get(System.getProperty("user.home")+"/ecommerce/products/"+ ((com.hamch.productserviceb.entities.Product) p).getPhotoName()),file.getBytes());
        productRepository.save(p);

    }
}
