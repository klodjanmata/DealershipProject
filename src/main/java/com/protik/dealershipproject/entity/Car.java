package com.protik.dealershipproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String make;
    
    private String model;
    
    private Integer year;
    
    private String color;
    
    private Double price;
    
    private Integer kilometers;
    
    private Boolean available;
}

