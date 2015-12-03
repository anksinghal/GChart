package com.googlecode.gchart.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class GChartExample11 extends GChart implements EntryPoint{
  final double INITIAL_PRICE = 100;
  final double MAX_MONTHLY_RELATIVE_CHANGE = 0.2;
  final int N_FORCASTED_MONTHS = 13;
  final int MIN = 0;
  final int MAX = 1;
  final int AVG = 2;
  final int STD = 3; // estimated standard deviation
  final int N_STATS = 4;
  final int PRICE = 0;  // curve id of simple price curve
  final int BDIF = 1;   // curve id of backward difference curve
  double[] prices = new double[N_FORCASTED_MONTHS];
  double[] stats = new double[N_STATS];
  String[] statLabels = {"min: ", "max: ", "avg: ", "std: ",}; 
  String[] shortStatLabels = {"min", "max", "avg", "std",};
  int y1[] = new int[] {20, 30, 80, 120, 150, 180, 140, 100, 90, 50, 40, 20, 10};
  int y2[] = new int[] {180, 170, 140, 120, 70, 40, 20, 70, 110, 130, 150, 170, 180};

  
  final String SOURCE_CODE_LINK =
"<a href='GChartExample11.txt' target='_blank'>Source code</a>";
  final Button updateButton = new Button("<small><b>Update</b></small>");
  final Label updateTimeMsg = new Label();
  //ListBox tensionChooser = new ListBox();  // 'curvyness' selector

  HoverUpdateableListBox[] hoverList = {new HoverUpdateableListBox(MIN),
                                        new HoverUpdateableListBox(MAX)};
    
  int[] iStat = {MIN, MAX}; // ids of stats mapped to the two pie slices

  HashMap<String, Double> curveData = new HashMap<String, Double>();
  
  void updateStatSlices(int iStat0, int iStat1) {
    iStat[0] = iStat0;
    iStat[1] = iStat1;
  }

  void updateStatSlices() {   
     getCurve(0).getSymbol().setPieSliceSize(
       stats[iStat[0]]/(stats[iStat[0]]+stats[iStat[1]]));
     getCurve(1).getSymbol().setPieSliceSize(
       stats[iStat[1]]/(stats[iStat[0]]+stats[iStat[1]]));
  }

  // pop-up-on-hover-over list box that shows, lets user
  // switch, statistic mapped to each pie slice
  class HoverUpdateableListBox extends ListBox
        implements HoverUpdateable {

     HoverUpdateableListBox(int iStat) {
        for (int i = 0; i < N_STATS; i++)
           addItem(statLabels[i] + stats[i]);
        setItemSelected(iStat,true);
        setVisibleItemCount(stats.length);
        addChangeHandler(new ChangeHandler() {
           public void onChange(ChangeEvent event) {
              updateStatSlices(hoverList[0].getSelectedIndex(),
                               hoverList[1].getSelectedIndex());
              update(TouchedPointUpdateOption.TOUCHED_POINT_LOCKED);
           }
        });

     }

     // The two HoverUpdateable interface methods follow
     public void hoverCleanup(Curve.Point hoveredAwayFrom) {}
     public void hoverUpdate(Curve.Point hoveredOver) {
        for (int i = 0; i < N_STATS; i++) // update list text
           setItemText(i, statLabels[i] +
              getYAxis(0).formatAsTickLabel(Math.round(stats[i])));
        // highlight statistic pie slice is now displaying
        setSelectedIndex(iStat[getCurveIndex(getTouchedCurve())]);
        
     }

  }
    
// updates the chart with results of a new oil price simulation  
  private void updateChart() {
    double sum = INITIAL_PRICE;  // simple sum of all prices   
    double ssq = INITIAL_PRICE*INITIAL_PRICE;  // sum of squares of prices
    stats[MIN] = INITIAL_PRICE;
    stats[MAX] = INITIAL_PRICE;
    prices[0] = INITIAL_PRICE;
    for (int i=1; i < N_FORCASTED_MONTHS; i++) {
       prices[i] = prices[i-1] *
         (1 + MAX_MONTHLY_RELATIVE_CHANGE*(2*Math.random()-1));
       stats[MIN] = Math.min(stats[MIN], prices[i]);
       stats[MAX] = Math.max(stats[MAX], prices[i]);
       sum += prices[i];
       ssq +=  prices[i]*prices[i];
    }
    stats[AVG] = sum/N_FORCASTED_MONTHS;
    // use "average of squares minus square of average"
    // formula for variance to get standard deviation. 
    stats[STD] = Math.sqrt(ssq/N_FORCASTED_MONTHS -
                           stats[AVG]*stats[AVG]);

    getCurve(PRICE).clearPoints();
    getCurve(1).clearPoints();
    getCurve(2).clearPoints();
    for (int i = 0; i < N_FORCASTED_MONTHS; i++) {
    	if(i>=N_FORCASTED_MONTHS-1){
    	}else{
    		getCurve(PRICE).addPoint(i + 1,prices[i]);
    	}
//      getCurve(1).addPoint(i, y1[i]);
      getCurve(1).addPoint(i, i*i);
//      getCurve(2).addPoint(i, y2[i]);
      getCurve(2).addPoint(i, 170-(i*i));

      getCurve(3).addPoint(i, 200-(i*i));
      if (prices[i]!=stats[MIN] && prices[i]!=stats[MAX]) {
        getCurve(PRICE).getPoint().setAnnotationText(null); //no label
      }
      else {
        getCurve(PRICE).getPoint().setAnnotationFontSize(10);
        getCurve(PRICE).getPoint().setAnnotationFontWeight("bold");
        if (prices[i]==stats[MIN]) {
          getCurve(PRICE).getPoint().setAnnotationLocation(
            AnnotationLocation.SOUTH);
          getCurve(PRICE).getPoint().setAnnotationText(shortStatLabels[MIN]);
          getCurve(PRICE).getPoint().setAnnotationFontColor("blue");
        }
        else {
          getCurve(PRICE).getPoint().setAnnotationLocation(
            AnnotationLocation.NORTH);
           getCurve(PRICE).getPoint().setAnnotationText(shortStatLabels[MAX]);
           getCurve(PRICE).getPoint().setAnnotationFontColor("blue");
        }
      }
    }
    update();
  }
  
  public GChartExample11() {
     long t0 = System.currentTimeMillis();
  // misc chart configuration
     setChartSize(650, 250);
     setCanvasExpansionFactors(0, 0.3);
     setClipToDecoratedChart(true);
     setWidth("100%");
     setPlotAreaBackgroundColor("#CCC");
     setLegendBackgroundColor(getPlotAreaBackgroundColor());
     setGridColor("#EEE");
// convenience methods; these properties could also have been defined
// via CSS. See the javadoc comment for GChart.USE_CSS for more info.
     setBackgroundColor("#DDF");
     setBorderColor("black");
     setBorderWidth("1px");
     setBorderStyle("outset");
// title and footnotes (w. update button)
     setChartTitle(
"<b style='font-size: 16px'>Estimated Future Oil Prices <br>" +
"<b><i style='font-size: 10px'>All results are pseudo-random. " + 
"Randomize fully before you invest.<br>&nbsp;" + 
"</i></b>");
     setChartTitleThickness(60);
     setLegendVisible(true);
              
     final Button updateButton = new Button("Update");
     updateButton.setTitle(
"Click for new totally unbiased, totally random, estimates.");
     updateButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent w) {
          long t0 = System.currentTimeMillis();
          updateChart();
          long t1 = System.currentTimeMillis();
          updateTimeMsg.setText((t1-t0) + "ms");
          updateButton.setFocus(true);
        }
     });

// x-axis config    
     getXAxis().setAxisLabel(
"<small>time (months from now)</small>");
     getXAxis().setAxisLabelThickness(20);
     getXAxis().setTickCount(13);
     getXAxis().setTicksPerLabel(2);
     getXAxis().setAxisMin(0);
     getXAxis().setAxisMax(N_FORCASTED_MONTHS-1);
     getXAxis().setHasGridlines(true);
// y-axis config
     getYAxis(0).setAxisLabel(
"<center class=\"leftCanvas\">Price</center>");
     getYAxis(0).setAxisLabelThickness(30);
     getYAxis(0).setAxisMin(0);
     getYAxis(0).setAxisMax(200);
     getYAxis(0).setTickCount(17);
     getYAxis(0).setTicksPerLabel(4);
     getYAxis(0).setTickLabelFormat("$#.##");
     getYAxis(0).setYRightAxisShift(0);
     getYAxis(0).setHasGridlines(true);

     // y2-axis config
     /*getYAxis(1).setAxisLabel(
"<center><br>P<br>r<br>o<br>d<br>u<br>c<br>t<br>i<br>o<br>n</center>");*/
     getYAxis(1).setAxisLabel("<center class=\"rightCanvas\">Production</center>");
     getYAxis(1).setAxisLabelThickness(30);
     getYAxis(1).setAxisMin(-16);
     getYAxis(1).setAxisMax(196);
     getYAxis(1).setTickCount(5);
     getYAxis(1).setTickLabelFormat("#.##t");
     getYAxis(1).setYRightAxisShift(1);
     getYAxis(1).setHasGridlines(false);
     
  // y2-axis config
     getYAxis(2).setAxisLabel(
"<center class=\"rightCanvas\">Demand</center>");
     getYAxis(2).setAxisLabelThickness(30);
     getYAxis(2).setAxisMin(-87);
     getYAxis(2).setAxisMax(146);
     getYAxis(2).setTickCount(5);
     getYAxis(2).setTickLabelFormat("#.##t");
     getYAxis(2).setYRightAxisShift(2);
     getYAxis(2).setHasGridlines(false);
     
  // y3-axis config
     getYAxis(3).setAxisLabel(
"<center class=\"rightCanvas\">Revenue</center>");
     getYAxis(3).setAxisLabelThickness(30);
     getYAxis(3).setAxisMin(-87);
     getYAxis(3).setAxisMax(146);
     getYAxis(3).setTickCount(5);
     getYAxis(3).setTickLabelFormat("#.##t");
     getYAxis(3).setYRightAxisShift(2);
     getYAxis(3).setHasGridlines(false);
  
     addCurve();     // one curve per quarter
     getCurve().getSymbol().setSymbolType(
        SymbolType.VBAR_SOUTHWEST);
     getCurve().getSymbol().setBackgroundColor("#55F");
     getCurve().setLegendLabel("Price");
     getCurve().getSymbol().setModelWidth(0.5);
     getCurve().getSymbol().setBorderColor("#55F");
     getCurve().getSymbol().setBorderWidth(1);
     getCurve().getSymbol().setHoverLocation(AnnotationLocation.NORTH);
     getCurve().getSymbol().setHovertextTemplate(
    	       GChart.formatAsHovertext("Month=${x}<br>Price=${y}"));
     getCurve().getSymbol().setHoverXShift(-15);
     getCurve().getSymbol().setHoverYShift(20);
     getCurve().getSymbol().setBrushSize(10, getYChartSizeDecorated());
      getCurve().getSymbol().setBrushLocation(AnnotationLocation.SOUTH);
     getCurve().getSymbol().setDistanceMetric(1, 0); 
     getCurve().setXShift(-12);
     getCurve().setYAxis(Y_AXIS[0]);
     
     // adding 1st y axis
     addCurve();
     getCurve().setCurveData(curveData);
     getCurve().getSymbol().setSymbolType(
       SymbolType.LINE);
     // this line makes it continuously filled areas (not just bars):
     getCurve().getSymbol().setFillSpacing(1);
     getCurve().getSymbol().setBaseline(2);
     getCurve().getSymbol().setBorderWidth(1);
     getCurve().getSymbol().setBorderColor("green");
     getCurve().getSymbol().setFillThickness(2);
     getCurve().getSymbol().setBackgroundColor("green");
     getCurve().setLegendLabel("Production");
     getCurve().getSymbol().setWidth(1);
     getCurve().getSymbol().setHovertextTemplate(
       GChart.formatAsHovertext("Month=${x}<br>Production=${y}"));
     getCurve().getSymbol().setHoverYShift(-5);
     getCurve().setYAxis(Y_AXIS[0]);
     // position hover popup in plot area's bottom left corner
     getCurve().getSymbol().setHoverAnnotationSymbolType(
        SymbolType.ANCHOR_SOUTHWEST);
     getCurve().getSymbol().setHoverLocation(
        AnnotationLocation.NORTHEAST);
     getCurve().getSymbol().setHoverXShift(2);
     getCurve().getSymbol().setHoverYShift(2);
     // 3px external selection border around bars
     getCurve().getSymbol().setHoverSelectionBorderWidth(1);
     getCurve().getSymbol().setHoverSelectionHeight(5);
     getCurve().getSymbol().setHoverSelectionWidth(5);
     getCurve().getSymbol().setHoverSelectionSymbolType(
        SymbolType.PIE_SLICE_OPTIMAL_SHADING);
     getCurve().getSymbol().setHoverSelectionBackgroundColor("rgba(255,128,128,0.5)");
//     getCurve().getSymbol().setHoverSelectionBorderColor("rgba(0,0,0,1)");
     // tall brush thick enough to at least touch 1 point.
     getCurve().getSymbol().setBrushSize(40, getYChartSizeDecorated());
     // brush south of mouse ==> selects points below it
     getCurve().getSymbol().setBrushLocation(AnnotationLocation.SOUTH);
     // x-closeness main criterion, but y can still break ties
     getCurve().getSymbol().setDistanceMetric(10, 1);

     // adding 2nd y axis
     addCurve();
     getCurve().setCurveData(curveData);
     getCurve().getSymbol().setSymbolType(
       SymbolType.LINE);
     // this line makes it continuously filled areas (not just bars):
     getCurve().getSymbol().setFillSpacing(1);
     getCurve().getSymbol().setBaseline(2);
     getCurve().getSymbol().setBorderWidth(1);
     getCurve().getSymbol().setBorderColor("maroon");
     getCurve().getSymbol().setFillThickness(2);
     getCurve().getSymbol().setBackgroundColor("maroon");
     getCurve().setLegendLabel("Demand");
     getCurve().getSymbol().setWidth(1);
     getCurve().getSymbol().setHovertextTemplate(
       GChart.formatAsHovertext("Month=${x}<br>Demand=${y}"));
     getCurve().getSymbol().setHoverYShift(-5);
     getCurve().setYAxis(Y_AXIS[0]);
     // position hover popup in plot area's bottom left corner
     getCurve().getSymbol().setHoverAnnotationSymbolType(
        SymbolType.ANCHOR_SOUTHWEST);
     getCurve().getSymbol().setHoverLocation(
        AnnotationLocation.NORTHEAST);
     getCurve().getSymbol().setHoverXShift(2);
     getCurve().getSymbol().setHoverYShift(2);
     // 3px external selection border around bars
     getCurve().getSymbol().setHoverSelectionBorderWidth(1);
     getCurve().getSymbol().setHoverSelectionHeight(5);
     getCurve().getSymbol().setHoverSelectionWidth(5);
     getCurve().getSymbol().setHoverSelectionSymbolType(
        SymbolType.PIE_SLICE_OPTIMAL_SHADING);
     getCurve().getSymbol().setHoverSelectionBackgroundColor("rgba(255,128,128,0.5)");
//     getCurve().getSymbol().setHoverSelectionBorderColor("rgba(0,0,0,1)");
     getCurve().getSymbol().setBrushSize(40, getYChartSizeDecorated());
     getCurve().getSymbol().setBrushLocation(AnnotationLocation.SOUTH);
     getCurve().getSymbol().setDistanceMetric(10, 1);


  // adding 3rd y axis
     addCurve();
     getCurve().setCurveData(curveData);
     getCurve().getSymbol().setSymbolType(
       SymbolType.LINE);
     // this line makes it continuously filled areas (not just bars):
     getCurve().getSymbol().setFillSpacing(1);
     getCurve().getSymbol().setBaseline(2);
     getCurve().getSymbol().setBorderWidth(1);
     getCurve().getSymbol().setBorderColor("red");
     getCurve().getSymbol().setFillThickness(2);
     getCurve().getSymbol().setBackgroundColor("red");
     getCurve().setLegendLabel("Revenue");
     getCurve().getSymbol().setWidth(1);
     getCurve().getSymbol().setHovertextTemplate(
       GChart.formatAsHovertext("Month=${x}<br>Revenue=${y}"));
     getCurve().getSymbol().setHoverYShift(-5);
     getCurve().setYAxis(Y_AXIS[0]);
     // position hover popup in plot area's bottom left corner
     getCurve().getSymbol().setHoverAnnotationSymbolType(
        SymbolType.ANCHOR_SOUTHWEST);
     getCurve().getSymbol().setHoverLocation(
        AnnotationLocation.NORTHEAST);
     getCurve().getSymbol().setHoverXShift(2);
     getCurve().getSymbol().setHoverYShift(2);
     // 3px external selection border around bars
     getCurve().getSymbol().setHoverSelectionBorderWidth(1);
     getCurve().getSymbol().setHoverSelectionHeight(5);
     getCurve().getSymbol().setHoverSelectionWidth(5);
     getCurve().getSymbol().setHoverSelectionSymbolType(
        SymbolType.PIE_SLICE_OPTIMAL_SHADING);
     getCurve().getSymbol().setHoverSelectionBackgroundColor("rgba(255,128,128,0.5)");
//     getCurve().getSymbol().setHoverSelectionBorderColor("rgba(0,0,0,1)");
     getCurve().getSymbol().setBrushSize(40, getYChartSizeDecorated());
     getCurve().getSymbol().setBrushLocation(AnnotationLocation.SOUTH);
     getCurve().getSymbol().setDistanceMetric(10, 1);

     
     updateChart();
     long t1 = System.currentTimeMillis();
     updateTimeMsg.setText((t1-t0) + "ms");
     
// button must be rendered in browser before it can accept focus
     DeferredCommand.addCommand(new Command() {
       public void execute() {
          updateButton.setFocus(true);
       }
     });

   }

@Override
public void onModuleLoad() {
	new GChartExample11();
}

}
