package com.cretasom.servics_rating.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cretasom.servics_rating.entity.external.Hotel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelService {

	@Autowired
	private RestTemplate restTemplate;

	@CircuitBreaker(name = "hotelService", fallbackMethod = "fetchHotelListFallback")
	public List<Hotel> fetchHotelList(String hotelIdList) {
		String hotelUri = "http://HOTEL-SERVICES/hotel/get-multiple/";
		HttpEntity<String> hotelRequest = new HttpEntity<String>(hotelIdList);
		List<Hotel> hotelList = restTemplate
				.exchange(hotelUri, HttpMethod.POST, hotelRequest, new ParameterizedTypeReference<List<Hotel>>() {
				}).getBody();
		return hotelList;
	}

	public List<Hotel> fetchHotelListFallback(String hotelIdList, Throwable ex) {
		log.error("exception while fetching hotel list", ex);
		return new ArrayList<>();
	}
}
