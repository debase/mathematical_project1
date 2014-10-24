package eu.debas.graphic;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import eu.debas.Model.Demographie;
import eu.debas.Model.Pays;

public class DemoGraphique extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Demographie mDemographie;
	String []	mCode;
	JFreeChart	mChart;
	Dimension	mDim = new Dimension();

	public DemoGraphique(String title, Demographie demographie, int w, int h, String [] args) {
		super(title);
		this.setPreferredSize(new Dimension(w, h));

		mDim.setSize(w, h);
		mDemographie = demographie;
		mCode = args;
		XYSeries moyenneDemo = createMoyenneDemographique("moyenne demographique");
		XYSeries series1 = createAjustXY("ajustement de X sur Y");
		XYSeries series2 = createAjustYX("ajustement de Y sur X");

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(moyenneDemo);
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		mChart = ChartFactory.createXYLineChart(
				"Graphique",
				"",
				"",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);

		// set interval to 10
//		setXInterval(mChart, 10);
		
		XYPlot plot = mChart.getXYPlot();
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, Color.BLUE); 
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(1, Color.GREEN); 
		plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(2, Color.RED); 

		final ChartPanel chartPanel = new ChartPanel(mChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        scaleCharts(mChart);
        
		final JPanel content = new JPanel(new BorderLayout());

//		JButton savePng = new JButton("Save as PNG");
//		final JPanel buttonPanel = new JPanel(new FlowLayout());
//        buttonPanel.add(savePng);
//        savePng.setActionCommand("SAVE_PNG");
//        savePng.addActionListener(this);
        
        
        content.add(chartPanel);
//        content.add(buttonPanel, BorderLayout.SOUTH);
        
		setContentPane(content);
	}
	
	public XYSeries createMoyenneDemographique(String name) {
		XYSeries	series = new XYSeries(name);
		List<Pays>		pays = mDemographie.getPaysFromCode(mCode);
		int x;
		double y;

		for (int i = 0; i < mDemographie.getNbrAnnee(); i++) {
			x = 1961 + i;
			y = mDemographie.getSumFromIndex(pays, i);
			series.add(x, y);
		}
		return series;
	}
	
	public XYSeries createAjustXY(String name) {
		XYSeries	series = new XYSeries(name);
		double y;

		for (int x = 1961; x < 2050; x++) {
			y = mDemographie.getAjustXY(x);
			series.add(x, y);
		}
		return series;
	}

	
	public XYSeries createAjustYX(String name) {
		XYSeries	series = new XYSeries(name);
		double y;

		for (int x = 1961; x < 2050; x++) {
			y = mDemographie.getAjustYX(x);
			series.add(x, y);
		}
		return series;
	}

	private void scaleCharts(JFreeChart chart) {
		XYPlot plot = chart.getXYPlot();
		ValueAxis axis = plot.getRangeAxis();

		if (axis instanceof NumberAxis) {
			NumberAxis naxis = (NumberAxis)axis;
	  	  	naxis.setAutoRangeIncludesZero(false);
	  	  	naxis.setAutoRangeStickyZero(false);
	    }
	}
	
//	private void setXInterval(JFreeChart chart, int interval) {
//		XYPlot plot = chart.getXYPlot();
//        ValueAxis rangeAxis = plot.getDomainAxis();
//        ((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(interval));
//	}
	
//	 public void actionPerformed(final ActionEvent e) {
//		 if (e.getActionCommand().equals("SAVE_PNG")) {
//			 try {
//				 JFileChooser fileChooser =new JFileChooser("C:/");
//				 fileChooser.setCurrentDirectory(new File("."));
//				 int retour = fileChooser.showSaveDialog(this);
//				 if(retour == JFileChooser.APPROVE_OPTION) {
//					 String file =  fileChooser.getSelectedFile().getAbsolutePath();
//					 if (file.length() > 0) {
//						 ChartUtilities.saveChartAsPNG(new File(file), mChart, (int)mDim.getWidth(), (int)mDim.getHeight());						 
//					 }
//				 }
//			 } catch (IOException e1) {
//				// TODO Auto-generated catch block
//					 e1.printStackTrace();
//			 }
//		 }
//	 }
}
