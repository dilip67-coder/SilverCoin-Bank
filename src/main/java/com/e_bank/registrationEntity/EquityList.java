package com.e_bank.registrationEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GeneratorType;

import lombok.Data;

@Entity
@Data
public class EquityList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long equityId;
	
	private String equity_name;
	private String series;
	private float open;
	private float close;
	private float high;
	private float low;
	private float prev_close;
	private float return_per_year;
	@Override
	public String toString() {
		return "EquityList [equityId=" + equityId + ", equity_name=" + equity_name + ", series=" + series + ", open="
				+ open + ", close=" + close + ", high=" + high + ", low=" + low + ", prev_close=" + prev_close
				+ ", return_per_year=" + return_per_year + "]";
	}
	
}
