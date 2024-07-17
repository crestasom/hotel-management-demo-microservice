package com.cretasom.servics_rating.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cretasom.servics_rating.entity.Rating;

public interface RatingRepo extends MongoRepository<Rating, String> {

}
