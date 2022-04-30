package com.example.project.service;

import com.example.project.entity.Discount;

import java.util.List;

public interface DiscountService {

    List<Discount> getDiscounts ();

    Discount getDiscountById (Long id);

    void saveDiscount (Discount discount);

    Discount saveAndReturnDiscounts (Discount discount);

    void deleteDiscount(Long id);
}
