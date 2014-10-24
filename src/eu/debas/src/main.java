package eu.debas.src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import DemographieException.CodePaysNotFound;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import eu.debas.Csv.PaysEntryParser;
import eu.debas.Model.Demographie;
import eu.debas.Model.Pays;
import eu.debas.graphic.DemoGraphique;


public class main {

	private static String dataCsvPath = "./assets/207demographie_donnees.csv";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length == 0) {
			System.out.println("Invalid use : 207 demographie coutry_code [country_code] ...");
		}
		else {			
			try {
				Reader csvFile = new FileReader(dataCsvPath);

				CSVReader<Pays> paysReader = new CSVReaderBuilder<Pays>(csvFile).entryParser(new PaysEntryParser()).build();

				Demographie demographie = new Demographie(paysReader.readAll(), args);				
				System.out.println(demographie.toString());
				
				DemoGraphique	graph = new DemoGraphique("207demographie", demographie, 1000, 900, args);

				graph.pack();
				graph.setVisible(true);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File :" + "\"" + dataCsvPath + "\"" + " not found.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodePaysNotFound e) {
				e.getMessage();
			}

		}
	}
}
