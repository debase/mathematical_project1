package eu.debas.Csv;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.jcsv.reader.CSVEntryParser;

import eu.debas.Model.Pays;

public class PaysEntryParser implements CSVEntryParser<Pays> {

	@Override
	public Pays parseEntry(String... data) {
		String pays = data[0];
		String code = data[1];
		
		List<Double>		dataPays = new ArrayList<Double>();

		for (int i = 2 ; i < data.length ; i++) {
			try {
				if (pays.compareTo("Country Name") == 0) {
					dataPays.add(Double.parseDouble(data[i].replaceAll(",", ".")));
				}
				else {
					dataPays.add(Double.parseDouble(data[i].replaceAll(",", ".")) / 1000000);
				}
			} catch (NumberFormatException e) {
				if (e.getMessage().compareTo("empty String") == 0) {
					dataPays.add(0.0);
				}
			}
		}
		return new Pays(pays, code, dataPays);
	}
	
}