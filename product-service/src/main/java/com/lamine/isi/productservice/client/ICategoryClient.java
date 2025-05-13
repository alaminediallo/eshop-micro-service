package com.lamine.isi.productservice.client;

import com.lamine.isi.productservice.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface ICategoryClient {
    @GetMapping("api/categories/{id}")
    CategoryDTO getCategoryById(@PathVariable("id") Long id);
}
