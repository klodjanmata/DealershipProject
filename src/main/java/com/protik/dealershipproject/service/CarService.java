package com.protik.dealershipproject.service;

import com.protik.dealershipproject.entity.Car;
import com.protik.dealershipproject.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;
    
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
    
    public List<Car> searchByMakeOrModel(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String q = query.trim();
        return carRepository.findByMakeContainingIgnoreCaseOrModelContainingIgnoreCase(q, q);
    }
    
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
    
    public long count() {
        return carRepository.count();
    }
}

