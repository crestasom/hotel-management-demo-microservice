package com.cretasom.servics_rating.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cretasom.servics_rating.entity.Rating;
import com.cretasom.servics_rating.entity.RatingDTO;
import com.cretasom.servics_rating.entity.external.Hotel;
import com.cretasom.servics_rating.entity.external.User;

@Service
public class RatingServiceImpl {

	List<Rating> ratingList = new ArrayList<>();
	int i = 1;

//	@Autowired
//	private HotelServiceImpl hotelImpl;
//	@Autowired
//	private UserServiceImpl userImpl;

	public Rating addRating(Rating rating) {
		// TODO Auto-generated method stub
		rating.setId(i);
		i++;
		ratingList.add(rating);
		return rating;
	}

	public Rating getRating(int id) {
		// id 1,2,4
		for (Rating u : ratingList) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}

	public List<RatingDTO> getAllRating() {
		RestTemplate restTemplate = new RestTemplate();
		String hotelUri = "http://localhost:8090/hotel/get/";
		String usersUri = "http://localhost:8091/users/get/";
		// return ratingList;

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

}
