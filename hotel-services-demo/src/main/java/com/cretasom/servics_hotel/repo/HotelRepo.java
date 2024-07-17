package com.cretasom.servics_hotel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cretasom.servics_hotel.entity.Hotel;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, String> {

	Hotel findByName(String name);

	Hotel findByAddress(String name);

	Hotel findByAddressAndName(String address, String name);
}
