package com.example.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CatalogItem {
    private String name;
    private String desc;
    private int rating;
}
