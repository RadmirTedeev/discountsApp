package com.example.project.controller;

import com.example.project.entity.Discount;
import com.example.project.entity.Product;
import com.example.project.service.DiscountService;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    DiscountService discountService;
    ProductService productService;

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showDiscountsPage(Model model) {
        List<Discount> discounts = discountService.getDiscounts();
        model.addAttribute("discounts", discounts);
        return "discount/discounts-page";
    }

    @GetMapping("/add")
    public String showAddDiscountForm(Model model) {
        Discount discount = new Discount();
        model.addAttribute("discount", discount);
        return "discount/add-discount-page";
    }

    @GetMapping("/edit/{id}")
    public String showEditDiscountForm(Model model, @PathVariable(value = "id") Long id) {
        Discount discount = discountService.getDiscountById(id);
        Set<Product> discountedProducts = discount.getProducts();
        Set<Product> productsWithoutDiscount = productService.findProductsWithoutDiscount(id);
        model.addAttribute("discount", discount);
        model.addAttribute("discountedProducts", discountedProducts);
        model.addAttribute("productsWithoutDiscount", productsWithoutDiscount);
        return "discount/edit-discount-page";
    }

    @GetMapping("edit/{discountId}/delete/{productId}")
    public String deleteProductFromDiscount(@PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);
        product.setDiscount(null);
        productService.saveProduct(product);
        return "redirect:/discounts/edit/{discountId}";
    }

    @GetMapping("edit/{discountId}/add/{productId}")
    public String addProductToDiscount(@PathVariable(value = "productId") Long productId,
                                       @PathVariable(value = "discountId") Long discountId) {
        Product product = productService.getProductById(productId);
        product.setDiscount(discountService.getDiscountById(discountId));
        productService.saveProduct(product);
        return "redirect:/discounts/edit/{discountId}";
    }

    @PostMapping("/edit")
    public String addDiscount(@ModelAttribute(value = "discount") Discount discount) {
        discountService.saveDiscount(discount);
        return "redirect:/discounts";
    }

    @GetMapping("/show/{id}")
    public String showDiscount(Model model, @PathVariable(value = "id") Long id) {
        Discount discount = discountService.getDiscountById(id);
        Set<Product> products = discount.getProducts();
        model.addAttribute("discount", discount);
        model.addAttribute("products", products);
        return "discount/discount-page";
    }

    @GetMapping("delete/{id}")
    public String deleteDiscount(@PathVariable(value = "id") Long id) {
        discountService.deleteDiscount(id);
        return "redirect:/discounts";
    }
}
