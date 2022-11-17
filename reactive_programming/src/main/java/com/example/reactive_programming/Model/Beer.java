package com.example.reactive_programming.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Beer {
    private String price;
    private String name;
    private Rating rating;
    private String image;
    private int id;
}
