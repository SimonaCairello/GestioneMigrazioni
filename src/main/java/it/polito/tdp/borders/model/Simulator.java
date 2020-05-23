package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {
	
	private Graph<Country, DefaultEdge> grafo;
	
	private PriorityQueue<Event> queue;
	
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	private int T = -1;
	private Map<Country, Integer> stanziali;
	
	
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		this.partenza = partenza;
		this.grafo = grafo;
		
		this.T = 1;
		stanziali = new HashMap<>();
		for(Country c : this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
		this.queue = new PriorityQueue<Event>();
		this.queue.add(new Event(T, partenza, N_MIGRANTI));
	}
	
	public void run() {
		Event e;
		while((e = this.queue.poll()) != null) {
			this.T = e.getT();
			
			int nPersone = e.getN();
			Country stato = e.getStato();
			List<Country> vicini = Graphs.neighborListOf(this.grafo, stato);
			
			int migranti = (nPersone / 2) / vicini.size(); 
			
			if(migranti > 0) {
				for(Country confinante : vicini) {
					queue.add(new Event(e.getT() + 1, confinante, migranti));
				}
			}
			
			int stanziali = nPersone - migranti * vicini.size();
			this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);	
		}
	}
	
	public Map<Country, Integer> getStanziali(){
		return this.stanziali;
	}
	
	public Integer getT() {
		return this.T;
	}
}