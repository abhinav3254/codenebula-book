package com.multi.service;


import com.multi.entity.Movies;
import com.multi.repo.MovieRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    // Autowired MovieRepo for database operations
    @Autowired
    private MovieRepo movieRepo;


    /**
     * Adds a single movie to the database.
     *
     * @param movies The movie to be added.
     * @return ResponseEntity with a success message or an error message.
     */
    public ResponseEntity<String> addMovies(Movies movies) {
        try {
            movieRepo.save(movies);
            return new ResponseEntity<>("movies saved",HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("add movie went into some issue",e);
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieves all movies from the database with pagination.
     *
     * @param page Page number for pagination.
     * @param size Number of movies per page.
     * @return ResponseEntity with a page of movies or an error message.
     */
    public ResponseEntity<Page<Movies>> getAllMovies(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Movies> movies = movieRepo.findAll(pageable);
            return new ResponseEntity<>(movies,HttpStatus.OK);
        } catch (Exception e) {
            logger.error("can't fetch movies right now",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Adds multiple movies to the database.
     *
     * @param list List of movies to be added.
     * @return ResponseEntity with a success message or an error message.
     */
    public ResponseEntity<String> addMultipleMovies(List<Movies> list) {
        try {

                for (Movies movies:list) {
                    movieRepo.save(movies);
                }

                return new ResponseEntity<>("all movies added",HttpStatus.OK);

        } catch (Exception e) {
            logger.error("add multiple movies gives an error ",e);
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}