package com.protik.dealershipproject.controller;

import com.protik.dealershipproject.dto.CarForm;
import com.protik.dealershipproject.entity.Car;
import com.protik.dealershipproject.service.CarService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ModelAttribute("carForm")
    public CarForm carForm() {
        CarForm form = new CarForm();
        form.setAvailable(Boolean.TRUE);
        return form;
    }

    @GetMapping()
    public String getAllCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "cars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public String searchCars(@RequestParam(value = "q", required = false) String q, Model model) {
        List<Car> cars = (q == null || q.trim().isEmpty())
                ? Collections.emptyList()
                : carService.searchByMakeOrModel(q);
        model.addAttribute("cars", cars);
        model.addAttribute("q", q == null ? "" : q);
        return "cars-search";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String saveCar(@Valid @ModelAttribute("carForm") CarForm carForm,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        Car targetCar = null;
        if (carForm.getId() != null) {
            targetCar = carService.findById(carForm.getId()).orElse(null);
            if (targetCar == null) {
                bindingResult.rejectValue("id", "carForm.id", "Selected car no longer exists.");
            }
        } else {
            targetCar = new Car();
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("cars", carService.getAllCars());
            if (carForm.getId() != null) {
                model.addAttribute("selectedCarId", carForm.getId());
            }
            return "cars";
        }

        targetCar.setMake(carForm.getMake().trim());
        targetCar.setModel(carForm.getModel().trim());
        targetCar.setYear(carForm.getYear());
        targetCar.setColor(carForm.getColor().trim());
        targetCar.setPrice(carForm.getPrice().doubleValue());
        targetCar.setKilometers(carForm.getKilometers());
        targetCar.setAvailable(carForm.getAvailable());

        Car saved = carService.saveCar(targetCar);
        redirectAttributes.addFlashAttribute("successMessage",
                carForm.getId() == null ? "Car created successfully." : "Car updated successfully.");
        redirectAttributes.addFlashAttribute("selectedCarId", saved.getId());
        return "redirect:/cars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String deleteCar(@RequestParam("id") Long id,
                            RedirectAttributes redirectAttributes) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a car to delete.");
            return "redirect:/cars";
        }
        if (carService.findById(id).isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The selected car no longer exists.");
            return "redirect:/cars";
        }

        carService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Car deleted successfully.");
        return "redirect:/cars";
    }
}
