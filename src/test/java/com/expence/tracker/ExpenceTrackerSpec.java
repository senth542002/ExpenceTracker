package com.expence.tracker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.expence.tracker.model.ExpenceDetail;
import com.expence.tracker.model.Person;
import com.expence.tracker.model.Transaction;

public class ExpenceTrackerSpec {

	ExpenceTracker expenceTracker;
	List<Person> participants;
	
	@Before
	public void initialize(){
		expenceTracker = new ExpenceTracker();
		participants = new ArrayList<>();
	}
	
	@Test
	public void trackExpenceBetweenTwoPeopleSingleTransaction() {
		participants.add(this.createPerson("Varun", false));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "Tea", 100.0, participants));
		List<ExpenceDetail> expenceSheet = expenceTracker.fetchExpenceSheet();
		
		assertThat(expenceSheet).isNotNull();
		assertThat(expenceSheet.get(0).getCreator()).isEqualTo(this.createPerson("Senthil", true));
		assertThat(expenceSheet.get(0).getParticipant()).isEqualTo(this.createPerson("Varun", true));
		assertThat(expenceSheet.get(0).getAmount()).isEqualTo(-50);
	}

	private int fetchExpeceDetail(List<ExpenceDetail> expenceSheet, String name) {
		return expenceSheet.indexOf(this.createPerson(name, false));
	}
	
	private ExpenceDetail fetchExpeceDetail(List<ExpenceDetail> expenceSheet, String creator, String participant) {
		return expenceSheet.stream().filter(exp -> 
		exp.getCreator().equals(this.createPerson(creator, true)) && 
		exp.getParticipant().equals(this.createPerson(participant, false))).collect(Collectors.toList()).get(0);
	}
	
	@Test
	public void trackExpenceBetweenThreePeopleSingleTransaction() {
		participants.add(this.createPerson("Varun", false));
		participants.add(this.createPerson("Kumar", false));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "Tea", 90.0, participants));
		List<ExpenceDetail> expenceSheet = expenceTracker.fetchExpenceSheet();
		
		assertThat(expenceSheet).isNotNull();
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Varun").getAmount()).isEqualTo(-30);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Kumar").getAmount()).isEqualTo(-30);
	}
	
	@Test
	public void trackExpenceBetweenThreePeopleMultipleTransaction() {
		participants.add(this.createPerson("Varun", false));
		participants.add(this.createPerson("Kumar", false));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "Tea", 90.0, participants));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "SpecialTea", 120.0, participants));
		List<ExpenceDetail> expenceSheet = expenceTracker.fetchExpenceSheet();
		
		assertThat(expenceSheet).isNotNull();
		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Varun").getAmount()).isEqualTo(-70);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Kumar").getAmount()).isEqualTo(-70);
	}

	@Test
	public void trackExpenceBetweenThreePeopleMultipleDifferentContributers() throws Exception {
		participants.add(this.createPerson("Varun", false));
		participants.add(this.createPerson("Kumar", false));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "Tea", 90.0, participants));
		participants = new ArrayList<>();
		participants.add(this.createPerson("Senthil", false));
		participants.add(this.createPerson("Kumar", false));		
		expenceTracker.addExpence(new Transaction(this.createPerson("Varun", true), "SpecialTea", 120.0, participants));
		List<ExpenceDetail> expenceSheet = expenceTracker.fetchExpenceSheet();
		
		assertThat(expenceSheet).isNotNull();
		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Varun").getAmount()).isEqualTo(10.0);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Kumar").getAmount()).isEqualTo(-30.0);
		assertThat(this.fetchExpeceDetail(expenceSheet, "Varun", "Senthil").getAmount()).isEqualTo(-10.0);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Varun", "Kumar").getAmount()).isEqualTo(-40.0);
	}
	
	
	@Test
	public void trackExpenceBetweenThreePeopleMultipleIrregularDifferentContributers() throws Exception {
		participants.add(this.createPerson("Varun", false));
		participants.add(this.createPerson("Kumar", false));
		expenceTracker.addExpence(new Transaction(this.createPerson("Senthil", true), "Tea", 90.0, participants));
		participants = new ArrayList<>();
		participants.add(this.createPerson("Arun", false));
		participants.add(this.createPerson("Kumar", false));		
		expenceTracker.addExpence(new Transaction(this.createPerson("Varun", true), "SpecialTea", 120.0, participants));
		List<ExpenceDetail> expenceSheet = expenceTracker.fetchExpenceSheet();
		
		assertThat(expenceSheet).isNotNull();
		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Varun").getAmount()).isEqualTo(-30.0);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Senthil", "Kumar").getAmount()).isEqualTo(-30.0);
		assertThat(this.fetchExpeceDetail(expenceSheet, "Varun", "Arun").getAmount()).isEqualTo(-40.0);		
		assertThat(this.fetchExpeceDetail(expenceSheet, "Varun", "Kumar").getAmount()).isEqualTo(-40.0);
	}
	
	private Person createPerson(String name, Boolean isCreator) {
		return new Person(name, isCreator);
	}
}
