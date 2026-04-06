package com.hamch.productserviceb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hamch.productserviceb.dto.ProductDTO;
import com.hamch.productserviceb.entities.Product;

@Service
public class ProductMapper {
    public ModelMapper modelMapper=new ModelMapper();

    public ProductDTO fromProduct(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }
    public Product fromProductDTO(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }
    public List<ProductDTO> fromListProducts(List<Product> products){
        return products.stream().map(p->modelMapper.map(p, ProductDTO   .class)).collect(Collectors.toList());
    }
    
}