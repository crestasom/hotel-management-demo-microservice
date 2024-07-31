package com.cretasom.servics_hotel.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cretasom.servics_hotel.config.MqttGateway;
import com.cretasom.servics_hotel.entity.Hotel;
import com.cretasom.servics_hotel.repo.HotelRepo;

@Service
public class HotelServiceImpl {

	@Autowired
	private HotelRepo repo;

	@Autowired
	MqttGateway mqtGateway;
	Logger logger = LoggerFactory.getLogger(getClass());

	public Hotel addHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		hotel.setId(UUID.randomUUID().toString());
		try {
			mqtGateway.senToMqtt(hotel.getId(), "hotelIdTopic");
		} catch (Exception ex) {
			logger.warn("cannot sent message to mqtt");
		}
		Hotel h1 = repo.save(hotel);
		return h1;
	}

	public Hotel getHotel(String id) {
		// id 1,2,4
		Optional<Hotel> hotel = repo.findById(id);
		return hotel.isPresent() ? hotel.get() : null;

	}

	public List<Hotel> getAllHotel() {
		return repo.findAll();
	}

	public List<Hotel> getMultipleHotels(String ids) {
		// return
		return repo.findAllById(Arrays.asList(ids.split(",")));
	}

}
