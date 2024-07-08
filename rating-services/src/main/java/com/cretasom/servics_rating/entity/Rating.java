package com.cretasom.servics_rating.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

	private int id;
	private int hotelId;
	private int userId;
	private int rating;
	private String comment;
}
