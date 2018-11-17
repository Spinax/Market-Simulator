import java.io.BufferedReader;
import java.io.IOException;

public class Market {
	private int EGP; // prezzo di equilibrio dei beni, letto da file
	private int EWP; // prezzo di equilibrio del lavoro, letto da file
	private int FGP = 0; // prezzo finale dei beni, calcolato ad ogni ciclo di sim da computeNewFGP
	private int FWP = 0; // prezzo finale del lavoro, calcolato ad ogni ciclo di sim da computeNewSFP
	private int totWH = 0; // numero totale di ore di lavoro offerte
	private int totWD = 0; // numero totale di ore di lavoro richieste
	private int totBP = 0;  // numero totale di beni richiesti
	private int totITBS = 0; // numero totale di beni offerti
	
	//costruttore usato nel main, legge i prezzi di equilibrio per beni e servizi
	public Market(BufferedReader file) throws NumberFormatException, IOException {
		try { 
			EGP = Integer.parseInt(file.readLine());
			EWP = Integer.parseInt(file.readLine());
		}
		//esco dal programma se il formato dell'input non e'corretto
		catch(NumberFormatException e) {
			System.out.println("Wrong input format");
			System.exit(0);
		}
	}	
	//get per ogni attributo della classe
	public int getEGP() {
		return EGP;
	}
	public int getEWP() {
		return EWP;
	}
	public int getFGP() {
		return FGP;
	}
	public int getFWP() {
		return FWP;
	}
	//metodo che calcola il num di h di lavoro totali offerte dalle famiglie in questo ciclo di simulazione
	public int computeTotalWH (Family[] families) {
		for (int i=0;i<families.length;i++) {
			totWH += families[i].generateWH(); //uso il metodo della classe Family per generare il numero di h di lavoro offerte da una famiglia, e le sommo a un contatore
		}
		return totWH;
	}
	//metodo analogo per le ore di lavoro richieste dalle industrie
	public int computeTotalWD (Industry[] Industries) {
		for (int i=0;i<Industries.length;i++) 
			totWD += Industries[i].generateWD(); //uso il metodo della classe Industry per generare il numero di h di lavoro offerte da un'industria, e le sommo a un contatore
		return totWD;
	}
	//metodo che calcola il totale degli oggetti acquistabili dalle famiglie
	public int computeTotalBP (Family[] Families) {
		for (int i=0;i<Families.length;i++) 
			totBP += Families[i].generateBP();
		return totBP;
	}
	//metodo che calcola il numero totale degli oggetti messi in vendita dalle industrie
	public int computeTotalITBS (Industry[] Industries) {
		for (int i=0;i<Industries.length;i++) 
			totITBS += Industries[i].generateItemsToBeSold();
		return totITBS;
	}
	//metodo che calcola il nuovo prezzo del lavoro, void perche'il valore verra usato del main attraverso il metodo getFWP
	public void computeNewFWP(Family[] Families,Industry[] Industries) {
		FWP = EWP + (((this.computeTotalWH(Families)-this.computeTotalWD(Industries))*100)/this.computeTotalWH(Families));
	}
	//metodo che calcola il nuovo prezzo dei beni, void perche'il valore verra usato del main attraverso il metodo getFWP
	public void computeNewFGP(Family[] Families,Industry[] Industries) {
		FGP = EGP + (((this.computeTotalBP(Families)-this.computeTotalITBS(Industries))*100)/this.computeTotalBP(Families));
	}
	//metodo che paga le famiglie per il lavoro effettuato, sottraendo soldi alle industrie
	public void workPayment(Family[] Families, Industry[] Industries) {
		for (int i = 0; i<Families.length;i++) 
			Families[i].setSavings(Families[i].getSavings() + (Families[i].getWH() * FWP));
		for (int i = 0; i<Industries.length;i++)
			Industries[i].setCapital(Industries[i].getCapital() + (Industries[i].getWD() * FWP));
	}
	//metodo che paga le industrie per il beni venduti,sottraendo soldi alle famiglie
	public void goodsPayment(Family[] Families, Industry[] Industries) {
		for (int i = 0; i<Families.length;i++)
			Families[i].setSavings(Families[i].getSavings() - (Families[i].getBP() * FGP)); 
		for (int i = 0; i<Industries.length;i++)
			Industries[i].setCapital(Industries[i].getCapital() + (Industries[i].getITBS() * FGP));		
	}
	//funzione che pone a zero i dati relativa a domanda e offerta per beni e servizi
	public void resetData() {
		totWH = 0; 
		totWD = 0; 
		totBP = 0;  
		totITBS = 0;
	}
	public void printData(Family[] Families, Industry[] Industries, int NIter) {
		System.out.println("INIZIO ITERAZIONE " + NIter);
		System.out.println("LAVORO domanda: " + totWH + " offerta: " + totWD + " prezzo: " + FWP);
		System.out.println("BENI domanda: " + totBP + " offerta: " + totITBS + " prezzo: " + FGP);
		for (int i = 0;i<Families.length;i++) 
			System.out.println("Famiglia: " + (i+1) + "; bilancio: " + Families[i].getSavings());
		System.out.println("---");
		for (int i = 0;i<Industries.length;i++) 
			System.out.println("Impresa: " + (i+1) + "; capitale: " + Industries[i].getCapital());	
		System.out.println("FINE ITERAZIONE " + NIter);
		System.out.println("***************");
	}	
}

