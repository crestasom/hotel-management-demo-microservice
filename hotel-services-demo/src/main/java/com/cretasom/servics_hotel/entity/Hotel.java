package com.cretasom.servics_hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
	@Id
	private String id;
	private String name;
	private String address;
}
