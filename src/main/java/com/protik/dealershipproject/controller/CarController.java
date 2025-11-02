package com.protik.dealershipproject.controller;

import com.protik.dealershipproject.entity.Car;
import com.protik.dealershipproject.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class CarController {
    
    @Autowired
    private CarService carService;
    
    @GetMapping("/cars")
    public String getAllCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "cars";
    }
    
    @GetMapping("/cars/search")
    public String searchCars(@RequestParam(value = "q", required = false) String q, Model model) {
        List<Car> cars = (q == null || q.trim().isEmpty())
                ? Collections.emptyList()
                : carService.searchByMakeOrModel(q);
        model.addAttribute("cars", cars);
        model.addAttribute("q", q == null ? "" : q);
        return "cars-search";
    }
}

