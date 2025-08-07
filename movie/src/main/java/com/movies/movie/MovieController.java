package com.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieRepo.findAll());
        return "index";
    }
    @PostMapping("/add-movie")
    public String addMovie(@RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam String description,
                           @RequestParam Integer releaseYear) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setDescription(description);
        movie.setReleaseYear(releaseYear);
        movieRepo.save(movie);
        return "redirect:/";
    }

    @PostMapping("/add-review")
    public String addReview(@RequestParam Long movieId,
                            @RequestParam String reviewer,
                            @RequestParam String content,
                            @RequestParam Integer rating) {
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setContent(content);
        review.setRating(rating);

        Movie movie = movieRepo.findById(movieId).orElse(null);
        if (movie != null) {
            review.setMovie(movie);
            reviewRepo.save(review);
        }

        return "redirect:/";
    }

}
