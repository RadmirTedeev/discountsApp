package com.example.project.service;

import com.example.project.entity.Discount;
import com.example.project.entity.Product;
import com.example.project.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService{
    private DiscountRepository discountRepository;

    @Autowired
    public void setDiscountRepository(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discount> getDiscounts () {
        return discountRepository.findAll(Sort.by("id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Discount getDiscountById (Long id) {
        return discountRepository.getById(id);
    }

    @Override
    @Transactional
    public void saveDiscount (Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    @Transactional
    public Discount saveAndReturnDiscounts (Discount discount) {
        discountRepository.save(discount);
        return discount;
    }

    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        Discount discount = getDiscountById(id);
        for (Product p : discount.getProducts()) {
            p.setDiscount(null);
        }
        discount.setProducts(null);
        discountRepository.deleteById(id);
    }
}
