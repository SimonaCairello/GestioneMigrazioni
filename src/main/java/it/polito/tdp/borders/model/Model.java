package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph ;
	private Map<Integer,Country> countriesMap ;
	private BordersDAO dao;
	private Simulator sim;
	
	public Model() {
		this.countriesMap = new HashMap<>() ;
		this.dao = new BordersDAO();
		this.sim = new Simulator();
	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno);		
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no()));
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list);
		return list ;
	}
	
	public void run(Integer stato, Integer anno) {
		this.sim.run(stato, anno);
	}
	
	public Integer getPassi() {
		return this.sim.getPassi();
	}
	
	public List<CountryAndNumber> getEventi() {
		List<Event> eventi = this.sim.getEventi();
		List<CountryAndNumber> list = new ArrayList<>();
		
		for(Event e : eventi) {
			list.add(new CountryAndNumber(countriesMap.get(e.getStato()), e.getStanziali()));
		}
		
		return list;
	}

}
