package eu.debas.Model;

import java.util.ArrayList;
import java.util.List;

import DemographieException.CodePaysNotFound;

public class Demographie {
	
	private List<Pays>	mList;
	private String []	mCode;
	private double		a0, b0, a1, b1, n, ecartType0, ecartType1, correlation;
	private int			nbrAnnee;
	
	public Demographie(List<Pays> list, String [] code) throws CodePaysNotFound {
		// TODO Auto-generated constructor stub
		boolean isFound;

		mList = list;
		mCode = code;

		for (String s : code) {
			isFound = false;
			for (Pays p : mList) {
				if (s.compareTo(p.getCode()) == 0) {
					isFound = true;
				}
			}
			if (isFound == false) {
				throw new CodePaysNotFound(s);				
			}
		}
		nbrAnnee = list.get(0).getData().size();

		n = mList.get(0).getData().size();
		calcAB();
		calcEcartType();
		calcCorrelation();
	}
	
	public List<Pays> getPaysFromCode(String [] code) {
		List<Pays>		pays = new ArrayList<Pays>();
		
		for (int i = 0 ; i < code.length ; i++) {
			for (Pays p : mList) {
				if (p.getCode().compareTo(code[i]) == 0)
					pays.add(p);
			}
		}
		return pays;
	}
	
	private void calcAB() {
		List<Pays> pays = getPaysFromCode(mCode);
		
		int i;
	    Double xsomme, ysomme, xysomme, xxsomme, yysomme;
	    
	    xsomme = 0.0; ysomme = 0.0; yysomme = 0.0;
	    xysomme = 0.0; xxsomme = 0.0;
	    for (i = 0; i < n; i++)
	      {
	    	Double xi = mList.get(0).getData().get(i);
	    	Double yi = getSumFromIndex(pays, i);

	    	xsomme = xsomme + xi;
	        ysomme = ysomme + yi;
	        xysomme = xysomme + xi * yi;
	        xxsomme = xxsomme + xi * xi;      
	        yysomme = yysomme + yi * yi;
	      }
	    a0 = (ysomme * xxsomme - xsomme * xysomme) / (n * xxsomme - xsomme * xsomme);
	    b0 = ((n * xysomme) - (xsomme * ysomme)) / ((n * xxsomme) - (xsomme * xsomme));
	    a1 = (xsomme * yysomme - ysomme * xysomme) / (n * yysomme - ysomme * ysomme);
	    b1 = (n * xysomme - xsomme * ysomme) / (n * yysomme - ysomme * ysomme);
	}
	
	public Double getSumFromIndex(List<Pays> pays, int index) {
		Double	res = 0.0;
		
		for (Pays p : pays) {
			res += p.getData().get(index);
		}
		return res;
	}
	
	private void calcEcartType() {
		int			i;
		Double		sum = 0.0;
		List<Pays> pays = getPaysFromCode(mCode);

		for (i = 0; i < n; i++)
	      {
	    	Double X = mList.get(0).getData().get(i);
	    	sum += Math.pow(getSumFromIndex(pays, i) - (a0 + b0 * X), 2);
	      }
		ecartType0 = Math.sqrt(sum / n);
		sum = 0.0;
		for (i = 0; i < n; i++)
	      {
	    	Double X = mList.get(0).getData().get(i);
	    	sum += Math.pow(getSumFromIndex(pays, i) - (X - a1) / b1, 2);
	      }
		ecartType1 = Math.sqrt(sum / n);
	}
	
	public Double getMoyenne(List<Pays> pays, int index) {
		Double total = 0.0;
		
		for (Pays p : pays) {
			 total += p.getData().get(index);
		}
		return total / pays.size();
	}

	public Double getSigmaY(List<Pays> pays) {
		Double ret = 0.0;
		
		for (int i = 0 ; i < n ; i++) {
			for (Pays p : pays) {
				ret += p.getData().get(i);
			}			
		}
		return ret;
	}

	public void calcCorrelation() {
		List<Pays>		pays = getPaysFromCode(mCode);
		Double num = 0.0, denom = 0.0;

		for (int i = 0; i < n; i++)
	      {
	    	Double X = mList.get(0).getData().get(i);
	    	num += Math.pow((a0 + b0 * X) - (getSigmaY(pays) / (pays.get(0).getData().size())), 2);
	    	denom += Math.pow(getSumFromIndex(pays, i) - (getSigmaY(pays) / (pays.get(0).getData().size())), 2);
	      }	
		correlation = Math.sqrt(num / denom);
	}
	
	public String toString () {
		String	ret = "";
		List<Pays>	pList = getPaysFromCode(mCode);
		
		ret += "pays :\n";
		for (Pays p: pList) {
			ret += "\t" + p.getPays() + "\n";
		}
		ret += "ajustement de X sur Y :\n";
		ret += "\t Y = " + b0 + " X " + (a0 > 0 ? ("+ " + a0) : "- " + -a0) + "\n";
		ret += "\t ecart-type :                             " + ecartType0 + "\n";
		ret += "\t estimation de la population en 2050 :    " + (a0 + b0 * 2050) + "\n";
		ret += "ajustement de Y sur X :\n";
		ret += "\t X = " + b1 + " Y " + (a1 > 0 ? ("+ " + a1) : "- " + -a1) + "\n";
		ret += "\t ecart-type :                             " + ecartType1 + "\n";
		ret += "\t estimation de la population en 2050 :    " + ((2050 - a1) / b1) + "\n";
		ret += "coefficient de correlation : " + correlation + "\n";
		return ret;
	}
	
	public double getAjustXY (int year) {
		return a0 +b0 * year;
	}
	
	public double getAjustYX (int year) {
		return (year - a1) /b1;
	}
	
	public int getNbrAnnee() {
		return nbrAnnee;
	}
	
	public List<Pays> getPays() {
		return mList;
	}
}
