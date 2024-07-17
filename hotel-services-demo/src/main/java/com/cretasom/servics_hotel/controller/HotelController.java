package com.cretasom.servics_hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cretasom.servics_hotel.entity.Hotel;
import com.cretasom.servics_hotel.service.HotelServiceImpl;

@RestController
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelServiceImpl hotelService;

	@PostMapping("/")
	public Hotel addHotel(@RequestBody Hotel hotel) {
		return hotelService.addHotel(hotel);
	}

	@GetMapping("/get-all")
	public List<Hotel> getAllHotel() {
		return hotelService.getAllHotel();
	}

	@GetMapping("/get/{id}")
	public Hotel getHotel(@PathVariable String id) {
		return hotelService.getHotel(id);
	}
}
