package com.example.project.controller;

import com.example.project.entity.Discount;
import com.example.project.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/discounts")
public class DiscountRestController {
    private DiscountService discountService;

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getDiscounts();
    }

    @GetMapping("{id}")
    public Discount getDiscount(@PathVariable("id") Discount discount) {
        return discount;
    }

    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        discountService.saveAndReturnDiscounts(discount);
        return discount;
    }

    @PutMapping("{id}")
    public Discount updateDiscount(@PathVariable("id") Discount discountFromDb, @RequestBody Discount discount) {
        discountFromDb.setName(discount.getName());
        discountFromDb.setStartDate(discount.getStartDate());
        discountFromDb.setEndDate(discount.getEndDate());
        discountFromDb.setProducts(discount.getProducts());
        discountService.saveDiscount(discountFromDb);
        return discountFromDb;
    }

    @DeleteMapping("{id}")
    public void deleteDiscount(@PathVariable("id") Discount discount) {
        discountService.deleteDiscount(discount.getId());
    }
}
