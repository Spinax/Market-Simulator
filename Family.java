import java.io.BufferedReader;
import java.util.Random;
import java.io.IOException;

public class Family {
	private int Savings; //risparmio di una famiglia
	private int maxBP; //capacita' massima di acquisto
	private int maxWH; //massima capacita' lavorativa
	private int WH = 0; //ore di lavoro efffettivamente richieste
	private int BP = 0; //numero di beni effettivamente richiesti
	private int seed;//capacita' massima di impiego
	
	//costruttore che legge da file i valori degli attributi
	public Family(BufferedReader file, int seed) throws NumberFormatException, IOException {
		try {
			Savings = Integer.parseInt(file.readLine());
			maxBP = Integer.parseInt(file.readLine());
			maxWH = Integer.parseInt(file.readLine());
			this.seed = seed;
		}
		//esco dal programma se l'input non e'del formato giusto
		catch(NumberFormatException e) {
			System.out.println("Wrong input format");
			System.exit(0);
		}
	}
	//costruttore usato in copy per creare gli oggetti del vettore Families del main
	public Family() {
		Savings = 0;
		maxBP = 0;
		maxWH = 0;
		seed = 0;
	}
	//get per ogni attributo della classe
	public int getSavings() {
		return Savings;
	}
	public int getmaxBP() {
		return maxBP;
	}
	public int getmaxWH() {
		return maxWH;
	}
	public int getWH() {
		return WH;
	}
	public int getBP() {
		return BP;
	}
	//set i risparmi di una famiglia
	public void setSavings(int savings) {
		Savings = savings;
	}
	//metodo per generare un vettore di industrie con i dati inizializzati ai valori letti da file
	public void copy(Family[] families) {
		for (int i = 0;i<families.length;i++) {
			families[i] = new Family();
			families[i].Savings = Savings;
			families[i].maxBP = maxBP;
			families[i].maxWH = maxWH;
			families[i].seed = seed;
		}
	}
	//metodo che genera le ore di lavoro offerte dalla famiglia
	public int generateWH() {
		Random r = new Random(seed);
		WH = r.nextInt(maxWH) + 1;
		return WH;
	}
	//metodo che genera il numero di  oggetti acquistabili dalla famiglia
	public int generateBP() {
		if (Savings > 0) { //le famiglie posso acquistare beni solo se il loro bilancio e'in positivo
			Random r = new Random(seed); //uso la classe random per il suo metodo nextInt
			BP = r.nextInt(maxBP) + 1;//nextInt genera un numero compreso tra 0 e MaxBP (MaxBP escluso), soommando 1 ottengo un valore compreso tra 1 e maxBP
			return BP;
		}
		else
			return 0;
	}
}
