import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class Industry {
	private int Capital; //risparmio di un'industria
	private int maxWD;  //ore di lavoro massime richieste
	private int WD = 0; //ore di lavoro effettivamente richieste
	private int EP; //efficenza produttiva = beni per ora di lavoro
	private int seed; //seme della generazione randomica
	private int ITBS = 0; //oggetti prodotti ad ogni ciclo (efficenza lavorativa * ore di lavoro)
	
	//costruttore che crea un oggetto con paramatri a 0 usato nel metodo copy
	public Industry() {
		Capital = 0;
		maxWD = 0;
		EP = 0;
		seed = 0;
	}
	//costruttore che legge da file i valori degli attributi
	public Industry(BufferedReader file , int seed) throws NumberFormatException, IOException {
		try {
			Capital = Integer.parseInt(file.readLine());
			maxWD = Integer.parseInt(file.readLine());
			EP = Integer.parseInt(file.readLine());
			this.seed = seed;
		}
		catch(NumberFormatException e) {
			System.out.println("Wrong input format");
			System.exit(0);
		}
	}
	//get per ogni attributo della classe
	public int getCapital() {
		return Capital;
	}
	public int getmaxWD() {
		return maxWD;
	}
	public int getWD() {
		return WD;
	}
	public int getITBS() {
		return ITBS;
	}
	//set per il capitale
	public void setCapital(int capital) {
		Capital = capital;
	}
	//metodo per generare un vettore di industrie con i dati inizializzati ai valori letti da file
	public void copy(Industry[] industries) {
		for (int i = 0;i<industries.length;i++) {
			industries[i] = new Industry();
			industries[i].Capital = Capital;
			industries[i].maxWD = maxWD;
			industries[i].EP = EP;
			industries[i].seed = seed;
		}
	}
	//genera il numero di ore di lavoro richieste dall'azienda
	public int generateWD() {
		Random r = new Random(seed); //uso la classe random per il suo metodo nextInt
		WD = r.nextInt(maxWD) + 1;//nextInt genera un numero compreso tra 0 e MaxBP (MaxBP escluso), soommando 1 ottengo un valore compreso tra 1 e maxBP
		return WD;
	}
	//metodo che genera il numero di oggetti effettivamente prodotti
	public int generateItemsToBeSold() {
		ITBS = generateWD() * EP; // il numero di oggetti in vendita e'il prodotto delle ore lavorative richieste per l'efficenza produttiva
		return ITBS;
	}
}
