package com.cretasom.servics_rating.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

	public List<RatingDTO> getAllRatingMultiple(String requestId) {
		requestId = StringUtils.hasText(requestId) ? requestId : UUID.randomUUID().toString();
		logger.info(requestId + " : getAllRatingMultiple: Start get all ratings");
		List<Rating> ratingList = repo.findAll();
		logger.info(requestId + " : Found all ratings [{}]", ratingList.size());
		String hotelUri = "http://HOTEL-SERVICES/hotel/get/";
		String usersUri = "http://USER-SERVICES/users/get/345345";
//

		logger.info(requestId + " : Start processing ratings");
		List<RatingDTO> ratingDtoList = new ArrayList<>();
		for (Rating r : ratingList) {
			logger.info(requestId + " : start processing for rating [{}]", r);
			RatingDTO rd = new RatingDTO();
			rd.setComment(r.getComment());
			rd.setRating(r.getRating());
			logger.info(requestId + " : get hotel for hotelId [{}]", r.getHotelId());
			Hotel h = restTemplate.getForObject(hotelUri + r.getHotelId(), Hotel.class);
			logger.info(requestId + " : got hotel for hotelId [{}]", h);
			rd.setHotelName(h.getName());
			logger.info(requestId + " : get user for userid [{}]", r.getUserId());
			User u = restTemplate.getForObject(usersUri + r.getUserId(), User.class);
			logger.info(requestId + ": got user for userid [{}]", u);

			ratingDtoList.add(rd);
			logger.info(requestId + ": start processing for rating [{}]", r);
		}
		logger.info(requestId + " : complete processing ratings");
		return ratingDtoList;
	}

	public List<RatingDTO> getAllRatingSingle(String requestId) {
		requestId = StringUtils.hasText(requestId) ? requestId : UUID.randomUUID().toString();
		logger.info(requestId + " : getAllRatingSingle: Start get all ratings");
		List<Rating> ratingList = repo.findAll();
		logger.info(requestId + " : Found all ratings [{}]", ratingList.size());
		String hotelUri = "http://HOTEL-SERVICES/hotel/get-multiple/";
		String usersUri = "http://USER-SERVICES/users/get-multiple/";
//1,2,4
		String hotelIdList = ratingList.stream().map(r -> r.getHotelId()).distinct().collect(Collectors.joining(","));
		logger.info(requestId + " : get hotel list for hotelIds [{}]", hotelIdList);
		HttpEntity<String> hotelRequest = new HttpEntity<String>(hotelIdList);
		List<Hotel> hotelList = restTemplate
				.exchange(hotelUri, HttpMethod.POST, hotelRequest, new ParameterizedTypeReference<List<Hotel>>() {
				}).getBody();
		logger.info(requestId + " : got hotel list for hotelIds [{}]", hotelList.size());

		String userIdList = ratingList.stream().map(r -> r.getUserId()).distinct().collect(Collectors.joining(","));
		HttpEntity<String> userRequest = new HttpEntity<String>(userIdList);
		logger.info(requestId + " : get user list for userIds [{}]", userIdList);
		List<User> userList = restTemplate
				.exchange(usersUri, HttpMethod.POST, userRequest, new ParameterizedTypeReference<List<User>>() {
				}).getBody();
		logger.info(requestId + " : got user list for userIds [{}]", userList.size());
		logger.info(requestId + " : Start processing ratings");
		List<RatingDTO> ratingDtoList = new ArrayList<>();
		for (Rating r : ratingList) {
			RatingDTO rd = new RatingDTO();
			rd.setComment(r.getComment());
			rd.setRating(r.getRating());
			String hotelName = hotelList.stream().filter(h -> h.getId().equals(r.getHotelId())).findFirst().get()
					.getName();

			// Hotel h = restTemplate.getForObject(hotelUri + r.getHotelId(), Hotel.class);
			// logger.info(requestId + " : got hotel for hotelId [{}]", h);
			rd.setHotelName(hotelName);
//			logger.info(requestId + " : get user for userid [{}]", r.getUserId());
			// User u = restTemplate.getForObject(usersUri + r.getUserId(), User.class);
			// logger.info(requestId + ": got user for userid [{}]", u);
			String userName = userList.stream().filter(u -> u.getId().equals(r.getUserId())).findFirst().get()
					.getName();
			rd.setUserName(userName);

			ratingDtoList.add(rd);
		}
		logger.info(requestId + " : complete processing ratings");
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
