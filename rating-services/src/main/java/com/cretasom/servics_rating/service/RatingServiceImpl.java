package com.cretasom.servics_rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cretasom.servics_rating.entity.Rating;
import com.cretasom.servics_rating.entity.RatingDTO;
import com.cretasom.servics_rating.entity.external.Hotel;
import com.cretasom.servics_rating.entity.external.User;
import com.cretasom.servics_rating.repo.RatingRepo;
import com.cretasom.servics_rating.repo.external.HotelRepo;
import com.cretasom.servics_rating.repo.external.UserRepo;

@Service
public class RatingServiceImpl {

	@Autowired
	RatingRepo repo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	HotelRepo hotelRepo;
//	List<Rating> ratingList = new ArrayList<>();
//	private Set<Integer> userIdList = new HashSet<>();
//	private Set<Integer> hotelIdList = new HashSet<>();
//	int i = 1;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${validate.data}")
	private boolean validateData;

	Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private HotelServiceImpl hotelImpl;
//	@Autowired
//	private UserServiceImpl userImpl;

	public Rating addRating(Rating rating) {

		if (validateData && hotelRepo.findById(rating.getHotelId()).isEmpty()) {
			throw new RuntimeException("hotel id not valid");
		}
		if (validateData && userRepo.findById(rating.getUserId()).isEmpty()) {
			throw new RuntimeException("user id not valid");
		}
		return repo.save(rating);

	}

	public Rating getRating(String id) {
		// id 1,2,4
		Optional<Rating> rating = repo.findById(id);

		return rating.isPresent() ? rating.get() : null;
	}

	public List<RatingDTO> getAllRating() {
		List<Rating> ratingList = repo.findAll();
		String hotelUri = "http://HOTEL-SERVICES/hotel/get/";
		String usersUri = "http://USER-SERVICES/users/get/";
//		// return ratingList;
//
		List<RatingDTO> ratingDtoList = new ArrayList<>();
		for (Rating r : ratingList) {
			RatingDTO rd = new RatingDTO();
			rd.setComment(r.getComment());
			rd.setRating(r.getRating());
			Hotel h = restTemplate.getForObject(hotelUri + r.getHotelId(), Hotel.class);
			rd.setHotelName(h.getName());
			User u = restTemplate.getForObject(usersUri + r.getUserId(), User.class);
			rd.setUserName(u.getName());
			ratingDtoList.add(rd);
		}
		return ratingDtoList;
	}

	public void addUserId(String userId) {
		User user = new User();
		user.setId(userId);
		userRepo.save(user);
		// userIdList.add(userId);
	}

	public void addHotelId(String hotelId) {
		Hotel h = new Hotel();
		h.setId(hotelId);
		hotelRepo.save(h);
		// hotelIdList.add(hotelId);
	}

}
