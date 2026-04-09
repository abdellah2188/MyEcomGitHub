package com.hamch.productserviceb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hamch.productserviceb.dto.CategoryDTO;
import com.hamch.productserviceb.entities.Category;

@Service
public class CategoryMapper {
    public ModelMapper modelMapper=new ModelMapper();

    public CategoryDTO fromCategory(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }
    public Category fromCategoryDTO(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }
    public List<CategoryDTO> fromListCategories(List<Category> categories){
        return categories.stream().map(p->modelMapper.map(p, CategoryDTO   .class)).collect(Collectors.toList());
    }
    
}