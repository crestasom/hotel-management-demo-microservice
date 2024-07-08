package com.cretasom.servics_hotel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cretasom.servics_hotel.entity.Hotel;

@Service
public class HotelServiceImpl {

	List<Hotel> hotelList = new ArrayList<>();
	int i = 1;

	public Hotel addHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		hotel.setId(i);
		i++;
		hotelList.add(hotel);
		return hotel;
	}

	public Hotel getHotel(int id) {
		// id 1,2,4
		for (Hotel h : hotelList) {
			if (h.getId() == id) {
				return h;
			}
		}
		return null;
	}

	public List<Hotel> getAllHotel() {
		return hotelList;
	}

}
