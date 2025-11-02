package com.protik.dealershipproject.config;

import com.protik.dealershipproject.entity.Car;
import com.protik.dealershipproject.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CarService carService;
    
    @Override
    public void run(String... args) throws Exception {
        if (carService.count() == 0) {
            carService.saveCar(new Car(null, "Toyota", "Camry", 2022, "White", 25000.0, 24000, true));
            carService.saveCar(new Car(null, "Honda", "Accord", 2023, "Black", 29000.0, 8000, true));
            carService.saveCar(new Car(null, "Ford", "F-150", 2022, "Blue", 41000.0, 32000, true));
            carService.saveCar(new Car(null, "BMW", "3 Series", 2023, "Silver", 38000.0, 13000, false));
            carService.saveCar(new Car(null, "Mercedes-Benz", "C-Class", 2022, "Black", 43000.0, 19000, true));
            carService.saveCar(new Car(null, "Audi", "A4", 2023, "Gray", 37000.0, 10000, true));
            carService.saveCar(new Car(null, "Nissan", "Altima", 2022, "Red", 23000.0, 29000, true));
            carService.saveCar(new Car(null, "Chevrolet", "Malibu", 2022, "White", 22000.0, 35000, false));
            carService.saveCar(new Car(null, "Hyundai", "Elantra", 2023, "Blue", 20000.0, 6000, true));
            carService.saveCar(new Car(null, "Mazda", "CX-5", 2023, "Red", 27000.0, 11000, true));
        }
    }
}

