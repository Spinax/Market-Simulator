import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// classe main, contiene lo svolgimento della simulazione
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		//1.vengono letti i dati
		//2.{fase1 della simulazione 
		//3.fase2..
		//4.stampa dei risultati } ripetuto n volte
		int rndSeed;
		int nSimCycles;
		int nFam;
		int nInd;
		BufferedReader datafile = null ;
		//se non viene inserito inserito l'input atteso (una stringa) il programma termina
		if (args.length != 1) {	
			System.out.println("Input must be a filepath.");
			System.exit(0);
		}
		else {
			try {
				//creazione dello stream dei dati da file
				FileReader file = new FileReader(args[0]);
				datafile = new BufferedReader(file);	
			}
			//esco dal programma se non trovo il file
			catch(FileNotFoundException e) {
				System.out.println("File not found.");
				System.exit(0);
			}
			//leggo il seed,il numero di cicli di simulazione da eseguire, il numero di famiglie e di industrie
			rndSeed = Integer.parseInt(datafile.readLine());
			nSimCycles = Integer.parseInt(datafile.readLine());
			nFam = Integer.parseInt(datafile.readLine());
			nInd = Integer.parseInt(datafile.readLine());
			//creo un oggetto di tipo mercato che leggera' il valore del prezzo di eq per beni e servizi
			Market Mercato = new Market(datafile);
			//creo una famiglia in cui inserisco i valori con i quali creare le n famiglie della simulazione
			Family defaultDataFamily = new Family(datafile,rndSeed);
			//creo un' industria in cui inserisco i valori con i quali creare le n industrie della simulazione
			Industry defaultDataIndustry = new Industry(datafile,rndSeed);
			//fine lettura dati
			datafile.close();
			//creo i vettori di oggetti famiglia e industria
			Family[] Families = new Family[nFam];
			Industry[] Industries= new Industry[nInd];
			//ripetizione della simulazione per nCSimCycles volte
			for (int i = 1;i<=nSimCycles;i++) {
				//con il metodo copy copio i dati della famiglia default in tutte le famiglie, anche per riportare ai parametri originali gli agenti ad ogni ciclo di simulazione
				defaultDataFamily.copy(Families);
				//analogamente per le industrie
				defaultDataIndustry.copy(Industries);
				//azzero i dati relativi alla domanda e all' offerta di lavoro e servizi
				Mercato.resetData();
				//fase di formazione dei prezzi
				Mercato.computeNewFWP(Families, Industries); //viene calcolato il prezzo del lavoro
				Mercato.computeNewFGP(Families, Industries); //viene calcolato il prezzo dei beni
			    //fase di scambio di denaro tra famiglie e le industrie
				Mercato.workPayment(Families, Industries);
				Mercato.goodsPayment(Families, Industries);
				//stampa dei dati del ciclo di simulazione
				Mercato.printData(Families, Industries, i);
			}   
		}
	}
}
