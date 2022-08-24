package com.rafael.avaliacao.compravenda;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A demonstration application showing a time series chart where you can
 * dynamically add (random) data by clicking on a button.
 *
 */
@SuppressWarnings("serial")
public class DynamicDataDemo extends ApplicationFrame {

	/** The time series data. */
	private TimeSeries series;
	private String title;
	private double dimMin;
	private double dimMax;

	/** The most recent value added. */
	private double lastValue = 100.0;
	
	private ArrayList<Double> mmcAtivo;

	/**
	 * Constructs a new demonstration application.
	 *
	 * @param title the frame title.
	 */
	public DynamicDataDemo(final String title, double dimMin, double dimMax) {

		
		super(title);
		
		mmcAtivo = new ArrayList<>();
		this.series = new TimeSeries("Time ", Millisecond.class);
		this.title = title;
		
		this.dimMin = dimMin;
		this.dimMax = dimMax;
		
		final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);
		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(content);

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset the dataset.
	 * 
	 * @return A sample chart.
	 */
	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(title, "Time", "Value", dataset,
				true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(dimMin, dimMax);
		return result;
	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
	// ****************************************************************************

	/**
	 * Handles a click on the button by adding new (random) data.
	 *
	 * @param e the action event.
	 */
	public void incrementValue(ArrayList<Double> ativo, String time) {

		if (ativo.size() > 1) {
			
			MediaMovel.mediaMovel(ativo, 5, mmcAtivo);
			
			if(mmcAtivo.size()>1) {
			
				this.lastValue = mmcAtivo.get(mmcAtivo.size()-1);
				final Millisecond now = new Millisecond();
				System.out.println("Now = " + now.toString());
				this.series.add(new Millisecond(), this.lastValue);
//				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//				Date date = sdf.parse(time);
//				long millis = date.getTime();
//				
//				System.out.println("Now = " + time);
//				this.series.add(millis, this.lastValue);
				
			}

		}
	}

}
