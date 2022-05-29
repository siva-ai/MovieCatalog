package com.example.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MovieDetails {
    private String movieId;
    private int rating;
    private String story;
    private List actors;
    private String dialogue;
    private String music_director;
    private List singers;
    private String choreographer;
}
