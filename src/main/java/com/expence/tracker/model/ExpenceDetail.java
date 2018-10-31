package com.expence.tracker.model;

import java.util.List;

public class ExpenceDetail {
	
	private Person creator;
	private Person participant;
	private Double amount;
	private Double tempAmount = 0.0;
	
	public ExpenceDetail(Person creator, Person participant, Double amount) {
		this.creator = creator;
		this.participant = participant;
		this.amount = amount;
	}
	
	public ExpenceDetail() {
		
	}

	public void trackTransaction(Transaction transaction, List<ExpenceDetail> expenceDetails) {
		
		if (expenceDetails.stream().filter(expence -> expence.creator.equals(this.participant) && expence.participant.equals(this.creator))
			.count() == 0) {
			transaction.getParticipants().stream().filter(part -> part.equals(this.participant))
			.forEach(participant -> this.amount -= transaction.getCostPerPerson());
		} else {
			
			expenceDetails.stream().filter(expence -> expence.creator.equals(this.participant) && expence.participant.equals(this.creator))
			.forEach(exp -> { 
				exp.amount += transaction.getCostPerPerson();
				this.tempAmount = exp.amount;
			});
			
			transaction.getParticipants().stream().filter(part -> part.equals(this.participant))
			.forEach(participant -> this.amount -= this.tempAmount);
		}
		
	}

	public ExpenceDetail createExpenceDetail(Transaction transaction) {
		return null;
	}

	public Person getCreator() {
		return creator;
	}

	public Person getParticipant() {
		return participant;
	}

	public Double getAmount() {
		return amount;
	}
	
}
