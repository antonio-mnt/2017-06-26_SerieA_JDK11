package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> boxSquadra;

    @FXML
    private ChoiceBox<Integer> boxStagione;

    @FXML
    private Button btnCalcolaConnessioniSquadra;

    @FXML
    private Button btnSimulaTifosi;

    @FXML
    private Button btnAnalizzaSquadre;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaSquadre(ActionEvent event) {
    	
    	this.model.creaGrafo();
    	
    	this.txtResult.setText("Grafo creato!\nVertici: "+this.model.getNumeroVertici()+" Archi: "+this.model.getNumeroArchi()+"\n");
    	
    	this.boxSquadra.getItems().addAll(this.model.getVertici());

    }

    @FXML
    void doCalcolaConnessioniSquadra(ActionEvent event) {
    	
    	
    	String squadra = this.boxSquadra.getValue();
    	
    	if(squadra==null) {
    		this.txtResult.setText("Devi selezionare una squadra\n");
    		return;
    	}
    	
    	this.model.trovaConnessione(squadra);
    	
    	this.txtResult.clear();
    	for(Vicino v: this.model.getConnessi()) {
    		this.txtResult.appendText(v.getTeam()+" "+v.getPeso()+"\n");
    	}

    }

    @FXML
    void doSimulaTifosi(ActionEvent event) {
    	
    	Integer anno = this.boxStagione.getValue();
    	
    	if(anno==null) {
    		this.txtResult.setText("Selezionare una Stagione");
    		return;
    	}
    	
    	this.model.simula(anno);
    	
    	this.txtResult.clear();
    	for(String s: this.model.getSquadre()) {
    		this.txtResult.appendText(String.format("%-20s #tifosi: %-20d Punti: %-20d \n", s,this.model.getTifosi().get(s),this.model.getClassifica().get(s)));
    	}
    	

    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxStagione.getItems().addAll(this.model.getSeason());
	}
}