package com.cretasom.servics_rating.repo.external;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cretasom.servics_rating.entity.external.Hotel;

public interface HotelRepo extends MongoRepository<Hotel, String> {

}
