package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductsPage(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "product/products-page";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/add-product-page";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(Model model, @PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/edit-product-page";
    }

    @PostMapping("/edit")
    public String saveProduct(@ModelAttribute(value = "product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProductPage(Model model, @PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/product-page";
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
