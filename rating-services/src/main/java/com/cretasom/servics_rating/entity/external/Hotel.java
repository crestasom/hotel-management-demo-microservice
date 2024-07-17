package com.cretasom.servics_rating.entity.external;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Hotel {

	private String id;
	private String name;
	private String address;
}
