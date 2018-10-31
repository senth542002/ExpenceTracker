package com.expence.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.expence.tracker.model.ExpenceDetail;
import com.expence.tracker.model.Transaction;

public class ExpenceTracker {
	
	public static List<ExpenceDetail> expenceDetails = new ArrayList<>();
	
	public ExpenceTracker() {
		//this.expenceSheet = new HashMap<>();
	}

	public List<ExpenceDetail> fetchExpenceSheet() {
		return expenceDetails;
	}

	public void addExpence(Transaction transaction) {
		if (expenceDetails.contains(transaction.getCreator())){
			/*expenceDetails.get(expenceDetails.indexOf(transaction.getCreator()))
				.trackTransaction(transaction, expenceDetails);*/
			expenceDetails.stream().filter(exp -> exp.getCreator().equals(transaction.getCreator()))
				.forEach(expence -> expence.trackTransaction(transaction, expenceDetails));
		} else {
			transaction.getParticipants().forEach(part -> expenceDetails.add(
					new ExpenceDetail(transaction.getCreator(), part, 0.0)));
			
			expenceDetails.stream().filter(exp -> exp.getCreator().equals(transaction.getCreator()))
			.forEach(expence -> expence.trackTransaction(transaction, expenceDetails));
		}
	}

}
