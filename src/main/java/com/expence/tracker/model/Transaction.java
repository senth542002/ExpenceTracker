package com.expence.tracker.model;

import java.util.List;

import com.expence.tracker.model.Person;

public class Transaction {

	private Person creator;
	private String expenceType;
	private double expenceAmount;
	private List<Person> participants;
	private double costPerPerson;

	public Transaction(Person creator, String expenceType, double expenceAmount, List<Person> participants) {
		this.creator = creator;
		this.expenceType = expenceType;
		this.expenceAmount = expenceAmount;
		this.participants = participants;
		this.costPerPerson = expenceAmount / (participants.size() + 1) ;
	}

	public Person getCreator() {
		return creator;
	}

	public String getExpenceType() {
		return expenceType;
	}

	public double getExpenceAmount() {
		return expenceAmount;
	}

	public List<Person> getParticipants() {
		return participants;
	}

	public double getCostPerPerson() {
		return costPerPerson;
	}
	
}
