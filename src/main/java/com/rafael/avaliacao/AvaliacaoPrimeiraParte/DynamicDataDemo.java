package com.rafael.avaliacao.AvaliacaoPrimeiraParte;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a button.
 *
 */
public class DynamicDataDemo extends ApplicationFrame {

    /** The time series data. */
    private TimeSeries series;

    /** The most recent value added. */
    private double  lastValue = 0.0;
    
    public DynamicDataDemo(final String title) {

        super(title);
        
        this.series = new TimeSeries("Random Data", Minute.class);
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
     * @param dataset  the dataset.
     * 
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Dynamic Data Demo", 
            "Time", 
            "Value",
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 200.0); 
        return result;
    }
    
//    public void actionPerformed(final ActionEvent e) {
//        if (e.getActionCommand().equals("ADD_DATA")) {
//            final double factor = 0.90 + 0.2 * Math.random();
//            this.lastValue = this.lastValue * factor;
//            final Millisecond now = new Millisecond();
//            System.out.println("Now = " + now.toString());
//            this.series.add(new Millisecond(), this.lastValue);
//        }
//    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
//    public static void main(final String[] args) {
//
//        final DynamicDataDemo demo = new DynamicDataDemo("Dynamic Data Demo");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//
//    }
//    
    
    public void setValueNow(double value, String time) {
    	
    	this.lastValue = value;
    	
    	String[] result = time.split(":");

    	this.series.add(new Millisecond(), this.lastValue);
    }

}

