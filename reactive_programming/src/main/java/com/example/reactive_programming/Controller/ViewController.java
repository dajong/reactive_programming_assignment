package com.example.reactive_programming.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/getBeer")
    public String displayBeer(@RequestParam(required = false) String minPrice,
                              @RequestParam(required = false) String maxPrice,
                              @RequestParam(required = false) String minRating,
                              Model model) {
        minPrice = (minPrice.equals("")) ? "null" : minPrice;
        maxPrice = (maxPrice.equals("")) ? "null" : maxPrice;
        minRating = (minRating.equals("")) ? "null" : minRating;

        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minRating", minRating);


        return "displayBeer";
    }
}
