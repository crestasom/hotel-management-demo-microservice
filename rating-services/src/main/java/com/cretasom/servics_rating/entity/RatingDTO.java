package com.cretasom.servics_rating.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RatingDTO {
	private String hotelName;
	private String userName;
	private int rating;
	private String comment;
}
