package com.protik.dealershipproject.service;

import com.protik.dealershipproject.entity.Car;
import com.protik.dealershipproject.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Car> searchByMakeOrModel(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String q = query.trim();
        return carRepository.findByMakeContainingIgnoreCaseOrModelContainingIgnoreCase(q, q);
    }

    @Transactional(readOnly = true)
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Transactional
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return carRepository.count();
    }
}
