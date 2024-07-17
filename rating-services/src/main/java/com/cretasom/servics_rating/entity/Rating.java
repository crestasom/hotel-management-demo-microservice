package com.cretasom.servics_rating.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Rating {
	@Id
	private String id;
	private String hotelId;
	private String userId;
	private int rating;
	private String comment;
}
