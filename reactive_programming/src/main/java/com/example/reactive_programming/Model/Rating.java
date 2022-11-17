package com.example.reactive_programming.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Rating {
    private double average;
    private int reviews;
}
