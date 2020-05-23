package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.borders.model.Event.EventType;

public class Simulator {
	
	private BordersDAO dao;
	private PriorityQueue<Event> queue;
	private List<Adiacenza> statiAdiacenti;
	private Integer passi;
	private List<Event> eventi;

	public Simulator() {
		this.dao = new BordersDAO();
		this.eventi = new ArrayList<>();
	}
	
	public void run(Integer stato, Integer anno) {
		this.queue = new PriorityQueue<>();
		statiAdiacenti = this.dao.getCoppieAdiacenti(anno);
		this.passi = 0;
		
		Event e = new Event(EventType.SPOSTAMENTO, 500, 500, 1, stato);
		this.queue.add(e);
		
		while(!this.queue.isEmpty()) {
			Event ev = this.queue.poll();
			this.eventi.add(ev);
			this.processEvent(ev);
		}
	}
	
	public void processEvent(Event e) {
		List<Integer> adiacenti = new ArrayList<>();
		
		for(Adiacenza a : statiAdiacenti) {
			if(a.getState1no()==e.getStato())
				adiacenti.add(a.getState2no());
		}
		
		Integer numMigranti = 0;
		
		while(e.getNonStanziali()>=adiacenti.size()) {
			numMigranti = e.getNonStanziali()/adiacenti.size();
			Event nuovo = null;
			
			for(Integer s : adiacenti) {
				nuovo = new Event(EventType.SPOSTAMENTO, (numMigranti/2)+1, (numMigranti/2), e.getTempo()+1, s);
				this.queue.add(nuovo);
				this.eventi.add(nuovo);
			}
		}
	
		passi = e.getTempo();
		e.setStanziali();
		e.setNonStanziali(0);
		this.eventi.remove(eventi.size()-1);
		this.eventi.add(e);
	}
	
	public Integer getPassi() {
		return this.passi;
	}
	
	public List<Event> getEventi() {
		return this.eventi;
	}

}
