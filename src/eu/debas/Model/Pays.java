package eu.debas.Model;

import java.util.List;

public class Pays {
	
	private String 			mPays;
	private String 			mCode;
	private List<Double>	mData;
	
	public Pays(String pays, String code, List<Double> data) {
		mPays = pays;
		mCode = code;
		mData = data;
		// TODO Auto-generated constructor stub
	}

	public String getPays() {
		return mPays;
	}

	public String getCode() {
		return mCode;
	}
	
	public List<Double> getData() {
		return mData;
	}

}
