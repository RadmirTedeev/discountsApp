package com.example.project.controller;

import com.example.project.ProjectApplication;
import com.example.project.entity.Discount;
import com.example.project.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProjectApplication.class)
@AutoConfigureMockMvc
public class DiscountRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    DiscountService discountService;

    Discount discount1 = new Discount(1L, "Бонусная неделя апреля", "25.04.2022", "01.05.2022", null);
    Discount discount2 = new Discount(2L, "Бонусная неделя мая", "02.05.2022", "08.05.2022", null);
    Discount discount3 = new Discount(3L, "Бонусная неделя июня", "06.06.2022", "13.06.2022", null);

    @Test
    public void getAllDiscountsSuccess() throws Exception {
        List<Discount> discountList = new ArrayList<>(Arrays.asList(discount1, discount2, discount3));

        Mockito.when(discountService.getDiscounts()).thenReturn(discountList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/discounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Бонусная неделя мая")));
    }

    @Test
    public void getDiscountByIdSuccess() throws Exception {
        Mockito.when(discountService.getDiscountById(discount1.getId())).thenReturn(discount1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/discounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Бонусная неделя апреля")));
    }

    @Test
    public void createDiscountSuccess() throws Exception {
        Discount discount = new Discount();
        discount.setName("Вторая бонусная неделя мая");
        discount.setStartDate("16.05.2022");
        discount.setEndDate("22.05.2022");

        Mockito.when(discountService.saveAndReturnDiscounts(discount)).thenReturn(discount);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(discount));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Вторая бонусная неделя мая")));
    }

    @Test
    public void updateDiscountSuccess() throws Exception {
        Discount updatedDiscount = new Discount();
        updatedDiscount.setId(2L);
        updatedDiscount.setName("Измененная бонусная неделя мая");
        updatedDiscount.setStartDate("17.05.2022");
        updatedDiscount.setEndDate("23.05.2022");

        Mockito.when(discountService.getDiscountById(discount2.getId())).thenReturn(discount2);
        Mockito.when(discountService.saveAndReturnDiscounts(updatedDiscount)).thenReturn(updatedDiscount);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/discounts/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedDiscount));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Измененная бонусная неделя мая")));
    }

    @Test
    public void deleteDiscountByIdSuccess() throws Exception {
        Mockito.when(discountService.getDiscountById(discount1.getId())).thenReturn(discount1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/discounts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
