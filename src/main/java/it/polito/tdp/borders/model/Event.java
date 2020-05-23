package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		SPOSTAMENTO
	}
	
	private EventType type;
	private Integer stanziali;
	private Integer nonStanziali;
	private Integer tempo;
	private Integer stato;
	
	public Event(EventType type, Integer stanziali, Integer nonStanziali, Integer tempo, Integer stato) {
		this.type = type;
		this.stanziali = stanziali;
		this.nonStanziali = nonStanziali;
		this.tempo = tempo;
		this.stato = stato;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Integer getNonStanziali() {
		return nonStanziali;
	}

	public void setNonStanziali(Integer nonStanziali) {
		this.nonStanziali = nonStanziali;
	}

	public Integer getStanziali() {
		return stanziali;
	}

	public void setStanziali() {
		this.stanziali = stanziali+nonStanziali;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getStato() {
		return stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}

	@Override
	public int compareTo(Event o) {
		return this.getTempo().compareTo(o.getTempo());
	}

}
