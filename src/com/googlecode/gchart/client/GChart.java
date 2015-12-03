/* 
 * Copyright 2007-2010 John C. Gunther
 * 
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 * 
 */

package com.googlecode.gchart.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.core.client.GWT;

/**
 * A GChart can represent and display a line chart, a bar chart, a pie chart, an
 * area chart, or a chart that contains arbitrary combinations of line, bar,
 * pie, and/or area based curves.
 * 
 * <p>
 * For detailed examples, with screen shots, visit the <a
 * href="package-summary.html#ChartGallery">Chart Gallery</a>.
 * 
 * <p>
 * For detailed instructions on how to integrate Client-side GChart into your
 * GWT application, see <a href="package-summary.html#InstallingGChart">
 * Installing Client-side GChart</a>.
 * 
 * <p>
 * <b>CSS Style Rule</b>
 * <ul>
 * .gchart-GChart { the GChart's primary top-level styles }
 * </ul>
 * 
 * It is sometimes more natural to consider certain CSS attributes as properties
 * of a GChart Java object. So, GChart supports "CSS convenience methods" that
 * let you (optionally) use Java to specify GChart CSS attributes such as
 * <tt>border-color</tt> and <tt>background-color</tt>. See {@link #USE_CSS
 * USE_CSS} for a detailed description of these CSS convenience methods--which
 * won't interfere with standard CSS-based specifications if you never invoke
 * them.
 * 
 */

public class GChart extends Composite implements HasClickHandlers,
    HasDoubleClickHandlers, HasMouseDownHandlers, HasMouseMoveHandlers,
    HasMouseOutHandlers, HasMouseOverHandlers, HasMouseUpHandlers,
    HasMouseWheelHandlers {

  /**
   * Defines the location of a data point's annotation or hover annotation
   * (which can be defined by either plain text, HTML, or a widget) relative to
   * the location of that point's symbol. The "Field Summary" section below
   * lists all available annotation locations.
   * <p>
   * 
   * The default annotation location is {@link AnnotationLocation#SOUTH SOUTH}
   * for annotations and is symbol-type-dependent for hover annotations. See the
   * <tt>setHoverLocation</tt> method for list of these defaults.
   * 
   * <p>
   * 
   * You can further adjust the position of a point's annotation (or hover
   * annotation) by specifying non-zero positional shifts via the
   * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> (or via the
   * <tt>setHoverXShift</tt>, <tt>setHoverYShift</tt>), and
   * <tt>setHoverAnnotationSymbolType</tt> methods for hover annotations).
   * <p>
   * 
   * @see Curve.Point#setAnnotationLocation Point.setAnnotationLocation
   * @see Curve.Point#setAnnotationXShift Point.setAnnotationXShift
   * @see Curve.Point#setAnnotationYShift Point.setAnnotationYShift
   * @see Symbol#setHoverLocation Symbol.setHoverLocation
   * @see Symbol#setHoverAnnotationSymbolType
   *      Symbol.setHoverAnnotationSymbolType
   * @see Symbol#setHoverXShift Symbol.setHoverXShift
   * @see Symbol#setHoverYShift Symbol.setHoverYShift
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final class AnnotationLocation {
    // non-public tagging-only locations used by ANCHOR_MOUSE_* symbol types
    static final AnnotationLocation AT_THE_MOUSE =
        new AnnotationLocation(0, 0);
    static final AnnotationLocation AT_THE_MOUSE_SNAP_TO_X =
        new AnnotationLocation(0, 0);
    static final AnnotationLocation AT_THE_MOUSE_SNAP_TO_Y =
        new AnnotationLocation(0, 0);
    /**
     * Specifies that a point's annotation (label) should be positioned so as to
     * be centered on the symbol used to represent the point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation CENTER =
        new AnnotationLocation(0, 0);
    private static final AnnotationLocation north =
        new AnnotationLocation(0, -1);
    private static final AnnotationLocation west =
        new AnnotationLocation(-1, 0);
    private static final AnnotationLocation south =
        new AnnotationLocation(0, 1);
    
    /**
     * Specifies that a point's annotation (label) should be placed just above,
     * and centered horizontally on, vertical bars that grow down from a
     * horizontal baseline, and just below, and centered horizontally on,
     * vertical bars that grow up from a horizontal baseline.
     * 
     * <p>
     * 
     * This another name for <tt>AnnotationLocation.NORTH</tt>. Its sole purpose
     * is to clarify/document the behavior of this location type when used in
     * conjunction with curves that employ <tt>VBAR_BASELINE_*</tt> symbol
     * types.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see SymbolType#VBAR_BASELINE_CENTER SymbolType.VBAR_BASELINE_CENTER
     * 
     */
    public static final AnnotationLocation CLOSEST_TO_HORIZONTAL_BASELINE = north;

    /**
     * Specifies that a point's annotation (label) should be placed just to the
     * right of, and centered vertically on, horizontal bars that grow left from
     * a vertical baseline, and just to the left of, and centered vertically on,
     * horizontal bars that grow right from a vertical baseline.
     * 
     * <p>
     * 
     * This another name for <tt>AnnotationLocation.WEST</tt>. Its sole purpose
     * is to clarify/document the behavior of this location type when used in
     * conjunction with curves that employ the <tt>HBAR_BASELINE_*</tt> symbol
     * types.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see SymbolType#HBAR_BASELINE_CENTER SymbolType.HBAR_BASELINE_CENTER
     * 
     */
    
    public static final AnnotationLocation CLOSEST_TO_VERTICAL_BASELINE = west;
    
    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the right of, and vertically centered on, the symbol used to represent
     * the point.
     * 
     * @see Curve.Point#setAnnotationLocation
     */
    
    public static final AnnotationLocation EAST =
        new AnnotationLocation(1, 0);
    
    /**
     * Specifies that a point's annotation (label) should be placed just below,
     * and centered horizontally on, vertical bars that grow down from a
     * horizontal baseline, and just above, and centered horizontally on,
     * vertical bars that grow up from a horizontal baseline.
     * 
     * <p>
     * 
     * This another name for <tt>AnnotationLocation.SOUTH</tt>. Its sole purpose
     * is to clarify/document the behavior of this location type when used in
     * conjunction with curves that employ <tt>VBAR_BASELINE_*</tt> symbol
     * types.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see SymbolType#VBAR_BASELINE_CENTER SymbolType.VBAR_BASELINE_CENTER
     * 
     */
    public static final AnnotationLocation FARTHEST_FROM_HORIZONTAL_BASELINE = south;
    
    /**
     * Specifies that a point's annotation (label) should be placed just to the
     * left of, and centered vertically on, horizontal bars that grow left from
     * a vertical baseline, and just to the right of, and centered vertically
     * on, horizontal bars that grow right from a vertical baseline.
     * 
     * <p>
     * 
     * This another name for <tt>AnnotationLocation.EAST</tt>. Its sole purpose
     * is to clarify/document the behavior of this location type when used in
     * conjunction with curves that employ the <tt>HBAR_BASELINE_*</tt> family
     * of symbol types.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see SymbolType#HBAR_BASELINE_CENTER SymbolType.HBAR_BASELINE_CENTER
     * 
     */
    public static final AnnotationLocation FARTHEST_FROM_VERTICAL_BASELINE = EAST;
    
    /**
     * Specifies that a point's annotation (label) should be positioned just
     * inside, and centered on, the arc side of a pie slice.
     * <p>
     * 
     * You can move a pie slice's annotation a specific number of pixels
     * radially away from (or towards) the pie center by passing a positive (or
     * negative) argument to the associated <tt>Point</tt>'s
     * <tt>setAnnotationXShift</tt> method.
     * 
     * <p>
     * This is pie-friendly synonym for, and when used with non-pie symbol types
     * will behave exactly the same as, <tt>AnnotationLocation.NORTH</tt>
     * 
     * @see #OUTSIDE_PIE_ARC OUTSIDE_PIE_ARC
     * @see #ON_PIE_ARC ON_PIE_ARC
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see AnnotationLocation#NORTH NORTH
     */

    public static final AnnotationLocation INSIDE_PIE_ARC = north;

    /**
     * Specifies that a point's annotation (label) should be positioned just
     * above, and horizontally centered on, the symbol used to represent the
     * point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation NORTH = north;

    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the right of and above, the symbol used to represent the point.
     * 
     * @see Curve.Point#setAnnotationLocation
     */
    public static final AnnotationLocation NORTHEAST =
        new AnnotationLocation(1, -1);
    
    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the left of and above, the symbol used to represent the point.
     * 
     * @see Curve.Point#setAnnotationLocation
     */
    public static final AnnotationLocation NORTHWEST =
        new AnnotationLocation(-1, -1);

    /**
     * Specifies that a point's annotation (label) should be centered on the
     * center-point of the arc side of a pie slice.
     * <p>
     * 
     * You can move a pie slice's annotation a specific number of pixels
     * radially away from (or towards) the pie center by passing a positive (or
     * negative) argument to the associated <tt>Point</tt>'s
     * <tt>setAnnotationXShift</tt> method.
     * 
     * <p>
     * This is pie-friendly synonym for, and when used with non-pie symbol types
     * will behave exactly the same as, <tt>AnnotationLocation.CENTER</tt>
     * 
     * @see #OUTSIDE_PIE_ARC OUTSIDE_PIE_ARC
     * @see #INSIDE_PIE_ARC INSIDE_PIE_ARC
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see AnnotationLocation#CENTER CENTER
     * 
     */
    public static final AnnotationLocation ON_PIE_ARC = CENTER;

    /**
     * Specifies that a point's annotation (label) should be positioned just
     * outside, and centered on, the arc side of a pie slice.
     * <p>
     * 
     * You can move a pie slice's annotation a specific number of pixels
     * radially away from (or towards) the pie center by passing a positive (or
     * negative) argument to the associated <tt>Point</tt>'s
     * <tt>setAnnotationXShift</tt> method.
     * 
     * <p>
     * This is pie-friendly synonym for, and when used with non-pie symbol types
     * will behave exactly the same as, <tt>AnnotationLocation.SOUTH</tt>
     * 
     * @see #INSIDE_PIE_ARC INSIDE_PIE_ARC
     * @see #ON_PIE_ARC ON_PIE_ARC
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see AnnotationLocation#SOUTH SOUTH
     */
    public static final AnnotationLocation OUTSIDE_PIE_ARC = south;

    /**
     * Specifies that a point's annotation (label) should be positioned just
     * below, and horizontally centered on, the symbol used to represent the
     * point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation SOUTH = south;

    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the right of and below, the symbol used to represent the point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation SOUTHEAST =
        new AnnotationLocation(1, 1);
    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the left of and below, the symbol used to represent the point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation SOUTHWEST =
        new AnnotationLocation(-1, 1);

    /**
     * Specifies that a point's annotation (label) should be positioned just to
     * the left of, and vertically centered on, the symbol used to represent the
     * point.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     */
    public static final AnnotationLocation WEST = west;

    /*
     * These multiply the width and height of the annotation and the
     * symbol it is attached to in order to define the center of the
     * annotation (c.f. <tt>getUpperLeftX</tt> and
     * <tt>getUpperLeftY</tt> below), and thus the upper left corner
     * anchoring point.
     * 
     */ 
    private int heightMultiplier;
    private int widthMultiplier;

    private AnnotationLocation(int widthMultiplier, int heightMultiplier) {
      validateMultipliers(widthMultiplier, heightMultiplier);
      this.widthMultiplier = widthMultiplier;
      this.heightMultiplier = heightMultiplier;
    }

    /*
     * Retrieves a static location given its multipliers.
     */
    private static AnnotationLocation getAnnotationLocation(
        int widthMultiplier, int heightMultiplier) {
      final AnnotationLocation[][] locationMap = {
          { NORTHWEST, NORTH, NORTHEAST },
          { WEST, CENTER, EAST },
          { SOUTHWEST, SOUTH, SOUTHEAST } };
      // assumes both multiplier are -1, 0, or 1
      AnnotationLocation result = locationMap[heightMultiplier + 1][widthMultiplier + 1];
      return result;
    }

    /*
     * Negative width or height "turn the symbol inside-out", requiring
     * a corresponding "reflection" of annotation location (only needed
     * for baseline-based bar symbols).
     * 
     */ 
    static AnnotationLocation transform(AnnotationLocation a,
        int signWidth, int signHeight) {
      AnnotationLocation result = a;
      if (signWidth < 0 || signHeight < 0)
        result = getAnnotationLocation(
            signWidth * a.widthMultiplier, signHeight * a.heightMultiplier);

      return result;
    }

    /*
     * These define the alignment of the label within it's containing 1
     * x 1 Grid (&lt;table*gt;). For example, if this containing Grid is
     * to the left of the labeled symbol (widthMultiplier==-1) the
     * horizontal alignment will be ALIGN_RIGHT, so as to bring the
     * contained label flush against the left edge of the labeled
     * symbol.
     * 
     */ 
    HasHorizontalAlignment.HorizontalAlignmentConstant getHorizontalAlignment() {
      HasHorizontalAlignment.HorizontalAlignmentConstant result;
      if (widthMultiplier == -1)
        result = HasHorizontalAlignment.ALIGN_RIGHT;
      else if (widthMultiplier == 0)
        result = HasHorizontalAlignment.ALIGN_CENTER;
      else if (widthMultiplier == 1)
        result = HasHorizontalAlignment.ALIGN_LEFT;
      else
        throw new IllegalStateException("Invalid widthMultiplier: "
            + widthMultiplier + " 1, 0, or -1 were expected.");
      return result;
    }

    /* analogous to getHorizontalAlignment, but for vertical alignment */
    HasVerticalAlignment.VerticalAlignmentConstant getVerticalAlignment() {
      HasVerticalAlignment.VerticalAlignmentConstant result;
      if (heightMultiplier == -1)
        result = HasVerticalAlignment.ALIGN_BOTTOM;
      else if (heightMultiplier == 0)
        result = HasVerticalAlignment.ALIGN_MIDDLE;
      else if (heightMultiplier == 1)
        result = HasVerticalAlignment.ALIGN_TOP;
      else
        throw new IllegalStateException("Invalid heightMultiplier: "
                                        + heightMultiplier + " -1, 0, or 1 were expected.");
      return result;
    }

    /*
     * Given the x-coordinate at the center of the symbol that this
     * annotation annotates, the annotation's width, and the symbol's
     * width, this method returns the x-coordinate of the upper left
     * corner (left edge) of this annotation.
     */
    int getUpperLeftX(double x, double w, double symbolW) {
      int result = (int) Math.round(x
          + (widthMultiplier * (w + symbolW) - w) / 2.);
      return result;
    }

    /* analogous to getUpperLeftX, but for the y-coordinate (top edge) */
    int getUpperLeftY(double y, double h, double symbolH) {
      int result = (int) Math.round(y
          + (heightMultiplier * (h + symbolH) - h) / 2.);
      return result;
    }

    /*
     * This method returns the annotation location whose "attachment point"
     * keeps the annotation either completely outside, centered on, or
     * completely inside (depending on if the heightMultiplier of this
     * annotation is 1, 0, or -1) the point on the pie's circumference
     * associated with the given angle. <p>
     * 
     * The use of heightMultiplier rather than widthMultiplier is somewhat
     * arbitrary, but was chosen so that the NORTH, CENTER, and SOUTH annotation
     * locations have the same interpretation for a pie slice whose bisecting
     * radius points due south (due south is the default initial pie slice
     * orientation) and for a 1px x 1px BOX_CENTER type symbol positioned at the
     * due south position on the pie's circumference. As the
     * pie-slice-arc-bisection point moves clockwise around the pie perimeter,
     * the attachment point (except for vertically-centered annotations, which
     * remain centered on the pie arc) also moves clockwise, but in discrete
     * jumps (e.g. from NORTH, to NORTHEAST, to EAST, to SOUTHEAST, to SOUTH,
     * etc. for annotations inside the pie) so the annotation remains
     * appropriately attached to the center of the slice's arc as the angle
     * changes.
     * <p>
     * 
     * thetaMid is the conventional trigonometric angle measured
     * counter-clockwise from +x.
     * 
     */
    AnnotationLocation decodePieLocation(double thetaMid) {
		/*
	     * A sin or cos that is small enough so that the associated angle
	     * is horizontal (for sines) or vertical (for cosines) enough to
	     * warrant use of a "centered" annotation location.
	     * 
	     */ 
      final double LOOKS_VERTICAL_OR_HORIZONTAL_DELTA = 0.1;
      double sinTheta = Math.sin(thetaMid);
      double cosTheta = Math.cos(thetaMid);
      int pieTransformedWidthMultiplier = heightMultiplier
          * ((cosTheta < -LOOKS_VERTICAL_OR_HORIZONTAL_DELTA) ? -1
          : ((cosTheta > LOOKS_VERTICAL_OR_HORIZONTAL_DELTA) ? 1
          : 0));
      int pieTransformedHeightMultiplier = heightMultiplier
          * ((sinTheta < -LOOKS_VERTICAL_OR_HORIZONTAL_DELTA) ? 1
          : ((sinTheta > LOOKS_VERTICAL_OR_HORIZONTAL_DELTA) ? -1
          : 0));

      return getAnnotationLocation(pieTransformedWidthMultiplier,
          pieTransformedHeightMultiplier);

    }

  } // end of class AnnotationLocation

  /**
   * Defines keywords that specify
   * the location of the legend on the chart.  <p>
   *
   * <i>Tip:</i> You can emulate the missing <tt>OUTSIDE_TOP</tt> and
   * <tt>OUTSIDE_BOTTOM</tt> locations by using <tt>INSIDE_TOP</tt> or
   * <tt>INSIDE_BOTTOM</tt> and shifting the y position appropriately
   * via <tt>setLegendYShift</tt>.
   * <p>
   *
   * <i>Tip:</i> If GChart's internally generated, single column
   * formatted, legend key isn't appropriate, you can use your own
   * legend widget via <tt>setLegend</tt>.
   * 
   * 
   * @see #setLegendLocation setLegendLocation
   * @see #setLegendXShift setLegendYShift
   * @see #setLegendYShift setLegendYShift
   * @see #setLegend setLegend
   * 
   */
  
  public static class LegendLocation {
    private SymbolType symbolType;
    private AnnotationLocation annotationLocation;

    private LegendLocation(SymbolType symbolType,
        AnnotationLocation annotationLocation) {
      this.symbolType = symbolType;
      this.annotationLocation = annotationLocation;
    }

    SymbolType getSymbolType() {
      return symbolType;
    }

    AnnotationLocation getAnnotationLocation() {
      return annotationLocation;
    }

    /*
     * These initial pixel shifts from the symbol/location defined
     * initial positions are used, for example, to leave space for the
     * x, y or y2 axis ticks, tick labels, and axis labels. Note that
     * legend anchoring positions are always at compass point positions
     * around the plot area (via the ANCHOR_* family of symbol types).
     * 
     */
    int getInitialXShift(GChart g) {
      return 0;
    }
    int getInitialYShift(GChart g) {
      return 0;
    }
    
    /*
     * Retrieves the space taken up by the legend on the left and the right
     * sides of the chart when it is placed at this location. <p>
     * 
     * The INSIDE_* family of legend locations take up no space on the left or
     * right of the chart, so they just use the default (0). <p>
     * 
     * OUTSIDE_LEFT, OUTSIDE_RIGHT methods override getLeftThickness,
     * getRightThickness so as to account for the space the legend takes up when
     * positioned outside the chart. <p>
     * 
     * GChart uses these methods to adjust the relative positioning of various
     * chart parts so as to leave enough room for the legend, regardless of
     * where it is located.
     * 
     */

    int getLeftThickness(GChart g) {
      return 0;
    }

    int getRightThickness(GChart g) {
      return 0;
    }

    // the legend location to the right of the plot area
    private static final class RightLegendLocation extends LegendLocation {
      RightLegendLocation() {
        super(SymbolType.ANCHOR_EAST, AnnotationLocation.CENTER);
      }

      @Override
      int getInitialXShift(GChart g) {
        int result = g.getYAxis(0).getTickLabelThickness(false)
            + g.getYAxis(0).getTickSpace()
            + g.getYAxis(0).getTickLabelPadding()
            + g.getYAxis(0).getAxisLabelThickness()
            + g.getLegendThickness() / 2 - 15;
        return result;
      }

      @Override
      int getRightThickness(GChart g) {
        int result = g.getLegendThickness();
        return result;
      }
    }

    // the legend location to the left of the plot area
    private static final class LeftLegendLocation extends LegendLocation {
      LeftLegendLocation() {
        super(SymbolType.ANCHOR_WEST, AnnotationLocation.CENTER);
      }

      @Override
      int getInitialXShift(GChart g) {
        int result = -g.getYAxis(0).getTickLabelThickness(false)
            - g.getYAxis(0).getTickSpace()
            - g.getYAxis(0).getTickLabelPadding()
            - g.getYAxis(0).getAxisLabelThickness()
            - g.getLegendThickness() / 2;
        return result;
      }

      @Override
      int getLeftThickness(GChart g) {
        int result = g.getLegendThickness();
        return result;
      }
    }

    /**
     * Places the legend inside the plot area, centered along the bottom
     * edge.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_BOTTOM = new LegendLocation(
            SymbolType.ANCHOR_SOUTH, AnnotationLocation.NORTH);
    /**
     * Places the legend in the lower left inside corner of the plot area.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_BOTTOMLEFT = new LegendLocation(
            SymbolType.ANCHOR_SOUTHWEST, AnnotationLocation.NORTHEAST);
    /**
     * Places the legend in the lower right inside corner of the plot area.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_BOTTOMRIGHT = new LegendLocation(
            SymbolType.ANCHOR_SOUTHEAST, AnnotationLocation.NORTHWEST);
    /**
     * Places the legend inside the plot area, centered along the left
     * edge.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_LEFT = new LegendLocation(
            SymbolType.ANCHOR_WEST, AnnotationLocation.EAST);
    /**
     * Places the legend inside the plot area, centered along the right
     * edge.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_RIGHT = new LegendLocation(
            SymbolType.ANCHOR_EAST, AnnotationLocation.WEST);
    /**
     * Places the legend inside the plot area, centered along the top
     * edge.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_TOP = new LegendLocation(
            SymbolType.ANCHOR_NORTH, AnnotationLocation.SOUTH);

    /**
     * Places the legend in the upper left inside corner of the plot area.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_TOPLEFT = new LegendLocation(
        SymbolType.ANCHOR_NORTHWEST, AnnotationLocation.SOUTHEAST);
    /**
     * Places the legend in the upper right inside corner of the plot area.
     * 
     * @see GChart#setLegendLocation setLegendLocation
     */
    public final static LegendLocation INSIDE_TOPRIGHT = new LegendLocation(
        SymbolType.ANCHOR_NORTHEAST, AnnotationLocation.SOUTHWEST);
    /**
     * Places the legend so that it is vertically centered on the left edge of
     * the chart's plot area, and to the left of the y axis label.
     * 
     */
    public final static LegendLocation OUTSIDE_LEFT = new LeftLegendLocation();
    /**
     * Places the legend so that it is vertically centered on the right edge of
     * the chart's plot area, and to the right of the y2 axis label.
     * 
     */
    public final static LegendLocation OUTSIDE_RIGHT = new RightLegendLocation();
  } // class LegendLocation 

  /**
   * Represents an axis of the chart, for example, the x, y, or y2 axis. An axis
   * consists of the axis itself, along with its tick marks, tick labels and
   * gridlines.
   * 
   * @see XAxis XAxis
   * @see YAxis YAxis
   * @see Y2Axis Y2Axis
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   * 
   */
  public abstract class Axis {
	  //shifting the y rigth side axis
	  private int yRightAxisShift = 0;
    // points dropped if more than this number of axis lengths off chart
    private double outOfBoundsMultiplier = Double.NaN;
    // true for X, false for Y
    protected boolean isHorizontalAxis;
    // sys curve representing ticks
    protected int ticksId;
    // sys curve representing gridlines
    protected int gridlinesId;
    // sys curve representing axis line
    protected int axisId;
    // +/-1 for right/left axes
    protected int axisPosition;
    protected TickLocation tickLocation = DEFAULT_TICK_LOCATION;
    // number of developer curves on axis. Count does not include
    // system or invisible curves.
    private int nCurvesVisibleOnAxis = 0;

    void incrementCurves() {
      nCurvesVisibleOnAxis++;
    }

    void decrementCurves() {
      nCurvesVisibleOnAxis--;
    }

    // model unit limits associated with an axis
    protected class AxisLimits {
      double min;
      double max; // in user-defined model units

      AxisLimits(double min, double max) {
        this.min = min;
        this.max = max;
      }

      boolean equals(AxisLimits al) {
        boolean result = (al.min == min && al.max == max);
        return result;
      }
    }

    // different initial curr, prev ==> "limits have changed" state
    private AxisLimits currentLimits = new AxisLimits(Double.MAX_VALUE,
        -Double.MAX_VALUE);
    private AxisLimits previousLimits = new AxisLimits(-Double.MAX_VALUE,
        Double.MAX_VALUE);

    private Widget axisLabel;
    protected int axisLabelThickness = GChart.NAI;
    private boolean hasGridlines = false;
    protected int tickCount = DEFAULT_TICK_COUNT;
    // axes auto-scale whenever min or max are NaN.
    protected double axisMax = Double.NaN;
    protected double axisMin = Double.NaN;
    protected String tickLabelFontColor = DEFAULT_TICK_LABEL_FONT_COLOR;
    protected String tickLabelFontFamily = null;
    /*
     * In CSS font-size pixels. These define the height of each
     * character; our code relies on the rule of thumb that character
     * width is approximately 3/5th this height to obtain a reasonably
     * tight upper bound on tick label widths. So, even when Widget
     * based tick labels override these sizes, the specified size can
     * still impact the default amount of space reserved for the labels.
     * 
     */ 
    protected int tickLabelFontSize = DEFAULT_TICK_LABEL_FONTSIZE;
    protected String tickLabelFontStyle = DEFAULT_TICK_LABEL_FONT_STYLE;
    protected String tickLabelFontWeight = DEFAULT_TICK_LABEL_FONT_WEIGHT;
    protected String tickLabelFormat = DEFAULT_TICK_LABEL_FORMAT;
    protected int tickLabelThickness = GChart.NAI;
    protected int tickLabelPadding = 0;
    protected int ticksPerLabel = 1;
    protected int ticksPerGridline = 1;
    protected int tickLength = DEFAULT_TICK_LENGTH;
    protected int tickThickness = DEFAULT_TICK_THICKNESS;

    // is axis itself visible (has no impact ticks or their labels)
    boolean axisVisible = true;
    
    public void setYRightAxisShift(int shift){
    	this.yRightAxisShift = shift;
    }
    
    public int getYRightAxisShift(){
    	return yRightAxisShift;
    }

    /**
     * Adds a tick on this axis at the specified position. Note that explicitly
     * adding a single tick via this method will eliminate any implicitly
     * generated ticks associated with the <tt>setTickCount</tt> method.
     * <p>
     * 
     * The label associated with this tick will be generated by applying the
     * format specified via <tt>setTickLabelFormat</tt> to the specified
     * position.
     * <p>
     * 
     * This is a convenience method equivalent to
     * <tt>addTick(tickPosition, thisAxis.formatAsTickLabel(tickPosition), GChart.NAI,
     * GChart.NAI)</tt>. See {@link #addTick(double,String,int,int)
     * addTick(tickPosition,tickLabel,widthUpperBound,heightUpperBound)} for
     * details.
     * 
     * @param tickPosition
     *          the position, in model units, along this axis at which this tick
     *          is displayed. For example, if the axis range goes from 0 to 100,
     *          a tick at position 50 would appear in the middle of the axis.
     * 
     * @see #clearTicks clearTicks
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #formatAsTickLabel formatAsTickLabel
     * @see #setTickCount setTickCount
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see #setTickLabelFontSize setTickLabelFontSize
     * 
     */
    public void addTick(double tickPosition) {
      addTick(tickPosition, formatAsTickLabel(tickPosition));
    }

    // adds a labeled tick mark via this Axis' special system tick curve
    private void addTickAsPoint(double tickPosition, String tickLabel,
        Widget tickWidget, int widthUpperBound, int heightUpperBound) {

      Curve c = getSystemCurve(ticksId);
      int curveIndex = 0;
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  if(YTICKS_ID[i] == ticksId)
    		  curveIndex = i;
      }
      if (isHorizontalAxis)
        c.addPoint(tickPosition, axisPosition * Double.MAX_VALUE);
      else
        c.addPoint(axisPosition * Double.MAX_VALUE, tickPosition);

      // unlabeled tick--we are done, so return to save time
      if (null == tickLabel && null == tickWidget)
        return;

      // add an annotation representing the tick label
      Curve.Point p = c.getPoint();
      if (isHorizontalAxis) {
        // below tick on X, above it on (the future) X2
        p.setAnnotationLocation((axisPosition < 0) ?
            AnnotationLocation.SOUTH : AnnotationLocation.NORTH);
        if (tickLabelPadding != 0) // padding < 0 is rare but allowed
          p.setAnnotationYShift(axisPosition * tickLabelPadding);
        // else stick with default of 0 y-shift
        p.setAnnotationFontColor(getTickLabelFontColor());

      } else {
    	  int shift = 0;
        // to left of tick mark on Y, to right of it on Y2
    	  p.setAnnotationLocation((axisPosition < 0) ?
    	            AnnotationLocation.WEST : AnnotationLocation.EAST);
    	  if(axisPosition > 0){
        	/*shift = getYAxis(0).getTickLabelThickness(false)
                + getYAxis(0).getTickSpace() + getYAxis(0).getTickLabelPadding() + 30;*/
					for (int i = 1; i < curveIndex; i++) {
						shift += getYAxis(i).getTickLabelThickness(false)
								+ getYAxis(i).getTickSpace()
								+ getYAxis(i).getTickLabelPadding() + getYAxis(i).getAxisLabelThickness() / 2 + 30;
					}
        }
        /*if(yRightAxisShift > 2){
        	shift = shift - (yRightAxisShift - 2) * 7;
        }*/
        try {
        	String color = null;
        	int curves = getNCurves();
    		Axis actualyYAxis = getYAxis(curveIndex);
    		boolean flag = false;
        	for (int i=0; i<curves; i++){
        		flag=false;
        		YAxisId curveAxisID = getCurve(i).getYAxis();
        		Axis curveAxis = null;
        		for(int j = 0; j < Y_AXES_MAX; j++){
            		if (curveAxisID == Y_AXIS[j]){
            			curveAxis = GChart.this.getYAxis(j);
            		}
            	}
        		if(curveAxis == actualyYAxis){
        			//color = getCurve(i).getSymbol().getBorderColor();
        			color = "Black";
      				p.getAnnotation().setFontColor(color);
      	        	getSystemCurve(YLABEL_ID[curveIndex]).getPoint(0).getAnnotation().setFontColor(color);
      	        	getSystemCurve(YTICKS_ID[curveIndex]).getSymbol().setBorderColor(color);
      	        	//getSystemCurve(YAXIS_ID[curveIndex]).getSymbol().setBorderColor(color);

      	        	//getSystemCurve(YAXIS_ID[i]).getPoint(0).getAnnotation().setFontColor(color);
      	        	flag = true;
        		}
        		if(flag)break;
        	}
        	/*YAxisId yAxis2 = getCurve(curveIndex).getYAxis();
  		  for(int i = 0; i < Y_AXES_MAX; i++){
  			  if(yAxis2 == Y_AXIS[i]){
  				color = getCurve(i).getSymbol().getBorderColor();
  				p.getAnnotation().setFontColor(color);
  	        	getSystemCurve(YLABEL_ID[i]).getPoint(0).getAnnotation().setFontColor(color);
  	        	getSystemCurve(YTICKS_ID[i]).getSymbol().setBorderColor(color);
  	        	getSystemCurve(YAXIS_ID[i]).getPoint(0).getAnnotation().setFontColor(color);
  			  }
  		  }*/
        } catch(IllegalArgumentException illegalArgumentException){
        	p.setAnnotationFontColor(getTickLabelFontColor());
        }
        	
        if(yRightAxisShift ==  0){
        	yRightAxisShift = 1;
        }
        if (tickLabelPadding == 0)
          p.setAnnotationXShift(axisPosition * tickLabelPadding + shift);
        else 
        	p.setAnnotationXShift(shift * (yRightAxisShift - 1));
        // else stick with default of 0 x-shift
      }

      if (null != tickLabel)
        p.setAnnotationText(tickLabel, widthUpperBound,
            heightUpperBound);
      else if (null != tickWidget)
        p.setAnnotationWidget(tickWidget, widthUpperBound,
            heightUpperBound);

      p.setAnnotationFontSize(getTickLabelFontSize());
      p.setAnnotationFontFamily(getTickLabelFontFamily());
      p.setAnnotationFontStyle(getTickLabelFontStyle());
      
      p.setAnnotationFontWeight(getTickLabelFontWeight());

    }

    /**
     * Adds a tick at the specified position with the specified label on this
     * axis, whose width and height are within the specified upper-bounds.
     * 
     * <p>
     * Note that explicitly adding a single tick via this method will eliminate
     * any auto-generated ticks associated with the <tt>setTickCount</tt>
     * method.
     * 
     * <p>
     * Use this method to specify unusually spaced tick marks with labels that
     * do not directly reflect the position (for example, for a logarithmic
     * axis, or for a bar chart with special keyword-type labels, or a time axis
     * that places date and time on two separate lines).
     * 
     * @param tickPosition
     *          the position, in model units, along this axis at which the tick
     *          is displayed. For example, if the axis range goes from 0 to 1, a
     *          tick at position 0.5 would appear in the middle of the axis.
     * 
     * @param tickLabel
     *          the label for this tick. HTML is supported in tick labels, but
     *          it must be prefixed by <tt>&lt;html&gt</tt>. See the
     *          {@link Curve.Point#setAnnotationText(String,int,int)
     *          setAnnotationText} method for more information.
     * 
     * @param widthUpperBound
     *          an upper bound on the width of the text or HTML, in pixels. Use
     *          <tt>GChart.NAI</tt> to get GChart to estimate this width for
     *          you. See the <tt>setAnnotationText</tt> method for more
     *          information.
     * 
     * @param heightUpperBound
     *          an upper bound on the height of the text or HTML, in pixels. Use
     *          <tt>GChart.NAI</tt> to get GChart to estimate this height for
     *          you. See the <tt>setAnnotationText</tt> method for more
     *          information.
     * 
     * @see #clearTicks clearTicks
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickCount setTickCount
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see Curve.Point#setAnnotationText(String,int,int) setAnnotationText
     * @see Curve.Point#setAnnotationWidget setAnnotationWidget
     * 
     */
    public void addTick(double tickPosition, String tickLabel,
        int widthUpperBound, int heightUpperBound) {
      chartDecorationsChanged = true;
      if (GChart.NAI != tickCount) { // clear out any auto-generated ticks
        Curve cTicks = getSystemCurve(ticksId);
        cTicks.clearPoints();
        tickCount = GChart.NAI;
      }
      addTickAsPoint(tickPosition, tickLabel, null, widthUpperBound,
          heightUpperBound);
    }

    /**
     * Adds a tick at the specified position with the specified label on this
     * axis.
     * <p>
     * 
     * This is a convenience method equivalent to
     * <tt>addTick(tickPosition, tickLabel, GChart.NAI,
     * GChart.NAI)</tt>. Most applications can usually just use this convenience
     * method. See {@link #addTick(double,String,int,int)
     * addTick(tickPosition,tickLabel, widthUpperBound,heightUpperBound)} for
     * the fine print.
     * 
     * @param tickPosition
     *          the position, in model units, along this axis at which the tick
     *          is displayed.
     * 
     * @param tickLabel
     *          the plain text or (<tt>&lt;html&gt</tt>-prefixed) HTML defining
     *          the tick's label.
     * 
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * 
     */
    public void addTick(double tickPosition, String tickLabel) {
      addTick(tickPosition, tickLabel, GChart.NAI, GChart.NAI);
    }

    /**
     * Adds a widget-defined tick label at the specified position, whose width
     * and height are within the specified upper-bounds.
     * <p>
     * 
     * This method is similar to <tt>addTick(double,String,int,int)</tt> except
     * that it uses a widget, rather than a string, to define the tick's label.
     * Although the string-based method is faster on first chart rendering, and
     * uses less memory, the widget-based method allows you to change the label
     * independently of the chart--potentially bypassing (or speeding up)
     * expensive chart updates later on.
     * <p>
     * 
     * You might use a widget-based tick label to pop up a dialog that allows
     * the user to edit the parameters defining the axis (min, max, etc.)
     * whenever they click on one of the tick labels on that axis, to define
     * hovertext that appears when the user mouses over a tick label, to use
     * images for your tick labels, etc.
     * 
     * @param tickPosition
     *          the position, in model units, along this axis at which the tick
     *          is displayed. For example, if the axis range goes from 0 to 1, a
     *          tick at position 0.5 would appear in the middle of the axis.
     * 
     * @param tickWidget
     *          the label for this tick, as defined by any GWT Widget.
     * 
     * @param widthUpperBound
     *          an upper bound on the width of the widget, in pixels. If this
     *          and the next parameter are omitted, GChart will use
     *          <tt>DEFAULT_WIDGET_WIDTH_UPPERBOUND</tt>.
     * 
     * @param heightUpperBound
     *          an upper bound on the height of the widget, in pixels. If this
     *          and the previous parameter are omitted, GChart will use <tt>
     *  DEFAULT_WIDGET_HEIGHT_UPPERBOUND</tt>
     * 
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see Curve.Point#setAnnotationWidget setAnnotationWidget
     * @see #DEFAULT_WIDGET_WIDTH_UPPERBOUND DEFAULT_WIDGET_WIDTH_UPPERBOUND
     * @see #DEFAULT_WIDGET_HEIGHT_UPPERBOUND DEFAULT_WIDGET_HEIGHT_UPPERBOUND
     */
    public void addTick(double tickPosition, Widget tickWidget,
        int widthUpperBound, int heightUpperBound) {
      chartDecorationsChanged = true;
      if (GChart.NAI != tickCount) { // clear out any auto-generated ticks
        Curve cTicks = getSystemCurve(ticksId);
        cTicks.clearPoints();
        tickCount = GChart.NAI;
      }
      addTickAsPoint(tickPosition, null, tickWidget, widthUpperBound,
          heightUpperBound);
    }

    /**
     * Adds a Widget-defined tick label at the specified position. Convenience
     * method equivalent to <tt>addTick(tickPosition, tickWidget,
     *  DEFAULT_WIDGET_WIDTH_UPPERBOUND,
     *  DEFAULT_WIDGET_HEIGHT_UPPERBOUND)</tt>.
     * 
     * @param tickPosition
     *          the position, in model units, along this axis at which the tick
     *          is displayed. For example, if the axis range goes from 0 to 1, a
     *          tick at position 0.5 would appear in the middle of the axis.
     * 
     * @param tickWidget
     *          the label for this tick, as defined by any GWT Widget.
     * 
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * 
     */
    public void addTick(double tickPosition, Widget tickWidget) {
      addTick(tickPosition, tickWidget, DEFAULT_WIDGET_WIDTH_UPPERBOUND,
          DEFAULT_WIDGET_HEIGHT_UPPERBOUND);
    }

    /**
     * 
     * Removes all ticks from this axis. Specifically, erases any ticks that
     * were explicitly specified via <tt>addTick</tt>, and also sets the tick
     * count to 0.
     * <p>
     * 
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * 
     */
    public void clearTicks() {
      chartDecorationsChanged = true;
      tickCount = GChart.NAI;
      Curve c = getSystemCurve(ticksId);
      c.clearPoints();
    }

    /**
     * Converts a pixel, client-window coordinate position along this axis into
     * the model units associated with this axis.
     * <p>
     * 
     * For example, if the client coordinate associated with this axis' midpoint
     * were passed to this method, it would return
     * <tt>(getAxisMin() + getAxisMax())/2.0</tt>.
     * <p>
     * 
     * <small> Note that the client/model coordinate mapping used is as of the
     * last <tt>update</tt>. Before the first <tt>update</tt>, this method
     * returns <tt>GChart.NaN</tt>. This method also invokes either
     * <tt>getAbsoluteTop</tt> (for the y or y2 axis) or
     * <tt>getAbsoluteLeft</tt> (for the x axis), and these GWT methods return 0
     * if the chart isn't actually rendered within the browser. So, results
     * likely won't be useful to you until after the page containing your chart
     * becomes visible to the user. Since most applications are expected to
     * invoke this method in response to the user mousing over the page, these
     * requirements should usually be satisfied. </small>
     * <p>
     * 
     * <small> Saurabh Hirani in <a href=
     * "http://groups.google.com/group/Google-Web-Toolkit/msg/80301715acb6f719"
     * > this GWT Forum post</a> and in GChart <a
     * href="http://code.google.com/p/gchart/issues/detail?id=21">issue #22</a>
     * most recently suggested the need for client to model coordinate mapping.
     * Client to model conversion was requested earlier in GChart <a
     * href="http://code.google.com/p/gchart/issues/detail?id=9">issue #9</a>
     * from <a href="http://yoxel.com">yoxel.com</a>. </small>
     * 
     * @param clientCoordinate
     *          a pixel-based coordinate that defines the dimension associated
     *          with this axis in the standard client window coordinates of GWT.
     * 
     * @return the location defined by the client-coordinate argument, but
     *         converted into the model units associated with this axis.
     * 
     * @see #getMouseCoordinate getMouseCoordinate
     * @see #modelToClient modelToClient
     * @see #pixelToModel pixelToModel
     * @see #modelToPixel modelToPixel
     * 
     * 
     */
    public abstract double clientToModel(int clientCoordinate);

    // these are used in formatting tick positions into tick labels:
    private NumberFormat numberFormat =
        NumberFormat.getFormat(DEFAULT_TICK_LABEL_FORMAT);
    private DateTimeFormat dateFormat =
        DateTimeFormat.getShortDateTimeFormat();
    private final int NUMBER_FORMAT_TYPE = 0;
    private final int DATE_FORMAT_TYPE = 1;
    private final int LOG10INVERSE_FORMAT_TYPE = 2;
    private final int LOG2INVERSE_FORMAT_TYPE = 3;
    private int tickLabelFormatType = NUMBER_FORMAT_TYPE;

    /**
     * 
     * Applies this axis' tick label format to format a given value.
     * 
     * @return the value formated as per this axis' currently specified tick
     *         label format.
     * 
     * @see #setTickLabelFormat(String) setTickLabelFormat
     * 
     */
    public String formatAsTickLabel(double value) {
      String result = null;
      switch (tickLabelFormatType) {
      case NUMBER_FORMAT_TYPE:  
        result = numberFormat.format(value);
        break;
      case DATE_FORMAT_TYPE:
        Date transDate = new Date((long) value);
        result = dateFormat.format(transDate);
        break;
      case LOG10INVERSE_FORMAT_TYPE:
        value = Math.pow(10., value);
        result = numberFormat.format(value);
        break;
      case LOG2INVERSE_FORMAT_TYPE:
        value = Math.pow(2., value);
        result = numberFormat.format(value);
        break;
      default:
        throw new IllegalStateException("Invalid tick label format type:" +
          tickLabelFormatType + ". Likely cause: a GChart bug.");
      }
      return result;
    }

    /**
     * @deprecated
     * 
     *             Equivalent to the better-named formatAsTickLabel.
     * 
     *             <p>
     * 
     * @see #formatAsTickLabel formatAsTickLabel
     * 
     */
    public String formatNumberAsTickLabel(double value) {
      return formatAsTickLabel(value);
    }

    /**
     * Returns the previously specified label of this axis.
     * 
     * @return the Widget used as the label of this axis
     * 
     * @see #setAxisLabel setAxisLabel
     * 
     */
    public Widget getAxisLabel() {
      return axisLabel;
    }

    /**
     * Returns the thickness of the axis-label-holding region adjacent to the
     * region allocated for this axis' tick labels.
     * <p>
     *
     * If the axis label thickness is specified explicitly, that value
     * is returned. Otherwise, with an undefined (<tt>GChart.NAI</tt>)
     * axis label thickness, an heuristic based on the text or HTML
     * defining the axis label, and an estimate of character size, is
     * used to estimate axis width.
     * 
     * <p>
     * 
     * @return the thickness of the axis-label-holding region, in pixels.
     * 
     * @see #setAxisLabelThickness setAxisLabelThickness
     * 
     */
    public int getAxisLabelThickness() {
      int result = 0;
      // Base class implementation is for y axes (x-axis will override).
      final int EXTRA_CHARWIDTH = 2; // 1-char padding on each side
      final int DEF_CHARWIDTH = 1; // when widget has no text
      if (GChart.NAI != axisLabelThickness)
        result = axisLabelThickness;
      else if (null == getAxisLabel())
        result = 0;
      else if (getAxisLabel() instanceof HasHTML) {
        int charWidth = htmlWidth(
            ((HasHTML) (getAxisLabel())).getHTML());
        result = (int) Math.round((charWidth + EXTRA_CHARWIDTH)
            * getTickLabelFontSize()
            * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
      } else if (getAxisLabel() instanceof HasText) {
        String text = ((HasText) (getAxisLabel())).getText();
        result = (int) Math.round((EXTRA_CHARWIDTH +
            ((null == text) ? 0 : text.length()))
            * getTickLabelFontSize()
            * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
      } else {
        // non-text widget. Not a clue, just use def width
        result = (int) Math.round((DEF_CHARWIDTH + EXTRA_CHARWIDTH)
            * getTickLabelFontSize()
            * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
      }
      return result;
    }

    /**
     * Returns the maximum value displayed on this axis. If the explicitly
     * specified maximum value is undefined (<tt>Double.NaN</tt>) the maximum
     * value returned by this function is calculated as the maximum of all of
     * the values either displayed on this axis via points on a curve, or
     * explicitly specified via tick positions.
     * 
     * @return maximum value visible on this axis, in "model units" (arbitrary,
     *         application-specific, units)
     * 
     * @see #setAxisMax setAxisMax
     * @see #getDataMin getDataMin
     * @see #getDataMax getDataMax
     */
    public double getAxisMax() {

      if (!(axisMax != axisMax)) { // x!=x is a faster isNaN
        return axisMax;
      } else if (GChart.NAI != tickCount) {
        return getDataMax();
      } else {
        return Math.max(getDataMax(), getTickMax());
      }
    }

    /**
     * 
     * Returns the minimum value displayed on this axis. If the minimum value is
     * undefined (<tt>Double.NaN</tt>) the minimum value returned by this
     * function is the minimum of all of the values either displayed on this
     * axis via points on a curve, or explicitly specified via tick positions.
     * 
     * @return minimum value visible on this axis, in "model units" (arbitrary,
     *         application-specific, units)
     * 
     * @see #setAxisMin setAxisMin
     */
    public double getAxisMin() {
      if (!(axisMin != axisMin)) { // x!=x is a faster isNaN
        return axisMin; // explicitly set
      } else if (GChart.NAI != tickCount) {
        return getDataMin();
      } else {
        return Math.min(getDataMin(), getTickMin());
      }
    }

    /**
     * Is axis line visible on the chart? Note that this property only
     * determines the visibility of the axis line itself. It does not control
     * the visibility of the tick marks or tick labels along this axis.
     * <p>
     * 
     * @return true if the axis line is visible, false otherwise.
     * 
     * @see #setAxisVisible setAxisVisible
     * 
     */
    public boolean getAxisVisible() {
      return axisVisible;
    }

    /**
     * The maximum number of "axis lengths" a point can be off this axis before
     * it is completely dropped from the chart's rendering.
     * 
     * @return the out of bounds multiplier.
     * 
     * @see #setOutOfBoundsMultiplier setOutOfBoundsMultiplier
     * 
     */
    public double getOutOfBoundsMultiplier() {
      return outOfBoundsMultiplier;
    }

    /**
     * Returns the maximum data value associated with values represented on this
     * axis. For example, for the left y-axis, this would be the largest y-value
     * of all points contained in curves that are displayed on the left y-axis.
     * 
     * @return the maximum value associated with values mapped onto this axis.
     * 
     * @see #getDataMin getDataMin
     * @see #getAxisMax getAxisMax
     * @see #getAxisMin getAxisMin
     * 
     */
    public abstract double getDataMax();

    /**
     * Returns the minimum data value associated with values represented on this
     * axis. For example, for the left y-axis, this would be the smallest
     * y-value of all points contained in curves that are displayed on the left
     * y-axis.
     * 
     * @return the minimum value associated with values mapped onto this axis.
     * 
     * @see #getDataMax getDataMax
     * @see #getAxisMax getAxisMax
     * @see #getAxisMin getAxisMax
     * 
     */
    public abstract double getDataMin();

    /**
     * Returns the gridline setting previously made with
     * <tt>setHasGridlines</tt>.
     * 
     * @return true if gridlines have been enabled, false if not.
     * 
     * @see #setHasGridlines setHasGridlines
     * 
     */
    public boolean getHasGridlines() {
      return hasGridlines;
    }

    /**
     * Returns the coordinate along this axis that is associated with the last
     * "GChart-tracked" mouse location.
     * <p>
     * 
     * The coordinate returned is in the "scale" associated with the axis. For
     * example, if the axis mininum is 0 and the maximum is 100, and the mouse
     * is at the axis midpoint, this method would return 50.
     * <p>
     * 
     * The main intended use for this method is to allow you to create points
     * that, if they have x and y coordinates defined by calling this method on
     * appropriate axes, will be positioned on the chart at the last
     * GChart-tracked mouse location.
     * <p>
     * 
     * As the user moves their mouse over the chart, GChart watches those mouse
     * moves and updates it's currently "tracked" mouse location. That
     * internally maintained position is the basis for the value returned by
     * this method. Note that the actual, physical, mouse cursor position could
     * differ from this GChart-tracked position because:
     * 
     * <p>
     * 
     * <ol>
     * 
     * <li>The mouse has moved off the chart, and it's GChart-tracked location
     * has become undefined (this method returns <tt>Double.NaN</tt> in that
     * case)
     * 
     * <li>You have invoked <tt>setHoverTouchingEnabled(false)</tt> which means
     * that mouse moves are no longer tracked, so the last GChart-tracked mouse
     * location will be the last position that the user clicked on.
     * 
     * <li>You have popped up a modal dialog that "eats" mouse moves so GChart
     * no longer sees them. In that case, the GChart-tracked mouse location is
     * the location the mouse was at when the modal dialog popped up.
     * 
     * <li>You are mousing over the opened hover widget (popup). Note that, to
     * prevent the user from accidentally "touching" nearby points while
     * interacting with the opened hover widget, GChart ignores mouse moves over
     * the opened hover widget.
     * 
     * <li>Other, similar, reasons.
     * 
     * </ol>
     * <p>
     * 
     * In other words, this routine tells you where, for hit testing and hover
     * selection feedback purposes, GChart considers the mouse to be, not the
     * actual physical location of the mouse. Despite the potential for
     * differences, in most cases, with the default setting of
     * <tt>setHoverTouchingEnabled(true)</tt>, and when you are not over the
     * opened hover widget, you can use the value returned by this method as if
     * it represented the physical mouse location.
     * <p>
     * 
     * For an example that uses this method to create points at the current
     * mouse location within a very simple line chart editor, see <a
     * href="package-summary.html#GChartExample22a">the Chart Gallery's
     * GChartExample22a</a>.
     * <p>
     * 
     * 
     * @return the coordinate, projected along this axis, in the scale defined
     *         by this axis, representing the position GChart has currently
     *         "tracked" the mouse to, or <tt>Double.NaN</tt> if GChart has
     *         tracked the mouse right off the edge of the chart.
     * 
     * @see #clientToModel clientToModel
     * @see #modelToClient modelToClient
     * @see #pixelToModel pixelToModel
     * @see #modelToPixel modelToPixel
     * @see GChart#setHoverTouchingEnabled setHoverTouchingEnabled
     * 
     */
    public abstract double getMouseCoordinate();

    /**
     * Returns the number of visible curves displayed on this axis.
     * <p>
     * 
     * @return the number of visible curves on this axis, or <tt>0</tt> if there
     *         are no visible curves on this axis.
     * 
     * @see Axis#setVisible setVisible
     * 
     */
    public int getNCurvesVisibleOnAxis() {
      return nCurvesVisibleOnAxis;
    }

    /**
     * Returns the number of ticks on this axis.
     * 
     * @return the number of ticks on this axis.
     * 
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #clearTicks clearTicks
     * 
     */
    public int getTickCount() {
      int result = tickCount;
      if (GChart.NAI == tickCount) {
        Curve c = getSystemCurve(ticksId);
        result = c.getNPoints();
      }
      return result;

    }

    /**
     * Returns the CSS font-weight specification to be used by this axis' tick
     * labels.
     * 
     * @return font-weight of this axis' tick labels
     * 
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     */
    public String getTickLabelFontWeight() {
      return tickLabelFontWeight;
    }

    /**
     * Returns the color of the font used to display the text of the tick labels
     * on this axis.
     * 
     * 
     * @return CSS color string defining the color of the text of the tick
     *         labels for this axis.
     * 
     * @see #setTickLabelFontColor setTickLabelFontColor
     * 
     * @see #DEFAULT_TICK_LABEL_FONT_COLOR DEFAULT_TICK_LABEL_FONT_COLOR
     * 
     * 
     * 
     */
    public String getTickLabelFontColor() {
      return tickLabelFontColor;
    }

    /**
     * Returns the font-family of the font used to render tick labels on this
     * axis.
     * <p>
     * 
     * @return the CSS font-family used to render the tick labels of this axis.
     * 
     * @see #setTickLabelFontFamily setTickLabelFontFamily
     */
    public String getTickLabelFontFamily() {
      return tickLabelFontFamily;
    }

    /**
     * Returns the CSS font size, in pixels, used for tick labels on this axis.
     * 
     * @return the tick label font size in pixels
     * 
     * @see #setTickLabelFontSize setTickLabelFontSize
     */
    public int getTickLabelFontSize() {
      return tickLabelFontSize;
    }

    /**
     * Returns the font-style of the font used to render tick labels on this
     * axis (typically either "italic" or "normal")
     * 
     * @return the CSS font-style in which tick labels of this axis are
     *         rendered.
     * 
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     */
    public String getTickLabelFontStyle() {
      return tickLabelFontStyle;
    }

    /**
     * Returns the tick label numeric format string for this axis.
     * 
     * @return numeric format used to generate tick labels.
     * 
     * @see #setTickLabelFormat setTickLabelFormat
     * 
     */
    public String getTickLabelFormat() {
      return tickLabelFormat;
    }

    /**
     * Returns the amount of padding (blank space) between the ticks and their
     * labels.
     * <p>
     * 
     * @return amount of padding between ticks and their labels, in pixels.
     * 
     * @see #setTickLabelPadding setTickLabelPadding
     * 
     */
    public int getTickLabelPadding() {
      return tickLabelPadding;
    }

    /*
     * Does real work of public getTickLabelThickness; flag saves time
     * during repeated calls made in updateChartDecorations.
     * 
     */ 
    int getTickLabelThickness(boolean needsPopulation) {
      int maxLength = 0;
      int result;
      if (tickLabelThickness != GChart.NAI)
        result = tickLabelThickness;
      else { // use an heuristic to estimate thickness
        if (needsPopulation)
          maybePopulateTicks();
        Curve c = getSystemCurve(ticksId);
        int nTicks = c.getNPoints();
        for (int i = 0; i < nTicks; i++) {
          String tt = c.getPoint(i).getAnnotationText();
          if (null != tt)
            maxLength = Math.max(maxLength,
              Annotation.getNumberOfCharsWide(tt));
        }
        result = (int) Math.round(maxLength * tickLabelFontSize
            * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
      }
      return result;
    }

    /**
     * Returns the thickness of the band adjacent to this axis that GChart will
     * allocate to hold this axis' tick labels.
     * <p>
     * 
     * @return width of band, in pixels, GChart will reserve for this axis' tick
     *         labels.
     * 
     * @see #setTickLabelThickness setTickLabelThickness
     * 
     */
    public int getTickLabelThickness() {
      int result = getTickLabelThickness(true);
      return result;
    }

    /**
     * Returns the ratio of the number of ticks to the number of ticks that have
     * an associated gridline.
     * 
     * @return number of ticks per gridline for this axis
     * 
     * @see #setTicksPerGridline setTicksPerGridline
     * 
     */
    public int getTicksPerGridline() {
      return ticksPerGridline;
    }

    /**
     * Returns the ratio of the number of ticks to the number of labeled ticks.
     * 
     * @return number of ticks per label.
     * 
     * @see #setTicksPerLabel setTicksPerLabel
     * 
     */
    public int getTicksPerLabel() {
      return ticksPerLabel;
    }

    /**
     * Returns the length of ticks for this axis.
     * 
     * @return the length of this axis' ticks, in pixels.
     * 
     * @see #setTickLength setTickLength
     */
    public int getTickLength() {
      return tickLength;
    }

    /*
     * GChart adds a pixel to even, centered, tick lengths (only
     * odd-length HTML ticks can be exactly centered on 1px axis)
     * 
     */ 
    int getActualTickLength() {
      int result = tickLength;
      if (TickLocation.CENTERED == tickLocation && 0 == (tickLength % 2)
          && tickLength > 0)
        result++;
      return result;
    }

    /**
     * Returns relative location of ticks on this axis.
     * <p>
     * 
     * @see #setTickLocation setTickLocation
     * 
     * @return <tt>TickLocation.INSIDE</tt>, <tt>TickLocation.OUTSIDE</tt>, or
     *         <tt>TickLocation.CENTERED</tt>
     * 
     */
    public TickLocation getTickLocation() {
      return tickLocation;
    }

    /**
     * Returns the amount of space along the axis reserved for the tick marks
     * themselves, in pixels.
     * <p>
     * 
     * This equals the length of the part of the tick that is outside of the
     * plot area.
     * 
     * @see #setTickLength setTickLength
     * @see #setTickLabelPadding setTickLabelPadding
     * @see #setTickLocation setTickLocation
     * 
     * @return the space GChart will allocate just outside the axis to hold any
     *         tick marks.
     * 
     */

    public int getTickSpace() {
      int result;
      if (TickLocation.CENTERED == tickLocation)
        result = (tickLength + 1) / 2; // round up to nearest pixel
      else if (TickLocation.OUTSIDE == tickLocation)
        result = tickLength;
      else // TickLocation.INSIDE
        result = 0;

      return result;
    }

    /**
     * Returns the thickness of ticks for this axis.
     * 
     * @return the thickness of this axis' ticks, in pixels.
     * 
     * @see #setTickThickness setTickThickness
     * @see #getTickLength getTickLength
     */
    public int getTickThickness() {
      return tickThickness;
    }

    /**
     * Converts a coordinate position in the model units associated with this
     * axis into a corresponding coordinate position expressed in standard GWT
     * client-window pixel coordinates.
     * <p>
     * 
     * For example, consider a completely undecorated chart (no axes, tick
     * labels, legend keys, etc.) that exactly fills a 1000px wide client
     * window, and whose x-axis min and max are 0 and 100. Then
     * <tt>getXAxis().modelToClient(50)</tt> would return <tt>500</tt>.
     * <p>
     * 
     * <small> Note that the client/model coordinate mapping used is as of the
     * last <tt>update</tt>. Before the first <tt>update</tt>, this method
     * returns <tt>GChart.NaN</tt>. This method also invokes either
     * <tt>getAbsoluteTop</tt> (for the y or y2 axis) or
     * <tt>getAbsoluteLeft</tt> (for the x axis), and these GWT methods return 0
     * if the chart isn't actually rendered within the browser. So, results
     * likely won't be useful to you until after the page containing your chart
     * becomes visible to the user. Since most applications are expected to
     * invoke this method in response to the user mousing over the page, these
     * requirements should usually be satisfied. </small>
     * 
     * @param modelCoordinate
     *          the position along this axis defined in the model units
     *          associated with this axis.
     * 
     * @return a pixel-based coordinate that defines the position associated
     *         with the argument in the standard pixel, client window,
     *         coordinates of GWT.
     * 
     * @see #getMouseCoordinate getMouseCoordinate
     * @see #clientToModel clientToModel
     * @see #pixelToModel pixelToModel
     * @see #modelToPixel modelToPixel
     * @see #modelToPlotAreaPixel modelToPlotAreaPixel
     * 
     */
    public abstract double modelToClient(double modelCoordinate);

    /**
     * Converts a coordinate position in the model units associated with this
     * axis into a corresponding coordinate position expressed in GChart's
     * decorated chart pixel coordinates.
     * <p>
     * 
     * These coordinates have their origin at the upper left corner of the
     * decorated GChart, and x pixel-coordinates that increase as you move
     * right, and y pixel-coordinates that increase as you move down. They are
     * related to GWT's standard client window coordinates via the following
     * equations:
     * 
     * <pre>
     * xClient = plotPanel.getAbsoluteLeft() - Window.getScrollLeft() + xPixel;
     * yClient = plotPanel.getAbsoluteTop() - Window.getScrollTop() + yPixel;
     * </pre>
     * <p>
     * 
     * In the above <tt>plotPanel</tt> is an internal <tt>AbsolutePanel</tt>
     * GChart creates to hold the entire, decorated, chart. Apart from borders
     * and such applied to the GChart as a whole, its absolute top and left
     * positions should be the same as those of the GChart itself.
     * <p>
     * 
     * <i>Tip:</i> In applications that continuously track mouse moves over the
     * chart, and where absolute and scroll positions cannot change, you can
     * gain a significant performance boost by computing the difference between
     * pixel and client coordinates once (
     * <tt>modelToPixel(axisMin)-modelToClient(axisMin)</tt>) and then adding
     * that difference to the client coordinates to get the pixel coordinates,
     * and then using <tt>pixelToModel</tt>, instead of using
     * <tt>clientToModel</tt> directly, which must repeatedly call GWT's scroll
     * and absolute position methods.
     * <p>
     * 
     * For example, for a completely undecorated chart (no tick labels, legend
     * keys, etc.) the plot area takes up the entire chart. In that case, if the
     * pixel units of the plot area range from <tt>0...100</tt> along this axis,
     * and the model coordinates range from <tt>0...10</tt> along this axis,
     * then <tt>modelToPixel(modelCoordinate)</tt> returns
     * <tt>10*modelCoordinate</tt>.
     * <p>
     * 
     * The model/pixel mapping is as of the last <tt>update</tt>; this method
     * returns <tt>Double.NaN</tt> before the first <tt>update</tt>. Note that,
     * unlike <tt>clientToModel</tt> and <tt>modelToClient</tt>, the GChart does
     * <i>not</i> need to be actually rendered within the browser for you to use
     * this method--a call to update is sufficient.
     * <p>
     * 
     * <i>Tip:</i> If you need to access this mapping before the first real
     * update, you can explicitly specify the min and max of this axis via
     * <tt>setAxisMin</tt> and <tt>setAxisMax</tt>, and then call
     * <tt>update</tt> before adding any curves to the chart (which, since the
     * chart is empty, should be very fast). This approach will allow you to
     * convert between model and pixel coordinates before the first real update,
     * and before the chart is rendered in the browser.
     * <p>
     * 
     * @param modelCoordinate
     *          a position on this axis expressed in the model units associated
     *          with this axis.
     * 
     * @return the distance, in pixels, from the left edge (for the x axis) or
     *         top edge (for the y or y2 axis) of the decorated chart to the
     *         given position on this axis.
     * 
     * @see #getMouseCoordinate getMouseCoordinate
     * @see #clientToModel clientToModel
     * @see #modelToClient modelToClient
     * @see #modelToPlotAreaPixel modelToPlotAreaPixel
     * @see #pixelToModel pixelToModel
     * 
     */
    public abstract double modelToPixel(double modelCoordinate);

    /**
     * Converts a coordinate position in the model units associated with this
     * axis into a corresponding coordinate position expressed in GChart's plot
     * area pixel coordinates.
     * <p>
     * 
     * These coordinates have their origin at the upper left corner of the plot
     * area, and x pixel-coordinates that increase as you move right, and y
     * pixel-coordinates that increase as you move down.
     * <p>
     * 
     * The plot area is the rectangular region bounded by the chart's axes, and
     * with a size specified via <tt>setChartSize</tt>, where the chart's curves
     * are typically displayed.
     * <p>
     * 
     * Apart from a shift in the origin of the pixel coordinates used, this
     * method works just like <tt>modelToPixel</tt>; see that method for
     * additional details, tips, and restrictions.
     * 
     * @param modelCoordinate
     *          a position on this axis expressed in the model units associated
     *          with this axis.
     * 
     * @return the distance, in pixels, from the left edge (for the x axis) or
     *         top edge (for the y or y2 axis) of the plot area to the given
     *         position on this axis.
     * 
     * @see #getMouseCoordinate getMouseCoordinate
     * @see #plotAreaPixelToModel plotAreaPixelToModel
     * @see #modelToPixel modelToPixel
     * @see #setChartSize setChartSize
     * 
     */
    public abstract double modelToPlotAreaPixel(double modelCoordinate);

    /**
     * Converts a coordinate position in GChart's decorated chart pixel
     * coordinates into the model units associated with this axis.
     * <p>
     * 
     * GChart's decorated chart pixel coordinates have their origin at the upper
     * left corner of the decorated GChart, and x pixel-coordinates that
     * increase as you move right, and y pixel-coordinates that increase as you
     * move down. They are related to GWT's standard client window coordinates
     * via the following equations:
     * 
     * <pre>
     * xClient = plotPanel.getAbsoluteLeft() - Window.getScrollLeft() + xPixel;
     * yClient = plotPanel.getAbsoluteTop() - Window.getScrollTop() + yPixel;
     * </pre>
     * <p>
     * 
     * In the above <tt>plotPanel</tt> is an internal <tt>AbsolutePanel</tt>
     * GChart creates to hold the entire, decorated, chart. Apart from borders
     * and such applied to the GChart as a whole, its absolute top and left
     * positions should be the same as those of the GChart itself.
     * <p>
     * 
     * For example, for a completely undecorated chart (no tick labels, legend
     * keys, etc.) the plot area takes up the entire chart. In that case, if the
     * pixel units of the plot area range from <tt>0...100</tt> along this axis,
     * and the model coordinates range from <tt>0...10</tt> along this axis,
     * then <tt>pixelToModel(pixelCoordinate)</tt> returns
     * <tt>pixelCoordinate/10.</tt>.
     * <p>
     * 
     * The model/pixel mapping is as of the last <tt>update</tt>; this method
     * returns <tt>Double.NaN</tt> before the first <tt>update</tt>. Note that,
     * unlike <tt>clientToModel</tt> and <tt>modelToClient</tt>, the GChart does
     * <i>not</i> need to be actually rendered within the browser for you to use
     * this method.
     * <p>
     * 
     * <i>Tip:</i> If you need to access this mapping before the first real
     * update, you can explicitly specify the min and max of this axis via
     * <tt>setAxisMin</tt> and <tt>setAxisMax</tt>, and then call
     * <tt>update</tt> before adding any curves to the chart (which, since the
     * chart is empty, should be very fast). This approach will allow you to
     * convert between model and pixel coordinates before the first real update,
     * and before the chart is rendered in the browser.
     * <p>
     * 
     * @param pixelCoordinate
     *          the distance, in pixels, from the left edge (for the x axis) or
     *          top edge (for the y or y2 axis) of the decorated chart to a
     *          point on this axis.
     * 
     * @return that same position on this axis expressed in the the model units
     *         associated with this axis.
     * 
     * @see #getMouseCoordinate getMouseCoordinate
     * @see #clientToModel clientToModel
     * @see #modelToClient modelToClient
     * @see #modelToPixel modelToPixel
     * @see #plotAreaPixelToModel plotAreaPixelToModel
     * 
     */
    public abstract double pixelToModel(int pixelCoordinate);

    /**
     * Converts a coordinate position in GChart's plot area pixel coordinates
     * into the model units associated with this axis.
     * <p>
     * 
     * GChart's plot area pixel coordinates have their origin at the upper left
     * corner of the plot area, and x pixel-coordinates that increase as you
     * move right, and y pixel-coordinates that increase as you move down.
     * <p>
     * 
     * The plot area is the rectangular region bounded by the chart's axes, and
     * with a size specified via <tt>setChartSize</tt>, where the chart's curves
     * are typically displayed.
     * <p>
     * 
     * Apart from a shift in the origin of the pixel coordinates used, this
     * method works just like <tt>pixelToModel</tt>; see that method for
     * additional details, tips, and restrictions.
     * 
     * @param pixelCoordinate
     *          the distance, in pixels, from the left edge (for the x axis) or
     *          top edge (for the y or y2 axis) of the plot area to a point on
     *          this axis.
     * 
     * @return that same position on this axis expressed in the the model units
     *         associated with this axis.
     * 
     * @see #modelToPlotAreaPixel modelToPlotAreaPixel
     * @see #clientToModel clientToModel
     * @see #pixelToModel pixelToModel
     * @see #setChartSize setChartSize
     * 
     */
    public abstract double plotAreaPixelToModel(int pixelCoordinate);

    /**
     * Specifies the label of this axis.
     * <p>
     * 
     * This label will be positioned just outside of, and centered lengthwise
     * on, the region adjacent to this axis that GChart reserves for this axis'
     * tick labels.
     * 
     * @param axisLabel
     *          a Widget to use as the label of the entire axis.
     * 
     * @see #getAxisLabel getAxisLabel
     * @see #setTickLabelThickness setTickLabelThickness
     * @see #setAxisLabelThickness setAxisLabelThickness
     * 
     */

    public void setAxisLabel(Widget axisLabel) {
      this.axisLabel = axisLabel;
      chartDecorationsChanged = true;
    }

    /**
     * Convenience method equivalent to <tt>setAxisLabel(new HTML(html))</tt>
     * 
     * @param html
     *          HTML text used to define the axis label
     * 
     * @see #setAxisLabel(Widget) setAxisLabel(Widget)
     */
    public void setAxisLabel(String html) {
      setAxisLabel(new HTML(html));
    }

    /**
     * Sets the thickness of the axis-label-holding region adjacent to the
     * region allocated for tick labels.
     * <p>
     * 
     * The axis label widget will be centered in this region. Choose a thickness
     * large enough to hold the largest font size you want users to be able to
     * zoom up to without the axis label spilling over into adjacent regions.
     * <p>
     * 
     * If the axis label thickness is <tt>GChart.NAI</tt> (the default),
     * and the widget defining the axis label implements
     * <tt>HasHTML</tt> (or <tt>HasText</tt>) then GChart uses a
     * thickness based on the estimated number of non-tag characters in
     * the first <tt>&lt;br&gt;</tt>, <tt>&lt;li&gt;</tt> or
     * <tt>&lt;tr&gt;</tt> delimited line for y-axis labels, and based
     * on the estimated number of <tt>&lt;br&gt;</tt>,
     * <tt>&lt;li&gt;</tt> or <tt>&lt;tr&gt;</tt> delimited text lines
     * for x-axis labels.  <p>
     * 
     * 
     * @param thickness
     *          the thickness of the axis-label-holding region, in pixels, or
     *          <tt>GChart.NAI</tt> to use GChart's character-based default
     *          thickness estimates.
     * 
     * @see #getAxisLabelThickness getAxisLabelThickness
     * @see #setAxisLabel setAxisLabel
     */
    public void setAxisLabelThickness(int thickness) {
      axisLabelThickness = thickness;
      chartDecorationsChanged = true;
    }

    /**
     * Specifies the maximum value visible on this axis.
     * <p>
     * 
     * Aspects of the chart rendered beyond this maximum will be clipped if the
     * chart's <tt>clipToPlotArea</tt> property is <tt>true</tt>.
     * 
     * <p>
     * 
     * If <tt>Double.NaN</tt> is specified, this maximum is auto-determined as
     * described in <tt>getAxisMax</tt>.
     * 
     * <p>
     * <i>Performance tip:</i> Using auto-determined axis limits (via
     * <tt>Double.NaN</tt>) forces GChart, at the next update, to re-render many
     * chart elements whenever the min or max data value displayed on this axis
     * changes. These (often expensive) re-renderings can be avoided by using
     * explicitly specified axis limits whenever possible.
     * <p>
     * 
     * @param max
     *          maximum value visible on this axis, in "model units" (arbitrary,
     *          application-specific, units) or <tt>Double.NaN</tt> (the default
     *          value) to use an auto-determined maximum.
     * 
     * @see #getAxisMax getAxisMax
     * @see #getDataMin getDataMin
     * @see #getDataMax getDataMax
     * @see GChart#setClipToPlotArea GChart.setClipToPlotArea
     * @see Curve#setClipToPlotArea Curve.setClipToPlotArea
     * 
     */
    public void setAxisMax(double max) {
      chartDecorationsChanged = true;
      this.axisMax = max;
    }

    /**
     * Specifies the minimum value of this axis.
     * <p>
     * 
     * Aspects of the chart rendered at positions before this minimum value will
     * be clipped if the chart's <tt>clipToPlotArea</tt> property is
     * <tt>true</tt>.
     * <p>
     * 
     * If <tt>Double.NaN</tt> is specified, this minimum is auto-determined as
     * described in <tt>getAxisMin</tt>.
     * 
     * <p>
     * <i>Performance tip:</i> Using auto-determined axis limits (via
     * <tt>Double.NaN</tt>) forces GChart, at the next update, to re-render many
     * chart elements whenever the min or max data value displayed on this axis
     * changes. These (often expensive) re-renderings can be avoided by using
     * explicitly specified axis limits whenever possible.
     * <p>
     * 
     * @param min
     *          minimum value visible on this axis, in "model units" (arbitrary,
     *          application-specific, units), or Double.NaN (the default) to use
     *          an auto-determined minimum.
     * 
     * @see #getAxisMin getAxisMin
     * @see #getDataMin getDataMin
     * @see #getDataMax getDataMax
     * 
     */
    public void setAxisMin(double min) {
      // min can change axis label width ==> changes position of plot area
      chartDecorationsChanged = true;
      this.axisMin = min;
    }

    /**
     * Defines if this axis is visible. Note that this property only defines the
     * visibility of the axis line itself, it does not control the visibility of
     * tick marks or tick labels associated with the axis.
     * <p>
     * 
     * <i>Tip:</i>Tick marks can be made invisible by using
     * <tt>setTickThickness</tt> to set the tick thickness to 0. Tick labels can
     * be made invisible by using <tt>setTickLabelFontColor</tt> to set the tick
     * label color to the chart's background color.
     * <p>
     * 
     * @param axisVisible
     *          false to hide axis, true to show it.
     * 
     * @see #setTickThickness setTickThickness
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #getAxisVisible getAxisVisible
     */
    public void setAxisVisible(boolean axisVisible) {
      chartDecorationsChanged = true;
      this.axisVisible = axisVisible;
    }

    /**
     * 
     * Defines the maximum number of "axis lengths" a point can be off this axis
     * before it is completely dropped from the chart's rendering.
     * <p>
     * 
     * Note that any connecting lines to/from such out of bounds points will
     * also be dropped.
     * <p>
     * 
     * The exact formulas that define how this <tt>outOfBoundsMultiplier</tt> is
     * related to these lower and upper "point dropping" bounds is:
     * <p>
     * 
     * <pre>
     *   lowerBound = axisMin - outOfBoundsMultiplier*(axisMax-axisMin)
     *   upperBound = axisMax + outOfBoundsMultiplier*(axisMax-axisMin)
     * </pre>
     * 
     * <p>
     * Here <tt>axisMin</tt> and <tt>axisMax</tt> are the smallest and largest
     * values visible in the plot area along this axis.
     * 
     * <p>
     * 
     * For example, if the out of bounds multiplier is <tt>10</tt>, and if your
     * axis range goes from <tt>1</tt> to <tt>6</tt>, points with coordinates
     * less than <tt>-49 (1 - 10*(6-1)</tt> or greater than
     * <tt>56 (6 + 10*(6-1))</tt> do not get rendered.
     * <p>
     * 
     * The out of bounds multiplier can be used to speed up the rendering of
     * charts with many points far outside of the plot area. Such situations can
     * arise, for example, in charts that manipulate axis limits in order to
     * support pan and zoom. Because out of limits points, as well as any
     * connecting lines to/from such points, are not rendered at all, much time
     * is saved compared to mere clipping, which first renders each connecting
     * line or filled region before clipping it.
     * <p>
     * 
     * Note that using this out of bounds multiplier, though faster than simple
     * clipping, has the drawback that connecting lines from any dropped point
     * that may intersect the plot area will not get drawn. Specific
     * applications can avoid this problem by choosing their out of bounds
     * multiplier large enough so that such dropped connecting lines are either
     * very unlikely or impossible. For example, a time sequence chart with
     * points spaced at regular pixel intervals along x could simply choose a
     * multiplier larger than the fraction of the x-axis occupied by one of
     * these intervals.
     * <p>
     * 
     * <small> <i>Note#1:</i> In IE, attempting to render points at very large
     * pixel coordinates (>100,000px) could cause the browser to go into the
     * "Not Responding" state. And, in FF2 and Chrome, rendering results for
     * lines drawn to positions far off (in testing, problems were encountered
     * at around 10,000px) the plot area are unreliable. If you prefer to simply
     * never render such "far out" lines rather than deal with such anamolies,
     * choose an <tt>outOfBoundsMultiplier</tt> that excludes such problem
     * points entirely.
     * <p>
     * 
     * <i>Note#2:</i> Out of bounds checking on a chart that has no out of
     * bounds points can slow chart rendering by up to 5% or so. If you have
     * lots of out of bounds points you will easily regain this cost in saved
     * rendering time, but if only a few points are out of bounds, it may be
     * faster to disable out of bounds checking completely by setting this
     * multiplier on every axis to <tt>Double.NaN</tt> (this is GChart's
     * default). </small>
     * 
     * @param outOfBoundsMultiplier
     *          number of axis lengths beyond which points are not rendered or
     *          <tt>Double.NaN</tt> (the default) to render all points,
     *          regardless of how far off the axis they are.
     * 
     * @see GChart#setClipToPlotArea GChart.setClipToPlotArea
     * @see Curve#setClipToPlotArea Curve.setClipToPlotArea
     * @see #setClipToDecoratedChart setClipToDecoratedChart
     * @see #getOutOfBoundsMultiplier getOutOfBoundsMultiplier
     */

    public void setOutOfBoundsMultiplier(double outOfBoundsMultiplier) {
      invalidateAccessibleCurves();
      this.outOfBoundsMultiplier = outOfBoundsMultiplier;
    }

    /**
     * Specifies if this axis should have gridlines. When an axis has gridlines,
     * tick marks with indexes <tt>0, N,
     * * 2*N,...</tt> where <tt>N</tt> is the value of this axis'
     * <tt>ticksPerGridline</tt> property, are in effect extended across the
     * entire chart.
     * 
     * @param hasGridlines
     *          true to display gridlines, false (the default) to not display
     *          them.
     * 
     * @see #getHasGridlines getHasGridlines
     * @see #setTicksPerGridline setTicksPerGridline
     * 
     */
    public void setHasGridlines(boolean hasGridlines) {
      chartDecorationsChanged = true;
      this.hasGridlines = hasGridlines;
    }

    /**
     * Sets the number of ticks to be placed on this axis. The default tick
     * count is 10. Ticks are always evenly spaced across the entire axis,
     * unless explicitly specified via <tt>addTick</tt>.
     * <p>
     * 
     * Note that setting the tick count overrides (erases) any ticks explicitly
     * specified via <tt>addTick</tt>.
     * 
     * @param tickCount
     *          the number of ticks for this axis.
     * 
     * @see #getTickCount getTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double, String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * 
     */
    public void setTickCount(int tickCount) {
      chartDecorationsChanged = true;
      // eliminate user specified ticks
      getSystemCurve(ticksId).clearPoints();
      this.tickCount = tickCount;
    }

    /**
     * Specifies the weight of the font used in this axis' tick labels.
     * 
     * @param cssWeight
     *          the weight of the font, such as bold, normal, light, 100, 200,
     *          ... 900, for tick labels.
     * 
     * @see #getTickLabelFontWeight getTickLabelFontWeight
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #DEFAULT_TICK_LABEL_FONT_WEIGHT DEFAULT_TICK_LABEL_FONT_WEIGHT
     * 
     */
    public void setTickLabelFontWeight(String cssWeight) {
      chartDecorationsChanged = true;
      // assure that any existing ticks are updated with new weight
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++)
        c.getPoint(i).setAnnotationFontWeight(cssWeight);
      tickLabelFontWeight = cssWeight;
    }

    /**
     * Specifies the color of the font used to render tick labels for this axis.
     * 
     * <p>
     * For more information on standard CSS color specifications see the
     * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
     * <p>
     * 
     * @param cssColor
     *          color of the font used to display this axis' tick labels, in
     *          standard CSS format.
     * 
     * @see #getTickLabelFontColor getTickLabelFontColor
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #DEFAULT_TICK_LABEL_FONT_COLOR DEFAULT_TICK_LABEL_FONT_COLOR
     * 
     */
    public void setTickLabelFontColor(String cssColor) {
      chartDecorationsChanged = true;
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++)
        c.getPoint(i).setAnnotationFontColor(cssColor);
      tickLabelFontColor = cssColor;
    }

    /**
     * Specifies the CSS font-family of this axis' tick labels.
     * <p>
     * 
     * If <tt>null</tt> (the default) tick labels will just use the font
     * family specified via <tt>GChart.setFontFamily</tt>.
     * 
     * @param cssFontFamily
     *          any valid CSS font-family, such as
     *          <tt>"serif", "sans-serif", "monospace", "cursive",
     *          "fantasy"</tt> or <tt>"Arial, sans-serif" or
     *          <tt>null</tt> to use the font family specified by
     *          <tt>GChart.setFontFamily</tt>.
     * 
     * @see #setFontFamily(String) GChart.setFontFamily
     * @see #getTickLabelFontFamily getTickLabelFontFamily
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #DEFAULT_FONT_FAMILY DEFAULT_FONT_FAMILY
     * 
     */
    public void setTickLabelFontFamily(String cssFontFamily) {
      chartDecorationsChanged = true;
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++)
        c.getPoint(i).setAnnotationFontFamily(cssFontFamily);
      tickLabelFontFamily = cssFontFamily;
    }

    /**
     * Specifies the CSS font-style of this axis' tick labels.
     * 
     * @param cssStyle
     *          any valid CSS font-style, namely, <tt>normal, italic,
     *          oblique,</tt> or <tt>inherit</tt>.
     * 
     * @see #getTickLabelFontStyle getTickLabelFontStyle
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #DEFAULT_TICK_LABEL_FONT_STYLE DEFAULT_TICK_LABEL_FONT_STYLE
     */
    public void setTickLabelFontStyle(String cssStyle) {
      chartDecorationsChanged = true;
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++)
        c.getPoint(i).setAnnotationFontStyle(cssStyle);
      tickLabelFontStyle = cssStyle;
    }

    /**
     * Sets the CSS font size for tick labels on this axis, in pixels.
     * 
     * @param tickLabelFontSize
     *          the font size of tick labels displayed on this axis.
     * 
     * @see #getTickLabelFontSize getTickLabelFontSize
     * @see #setTickLabelFormat setTickLabelFormat
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see GChart#DEFAULT_TICK_LABEL_FONTSIZE DEFAULT_TICK_LABEL_FONTSIZE
     * 
     */

    public void setTickLabelFontSize(int tickLabelFontSize) {
      chartDecorationsChanged = true;
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++)
        c.getPoint(i).setAnnotationFontSize(tickLabelFontSize);
      this.tickLabelFontSize = tickLabelFontSize;
    }

    /**
     * Specifies a format string to be used in converting the numeric values
     * associated with each tick on this axis into tick labels. This string must
     * follow the conventions of the number format patterns used by the GWT <a
     * href="http://google-web-toolkit.googlecode.com/svn/javadoc/1.4/com/google/gwt/i18n/client/NumberFormat.html"
     * > NumberFormat</a> class, <i>with three exceptions:</i>
     * <p>
     * <ol>
     * 
     * <li><i>Log10 inverse prefix</i>: If the string begins with the prefix
     * <tt>=10^</tt> the value is replaced with <tt>pow(10.,value)</tt> and the
     * so-transformed value is then formatted using the part of the format
     * string that comes after this prefix, which must be a valid GWT
     * <tt>NumberFormat</tt> pattern (e.g. "##.##").
     * <p>
     * For an example of how to use this prefix to create a semi-log plot, see
     * <a href="package-summary.html#GChartExample04">the Chart Gallery's
     * GChartExample04</a>.
     * <p>
     * 
     * <li><i>Log2 inverse prefix</i>: If the string begins with the prefix
     * <tt>=2^</tt> the value is replaced with <tt>pow(2.,value)</tt> and the
     * so-transformed value is then formatted using the part of the format
     * string that comes after this prefix, which must be a valid GWT
     * <tt>NumberFormat</tt> pattern.
     * <p>
     * 
     * <li><i>Date casting prefix</i>: If the string begins with the prefix
     * <tt>=(Date)</tt> the value is replaced with
     * <tt>new Date((long) value)</tt> and the so-transformed value is then
     * formatted using the format string that comes after this prefix, which
     * must be a valid GWT <a href="http://google-web-toolkit.googlecode.com/svn/javadoc/1.4/com/google/gwt/i18n/client/DateTimeFormat.html"
     * > DateTimeFormat</a> pattern (e.g. "yyyy-MM-dd&nbsp;HH:mm"). For the
     * special case format string of <tt>"=(Date)"</tt> (just the date casting
     * prefix) GChart uses the <tt>DateTimeFormat</tt> returned by the
     * <tt>DateTimeFormat.getShortDateTimeFormat</tt> method.
     * <p>
     * 
     * Note that the values associated with this Axis must represent the number
     * of milliseconds since January 1, 1970 (in the GMT time zone) whenever
     * this date casting prefix is used.
     * <p>
     * 
     * For example, if the x-axis tick label format were
     * "=(Date)MMM-dd-yyyy HH", then, for a tick located at the x position of 0,
     * the tick label would be "Jan-01-1970 00" (on a client in the GMT time
     * zone) and for a tick located at the x position of 25*60*60*1000 (one day
     * + one hour, in milliseconds) the tick label would be "Jan-02-1970 01"
     * (again, on a GMT-based client). Results would be shifted appropriately on
     * clients in different time zones.
     * <p>
     * 
     * Note that if your chart is based on absolute, GMT-based, millisecond
     * times then date labels will change when your chart is displayed on
     * clients in different time zones. Sometimes, this is what you want. To
     * keep the date labels the same in all time zones, convert date labels into
     * Java <tt>Date</tt> objects in your client-side code, then use the
     * <tt>Date.getTime</tt> method, also in your client-side code, to convert
     * those dates into the millisecond values GChart requires. The <a
     * href="package-summary.html#GChartExample12"> Chart Gallery's
     * GChartExample12</a> illustrates how to use this second approach to
     * produce a time series chart whose date-time labels are the same in all
     * time zones.
     * 
     * <p>
     * 
     * <blockquote><small>
     * 
     * Ben Martin describes an alternative (and more flexible) approach to
     * formatting time series tick labels in his <a
     * href="http://www.linux.com/feature/132854">GChart tutorial</a>. Ben's
     * article, along with Malcolm Gorman's related <a href=
     * "http://groups.google.com/group/Google-Web-Toolkit/msg/6125ce39fd2339ac"
     * > GWT forum post</a> were the origin of this date casting prefix. Thanks!
     * </small></blockquote>
     * 
     * </ol>
     * 
     * <p>
     * Though HTML text is not supported in the tick label format string, you
     * can change the size, weight, style, and color of tick label text via the
     * <tt>setTickLabelFont*</tt> family of methods. You <i>can</i> use HTML in
     * tick labels (e.g. for a multi-line x-axis label) but but only if you
     * define each tick label explicitly using the <tt>addTick</tt> method.
     * 
     * @param format
     *          an appropriately prefixed GWT <tt>NumberFormat</tt> compatible
     *          or GWT <tt>DateTimeFormat</tt> compatible format string that
     *          defines how to convert tick values into tick labels.
     * 
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #setTickLabelFontStyle setTickLabelFontStyle
     * @see #setTickLabelFontColor setTickLabelFontColor
     * @see #setTickLabelFontWeight setTickLabelFontWeight
     * @see #getTickLabelFormat getTickLabelFormat
     */
    public void setTickLabelFormat(String format) {
      // interpret prefixes and create an appropriate formatter
      if (!tickLabelFormat.equals(format)) {
        chartDecorationsChanged = true;
        if (format.startsWith("=(Date)")) {
          String transFormat = format.substring("=(Date)".length());
          if (transFormat.equals("")) // so "=(Date)" works
            dateFormat = DateTimeFormat.getShortDateTimeFormat();
          else
            // e.g. "=(Date)mm/dd/yy hh:mm"
            dateFormat = DateTimeFormat.getFormat(transFormat);
          tickLabelFormatType = DATE_FORMAT_TYPE;
        } else if (format.startsWith("=10^")) {
          String transFormat = format.substring("=10^".length());
          numberFormat = NumberFormat.getFormat(transFormat);
          tickLabelFormatType = LOG10INVERSE_FORMAT_TYPE;
        } else if (format.startsWith("=2^")) {
          String transFormat = format.substring("=2^".length());
          numberFormat = NumberFormat.getFormat(transFormat);
          tickLabelFormatType = LOG2INVERSE_FORMAT_TYPE;
        } else {
          numberFormat = NumberFormat.getFormat(format);
          tickLabelFormatType = NUMBER_FORMAT_TYPE;
        }
      }
      // remember original format (for use with the getter)
      tickLabelFormat = format;
    }

    /**
     * Specifies the number of pixels of padding (blank space) between the tick
     * marks and their labels.
     * <p>
     * 
     * With the default of <tt>0</tt>, each tick label is flush against its tick
     * mark.
     * 
     * @param tickLabelPadding
     *          the amount of padding between tick labels and tick marks, in
     *          pixels.
     * 
     * @see #getTickLabelPadding getTickLabelPadding
     * @see #setTickLength setTickLength
     * @see #setTickLocation setTickLocation
     * 
     */
    public void setTickLabelPadding(int tickLabelPadding) {
      chartDecorationsChanged = true;
      // assure that any existing ticks are updated with new padding
      Curve c = getSystemCurve(ticksId);
      int nPoints = c.getNPoints();
      for (int i = 0; i < nPoints; i++) {
         if (isHorizontalAxis)
           c.getPoint(i).setAnnotationYShift(axisPosition * tickLabelPadding);
         else
           c.getPoint(i).setAnnotationXShift(axisPosition * tickLabelPadding);
      }
      this.tickLabelPadding = tickLabelPadding;
    }

    /**
     * Specifies the thickness of the region adjacent to this axis that GChart
     * will reserve for purposes of holding this axis' tick labels.
     * <p>
     * 
     * For vertical axes, this represents the width of the widest tick label,
     * for horizontal axes, this represents the height of highest tick label.
     * <p>
     * 
     * By default, this property has the special "undefined" value
     * <tt>GChart.NAI</tt>. With this value, the companion method
     * <tt>getTickLabelThickness</tt> uses a character-counting
     * heuristic to estimate the thickness.
     * 
     * @see #getTickLabelThickness getTickLabelThickness
     * @see #setTickLabelFontSize setTickLabelFontSize
     * @see #setTickLocation setTickLocation
     * @see #setTickLabelPadding setTickLabelPadding
     * @see #setAxisLabel setAxisLabel
     * @see GChart#NAI NAI
     * 
     */
    public void setTickLabelThickness(int tickLabelThickness) {
      chartDecorationsChanged = true;
      this.tickLabelThickness = tickLabelThickness;
    }

    /**
     * Specifies the ratio of the number of tick marks on the axis, to the
     * number of gridlines on the axis.
     * <p>
     * 
     * For example, with the default value of 1, every tick has an associated
     * gridline, whereas with a <tt>ticksPerGridline</tt> setting of 2, only the
     * first, third, fifth, etc. ticks have gridlines.
     * <p>
     * 
     * This setting only has an impact when the axis' gridlines are turned on,
     * that is, when this axis' <tt>getHasGridlines</tt> method returns true.
     * 
     * @see #setHasGridlines setHasGridlines
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * 
     * @param ticksPerGridline
     *          the number of ticks on this axis per "gridline-extended" tick.
     * 
     */
    public void setTicksPerGridline(int ticksPerGridline) {
      if (ticksPerGridline <= 0)
        throw new IllegalArgumentException("ticksPerGridline="
            + ticksPerGridline + "; ticksPerGridline must be > 0");
      chartDecorationsChanged = true;
      this.ticksPerGridline = ticksPerGridline;
    }

    /**
     * Specifies the ratio of the number of tick marks on the axis, to the
     * number of labeled tick marks on the axis.
     * <p>
     * 
     * For example, with the default value of 1, every tick is labeled, whereas
     * with a <tt>ticksPerLabel</tt> setting of 2, only the first, third, fifth,
     * etc. ticks are labeled.
     * <p>
     * 
     * This setting is only used when tick labels are specified implicitly via
     * <tt>setTickCount</tt>. It is ignored when tick positions and their labels
     * are explicitly specified via <tt>addTick</tt>.
     * 
     * @see #setTickCount setTickCount
     * @see #addTick(double) addTick(double)
     * @see #addTick(double,String) addTick(double,String)
     * @see #addTick(double,String,int,int) addTick(double,String,int,int)
     * @see #addTick(double,Widget) addTick(double,Widget)
     * @see #addTick(double,Widget,int,int) addTick(double,Widget,int,int)
     * 
     * @param ticksPerLabel
     *          the ratio of the number of ticks, to the number of labeled
     *          ticks.
     * 
     */
    public void setTicksPerLabel(int ticksPerLabel) {
      chartDecorationsChanged = true;
      if (ticksPerLabel <= 0)
        throw new IllegalArgumentException("ticksPerLabel="
            + ticksPerLabel + "; ticksPerLabel must be > 0");
      this.ticksPerLabel = ticksPerLabel;
    }

    /**
     * Sets this axis' tick length. Set the tick length to zero to eliminate the
     * tick entirely.
     * <p>
     * 
     * @param tickLength
     *          the length of the tick.
     * 
     * @see #getTickLength getTickLength
     * @see #setTickThickness setTickThickness
     * @see #setTickLabelPadding setTickLabelPadding
     * @see #setTickLocation setTickLocation
     * 
     */
    abstract public void setTickLength(int tickLength);

    /**
     * Specifies the location of the tick marks relative to this axis, namely,
     * if tick marks are outside, inside, or centered on this axis.
     * <p>
     * 
     * @see #getTickLocation getTickLocation
     * @see #setTickThickness setTickThickness
     * @see #setTickLength setTickLength
     * @see #setTickLabelPadding setTickLabelPadding
     * 
     * @param tickLocation
     *          Specify either <tt>TickLocation.INSIDE</tt>,
     *          <tt>TickLocation.OUTSIDE</tt>, or <tt>TickLocation.CENTERED</tt>
     * 
     */
    public void setTickLocation(TickLocation tickLocation) {
      this.tickLocation = tickLocation;
      chartDecorationsChanged = true;
      GChart.Symbol sym = getSystemCurve(ticksId).getSymbol();
      if (isHorizontalAxis) {
        sym.setSymbolType(
           tickLocation.getXAxisSymbolType(axisPosition));
        sym.setHeight(getActualTickLength());
      } else {
        sym.setSymbolType(
           tickLocation.getYAxisSymbolType(axisPosition));
        sym.setWidth(getActualTickLength());
      }
    }

    /**
     * Sets this axis' tick thickness.
     * <p>
     * 
     * @param tickThickness
     *          the thickness of the tick.
     * 
     * @see #getTickThickness getTickThickness
     * @see #setTickLength setTickLength
     * @see #setTickLabelPadding setTickLabelPadding
     * @see #setTickLocation setTickLocation
     * 
     */
    abstract public void setTickThickness(int tickThickness);

    void maybePopulateTicks() {
      if (tickCount != GChart.NAI)
        populateTicks();
    }

    // fills in the tick positions when auto-generated.
    private void populateTicks() {
      getSystemCurve(ticksId).clearPoints();
      /*
       * TODO: It should be possible to control the visibility of each
       * axis, including ticks and tick labels, independent of the
       * specifications of the tick marks on that axis, and independent
       * of if any curves are mapped to that axis or not. A
       * setVisible(Boolean isVisible) as a three-way, with null being
       * the current, dependent, defaults, and TRUE, FALSE explicitly
       * making the entire axis, including tick marks and labels visible
       * or not without having to zero the tick count, add dummy curve
       * to the axis, etc. to control axis visibility is needed.
       * 
       */ 
      if (XTICKS_ID == ticksId || // x, y ticks are drawn even
          YTICKS_ID[0] == ticksId || // if no curves are on these axes
          0 < getNCurvesVisibleOnAxis()) {
        AxisLimits lim = getAxisLimits();
        if (1 == tickCount)
          addTickAsPoint(lim.max, formatAsTickLabel(lim.max),
                         null, GChart.NAI, GChart.NAI);
        else {
          int tcm1 = tickCount - 1;
          for (int i = 0; i < tickCount; i++) {
            // linear interpolation between min and max
            double xOrY = (lim.min * (tcm1 - i) + i * lim.max) / tcm1;
            if (0 == i % ticksPerLabel)
              addTickAsPoint(xOrY, formatAsTickLabel(xOrY),
                             null, GChart.NAI, GChart.NAI);
            else
              addTickAsPoint(xOrY, null,
                             null, GChart.NAI, GChart.NAI);
          }
        }
      }
    }

    // fills in the gridlines; ticks are assumed already populated
    void populateGridlines() {
      Curve cTicks = getSystemCurve(ticksId);
      Curve cGridlines = getSystemCurve(gridlinesId);
      cGridlines.clearPoints();
      int nTicks = cTicks.getNPoints();
      for (int iTick = 0; iTick < nTicks; iTick++) {
        if (hasGridlines && (iTick % ticksPerGridline) == 0) {
          Curve.Point p = cTicks.getPoint(iTick);
          cGridlines.addPoint(p.getX(), p.getY());
        }
      }
    }

    protected void getAxisLimits(AxisLimits result) {
      // so we get 1-unit changes between adjacent ticks
      final int DEFAULT_AXIS_RANGE = DEFAULT_TICK_COUNT - 1;
      double min = getAxisMin();
      double max = getAxisMax();
      /*
       * Adjust min/max so that special cases, like one-point
       * charts, do not have axes that shrink down to a point,
       * which would create numerical and visual difficulties.
       * 
       */ 
      if ((min != min) && (max != max)) { // x!=x is a faster isNaN
        // e.g. no data and no explicitly specified ticks
        min = 0;
        max = min + DEFAULT_AXIS_RANGE;
      } else if ((min != min) && !(max != max)) { 
        // e.g. no data but only max explicitly set
        min = max - DEFAULT_AXIS_RANGE;
      } else if (!(min != min) && (max != max)) { 
        // e.g. no data but only min explicitly set
        max = min + DEFAULT_AXIS_RANGE;
      } else if (min == max) {
        // e.g one data point only, or they set min=max
        max = min + DEFAULT_AXIS_RANGE;
      }
      result.min = min;
      result.max = max;
    }

    AxisLimits getAxisLimits() {
      getAxisLimits(currentLimits);
      return currentLimits;
    }

    void rememberLimits() {
      getAxisLimits(previousLimits);
    }

    boolean limitsChanged() {
      boolean result = !getAxisLimits().equals(previousLimits);
      return result;
    }

    /* similar to getTickText, except for the tick position */
    private double getTickPosition(Curve c, int iTick) {
      double result;
      if (isHorizontalAxis)
        result = c.getPoint(iTick).getX();
      else
        result = c.getPoint(iTick).getY();
      return result;
    }

    // returns the largest, explicitly specified, tick position
    private double getTickMax() {
      double result = -Double.MAX_VALUE;
      Curve c = getSystemCurve(ticksId);
      int nTicks = c.getNPoints();
      for (int i = 0; i < nTicks; i++)
        result = Math.max(result, getTickPosition(c, i));
      return result;
    }

    // returns the smallest, explicitly specified, tick position
    private double getTickMin() {
      double result = Double.MAX_VALUE;
      Curve c = getSystemCurve(ticksId);
      int nTicks = c.getNPoints();
      for (int i = 0; i < nTicks; i++)
        result = Math.min(result, getTickPosition(c, i));
      return result;
    }

    // Same as max, except treats NaN/MAX_VALUE values as "not there"
    protected double maxIgnoreNaNAndMaxValue(double x1, double x2) {
      double result;
      // x!=x is a faster isNaN
      if ((x1 != x1) || Double.MAX_VALUE == x1 || -Double.MAX_VALUE == x1)
        result = x2;
      else if ((x2 != x2) || Double.MAX_VALUE == x2
          || -Double.MAX_VALUE == x2)
        result = x1;
      else
        result = Math.max(x1, x2);
      return result;
    }

    // Same as Math.min, except treats NaN/MAX_VALUE values as "not there"
    protected double minIgnoreNaNAndMaxValue(double x1, double x2) {
      double result;
      // x!=x is a faster isNaN
      if ((x1 != x1) || Double.MAX_VALUE == x1 || -Double.MAX_VALUE == x1)
        result = x2;
      else if ((x2 != x2) || Double.MAX_VALUE == x2
          || -Double.MAX_VALUE == x2)
        result = x1;
      else
        result = Math.min(x1, x2);
      return result;
    }

  } // end of class Axis

  // creates canvas Widgets GChart needs for its canvas-rendering option.
  private static GChartCanvasFactory canvasFactory = null;

  /**
   * 
   * Tells GChart how to create the canvas widgets it needs (specifically,
   * widgets that implement GChart's <tt>GChartCanvasLite</tt> interface) to
   * render your charts using an external vector graphics library.
   * <p>
   * 
   * You must define a class that implements <tt>GChartCanvasFactory</tt> and
   * then pass an instance of that class to this method, if you want to have the
   * fast, crisply drawn connecting lines, polygonal areas, and 2-D pie slices
   * that only a vector graphics library can provide.
   * <p>
   * 
   * <small> <i>Note:</i> If all of your charts only have rectangular elements
   * (e.g. bar charts) GChart will continue to render those charts using HTML
   * elements, even if a canvas factory is provided. Thus, there is no point to
   * defining a canvas factory if all you use GChart for is bar charts, scatter
   * plots without connecting lines between each point, banded-fill pie slices,
   * and so on.
   * <p>
   * 
   * On the other hand, if you need continously connected lines, solid-fill pie
   * slices, or solid-fill area charts (all of which will also need a
   * <tt>setFillSpacing(0)</tt> to specify continuous filling) you will gain
   * substantial performance/quality improvements if you add a canvas factory.
   * <p>
   * </small>
   * 
   * In detail, to exploit browser-based vector graphics (Mozilla's canvas, IE's
   * VML, etc.) rendering in your charts, you must:
   * <p>
   * 
   * <ol>
   * 
   * <li>Import an external GWT canvas library, such as provided by the <a
   * href="http://code.google.com/p/google-web-toolkit-incubator/"> GWT
   * incubator project's</a> <tt>GWTCanvas</tt> class, into your project. For
   * example, our GChart test application uses <tt>GWTCanvas</tt> and imports it
   * by adding this line to its <tt>.gwt.xml</tt> file:
   * <p>
   * 
   * <pre>
   *   &lt;inherits name='com.google.gwt.widgetideas.GWTCanvas' /&gt;
   * </pre>
   * 
   * To make this work, we also had to add the <a
   * href="http://code.google.com/p/google-web-toolkit-incubator/">
   * gwt-incubator.jar</a> file (which contains <tt>GWTCanvas</tt>) to our build
   * path via Eclipse's "Configure Build Path..." command and to the libraries
   * listed in the <tt>classpath=...</tt> line of our ant build script's
   * <tt>java</tt> task.
   * <p>
   * 
   * <small>For more on the <tt>GWTCanvas</tt> widget, see the <a href=
   * "http://code.google.com/p/google-web-toolkit-incubator/wiki/GWTCanvas">
   * GWTCanvas Wiki page</a> within the GWT incubator site.</small>
   * <p>
   * 
   * <li>Implement a class that extends <tt>Widget</tt> and implements the
   * <tt>GChartCanvasLite</tt> interface that GChart expects. Again, in GChart's
   * <tt>GWTCanvas</tt>-based test application we use:
   * <p>
   * 
   * {@code.sample ../../../../../../gcharttestapp/src/com/googlecode/gchart/gcharttestapp/client/GWTCanvasBasedCanvasLite.java}
   * <p>
   * 
   * <li>Create a <tt>GChartCanvasFactory</tt> class that has a single
   * <tt>create</tt> method that returns new instances of your
   * <tt>GChartCanvasLite</tt> Widget. GChart's test application uses:
   * <p>
   * 
   * {@code.sample
   * ../../../../../../gcharttestapp/src/com/googlecode/gchart/gcharttestapp/client/GWTCanvasBasedCanvasFactory.java}
   * <p>
   * 
   * <li>Finally pass an instance of that factory to GChart via a single
   * invocation of <tt>setCanvasFactory</tt>:
   * 
   * <pre>
   * static {
   *   GChart.setCanvasFactory(new GWTCanvasBasedCanvasFactory());
   * }
   * </pre>
   * <p>
   * 
   * <small>Because the above line essentially completes the definition of the
   * GChart class (and thus should only be executed once per application) a good
   * home for it is in a static initializer, as shown above. I recommend placing
   * that initializer within your application's <tt>EntryPoint</tt>
   * class.</small>
   * </ol>
   * <p>
   * 
   * <i>Note:</i> To make things a bit simpler for you, the following code
   * combines steps 2, 3, and 4 into a single chunk of easily pasted
   * boilerplate:
   * <p>
   * 
   * <small><small> {@code.sample
   * ../../../../../../gchartdemoapp/src/com/googlecode/gchart/gchartdemoapp/client/GChartDemoApp.java#1} {@code.sample
   * ../../../../../../gchartdemoapp/src/com/googlecode/gchart/gchartdemoapp/client/GChartDemoApp.java#2} </small></small>
   * <p>
   * 
   * To see the above boilerplate within a working example application, follow
   * the link at the bottom of GChart's <a
   * href="http://clientsidegchart.googlecode.com/svn/trunk/live-demo/v2_7/com.googlecode.gchart.gchartdemoapp.GChartDemoApp/GChartDemoApp.html">
   * live demo page</a> to examine its <tt>EntryPoint</tt> class' code.
   * <p>
   * 
   * <i>GChart's mixed canvas and HTML rendering</i>
   * <p>
   * 
   * <small><blockquote>
   * Even with an external canvas factory enabled, many aspects of your chart
   * will remain HTML-rendered. In general, GChart only renders non-rectangular,
   * "continuously filled" aspects of your chart using canvas. Other aspects,
   * such as the bars on a bar chart, or the individual rectangular point
   * markers on a line chart, as well as all chart text, will remain HTML
   * rendered.
   * <p>
   * 
   * The rendering mode (HTML-only or HTML+canvas) for each curve is defined by
   * if a canvas factory is available and if that curve uses the special
   * <tt>setFillSpacing(0)</tt> setting (meaning: "continuously filled"). So, to
   * force any curve to be only HTML rendered, simply set this fill spacing to a
   * value greater than <tt>0</tt>.
   * <p>
   * 
   * HTML rendering offers some features not available with canvas rendering.
   * For example, only HTML-rendered curves can overwrite the enclosing page
   * without monopolizing mouse events within that curve's bounding rectangle,
   * and only HTML-rendered curves can define their "fill" via an image. HTML
   * rendered curves can sometimes even be more memory-efficent, provided the
   * number of pixels greatly exceeds the number of HTML elements. And the HTML
   * only rendering option provides a useful least common denominator/fall-back,
   * supported by even the most obscure browsers.
   * <p>
   * 
   * Note that the internal curves that GChart uses to render tick marks,
   * gridlines, etc. are never canvas rendered, because they only involve
   * vertical or horizontal rectangles, which GChart always renders with HTML.
   * </blockquote></small>
   * <p>
   * 
   * Finally, if you are content with GChart's built-in HTML-based rendering, or
   * if your charts only use rectangular elements (e.g. bar charts) you don't
   * need to bother with any of the steps listed above. Your charts will then
   * only depend on the standard GWT distribution and the 3,000 or so lines of
   * pure GWT Java that implement GChart.
   * <p>
   * 
   * <small> <i>Important</i>: GChart only uses your external canvas facility to
   * draw a chart's non-rectangular aspects. Given how GChart works, a curve can
   * only have non-rectangular aspects if <tt>setFillSpacing</tt> has been set
   * to <tt>0</tt> (which implies "continuous filling"), and
   * <tt>setFillThickness</tt> has been set to a value <tt>&gt;
   * 0</tt>. If you are not seeing crisp, canvas-rendered area, line, or pie
   * charts, be sure to check these two settings on the curves in question.
   * </small>
   * 
   * @see GChartCanvasFactory GChartCanvasFactory
   * @see GChartCanvasLite GChartCanvasLite
   * @see #getCanvasFactory getCanvasFactory
   * @see GChart.Symbol#setFillSpacing setFillSpacing
   * @see GChart.Symbol#setFillThickness setFillThickness
   * 
   */
  public static void setCanvasFactory(GChartCanvasFactory factory) {
    canvasFactory = factory;
  }

  /**
   * Returns the GChart class' canvas factory, or <tt>null</tt> if no canvas
   * factory has been specified.
   * 
   * @return the previously specified canvas factory
   * 
   * @see #setCanvasFactory setCanvasFactory
   * 
   */
  public static GChartCanvasFactory getCanvasFactory() {
    return canvasFactory;
  }

  /**
   * Represents a curve on a chart, which includes information such as the x,y
   * coordinates of each point, the symbol used to represent points on the
   * curve, etc.
   * <p>
   * To create a new curve, use the <tt>GChart.addCurve</tt> method.
   * 
   * @see GChart#addCurve() addCurve()
   * 
   */
  public class Curve {
    private boolean isVisible = true;
    // a generic, user-defined, auxiliary curve-related object
    private Object curveData = null;    
    private Boolean clipToPlotArea = null;
    private String legendHTML = null;
    private ArrayList<Point> points = new ArrayList<Point>();
    // symbol defines how every point on this curve is rendered
    private Symbol symbol = new Symbol(this);
    /*
     * Pixel amounts the panels holding this curve's rendering are
     * shifted (to shift rendering panel position without re-rendering).
     *
     */ 
    private int xShift = 0;
    private int yShift = 0;
    boolean xOrYShiftChanged = false;

    private YAxisId yAxisId = Y_AXIS[0];
    private boolean isValidated = false;

    boolean isValidated() {
      return isValidated;
    }

    /*
     * TestGChart14d revealed that curves.indexOf(curve) could, due to its
     * sequential search, create a performance bug if the chart had over 100
     * curves (e.g. the 160 pie chart slices/curves of TestGChart14d) <p>
     * 
     * With a little extra bookkeeping during add/remove curve to call these
     * methods (and the extra int) this problem was corrected.
     */
    private int indexOf = GChart.NAI;

    void incrementIndex() {
      indexOf++;
    }

    void decrementIndex() {
      indexOf--;
    }

    void clearIndex() {
      indexOf = GChart.NAI;
    }

    int getIndexOf() {
      return indexOf;
    }

    /*
     * No public constructor because curves are always contained within, and
     * managed by, their containing GChart via its addCurve, removeCurve, and
     * related methods.
     * 
     */
    Curve(int indexOf) {
      super();
      this.indexOf = indexOf;
    }

    /**
     * Adds a new point to the curve, at the end of the current list of points,
     * with the specified coordinates in model-units (arbitrary,
     * application-specific, units).
     * <p>
     * 
     * GChart gives a special interpretation to the following values:
     * <p>
     * 
     * <ol>
     * 
     * <li>If <tt>-Double.MAX_VALUE</tt> is specified for either x or y, the
     * point acts as if it were placed at the leftmost or bottommost x or y
     * position within the plot area.
     * <p>
     * 
     * <li>Similarly, if <tt>Double.MAX_VALUE</tt> is specified for either x or
     * y, the point acts as if it were placed at the rightmost x or topmost y
     * position within the plot area.
     * <p>
     * 
     * <li>If <tt>Double.NaN</tt> is specified for either x or y, the point is
     * created, but it will not be visible in the charting region.
     * <p>
     * 
     * <i>Tip:</i> Connecting lines to/from such <tt>Double.NaN</tt> points are
     * elided, so you can use such a point to create a break in an otherwise
     * connected curve.
     * </ol>
     * <p>
     * 
     * @param x
     *          the x-coordinate of the new point
     * @param y
     *          the y-coordinate of the new point
     * 
     * @see #getPoint getPoint
     * @see #addPoint(int,double,double) addPoint(int,double,double)
     * @see #removePoint removePoint
     * @see #clearPoints clearPoints
     * @see #getNPoints getNPoints
     */
    public void addPoint(double x, double y) {
      invalidate();
      points.add(new Point(x, y));
    }

    /**
     * Adds a new point at the specified position in the point sequence,
     * increasing the indexes of existing points at or after the specified
     * position by 1.
     * 
     * @param iPoint
     *          the position that the new point will occupy
     * @param x
     *          the x-coordinate of the new point (model units)
     * @param y
     *          the y-coordinate of the new point (model units)
     * 
     * @see #getPoint getPoint
     * @see #addPoint(double, double) addPoint(double,double)
     * @see #removePoint removePoint
     * @see #clearPoints clearPoints
     * @see #getNPoints getNPoints
     */
    public void addPoint(int iPoint, double x, double y) {
      invalidate();
      points.add(iPoint, new Point(x, y));
    }

    /**
     * Removes every point this curve contains.
     * 
     * @see Point Point
     * @see #getPoint getPoint
     * @see #addPoint(double, double) addPoint(double,double)
     * @see #addPoint(int,double,double) addPoint(int,double,double)
     * @see #removePoint removePoint
     * @see #getNPoints getNPoints
     */
    public void clearPoints() {
      if (this == getTouchedCurve())
        plotPanel.touch(null);
      invalidate();
      points.clear();
    }

    /*
     * Locates index of vertical or horizontal hit-testing band that the given
     * point appears in. The first and last "pseudo-bands" are devoted to
     * holding all points that fall either to the left of or to the right of (or
     * above or below for horizontal banding) the first or last "normal" band
     * covering the plot area's containing "box". <p>
     * 
     * Note that some points just slightly off the right or bottom edge may not
     * end up in a pseudo-band, due to the fact that the chart width (or height)
     * need not be an even multiple of the (fixed) band thickness (the
     * "last band sticking out a bit" effect).
     */
    private int getBand(int iPoint, double bandThickness) {
      int result = GChart.NAI;
      SymbolType symType = getSymbol().getSymbolType();
      double xPx = symType.getCenterX(plotPanel, getSymbol(), iPoint);
      if (xPx != xPx)
        return result; // NaN points not in any band
      double yPx = symType.getCenterY(plotPanel, getSymbol(), iPoint,getIndexForAxisID(getYAxis()));
      if (yPx != yPx)
        return result; // NaN points not in any band

      // now, we've got a point with x,y values in some sort of band

      if (getSymbol().isHorizontallyBanded()) {
        if (yPx < 0)
          result = 0; // point above plot-area
        else if (yPx >= (bandList.length - EXTRA_BANDS) * bandThickness)
          result = bandList.length - 1; // point below plot-area
        else
          // inside a normal, chart-covering, band
          result = 1 + (int) Math.floor(yPx / bandThickness);
      } else { // vertically banded
        if (xPx < 0)
          result = 0; // to the left of plot-area
        else if (xPx >= (bandList.length - EXTRA_BANDS) * bandThickness)
          result = bandList.length - 1; // to the right
        else
          // within one of the real bands covering the plot-area
          result = 1 + (int) Math.floor(xPx / bandThickness);
      }
      return result;
    }

    // Off-the-plot-area points in either 'too small' or 'too large' bands
    private final int EXTRA_BANDS = 2;    
    /*
     * Number of hit-test bands for this curve, for a given band thickness.
     * 
     */
    private int getNBands(double bandThickness) {
      int result = EXTRA_BANDS;
      if (getSymbol().isHorizontallyBanded())
        result += (int) Math.ceil(getYChartSize() / bandThickness);
      else
        result += (int) Math.ceil(getXChartSize() / bandThickness);
      return result;
    }

    // index of first point in each band
    private int[] bandList = null; 
    private double bandThickness = Double.NaN;
    void clearBandList() {
      bandList = null;
    }

    /*
     * Separates points on this curve into bins associated with successive
     * vertical (or horizontal) bands across the plot area. <p>
     * 
     * Because busy charts typically distribute points evenly across the
     * plot area, by jumping to the appropriate band's list, we can
     * (usually) greatly accelerate worst case mouse hit testing. And
     * because the bin organizing step only requires a single pass over
     * all the points (and less than a two int memory overhead per
     * point) it should almost always be a "good deal" performance-wise
     * (compared to a simple full point-list scan with every hit
     * test).<p>
     * 
     * Points are placed into bins based on the (pixel) position of the
     * x (or, with horizontal bands, y) at the center of the rendered
     * symbol. We choose bin size to guarantee that bins are at least as
     * wide (or high, for horizontally banded hit testing) as the
     * rendered symbols on this curve.  This simplifies hit testing,
     * since bins are big enough to assure that a single symbol
     * straddles at most two adjacent bands. Exploits fact that all
     * symbols on the same curve have the same size (in the
     * banding-relevant dimension), and that curves with many points on
     * them tend to have smaller sized symbols.  <p>
     * 
     * Note that, for bin placement purposes, pie slices are considered
     * to have a "center" equal to the center of the pie that contains
     * them, and to have a width and height equal to the diameter of the
     * pie containing the slice (the "worst-case slice").  <p>
     * 
     * Also note that a symbol whose center is in the right side of a vertical
     * band may overlap into the following band, and one in the left side may
     * overlap the preceding band. Thus during hit testing, we must check not
     * only the lists of points in the bands "touched" by the
     * mouse-cursor-centered brush, but also 1) the band to the immediate left
     * of the leftmost touched band, whenever a left-side sub-band of that band
     * is touched by the brush and 2) the band to the immediate right of the
     * rightmost touched band, whenever a right-side sub-band of that band is
     * touched. The thickness of these left and right side sub-bands equals half
     * the symbol width. Expanding the brush a half-symbol width on either edge
     * is the easiest way to apply these rules. Exactly analogous statements
     * apply to horizontal bands. <p>
     * 
     * A minimum band size is enforced to prevent the number of bands from
     * growing too large with small symbols. Each symbol type defines if
     * vertical or horizontal banding is more appropriate, or if brush shape
     * should determine banding strategy (as of this writing, only horizontal
     * bar symbol types require horizontal hit-test bands). Exploits the fact
     * that all symbols have a fixed size for at least one of their dimensions
     * (for example, vertical bars have variable height but fixed width).<p>
     * 
     * Note: this method must be called after the curve is rendered during an
     * update(), to assure that hit-test-bins are consistent with rendered
     * curves, and ready for use before the first mouse hit testing is done. <p>
     * 
     * After running this method, points on this curve within a given band can
     * be enumerated as in the following code: <p>
     * 
     * <pre>
     * Point p = null;
     * for (int iPoint = bandList[iBand];
     *      iPoint != GChart.NAI; iPoint = p.getINextInBand()) {
     *   p = getPoint(iPoint);
     *   // do something requiring points in a given band...
     * }
     * </pre>
     * 
     */
    void bandSeparatePoints() {
      bandThickness = getSymbol().getSymbolType().getBandThickness(
          plotPanel, getSymbol(), getIndexForAxisID(getYAxis()));
      int nBands = getNBands(bandThickness);

      if (bandList == null || bandList.length != nBands)
        bandList = new int[nBands];
      // else bandList already has required length, reuse it.

      // all bands contain NAI terminated, empty lists to start with
      for (int i = 0; i < bandList.length; i++)
        bandList[i] = GChart.NAI;

      for (int iPoint = 0; iPoint < getNPoints(); iPoint++) {
        int iBand = getBand(iPoint, bandThickness);
        Point p = getPoint(iPoint);
        if (GChart.NAI == iBand) {
          /*
           * Point isn't rendered at all, so isn't in a band (a next
           * link pointing to self means "I'm not in any band"). To let
           * us skip over these points quickly during rendering.
           *
           */ 
          p.setINextInBand(iPoint);
        } else {
          // Add point to front of list for whatever band it's in
          // (note that point order therefore gets reversed).
          p.setINextInBand(bandList[iBand]);
          bandList[iBand] = iPoint;
        }
      }
    }

    /*
     * Returns the index of the point on this curve whose rendered symbol
     * intersects a rectangle with the specified width and height centered on
     * the specified point (this rectangle is typically a point selection
     * "brush", centered on the mouse cursor). <p>
     * 
     * In the event that more than one point's rendered symbol intersects with
     * the specified rectangle, the point whose center is closest to the
     * specified rectangle's center is returned. In the event of a tie, the
     * point with the largest point index is returned. If no point "touches" the
     * rectangle, <tt>GChart.NAI</tt> is returned. <p>
     * 
     * Assumes/requires up-to-date <tt>bandList</tt> array and related
     * <tt>iNextInBand</tt> indexes (these get defined within the
     * <tt>bandSeparatePoints</tt> method).
     */

    int getClosestTouchingPoint(int xBrushIn, int yBrushIn) {
      /*
       * Reverse-shifting brush is an easier way to incorporate
       * hit-testing impact of xShift, yShift than actually shifting
       * object positions.
       * 
       */
      int xBrush = xBrushIn - xShift;
      int yBrush = yBrushIn + yShift; // '+': cartesion-to-pixel transform
      int result = GChart.NAI;
      // ANCHOR_MOUSE symbol type curves not band separated/hit tested
      if (null == bandList)
        return result;
      SymbolType symType = getSymbol().getSymbolType();
      double dBest = Double.MAX_VALUE; // closest touching pt's distance^2

      int iBandFirst;
      int iBandLast;

      int brushWidth = symType.getBrushWidth(getSymbol());
      /*
       * In every tested browser EXCEPT FF3, we don't need the +1 below
       * to select a 1px tall, off-chart, symbol with a 1x1 px brush
       * (specifically, to select the leftmost vertical bar on
       * TestGChart28). The +1 below in effect adds 1 px to the height
       * of the brush to workaround this problem.
       */
      int brushHeight = symType.getBrushHeight(getSymbol()) + 1;
      AnnotationLocation brushLocation =
          symType.getBrushLocation(getSymbol());
      int nBands = bandList.length;

      /*
       * Determine range of bands touched by brush, taking into account
       * potential for symbols whose centers are in one band to "stick
       * out" into an adjacent band by half-band thickening of either
       * end of the brush.
       *
       * Note that the 0th and (nBand-1)th bands represent
       * "pseudo-bands" that hold all points that fall to the left or
       * right (or above or below if horizontally banded) the
       * rectangular plot area. The tacit assumption is that such
       * completely off-the-plot-area points are rare, so it's OK to
       * bunch them up into just 2 bands.
       *
       */ 
      if (getSymbol().isHorizontallyBanded()) {
        // horizontal bars and curves with "wider than high" brushes
        double top =
          brushLocation.getUpperLeftY(yBrush, brushHeight, 0);
        double bottom = top + brushHeight;
        top -= bandThickness / 2.;
        bottom += bandThickness / 2.;
        iBandFirst = (int) Math.max(0, Math.min(nBands - 1,
          1 + Math.floor(top / bandThickness)));
        iBandLast = (int) Math.max(0, Math.min(nBands - 1,
          1 + Math.floor(bottom / bandThickness)));
      } else { // vertical bars, some curves with "tall or square" brushes
        double left =
          brushLocation.getUpperLeftX(xBrush, brushWidth, 0);
        double right = left + brushWidth;
        left -= bandThickness / 2.0;
        right += bandThickness / 2.0;
        iBandFirst = (int) Math.max(0, Math.min(nBands - 1,
          1 + Math.floor(left / bandThickness)));
        iBandLast = (int) Math.max(0, Math.min(nBands - 1,
          1 + Math.floor(right / bandThickness)));
      }
      /*
       * Every point whose symbol touches the brush must be in one
       * of these bands. Search them to find closest touching point.
       * 
       */ 
      for (int iBand = iBandFirst; iBand <= iBandLast; iBand++) {
        Point p = null;
        for (int iPoint = bandList[iBand];
             iPoint != GChart.NAI;
             iPoint = p.getINextInBand()) {
          if (iPoint < 0 || iPoint >= getNPoints())
            throw new IllegalStateException(
                "Inappropriately terminated band-point-list, GChart bug likely. "
                    + "iPoint=" + iPoint + " nPoints="
                    + getNPoints() + " iBand=" + iBand
                    + " iBandFirst=" + iBandFirst
                    + " iBandLast=" + iBandLast
                    + " xBrush=" + xBrush + " yBrush="
                    + yBrush + " brushWidth=" + brushWidth
                    + " brushHeight=" + brushHeight
                    + " bandThickness=" + bandThickness);
          p = getPoint(iPoint);
          if (symType.isIntersecting(plotPanel, getSymbol(), iPoint,
              getIndexForAxisID(getYAxis()), xBrush, yBrush, brushWidth, brushHeight)) {
            // this point touches the brush (keep it if closest)
            double xPoint = symType.getCenterX(plotPanel,
                getSymbol(), iPoint);
            double yPoint = symType.getCenterY(plotPanel,
                getSymbol(), iPoint, getIndexForAxisID(getYAxis()));
            double dx = getSymbol().xScaleFactor
                * (xPoint - xBrush);
            double dy = getSymbol().yScaleFactor
                * (yPoint - yBrush);
            double d = dx * dx + dy * dy;
            if (d < dBest) {
              result = iPoint;
              dBest = d;
            } else if (d == dBest && iPoint > result) {
              /*
               * In the case of ties, choose largest point index
               * (highest "z-order" -- the one "on top")
               */ 
              result = iPoint;
            }
          }
        }
      }

      return result;

    }

    /**
     * Will the part of this curve's rendering that falls outside of the plot
     * area be hidden?
     * <p>
     * 
     * @return <tt>true</tt> if this curve's rendering is clipped to the <plot
     *         area tt>false</tt> otherwise.
     * 
     * @see Curve#setClipToPlotArea Curve.setClipToPlotArea
     * @see GChart#setClipToPlotArea GChart.setClipToPlotArea
     * 
     */
    public boolean getClippedToPlotArea() {
      boolean result = (null == clipToPlotArea) ?
                       GChart.this.getClipToPlotArea() :
                       clipToPlotArea.booleanValue();
      return result;
    }

    /**
     * Returns value previously set via <tt>Curve.setClipToPlotArea</tt>.
     * <p>
     * 
     * Note: Use <tt>getClippedToPlotArea</tt> to determine if this curve will
     * actually be clipped to the plot area.
     * 
     * @return value previously set with setClipToPlotArea.
     * 
     * @see Curve#setClipToPlotArea setClipToPlotArea
     * @see Curve#getClippedToPlotArea getClippedToPlotArea
     * 
     */
    public Boolean getClipToPlotArea() {
      return clipToPlotArea;
    }

    /**
     * Returns any developer-defined data associated with this curve.
     * 
     * @return the developer-defined curve data, or <tt>null</tt> if none.
     * 
     * @see #setCurveData setCurveData
     * 
     */
    public Object getCurveData() {
      return curveData;
    }

    /**
     * @deprecated
     * 
     *             This method is equivalent to:
     *             <p>
     *             <tt>getSymbol().getHovertextTemplate()</tt>
     *             <p>
     * 
     *             It is retained only for GChart 1.1 compatibility purposes.
     * 
     * @see Symbol#getHovertextTemplate() Symbol.getHovertextTemplate
     * 
     */
    public String getHovertextTemplate() {
      return symbol.getHovertextTemplate();
    }

    /**
     * Returns the HTML defining this curve's legend label.
     * 
     * @return the legend label HTML for this curve
     * 
     * @see #setLegendLabel setLegendLabel
     * 
     */
    public String getLegendLabel() {
      return legendHTML;
    }

    /**
     * Returns the number of points this curve contains.
     * 
     * @return the number of points this curve contains.
     * 
     * @see #getPoint getPoint
     * @see #addPoint(double, double) addPoint(double,double)
     * @see #addPoint(int,double,double) addPoint(int,double,double)
     * @see #removePoint removePoint
     * @see #clearPoints clearPoints
     */
    public int getNPoints() {
      return points.size();
    }

    /**
     * Returns a reference to the GChart that contains this curve.
     * 
     * @return GChart that contains this curve--its "parent".
     * 
     */
    public GChart getParent() {
      return GChart.this;
    }

    /**
     * Convenience method equivalent to <tt>getPoint(getNPoints()-1)</tt>.
     * <p>
     * 
     * This method makes code more readable for the common case when you first
     * add a point to the end of a curve, and then modify that point's
     * attributes, as illustrated below:
     * <p>
     * 
     * <pre>
     * class MyChart extends GChart {
     *   public MyChart() {
     *     addCurve();
     *     for (int i = 0; i &lt; 10; i++) {
     *       getCurve().addPoint(i, i);
     *       getCurve().getPoint().setAnnotationText(&quot;Point &quot; + i);
     *     }
     *     update();
     *   }
     * }
     * </pre>
     * 
     * @return the point on the curve with the highest integer index
     * 
     * @see #getPoint(int) getPoint(int)
     * @see #getNPoints() getNPoints()
     * 
     */
    public Point getPoint() {
      Point result = getPoint(getNPoints() - 1);
      return result;
    }

    /**
     * Returns a reference to the point at the specified index. The returned
     * reference can be used to modify various properties of the point, such as
     * its optional annotation (text label).
     * 
     * <p>
     * 
     * @param iPoint
     *          the index of the point to be returned.
     * @return a reference to the Point at the specified index.
     * 
     * @see #addPoint(double, double) addPoint(double,double)
     * @see #addPoint(int,double,double) addPoint(int,double,double)
     * @see #removePoint removePoint
     * @see #clearPoints clearPoints
     * @see #getNPoints getNPoints
     */
    public Point getPoint(int iPoint) {
      if (iPoint < 0 || iPoint >= points.size())
        throw new IllegalArgumentException(
            "Point index iPoint=" + iPoint
            + " is either < 0 or >= the number of points on the curve.");
      Point result = points.get(iPoint);
      return result;
    }

    /**
     * Returns the positional index (within this curve's list of points) of the
     * specified point.
     * <p>
     * 
     * Returns <tt>GChart.NAI</tt> if the specified point is not found on this
     * curve's point list.
     * 
     * <p>
     * 
     * @param point
     *          point whose list position is to be retrieved
     * @return position of point on this curve's point list, or
     *         <tt>GChart.NAI</tt> if not on the list.
     * 
     * @see #getPoint() getPoint()
     * @see #getPoint(int) getPoint(int)
     * @see #addPoint addPoint
     * @see #removePoint removePoint
     * @see #clearPoints clearPoints
     * @see #getNPoints getNPoints
     */
    public int getPointIndex(Point point) {
      int result = points.indexOf(point);
      if (-1 == result)
        result = GChart.NAI;
      return result;
    }

    /**
     * Returns the symbol associated with this curve.
     * <p>
     * 
     * Though you cannot set the symbol itself (there is no <tt>setSymbol</tt>
     * method) you can have essentially the same effect by setting the
     * <tt>SymbolType</tt> (to get qualitatively different kinds of symbols,
     * e.g. bar-chart bars vs. boxes) and by changing symbol attributes such as
     * background color, height, and width.
     * 
     * @return the symbol used to represent points on this curve
     * 
     * @see Symbol#setSymbolType Symbol.setSymbolType
     * @see Symbol#setBackgroundColor Symbol.setBackgroundColor
     * @see Symbol#setBorderWidth Symbol.setBorderWidth
     * @see Symbol#setBorderStyle Symbol.setBorderStyle
     * @see Symbol#setWidth Symbol.setWidth
     * @see Symbol#setHeight Symbol.setHeight
     * @see Symbol#setModelWidth Symbol.setModelWidth
     * @see Symbol#setModelHeight Symbol.setModelHeight
     * 
     */
    public Symbol getSymbol() {
      return symbol;
    }

    /**
     * The number of pixels the entire rendered curve will be shifted along the
     * x-axis from it's default position.
     * 
     * @return pixel shift along x axis
     * 
     * @see #setXShift setXShift
     */
    public int getXShift() {
      return xShift;
    }

    /**
     * The number of pixels the entire rendered curve will be shifted along the
     * y-axis from it's default position.
     * 
     * @return pixel shift along y axis
     * 
     * @see #setYShift setYShift
     */
    public int getYShift() {
      return yShift;
    }

    /**
     * Returns the y-axis (Y_AXIS or Y2_AXIS) this curve is plotted on.
     * 
     * @return an identifier, either Y_AXIS, or Y2_AXIS, indicating if this
     *         curve is plotted on the left (y) or right (y2) y-axis
     * 
     * @see #setYAxis setYAxis
     * @see GChart#Y_AXIS Y_AXIS
     * @see GChart#Y2_AXIS Y2_AXIS
     * 
     */
    public YAxisId getYAxis() {
      return yAxisId;
    }

    /**
     * Explicitly tells GChart to re-render this curve at the next
     * <tt>update</tt> invocation.
     * <p>
     *
     * <small> <i>Note:</i> GChart will usually automatically invalidate
     * a curve's rendering when you modify its properties. So you do not
     * ordinarily need to explicitly force a re-rendering via this
     * method. But, you might need to do this, for example, in a specialized
     * application that used an external vector graphics library in a
     * manner such that the rendering depended on properties not
     * directly managed by the GChart.  </small>
     * 
     * @see #update update
     * 
     */
    public void invalidateRendering() {
      invalidate();
    }

    /**
     * Is this curve visible on the chart and legend key, or is it hidden from
     * view.
     * 
     * @return true if the curve is visible, false otherwise.
     * 
     * @see #setVisible setVisible
     */
    public boolean isVisible() {
      return isVisible;
    }
    

    /**
     * Removes the point at the specified index.
     * 
     * @param iPoint
     *          index of point to be removed.
     * 
     * @see #getPoint getPoint
     * @see #addPoint(double, double) addPoint(double,double)
     * @see #addPoint(int,double,double) addPoint(int,double,double)
     * @see #clearPoints clearPoints
     * @see #getNPoints getNPoints
     */
    public void removePoint(int iPoint) {
      if (iPoint < 0 || iPoint >= getNPoints())
        throw new IllegalArgumentException("iPoint=" + iPoint
            + " iPoint arg must be >= 0 and < " + getNPoints()
            + ", the number of points on the curve.");
      invalidate();

      /*
       * Simulate user moving away from point before it is deleted (this
       * assures that any required hoverCleanup gets called, and clears
       * the otherwise dangling reference to the point)
       *
       */ 
      if (plotPanel.touchedPoint == getPoint(iPoint))
        plotPanel.touch(null);

      points.remove(iPoint);
    }

    /**
     * Removes the given point from this curve.
     * <p>
     * 
     * If the given point is not on this curve, or is <tt>null</tt>, an
     * exception is thrown.
     * 
     * @param p
     *          the point to be removed.
     * 
     * 
     */
    public void removePoint(Point p) {
      if (null == p)
        throw new IllegalArgumentException("p cannot be null.");
      int index = getPointIndex(p);
      if (GChart.NAI == index)
        throw new IllegalArgumentException(
            "p must be a point on this curve "
            + "(whose curveIndex is "
            + getParent().getCurveIndex(this) + ")");
      removePoint(index);
    }

    /**
     * Specifies if the part of this curve's rendering that extends outside of
     * the plot area is hidden (clipped off).
     * <p>
     * 
     * If <tt>null</tt> (the default), the value of the chart's overall
     * <tt>clipToPlotArea</tt> property (as defined by
     * <tt>GChart.setClipToPlotArea</tt>) determines if the curve is clipped.
     * 
     * @param clipToPlotArea
     *          <tt>Boolean.TRUE</tt> to clip, <tt>Boolean.FALSE</tt> to not
     *          clip, or <tt>null</tt> to clip based on the parent GChart's
     *          <tt>clipToPlotArea</tt> setting.
     * 
     * @see GChart#setClipToPlotArea GChart.setClipToPlotArea
     */
    public void setClipToPlotArea(Boolean clipToPlotArea) {
      invalidate();
      this.clipToPlotArea = clipToPlotArea;
    }

    /**
     * Provides a convenient way to associate developer-defined data with a
     * GChart curve.
     * <p>
     * 
     * If you use an external vector graphics library to render your charts (see
     * <tt>setCanvasFactory</tt> for details) all of your vector graphics
     * methods (<tt>lineTo</tt>, <tt>stroke</tt> and so forth) can access this
     * data while the curve is being rendered via the
     * <tt>getCurrentCurveData</tt> method.
     * <p>
     * 
     * For example, the oil price simulation chart of the
     * <a
     * href="http://clientsidegchart.googlecode.com/svn/trunk/live-demo/v2_7/com.googlecode.gchart.gchartdemoapp.GChartDemoApp/GChartDemoApp.html"> GChart live demo</a>
     * uses this method to hold the Catmull-Rom tension
     * parameters it uses to control the "% curvyness" of its lines.
     * <p>
     * 
     * <i>Tip</i>: To associated an application specific list of property value
     * pairs with your curve, either create an appropriate class with the named
     * properties you need, or simply use a <tt>HashMap</tt> as your data
     * object.
     * 
     * @param curveData
     *          a reference to a developer-defined object used to store
     *          auxiliary, application-specific data for this curve.
     * 
     * @see Curve#getCurveData getCurveData
     * @see GChart#getCurrentCurveData getCurrentCurveData
     * @see #setCanvasFactory setCanvasFactory
     * 
     */

    public void setCurveData(Object curveData) {
      invalidate();
      this.curveData = curveData;
    }

    /**
     * @deprecated
     * 
     *             This method is equivalent to:
     *             <p>
     *             <tt>getSymbol().setHovertextTemplate(hovertextTemplate)</tt>
     *             <p>
     *             It is retained only for GChart 1.1 compatibility purposes.
     * 
     * @see Symbol#setHovertextTemplate Symbol.setHovertextTemplate
     */
    public void setHovertextTemplate(String hovertextTemplate) {
      symbol.setHovertextTemplate(hovertextTemplate);
    }

    /**
     * Sets the HTML that defines the label shown to the right of the icon
     * representing the curve's symbol in the chart's legend.
     * <p>
     * 
     * Setting the legend label to <tt>null</tt> removes the entire row (the
     * label and the icon) associated with this curve from the chart key.
     * <p>
     * 
     * Note that, since <tt>null</tt> is the default, unless you set at least
     * one legend label, no chart key will appear at all.
     * 
     * @param legendHTML
     *          the HTML defining this curve's legend label, or <tt>null</tt> to
     *          remove the curve from the legend entirely.
     * 
     * @see #getLegendLabel getLegendLabel
     * @see GChart#setLegendThickness setLegendThickness
     * @see GChart#setLegendLocation setLegendLocation
     * 
     */
    public void setLegendLabel(String legendHTML) {
      chartDecorationsChanged = true;
      this.legendHTML = legendHTML;
    }

    /**
     * Defines if this curve is visible both in the plotting region and on the
     * legend key.
     * <p>
     * 
     * <i>Notes:</i>
     * 
     * <ol>
     * <li>A curve must also have a non-<tt>null</tt> legend label if it is to
     * appear on the legend key.
     * <p>
     * 
     * <li>Hidden curves are excluded from the computation of any auto-computed
     * axis limits.
     * 
     * </ol>
     * 
     * @param isVisible
     *          false to hide curve, true to reveal it.
     * 
     * @see #isVisible() isVisible
     * @see #setLegendLabel setLegendLabel
     * 
     */
    public void setVisible(boolean isVisible) {
      /*
       * Axis curve count bookkeeping requires that curve be on the list of
       * curves. <p>
       * 
       * Developer refs to removed curves could screw up this bookkeeping. <p>
       * 
       * Though often this would be a developer error, best not to throw an
       * exception because some developers could use deleted curves as
       * repositories for curve state data, or an event sequence might produce
       * setVisible calls through a dangling curve reference after a curve had
       * been removed, and best to let developer get away with that kind of
       * thing. <p>
       * 
       * No point in invalidating since such removed curves can never become part of
       * a rendered GChart again.
       */

      if (getIndexOf() == GChart.NAI) {
        this.isVisible = isVisible;
        return;
      }

      invalidate();

      /*
       * System curves, such as the hover selection feedback curve,
       * never impact curve counts or appear on the chart legend, so
       * their visibility changes never have additional side effects
       * that require a refresh of the chart's decorations. 
       * 
       */ 
      if (isSystemCurve()) {
        this.isVisible = isVisible;
      } else if (this.isVisible != isVisible) {
        Axis yaxis = getYAxisForAxisID(getYAxis());
        boolean axisCreatedOrDestroyed;
        if (isVisible) {
          axisCreatedOrDestroyed = (yaxis.getNCurvesVisibleOnAxis() == 0);
          getXAxis().incrementCurves();
          yaxis.incrementCurves();
        } else {
          getXAxis().decrementCurves();
          yaxis.decrementCurves();
          axisCreatedOrDestroyed = (yaxis.getNCurvesVisibleOnAxis() == 0);
        }

        if ((null != getLegendLabel() && isLegendVisible())
            || axisCreatedOrDestroyed)
          chartDecorationsChanged = true;

        this.isVisible = isVisible;
      }
    }

    /**
     * Specifies the number of pixels the entire rendered curve is to be shifted
     * along the x-axis from it's default position.
     * <p>
     * 
     * Positive values shift the curve to the right, negative values to the
     * left.
     * <p>
     * 
     * <small><i>Tip:</i> Because the entire <tt>Panel</tt> that holds the
     * curve's rendering is shifted, an <tt>update</tt> that only shifts the
     * curve's position using this method can be performed much more quickly
     * than a re-rendering of the entire curve. This makes such shifts suitable
     * for use in animations and similar applications requiring fast changes in
     * a previously rendered curve's position. For example, the <a
     * href="http://clientsidegchart.googlecode.com/svn/trunk/live-demo/v2_7/com.googlecode.gchart.gchartdemoapp.GChartDemoApp/GChartDemoApp.html"> GChart live demo's</a> LayZLine&trade; chart editor uses these methods
     * to provide visual feedback for its "drag-to-pan" feature.
     * </small>
     * 
     * @param xShift
     *          number of pixels to shift rendered curve along x-axis.
     * 
     * @see #setYShift setYShift
     * 
     */
    public void setXShift(int xShift) {
      xOrYShiftChanged = true;
      this.xShift = xShift;
    }

    /**
     * Specifies the number of pixels the entire rendered curve is to be shifted
     * along the y-axis from it's default position.
     * <p>
     * 
     * Positive values shift the curve towards the top of the page, negative
     * values towards the bottom.
     * <p>
     * 
     * <small><i>Tip:</i> Because the entire <tt>Panel</tt> that holds the
     * curve's rendering is shifted, an <tt>update</tt> that only shifts the
     * curve's position using this method can be performed much more quickly
     * than a re-rendering of the entire curve. This makes such shifts suitable
     * for use in animations and similar applications requiring fast changes in
     * a previously rendered curve's position. </small>
     * 
     * @param yShift
     *          number of pixels to shift rendered curve along y-axis.
     * 
     * @see #setXShift setXShift
     * 
     */
    public void setYShift(int yShift) {
      xOrYShiftChanged = true;
      this.yShift = yShift;
    }

    /**
     * Sets the y-axis that this curve is plotted on.
     * <p>
     * 
     * @param axisId
     *          must be either <tt>GChart.Y_AXIS</tt> or
     *          <tt>GChart.Y2_AXIS</tt>
     * 
     * @see #getYAxis getYAxis
     * @see GChart#Y_AXIS Y_AXIS
     * @see GChart#Y2_AXIS Y2_AXIS
     * 
     */
    public void setYAxis(YAxisId axisId) {
        invalidate();
        if (isSystemCurve()) {
          yAxisId = axisId;
        } else if (axisId != yAxisId) {
        	for(int i = 0; i < Y_AXES_MAX; i++){
        		if (axisId == Y_AXIS[i]){
        			for(int j = 0; j < Y_AXES_MAX; j++){
        				if(i == j){
        					GChart.this.getYAxis(i).incrementCurves();
        					updateAxisColor(i);
        				} /*else {
        					GChart.this.getYAxis(i).decrementCurves();
        				}*/
        			}
        		}
        	}
        	yAxisId = axisId;
        }else {
        	updateAxisColor(0);
        }
      }
    
    public void updateAxisColor(int i){
    	/*getSystemCurve(YLABEL_ID[i]).getPoint(0).getAnnotation().setFontColor(getSymbol().getBorderColor());
    	getSystemCurve(YTICKS_ID[i]).getSymbol().setBorderColor(getSymbol().getBorderColor());
    	getSystemCurve(YAXIS_ID[i]).getPoint(0).getAnnotation().setFontColor(getSymbol().getBorderColor());*/
    }
    
    public Axis getYAxisForAxisID(YAxisId axisId){
    	for(int i = 0; i < Y_AXES_MAX; i++){
    		if (axisId == Y_AXIS[i]){
    			return GChart.this.getYAxis(i);
    		}
    	}
    	return GChart.this.getYAxis(0);
    }
    
    public int getIndexForAxisID(YAxisId axisId){
    	for(int i = 0; i < Y_AXES_MAX; i++){
    		if (axisId == Y_AXIS[i]){
    			return i;
    		}
    	}
    	return 0;
    }

    /*
     * Is this curve one of GChart's special, internally created, system curves?
     * These curves can't be directly accessed by users, and are used by GChart
     * to render special features of the chart, such as the hover selection
     * cursors, titles, footnotes, ticks, gridlines, etc.
     */
    private boolean isSystemCurve() {
      // negative curve indexes are reserved for system curves
      boolean result = (indexOf != GChart.NAI)
          && externalCurveIndex(indexOf) < 0;
      return result;
    }

    // renders the specified point of this curve on the given panels
    void realizePoint(PlotPanel pp, GraphicsRenderingPanel grp,
        AnnotationRenderingPanel arp, int iPoint, int curveIndex) {
      Point p = points.get(iPoint);
      double x = p.getX();
      double y = p.getY();
      //boolean onY2 = onY2();
      // skip points at undefined locations
      if ((x != x) || (y != y) || // x!=x is a faster isNaN
          plotPanel.isOutOfBounds(x, y))
        return;

      double prevX = Double.NaN;
      double prevY = Double.NaN;
      if (iPoint > 0) {
        Point prevP = points.get(iPoint - 1);
        prevX = prevP.getX();
        prevY = prevP.getY();
        if (plotPanel.isOutOfBounds(prevX, prevY))
          prevX = prevY = Double.NaN;
      }
      double nextX = Double.NaN;
      double nextY = Double.NaN;
      Point nextP = null;
      if (iPoint < getNPoints() - 1) {
        nextP = points.get(iPoint + 1);
        nextX = nextP.getX();
        nextY = nextP.getY();
        if (plotPanel.isOutOfBounds(nextX, nextY))
          nextX = nextY = Double.NaN;
      }

      /*
       * If point was not assigned to any band, it's not drawn
       * at all (undefined x or y, or off chart entirely).
       * 
       */
      boolean drawMainSymbol = (p.getINextInBand() != iPoint);

      getSymbol().getSymbolType().realizeSymbol(
          pp, grp, arp, getSymbol(), p.getAnnotation(), getYAxesIndexForCurveId(curveIndex),
          getClippedToPlotArea(), getClipToDecoratedChart(),
          getRenderPaddingFactor(), drawMainSymbol, x, y, prevX,
          prevY, nextX, nextY);
    }

    /**
     * Represents a single point on one of the chart's curves. This includes the
     * x, y values of the point in "model coordinates" (arbitrary,
     * application-specific, units), as well as an optional annotation (text
     * label) for the point.
     * <p>
     * 
     * To create points, you must use a curve's <tt>addPoint</tt> method.
     * 
     * @see Curve#addPoint addPoint
     * 
     */
    public class Point {

      /*
       * x, y location (user coordinates) of point (points are drawn
       * using the containing curve's symbol).
       * 
       */ 
      private double x;
      private double y;
      Annotation annotation = null;
      /*
       * Points to index of next point in a vertical or horizontal
       * band (used by the <tt>bandSeparatePoints</tt> method).
       *
       */ 
      private int iNextInBand = GChart.NAI;

      int getINextInBand() {
        return iNextInBand;
      }

      void setINextInBand(int iNext) {
        iNextInBand = iNext;
      }

      Point(double x, double y) {
        this.x = x;
        this.y = y;
      }

      /**
       * Returns the weight of the font that will be used to render the text
       * of this point's annotation.
       * <p>
       * 
       * @return the font weight of the annotation.
       * 
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       */
      public String getAnnotationFontWeight() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getFontWeight();
      }

      /**
       * Returns the color of the font used to display the point's annotation
       * text.
       * 
       * @return CSS color string defining the annotation's color
       * 
       * @see #setAnnotationFontColor setAnnotationFontColor
       */
      public String getAnnotationFontColor() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getFontColor();
      }

      /**
       * Returns the CSS font-family used to render the text of this
       * point's annotation.
       * 
       * @return the CSS font-family used by this annotation.
       * 
       * @see #setAnnotationFontFamily setAnnotationFontFamily
       */
      public String getAnnotationFontFamily() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getFontFamily();
      }

      /**
       * Returns the CSS font-style in which the text of this point's
       * annotation will be rendered.
       * 
       * @return the font-style used by this point's annotation (italic, normal, etc.)
       * 
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       */
      public String getAnnotationFontStyle() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getFontStyle();
      }

      /**
       * Returns the CSS font size of this point's annotation (text label), in
       * pixels.
       * 
       * @return the font size of this point's annotation, in pixels.
       * 
       * @see #setAnnotationFontSize setAnnotationFontSize
       */
      public int getAnnotationFontSize() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getFontSize();
      }

      /**
       * Returns the previously specified location, relative to the symbol
       * representing the point, of the annotation (text label) associated with
       * this point.
       * 
       * @return relative location of the point's annotation
       * 
       * @see #setAnnotationLocation setAnnotationLocation
       * 
       */
      public AnnotationLocation getAnnotationLocation() {
        if (null == annotation)
          annotation = new Annotation();
        AnnotationLocation result = annotation.getLocation();
        if (null == result)
          result =
            getParent().getSymbol().getSymbolType().defaultAnnotationLocation();
        return result;
      }

      /**
       * Returns the text of this point's annotation.
       * 
       * @return the text of the annotation, or <tt>null</tt> if this point
       *         either lacks an annotation or uses a widget-based annotation.
       * 
       * @see #setAnnotationText setAnnotationText
       * 
       */
      public String getAnnotationText() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getText();
      }

      /**
       * Returns the widget reference that defines this point's annotation as
       * previously specified by <tt>setAnnotationWidget</tt>. Returns
       * <tt>null</tt> if the annotation has not yet been specified, or if it
       * was defined via <tt>setAnnotationText</tt>.
       * 
       * @return reference to the widget defining this point's annotation, or
       *         <tt>null</tt> if none.
       * 
       * @see #setAnnotationWidget setAnnotationWidget
       * @see #setAnnotationText setAnnotationText
       * 
       */

      public Widget getAnnotationWidget() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getWidget();
      }

      /**
       * Returns true is the point's annotation is visible, false otherwise
       * 
       * @return if the a annotation defined for this point will be visible or
       *         not after the next update.
       * 
       * @see #setAnnotationVisible setAnnotationVisible
       */
      public boolean getAnnotationVisible() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getVisible();
      }

      /**
       * Returns the distance, in pixels, that this annotation will be shifted
       * along the x-axis from it's default location.
       * <p>
       * 
       * @return amount annotation will be shifted along the x-axis, in pixels.
       * 
       * @see #setAnnotationXShift setAnnotationXShift
       */
      public int getAnnotationXShift() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getXShift();
      }

      /**
       * Returns the distance, in pixels, that this annotation will be shifted
       * along the y-axis from it's default location.
       * <p>
       * 
       * @return amount annotation will be shifted along the y-axis, in pixels.
       * 
       * @see #setAnnotationYShift setAnnotationYShift
       */
      public int getAnnotationYShift() {
        if (null == annotation)
          annotation = new Annotation();
        return annotation.getYShift();
      }

      /**
       * Returns the <tt>Curve</tt> that this point was added to.
       * 
       * @return a reference to the <tt>Curve</tt> that contains this point (its
       *         "parent").
       * 
       */
      public Curve getParent() {
        return Curve.this;
      }

      /**
       * Returns the x-coordinate of this point in "model units" (arbitrary,
       * application-specific, units).
       * 
       * @return the x-coordinate, in model units
       * 
       * @see #setX setX
       * @see #setY setY
       * @see #getY getY
       * 
       */
      public double getX() {
        return x;
      }

      /**
       * Returns the y-coordinate of this point in "model units" (arbitrary,
       * application-specific, units).
       * 
       * @return the y-coordinate, in model units
       * 
       * @see #getX getX
       * @see #setX setX
       * @see #setY setY
       * 
       */
      public double getY() {
        return y;
      }

      /**
       * Specifies the weight of the font that will be used to render the text
       * of this point's annotation.
       * <p>
       * 
       * @param cssWeight
       *          A standard CSS font-weight specification such as normal, bold,
       *          bolder, lighter, 100, 200, ... 900, or inherit
       * 
       * @see #getAnnotationFontWeight getAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       */
      public void setAnnotationFontWeight(String cssWeight) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setFontWeight(cssWeight);
      }

      /**
       * Specifies the color of the annotation's font.
       * <p>
       * 
       * For more information on standard CSS color specifications see the
       * discussion in {@link Symbol#setBackgroundColor
       * Symbol.setBackgroundColor}.
       * <p>
       * 
       * @param cssColor
       *          color of the font used to display this point's annotation.
       * 
       * @see #getAnnotationFontColor getAnnotationFontColor
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       */
      public void setAnnotationFontColor(String cssColor) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setFontColor(cssColor);
      }

      /**
       * Specifies the CSS font-family used by this point's annotation.
       * <p>
       * 
       * The default font-family is <tt>DEFAULT_FONT_FAMILY</tt>.
       * <p>
       * 
       * @param cssFontFamily
       *          any valid CSS font-family, such as
       *          <tt>"serif", "sans-serif", "monospace", "cursive",
       *          "fantasy"</tt> or <tt>"Arial, sans-serif"</tt>.
       * 
       * @see #getAnnotationFontFamily getAnnotationFontFamily
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #DEFAULT_FONT_FAMILY DEFAULT_FONT_FAMILY
       * 
       */
      public void setAnnotationFontFamily(String cssFontFamily) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setFontFamily(cssFontFamily);
      }

      /**
       * Specifies the CSS font-style used by this point's annotation.
       * 
       * @param cssStyle
       *          any valid CSS font-style, namely, normal, italic, oblique, or
       *          inherit.
       * 
       * @see #getAnnotationFontStyle getAnnotationFontStyle
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       * 
       */
      public void setAnnotationFontStyle(String cssStyle) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setFontStyle(cssStyle);
      }

      /**
       * Specifies the CSS font size of this point's annotation, in pixels.
       * 
       * @param fontSize
       *          the font size of this point's annotation, in pixels.
       * 
       * @see #getAnnotationFontSize getAnnotationFontSize
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       */
      public void setAnnotationFontSize(int fontSize) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setFontSize(fontSize);
      }

      /**
       * Specifies the location, relative to this point's symbol, of this
       * point's annotation (text label).
       * <p>
       * 
       * You can further adjust the position of a point's annotation by
       * specifying non-zero positional shifts via the
       * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods.
       * 
       * 
       * @param annotationLocation
       *          the relative location of the annotation
       * 
       * @see #getAnnotationLocation getAnnotationLocation
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationVisible setAnnotationVisible
       * 
       */
      public void setAnnotationLocation(
          AnnotationLocation annotationLocation) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setLocation(annotationLocation);
      }

      /**
       * Specifies the text of this point's annotation (label).
       * <p>
       * 
       * By default text is plain text, though you can change the size, weight,
       * style, and color of the text via the <tt>setAnnotationFont*</tt> family
       * of methods.
       * <p>
       * 
       * <b>To use HTML, <i>your text must begin with</i> <tt>&lt;html&gt</tt>
       * </b> (otherwise, GChart will treat it as plain text). Note that the
       * leading <tt>&lt;html&gt</tt> is stripped off by GChart before your HTML
       * gets to the browser. Since it's just a flag for GChart, not a real HTML
       * tag, you should <i>not</i> use a closing <tt>&lt;/html&gt</tt> at the
       * end.
       * <p>
       * 
       * <small> The idea for adding HTML support (only plain text was supported
       * originally) came from <a href=
       * "http://groups.google.com/group/Google-Web-Toolkit/msg/cb89003dad2416fe"
       * > this GWT forum post by Malcolm Gorman</a>. The current HTML support
       * (and, it's natural extension, Widget support) in tick labels and
       * annotations, which seems so obvious in hindsight, might never have been
       * added had it not been for this post. Thanks!</small>
       * <p>
       * 
       * <small><b>How to use the width and height upperbounds:</b> </small>
       * <p>
       * 
       * <blockquote><small>
       * In most cases, you can safely ignore these two parameters, simply
       * calling the {@link #setAnnotationText(String) 1-arg convenience method}
       * and getting GChart to estimate them for you.
       * <p>
       * 
       * The width and height upper-bounds define an invisible bounding box (a
       * 1x1 GWT Grid, actualy) that is used to properly align and center your
       * annotation.
       * <p>
       * 
       * <p>
       * Annotations can become misaligned if, say, due to the user zooming up
       * their font size, an annotation's size exceeds these upperbounds. This
       * misalignment problem can be fixed by specifying a larger width and/or
       * height upperbound. But, larger upperbounds slow chart updates a bit.
       * The defaults try to balance the performance and alignment tradeoff.
       * <p>
       * 
       * There is one annoying but generally harmless side effect of using very
       * large upperbounds: most browsers will extend their scroll regions to
       * the right (and presumably below) the real page content so as to include
       * the invisible bounding box. When this happens, it looks to the user as
       * if there is a bunch of blank space on, say, the right edge of the page.
       * In practice, I've always been able to prevent this problem simply by
       * choosing at-least-somewhat-reasonably-tight upper bounds, though a
       * little blank space may be unavoidable in some special cases.
       * </blockquote></small>
       * <p>
       * 
       * @param annotationText
       *          the text or (<tt>&lt;html&gt</tt> prefixed) HTML of this
       *          point's annotation, or <tt>null</tt> to remove all annotation.
       * 
       * @param widthUpperBound
       *          an upper bound on the width of the text or HTML, in pixels.
       *          Use <tt>GChart.NAI</tt> to get GChart to estimate this width
       *          using an heuristic that works fine most of the time.
       * 
       * @param heightUpperBound
       *          an upper bound on the height of the text or HTML, in pixels.
       *          Use <tt>GChart.NAI</tt> to get GChart to estimate this height
       *          using an heuristic that works fine most of the time.
       * 
       * @see #getAnnotationText getAnnotationText
       * @see #setAnnotationText(String) setAnnotationText(String)
       * @see #setAnnotationLocation setAnnotationLocation
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationWidget setAnnotationWidget
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationVisible setAnnotationVisible
       * @see GChart.Axis#addTick(double,String,int,int) addTick
       * 
       */
      public void setAnnotationText(String annotationText,
          int widthUpperBound, int heightUpperBound) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setText(annotationText, widthUpperBound,
            heightUpperBound);
      }

      /**
       * Sets the text of an annotation.
       * <p>
       * 
       * This is a convenience method equivalent to
       * <tt>setAnnotationText(annotationText, GChart.NAI, GChart.NAI)</tt> .
       * See that method for further details.
       * <p>
       * 
       * @param annotationText
       *          the text or (<tt>&lt;html&gt</tt>-prefixed) HTML of this
       *          point's annotation, or <tt>null</tt> to remove all annotation.
       * 
       * @see #setAnnotationText(String, int, int)
       *      setAnnotationText(String,int,int)
       * 
       */
      public void setAnnotationText(String annotationText) {
        setAnnotationText(annotationText, GChart.NAI, GChart.NAI);
      }

      /**
       * Specifies a widget defining this point's annotation
       * <p>
       * 
       * This method is similar to <tt>setAnnotationText</tt> except that it
       * uses a widget, rather than a string to define this point's annotation.
       * Although the string based method is faster on first chart rendering,
       * and uses less memory, the widget-based method allows you to change the
       * annotation independently of the chart--potentially bypassing (or at
       * least speeding up) expensive chart updates later on.
       * <p>
       * 
       * You might use a widget-based annotation to pop-up a message whenever
       * the user clicks on a button underneath a particular data point on the
       * chart, to include a small GWT <tt>Grid</tt> as a table embedded in the
       * upper left hand corner of the chart, to trigger mouse-over events when
       * the user hovers over a transparent image-based annotation centered on a
       * particular point, etc.
       * <p>
       * 
       * <i>Tip:</i>If you need to instrument a chart using widgets precisely
       * positioned on the chart, but not associated with any visible curve, add
       * a curve just to hold these annotations, with one point per annotation,
       * and set that curve's symbol type to <tt>SymbolType.NONE</tt>.
       * <p>
       * 
       * <b><i>Warning:</i></b> If you use the exact same widget reference to
       * define two different annotations, GChart will render only one of them,
       * and <i>there is no easy rule</i> that lets you reliably determine which
       * one. So, don't do that. Instead, if you want to use the same widget for
       * two different annotations, use two identical but distinct copies of
       * that widget. Similarly, if you want to move a single widget annotation
       * from one point to another, be sure to "<tt>null</tt> out" the first
       * point's annotation (e.g. via <tt>setAnnotationWidget(null)</tt>) or the
       * widget may not be rendered where you expect. You should also
       * <tt>null</tt> out the annotation widget reference before moving the
       * annotation widget to a position in the DOM completely outside of the
       * GChart. This required extra bookkeeping on your part makes it possible to
       * significantly simplify and streamline GChart's rendering algorithms.
       * 
       * @param annotationWidget
       *          the GWT Widget that defines this point's annotation.
       * 
       * @param widthUpperBound
       *          an upper bound on the width of the Widget, in pixels. If this
       *          and the next parameter are omitted, GChart will use
       *          <tt>DEFAULT_WIDGET_WIDTH_UPPERBOUND</tt>.
       * 
       * @param heightUpperBound
       *          an upper bound on the height of the Widget, in pixels. If this
       *          and the previous parameter are omitted, GChart will use
       *          <tt>DEFAULT_WIDGET_HEIGHT_UPPERBOUND</tt>
       * 
       * @see #getAnnotationWidget getAnnotationWidget
       * @see #setAnnotationText(String, int, int)
       *      setAnnotationText(String,int,int)
       * @see #setAnnotationWidget(Widget) setAnnotationWidget(Widget)
       * @see #DEFAULT_WIDGET_HEIGHT_UPPERBOUND DEFAULT_WIDGET_HEIGHT_UPPERBOUND
       * @see #DEFAULT_WIDGET_WIDTH_UPPERBOUND DEFAULT_WIDGET_WIDTH_UPPERBOUND
       * @see SymbolType#NONE SymbolType.NONE
       * 
       */
      public void setAnnotationWidget(Widget annotationWidget,
          int widthUpperBound, int heightUpperBound) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        // accept "Not an Integer" (because setAnnotationText does)
        if (widthUpperBound == GChart.NAI)
          widthUpperBound = DEFAULT_WIDGET_WIDTH_UPPERBOUND;
        if (heightUpperBound == GChart.NAI)
          heightUpperBound = DEFAULT_WIDGET_HEIGHT_UPPERBOUND;
        annotation.setWidget(annotationWidget, widthUpperBound,
            heightUpperBound);
      }

      /**
       * Specifies a widget defining this point's annotation.
       * <p>
       * 
       * A convenience method equivalent to
       * <tt>setAnnotationWidget(annotationWidget,
       * DEFAULT_WIDGET_WIDTH_UPPERBOUND,
       * DEFAULT_WIDGET_HEIGHT_UPPERBOUND)</tt>
       * 
       * @param annotationWidget
       *          the GWT Widget that defines this point's annotation.
       * 
       * @see #setAnnotationWidget(Widget,int,int)
       *      setAnnotationWidget(Widget,int,int)
       * @see #DEFAULT_WIDGET_HEIGHT_UPPERBOUND DEFAULT_WIDGET_HEIGHT_UPPERBOUND
       * @see #DEFAULT_WIDGET_WIDTH_UPPERBOUND DEFAULT_WIDGET_WIDTH_UPPERBOUND
       * 
       */
      public void setAnnotationWidget(Widget annotationWidget) {
        setAnnotationWidget(annotationWidget,
            DEFAULT_WIDGET_WIDTH_UPPERBOUND,
            DEFAULT_WIDGET_HEIGHT_UPPERBOUND);
      }

      /**
       * Specifies if this point's annotation (label) is visible or not.
       * <p>
       * 
       * @param isVisible
       *          use true to make the annotation visible, or false to hide it.
       * 
       * @see #getAnnotationVisible getAnnotationVisible
       * @see #setAnnotationLocation setAnnotationLocation
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationText setAnnotationText
       */
      public void setAnnotationVisible(boolean isVisible) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setVisible(isVisible);
      }

      /**
       * Specifies the number of pixels (along the x-axis) to move this point's
       * annotation from its default, <tt>AnnotationLocation</tt> defined,
       * position. Negative values move the annotation in the negative x
       * direction.
       * <p>
       * 
       * For example, with the default <tt>xShift</tt> of 0, annotations with an
       * <tt>AnnotationLocation</tt> of <tt>EAST</tt> will have their left edges
       * flush against the right edge of, say, a box symbol representing the
       * annotated point. You could use an <tt>xShift</tt> setting of 10 to move
       * the annotation 10 pixels to the right and thus introduce some space
       * between the annotation and the box.
       * <p>
       * 
       * <i>Special convention for pie slices:</i> Points on curves whose
       * symbols represent pie slices always have the positive x-axis associated
       * with the shifts specified by this method aligned with the
       * outward-pointing pie radius that bisects the pie slice. This convention
       * makes it easy to move pie slice annotations radially outward (via
       * <tt>xShift > 0</tt>) or radially inward (via <tt>xShift < 0</tt>). For
       * those rare situations where you may need to move a pie annotation
       * perpendicularly to this radius, use <tt>setAnnotationYShift</tt>.
       * 
       * @param xShift
       *          number of pixels to move annotation along the x axis from it's
       *          default, <tt>AnnotationLocation</tt> defined, location.
       * 
       * @see #setAnnotationYShift setAnnotationYShift
       * @see #setAnnotationLocation setAnnotationLocation
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       * @see #getAnnotationXShift getAnnotationXShift
       */
      public void setAnnotationXShift(int xShift) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setXShift(xShift);
      }

      /**
       * Specifies the number of pixels (along the y-axis) to move this point's
       * annotation from its default, <tt>AnnotationLocation</tt> defined,
       * position. Negative values move the annotation in the negative y
       * direction.
       * 
       * <p>
       * For example, with the default <tt>yShift</tt> of 0, annotations with an
       * <tt>AnnotationLocation</tt> of <tt>SOUTH</tt> will have their top edges
       * flush against the bottom edge of, say, a box symbol representing the
       * annotated point. You could use a <tt>yShift</tt> setting of -10 to move
       * the annotation down 10 pixels and thus introduce some spacing between
       * the annotation and the box.
       * <p>
       * 
       * <i>Special convention for pie slices:</i> The positive y-axis for pie
       * slices always points one 90 degree counter-clockwise rotation from the
       * direction of the outward-pointing pie radius that bisects the pie
       * slice. This convention means that <tt>yShift</tt> moves pie slice
       * annotations along a line <i>perpendicular to</i> this bisecting pie
       * radius. Use the companion method <tt>setAnnotationXShift</tt> for the
       * more common operation of moving the annotation along this bisecting
       * radius.
       * <p>
       * 
       * @param yShift
       *          number of pixels to move annotation along the y-axis from it's
       *          default, <tt>AnnotationLocation</tt> defined, location.
       * 
       * @see #setAnnotationXShift setAnnotationXShift
       * @see #setAnnotationLocation setAnnotationLocation
       * @see #setAnnotationFontWeight setAnnotationFontWeight
       * @see #setAnnotationFontColor setAnnotationFontColor
       * @see #setAnnotationFontStyle setAnnotationFontStyle
       * @see #setAnnotationFontSize setAnnotationFontSize
       * @see #setAnnotationText setAnnotationText
       * @see #setAnnotationVisible setAnnotationVisible
       * @see #getAnnotationXShift getAnnotationXShift
       * 
       */
      public void setAnnotationYShift(int yShift) {
        getParent().invalidate();
        if (null == annotation)
          annotation = new Annotation();
        annotation.setYShift(yShift);
      }

      /**
       * Defines the x-coordinate of this point in "model units" (arbitrary,
       * application-specific, units mapped to the horizontal dimension of the
       * plot area).
       * <p>
       * 
       * <tt>Double.NaN</tt>, <tt>Double.MAX_VALUE</tt>, and
       * <tt>-Double.MAX_VALUE</tt> have special meanings. See the
       * <tt>addPoint</tt> method for details.
       * <p>
       * 
       * @param x
       *          the x-coordinate of the point in model units.
       * 
       * @see #getX getX
       * @see #setY setY
       * @see #getY getY
       * @see #addPoint(double, double) addPoint
       */
      public void setX(double x) {
        getParent().invalidate();
        this.x = x;
      }

      /**
       * Defines the y-coordinate of this point in "model units" (arbitrary,
       * application-specific, units mapped to the vertical dimension of the
       * plot area).
       * <p>
       * 
       * <tt>Double.NaN</tt>, <tt>Double.MAX_VALUE</tt>, and
       * <tt>-Double.MAX_VALUE</tt> have special meanings. See the
       * <tt>addPoint</tt> method for details.
       * <p>
       * 
       * @param y
       *          the y-coordinate of the point in model units.
       * 
       * @see #getX getX
       * @see #setX setX
       * @see #getY getY
       * @see #addPoint(double, double) addPoint
       * 
       */
      public void setY(double y) {
        getParent().invalidate();
        this.y = y;
      }

      Annotation getAnnotation() {
        if (annotation == null)
          annotation = new Annotation();
        return annotation;
      }

      /**
       * Retrieves the expanded hovertext associated with this point.
       * <p>
       * 
       * The expanded hovertext is obtained by replacing any embedded parameters
       * in the hovertext template with their values as evaluated at this point.
       * For example, references to <tt>${x}</tt> and <tt>${y}</tt> in the
       * hovertext template are replaced with appropriately formatted
       * representations of this point's x and y properties.
       * <p>
       * 
       * By default, GChart will display this expanded hovertext whenever the
       * user "touches" a point with the curve-specific, rectangular,
       * mouse-centered, brush.
       * <p>
       * 
       * <i>Tip:</i> To define your own custom parameter names that can be
       * embedded within hovertext templates and will be interpreted/expanded
       * relative to the touched point, use the
       * <tt>setHoverParameterInterpreter</tt> method.
       * <p>
       * 
       * @return the expanded hover text associated with this point.
       * 
       * @see Symbol#setHovertextTemplate setHovertextTemplate
       * @see #setHoverParameterInterpreter setHoverParameterInterpreter
       * 
       */
      public String getHovertext() {
        String result = HovertextChunk.getHovertext(getParent()
            .getSymbol().getHovertextChunks(), this);
        return result;
      }

    } // end of class GChart.Curve.Point

    /*
     * Declares that this curve's rendering panel (its DOM representation) is
     * inconsistent with its current curve specifications.
     * <p>
     * 
     * Sets the flag <tt>update</tt> uses to determine if a curve needs to be
     * re-rendered. <p>
     */
    void invalidate() {
      /*
       * This guard isn't just for speed; it keeps us out of trouble
       * when the system curves are being added/configured initially.
       *
       */ 
      if (isValidated) {
        isValidated = false;
        /*
         * For efficiency, all background curves share one rendering
         * panel, so invalidating one such curve invalidates them all.
         *
         */ 
        if (indexOf < N_PRE_SYSTEM_CURVES) {
          for (int i = 0; i < N_PRE_SYSTEM_CURVES; i++)
            curves.get(i).isValidated = false;
        }
      }
    }

    /*
     * Smallest rectangle containing curve's graphics (ignoring annotations) <p>
     * 
     * Each curve has it's own canvas to allow for fast, single curve, updates
     * (and we hope this rendering independence will facilitate additional
     * features in future releases). So, we have to economize on canvas size.
     * Moreover, because we allow rendering outside of the decorated chart
     * region, we can't just set the size of the canvas to the size of the plot
     * area or decorated chart (even if we could afford to do that,
     * memory-wise).
     * <p>
     *
     * Rectangle is in plot-area pixel coordinates.
     * 
     */
    Rectangle getContainingRectangle(PlotPanel pp) {
      final Rectangle result = new Rectangle();
      if (getNPoints() == 0) {
        result.x = result.y = result.width = result.height = 0;
        return result;
      }

      double minX = Double.MAX_VALUE;
      double maxX = -Double.MAX_VALUE;
      double minY = Double.MAX_VALUE;
      double maxY = -Double.MAX_VALUE;
      // do keyword-positioned points exist on this curve?
      boolean pointAtXAxisMin = false; 
      boolean pointAtXAxisMax = false; 
      boolean pointAtYAxisMin = false;
      boolean pointAtYAxisMax = false;
      boolean isClippedToDecoratedChart = getClipToDecoratedChart();
      boolean isClippedToPlotArea = getClippedToPlotArea();
      //boolean onY2 = onY2();
      // Find min, max for x,y and record each keyword position used
      int nPoints = getNPoints();
      for (int i = 0; i < nPoints; i++) {
        Point p = getPoint(i);
        double x = p.getX();
        double y = p.getY();
        if ((x != x) || (y != y) || // x!=x is a faster isNaN
            plotPanel.isOutOfBounds(x, y))
          continue;

        if (Double.MAX_VALUE == x)
          pointAtXAxisMax = true;
        else if (-Double.MAX_VALUE == x)
          pointAtXAxisMin = true;
        else {
          if (x < minX)
            minX = x;
          if (x > maxX)
            maxX = x;
        }
        if (Double.MAX_VALUE == y)
          pointAtYAxisMax = true;
        else if (-Double.MAX_VALUE == y)
          pointAtYAxisMin = true;
        else {
          if (y < minY)
            minY = y;
          if (y > maxY)
            maxY = y;
        }
      }

      // take "at min/max" keywords into account
      if (pointAtXAxisMin) {
        minX = Math.min(minX, pp.getXMin());
        maxX = Math.max(maxX, pp.getXMin());
      }
      if (pointAtXAxisMax) {
        minX = Math.min(minX, pp.getXMax());
        maxX = Math.max(maxX, pp.getXMax());
      }
      //if (onY2) {
        if (pointAtYAxisMin) {
          minY = Math.min(minY, pp.getYMin(getIndexForAxisID(getYAxis())));
          maxY = Math.max(maxY, pp.getYMin(getIndexForAxisID(getYAxis())));
        }
        if (pointAtYAxisMax) {
          minY = Math.min(minY, pp.getYMax(getIndexForAxisID(getYAxis())));
          maxY = Math.max(maxY, pp.getYMax(getIndexForAxisID(getYAxis())));
        }
    //  } 
      /*else {
        if (pointAtYAxisMin) {
          minY = Math.min(minY, pp.getYMin());
          maxY = Math.max(maxY, pp.getYMin());
        }
        if (pointAtYAxisMax) {
          minY = Math.min(minY, pp.getYMax());
          maxY = Math.max(maxY, pp.getYMax());
        }
      }*/

      /*
       * Convert to pixels while taking into account the size of the
       * rendered symbol itself (e.g. pies can stick out from their x,y
       * specified center point, etc.)
       *
       */ 
      Symbol sym = getSymbol();
      SymbolType symType = sym.getSymbolType();
      /*
       * Given how baseline bars currently work, they can have negative
       * widths or heights that 'turn the bar inside-out' interchanging
       * the roles of left and right and top and bottom edges. So, we
       * need to check all combinations of min and the max x and y and
       * left and right edge determining methods in order to determine
       * the actual bounding rectangle.
       * 
       */ 
      int index = getIndexForAxisID(getYAxis());
      double left0 = symType.getEdgeLeft(pp, sym, minX, index);
      double left1 = symType.getEdgeLeft(pp, sym, maxX, index);
      double right0 = symType.getEdgeRight(pp, sym, minX, index);
      double right1 = symType.getEdgeRight(pp, sym, maxX, index);
      double bottom0 = symType.getEdgeBottom(pp, sym, minY, index);
      double bottom1 = symType.getEdgeBottom(pp, sym, maxY, index);
      double top0 = symType.getEdgeTop(pp, sym, minY, index);
      double top1 = symType.getEdgeTop(pp, sym, maxY, index);
      double xPxMin = Math.min(Math.min(left0, left1),
                               Math.min(right0,right1));
      double xPxMax = Math.max(Math.max(left0, left1),
                               Math.max(right0,right1));
      double yPxMin = Math.min(Math.min(bottom0, bottom1),
                               Math.min(top0,top1));
      double yPxMax = Math.max(Math.max(bottom0, bottom1),
                               Math.max(top0, top1));

      // in obscure cases, canvas could clip without this extra space
      int extraSpace = sym.getFillThickness();
      extraSpace += Math.abs(sym.getBorderWidth());
      double extraXSpace = extraSpace + 0.5 * canvasExpansionFactorX
          * (xPxMax - xPxMin);
      double extraYSpace = extraSpace + 0.5 * canvasExpansionFactorY
          * (yPxMax - yPxMin);
      xPxMin -= extraXSpace;
      xPxMax += extraXSpace;
      yPxMin -= extraYSpace;
      yPxMax += extraYSpace;
      // finally, clip the rectangle, when appropriate
      if (isClippedToPlotArea || isClippedToDecoratedChart) {
        double edge0;
        double edge1;
        double edgeMin;
        double edgeMax;

        if (isClippedToPlotArea) {
          edge0 = pp.xToPixel(pp.getXMin());
          edge1 = pp.xToPixel(pp.getXMax());
        } else { // clipped to decorated chart
          edge0 = pp.xToPixel(getXAxis().pixelToModel(0));
          edge1 = pp.xToPixel(getXAxis().pixelToModel(
              pp.getXChartSizeDecoratedQuickly()));
        }
        // axis min and max are not ness smallest/largest values
        edgeMin = Math.min(edge0, edge1);
        edgeMax = Math.max(edge0, edge1);
        xPxMin = Math.max(xPxMin, edgeMin - renderPaddingFactor
            * (edgeMax - edgeMin));
        xPxMax = Math.min(xPxMax, edgeMax + renderPaddingFactor
            * (edgeMax - edgeMin));

        int index1 = getIndexForAxisID(getYAxis());
          if (isClippedToPlotArea) {
            edge0 = pp.yToPixel(pp.getYMin(index1), index1);
            edge1 = pp.yToPixel(pp.getYMax(index1), index1);
          } else { // clipped to decorated chart
            edge0 = pp.yToPixel(GChart.this.getYAxis(index1)
                .pixelToModel(0), index1);
            edge1 = pp.yToPixel(GChart.this.getYAxis(index1)
                .pixelToModel(
                pp.getYChartSizeDecoratedQuickly()),
                index);
          }
        //}
        edgeMin = Math.min(edge0, edge1);
        edgeMax = Math.max(edge0, edge1);
        yPxMin = Math.max(yPxMin, edgeMin - renderPaddingFactor
            * (edgeMax - edgeMin));
        yPxMax = Math.min(yPxMax, edgeMax + renderPaddingFactor
            * (edgeMax - edgeMin));
      }

      if (xPxMax < xPxMin || yPxMax < yPxMin) {
        // order flipped ==> canvas rect outside clipping region
        result.x = result.y = result.width = result.height = 0;
      } else {
        result.x = xPxMin;
        result.y = yPxMin;
        result.width = xPxMax - xPxMin + 1;
        result.height = yPxMax - yPxMin + 1;
        if (result.width * result.height > maxCanvasPixels) {
          /*
           * Shrink canvas, then center it on the plot area midpoint
           * (XLarge canvases can use lots of RAM and/or not work).
           *
           * If such an approach would waste pixels (which happens
           * whenever the so-shrunken/centered rectangle isn't entirely
           * contained in the original rectangle) we translate the
           * shrunken rectangle the smallest amount needed to make it
           * fit entirely within the original rectangle.
           *
           * This approach isn't perfect, and will end up shrinking in
           * ways that occlude the more interesting points in special
           * cases, but it's a reasonable strategy that should work OK
           * for typical point sets, where most of the interesting
           * points are near the center of the plot area anyway.
           *
           */

          // shrink, center on plot area     
          double xMid = xChartSize / 2;
          double yMid = yChartSize / 2;
          double alpha = Math.sqrt(maxCanvasPixels
              / (result.width * result.height));
          double newWidth = alpha*result.width;
          double newHeight = alpha*result.height;
          double newX = xMid - newWidth / 2;
          double newY = yMid - newHeight / 2;

          // if needed, translate to fit within original rectangle
          if (newX < result.x)
            newX = result.x;
          else if (newX + newWidth > result.x + result.width)
            newX = result.x + result.width - newWidth;

          if (newY < result.y)
            newY = result.y;
          else if (newY + newHeight > result.y + result.height)
            newY = result.y + result.height - newHeight;

          result.x = newX;
          result.y = newY;
          result.width = newWidth;
          result.height = newHeight;
        }
      }
      // result is (roughly) smallest rectangle that contains every
      // rendered symbol on this curve (ignoring annotations)

      return result;

    }

    // keeps track of if last rendering was canvas-based or not
    private boolean wasCanvasRendered = false;

    void setWasCanvasRendered(boolean wasCanvasRendered) {
      this.wasCanvasRendered = wasCanvasRendered;
    }

    // is curve currently canvas rendered and up-to-date
    boolean isCanvasRendered() {
      return isValidated && wasCanvasRendered;
    }

  } // end of class GChart.Curve

  /*
   * Allows hovertext templates to be parsed into "chunks"
   * so that they can be expanded into hovertext faster.
   *
   */ 
  static class HovertextChunk {
    final static int HOVERTEXT_PARAM_NONE = 0; // plain old text
    final static int HOVERTEXT_PARAM_X = 1; // ${x}
    final static int HOVERTEXT_PARAM_Y = 2; // ${y}
    final static int HOVERTEXT_PARAM_PIESLICESIZE = 3; // ${pieSlicePercent}
    final static int HOVERTEXT_PARAM_USERDEFINED = 4; // ${mySpecialParameter}
    // id of substitution parameter
    int paramId; 
    // name of substitution parameter 
    String paramName; 
    // plain text that follows this parameter
    String chunkText;

    HovertextChunk(int id, String name, String text) {
      paramId = id;
      paramName = name;
      chunkText = text;
    }

    /*
     * Returns array of "chunks" corresponding to the given
     * hovertext template.
     *
     */ 
    static HovertextChunk[] parseHovertextTemplate(String htTemplate) {
      if (htTemplate.equals(""))
        return new HovertextChunk[0];
      /*
       * Takes "x=${x}; y=${y}" into {"x=", "x}; y=", "y}"} Thus, except
       * for the first, which contains just a string literal, chunks
       * contain a keyword like part (e.g. "x}"), followed by a string
       * literal (e.g. "; y=")
       *
       */ 
      String[] sChunk = htTemplate.split("\\$\\{");
      HovertextChunk[] result = new HovertextChunk[sChunk.length];

      for (int i = 0; i < sChunk.length; i++) {
        String sC = sChunk[i];
        if (0 == i)
          // leading (non-parametric) plain text chunk
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_NONE, null,
              sC);
        else if (sC.startsWith("x}"))
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_X, "x",
              sC.substring("x}".length()));
        else if (sC.startsWith("y}"))
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_Y, "y",
              sC.substring("y}".length()));
        else if (sC.startsWith("pieSliceSize}"))
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_PIESLICESIZE,
              "pieSliceSize",
              sC.substring("pieSliceSize}".length()));
        else if (sC.matches("[a-zA-Z][a-zA-Z0-9_]*\\}.*")) {
          // fits pattern for a user defined parameter
          int closeCurlyIndex = sC.indexOf("}");
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_USERDEFINED,
              sC.substring(0, closeCurlyIndex), 
              sC.substring(closeCurlyIndex + 1));
        } else {
          /*
           * Leading "${" without "paramName}". Likely a
           * typo, but output verbatim to give them a clue:
           * 
           */ 
          result[i] = new HovertextChunk(HOVERTEXT_PARAM_NONE, null,
              "${" + sC);
        }

      }
      return result;
    }

    /* hovertext associated with parsed "chunks" for a given point */
    static String getHovertext(HovertextChunk[] htc, Curve.Point p) {
      String result = "";
      String xS = null;
      String yS = null;
      String pieSlicePercentS = null;
      HoverParameterInterpreter hpi = p.getParent().getParent()
          .getHoverParameterInterpreter();
      for (int i = 0; i < htc.length; i++) {
        switch (htc[i].paramId) {
        case HovertextChunk.HOVERTEXT_PARAM_NONE:
          break;
        case HovertextChunk.HOVERTEXT_PARAM_X:
          if (null == xS) {
            String hoverParam = (null == hpi) ? null : 
                hpi.getHoverParameter(htc[i].paramName, p);
            if (null != hoverParam)
              xS = hoverParam;
            else {
              Axis axis = p.getParent().getParent().getXAxis();
              xS = axis.formatAsTickLabel(p.getX());
            }
          }
          result += xS;
          break;
        case HovertextChunk.HOVERTEXT_PARAM_Y:
          if (null == yS) {
            String hoverParam = (null == hpi) ? null : 
                hpi.getHoverParameter(htc[i].paramName, p);
            if (null != hoverParam)
              yS = hoverParam;
            else {
              Axis axis = p.getParent().getParent().getYAxis(p.getParent().getIndexForAxisID(p.getParent().getYAxis()));
            	  /*p.getParent().onY2() ? 
                  p.getParent().getParent().getY2Axis() : 
                  p.getParent().getParent().getYAxis();*/
              yS = axis.formatAsTickLabel(p.getY());
            }
          }
          result += yS;
          break;

        case HovertextChunk.HOVERTEXT_PARAM_PIESLICESIZE:
          if (null == pieSlicePercentS) {
            String hoverParam = (null == hpi) ? null : 
                hpi.getHoverParameter(htc[i].paramName, p);
            if (null != hoverParam)
              pieSlicePercentS = hoverParam;
            else {
              double pieSliceSize = 
                p.getParent().getSymbol().getPieSliceSize();
              Axis axis = p.getParent().getParent().getYAxis(p.getParent().getIndexForAxisID(p.getParent().getYAxis()));
            	  //p.getParent().getionY2() ? 
                //p.getParent().getParent().getY2Axis() : 
                //p.getParent().getParent().getYAxis();
              pieSlicePercentS = 
                axis.formatAsTickLabel(100 * pieSliceSize) + "%";
            }
          }
          result += pieSlicePercentS;
          break;

        case HovertextChunk.HOVERTEXT_PARAM_USERDEFINED:

          String hoverParam = (null == hpi) ? null : 
              hpi.getHoverParameter(htc[i].paramName, p);
          if (null == hoverParam)
            /*
             * null means "unrecognized parameter" - so
             * regenerate the original, unparsed, param spec
             * to clue them in that it was not processed.
             * 
             */ 
            result += "${" + htc[i].paramName + "}";
          else
            result += hoverParam;

          break;
        default:
          throw new IllegalStateException(
              "An illegal HOVERTEXT_PARAM_* id: "
              + htc[i].paramId
              + " was encountered. A GChart bug is likely to blame.");
        }
        result += htc[i].chunkText;
      }
      return result;
    }
  }

  /**
   * Defines a chart curve symbol. Each point on a curve is represented
   * on the chart by an appropriate rendering of the curve's symbol.
   * 
   * @see Curve#getSymbol Curve.getSymbol
   * @see SymbolType SymbolType
   * 
   */

  public class Symbol {

    private String backgroundColor = DEFAULT_SYMBOL_BACKGROUND_COLOR;
    // same as backgroundColor, except RGBA collapsed to plain RGA
    private String backgroundColorCSS = DEFAULT_SYMBOL_BACKGROUND_COLOR;
    private double baseline = Double.NaN;
    private String borderColor = "black";
    private String borderColorCSS = "black";
    private String borderStyle = DEFAULT_SYMBOL_BORDER_STYLE;
    private int borderWidth = DEFAULT_SYMBOL_BORDER_WIDTH;
    private int brushHeight = DEFAULT_BRUSH_HEIGHT;
    private AnnotationLocation brushLocation = AnnotationLocation.CENTER;
    private int brushWidth = DEFAULT_BRUSH_WIDTH;
    private boolean fillHasHovertext = true;
    private double fillSpacing = Double.NaN;
    private int fillThickness = GChart.NAI;
    private int height = DEFAULT_SYMBOL_HEIGHT;
    private String hovertextTemplate = null;
    /*
     * Holds specification for the hover annotation. Actual
     * hover annotation is generated on the fly when they hover.
     * 
     */ 
    private Annotation hoverAnnotation = null;
    private boolean hoverAnnotationEnabled = true;
    /*
     * Allows hover annotation to use a different symbol type
     * than the symbol being hovered over. Main use expected to
     * be to place hover feedback at a fixed location on the chart (via
     * ANCHOR_* family of symbol types), for example, a status
     * bar message that changes depending on what the mouse
     * is touching.
     * 
     */ 
    private SymbolType hoverAnnotationSymbolType = null;
    // encloses each symbol in a 1 px gray selection rectangle:
    private String hoverSelectionBackgroundColor = "transparent";
    private String hoverSelectionBorderColor = "gray";
    private String hoverSelectionBorderStyle = "solid";
    private int hoverSelectionBorderWidth = -1;
    private boolean hoverSelectionEnabled = true;
    private double hoverSelectionFillSpacing = Double.NaN;
    private int hoverSelectionFillThickness = GChart.NAI;
    private int hoverSelectionHeight = GChart.NAI;
    private String hoverSelectionImageURL = null;
    private int hoverSelectionWidth = GChart.NAI;
    private SymbolType hoverSelectionSymbolType = null;

    private HovertextChunk[] hovertextChunks = null;
    private String imageURL = null;
    /*
     * XXX: Ticks are now rendered via specialized system curves,
     * instead using a Symbol instantiated independently of curves.
     * So, Symbol could become an inner class of Curve, and this
     * explicit parent pointer would then no longer be required.
     *
     */ 
    private Curve parent = null;
    // when specified, model width/height are in user-defined units.
    private double modelHeight = Double.NaN;
    private double modelWidth = Double.NaN;
    /*
     * NaN means "begin this slice where last slice left off, or at
     * initialPieSliceOrientation if it is the first slice to be rendered"
     *
     */ 
    private double pieSliceOrientation = Double.NaN;
    private double defaultPieSliceOrientation = 0.0;
    // slices, by default, fill the entire pie (useful for drawing disks)
    private double pieSliceSize = 1;

    private SymbolType symbolType = DEFAULT_SYMBOL_TYPE;

    private int width = DEFAULT_SYMBOL_WIDTH;
    double xScaleFactor = 1.0;
    double yScaleFactor = 1.0;

    Symbol(Curve parent) {
      super();
      this.parent = parent;
    }

    /**
     * Returns the background or fill color of this symbol.
     * 
     * @return the background or fill color of this symbol.
     * 
     * @see #setBackgroundColor(String) setBackgroundColor
     */
    public String getBackgroundColor() {
      return backgroundColor;
    }

    String getBackgroundColorCSS() {
      return backgroundColorCSS;
    }

    /**
     * Returns the baseline value for this symbol, previously specified via
     * <tt>setBaseline</tt>
     * 
     * @return the previously specified baseline value for this symbol.
     * 
     * @see #setBaseline setBaseline
     */
    public double getBaseline() {
      return baseline;
    }

    /**
     * Returns the border color. 
     * 
     * <p>
     * 
     * @return the border color 
     * 
     * @see #setBorderColor setBorderColor
     * 
     */
    public String getBorderColor() {
      return borderColor;
    }

    String getBorderColorCSS() {
      return borderColorCSS;
    }

    /**
     * Returns the border style of all of the rectangular elements from which
     * this symbol is built.
     * <p>
     * 
     * @return the CSS borderStyle of this symbol's elements (dotted, dashed,
     *         solid, etc. )
     * 
     * @see #setBorderStyle setBorderStyle
     */
    public String getBorderStyle() {
      return borderStyle;
    }

    /**
     * Returns the width of the border.
     * <p>
     * 
     * @return the previously set border width (in pixels).
     * 
     * @see #setBorderWidth setBorderWidth
     */
    public int getBorderWidth() {
      return borderWidth;
    }

    /**
     * 
     * Returns the height of the rectangular "brush" that defines how close the
     * mouse cursor must be to a rendered symbol for the symbol to be considered
     * to have been "touched" (which causes the point's hover feedback to pop
     * up).
     * <p>
     * 
     * @return the height of the "brush", in pixels, associated with this
     *         symbol/curve.
     * 
     * @see #setBrushHeight setBrushHeight
     * 
     */
    public int getBrushHeight() {
      return brushHeight;
    }

    /**
     * 
     * Returns the location of the rectangular brush relative to the current x,y
     * coordinates of the mouse cursor.
     * <p>
     * 
     * @return the location of the rectangular brush relative to the x,y
     *         coordinates of the mouse cursor.
     * 
     * @see #setBrushLocation setBrushLocation
     */
    public AnnotationLocation getBrushLocation() {
      return brushLocation;
    }

    /**
     * 
     * Returns the width of the rectangular "brush" that defines how close the
     * mouse cursor must be to a rendered symbol for the symbol to be considered
     * to have been "touched" (which causes the point's hover feedback to pop
     * up).
     * <p>
     * 
     * @return the width of the "brush", in pixels, associated with this
     *         symbol/curve.
     * 
     * @see #setBrushWidth setBrushWidth
     * 
     */
    public int getBrushWidth() {
      return brushWidth;
    }

    /**
     * @deprecated
     * 
     *             Returns the value previously set by setFillHasHovertext.
     * 
     * @see #setFillHasHovertext setFillHasHovertext
     * 
     */
    public boolean getFillHasHovertext() {
      return fillHasHovertext;
    }

    /**
     * Returns the spacing between successive rectangular elements used to
     * emulate any required non-rectangular features of the symbol.
     * <p>
     * 
     * @return the previously set (or the default, if the fillSpacing has been
     *         set to <tt>Double.NaN</tt>) fill spacing (in pixels).
     * 
     * @see #setFillSpacing setFillSpacing
     * @see #setFillThickness setFillThickness
     * 
     */
    public double getFillSpacing() {
      if ((fillSpacing != fillSpacing)) // x!=x is a faster isNaN
        return symbolType.defaultFillSpacing();
      else
        return fillSpacing;
    }

    /**
     * Returns the "thickness" of rectangular elements used to emulate any
     * required non-rectangular features of the symbol.
     * <p>
     * 
     * @return the previously set (or the default, if the fillThickness has been
     *         set to <tt>GChart.NAI</tt>) fill thickness (in pixels).
     * 
     * @see #setFillThickness setFillThickness
     * @see #setFillSpacing setFillSpacing
     */
    public int getFillThickness() {
      if (fillThickness == GChart.NAI)
        return symbolType.defaultFillThickness();
      else
        return fillThickness;
    }

    /*
     * Retrieves the annotation that defines the properties of the internally
     * generated annotations used to display hover feedback.
     * 
     */
    Annotation getHoverAnnotation() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      return hoverAnnotation;
    }

    /**
     * Retrieves a boolean that indicates if point-specific annotations popup
     * whenever you hover over a point on the curve associated with this symbol.
     * <p>
     * 
     * @return true if hover-induced annotations popup, false otherwise.
     * 
     * @see #setHoverAnnotationEnabled setHoverAnnotationEnabled
     * 
     */
    public boolean getHoverAnnotationEnabled() {
      return hoverAnnotationEnabled;
    }

    /**
     * Retrieves the weight of the font that will be used with this symbol's
     * hover annotations.
     * <p>
     * 
     * @return the standard CSS font-weight specification such as normal, bold,
     *         bolder, lighter, 100, 200, ... 900, or inherit used by hover
     *         annotations
     * 
     * @see #setHoverFontWeight setHoverFontWeight
     * 
     */
    public String getHoverFontWeight() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      String result = hoverAnnotation.getFontWeight();
      return result;
    }

    /**
     * Retrieves the font color of this symbol's hover annotations.
     * <p>
     * 
     * @return color of the font used to display this symbol's hover annotations
     * 
     * @see #setHoverFontColor setHoverFontColor
     */
    public String getHoverFontColor() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      String result = hoverAnnotation.getFontColor();
      return result;
    }

    /**
     * Retrieves the CSS font-family used with this symbol's hover annotations.
     * 
     * @return the CSS font-family of text displayed in the hover annotations
     *         associated with this symbol.
     * 
     * @see #setHoverFontFamily setHoverFontFamily
     */
    public String getHoverFontFamily() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      String result = hoverAnnotation.getFontFamily();
      return result;
    }

    /**
     * Retrieves the CSS font-style used with this symbol's hover annotations.
     * 
     * @return the CSS font-style, namely, normal, italic, oblique, or inherit
     *         of text displayed in the hover annotations associated with this
     *         symbol
     * 
     * @see #setHoverFontStyle setHoverFontStyle
     */
    public String getHoverFontStyle() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      String result = hoverAnnotation.getFontStyle();
      return result;
    }

    /**
     * Retrieves the CSS font size used with this symbol's hover annotations, in
     * pixels.
     * 
     * @return the font size used in the text displayed in the hover annotations
     *         associated with this symbol.
     * 
     * @see #setHoverFontSize setHoverFontSize
     * 
     */
    public int getHoverFontSize() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      int result = hoverAnnotation.getFontSize();
      return result;
    }

    /**
     * Retrieves point-relative location of this symbol's hover annotations.
     * <p>
     * 
     * @return the relative location of the hover annotations for all points on
     *         the curve associated with this symbol.
     * 
     * @see #setHoverLocation setHoverLocation
     * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
     * 
     */
    public AnnotationLocation getHoverLocation() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      AnnotationLocation result = hoverAnnotation.getLocation();
      if (null == result)
        result = getSymbolType().defaultHoverLocation();
      return result;
    }

    /**
     * Retrieves the symbol type that will determine how the hover annotations
     * for this symbol gets positioned.
     * <p>
     * 
     * @return <tt>SymbolType</tt> used to position hover annotations, or
     *         <tt>null</tt> if the symbol type of the hovered over point is
     *         being used.
     * 
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * 
     */
    public SymbolType getHoverAnnotationSymbolType() {
      return hoverAnnotationSymbolType;
    }

    /**
     * Retrieves the background color used to indicate that the mouse is
     * "touching" (hovering over) a point.
     * 
     * @return a CSS color specification string that represents the background
     *         color used to indicate "hover-selection".
     * 
     * @see #setHoverSelectionBackgroundColor setHoverSelectionBackgroundColor
     */
    public String getHoverSelectionBackgroundColor() {
      return hoverSelectionBackgroundColor;
    }

    /**
     * Retrieves the border color used to indicate that the mouse is "touching"
     * (hovering over) a point.
     * 
     * @return a CSS color specification string that represents the border color
     *         used to indicate "hover-selection".
     * 
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     */
    public String getHoverSelectionBorderColor() {
      return hoverSelectionBorderColor;
    }

    /**
     * Retrieves the border style used to indicate that the mouse is "touching"
     * (hovering over) a point.
     * 
     * @return a CSS border style specification string that represents the
     *         border style used to indicate "hover-selection".
     * 
     * @see #setHoverSelectionBorderStyle setHoverSelectionBorderStyle
     */
    public String getHoverSelectionBorderStyle() {
      return hoverSelectionBorderStyle;
    }

    /**
     * Retrieves the width of the border drawn to indicate that the
     * mouse is "touching" (hovering over) a point.
     * 
     * @return the width of the border drawn around to indicate that it has been
     *         "touched: by the mouse.
     * 
     * @see #setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     */
    public int getHoverSelectionBorderWidth() {
      return hoverSelectionBorderWidth;
    }

    /**
     * Retrieves a boolean that indicates if hover selection feedback will be
     * provided for this curve.
     * <p>
     * 
     * @return if true, hover selection feedback is enabled, if false, hovering
     *         over a point does not change its color.
     * 
     * @see #setHoverSelectionEnabled setHoverSelectionEnabled
     * 
     */
    public boolean getHoverSelectionEnabled() {
      return hoverSelectionEnabled;
    }

    /**
     * Returns the fill spacing that will be used when rendering this curve's
     * hover selection feedback.
     * <p>
     * 
     * @return fill spacing used by hover selection feedback, or
     *         <tt>GChart.NAI</tt> if the fill spacing setting of the
     *         hovered-over curve is to be used.
     * 
     * @see #setHoverSelectionFillSpacing setHoverSelectionFillSpacing
     * 
     */
    public double getHoverSelectionFillSpacing() {
      return hoverSelectionFillSpacing;
    }

    /**
     * Returns the fill thickness that will be used when rendering this curve's
     * hover selection feedback.
     * <p>
     * 
     * @return fill thickness used by hover selection feedback, or
     *         <tt>GChart.NAI</tt> if the fill thickness setting of the
     *         hovered-over curve is to be used.
     * 
     * @see #setHoverSelectionFillThickness setHoverSelectionFillThickness
     * 
     */
    public int getHoverSelectionFillThickness() {
      return hoverSelectionFillThickness;
    }

    /**
     * Returns the height of the symbol used to indicate when a given point is
     * being "hovered over" with the mouse.
     * <p>
     * 
     * @return the height of the symbol used to indicate that that a point has
     *         been selected, or <tt>GChart.NAI</tt> if the the height of the
     *         symbol representing the selected point is being used.
     * 
     * 
     * @see #setHoverSelectionHeight setHoverSelectionHeight
     * 
     */
    public int getHoverSelectionHeight() {
      return hoverSelectionHeight;
    }

    /**
     * Returns the URL that will be used for all of the images used in rendering
     * this symbol's selection feedback.
     * 
     * @see #setHoverSelectionImageURL setHoverSelectionImageURL
     * 
     * @return the url that defines the <tt>src</tt> property of all images used
     *         to draw this the selection feedback associated with this symbol.
     * 
     */

    public String getHoverSelectionImageURL() {
      String result = (null == hoverSelectionImageURL) ? getBlankImageURL()
          : hoverSelectionImageURL;
      return result;
    }

    /**
     * 
     * Returns the symbol type that GChart will use when generating selection
     * feedback. GChart indicates that a point is selected by re-rendering the
     * point as if it had this symbol type.
     * 
     * @return the symbol type that in part determines how selection feedback
     *         for a hovered over point is drawn, or <tt>null</tt> if defaulting
     *         to the symbol type of the hovered over point.
     * 
     * @see #setHoverSelectionSymbolType setHoverSelectionSymbolType
     * 
     */
    public SymbolType getHoverSelectionSymbolType() {
      return hoverSelectionSymbolType;
    }

    /**
     * Returns the width of the symbol used to indicate when a given point is
     * being "hovered over" with the mouse.
     * 
     * @return the width of the symbol used to indicate that that a point has
     *         been selected, or <tt>GChart.NAI</tt> if using to the width of
     *         the symbol representing the selected point.
     * 
     * @see #setHoverSelectionWidth setHoverSelectionWidth
     * 
     * 
     */
    public int getHoverSelectionWidth() {
      return hoverSelectionWidth;
    }

    /**
     * Returns the hovertextTemplate of this symbol.
     * <p>
     * 
     * @return hovertextTemplate of the symbol
     * 
     * 
     * @see #setHovertextTemplate(String) setHovertextTemplate
     * 
     */
    public String getHovertextTemplate() {
      if (null == hovertextTemplate)
        return symbolType.defaultHovertextTemplate();
      else
        return hovertextTemplate;
    }

    /**
     * When widget-based hover annotations are being used by the curve
     * associated with this symbol, this method returns the
     * <tt>HoverUpdateable</tt> widget used within those annotations. When
     * simple text or HTML hover annotations are being used, it returns null.
     * 
     * @return the widget used to provide widget-based hover annotations or null
     *         if hover annotations are not widget-based.
     * 
     * @see #setHoverWidget setHoverWidget
     * 
     */
    public HoverUpdateable getHoverWidget() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      return (HoverUpdateable) hoverAnnotation.getWidget();
    }

    /**
     * Retrieves the number of pixels (along the x-axis) that this point's
     * hover-annotation will be moved from its default,
     * <tt>setHoverLocation</tt> defined, point-relative location.
     * <p>
     * 
     * @return x-shift, in pixels, of the hover annotation
     * 
     * @see #setHoverXShift getHoverXShift
     * 
     */
    public int getHoverXShift() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      int result = hoverAnnotation.getXShift();
      return result;
    }

    /**
     * Retrieves the number of pixels (along the y-axis) that this point's hover
     * annotation will be moved from its default, <tt>setHoverLocation</tt>
     * defined, point-relative location.
     * <p>
     * 
     * @return y-shift, in pixels, of the hover annotation
     * 
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public int getHoverYShift() {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      int result = hoverAnnotation.getYShift();
      return result;
    }

    /**
     * Returns the URL that will be used for all of the images used in rendering
     * this symbol.
     * <p>
     * 
     * @see #setImageURL setImageURL
     * @see #setBlankImageURL setBlankImageURL
     * 
     * @return the url that defines the <tt>src</tt> property of all images used
     *         to draw this symbol on the chart.
     */
    public String getImageURL() {
      String result = (null == imageURL) ? getBlankImageURL() : imageURL;
      return result;
    }

    // returns an internal, parsed form of the hovertext template
    HovertextChunk[] getHovertextChunks() {
      if (null == hovertextChunks)
        hovertextChunks = 
          HovertextChunk.parseHovertextTemplate(getHovertextTemplate());

      return hovertextChunks;
    }

    /**
     * Returns the <tt>Curve</tt> that contains this <tt>Symbol</tt>.
     * 
     * @return a reference to the <tt>Curve</tt> that contains this
     *         <tt>Symbol</tt> (its "parent")
     * 
     */
    public Curve getParent() {
      return parent;
    }

    /**
     * Returns the value, previously specified via
     * <tt>setPieSliceOrientation</tt>, that defines the angular orientation of
     * any pie slices associated with this symbol.
     * <p>
     * 
     * 
     * @return the value, either <tt>Double.NaN</tt> or a value between 0 and 1
     *         previously set via <tt>setPieSliceOrientation</tt>, that
     *         determines the angular orientation of any pie slice associated
     *         with this symbol.
     * 
     * @see #setPieSliceOrientation setPieSliceOrientation
     * @see #setPieSliceSize setPieSliceSize
     * 
     */
    public double getPieSliceOrientation() {
      return pieSliceOrientation;
    }

    /*
     * Used internally to translate <tt>Double.NaN</tt> into
     * an appropriate default slice orientation that, when pie
     * slice orientation isn't explicitly specified, results
     * in a series of adjacent slices that will form a pie
     * when the sum of the slice sizes equals 1.0
     * 
     */ 

    double getDecodedPieSliceOrientation() {
      double result = pieSliceOrientation;
      if ((result != result)) // x!=x is a faster isNaN
        result = defaultPieSliceOrientation;
      return result;
    }

    void setDefaultPieSliceOrientation(double defaultOrientation) {
      defaultPieSliceOrientation = defaultOrientation;
    }

    double getDefaultPieSliceOrientation() {
      return defaultPieSliceOrientation;
    }

    /**
     * Returns the value, previously specified via <tt>setPieSliceSize</tt>,
     * that defines the size of the angle subtended by any pie slice associated
     * with this symbol.
     * <p>
     * 
     * @return the value, between 0 and 1 and previously set via
     *         <tt>setPieSliceSize</tt>, that defines the size of the
     *         "wedge of pie" as a fraction of the total pie, for any pie slice
     *         associated with this symbol.
     * 
     * @see #setPieSliceOrientation setPieSliceOrientation
     * @see #setPieSliceSize setPieSliceSize
     * 
     */
    public double getPieSliceSize() {
      return pieSliceSize;
    }

    /*
     * Returns the radius of the pie from which this symbol's pie slice was
     * extracted.
     */
    double getPieSliceRadius(PlotPanel pp, int index) {
      double w = getWidth(pp); // needed to decode model
      double h = getHeight(pp, index);// width,height into pixels
      double result = Math.sqrt(w * w + h * h) / 2.;
      /*
       * Tweak radius to assure it is an even multiple of the fill
       * spacing. Makes it possible to assure regular band spacing
       * across pie at the expense of less precise control of pie
       * size (regular band spacing makes it look much better).
       *
       */ 
      double spacing = getFillSpacing();
      if (0 == spacing)
        spacing = 1;
      int nBands = (int) Math.round(result / spacing);
      result = nBands * spacing;
      return result;
    }

    // defines first, second edge angle in standard radian units
    double getPieSliceTheta0() {
      double result;
      result = (0.75 - getDecodedPieSliceOrientation()) * 2 * Math.PI;
      return result;
    }

    double getPieSliceTheta1() {
      return getPieSliceTheta0() - 2. * Math.PI * getPieSliceSize();
    }

    /**
     * Returns this symbol's height, as previously set by <tt>setHeight</tt> .
     * 
     * @return the previously set symbol height, in pixels.
     * 
     * @see #setHeight setHeight
     */
    public int getHeight() {
      return height;
    }

    /**
     * Returns this symbol's height as previously set by
     * <tt>setModelHeight</tt>.
     * 
     * @return the previously set symbol height, in model units
     * 
     * @see #setModelHeight setModelHeight
     * @see #setModelWidth setWidth
     * @see #setHeight setHeight
     * @see #setWidth setWidth
     * 
     */
    public double getModelHeight() {
      return modelHeight;
    }

    /**
     * Returns this symbol's width as previously set by <tt>setModelWidth</tt>.
     * 
     * @return the previously set symbol width, in model units.
     * 
     * @see #setModelWidth setModelWidth
     * @see #setModelHeight setModelHeight
     * @see #setWidth setWidth
     * @see #setHeight setHeight
     * 
     */
    public double getModelWidth() {
      return modelWidth;
    }

    /**
     * Returns this symbol's type.
     * 
     * @return the type of this symbol.
     * 
     * @see #setSymbolType setSymbolType
     * 
     */
    public SymbolType getSymbolType() {
      return symbolType;
    }

    /**
     * Returns this symbol's width as previously set by <tt>setWidth</tt>.
     * <p>
     * 
     * <i>Warning:</i> This method won't return the correct pixel width
     * associated with a <tt>setModelWidth</tt> setting, as you might have
     * expected. It only returns the pixel width you last explicitly specified
     * via <tt>setWidth</tt>.
     * <p>
     * 
     * @return the previously set symbol width, in pixels
     * 
     * @see #setWidth setWidth
     * @see #setModelWidth setModelWidth
     */
    public int getWidth() {
      return width;
    }

    /*
     * Do points on the curve associated with this symbol use a
     * horizontal (or vertical) binning strategy for "what point is the
     * mouse over" hit testing?
     * 
     */
    boolean isHorizontallyBanded() {
      boolean result;
      if (null == symbolType.isHorizontallyBanded)
        /*
         * Not fixed by symbol type: use brush shape determined banding
         * (we are guessing point distribution based on brush shape)
         *
         */ 
        result = brushHeight < brushWidth;
      else
        result = symbolType.isHorizontallyBanded.booleanValue();

      return result;
    }

    /*
     * If passed an rgba-like string (rgba(255,255,128,0.5)) returns the
     * collapsed-to-rgb version (rgb(255,255,128)). Else returns the
     * original string. Throws an exception if string begins with rgba(
     * but lacks required format after that.
     * 
     */
    private String collapseRGBAToRGB(String rgba) {
      // an int in the range 0..255 for the "R,G,B" parts
      final String RGB = "([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))";
      // a double in the range 0..1 for the "A" part
      final String A = "(0|1|(1[.]0*)|(0[.][0-9]*)|([.][0-9]+))";
      // full RGBA pattern
      final String RGBA_PATTERN = "rgba[(]" + RGB + "[,]" + RGB + "[,]"
          + RGB + "[,]" + A + "[)]";
      String result = rgba;
      if (null != rgba && rgba.startsWith("rgba(")) {
        if (rgba.matches(RGBA_PATTERN)) {
          final int FIRST_PAREN = 4;
          int lastComma = rgba.lastIndexOf(",");
          result = "rgb" + rgba.substring(FIRST_PAREN, lastComma)
              + ")";
        } else
          throw new IllegalArgumentException(
              "Your RGBA color specification: '"
                  + rgba
                  + "'"
                  + " was not in the GChart-required form: rgba(Red,Green,Blue,Alpha)"
                  + " where Red, Green and Blue are integers in the range 0 to 255 and"
                  + " Alpha is a double in the range 0.0 to 1.0");
      }
      // else special keyword or else some (unchecked) CSS color format
      return result;
    }

    /**
     * Specifies the background or fill color of this symbol.
     * <p>
     * 
     * For example, this would define the color of the interior, non-border,
     * part of bars in a bar-chart, the color of the interior of each shading
     * bar in a banded-fill pie slice, or the canvas "fill" color of solid fill,
     * canvas-rendered, pie slices.
     * <p>
     * 
     * You can use one of the 16 standard HTML/CSS color literals, illustrated
     * below, to quickly specify common colors:
     * 
     * <p>
     * <ul>
     * <img src="{@docRoot}/com/googlecode/gchart/client/doc-files/gchartexample10.png">
     * </ul>
     * 
     * <p>
     * For more variety, use a standard CSS RGB (<b>r</b>ed, <b>g</b>reen, and
     * <b>b</b>lue) color format such as "#FF0000" (same as "red"), "#00FF00"
     * (same as "green"), "#0000FF" (same as "blue"), "#FFFFFF" (same as
     * "white") or "#000000" (same as "black").
     * <p>
     * 
     * If you are using an external canvas library that supports them, feel free
     * to use RGBA color specifications (e.g, <tt>rgba(255,255,255,0.5))</tt>
     * for semi-transparent white). GChart will automatically collapse these
     * specifications down to CSS standard RGB (e.g. <tt>rgb(255,255,255)</tt>)
     * as needed for any non-canvas-rendered parts of the curve.
     * <p>
     * 
     * <small> To maintain <tt>GWTCanvas</tt> consistency, GChart requires that
     * you use integers in the range <tt>0..255</tt> in the first three comma
     * delimited RGBA slots, and a double in the range <tt>0..1</tt> in the
     * fourth. A runtime exception will be raised if you use any other format.
     * <p>
     * </small>
     * 
     * <i>Why GChart sometimes deliberately drops your alpha-transparency specs
     * (and how to workaround this limitation):</i>
     * 
     * <p>
     * <blockquote> <small>
     * <p>
     * 
     * The external <tt>GWTCanvas</tt> vector graphics library lets you use the
     * RGBA format, which adds a fourth, <tt><b>a</b>lpha</tt> parameter in
     * addition to the standard <tt><b>r</b>ed</tt>, <tt><b>g</b>reen</tt> and
     * <tt><b>b</b>lue</tt>. This <tt>alpha</tt> lets you define the degree of
     * transparency: from 0 (transparent) to 1 (opaque).
     * <p>
     * 
     * Problem is, if you use this <tt>rgba(r,g,b,a)</tt> syntax to specify,
     * say, the color of an HTML element's border, IE won't display anything but
     * an error. So, whenever GChart uses an HTML element to render a symbol's
     * background or border (instead of your external vector graphics library)
     * it collapses the RGBA into the more widely accepted RGB format, by
     * dropping the fourth, <tt>alpha</tt>, parameter and changing the leading
     * <tt>rgba</tt> identifier to <tt>rgb</tt>.
     * <p>
     * 
     * GChart will render any "continuously filled" (you can use
     * <tt>setFillSpacing(0)</tt> to request continuous filling) aspects of your
     * symbols using whatever external vector graphics library you specified via
     * <tt>setCanvasFactory</tt>. So, you can rely on the full RGBA syntax
     * working for these aspects of your curve, provided that, like
     * <tt>GWTCanvas</tt>, your external graphics library supports the
     * <tt>rgba(r,g,b,a)</tt> syntax. Specifically, solid fill pie slices and
     * their borders, solid fill area charts and their borders, and solid
     * point-to-point connecting lines on a line chart all support
     * RGBA-specified alpha-transparency.
     * <p>
     * 
     * On the other hand, due to a limitation of GChart's implementation, any
     * rectangular aspects of your symbols, namely bar chart symbols and their
     * borders, rectangular point markers and their borders, and banded-filled
     * pie slices, are rendered via HTML and will collapse your
     * <tt>rgba(r,g,b,a)</tt> specs into <tt>rgb(r,g,b)</tt>.
     * <p>
     * 
     * But what if you need a semi-transparent bar chart, banded-fill pie slice,
     * or rectangular point marker? Fortunately, there is a simple workaround:
     * just pass the URL of an appropriately-sized semi-transparent image to the
     * <tt>setImageURL</tt> method of the curve in question.
     * 
     * </small> </blockquote>
     * <p>
     * 
     * The default symbol background color is
     * <tt>DEFAULT_SYMBOL_BACKGROUND_COLOR</tt>
     * 
     * 
     * @param backgroundColor
     *          a standard CSS or canvas-library supported RGBA background color
     *          specification string.
     * 
     * 
     * @see #getBackgroundColor getBackgroundColor
     * @see #setBorderColor setBorderColor
     * @see #DEFAULT_SYMBOL_BACKGROUND_COLOR DEFAULT_SYMBOL_BACKGROUND_COLOR
     * @see #setImageURL setImageURL
     * 
     */
    public void setBackgroundColor(String backgroundColor) {
      getParent().invalidate();
      this.backgroundColor = backgroundColor;
      // don't want to keep collapsing whenever we render, so do it once, now.
      backgroundColorCSS = collapseRGBAToRGB(backgroundColor);
    }

    /**
     * Specifies the baseline value for this symbol. Use a baseline value when
     * you need to create bar charts whose bars extend up/down to a specified y
     * baseline value (for vertical bar charts) or left/right to a specified x
     * baseline value (for horizontal bar charts).
     * <p>
     * 
     * In greater detail:
     * <p>
     * <ul>
     * 
     * <li>For curves that employ symbol types with names of the form
     * <tt>VBAR_BASELINE_*</tt>, a vertical bar is drawn that connects the x,y
     * position of each data point to the horizontal line defined by the
     * equation <tt>y=baseline</tt>. For the default baseline setting of
     * <tt>Double.NaN</tt>, the defining equation is <tt>y=(yMin+yMax)/2</tt>
     * (i.e., a midpoint baseline).
     * <p>
     * 
     * <li>For curves that employ symbol types with names of the form
     * <tt>HBAR_BASELINE_*</tt>, a horizontal bar is drawn from the x,y position
     * associated with each data point to the vertical line defined by the
     * equation <tt>x=baseline</tt>. For the default baseline setting of
     * <tt>Double.NaN</tt>, the defining equation is <tt>x=(xMin+xMax)/2</tt>.
     * </ul>
     * <p>
     * 
     * @param baseline
     *          the y (or x) that defines the horizontal (or vertical) line to
     *          which any baseline-based vertical (or horizontal) bars are
     *          extended.
     * 
     * @see #getBaseline getBaseline
     * @see SymbolType#HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see SymbolType#HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see SymbolType#HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see SymbolType#VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see SymbolType#VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see SymbolType#VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * 
     */
    public void setBaseline(double baseline) {
      getParent().invalidate();
      this.baseline = baseline;
    }

    /**
     * Specifies the border color, as a CSS or RGBA color specification string.
     * 
     * <p>
     * 
     * Both standard CSS and the all-but-IE-supported RGBA extension (with the
     * fourth parameter defining alpha-transparency, for example,
     * <tt>rgba(255,255,255,0.5)</tt> to define a semi-transparent white) are
     * allowed. However, GChart may collapse these extended specifications into
     * ordinary RGB in some cases. See {@link Symbol#setBackgroundColor
     * setBackgroundColor} for full details.
     * <p>
     * 
     * For example, for a square symbol, this would set the color of the line
     * that indicates the outter perimeter of that square. For a banded-fill pie
     * slice, this would set the color of the outter perimeter of every shading
     * bar used to fill in the pie slice. For a solid fill pie slice, with an
     * external canvas library such as <tt>GWTCanvas</tt> plugged into GChart
     * via <tt>setCanvasFactory</tt>, this method would instead set the color of
     * the canvas-stroked line around the slice's perimeter.
     * <p>
     * 
     * This color also defines the color of the point-to-point connecting lines
     * of a continuously connected line chart.
     * <p>
     * 
     * <i>Using <tt>TRANSPARENT_BORDER_COLOR</tt>:</i>
     * <p>
     * 
     * <blockquote> <small>
     * Although you can use the special CSS keyword "transparent", due to a
     * well-known bug, IE6 will usually render that as black. Plus, different
     * browsers define the background color that shines through the transparent
     * border differently, and external canvas libraries such as
     * <tt>GWTCanvas</tt> may not support the use of "transparent". So, GChart
     * provides a browser-independent keyword <tt>TRANSPARENT_BORDER_COLOR</tt>
     * that you can use instead, that emulates transparency by removing the
     * border entirely, and then shifting and resizing the transparently
     * bordered element so as to create the illusion that its transparent border
     * is still there. That's not exactly the same thing as having a transparent
     * border (the emulated borders can't "see" mouse moves over them, for
     * example) but it's close enough for most charting purposes. Another simple
     * way to workaround these transparent border inconsistencies is to use an
     * explicitly specified border color equal to the chart's background color.
     * </small> </blockquote>
     * 
     * @param borderColor
     *          the color of the borders of this curve's rendered symbols, and
     *          of any point-to-point connecting lines. Use any valid CSS color
     *          specification string (including the RGBA extension), or the
     *          special GChart keyword <tt>TRANSPARENT_BORDER_COLOR</tt>.
     * 
     *          For more information on standard CSS color specifications
     *          including how GChart handles the RGBA extended format, see
     *          {@link Symbol#setBackgroundColor setBackgroundColor}.
     * 
     * @see #TRANSPARENT_BORDER_COLOR TRANSPARENT_BORDER_COLOR
     * @see #getBorderColor getBorderColor
     * @see #setBackgroundColor setBackgroundColor
     * @see #setCanvasFactory setCanvasFactory
     * 
     */
    public void setBorderColor(String borderColor) {
      getParent().invalidate();
      this.borderColor = borderColor;
      borderColorCSS = collapseRGBAToRGB(borderColor);
    }

    /**
     * Sets the border style of the rectangular elements used to render this
     * symbol.
     * 
     * @param borderStyle
     *          a CSS border style such as "solid", "dotted", "dashed", etc.
     * 
     * @see #getBorderStyle getBorderStyle
     * @see #setBackgroundColor setBackgroundColor
     * @see #setBorderColor setBorderColor
     */
    public void setBorderStyle(String borderStyle) {
      getParent().invalidate();
      this.borderStyle = borderStyle;
    }

    /**
     * Sets the width of the border around the graphical element(s) used to
     * render this curve, in pixels.
     * <p>
     * 
     * If positive, the border is drawn inside each graphical element. If
     * negative, the border is drawn outside of those elements. Note that,
     * negative, external, borders do not increase the mouse hit-testing region
     * associated with the rendered symbols.
     * <p>
     * 
     * <blockquote><small> <i>Tip:</i> To get the hit test region around a
     * symbol to, <i>in effect</i>, include the external border associated with
     * a negative border width, simply increase the width and the height of the
     * symbol's point selection brush by <tt>2*Math.abs(getBorderWidth())</tt>.
     * </small> </blockquote>
     * <p>
     * 
     * If a rectangular symbol's width or height ever become less than twice the
     * specified positive border width, the border will be shrunk down until it
     * just fills up the entire rectangular area of the symbol.
     * <p>
     * 
     * <i>On backgrounds vs borders in bar charts when printing:</i>
     * <p>
     * 
     * <blockquote><small> Because borders are usually printed more reliably
     * than background colors cross-browser, using such oversized borders is a
     * simple way to assure that on-screen and printed bar chart renderings are
     * consistent. Consistency isn't everything: the removal of background
     * colors can save ink. So, you might prefer to just design bar charts that
     * look good with either solid-filled (on-screen) or outlined (printed w.
     * 'print background colors' unchecked in FF, for example) bars.
     * </small></blockquote>
     * 
     * @param borderWidth
     *          the width of the symbol's border, in pixels
     *          
     * @see #getBorderWidth getBorderWidth
     */
    public void setBorderWidth(int borderWidth) {
      getParent().invalidate();
      this.borderWidth = borderWidth;
    }

    /**
     * 
     * Sets the height of the rectangular point-selection "brush". This brush
     * defines how close the mouse must get to a point on the chart in order to
     * "touch" it.
     * <p>
     * 
     * Whenever a point is touched, GChart displays that point's hover feedback.
     * By default, the selected point is highlighted, the associated symbol's
     * hovertext template is expanded relative to the touched point, and the
     * resulting plain text or HTML is placed onto the chart at an appropriate,
     * point-relative, location. You can override this default by passing a
     * <tt>HoverUpdateable</tt> <tt>Widget</tt> to the
     * <tt>setHoverWidget</tt> method. In that case, instead of expanding your
     * hovertext template, the <tt>HoverUpdateable</tt> <tt>Widget</tt> is
     * instead positioned properly relative to the touched point, and its
     * <tt>hoverUpdate</tt> method is invoked with a reference to the touched
     * point passed in as its first argument.
     * <p>
     * 
     * The rules for determining if the brush is "touching" a symbol are as
     * follows:
     * 
     * <ol>
     * 
     * <li>For all symbols <i>except</i> pie slices, the symbol is considered to
     * have been touched if the rectangle containing the rendered symbol, and a
     * rectangle with the specified brush width and height, and centered on the
     * current mouse position, intersect.
     * <p>
     * 
     * 
     * <small><i>Note:</i> The brush is centered on the mouse by default. You
     * can place the brush above, below, etc. the mouse position via the
     * <tt>setBrushLocation</tt> method. </small>
     * <p>
     * 
     * For example, with a 10 x 10 pixel brush, a bar chart's bar gets "touched"
     * whenever the mouse is either within, or within a 5 pixel border around,
     * the bar. Note that on line charts, only the individual (rectangular)
     * point markers, not connecting lines between them, are touchable.
     * <p>
     * 
     * <li>For pie slices, the slice is considered to have been touched if the
     * mouse cursor is within the angle subtended by the slice, and within
     * <tt>sliceRadius + max(brushWidth,
     *   brushHeight)/2.0</tt> of the center of the pie containing the slice.
     * Intuitively, the brush in effect aligns its longest dimension,
     * compass-like, so that it points to the center of the pie containing the
     * slice.
     * <p>
     * 
     * <small><i>Note:</i> Unlike other symbol types, pie slice hit testing
     * works the same regardless of the brush location (c.f.
     * <tt>setBrushLocation</tt>) setting. </small>
     * </ol>
     * <p>
     * 
     * Even though each curve's symbol can have an independently sized
     * brush--which gives you a lot of control over which curve's points are
     * easiest to select--using the same sized brush for all curves produces
     * results more consistent with the simple concept of a single physical
     * brush "touching" symbols on the chart.
     * <p>
     * 
     * Finally, if the brush touches more than one symbol, the symbol whose
     * center is closest to the mouse cursor (by default, that's also the brush
     * center point) is considered to have been touched. (In these calculations,
     * the ordinary, Euclidean, definition of distance is used by default; the
     * <tt>setDistanceMetric</tt> method allows for other definitions). For pie
     * slices, the symbol's center point is defined (to simplify the
     * calculations) as the point at the center of the pie that contains the
     * slice. In case of any remaining ties, the point later on the point list
     * (the one "on top") is selected.
     * <p>
     * 
     * <small> <i>Fine-print:</i> Known brush size limitations when selecting
     * off-chart points:
     * <p>
     * 
     * <blockquote> GChart allows you to render points that fall outside of the
     * GChart's containing rectangle.
     * <p>
     * 
     * But, GChart can't see mouse moves around such off-chart elements--the
     * mouse has to be right on top of these elements before it's motion is
     * detected by GChart (the browser sends mouse moves in these regions to
     * whatever non-GChart related browser elements occupy this space).
     * <p>
     * 
     * Consequently, even though GChart still uses your brush settings for hit
     * testing with such points, because it can't see mouse activity until the
     * center of the mouse "touches" such elements, it will often act <i>as
     * if</i> you were using a <tt>1x1</tt> pixel brush for such externally
     * rendered points.
     * <p>
     * 
     * Unfortunately, this is a basic limitation of how GChart handles mouse
     * events. But there are a number of possible workarounds, such as adding a
     * transparent curve with similar, but larger, dimensions/point locations to
     * the off-chart curve's points (so as to capture external mouse moves
     * around external points) or adding a transparent annotation around
     * external points (again, to create a mouse capture region around these
     * points). Perhaps the easiest approach is to simply make the outter
     * decoration regions around the chart big enough (via methods such as
     * <tt>setAxisLabelThickness</tt> and similarly named <tt>set*Thickness</tt>
     * methods) so that there is enough empty space around the plot area's
     * perimeter so that points never fall outside the chart's bounding
     * rectangle.
     * <p>
     * 
     * Because most applications place their points on the chart, this is only
     * an issue for advanced applications that deliberately draw outside of the
     * chart's rectangle so as to overlay the chart onto the host page.
     * </blockquote></small>
     * 
     * @param height
     *          the height of the rectangular point selection brush used by
     *          points on the curve associated with this symbol (in pixels).
     * 
     * @see #getBrushHeight getBrushHeight
     * @see #setBrushWidth setBrushWidth
     * @see #setBrushSize setBrushSize
     * @see #setBrushLocation setBrushLocation
     * @see #setDistanceMetric setDistanceMetric
     * @see Symbol#setHoverWidget setHoverWidget
     * @see HoverUpdateable HoverUpdateable
     * @see #DEFAULT_BRUSH_WIDTH DEFAULT_BRUSH_WIDTH
     * @see #DEFAULT_BRUSH_HEIGHT DEFAULT_BRUSH_HEIGHT
     * @see #getTouchedPoint getTouchedPoint
     * @see #touch touch
     * @see GChart#setHoverTouchingEnabled setHoverTouchingEnabled
     * 
     */
    public void setBrushHeight(int height) {
      brushHeight = height;
    }

    /**
     * Sets the location of the brush relative to the mouse x,y coordinates.
     * <p>
     * 
     * With the default setting of <tt>AnnotationLocation.CENTER</tt>, the brush
     * is centered on the mouse cursor, which is usually acceptable.
     * <p>
     * 
     * The most useful non-default settings, which facilitate "single sided"
     * point selection, are tabulated below:
     * 
     * <table border>
     * <tr>
     * <th>Location</th>
     * <th>Impact on point selection</th>
     * </tr>
     * <tr>
     * <td><tt>AnnotationLocation.NORTH</tt></td>
     * <td>The mouse only selects points when it is on or below them.</td>
     * </tr>
     * <tr>
     * <td><tt>AnnotationLocation.SOUTH</tt></td>
     * <td>The mouse only selects points when it is on or above them.</td>
     * </tr>
     * <tr>
     * <td><tt>AnnotationLocation.WEST</tt></td>
     * <td>The mouse only selects points when it is on them or to their right.</td>
     * </tr>
     * <tr>
     * <td><tt>AnnotationLocation.EAST</tt></td>
     * <td>The mouse only selects points when it is on them or to their left.</td>
     * </tr>
     * 
     * </table>
     * <p>
     * 
     * <i>Tip:</i> When a chart has two curves, setting one curve to use
     * <tt>NORTH</tt> as its brush location, and the other to use <tt>SOUTH</tt>
     * , and using a brush height equal to the height of the decorated chart
     * (returned via <tt>getYChartSizeDecorated()</tt>) allows points on one
     * curve to be selected when the user is near the top of the chart, and
     * points on the other curve to be selected when the user is near the bottom
     * of the chart.
     * <p>
     * 
     * @see #setBrushHeight setBrushHeight
     * @see #setBrushWidth setBrushWidth
     * @see GChart#getYChartSizeDecorated getYChartSizeDecorated
     * 
     * @param location
     *          the location of the rectangular brush, relative to the x,y
     *          position of the mouse.
     * 
     */
    public void setBrushLocation(AnnotationLocation location) {
      brushLocation = location;
    }

    /**
     * 
     * Convenience method equivalent to:
     * <p>
     * 
     * <pre>
     * setBrushWidth(width);
     * setBrushHeight(height);
     * </pre>
     * <p>
     * 
     * For a full discussion of how GChart uses it's "brush" to determine when
     * hover feedback for a point gets displayed, see <tt>setBrushHeight</tt>.
     * <p>
     * 
     * @param width
     *          the width of this chart's brush, in pixels
     * @param height
     *          the height of this chart's brush, in pixels
     * 
     * @see #setBrushHeight setBrushHeight
     * @see #setBrushWidth setBrushWidth
     * @see #DEFAULT_BRUSH_WIDTH DEFAULT_BRUSH_WIDTH
     * @see #DEFAULT_BRUSH_HEIGHT DEFAULT_BRUSH_HEIGHT
     * 
     */
    public void setBrushSize(int width, int height) {
      setBrushWidth(width);
      setBrushHeight(height);
    }

    /**
     * Sets the width of the rectangular "brush" that defines how close the
     * mouse position must be to a rendered symbol for that symbol to have been
     * "touched".
     * <p>
     * 
     * For a full discussion of how GChart uses it's "brush" to determine when
     * hover feedback for a point gets displayed, see <tt>setBrushHeight</tt>.
     * 
     * @param width
     *          width of the point selection brush, in pixels.
     * 
     * @see #setBrushHeight setBrushHeight
     * @see #setBrushSize setBrushSize
     * @see #DEFAULT_BRUSH_WIDTH DEFAULT_BRUSH_WIDTH
     * @see #DEFAULT_BRUSH_HEIGHT DEFAULT_BRUSH_HEIGHT
     * 
     */
    public void setBrushWidth(int width) {
      brushWidth = width;
    }

    /**
     * Allows you to change the x,y scale factors that define the distance
     * between the mouse cursor and each rendered point; these distances
     * determine which point is "closest" to the mouse during hit testing.
     * <p>
     * 
     * Whenever the mouse selection brush "touches" more than one point, the
     * point whose center is closest to the mouse cursor is the one selected.
     * For a point centered at (all coordinates are in pixels)
     * <tt>(xCenter, yCenter)</tt> the distance to the mouse cursor at
     * <tt>(xMouse, yMouse)</tt> is given by:
     * <p>
     * 
     * <pre>
     * dx = xScaleFactor * (xCenter - xMouse);
     * dy = yScaleFactor * (yCenter - yMouse);
     * distance = Math.sqrt(dx * dx + dy * dy);
     * </pre>
     * <p>
     * 
     * In the above, <tt>(xCenter, yCenter)</tt> is the position at the center
     * of the rectangle associated with the rendered point. For pie slices, it
     * is the position at the center of the pie containing the slice.
     * <p>
     * 
     * <i>Tip:</i> Here are the most commonly used x-y scale factors, and how
     * they are typically used:
     * <p>
     * 
     * <ol>
     * <li>To select points based on the ordinary (Euclidean) distance use
     * <tt>xScaleFactor = 1, yScaleFactor = 1</tt> (this is the default).
     * <p>
     * 
     * <li>To select points based only on how close the mouse x-coordinate is to
     * the x-coordinate at the point's center (often a good choice for a time
     * series chart) use <tt>xScaleFactor=1, yScaleFactor=0</tt>
     * <p>
     * 
     * <li>To select points based only on how close the mouse y-coordinate is to
     * the y-coordinate at the point's center (a good choice for a horizontally
     * oriented bar chart) use <tt>xScaleFactor=0, yScaleFactor=1</tt>
     * <p>
     * 
     * <li>To ignore the distance to the point, and instead always
     * select, from among those points touching the brush, the point
     * rendered last, use <tt>xScaleFactor=0, yScaleFactor=0</tt>. This
     * choice guarantees that points that are completely occluded by
     * other points can never get selected. But, because distances are
     * ignored, this choice can lead to points further from the mouse
     * being selected in preference to points closer to the mouse. You
     * can minimize this effect by using a very small brush size.
     * </ol>
     * <p>
     * 
     * <i>Warning:</i> Mixed metrics, like mixed metaphors, can be confusing:
     * <p>
     * 
     * <blockquote>
     * Since each curve gets it's own distance metric, it's possible to
     * preferentially select one curve over another by giving it relatively
     * smaller scale factors. This can produce very counter-intuitive selection
     * behaviors (selecting a point that is farther away from the mouse than
     * another point, for example). Generally, <b><i>it's best to use the same
     * distance metric for all curves</i></b> except in special cases.
     * <p>
     * 
     * For example, suppose you had a vertical bar chart on curve 0 that was
     * restricted to the top half of your chart, and a horizontal bar chart on
     * curve 1 restricted to the bottom half. Then you might use
     * <tt>xScaleFactor=1, yScaleFactor=0</tt> for curve 0 and
     * <tt>xScaleFactor=0, yScaleFactor=1</tt> for curve 1. This "mixed metric"
     * would not create confusion, because the user would view the
     * region-specific selection behaviour as sensibly coordinated with the
     * orientation of the bars in each region.
     * </blockquote>
     * <p>
     * 
     * Though using a relatively larger <tt>xScaleFactor</tt>,
     * <tt>yScaleFactor</tt> makes a curve's points relatively harder to select
     * during hit testing, to completely ignore a curve's points during hit
     * testing, you need to use <tt>setHoverSelectionEnabled(false)</tt> and
     * <tt>setHoverAnnotationEnabled(false)</tt>.
     * 
     * @param xScaleFactor
     *          multiplies the x-pixel distance between the mouse cursor and the
     *          point center (see distance formula above).
     * @param yScaleFactor
     *          multiplies the y-pixel distance between the mouse cursor and the
     *          point center (see distance formula above).
     * 
     * @see #setBrushSize setBrushSize
     * @see #setBrushLocation setBrushLocation
     * @see #setHoverSelectionEnabled setHoverSelectionEnabled
     * @see #setHoverAnnotationEnabled setHoverAnnotationEnabled
     * 
     */
    public void setDistanceMetric(double xScaleFactor, double yScaleFactor) {
      this.xScaleFactor = xScaleFactor;
      this.yScaleFactor = yScaleFactor;
    }

    /**
     * @deprecated
     *             This method has no impact on the rendered chart; it
     *             is retained for compatibility purposes only.
     *             <p>
     *             
     *             As of GChart 2.4, hover feedback has been completely
     *             redesigned. Though these changes are mostly positive, one
     *             downside is that, to simplify its hit-testing algorithms,
     *             GChart only provides hover feedback for the explicitly
     *             specified data points on a line chart; it can no longer
     *             provide feedback for the "filled in" points connecting
     *             successive data points. If you need hover feedback on such
     *             interpolated points you will have to explicitly add
     *             individual data points to the curve representing the
     *             interpolated values.
     *             <p>
     * 
     *             Another difference is that you can no longer turn off hover
     *             feedback for a pie slice via this method. If you need to turn
     *             hover feedback off for a pie slice (or for any other symbol,
     *             for that matter) you can use the (new with 2.4)
     *             <tt>setHoverAnnotationEnabled</tt> and
     *             <tt>setHoverSelectionEnabled</tt> methods.
     * 
     * 
     * @see #getFillHasHovertext getFillHasHovertext
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverAnnotationEnabled setHoverAnnotationEnabled
     * @see #setHoverSelectionEnabled setHoverSelectionEnabled
     * 
     */
    public void setFillHasHovertext(boolean fillHasHovertext) {
      this.fillHasHovertext = fillHasHovertext;
    }

    /**
     * Specifies the spacing between successive rectangular elements used to
     * render any required non-rectangular features of the symbol.
     * <p>
     * 
     * The exact meaning of this spacing setting depends on the symbol type, and
     * on if an external canvas factory has been specified via
     * <tt>setCanvasFactory</tt>:
     * <p>
     * 
     * <table border>
     * <tr>
     * <th>SymbolType</th>
     * <th>How spacing is interpreted</th>
     * <th>Default value</th>
     * </tr>
     * <tr>
     * <td>BOX_*</td>
     * 
     * <td>The distance between the centers of the "dots" used to draw the
     * dotted connecting lines between successive x,y data points on a curve.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been specified via
     * <tt>setCanvasFactory</tt>, a continuous line connecting the centers of
     * successive boxes is produced in exactly the same way as is done for the
     * <tt>LINE</tt> symbol type. Without a canvas factory (the default)
     * <tt>fillSpacing == 0</tt> works the same as <tt>fillSpacing == 1</tt>.
     * </td>
     * 
     * <td>4</td>
     * </tr>
     * 
     * <tr>
     * <td>LINE</td>
     * 
     * <td>The horizontal distance between the centers of the successive
     * vertical bars, or the vertical distance between the centers of the
     * successive horizontal bars, that GChart uses to render the point-to-point
     * connecting lines of the LINE symbol type.
     * <p>
     * 
     * The defaults (no canvas factory specified, 0px spacing) provide the
     * smoothest lines possible without using canvas, but also the longest chart
     * update times. Spacing values larger than 1px will provide proportionally
     * faster rendering of connecting lines (provided the connecting line
     * segments are significantly longer than the specified spacing), but they
     * give the lines a grainy, "stair-step" look.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been specified via
     * <tt>setCanvasFactory</tt>, a continuous, crisp (sans stair-steps) line
     * connecting the centers of successive boxes is produced--quickly. Without
     * any canvas factory specified, <tt>fillSpacing == 0</tt> will work the
     * same as <tt>fillSpacing == 1</tt>.
     * <p>
     * 
     * <i>Tip:</i> To assure an unbroken connecting line, use a non-zero fill
     * thickness setting greater than or equal to your fill spacing setting.
     * </td>
     * 
     * <td>0</td>
     * </tr>
     * 
     * <tr>
     * <td>PIE_SLICE_*</td>
     * 
     * <td>The vertical or horizontal distance between the centers of the
     * vertical, and/or horizontal, shading bars used to fill in the pie slice.
     * With the default setting, this produces a banded-fill look.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been
     * specified via <tt>setCanvasFactory</tt>, the pie slices are
     * crisp, quickly-rendered, and solid-filled. Without a canvas
     * factory specified (the default) <tt>fillSpacing == 0</tt> works
     * the same as <tt>fillSpacing == 1</tt>.
     * </td>
     * 
     * <td>4</td>
     * </tr>
     * 
     * <tr>
     * <td>VBAR_*</td>
     * 
     * <td>The horizontal distance between corresponding edges of the vertical
     * bars used to fill in the trapezoidal areas linearly interpolated between
     * successive vertical bars on a curve.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been specified via
     * <tt>setCanvasFactory</tt>, a filled polygon whose perimeter connects the
     * x,y points of the curve with a corresponding interval on the x-axis,
     * x2-axis, or horizontal baseline is rendered, so as to create a vertical,
     * solid-filled, area chart.
     * <p>
     * 
     * Without a canvas factory, <tt>fillSpacing == 0</tt> works the same as
     * <tt>fillSpacing == 1</tt>.
     * </td>
     * 
     * <td>0</td>
     * 
     * <tr>
     * <td>HBAR_*</td>
     * 
     * <td>The vertical distance between corresponding edges of the horizontal
     * bars used to fill in the trapezoidal areas linearly interpolated between
     * successive horizontal bars on a curve.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been
     * specified via <tt>setCanvasFactory</tt>, a filled polygon whose
     * perimeter connects the x,y points of the curve with a
     * corresponding interval on the y-axis, y2-axis, or vertical
     * baseline will be rendered, so as to create a horizontal,
     * solid-filled, area chart.
     * <p>
     * 
     * Without a canvas factory, <tt>fillSpacing == 0</tt> works the same as
     * <tt>fillSpacing == 1</tt>.
     * </td>
     * 
     * <td>0</td>
     * </tr>
     * 
     * <tr>
     * <td>XGRIDLINE</td>
     * 
     * <td>The horizontal distance between corresponding edges of the vertical
     * bars used to fill in the trapezoidal areas linearly interpolated between
     * successive x-gridlines on a curve.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been
     * specified via <tt>setCanvasFactory</tt>, a filled polygon is
     * drawn whose perimeter connects the x,y points of the curve with a
     * corresponding interval on either the x-axis or x2-axis, depending
     * on whichever axis is furthest from each point on the curve.
     * <p>
     * 
     * Without a canvas factory, <tt>fillSpacing == 0</tt> works the same as
     * <tt>fillSpacing == 1</tt>.
     * <p>
     * 
     * <i>Tip:<i> To make a canvas-filled x-gridline curve fill in a rectangular
     * region, set the y of each point to <tt>Double.MAX_VALUE</tt>.
     * </td>
     * 
     * <td>4</td>
     * </tr>
     * <tr>
     * <td>YGRIDLINE</td>
     * 
     * <td>The vertical distance between corresponding edges of the horizontal
     * bars used to fill in the trapezoidal areas linearly interpolated between
     * successive y-gridlines on a curve.
     * <p>
     * 
     * If <tt>fillSpacing == 0</tt> ("continuously filled"),
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been
     * specified via <tt>setCanvasFactory</tt>, a filled polygon is
     * drawn whose perimeter connects the x,y points of the curve with a
     * corresponding interval on either the y-axis or y2-axis, depending
     * on whichever axis is furthest from each point on the curve.
     * 
     * <p>
     * 
     * Without a canvas factory, <tt>fillSpacing == 0</tt> works the same as
     * <tt>fillSpacing == 1</tt>.
     * <p>
     * 
     * <i>Tip:<i> To make a canvas-filled y-gridline curve fill in a rectangular
     * region, set the x of each point to <tt>Double.MAX_VALUE</tt>.
     * </td>
     * 
     * <td>4</td>
     * </tr>
     * </table>
     * <p>
     * 
     * As of version 2.5 GChart provides support for canvas-based, crisp,
     * quickly drawn, lines, 2-D pie slices, and area charts if a
     * <tt>fillSpacing</tt> of <tt>0</tt> is specified ("continuously filled")
     * along with a <tt>fillThickness &gt; 0</tt>. However, you must bolt-on an
     * external canvas library (plain vanilla GWT does not currently come
     * pre-loaded with a browser independent canvas Widget. However, the fact
     * that the GWT incubator project contains one implies that the GWT team is
     * considering adding one). See the <tt>setCanvasFactory</tt> method for
     * details.
     * <p>
     * 
     * By default, GChart does not use an external canvas library, and thus
     * depends only on the standard GWT distribution and its own 3,000-odd
     * semi-colon terminated lines of GWT Java. If you decide to stick with this
     * default, the following tips may help you workaround GChart's
     * rectangle-element-based limitations when used in this mode.
     * <p>
     * 
     * First, if your goal is to produce a solid connecting line between points
     * always use the <tt>LINE</tt> symbol type rather than the
     * <tt>BOX_CENTER</tt> symbol type with a fill spacing of 1px. The
     * <tt>LINE</tt> symbol type knows how to merge adjacent pixels into larger
     * rectangular elements, and is therefore usually much more efficient,
     * especially with curves that involve many near-vertical or near-horizontal
     * connecting lines. For best performance, use the <tt>BOX_CENTER</tt>
     * symbol only to produce dotted connecting lines that have widely spaced
     * dots.
     * <p>
     * 
     * In general, since the number of elements required is often inversely
     * proportional to fill spacing, using a very small fill spacing like 1px,
     * while allowed, could degrade performance unacceptably, especially for
     * very large-sized charts. On the other hand, too large a fill
     * spacing/thickness can degrade graphical quality unacceptably (e.g. due to
     * too few "dots" on dotted connecting lines, "stair-step" solid connecting
     * lines, or "grainy filled" pie slices).
     * <p>
     * 
     * <blockquote><small>
     * 
     * <i>Tip:</i> For pie slices as well as for dotted or solid connecting
     * lines, scaling down the size of the chart via <tt>setXPixelSize</tt> and
     * <tt>setYPixelSize</tt> can also speed up chart display, and thus will
     * often provide a better-looking alternative to increasing the fill
     * spacing.
     * <p>
     * 
     * In particular, for a typical curve whose x-values always increase with
     * point index (i.e. no "doubling back") <tt>LINE</tt> symbol type curves
     * often have a number of elements, and thus an update time, that is
     * approximately equal to:
     * <p>
     * 
     * <pre>
     * 
     * &quot;Some Constant&quot; * (xMaxInPixels - xMinInPixels) / fillSpacing
     * 
     * </pre>
     * <p>
     * 
     * So, for <tt>LINE</tt> curves, halving the x-axis range via
     * <tt>setXPixelSize</tt> will provide approximately the same speedup as
     * doubling the fill spacing setting, and, because the lines will be less
     * "stair-steppy", will often provide a more acceptable visual result.
     * </small> </blockquote>
     * <p>
     * 
     * Experience suggests that many applications will be able to find a
     * combination of chart size and spacing/thickness settings that provide an
     * acceptable level of both graphical quality and performance--particularly
     * if your charting needs are more utilitarian than aesthetic. When that's
     * not good enough, it's time to use <tt>setCanvasFactory</tt> to
     * super-charge GChart's rendering with the power of a cross-browser vector
     * graphics facility, such as <tt>GWTCanvas</tt>.
     * <p>
     * 
     * @param fillSpacing
     *          spacing between successive rectangular elements used to fill in
     *          non-rectangular symbols, in pixels. If a canvas factory has been
     *          specified, you can use a setting of <tt>0</tt> to produce
     *          "continuously filled" elements.
     * 
     * @see #getFillSpacing getFillSpacing
     * @see #setFillThickness setFillThickness
     * @see #setCanvasFactory setCanvasFactory
     * 
     */
    public void setFillSpacing(double fillSpacing) {
      getParent().invalidate();
      // x!=x is a faster isNaN
      if (!(fillSpacing != fillSpacing) && fillSpacing != 0
          && fillSpacing < 1)
        throw new IllegalArgumentException("fillSpacing=" + fillSpacing
            + "; " + "fillSpacing must either be >= 1, or else "
            + "equal to either 0 or Double.NaN.");
      this.fillSpacing = fillSpacing;
    }

    /**
     * Sets the "thickness" of the rectangular elements used to render any
     * required non-rectangular features of this symbol.
     * <p>
     * 
     * The exact meaning of this thickness setting, as well as the default used
     * whenever the thickness is set to the special undefined integer value
     * recognized by GChart (<tt>GChart.NAI</tt>), depends on the symbol type,
     * and if an external canvas factory has been specified via
     * <tt>setCanvasFactory</tt>:
     * <p>
     * 
     * <table border>
     * <tr>
     * <th>SymbolType</th>
     * <th>How thickness is interpreted</th>
     * <th>Default value</th>
     * </tr>
     * 
     * <tr>
     * <td>BOX_*</td>
     * 
     * <td>The height and width of rectangular "dots" used to draw the dotted
     * connecting lines between successive x,y data points on a curve.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt> and a canvas
     * factory has been specified via <tt>setCanvasFactory</tt>,
     * <tt>fillThickness</tt> is the width of the continuous connecting lines
     * between successive points.
     * </td>
     * <p>
     * 
     * <td>0 (implies no interpolated dots or connecting lines)</td>
     * </tr>
     * 
     * <tr>
     * <td>LINE</td>
     * 
     * <td>The width of the vertical line segments placed end-to-end to render
     * any "more-nearly-vertical" connecting lines of the curve, and the height
     * of the horizontal line segments placed end-to-end to render any
     * "more-nearly-horizontal" connecting lines on this curve. Note that if you
     * use a fill thickness less than the fill spacing, your line will not be
     * continuously connected.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt> and a canvas
     * factory has been specified via <tt>setCanvasFactory</tt>,
     * <tt>fillThickness</tt> is the width of the continuous connecting lines
     * drawn between successive points.
     * 
     * </td>
     * 
     * <td>1</td>
     * </tr>
     * 
     * <tr>
     * <td>PIE_SLICE_*</td>
     * 
     * <td>The width of vertical, and/or the height of horizontal, shading bars
     * used to fill in the pie slice
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt> and a canvas
     * factory has been specified via <tt>setCanvasFactory</tt>, pie slices are
     * solidly and continously filled for any <tt>fillThickness &gt; 0</tt>.
     * </td>
     * 
     * <td>2</td>
     * 
     * </tr>
     * 
     * <tr>
     * <td>VBAR_*</td>
     * 
     * <td>The width of vertical bars used to fill in the trapezoidal areas
     * linearly interpolated between successive vertical bars on a curve.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt> and a canvas
     * factory has been specified via <tt>setCanvasFactory</tt>, these
     * trapezoidal areas are merged together and solidly and continously filled
     * for any <tt>fillThickness &gt; 0</tt>.
     * 
     * </td>
     * 
     * <td>0 (implies no "area filling" between bars)
     * 
     * <tr>
     * <td>HBAR_*</td>
     * 
     * <td>The height of horizontal bars used to fill in the trapezoidal areas
     * linearly interpolated between successive horizontal bars on a curve.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt> and a canvas
     * factory has been specified via <tt>setCanvasFactory</tt>, these
     * trapezoidal areas are merged together and solidly and continously filled
     * for any <tt>fillThickness &gt; 0</tt>.
     * 
     * </td>
     * 
     * <td>0 (implies no "area filling" between bars)</td>
     * </tr>
     * 
     * <tr>
     * <td>XGRIDLINE</td>
     * 
     * <td>The width of vertical bars used to fill in the trapezoidal areas
     * linearly interpolated between successive x-gridlilnes on a curve.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt>,
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been specified
     * via <tt>setCanvasFactory</tt>, a continuously filled area chart, as
     * described in <tt>setFillSpacing</tt>, will be produced.
     * </td>
     * 
     * <td>0 (implies no "area filling" between gridlines)
     * </tr>
     * 
     * <tr>
     * <td>YGRIDLINE</td>
     * 
     * <td>The height of horizontal bars used to fill in the trapezoidal areas
     * linearly interpolated between successive y-gridlilnes on a curve.
     * <p>
     * 
     * If the fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt>,
     * <tt>fillThickness &gt; 0</tt>, and a canvas factory has been specified
     * via <tt>setCanvasFactory</tt>, a continuously filled area-chart, as
     * described in <tt>setFillSpacing</tt>, will be produced.
     * 
     * </td>
     * 
     * <td>0 (implies no "area filling" between gridlines)
     * </tr>
     * </table>
     * 
     * <p>
     * This fill thickness setting and the associated fill spacing setting (c.f.
     * <tt>setFillSpacing</tt>) work together to define the look and efficiency
     * of pie slice shading, connecting lines, etc.
     * 
     * @param fillThickness
     *          the fill thickness, in pixels
     * 
     * @see #setCanvasFactory setCanvasFactory
     * @see #getFillThickness getFillThickness
     * @see #setFillSpacing setFillSpacing
     */
    public void setFillThickness(int fillThickness) {
      getParent().invalidate();
      if (fillThickness != GChart.NAI && fillThickness < 0)
        throw new IllegalArgumentException("fillThickness="
            + fillThickness + "; "
            + "fillThickness must either be >= 0, or else "
            + "equal to GChart.NAI.");
      this.fillThickness = fillThickness;
    }

    /**
     * Sets a boolean that determines if point-specific annotations will popup
     * whenever you hover over a point on the curve associated with this symbol.
     * <p>
     * 
     * By default, these hover-induced popups are enabled.
     * <p>
     * 
     * Note that the point selection feedback on the hovered-over point is
     * controlled separately, via the <tt>setHoverSelectionEnabled</tt> method.
     * 
     * @param hoverAnnotationEnabled
     *          true if hover-induced annotations popup on this curve, false
     *          otherwise.
     * 
     * @see #getHoverAnnotationEnabled getHoverAnnotationEnabled
     * @see #setHoverSelectionEnabled setHoverSelectionEnabled
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public void setHoverAnnotationEnabled(boolean hoverAnnotationEnabled) {
      this.hoverAnnotationEnabled = hoverAnnotationEnabled;
    }

    /**
     * Specifies the weight of the font that will be used to render the text of
     * this point's hover annotations.
     * <p>
     * 
     * @param cssWeight
     *          A standard CSS font-weight specification such as normal, bold,
     *          bolder, lighter, 100, 200, ... 900, or inherit
     * 
     * @see #getHoverFontWeight getHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public void setHoverFontWeight(String cssWeight) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setFontWeight(cssWeight);
    }

    /**
     * Specifies the color of the hover annotations' font.
     * <p>
     * 
     * For more information on standard CSS color specifications see the
     * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
     * <p>
     * 
     * @param cssColor
     *          color of the font used to display this symbol's hover
     *          annotations.
     * 
     * @see #getHoverFontColor getHoverFontColor
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public void setHoverFontColor(String cssColor) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setFontColor(cssColor);
    }

    /**
     * Specifies the CSS font-family used by this symbol's hover annotations.
     * 
     * @param cssFontFamily
     *          any valid CSS font-family, such as
     *          <tt>"serif", "sans-serif", "monospace", "cursive",
     *          "fantasy"</tt> or <tt>"Arial, sans-serif"</tt>.
     * 
     * @see #getHoverFontFamily getHoverFontFamily
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * @see #setHoverFontStyle setHoverFontStyle
     */
    public void setHoverFontFamily(String cssFontFamily) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setFontFamily(cssFontFamily);
    }

    /**
     * Specifies the CSS font-style used by this symbol's hover annotations.
     * 
     * @param cssStyle
     *          any valid CSS font-style, namely, normal, italic, oblique, or
     *          inherit.
     * 
     * @see #getHoverFontStyle getHoverFontStyle
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     */
    public void setHoverFontStyle(String cssStyle) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setFontStyle(cssStyle);
    }

    /**
     * Specifies the CSS font size used in this symbol's hover annotations, in
     * pixels.
     * 
     * @param fontSize
     *          the CSS font size used in the hover annotations associated with
     *          this symbol, in pixels.
     * 
     * @see #getHoverFontSize getHoverFontSize
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     */
    public void setHoverFontSize(int fontSize) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setFontSize(fontSize);
    }

    /**
     * Specifies the location of this point's hover annotations. Set this
     * property to <tt>null</tt> (the default) to use GChart's default hover
     * location, which varies with the hover annotation's symbol type, as
     * tabulated below. (The hover annotation symbol type defaults to the symbol
     * type of the hovered over curve, and can be specified explicitly via the
     * <tt>setHoverAnnotationSymbolType</tt> method).
     * <p>
     * 
     * <table border>
     * <tr>
     * <th>SymbolType used to<br>
     * position hover annotation</th>
     * <th>Default Hover<br>
     * AnnotationLocation</th>
     * </tr>
     * <tr>
     * <td>HBAR_BASELINE_*</td>
     * <td>FARTHEST_FROM_VERTICAL_BASELINE</td>
     * </tr>
     * <tr>
     * <td>HBAR_*WEST</td>
     * <td>EAST</td>
     * </tr>
     * <tr>
     * <td>HBAR_*EAST</td>
     * <td>WEST</td>
     * </tr>
     * <tr>
     * <td>PIE_SLICE_*</td>
     * <td>OUTSIDE_PIE_ARC</td>
     * </tr>
     * <tr>
     * <td>VBAR_SOUTH*</td>
     * <td>NORTH</td>
     * </tr>
     * <tr>
     * <td>VBAR_BASELINE_*</td>
     * <td>FARTHEST_FROM_HORIZONTAL_BASELINE</td>
     * </tr>
     * <tr>
     * <td>VBAR_NORTH*</td>
     * <td>SOUTH</td>
     * </tr>
     * <tr>
     * <td>All others</td>
     * <td>NORTHWEST</td>
     * </tr>
     * </table>
     * 
     * <p>
     * 
     * You can further adjust the position of a point's hover annotations by
     * specifying non-zero positional shifts via the <tt>setHoverXShift</tt> and
     * <tt>setHoverYShift</tt> methods, and via the
     * <tt>setHoverAnnotationSymbolType</tt> method.
     * <p>
     * 
     * <i>Tip:</i> To position hover annotations at a fixed location on the
     * chart, (such as a status bar that displays information about the hovered
     * over point, an inset chart that shows a detailed view, etc.) pass one of
     * the <tt>ANCHOR_*</tt> symbol types to the
     * <tt>setHoverAnnotationSymbolType</tt> method.
     * <p>
     * 
     * @param hoverLocation
     *          the relative location of the hover annotations, or <tt>null</tt>
     *          to use a symbol-type-specific default.
     * 
     * @see #getHoverLocation getHoverLocation
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
     * 
     */
    public void setHoverLocation(AnnotationLocation hoverLocation) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setLocation(hoverLocation);
    }

    /**
     * Sets the symbol type that GChart will use when positioning hover
     * annotations. GChart positions each hover annotation as if it were
     * associated with a point with the same x,y as the hovered over point, and
     * mapped to the same y-axis, but that appears on a curve with the symbol
     * type specified by this method.
     * 
     * <p>
     * If <tt>null</tt> is used (this is the default) GChart will use the symbol
     * type associated with the curve containing the hovered over point. Since
     * normally you will want hover annotations to be positioned as if they were
     * annotations of the hovered over points, this default is usually
     * appropriate.
     * <p>
     * 
     * However, sometimes you would like the hover annotations to be positioned
     * differently. For example, you might prefer the hover annotations to
     * always appear within a status bar at the bottom of the chart. To achieve
     * this, you could set this property to <tt>ANCHOR_SOUTHWEST</tt>. Or
     * suppose you always wanted a pie chart's hover annotations to appear in
     * the center of the pie instead of along the outer perimeter. Then you
     * could use <tt>BOX_CENTER</tt>. Or, if you wanted the hover annotations to
     * be positioned relative to the position that the mouse was at when the
     * symbol was first "touched", you could use <tt>ANCHOR_MOUSE</tt>.
     * <p>
     * 
     * <i>Tip:</i> Pre v2.4 versions of GChart supported a much simpler,
     * "at-the-mouse", <tt>setTitle</tt>-based, hover feedback that you can
     * emulate using code such as:
     * <p>
     * 
     * <pre>
     * Symbol sym = getCurve().getSymbol();
     * sym.setHoverAnnotationSymbolType(SymbolType.ANCHOR_MOUSE);
     * sym.setHoverLocation(AnnotationLocation.SOUTHEAST);
     * // push 20px below mouse (kind of like setTitle does it).
     * sym.setHoverYShift(-20);
     * 
     * // Convenience/transition-helper method
     * // formatAsHovertext wraps plain text in appropriate
     * // HTML so it looks kind of like setTitle-based hovertext.
     * 
     * sym.setHovertextTemplate(GChart.formatAsHovertext(&quot;x=${x}, y=${y}&quot;));
     * </pre>
     * <p>
     * 
     * @param hoverAnnotationSymbolType
     *          the symbol type that in part determines how the hover
     *          annotations get positioned, or <tt>null</tt> (the default) to
     *          use the symbol type of the hovered over point.
     * 
     * @see #getHoverAnnotationSymbolType getHoverAnnotationSymbolType
     * @see #setHoverLocation setHoverLocation
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * @see GChart#formatAsHovertext GChart.formatAsHovertext
     */
    public void setHoverAnnotationSymbolType(
        SymbolType hoverAnnotationSymbolType) {
      this.hoverAnnotationSymbolType = hoverAnnotationSymbolType;
    }

    /**
     * Specifies the background color used to indicate that the mouse is
     * "touching" (hovering over) a point.
     * <p>
     * 
     * Whenever the user "touches" a point on this curve with the curve's
     * mouse-centered "brush", GChart displays the hover feedback for that
     * point, and indicates that the point is the one the hover feedback refers
     * to by changing its background color to this color.
     * <p>
     * 
     * The default hover selection background color is "transparent". This
     * allows the original symbol to appear within selection rectangles that can
     * be defined via the <tt>setHoverSelectionBorderWidth</tt> and
     * <tt>setHoverSelectionBorderColor</tt> methods (1px thick external gray
     * selection rectangles are used by default).
     * <p>
     * 
     * <i>Tip:</i> Because the background selection color will often cover the
     * original symbol, it's usually best to choose a selection background color
     * closely related to the original symbol's background color. For example,
     * if the original symbol were blue, you might use a lighter shade of blue.
     * 
     * @param hoverSelectionBackgroundColor
     *          a CSS color specification string that specifies the background
     *          color used to indicate "hover-selection".
     * 
     * @see #getHoverSelectionBackgroundColor getHoverSelectionBackgroundColor
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setHoverSelectionBorderStyle setHoverSelectionBorderStyle
     * @see #setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     * @see #setBrushHeight setBrushHeight
     * 
     */
    public void setHoverSelectionBackgroundColor(
        String hoverSelectionBackgroundColor) {
      this.hoverSelectionBackgroundColor = hoverSelectionBackgroundColor;
    }

    /**
     * Specifies the border color used to indicate that the mouse is "touching"
     * (hovering over) a point.
     * <p>
     * 
     * Whenever the user "touches" a point on this curve with the mouse-centered
     * "brush", GChart displays the hover feedback for that point, and indicates
     * that the point is the one the hover feedback refers to by drawing a
     * border around it with the given color.
     * <p>
     * 
     * The width of this border, and if the is drawn outside or just inside the
     * rectangles associated with the symbol, can be specified via
     * <tt>setHoverSelectionBorderWidth</tt>.
     * <p>
     * 
     * The default hover selection border color is <tt>gray</tt>.
     * 
     * @param hoverSelectionBorderColor
     *          a CSS color specification string that specifies the color used
     *          to indicate "hover-selection", or the special keyword
     *          TRANSPARENT_BORDER_COLOR for a cross-browser consistent
     *          transparent border.
     * 
     * @see #getHoverSelectionBorderColor getHoverSelectionBorderColor
     * @see #setHoverSelectionBorderStyle setHoverSelectionBorderStyle
     * @see #setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     * @see #setBrushHeight setBrushHeight
     * 
     */
    public void setHoverSelectionBorderColor(
        String hoverSelectionBorderColor) {
      this.hoverSelectionBorderColor = hoverSelectionBorderColor;
    }

    /**
     * Specifies the border style used to indicate that the mouse is "touching"
     * (hovering over) a point.
     * <p>
     * 
     * Whenever the user "touches" a point on this curve with the mouse-centered
     * "brush", GChart displays the hover feedback for that point, and indicates
     * that the point is the one the hover feedback refers to by drawing a
     * border around it with the given style.
     * <p>
     * 
     * The width of this border, and if it is drawn outside or just inside the
     * rectangles associated with the symbol, can be specified via
     * <tt>setHoverSelectionBorderWidth</tt>.
     * 
     * The default hover selection border style is <tt>solid</tt>.
     * 
     * @param hoverSelectionBorderStyle
     *          a CSS border style specification string that indicates the style
     *          of border used to indicate "hover-selection".
     * 
     * @see #getHoverSelectionBorderStyle getHoverSelectionBorderStyle
     * @see #setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setBrushHeight setBrushHeight
     * 
     */
    public void setHoverSelectionBorderStyle(
        String hoverSelectionBorderStyle) {
      this.hoverSelectionBorderStyle = hoverSelectionBorderStyle;
    }

    /**
     * Sets the width of the border around the perimeter of the symbol used to
     * indicate that the mouse is "touching" (hovering over) a point.
     * <p>
     * 
     * If positive, the border is drawn just inside the selected
     * symbol's perimeter; if negative, the border is drawn just outside
     * of it.  <p>
     * 
     * <i>Tip:</i> To create the illusion that symbols increase in size when
     * they are "touched", use a hover selection border color that matches the
     * symbol's color along with a negative hover selection border width.
     * <p>
     * 
     * @param borderWidth
     *          the width of the border drawn around the perimeter of the
     *          selected symbolto indicate that the symbol is
     *          being "touched: by the mouse. A negative value adds that border
     *          around the outside of the symbol, in effect
     *          increasing the selected symbol's size (in pixels).
     * 
     * @see #getHoverSelectionBorderWidth getHoverSelectionBorderWidth
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setHoverSelectionBackgroundColor setHoverSelectionBackgroundColor
     * @see #setHoverSelectionBorderStyle setHoverSelectionBorderStyle
     * 
     */
    public void setHoverSelectionBorderWidth(int borderWidth) {
      hoverSelectionBorderWidth = borderWidth;
    }

    /**
     * Specifies if hover selection feedback will be provided for this curve.
     * <p>
     * 
     * When enabled, whenever the user "touches" a point on this curve with the
     * mouse-centered "brush", GChart indicates the hover-selected point by
     * adding a selection border around the point, etc.
     * <p>
     * 
     * By default, hover selection feedback is enabled.
     * <p>
     * 
     * Note that the pop-up hover annotation itself is controlled separately,
     * via the <tt>setHoverAnnotationEnabled</tt> method.
     * <p>
     * 
     * @param hoverSelectionEnabled
     *          a if true, hover selection feedback is enabled, if false,
     *          hovering over a point does not change its color, etc.
     * 
     * @see #getHoverSelectionEnabled getHoverSelectionEnabled
     * @see #setHoverAnnotationEnabled setHoverAnnotationEnabled
     * @see #setHoverSelectionBackgroundColor setHoverSelectionBackgroundColor
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setHoverSelectionBorderStyle setHoverSelectionBorderStyle
     * @see #setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     * @see #setHoverSelectionSymbolType setHoverSelectionSymbolType
     * 
     */
    public void setHoverSelectionEnabled(boolean hoverSelectionEnabled) {
      this.hoverSelectionEnabled = hoverSelectionEnabled;
    }

    /**
     * Specifies the fill spacing that will be used when rendering this curve's
     * hover selection feedback.
     * <p>
     * 
     * For more on fill spacing, see <tt>setFillSpacing</tt>.
     * <p>
     * 
     * @param selectionFillSpacing
     *          fill spacing, in pixels, used when rendering this curve's hover
     *          selection feedback or <tt>Double.NaN</tt> (the default) to adopt
     *          the curve's fill spacing.
     * 
     * @see #getHoverSelectionFillSpacing getHoverSelectionFillSpacing
     * @see #setFillSpacing setFillSpacing
     * 
     */

    public void setHoverSelectionFillSpacing(double selectionFillSpacing) {
      hoverSelectionFillSpacing = selectionFillSpacing;
    }

    /**
     * Specifies the fill thickness that will be used when rendering this
     * curve's hover selection feedback.
     * <p>
     * 
     * For more on fill thickness, see <tt>setFillThickness</tt>.
     * <p>
     * 
     * @param selectionFillThickness
     *          fill thickness, in pixels, used when rendering this curve's
     *          hover selection feedback or <tt>GChart.NAI</tt> (the default) to
     *          adopt the curve's fill thickness.
     * 
     * @see #getHoverSelectionFillThickness getHoverSelectionFillThickness
     * @see #setFillThickness setFillThickness
     * 
     */
    public void setHoverSelectionFillThickness(int selectionFillThickness) {
      hoverSelectionFillThickness = selectionFillThickness;
    }

    /**
     * Sets the height of the symbol used to indicate when a given point is
     * being "hovered over" with the mouse.
     * <p>
     * 
     * With the default setting of <tt>GChart.NAI</tt>, GChart simply gives the
     * hover selection symbol the same height as the symbol representing the
     * point itself. Though this default is usually appropriate, you might want
     * the selection symbol to have a larger size so as to increase the
     * visibility of the selected point, etc.
     * <p>
     * 
     * @param selectionHeight
     *          the height of the symbol used to indicate that that a point has
     *          been selected, in pixels, or <tt>GChart.NAI</tt> (the default)
     *          to use the height of the symbol representing the selected point.
     * 
     * 
     * @see #getHoverSelectionHeight getHoverSelectionHeight
     * @see #setHoverSelectionWidth setHoverSelectionWidth
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setHoverSelectionBackgroundColor setHoverSelectionBackgroundColor
     * 
     */
    public void setHoverSelectionHeight(int selectionHeight) {
      hoverSelectionHeight = selectionHeight;
    }

    /**
     * Specifies the URL that will define the image used to render selection
     * feedback for points on the curve associated with this symbol.
     * <p>
     * 
     * Specify <tt>null</tt> to use the URL returned by
     * <tt>getBlankImageURL</tt> (this is the default, and gives you a blank 1x1
     * pixel GIF). Since the image is transparent, the
     * <tt>setHoverSelectionBackgroundColor</tt> method can be used to define
     * the background color of the selection feedback.
     * <p>
     * 
     * Though most applications will do just fine with this default, you can use
     * this method for special selection effects, such creating a
     * semi-transparent "screen" (say, by using an image with alternating
     * transparent and gray pixels) that overlays the selected points.
     * <p>
     * 
     * The image is applied in the same way as the symbol's own image URL, but
     * to the internal, system, curve GChart uses to render the selection
     * feedback. See <tt>setImageURL</tt> for additional information.
     * 
     * @see #getHoverSelectionImageURL getHoverSelectionImageURL
     * @see #setImageURL setImageURL
     * @see #setBlankImageURL setBlankImageURL
     * 
     * @param imageURL
     *          the url that defines the image used to generate selection
     *          feedback for points rendered with this symbol, or <tt>null</tt>
     *          to to use GChart's default selection image URL (a 1x1
     *          transparent blank GIF).
     * 
     */
    public void setHoverSelectionImageURL(String imageURL) {
      hoverSelectionImageURL = imageURL;
    }

    /**
     * Sets the symbol type that GChart will use when generating selection
     * feedback. GChart indicates that a point is selected by re-rendering the
     * point <i>as if</i> it had this symbol type (this re-rendering overlays,
     * but need not completely cover, the original rendering).
     * <p>
     * 
     * If <tt>null</tt> is used (this is the default) GChart will use the symbol
     * type associated with the original point. This default, which overlays the
     * selection feedback on top of the rendered symbol, is usually appropriate.
     * <p>
     * 
     * However, sometimes you would like the selection feedback to use a
     * different symbol type. For example, you might prefer to indicate that a
     * point is selected by drawing a vertical gridline through the point. To
     * achieve this, you could use the <tt>XGRIDLINE</tt> symbol type. Or, you
     * might wish to indicate selection by dropping a vertical line from the
     * center of the selected point to the x-axis. In this case, you could use
     * <tt>VBAR_SOUTH</tt> as the hover selection symbol type.
     * <p>
     * 
     * <i>Note:</i> The special mouse related symbol types (those with names
     * matching <tt>ANCHOR_MOUSE*</tt>) are intended for use in positioning
     * hover popup annotations (via <tt>setHoverAnnotationSymbolType</tt>). They
     * are not expected to be useful, and could potentially cause confusion, if
     * used as the symbol type passed to this method.
     * <p>
     * 
     * @param hoverSelectionSymbolType
     *          the symbol type that in part determines how selection feedback
     *          for a hovered over point is drawn, or <tt>null</tt> (the
     *          default) to use the symbol type of the hovered over point.
     * 
     * @see #getHoverSelectionSymbolType getHoverSelectionSymbolType
     * @see Symbol#setHoverSelectionBackgroundColor
     *      setHoverSelectionBackgroundColor
     * @see Symbol#setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see Symbol#setHoverSelectionBorderWidth setHoverSelectionBorderWidth
     * @see Symbol#setHoverSelectionHeight setHoverSelectionHeight
     * @see Symbol#setHoverSelectionWidth setHoverSelectionWidth
     * @see Symbol#setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * 
     */
    public void setHoverSelectionSymbolType(
        SymbolType hoverSelectionSymbolType) {
      this.hoverSelectionSymbolType = hoverSelectionSymbolType;
    }

    /**
     * Sets the width of the symbol used to indicate when a given point is being
     * "hovered over" with the mouse.
     * <p>
     * 
     * With the default setting of <tt>GChart.NAI</tt>, GChart simply gives the
     * hover selection symbol the same width as the symbol representing the
     * point itself. Though this default is usually appropriate, you might want
     * the selection symbol to have a larger size so as to increase the
     * visibility of the selected point, etc.
     * <p>
     * 
     * @param selectionWidth
     *          the width of the symbol used to indicate that a point has
     *          been selected, in pixels, or <tt>GChart.NAI</tt> (the default)
     *          to use the width of the symbol representing the selected point.
     * 
     * @see #getHoverSelectionWidth getHoverSelectionWidth
     * @see #setHoverSelectionHeight setHoverSelectionHeight
     * @see #setHoverSelectionBorderColor setHoverSelectionBorderColor
     * @see #setHoverSelectionBackgroundColor setHoverSelectionBackgroundColor
     * 
     */
    public void setHoverSelectionWidth(int selectionWidth) {
      hoverSelectionWidth = selectionWidth;
    }

    /**
     * Defines the "hover-text" that appears whenever the user points their
     * mouse at a point on the curve.
     * <p>
     * 
     * HTML is supported within hover-text. As with <tt>setAnnotationText</tt>,
     * you must prefix HTML template strings with <tt>&lt;html&gt;</tt> or they
     * will be treated as plain text.
     * <p>
     * 
     * Three built-in parameters, <tt>${x}</tt>, <tt>${y}</tt>, and
     * <tt>${pieSliceSize}</tt> are recognized within these hover text
     * templates. Any occurrences of <tt>${x}</tt> in the string will be
     * replaced with the x-coordinate of the point, formatted as per the
     * specified tick label format of the x-axis. Any occurrences of
     * <tt>${y}</tt> within the string will be replaced with the y-coordinate of
     * the point, formatted using either the y-axis or y2-axis tick label
     * format, depending on the axis on which the curve is displayed. Any
     * occurrences of <tt>${pieSliceSize}</tt> within the string will be
     * replaced with 100 times the specified <tt>pieSliceSize</tt> of the point,
     * formatted the same way as <tt>${y}</tt>, except that a "%" is tacked onto
     * the end.
     * <p>
     * 
     * In addition to these built-in parameters, user-defined parameters are
     * also supported. All parameter names must begin with a letter (
     * <tt>a,b,...,z</tt> or <tt>A,B,...,Z</tt>) and be followed by a series of
     * letters, digits (<tt>0,1,...,9</tt>), and underscores (<tt>_</tt> ). For
     * example, <tt>${myParam3}</tt> or <tt>${xyz_123}</tt>. Note that parameter
     * names are case-sensitive.
     * <p>
     * 
     * You define the rules for expanding these user-defined parameters relative
     * to the hovered-over point, by instantiating a
     * <tt>HoverParameterInterpreter</tt> and passing it to GChart's
     * <tt>setHoverParameterInterpreter</tt> method. See the
     * <tt>HoverParameterInterpreter</tt> interface's javadocs for full details.
     * <p>
     * 
     * <blockquote>
     * <i>Tip:</i> If the <tt>${</tt> is not followed by a valid parameter name
     * and then by <tt>}</tt>, the "invalid name", along with the original
     * delimiters, passes through literally into the final hovertext (no
     * exception is thrown). So, if you see keywords in your hovertext, it
     * probably means you misspelled a keyword (e.g. you entered
     * <tt>${piesliceSize}</tt> instead of <tt>${pieSliceSize}</tt> ), forgot
     * the closing <tt>}</tt>, began a user-defined parameter name with a digit,
     * and so on.
     * </blockquote>
     * <p>
     * 
     * The default hovertext template, used automatically if hovertext template
     * is <tt>null</tt>, is <tt>DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE</tt> for
     * pie slice type symbols and <tt>DEFAULT_HOVERTEXT_TEMPLATE</tt> for all
     * other symbol types.
     * 
     * <blockquote> <i>Tip:</i>If you notice unexplained whitespace to the right
     * or below your pages, that comes and goes as you hover over the chart, it
     * could be due to an overly large default estimate for the hidden
     * "bounding box" GChart uses to properly center hover annotations. You can
     * use the {@link #setHoverWidget setHoverWidget} method, with a
     * <tt>null</tt> first widget parameter, to override these defaults and
     * correct this problem. </blockquote>
     * <p>
     * 
     * @param hovertextTemplate
     *          defines the hoverText to display when the mouse moves over a
     *          point on this curve, with <tt>${x}</tt>, <tt>${y}</tt> and
     *          <tt>${pieSliceSize}</tt> parameters replaced as described above,
     *          and custom parameters replaced as defined by the parent GChart's
     *          <tt>HoverParameterInterpreter</tt>.
     * 
     * @see #getHovertextTemplate getHovertextTemplate
     * @see Curve.Point#getHovertext getHovertext
     * @see HoverParameterInterpreter HoverParameterInterpreter
     * @see GChart#setHoverParameterInterpreter setHoverParameterInterpreter
     * @see HoverUpdateable HoverUpdateable
     * @see GChart.Curve.Point#setAnnotationText setAnnotationText
     * @see #DEFAULT_HOVERTEXT_TEMPLATE DEFAULT_HOVERTEXT_TEMPLATE
     * @see #DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE
     *      DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE
     */
    public void setHovertextTemplate(String hovertextTemplate) {
      if (this.hovertextTemplate != hovertextTemplate)
        hovertextChunks = null; // invalidates prev chunk-parse
      this.hovertextTemplate = hovertextTemplate;
    }

    /**
     * Specifies a <tt>HoverUpdateable</tt> widget that will be used to display
     * the hover annotations associated with this symbol. If <tt>null</tt>,
     * GChart's built-in, <tt>setHovertextTemplate</tt> based, text or HTML
     * based hover annotations will instead be used.
     * <p>
     * 
     * Whenever the rectangular "brush" centered on the current mouse position
     * "touches" a point on this symbol's parent curve, GChart will first call
     * the <tt>hoverUpdate</tt> method of this "hover-widget", and then position
     * it appropriately relative to the touched point. Most applications will
     * want to implement <tt>hoverUpdate</tt> so as to populate the hover widget
     * with detailed information about the touched point. For example, to
     * emulate GChart's default hover feedback, you could extend an
     * <tt>HTML</tt> widget and, within the <tt>hoverUpdate</tt> method, use the
     * <tt>setHTML</tt> method to set the widget's HTML to the expanded hover
     * text returned by <tt>hoveredOverPoint.getHovertext()</tt>.
     * <p>
     * 
     * The exact position of the hover widget relative to the touched point is
     * defined by the companion methods, <tt>setHoverLocation</tt>,
     * <tt>setHoverAnnotationSymbolType</tt>, <tt>setHoverXShift</tt>, and
     * <tt>setHoverYShift</tt>.
     * 
     * @param hoverWidget
     *          a <tt>Widget</tt> that implements the <tt>HoverUpdateable</tt>
     *          interface that GChart will use when generating this symbol's
     *          widget-based hover annotations, or <tt>null</tt> to use GChart's
     *          text or HTML based hover annotations (the other two parameters
     *          can still be used to specify upper-bounds on the width and
     *          height of this default hover text).
     * 
     * @param widthUpperBound
     *          an upper bound on the width of the widget (or default hover
     *          annotation) in pixels. Use GChart.NAI to get the
     *          GChart-determined default.
     * 
     * @param heightUpperBound
     *          an upper bound on the height of the widget (or default hover
     *          annotation) in pixels. Use GChart.NAI to get the
     *          GChart-determined default.
     * 
     * @see #getHoverWidget getHoverWidget
     * @see Curve.Point#getHovertext getHovertext
     * @see HoverUpdateable HoverUpdateable
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverXShift setHoverXShift
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public void setHoverWidget(HoverUpdateable hoverWidget,
        int widthUpperBound, int heightUpperBound) {
      if (null != hoverWidget && !(hoverWidget instanceof Widget))
        throw new IllegalArgumentException(
            "hoverWidget must either be null or a Widget.");

      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setWidget((Widget) hoverWidget, widthUpperBound,
          heightUpperBound);
    }

    /**
     * Specifies a <tt>HoverUpdateable</tt> widget that will be used to display
     * all hover annotations associated with this symbol. If <tt>null</tt>,
     * GChart's built-in, <tt>setHovertextTemplate</tt> based, text or HTML
     * based hover annotations will instead be used.
     * <p>
     * 
     * A convenience method equivalent to
     * <tt>setHoverWidget(hoverWidget, GChart.NAI, GChart.NAI)</tt>
     * 
     * @param annotationWidget
     *          the GWT Widget that defines this point's hover-induced
     *          annotation, or <tt>null</tt> to use the default hover
     *          annotation, which is based on expanding the hover text template
     *          relative to the hovered over point.
     * 
     * @see #setHoverWidget(HoverUpdateable,int,int)
     *      setHoverWidget(HoverUpdateable,int,int)
     * @see #setHovertextTemplate setHovertextTemplate
     * @see Curve.Point#getHovertext getHovertext
     * @see #DEFAULT_WIDGET_HEIGHT_UPPERBOUND DEFAULT_WIDGET_HEIGHT_UPPERBOUND
     * @see #DEFAULT_WIDGET_WIDTH_UPPERBOUND DEFAULT_WIDGET_WIDTH_UPPERBOUND
     * 
     */
    public void setHoverWidget(HoverUpdateable annotationWidget) {
      setHoverWidget(annotationWidget, DEFAULT_WIDGET_WIDTH_UPPERBOUND,
          DEFAULT_WIDGET_HEIGHT_UPPERBOUND);
    }

    /**
     * Specifies the number of pixels (along the x-axis) to move this symbol's
     * hover annotations from their default, <tt>AnnotationLocation</tt>
     * defined, point-relative locations.
     * <p>
     * 
     * Actual positional shifts are defined via the same conventions as are used
     * by <tt>setAnnotationXShift</tt>. See that method for further details.
     * 
     * @see #getHoverXShift getHoverXShift
     * @see GChart.Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverYShift setHoverYShift
     * 
     */
    public void setHoverXShift(int xShift) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setXShift(xShift);
    }

    /**
     * Specifies the number of pixels (along the y-axis) to move this symbol's
     * hover annotations from their default, <tt>AnnotationLocation</tt>
     * defined, point-relative locations.
     * <p>
     * 
     * Actual positional shifts are defined via the same conventions as are used
     * by <tt>setAnnotationYShift</tt>. See that method for further details.
     * 
     * @see #getHoverYShift getHoverYShift
     * @see GChart.Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #setHoverFontWeight setHoverFontWeight
     * @see #setHoverFontColor setHoverFontColor
     * @see #setHoverFontStyle setHoverFontStyle
     * @see #setHoverFontSize setHoverFontSize
     * @see #setHoverLocation setHoverLocation
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverWidget setHoverWidget
     * @see #setHoverXShift setHoverXShift
     * 
     */
    public void setHoverYShift(int yShift) {
      if (hoverAnnotation == null)
        hoverAnnotation = new Annotation();
      hoverAnnotation.setYShift(yShift);
    }

    /**
     * Specifies the URL that will define the image used to represent the points
     * on this curve.
     * <p>
     * 
     * Specify <tt>null</tt> to use the URL returned by
     * <tt>getBlankImageURL</tt> (this is the default, and gives you a blank 1x1
     * pixel GIF).
     * <p>
     * 
     * Most applications will do just fine with the default. However, this
     * method lets you replace the default, rectangular, chart symbols with
     * custom images (e.g. a five pointed star) or even a Google Chart API url
     * to use tiny 3-D pie charts for each point symbol (it looks a bit strange,
     * and your chart will no longer be strictly client-side any more, but it
     * does work).
     * <p>
     * 
     * Note that if the symbol's width and height are bigger or smaller than the
     * specified image, the image will be stretched to fit the symbol's size.
     * Except for single pixel images and such, this does not usually look that
     * great, so exactly matching up the symbol and image size is often best.
     * <p>
     * 
     * <small> <i>Tip:</i> By using a single pixel high or wide image whose
     * other dimension exactly matches the width or height of the symbol, the
     * image will stretch to produce a regular pattern of horizontal or vertical
     * lines. For example, this technique can be used to produce a vertical or
     * horizontal gradient effect in a horizontal or vertical bar chart.
     * </small>
     * <p>
     * 
     * Be aware that GChart was originally designed with only blank image URL's
     * in mind, so it may take some effort to adjust other settings (such as
     * symbol type, width, height, background color, border color, various
     * legend related settings, and curve order) so that the overall chart looks
     * right with your custom images for the curve symbols. In particular, the
     * legend icons are just scaled-down versions of the image, which often
     * doesn't look that great.
     * <p>
     * 
     * A alternative that gives you more control (but is less efficient) is to
     * use <tt>SymbolType.NONE</tt> with <tt>setAnnotationWidget</tt> (or
     * <tt>setAnnotationText</tt>) and
     * <tt>setAnnotationLocation(AnnotationLocation.CENTER)</tt> to use
     * separate, centered, widget-based (or HTML based) annotations in lieu of
     * each point's image-based symbol.
     * 
     * @see #getImageURL getImageURL
     * @see #setBlankImageURL setBlankImageURL
     * @see GChart#setPlotAreaImageURL setPlotAreaImageURL
     * @see Curve.Point#setAnnotationWidget setAnnotationWidget
     * @see Curve.Point#setAnnotationText setAnnotationText
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Symbol#setSymbolType setSymbolType
     * @see SymbolType#NONE SymbolType.NONE
     * 
     * @param imageURL
     *          the url that defines the image within all the rectangular
     *          elements used to draw this symbol on the chart, or <tt>null</tt>
     *          to revert to GChart's default (a 1x1 transparent blank GIF).
     * 
     */
    public void setImageURL(String imageURL) {
      getParent().invalidate();
      this.imageURL = imageURL;
    }

    /**
     * Sets the height of this symbol (including any specified border) in
     * pixels.
     * <p>
     * 
     * Symbols that draw vertical bars often compute their heights
     * automatically based on curve data, axes limits, specified baselines, etc.
     * These symbols, namely <tt>XGRIDLINE</tt> and all those whose names begin
     * with <tt>VBAR_</tt> will ignore this height setting.
     * <p>
     * 
     * @param height
     *          height of this symbol, in pixels.
     * 
     * @see #getHeight getHeight
     * @see #setWidth setWidth
     * @see #setModelHeight setModelHeight
     * @see #setModelWidth setModelWidth
     */
    public void setHeight(int height) {
      getParent().invalidate();
      this.height = height;
      this.modelHeight = Double.NaN;
    }

    /**
     * Sets the height of this symbol (including any specified border) in model
     * units (arbitrary, user-defined, units). Model units are the same units in
     * which the points on the chart are specified and charted.
     * <p>
     * 
     * Specification of the modelHeight undefines (that is, sets to
     * <tt>GChart.NAI</tt>) any previous pixel-based specification made via
     * <tt>setHeight</tt>.
     * <p>
     * 
     * Symbols for drawing vertical bars often compute their heights
     * automatically based on curve data, axes limits, specified baselines, etc.
     * These symbols, namely <tt>XGRIDLINE</tt> and all those whose names begin
     * with <tt>VBAR_</tt> will ignore this height setting.
     * <p>
     * 
     * @param modelHeight
     *          height of this symbol, in model units
     * 
     * @see #getModelHeight getModelHeight
     * @see #setHeight setHeight
     * @see #setModelWidth setModelWidth
     * @see #setWidth setWidth
     */
    public void setModelHeight(double modelHeight) {
      getParent().invalidate();
      this.modelHeight = modelHeight;
    }

    /**
     * Sets the width of this symbol (including any specified border) in model
     * units. Model units are an arbitrary, user-defined units system associated
     * with the x,y coordinates of points displayed on the chart.
     * <p>
     * 
     * Specification of a symbol's model width undefines (that is, sets to
     * <tt>GChart.NAI</tt>) any previous, pixel-based, width specification made
     * via <tt>setWidth</tt>.
     * <p>
     * 
     * Symbols for drawing horizontal bars often compute their widths
     * automatically based on curve data, axes limits, specified baseline, etc.
     * These symbols, namely <tt>YGRIDLINE</tt> and all those whose names begin
     * with <tt>HBAR_</tt> will ignore this width setting.
     * 
     * <p>
     * 
     * @param modelWidth
     *          width of this symbol, in model units.
     * 
     * @see #setModelHeight setModelHeight
     * @see #setWidth setWidth
     * @see #setHeight setHeight
     * 
     */
    public void setModelWidth(double modelWidth) {
      getParent().invalidate();
      this.modelWidth = modelWidth;
    }

    /**
     * Specifies a value that defines the angular orientation of the first edge
     * of the pie slice associated with this symbol. (An additional clockwise
     * rotation as defined by <tt>setPieSliceSize</tt> defines the angular
     * orientation of the second edge of the pie slice).
     * <p>
     * 
     * When specified explicitly, the value must be a fraction >= 0 and < 1,
     * with 0 representing due south, 0.25 an additional clockwise angular
     * rotation (starting at due south) that is 25% of the full, 360 degree
     * rotation (and thus, if you can follow these gyrations, is due west), 0.5
     * representing a 50% clockwise angular rotation from due south (thus, due
     * north), .75 a 75% clockwise rotation (and thus, due east), etc.
     * <p>
     * 
     * If the specially recognized value, <tt>Double.NaN</tt>, is specified,
     * orientation is chosen so as to make this slice appear adjacent to the
     * previous slice, (assuming it has the same x,y as the previous slice and
     * is thus part of the same pie figure). If this symbol/point represents the
     * very first pie slice, <tt>Double.NaN</tt> causes the slice to be oriented
     * as specified via the <tt>setInitialPieSliceOrientation</tt> method (by
     * default, that's due south).
     * <p>
     * 
     * Note that though this value can be set regardless of the symbol's
     * <tt>SymbolType</tt>, it only has an impact on how the symbol is rendered
     * if the symbol has one of the pie slice symbol types (e.g.
     * <tt>PIE_SLICE_VERTICAL_SHADING</tt>).
     * 
     * @param pieSliceOrientation
     *          angle at which first edge of pie slice appears, expressed as a
     *          fraction of a full 360 degree (2*Pi radians) clockwise rotation
     *          from an initial due south position (the 6 o'clock position)
     *          required to reach the first edge of the pie slice.
     * 
     * @see #getPieSliceOrientation getPieSliceOrientation
     * @see #setPieSliceSize setPieSliceSize
     * @see GChart#setInitialPieSliceOrientation setInitialPieSliceOrientation
     * 
     */
    public void setPieSliceOrientation(double pieSliceOrientation) {
      invalidateDependentSlices(getCurveIndex(getParent()));
      if (pieSliceOrientation != Double.NaN
          && (pieSliceOrientation < 0 || pieSliceOrientation >= 1))
        throw new IllegalArgumentException("pieSliceOrientation="
            + pieSliceOrientation + "; "
            + "pieSliceOrientation must be >=0 and < 1, or else "
            + "equal to Double.NaN.");
      this.pieSliceOrientation = pieSliceOrientation;
    }

    /**
     * Specifies a value that defines the angular size of any pie slice
     * associated with this symbol.
     * <p>
     * 
     * This must be value between 0 and 1. 0.25 represents a quarter pie slice,
     * 0.5 a half pie, 1 a full pie, etc.
     * <p>
     * 
     * <i>Note:</i> To create a complete pie, you must arrange things so that
     * the sum of all of your pie slice sizes adds up to exactly 1.0. If they
     * sum to more than 1, some slices will cover up others; it they sum to
     * less, your pie will have missing slices. You can assure you get a full
     * pie, regardless of the original slice sizes by normalizing your slice
     * sizes. Specifically, divide each original slice size by the sum over all
     * of the original slice sizes. For example, if the original slice sizes
     * were 1, 2, and 2 you could divide them by their sum (1 + 2 + 2 = 5) to
     * obtain normalized slice sizes of 0.2, 0.4, and 0.4.
     * <p>
     * 
     * Note that though this value can be set regardless of the symbol's current
     * <tt>SymbolType</tt>, it only has an impact on how the symbol is rendered
     * if the symbol has one of the pie slice symbol types (e.g.
     * <tt>PIE_SLICE_VERTICAL_SHADING</tt>).
     * 
     * @param pieSliceSize
     *          Fraction of a full pie subtended by this particular pie slice.
     *          Must be between 0 and 1, inclusive.
     * 
     * @see #getPieSliceSize getPieSliceSize
     * @see #setPieSliceOrientation setPieSliceOrientation
     * 
     */
    public void setPieSliceSize(double pieSliceSize) {
      invalidateDependentSlices(getCurveIndex(getParent()));
      if (!withinRange(pieSliceSize, 0, 1))
        throw new IllegalArgumentException("pieSliceSize="
            + pieSliceSize + "; the requirement: "
            + "0.0 <= pieSliceSize <= 1.0 must be satisfied.");
      this.pieSliceSize = pieSliceSize;
    }

    /**
     * Sets the type of this symbol.
     * <p>
     * 
     * <i>Note:</i> The special mouse related symbol types (those with names
     * that begin with <tt>ANCHOR_MOUSE</tt>) are intended for use in
     * positioning hover popup annotations (via
     * <tt>setHoverAnnotationSymbolType</tt>). They are not expected to be
     * useful, and could potentially cause confusion, if used as the symbol
     * types of ordinary curves.
     * 
     * @param symbolType
     *          the new symbol type for this symbol.
     * @see SymbolType SymbolType
     * @see SymbolType#ANCHOR_MOUSE ANCHOR_MOUSE
     * @see #setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * 
     */
    public void setSymbolType(SymbolType symbolType) {
      getParent().invalidate();
      // will invalidate dependent slices if it was previously a pie slice
      invalidateDependentSlices(getCurveIndex(getParent()));
      this.symbolType = symbolType;
      // will invalidate dependent slices if it is now a pie slice
      invalidateDependentSlices(getCurveIndex(getParent()));
    }

    /**
     * Sets the width of this symbol (including any specified border) in pixels.
     * <p>
     * 
     * Symbols for drawing horizontal bars often compute their widths
     * automatically based on curve data, axes limits, specified baseline, etc.
     * These symbols, namely <tt>YGRIDLINE</tt> and all those whose names begin
     * with <tt>HBAR_</tt> will ignore this width setting.
     * <p>
     * 
     * @param width
     *          width of this symbol, in pixels
     * 
     * @see #getWidth getHeight
     * @see #setHeight setHeight
     * @see #setModelHeight setModelHeight
     * @see #setModelWidth setModelWidth
     * 
     */
    public void setWidth(int width) {
      getParent().invalidate();
      this.width = width;
      this.modelWidth = Double.NaN;
    }

    /*
     * Copies properties of the "from" symbol to this symbol.
     * <p>
     * 
     * This isn't a generic copy, but is used only when copying the properties
     * of the hovered-over curve into the system curves used to render the
     * selection feedback and hover annotations (it contains some special logic
     * needed only in that context).
     */
    void copy(Symbol from) {
      setBackgroundColor(from.getBackgroundColor());
      setBaseline(from.getBaseline());
      setBorderColor(from.getBorderColor());
      setBorderStyle(from.getBorderStyle());
      setBorderWidth(from.getBorderWidth());
      setFillSpacing(from.getFillSpacing());
      setFillThickness(from.getFillThickness());
      // setHoverAnnotationEnabled(from.getHoverAnnotationEnabled());
      setHoverFontColor(from.getHoverFontColor());
      setHoverFontSize(from.getHoverFontSize());
      setHoverFontFamily(from.getHoverFontFamily());
      setHoverFontStyle(from.getHoverFontStyle());
      setHoverFontWeight(from.getHoverFontWeight());
      setHoverLocation(from.getHoverLocation());
      setHoverAnnotationSymbolType(from.getHoverAnnotationSymbolType());
      setHoverSelectionBackgroundColor(from
          .getHoverSelectionBackgroundColor());
      setHoverSelectionBorderColor(from.getHoverSelectionBorderColor());
      setHoverSelectionBorderStyle(from.getHoverSelectionBorderStyle());
      setHoverSelectionBorderWidth(from.getHoverSelectionBorderWidth());
      // setHoverSelectionEnabled(from.getHoverSelectionEnabled());
      setHovertextTemplate(from.getHovertextTemplate());
      setHoverWidget(from.getHoverWidget());
      setHoverXShift(from.getHoverXShift());
      setHoverYShift(from.getHoverYShift());
      setImageURL(from.getImageURL());
      // Model and pixel variants of width/height actually
      // represent a single underlying property (setting one,
      // unsets the other, etc.). Logic below reflects this.
      if (!Double.isNaN(from.getModelHeight()))
        setModelHeight(from.getModelHeight());
      else
        setHeight(from.getHeight());
      if (!Double.isNaN(from.getModelWidth()))
        setModelWidth(from.getModelWidth());
      else
        setWidth(from.getWidth());

      setPieSliceOrientation(from.getPieSliceOrientation());
      setDefaultPieSliceOrientation(from.getDefaultPieSliceOrientation());
      setPieSliceSize(from.getPieSliceSize());
      setSymbolType(from.getSymbolType());

    }

    // Pixel height of symbol when rendered on given plot panel
    double getHeight(PlotPanel pp, int index) {
      double result;
      double mH = getModelHeight();
      if ((mH != mH)) // x!=x is a faster isNaN
        result = getHeight();
      else
        result = pp.dyToPixel(mH, index);

      return result;
    }

    // Pixel width of symbol when rendered on given plot panel
    double getWidth(PlotPanel pp) {
      double result;
      double mW = getModelWidth();
      if ((mW != mW)) // x!=x is a faster isNaN
        result = getWidth();
      else
        result = pp.dxToPixel(mW);

      return result;
    }

//    void realizeSymbol(PlotPanel pp, GraphicsRenderingPanel grp,
//        AnnotationRenderingPanel arp, Annotation annotation,
//        boolean onY2, boolean clipPlotArea, boolean clipDecoratedChart,
//        double rpf, boolean drawMainSymbol, double x, double y,
//        double prevX, double prevY, double nextX, double nextY) {
//      getSymbolType().realizeSymbol(pp, grp, arp, this, annotation, onY2,
//          clipPlotArea, clipDecoratedChart, rpf, drawMainSymbol, x,
//          y, prevX, prevY, nextX, nextY);
//
//    }

  } // end of class Symbol

  private static double lastPieSliceOrientation;

  static double getLastPieSliceOrientation() {
    return lastPieSliceOrientation;
  }

  static void setLastPieSliceOrientation(double lastOrientation) {
    lastPieSliceOrientation = lastOrientation % 1.0;
  }


  /**
   * Specifies the type of symbol used by a curve. GChart includes a
   * <tt>LINE</tt> symbol type (suitable for solidly connected line charts),
   * various "box" symbol types (suitable for scatter and dotted-line charts),
   * horizontal and vertical bars that extend to axis limits or a specified
   * baseline (suitable for bar and area charts), and pie slices (suitable for
   * pie charts) in these symbol types. Thus, choosing a curve's symbol type has
   * a bigger impact on the kind of chart you create than in other charting APIs
   * you may have used.
   * <p>
   * 
   * One advantage of this symbol type based approach: you can place multiple
   * pies, lines and/or bars on a single chart simply by creating multiple
   * curves whose associated symbols have appropriately different symbol types.
   * <p>
   * 
   * Note that, for line, area, or pie charts, the exact look of the
   * non-rectangular aspects (connecting lines, filled-in areas, etc.) of these
   * symbols in the chart is largely governed by the host <tt>Symbol</tt>'s
   * <tt>fillSpacing</tt> and <tt>fillThickness</tt> properties.
   * <p>
   * 
   * For instance, with the default <tt>fillThickness</tt> of 0 for the
   * <tt>BOX_CENTER</tt> symbol, curves display only explicitly specified data
   * points, without any connecting lines between them. But, if you set
   * <tt>fillThickness</tt> to 1, GChart interpolates a series of 1 pixel by 1
   * pixel rectangular "dots" between successive data points, with an intra-dot
   * spacing defined by the symbol's <tt>fillSpacing</tt> setting.
   * <p>
   * 
   * Since v2.5, a new <tt>fillSpacing==0</tt> setting, with the special meaning
   * of "continuous filling", is allowed. If an external canvas library has been
   * plugged into GChart via <tt>setCanvasFactory</tt>, higher quality,
   * continuously filled pie, line, and area charts can be produced via the
   * combination: <tt>fillSpacing==0</tt> and <tt>fillThickness &gt; 0</tt> along
   * with one of the pie, line, or bar symbol types described below.
   * <p>
   * 
   * You must select each curve's symbol type from the predefined set of
   * supported types listed in the "Field Summary" section below. The default
   * symbol type is <tt>BOX_CENTER</tt>.
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setSymbolType setSymbolType
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * @see Symbol Symbol
   * 
   */
  public static class SymbolType {

    /*
     * For efficiency during hit testing, points get separated into bins
     * associated with adjacent vertical (or horizontal) bands that cover the
     * plot area. <p>
     * 
     * Subclasses (such as those for producing horizontal bar charts) whose
     * rendered symbols do not have a fixed width across all the points on a
     * single curve, MUST set this field to true within their constructors,
     * because the hit testing approach assumes fixed "thickness" symbols for
     * simplicity/efficiency. <p>
     * 
     * Subclasses that have both a fixed width and height MAY set this to true
     * if they are typically used in a way that tends to make horizontal banding
     * a better (= tends to place same # of points in each band) binning
     * strategy.
     * <p>
     * 
     * If <tt>null</tt>, GChart uses a simple heuristic that assumes
     * that a brush that is wider than high implies that the developer
     * is trying to let the user distinguish finer y differences, and thus
     * our bands should separate points more finely (and hence allow for
     * faster band-indexed hit testing) if we use horizontal banding in
     * this case (and vertical otherwise). <p>
     * 
     * See the <tt>bandSeparatePoints</tt> method for more info.
     */
    Boolean isHorizontallyBanded = null;
    /*
     * Use smallest min band size, since I expect per band
     * cost to be small compared to per-point hit testing.
     * 
     */ 
    protected final int MIN_BAND_SIZE = 1;  // in pixels

    /*
     * Thickness (in pixels) of hit-test-bands used with this symbol type. <p>
     * 
     * Gets overriden for pie slice symbol types, which base thickness on pie
     * diameter.
     */
    protected double getBandThickness(PlotPanel pp, Symbol sym, int index) {
      double result;
      if (sym.isHorizontallyBanded())
        result = Math.max(MIN_BAND_SIZE, sym.getHeight(pp, index));
      else
        result = Math.max(MIN_BAND_SIZE, sym.getWidth(pp));
      return result;
    }

    // is overridden by pie slices, which use a different brush shape
    protected int getBrushHeight(Symbol sym) {
      int result = sym.getBrushHeight();
      return result;
    }

    // again, pie slices will override
    protected AnnotationLocation getBrushLocation(Symbol sym) {
      AnnotationLocation result = sym.getBrushLocation();
      return result;
    }

    // is overridden by pie slices, which use a different brush shape
    protected int getBrushWidth(Symbol sym) {
      int result = sym.getBrushWidth();
      return result;
    }

    /*
     * This symbol type provides a convenient anchor point at one of the
     * standard 9 named positions within the plot area. The actual x,y of the
     * points using this symbol type is ignored. Useful for placing annotations
     * around and along the perimeter of, or centered on, the plot panel.<p>
     * 
     * For example, chart decorations such as axis labels and footnotes
     * internally use symbols of this type (with appropriate setAnnotationXShift
     * or setAnnotationYShift adjustments to position the decoration
     * appropriately relative to the anchor point). End-users can use a curve
     * with this symbol type, along with a single point and appropriate
     * widget-based annotation, to place a table in the upper left corner of the
     * plot area, etc.
     */

    private static class AnnotationAnchor extends SymbolType {
      AnnotationLocation location;

      AnnotationAnchor(AnnotationLocation location) {
        super(0, 0, 0, 0, 0, 0);
        this.location = location;
      }

      /*
       * Actual curve symbol zero-sized so it does not
       * appear--it's just for positioning the annotation.
       *
       */ 
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return 0;
      }

      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return 0;
      }

      /*
       * Just return one of the standard 9 positions, or the mouse
       * coordinates, based on the location defined in the
       * constructor.
       * 
       */ 
      double getUpperLeftX(double width, double x, double xPrev,
          double xNext, double xMin, double xMax, double xMid,
          int xMouse) {
        double result;
        if (AnnotationLocation.AT_THE_MOUSE == location)
          result = (GChart.NAI == xMouse) ? Double.NaN : xMouse;
        else if (AnnotationLocation.AT_THE_MOUSE_SNAP_TO_X == location)
          result = (GChart.NAI == xMouse) ? Double.NaN : x;
        else if (AnnotationLocation.AT_THE_MOUSE_SNAP_TO_Y == location)
          result = (GChart.NAI == xMouse) ? Double.NaN : xMouse;
        else if (AnnotationLocation.NORTHWEST == location
            || AnnotationLocation.WEST == location
            || AnnotationLocation.SOUTHWEST == location)
          result = xMin;
        else if (AnnotationLocation.NORTHEAST == location
            || AnnotationLocation.EAST == location
            || AnnotationLocation.SOUTHEAST == location)
          result = xMax;
        else
          // NORTH, CENTER, or SOUTH
          result = (xMin + xMax) / 2;

        return result;

      }

      double getUpperLeftY(double height, double y, double yPrev,
          double yNext, double yMin, double yMax, double yMid,
          int yMouse) {
        double result;
        if (AnnotationLocation.AT_THE_MOUSE == location)
          result = (GChart.NAI == yMouse) ? Double.NaN : yMouse;
        else if (AnnotationLocation.AT_THE_MOUSE_SNAP_TO_X == location)
          result = (GChart.NAI == yMouse) ? Double.NaN : yMouse;
        else if (AnnotationLocation.AT_THE_MOUSE_SNAP_TO_Y == location)
          result = (GChart.NAI == yMouse) ? Double.NaN : y;
        else if (AnnotationLocation.NORTHWEST == location
            || AnnotationLocation.NORTH == location
            || AnnotationLocation.NORTHEAST == location)
          result = yMin;
        else if (AnnotationLocation.SOUTHWEST == location
            || AnnotationLocation.SOUTH == location
            || AnnotationLocation.SOUTHEAST == location)
          result = yMax;
        else
          // WEST, CENTER, or EAST
          result = (yMin + yMax) / 2;
        return result;
      }
    }

    // horizontal baseline-based bars
    private static class HBarBaseline extends SymbolType {
      HBarBaseline(int wm, int hm) {
        super(wm, hm, 0.5, 0.5, 0, 0, Boolean.TRUE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_HBAR_BASELINE_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return x - xMid;
      }

      @Override
      double getUpperLeftX(double width, double x, double xPrev,
          double xNext, double xMin, double xMax, double xMid,
          int xMouse) {
        return xMid;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return legendFontSize;
      }

    } // end of class HBarBaseline

    private static class HBarLeft extends SymbolType {
      HBarLeft(int wm, int hm) {
        super(wm, hm, 0.5, 0.5, 0.5, 0.5, Boolean.TRUE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_HBARLEFT_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return x - xMin;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return legendFontSize;
      }

    } // end of class HBarLeft

    private static class HBarRight extends SymbolType {
      HBarRight(int wm, int hm) {
        super(wm, hm, 0.5, 0.5, 0.5, 0.5, Boolean.TRUE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_HBARRIGHT_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return xMax - x;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return legendFontSize;
      }
    } // end of class HBarRight

    // draws a connected straight line between successive points
    private static class LineSymbolType extends SymbolType {
      LineSymbolType() {
        /*
         * Same constructor as BOX_CENTER, which centers line segments on the
         * points that they represent, as required.
         *
         */ 
        super(0, 0, 0, 0, 0, 0);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_LINE_FILL_SPACING;
      }

      @Override
      protected int defaultFillThickness() {
        return DEFAULT_LINE_FILL_THICKNESS;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return 3; // leaves room for a 1px border and a center
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return Math.max(3, legendFontSize);
      }

      /*
       * Draws an approximate line from x,y to nextX, nextY, using an
       * appropriate series of vertical (for a > 45 degree slope) or (for a < 45
       * degree slope) horizontal line segments. If a GWT canvas is available
       * and if continuous fill (fillSpacing==0) was requested the
       * lineTo,stroke,etc. of the canvas Widget are instead used to draw the
       * line. <p>
       * 
       * The canvas part of this code assumes/requires that points on a curve
       * are rendered in the order that they appear on the point list (GChart
       * will assure this), and that on the first point on the curve
       * <tt>prevX</tt> and <tt>prevY</tt>, and on the last point <tt>nextX</tt>
       * and <tt>nextY</tt>, are undefined (Double.NaN)
       */

      @Override
      void realizeSymbol(PlotPanel pp, GraphicsRenderingPanel grp,
          AnnotationRenderingPanel arp, Symbol symbol,
          Annotation annotation, int index, boolean clipPlotArea,
          boolean clipDecoratedChart, double renderPaddingFactor,
          boolean drawMainSymbol, double x, double y, double prevX,
          double prevY, double nextX, double nextY) {

        if ((x != x) || (y != y)) // this point undefined (isNaN)
          return;
        // else point itself is at least defined

        double spacing = symbol.getFillSpacing();
        int thickness = symbol.getFillThickness();
        GChartCanvasLite canvas = grp.getCanvas();

        if (0 == spacing && null != canvas && thickness > 0) {
          // when canvas is available and continuous fill requested,
          // BOX_CENTER and LINE work exactly the same way
          BOX_CENTER.realizeSymbol(pp, grp, arp, symbol, annotation,
        		  index, clipPlotArea, clipDecoratedChart, renderPaddingFactor,
              drawMainSymbol, x, y, prevX, prevY, nextX, nextY);
          return;
        }
        
        double xPx = pp.xToPixel(x);
        double yPx = pp.yToPixel(y, index);
        double nextXPx = pp.xToPixel(nextX);
        double nextYPx = pp.yToPixel(nextY, index);

        if (nextX == nextX && nextY == nextY && // next point defined
            thickness > 0 && // not a zero thickness connection
            (x != nextX || y != nextY)) { // this/next are distinct
          // draw HTML-element rendered line segment

          // Continuous fill not supported; 1px is reasonable approx.
          if (0 == spacing)
            spacing = 1;
          double deltaX = nextXPx - xPx;
          double deltaY = nextYPx - yPx;
          boolean dXIsShorter = deltaX * deltaX < deltaY * deltaY;
          /*
           * Increasing width by 1 adds half px on each edge
           * to heal the occasional roundoff-induced gap
           * 
           */ 
          final double EPS = 1;
          /*
           * TODO: the case in which the connecting line does not intersect the
           * plot area, and off-plot-area points are not being drawn is handled
           * very inefficiently, and not entirely correctly, by trying to draw
           * the entire line and excluding each segment as we attempt to draw
           * it.  Need to compute intersecting sub-line-segment and just draw
           * that instead, ignoring lines with no intersecting segments
           * completely. Can make a huge difference with lots of off-chart
           * points, such as a deliberately narrowed x axis range.
           * 
           */ 
          if (deltaX == 0) { // special-case of vertical line
            realizeOneImageOfSymbol(pp, grp, arp, symbol, null,
                index, clipPlotArea, clipDecoratedChart, renderPaddingFactor,
                xPx, 0.5 * (yPx + nextYPx), Double.NaN,
                Double.NaN, nextXPx, nextYPx, thickness,
                Math.abs(nextYPx - yPx) + EPS);
          } else if (deltaY == 0) { // special case of horizontal line
            realizeOneImageOfSymbol(pp, grp, arp, symbol, null,
            		index, clipPlotArea, clipDecoratedChart, renderPaddingFactor,
                0.5 * (xPx + nextXPx), yPx, Double.NaN,
                Double.NaN, nextXPx, nextYPx,
                Math.abs(nextXPx - xPx) + EPS, thickness);
          } else if (dXIsShorter) { // series of vertical segments
            double xMin = (xPx < nextXPx) ? xPx : nextXPx;
            double xMax = (xPx > nextXPx) ? xPx : nextXPx;
            double yAtXMin = (xPx < nextXPx) ? yPx : nextYPx;
            double yAtXMax = (xPx > nextXPx) ? yPx : nextYPx;

            double xiPrev = xMin;
            double yiPrev = yAtXMin;
            double xi = xiPrev;
            double yi = yiPrev;
            // round up to err on side of providing more detail
            int N = (int) Math.ceil((xMax - xMin) / spacing);
            double dy = Math.abs((yAtXMax - yAtXMin) / N) + EPS;
            for (int i = 1; i <= N; i++) {
              xi = xMin + i * (xMax - xMin) / N;
              yi = yAtXMin + i * (yAtXMax - yAtXMin) / N;
              realizeOneImageOfSymbol(pp, grp, arp, symbol, null,
            		  index, clipPlotArea, clipDecoratedChart,
                  renderPaddingFactor, 0.5 * (xiPrev + xi),
                  0.5 * (yiPrev + yi), Double.NaN,
                  Double.NaN, nextXPx, nextYPx, thickness, dy);
              xiPrev = xi;
              yiPrev = yi;
            }
          } else { // dY is shorter. Series of horizontal segments
            double yMin = (yPx < nextYPx) ? yPx : nextYPx;
            double yMax = (yPx > nextYPx) ? yPx : nextYPx;
            double xAtYMin = (yPx < nextYPx) ? xPx : nextXPx;
            double xAtYMax = (yPx > nextYPx) ? xPx : nextXPx;

            double xiPrev = xAtYMin;
            double yiPrev = yMin;
            double xi = xiPrev;
            double yi = yiPrev;
            int N = (int) Math.ceil((yMax - yMin) / spacing);
            double dx = Math.abs((xAtYMax - xAtYMin) / N) + EPS;
            for (int i = 1; i <= N; i++) {
              yi = yMin + i * (yMax - yMin) / N;
              xi = xAtYMin + i * (xAtYMax - xAtYMin) / N;
              realizeOneImageOfSymbol(pp, grp, arp, symbol, null,
            		  index, clipPlotArea, clipDecoratedChart,
                  renderPaddingFactor, 0.5 * (xiPrev + xi),
                  0.5 * (yiPrev + yi), Double.NaN,
                  Double.NaN, nextXPx, nextYPx, dx, thickness);
              xiPrev = xi;
              yiPrev = yi;
            }
          }
        }

        // the "main" symbol (the one on the (x,y) point itself) is
        // rendered last to put it on top of interpolated images
        if (drawMainSymbol) {
          double w = symbol.getWidth(pp);
          double h = symbol.getHeight(pp, index);
          realizeOneImageOfSymbol(pp, grp, arp, symbol, annotation,
        		  index, clipPlotArea, clipDecoratedChart, renderPaddingFactor, xPx,
              yPx, Double.NaN, Double.NaN, nextXPx, nextYPx, w, h);
        }
      } // realizeSymbol

    }

    /*
     * Symbols that are assigned this symbol type can be used to represent a pie
     * chart slice.
     * <p>
     * 
     * The pivot point (center of containing pie) is at the x,y location of the
     * point. Typically, only a single point per pie-slice curve is used
     * (multiple points simply translate the same pie slice symbol to another
     * position, such behavior is useful if you want to use a pie slice as a
     * traditional curve symbol, but it isn't needed for a typical pie chart).
     * <p>
     * 
     * The initial angle and angle subtended by the slice are specified by the
     * <tt>pieSliceOrientation</tt> and <tt>pieSliceSize</tt> properties of the
     * host <tt>Symbol</tt> (these properties only have meaning with pie slice
     * symbol types). Typically, several curves share a common pie center point
     * (x,y) and have orientations and sizes that are coordinated so that the
     * slices fit together to form a single complete pie. GChart facilitates
     * this by choosing (by default) the next slice's orientation so that it is
     * adjacent to the preceeding slice. However, other useful idioms include,
     * for example, adjusting the x,y pivots to produce "exploded pie charts",
     * or using a single slice that fills up the entire pie as a disc-like
     * alternative to <tt>BOX_CENTER</tt>. <p>
     * 
     * The radius of the slice is chosen as the radius such that the rectangle
     * defined by the hosting Symbol's width and height just barely fits within
     * a circle with that radius. This convention allows users to define the pie
     * radius in terms of the x model coordinates, y model coordinates, or in
     * pixels, as desired. <p>
     * 
     * The host <tt>Symbol</tt>'s fillSpacing and fillThickness properties,
     * along with horizontallyShaded and verticallyShaded properties of this
     * SymbolType, govern how the slice is filled in.
     * 
     * For more information with example code, see the discussion under the
     * {@link #PIE_SLICE_OPTIMAL_SHADING PIE_SLICE_OPTIMAL_SHADING} symbol type.
     */

    private static class PieSliceSymbolType extends SymbolType {
      private boolean horizontallyShaded;
      private boolean verticallyShaded;
      private boolean optimallyShaded;

      PieSliceSymbolType(boolean horizontallyShaded,
          boolean verticallyShaded, boolean optimallyShaded,
          double pixelPadLeft, double pixelPadRight,
          double pixelPadTop, double pixelPadBottom) {
        // same as BOX_SOUTHEAST (allows shading bars to be
        // easily positions by their upper left corners):
        super(1, 1, pixelPadLeft, pixelPadRight, pixelPadTop,
            pixelPadBottom);

        this.horizontallyShaded = horizontallyShaded;
        this.verticallyShaded = verticallyShaded;
        this.optimallyShaded = optimallyShaded;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_PIE_SLICE_HOVER_LOCATION;
      }

      /*
       * For simplicity, pie slices are given the upper bound band thickness of
       * a slice that occupies the entire pie. <p>
       * 
       * The case where hit test banding is most needed: lots of very small full
       * pies on a single curve (pie used as circular alternative to a
       * rectangular point marker) won't suffer from this up-sizing
       * approximation, since it uses full pies anyway.
       */
      @Override
      protected double getBandThickness(PlotPanel pp, Symbol sym,
          int index) {
          double result = Math.max(MIN_BAND_SIZE,
                                   2 * sym.getPieSliceRadius(pp, index));
        return result;
      }

      /*
       * Pie slices use a special radially oriented brush, whose radial
       * dimension is the larger of the specified brush width and height. <p>
       * 
       * So, from the point of view of the banded/binned hit testing algorithm,
       * which works entirely with rectangles, treating the brush as a
       * square with sides equal to the larger of the brush width and height,
       * "gets you in the right bin" using the same bin testing algorithm as
       * for rectangular symbols.
       * <p>
       * 
       * Thus, regardless of if the pie uses horizontal or vertical hit test
       * banding, the as-if-rectangular brush used in binned/banded hit testing
       * is same square box, given by this method and its companion,
       * <tt>getBrushWidth</tt>, below. <p>
       * 
       * This code also relies on the fact that for pie slices, only the larger
       * of width, height has an impact on the more exact, slice/angle/radius
       * closeness testing that is applied only to the subset of nearby points
       * determined by using the bins/bands hit test. So, making brush width and
       * height the same for pie slices doesn't cause any detail hit testing
       * errors (as it would for ordinary rectangular hit testing). <p>
       * 
       * TODO: Above works (I think) but is convoluted. Try to find a clearer,
       * simpler, way to express/handle pie slice differences. The special case
       * brush location handling for pies also seems a bit obscure.
       */
      @Override
      protected int getBrushHeight(Symbol sym) {
        int result = Math.max(sym.getBrushHeight(),
                              sym.getBrushWidth());
        return result;
      }

      // pie slices always use a centered brush location
      @Override
      protected AnnotationLocation getBrushLocation(Symbol sym) {
        AnnotationLocation result = AnnotationLocation.CENTER;
        return result;
      }

      // See comment on getBrushHeight above
      @Override
      protected int getBrushWidth(Symbol sym) {
        int result = Math.max(sym.getBrushHeight(),
                              sym.getBrushWidth());
        return result;
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_PIE_SLICE_FILL_SPACING;
      }

      @Override
      protected int defaultFillThickness() {
        return DEFAULT_PIE_SLICE_FILL_THICKNESS;
      }

      @Override
      protected String defaultHovertextTemplate() {
        return DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE;
      }

      // holds min/max x (cosine) and y (sine) over a "unit circle slice"
      private static class SliceLimits {
        double xMin;
        double xMax;
        double yMin;
        double yMax;
      }

      // Gets min/max sin, cos over slice cut from unit circle
      private SliceLimits getSliceLimits(double tMin, double tMax) {
        final SliceLimits result = new SliceLimits();
        double xMin = 0; // origin of 0,0 present in every slice
        double xMax = 0; // (it's the pie center/slice pivot point)
        double yMin = 0;
        double yMax = 0;
        double tmp = 0;
        // points where each edge intersects the arc could be
        // extremal points--include them too.
        tmp = Math.cos(tMin);
        xMin = (xMin < tmp) ? xMin : tmp;
        xMax = (xMax > tmp) ? xMax : tmp;
        tmp = Math.sin(tMin);
        yMin = (yMin < tmp) ? yMin : tmp;
        yMax = (yMax > tmp) ? yMax : tmp;

        tmp = Math.cos(tMax);
        xMin = (xMin < tmp) ? xMin : tmp;
        xMax = (xMax > tmp) ? xMax : tmp;
        tmp = Math.sin(tMax);
        yMin = (yMin < tmp) ? yMin : tmp;
        yMax = (yMax > tmp) ? yMax : tmp;

        // finally if slice includes any special extreme points
        // on the arc (namely, points of the arc that are
        // either due north, due south, due east or due west)
        // include those points in determining the min/max x
        // and min/max y included in the slice:
        double halfPi = Math.PI / 2.;
        for (int i = (int) Math.ceil(tMin / halfPi); i * halfPi < tMax; i++) {
          double t = i * halfPi;
          tmp = Math.cos(t);
          xMin = (xMin < tmp) ? xMin : tmp;
          xMax = (xMax > tmp) ? xMax : tmp;
          tmp = Math.sin(t);
          yMin = (yMin < tmp) ? yMin : tmp;
          yMax = (yMax > tmp) ? yMax : tmp;
        }

        result.xMin = xMin;
        result.xMax = xMax;
        result.yMin = yMin;
        result.yMax = yMax;

        return result;

      }

      @Override
      protected double getEdgeLeft(PlotPanel pp, Symbol symbol, double x,
          int index) {

        double r = symbol.getPieSliceRadius(pp, index);
        double theta0 = symbol.getPieSliceTheta0();
        double theta1 = symbol.getPieSliceTheta1();
        SliceLimits sl = getSliceLimits(theta1, theta0);
        double xPx = pp.xToPixel(x);
        // scale up the xMin on unit circle to get to left edge
        double result = xPx + sl.xMin * r;
        return result;
      }

      @Override
      protected double getEdgeRight(PlotPanel pp, Symbol symbol,
          double x, int index) {
        double r = symbol.getPieSliceRadius(pp, index);
        double theta0 = symbol.getPieSliceTheta0();
        double theta1 = symbol.getPieSliceTheta1();
        SliceLimits sl = getSliceLimits(theta1, theta0);
        double xPx = pp.xToPixel(x);
        // scale up the xMax on unit circle to get to right edge
        double result = xPx + sl.xMax * r;
        return result;
      }

      @Override
      protected double getEdgeTop(PlotPanel pp, Symbol symbol, double y,
          int index) {

        double r = symbol.getPieSliceRadius(pp, index);
        double theta0 = symbol.getPieSliceTheta0();
        double theta1 = symbol.getPieSliceTheta1();
        SliceLimits sl = getSliceLimits(theta1, theta0);
        double yPx = pp.yToPixel(y, index);
        // minus for the Cartesian to pixel-coord transform
        double result = yPx - sl.yMax * r;
        return result;
      }

      @Override
      protected double getEdgeBottom(PlotPanel pp, Symbol symbol,
          double y, int index) {

        double r = symbol.getPieSliceRadius(pp, index);
        double theta0 = symbol.getPieSliceTheta0();
        double theta1 = symbol.getPieSliceTheta1();
        SliceLimits sl = getSliceLimits(theta1, theta0);
        double yPx = pp.yToPixel(y, index);
        // minus for the Cartesian to pixel-coord transform
        double result = yPx - sl.yMin * r;
        return result;
      }

      // returns the y coordinate where a pie slice edge
      // intersects a given vertical line, or NaN if none.
      private static double yWherePieEdgeIntersectsVerticalLine(
          double xOfVerticalLine, double xPieCenter,
          double yPieCenter, double pieRadius, double pieEdgeAngle) {
        double result = Double.NaN;
        double dxToArc = pieRadius * Math.cos(pieEdgeAngle);
        if (dxToArc != 0) {
          // The fraction of the way (from pie center to pie perimeter
          // along the pie slice edge) that you must go to reach the
          // point at which the vertical line intersects with the pie
          // slice edge. For example, this fraction is 0.5 whenever the
          // vertical line bisects the pie slice edge.
          double t = (xOfVerticalLine - xPieCenter) / dxToArc;
          if (GChart.withinRange(t, 0, 1)) {
            result = yPieCenter - t * pieRadius
                * Math.sin(pieEdgeAngle);
          }
        }
        return result;
      }

      // returns the x coordinate where a pie slice edge
      // intersects a given horizontal line, or NaN if none.
      private static double xWherePieEdgeIntersectsHorizontalLine(
          double yOfHorizontalLine, double xPieCenter,
          double yPieCenter, double pieRadius, double pieEdgeAngle) {
        double result = Double.NaN;
        double dyToArc = pieRadius * Math.sin(pieEdgeAngle);
        if (dyToArc != 0) {
          // The fraction of the way (from pie center to pie perimeter
          // along the pie slice edge) that you must go to reach the
          // point at which the horizontal line intersects with the pie
          // slice edge. For example, this fraction is 0.5 whenever the
          // horizontal line bisects the pie slice edge.
          double t = (yPieCenter - yOfHorizontalLine) / dyToArc;
          if (GChart.withinRange(t, 0, 1)) {
            result = xPieCenter + t * pieRadius
                * Math.cos(pieEdgeAngle);
          }
        }
        return result;
      }

      /*
       * Returns the angle of a line extending from (0,0) to (x,y) in radians in
       * the standard range, 0 to 2*Pi. For example, a line pointing due east
       * such as (1,0) would return 0, one pointing due north such as (0,0.5)
       * would return Pi/2, one pointing due west such as (-4.13,0) would return
       * Pi and the point (1,1) returns Pi/4. <p>
       * 
       * x,y are in the ordinary cartesian coordinate system (not in the typical
       * graphics/pixel coordinates)
       */
      private static double angle(double x, double y) {
        double result = Double.NaN;
        if (x == 0) {
          if (y > 0)
            result = Math.PI / 2.;
          else if (y < 0)
            result = 3 * Math.PI / 2.;
        } else if (x > 0 && y >= 0)
          result = Math.atan(y / x);
        else if (x < 0 && y >= 0)
          result = Math.PI - Math.atan(-y / x);
        else if (x < 0 && y < 0)
          result = Math.PI + Math.atan(y / x);
        else if (x > 0 && y < 0)
          result = 2 * Math.PI - Math.atan(-y / x);

        return result;
      }

      // is the given angle between the two angles given?
      private static boolean angleInRange(double angle, double theta0,
          double theta1) {

        if (theta0 > theta1)
          return angleInRange(angle, theta1, theta0);
        // angle is in standard 0 to 2*Pi range, but thetas
        // can be "wrapped around" several negative
        // multiples of 2*Pi less than the standard range;
        // this loop brings angle into same range as thetas
        while (angle > theta1)
          angle -= 2 * Math.PI;

        boolean result = GChart.withinRange(angle, theta0, theta1);
        return result;
      }

      /*
       * 
       * @Override
       * 
       * The x, y coordinates at the "center" of the slice for hit testing
       * purposes.
       * 
       * During hit testing, if more than one symbol touches the brush, the
       * point whose center is closest to the mouse position is selected.
       * 
       * To simplify the calculation, that center point is taken to be the
       * center of the pie containing the slice, rather than the center of the
       * slice per se. Though not an ideal choice, it is unlikely to cause
       * significant deviations from user expectations, given how pie slices
       * tend to be used to compose full pies out of a series of non-overlapping
       * slices.
       */
     @Override
     protected double getCenterX(PlotPanel pp, Symbol symbol, int iPoint) {
        Curve.Point p = symbol.getParent().getPoint(iPoint);
        double result = pp.xToPixel(p.getX());
        return result;
      }

      /*
       * @Override
       * 
       * See comment on getCenterX above.
       */
      @Override
      protected double getCenterY(PlotPanel pp, Symbol symbol,
          int iPoint, int index) {
        Curve.Point p = symbol.getParent().getPoint(iPoint);
        double result = pp.yToPixel(p.getY(), index);
        return result;
      }

      /*
       * @Override
       * 
       * Pie slices redefine what constitutes intersection of the mouse-centered
       * brush and the rendered symbol to be: "mouse position within a
       * radially-expanded version of the slice". The pie radius is expanded by
       * half the larger dimension of the point selection brush.
       */
     @Override
     protected boolean isIntersecting(PlotPanel pp, Symbol symbol,
          int iPoint, int index, int xBrush, int yBrush,
          int brushWidth, int brushHeight) {

        boolean result = false;
        Curve.Point p = symbol.getParent().getPoint(iPoint);
        double x = p.getX(); // pie center point (slice pivot)
        double y = p.getY();
        double xPx = pp.xToPixel(x);
        double yPx = pp.yToPixel(y, index);
        double dx = xBrush - xPx;
        // - represents switch from graphics to cartesian coordinates
        double dy = -(yBrush - yPx);

        double rSquared = dx * dx + dy * dy;
        double angle = angle(dx, dy);
        // pie angles grow clockwise but radians counter-clockwise,
        // hence the odd "0 into max, 1 into min" mapping below.
        double thetaMax = symbol.getPieSliceTheta0();
        double thetaMin = symbol.getPieSliceTheta1();
        double rPiePlus = symbol.getPieSliceRadius(pp, index) + 0.5
            * Math.max(brushWidth, brushHeight);

        /*
         * Enforce a minimum slice angle for hit testing purposes, equivalent to
         * +/- 1 px of play along the arcs of tiny slices, to make them easier
         * to select:<p>
         * 
         * <pre> r*minDTheta = 1 px </pre>
         * 
         * This helps with tiny slices adjacent to large ones, but if several
         * tiny slices are adjacent to each other, or if both adjacent slices
         * come after the tiny slice in the curve order (e.g. the tiny slice is
         * the very first slice) it still won't be selectable. Developers can
         * switch curve order to get around this, but it's not ideal. <p>
         * 
         * TODO: Integrate a "closest to slice angle" criterion to resolve ties
         * when hit testing slices to provide a better hit testing behavior with
         * small or overlapping slices.
         */
        double minDTheta = (rPiePlus < 1) ? 1.0 : 1. / rPiePlus;
        if (thetaMax - thetaMin < 2 * minDTheta) {
          double thetaMid = 0.5 * (thetaMax + thetaMin);
          thetaMin = thetaMid - minDTheta;
          thetaMax = thetaMid + minDTheta;
        }
        if (rSquared <= rPiePlus * rPiePlus
            && angleInRange(angle, thetaMin, thetaMax))
          result = true;

        return result;
      }

      /*
       * Renders a pie slice symbol.
       *
       */
      @Override
      void realizeSymbol(PlotPanel pp, GraphicsRenderingPanel grp,
          AnnotationRenderingPanel arp, Symbol symbol,
          Annotation annotation, int index, boolean clipPlotArea,
          boolean clipDecoratedChart, double renderPaddingFactor,
          boolean drawMainSymbol, double x, double y, double prevX,
          double prevY, double nextX, double nextY) {

        if (!drawMainSymbol)
          return;
        double xPx = pp.xToPixel(x);
        double yPx = pp.yToPixel(y, index);
        double spacing = symbol.getFillSpacing();
        int thickness = symbol.getFillThickness();
        double r = symbol.getPieSliceRadius(pp, index);
        double theta0 = symbol.getPieSliceTheta0();
        double theta1 = symbol.getPieSliceTheta1();
        GChartCanvasLite canvas = grp.getCanvas();
        // x!=x is a faster isNaN
        if ((xPx != xPx) || (yPx != yPx))
          return; // undefined slice pivot point
        else if (clipPlotArea
		 && !paddedIntersects(renderPaddingFactor, 0, 0, 
                    pp.getXChartSize(), pp.getYChartSize(), xPx - r, 
                    yPx - r, xPx + r, yPx + r))
          return; // rect containing pie is off plot area
        else if (clipDecoratedChart) {
          int leftOfY = pp.getLeftOfYWidth();
          int titleThickness = pp.chartTitleThickness();
          if (!paddedIntersects(renderPaddingFactor, 0.0 - leftOfY,
              0.0 - titleThickness, 
              pp.getXChartSizeDecoratedQuickly() - leftOfY,
              pp.getYChartSizeDecoratedQuickly() - titleThickness,
              xPx - r, yPx - r, xPx + r, yPx + r))
            return; // rect containing pie is off decorated chart
        }
        // else bounding rectangle of pie containing the slice visible

        if (0 == spacing && null != canvas && thickness > 0) {
          // continuous fill pie slice and canvas is available

          /*
           * Solid fill pie slices implement the notion of internal vs external
           * borders a bit differently than rectangular symbols. <p>
           * 
           * Internal borders are always drawn "centered", that is, half
           * internal, half external. In part, this is because that is how
           * "stroke" of the canvas API does it, so it's easier to implement.
           * But mainly it is because, when you assemble several slices into a
           * full pie, a centered border, provided that each slice has the same
           * border color, is really the only choice that looks right (this is a
           * constraint of the geometry how the slices fit together into a pie).
           * <p>
           * 
           * External borders (negative border width) are drawn outside the
           * slice proper by doubling the thickness, and then over-filling the
           * internal part of the border by issuing the fill after, instead of
           * before, the border is drawn. Though external borders don't look
           * right within a pie (because of how the slices occlude each other's
           * borders) they can be handy for making slice selection borders that
           * are drawn entirely outside of the selected slice.
           */
          int borderWidth = symbol.getBorderWidth();
          int adjustedBorderWidth = (borderWidth >= 0) ? borderWidth
              : 2 * Math.abs(borderWidth);

          /*
           * With incubator's <tt>GWTCanvas</tt>, IE7 & Chrome draw 0 and 2*Pi
           * slices incorrectly. See issues #278 #282 for more information: <p>
           * 
           * http://code.google.com/p/google-web-toolkit-incubator/issues/detail?id=278
           * http://code.google.com/p/google-web-toolkit-incubator/issues/detail?id=282 <p>
           * 
           * Pies with > 500 px radii are unlikely (tried using 10000, but it
           * didn't work in Chrome, tried 1000, but it didn't work in IE7 in
           * Vista with the circle-selection cursor on the oil price simulation
           * chart.). Such almost but not quite full pie slices could be
           * inappropriately rendered as full pies due to this workaround with
           * pies with large radii. A lesser evil than dropping the entire slice
           * for full pies.
           */
          final double MIN_DTHETA = 1. / 500.0;
          final double MAX_DTHETA = 2 * Math.PI - MIN_DTHETA;

          // canvas measures angles clockwise from +x-axis;
          // our angles are counter-clockwise from +x-axis
          double dTheta = theta0 - theta1;
          double angleStart = 2 * Math.PI - theta0;
          double angleEnd = angleStart
              + Math.max(MIN_DTHETA, Math.min(dTheta,MAX_DTHETA));

          if (dTheta >= MIN_DTHETA || borderWidth < 0) {
            canvas.beginPath();
            canvas.setLineWidth(adjustedBorderWidth);

            canvas.arc(xPx - grp.x0, yPx - grp.y0, r, angleStart,
                angleEnd, false);
            if (dTheta <= MAX_DTHETA)
              canvas.lineTo(xPx - grp.x0, yPx - grp.y0);
            // else avoid "line to center" in full pies

            canvas.closePath();

            String borderColor = symbol.getBorderColor();
            String backgroundColor = symbol.getBackgroundColor();
            /*
             * XXX: The approach to transparent border/fill used below is to
             * simply not stroke the border or to not fill the inside of the
             * path. This isn't exactly right, because the region where the
             * border overlaps the filled area does not always become
             * transparent when it should. These errors likely won't be noticed
             * in most usage scenarios, and without the ability to replace
             * filled/stroked regions with transparent pixels (I don't think
             * GWTCanvas can do this?) there isn't an easy fix.
             */

            // non-negative borders fill before stroking (thus
            // stroke overwrites internal half of border)
            if (borderWidth >= 0 && thickness > 0
                && TRANSPARENT_BORDER_COLOR != backgroundColor
                // GWTCanvas throws an exception w "transparent"
                && "transparent" != backgroundColor) {
              canvas.setFillStyle(backgroundColor);
              canvas.fill();
            }

            // stroke whenever a border is present
            if (borderWidth != 0
                && TRANSPARENT_BORDER_COLOR != borderColor
                && "transparent" != borderColor) {
              canvas.setStrokeStyle(borderColor);
              canvas.stroke();
            }

            // negative borders fill AFTER stroking (thus zapping
            // the internal half of the stroked border).
            if (borderWidth < 0 && thickness > 0
                && TRANSPARENT_BORDER_COLOR != backgroundColor
                && "transparent" != backgroundColor) {
              canvas.setFillStyle(backgroundColor);
              canvas.fill();
            }
          }
          // else 0-sized slice, 0 or internal border, is just dropped
        } else {
          if (0 == spacing)
            spacing = 1;
          // if center point is on the chart, draw it:

          double prevXPx = pp.xToPixel(prevX);
          double prevYPx = pp.yToPixel(prevY, index);
          double nextXPx = pp.xToPixel(nextX);
          double nextYPx = pp.yToPixel(nextY, index);
          int nBands = (int) Math.round(r / spacing);
          /*
           * Holds positions at which the current vertical or horizontal
           * "gridline-like band" intersects the outter perimeter of the current
           * pie slice. These positions are used to define the location and size
           * of shading bars required for each pie slice.
           * 
           * Note: Although most pie slice perimeters are convex and thus have
           * perimeters that intersect a gridline in at most two points, pie
           * slices that take up more than half of the entire pie have
           * perimeters that can (across their pacman-like mouth) intersect a
           * gridline at up to four points.
           */
          final int MAX_PIE_SLICE_PERIMETER_INTERSECTIONS = 4;
          double[] p = new double[MAX_PIE_SLICE_PERIMETER_INTERSECTIONS];
          final double EPS = 0.5;
          SliceLimits sl = getSliceLimits(theta1, theta0);
          boolean optimalIsVertical = (sl.yMax - sl.yMin) > (sl.xMax - sl.xMin);
          boolean isFullPie = (symbol.getPieSliceSize() == 1.0);
          // perform any vertical shading that may be required:
          if (nBands > 0
              && (verticallyShaded || (optimallyShaded && optimalIsVertical))) {
            for (int i = (int) Math.round(nBands * sl.xMin); i < sl.xMax
                * nBands; i++) {
              int nP = 0;
              double dxPx = r * (i + 0.5) / nBands;
              double dyPx = Math.sqrt(r * r - dxPx * dxPx);
              // x of vertical line bisecting the shading band
              double xi = xPx + dxPx;
              // y-positions where this band crosses circle
              // perimeter
              double c1 = yPx - dyPx;
              double c2 = yPx + dyPx;
              // y-positions where this band crosses each slice
              // edge
              // (full pies don't have pie slice edges)
              double e1 = isFullPie ? Double.NaN
                  : yWherePieEdgeIntersectsVerticalLine(xi,
                  xPx, yPx, r, theta0);
              double e2 = isFullPie ? Double.NaN
                  : yWherePieEdgeIntersectsVerticalLine(xi,
                  xPx, yPx, r, theta1);
              // Exclude circle perimeter intercepts outside of
              // the slice. Note: Pixel y coordinates used in
              // browser increase going down, but cartesian y
              // coordinates used in trig functions increase
              // going up, hence the sign-flipping on second arg
              // of angle function below.
              if (angleInRange(angle(xi - xPx, yPx - c1), theta0,
                  theta1))
                p[nP++] = c1;
              // intersection points sorted by increasing y within
              // p[]
              if (e1 < e2) {
                // x!=x is a faster isNaN
                if (!(e1 != e1))
                  p[nP++] = e1;
                if (!(e2 != e2))
                  p[nP++] = e2;
              } else {
                if (!(e2 != e2))
                  p[nP++] = e2;
                if (!(e1 != e1))
                  p[nP++] = e1;
              }

              if (angleInRange(angle(xi - xPx, yPx - c2), theta0,
                  theta1))
                p[nP++] = c2;
              for (int j = 1; j < nP; j++) {
                // logic below avoids drawing a line across the
                // non-convex "pacman mouth" that occurs with
                // any
                // bigger-than-half-pie-sized slices, by
                // requiring that a line drawn from the pie
                // center to an interpolated point on each
                // shading bar forms an angle in the slice's
                // angular range. We use a point 30% rather than
                // 50% of the way inbetween to avoid ever
                // hitting the
                // center of the pie (where angle is ambiguous).
                //
                // Note that, due to roundoff error, you cannot
                // ALWAYS rely on the (mathematically correct)
                // fact that problematic bars always connect
                // p[1]
                // and p[2].
                if (Math.abs(theta0 - theta1) <= Math.PI
                    || angleInRange(
                    angle(
                    xi - xPx,
                    yPx
                    - (0.3 * p[j] + 0.7 * p[j - 1])),
                    theta0, theta1)) {
                  // widening of EPS pixels on either side
                  // fills in
                  // tiny intra-slice gaps (that can otherwise
                  // appear
                  // due to roundoff) by making each bar a tad
                  // bigger.
                  realizeOneImageOfSymbol(pp, grp, arp,
                      symbol, null, index, clipPlotArea,
                      clipDecoratedChart, renderPaddingFactor,
                      xi - 0.5 * thickness,
                      p[j - 1] - EPS, prevXPx, prevYPx,
                      nextXPx, nextYPx, thickness, p[j]
                      - p[j - 1] + 2 * EPS);
                }
              }
            }
          }

          // Now do any required horizontal shading. This is
          // basically the same as the code for vertical shading
          // above (w appropriate transposition/adjustments).
          if (nBands > 0
              && (horizontallyShaded || (optimallyShaded && !optimalIsVertical))) {
            for (int i = (int) Math.round(-nBands * sl.yMax); i < -nBands
                * sl.yMin; i++) {
              int nP = 0;
              double dyPx = r * (i + 0.5) / nBands;
              double dxPx = Math.sqrt(r * r - dyPx * dyPx);
              // y of the horizontal line bisecting the shading
              // band
              double yi = yPx + dyPx;

              // x-positions where this band crosses circle
              // perimeter
              double c1 = xPx - dxPx;
              double c2 = xPx + dxPx;

              // x-positions where this band crosses each slice
              // edge
              // (full pies don't have pie slice edges)
              double e1 = isFullPie ? Double.NaN
                  : xWherePieEdgeIntersectsHorizontalLine(yi,
                  xPx, yPx, r, theta0);
              double e2 = isFullPie ? Double.NaN
                  : xWherePieEdgeIntersectsHorizontalLine(yi,
                  xPx, yPx, r, theta1);
              // exclude circle perimeter intercepts outside of
              // the slice
              if (angleInRange(angle(c1 - xPx, yPx - yi), theta0,
                  theta1))
                p[nP++] = c1;

              // intersection points sorted by increasing x within
              // p[]
              if (e1 < e2) {
                // x!=x is a faster isNaN
                if (!(e1 != e1))
                  p[nP++] = e1;
                if (!(e2 != e2))
                  p[nP++] = e2;
              } else {
                if (!(e2 != e2))
                  p[nP++] = e2;
                if (!(e1 != e1))
                  p[nP++] = e1;
              }

              if (angleInRange(angle(c2 - xPx, yPx - yi), theta0,
                  theta1))
                p[nP++] = c2;

              for (int j = 1; j < nP; j++) {
                // c.f. comment on corresponding vertical code
                // above.
                if (Math.abs(theta0 - theta1) <= Math.PI
                    || angleInRange(angle(
                    (0.3 * p[j] + 0.7 * p[j - 1])
                    - xPx, yPx - yi),
                    theta0, theta1)) {
                  // widening of EPS pixels on either side
                  // fills in
                  // tiny intra-slice gaps that can sometimes
                  // appear
                  // by making slices just a tad bigger.
                  realizeOneImageOfSymbol(pp, grp, arp,
                      symbol, null, index, clipPlotArea,
                      clipDecoratedChart, renderPaddingFactor,
                      p[j - 1] - EPS,
                      yi - 0.5 * thickness, prevXPx,
                      prevYPx, nextXPx, nextYPx, p[j]
                      - p[j - 1] + 2 * EPS,
                      thickness);
                }
              }
            }
          }
        }

        // if the image has an attached label, realize that
        if (annotation != null
            && (annotation.getText() != null ||
                annotation.getWidget() != null)
            && annotation.getVisible()) {

          // plus x-axis, for shifts, always corresponds to
          // outward pointing radius that bisects the slice,
          // with positive y axis, for shifts, at a 90 degree
          // counter-clockwise rotation from this x. Basic
          // trigonometry and this spec yeilds lines below.
          double thetaMid = (theta0 + theta1) / 2.;
          double dX = annotation.getXShift();
          double dY = annotation.getYShift();
          double sinTheta = Math.sin(thetaMid);
          double cosTheta = Math.cos(thetaMid);
          AnnotationLocation loc = annotation.getLocation();
          if (null == loc)
            loc = defaultAnnotationLocation();
          // note: pixel Y increases down but yShift & "trig Y"
          // increase going up, which explains dY sign reversal
          arp.renderAnnotation(annotation,
              loc.decodePieLocation(thetaMid), 
              xPx + (r + dX) * cosTheta - dY * sinTheta, 
              yPx - (r + dX) * sinTheta - dY * cosTheta, 
              0, 0, symbol);
        }
      }
    } // end of class PieSliceSymbolType

    private static class VBarBottom extends SymbolType {
      VBarBottom(int wm, int hm) {
        super(wm, hm, 0.5, 0.5, 0.5, 0.5, Boolean.FALSE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_VBARBOTTOM_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return yMax - y;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return legendFontSize;
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }
    } // end of class VBarBottom

    private static class VBarBaseline extends SymbolType {
      VBarBaseline(int wm, int hm) {
        super(wm, hm, 0, 0, 0.5, 0.5, Boolean.FALSE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_VBAR_BASELINE_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return y - yMid;
      }

      @Override
      double getUpperLeftY(double height, double y, double yPrev,
          double yNext, double yMin, double yMax, double yMid,
          int yMouse) {
        return yMid;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return legendFontSize;
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }
    } // end of class VBarBaseline

    /**
     * Use vertical bars that extend from the top of the chart to each point on
     * the curve.
     */
    private static class VBarTop extends SymbolType {
      VBarTop(int wm, int hm) {
        super(wm, hm, 0.5, 0.5, 0.5, 0.5, Boolean.FALSE);
      }

      @Override
      protected double defaultFillSpacing() {
        return DEFAULT_BAR_FILL_SPACING;
      }

      @Override
      protected AnnotationLocation defaultHoverLocation() {
        return DEFAULT_VBARTOP_HOVER_LOCATION;
      }

      @Override
      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return y - yMin;
      }

      @Override
      int getIconHeight(int legendFontSize) {
        return legendFontSize;
      }

      @Override
      int getIconWidth(int legendFontSize) {
        return (int) Math.round(legendFontSize / 2.);
      }
    } // end of class VBarTop

    /**
     * Points on curves with this symbol type are positioned at the center of
     * the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the center of the plot area.
     * <p>
     * 
     * <small> <i>Position isn't everything, but it matters:</i> The
     * <tt>(x,y)</tt> position of points on curves that employ one of these
     * <tt>ANCHOR_*</tt> symbol types does not determine where the annotation is
     * placed. <i>But</i> it <i>could</i> determine if that annotation gets
     * clipped off the chart entirely. So if, as is frequently the case, you
     * never want your anchored annotations clipped off, use
     * <tt>(+/-Double.MAX_VALUE,
     * * +/-Double.MAX_VALUE)</tt> as the coordinates of each annotatated point.
     * Since GChart recognizes these special values as keywords the denote one
     * of the corners of the plot area, the points associated with these
     * annotations are always inside the plot area, and thus never clipped.
     * </small>
     * 
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     */
    public static SymbolType ANCHOR_CENTER = new AnnotationAnchor(
        AnnotationLocation.CENTER);

    /**
     * Points on curves with this symbol type are positioned at the center of
     * the right edge of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the center of the right edge of the plot
     * area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_EAST = new AnnotationAnchor(
        AnnotationLocation.EAST);

    /**
     * When passed to the <tt>setHoverAnnotationSymbolType</tt> method, this
     * symbol type enables <tt>setTitle</tt>-like,
     * "anchored at the mouse cursor" hover annotation positioning.
     * Specifically, hover annotions act as if they were annotations of 1px x
     * 1px points placed at the current mouse cursor position.
     * <p>
     * 
     * Because this and its related symbol types,
     * <tt>ANCHOR_MOUSE_SNAP_TO_X</tt> and <tt>ANCHOR_MOUSE_SNAP_TO_Y</tt>, are
     * intended only to facilitate positioning of hover-induced pop-up
     * annotations (via the <tt>setHoverAnnotationSymbolType</tt> method) I
     * cannot imagine a scenario where it would make sense to use them as the
     * symbol type of an ordinary, user defined, curve (if you find a use for
     * this, please let me know).
     * 
     * 
     * @see #ANCHOR_MOUSE_SNAP_TO_X ANCHOR_MOUSE_SNAP_TO_X
     * @see #ANCHOR_MOUSE_SNAP_TO_Y ANCHOR_MOUSE_SNAP_TO_Y
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * @see Symbol#setHoverLocation setHoverLocation
     * @see Symbol#setHoverAnnotationSymbolType setHoverAnnotationSymbolType
     * @see Symbol#setHovertextTemplate setHovertextTemplate
     * @see Symbol#setHoverXShift setHoverXShift
     * @see Symbol#setHoverYShift setHoverYShift
     * @see Symbol#setHoverWidget setHoverWidget
     */
    public static SymbolType ANCHOR_MOUSE = new AnnotationAnchor(
        AnnotationLocation.AT_THE_MOUSE);

    /**
     * The same as the ANCHOR_MOUSE symbol type, except that the x coordinate of
     * the rendered symbol is taken from the x coordinate of the point, rather
     * than the x coordinate of the mouse.
     * 
     * @see #ANCHOR_MOUSE ANCHOR_MOUSE
     * @see #ANCHOR_MOUSE_SNAP_TO_Y ANCHOR_MOUSE_SNAP_TO_Y
     * 
     */
    public static SymbolType ANCHOR_MOUSE_SNAP_TO_X = new AnnotationAnchor(
        AnnotationLocation.AT_THE_MOUSE_SNAP_TO_X);
    /**
     * The same as the ANCHOR_MOUSE symbol type, except that the y coordinate of
     * the rendered symbol is taken from the y coordinate of the point, rather
     * than the y coordinate of the mouse.
     * 
     * @see #ANCHOR_MOUSE ANCHOR_MOUSE
     * @see #ANCHOR_MOUSE_SNAP_TO_X ANCHOR_MOUSE_SNAP_TO_X
     * 
     */
    public static SymbolType ANCHOR_MOUSE_SNAP_TO_Y = new AnnotationAnchor(
        AnnotationLocation.AT_THE_MOUSE_SNAP_TO_Y);
    /**
     * Points on curves with this symbol type are positioned at the center of
     * the top edge of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the center of the top edge of the plot
     * area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_NORTH = new AnnotationAnchor(
        AnnotationLocation.NORTH);
    /**
     * Points on curves with this symbol type are positioned at the upper right
     * corner of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the upper right corner of the plot area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_NORTHEAST = new AnnotationAnchor(
        AnnotationLocation.NORTHEAST);

    /**
     * Points on curves with this symbol type are positioned at the upper left
     * corner of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the upper left corner of the plot area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_NORTHWEST = new AnnotationAnchor(
        AnnotationLocation.NORTHWEST);

    /**
     * Points on curves with this symbol type are positioned at the center of
     * the bottom edge of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the center of the bottom edge of the
     * plot area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_SOUTH = new AnnotationAnchor(
        AnnotationLocation.SOUTH);

    /**
     * Points on curves with this symbol type are positioned at the lower right
     * corner of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the lower right corner of the plot area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_SOUTHEAST = new AnnotationAnchor(
        AnnotationLocation.SOUTHEAST);

    /**
     * Points on curves with this symbol type are positioned at the lower left
     * corner of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the lower left corner of the plot area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_SOUTHWEST = new AnnotationAnchor(
        AnnotationLocation.SOUTHWEST);

    /**
     * Points on curves with this symbol type are positioned at the center of
     * the left edge of the plot area, and do not have a visible symbol.
     * <p>
     * 
     * Use this symbol type, along with the <tt>setAnnotationLocation</tt>,
     * <tt>setAnnotationXShift</tt> and <tt>setAnnotationYShift</tt> methods, to
     * position annotations relative to the center of the left edge of the plot
     * area.
     * 
     * @see Curve.Point#setAnnotationLocation setAnnotationLocation
     * @see Curve.Point#setAnnotationXShift setAnnotationXShift
     * @see Curve.Point#setAnnotationYShift setAnnotationYShift
     * @see #ANCHOR_CENTER ANCHOR_CENTER
     * 
     */
    public static SymbolType ANCHOR_WEST = new AnnotationAnchor(
        AnnotationLocation.WEST);

    /**
     * Use rectangles horizontally and vertically centered on each point of the
     * curve
     */
    public static SymbolType BOX_CENTER = new SymbolType(0, 0, 0, 0, 0, 0);
    /**
     * Use rectangles just to the right of, and vertically centered on, each
     * point of the curve
     */
    public static SymbolType BOX_EAST = new SymbolType(1, 0, 0.5, -0.5, 0,
        0);
    /**
     * Use rectangles just above, and horizontally centered on, each point of
     * the curve
     */
    public static SymbolType BOX_NORTH = new SymbolType(0, -1, 0, 0, -0.5,
        0.5);

    /**
     * Use rectangles just above, and to the right of, each point of the curve
     */
    public static SymbolType BOX_NORTHEAST = new SymbolType(1, -1, 0.5,
        -0.5, -0.5, 0.5);

    /**
     * Use rectangles just above and to the left of, each point of the curve
     */
    public static SymbolType BOX_NORTHWEST = new SymbolType(-1, -1, -0.5,
        0.5, -0.5, 0.5);

    /**
     * Use rectangles just below, and horizontally centered on, each point of
     * the curve
     */
    public static SymbolType BOX_SOUTH = new SymbolType(0, 1, 0, 0, 0.5,
        -0.5);

    /**
     * Use rectangles just below, and to the right of, each point of the curve
     */
    public static SymbolType BOX_SOUTHEAST = new SymbolType(1, 1, 0.5,
        -0.5, 0.5, -0.5);

    /**
     * Use rectangles just below, and to the left of, each point of the curve
     */
    public static SymbolType BOX_SOUTHWEST = new SymbolType(-1, 1, -0.5,
        0.5, 0.5, -0.5);

    /**
     * Use rectangles just to the left of, and vertically centered on, each
     * point of the curve
     */
    public static SymbolType BOX_WEST = new SymbolType(-1, 0, -0.5, 0.5, 0,
        0);
    /**
     * Use horizontal bars that extend from the x,y position associated with
     * each point, to the x position defined by the host <tt>Symbol</tt>'s
     * baseline property, and that are vertically centered on the data point.
     * 
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * @see Symbol Symbol
     * 
     */
    public static SymbolType HBAR_BASELINE_CENTER = new HBarBaseline(0, 0);
    /**
     * Use horizontal bars that extend from the x,y position associated with
     * each point, to the x position defined by the host <tt>Symbol</tt>'s
     * baseline property, and whose bottom edge passes through the data point.
     * 
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * @see Symbol Symbol
     * 
     */
    public static SymbolType HBAR_BASELINE_NORTH = new HBarBaseline(0, -1);
    /**
     * Use horizontal bars that extend from the x,y position associated with
     * each point, to the x position defined by the host <tt>Symbol</tt>'s
     * baseline property, and whose top edge passes through the data point.
     * 
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * 
     */
    public static SymbolType HBAR_BASELINE_SOUTH = new HBarBaseline(0, 1);
    /**
     * Use horizontal bars that extend from the right y-axis to each point on
     * the curve, and that are vertically centered on the point.
     */
    public static SymbolType HBAR_EAST = new HBarRight(1, 0);
    private static SymbolType line = new LineSymbolType();
    /**
     * @deprecated
     * 
     *             As of version 2.4, this symbol has been redefined to be
     *             synonomous with the LINE symbol type.
     *             <p>
     * 
     *             Prior to v2.4, this symbol drew a horizontal bar from each
     *             point to the x coordinate of the next point. Some
     *             applications may need to use a revised point set in order to
     *             produce the same curves using <tt>LINE</tt> that they used to
     *             produce with this symbol.
     *             <p>
     * 
     *             See the discussion within the {@link #VBAR_NEXT VBAR_NEXT}
     *             symbol for more information about why support for these
     *             vertically and horizontally constrained connecting line
     *             symbol types was dropped.
     * 
     * @see #LINE LINE
     * @see #HBAR_PREV HBAR_PREV
     * @see #VBAR_PREV VBAR_PREV
     * @see #VBAR_NEXT VBAR_NEXT
     * 
     */
    public static SymbolType HBAR_NEXT = line;
    /**
     * Use horizontal bars that extend from the right y-axis to each point on
     * the curve, and that are vertically just above the point.
     */
    public static SymbolType HBAR_NORTHEAST = new HBarRight(1, -1);

    /**
     * Use horizontal bars that extend from the left y-axis to each point on the
     * curve, and that are vertically just above point.
     */
    public static SymbolType HBAR_NORTHWEST = new HBarLeft(-1, -1);
    /**
     * @deprecated
     * 
     *             As of version 2.4, this symbol has been redefined to be
     *             synonymous with the LINE symbol type.
     *             <p>
     * 
     *             Prior to v2.4, this symbol drew a horizontal bar from each
     *             point to the x coordinate of the previous point. Some
     *             applications may need to use a revised point set in order to
     *             produce the same curves using <tt>LINE</tt> that they used to
     *             produce with this symbol.
     *             <p>
     * 
     *             See the discussion within the {@link #VBAR_NEXT VBAR_NEXT}
     *             symbol for more information about why support for these
     *             vertically and horizontally constrained connecting line
     *             symbol types was dropped.
     * 
     * @see #LINE LINE
     * @see #HBAR_NEXT HBAR_NEXT
     * @see #VBAR_PREV VBAR_PREV
     * @see #VBAR_NEXT VBAR_NEXT
     * 
     * 
     */
    public static SymbolType HBAR_PREV = line;
    /**
     * Use horizontal bars that extend from the right y-axis to each point on
     * the curve, and that are vertically just below the point.
     */
    public static SymbolType HBAR_SOUTHEAST = new HBarRight(1, 1);
    /**
     * Use horizontal bars that extend from the left y-axis to each point on the
     * curve, and that are vertically just below the point.
     */
    public static SymbolType HBAR_SOUTHWEST = new HBarLeft(-1, 1);

    /**
     * Use horizontal bars that extend from the left y-axis to each point on the
     * curve, and that are vertically centered on the point.
     */
    public static SymbolType HBAR_WEST = new HBarLeft(-1, 0);

    /**
     * This symbol type draws a continuous straight line between successive
     * individual data points. By default, the line is drawn via an appropriate
     * series of rectangular HTML elements, which can produce a "stair-step"
     * look at certain angles.
     * 
     * <small><blockquote> <i>Tip:</i> You can get order-of-magnitude faster,
     * and crisper, line charts by adding an external vector graphics library to
     * GChart via the <tt>setCanvasFactory</tt> method.</small>
     * <p>
     * 
     * Apart from this connecting line, the individual data points are displayed
     * exactly as they would have been displayed via BOX_CENTER.
     * <p>
     * 
     * Produces a connecting line similar to what could be produced via
     * BOX_CENTER with a fill spacing of 1px, except that it uses a more
     * efficient representation that merges vertical or horizontal "dot blocks"
     * into single HTML elements whenever possible.
     * <p>
     * 
     * To produce a line without showing the individual data points as separate
     * rectangular symbols, set width and height to match your symbol's
     * specified <tt>fillThickness</tt>.
     * 
     * @see #BOX_CENTER BOX_CENTER
     * @see #setCanvasFactory setCanvasFactory
     * @see Symbol#setFillThickness setFillThickness
     * 
     * 
     * 
     */
    public static SymbolType LINE = line;

    /**
     * @deprecated
     * 
     *             In GChart 2.3, you had to use this special symbol type to get
     *             GChart to use an external canvas library to draw crisp
     *             connecting lines.
     *             <p>
     * 
     *             As of GChart 2.5, the <tt>LINE</tt> symbol type will, by
     *             default, be rendered with whatever external canvas library
     *             you provide to GChart via the <tt>setCanvasFactory</tt>
     *             method.
     * 
     *             <p>
     * 
     *             So now, <tt>LINE_CANVAS</tt> is just another name for
     *             <tt>LINE</tt>. Please replace <tt>LINE_CANVAS</tt> with
     *             <tt>LINE</tt> in your code.
     *             <p>
     * 
     *             <small> Note that <tt>LINE</tt> only draws continuous lines
     *             if fill spacing (<tt>setFillSpacing</tt>) is <tt>0</tt>. With
     *             fill spacing > 0, it uses the old HTML-rendering method.
     *             Since <tt>0</tt> is now the new default fill spacing for the
     *             <tt>LINE</tt> symbol type, normally <tt>LINE</tt> works
     *             exactly like <tt>LINE_CANVAS</tt> did. But, if you had
     *             explicitly set the fill spacing, you may have to remove this
     *             specification, or set it to <tt>0</tt>, to get the same
     *             behavior you had before with <tt>LINE_CANVAS</tt>.
     *             <p>
     *             </small>
     * 
     * @see #LINE LINE
     * @see #setCanvasFactory setCanvasFactory
     * @see Symbol#setFillSpacing setFillSpacing
     * 
     */
    public static SymbolType LINE_CANVAS = line;
    /**
     * A symbol type that does not draw any main symbol. Use this symbol type
     * for curves whose points exist solely for the purpose of positioning their
     * associated annotations. Note that if <tt>fillThickness</tt> is non-zero,
     * any connecting dots between the points will still be drawn.
     * <p>
     * 
     * Equivalent to using the <tt>BOX_CENTER</tt> symbol type, but with the
     * host symbol's width and height both set to zero, so that no box symbol is
     * ever visible.
     * <p>
     * 
     * On Disabling hover selection feedback via <tt>NONE</tt>:
     * <p>
     * 
     * <blockquote><small> Note that if the border width of the host symbol is
     * negative, consistent with a 0 x 0 px <tt>BOX_CENTER</tt> symbol type, an
     * external border will still appear around the <tt>SymbolType.NONE</tt>
     * symbol. Because the default hover selection border width is <tt>-1</tt>,
     * when passing <tt>SymbolType.NONE</tt> to
     * <tt>setHoverSelectionSymbolType</tt>, you generally will also need to add
     * a code line such as:
     * 
     * <pre>
     * getCurve().getSymbol().setHoverSelectionBorderWidth(0);
     * </pre>
     * <p>
     * 
     * If your intention is to disable hover selection feedback, it's probably
     * easier to just use <tt>setHoverSelectionEnabled(false)</tt>, rather than
     * setting the hover selection symbol type to <tt>NONE</tt>.
     * 
     *</small></blockquote>
     * 
     * @see #BOX_CENTER BOX_CENTER
     * @see Symbol#setFillThickness setFillThickness
     * @see Symbol#setHoverSelectionSymbolType setHoverSelectionSymbolType
     * @see Symbol#setHoverSelectionEnabled setHoverSelectionEnabled
     */
    public static SymbolType NONE = new SymbolType(0, 0, 0, 0, 0, 0) {
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return 0;
      }

      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return 0;
      }

      int getIconHeight(int legendFontSize) {
        return 0;
      }

      int getIconWidth(int legendFontSize) {
        return 0;
      }

    };
    /**
     * Draws a pie slice whose area is shaded using horizontal bars.
     * 
     * <p>
     * The vertical distance between corresponding edges of successive bars is
     * governed by the symbol's fill spacing property; the height of each bar is
     * defined by the symbol's fill thickness property; the border and
     * background of each shading bar are defined by the symbol's border color,
     * border width, border style, and background color properties.
     * 
     * <p>
     * The radius of the pie slice (length of the non-arc sides of the slice) is
     * chosen such that a circle with this radius circumscribes the host
     * <tt>Symbol</tt>'s width/height determined rectangle. The slice pivot
     * point is defined by each point's x,y position, and the orientation and
     * size of the slice by the corresponding properties (see links below) of
     * the host <tt>Symbol</tt>.
     * 
     * @see Symbol#setFillSpacing setFillSpacing
     * @see Symbol#setFillThickness setFillThickness
     * @see Symbol#setBorderColor setBorderColor
     * @see Symbol#setBorderWidth setBorderWidth
     * @see Symbol#setBackgroundColor setBackgroundColor
     * @see Symbol#setPieSliceOrientation setPieSliceOrientation
     * @see Symbol#setPieSliceSize setPieSliceSize
     * @see Curve.Point#setX setX
     * @see Curve.Point#setY setY
     * @see #PIE_SLICE_VERTICAL_SHADING PIE_SLICE_VERTICAL_SHADING
     * @see #PIE_SLICE_HATCHED_SHADING PIE_SLICE_HATCHED_SHADING
     * @see #PIE_SLICE_OPTIMAL_SHADING PIE_SLICE_OPTIMAL_SHADING
     * @see Symbol Symbol
     * 
     * 
     */
    public static SymbolType PIE_SLICE_HORIZONTAL_SHADING = 
      new PieSliceSymbolType(true, false, false, 0, 0, 0, 0);
    /**
     * Draws a pie slice whose area is shaded using vertical bars.
     * <p>
     * 
     * The horizontal distance between corresponding edges of successive bars is
     * governed by the symbol's fill spacing property; the width of each bar is
     * defined by the symbol's fill thickness property; the border and
     * background of each shading bar are defined by the symbol's border color,
     * border width, and background color properties.
     * 
     * <p>
     * The radius of the pie slice (length of the non-arc sides of the slice) is
     * chosen such that a circle with this radius circumscribes the host
     * <tt>Symbol</tt>'s width/height determined rectangle. The slice pivot
     * point is defined by each point's x,y position, and the orientation and
     * size of the slice by the corresponding properties (see links below) of
     * the host <tt>Symbol</tt>.
     * 
     * @see Symbol#setFillSpacing setFillSpacing
     * @see Symbol#setFillThickness setFillThickness
     * @see Symbol#setBorderColor setBorderColor
     * @see Symbol#setBorderWidth setBorderWidth
     * @see Symbol#setBackgroundColor setBackgroundColor
     * @see Symbol#setPieSliceOrientation setPieSliceOrientation
     * @see Symbol#setPieSliceSize setPieSliceSize
     * @see Curve.Point#setX setX
     * @see Curve.Point#setY setY
     * @see #PIE_SLICE_HORIZONTAL_SHADING PIE_SLICE_HORIZONTAL_SHADING
     * @see #PIE_SLICE_HATCHED_SHADING PIE_SLICE_HATCHED_SHADING
     * @see #PIE_SLICE_OPTIMAL_SHADING PIE_SLICE_OPTIMAL_SHADING
     * @see Symbol Symbol
     * 
     * 
     */
    public static SymbolType PIE_SLICE_VERTICAL_SHADING = new PieSliceSymbolType(
        false, true, false, 0, 0, 0, 0);
    /**
     * Draws a pie slice whose area is shaded using both vertical and horizontal
     * bars, which produces a "cross-hatched" pattern.
     * <p>
     * 
     * The distance between corresponding edges of successive bars is governed
     * by the symbol's fill spacing property; the thickness of each bar is
     * defined by the symbol's fill thickness property; the border and
     * background of each shading bar are defined by the symbol's border color,
     * border width, border style, and background color properties.
     * 
     * <p>
     * The radius of the pie slice (length of the non-arc sides of the slice) is
     * chosen such that a circle with this radius circumscribes the host
     * <tt>Symbol</tt>'s width/height determined rectangle. The slice pivot
     * point (i.e. pie center) is defined by each point's x,y position, and the
     * orientation and size of the slice by the corresponding properties (see
     * links below) of the host <tt>Symbol</tt>.
     * 
     * @see Symbol#setFillSpacing setFillSpacing
     * @see Symbol#setFillThickness setFillThickness
     * @see Symbol#setBorderColor setBorderColor
     * @see Symbol#setBorderWidth setBorderWidth
     * @see Symbol#setBackgroundColor setBackgroundColor
     * @see Symbol#setPieSliceOrientation setPieSliceOrientation
     * @see Symbol#setPieSliceSize setPieSliceSize
     * @see Curve.Point#setX setX
     * @see Curve.Point#setY setY
     * @see #PIE_SLICE_VERTICAL_SHADING PIE_SLICE_VERTICAL_SHADING
     * @see #PIE_SLICE_HORIZONTAL_SHADING PIE_SLICE_HORIZONTAL_SHADING
     * @see #PIE_SLICE_OPTIMAL_SHADING PIE_SLICE_OPTIMAL_SHADING
     * @see Symbol Symbol
     * 
     * 
     */
    public static SymbolType PIE_SLICE_HATCHED_SHADING = new PieSliceSymbolType(
        true, true, false, 0, 0, 0, 0);
    /**
     * Draw a pie slice whose area is shaded using either vertical bars or
     * horizontal bars--whichever renders the slice more efficiently.
     * Specifically, pie slices that are wider than they are tall use horizontal
     * shading and pie slices that are taller than they are wide use vertical
     * shading. These choices minimize the the number of shading bars (and thus
     * memory and time) required to render the pie slice.
     * 
     * <p>
     * 
     * The distance between corresponding edges of successive bars is governed
     * by the symbol's fill spacing property; the thickness of each bar is
     * defined by the symbol's fill thickness property; the border and
     * background of each shading bar are defined by the symbol's border color,
     * border width, and background color properties.
     * <p>
     * 
     * The pie slice radius is always determined by the formula:
     * 
     * <p>
     * <blockquote>
     * 
     * <pre>
     * sqrt(symbolWidth &circ; 2 + symbolHeight &circ; 2) / 2
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * Here <tt>symbolWidth</tt> and <tt>symbolHeight</tt> are the pie slice
     * symbol's width and height, in pixels.
     * <p>
     * 
     * Note that this formula implies that the pie slice radius is the one
     * associated with the circle that circumscribes the symbol, that is, the
     * smallest circle that is big enough to completely contain the symbol's
     * width/height defined bounding rectangle. Equivalently, the length of the
     * pie slice radius equals the half the length of the diagonal across the
     * symbol's bounding rectangle.
     * 
     * <p>
     * 
     * To assure an integral number of shading bars and thus improve the visual
     * look of the pie chart, GChart automatically rounds the radius to the
     * nearest multiple of the specified <tt>fillSpacing</tt>. For example, if
     * the radius computed from the above formula were 96 pixels and the
     * <tt>fillSpacing</tt> were 10 pixels, GChart would actually use a radius
     * of 100 pixels.
     * 
     * <p>
     * 
     * <i>Tip:</i> To produce a pie slice with a radius, r, set the symbol's
     * height to 0, and its width to 2*r (or visa-versa). To specify the radius
     * in pixels, use the symbol's <tt>setWidth</tt> and <tt>setHeight</tt>
     * methods; to specify the radius in "model units" (which scale up or down
     * with the chart dimensions) use <tt>setModelWidth</tt> and
     * <tt>setModelHeight</tt> instead.
     * 
     * <p>
     * 
     * 
     * <p>
     * The slice pivot point (i.e. pie center) is defined by each point's x,y
     * position, and the orientation and size of the slice by the
     * <tt>setPieSliceOrientation</tt> and <tt>setPieSliceSize</tt> methods of
     * the host <tt>Symbol</tt>.
     * <p>
     * 
     * Creating a pie chart from such pie slices requires that you define a
     * separate curve for each slice, as illustrated in the code below:
     * 
     * {@code.sample ../../../../../../gcharttestapp/src/com/googlecode/gchart/gcharttestapp/client/GChartExample09.java}
     * 
     * <p>
     * 
     * Which produces this:
     * <p>
     * 
     * <img src="{@docRoot}/com/googlecode/gchart/client/doc-files/gchartexample09.png">
     * 
     * <p>
     * Note how, because <tt>PIE_SLICE_OPTIMAL_SHADING</tt> was used, vertical
     * or horizontal shading is automatically selected so as to minimize the
     * number of shading bars in each slice.
     * 
     * @see Symbol Symbol
     * @see Symbol#setFillSpacing setFillSpacing
     * @see Symbol#setFillThickness setFillThickness
     * @see Symbol#setBorderColor setBorderColor
     * @see Symbol#setBorderWidth setBorderWidth
     * @see Symbol#setBackgroundColor setBackgroundColor
     * @see Symbol#setPieSliceOrientation setPieSliceOrientation
     * @see Symbol#setPieSliceSize setPieSliceSize
     * @see Symbol#setWidth setWidth
     * @see Symbol#setHeight setHeight
     * @see Symbol#setModelWidth setModelWidth
     * @see Symbol#setModelHeight setModelHeight
     * @see Curve.Point#setX setX
     * @see Curve.Point#setY setY
     * @see #PIE_SLICE_VERTICAL_SHADING PIE_SLICE_VERTICAL_SHADING
     * @see #PIE_SLICE_HORIZONTAL_SHADING PIE_SLICE_HORIZONTAL_SHADING
     * @see #PIE_SLICE_HATCHED_SHADING PIE_SLICE_HATCHED_SHADING
     * 
     */
    public static SymbolType PIE_SLICE_OPTIMAL_SHADING = new PieSliceSymbolType(
        false, false, true, 0, 0, 0, 0);

    /**
     * Use vertical bars that extend from the x,y position associated with each
     * point, to the y position defined by the host <tt>Symbol</tt>'s baseline
     * property, and that are horizontally centered on the data point.
     * 
     * @see Symbol Symbol
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * 
     */
    public static SymbolType VBAR_BASELINE_CENTER = new VBarBaseline(0, 0);
    /**
     * Use vertical bars that extend from the x,y position associated with each
     * point, to the y position defined by the host <tt>Symbol</tt>'s baseline
     * property, and whose right edge passes through the data point.
     * 
     * @see Symbol Symbol
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * 
     */
    public static SymbolType VBAR_BASELINE_WEST = new VBarBaseline(-1, 0);
    /**
     * Use vertical bars that extend from the x,y position associated with each
     * point, to the y position defined by the host <tt>Symbol</tt>'s baseline
     * property, and whose left edge passes through the data point.
     * 
     * @see Symbol#setBaseline setBaseline
     * @see #HBAR_BASELINE_CENTER HBAR_BASELINE_CENTER
     * @see #HBAR_BASELINE_SOUTH HBAR_BASELINE_SOUTH
     * @see #HBAR_BASELINE_NORTH HBAR_BASELINE_NORTH
     * @see #VBAR_BASELINE_CENTER VBAR_BASELINE_CENTER
     * @see #VBAR_BASELINE_EAST VBAR_BASELINE_EAST
     * @see #VBAR_BASELINE_WEST VBAR_BASELINE_WEST
     * 
     */
    public static SymbolType VBAR_BASELINE_EAST = new VBarBaseline(1, 0);
    /**
     * @deprecated
     * 
     *             As of version 2.4, this symbol has been redefined to be
     *             synonomous with the LINE symbol type.
     *             <p>
     * 
     *             Prior to v2.4, this symbol drew a vertical bar from each
     *             point to the y coordinate of the next point. Some
     *             applications may need to use a revised point set in order to
     *             produce the same curves with <tt>LINE</tt> that they used to
     *             produce with this symbol.
     *             <p>
     * 
     *             Support was dropped because:
     * 
     *             <p>
     *             <ol>
     * 
     *             <li>Continued support would have complicated implementation
     *             of the new hover feedback system introduced with v2.4 (these
     *             are the only symbols whose hit-testing-related size depends
     *             on preceding or subsequent points).
     *             <p>
     * 
     *             <li>With the introduction of <tt>LINE</tt> the main reason
     *             for this, and related, vertically (or horizontally)
     *             constrained line drawing symbol types had been eliminated
     *             (had <tt>LINE</tt> existed at the beginning, these
     *             constrained line drawing symbol types would never have been
     *             added).
     *             <p>
     * 
     *             </ol>
     *             <p>
     * 
     *             Finally, note that if lines are vertical or horizontal, and
     *             solidly connected, <tt>LINE</tt> automatically collapses them
     *             into a single element, so no element-based efficiency losses
     *             need be associated with replacing curves using such
     *             rectilinear symbol types with equivalent curves rendered via
     *             the <tt>LINE</tt> symbol type.
     *             <p>
     * 
     * 
     * @see #HBAR_PREV HBAR_PREV
     * @see #HBAR_NEXT HBAR_NEXT
     * @see #LINE LINE
     * @see #VBAR_PREV VBAR_PREV
     * 
     */
    public static SymbolType VBAR_NEXT = line;

    /**
     * Use vertical bars that extend from the top of the chart to each point on
     * the curve, and are horizontally centered on the point.
     */
    public static SymbolType VBAR_NORTH = new VBarTop(0, -1);
    /**
     * Use vertical bars that extend from the top of the chart to each point on
     * the curve, and are horizontally to the right of the point.
     */
    public static SymbolType VBAR_NORTHEAST = new VBarTop(1, -1);

    /**
     * Use vertical bars that extend from the top of the chart to each point on
     * the curve, and are horizontally to the left of the point.
     */
    public static SymbolType VBAR_NORTHWEST = new VBarTop(-1, -1);
    /**
     * @deprecated
     * 
     *             As of version 2.4, this symbol has been redefined to be
     *             synonomous with the LINE symbol type.
     *             <p>
     * 
     *             Prior to v2.4, this symbol drew a vertical bar from each
     *             point to the y coordinate of the previous point. Some
     *             applications may need to use a revised point set in order to
     *             produce the same curves using <tt>LINE</tt> that they used to
     *             produce with this symbol.
     *             <p>
     * 
     *             See the discussion within the {@link #VBAR_NEXT VBAR_NEXT}
     *             symbol for more information about why support for these
     *             vertically and horizontally constrained connecting line
     *             symbol types was dropped.
     * 
     * @see #LINE LINE
     * @see #HBAR_PREV HBAR_PREV
     * @see #HBAR_NEXT HBAR_NEXT
     * @see #VBAR_NEXT VBAR_NEXT
     * 
     */

    public static SymbolType VBAR_PREV = line;

    /**
     * Use vertical bars that extend from the x-axis to each point on the curve,
     * and that are horizontally centered on the point.
     */
    public static SymbolType VBAR_SOUTH = new VBarBottom(0, 1);
    /**
     * Use vertical bars that extend from the x-axis to each point on the curve,
     * and that are horizontally to the right of the point.
     */
    public static SymbolType VBAR_SOUTHEAST = new VBarBottom(1, 1);

    /**
     * Use vertical bars that extend from the x-axis to each point on the curve,
     * and that are horizontally to the left of the point.
     */
    public static SymbolType VBAR_SOUTHWEST = new VBarBottom(-1, 1);
    /**
     * Represents a single x-axis grid-line. You can use this symbol to draw a
     * single vertical bar across the chart.
     * 
     */
    public static SymbolType XGRIDLINE = new SymbolType(0, 0, 0, 0, 0.5,
        0.5, Boolean.FALSE) {
      public double getAdjustedHeight(double height, double y,
          double yPrev, double yNext, double yMin, double yMax,
          double yMid) {
        return yMax - yMin;
      }

      public double getUpperLeftY(double height, double y, double yPrev,
          double yNext, double yMin, double yMax, double yMid,
          int yMouse) {
        return yMin;
      }

      int getIconHeight(int legendFontSize) {
        return legendFontSize;
      }

      int getIconWidth(int legendFontSize) {
        return 1;
      }

    };
    /**
     * Represents a single y-axis (or y2-axis) grid-line. You can use this
     * symbol to draw a single horizontal line (or bar) across the chart, for
     * example, to display an upper bound or control limit.
     * 
     */
    public static SymbolType YGRIDLINE = new SymbolType(0, 0, 0.5, 0.5, 0,
        0, Boolean.TRUE) {
      public double getAdjustedWidth(double width, double x,
          double xPrev, double xNext, double xMin, double xMax,
          double xMid) {
        return xMax - xMin;
      }

      public double getUpperLeftX(double width, double x, double xPrev,
          double xNext, double xMin, double xMax, double xMid,
          int xMouse) {
        return xMin;
      }

      int getIconHeight(int legendFontSize) {
        return 1;
      }

      int getIconWidth(int legendFontSize) {
        return legendFontSize;
      }

    };

    // play similar role as same-named fields of AnnotationLocation
    protected int heightMultiplier;
    protected int widthMultiplier;
    /*
     * If a symbol's left, right, top, or bottom edge represents the x or y
     * location associated with this symbol, the corresponding pixel paddings
     * are 0.
     * 
     * If a position 1/2 pixel to the right, left, below, or above
     * (respectively) those edges represents the position of the x or y in
     * question, the values are 0.5.
     * 
     * Why is this needed? Because GChart uses 1 px gridlines whose center
     * represents the point associated with the gridline, and to get a
     * corresponding edge to perfectly overlay a gridline when its associated
     * position is the same as that gridline, we need to associated the position
     * of the represented x or y coordinate not with the symbol's box edge
     * itself, but rather with a position 1/2 px towards the center of the
     * symbol. If you don't specify an extra half pixel for, say, a vertical
     * bar, it won't align right on top of gridlines when it has the same height
     * as the associated gridline. <p>
     * 
     * In effect, we deliberately add a 1/2 px error to certain symbols so that
     * they appear to line up perfectly with the gridlines.
     */
    protected double pixelPadLeft;
    protected double pixelPadRight;
    protected double pixelPadTop;
    protected double pixelPadBottom;

    // symbols are part of the internals of a GChart,
    // so only we should instantiate them.
    private SymbolType(int widthMultiplier, int heightMultiplier,
        double pixelPadLeft, double pixelPadRight, double pixelPadTop,
        double pixelPadBottom, Boolean isHorizontallyBanded) {
      validateMultipliers(widthMultiplier, heightMultiplier);
      this.widthMultiplier = widthMultiplier;
      this.heightMultiplier = heightMultiplier;
      this.pixelPadLeft = pixelPadLeft;
      this.pixelPadRight = pixelPadRight;
      this.pixelPadTop = pixelPadTop;
      this.pixelPadBottom = pixelPadBottom;
      this.isHorizontallyBanded = isHorizontallyBanded;
    }

    private SymbolType(int widthMultiplier, int heightMultiplier,
        double pixelPadLeft, double pixelPadRight, double pixelPadTop,
        double pixelPadBottom) {
      this(widthMultiplier, heightMultiplier, pixelPadLeft,
          pixelPadRight, pixelPadTop, pixelPadBottom, null);
    }

    double getAdjustedHeight(double height, double y, double yPrev,
        double yNext, double yMin, double yMax, double yMid) {
      return height;
    }

    double getAdjustedWidth(double width, double x, double xPrev,
        double xNext, double xMin, double xMax, double xMid) {
      return width;
    }

    /*
     * Pixel x-coordinate at center of bounding rectangle surrounding given
     * symbol rendered with this symbol type. <p>
     * 
     * This method defines the x-coordinate of the rendered symbol's
     * center-point (used for hit testing purposes) for all symbols except pie
     * slices.
     */
    protected double getCenterX(PlotPanel pp, Symbol symbol, double prevX,
        double x, double nextX) {
      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);
      double xPx = pp.xToPixel(x);
      double prevXPx = pp.xToPixel(prevX);
      double nextXPx = pp.xToPixel(nextX);
      double width = symbol.getWidth(pp);

      double symWidth = getAdjustedWidth(width, xPx, prevXPx, nextXPx,
          xMinPx, xMaxPx, xMidPx);
      if ((symWidth != symWidth))
        return Double.NaN;

      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      if ((xLeft != xLeft))
        return Double.NaN;

      double xCenter = xLeft + symWidth / 2.;

      return xCenter;

    }

    /*
     * Pixel x-coordinate at center of the symbol, used for hit-testing purposes
     * by rectangular symbol types. <p>
     * 
     * Overridden by pie slice symbol types.
     */
    protected double getCenterX(PlotPanel pp, Symbol symbol, int iPoint) {

      Curve c = symbol.getParent();
      Curve.Point p = c.getPoint(iPoint);
      double prevX = Double.NaN;
      double x = p.getX();
      double nextX = Double.NaN;
      if (iPoint > 0)
        prevX = c.getPoint(iPoint - 1).getX();
      if (iPoint + 1 < c.getNPoints())
        nextX = c.getPoint(iPoint + 1).getX();

      double result = getCenterX(pp, symbol, prevX, x, nextX);

      return result;
    }

    /*
     * Pixel y-coordinate at center of bounding rectangle surrounding given
     * symbol rendered with this symbol type. <p>
     * 
     * This method defines the y-coordinate of the rendered symbol's
     * center-point (used for hit testing purposes) for all symbols except pie
     * slices.
     */
    protected double getCenterY(PlotPanel pp, Symbol symbol, double prevY,
        double y, double nextY, int index) {

      // the cartesian data and pixel Y coordinates are
      // flipped, hence the (counter-intuitive) min/max
      // interchange below:
      double yMin = pp.getYMax(index);
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index);
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);
      double yPx = pp.yToPixel(y, index);
      double prevYPx = pp.yToPixel(prevY, index);
      double nextYPx = pp.yToPixel(nextY, index);
      double height = symbol.getHeight(pp, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return Double.NaN;

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return Double.NaN;

      double yCenter = yTop + symHeight / 2.;

      return yCenter;

    }

    /*
     * Pixel y-coordinate at center of the symbol, used for hit-testing purposes
     * by rectangular symbol types. <p>
     * 
     * Overridden by pie slice symbol types.
     */
    protected double getCenterY(PlotPanel pp, Symbol symbol, int iPoint, int index) {

      Curve c = symbol.getParent();
      Curve.Point p = c.getPoint(iPoint);
      double prevY = Double.NaN;
      double y = p.getY();
      double nextY = Double.NaN;
      if (iPoint > 0)
        prevY = c.getPoint(iPoint - 1).getY();
      if (iPoint + 1 < c.getNPoints())
        nextY = c.getPoint(iPoint + 1).getY();

      double result = getCenterY(pp, symbol, prevY, y, nextY, index);

      return result;
    }

    // pixel coordinate of left edge of symbol if rendered at given x
    // Note: this can actually be the right edge if the symbol
    // width is negative, as can occur with baseline-based bars
    protected double getEdgeLeft(PlotPanel pp, Symbol symbol, double x, int index) {
      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);
      double xPx = pp.xToPixel(x);
      double prevXPx = Double.NaN;
      double nextXPx = Double.NaN;
      double width = symbol.getWidth(pp);

      double symWidth = getAdjustedWidth(width, xPx, prevXPx, nextXPx,
          xMinPx, xMaxPx, xMidPx);
      if ((symWidth != symWidth))
        return Double.NaN;

      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      if ((xLeft != xLeft))
        return Double.NaN;
      double result = xLeft;
      return result;
    }

    // pixel coordinate of right edge of symbol if rendered at given x
    // Note: this can actually be the left edge if the symbol
    // width is negative, as can occur with baseline-based bars
    protected double getEdgeRight(PlotPanel pp, Symbol symbol, double x, int index) {
      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);
      double xPx = pp.xToPixel(x);
      double prevXPx = Double.NaN;
      double nextXPx = Double.NaN;
      double width = symbol.getWidth(pp);

      double symWidth = getAdjustedWidth(width, xPx, prevXPx, nextXPx,
          xMinPx, xMaxPx, xMidPx);
      if ((symWidth != symWidth))
        return Double.NaN;

      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      if ((xLeft != xLeft))
        return Double.NaN;

      double result = xLeft + symWidth;

      return result;

    }

    // pixel coordinate of top edge of symbol if rendered at given y
    // Note: this can actually be the bottom edge if the symbol
    // width is negative, as can occur with baseline-based bars
    protected double getEdgeTop(PlotPanel pp, Symbol symbol, double y, int index) {

      // the cartesian data and pixel Y coordinates are
      // flipped, hence the (counter-intuitive) min/max
      // interchange below:
      double yMin = pp.getYMax(index);
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index);
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);
      double yPx = pp.yToPixel(y, index);
      double prevYPx = Double.NaN;
      double nextYPx = Double.NaN;
      double height = symbol.getHeight(pp, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return Double.NaN;

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return Double.NaN;

      double result = yTop;

      return result;

    }

    // pixel coordinate of bottom edge of symbol if rendered at given y
    // Note: this can actually be the top edge if the symbol
    // width is negative, as can occur with baseline-based bars
    protected double getEdgeBottom(PlotPanel pp, Symbol symbol, double y, int index) {

      // the cartesian data and pixel Y coordinates are
      // flipped, hence the (counter-intuitive) min/max
      // interchange below:
      double yMin = pp.getYMax(index);
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index);
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);
      double yPx = pp.yToPixel(y, index);
      double prevYPx = Double.NaN;
      double nextYPx = Double.NaN;
      double height = symbol.getHeight(pp, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return Double.NaN;

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return Double.NaN;

      double result = yTop + symHeight;

      return result;

    }

    // gets edge that is furthest away from the point, horizontally
    // Note: for bar charts, this is the edge of the symbol
    // along the y-axis, y2-axis, or vertical baseline.
    protected double getEdgeOppositeHorizontally(PlotPanel pp,
        Symbol symbol, double x, int index) {
      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);
      double xPx = pp.xToPixel(x);
      double prevXPx = Double.NaN;
      double nextXPx = Double.NaN;
      double width = symbol.getWidth(pp);

      double symWidth = getAdjustedWidth(width, xPx, prevXPx, nextXPx,
          xMinPx, xMaxPx, xMidPx);
      if ((symWidth != symWidth))
        return Double.NaN;

      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      if ((xLeft != xLeft))
        return Double.NaN;

      double result = xLeft + symWidth;
      if (Math.abs(xLeft - xPx) > Math.abs(result - xPx))
        result = xLeft;

      return result;

    }

    // gets edge that is furthest away from the point, vertically
    // Note: for bar charts, this is the edge of the symbol
    // along the x-axis, x2-axis, or horizontal baseline.
    protected double getEdgeOppositeVertically(PlotPanel pp, Symbol symbol,
        double y, int index) {

      // the cartesian data and pixel Y coordinates are
      // flipped, hence the (counter-intuitive) min/max
      // interchange below:
      double yMin = pp.getYMax(index);
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index);
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);
      double yPx = pp.yToPixel(y, index);
      double prevYPx = Double.NaN;
      double nextYPx = Double.NaN;
      double height = symbol.getHeight(pp, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return Double.NaN;

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return Double.NaN;

      double result = yTop + symHeight;
      if (Math.abs(yTop - yPx) > Math.abs(result - yPx))
        result = yTop;

      return result;

    }

    /*
     * Determines if a symbol, rendered at the specified position (and with the
     * given positions of the previous and subsequent points, and the y-axis on
     * which it is rendered) intersects with a given rectangle.
     */
    private boolean isIntersecting(PlotPanel pp, Symbol symbol,
        double prevX, double x, double nextX, double prevY, double y,
        double nextY, int index, double top, double right,
        double bottom, double left) {

      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);
      double xPx = pp.xToPixel(x);
      double prevXPx = pp.xToPixel(prevX);
      double nextXPx = pp.xToPixel(nextX);
      double width = symbol.getWidth(pp);

      double symWidth = getAdjustedWidth(width, xPx, prevXPx, nextXPx,
          xMinPx, xMaxPx, xMidPx);
      if ((symWidth != symWidth))
        return false;

      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      if ((xLeft != xLeft))
        return false;

      // note: symWidth can be negative.
      if (Math.max(xLeft, xLeft + symWidth) < left)
        return false; // symbol is entirely to left of rectangle
      else if (Math.min(xLeft, xLeft + symWidth) > right)
        return false; // symbol is entirely to right of rectangle
      // else brush and symbol have overlapping x-intervals

      // the cartesian data and pixel Y coordinates are flipped,
      // hence the (counter-intuitive) min/max interchange below:
      double yMin = pp.getYMax(index);
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index);
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);
      double yPx = pp.yToPixel(y, index);
      double prevYPx = pp.yToPixel(prevY, index);
      double nextYPx = pp.yToPixel(nextY, index);
      double height = symbol.getHeight(pp, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return false;

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return false;

      // note: symHeight can be negative.
      if (Math.max(yTop, yTop + symHeight) < top)
        return false; // symbol is entirely above rectangle
      else if (Math.min(yTop, yTop + symHeight) > bottom)
        return false; // symbol is entirely below the rectangle
      // else rectangle and symbol have overlapping y-intervals

      // overlapping x and y intervals ==> rectangle intersects symbol
      return true;

    }

    /*
     * Determines if a symbol, when rendered at a given point, intersects with a
     * "rectangular brush". <p>
     * 
     * This brush is typically centered at the current mouse position, and
     * allows the user to select the point on a curve, the pie slice, etc. for
     * which hover feedback will be displayed. <p>
     * 
     * This method gets overridden for pie slices (due to their non-rectangular
     * shape).
     */
    protected boolean isIntersecting(PlotPanel pp, Symbol symbol,
        int iPoint, int index, int xBrush, int yBrush,
        int brushWidth, int brushHeight) {

      Curve c = symbol.getParent();
      Curve.Point p = c.getPoint(iPoint);
      double prevX = Double.NaN;
      double x = p.getX();
      double nextX = Double.NaN;
      double prevY = Double.NaN;
      double y = p.getY();
      double nextY = Double.NaN;
      if (iPoint > 0) {
        prevX = c.getPoint(iPoint - 1).getX();
        prevY = c.getPoint(iPoint - 1).getY();
      }
      if (iPoint + 1 < c.getNPoints()) {
        nextX = c.getPoint(iPoint + 1).getX();
        nextY = c.getPoint(iPoint + 1).getY();
      }

      // Treat mouse cursor as if it were a 0x0 pixel symbol
      // centered at xBrush, yBrush to which an annotation of
      // the width, height of the brush is attached.
      int top = symbol.getBrushLocation().getUpperLeftY(yBrush,
          brushHeight, 0);
      int bottom = top + brushHeight;
      int left = symbol.getBrushLocation().getUpperLeftX(xBrush,
          brushWidth, 0);
      int right = left + brushWidth;

      
      boolean result = isIntersecting(pp, symbol, prevX, x, nextX, prevY,
          y, nextY, index, top, right, bottom, left);

      return result;
    }

    // width of border of symbol displayed in legend key
    int getIconBorderWidth(int legendFontSize, double symBorderFraction) {
      int result = 0;
      if (symBorderFraction > 0) {
        result = (int) Math.max(1.0, Math.floor(symBorderFraction
            * Math.min(getIconWidth(legendFontSize),
            getIconHeight(legendFontSize))));
      }
      return result;
    }

    int getIconHeight(int legendFontSize) {
      return (int) Math.round(0.75 * legendFontSize);
    }

    int getIconWidth(int legendFontSize) {
      return (int) Math.round(0.75 * legendFontSize);
    }

    double getUpperLeftX(double width, double x, double xPrev,
        double xNext, double xMin, double xMax, double xMid, int xMouse) {
      double adjWidth = getAdjustedWidth(width, x, xPrev, xNext, xMin,
          xMax, xMid);
      double result = x + (0.5 * (widthMultiplier - 1)) * adjWidth;
      return result;
    }

    double getUpperLeftY(double height, double y, double yPrev,
        double yNext, double yMin, double yMax, double yMid, int yMouse) {
      double adjHeight = getAdjustedHeight(height, y, yPrev, yNext, yMin,
          yMax, yMid);
      double result = y + (0.5 * (heightMultiplier - 1)) * adjHeight;
      return result;

    }

    protected AnnotationLocation defaultAnnotationLocation() {
      // return AnnotationLocation.SOUTH;
      AnnotationLocation result = defaultHoverLocation();
      return result;
    }

    // fillSpacing to use when a symbol's fillSpacing is Double.NaN
    protected double defaultFillSpacing() {
      return DEFAULT_SYMBOL_FILL_SPACING;
    }

    // fillThickness to use when a symbol's fillThickness is
    // GChart.NAI
    protected int defaultFillThickness() {
      return DEFAULT_SYMBOL_FILL_THICKNESS;
    }

    // symbol-type-specific default hovertextTemplate
    protected String defaultHovertextTemplate() {
      return DEFAULT_HOVERTEXT_TEMPLATE;
    }

    // symbol-type-specific default location of hover feedback
    protected AnnotationLocation defaultHoverLocation() {
      return DEFAULT_HOVER_LOCATION;
    }

    /*
     * Unmanaged images. Supports older code that simply zaps/recreates each
     * image, relying on browser's garbage collector to deal with the reuse
     * issue (that's slower).
     */
    private Image createImage(Symbol symbol, double width, double height,
        int borderWidth, String url) {

      Image result = new Image(url);
      // if smaller of width, height is at least twice
      // the border width, border width is used as is, otherwise,
      // it's replaced with half the smaller of width, height:
      int cappedBW = (int) ((2 * borderWidth <= ((width < height) ? width
          : height)) ? borderWidth : (((width < height) ? width
          : height) / 2));

      String borderColor = symbol.getBorderColorCSS();
      // If border was too big to fit inside rectangle, since GChart
      // borders are uniform around the rectangle, odd-sized
      // dimensions can leave a single "leftover" 1px inside the
      // border. Set background to the border's color so that the
      // border, in effect, takes up the entire rectangle.
      String backgroundColor = (cappedBW == borderWidth) ? symbol
          .getBackgroundColorCSS() : borderColor;
      // In principle, x,y position should also change with transparency
      // emulation in some cases. But these images are only used in
      // tables on the legend key, where they are always centered, so
      // that doesn't matter.

      if (TRANSPARENT_BORDER_COLOR == borderColor) {// transparency emulation
        if (cappedBW > 0) {
          // to emulate an internal transparent border using a 0 width
          // border, we need to shrink the size by twice the amount
          // of the border.
          height -= 2 * cappedBW; // shrink size
          width -= 2 * cappedBW;
        }
        // else, external border is just eliminated, no adjustment
        // needed
        cappedBW = 0;
        borderColor = "transparent";
        if (TRANSPARENT_BORDER_COLOR == backgroundColor)
          backgroundColor = "transparent";
      } else if (cappedBW > 0) {
        height -= 2 * cappedBW; // shrink size to incorporate
        width -= 2 * cappedBW; // impact of internal border.
      }
      GChart.setBackgroundColor(result, backgroundColor);
      GChart.setBorderColor(result, borderColor);
      GChart.setBorderStyle(result, symbol.getBorderStyle());
      GChart.setBorderWidth(result, Math.abs(cappedBW));
      result.setPixelSize((int) Math.round(width), (int) Math.round(height));
      return result;
    }

    // creates small image of symbol (used in the chart legend).
    Image createIconImage(Symbol symbol, int legendFontSize,
        double symBorderFraction) {
      Image result = createImage(symbol, getIconWidth(legendFontSize),
          getIconHeight(legendFontSize), getIconBorderWidth(
          legendFontSize, symBorderFraction), symbol.getImageURL());
      return result;
    }

    // are two one-dimensional ranges (x1...x2 and y1...y2) disjoint?
    static private boolean areDisjointRanges(double x1, double x2,
        double y1, double y2) {
      boolean result = false;
      if ((x1 < y1 && x2 < y1 && x1 < y2 && x2 < y2)
          || (y1 < x1 && y2 < x1 && y1 < x2 && y2 < x2))
        result = true;
      return result;
    }

    // do two rectangular regions intersect (left/right and/or
    // top/bottom can be interchanged and it still works)
    static boolean intersects(double left1, double top1, double right1,
        double bottom1, double left2, double top2, double right2,
        double bottom2) {
      boolean result = true;
      if (areDisjointRanges(left1, right1, left2, right2)
          || areDisjointRanges(top1, bottom1, top2, bottom2))
        result = false;
      return result;
    }

    // Does 2nd rectangle intersect the 'padded' first rectangle?
    // 
    // rpf is the "render padding factor", the fraction of the width
    // (height) of the first rectangle to add as padding to its left and
    // right (top and bottom) edges.
    static boolean paddedIntersects(double rpf, double left1, double top1,
        double right1, double bottom1, double left2, double top2,
        double right2, double bottom2) {
      boolean result = intersects(left1 - rpf * (right1 - left1), top1
          - rpf * (bottom1 - top1), right1 + rpf * (right1 - left1),
          bottom1 + rpf * (bottom1 - top1), left2, top2, right2,
          bottom2);
      return result;
    }

    /*
     * renders a single image that is part of a (possibly multi-image) symbol,
     * along with that image's annotation
     */
    protected void realizeOneImageOfSymbol(PlotPanel pp,
        GraphicsRenderingPanel grp, AnnotationRenderingPanel arp,
        Symbol symbol, Annotation annotation, int index,
        boolean clipPlotArea, boolean clipDecoratedChart, 
        double renderPaddingFactor, double xPx, double yPx, 
        double prevXPx, double prevYPx, double nextXPx, double nextYPx, 
        double width, double height) {

      double xMin = pp.getXMin();
      double xMax = pp.getXMax();
      double xMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((xMid != xMid))
        xMid = (xMin + xMax) / 2.;
      double xMinPx = pp.xToPixel(xMin);
      double xMaxPx = pp.xToPixel(xMax);
      double xMidPx = pp.xToPixel(xMid);

      int xShift = 0;
      if(annotation != null){
    	  xShift = annotation.getXShift();
      }
      double symWidth = getAdjustedWidth(width, xPx + xShift, prevXPx, nextXPx,
          xMinPx, xMaxPx + xShift, xMidPx);
      if ((symWidth != symWidth))
        return; // x!=x is a faster isNaN

      double xCenter = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
              xMaxPx, xMidPx, pp.getXMousePlotArea());
      xCenter += symWidth / 2.;
      
      xPx += xShift;
      xMaxPx += xShift;
      
      double xLeft = getUpperLeftX(width, xPx, prevXPx, nextXPx, xMinPx,
          xMaxPx, xMidPx, pp.getXMousePlotArea());
      //System.out.println(xLeft);
      if ((xLeft != xLeft))
        return; // x!=x is a faster isNaN

      // the data and pixel Y coordinates are flipped, hence
      // the (counter-intuitive) min/max interchange below:
      double yMin = pp.getYMax(index); 
    	  //onY2 ? pp.getY2Max() : pp.getYMax();
      double yMax = pp.getYMin(index); 
    	  //onY2 ? pp.getY2Min() : pp.getYMin();
      double yMid = symbol.getBaseline();
      // x!=x is a faster isNaN
      if ((yMid != yMid))
        yMid = (yMin + yMax) / 2.;
      double yMinPx = pp.yToPixel(yMin, index);
      double yMaxPx = pp.yToPixel(yMax, index);
      double yMidPx = pp.yToPixel(yMid, index);

      double symHeight = getAdjustedHeight(height, yPx, prevYPx, nextYPx,
          yMinPx, yMaxPx, yMidPx);
      if ((symHeight != symHeight))
        return; // x!=x is a faster isNaN

      double yTop = getUpperLeftY(height, yPx, prevYPx, nextYPx, yMinPx,
          yMaxPx, yMidPx, pp.getYMousePlotArea());
      if ((yTop != yTop))
        return; // x!=x is a faster isNaN

      double yCenter = yTop + symHeight / 2.;

      if (clipPlotArea
          && !paddedIntersects(renderPaddingFactor, 
          xMinPx, yMinPx, xMaxPx, yMaxPx,
          xLeft, yTop, xLeft + symWidth, yTop + symHeight))
        return; // image is completely off plot area, so skip it.
      else if (clipDecoratedChart) {
        int leftOfY = pp.getLeftOfYWidth();
        int titleThickness = pp.chartTitleThickness();
        if (!paddedIntersects(renderPaddingFactor, 
             xMinPx - leftOfY, yMinPx - titleThickness, 
             pp.getXChartSizeDecoratedQuickly() - leftOfY, 
             pp.getYChartSizeDecoratedQuickly() - titleThickness, 
             xLeft, yTop, xLeft + symWidth, yTop + symHeight))
          return; // image completely off decorated chart, so skip
      }
      // translate negative width, height to equivalent
      // positive values that image tags can handle
      int signWidth = 1;
      if (symWidth < 0) {
        xLeft = xLeft + symWidth;
        symWidth *= -1;
        signWidth = -1;
      }
      int signHeight = 1;
      if (symHeight < 0) {
        yTop = yTop + symHeight;
        symHeight *= -1;
        signHeight = -1;
      }

      // Positive pixel padding pushes the specified edge
      // outward from the center by the given amount, without
      // changing the location of the center the symbol.
      // Similarly, negative padding, pushes the edge inward.

      if (symWidth != 0) {
        xLeft -= pixelPadLeft;
        symWidth += pixelPadLeft + pixelPadRight;
      }
      // else, zero width, keep it that way (no padding added)

      if (symHeight != 0) {
        yTop -= pixelPadTop;
        symHeight += pixelPadTop + pixelPadBottom;
      }
      // else, zero height, keep it that way (no padding added)

      int borderWidth = symbol.getBorderWidth();
      // borderWidth < 0 ==> external border
      if ((symWidth > 0 && symHeight > 0) || borderWidth < 0) {
        grp.renderBorderedImage(symbol.getBackgroundColorCSS(), symbol
            .getBorderColorCSS(), symbol.getBorderStyle(),
            borderWidth, symWidth, symHeight, xLeft, yTop, symbol
            .getImageURL());
      }
      // if the image has an attached label, realize that
      if (annotation != null
          && (annotation.getText() != null || annotation.getWidget() != null)
          && annotation.getVisible()) {
        AnnotationLocation loc = annotation.getLocation();
        if (null == loc)
          loc = defaultAnnotationLocation();
        loc = AnnotationLocation.transform(loc, signWidth, signHeight);
        // Note: yShift follows orientation of cartesian y
        // Axis, which is 180 degrees different from pixel y
        // coordinates, hence the extra "-" below.
        //
        // signWidth, signHeight multipliers assure that shifts are
        // appropriately symetrical for bars above and below or to the
        // left or right of their baselines (only baseline bars use
        // negative symbol widths) For example, a yShift of
        // 10px would shift up for bars above the baseline, and down
        // for bars below the baseline, which is usually what you
        // want (e.g. placing labels above or below the bars).
        arp.renderAnnotation(annotation, loc, xCenter + signWidth
        		* annotation.getXShift(), yCenter - signHeight
            * annotation.getYShift(), symWidth, symHeight, symbol);
      }

    }

    // Distance from the point (x1, y1) to the point (x2, y2)
    protected double distance(double x1, double y1, double x2, double y2) {
      double result = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1)
          * (y2 - y1));
      return result;
    }

    /*
     * Renders the symbol at the specified position within the plot panel, by
     * creating appropriately positioned Canvas, Image and/or Label objects within the
     * given rendering panel. <p>
     * 
     * Most of the Image widgets will be replaced with drawing on the rendering
     * panel's dedicated canas Widget if an external canvas capability has been
     * bolted onto GChart, and continuous fill (fillSpacing == 0) has been
     * requested for the curve.
     * <p>
     * 
     * So-rendered symbols are used to represent: each point on a curve
     * (including any "filled" elements linearly interpolated between successive
     * points, such as, point-to-point connecting lines and
     * "areas under the curve") and (via special hidden system curves) axes,
     * gridlines, ticks, tick-labels, titles, footnotes, and the legend key. <p>
     * 
     * This method is overridden for pie slice symbols and the LINE symbol type.
     */
    // retains coord of an area chart's "filled to" axis/baseline
    static double oppositeEdge = Double.NaN;

    void realizeSymbol(PlotPanel pp, GraphicsRenderingPanel grp,
        AnnotationRenderingPanel arp, Symbol symbol,
        Annotation annotation, int index, boolean clipPlotArea,
        boolean clipDecoratedChart, double renderPaddingFactor, 
        boolean drawMainSymbol, double x, double y, 
        double prevX, double prevY, double nextX, double nextY) {

      if ((x != x) || (y != y)) // this point undefined (isNaN)
        return;

      double xPx = pp.xToPixel(x);
      double yPx = pp.yToPixel(y, index);
      double prevXPx = pp.xToPixel(prevX);
      double prevYPx = pp.yToPixel(prevY, index);
      double nextXPx = pp.xToPixel(nextX);
      double nextYPx = pp.yToPixel(nextY, index);
      double spacing = symbol.getFillSpacing();
      int thickness = symbol.getFillThickness();
      GChartCanvasLite canvas = grp.getCanvas();

      if (0 == spacing && null != canvas && thickness > 0) {
        // if canvas rendered
        if (null == isHorizontallyBanded) {
          /*
           * Continuous fill, canvas available, and not explicitly horizontally
           * or vertically banded. For example, BOX_* symbol types are not
           * explicitly oriented, but VBAR_* (vertically) and HBAR_*
           * (horizontally) are: use canvas to draw a straight line between
           * points. <p>
           * 
           * Code in this branch also gets executed by the LINE symbol type.
           */
          int borderWidth = symbol.getBorderWidth();
          // negative (external) border widens line by 2*|borderWidth|
          int externalLineWidth = (borderWidth >= 0) ? thickness
              : (thickness + 2 * Math.abs(borderWidth));
          int internalLineWidth = (borderWidth >= 0) ? Math.max(
              thickness - 2 * borderWidth, 0) : thickness;
          String borderColor = symbol.getBorderColor();
          String backgroundColor = symbol.getBackgroundColor();
          if (externalLineWidth > 0
              && ((TRANSPARENT_BORDER_COLOR != borderColor && 
                   "transparent" != borderColor) || 
                   (TRANSPARENT_BORDER_COLOR != backgroundColor && 
                    "transparent" != backgroundColor))) {
            if (prevX != prevX || prevY != prevY) {
              // first defined point after an undefined point ==> new
              // path (need to draw zero-thickness lines for possible
              // line endings user may have defined by overriding
              // beginPath)
              canvas.beginPath();
              canvas.moveTo(xPx - grp.x0, yPx - grp.y0);
            }
            if (nextX != nextX || nextY != nextY) {
              // last defined point before undefined point ==>
              // draw accumulated path
              if (TRANSPARENT_BORDER_COLOR != borderColor
                  && "transparent" != borderColor
                  && externalLineWidth > 0) {
                canvas.setStrokeStyle(borderColor);
                canvas.setLineWidth(externalLineWidth);
                canvas.stroke();
              }
              if (TRANSPARENT_BORDER_COLOR != backgroundColor
                  && "transparent" != backgroundColor
                  && internalLineWidth > 0) {
                canvas.setLineWidth(internalLineWidth);
                canvas.setStrokeStyle(backgroundColor);
                canvas.stroke();
              }
            } else
              // not at end of chain ==> add one more segment to the
              // path (need to draw doubled points for possibly "line
              // join" user may have defined via overriding beginPath)
              canvas.lineTo(nextXPx - grp.x0, nextYPx - grp.y0);
          }
          // else lines are 0-width or transparent, so not rendered
        } else {
          /*
           * Explicitly oriented bandedness occurs only for vert or horizontal
           * bars. x,y coordinates are connected into a path (as in a line
           * chart), and then that path is extended into a closed polygon by
           * adding a closing segment formed from an appropriate section of an
           * axis or baseline.
           */

          /*
           * Draw area interpolated between successive bars.
           * 
           * Note that the "opposite" edge could be a point on an x or y axis,
           * or on the curve's baseline, depending on the kind of bar chart
           * involved: it's the edge of the bar that is furthest from the x,y
           * point.
           */
          boolean closeStrokeAndFill = false;
          if (Boolean.FALSE == isHorizontallyBanded) {
            if (prevX != prevX || prevY != prevY) {
              // 1st point, or 1st point after a break in the line
              oppositeEdge = getEdgeOppositeVertically(pp,
                  symbol, y, index);
              canvas.beginPath();
              canvas.moveTo(xPx - grp.x0, oppositeEdge - grp.y0);
              canvas.lineTo(xPx - grp.x0, yPx - grp.y0);
            }
            if (nextX != nextX || nextY != nextY) {
              // last point, or last point before a break in the line
              canvas.lineTo(xPx - grp.x0, oppositeEdge - grp.y0);
              closeStrokeAndFill = true;
            } else {
              canvas.lineTo(nextXPx - grp.x0, nextYPx - grp.y0);
            }
          } else {

            if (prevX != prevX || prevY != prevY) {
              // 1st point, or 1st point after a break in the line
              oppositeEdge = getEdgeOppositeHorizontally(pp,
                  symbol, x, index);
              canvas.beginPath();
              canvas.moveTo(oppositeEdge - grp.x0, yPx - grp.y0);
              canvas.lineTo(xPx - grp.x0, yPx - grp.y0);
            }
            if (nextX != nextX || nextY != nextY) {
              // last point, or last point before a break in the line
              canvas.lineTo(oppositeEdge - grp.x0, yPx - grp.y0);
              closeStrokeAndFill = true;
            } else {
              canvas.lineTo(nextXPx - grp.x0, nextYPx - grp.y0);
            }

          }

          if (closeStrokeAndFill) {

            canvas.closePath();
            int borderWidth = symbol.getBorderWidth();
            // negative (external) border requires double-wide stroke
            int lineWidth = (borderWidth >= 0) ? borderWidth
                : (2 * Math.abs(borderWidth));
            String borderColor = symbol.getBorderColor();
            String backgroundColor = symbol.getBackgroundColor();

            /*
             * XXX: Simply dropping the rendering as we do below does not
             * exactly simulate the effect of transparent border/fill,
             * specifically:
             * 
             * <ol> <li> Transparent internal border ==> the background fill
             * shines through the inner half of that border <li> Transparent
             * external border ==> Works OK <li> Transparent fill w external
             * border ==> border extended internally to double width <li>
             * Transparent fill w internal border ==> works OK </ol>
             * 
             * GWTCanvas does not provide a mechanism to "stroke transparent
             * pixels", which is what I really needed. And emulating this,
             * though possible via properly positioned inner/outter regions,
             * etc. would have required a lot of effort to assure that sharply
             * peaked angles, say, get rendered right.
             */                

            // non-negative borders fill before stroking (thus
            // stroke overwrites internal half of border)
            if (borderWidth >= 0 && thickness > 0
                && TRANSPARENT_BORDER_COLOR != backgroundColor
                && "transparent" != backgroundColor) {
              canvas.setFillStyle(backgroundColor);
              canvas.fill();
            }

            // stroke whenever a border is present
            if (borderWidth != 0
                && TRANSPARENT_BORDER_COLOR != borderColor
                && "transparent" != borderColor) {
              canvas.setStrokeStyle(borderColor);
              canvas.setLineWidth(lineWidth);
              canvas.stroke();
            }

            // negative borders fill AFTER stroking (thus zapping
            // the internal half of the stroked border).
            if (borderWidth < 0 && thickness > 0
                && TRANSPARENT_BORDER_COLOR != backgroundColor
                && "transparent" != backgroundColor) {
              canvas.setFillStyle(backgroundColor);
              canvas.fill();
            }
          }
        }
      } // if (0 == spacing && null != canvas && thickness > 0)
      else if (nextX == nextX && nextY == nextY && // next point defined
          thickness > 0 && // not a zero thickness connection
          (x != nextX || y != nextY)) { // this/next point not overlayed
        if (0 == spacing) // 1px is as close as HTML-element
          spacing = 1; // based filling can get to continuous
        double d = distance(xPx, yPx, nextXPx, nextYPx);
        int nChunks = (int) Math.round(d / spacing);
        if (nChunks > 1) {
          double deltaX = nextXPx - xPx;
          double deltaY = nextYPx - yPx;
          boolean dXIsLonger = deltaX * deltaX > deltaY * deltaY;
          if (dXIsLonger) {
            deltaY /= deltaX; // from now on, dy is really dy/dx
            deltaX /= nChunks;// from now on, dx is for 1 chunk
          } else {
            deltaX /= deltaY; // from now on, dx is really dx/dy
            deltaY /= nChunks; // from now on, dy is for 1 chunk
          }
          // i==0 corresponds to the (to-be-drawn-last) symbol on
          // (x,y).
          for (int i = 1; i < nChunks; i++) {
            // linearly interpolate forwards towards the next
            // point; forward interpolation (usually) lets us
            // place the "main" symbol for the original point on
            // top of these interpolated symbols, in one pass.
            double xi;
            double yi;

            // Rounding to the longer dimension first, then
            // using that pixelated position to determine other
            // dimension tends to keep points closer to being
            // on the mathematically ideal line (at the cost of
            // being less evenly spaced along that line). It's
            // not too hard to see the improved alignment on
            // GChartExample03, for example.
            if (dXIsLonger) {
              xi = Math.round(xPx + deltaX * i);
              yi = Math.round(yPx + deltaY * (xi - xPx));
            } else { // delta y is longer
              yi = Math.round(yPx + deltaY * i);
              xi = Math.round(xPx + deltaX * (yi - yPx));
            }

            // interpolated symbols set width & height to
            // thickness, but are otherwise the same as main
            // symbol at (x,y)
            realizeOneImageOfSymbol(pp, grp, arp, symbol, null,
            		index, clipPlotArea, clipDecoratedChart, renderPaddingFactor,
                xi, yi, prevXPx, prevYPx, nextXPx, nextYPx,
                thickness, thickness);
          }
        }
        // else points too close to require any "filler" elements
      }
      // the "main" symbol (the one on the (x,y) point itself) is
      // rendered last to put it on top of interpolated images; this
      // is also where any annotation on the point gets rendered.
      if (drawMainSymbol) {
        realizeOneImageOfSymbol(pp, grp, arp, symbol, annotation, index,
            clipPlotArea, clipDecoratedChart, renderPaddingFactor, 
            xPx, yPx, prevXPx, prevYPx, nextXPx, nextYPx,
            symbol.getWidth(pp), symbol.getHeight(pp, index));
      }
    }

  } // end of class SymbolType

  /**
   * Defines keywords <tt>INSIDE</tt>, <tt>OUTSIDE</tt>, and <tt>CENTERED</tt>
   * that specify the location of ticks relative to their axis.
   * <p>
   * 
   * @see Axis#setTickLocation setTickLocation
   * 
   */
  public static final class TickLocation {
    /*
     * An integer form of the tick location (-1 - OUTSIDE, 0 - CENTERED, +1 -
     * INSIDE) that facilitates generating appropriate symbol types for
     * rendering ticks.
     */
    int locationIndex;

    private TickLocation(int locationIndex) {
      this.locationIndex = locationIndex;
    }

    /**
     * Indicates that ticks are located outside of the axis.
     * 
     * @see Axis#setTickLocation setTickLocation
     */
    public static final TickLocation OUTSIDE = new TickLocation(-1);
    /**
     * Indicates that ticks are centered on the axis.
     * 
     * @see Axis#setTickLocation setTickLocation
     */
    public static final TickLocation CENTERED = new TickLocation(0);
    /**
     * Indicates that ticks are located inside of the axis.
     * 
     * @see Axis#setTickLocation setTickLocation
     */
    public static final TickLocation INSIDE = new TickLocation(1);

    // symbol type representing ticks on x axes at given position
    // axisPosition of -1 is x-axis, +1 is x2-axis.
    //
    // (symbols representing ticks depend on the axis they are on)
    SymbolType getXAxisSymbolType(int axisPosition) {
      final SymbolType[] symbolMap = { SymbolType.BOX_NORTH,
          SymbolType.BOX_CENTER, SymbolType.BOX_SOUTH };
      SymbolType result = symbolMap[axisPosition * locationIndex + 1];
      return result;
    }

    // symbol type representing ticks on y axes at given position
    // axisPosition of -1 is y-axis, +1 is y2-axis
    //
    // (symbols representing ticks depend on the axis they are on)
    SymbolType getYAxisSymbolType(int axisPosition) {
      final SymbolType[] symbolMap = { SymbolType.BOX_EAST,
          SymbolType.BOX_CENTER, SymbolType.BOX_WEST };
      SymbolType result = symbolMap[axisPosition * locationIndex + 1];
      return result;
    }

  } // class TickLocation

  /**
   * Defines how the <tt>update</tt> method updates the touched point, that is,
   * the point the user is considered to be hovered over.
   * 
   * @see #update(TouchedPointUpdateOption) update
   * 
   */

  public static final class TouchedPointUpdateOption {
    private TouchedPointUpdateOption() {
      super();
    }

    /**
     * When this option is passed to the update method, any touched point is
     * cleared as a consequence of the update.
     * <p>
     * 
     * This option can be used when you want to "start fresh" with regards to
     * hover feedback after an update, and want to assure that only explicit
     * user-generated mouse move actions (rather than objects moving
     * <i>underneath</i> a fixed-position mouse cursor) can trigger hover
     * feedback.
     * 
     * @see #update update
     * @see #TOUCHED_POINT_LOCKED TOUCHED_POINT_LOCKED
     * @see #TOUCHED_POINT_UPDATED TOUCHED_POINT_UPDATED
     * 
     */
    public static final TouchedPointUpdateOption TOUCHED_POINT_CLEARED = new TouchedPointUpdateOption();

    /**
     * When this option is passed to the update method, any previously touched
     * point is locked in (remains unchanged).
     * <p>
     * 
     * For example, if the mouse is over a certain point before the update, and
     * that point moves away from the mouse (without the mouse moving otherwise)
     * as a consequence of the update, the hover feedback remains "locked in" to
     * the original point, even though the mouse is no longer on top of that
     * point.
     * <p>
     * 
     * This option is useful for hover widgets that modify the position, size,
     * symbol of points/curves, and do not want the selected point/curve (and
     * popup hover widget) to change as a consequence of such changes.
     * <p>
     * 
     * <i>Note:</i> If the currently touched point or the curve containing it is
     * deleted, GChart sets the touched point reference to <tt>null</tt>. In
     * that case, this option and <tt>TOUCHED_POINT_CLEARED</tt> behave the same
     * way.
     * 
     * 
     * @see #update update
     * @see #TOUCHED_POINT_CLEARED TOUCHED_POINT_CLEARED
     * @see #TOUCHED_POINT_UPDATED TOUCHED_POINT_UPDATED
     * 
     */
    public static final TouchedPointUpdateOption TOUCHED_POINT_LOCKED = new TouchedPointUpdateOption();
    /**
     * When this option is passed to the update method, the touched point is
     * updated so that it reflects whatever point is underneath the mouse cursor
     * after the update completes.
     * <p>
     * 
     * For example, if the mouse is not hovering over any point before the
     * update, but the update repositions one of the points so that it is now
     * underneath the mouse cursor, the hover feedback for that point will be
     * displayed. Similarly, if the update moves a point away from the mouse
     * cursor, previously displayed hover feedback will be eliminated.
     * <p>
     * 
     * @see #update update
     * @see #TOUCHED_POINT_CLEARED TOUCHED_POINT_CLEARED
     * @see #TOUCHED_POINT_LOCKED TOUCHED_POINT_LOCKED
     * 
     */
    public static final TouchedPointUpdateOption TOUCHED_POINT_UPDATED = new TouchedPointUpdateOption();
  }

  /**
   * The x-axis of a GChart.
   * 
   * @see GChart#getXAxis getXAxis
   */

  public class XAxis extends Axis {
    XAxis() {
      super();
      isHorizontalAxis = true;
      ticksId = XTICKS_ID;
      gridlinesId = XGRIDLINES_ID;
      axisId = XAXIS_ID;
      axisPosition = -1;
      setTickLocation(DEFAULT_TICK_LOCATION);
      setTickThickness(DEFAULT_TICK_THICKNESS);
      setTickLength(DEFAULT_TICK_LENGTH);
    }

    public double clientToModel(int clientCoordinate) {
      int xPixel = Window.getScrollLeft() + clientCoordinate
          - plotPanel.getAbsoluteLeft();
      double result = plotPanel.xChartPixelToX(xPixel);
      return result;
    }

    public int getAxisLabelThickness() {
      final int EXTRA_CHARHEIGHT = 2; // 1-char space above & below
      final int DEF_CHARHEIGHT = 1;
      int result = 0;
      if (GChart.NAI != axisLabelThickness)
        result = axisLabelThickness;
      else if (null == getAxisLabel())
        result = 0;
      else if (getAxisLabel() instanceof HasHTML) {
        int charHeight = htmlHeight(((HasHTML) (getAxisLabel()))
            .getHTML());
        result = (int) Math.round((EXTRA_CHARHEIGHT + charHeight)
            * getTickLabelFontSize()
            * TICK_CHARHEIGHT_TO_FONTSIZE_LOWERBOUND);
      } else
        result = (int) Math.round((EXTRA_CHARHEIGHT + DEF_CHARHEIGHT)
            * getTickLabelFontSize()
            * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
      return result;
    }

    public double getDataMax() {
      double result = -Double.MAX_VALUE;
      int nCurves = getNCurves();
      for (int i = 0; i < nCurves; i++) {
        Curve c = getSystemCurve(i);
        if (!c.isVisible())
          continue;
        int nPoints = c.getNPoints();
        for (int j = 0; j < nPoints; j++) {
          result = maxIgnoreNaNAndMaxValue(result, c.getPoint(j)
              .getX());
        }
      }
      return result == -Double.MAX_VALUE ? Double.NaN : result;
    }

    public double getDataMin() {
      double result = Double.MAX_VALUE;
      int nCurves = getNCurves();
      for (int i = 0; i < nCurves; i++) {
        Curve c = getSystemCurve(i);
        if (!c.isVisible())
          continue;
        int nPoints = c.getNPoints();
        for (int j = 0; j < nPoints; j++) {
          result = minIgnoreNaNAndMaxValue(result, c.getPoint(j)
              .getX());
        }
      }
      return result == Double.MAX_VALUE ? Double.NaN : result;
    }

    public double getMouseCoordinate() {
      double result = plotPanel.xChartPixelToX(plotPanel.getXMouse());
      return result;
    }

    @Override
    public int getTickLabelThickness(boolean needsPopulation) {
      int result;
      if (tickLabelThickness != GChart.NAI)
        result = tickLabelThickness;
      else if (getTickCount() == 0)
        result = 0;
      else {
        // XXX: single line labels assumed; these have height
        // almost equal to the fontSize in pixels. Not really
        // right, since multi-line HTML can now be used, but user
        // can explicitly change tick label thickness with
        // multi-line, HTML based, ticks, so OK for now.
        result = (int) Math.round(TICK_CHARHEIGHT_TO_FONTSIZE_LOWERBOUND
            * tickLabelFontSize);
      }
      return result;
    }

    public double modelToClient(double modelCoordinate) {
      double xPixel = plotPanel.xToChartPixel(modelCoordinate);
      double result = plotPanel.getAbsoluteLeft()
          - Window.getScrollLeft() + xPixel;
      return result;
    }

    public double modelToPixel(double modelCoordinate) {
      double result = plotPanel.xToChartPixel(modelCoordinate);
      return result;
    }

    public double modelToPlotAreaPixel(double modelCoordinate) {
      double result = plotPanel.xToPixel(modelCoordinate);
      return result;
    }

    public double pixelToModel(int pixelCoordinate) {
      double result = plotPanel.xChartPixelToX(pixelCoordinate);
      return result;
    }

    public double plotAreaPixelToModel(int pixelCoordinate) {
      double result = plotPanel.xPixelToX(pixelCoordinate);
      return result;
    }

    public void setTickLength(int tickLength) {
      chartDecorationsChanged = true;
      this.tickLength = tickLength;
      getSystemCurve(ticksId).getSymbol()
          .setHeight(getActualTickLength());
    }

    public void setTickThickness(int tickThickness) {
      this.tickThickness = tickThickness;
      getSystemCurve(ticksId).getSymbol().setWidth(tickThickness);
    }

  } // end of class XAxis

  /**
   * The left y-axis of a GChart.
   * 
   * @see GChart#getYAxis getYAxis
   * 
   */

  public class YAxis extends Axis {
    YAxis() {
      super();
      isHorizontalAxis = false;
      ticksId = YTICKS_ID[0];
      gridlinesId = YGRIDLINES_ID[0];
      axisId = YAXIS_ID[0];
      axisPosition = -1;
      setTickLocation(DEFAULT_TICK_LOCATION);
      setTickThickness(DEFAULT_TICK_THICKNESS);
      setTickLength(DEFAULT_TICK_LENGTH);
    }
    
    YAxis(int k) {
        super();
        yIndex = k;
        isHorizontalAxis = false;
        ticksId = YTICKS_ID[k];
        gridlinesId = YGRIDLINES_ID[k];
        axisId = YAXIS_ID[k];
        axisPosition = (k == 0) ? -1 : 1;
        setTickLocation(DEFAULT_TICK_LOCATION);
        setTickThickness(DEFAULT_TICK_THICKNESS);
        setTickLength(DEFAULT_TICK_LENGTH);
      }

    int yIndex = 0;
    public double clientToModel(int clientCoordinate) {
      int yPixel = Window.getScrollTop() + clientCoordinate
          - plotPanel.getAbsoluteTop();
      double result = plotPanel.yChartPixelToY(yPixel);
      return result;
    }

    public double getDataMax() {
      double result = -Double.MAX_VALUE;
      int nCurves = getNCurves();
      for (int i = 0; i < nCurves; i++) {
        Curve c = getSystemCurve(i);
        if (!c.isVisible())
          continue;
        if (c.getYAxis() == Y_AXIS[yIndex]) {
          int nPoints = c.getNPoints();
          for (int j = 0; j < nPoints; j++) {
            result = maxIgnoreNaNAndMaxValue(result, c.getPoint(j)
                .getY());
          }
        }
      }
      return result == -Double.MAX_VALUE ? Double.NaN : result;
    }

    public double getDataMin() {
      double result = Double.MAX_VALUE;
      int nCurves = getNCurves();
      for (int i = 0; i < nCurves; i++) {
        Curve c = getSystemCurve(i);
        if (!c.isVisible())
          continue;
        if (c.getYAxis() == Y_AXIS[yIndex]) {
          int nPoints = c.getNPoints();
          for (int j = 0; j < nPoints; j++) {
            result = minIgnoreNaNAndMaxValue(result, c.getPoint(j)
                .getY());
          }
        }
      }
      return result == Double.MAX_VALUE ? Double.NaN : result;
    }

    public double getMouseCoordinate() {
      double result = plotPanel.yChartPixelToY(plotPanel.getYMouse());
      return result;
    }

    public double modelToClient(double modelCoordinate) {
    	double yPixel = plotPanel.yToChartPixel(modelCoordinate, yIndex);
      double result = plotPanel.getAbsoluteTop() - Window.getScrollTop()
          + yPixel;
      return result;
    }

    public double modelToPixel(double modelCoordinate) {
      double result = plotPanel.yToChartPixel(modelCoordinate, yIndex);
      return result;
    }

    public double modelToPlotAreaPixel(double modelCoordinate) {
      double result = plotPanel.yToPixel(modelCoordinate, yIndex);
      return result;
    }

    public double pixelToModel(int pixelCoordinate) {
      double result = plotPanel.yChartPixelToY(pixelCoordinate);
      return result;
    }

    public double plotAreaPixelToModel(int pixelCoordinate) {
      double result = plotPanel.yPixelToY(pixelCoordinate);
      return result;
    }

    public void setTickLength(int tickLength) {
      chartDecorationsChanged = true;
      this.tickLength = tickLength;
      getSystemCurve(ticksId).getSymbol().setWidth(getActualTickLength());
    }

    public void setTickThickness(int tickThickness) {
      this.tickThickness = tickThickness;
      getSystemCurve(ticksId).getSymbol().setHeight(tickThickness);
    }

  } // end of class YAxis

  /*
   * Allows precise alignment of a text label before the exact size of that
   * label is known, by enclosing it within a 1x1 grid. <p>
   * 
   * The external grid must be larger than the label or requested grid alignment
   * won't be realized. But larger than required containing grids occlude mouse
   * events from nearby elements. The NonoccludingReusableAlignedLabel
   * subclasses this class to solve this problem.
   */

  private static class AlignedLabel extends Grid {
    AlignedLabel() {
      super(1, 1);
      getCellFormatter().setWordWrap(0, 0, false);
      setCellPadding(0);
      setCellSpacing(0);
      setBorderWidth(0);
    }
  }

  /*
   * This class' sole purpose is to work around a FF 2 performance limitation:
   * chart update times increase as O(N^2) after the number of direct ancestor
   * child widgets in a single AbsolutePanel exceeds around 500-1000, AND the
   * chart is updated in more than one browser-displayed stage (e.g, via a
   * series of incremental updates that successively add more curve data, for
   * purposes of user feedback). By contrast, IE7 times grow as O(N) even if
   * 3,000 child widgets are added to a previously displayed chart (e.g. by
   * adding a bar curve with 3,000 bars on it).
   * 
   * <p>
   * 
   * For solid-fill line chart support (LINE SymbolType introduced in 2.2),
   * thousands of widgets are often needed and so these O(N^2) FF 2 times were
   * just too slow.
   * 
   * <p>
   * 
   * Some kind of fixed hash table inside of FF 2 divs could explain this switch
   * from O(N) to O(N^2) performance. <p>
   * 
   * Approach is to split up the large AbsolutePanel into a series of child
   * panels, each of which contains a number of elements within the range where
   * FF 2 updates are O(N). <p>
   * 
   * Tried to keep it light-weight so IE7 isn't penalized too much for having to
   * workaround this FF 2 limitation.
   */

  static class PartitionedAbsolutePanel extends Composite {
    /*
     * Max number of widgets in each panel; chose a value as large as possible
     * while remaining within the empirically determined FF 2 O(N) range. Here's
     * the data (from a 3,000 element test based on the sin curve of the 2.1
     * live demo called GChartExample15c.java) upon which this choice was based:
     * <p>
     * 
     * <pre>
     * 
     * Size FF2 IE7
     *     (sec) (sec)
     *  1    ~14 16
     *  2     11 13
     *  32     9 11
     *  64     9 12
     *  128    9 11
     *  256    9 11
     *  512   10 11
     *  1024  15 11
     *  2048  27 11
     *  4096* 61 12
     * 
     * </pre> <p>
     * 
     * The two largest sizes are good approximations of FF2 times we got
     * informally before the switch to partitioned AbsolutePanels with charts
     * with the corresponding number of elements (2000 or 3000). The overhead of
     * the partitioning itself is very low, as shown by the modest time increase
     * even when each element is placed into its own sub-panel. Tests with a <
     * 256 element chart suggested at most a couple of ms of time increases due
     * to the introduction of partitioning.
     * 
     * <p>
     * 
     * I kind of expected to see >61 second times with a sub-panel size of 1,
     * since the parent panel still has >3,000 elements in this case. Whatever
     * the cause of the performance logjam (presumably in the FF2 heap
     * somewhere?) simply the fact that you introduce a parent AbsolutePanel
     * that holds various child AbslolutePanels that hold the actual image
     * widgets appears to work around most of the problem. If such a useless
     * change makes FF2 materially faster, that seems like a performance bug in
     * FF2 to me.<p>
     * 
     * The root FF cause is apparently NOT image cache related, though, since
     * turning off the image cache via about:config didn't change times for the
     * last row of the table above.
     */

    /*
     * Strange, reorganized DOM layout in GChart v2.5 and now cannot reproduce
     * these results in FF2 (partitioning gave just a modest 1 second boost in a
     * 15 second, 4,000 point test). So, I was about to drop back to a simple
     * AbsolutePanel. BUT, in FF3, now, the 4,000 point test crashes unless I
     * use PartitionedAbsolutePanel! I guess Firefox 3 has problems with divs
     * that have 4,000 elements, or at least 4,000 image elements, on them,
     * which the partioning fixes.
     */

    final int WIDGETS_PER_PANEL = 256;
    private AbsolutePanel root = new AbsolutePanel();
    private AbsolutePanel subPanel = null; // "selected" subPanel
    private int iSubPanel = -1; // index of "selected" subPanel
    private int nWidgets = 0; // total # over all subPanels

    PartitionedAbsolutePanel() {
      super();
      initWidget(root);
    }

    /* resets the partitioned panel to it's initial state */
    public void clear() {
      root.clear();
      subPanel = null;
      iSubPanel = -1;
      nWidgets = 0;
    }

    public int getWidgetCount() {
      return nWidgets;
    }

    // makes the subpanel containing the widget the selected one.
    private void selectSubPanel(int iWidget) {
      if (iSubPanel != iWidget / WIDGETS_PER_PANEL) {
        iSubPanel = iWidget / WIDGETS_PER_PANEL;
        subPanel = (AbsolutePanel) root.getWidget(iSubPanel);
      }
    }

    // adds a widget to end of this partioned absolute panel
    public void add(Widget w) {
      if (nWidgets % WIDGETS_PER_PANEL == 0) {
        // last panel is full, time to add a new one
        subPanel = new AbsolutePanel();
        // Panel sits in upper left corner. Does nothing, can't
        // be seen. It's just a holder for other widgets.
        GChart.setOverflow(subPanel, "visible");
        subPanel.setPixelSize(0, 0);
        root.add(subPanel, 0, 0);
      }
      selectSubPanel(nWidgets);
      subPanel.add(w);
      nWidgets++;
    }

    // returns widget at given index
    public Widget getWidget(int iWidget) {
      if (iWidget < 0 || iWidget >= nWidgets)
        throw new IllegalArgumentException("Invalid widget index: "
            + iWidget + ". Valid range is: 0..." + (nWidgets - 1));

      selectSubPanel(iWidget);
      Widget result = subPanel.getWidget(iWidget % WIDGETS_PER_PANEL);
      return result;
    }

    // Remove very last widget from panel.
    public boolean remove(int iWidget) {
      if (iWidget != nWidgets - 1)
        throw new IllegalArgumentException("iWidgets arg = " + iWidget
            + " nWidgets-1 (" + (nWidgets - 1) + ") is required.");

      selectSubPanel(iWidget);
      boolean result = subPanel.remove(iWidget % WIDGETS_PER_PANEL);
      if (iWidget % WIDGETS_PER_PANEL == 0) {
        // if deleted widget is last widget overall, and first on
        // the selected panel, selected panel will now be empty.
        root.remove(subPanel);
        iSubPanel = -1; // next selectSubPanel will reset these
        subPanel = null;
      }
      nWidgets--;
      return result;
    }

    // To assure that w is on the selected subPanel, this method
    // must only be passed a widget that is in the currently
    // selected subPanel. This can be assured by passing in a
    // widget that was just added via add(), or just retrieved
    // via getWidget (otherwise, an exception will be thrown).

    public void setWidgetPosition(Widget w, int left, int top) {
      subPanel.setWidgetPosition(w, left, top);
    }

  } // end of class PartitionedAbsolutePanel

  static class Rectangle { // a (pixel graphics coords) rectangle
    double x; // x, y at upper left corner of rectangle
    double y;
    double width; // distance from x to right edge
    double height; // distance from y to bottom edge
  }

  /*
   * AbsolutePanel that allows annotations it contains to be easily reused, for
   * increased efficiency.
   */
  class AnnotationRenderingPanel extends PartitionedAbsolutePanel {
    int labelIndex = 0; // to-be-added-next label index
    private int lastVisibleLabel = -1; // just before 1st valid index

    /*
     * Returns the inner grid of the first reusuable, non-occluding aligned
     * label in this rendering panel.
     */
    AlignedLabel getFirstInnerAlignedLabel() {
      AlignedLabel result = null;
      if (labelIndex > 0) {
        NonoccludingReusuableAlignedLabel parent = (NonoccludingReusuableAlignedLabel) getWidget(0);
        result = parent.getInnerGrid();
      }
      return result;
    }

    /**
     * Provides support for reusing certain property specifications that are
     * likely to be the same, given how aligned labels in a GChart get reused,
     * and given certain assumptions about which properties of the labels are
     * most likely to remain unchanged between updates.
     * <p>
     * 
     * Also applies a hidden outter grid technique to allow proper alignment
     * with labels of unknown size without occluding mouse events of nearby
     * elements.
     * <p>
     * 
     * Very similar in intent to the ReusableImage, see that class' header
     * comment for more info.
     * 
     */
    class NonoccludingReusuableAlignedLabel extends AlignedLabel {
      int fontSize = GChart.NAI;
      String fontFamily = USE_CSS;
      String fontStyle = USE_CSS;
      String fontWeight = USE_CSS;
      String fontColor = USE_CSS;
      HasHorizontalAlignment.HorizontalAlignmentConstant hAlign;
      HasVerticalAlignment.VerticalAlignmentConstant vAlign;
      String labelText = null;
      boolean isHTML = false;
      Widget labelWidget = null;
      final AlignedLabel innerGrid = new AlignedLabel();

      AlignedLabel getInnerGrid() {
        return innerGrid;
      }

      NonoccludingReusuableAlignedLabel() {
        super();
        setWidget(0, 0, innerGrid);
        /*
         * The basic technique being used in the lines below is illustrated by
         * this excerpt from p 317 of "CSS, The Definitive Guide" by Eric A.
         * Meyer: <p>
         * 
         * <pre> p.clear {visibility: hidden;} p.clear em {visibility: visible;}
         * </pre> <p>
         * 
         * In the above example, emphasized (italic) text is positioned exactly
         * as it would have been in the paragraph had the normal text been
         * visible, except that the normal text <i>isn't</i> visible. And,
         * unlike transparent text, the invisible text also won't capture mouse
         * events (essential for our aligned labels)
         * 
         * <p>
         * 
         * With GChart's aligned label, we want the outter Grid (HTML table) to
         * be "not there" as far as visibility and mouseovers, but still impact
         * centering and other alignment of the stuff in the visible, inner
         * Grid.
         * 
         * <p>
         * 
         * Note that we cannot just make the Grid color transparent (tried that
         * first) because in that case the oversized outter Grid required for
         * alignment will still grab the mouseover events inappropriately (wrong
         * hovertext problem).
         * 
         * <p>
         * 
         * Not certain but, apparently, IE6 requires that, if you want to apply
         * this trick when the outer element is a table you must use another
         * table as the inner element. At least, the div inner element approach
         * I used at first (that basically worked in Firefox), made both parent
         * and child invisible in IE6. <p>
         * 
         * In summary:
         * 
         * The upside: alignment without inappropriate event occlusion The
         * downside: the extra Grid element saps performance
         */

        DOM.setStyleAttribute(getElement(), "visibility", "hidden");
        DOM.setStyleAttribute(innerGrid.getElement(), "visibility",
            "visible");
      }

      /*
       * Sets properties only if they have changed; to replace expensive DOM
       * calls with cheap inequality tests.
       * 
       * TODO: Investigate moving properties that are guaranteed to be the same
       * across all elements in the image panel (backgroundColor, borderColor,
       * borderWidth, borderStyle, image url, possibly more) into a single style
       * for the image panel as a whole, and just add that styleName to each
       * image, and set the properties once, in the style. If this worked, it
       * would save both time and space (style would be internal and not
       * intended for direct developer access, since in some charts canvas, not
       * styles, would be responsible for these curve properties), and the same
       * approach could be applied to the labelPanel.
       */

      void setReusableProperties(int fontSize, String fontFamily,
          String fontStyle, String fontWeight, String fontColor,
          HasHorizontalAlignment.HorizontalAlignmentConstant hAlign,
          HasVerticalAlignment.VerticalAlignmentConstant vAlign,
          String labelText, boolean isHTML, Widget labelWidget) {

        if (this.fontSize != fontSize) {
          DOM.setIntStyleAttribute(innerGrid.getCellFormatter()
              .getElement(0, 0), "fontSize", fontSize);
          this.fontSize = fontSize;
        }
        if (this.fontFamily != fontFamily) {
          DOM.setStyleAttribute(innerGrid.getCellFormatter()
              .getElement(0, 0), "fontFamily", fontFamily);
          this.fontFamily = fontFamily;
        }
        if (this.fontStyle != fontStyle) {
          DOM.setStyleAttribute(innerGrid.getCellFormatter()
              .getElement(0, 0), "fontStyle", fontStyle);
          this.fontStyle = fontStyle;
        }
        if (this.fontWeight != fontWeight) {
          DOM.setStyleAttribute(innerGrid.getCellFormatter()
              .getElement(0, 0), "fontWeight", fontWeight);
          this.fontWeight = fontWeight;
        }
        if (this.fontColor != fontColor) {
          DOM.setStyleAttribute(innerGrid.getCellFormatter()
              .getElement(0, 0), "color", fontColor);
          this.fontColor = fontColor;
        }
        if (this.hAlign != hAlign) {
          getCellFormatter().setHorizontalAlignment(0, 0, hAlign);
          // without this, only IE6-quirks doesn't quite align right:
          innerGrid.getCellFormatter().setHorizontalAlignment(0, 0,
              hAlign);
          this.hAlign = hAlign;
        }
        if (this.vAlign != vAlign) {
          getCellFormatter().setVerticalAlignment(0, 0, vAlign);
          // without this, only IE6-quirks doesn't quite align right:
          innerGrid.getCellFormatter().setVerticalAlignment(0, 0,
              vAlign);
          this.vAlign = vAlign;
        }

        if (null != labelWidget) {
          if (this.labelWidget != labelWidget) {
            innerGrid.setWidget(0, 0, labelWidget);
            this.labelWidget = labelWidget;
            this.labelText = null;
          }
        } else if (this.labelText != labelText || this.isHTML != isHTML) {
          if (null == labelText || "" == labelText)
            innerGrid.setText(0, 0, "");
          else if (!isHTML) {
            innerGrid.setText(0, 0, labelText);
          } else {
            innerGrid.setHTML(0, 0, labelText);
          }
          this.isHTML = isHTML;
          this.labelText = labelText;
          this.labelWidget = null;
        }
      }
    } // end of class NonoccludingReusuableAlignedLabel

    AnnotationRenderingPanel() {
      super();
      /*
       * Because of event-occlusion that can occur on all browsers but IE,
       * annotation panels MUST by 0-sized/overflow:visible otherwise they will
       * short-circuit event processing needed for widget annotations added by
       * developer. Graphics rendering panels don't have this constraint and
       * thus can be clipped to the plot area.
       */
      GChart.setOverflow(this, "visible");
      this.setPixelSize(0, 0);
    }

    void setLabelPosition(NonoccludingReusuableAlignedLabel lbl, int x,
        int y) {
      // workaround problem with special meaning of (-1,-1) to
      // setWidgetPosition (makes position off by one pixel).
      if (x == -1 && y == -1)
        x = 0;
      setWidgetPosition(lbl, x, y);
    }

    void beginRendering() {
      labelIndex = 0;
    }

    void endRendering() {
      // hide or remove labels no longer being used
      for (int iLabel = optimizeForMemory ? (getWidgetCount() - 1)
          : lastVisibleLabel; iLabel >= labelIndex; iLabel--) {
        Widget w = getWidget(iLabel);
        if (optimizeForMemory)
          remove(iLabel);
        else
          w.setVisible(false);
      }
      lastVisibleLabel = labelIndex - 1;
    }

    /*
     * Creates (or reveals), and configures, an aligned label. Works very
     * similarly to addOrRevealImage.
     */
    NonoccludingReusuableAlignedLabel getNextOrNewAlignedLabel(
        int fontSize, String fontFamily, String fontStyle,
        String fontWeight, String fontColor,
        HasHorizontalAlignment.HorizontalAlignmentConstant hAlign,
        HasVerticalAlignment.VerticalAlignmentConstant vAlign,
        String labelText, boolean isHTML, Widget labelWidget) {
      NonoccludingReusuableAlignedLabel result;
      if (labelIndex < getWidgetCount()) {
        result = (NonoccludingReusuableAlignedLabel) getWidget(labelIndex);
        if (null != result.labelWidget
            && labelWidget == result.labelWidget) {
          /*
           * DOM element actually stored in the label's Grid-cell, and what the
           * label "thinks" is stored there, could be inconsistent if, for
           * example, the same label widget reference was used to define two
           * different points' annotations. In that case, we need to clear the
           * widget reference that the label stores thus making it consistent
           * with what is really in the DOM. <p>
           * 
           * This code was added to fix the bug reproduced by TestGChart53.java.
           * See that test for more info.
           */
          Element e = labelWidget.getElement();
          if (null == e
              || (e.getParentElement() != result.innerGrid
              .getCellFormatter().getElement(0, 0)))
            // the widget' DOM parent isn't label's grid-cell (it
            // was moved)
            result.labelWidget = null;
        }

        if (labelIndex > lastVisibleLabel)
          result.setVisible(true);
      } else {
        result = new NonoccludingReusuableAlignedLabel();
        add(result);
      }
      result.setReusableProperties(fontSize, fontFamily, fontStyle,
          fontWeight, fontColor, hAlign, vAlign, labelText, isHTML,
          labelWidget);

      if (lastVisibleLabel < labelIndex)
        lastVisibleLabel = labelIndex;
      labelIndex++;
      return result;
    }

    protected void renderAnnotation(Annotation annotation,
        AnnotationLocation loc, double xCenter, double yCenter,
        double symWidth, double symHeight, Symbol symbol) {

      int widthUpperBound = annotation.getWidthUpperBound();
      int upLeftX = loc.getUpperLeftX(xCenter, widthUpperBound, Math
          .abs(symWidth));
      int heightUpperBound = annotation.getHeightUpperBound();
      int upLeftY = loc.getUpperLeftY(yCenter, heightUpperBound, Math
          .abs(symHeight));

      NonoccludingReusuableAlignedLabel alignedLabel = getNextOrNewAlignedLabel(
          annotation.getFontSize(), 
          annotation.getFontFamilyExpanded(getFontFamily()),
          annotation.getFontStyle(), annotation.getFontWeight(),
          annotation.getFontColor(), loc.getHorizontalAlignment(),
          loc.getVerticalAlignment(), annotation.getText(),
          annotation.isHTML(), annotation.getWidget());
      // If positioning by top or left edges, explicit sizing isn't
      // needed (makes the bounding box tighter, which, for reasons
      // unknown, makes rendering around 10% faster on some browsers and
      // usage scenarios).
      if (loc.getHorizontalAlignment() != HasHorizontalAlignment.ALIGN_LEFT)
        alignedLabel.setWidth(widthUpperBound + "px");
      else
        alignedLabel.setWidth("");

      if (loc.getVerticalAlignment() != HasVerticalAlignment.ALIGN_TOP)
        alignedLabel.setHeight(heightUpperBound + "px");
      else
        alignedLabel.setHeight("");

      setLabelPosition(alignedLabel, upLeftX, upLeftY);
    }

  } // end of class AnnotationRenderingPanel

  class GraphicsRenderingPanel extends AbsolutePanel {
    private GChartCanvasLite canvas = null;
    int x0 = 0; // origin, in pixel coords, of upper left..
    int y0 = 0; // corner of rendering canvas widget
    int canvasWidth = 0; // width of last used rendering canvas
    int canvasHeight = 0; // height of last used rendering canvas
    private AbsolutePanel canvasPanel = new AbsolutePanel();
    private PartitionedAbsolutePanel imagePanel = new PartitionedAbsolutePanel();
    int imageIndex = 0;
    // helps minimize calls to setVisible (which can be expensive)
    private int lastVisibleImage = -1;

    void setRenderingPosition(int xShift, int yShift) {
      setWidgetPosition(canvasPanel, xShift, -yShift);
      setWidgetPosition(imagePanel, xShift, -yShift);
    }

    // Add a canvas, if needed
    void maybeAddCanvas() {
      if (null != canvasFactory && null == canvas) {
        canvas = canvasFactory.create();
        if (null != canvas) {
          if (canvas instanceof Widget) {
            DOM.setElementAttribute(((Widget) canvas).getElement(),
                "align", "left");
            canvasPanel.add((Widget) canvas, 0, 0);
          } else
            throw new IllegalStateException(
                "Your canvas factory's create method did not return "
                + "either null or a GWT Widget, as required. See the "
                + "GChart.setCanvasFactory method javadocs for details.");
        }
      }
    }

    class ReusableImage extends Image {
      private String backgroundColor = USE_CSS;
      private String borderColor = USE_CSS;
      private String borderStyle = USE_CSS;
      // the capped border width, times two (to allow half-pixel widths)
      private int cappedBorderWidthX2 = GChart.NAI;
      private int width = GChart.NAI;
      private int height = GChart.NAI;
      int x = GChart.NAI;
      int y = GChart.NAI;
      String url = null;

      ReusableImage() {
        super();
        this.url = null;
      }

      void setReusableProperties(String backgroundColor,
          String borderColor, String borderStyle, int borderWidth,
          double dWidth, double dHeight, double xD, double yD,
          String url) {

        // Round two edges, and define width to be their difference.
        // (rounding this way assures bars align with gridlines, etc.)
        int newX = (int) Math.round(xD);
        int newW = (int) Math.round(xD + dWidth) - newX;
        int newY = (int) Math.round(yD);
        int newH = (int) Math.round(yD + dHeight) - newY;
        int thickness = (newW < newH) ? newW : newH;
        int newCappedBorderWidthX2 = (2 * borderWidth < thickness) ? 2 * borderWidth
            : thickness;
        if (TRANSPARENT_BORDER_COLOR == borderColor) {// transparency
          // emulation
          if (newCappedBorderWidthX2 > 0) {
            // to emulate an internal transparent border using a 0 width
            // border, we need to shift the upper left corner by the
            // amount of border, and shrink the size by twice the amount
            // of the border.
            newX += newCappedBorderWidthX2 / 2; // shift upper left
            // corner
            newY += newCappedBorderWidthX2 / 2;
            newH -= newCappedBorderWidthX2; // shrink size
            newW -= newCappedBorderWidthX2;
          }
          // else, external border is just eliminated, no adjustment
          // needed
          newCappedBorderWidthX2 = 0;
          borderColor = "transparent"; // because DOM won't accept null
          if (backgroundColor == TRANSPARENT_BORDER_COLOR)
            backgroundColor = "transparent";
        } else if (newCappedBorderWidthX2 < 0) {
          newX += newCappedBorderWidthX2 / 2; // shift upper left corner back
          newY += newCappedBorderWidthX2 / 2; // to incorporate external border.
        } else {
          newH -= newCappedBorderWidthX2; // shrink size to incorporate
          newW -= newCappedBorderWidthX2; // impact of internal border.
        }

        if (cappedBorderWidthX2 != newCappedBorderWidthX2) {
          if (1 == (newCappedBorderWidthX2 % 2)) {
            // odd pixel 2 x borderWidth needs asymetical borders to
            // fill rect (only positive (internal) borders can have
            // half-pixel widths)
            int floorBW = newCappedBorderWidthX2 / 2;
            int ceilBW = floorBW + 1;
            // (top, right, bottom, left) == (floor, floor, ceil, ceil)
            // assures symbol is odd-pixel border-filled in all cases
            DOM.setStyleAttribute(getElement(), "borderWidth",
                floorBW + "px " + floorBW + "px " + ceilBW
                + "px " + ceilBW + "px ");
          } else {
            DOM.setStyleAttribute(getElement(), "borderWidth", Math
                .abs(newCappedBorderWidthX2 / 2)
                + "px");
          }
          cappedBorderWidthX2 = newCappedBorderWidthX2;
        }

        if (GChart.NAI == this.x) {
          // At first, use AbsolutePanel's official API (to insulate us
          // from any future AbsolutePanel changes)
          setImagePosition(this, newX, newY);
          this.x = newX;
          this.y = newY;
        } else { // for speed, just set the edge positions that changed
          // (works, but bypasses AbsolutePanel's official API)
          if (this.x != newX) {
            DOM.setStyleAttribute(getElement(), "left", newX + "px");
            this.x = newX;
          }
          if (this.y != newY) {
            DOM.setStyleAttribute(getElement(), "top", newY + "px");
            this.y = newY;
          }
        }

        if (this.width != newW) {
          setWidth(newW + "px");
          this.width = newW;
        }
        if (this.height != newH) {
          setHeight(newH + "px");
          this.height = newH;
        }

        if (this.backgroundColor != backgroundColor) {
          DOM.setStyleAttribute(getElement(), "backgroundColor",
              backgroundColor);
          this.backgroundColor = backgroundColor;
        }
        if (this.borderColor != borderColor) {
          DOM.setStyleAttribute(getElement(), "borderColor",
              borderColor);
          this.borderColor = borderColor;
        }
        if (this.borderStyle != borderStyle) {
          DOM.setStyleAttribute(getElement(), "borderStyle",
              borderStyle);
          this.borderStyle = borderStyle;
        }

        if (this.url != url) {
          setUrl(url);
          this.url = url;
        }
      }
    } // end of class ReusableImage

    GraphicsRenderingPanel() {
      super();
      // Overflow of this panel is controlled when it is added
      // GChart.setOverflow(this, "visible");
      GChart.setOverflow(canvasPanel, "visible");
      GChart.setOverflow(imagePanel, "visible");
      // these sub-panels have no size themselves, they are merely
      // there to segregate canvas and HTML rendered parts of curve
      canvasPanel.setPixelSize(0, 0);
      imagePanel.setPixelSize(0, 0);
      this.add(canvasPanel, 0, 0);
      this.add(imagePanel, 0, 0);
    }

    GChartCanvasLite getCanvas() {
      return canvas;
    }

    void setImagePosition(ReusableImage img, int x, int y) {
      // workaround problem of special meaning of (-1,-1) to
      // setWidgetPosition (makes position off by one pixel, though).
      if (x == -1 && y == -1)
        x = 0;
      imagePanel.setWidgetPosition(img, x, y);
    }

    // Tells panel you are ready to start drawing the curve on it
    void beginRendering(Rectangle canvasRegion) {
      if (null != canvas) {
        if (null == canvasRegion) {
          // hold onto empty canvas for simplicity
          canvas.resize(0, 0);
          canvasWidth = canvasHeight = 0;
        } else {
          int width = (int) Math.round(canvasRegion.width);
          int height = (int) Math.round(canvasRegion.height);
          // if exactly same size, just clear...seems to save a little
          // time
          if (width == canvasWidth && height == canvasHeight)
            canvas.clear(); // reuse same canvas
          else { // size changed
            canvas.resize(width, height);
            canvasWidth = width;
            canvasHeight = height;
          }
          x0 = (int) Math.round(canvasRegion.x);
          y0 = (int) Math.round(canvasRegion.y);
          // workaround problem with special meaning of (-1,-1) to
          // setWidgetPosition (makes position off by one pixel).
          if (x0 == -1 && y0 == -1)
            x0 = 0;
          canvasPanel.setWidgetPosition((Widget) canvas, x0, y0);
        }
      }
      imageIndex = 0;
    }

    // Tells panel you are done drawing on it, and
    // it's OK to do any final cleanup/bookkeeping needed.
    void endRendering() {
      // hide or remove images no longer being used
      for (int iImage = optimizeForMemory ? (imagePanel.getWidgetCount() - 1)
          : lastVisibleImage; iImage >= imageIndex; iImage--) {
        Widget w = imagePanel.getWidget(iImage);
        if (optimizeForMemory)
          imagePanel.remove(iImage);
        else
          DOM.setStyleAttribute(w.getElement(), "visibility",
              "hidden");
        // setVisible unreliable w Images in IE as shown in
        // TestGChart41a.java
        // w.setVisible(false);
      }
      lastVisibleImage = imageIndex - 1;
    }

    /*
     * Speedier, reusable, rendering-panel-managed images. In effect, turns
     * image panel into a specialized memory manager.
     */
    void addOrRevealImage(String backgroundColor, String borderColor,
        String borderStyle, int borderWidth, double width,
        double height, double x, double y, String url) {
      ReusableImage img;
      if (imageIndex < imagePanel.getWidgetCount()) { // reuse an old image
        img = (ReusableImage) imagePanel.getWidget(imageIndex);
        if (imageIndex > lastVisibleImage)
          // "" visibility means
          // "visible whenever the parent is visible"
          DOM.setStyleAttribute(img.getElement(), "visibility", "");
        // setVisible unreliable for Images in IE as shown in
        // TestGChart41a.java
        // img.setVisible(true);
      } else { // add a new image
        img = new ReusableImage();
        imagePanel.add(img);
      }

      img.setReusableProperties(backgroundColor, borderColor,
          borderStyle, borderWidth, width, height, x, y, url);

      if (lastVisibleImage < imageIndex)
        lastVisibleImage = imageIndex;
      imageIndex++;

    }



    public void renderBorderedImage(String backgroundColor,
        String borderColor, String borderStyle, int borderWidth,
        double width, double height, double x, double y, String url) {

    	addOrRevealImage(backgroundColor, borderColor, borderStyle,
          borderWidth, width, height, x, y, url);

    }

  } // end of class GraphicsRenderingPanel

  // An AbsolutePanel that just turns the protected insert method public
  class InsertableAbsolutePanel extends AbsolutePanel {
    public void insert(Widget child,
        com.google.gwt.user.client.Element container, int beforeIndex,
        boolean domInsert) {
      super.insert(child, container, beforeIndex, domInsert);
    }
  }

  class PlotPanel extends AbsolutePanel {
    private int leftOfYWidth;
    private int rightOfYMaxWidth;
    private int topMargin;
    private int xAxisEnsembleHeight;
    private int xChartSize;
    private double xLB = Double.NaN; // lower bound on x
    private double xUB = Double.NaN; // upper bound on x
    private double xMax = Double.NaN;
    private double xMin = Double.NaN;
    private int yMaxAxisEnsembleWidth;
    /*private double y2LB = Double.NaN;
    private double y2UB = Double.NaN;
    private double y2Max = Double.NaN;
    private double y2Min = Double.NaN;*/
    private int yAxisEnsembleWidth;
    private int chartLegendThickness;
    private int chartFootnotesThickness;
    private int yChartSize;
    private double yLB[] = new double[Y_AXES_MAX];
    private double yUB[] = new double[Y_AXES_MAX];
    private double yMax[] = new double[Y_AXES_MAX];
    private double yMin[] = new double[Y_AXES_MAX];
    // Retains the last moved-to (Event.ONMOUSEMOVE) client mouse position,
    // or NAI if
    // mouse moved away from chart entirely.
    private int clientX = GChart.NAI;
    private int clientY = GChart.NAI;
    // Pixel coords of above mouse position, relative to top-left
    // corner of the GChart (mouse position in GChart's pixel coords)
    private int xMouse = GChart.NAI;
    private int yMouse = GChart.NAI;
    // first rendering panel is reserved for chart decorations,
    // and its overflow outside of the plot panel is never hidden
    final static int DECORATIVE_RENDERING_PANEL_INDEX = 0;
    private InsertableAbsolutePanel graphicsPanel = new InsertableAbsolutePanel();
    private InsertableAbsolutePanel annotationPanel = new InsertableAbsolutePanel();

    private boolean outOfBoundsCheckingDisabled;

    /*
     * Adds a sub-panel of this plot panel that contains the widgets used to
     * render the graphical parts of the given curve <p>
     * 
     * This method must be called just after a new curve is added to the chart,
     * to add its associated graphics rendering panel; GChart assumes each
     * curve (except internal decoration rendering curves, which share a single
     * rendering panel for efficiency) already has a corresponding, unique,
     * rendering panel available and ready to go during updates.
     */

    void addGraphicsRenderingPanel(int rpIndex) {
      final boolean domInsert = true;
      GraphicsRenderingPanel w = new GraphicsRenderingPanel();
      if (getCurveFromRenderingPanelIndex(rpIndex).getClippedToPlotArea()) {
        w.setPixelSize(getXChartSize(), getYChartSize());
        GChart.setOverflow(w, "hidden");
      } else {
        w.setPixelSize(0, 0);
        GChart.setOverflow(w, "visible");
      }
      graphicsPanel.insert(w, graphicsPanel.getElement(), rpIndex,
          domInsert);
      graphicsPanel.setWidgetPosition(w, 0, 0);
    }

    /*
     * Adds a sub-panel of this plot panel that contains the widgets used to
     * render the annnotations of the given curve <p>
     * 
     * This method must be called just after a new curve is added to the chart,
     * to add its associated annotation rendering panel; GChart assumes each
     * curve has a correspondingly indexed rendering panel during updates.
     */

    void addAnnotationRenderingPanel(int rpIndex) {
      final boolean domInsert = true;
      AnnotationRenderingPanel w = new AnnotationRenderingPanel();
      annotationPanel.insert(w, annotationPanel.getElement(), rpIndex,
          domInsert);
      annotationPanel.setWidgetPosition(w, 0, 0);
    }

    /*
     * Removes the rendering panel of the curve with the given internal index on
     * the curves list. <p>
     * 
     * This method must be called just before a curve is removed from the chart,
     * to remove the widgets used to render that curve in the browser.
     */
    void removeGraphicsRenderingPanel(int rpIndex) {
      graphicsPanel.remove(rpIndex);
    }

    void removeAnnotationRenderingPanel(int rpIndex) {
      annotationPanel.remove(rpIndex);
    }

    /*
     * Returns panel used to render the graphical elements of the curve with the
     * given rendering panel index.
     */
    GraphicsRenderingPanel getGraphicsRenderingPanel(int rpIndex) {
      if (0 == graphicsPanel.getWidgetCount()) // for lazy addition
        // smaller,faster if all background curves put on single panel
        for (int i = N_PRE_SYSTEM_CURVES - 1; i < curves.size(); i++) {
          int rpInd = getRenderingPanelIndex(i);
          addGraphicsRenderingPanel(rpInd);
        }
      GraphicsRenderingPanel result = (GraphicsRenderingPanel) (graphicsPanel
          .getWidget(rpIndex));
      return result;
    }

    /*
     * Returns panel used to render the textual elements of the curve with the
     * given rendering panel index.
     */
    AnnotationRenderingPanel getAnnotationRenderingPanel(int rpIndex) {
      if (0 == annotationPanel.getWidgetCount()) // for lazy addition
        // smaller,faster if all background curves put on single panel
        for (int i = N_PRE_SYSTEM_CURVES - 1; i < curves.size(); i++) {
          int rpInd = getRenderingPanelIndex(i);
          addAnnotationRenderingPanel(rpInd);
        }
      AnnotationRenderingPanel result = (AnnotationRenderingPanel) (annotationPanel
          .getWidget(rpIndex));
      return result;
    }

    int getClientX() {
      return clientX;
    }

    void setClientX(int clientX, boolean isClick) {
      /*
       * Due to presumed bugs in FF2 and Chrome, space-bar clicking on
       * TestGChart25's "rotate" button produces bogus 0 and/or (in Chrome)
       * seemingly random negative return values from <tt>event.getClient[XY]
       * with the ONCLICK event. IE7 produces correct mouse coordinates for
       * Event.ONCLICK in this case. The bogus coordinates, if not corrected,
       * generate bogus "mouse moved off chart"-like actions (in TestGChart25,
       * Chrome produces inappropriate deselection of the hovered over point
       * after a space-bar invoked update)<p>
       * 
       * Workaround is to just ignore any 0 or negative coordinates--thus using
       * the last valid coordinates seen by the chart's mouse tracking code in
       * lieu of the bogus ones.
       * 
       * <p>
       * 
       * The resulting 1px "partly-dead" band at the top and left edges of the
       * client area due to this workaround (0 is a valid client coordinate) is
       * unlikely to be a significant problem, since clicked-on stuff is rarely
       * clicked on right along the edges of the client area. <p>
       */
      if (clientX <= 0 && isClick)
        return;
      else if (clientX < 0)
        // some browsers (e.g. FF2) use -1 to indicate undefined mouse
        // coords.
        clientX = GChart.NAI;

      this.clientX = clientX;
      // computing this on-the-fly is VERY expensive, so we retain it
      // (the buffering can be wrong in unusual scrolling scenarios)
      xMouse = (GChart.NAI == clientX) ? GChart.NAI : (Window
          .getScrollLeft()
          + clientX - getAbsoluteLeft());
    }

    int getClientY() {
      return clientY;
    }

    // See comments on analogous lines in setClientX above
    void setClientY(int clientY, boolean isClick) {
      if (clientY <= 0 && isClick)
        return;
      else if (clientY < 0)
        clientY = GChart.NAI;

      this.clientY = clientY;
      yMouse = (GChart.NAI == clientY) ? GChart.NAI : (Window
          .getScrollTop()
          + clientY - getAbsoluteTop());
    }

    /*
     * In IE drop-down list boxes, when you enter the dropdown part of the list,
     * client coordinates suddenly become -1, -1 (presumably IE's way of saying
     * it won't tell you what they are; apparently the dropdown part of the list
     * isn't in the DOM) These repair methods replace such impossible client
     * coordinates with the last valid coordinates. <p>
     * 
     * Without this patch, the geometric "within the hover widget" test on
     * GChartExample20a fails in IE7: as soon as user mouses into the dropdown
     * list, the entire hover widget is closed. <p>
     */
    private int repairBadClientX(int x) {
      if (x <= 0) // 0 isn't strictly bad, but its one of the
        // bad values that can pop up in some browsers.
        return clientX;
      else
        return x;
    }

    private int repairBadClientY(int y) {
      if (y <= 0)
        return clientY;
      else
        return y;
    }

    int getXMouse() {
      return xMouse;
    }

    int getYMouse() {
      return yMouse;
    }

    // Mouse x position relative to plot area upper left corner.
    int getXMousePlotArea() {
      int result = xMouse - leftOfYWidth;
      return result;
    }

    // Mouse y position relative to plot area upper left corner.
    int getYMousePlotArea() {
      int result = yMouse - topMargin;
      return result;
    }

    PlotPanel() {
      super();
      
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  yLB[i] = Double.NaN;
    	  yUB[i] = Double.NaN;
    	  yMax[i] = Double.NaN;
    	  yMin[i] = Double.NaN; 
      }
      // allows labels, symbols, that extend a tad off the
      // chart proper to still appear on the chart; AbsolutePanel
      // default is to truncate these.
      GChart.setOverflow(this, "visible");
      GChart.setOverflow(graphicsPanel, "visible");
      GChart.setOverflow(annotationPanel, "visible");
      // these sub-panels have no size themselves, they are merely
      // there to segregate the graphical and annotation parts of chart
      graphicsPanel.setPixelSize(0, 0);
      annotationPanel.setPixelSize(0, 0);
      // this order assures all the annotations are on top of all the
      // graphics
      this.add(graphicsPanel, 0, 0);
      this.add(annotationPanel, 0, 0);
      // events for hover selection feedback, click event handling
      sinkEvents(Event.ONMOUSEMOVE | Event.ONMOUSEOUT | Event.ONCLICK
          | Event.ONMOUSEOVER);
    }

    int getXAxisEnsembleHeight() {
      return xAxisEnsembleHeight;
    }

    double getXMax() {
      return xMax;
    }

    double getXMin() {
      return xMin;
    }

    int getYMaxAxisEnsembleWidth() {
      return yMaxAxisEnsembleWidth;
    }

    int getRightOfYMaxWidth() {
      return rightOfYMaxWidth;
    }

    double getYMax(int k) {
      return yMax[k];
    }

    double getYMin(int k) {
      return yMin[k];
    }

    int getYAxisEnsembleWidth() {
      return yAxisEnsembleWidth;
    }

    int getLeftOfYWidth() {
      return leftOfYWidth;
    }

    // is the given x,y point out of the plot area hard limits?
    boolean isOutOfBounds(double x, double y) {
      // next line makes 2,000 pt sin curve of live demo 5% faster
      if (outOfBoundsCheckingDisabled)
        return false;
      boolean result = false;
      boolean xAtAxisExtrema = (Double.MAX_VALUE == x || -Double.MAX_VALUE == x);
      boolean yAtAxisExtrema = (Double.MAX_VALUE == y || -Double.MAX_VALUE == y);

      if (!xAtAxisExtrema && (x < xLB || xUB < x))
        result = true;
      else if (!yAtAxisExtrema && (y < yLB[0] || yUB[0] < y))
        result = true;
      else if (!yAtAxisExtrema && yLB[1] != Double.NaN && yUB[1] != Double.NaN && (y < yLB[1] || yUB[1] < y))
        result = true;
      else if (!yAtAxisExtrema && yLB[2] != Double.NaN && yUB[2] != Double.NaN && (y < yLB[2] || yUB[2] < y))
          result = true;
      else if (!yAtAxisExtrema && yLB[3] != Double.NaN && yUB[3] != Double.NaN && (y < yLB[3] || yUB[3] < y))
          result = true;
      else if (!yAtAxisExtrema && yLB[4] != Double.NaN && yUB[4] != Double.NaN && (y < yLB[4] || yUB[4] < y))
          result = true;
      else if (!yAtAxisExtrema && yLB[5] != Double.NaN && yUB[5] != Double.NaN && (y < yLB[5] || yUB[5] < y))
          result = true;
      return result;
    }

    int legendThickness() {
      return chartLegendThickness;
    }

    int chartFootnotesThickness() {
      return chartFootnotesThickness;
    }

    int chartTitleThickness() {
      return topMargin;
    }

    double getYMax() {
      return yMax[0];
    }

    double getYMin() {
      return yMin[0];
    }

    void reset(int xChartSize, int yChartSize, Axis xAxis, Axis[] yAxis) {

      // these must come first (getTickLabelThickness(false) needs them)
      getXAxis().maybePopulateTicks();
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  getYAxis(i).maybePopulateTicks();
      }
      
//      getY2Axis().maybePopulateTicks();

      this.xChartSize = xChartSize;
      this.yChartSize = yChartSize;

      Axis.AxisLimits axisLimits = xAxis.getAxisLimits();
      xMin = axisLimits.min;
      xMax = axisLimits.max;
      double b0 = Math.min(xMin, xMax);
      double b1 = Math.max(xMin, xMax);
      double db = getXAxis().getOutOfBoundsMultiplier() * (b1 - b0);
      xLB = b0 - db;
      xUB = b1 + db;
      
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  axisLimits = yAxis[i].getAxisLimits();
    	  yMin[i] = axisLimits.min;
          yMax[i] = axisLimits.max;
          b0 = Math.min(yMin[i], yMax[i]);
          b1 = Math.max(yMin[i], yMax[i]);
          db = getYAxis(i).getOutOfBoundsMultiplier() * (b1 - b0);
          yLB[i] = b0 - db;
          yUB[i] = b1 + db;
      }

      // if all bounds are undefined, checking is not needed
      boolean isYAxesBoundsUndefined = true;
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  isYAxesBoundsUndefined = (isYAxesBoundsUndefined && (yLB[i] != yLB[i])); 
      }
      outOfBoundsCheckingDisabled = (xLB != xLB && isYAxesBoundsUndefined);

      topMargin = getChartTitleThickness();

      xAxisEnsembleHeight = xAxis.getAxisLabelThickness()
          + xAxis.getTickLabelThickness(false) + xAxis.getTickSpace()
          + xAxis.getTickLabelPadding();
      yAxisEnsembleWidth = yAxis[0].getAxisLabelThickness()
          + yAxis[0].getTickLabelThickness(false) + yAxis[0].getTickSpace()
          + yAxis[0].getTickLabelPadding();
      leftOfYWidth = legendLocation.getLeftThickness(GChart.this)
          + yAxisEnsembleWidth;
      yMaxAxisEnsembleWidth = 0;
      for(int i = 1; i < Y_AXES_MAX; i++){
    	  yMaxAxisEnsembleWidth += yAxis[i].getAxisLabelThickness()
          + yAxis[i].getTickLabelThickness(false)
          + yAxis[i].getTickSpace() + yAxis[i].getTickLabelPadding();
      
      }
      rightOfYMaxWidth = legendLocation.getRightThickness(GChart.this)
          + yMaxAxisEnsembleWidth;
      chartLegendThickness = getLegendThickness();
      chartFootnotesThickness = getChartFootnotesThickness();

      setPixelSize(getXChartSizeDecoratedQuickly(),
          getYChartSizeDecoratedQuickly());

      setWidgetPosition(graphicsPanel, leftOfYWidth, topMargin);
      setWidgetPosition(annotationPanel, leftOfYWidth, topMargin);

      // if there are any existing graphical rendering panels, bring
      // their clipping specs into agreement with the chartspecs
      for (int i = 0; i < getRenderingPanelCount(); i++) {
        GraphicsRenderingPanel grp = (GraphicsRenderingPanel) graphicsPanel
            .getWidget(i);
        if (getCurveFromRenderingPanelIndex(i).getClippedToPlotArea()) {
          grp.setPixelSize(getXChartSize(), getYChartSize());
          GChart.setOverflow(grp, "hidden");
        } else {
          grp.setPixelSize(0, 0);
          GChart.setOverflow(grp, "visible");
        }
      }
    }

    double xToChartPixel(double x) {
      double result = Double.NaN;
      if (-Double.MAX_VALUE == x)
        result = leftOfYWidth;
      else if (Double.MAX_VALUE == x)
        result = leftOfYWidth + xChartSize - 1.0;
      else if (!(x != x)) { // x!=x is a faster isNaN
        result = (leftOfYWidth * (xMax - x) + (leftOfYWidth
            + xChartSize - 1.0)
            * (x - xMin))
            / (xMax - xMin);
      }

      return result;
    }

    double xToPixel(double x) {
      double result = Double.NaN;
      if (-Double.MAX_VALUE == x)
        result = 0;
      else if (Double.MAX_VALUE == x)
        result = xChartSize - 1.0;
      else if (!(x != x)) { // x!=x is a faster isNaN
        result = (xChartSize - 1.0) * (x - xMin) / (xMax - xMin);
      }
      return result;
    }

    double xChartPixelToX(int xPx) {
      double result = Double.NaN;
      if (GChart.NAI != xPx && xChartSize > 1) {
        result = xMin + (xMax - xMin) * (xPx - leftOfYWidth)
            / (xChartSize - 1.);
      }
      return result;
    }

    double xPixelToX(int xPx) {
      double result = Double.NaN;
      if (GChart.NAI != xPx && xChartSize > 1) {
        result = xMin + (xMax - xMin) * xPx / (xChartSize - 1.);
      }
      return result;
    }

    double dxToPixel(double dx) {
      // xMax and xMin are at centers of their pixels, hence the -1
      double result = (dx * (xChartSize - 1)) / (xMax - xMin);
      return result;
    }

    double yToChartPixel(double y, int index) {
      double minY = getYMin(index);
    	  //isY2 ? y2Min : yMin;
      double maxY = getYMax(index);
    	  //isY2 ? y2Max : yMax;
      double result = Double.NaN;
      if (-Double.MAX_VALUE == y)
        result = yChartSize + topMargin - 1.0;
      else if (Double.MAX_VALUE == y)
        result = topMargin;
      else if (!(y != y)) // x!=x is a faster isNaN
        result = (topMargin * (y - minY) + ((yChartSize + topMargin - 1.0) * (maxY - y)))
            / (maxY - minY);
      return result;
    }

    double yToPixel(double y, int index) {
    	
      double minY = getYMin(index);
      double maxY = getYMax(index);
    	  //isY2 ? y2Max : yMax;
      double result = Double.NaN;
      if (-Double.MAX_VALUE == y)
        result = yChartSize - 1.0;
      else if (Double.MAX_VALUE == y)
        result = 0;
      else if (!(y != y)) // x!=x is a faster isNaN
        result = (yChartSize - 1.0) * (maxY - y) / (maxY - minY);
      return result;
    }

    double yChartPixelToY(int yPx) {
      double result = Double.NaN;
      if (GChart.NAI != yPx && yChartSize > 1) {
        result = yMax[0] + (yMin[0] - yMax[0]) * (yPx - topMargin)
            / (yChartSize - 1.);
      }
      return result;
    }

    double yPixelToY(int yPx) {
      double result = Double.NaN;
      if (GChart.NAI != yPx && yChartSize > 1) {
        result = yMax[0] + (yMin[0] - yMax[0]) * yPx / (yChartSize - 1.);
      }
      return result;
    }

    double dyToPixel(double dy, int index) {
      double minY = getYMin(index);
    	  //isY2 ? y2Min : yMin;
      double maxY = getYMax(index); 
    	  //isY2 ? y2Max : yMax;
      // maxY and minY are at centers of their pixels, hence the -1
      double result = (dy * (yChartSize - 1)) / (maxY - minY);
      return result;
    }

    // keep track of last touched point & hover widget
    Curve.Point touchedPoint = null;
    HoverUpdateable touchedHoverWidget = null;

    // returns the inner aligned label of the opened hover annotation
    // (this is the one that directly holds the popup hover annotation)
    AlignedLabel getOpenedHoverContainer() {
      AlignedLabel result = null;
      Curve c = getSystemCurve(HOVER_ANNOTATION_ID);
      if (touchedPoint != null && c.isVisible()) {
        int internalIndex = getInternalCurveIndex(c);
        int rpIndex = getRenderingPanelIndex(internalIndex);
        AnnotationRenderingPanel arp = getAnnotationRenderingPanel(rpIndex);
        result = arp.getFirstInnerAlignedLabel();
      }
      return result;
    }

    // the element associated with any opened hover container, else null
    Element getOpenedHoverElement() {
      AlignedLabel hoverContainer = getOpenedHoverContainer();
      Element result = (null == hoverContainer) ? null : hoverContainer
          .getElement();
      return result;
    }

    // so if user calls update inside hoverUpdate, it won't recurse
    private boolean insideHoverUpdate = false;
    // so if user calls update inside hoverCleanup, it won't recurse
    private boolean insideHoverCleanup = false;

    /*
     * Configures GChart's special selection cursor and hover annotation
     * "system curves" so that they provide appropriate feedback associated with
     * "touching" the given point with the mouse. When rendered, these curves
     * will: <p>
     * 
     * <ol> <li> Highlight the selected point in accord with specified hover
     * selection options <p> <li> Display a point-specific hover annotation, in
     * accord with various hover annotation related options.
     * 
     * </ol>
     * 
     * <p>
     * 
     * Also executes <tt>hoverCleanup</tt> on any hover widget associated with
     * the previously touched point, and <tt>hoverUpdate</tt> on any hover
     * widget associated with the newly touched point, and maintains up-to-date
     * references to the last touched point and last touched hover widget.
     */
    private void touch(Curve.Point p) {
      // Note: getTouchedPoint always returns NEW touched point
      Curve.Point prevTouchedPoint = touchedPoint;
      touchedPoint = p;
      Curve cAnnotation = getSystemCurve(HOVER_ANNOTATION_ID);
      Curve cCursor = getSystemCurve(HOVER_CURSOR_ID);
      Curve cTouched = (null == p) ? null : p.getParent();

      if (null != touchedHoverWidget) {
        // free up resources allocated to previous hover widget
        if (!insideHoverCleanup) {
          try {
            insideHoverCleanup = true;
            touchedHoverWidget.hoverCleanup(prevTouchedPoint);
          } finally {
            insideHoverCleanup = false;
          }
        }

      }

      // with hoverCleanup out of the way, switch to new hover widget
      touchedHoverWidget = (null == cTouched) ? null : cTouched
          .getSymbol().getHoverWidget();

      if (null == touchedHoverWidget) {
        if (null != p) {
          // no hover-widget, just use expanded hover-template
          String hovertext = p.getHovertext();

          cAnnotation
              .getPoint(0)
              .setAnnotationText(
              hovertext,
              cTouched.getSymbol().getHoverAnnotation().widthUpperBound,
              cTouched.getSymbol().getHoverAnnotation().heightUpperBound);
        }
      } else {
        // touched curve has custom hover widget; update it, etc.
        if (!insideHoverUpdate) {
          try {
            insideHoverUpdate = true;
            touchedHoverWidget.hoverUpdate(p);
          } finally {
            insideHoverUpdate = false;
          }
        }
        cAnnotation
            .getPoint(0)
            .setAnnotationWidget(
            (Widget) touchedHoverWidget,
            cTouched.getSymbol().getHoverAnnotation().widthUpperBound,
            cTouched.getSymbol().getHoverAnnotation().heightUpperBound);
      }

      if (null == p) { // no longer touching anything
        cAnnotation.setVisible(false);
        cCursor.setVisible(false);
      } else { // touching something, show that

        if (!cTouched.getSymbol().getHoverAnnotationEnabled()) {
          cAnnotation.setVisible(false);
        } else {
          cAnnotation.setVisible(true);
          cAnnotation.setXShift(cTouched.getXShift());
          cAnnotation.setYShift(cTouched.getYShift());
          cAnnotation.setYAxis(cTouched.getYAxis());
          cAnnotation.getPoint(0).setX(p.getX());
          cAnnotation.getPoint(0).setY(p.getY());
          cAnnotation.getSymbol().copy(cTouched.getSymbol());
          // the symbol isn't needed, so make it transparent
          // and zap any images (can't make it 0-sized since
          // annotation placement is size-dependent)
          cAnnotation.getSymbol().setImageURL(
              GChart.DEFAULT_BLANK_IMAGE_URL_FULLPATH);
          cAnnotation.getSymbol().setBackgroundColor("transparent");
          cAnnotation.getSymbol().setBorderColor(
              TRANSPARENT_BORDER_COLOR);
          if (null != cTouched.getSymbol()
              .getHoverAnnotationSymbolType())
            cAnnotation.getSymbol().setSymbolType(
                cTouched.getSymbol()
                .getHoverAnnotationSymbolType());
          // else just stick with the touched symbol's type

          // copy the hover annotations specs (including
          // hover widget ref or HTML defined above)
          // to the annotation curve's point
          cAnnotation.getPoint(0).setAnnotationFontColor(
              cTouched.getSymbol().getHoverFontColor());
          cAnnotation.getPoint(0).setAnnotationFontSize(
              cTouched.getSymbol().getHoverFontSize());
          cAnnotation.getPoint(0).setAnnotationFontFamily(
              cTouched.getSymbol().getHoverFontFamily());
          cAnnotation.getPoint(0).setAnnotationFontStyle(
              cTouched.getSymbol().getHoverFontStyle());
          cAnnotation.getPoint(0).setAnnotationFontWeight(
              cTouched.getSymbol().getHoverFontWeight());
          cAnnotation.getPoint(0).setAnnotationLocation(
              cTouched.getSymbol().getHoverLocation());
          cAnnotation.getPoint(0).setAnnotationXShift(
              cTouched.getSymbol().getHoverXShift());
          cAnnotation.getPoint(0).setAnnotationYShift(
              cTouched.getSymbol().getHoverYShift());
        }

        if (!cTouched.getSymbol().getHoverSelectionEnabled()) {
          cCursor.setVisible(false);
        } else {
          cCursor.setVisible(true);
          cCursor.setXShift(cTouched.getXShift());
          cCursor.setYShift(cTouched.getYShift());
          cCursor.setYAxis(cTouched.getYAxis());
          // place cursor curve's point where touched point is:
          cCursor.getPoint(0).setX(p.getX());
          cCursor.getPoint(0).setY(p.getY());
          // cursor gets (mostly) same props as touched symbol
          cCursor.getSymbol().copy(cTouched.getSymbol());
          if (null != cTouched.getSymbol()
              .getHoverSelectionSymbolType())
            cCursor.getSymbol().setSymbolType(
                cTouched.getSymbol()
                .getHoverSelectionSymbolType());
          double fillSpacing = cTouched.getSymbol()
              .getHoverSelectionFillSpacing();
          if (!(fillSpacing != fillSpacing))
            cCursor.getSymbol().setFillSpacing(fillSpacing);
          int fillThickness = cTouched.getSymbol()
              .getHoverSelectionFillThickness();
          if (GChart.NAI != fillThickness)
            cCursor.getSymbol().setFillThickness(fillThickness);
          if (GChart.NAI != cTouched.getSymbol()
              .getHoverSelectionHeight())
            cCursor.getSymbol().setHeight(
                cTouched.getSymbol().getHoverSelectionHeight());
          if (GChart.NAI != cTouched.getSymbol()
              .getHoverSelectionWidth())
            cCursor.getSymbol().setWidth(
                cTouched.getSymbol().getHoverSelectionWidth());
          cCursor.getSymbol().setImageURL(
              cTouched.getSymbol().getHoverSelectionImageURL());
          cCursor.getSymbol().setBackgroundColor(
              cTouched.getSymbol()
              .getHoverSelectionBackgroundColor());
          cCursor.getSymbol()
              .setBorderColor(
              cTouched.getSymbol()
              .getHoverSelectionBorderColor());
          cCursor.getSymbol()
              .setBorderStyle(
              cTouched.getSymbol()
              .getHoverSelectionBorderStyle());
          int borderWidth = cTouched.getSymbol()
              .getHoverSelectionBorderWidth();
          cCursor.getSymbol().setBorderWidth(borderWidth);
        }
      }
    }

    /*
     * Is the given target element contained within the given container? (tried
     * isOrHasChild but was getting exceptions in FF2 I could not track to their
     * source, so I just stuck with this)
     */
    private boolean isContainedIn(Element container, EventTarget et) {
      Element part = (null == et || !Element.is(et)) ? null : Element
          .as(et);
      /*
       * In Chrome and FF2, the next line makes dropdown lists inside hover
       * widgets work more correctly when they click on the dropdown part of the
       * list. Otherwise, hover widget can close inappropriately. As tested in
       * GChartExample20a.java
       */
      if (null == part)
        return true;
      try {
        for (Element ancestor = part; ancestor != null
            && container != null; ancestor = ancestor
            .getParentElement())
          if (container == ancestor)
            return true;
      } catch (Exception e) {
        /*
         * In FF2, we get the error "Error: uncaught exception: Permission
         * denied to get property HTMLDivElement.parentNode" if a TextBox is
         * placed into the chart x axis label and you mouse over that textbox
         * (as reported by secnoc in issue #24, which has additional useful info
         * about the likely cause of this problem; TestGChart44.java reproduces
         * this behavior if this try/catch is removed).
         * 
         * Returning true, which will act as if the element is contained in the
         * GChart, has "cruft possibilities" as in some cases a hoverwidget may
         * not get closed when user mouses completely out of the GChart, onto a
         * TextBox, but should otherwise be a lesser evil than false or an
         * uncaught exception.
         */
        return true;

      }
      return false;
    }

    /*
     * Is given mouse client point geometrically "inside" container? <p>
     * 
     * Note: Certain widgets, like drop-down lists, SuggestBox, etc. implicitly
     * create popups that are not children of the chart, and thus when the user
     * moves into these popups, a mouseout that looks like they are leaving the
     * hover widget or chart, and thus inappropriately hides it can often make
     * hover widgets involving such elements unusable.
     * 
     * <p>
     * 
     * But, by adding a geometric condition to define "being out of the chart or
     * hover widget", hover widgets that use such popups can avoid such
     * inappropriate hiding as long as the popups remain geometrically within
     * either the chart as a whole, or the hover widget itself. For example a
     * form with drop-down lists is OK as long as the drop down lists don't
     * extrude off the form when they drop down.<p>
     * 
     * Unfortunately, this creates another problem, in that hover widgets that
     * extrude off the chart can remain open when the user mouses off the chart
     * in such a way that the mouseout occurs at a point geometrically within
     * the hover widget. We consider this small amount of "hover cruft" a lesser
     * evil than not being able to use dropdown lists and such within hover
     * widgets at all. <p>
     * 
     * Often a more natural solution, rather than wrestling with such
     * "geometrical popup containment", is a click-invoked modal dialog. But
     * that has a distinctly different feel than the more nearly modeless hover
     * widget, so I thought there was room/need for both approaches.
     */
    private boolean isGeometricallyContainedIn(Element container,
        int clientX, int clientY) {

      if (null == container)
        throw new IllegalArgumentException("Container cannot be null");
      boolean result = false;

      /*
       * Equations below shrink container 1px around its perimeter, to account
       * for apparent roundoff errors in FF2 associated with window scrolling.
       * FF2 mouseout events get dropped (about half the time, with random
       * scroll position choices hence my roundoff suspicions--problem did not
       * occur in Chrome or IE7) without this 1px shrinkage. End users can't
       * discriminate a 1px mouse shift anyway, so there is no real downside
       * (except that you had to read this comment) to using this workaround.
       */
      int y = Window.getScrollTop() + repairBadClientY(clientY);
      int absTop = container.getAbsoluteTop();
      if (absTop < y && y + 1 < absTop + container.getOffsetHeight()) {
        int x = Window.getScrollLeft() + repairBadClientX(clientX);
        int absLeft = container.getAbsoluteLeft();
        if (absLeft < x && x + 1 < absLeft + container.getOffsetWidth())
          result = true;
      }

      return result;
    }

    // Touches the underlying object at the last event's mouse
    // position if it is different from the currently touched point,
    // or if retouch is true. Returns true if a touch occured.
    private boolean touchObjectAtMousePosition(boolean retouch) {
      boolean result = false;
      Curve.Point pointAtPosition = getClosestBrushTouchingPointNoCheck(
          getXMousePlotArea(), getYMousePlotArea());
      if ((pointAtPosition != touchedPoint) || retouch) {
        touch(pointAtPosition);
        result = true;
      }
      return result;
    }

    // touch object at mouse, but only if it is a different one
    boolean touchObjectAtMousePosition() {
      boolean result = touchObjectAtMousePosition(false);
      return result;
    }

    // touch object at mouse, even if it is the same one as last time
    void retouchObjectAtMousePosition() {
      touchObjectAtMousePosition(true);
    }

    /*
     * Does the event occur over the currently opened hover annotation?
     * 
     * This method helps us to ignore mouse clicks and moves over a
     * "sticky-open" hover annotation, or one of its children. Idea is to
     * prevent the hover feedback from jumping to another point while the user
     * interacts with an opened hover widget. <p>
     * 
     * This is not just important for hover annotations that contain buttons and
     * such, but even for hover annotations based on static text, since the user
     * might want to select/copy that text (involving mouse moves) for example.
     */
    private boolean isOverOpenedHoverAnnotation(Event event) {
      boolean result = false;
      Element hoverElement = getOpenedHoverElement();
      if (null != hoverElement) {
        if (isContainedIn(hoverElement, event.getEventTarget()))
          result = true;
        else if (isGeometricallyContainedIn(hoverElement, event
            .getClientX(), event.getClientY()))
          result = true;
      }
      return result;
    }

    /*
     * Does event (assumed a MOUSEOUT) take the mouse completely outside of the
     * current chart? <p>
     * 
     * To be completely outside the chart, the event must be both not associated
     * with any child element of the chart (as represented in the DOM) and
     * geometrically outside of the chart's "box" and the "box" of any currently
     * opened hover annotation. <p>
     * 
     * Because a GChart is rendered with many DOM elements, moving the mouse
     * across it generates many MOUSEOUT events. This method lets us focus on
     * only those that take us completely off the chart, and thus require the
     * hover feedback to be turned off. <p>
     */
    private boolean takesUsCompletelyOutsideChart(Event event) {
      boolean result = true;

      if (isContainedIn(getElement(), event.getRelatedEventTarget()))
        /*
         * hoverElement is always a descendant of the main chart element due to
         * how GChart generates it, so if this branch isn't reached, toElement
         * is not contained in either chart or the opened hover annotation
         */
        result = false;
      else if (isGeometricallyContainedIn(getElement(), event
          .getClientX(), event.getClientY()))
        result = false;
      else {
        Element hoverElement = getOpenedHoverElement();
        if (null != hoverElement) {
          if (isGeometricallyContainedIn(hoverElement, event
              .getClientX(), event.getClientY()))
            result = false;
        }
      }

      return result;

    }

    /**
     * Fired whenever a browser event is recieved.
     * <p>
     * 
     * GChart keeps track of browser mouse-moves, mouse-outs, mouse-overs, and
     * mouse clicks and will automatically provide appropriate hover feedback
     * whenever the mouse "touches" rendered symbols on the chart. It also
     * maintains a reference to the "currently touched" point which you can
     * retrieve via the <tt>getTouchedPoint</tt> method.
     * <p>
     * 
     * GChart never "eats" mouse events (it just watches them go by, and keeps
     * track of what the mouse-anchored brush is touching) so containing Widgets
     * can track and respond to the same mouse event stream after GChart does,
     * if they want to.
     * 
     * <p>
     * 
     * Each curve's symbol can configure how hover feedback is displayed via the
     * <tt>setHover*</tt> family of methods and the related HoverUpdateable and
     * HoverParameterInterpreter interfaces. In addition, GChart implements the
     * standard GWT <tt>SourcesClickEvents</tt> interface, so you can easily
     * implement <tt>ClickListener.onClick</tt> to be notified of user clicks on
     * a GChart, using <tt>getTouchedPoint</tt> to grab the clicked-on point.
     * <p>
     * 
     * This method can only properly track events when <tt>isUpdateNeeded</tt>
     * returns false (implies DOM/GChart specs are in synch) so if you want this
     * tracking system to work as intended, you need to be sure to always call
     * <tt>update</tt> after making a series of chart specification changes,
     * just before you give control back to the browser.
     * 
     * 
     * @param event
     *          the browser event that GChart will monitor so as to maintain a
     *          reference to the "touched" point and provide appropriate hover
     *          feedback.
     * 
     * @see #touch touch
     * @see #getTouchedPoint getTouchedPoint
     * @see #setHoverWidget setHoverWidget
     * @see #setHovertextTemplate setHovertextTemplate
     * @see #setHoverAnnotationEnabled setHoverAnnotationEnabled
     * @see #setHoverSelectionEnabled setHoverSelectionEnabled
     * @see HoverUpdateable HoverUpdateable
     * @see #setHoverParameterInterpreter setHoverParameterInterpreter
     * @see HoverParameterInterpreter HoverParameterInterpreter
     * @see #isUpdateNeeded isUpdateNeeded
     * @see #update update
     * 
     */
    public void onBrowserEvent(Event event) {
      // GWT docs say without this, 1.6+ event handlers won't work
      super.onBrowserEvent(event);

      /*
       * The tracking of the mouse position depends on if there are opened hover
       * annotations or not (mouse moves over such annotations don't get
       * tracked, and thus don't change the "touched" point). However, all of
       * that dependency can be determined by the current DOM rendering of the
       * chart--there is no need to look at actual chart specs. <p>
       * 
       * So, when the DOM/chart specs are inconsistent (chart "needs update") we
       * continue to perform mouse tracking based on the <i>last completed DOM
       * rendering</i> of the chart (that is, the last <tt>assembleChart</tt>
       * call). However, actual changes to the chart are "frozen" (as assured by
       * the <tt>!isUpdateNeeded</tt> test below) so no DOM changes occur
       * automatically in response to mouse moves over things (e.g. no changes
       * to hover feedback occur--that's frozen, too). In short, we track, but
       * do not act.
       * 
       * <p>
       * 
       * We can think of it this way: mouse tracking remains consistent with the
       * <i>last DOM rendered</i> specification and then it is as if all of the
       * accumulated specification changes are applied to the DOM at that point
       * in time when the next (developer invoked) update occurs. That means
       * there is exactly one point in time of "unpredictable change" (points
       * previously hovered over can disappear from under the mouse since they
       * have been deleted or moved, etc.). But that discontinuity can be
       * adequately managed by the developer via the
       * <tt>TouchedPointUpdateOption</tt> argument to update. <p>
       * 
       * Well, that's the theory. But GChart's mouse tracking has only been
       * tested for the case where <tt>update</tt> is always called just before
       * the developer ceeds control back to the browser after making a series
       * of chart spec changes. So, the public docs warn developers to be sure
       * that they do that, too. But the hope is that specialized applications
       * where they don't call update until the user explicitly asks for that
       * (say, for a very busy chart with an editing capability and a "refresh"
       * button) will also work OK.
       * 
       * <p>
       * 
       * Another important consequence of this "track but don't act" approach is
       * that it assures that only cheap/quick operations can be triggered
       * automatically by direct user mousing. Potentially expensive
       * "full chart" updates always require a direct developer update
       * invocation. So, if the system
       * "locks up while it's doing a lengthly update" there will always be an
       * actual developer line of code responsible for that, not some
       * mysteriously event-triggered call.
       */

      int eventId = DOM.eventGetType(event);
      /*
       * Note that a click that closes a modal DialogBox can generate a mouse
       * location change without an ONMOUSEMOVE, and a point that moves under
       * the mouse due to an update can generate a mouseover without a MOUSEMOVE
       */
      boolean isClick = (Event.ONCLICK == eventId);
      if ((Event.ONMOUSEMOVE == eventId || Event.ONMOUSEOVER == eventId || isClick)
          && !isOverOpenedHoverAnnotation(event)) {
        // remember last "tracked" mouse location
        // if (Event.ONCLICK == eventId)
        // Window.alert("CLICK: event.getClientX()=" +
        // event.getClientX() +
        // " event.getClientY()=" + event.getClientY() +
        // " event.getTarget()==this.getElement() is " +
        // (event.getTarget() == this.getElement()) +
        // " event.getCurrentTarget()="+event.getCurrentTarget() +
        // " event.getTarget()=" + event.getTarget());
        // else if (Event.ONMOUSEOVER == eventId)
        // Window.alert("MOUSEOVER: event.getClientX()=" +
        // event.getClientX() +
        // " event.getClientY()=" + event.getClientY() +
        // " event.getCurrentTarget()="+event.getCurrentTarget() +
        // " event.getTarget()=" + event.getTarget());
        if (getHoverTouchingEnabled() || isClick) {
          setClientX(event.getClientX(), isClick);
          setClientY(event.getClientY(), isClick);
          if (!isUpdateNeeded()
              && touchObjectAtMousePosition(isClick))
            assembleChart();
        }
      } else if (Event.ONMOUSEOUT == eventId && getHoverTouchingEnabled()
          && takesUsCompletelyOutsideChart(event)) {
        // Window.alert("MOUSEOUT: event.getClientX()=" +
        // event.getClientX() +
        // " event.getClientY()=" + event.getClientY() +
         // " event.getCurrentTarget()="+event.getCurrentTarget() +
        // " event.getTarget()=" + event.getTarget());
        setClientX(GChart.NAI, false); // mouse not over chart,
        setClientY(GChart.NAI, false); // so position is undefined
        if (!isUpdateNeeded() && touchObjectAtMousePosition())
          assembleChart();
      }

    }

    // Is chart's DOM rendering consistent with its specs?
    boolean isValidated() {
      boolean result = true;
      for (int i = 0; result && i < curves.size(); i++)
        result = curves.get(i).isValidated();
      return result;
    }

    /*
     * Returns number of "rendering panels" that there actually are right now.
     * 
     * GChart's implementation assures that this number is exactly the same for
     * the graphics and annotation rendering panels.
     */
    int getRenderingPanelCount() {
      int result = graphicsPanel.getWidgetCount();
      return result;
    }

    int getXChartSize() {
      return xChartSize;
    }

    int getYChartSize() {
      return yChartSize;
    }

    // quickly returns decorated xChartsize as of the last plotPanel.reset
    int getXChartSizeDecoratedQuickly() {
      int result = xChartSize + leftOfYWidth + rightOfYMaxWidth;
      return result;
    }

    // quickly returns decorated yChartsize as of the last plotPanel.reset
    int getYChartSizeDecoratedQuickly() {
      int result = yChartSize + xAxisEnsembleHeight + topMargin
          + chartFootnotesThickness;
      return result;
    }

    void setRenderingPosition(GraphicsRenderingPanel grp,
        AnnotationRenderingPanel arp, int xShift, int yShift) {
      // "-" is the cartesian to pixel transformation
      grp.setRenderingPosition(xShift, yShift);
      annotationPanel.setWidgetPosition(arp, xShift, -yShift);
    }

  } // end of class PlotPanel

  // axis types (used to define which y-axis each curve is on)
  private static class YAxisId {
  }

  // case-independent index of next "break" tag in string (case of HTML
  // returned from HasHTML.getHTML can change with browser)
  private static int indexOfBr(String s, int iStart) {
    final String BR1 = "<br>";
    final String BR2 = "<BR>";
    final String BR3 = "<li>"; // recognize <li> as a break.
    final String BR4 = "<LI>";
    final String BR5 = "<tr>"; // recognize <tr> as a break.
    final String BR6 = "<TR>";
    int iBr1 = s.indexOf(BR1, iStart);
    int iBr2 = s.indexOf(BR2, iStart);
    int iBr3 = s.indexOf(BR3, iStart);
    int iBr4 = s.indexOf(BR4, iStart);
    int iBr5 = s.indexOf(BR5, iStart);
    int iBr6 = s.indexOf(BR6, iStart);
    int result1 = 0;
    int result2 = 0;
    int result3 = 0;
    int result = 0;

    if (-1 == iBr1)
      result1 = iBr2;
    else if (-1 == iBr2)
      result1 = iBr1;
    else
      result1 = Math.min(iBr1, iBr2);

    if (-1 == iBr3)
      result2 = iBr4;
    else if (-1 == iBr4)
      result2 = iBr3;
    else
      result2 = Math.min(iBr3, iBr4);

    if (-1 == iBr5)
      result3 = iBr6;
    else if (-1 == iBr6)
      result3 = iBr5;
    else
      result3 = Math.min(iBr5, iBr6);

    if (-1 == result1)
      result = result2;
    else if (-1 == result2)
      result = result1;
    else
      result = Math.min(result1, result2);

    if (-1 == result)
      result = result3;
    else if (-1 != result3)
      result = Math.min(result, result3);

    return result;

  }

  private static int indexOfBr(String s) {
    return indexOfBr(s, 0);
  }

  // Provides a character-based width estimate when simple tags
  // such as <b> and <i> are present in a multi-line,
  // "break"-delimited, string. Very approximate, but a useful
  // default.
  private static int htmlWidth(String sIn) {
    int iBr = indexOfBr(sIn);
    String s = (-1 == iBr) ? sIn : sIn.substring(0, iBr);
    final String LITERAL_PAT = "[&][#a-zA-Z]+[;]";
    s = s.replaceAll(LITERAL_PAT, "X"); // literals count as 1 char
    final String TAG_PAT = "[<][^>]+[>]";
    s = s.replaceAll(TAG_PAT, ""); // tags don't count at all
    return s.length();
  }

  // number of <br> delimited lines in an HTML string
  private static int htmlHeight(String s) {
    final int BR_LEN = 4;
    int iBr = 0;
    int result = 1;
    if (null != s) {
      for (iBr = indexOfBr(s); iBr != -1; iBr = indexOfBr(s, iBr + BR_LEN))
        result++;
    }
    return result;
  }

  /*
   * Annotates (labels) a chart symbol. Users access this class via wrapper
   * methods of the Point class, and via various tick-label related methods of
   * the Axis class.
   */

  static class Annotation {
    String fontColor = DEFAULT_FONT_COLOR;
    int fontSize = DEFAULT_ANNOTATION_FONTSIZE;
    String fontFamily = null; // will cause it to adapt parent GChart's
    String fontStyle = "normal";
    String fontWeight = "normal";
    AnnotationLocation location = null;
    String text = null;
    Widget widget = null; // may be used in lieu of text or HTML
    boolean isVisible = true;
    int xShift = 0;
    int yShift = 0;
    // no break tags ==> plain text Estimated number of lines, width in
    // chars, of annotation text (not used by Widgets)
    boolean isHTML = false;
    int numberOfLinesHigh = 0;
    int numberOfCharsWide = 0;
    int widthUpperBound = GChart.NAI;
    int heightUpperBound = GChart.NAI;
    static final int HTML_LEN = 6; // <html>
    static final int BR_LEN = 4;   // <br>

    /*
     * Computes parameters used to estimate the width and height of the
     * (invisible) enclosing 1x1 Grid of an annotation (used to align, center,
     * etc. the annotation) <p>
     */
    private String analyzeHTML(String s) {
      String result = null;
      if (null == s) {
        isHTML = false;
        numberOfLinesHigh = 0;
        numberOfCharsWide = 0;
      } else if (!s.startsWith("<html>")) { // no html==>plain text
        isHTML = false;
        numberOfLinesHigh = 1;
        numberOfCharsWide = s.length();
        result = s;
      } else { // HTML
        isHTML = true;
        // <html> is just a flag, not a tag, so strip it out.
        result = s.substring(HTML_LEN);
        if (widthUpperBound == GChart.NAI)
          numberOfCharsWide = htmlWidth(result);

        if (heightUpperBound == GChart.NAI)
          numberOfLinesHigh = htmlHeight(result);

      }
      return result;

    }

    // Returns number of chars in first <br>-delimited line of
    // given string. A very crude way to estimate (especially
    // HTML) width in characters, but user can give explicit
    // widths when the width estimates based on this char width
    // heuristic fail them.
    static int getNumberOfCharsWide(String s) {
      int result = 0;
      if (!s.startsWith("<html>")) {
        result = s.length();
      } else {
        result = htmlWidth(s);
      }
      return result;
    }

    String getFontColor() {
      return fontColor;
    }

    int getFontSize() {
      return fontSize;
    }

    AnnotationLocation getLocation() {
      return location;
    }

    boolean isHTML() {
      return isHTML;
    }

    String getText() {
      return (isHTML ? ("<html>" + text) : text);
    }

    boolean getVisible() {
      return isVisible;
    }

    int getXShift() {
      return xShift;
    }

    int getYShift() {
      return yShift;
    }

    void setFontColor(String cssColor) {
      this.fontColor = cssColor;
    }

    void setFontSize(int fontSize) {
      this.fontSize = fontSize;
    }

    void setFontWeight(String cssWeight) {
      this.fontWeight = cssWeight;
    }

    void setFontFamily(String cssFontFamily) {
      this.fontFamily = cssFontFamily;
    }

    void setFontStyle(String cssStyle) {
      this.fontStyle = cssStyle;
    }

    String getFontWeight() {
      return fontWeight;
    }

    String getFontFamily() {
      return fontFamily;
    }
    String getFontFamilyExpanded(String defFontFamily) {
      String result = fontFamily;
      if (null == result)
        result = defFontFamily;
      return result;
    }
    
    String getFontStyle() {
      return fontStyle;
    }

    void setLocation(AnnotationLocation location) {
      this.location = location;
    }

    void setText(String text, int widthUpperBound, int heightUpperBound) {
      this.widthUpperBound = widthUpperBound;
      this.heightUpperBound = heightUpperBound;
      this.text = analyzeHTML(text);
      this.widget = null;
    }

    void setText(String text) {
      setText(text, GChart.NAI, GChart.NAI);
    }

    void setVisible(boolean isVisible) {
      this.isVisible = isVisible;
    }

    void setWidget(Widget widget, int widthUpperBound, int heightUpperBound) {
      this.widthUpperBound = widthUpperBound;
      this.heightUpperBound = heightUpperBound;
      this.text = null;
      this.widget = widget;
    }

    void setWidget(Widget widget) {
      setWidget(widget, DEFAULT_WIDGET_WIDTH_UPPERBOUND,
          DEFAULT_WIDGET_HEIGHT_UPPERBOUND);
    }

    Widget getWidget() {
      return widget;
    }

    void setXShift(int xShift) {
      this.xShift = xShift;
    }

    void setYShift(int yShift) {
      this.yShift = yShift;
    }

    int getHeightUpperBound() {
      int result = 0;
      if (heightUpperBound != GChart.NAI)
        result = heightUpperBound;
      else {
        result = (int) Math.ceil(fontSize * numberOfLinesHigh
            * CHARHEIGHT_TO_FONTSIZE_UPPERBOUND);
      }
      return result;
    }

    int getWidthUpperBound() {
      int result = 0;
      if (widthUpperBound != GChart.NAI)
        result = widthUpperBound;
      else {
        result = (int) Math.ceil(fontSize * numberOfCharsWide
            * CHARWIDTH_TO_FONTSIZE_UPPERBOUND);
      }
      return result;
    }

  } // end of class Annotation

  /**
   * Default size, in pixels, of text used to annotate individual plotted points
   * on a curve.
   * 
   * @see Curve.Point#setFontSize Point.setFontSize
   */
  public static final int DEFAULT_ANNOTATION_FONTSIZE = 12;

  /**
   * Default pixel height of rectangular "brush" that defines how close the
   * mouse cursor must be to a rendered symbol for it to be "touched" (which
   * pops up its hover feedback).
   * 
   * @see Symbol#setBrushHeight setBrushHeight
   * @see Symbol#setBrushWidth setBrushWidth
   * @see #DEFAULT_BRUSH_WIDTH DEFAULT_BRUSH_WIDTH
   */
  public static final int DEFAULT_BRUSH_HEIGHT = 1;
  /**
   * 
   * Default pixel width of rectangular "brush" that defines how close the mouse
   * cursor must be to a rendered symbol for it to be "touched" (which pops up
   * its hover feedback).
   * 
   * @see Symbol#setBrushHeight setBrushHeight
   * @see Symbol#setBrushWidth setBrushWidth
   * @see #DEFAULT_BRUSH_HEIGHT DEFAULT_BRUSH_HEIGHT
   */
  public static final int DEFAULT_BRUSH_WIDTH = 1;

  /**
   * Default color of border around the chart legend
   * 
   * @see #setLegendBorderColor setLegendBorderColor
   * 
   */
  public static final String DEFAULT_LEGEND_BORDER_COLOR = "black";
  /**
   * Default width of border around the chart legend
   * 
   * @see #setLegendBorderWidth setLegendBorderWidth
   * 
   */
  public static final int DEFAULT_LEGEND_BORDER_WIDTH = 1;
  /**
   * Default style of border around the chart legend
   * 
   * @see #setLegendBorderStyle setLegendBorderStyle
   * 
   */
  public static final String DEFAULT_LEGEND_BORDER_STYLE = "solid";

  /**
   * Default color of background of the chart legend
   * 
   * @see #setLegendBackgroundColor setLegendBackgroundColor
   * 
   */
  public static final String DEFAULT_LEGEND_BACKGROUND_COLOR = "transparent";
  /**
   * The default color of any text appearing in a chart's legend, annotations,
   * or tick labels.
   * 
   * @see #setLegendFontColor setLegendFontColor
   * @see Axis#setTickLabelFontColor setTickLabelFontColor
   * @see Curve.Point#setAnnotationFontColor setAnnotationFontColor
   * 
   */
  public final static String DEFAULT_FONT_COLOR = "black";
  /**
   * The font-family that GChart uses by default for all textual
   * annotations on a chart (tick labels, point annotation, legend key
   * text, titles, and footnotes).
   * <p>
   *
   * Chosen to be the same font-family as that used in GWT's standard
   * CSS stylesheet ("theme"). <p>
   * 
   * @see #setLegendFontFamily setLegendFontFamily
   * @see Axis#setTickLabelFontFamily setTickLabelFontFamily
   * @see Curve.Point#setAnnotationFontFamily setAnnotationFontFamily
   * 
   */
  public static final String DEFAULT_FONT_FAMILY = "Arial Unicode MS, Arial, sans-serif";
  /**
   * Default style of axis label and legend fonts.
   * 
   * @see #setLegendFontStyle setLegendFontStyle
   * @see Axis#setTickLabelFontStyle setTickLabelFontStyle
   * @see Curve.Point#setAnnotationFontStyle setAnnotationFontStyle
   * 
   */
  public static final String DEFAULT_FONT_STYLE = "normal";
  /**
   * Default weight of axis label and legend fonts.
   * 
   * @see #setLegendFontWeight setLegendFontWeight
   * @see Axis#setTickLabelFontWeight setTickLabelFontWeight
   * @see Curve.Point#setAnnotationFontWeight setAnnotationFontWeight
   * 
   */

  public static final String DEFAULT_FONT_WEIGHT = "normal";

  /**
   * The default template string used to generate the hovertext displayed when
   * the user hovers their mouse above a point on a curve (pie slices have a
   * different default).
   * 
   * @see Symbol#setHovertextTemplate setHovertextTemplate
   * @see #DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE
   *      DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE
   * 
   */
  public static final String DEFAULT_HOVERTEXT_TEMPLATE = GChart
      .formatAsHovertext("(${x}, ${y})");
  /**
   * The default hover feedback location used to position the hover feedback
   * when the user hovers their mouse above a point on a curve (pie slices, and
   * bar symbols have different defaults).
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_PIE_SLICE_HOVER_LOCATION DEFAULT_PIE_SLICE_HOVER_LOCATION
   * @see #DEFAULT_VBAR_BASELINE_HOVER_LOCATION
   *      DEFAULT_VBAR_BASELINE_HOVER_LOCATION
   * @see #DEFAULT_VBARBOTTOM_HOVER_LOCATION DEFAULT_VBARBOTTOM_HOVER_LOCATION
   * @see #DEFAULT_VBARTOP_HOVER_LOCATION DEFAULT_VBARTOP_HOVER_LOCATION
   * @see #DEFAULT_HBAR_BASELINE_HOVER_LOCATION
   *      DEFAULT_HBAR_BASELINE_HOVER_LOCATION
   * @see #DEFAULT_HBARLEFT_HOVER_LOCATION DEFAULT_HBARLEFT_HOVER_LOCATION
   * @see #DEFAULT_HBARRIGHT_HOVER_LOCATION DEFAULT_HBARRIGHT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_HOVER_LOCATION = AnnotationLocation.NORTHWEST;
  /**
   * The default fontsize of text that appears in the chart's legend (key).
   * 
   * @see Axis#setTickLabelFontSize setTickLabelFontSize
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   * 
   */
  public final static int DEFAULT_LEGEND_FONTSIZE = 12;

  /**
   * The default background color used for the chart's plot area if none is
   * specified.
   * 
   * @see #setPlotAreaBackgroundColor setPlotAreaBackgroundColor
   * 
   */
  public final static String DEFAULT_PLOTAREA_BACKGROUND_COLOR = "transparent";
  /**
   * The default border color used for the chart's plot area if none is
   * specified.
   * 
   * @see #setPlotAreaBorderColor setPlotAreaBorderColor
   * 
   */
  public final static String DEFAULT_PLOTAREA_BORDER_COLOR = "black";
  /**
   * The default style of the border around the chart's plot area if none is
   * specified.
   * 
   * @see #setPlotAreaBorderStyle setPlotAreaBorderStyle
   * 
   */
  public final static String DEFAULT_PLOTAREA_BORDER_STYLE = "solid";
  /**
   * The default width of the border around the chart's plot area if none is
   * specified.
   * 
   * @see #setPlotAreaBorderWidth setPlotAreaBorderWidth
   * 
   */
  public final static int DEFAULT_PLOTAREA_BORDER_WIDTH = 0;
  /**
   * The default CSS background color used for symbols if none is specified.
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setBackgroundColor setBackgroundColor
   */
  public static final String DEFAULT_SYMBOL_BACKGROUND_COLOR = "transparent";
  /**
   * The default CSS border colors used for symbols if none are specified. These
   * defaults are, in order of the curve's integer index: red, green, blue,
   * fuchsia, aqua, teal, maroon, lime, navy, silver, olive, purple. This
   * sequence repeats if there are more than 12 curves.
   * <p>
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setBorderColor setBorderColor
   * 
   */

  public static final String[] DEFAULT_SYMBOL_BORDER_COLORS = { "red",
      "green", "blue", "fuchsia", "aqua", "teal", "maroon", "lime",
      "navy", "silver", "olive", "purple" };
  private static String[] defaultSymbolBorderColors = DEFAULT_SYMBOL_BORDER_COLORS;

  /**
   * The default CSS border style used for symbols if none is specified; this
   * default is "solid".
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setBorderStyle setBorderStyle
   * 
   */
  public static final String DEFAULT_SYMBOL_BORDER_STYLE = "solid";
  /**
   * The default CSS border width used for symbols if none is specified; this
   * default is 1 pixel.
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setBorderWidth setBorderWidth
   * 
   */
  public static final int DEFAULT_SYMBOL_BORDER_WIDTH = 1;
  /**
   * The default spacing between discrete, rectangular, elements used to
   * simulate continuous graphical elements. This default does not apply to bar
   * chart symbol types or the LINE symbol type, which have their own default
   * fill spacings.
   * <p>
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * @see #DEFAULT_BAR_FILL_SPACING DEFAULT_BAR_FILL_SPACING
   * @see #DEFAULT_LINE_FILL_SPACING DEFAULT_LINE_FILL_SPACING
   * 
   */
  public static final double DEFAULT_SYMBOL_FILL_SPACING = 4;
  /**
   * The default "thickness" of the rectangular elements used to simulate
   * continuous graphical objects, such as connecting lines in line charts. This
   * default applies to all symbol types <tt>except</tt> for those representing
   * pie slices, whose default is <tt>DEFAULT_PIE_SLICE_FILL_THICKNESS</tt>, and
   * the LINE symbol type, whose default is DEFAULT_LINE_FILL_THICKNESS.
   * 
   * <p>
   * Since this default thickness is 0 px, this implies that, except for pie
   * slices and lines, no such continuous fill elements are generated by
   * default. For example, if you want to have dotted connecting lines drawn
   * between individual data points represented using the <tt>BOX_CENTER</tt>
   * symbol type, you must explicitly specify a positive fill thickness (for
   * solid connecting lines, the LINE symbol is usually far more efficient than
   * using a fill thickness of 1px with the BOX_CENTER symbol).
   * 
   * @see #DEFAULT_PIE_SLICE_FILL_THICKNESS DEFAULT_PIE_SLICE_FILL_THICKNESS
   * @see #DEFAULT_LINE_FILL_THICKNESS DEFAULT_LINE_FILL_THICKNESS
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * 
   */
  public static final int DEFAULT_SYMBOL_FILL_THICKNESS = 0;

  /**
   * The default spacing between discrete, rectangular, elements used to
   * simulate continuous filling of polygonal regions formed by connecting
   * corresponding ends of successive bars in a bar chart.
   * <p>
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * @see #DEFAULT_SYMBOL_FILL_SPACING DEFAULT_SYMBOL_FILL_SPACING
   * 
   */
  public static final double DEFAULT_BAR_FILL_SPACING = 0;

  /**
   * The default thickness of connecting lines drawn on curves whose symbols
   * have the LINE symbol type.
   * 
   * @see #DEFAULT_SYMBOL_FILL_THICKNESS DEFAULT_SYMBOL_FILL_THICKNESS
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * 
   */
  public static final int DEFAULT_LINE_FILL_THICKNESS = 1;

  /**
   * The default spacing between discrete, rectangular, elements used to
   * simulate continuously connected lines between successive points on a curve
   * that uses the <tt>LINE</tt> symbol type.
   * <p>
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * @see #DEFAULT_SYMBOL_FILL_SPACING DEFAULT_SYMBOL_FILL_SPACING
   * 
   */
  public static final int DEFAULT_LINE_FILL_SPACING = 0;

  /**
   * The default "spacing" between corresponding edges of the rectangular
   * elements used to simulate continuous fill of pie slices.
   * <p>
   * 
   * @see #DEFAULT_SYMBOL_FILL_SPACING DEFAULT_SYMBOL_FILL_SPACING
   * @see #DEFAULT_PIE_SLICE_FILL_THICKNESS DEFAULT_PIE_SLICE_FILL_THICKNESS
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * 
   */
  public static final double DEFAULT_PIE_SLICE_FILL_SPACING = 4;
  /**
   * The default "thickness" of the rectangular elements used to simulate
   * continuous fill of pie slices. This thickness defines the height of
   * horizontal pie slice shading bars, and the width of vertical shading bars.
   * <p>
   * 
   * @see #DEFAULT_SYMBOL_FILL_THICKNESS DEFAULT_SYMBOL_FILL_THICKNESS
   * @see #DEFAULT_LINE_FILL_THICKNESS DEFAULT_LINE_FILL_THICKNESS
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setFillSpacing setFillSpacing
   * @see Symbol#setFillThickness setFillThickness
   * 
   */
  public static final int DEFAULT_PIE_SLICE_FILL_THICKNESS = 2;

  /**
   * The default hovertext template used by symbols when they have a symbol type
   * of of the form PIE_SLICE_*.
   * 
   * @see Symbol#setHovertextTemplate setHovertextTemplate
   * @see SymbolType#PIE_SLICE_OPTIMAL_SHADING PIE_SLICE_OPTIMAL_SHADING
   * @see #DEFAULT_HOVERTEXT_TEMPLATE DEFAULT_HOVERTEXT_TEMPLATE
   * 
   */
  public static final String DEFAULT_PIE_SLICE_HOVERTEXT_TEMPLATE =
          GChart.formatAsHovertext("${pieSliceSize}");

  /**
   * The default hover feedback location used by symbols when they have a symbol
   * type of of the form PIE_SLICE_*.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_PIE_SLICE_HOVER_LOCATION =
          AnnotationLocation.OUTSIDE_PIE_ARC;
  /**
   * The default height (including borders) used for symbols if none is
   * specified; this default is the same as for <tt>DEFAULT_SYMBOL_WIDTH</tt>
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setHeight setHeight
   * @see #DEFAULT_SYMBOL_WIDTH DEFAULT_SYMBOL_WIDTH
   * 
   */
  public static final int DEFAULT_SYMBOL_HEIGHT = 7;

  /**
   * The default symbol type for curve if none is specified; this default is
   * BOX_CENTER
   * 
   * @see SymbolType#BOX_CENTER BOX_CENTER
   * @see Symbol#setSymbolType setSymbolType
   * 
   */
  public static final SymbolType DEFAULT_SYMBOL_TYPE = SymbolType.BOX_CENTER;

  /**
   * The default width (including borders) used for symbols if none is
   * specified.
   * 
   * @see Curve#getSymbol getSymbol
   * @see Symbol#setWidth setWidth
   * @see #DEFAULT_SYMBOL_WIDTH DEFAULT_SYMBOL_WIDTH
   */
  public static final int DEFAULT_SYMBOL_WIDTH = DEFAULT_SYMBOL_HEIGHT;

  /**
   * The default number of tick marks on each Axis.
   * 
   * @see Axis#setTickCount setTickCount
   * 
   */
  public static final int DEFAULT_TICK_COUNT = 10;

  /**
   * The default color (a CSS color specification) of tick labels
   * 
   * @see Axis#setTickLabelFontColor setTickLabelFontColor
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   */
  public final static String DEFAULT_TICK_LABEL_FONT_COLOR = "black";

  /**
   * The default CSS font-style applied to tick labels
   * 
   * @see Axis#setTickLabelFontStyle setTickLabelFontStyle
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   */
  public final static String DEFAULT_TICK_LABEL_FONT_STYLE = "normal";

  /**
   * The default CSS font-weight applied to tick labels
   * 
   * @see Axis#setTickLabelFontWeight setTickLabelFontWeight
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   */
  public final static String DEFAULT_TICK_LABEL_FONT_WEIGHT = "normal";

  /**
   * The default fontsize (in pixels) of tick labels
   * 
   * @see Axis#setTickLabelFontSize setTickLabelFontSize
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   */
  public final static int DEFAULT_TICK_LABEL_FONTSIZE = 12;
  /**
   * The default GWT <tt>NumberFormat</tt> format string used to convert numbers
   * to the text strings displayed as tick labels on X, Y, and Y2 axes.
   * 
   * @see Axis#setTickLabelFormat setTickLabelFormat
   * @see #getXAxis getXAxis
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   * 
   */
  public final static String DEFAULT_TICK_LABEL_FORMAT = "#.##";

  /**
   * The default length of tick marks, in pixels.
   * 
   * @see Axis#setTickLength setTickLength
   */
  public static final int DEFAULT_TICK_LENGTH = 6;

  /**
   * The default tick location.
   * 
   * @see Axis#setTickLocation setTickLocation
   */
  public static final TickLocation DEFAULT_TICK_LOCATION = TickLocation.OUTSIDE;

  /**
   * The default thickness of tick marks, in pixels.
   * 
   * @see Axis#setTickThickness setTickThickness
   */
  public static final int DEFAULT_TICK_THICKNESS = 1; // pixel

  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses a VBAR_BASELINE_*
   * symbol type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_VBAR_BASELINE_HOVER_LOCATION =
          AnnotationLocation.FARTHEST_FROM_HORIZONTAL_BASELINE;
  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses a VBAR_SOUTH* symbol
   * type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_VBARBOTTOM_HOVER_LOCATION =
          AnnotationLocation.NORTH;

  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses a VBAR_NORTH* symbol
   * type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_VBARTOP_HOVER_LOCATION =
          AnnotationLocation.SOUTH;

  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses a HBAR_BASELINE_*
   * symbol type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_HBAR_BASELINE_HOVER_LOCATION =
          AnnotationLocation.FARTHEST_FROM_VERTICAL_BASELINE;

  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses an HBAR_*WEST symbol
   * type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_HBARLEFT_HOVER_LOCATION =
          AnnotationLocation.EAST;

  /**
   * The default location used to position the hover feedback when the user
   * hovers their mouse above a point on a curve that uses an HBAR_*EAST symbol
   * type.
   * 
   * @see Symbol#setHoverLocation setHoverLocation
   * @see #DEFAULT_HOVER_LOCATION DEFAULT_HOVER_LOCATION
   * 
   */
  public static final AnnotationLocation DEFAULT_HBARRIGHT_HOVER_LOCATION =
          AnnotationLocation.WEST;

  /**
   * Default upper bound on the number of pixels any canvas widget used by
   * GChart can have.
   * <p>
   * 
   * @see #setMaxCanvasPixels setMaxCanvasPixels
   * 
   */
  public static final double DEFAULT_MAX_CANVAS_PIXELS = 4096 * 4096;

  /**
   * The default upper bound on the width of widgets used in annotations and
   * tick labels that GChart will assume for centering and similar alignment
   * purposes.
   * 
   * @see Curve.Point#setAnnotationWidget setAnnotationWidget
   * @see Axis#addTick(double,Widget,int,int) addTick
   * 
   */
  public static final int DEFAULT_WIDGET_WIDTH_UPPERBOUND = 400;
  /**
   * The default upper bound on the height of widgets used in annotations and
   * tick labels that GChart will assume for centering and similar alignment
   * purposes.
   * 
   * @see Curve.Point#setAnnotationWidget setAnnotationWidget
   * @see Axis#addTick(double,Widget,int,int) addTick
   * 
   */
  public static final int DEFAULT_WIDGET_HEIGHT_UPPERBOUND = 400;

  /**
   * The default width of the area of the chart in which curves are displayed,
   * in pixels.
   */
  public final static int DEFAULT_X_CHARTSIZE = 300; // pixels
  /**
   * The default height of the area of the chart in which curves are displayed,
   * in pixels.
   */
  public final static int DEFAULT_Y_CHARTSIZE = 300; // pixels

  /**
   * In analogy to how it uses <tt>Double.NaN</tt> (Not a Number), GChart uses
   * <tt>GChart.NAI</tt> (Not an Integer) to represent integers whose values
   * have not been explicitly specified.
   * 
   */
  public static final int NAI = Integer.MIN_VALUE;

  /**
   * Due to a well-known bug (see, for example, <a href=
   * "http://www.hedgerwow.com/360/dhtml/css-ie-transparent-border.html"> this
   * explanation on Hedger Wang's blog</a>), though white may not be black in
   * IE6, transparent borders certainly are. Besides this outright bug,
   * different browsers define which element's background color "shines through"
   * a transparent border differently. For example, in FF2, the background of
   * the element containing the border shines through, which makes setting the
   * border color to "transparent" equivalent to setting the border color to
   * equal the background color. In IE7, the color of the chart's background
   * "shines through"--which is more likely what you intended when you set a
   * symbol's border to transparent.
   * <p>
   * 
   * To make it easy for you to eliminate such problems, and obtain a
   * consistently behaving transparent-border behavior cross-browser, this
   * special GChart-only "color" (recognized by all GChart border color related
   * methods <i>except</i> <tt>GChart.setBorderColor</tt>) causes GChart to
   * emulate a transparent border by eliminating the border entirely (setting
   * it's width to 0) and changing the size and position of the element so as to
   * make it look like the border is still "taking up space".
   * 
   * <p>
   * 
   * 
   * <blockquote><small> <i>Note:</i>The <tt>GChart.setBorderColor</tt> method,
   * which sets the color of the border around the entire chart, does <i>not</i>
   * support this keyword because GChart's transparent border emulation relies
   * on changing the size of, and shifting the position of, the transparently
   * bordered element. But, the position of the GChart as a whole is determined
   * not by GChart, but by the enclosing page. Well-known CSS tricks, such as
   * described in the "hedgerwow" link above, can be used if you need a truely
   * transparent border around the entire chart. Or, just fake it by setting the
   * border color to equal the background color of the containing page.
   * </small></blockquote>
   * 
   * <p>
   * 
   * This differs from setting the border color to "transparent" (which you can
   * still do should you need the "standard non-standard" transparent border
   * color behavior) in subtle ways that can matter in special cases. For
   * example, because the element is smaller than it is with "transparent", if
   * you draw your symbols outside the chart rectangle, GChart will not be able
   * to track the mouse moves inside the transparent region (yes, this is a fine
   * point, but there could be other differences I'm not aware of). In almost
   * every other case I can think of, though, setting the border color to this
   * special keyword instead of "transparent" will be the simplest way to
   * eliminate these inconsistent transparent border problems from your charts.
   * <p>
   * 
   * @see Symbol#setBorderColor setBorderColor
   * 
   */

  public static final String TRANSPARENT_BORDER_COLOR = null;

  /**
   * A special value used to tell GChart that a property should be defined via
   * CSS, not via an explicit Java API specification.
   * 
   * <p>
   * 
   * This value is mainly used by GChart's "CSS convenience methods" which make
   * it possible to use the Java API to specify certain properties of a GChart
   * that can also be specified via CSS. When the value of the Java property is
   * set to <tt>USE_CSS</tt> GChart ignores the API specification and allows the
   * standard "CSS cascade" to define the property.
   * 
   * The discussion below elaborates on why GChart supports CSS convenience
   * methods, and how the <tt>USE_CSS</tt> keyword fits into that support.
   * 
   * <p>
   * 
   * <blockquote><small> <b>CSS Convenience Methods and the
   * <tt>GChart.USE_CSS</tt> keyword</b>
   * <p>
   * 
   * Like all GWT Widgets, a GChart is both an object in a Java application, and
   * an HTML element in a web page.
   * 
   * <p>
   * This duality naturally divides the properties of a GChart into three
   * categories:
   * 
   * <ol>
   * 
   * <li>Those you can access only via the Java API.
   * <li>Those you can access only via CSS and the DOM.
   * <li>Those you can access both ways.
   * </ol>
   * <p>
   * 
   * I've used the following criteria to determine the access method appropriate
   * for a given GChart property:
   * <p>
   * 
   * <ol>
   * 
   * <li>Those properties that mainly define the chart itself--independent of
   * its relationship to any containing web page--are exclusively accessed via
   * the Java API.
   * 
   * <p>
   * 
   * For example, the x,y data values of a curve have everything to do with the
   * chart itself and nothing to do with the enclosing web page, so all the
   * defining x,y data of a curve can only be accessed via the Java API.
   * <p>
   * 
   * <li>Those properties that mainly define how the chart fits into the
   * enclosing web page and have nothing to do with the chart itself are
   * accessed exclusively via CSS stylesheets or the GWT DOM class.
   * 
   * <p>
   * For example, how big of a margin should be placed around a GChart is only
   * about how the GChart fits into the enclosing web page, so you must define a
   * GChart's margins using a CSS stylesheet (or via the GWT DOM class)--there
   * is no <tt>GChart.setMargin</tt> method.
   * 
   * <li>Finally, those properties that, in some situations are best viewed as
   * part of the chart itself, and in other situations as defining how the chart
   * fits into the enclosing web page can be accessed <i>either</i> via the Java
   * API, or via CSS/DOM.
   * <p>
   * 
   * For example, if you are focused on assuring that the chart has a the same
   * border as every other element on the page, the border around the chart as a
   * whole can be viewed as relating to how the chart fits into the enclosing
   * web page. On the other hand, if you are focused on assuring that, like the
   * frame around a picture, the border looks good around that particular chart,
   * it makes more sense to view it as a part of the chart itself.
   * 
   * </ol>
   * 
   * <p>
   * 
   * The Java API methods for GChart properties in this third category are known
   * as "CSS convenience methods" because, though you could do the same thing by
   * exploiting the "GChart as HTML element" perspective, these methods save you
   * the trouble of looking up CSS syntax, splitting up your chart's
   * specification between Java code and a CSS stylesheet, invoking a rather
   * hard-to-remember method call in the GWT DOM class, etc.
   * 
   * <p>
   * Specifications made via the GChart Java API always take precedence over
   * those made via stylesheets or the DOM class. To instruct GChart that you
   * want one of these properties to be defined via CSS or the DOM, set the
   * associated Java API property to the special value <tt>GChart.USE_CSS</tt>.
   * 
   * <p>
   * Fortunately, since <tt>USE_CSS</tt> is the default value for every one of
   * these CSS convenience properties, if you never use the Java API to set
   * them, you can use CSS to control them just as you would for a GWT widget
   * that did not support convenience properties.
   * 
   * 
   * Unfortunately, these CSS defaults rarely produce a great looking chart out
   * of the box; the example CSS snippet below defines all of these convenience
   * properties and attaches them to GChart's default CSS selector (aka
   * stylename) in a way that I think looks better. A comment to the right of
   * each line contains the corresponding CSS convenience-method call that has
   * the same effect.
   * 
   * <p>
   * 
   * <pre>
   * .gchart-GChart {
   *    background-color: #DDF;   /* setBackgroundColor("#DDF"); *&#47;
   *    border-width: 1px;         /* setBorderWidth("1px"); *&#47;
   *    border-color: black;       /* setBorderColor("black"); *&#47; 
   *    border-style: solid;       /* setBorderStyle("solid");  *&#47; 
   *  }
   * </pre>
   * 
   * 
   * Note that certain CSS convenience methods that could in principle have been
   * added, such as those for defining the background image of a chart, were
   * omitted because I thought they would almost never be used. Of course, you
   * can always access these CSS properties "the old fashioned way" (via a CSS
   * specification or methods of the GWT DOM class).
   * 
   * <p>
   * </small></blockquote>
   * 
   * @see #setBorderColor(String) setBorderColor
   * @see #setBorderStyle(String) setBorderStyle
   * @see #setBackgroundColor(String) setBackgroundColor
   * @see #setBorderWidth(String) setBorderWidth
   * 
   * 
   * 
   */
  /*
   * Setting a CSS property to "" generally clears the attribute specification,
   * restoring things to their initial defaults (not sure if this always works,
   * but it appears to so far).
   */
  public final static String USE_CSS = "";

  private final static int Y_AXES_MAX = 5;
  
  /**
   * Keyword used to indicate that a curve should be displayed on the left
   * y-axis.
   * 
   * @see #Y2_AXIS Y2_AXIS
   * @see GChart.Curve#setYAxis(GChart.YAxisId) setYAxis
   * 
   */
  public static final YAxisId Y_AXIS[] = new YAxisId[Y_AXES_MAX];

  /**
   * Keyword used to indicate that a curve should be displayed on the right (the
   * so-called y2) y-axis.
   * 
   * @see #Y_AXIS Y_AXIS
   * @see Curve#setYAxis setYAxis
   * 
   */
 // public static final YAxisId Y2_AXIS = new YAxisId();

  /**
   * The default URL GChart will use to access the blank image (specifically, a
   * 1 x 1 pixel transparent GIF) it requires to prevent "missing image" icons
   * from appearing in your charts.
   * 
   * @see #setBlankImageURL setBlankImageURL
   * 
   */
  public final static String DEFAULT_BLANK_IMAGE_URL = "gchart.gif";
  /**
   * The full path to the default GChart blank image (specifically, a 1 x 1
   * pixel transparent GIF) it requires to prevent "missing image" icons from
   * appearing in your charts.
   * <p>
   * 
   * Convenience constant equal to:
   * 
   * <pre>
   * GWT.getModuleBaseURL() + GChart.DEFAULT_BLANK_IMAGE_URL
   * </pre>
   * 
   * @see #setBlankImageURL setBlankImageURL
   * 
   */
  public final static String DEFAULT_BLANK_IMAGE_URL_FULLPATH =
          GWT.getModuleBaseURL() + GChart.DEFAULT_BLANK_IMAGE_URL;
  private static final int DEFAULT_GRID_HEIGHT = DEFAULT_TICK_THICKNESS;
  private static final int DEFAULT_GRID_WIDTH = DEFAULT_TICK_THICKNESS;
  private static final String GRID_BORDER_STYLE = "solid";
  private static final int GRID_BORDER_WIDTH = 1;

  /**
   * The default color used for all axes, gridlines, and ticks.
   * 
   * @see #setGridColor setGridColor
   * 
   */
  public static final String DEFAULT_GRID_COLOR = "black";

  /**
   * The default thickness (height) of the rectangular region at the bottom of
   * the chart allocated for footnotes, per <tt>&lt;br&gt;</tt> or
   * <tt>&lt;li&gt;</tt> delimited HTML line. This default is only used when the
   * footnote thickness is set to <tt>GChart.NAI</tt> (the default).
   * 
   * @see #setChartFootnotesThickness setChartFootnotesThickness
   * 
   */
  public static final int DEFAULT_FOOTNOTES_THICKNESS = 15;

  /**
   * 
   * The default thickness (height) of the rectangular region at the top of the
   * chart allocated for the chart's title, per <tt>&lt;br&gt;</tt> or
   * <tt>&lt;li&gt;</tt> delimited HTML line. This default is only used when the
   * title thickness is set to <tt>GChart.NAI</tt>.
   * 
   * 
   * @see #setChartTitleThickness setChartTitleThickness
   * 
   */
  public static final int DEFAULT_TITLE_THICKNESS = 15;

  // for purposes of estimating fixed space "band" around the plot
  // panel reserved for the tick labels:
  private static final double TICK_CHARHEIGHT_TO_FONTSIZE_LOWERBOUND = 1.0;
  // a bit larger than the 0.6 rule-of-thumb
  private static final double TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND = 0.7;
  // For estimating size of invisible "box" needed for alignment
  // purposes. Note: when these are bigger, annotations remain
  // properly aligned longer as user zooms up font sizes. But,
  // bigger bounding boxes can slow updates (not sure why,
  // maybe it's related to hit testing browser has to do)
  private static final double CHARHEIGHT_TO_FONTSIZE_UPPERBOUND = 2 * 1.5;
  private static final double CHARWIDTH_TO_FONTSIZE_UPPERBOUND = 2 * 0.7;

  private static final String TICK_BORDER_STYLE = GRID_BORDER_STYLE;
  private static final int TICK_BORDER_WIDTH = GRID_BORDER_WIDTH;

  private static void setBackgroundColor(UIObject uio, String cssColor) {
    DOM.setStyleAttribute(uio.getElement(), "backgroundColor", cssColor);
  }

  // private static void setBackground(
  // UIObject uio, String cssColor) {
  // DOM.setStyleAttribute(uio.getElement(),
  // "background", cssColor);
  // }

  private static void setBorderColor(UIObject uio, String cssColor) {
    DOM.setStyleAttribute(uio.getElement(), "borderColor", cssColor);
  }

  private static void setBorderStyle(UIObject uio, String cssBorderStyle) {
    DOM.setStyleAttribute(uio.getElement(), "borderStyle", cssBorderStyle);
  }

  private static void setBorderWidth(UIObject uio, String cssBorderWidth) {
    DOM.setStyleAttribute(uio.getElement(), "borderWidth", cssBorderWidth);
  }

  private static void setBorderWidth(UIObject uio, int borderWidth) {
    if (borderWidth != GChart.NAI)
      setBorderWidth(uio, borderWidth + "px");
    else
      setBorderWidth(uio, "");
  }

  private static void setFontFamily(UIObject uio, String cssFontFamily) {
    DOM.setStyleAttribute(uio.getElement(), "fontFamily", cssFontFamily);
  }

  private static void setFontSize(UIObject uio, int fontSize) {
    DOM.setIntStyleAttribute(uio.getElement(), "fontSize", fontSize);
  }

  private static void setFontStyle(UIObject uio, String fontStyle) {
    DOM.setStyleAttribute(uio.getElement(), "fontStyle", fontStyle);
  }

  private static void setFontWeight(UIObject uio, String fontWeight) {
    DOM.setStyleAttribute(uio.getElement(), "fontWeight", fontWeight);
  }

  private static void setColor(UIObject uio, String cssColor) {
    DOM.setStyleAttribute(uio.getElement(), "color", cssColor);
  }

  // valid layout strings are fixed, auto, and inherit
  // private static void setTableLayout(
  // UIObject uio, String layout) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "table-layout", layout);
  // }

  // private static void setLineHeight(
  // UIObject uio, String cssLineHeight) {
  // DOM.setStyleAttribute(uio.getElement(),
  // "lineHeight", cssLineHeight);
  // }

  // private static void setTextAlign(
  // UIObject uio, String cssTextAlign) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "textAlign", cssTextAlign);
  // }
  //  
  // private static void setMargin(
  // UIObject uio, String cssMargin) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "margin", cssMargin);
  // }
  private static void setPadding(UIObject uio, String cssPadding) {
    DOM.setStyleAttribute(uio.getElement(), "padding", cssPadding);
  }

  // valid choices are block, inline, list-item, or none
  // private static void setDisplay(
  // UIObject uio, String cssDisplay) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "display", cssDisplay);
  // }
  // choices are: visible, hidden, scroll or auto
  private static void setOverflow(UIObject uio, String cssOverflow) {
    DOM.setStyleAttribute(uio.getElement(), "overflow", cssOverflow);
  }

  // private static void setTextLeading(
  // UIObject uio, String cssTextLeading) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "textTrailing", cssTextLeading);
  // }
  // private static void setVerticalAlign(
  // UIObject uio, String cssVerticalAlign) {
  // DOM.setStyleAttribute(
  // uio.getElement(), "verticalAlign", cssVerticalAlign);
  // }

  // returns the sign of the given number
  static int sign(double x) {
    int result = (x < 0) ? -1 : 1;
    return result;
  }

  // Validates multipliers used to simplify computing the
  // upper left corner location of symbols and labels to
  // properly reflect their alignment relative to the
  // plotted point or labeled symbol.
  static void validateMultipliers(int widthMultiplier, int heightMultiplier) {
    if (!(widthMultiplier == 0 || Math.abs(widthMultiplier) == 1)
        && !(heightMultiplier == 0 || Math.abs(heightMultiplier) == 1))
      throw new IllegalArgumentException(
          "widthMultiplier, heightMultiplier args must both be "
          + "either 0, 1, or -1");
  }

  // is value within given limits, inclusive?
  static boolean withinRange(double x, double minLim, double maxLim) {
    // x!=x is a faster isNaN; NaN is considered in range
    boolean result = (x != x) ? true : (x >= minLim && x <= maxLim);
    return result;
  }

  private Widget chartFootnotes;
  private boolean chartFootnotesLeftJustified;

  // outer container needed so CSS-defined paddings don't interfere with
  // positioning
  private SimplePanel chartPanel = new SimplePanel();

  private String borderWidth = USE_CSS;
  private String borderStyle = USE_CSS;
  private String borderColor = USE_CSS;
  private String backgroundColor = USE_CSS;
  private static String blankImageURL = null;
  private double canvasExpansionFactorX = 0;
  private double canvasExpansionFactorY = 0;
  boolean chartDecorationsChanged = true;
  private Widget chartTitle;
  // collection of curves associated with this chart.
  private ArrayList<Curve> curves = new ArrayList<Curve>();
  private String fontFamily = DEFAULT_FONT_FAMILY;
  private int footnotesThickness = GChart.NAI;
  private String legendBackgroundColor = DEFAULT_LEGEND_BACKGROUND_COLOR;
  private String legendBorderColor = DEFAULT_LEGEND_BORDER_COLOR;
  private int legendBorderWidth = DEFAULT_LEGEND_BORDER_WIDTH;
  private String legendBorderStyle = DEFAULT_LEGEND_BORDER_STYLE;
  private int legendThickness = GChart.NAI;

  private boolean isLegendVisible = true;

  private String legendFontColor = DEFAULT_FONT_COLOR;
  private String legendFontFamily = null;
  private int legendFontSize = DEFAULT_LEGEND_FONTSIZE;
  private String legendFontStyle = DEFAULT_FONT_STYLE;
  private String legendFontWeight = DEFAULT_FONT_WEIGHT;
  private Widget legend = null; // developer-defined
  // legend
  private LegendLocation legendLocation = LegendLocation.OUTSIDE_RIGHT;
  private int legendXShift = 0;
  private int legendYShift = 0;
  private double maxCanvasPixels = DEFAULT_MAX_CANVAS_PIXELS;

  /*
   * Contains the plotting region, as well as axes, ticks, and tick-labels
   * associated with that region. Note that tickText must be centered on the
   * ticks--placing them on the same AbsolutePanel as the ticks/plots
   * facilitates this.
   */
  PlotPanel plotPanel = new PlotPanel();
  private String padding = USE_CSS;
  private boolean optimizeForMemory = false;
  private double renderPaddingFactor = 0.0;
  private boolean clipToPlotArea = false;
  private boolean clipToDecoratedChart = false;
  private int titleThickness = GChart.NAI;

  private Axis xAxis; // must be created in constructor
  private Axis yAxis[]; // because they use system curves
  //private Axis y2Axis;

  private int xChartSize; // pixel size of plotting region
  private int yChartSize;

  // # of system curves "underneath" (before in DOM-order) user's curves
  private static int N_PRE_SYSTEM_CURVES = 28;
  // # of system curves "on top of" (after in DOM-order) user's curves
  private static int N_POST_SYSTEM_CURVES = 2;
  private static int N_SYSTEM_CURVES = N_PRE_SYSTEM_CURVES
      + N_POST_SYSTEM_CURVES;
  
  private final static int Y_BASE_INDEX = 2;
  private final static int POST_Y_BASE_INDEX = Y_BASE_INDEX + Y_AXES_MAX * 4;
  
  private final static int YAXIS_ID[] = new int[Y_AXES_MAX];
  private final static int YTICKS_ID[] = new int[Y_AXES_MAX];
  private final static int YGRIDLINES_ID[] = new int[Y_AXES_MAX];
  private final static int YLABEL_ID[] = new int[Y_AXES_MAX];
  
  public int getYAxesIndexForCurveId(int curveIndex){
	  int result = 0;
	  if(curveIndex >= N_PRE_SYSTEM_CURVES && curveIndex < N_PRE_SYSTEM_CURVES+getNCurves()){
		  YAxisId yAxis2 = getCurve(curveIndex-N_PRE_SYSTEM_CURVES).getYAxis();
		  for(int i = 0; i < Y_AXES_MAX; i++){
			  if(yAxis2 == Y_AXIS[i])
				  return i;
		  }
	  }else if(curveIndex >= N_PRE_SYSTEM_CURVES+getNCurves()){
		  YAxisId yAxis2 = getSystemCurve(HOVER_CURSOR_ID).getYAxis();
		  for(int i = 0; i<Y_AXES_MAX; i++){
			  if(yAxis2 == Y_AXIS[i])
				  return i;
		  }
	  }else{	  
	  curveIndex = curveIndex - N_SYSTEM_CURVES;
	  for(int i = 0; i < Y_AXES_MAX; i++){
		  if(curveIndex == YAXIS_ID[i] || curveIndex == YTICKS_ID[i] || curveIndex == YGRIDLINES_ID[i]
		                      || curveIndex == YLABEL_ID[i]){
			  return i;
		  }
	  }
	  }
	  return result;
  }

  static {

	  int count = 0;
    for (int i = 0; i < Y_AXES_MAX; ++i) {
      YAXIS_ID[i] = Y_BASE_INDEX + count++ - N_SYSTEM_CURVES;
      YTICKS_ID[i] = Y_BASE_INDEX + count++ - N_SYSTEM_CURVES;
      YGRIDLINES_ID[i] = Y_BASE_INDEX + count++ - N_SYSTEM_CURVES;
      YLABEL_ID[i] = Y_BASE_INDEX + count++ - N_SYSTEM_CURVES;
    }
  }
  
  // index of curve that holds correspondingly-named chart part
  // (sys curve indexes are negative & not directly developer-accessible)
  private final static int PLOTAREA_ID = 0 - N_SYSTEM_CURVES;
  private final static int TITLE_ID = 1 - N_SYSTEM_CURVES;
  /*private final static int YAXIS_ID = 2 - N_SYSTEM_CURVES;
  private final static int YTICKS_ID = 3 - N_SYSTEM_CURVES;
  private final static int YGRIDLINES_ID = 4 - N_SYSTEM_CURVES;
  private final static int YLABEL_ID = 5 - N_SYSTEM_CURVES;
  private final static int Y2AXIS_ID = 6 - N_SYSTEM_CURVES;
  private final static int Y2TICKS_ID = 7 - N_SYSTEM_CURVES;
  private final static int Y2GRIDLINES_ID = 8 - N_SYSTEM_CURVES;
  private final static int Y2LABEL_ID = 9 - N_SYSTEM_CURVES;*/
  private final static int LEGEND_ID = POST_Y_BASE_INDEX  - N_SYSTEM_CURVES;
  private final static int XAXIS_ID = POST_Y_BASE_INDEX + 1 - N_SYSTEM_CURVES;
  private final static int XTICKS_ID = POST_Y_BASE_INDEX + 2 - N_SYSTEM_CURVES;
  private final static int XGRIDLINES_ID = POST_Y_BASE_INDEX + 3 - N_SYSTEM_CURVES;
  private final static int XLABEL_ID = POST_Y_BASE_INDEX + 4 - N_SYSTEM_CURVES;
  private final static int FOOTNOTES_ID = POST_Y_BASE_INDEX + 5 - N_SYSTEM_CURVES;
  private final static int HOVER_CURSOR_ID = POST_Y_BASE_INDEX + 6 - N_SYSTEM_CURVES;
  private final static int HOVER_ANNOTATION_ID = POST_Y_BASE_INDEX + 7 - N_SYSTEM_CURVES;

  // adds system curves GChart uses to render title, ticks, etc.
  private void addSystemCurves() {
    // Must be first: other methods assume sys curves exist
    for (int i = 0; i < N_SYSTEM_CURVES; i++) {
      Curve c = new Curve(i);
      curves.add(c);
      // Required rendering panels are added lazily, later on
    }

    // define static (or default) properties, points on, system curves
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.BOX_SOUTHEAST);
    c.getSymbol().setBackgroundColor(DEFAULT_PLOTAREA_BACKGROUND_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_PLOTAREA_BORDER_COLOR);
    c.getSymbol().setBorderStyle(DEFAULT_PLOTAREA_BORDER_STYLE);
    c.getSymbol().setBorderWidth(DEFAULT_PLOTAREA_BORDER_WIDTH);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(-Double.MAX_VALUE, Double.MAX_VALUE);

    c = getSystemCurve(TITLE_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.ANCHOR_NORTHWEST);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    c = getSystemCurve(YAXIS_ID[0]);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.XGRIDLINE);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
    c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
    c.getSymbol().setWidth(3);
    c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(-Double.MAX_VALUE, -Double.MAX_VALUE);

    c = getSystemCurve(YTICKS_ID[0]);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.BOX_WEST);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(TICK_BORDER_STYLE);
    c.getSymbol().setBorderWidth(TICK_BORDER_WIDTH);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    // points, annotation locations added when ticks are

    c = getSystemCurve(YGRIDLINES_ID[0]);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.YGRIDLINE);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
    c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
    c.getSymbol().setWidth(DEFAULT_GRID_WIDTH);
    c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);

    c = getSystemCurve(YLABEL_ID[0]);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.ANCHOR_WEST);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    for(int i = 1; i < Y_AXES_MAX; i++){
    	c = getSystemCurve(YAXIS_ID[i]);
        c.setClipToPlotArea(Boolean.FALSE);
        c.getSymbol().setSymbolType(SymbolType.XGRIDLINE);
        c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
        c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
        c.getSymbol().setWidth(3);
        c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
        c.getSymbol().setHoverAnnotationEnabled(false);
        c.getSymbol().setHoverSelectionEnabled(false);
        c.addPoint(Double.MAX_VALUE, -Double.MAX_VALUE);
        c.getPoint().setAnnotationLocation(AnnotationLocation.NORTHEAST);
        
        c = getSystemCurve(YTICKS_ID[i]);
        c.setClipToPlotArea(Boolean.FALSE);
        c.getSymbol().setSymbolType(SymbolType.BOX_EAST);
        c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderStyle(TICK_BORDER_STYLE);
        c.getSymbol().setBorderWidth(TICK_BORDER_WIDTH);
        c.getSymbol().setHoverAnnotationEnabled(false);
        c.getSymbol().setHoverSelectionEnabled(false);

        c = getSystemCurve(YGRIDLINES_ID[i]);
        c.setClipToPlotArea(Boolean.FALSE);
        //c.setYAxis(Y_AXIS[i]);
        c.getSymbol().setSymbolType(SymbolType.YGRIDLINE);
        c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
        c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
        c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
        c.getSymbol().setWidth(DEFAULT_GRID_WIDTH);
        c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
        c.getSymbol().setHoverAnnotationEnabled(false);
        c.getSymbol().setHoverSelectionEnabled(false);

        c = getSystemCurve(YLABEL_ID[i]);
        c.setClipToPlotArea(Boolean.FALSE);
        //c.setYAxis(Y_AXIS[i]);
        c.getSymbol().setSymbolType(SymbolType.ANCHOR_EAST);
        c.getSymbol().setHoverAnnotationEnabled(false);
        c.getSymbol().setHoverSelectionEnabled(false);
        c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);
        c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);
    }
    

    c = getSystemCurve(LEGEND_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.getSymbol().setSymbolType(SymbolType.ANCHOR_EAST);
    c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);

    c = getSystemCurve(XAXIS_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.YGRIDLINE);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
    c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
    c.getSymbol().setWidth(DEFAULT_GRID_WIDTH);
    c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(-Double.MAX_VALUE, -Double.MAX_VALUE);

    // tick thickness and length get set in the axis constructors
    c = getSystemCurve(XTICKS_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.BOX_SOUTH);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(TICK_BORDER_STYLE);
    c.getSymbol().setBorderWidth(TICK_BORDER_WIDTH);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);

    c = getSystemCurve(XGRIDLINES_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.XGRIDLINE);
    c.getSymbol().setBackgroundColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderColor(DEFAULT_GRID_COLOR);
    c.getSymbol().setBorderStyle(GRID_BORDER_STYLE);
    c.getSymbol().setBorderWidth(GRID_BORDER_WIDTH);
    c.getSymbol().setWidth(DEFAULT_GRID_WIDTH);
    c.getSymbol().setHeight(DEFAULT_GRID_HEIGHT);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);

    c = getSystemCurve(XLABEL_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.ANCHOR_SOUTH);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    c = getSystemCurve(FOOTNOTES_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.getSymbol().setSymbolType(SymbolType.ANCHOR_SOUTHWEST);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.MAX_VALUE, Double.MAX_VALUE);
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    c = getSystemCurve(HOVER_ANNOTATION_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.setVisible(false); // initially no hover annotation
    c.getSymbol().setSymbolType(SymbolType.NONE);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.NaN, Double.NaN); // off initially, set on-the-fly
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    c = getSystemCurve(HOVER_CURSOR_ID);
    c.setClipToPlotArea(Boolean.FALSE);
    c.setVisible(false); // initially no hover selection
    c.getSymbol().setSymbolType(SymbolType.NONE);
    c.getSymbol().setHoverAnnotationEnabled(false);
    c.getSymbol().setHoverSelectionEnabled(false);
    c.addPoint(Double.NaN, Double.NaN);
    c.getPoint().setAnnotationLocation(AnnotationLocation.CENTER);

    // external "curve count" should now be 0 (system curves don't count)
    if (getNCurves() != 0)
      throw new IllegalStateException(
          "getNCurves() != 0. Probably a GChart bug.");

  }

  /*
   * Updates the system curves that represent chart decorations (axis labels,
   * title, ticks, etc.).<p>
   * 
   * Note that all x, y shifts are relative to the "anchoring" symbol type
   * locations defined once and for all in the addSystemCurves method above.
   */
  private void updateDecorations(int xChartSizeDecorated) {

    // x-axis label
    Curve.Point p = getSystemCurve(XLABEL_ID).getPoint(0);
    p.setAnnotationWidget(getXAxis().getAxisLabel(), getXChartSize(),
        getXAxis().getAxisLabelThickness());
    p.setAnnotationYShift(-getXAxis().getTickLabelThickness(false)
        - getXAxis().getTickSpace() - getXAxis().getTickLabelPadding()
        - getXAxis().getAxisLabelThickness() / 2);

    
    // y-axis label
    
    //For YLABEL_ID[0] x-shift would be -ve
    p = getSystemCurve(YLABEL_ID[0]).getPoint(0);
    p.setAnnotationWidget(getYAxis(0).getAxisLabel(), getYAxis(0)
        .getAxisLabelThickness(), getYChartSize());
    p.setAnnotationXShift(-getYAxis(0).getTickLabelThickness(false)
        - getYAxis(0).getTickSpace() - getYAxis(0).getTickLabelPadding()
        - getYAxis(0).getAxisLabelThickness() / 2);
    
    // For YLABEL_ID[1] x-shift would be +ve
    int thickness = 0;
    for(int i = 1; i < Y_AXES_MAX; i++){
    	
    	p = getSystemCurve(YLABEL_ID[i]).getPoint(0);
        p.setAnnotationWidget(getYAxis(i).getAxisLabel(), getYAxis(i).getAxisLabelThickness(), getYChartSize());
			thickness += getYAxis(i).getTickLabelThickness(false)
					+ getYAxis(i).getTickSpace()
					+ getYAxis(i).getTickLabelPadding()
					+ getYAxis(i).getAxisLabelThickness() / 2;
        p.setAnnotationXShift(thickness);
        
        if(i < Y_AXES_MAX -1){
        	p = getSystemCurve(YAXIS_ID[i + 1]).getPoint(0);
            p.setAnnotationText("");
            thickness += 30;
            p.setAnnotationXShift(thickness);
        }
        
        
    }
   
    //y axis id 
    
    /*for(int i = 2; i < Y_AXES_MAX; i++){        
        p = getSystemCurve(YAXIS_ID[i]).getPoint(0);
        p.setAnnotationText("");
//        thickness = getSystemCurve(YLABEL_ID[i - 1]).getPoint(0).getAnnotationXShift() + 10;
        thickness = getYAxis(i).getTickLabelThickness(false)
        	+ getYAxis(i).getTickSpace() + getYAxis(i).getTickLabelPadding()
        	+ getYAxis(i).getAxisLabelThickness() / 3 - 5 + ((i -2) * 5);
        p.setAnnotationXShift(thickness * i);
    }*/
    
    // legend
    /*int width = getYAxis(1).getTickLabelThickness(false)
	+ getYAxis(1).getTickSpace()
	+ getYAxis(1).getTickLabelPadding()
	+ getYAxis(1).getAxisLabelThickness() / 2;
    */
    Curve c = getSystemCurve(LEGEND_ID);
    p = c.getPoint(0);
    if (null == legend
        && (!isLegendVisible() || 0 == getNVisibleCurvesOnLegend())) {
      p.setAnnotationWidget(null);
    } else {
      c.getSymbol().setSymbolType(legendLocation.getSymbolType());
      Widget theLegend = (null == legend) ? createLegend(plotPanel)
          : legend;
      p.setAnnotationWidget(theLegend, getLegendThickness(),
          getYChartSize());
      p.setAnnotationLocation(legendLocation.getAnnotationLocation());
      /*p.setAnnotationXShift(legendLocation.getInitialXShift(this)
          + legendXShift);*/
      //thickness += width*3;
      p.setAnnotationXShift(getLegendXShift());
      
      
      p.setAnnotationYShift(legendLocation.getInitialYShift(this)
                            + legendYShift);
      // GChart generated legends set font-family at a lower level. But,
      // if user supplied their own legend, this line causes any legend
      // font-family to be inherited (to the extent CSS cascade allows that)
      p.setAnnotationFontFamily(getLegendFontFamily());
    }
    // title
    int shiftToLeftEdge = -getYAxis(0).getAxisLabelThickness()
        - getYAxis(0).getTickLabelThickness(false)
        - getYAxis(0).getTickSpace() - getYAxis(0).getTickLabelPadding();
    int shiftToHorizontalMidpoint = shiftToLeftEdge + xChartSizeDecorated
        / 2;
    p = getSystemCurve(TITLE_ID).getPoint(0);
    p.setAnnotationWidget(getChartTitle(), xChartSizeDecorated,
        getChartTitleThickness());
    p.setAnnotationYShift(getChartTitleThickness() / 2);
    p.setAnnotationXShift(shiftToHorizontalMidpoint);

    // footnotes
    p = getSystemCurve(FOOTNOTES_ID).getPoint(0);
    p.setAnnotationWidget(getChartFootnotes(), xChartSizeDecorated,
        getChartFootnotesThickness());
    p.setAnnotationYShift(-getXAxis().getTickLabelThickness(false)
        - getXAxis().getTickSpace() - getXAxis().getTickLabelPadding()
        - getXAxis().getAxisLabelThickness()
        - getChartFootnotesThickness() / 2);
    if (getChartFootnotesLeftJustified()) {
      p.setAnnotationXShift(shiftToLeftEdge);
      p.setAnnotationLocation(AnnotationLocation.EAST);
    } else { // footnotes centered
      p.setAnnotationXShift(shiftToHorizontalMidpoint);
      p.setAnnotationLocation(AnnotationLocation.CENTER);
    }

    // add points to ticks and gridlines curves in accord with chart specs

    // x & y axis can be present even if no curves mapped to them
    getSystemCurve(XAXIS_ID).setVisible(getXAxis().getAxisVisible());
    getXAxis().populateGridlines();
    getSystemCurve(YAXIS_ID[0]).setVisible(getYAxis(0).getAxisVisible());
    getYAxis(0).populateGridlines();

    // y2 axis is present only if at least 1 curve is on it.
    for(int i = 1; i < Y_AXES_MAX; i++){
    	if (hasYKAxis(i)) {
    	      getYAxis(i).populateGridlines();
    	      getSystemCurve(YAXIS_ID[i]).setVisible(getYAxis(i).getAxisVisible());
    	      getSystemCurve(YTICKS_ID[i]).setVisible(true);
    	      getSystemCurve(YGRIDLINES_ID[i]).setVisible(true);
    	      getSystemCurve(YLABEL_ID[i]).setVisible(true);
    	    } else {
    	      getSystemCurve(YAXIS_ID[i]).setVisible(false);
    	      getSystemCurve(YTICKS_ID[i]).setVisible(false);
    	      getSystemCurve(YGRIDLINES_ID[i]).setVisible(false);
    	      getSystemCurve(YLABEL_ID[i]).setVisible(false);
    	    }
    }
  }

  /**
   * Instantiates a GChart with a curve display region of the specified size.
   * 
   * 
   * @param xChartSize
   *          the width of the curve display region, in pixels.
   * @param yChartSize
   *          the height of the curve display region, in pixels.
   * 
   * @see #setXChartSize setXChartSize
   * @see #setYChartSize setYChartSize
   * @see #setChartSize setChartSize
   */
  public GChart(int xChartSize, int yChartSize) {
    super();
    addSystemCurves(); // must come first: later lines use system curves
    for(int i = 0; i < Y_AXES_MAX; i++){
    	Y_AXIS[i] = new YAxisId();
    }
    xAxis = new XAxis();
    yAxis = new YAxis[Y_AXES_MAX];
    for(int i = 0; i < Y_AXES_MAX; i++){
    	yAxis[i] = new YAxis(i);
    }
//    y2Axis = new Y2Axis();
    setXChartSize(xChartSize);
    setYChartSize(yChartSize);
    // Note: plotPanel (where real chart resides) won't get
    // added to chartPanel (top-level do-nothing container for
    // padding and such) until AFTER first update; FF2 has some
    // serious performance problems otherwise for common usage
    // scenarios with large widget-count pages.
    initWidget(chartPanel);
    /*
     * See the block comment at top of "class GChart" for a detailed
     * discussion/rational for GChart's (very minimal support) of stylenames.
     * Would like deeper support if I can ever figure out how to do it without
     * hamstringing future versions by locking them into a particular
     * implementation I might need to change later on. In particular, I don't
     * know how to provide such "deep" stylenames that also work consistently
     * with canvas-rendered curves.
     */
    setStyleName("gchart-GChart");
  }

  /**
   * Convenience no-arg constructor equivalent to
   * <tt>GChart(DEFAULT_X_CHARTSIZE,DEFAULT_Y_CHARTSIZE)</tt>.
   * 
   * @see #GChart(int,int) GChart(int,int)
   * 
   */
  public GChart() {
    this(DEFAULT_X_CHARTSIZE, DEFAULT_Y_CHARTSIZE);
  }

  public HandlerRegistration addClickHandler(ClickHandler handler) {
    HandlerRegistration result = addDomHandler(handler, ClickEvent.getType());
    return result;
  }

  public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
    HandlerRegistration result = addDomHandler(handler, DoubleClickEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseDownEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse down handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseDownEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseMoveEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse move handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseMoveEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseOutEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse out handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseOutEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseOverEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse over handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseOverEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseUpEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse up handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseUpEvent.getType());
    return result;
  }

  /**
   * Adds a <tt>MouseWheelEvent</tt> handler.
   * <p>
   * 
   * See <tt>addClickHandler</tt> for GChart-specific information you will need
   * to properly handle the event.
   * <p>
   * 
   * @param handler
   *          the mouse wheel handler
   * @return HandlerRegistration used to remove this handler
   * 
   * @see #addClickHandler addClickHandler
   * 
   */
  public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
    HandlerRegistration result = addDomHandler(handler, MouseWheelEvent.getType());
    return result;
  }

  /**
   * Adds a new curve to the chart, at the end of the current list of curves.
   * <p>
   * 
   * @see #getCurve getCurve
   * @see #addCurve(int) addCurve(int)
   * @see #removeCurve removeCurve
   * @see #clearCurves clearCurves
   * @see #getNCurves getNCurves
   */

  public void addCurve() {
    addCurve(getNCurves());
  }

  /*
   * Given external, coded, index returns a curve's ArrayList index
   * 
   * Basic order within the curves array is as follows:
   * 
   * o quite a few decorative curves that hold axis ticks, title, etc.
   * o "getNCurves()" user-created/accessible curves
   * o 1 "Pop-up" hover annotation holding curve
   * o 1 Selection cursor holding curve
   * 
   * It's very important that the last two system curves come last, to
   * ensure these elements are always on top of all other chart
   * elements, as required. <p>
   * 
   * The "external" system curve indexes are in a continuous range of negative
   * integers, which are mapped into the ArrayList positions given above via this
   * method.
   */
  int internalCurveIndex(int externalIndex) {
    int result;
    if (GChart.NAI == externalIndex)
      // -1 is the "no such curve" index used by an ArrayList
      result = -1;
    else if (externalIndex < -N_POST_SYSTEM_CURVES)
      // decoration related sys curves (before user's)
      result = externalIndex + N_SYSTEM_CURVES;
    else if (externalIndex < 0)
      // hover feedback related, after user curves (at the end)
      result = curves.size() + externalIndex;
    else
      // + indexes mapped to ordinary user-created curves
      result = externalIndex + N_PRE_SYSTEM_CURVES;
    return result;
  }

  /*
   * Given a curves ArrayList index returns external, coded, index
   * 
   * Companion/inverse of preceeding method.
   */
  int externalCurveIndex(int internalIndex) {
    int result;
    if (internalIndex < 0)
      result = GChart.NAI;
    else if (internalIndex < N_PRE_SYSTEM_CURVES)
      // one of the sys curves that comes before user's curves
      result = internalIndex - N_SYSTEM_CURVES;
    else if (internalIndex >= curves.size() - N_POST_SYSTEM_CURVES)
      // sys curves, like hover feedback, that come after user's
      result = internalIndex - curves.size();
    else
      // ordinary user created curve
      result = internalIndex - N_PRE_SYSTEM_CURVES;
    return result;
  }

  // does the external curve index represent a GChart-sys-defined curve?
  private boolean isSystemCurveIndex(int externalIndex) {
    boolean result = externalIndex < 0;
    return result;
  }

  /**
   * Adds a new curve to the chart, at the specified position in the curve
   * sequence. Existing curves at postions at or greater than the specified
   * position have their positional indexes increased by 1.
   * <p>
   * 
   * @see #getCurve getCurve
   * @see #addCurve() addCurve()
   * @see #removeCurve removeCurve
   * @see #clearCurves clearCurves
   * @see #getNCurves getNCurves
   */

  public void addCurve(int iCurve) {
    if (iCurve > getNCurves())
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not exceed getNCurves() (" + getNCurves()
          + ")");
    else if (iCurve < 0)
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not be negative.");
    int internalIndex = internalCurveIndex(iCurve);
    Curve c = new Curve(internalIndex);
    curves.add(internalIndex, c);
    // curves are initially added to the x, y axes.
    getXAxis().incrementCurves();
    getYAxis(0).incrementCurves();
    // adjust ArrayList indexes to account for newly added element
    for (int i = internalIndex + 1; i < curves.size(); i++)
      curves.get(i).incrementIndex();
    if (0 != plotPanel.getRenderingPanelCount()) { // other panels are already there
      int rpIndex = getRenderingPanelIndex(internalIndex);
      plotPanel.addGraphicsRenderingPanel(rpIndex);
      plotPanel.addAnnotationRenderingPanel(rpIndex);
    }
    // otherwise, before 1st update: lazy-add panels when they're 1st used
    c.invalidate();
    if (getNCurves() > 0)
      setDefaultBorderColor(c, getNCurves() - 1);

  }

  /**
   * Removes every curve this chart contains.
   * 
   * @see #getCurve getCurve
   * @see #addCurve() addCurve()
   * @see #addCurve(int) addCurve(int)
   * @see #removeCurve removeCurve
   * @see #getNCurves getNCurves
   */
  public void clearCurves() {
    for (int iCurve = getNCurves() - 1; iCurve >= 0; iCurve--)
      removeCurve(iCurve);
  }

  /**
   * Convenience method that, given a plain text label, returns an HTML snippet
   * appropriate for use as an argument to the <tt>setHovertextTemplate</tt> or
   * <tt>setAnnotationText</tt> methods, that will display the plain text label
   * with formatting appropriate for use with hovertext.
   * <p>
   * 
   * 
   * When the string returned from this method is used as an argument to
   * <tt>setHovertextTemplate</tt>, the hovertext that is generated is similar
   * in format (light yellow background, black 1px border) to what is produced
   * via the <tt>setTitle</tt> method (standard browser element hovertext).
   * <p>
   * 
   * In detail, this method creates the returned string via the line:
   * <p>
   * 
   * <pre>
   * result = &quot;&lt;html&gt;&lt;div style='background-color:#FFFFF0; border-color:black; border-style:solid; border-width:1px; padding:2px'&gt;&quot;
   *     + plainTextLabel + &quot;&lt;/div&gt;&quot;;
   * </pre>
   * 
   * <p>
   * This method is provided mainly to simplify transitioning from the old hover
   * feedback system to the new one introduced in v2.4.
   * <p>
   * 
   * It is expected that many pre v2.4 GChart applications will find that simply
   * wrapping a <tt>formatAsHovertext</tt> call around existing hovertext
   * templates will provide acceptable (though somewhat different) hover
   * feedback for existing charts:
   * 
   * <pre>
   * getCurve().getSymbol().setHovertextTemplate(
   *     GChart.formatAsHovertext(&quot;(x,y) = (${x}, ${y})&quot;));
   * </pre>
   * <p>
   * 
   * Note that GChart v2.4 automatically includes such a wrapping
   * <tt>formatAsHovertext</tt> call on all default hovertext templates.
   * 
   * <p>
   * 
   * See the {@link Symbol#setHoverAnnotationSymbolType
   * setHoverAnnotationSymbolType} method for a code snippet that emulates not
   * just the formatting, but also the at-the-mouse positioning behaviour, of
   * setTitle-based hovertext.
   * 
   * <p>
   * 
   * 
   * @see Symbol#setHovertextTemplate setHovertextTemplate
   * @see Curve.Point#setAnnotationText setAnnotationText
   * @see Symbol#setHoverAnnotationSymbolType setHoverAnnotationSymbolType
   * 
   * @param plainTextLabel
   *          the plain text label that is to be HTML-wrapped to make it look
   *          like <tt>setTitle</tt>-based hovertext.
   * 
   * 
   */

  public static String formatAsHovertext(String plainTextLabel) {
    String result = "<html><div style='background-color:#FFFFF0; border-color:black; border-style:solid; border-width:1px 1px 1px 1px; padding:2px; text-align:left'>"
        + plainTextLabel + "</div>";
    return result;
  }

  /**
   * Returns the background color of the chart as a whole.
   * 
   * @return the chart's background color, in a standard CSS color string
   *         format.
   * 
   * @see #setBackgroundColor(String) setBackgroundColor
   * 
   */
  public String getBackgroundColor() {
    return (backgroundColor);
  }

  /**
   * Returns the color of the border around the chart as a whole.
   * 
   * @return the color of the chart's border, in a standard CSS color string
   *         format.
   * 
   * @see #setBorderColor(String) setBorderColor
   * 
   */
  public String getBorderColor() {
    return borderColor;
  }

  /**
   * Returns the width of the border around the chart as a whole
   * 
   * @return width of the border around the chart as a whole, as a CSS border
   *         width specification string (e.g. "1px").
   * 
   * @see #setBorderWidth(String) setBorderWidth
   * 
   */
  public String getBorderWidth() {
    return borderWidth;
  }

  /**
   * Returns the style of the border around the chart as a whole
   * 
   * @return cssStyle for the border around the chart as a whole
   * 
   * @see #setBorderStyle(String) setBorderStyle
   * 
   */
  public String getBorderStyle() {
    return borderStyle;
  }

  /**
   * Returns the previously specified chart footnotes widget.
   * 
   * @return widget representing chart's footnotes or <tt>null</tt> if none.
   * 
   * @see #setChartFootnotes(Widget) setChartFootnotes(Widget)
   * @see #setChartFootnotes(String) setChartFootnotes(String)
   * @see #getChartTitle getChartTitle
   */
  public Widget getChartFootnotes() {
    return chartFootnotes;
  }

  /**
   * Returns flag indicating if this chart's footnotes are left-justified or
   * centered.
   * 
   * @return true if footnotes are flush against the left edge of the chart,
   *         false if they are horizontally centered across the bottom edge of
   *         the chart.
   * 
   * @see #setChartFootnotesLeftJustified setChartFootnotesLeftJustified
   * @see #setChartFootnotes(String) setChartFootnotes(String)
   * @see #setChartTitle setChartTitle
   */
  public boolean getChartFootnotesLeftJustified() {
    return chartFootnotesLeftJustified;
  }

  /**
   * Returns the thickness (height) of the rectangular region at the bottom of
   * the chart allocated for footnotes.
   * <p>
   * 
   * The width of this region always equals the width of the entire GChart
   * (including legend and axis labels).
   * <p>
   * 
   * Your footnotes widget is always vertically centered in this region.
   * <p>
   * 
   * 
   * Your footnotes widget will either be horizontally centered in this region,
   * or left justified in it, depending on the property defined by the
   * <tt>setChartFootnotesLeftJustified</tt> method.
   * 
   * <p>
   * 
   * 
   * This method always returns 0 if the footnotes widget is <tt>null</tt> (the
   * default); the rectangular footnotes region is entirely eliminated in that
   * case.
   * <p>
   * 
   * @return the thickness (height) of the rectangular region at the bottom of
   *         the chart allocated for footnotes, in pixels.
   * 
   * @see #setChartFootnotesThickness(int) setChartFootnotesThickness
   * @see #setChartFootnotesLeftJustified setChartFootnotesLeftJustified
   */
  public int getChartFootnotesThickness() {
    int result = 0;
    final int EXTRA_HEIGHT = 3; // 1.5 lines padding above/below
    final int DEF_HEIGHT = 1;
    if (null == getChartFootnotes())
      result = 0;
    else if (GChart.NAI != footnotesThickness)
      result = footnotesThickness;
    else if (getChartFootnotes() instanceof HasHTML)
      result = DEFAULT_FOOTNOTES_THICKNESS
          * (EXTRA_HEIGHT + htmlHeight(((HasHTML) (getChartFootnotes())).getHTML()));
    else
      result = DEFAULT_FOOTNOTES_THICKNESS * (DEF_HEIGHT + EXTRA_HEIGHT);
    return result;
  }

  /**
   * Returns the previously specified widget representing the chart's title.
   * 
   * @return widget representing chart's title or <tt>null</tt> if none
   * 
   * @see #setChartTitle(Widget) setChartTitle(Widget)
   * @see #setChartTitle(String) setChartTitle(String)
   * 
   */
  public Widget getChartTitle() {
    return chartTitle;
  }

  /**
   * Returns the thickness (height) of the rectangular region at the top of the
   * chart allocated for the title.
   * <p>
   * 
   * This method always returns 0 if the title widget is <tt>null</tt> (the
   * default); the rectangular title region is entirely eliminated in that case.
   * <p>
   * 
   * Your title widget is always centered vertically and horizontally within
   * this rectangular region.
   * 
   * 
   * @return the thickness (height) of the rectangle that contains the chart's
   *         title, in pixels.
   * 
   * @see #setChartTitleThickness setChartTitleThickness
   * 
   */
  public int getChartTitleThickness() {
    int result = 0;
    final int EXTRA_HEIGHT = 3; // 1.5 lines above & below title
    final int DEF_HEIGHT = 1;
    if (null == getChartTitle())
      result = 0;
    else if (GChart.NAI != titleThickness)
      result = titleThickness;
    else if (getChartTitle() instanceof HasHTML)
      result = DEFAULT_TITLE_THICKNESS
          * (EXTRA_HEIGHT + htmlHeight(((HasHTML) (getChartTitle())).getHTML()));
    else
      result = DEFAULT_TITLE_THICKNESS * (EXTRA_HEIGHT + DEF_HEIGHT);
    return result;
  }

  /**
   * Determines if this chart will clip any chart elements that extend beyond
   * the bounds of the decorated chart. The decorated chart includes title,
   * footnotes, etc. as well as the plot area proper.
   * 
   * @return true if off-the-decorated-chart elements are clipped, false
   *         otherwise.
   * 
   * @see #setClipToDecoratedChart setClipToDecoratedChart
   * 
   */
  public boolean getClipToDecoratedChart() {
    return clipToDecoratedChart;
  }

  /**
   * Returns true if graphical aspects of the chart that fall outside of the
   * plot area are being clipped off, false otherwise.
   * 
   * @return <tt>true</tt> if clipping to plot area, else <tt>false</tt>.
   * 
   * @see #setClipToPlotArea setClipToPlotArea
   */

  public boolean getClipToPlotArea() {
    return clipToPlotArea;
  }

  // returns point closest to given plot-panel pixel coordinates
  Curve.Point getClosestBrushTouchingPointNoCheck(int x, int y) {

    Curve.Point result = null;
    // NAI means mouse is at some unknown, off-the-chart, position
    if (x == GChart.NAI || y == GChart.NAI)
      return result;
    double dBest = Double.MAX_VALUE; // dist. to closest symbol

    // fact that charts tend to have a small number of curves
    // allows us to use simple sequential search across curves
    int nCurves = getNCurves();
    for (int iCurve = 0; iCurve < nCurves; iCurve++) {
      Curve c = getSystemCurve(iCurve);
      if (!c.isVisible())
        continue;
      Symbol sym = c.getSymbol();
      if (!sym.getHoverAnnotationEnabled()
          && !sym.getHoverSelectionEnabled())
        continue;
      SymbolType symType = sym.getSymbolType();
      //boolean onY2 = c.onY2();
      int iClosest = c.getClosestTouchingPoint(x, y);
      if (GChart.NAI == iClosest)
        continue; // no hits on this curve

      double xPoint = symType.getCenterX(plotPanel, sym, iClosest);
      double yPoint = symType.getCenterY(plotPanel, sym, iClosest, c.getIndexForAxisID(c.getYAxis()));
      double dx = sym.xScaleFactor * (x - xPoint);
      double dy = sym.yScaleFactor * (y - yPoint);
      // distance, squared, of mouse from symbol's "center"
      double d = dx * dx + dy * dy;
      if (d <= dBest) { // for ties, use later, "on top", point
        dBest = d;
        result = c.getPoint(iClosest);
      }
    }
    return result;
  }

  /**
   * 
   * Returns the point that would be touched if the mouse were moved to the
   * given x,y plot-area pixel coordinates, or <tt>null</tt> if the moving the
   * mouse to these coordinates would not have touched any points.
   * <p>
   * 
   * This method only works if the chart rendering is up-to-date (if
   * <tt>isUpdateNeeded</tt> returns <tt>false</tt>). Otherwise, <tt>null</tt>
   * is returned.
   * <p>
   * 
   * <small> GChart's hit testing method works best if a chart's points are
   * approximately evenly distributed across the plot area's x or y axis, across
   * a small number of curves. In particular, charts that have many points
   * bunched up into a small part of the plot area, or that have many points
   * completely outside of the plot area, or that place each point into a
   * separate curve, could experience significantly worse that usual hit testing
   * performance. Though such cases are expected to be rare, in the worst case,
   * GChart could be reduced to a simple linear search across all the chart's
   * points during hit testing. </small>
   * 
   * @param xPlotArea
   *          x-coordinate of trial mouse position, in GChart's plot area pixel
   *          coordinates.
   * @param yPlotArea
   *          y-coordinate of trial mouse position, in GChart's plot area pixel
   *          coordinates.
   * 
   * @return reference to the point that would have been "touched" by the mouse,
   *         or <tt>null</tt> if positioning the mouse to these coordinates
   *         would not have touched any point.
   * 
   * @see Axis#getMouseCoordinate getMouseCoordinate
   * @see Axis#modelToPlotAreaPixel modelToPlotAreaPixel
   * @see #isUpdateNeeded isUpdateNeeded
   * @see #touch touch
   * 
   */

  public Curve.Point getClosestBrushTouchingPoint(int xPlotArea, int yPlotArea) {
    Curve.Point result = null;
    if (!isUpdateNeeded()) {
      result = getClosestBrushTouchingPointNoCheck(xPlotArea, yPlotArea);
    }
    return result;
  }

  /**
   * Convenience method equivalent to <tt>getCurve(getNCurves()-1)</tt>.
   * <p>
   * This method, when used in conjunction with no-arg <tt>addCurve</tt>,
   * method, makes code blocks that create and define the properties of a
   * chart's curves more readable/editable. For example:
   * 
   * <pre>
   * addCurve(); // add 1st curve
   * getCurve().setYAxis(Y2_AXIS); // first setting for 1st curve
   * //... other settings for first curve
   * addCurve(); // add 2nd curve
   * getCurve().setYAxis(Y_AXIS); // first setting for 2nd curve
   * // ... other settings for 2nd curve
   * </pre>
   *<p>
   * Note that using the no-arg methods in this way allows you to copy entire
   * groups of curve properties, unchanged, between such curve related blocks.
   * 
   * @return the curve with the highest integer index. In other words, the curve
   *         with an index of <tt>getNCurves()-1</tt>.
   * 
   * @see #getCurve(int) getCurve(int)
   * @see #getNCurves getNCurves
   * @see #addCurve() addCurve()
   */
  public Curve getCurve() {
    int N = getNCurves();
    if (N < 1)
      throw new IllegalStateException(
          "You must add at least 1 curve before invoking getCurve()");
    Curve result = getSystemCurve(N - 1);
    return result;
  }

  /**
   * Returns a reference to the curve at the specified positional index. Use the
   * reference returned by this method to modify properties of a curve (the
   * symbol, data points, etc.)
   * 
   * <p>
   * 
   * @param iCurve
   *          index of the curve to be retrieved.
   * @return reference to the Curve at the specified position.
   * 
   * @see #getCurve() getCurve()
   * @see #addCurve() addCurve()
   * @see #addCurve(int) addCurve(int)
   * @see #removeCurve removeCurve
   * @see #clearCurves clearCurves
   * @see #getNCurves getNCurves
   */
  public Curve getCurve(int iCurve) {

    if (iCurve >= getNCurves())
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not exceed getNCurves()-1 ("
          + (getNCurves() - 1) + ")");
    else if (iCurve < 0)
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not be negative.");

    Curve result = getSystemCurve(iCurve);
    return result;
  }

  // Version of getCurve that allows sys curve (negative id) access
  Curve getSystemCurve(int iCurve) {
    int internalIndex = internalCurveIndex(iCurve);
    Curve result = curves.get(internalIndex);
    return result;
  }

  /**
   * Returns the positional index (within this chart's list of curves) of the
   * specified curve.
   * <p>
   * 
   * Returns <i>GChart.NAI</i> if the specified curve is not found on this
   * GChart's curve list.
   * 
   * <p>
   * 
   * @param curve
   *          whose list position is to be retrieved
   * @return position of curve in GChart's curve list, or <i>GChart.NAI</i> if
   *         not on this chart's curve list.
   * 
   * @see #getCurve() getCurve()
   * @see #getCurve(int) getCurve(int)
   * @see #addCurve() addCurve()
   * @see #addCurve(int) addCurve(int)
   * @see #removeCurve removeCurve
   * @see #clearCurves clearCurves
   * @see #getNCurves getNCurves
   */
  public int getCurveIndex(Curve curve) {
    int internalIndex = curve.getIndexOf();
    int result = externalCurveIndex(internalIndex);
    return result;
  }

  int getInternalCurveIndex(Curve curve) {
    int result = curve.getIndexOf();
    return result;
  }

  // maps all background curve indexes into first rendering panel
  int getRenderingPanelIndex(int internalCurveIndex) {
    int result = 0;
    if (N_PRE_SYSTEM_CURVES <= internalCurveIndex)
      result = internalCurveIndex - N_PRE_SYSTEM_CURVES + 1;
    return result;
  }

  Curve getCurveFromRenderingPanelIndex(int rpi) {
    int internalCurveIndex = rpi + N_PRE_SYSTEM_CURVES - 1;
    Curve result = curves.get(internalCurveIndex);
    return result;
  }

  /**
   * 
   * Returns the font-family that is used, by default, for all
   * textual annotations (tick labels, point lables, title, footnotes,
   * legend labels, and axis labels) on that chart. 
   * 
   * @see #setFontFamily(String) setFontFamily
   * 
   * 
   */
  public String getFontFamily() {
    return fontFamily;
  }

  /**
   * Returns CSS color specification for all gridlines, axes, and tickmarks.
   * 
   * @see #setGridColor setGridColor
   * 
   * @return the color, in CSS standard color format, used for all gridlines,
   *         axes, and tick marks.
   * 
   */

  public String getGridColor() {
    Curve cGridlines = getSystemCurve(XGRIDLINES_ID);
    String result = cGridlines.getSymbol().getBorderColor();
    return result;
  }

  /**
   * Returns the background color of the chart's legend.
   * 
   * @return the legend's background color, in a standard CSS color string
   *         format.
   * 
   * @see #setLegendBackgroundColor setLegendBackgroundColor
   * 
   */
  public String getLegendBackgroundColor() {
    return legendBackgroundColor;
  }

  /**
   * Returns the border color of the chart's legend.
   * 
   * @return the color of the legend's border, in a standard CSS color string
   *         format, or else the special GChart keyword
   *         <tt>TRANSPARENT_BORDER_COLOR</tt>.
   * 
   * @see #setLegendBorderColor setLegendBordergroundColor
   * @see #TRANSPARENT_BORDER_COLOR TRANSPARENT_BORDER_COLOR
   * 
   */
  public String getLegendBorderColor() {
    return legendBorderColor;
  }

  /**
   * Returns the width of the chart's legend's border
   * 
   * @return width of the legend's border, in pixels
   * 
   * @see #setLegendBorderWidth setLegendBorderWidth
   * 
   */
  public int getLegendBorderWidth() {
    return legendBorderWidth;
  }

  /**
   * Returns the style of the chart's legend's border
   * 
   * @return cssStyle of the legend's border
   * 
   * @see #setLegendBorderStyle setLegendBorderStyle
   * 
   */
  public String getLegendBorderStyle() {
    return legendBorderStyle;
  }

  /**
   * Returns the color of the font used to display the labels within the legend
   * (chart key)
   * 
   * @return CSS color string defining the legend text's color
   * 
   * @see #setLegendFontColor setLegendFontColor
   */
  public String getLegendFontColor() {
    return legendFontColor;
  }

  /**
   * Returns the CSS font size, in pixels, of text displayed in the chart's
   * legend (also know as a chart's key).
   * 
   * @return the (previously specified) font size of legend text
   * 
   * @see #setLegendFontSize setLegendFontSize
   */
  public int getLegendFontSize() {
    return legendFontSize;
  }

  /**
   * Returns the font-family used to render this GChart's legend text.
   * 
   * @return font-famly of legend text.
   * 
   * @see #setLegendFontFamily setLegendFontFamily
   */
  public String getLegendFontFamily() {
    return legendFontFamily;
  }

  /**
   * Returns the font-style in which this GChart's legend text will be rendered.
   * 
   * @return font-style of legend text (italic, normal, etc.)
   * 
   * @see #setLegendFontStyle setLegendFontStyle
   */
  public String getLegendFontStyle() {
    return legendFontStyle;
  }

  /**
   * Returns true if legend text will be rendered in a bold, or false if in
   * normal, weight font.
   * 
   * @return if the legend's text is in bold or not.
   * 
   * @see #setLegendFontWeight setLegendFontWeight
   */
  public String getLegendFontWeight() {
    return legendFontWeight;
  }

  /**
   * Returns the thickness (width) of the rectangular region to the right of the
   * y2-axis label allocated for the chart legend.
   * <p>
   * 
   * The region extends vertically in parallel with the right edge of the plot
   * area. The legend is always centered vertically and horizontally within this
   * rectangular region.
   * <p>
   * 
   * This method always returns 0 if the legend is not visible; the rectangular
   * legend region is entirely eliminated in that case.
   * 
   * @return thickness (width) of legend key holding region, in pixels.
   * 
   * @see #setLegendThickness setLegendThickness
   */
  public int getLegendThickness() {
    int result = 0;
    if (isLegendVisible() && 0 < getNVisibleCurvesOnLegend()) {
      if (GChart.NAI == legendThickness)
        result = getDefaultLegendThickness();
      else
        result = legendThickness;
    }

    return result;

  }

  /**
   * Returns the developer-defined widget used for this chart's Legend.
   * <p>
   * 
   * If <tt>null</tt> (the default) GChart will generate a legend widget
   * internally.
   * 
   * 
   * @return the developer-defined legend, or <tt>null</tt> if none.
   * 
   * @see #setLegend setLegend
   * 
   */
  public Widget getLegend() {
    return legend;
  }

  /**
   * The amount, in pixels, the legend is shifted along the x axis from its base
   * position.
   * 
   * @return amount legend is shifted along the x axis
   * 
   * @see #setLegendXShift setLegendXShift
   * 
   */
  public int getLegendXShift() {
    return legendXShift;
  }
  /**
   * Returns a previously specified initial pie slice orientation.
   * 
   * @return the fraction of a clockwise rotation, beginning from the 6 o'clock
   *         position, needed to reach the default initial pie slice orientation.
   * 
   * @see #setInitialPieSliceOrientation setInitialPieSliceOrientation
   */
  public double getInitialPieSliceOrientation() {
    return initialPieSliceOrientation;
  }

  /**
   * The amount, in pixels, the legend is shifted along the y axis from its base
   * position.
   * 
   * @return amount legend is shifted along the y axis
   * 
   * @see #setLegendYShift setLegendYShift
   * 
   */
  public int getLegendYShift() {
    return legendXShift;
  }

  /**
   * Returns the maximum number of pixels any canvas Widget used in GChart's
   * rendering can have.
   * 
   * @return the maximum number of pixels on a GChart canvas widget
   * 
   * @see #setMaxCanvasPixels setMaxCanvasPixels
   */
  public double getMaxCanvasPixels() {
    return maxCanvasPixels;
  }

  /**
   * Returns the number of curves on this chart.
   * 
   * @return the number of curves on this chart
   * 
   * @see #getCurve getCurve
   * @see #addCurve() addCurve()
   * @see #addCurve(int) addCurve(int)
   * @see #removeCurve removeCurve
   * @see #clearCurves clearCurves
   */
  public int getNCurves() {
    return curves.size() - N_SYSTEM_CURVES;
  }

  /**
   * Returns the CSS string that specifies the width of the padding between the
   * chart and it's external border
   * <p>
   * 
   * @return the CSS string that defines the CSS padding property for the GChart
   *         as a whole.
   * 
   * @see #setPadding(String) setPadding
   * 
   */
  public String getPadding() {
    return padding;
  }

  /**
   * Returns the background color of the area of the chart in which symbols
   * representing curve data are displayed
   * 
   * @return CSS color string defining the plot area's background color
   * 
   * @see #setPlotAreaBackgroundColor setPlotAreaBackgroundColor
   */
  public String getPlotAreaBackgroundColor() {
    Curve c = getSystemCurve(PLOTAREA_ID);
    String result = c.getSymbol().getBackgroundColor();
    return result;
  }

  /**
   * Returns the border color of the area of the chart in which symbols
   * representing curve data are displayed
   * 
   * @return CSS color string defining the color of the plot area's border
   * 
   * @see #setPlotAreaBorderColor setPlotAreaBorderColor
   */
  public String getPlotAreaBorderColor() {
    Curve c = getSystemCurve(PLOTAREA_ID);
    String result = c.getSymbol().getBorderColor();
    return result;
  }

  /**
   * Returns the width of the border around the area of the chart in which
   * symbols representing curve data are displayed.
   * 
   * @return width, in pixels, of the border around the plot area
   * 
   * @see #setPlotAreaBorderWidth setPlotAreaBorderWidth
   */
  public int getPlotAreaBorderWidth() {
    Curve c = getSystemCurve(PLOTAREA_ID);
    int result = c.getSymbol().getBorderWidth();
    return result;
  }

  /**
   * Returns the style of the border around the area of the chart in which
   * symbols representing curve data are displayed (the so-called plot area).
   * 
   * @return CSS style of the border around the plot area
   * 
   * @see #setPlotAreaBorderStyle setPlotAreaBorderStyle
   */
  public String getPlotAreaBorderStyle() {
    Curve c = getSystemCurve(PLOTAREA_ID);
    String result = c.getSymbol().getBorderStyle();
    return result;
  }

  /**
   * 
   * Returns the image URL that will be used to define the plot area's
   * background the next time <tt>update</tt> is called.
   * <p>
   * 
   * @return url of image to be used as the background of the plot area the next
   *         time that <tt>update</tt> is called.
   * 
   * @see #setPlotAreaImageURL setPlotAreaImageURL
   * @see #update update
   * 
   */
  public String getPlotAreaImageURL() {
    Curve c = getSystemCurve(PLOTAREA_ID);
    String result = c.getSymbol().getImageURL();
    return result;
  }

  /**
   * 
   * Returns a flag that tells if GChart is configured to perform updates so
   * that the chart uses less memory.
   * 
   * @return <tt>true</tt> if GChart optimizes updates to save memory,
   *         <tt>false</tt> (the default) if it optimizes them to save time.
   * 
   * @see #setOptimizeForMemory setOptimizeForMemory
   * 
   */
  public boolean getOptimizeForMemory() {
    return optimizeForMemory;
  }

  /**
   * Returns the previously specified render padding factor.
   * 
   * @return the render padding factor
   * 
   * @see #setRenderPaddingFactor setRenderPaddingFactor
   * 
   */
  public double getRenderPaddingFactor() {
    return renderPaddingFactor;
  }

  /**
   * @deprecated
   * 
   *             Equivalent to <tt>!getClipToPlotArea()</tt>. Use that method
   *             instead.
   * 
   * @see #getClipToPlotArea getClipToPlotArea
   */

  public boolean getShowOffChartPoints() {
    return !getClipToPlotArea();
  }

  /**
   * @deprecated
   * 
   *             Equivalent to <tt>!getClipToDecoratedChart()</tt>. Use that
   *             method instead.
   * 
   * @see #getClipToDecoratedChart getClipToDecoratedChart
   * 
   */
  public boolean getShowOffDecoratedChartGlyphs() {
    return !getClipToDecoratedChart();
  }

  /**
   * Returns a URL that points to a 1 x 1 pixel blank image file GChart requires
   * to render its charts without producing missing image icons.
   * 
   * <p>
   * 
   * @return the URL of the file GChart needs to prevent missing image icons
   *         from appearing on your chart.
   * 
   * @see #setBlankImageURL setBlankImageURL
   * 
   */

  public static String getBlankImageURL() {
    return null == blankImageURL ? DEFAULT_BLANK_IMAGE_URL_FULLPATH
        : blankImageURL;
  }

  private static Object currentCurveData = null;

  /**
   * 
   * Returns a reference to the auxiliary data object of the curve that is
   * currently being rendered by GChart. If no curve is currently being
   * rendered, returns <tt>null</tt>.
   * 
   * <p>
   * 
   * Vector graphics widgets that implement <tt>GChartCanvasLite</tt> can use
   * this method to retrieve auxiliary curve data that can then be used to
   * control advanced features of the vector graphics (
   * <tt>GChartCanvasLite</tt> implementing) widget that GChart does not
   * directly support. In other words, this method lets you access any custom
   * curve specific data you may need to extend GChart's vector graphics
   * capabilities in various ways.
   * 
   * <p>
   * 
   * For example, the GChart live demo uses this method to retrieve a special
   * "Catmull-Rom tension parameter" from the curve's auxiliary data that it
   * uses to adjust the amount of "curvyness" in its various connecting lines.
   * By default, GChart only supports straight connecting lines so the live demo
   * overrides the <tt>lineTo</tt> method in its <tt>GChartCanvasLite</tt>
   * implementation so as to call the GWTCanvas method's <tt>cubicCurveTo</tt>
   * to produce curvy connecting lines with the specified amount of "tension".
   * <p>
   * 
   * As you've probably gathered by now, this kind of thing is not for the faint
   * of heart, and relies on hidden connections with aspects of GChart's
   * internal implementation that could break your code in future GChart
   * releases. On the other hand, there are a myriad of vector graphics library
   * features GChart does not support (curvy lines, gradients, image-based area
   * filling, etc.) and this "backdoor" approach is flexible enough that, if you
   * work a bit, you may be able to exploit many of these advanced features in
   * your charts.
   * <p>
   * 
   * If you implement such a rendering extension that would be helpful to
   * others, please share it on the GChart issue tracker. As we learn more about
   * how people exploit this backdoor, we can probably improve it, make it more
   * reliable, and/or incorporate new vector graphics capabilities directly into
   * GChart.
   * <p>
   * 
   * <i>Tip:</i> This method does not return a reference to the curve itself,
   * because GChart's rendering code assumes that curve specifications proper do
   * not change during rendering. If really you need to get at the curve itself,
   * store a reference to it within your custom data object.
   * 
   * @see GChartCanvasLite GChartCanvasLite
   * @see #setCanvasFactory setCanvasFactory
   * @see Curve#setCurveData setCurveData
   * 
   * @return the auxiliary, developer-defined, curve data object associated with
   *         the currently-being-rendered curve, or <tt>null</tt> if no curve is
   *         currently being rendered.
   * 
   */
  public static Object getCurrentCurveData() {
    return currentCurveData;
  }

  private HoverParameterInterpreter hoverParameterInterpreter = null;

  /**
   * Returns this GChart's hover parameter interpreter.
   * 
   * @see #setHoverParameterInterpreter setHoverParameterInterpreter
   * 
   * @return the hover parameter interpreter used by this GChart, or
   *         <tt>null</tt> if none.
   * 
   */
  public HoverParameterInterpreter getHoverParameterInterpreter() {
    return hoverParameterInterpreter;
  }

  private boolean hoverTouchingEnabled = true;

  /**
   * Is it possible to select points and have their hover annotations pop up,
   * merely by "touching" them with the mouse-attached "brush"?
   * 
   * @return true (the default) if just hovering over a point can select it,
   *         false if you must click on a point to select it.
   * 
   * @see #setHoverTouchingEnabled setHoverTouchingEnabled
   * 
   */
  public boolean getHoverTouchingEnabled() {
    return hoverTouchingEnabled;
  }

  /**
   * Returns the x-axis associated with this chart. Use the returned reference
   * to manipulate axis min and max, number of ticks, tick positions, tick label
   * formats, etc.
   * <p>
   * 
   * @return object representing the x-axis of this chart.
   * 
   * @see #getYAxis getYAxis
   * @see #getY2Axis getY2Axis
   */
  public Axis getXAxis() {
    return xAxis;
  }

  /**
   * Returns the number of x-pixels in the region of the chart used for curve
   * display purposes.
   * 
   * @return the number of x-pixels available for curve display.
   * 
   * @see #setXChartSize setXChartSize
   * 
   */
  public int getXChartSize() {
    return xChartSize;
  }

  /**
   * Returns the number of x-pixels reserved for the chart as a whole, including
   * space reserved for decorations (title, footnotes, axis labels, ticks, tick
   * labels, legend key, etc.).
   * <p>
   * 
   * The returned size does not include the border or padding around the chart
   * as a whole.
   * <p>
   * 
   * You cannot directly set the decorated x chart size. Instead, you must set
   * the width of the plot area, and the thicknesses of certain of the
   * decoration-holding regions (using methods linked to below) that, summed
   * together, define the total width of the chart.
   * 
   * @return the width of the entire chart, in pixels.
   * 
   * @see #setXChartSize setXChartSize
   * @see #getYChartSizeDecorated getYChartSizeDecorated
   * @see Axis#setAxisLabelThickness setAxisLabelThickness
   * @see Axis#setTickLabelThickness setTickLabelThickness
   * @see Axis#setTickLength setTickLength
   * @see Axis#setTickLocation setTickLocation
   * @see Axis#setTickLabelPadding setTickLabelPadding
   * @see Axis#setLegendThickness setLegendThickness
   * 
   */
  public int getXChartSizeDecorated() {
	  int yAxesSize = 0;
	  for(int i = 0; i < Y_AXES_MAX; i++){
		  if(getYAxis(i) != null){
			  yAxesSize += getYAxis(i).getAxisLabelThickness()
		        + getYAxis(i).getTickLabelThickness()
		        + getYAxis(i).getTickSpace() + getYAxis(i).getTickLabelPadding();
		  }
	  }
	  int result = getXChartSize() + yAxesSize
        + legendLocation.getLeftThickness(this)
        + legendLocation.getRightThickness(this);
	  return result;
  }

  /**
   * Returns the y2-axis (right y axis) associated with this chart. Use the
   * returned reference to manipulate axis min and max, number of ticks, tick
   * positions, tick label formats, etc.
   * 
   * <p>
   * 
   * @return object representing the y2-axis of this chart.
   * 
   * @see #getYAxis getYAxis
   * @see #getXAxis getXAxis
   */
  /*public Axis getY2Axis() {
    return y2Axis;
  }*/

  /**
   * Returns the (left) y-axis associated with this chart. Use the returned
   * reference to manipulate axis min and max, number of ticks, tick positions,
   * tick label formats, etc.
   * <p>
   * 
   * @return object representing the y-axis of this chart.
   * 
   * @see #getXAxis getXAxis
   * @see #getY2Axis getY2Axis
   */
  public Axis getYAxis(int k) {
    return yAxis[k];
  }

  /**
   * Returns the number of y-pixels in the region of the chart used for curve
   * display purposes.
   * 
   * @return the number of y-pixels available for curve display.
   * 
   * @see #setYChartSize setYChartSize
   * 
   */
  public int getYChartSize() {
    return yChartSize;
  }

  /**
   * Returns the number of y-pixels reserved for the chart as a whole, including
   * space reserved for decorations (title, footnotes, axis labels, ticks, tick
   * labels, etc.).
   * <p>
   * 
   * The returned size does not include the border or padding around the chart
   * as a whole.
   * <p>
   * 
   * You cannot directly set the decorated y chart size. Instead, you must set
   * sizes and thicknesses of the plot area and certain of the
   * decoration-holding regions (using the methods linked-to below) that, when
   * summed together, define the height of the decorated chart.
   * 
   * @return the height of the entire chart, in pixels.
   * 
   * @see #setYChartSize setYChartSize
   * @see #getXChartSizeDecorated getXChartSizeDecorated
   * @see Axis#setAxisLabelThickness setAxisLabelThickness
   * @see Axis#setTickLabelThickness setTickLabelThickness
   * @see Axis#setTickLength setTickLength
   * @see Axis#setTickLocation setTickLocation
   * @see Axis#setTickLabelPadding setTickLabelPadding
   * @see #setChartTitleThickness setChartTitleThickness
   * @see #setChartFootnotesThickness setChartFootnotesThickness
   * 
   */
  public int getYChartSizeDecorated() {
    int result = getYChartSize() + getXAxis().getAxisLabelThickness()
        + getXAxis().getTickLabelThickness()
        + getXAxis().getTickSpace() + getXAxis().getTickLabelPadding()
        + getChartTitleThickness() + getChartFootnotesThickness();

    return result;
  }

  /**
   * Determines if this chart has a "y2" (right) y-axis.
   * <p>
   * Only charts that have at least one curve on the right y axis will have a
   * y2-axis.
   * 
   * @return true if the chart has a second y axis, false otherwise.
   * 
   * @see Curve#setYAxis Curve.setYAxis
   */
  public boolean hasYKAxis(int k) {
    boolean result = getYAxis(k).getNCurvesVisibleOnAxis() > 0;
    return result;
  }

  /**
   * Determines if this chart has an ordinary, or left, y-axis.
   * <p>
   * Only charts that have at least one curve on the left y axis will have a
   * y-axis.
   * 
   * @return true if the chart has a left y axis, false otherwise
   * 
   * @see Curve#setYAxis Curve.setYAxis
   * 
   */
  public boolean hasYAxis() {
    boolean result = getYAxis(0).getNCurvesVisibleOnAxis() > 0;
    return result;
  }

  /**
   * Determines if the legend of this chart is visible.
   * 
   * 
   * @return true if the legend is visible, false otherwise.
   * 
   * @see #setLegendVisible setLegendVisible
   */
  public boolean isLegendVisible() {
    return isLegendVisible;
  }

  /**
   * 
   * Is the in-browser rendition of the chart inconsistent with the current
   * chart specs? In other words, is a call to GChart's <tt>update</tt> method
   * needed to bring the browser's display into agreement with current chart
   * specs?
   * <p>
   * 
   * <i>Note:</i> Whenever this method returns <tt>true</tt>, GChart "freezes"
   * hover feedback, and can no longer actively track the currently "touched"
   * point. This is because GChart, to simplify its bookkeeping, assumes
   * in-browser (DOM) rendering and current chart specs are in synch when
   * determining the point selection consequences of mouse events over the
   * chart.
   * 
   * @return true if a call to <tt>update</tt> is needed to bring current chart
   *         specifications and browser-rendered representation into synch,
   *         false otherwise.
   * 
   * @see #update update
   * @see #getTouchedPoint getTouchedPoint
   * 
   */
  public boolean isUpdateNeeded() {
    boolean result = chartDecorationsChanged || !plotPanel.isValidated();
    return result;
  }

  /**
   * Removes the curve at the specified positional index.
   * <p>
   * 
   * @param iCurve
   *          index of the curve to be removed
   * 
   * @see #removeCurve(Curve) removeCurve(Curve)
   * @see #getCurve getCurve
   * @see #addCurve() addCurve()
   * @see #addCurve(int) addCurve(int)
   * @see #clearCurves clearCurves
   * @see #getNCurves getNCurves
   */
  public void removeCurve(int iCurve) {
    if (iCurve >= getNCurves())
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not exceed getNCurves()-1 ("
          + (getNCurves() - 1) + ")");
    else if (iCurve < 0)
      throw new IllegalArgumentException("iCurve = " + iCurve
          + "; iCurve may not be negative.");

    invalidateDependentSlices(iCurve);

    /*
     * Simulate user moving away from point before it is deleted (this assures
     * that any required hoverCleanup gets called, and clears the otherwise
     * dangling reference to the touched point).
     */
    if (plotPanel.touchedPoint != null
        && plotPanel.touchedPoint.getParent() == getSystemCurve(iCurve))
      plotPanel.touch(null);

    // remove the rendering panel that corresponds to this curve
    // (must keep the two lists in synch or 1-to-1 mapping breaks)
    int internalIndex = internalCurveIndex(iCurve);
    if (0 != plotPanel.getRenderingPanelCount()) {
      int rpIndex = getRenderingPanelIndex(internalIndex);
      plotPanel.removeGraphicsRenderingPanel(rpIndex);
      plotPanel.removeAnnotationRenderingPanel(rpIndex);
    }
    // else before 1st update, no rendering panels created yet

    Curve c = curves.get(internalIndex);
    if (c.isVisible()) {
    	getXAxis().decrementCurves();
    	for(int i = 0; i < Y_AXES_MAX; i++){
    		if (c.getYAxis() == Y_AXIS[i])
        		getYAxis(i).decrementCurves();
    	}	
    }
    c.clearIndex();

    curves.remove(internalIndex);
    // adjust ArrayList indexes to account for newly removed element
    for (int i = internalIndex; i < curves.size(); i++)
      curves.get(i).decrementIndex();
  }

  /**
   * Removes the given curve from this GChart.
   * <p>
   * 
   * If the given curve is <tt>null</tt> or is not a curve on this GChart, an
   * exception is thrown.
   * 
   * <p>
   * 
   * @param curve
   *          the curve to be removed.
   * 
   * @see #removeCurve(int) removeCurve(int)
   * 
   */

  public void removeCurve(Curve curve) {
    if (null == curve)
      throw new IllegalArgumentException("Curve cannot be null.");
    int index = getCurveIndex(curve);
    if (index == GChart.NAI)
      throw new IllegalArgumentException(
          "Curve is not one of this GChart's curves.");

    if (index < 0)
      throw new IllegalArgumentException(
          "System curves cannot be removed (this should be impossible; a GChart bug is likely.)");
    else
      removeCurve(index);
  }

  /**
   * Specifies the background color of the chart as a whole.
   * 
   * <p>
   * The default background color is <tt>USE_CSS</tt>.
   * <p>
   * 
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          the chart's background color, in a standard CSS color string
   *          format.
   * 
   * 
   * @see #USE_CSS USE_CSS
   * 
   */
  public void setBackgroundColor(String cssColor) {
    chartDecorationsChanged = true;
    backgroundColor = cssColor;
  }

  /**
   * Specifies a URL that points to the transparent, 1 x 1 pixel, "blank GIF"
   * that GChart uses in order to render your chart without adding spurious
   * "missing image" icons to it.
   * <p>
   * 
   * When GWT compiles an application that imports the GChart library, it
   * automatically adds an appropriate blank image, <tt>gchart.gif</tt>, to the
   * module base directory (this is the directory into which GWT also copies
   * your compiled Javascript, all the files in your public directory, etc.).
   * <p>
   * 
   * By default, GChart uses the following blank image URL:
   * <p>
   * 
   * <pre>
   * GWT.getModuleBaseURL() + &quot;gchart.gif&quot;
   * </pre>
   * <p>
   * 
   * <small> Earlier versions used "gchart.gif" as this default url. <a href=
   * "http://groups.google.com/group/Google-Web-Toolkit/msg/4be3f19dc14f958a">
   * This GWT forum post by Dean S. Jones</a> identified the need to add the
   * <tt>GWT.getModuleBaseURL()</tt> prefix. </small>
   * <p>
   * 
   * Note that this default adds a potentially very long URL to every
   * <tt>img</tt> element added by GChart to render your chart, which can (in
   * theory) more than double the memory required to represent your chart in the
   * browser, because the absolute URLs can be of undetermined length. In
   * practice, browser memory usage increases of 10% have been observed with the
   * on-line demo GChart and a typicial, 60-odd character absolute URL.
   * <p>
   * 
   * You have several alternatives to the above default that can often reduce
   * the length of the URL and thus save browser memory:
   * 
   * <p>
   * 
   * <ol>
   * <li>Simply copy <tt>gchart.gif</tt> from the module base directory into
   * your host page's base directory, and then use
   * <tt>setBlankImageURL("gchart.gif")</tt> to access this URL relatively.
   * 
   * <li>If the relative path from the host page base directory to the module
   * base directory is reasonably short, pass that alternative relative URL to
   * this method (note that all relative URLs are interpreted relative to the
   * base directory of the host page containing your GChart).
   * 
   * <li>Place a copy of <tt>gchart.gif</tt> into a directory whose absolute URL
   * is very short, and then pass that short absolute URL to this method.
   * 
   * </ol>
   * <p>
   * 
   * <small> <i>Special note to anyone reading this who designed HTML's
   * <tt>image</tt> tag:</i> If you had provided a <tt>src=none</tt> option,
   * this method would not have to exist. </small>
   * <p>
   * 
   * <i>Tip:</i> If you already have an appropriate blank gif on your site that
   * is accessible from the host page via a reasonably short URL there is no
   * need to copy <tt>gchart.gif</tt>. You can just pass that URL to this
   * method.
   * 
   * <p>
   * 
   * <i>Note:</i> Though GChart uses this blank image by default, you can use
   * the <tt>setImageURL</tt> method to specify a non-blank image for use in
   * rendering a specific curve.
   * <p>
   * 
   * 
   * @param blankImageURL
   *          a URL that points to a 1 x 1 pixel transparent image that GChart
   *          needs to render your charts without adding a spurious
   *          "missing image" icon.
   * 
   * @see #getBlankImageURL getBlankImageURL
   * @see #DEFAULT_BLANK_IMAGE_URL DEFAULT_BLANK_IMAGE_URL
   * @see Symbol#setImageURL setImageURL
   * 
   */

  public static void setBlankImageURL(String blankImageURL) {
    if (blankImageURL != GChart.blankImageURL) {
      GChart.blankImageURL = blankImageURL;
      // Decided not to prefetch blank image URL because 1) pre-fetching
      // doesn't improve performance noticably in tested browsers,
      // 2) there are reports of possible memory leaks associated with
      // its use in the GWT issue tracker, and 3) users can
      // easily do the prefetch on their own if they want to, and that
      // is really the right place to do a prefetch anyway.
      // Image.prefetch(GChart.getBlankImageURL());
    }
  }

  /**
   * Specifies the color of the border around the chart as a whole.
   * 
   * <p>
   * The default border color is <tt>USE_CSS</tt>.
   * 
   * <p>
   * <blockquote><small> <i>Tip:</i> No border will appear if either
   * <tt>borderStyle</tt> is <tt>none</tt>, <tt>borderWidth</tt> is <tt>0px</tt>
   * or <tt>borderColor</tt> is <tt>transparent</tt>. Since these will often be
   * the "CSS inherited" values, generally, it's best to set all three
   * properties whenever you set any one of them. </small></blockquote>
   * <p>
   * 
   * 
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          the color of the chart's border, in a standard CSS color string
   *          format.
   * 
   * @see #setBorderWidth(String) setBorderWidth
   * @see #setBorderStyle(String) setBorderStyle
   * @see #getBorderColor getBorderColor
   * @see #USE_CSS USE_CSS
   * 
   */
  public void setBorderColor(String cssColor) {
    chartDecorationsChanged = true;
    if (borderColor == null || borderColor == TRANSPARENT_BORDER_COLOR)
      throw new IllegalArgumentException(
          "null and TRANSPARENT_BORDER_COLOR are not allowed. This method requires a valid CSS color specification String.");
    borderColor = cssColor;
  }

  /**
   * Sets style of the border around the chart as a whole.
   * 
   * <p>
   * The default border style is <tt>USE_CSS</tt>.
   * <p>
   * 
   * <p>
   * <blockquote><small> <i>Tip:</i> No border will appear if either
   * <tt>borderStyle</tt> is <tt>none</tt>, <tt>borderWidth</tt> is <tt>0px</tt>
   * or <tt>borderColor</tt> is <tt>transparent</tt>. Since these will often be
   * the "CSS inherited" values, generally, it's best to set all three
   * properties whenever you set any one of them. </small></blockquote>
   * <p>
   * 
   * 
   * @param borderStyle
   *          a CSS border style such as "solid", "dotted", "dashed", etc.
   * 
   * @see #getBorderStyle getBorderStyle
   * @see #setBackgroundColor(String) setBackgroundColor
   * @see #setBorderColor(String) setBorderColor
   * @see #setBorderWidth(String) setBorderWidth
   * @see #USE_CSS USE_CSS
   * 
   * 
   */
  public void setBorderStyle(String borderStyle) {
    chartDecorationsChanged = true;
    this.borderStyle = borderStyle;
  }

  /**
   * Specifies the width of the border around the chart as a whole.
   * 
   * <p>
   * The default border width is <tt>USE_CSS</tt>.
   * 
   * <p>
   * <blockquote><small> <i>Tip:</i> No border will appear if either
   * <tt>borderStyle</tt> is <tt>none</tt>, <tt>borderWidth</tt> is <tt>0px</tt>
   * or <tt>borderColor</tt> is <tt>transparent</tt>. Since these will often be
   * the "CSS inherited" values, generally, it's best to set all three
   * properties whenever you set any one of them. </small></blockquote>
   * 
   * @param cssWidth
   *          width of the border around the chart as a whole, expressed as a
   *          CSS border-width specification string, such as "1px".
   * 
   * @see #getBorderWidth getBorderWidth
   * @see #setBorderStyle(String) setBorderStyle
   * @see #setBorderColor(String) setBorderColor
   * @see #USE_CSS USE_CSS
   */
  public void setBorderWidth(String cssWidth) {
    chartDecorationsChanged = true;
    borderWidth = cssWidth;
  }

  /**
   * Specifies the fractional amount that the default width and height of each
   * canvas used to render this chart will be increased. For example, with 100px
   * x 200px default sized canvas rectangle, and canvas width and height
   * expansion factors of 0.1 and 0.2, the canvas rectangle would be increased
   * to 110px x 240px.
   * 
   * <p>
   * 
   * If you plug in an external vector graphics library via
   * <tt>setCanvasFactory</tt>, GChart creates a vector graphics "canvas" for
   * each curve that is <i>just big enough</i> to contain the points on that
   * curve. These "shrink-wrapped" canvas rectangles save memory. But, if you
   * use Bezier curves, your curves can easily extend outside of these
   * rectangles. So, this method lets you "pad" these canvas rectangles with
   * extra space, thus preventing such "overshooting" curves from being
   * improperly clipped off.
   * <p>
   * 
   * <small>To handle every possible Bezier overshoot contingency, these canvas
   * expansion factors would have to be huge. But, in the oil price simulation
   * of the live demo, even with its "100% curvy" option, a factor of 0.3 on the
   * y axis and 0.0 on the x axis (a 30% memory hit for this chart) works well.
   * 0.1 on y even works OK, if you don't mind truncatiing the wildest
   * overshooting curves. </small>
   * 
   * @param widthExpansionFactor
   *          the fraction that the width of the canvas rectangle will be
   *          expanded. The default is <tt>0</tt> (no width expansion).
   * @param heightExpansionFactor
   *          the fraction that the height of the canvas rectangle will be
   *          expanded. The default is <tt>0</tt> (no height expansion).
   * 
   * @see #setCanvasFactory setCanvasFactory
   * 
   */
  public void setCanvasExpansionFactors(double widthExpansionFactor,
      double heightExpansionFactor) {
    invalidateAccessibleCurves();
    canvasExpansionFactorX = widthExpansionFactor;
    canvasExpansionFactorY = heightExpansionFactor;
  }

  /**
   * Convenience method equivalent to <tt>setChartFootnotes(new HTML(html))</tt>
   * .
   * 
   * @param html
   *          HTML text used to define the chart's title.
   * 
   * @see #setChartFootnotes(Widget) setChartFootnotes(Widget)
   */
  public void setChartFootnotes(String html) {
    setChartFootnotes(new HTML(html));
  }

  /**
   * Sets widget that appears just below the chart.
   * <p>
   * 
   * The widget will vertically centered within a band just below the x axis
   * label that stretches along the entire bottom edge of the chart, and whose
   * height is defined by <tt>setChartFootnotesThickness</tt>.
   * 
   * <p>
   * 
   * The widget will either be left justified, or horizontally centered, within
   * this band depending on the property defined by
   * <tt>setChartFootnotesLeftJustified</tt>
   * 
   * 
   * @param chartFootnotes
   *          widget representing the chart's footnotes
   * 
   * @see #setChartFootnotes(String) setChartFootnotes(String)
   * @see #setChartFootnotesThickness setChartFootnotesThickness
   * @see #getChartFootnotes getChartFootnotes
   * @see #setChartFootnotesLeftJustified setChartFootnotesLeftJustified
   */
  public void setChartFootnotes(Widget chartFootnotes) {
    chartDecorationsChanged = true;
    this.chartFootnotes = chartFootnotes;
  }

  /**
   * Defines if this chart's footnotes are left justified, or horizontally
   * centered across the bottom edge of the chart.
   * <p>
   * Note that a chart's footnotes are always vertically centered within the
   * band at the bottom of the chart reserved for chart footnotes. Use the
   * <tt>setChartFootnotesThickness</tt> method to set the height of this band.
   * 
   * @param footnotesLeftJustified
   *          true to position chart footnotes flush against the left edge of
   *          the chart, false (the default) to center them horizontally across
   *          the chart's bottom edge.
   * 
   * @see #setChartFootnotes(String) setChartFootnotes(String)
   * @see #getChartFootnotes getChartFootnotes
   * @see #setChartFootnotesThickness
   */
  public void setChartFootnotesLeftJustified(boolean footnotesLeftJustified) {
    chartDecorationsChanged = true;
    chartFootnotesLeftJustified = footnotesLeftJustified;
  }

  /**
   * Sets the thickness (height) of the rectangular region at the bottom of the
   * chart allocated for the footnotes.
   * <p>
   * 
   * The width of this region always equals the width of the entire GChart
   * (including legend and axis labels).
   * <p>
   * 
   * Your footnotes widget is always vertically centered in this region.
   * <p>
   * 
   * 
   * Your footnotes widget will either be horizontally centered in this region,
   * or left justified in it, depending on the property defined by the
   * <tt>setChartFootnotesLeftJustified</tt> method.
   * <p>
   * 
   * This setting has no impact on chart layout if the footnotes widget is
   * <tt>null</tt> (the default); the rectangular footnotes region is entirely
   * eliminated, and in effect has a 0 thickness, in that case.
   * <p>
   * 
   * If you set the footnotes thickness to <tt>GChart.NAI</tt> (the default)
   * GChart will use a thickness based on the estimated number of (
   * <tt>&lt;br&gt;</tt> or <tt>&lt;li&gt;</tt> delimited) lines.
   * 
   * @param thickness
   *          the thickness (height) of the rectangle that contains the
   *          footnotes, in pixels, or <tt>GChart.NAI</tt> to use the default
   *          thickness.
   * 
   * @see #getChartFootnotesThickness getChartFootnotesThickness
   * @see #setChartFootnotesLeftJustified setChartFootnotesLeftJustified
   * @see GChart#NAI GChart.NAI
   * @see #DEFAULT_FOOTNOTES_THICKNESS DEFAULT_FOOTNOTES_THICKNESS
   * 
   */
  public void setChartFootnotesThickness(int thickness) {
    chartDecorationsChanged = true;
    this.footnotesThickness = thickness;
  }

  /**
   * Convenience method equivalent to
   * <tt>setXChartSize(xChartSize); setYChartSize(yChartSize)</tt>.
   * 
   * @param xChartSize
   *          number of x-pixels in the curve display area of the chart
   * @param yChartSize
   *          number of y-pixels in the curve display area of the chart
   * 
   * @see #setXChartSize setXChartSize
   * @see #setYChartSize setYChartSize
   * 
   */
  public void setChartSize(int xChartSize, int yChartSize) {
    setXChartSize(xChartSize);
    setYChartSize(yChartSize);
  }

  /**
   * Convenience method equivalent to <tt>setChartTitle(new HTML(html))</tt>.
   * 
   * @param html
   *          HTML text used to define the chart's title.
   * 
   * @see #setChartTitle(Widget) setChartTitle(Widget)
   */
  public void setChartTitle(String html) {
    setChartTitle(new HTML(html));
  }

  // returns x,y min/max over every plotted curve

  /**
   * Specifies the widget that appears centered just above the chart.
   * 
   * @param chartTitle
   *          the widget to be used as this chart's title.
   * 
   * @see #setChartTitle(String) setChartTitle(String)
   * @see #setChartTitleThickness setChartTitleThickness
   * @see #getChartTitle getChartTitle
   * 
   */
  public void setChartTitle(Widget chartTitle) {
    chartDecorationsChanged = true;
    this.chartTitle = chartTitle;
  }

  /**
   * Sets the thickness (height) of the rectangular region at the top of the
   * chart allocated for the title.
   * <p>
   * 
   * Your title widget is always centered vertically and horizontally within
   * this rectangular region.
   * <p>
   * 
   * This setting has no impact on chart layout if the title widget is
   * <tt>null</tt>, since the title-holding region is entirely eliminated in
   * that case.
   * 
   * If you set the title thickness to <tt>GChart.NAI</tt> (the default) GChart
   * will use a thickness that is based on the the number of <tt>&lt;br&gt;</tt>
   * or <tt>&lt;li&gt;</tt> delimited HTML lines if the title Widget implements
   * <tt>HasHTML</tt>.
   * 
   * @param thickness
   *          the thickness (height) of the rectangle that contains the title,
   *          in pixels, or <tt>GChart.NAI</tt> to use the default thickness.
   * 
   * @see #getChartTitleThickness getChartTitleThickness
   * @see GChart#NAI GChart.NAI
   * @see #DEFAULT_TITLE_THICKNESS DEFAULT_TITLE_THICKNESS
   * 
   */
  public void setChartTitleThickness(int thickness) {
    chartDecorationsChanged = true;
    this.titleThickness = thickness;
  }

  /**
   * Specifies if this chart will clip any rendered chart elements (including
   * hover selection feedback and popup annotations) that extends beyond the
   * bounds of the decorated chart.
   * <p>
   * 
   * The decorated chart includes not just the plot area, but space allocated
   * for titles, footnotes, legend key, axis labels, tick marks, etc. The size
   * of this decorated chart can be obtained via the
   * <tt>getXChartSizeDecorated</tt> and <tt>getYChartSizeDecorated</tt>
   * methods.
   * <p>
   * 
   * <small> Note that, in non-IE browsers, drawing a curve via
   * <tt>GWTCanvas</tt> that falls outside the bounds of the decorated chart
   * could occlude mouse events over elements on the enclosing page <i>that fall
   * within the smallest bounding rectangle that contains the canvas-rendered
   * curve</i>. HTML rendering (IE's element-based VML used by
   * <tt>GWTCanvas</tt> is essentially HTML-like in this respect) only creates
   * such occlusions at the positions where the curve is actually rendered.
   * </small>
   * 
   * @param clipToDecoratedChart
   *          use <tt>true</tt> to clip off-the-decorated-chart symbols,
   *          annotations, etc. or <tt>false</tt> (the default) to allow such
   *          chart elements to be drawn outside of the rectangular region
   *          allocated for the chart.
   * 
   * @see #getClipToDecoratedChart getClipToDecoratedChart
   * @see GChart#setClipToPlotArea GChart.setClipToPlotArea
   * @see Curve#setClipToPlotArea Curve.setClipToPlotArea
   * @see #getXChartSizeDecorated getXChartSizeDecorated
   * @see #getYChartSizeDecorated getYChartSizeDecorated
   * @see #setCanvasFactory setCanvasFactory
   * 
   */

  public void setClipToDecoratedChart(boolean clipToDecoratedChart) {
    chartDecorationsChanged = true;
    invalidateAccessibleCurves();
    this.clipToDecoratedChart = clipToDecoratedChart;
  }

  /**
   * Specifies if rendered graphics falling outside the plot area will be
   * clipped off.
   * <p>
   * 
   * <i>Note:</i> This clipping does not apply to the hover selection feedback.
   * In particular, points that fall outside the plot area, though not visible,
   * will still display their selection feedback and pop-up hover annotations
   * when the user mouses over them.
   * 
   * @param clipToPlotArea
   *          <tt>false</tt> (the default) to display off-the-plot-area
   *          graphics, <tt>true</tt> to clip them off.
   * 
   * @see #getClipToPlotArea getClipToPlotArea
   * @see #setClipToDecoratedChart setClipToDecoratedChart
   * @see Curve#setClipToPlotArea Curve.setClipToPlotArea
   * 
   */
  public void setClipToPlotArea(boolean clipToPlotArea) {
    chartDecorationsChanged = true;
    invalidateAccessibleCurves();
    this.clipToPlotArea = clipToPlotArea;
  }

  /**
   * Sets the symbol border colors that are used by default for newly created
   * curves. The array must contain one or more elements, each a standard CSS or
   * RGBA color specification string (see the <tt>setBackgroundColor</tt> link
   * below for more on CSS color specification strings) or the special GChart
   * keyword <tt>TRANSPARENT_BORDER_COLOR</tt>.
   * <p>
   * 
   * GChart uses the first color in this array as the default border color of
   * the first curve added (via <tt>addCurve</tt>), the second color for the
   * second curve added, and so on. If more curves are added than the number of
   * elements in the default border colors array, the sequence is repeated.
   * 
   * <p>
   * <small> Note that each curve/symbol's default color is "locked in" at the
   * point when that curve/symbol is first added, based on the total number of
   * curves at that time. </small>
   * 
   * <p>
   * 
   * Because, by default, GChart uses a transparent symbol background color, the
   * default border color will usually, in effect, define the default color of
   * each curve. The default border color also defines the default color of
   * point-to-point connecting lines in a line chart.
   * <p>
   * 
   * If not explicitly specified via this method, GChart uses
   * <tt>GChart.DEFAULT_SYMBOL_BORDER_COLORS</tt> by default. However, most
   * people find the color sequence <a href=
   * "http://ui.openoffice.org/VisualDesign/OOoChart_colors_drafts.html#02">
   * used by OpenOffice's Charts</a> more aesthetically pleasing. The <a
   * href="package-summary.html#GChartExample22a">World's Simplest Line Chart
   * Editor</a> example chart contains a line of code that makes GChart use the
   * OpenOffice defaults.
   * <p>
   * 
   * <small>This feature was added in response to an email from <a
   * href="http://www.profilercorp.com">Joe Cole</a> and <a
   * href="http://gwt-ext.com/forum/viewtopic.php?f=13&t=3465&start=3"> this
   * post</a> by Sanjiv Jivan. They both pointed out the importance of changing
   * GChart's default colors.</small>
   * 
   * 
   * @param defaultBorderColors
   *          array of CSS color strings whose successive elements define the
   *          initial symbol border colors for curves in the order that they are
   *          added.
   * 
   * @see #DEFAULT_SYMBOL_BORDER_COLORS DEFAULT_SYMBOL_BORDER_COLORS
   * @see #TRANSPARENT_BORDER_COLOR TRANSPARENT_BORDER_COLOR
   * @see Symbol#setBackgroundColor setBackgroundColor
   * @see Symbol#setBorderColor setBorderColor
   * @see #addCurve addCurve
   * 
   */

  static public void setDefaultSymbolBorderColors(String[] defaultBorderColors) {
    if (null == defaultBorderColors)
      throw new IllegalArgumentException(
          "defaultBorderColors array cannot be null.");
    else if (defaultBorderColors.length < 1)
      throw new IllegalArgumentException(
          "defaultBorderColors array must have at least 1 element.");
    else
      defaultSymbolBorderColors = defaultBorderColors;

  }

  /**
   * Specifies the font-family that is used, by default, for all
   * textual annotations (tick labels, point labels, title, footnotes,
   * legend labels, and axis labels) on this chart. 
   * <p>
   *
   * This chart-specific default font family setting will be overridden
   * if a more specific method of setting the font family has been
   * employed.
   * 
   * <p>
   *
   * The default overall font family is <tt>DEFAULT_FONT_FAMILY</tt>.
   * 
   * @see Axis#setTickLabelFontFamily setTickLabelFontFamily
   * @see Point#setAnnotationFontFamily setAnnotationFontFamily
   * @see #setLegendFontFamily setLegendFontFamily
   * @see #DEFAULT_FONT_FAMILY DEFAULT_FONT_FAMILY
   * 
   */
  public void setFontFamily(String fontFamily) {
    chartDecorationsChanged = true;
    this.fontFamily = fontFamily;
  }

  /**
   * Specifies the single color used for all gridlines, axes lines, and tick
   * marks.
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          the color, in CSS standard color format, to be used for all
   *          gridlines, axes, and tick marks.
   * 
   * @see #getGridColor getGridColor
   * @see #DEFAULT_GRID_COLOR DEFAULT_GRID_COLOR
   * 
   */
  public void setGridColor(String cssColor) {
    // TODO: support line style for dotted/dashed gridlines lines,
    // allow tick and grid colors to be specified separately, etc.
    getSystemCurve(XGRIDLINES_ID).getSymbol().setBorderColor(cssColor);
    getSystemCurve(XAXIS_ID).getSymbol().setBorderColor(cssColor);
    getSystemCurve(XTICKS_ID).getSymbol().setBorderColor(cssColor);
    for(int i = 0; i < Y_AXES_MAX; i++){
    	getSystemCurve(YGRIDLINES_ID[i]).getSymbol().setBorderColor(cssColor);
    	getSystemCurve(YAXIS_ID[i]).getSymbol().setBorderColor(cssColor);
    	getSystemCurve(YTICKS_ID[i]).getSymbol().setBorderColor(cssColor);
    }
  }

  /**
   * Defines this GChart's hover parameter interpreter.
   * <p>
   * 
   * Hovertext template strings can include <tt>${</tt>... <tt>}</tt> bracketed
   * references to built-in parameters such as <tt>${x}</tt> and <tt>${y}</tt>
   * that get get replaced with appropriate string representations of the x or y
   * values of the hovered-over point in displayed hovertext. You can add new,
   * custom, named parameters, and/or redefine the meaning of built-in
   * parameters, by passing a hover parameter interpreter to this method.
   * <p>
   * 
   * For sample code that shows you how to define a hover parameter interpreter,
   * see <tt>HoverParameterInterpreter</tt>.
   * 
   * @see HoverParameterInterpreter HoverParameterInterpreter
   * @see Symbol#setHovertextTemplate setHovertextTemplate
   * 
   * @param hpi
   *          the hover parameter interpreter to use with all hovertext
   *          templates on this GChart (this interpreter is responsible for
   *          replacing <tt>${</tt>...<tt>}</tt> bracketed embedded parameter
   *          names in the hover text template with appropriate HTML snippets
   *          representing the value of that parameter at the hovered-over
   *          point).
   * 
   */
  public void setHoverParameterInterpreter(HoverParameterInterpreter hpi) {
    hoverParameterInterpreter = hpi;
  }

  /**
   * Specifies if merely hovering over a point is sufficient to select it and
   * display its hover annotation (<tt>true</tt>), or if an actual click is
   * needed (<tt>false</tt>).
   * <p>
   * 
   * With the default of <tt>true</tt>, points are auto-selected as the user
   * "touches" them with the mouse-attached "brush"--no clicking is required.
   * <p>
   * 
   * When hover touching is disabled, a GChart can be used in a manner analogous
   * to a single-selection (sorry there's no multi-selection capability)
   * listbox, with its click-selectable points playing the role of the
   * selectable list items. Specifically, disabling hover touching lets you move
   * the mouse freely without any danger of changing the selected point--the
   * point even remains selected if the mouse moves entirely off the chart. This
   * is helpful when your application follows the common pattern of "select the
   * thing you want to operate on, then issue a command that operates on that
   * thing". This option is also helpful if you use very compute-intensive hover
   * widgets, or if you simply prefer explictly-clicked-open/closed pop-up
   * annotations.
   * <p>
   * 
   * <small> <i>How to Stop Leaky Clicks:</i> In IE7 and the hosted mode
   * browser, clicking ahead on a <tt>Button</tt> widget "leaks" clicks upwards
   * to the enclosing parent, even if you call
   * <tt>event.cancelBubble(true)</tt>.
   * Such "leaky clicks" can inappropriately change the selected point, when
   * you really just wanted to operate on it. This does not happen in Firefox 2,
   * 3, or Chrome, whose buttons properly "eat" the clicks--even when they come
   * in fast. To workaround the problem, you can place the buttons into a hover
   * widget (as shown in <tt>GChartExample21.java</tt> in the chart gallery).
   * This works because GChart applies checks that ignore any mouse events that
   * occur within the rectangular region associated with the opened hover
   * widget. </small>
   * <p>
   * 
   * For an example that uses <tt>setHoverTouchingEnabled(false)</tt> to allow
   * the user to change the y-value of the selected point, see the Chart
   * Gallery's <a href="package-summary.html#GChartExample21">
   * GChartExample21</a>.
   * 
   * 
   * @param hoverTouchingEnabled
   *          <tt>true</tt> (the default) if you want users to be able to select
   *          points simply by hovering over them with their mouse,
   *          <tt>false</tt> if you want to require that they actually click on
   *          points to select them.
   * 
   * @see #getHoverTouchingEnabled getHoverTouchingEnabled
   * @see Symbol#setBrushHeight setBrushHeight
   * @see #touch touch
   * @see #update update
   * @see HoverUpdateable HoverUpdateable
   * 
   */
  public void setHoverTouchingEnabled(boolean hoverTouchingEnabled) {
    this.hoverTouchingEnabled = hoverTouchingEnabled;
  }

  private double initialPieSliceOrientation = 0.0;
  /**
   * Sets the default initial orientation for pie slices.
   * <p>
   * 
   * The default initial orientation is used as the first pie slice's first
   * edge's orientation setting only if the symbol associated with that pie
   * slice has the default, undefined, orientation setting of
   * <tt>Double.NaN</tt>.
   * <p>
   * 
   * The default value of this setting is 0, which corresponds to due south (6
   * o'clock). The value specifies the fraction of a complete clockwise
   * rotation, beginning at due south, required to reach the first edge of the
   * pie slice.
   * 
   * @see Symbol#setPieSliceOrientation setPieSliceOrientation
   * 
   * @param orientation
   *          the orientation to use for the first edge of the first pie slice
   *          in this GChart, in cases in which that first pie slice's
   *          orientation is undefined (<tt>Double.NaN</tt>).
   */

  public void setInitialPieSliceOrientation(double orientation) {
    if (orientation < 0 || orientation >= 1)
      throw new IllegalArgumentException("orientation=" + orientation
                                         + "; " + "orientation must be >=0 and < 1.");
    this.initialPieSliceOrientation = orientation;
    invalidateAllSlices();
  }

  /**
   * Sets the background color of the chart's legend.
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          the legend's background color, in a standard CSS color string
   *          format.
   * 
   * @see #getLegendBackgroundColor getLegendBackgroundColor
   * @see #DEFAULT_LEGEND_BACKGROUND_COLOR DEFAULT_LEGEND_BACKGROUND_COLOR
   */
  public void setLegendBackgroundColor(String cssColor) {
    chartDecorationsChanged = true;
    legendBackgroundColor = cssColor;
  }

  /**
   * Sets the border color of the chart's legend.
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          the color of the legend's border, in a standard CSS color string
   *          format, of the special GChart keyword
   *          <tt>TRANSPARENT_BORDER_COLOR</tt> for a transparent border.
   * 
   * 
   * @see #getLegendBorderColor getLegendBorderColor
   * @see #DEFAULT_LEGEND_BORDER_COLOR DEFAULT_LEGEND_BORDER_COLOR
   * @see #TRANSPARENT_BORDER_COLOR TRANSPARENT_BORDER_COLOR
   * 
   */
  public void setLegendBorderColor(String cssColor) {
    chartDecorationsChanged = true;
    legendBorderColor = cssColor;
  }

  /**
   * Sets the width of the chart legend's border.
   * 
   * @param width
   *          the width of the legend's border, in pixels
   * 
   * @see #getLegendBorderWidth getLegendBorderWidth
   * @see #DEFAULT_LEGEND_BORDER_WIDTH DEFAULT_LEGEND_BORDER_WIDTH
   */
  public void setLegendBorderWidth(int width) {
    chartDecorationsChanged = true;
    legendBorderWidth = width;
  }

  /**
   * Sets style of the border around the chart's legend (key).
   * 
   * <p>
   * 
   * <p>
   * 
   * @param borderStyle
   *          a CSS border style such as "solid", "dotted", "dashed", etc.
   * 
   * @see #getLegendBorderStyle getLegendBorderStyle
   * @see #setLegendBackgroundColor setLegendBackgroundColor
   * @see #setLegendBorderColor setLegendBorderColor
   * @see #DEFAULT_LEGEND_BORDER_STYLE DEFAULT_LEGEND_BORDER_STYLE
   */
  public void setLegendBorderStyle(String borderStyle) {
    chartDecorationsChanged = true;
    legendBorderStyle = borderStyle;
  }

  /**
   * Specifies the color of the legend's font. Default is
   * <tt>DEFAULT_FONT_COLOR</tt>.
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          color of the font used to display the labels in the legend.
   * 
   * @see #getLegendFontColor getLegendFontColor
   * @see #DEFAULT_FONT_COLOR DEFAULT_FONT_COLOR
   * 
   */
  public void setLegendFontColor(String cssColor) {
    chartDecorationsChanged = true;
    legendFontColor = cssColor;
  }

  /**
   * Specifies the CSS font size, in pixels, of text displayed in the chart's
   * legend (also know as a chart's key).
   * <p>
   * This size also governs the size of the symbol icon displayed in the legend.
   * <p>
   * Default is <tt>DEFAULT_LEGEND_FONTSIZE</tt>.
   * 
   * @param legendFontSize
   *          the font size of legend text
   * 
   * @see #getLegendFontSize getLegendFontSize
   * @see #DEFAULT_LEGEND_FONTSIZE DEFAULT_LEGEND_FONTSIZE
   * 
   */
  public void setLegendFontSize(int legendFontSize) {
    chartDecorationsChanged = true;
    this.legendFontSize = legendFontSize;
  }

  /**
   * Specifies the CSS font-family of the font used to render the legend's
   * labels.
   * <p>
   *
   * If <tt>null</tt> (the default) the legend will just use the font
   * family specified via <tt>GChart.setFontFamily</tt>.
   * 
   * @param cssFontFamily
   *          any valid CSS font-family, such as
   *          <tt>"serif", "sans-serif", "monospace", "cursive",
   * * "fantasy"</tt> or <tt>"Arial, sans-serif"</tt>.
   * 
   * @see #getLegendFontFamily getLegendFontFamily
   * @see #DEFAULT_FONT_FAMILY DEFAULT_FONT_FAMILY
   * 
   */
  public void setLegendFontFamily(String cssFontFamily) {
    chartDecorationsChanged = true;
    legendFontFamily = cssFontFamily;
  }

  /**
   * Specifies the cssStyle of the font used to render the legend's labels.
   * Default is <tt>DEFAULT_FONT_STYLE</tt>.
   * 
   * @param cssStyle
   *          any valid CSS font-style, namely, normal, italic, oblique, or
   *          inherit.
   * 
   * @see #getLegendFontStyle getLegendFontStyle
   * @see #DEFAULT_FONT_STYLE DEFAULT_FONT_STYLE
   */
  public void setLegendFontStyle(String cssStyle) {
    chartDecorationsChanged = true;
    legendFontStyle = cssStyle;
  }

  /**
   * Specifies the weight of the font used in the labels of the legend. Default
   * is <tt>DEFAULT_FONT_WEIGHT</tt>.
   * 
   * @param cssWeight
   *          a CSS font-weight specification, such as bold, bolder, normal,
   *          light, 100, 200, ... or 900.
   * 
   * @see #getLegendFontWeight getLegendFontWeight
   * @see #DEFAULT_FONT_WEIGHT DEFAULT_FONT_WEIGHT
   */
  public void setLegendFontWeight(String cssWeight) {
    chartDecorationsChanged = true;
    legendFontWeight = cssWeight;
  }

  /**
   * Specifies the position of the legend on the chart.
   * <p>
   * 
   * The default <tt>LegendLocation</tt> is
   * <tt>LegendLocation.OUTSIDE_RIGHT</tt>.  <p>
   * 
   * To shift the legend relative to these base locations, use
   * <tt>setLegendXShift</tt> and <tt>setLegendYShift</tt>.
   * 
   * @param legendLocation
   *          the postion of the legend on the chart
   * 
   * @see #setLegendXShift setLegendXShift
   * @see #setLegendYShift setLegendYShift
   * @see #setLegend setLegend
   * 
   */
  public void setLegendLocation(LegendLocation legendLocation) {
    this.legendLocation = legendLocation;
  }

  /**
   * This method allows you to replace GChart's legend with a Widget you create
   * to represent the chart legend.
   * <p>
   * 
   * Passing in <tt>null</tt> reverts to the default GChart-generated legend.
   * 
   * @param legend
   *          - a Widget that will be placed at the location specified by
   *          <tt>setLegendLocation</tt>, or <tt>null</tt> to let GChart create
   *          the legend Widget.
   * 
   * @see #getLegend getLegend
   * @see #setLegendLocation setLegendLocation
   * @see #setLegendXShift setLegendXShift
   * @see #setLegendYShift setLegendYShift
   * 
   */
  public void setLegend(Widget legend) {
    this.legend = legend;
  }

  /**
   * Specifies the number of pixels (along the x-axis) to move the legend from
   * its default, <tt>LegendLocation</tt>-defined, position. Negative values
   * move the legend in the negative x direction.
   * <p>
   * 
   * @param xShift
   *          number of pixels to move legend along the x axis from it's
   *          default, <tt>LegendLocation</tt>-defined, position.
   * 
   * @see #setLegendLocation setLegendLocation
   * @see #setLegendYShift setLegendYShift
   * 
   */
  public void setLegendXShift(int xShift) {
    this.legendXShift = xShift;
  }

  /**
   * Specifies the number of pixels (along the y-axis) to move the legend from
   * its default, <tt>LegendLocation</tt>-defined, position. Postive values
   * shift the legend towards the top of the page, negative values towards the
   * bottom.
   * <p>
   * 
   * @param yShift
   *          number of pixels to move legend along the y axis from it's
   *          default, <tt>LegendLocation</tt>-defined, position.
   * 
   * @see #setLegendLocation setLegendLocation
   * @see #setLegendXShift setLegendXShift
   */
  public void setLegendYShift(int yShift) {
    this.legendYShift = yShift;
  }

  /**
   * Sets the thickness (width) of the rectangular region at the right or left
   * of the chart allocated for the legend key.
   * <p>
   * 
   * This setting has no impact on chart layout if the legend key is not
   * visible, or if the legend key is placed inside the plot area of the chart,
   * since the legend takes up no space on the left or right sides of the chart
   * in such cases.
   * 
   * <p>
   * 
   * If the legend thickness is set to <tt>GChart.NAI</tt> (the default) GChart
   * uses an heuristic to set the legend thickness based on the number of
   * characters in each curve's legend label.
   * 
   * 
   * @param legendThickness
   *          the thickness (width) of the rectangle that contains the legend
   *          key, in pixels, or <tt>GChart.NAI</tt> to use a built-in heurstic
   *          to determine the legend width.
   * 
   * @see #getLegendThickness getLegendThickness
   * @see Curve#setLegendLabel setLegendLabel
   * @see Y2Axis#setAxisLabelThickness Y2Axis.setAxisLabelThickness
   * 
   */
  public void setLegendThickness(int legendThickness) {
    chartDecorationsChanged = true;
    this.legendThickness = legendThickness;
  }

  /**
   * Specifies if the legend is to be visible on this chart. Legends are visible
   * by default. However, a legend is only generated if at least one curve's
   * legend label has been specified.
   * 
   * @param isLegendVisible
   *          true to display the legend, false to hide it.
   * 
   * @see #isLegendVisible isLegendVisible
   * @see Curve#setLegendLabel setLegendLabel
   */
  public void setLegendVisible(boolean isLegendVisible) {
    chartDecorationsChanged = true;
    this.isLegendVisible = isLegendVisible;
  }

  /**
   * Sets the upper bound on the number of pixels any canvas widget used by
   * GChart can have.
   * <p>
   * 
   * If the number of pixels ever exceeds this amount, GChart will scale
   * the offending canvas down uniformly in width and height, until it
   * has around the specified number of pixels, and then move it as
   * close to being centered on the plot area as is consistent with
   * remaining within the original, unshrunken, clipping rectangle. This
   * strategy means that points furthest off the plot area could be
   * dropped due to this max pixel limit.
   * 
   * <p>
   * 
   * Because GChart allows you to draw outside of the decorated chart,
   * the size of the required canvas is not always known in advance. But
   * the use of very large sized canvas widgets can lead to serious
   * performance problems (including large memory use on some browsers,
   * e.g. Firefox). This method provides a simple way for developers to
   * avoid such problems without having to explicitly eliminate "far off
   * the chart" data points from their charts.
   * 
   * @param maxCanvasPixels
   *          the maximum number of pixels any single GChart canvas Widget
   *          (GChartCanvasLite) will contain.
   * 
   * @see #DEFAULT_MAX_CANVAS_PIXELS DEFAULT_MAX_CANVAS_PIXELS
   * @see #getMaxCanvasPixels getMaxCanvasPixels
   * 
   */
  public void setMaxCanvasPixels(double maxCanvasPixels) {
    this.maxCanvasPixels = maxCanvasPixels;
  }

  /**
   * By default, this property is <tt>false</tt>, which means that GChart will
   * retain no-longer-needed Image and Grid widgets (plus any user object
   * references associated with those widgets, such as those created via the
   * <tt>setAnnotationText</tt> and <tt>setAnnotationWidget</tt> methods)
   * between <tt>updates</tt> in the expectation that they may be needed by
   * future updates. This strategy often makes updates faster, because building
   * new Image and Grid elements "from scratch" is very expensive. However,
   * strictly speaking, GChart is holding onto memory it no longer needs to
   * render the chart <i>right now</i>--which would normally be considered a
   * memory leak if it were not being done deliberately.
   * <p>
   * 
   * If <tt>optimizeForMemory</tt> is set to <tt>true</tt>, GChart will (at the
   * very next <tt>update()</tt> call) free up any Image or Grid elements that
   * are no longer required to render the current chart. Should a chart's size
   * grow back to a former size, the subsequent update would be slower, though.
   * 
   * <p>
   * Charts that use exactly the same number of Image and Grid elements for each
   * update (for example a bar chart where the number of bars is fixed) will see
   * no impact on either memory use or update speeds by setting this parameter.
   * Charts that have a highly variable number of Image or Grid elements (for
   * example, a chart whose number of points varies randomly between 5 and 500)
   * may see a very large impact on speed (false is faster) or memory (true is
   * more compact).
   * <p>
   * 
   * The setting of this parameter never has any impact on the speed or memory
   * used on the <i>very first</i> chart update.
   * <p>
   * 
   * In one test using the future oil price simulation chart of GChart's live
   * demo (which has only small changes in the number of elements required to
   * render the chart between updates) setting this parameter to true made the
   * updates, on average, around 10% slower, but also reduced the memory
   * footprint by around 2%.
   * 
   * @param optimizeForMemory
   *          <tt>true</tt> to optimize updates to use less memory,
   *          <tt>false</tt> (the default) to optimize them to use less time.
   * 
   * @see #update update
   * 
   */
  public void setOptimizeForMemory(boolean optimizeForMemory) {
    this.optimizeForMemory = optimizeForMemory;
  }

  /**
   * Specifies the amount of padding to add just inside of the chart's border,
   * as a CSS padding specification string.
   * <p>
   * 
   * <p>
   * The default padding is <tt>USE_CSS</tt>.
   * 
   * <p>
   * 
   * @param cssPadding
   *          the width of the padding, as a CSS padding specification string
   *          (e.g. use "1px" to introduce a 1 pixel padding just between the
   *          chart' border and the chart itself)
   * 
   * @see #getPadding getPadding
   * @see #setBorderWidth setBorderWidth
   * @see #setBorderStyle(String) setBorderStyle
   * @see #setBorderColor(String) setBorderColor
   * @see #USE_CSS USE_CSS
   */
  public void setPadding(String cssPadding) {
    chartDecorationsChanged = true;
    padding = cssPadding;
  }

  /**
   * Specifies the background color of the area of the chart in which symbols
   * representing curve data are displayed
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          CSS color string defining the plot area's background color
   * 
   * @see #getPlotAreaBackgroundColor getPlotAreaBackgroundColor
   */
  public void setPlotAreaBackgroundColor(String cssColor) {
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setBackgroundColor(cssColor);
  }

  /**
   * Specifies the color of the border around the area of the chart in which
   * symbols representing curve data are displayed.
   * 
   * 
   * <p>
   * For more information on standard CSS color specifications see the
   * discussion in {@link Symbol#setBackgroundColor Symbol.setBackgroundColor}.
   * <p>
   * 
   * @param cssColor
   *          CSS color string defining the color of the plot area's border
   * 
   * @see #getPlotAreaBorderColor getPlotAreaBorderColor
   */
  public void setPlotAreaBorderColor(String cssColor) {
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setBorderColor(cssColor);
  }

  /**
   * Specifies the width of the border around the area of the chart in which
   * symbols representing curve data are displayed.
   * 
   * @param width
   *          the width, in pixels, of the border around the plot area
   * 
   * @see #getPlotAreaBorderWidth getPlotAreaBorderWidth
   */
  public void setPlotAreaBorderWidth(int width) {
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setBorderWidth(width);
  }

  /**
   * Sets style of the border around the chart's plot area (the rectangular area
   * where the curves are drawn).
   * 
   * <p>
   * 
   * <p>
   * 
   * @param borderStyle
   *          a CSS border style such as "solid", "dotted", "dashed", etc.
   * 
   * @see #getPlotAreaBorderStyle getPlotAreaBorderStyle
   * @see #setPlotAreaBackgroundColor setPlotAreaBackgroundColor
   * @see #setPlotAreaBorderColor setPlotAreaBorderColor
   */
  public void setPlotAreaBorderStyle(String borderStyle) {
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setBorderStyle(borderStyle);
  }

  /**
   * Sets the image URL that defines the background of the GChart plot area. The
   * GChart plot area is the rectangular region defined by the x and y axes of
   * the plot, but does not include those axes (or their ticks).
   * <p>
   * Note that by default, or if this URL is set to <tt>null</tt>, GChart will
   * use the URL returned by <tt>getBlankImageURL</tt>.
   * <p>
   * 
   * <small><b>Ideas/tips for using the plot area background URL:</b>
   * <blockquote>
   * <ol>
   * <li>It's often best to exactly match the width and height of the image with
   * the GChart plot area width and height (defined via (via
   * <tt>setChartSize</tt>). Otherwise, the image will be scaled up or down to
   * fit the plot area, which usually doesn't look that great.
   * 
   * <li>Note that since a Google Chart API url is just an image url to GChart,
   * you can easily use a Google Chart API url to define the background of an
   * otherwise client-side chart. For example, you might place a static 3-D pie
   * chart behind a rapidly changing client-side GChart bar chart.
   * 
   * <li>Note that this method's image will appear <i>behind</i> every gridline
   * and curve on the chart. To overlay images <i>on top of</i> the gridlines or
   * other curves, or even to place them outside of the plot area, use a
   * dedicated curve and its symbol's <tt>setImageURL</tt> method, or simply
   * embed such images within HTML-defined point annotations.
   * </ol>
   * 
   * </blockquote></small>
   * 
   * @see #getPlotAreaImageURL getPlotAreaImageURL
   * @see #setBlankImageURL setBlankImageURL
   * @see GChart.Symbol#setImageURL setImageURL
   * 
   * @param imageURL
   *          URL of the image used as the background of the plot area.
   * 
   */

  public void setPlotAreaImageURL(String imageURL) {
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setImageURL(imageURL);
  }

  /**
   * Defines a region around the perimeter of the currently visible area of the
   * chart that will be rendered, even though it cannot at present be seen (due
   * to clipping).
   * <p>
   * 
   * By default, this factor is <tt>0.0</tt> and GChart is free to skip the
   * rendering any parts of the chart that would have been clipped off anyway.
   * However, special applications, such as panning, may find it advantageous to
   * fully render areas adjacent to the clipping window, so that these
   * pre-rendered areas can then be quickly shifted into view via the
   * <tt>setXShift</tt> and <tt>setYShift</tt> methods.
   * <p>
   * 
   * The width and height of the clipping window (typically the plot area) are
   * expanded by the specified fraction along each edge. For example, a render
   * padding factor of <tt>1.0</tt> expands the rendered height by a factor of
   * 300% (100% extra above, and 100% extra below) and the width by the same
   * factor (100% extra to the left, and 100% extra to the right).
   * <p>
   * 
   * @param renderPaddingFactor
   *          the fractional amount to expand to the rendering window around the
   *          perimeter of the clipping window.
   * 
   * @see Curve#setXShift setXShift
   * @see Curve#setYShift setYShift
   * @see #getRenderPaddingFactor getRenderPaddingFactor
   * 
   */
  public void setRenderPaddingFactor(double renderPaddingFactor) {
    this.renderPaddingFactor = renderPaddingFactor;
  }

  /**
   * @deprecated
   * 
   *             Equivalent to <tt>setClipToPlotArea(!showOffChartPoints)</tt> .
   *             Use that method instead.
   *             <p>
   * 
   *             <small> As of GChart 2.5, the clip-to-plot-area algorithm no
   *             longer drops the entire symbol if it's x,y coordinates are
   *             outside of the plot area; instead, it clips them off in the
   *             traditional "<tt>overflow: hidden</tt>" manner. Though unlikely
   *             you would need to, there is no easy way to recreate the
   *             previous behavior.
   *             <p>
   * 
   *             This change was made so that both rectangular HTML and
   *             continuous, canvas-rendered chart elements would be clipped in
   *             a consistent and sensible way. </small>
   * 
   * @see #setClipToPlotArea setClipToPlotArea
   * 
   */
  public void setShowOffChartPoints(boolean showOffChartPoints) {
    setClipToPlotArea(!showOffChartPoints);
  }

  /**
   * @deprecated
   * 
   *             Equivalent to setClipToDecoratedChart(!showOffDecoratedChart),
   *             please use that method instead.
   * 
   * @see #setClipToDecoratedChart setClipToDecoratedChart
   */
  public void setShowOffDecoratedChartGlyphs(
      boolean showOffDecoratedChartGlyphs) {
    setClipToDecoratedChart(!showOffDecoratedChartGlyphs);
  }

  /**
   * Returns the curve that the mouse "brush" is currently "touching" (the
   * so-called "hovered over" point), or <tt>null</tt> if none.
   * <p>
   * 
   * Convenience method equivalent to (when the touched point is not
   * <tt>null</tt>) <tt>getTouchedPoint().getParent()</tt>. See
   * <tt>getTouchedPoint</tt> for full details.
   * <p>
   * 
   * 
   * See the <tt>setBrushHeight</tt> method for the rules GChart uses to
   * determine the currently touched point.
   * <p>
   * 
   * 
   * @return a reference to the curve that the mouse "brush" is currently
   *         "touching".
   * 
   * @see #getTouchedPoint getTouchedPoint
   * @see Symbol#setBrushHeight setBrushHeight
   * @see Symbol#setHoverSelectionSymbolType setHoverSelectionSymbolType
   * 
   */
  public Curve getTouchedCurve() {
    Curve result = null;
    if (null != getTouchedPoint())
      result = getTouchedPoint().getParent();
    return result;
  }

  /**
   * Returns the point that the mouse "brush" is currently "touching" (the
   * so-called "hovered over" point), or <tt>null</tt> if none.
   * 
   * <p>
   * <small> <i>Fine-print:</i> If the chart clicked on needs an update, this
   * method returns the touched point <i>as of the last time the chart's
   * in-browser (DOM) display was up-to-date</i>. If you don't assure that your
   * chart's DOM display is up-to-date via other means (e.g. updating right
   * after you change its specifications) a quick check with the
   * <tt>isUpdateNeeded</tt> method and a subsequent <tt>update</tt> before
   * accessing the touched point can be a good strategy.
   * <p>
   * </small>
   * 
   * 
   * See the <tt>setBrushHeight</tt> method for the rules GChart uses to
   * determine the currently touched point.
   * <p>
   * 
   * <small> <i>Warning:</i> The currently touched point, on FF2 (but not in
   * IE7) can be changed (or set to <tt>null</tt>) by invoking
   * <tt>Window.alert</tt>. Though I originally expected that such a modal alert
   * box would "eat" all mouse events (and it does just that in IE7) in FF2 (and
   * possibly other browsers) some mouse events on the alert box are also passed
   * on up to the GChart. It's best for applications that need to "lock on" to
   * the <i>initially</i> touched point to grab a reference to the touched point
   * <i>before</i> performing any activity that allows the user to interact with
   * the browser in ways that could possibly generate GChart-visible mouse
   * events. </small>
   * <p>
   * 
   * @return a reference to the point that the mouse "brush" is currently
   *         "touching".
   * 
   * @see #getTouchedCurve getTouchedCurve
   * @see #touch touch
   * @see Symbol#setBrushHeight setBrushHeight
   * @see Symbol#setHoverSelectionSymbolType setHoverSelectionSymbolType
   * @see #isUpdateNeeded isUpdateNeeded
   * @see #update update
   * @see Axis#getMouseCoordinate getMouseCoordinate
   * @see Axis#clientToModel clientToModel
   * @see Axis#modelToClient modelToClient
   * @see Axis#pixelToModel pixelToModel
   * @see Axis#modelToPixel modelToPixel
   * 
   */
  public Curve.Point getTouchedPoint() {
    return plotPanel.touchedPoint;
  }

  /**
   * Sets the number of pixels, in the horizontal dimension, available for curve
   * display. Note that this curve display area does <i>not</i> include the axes
   * themselves, their tick marks, their labels, etc.
   * 
   * <p>
   * 
   * <i>Note</i>: Most modern display devices use "square" pixels, that is,
   * pixels whose width and height are the same. GChart tacitly assumes square
   * pixels in many of its default settings.
   * 
   * 
   * @param xChartSize
   *          the number of x-pixels in the chart region used for curve display.
   * 
   * @see #getXChartSize getXChartSize
   * @see #getXChartSizeDecorated getXChartSizeDecorated
   * @see #setYChartSize setYChartSize
   * 
   */
  public void setXChartSize(int xChartSize) {
    chartDecorationsChanged = true;
    this.xChartSize = xChartSize;
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setWidth(xChartSize);
  }

  /**
   * Sets the number of pixels, in the vertical dimension, available for curve
   * display. Note that this curve display region of the chart does <i>not</i>
   * include the axes themselves, their tick marks, labels, etc.
   * 
   * <p>
   * 
   * <i>Note</i>: Most modern display devices use "square" pixels, that is,
   * pixels whose width and height are the same. GChart tacitly assumes square
   * pixels in many of its default settings.
   * 
   * @param yChartSize
   *          the number of y-pixels in the chart region used for curve display.
   * 
   * @see #getYChartSize getYChartSize
   * @see #getYChartSizeDecorated getYChartSizeDecorated
   * @see #setXChartSize setXChartSize
   * 
   */
  public void setYChartSize(int yChartSize) {
    chartDecorationsChanged = true;
    this.yChartSize = yChartSize;
    Curve c = getSystemCurve(PLOTAREA_ID);
    c.getSymbol().setHeight(yChartSize);
  }

/**
   * Simulates the user "touching" a point with the mouse, by
   * performing those operations that occur when the user "hovers
   * over" the specified point. In detail, this method does the
   * following:<p>
   * 
   * <ol>
   *
   *  <li> The specified point is made the currently "touched point"
   *  (this is the reference returned by <tt>getTouchedPoint</tt>). <p>
   *
   *  <li>If the previously touched point had a hover widget,
   *  that hover widget's <tt>hoverCleanup</tt> method is called.<p>
   *
   *  <li>If the touched point has an associated hover widget, that
   *  widget's <tt>hoverUpdate</tt> method is called.<p>
   *
   *  <li> Any hover selection feedback or hover annotation on
   *  any previously touched point is removed.<p>
   *
   *  <li>Any hover annotation for the newly touched point is
   *  displayed as per the various hover annotation related
   *  specifications (e.g.  <tt>setHoverLocation</tt>) associated with
   *  the symbol used to render the point.<p>
   *
   *  <li> Any selection feedback for the newly touched point is
   *  displayed in accord with the hover selection feedback
   *  specificiations (e.g.  <tt>setHoverSelectionBorderColor</tt>)
   *  associated with the symbol used to render the point.<p>
   *  
   *  </ol>
   *
   * Using <tt>null</tt> as the point to touch simulates
   * the user moving the mouse into a region where it is not
   * touching any point (for example, off the chart entirely).
   * <p>
   * 
   * Note that, as with all chart specification changes, you must
   * invoke <tt>update</tt> before the point selection and other
   * changes associated with this method will appear on the chart.
   * <p>
   *
   * <i>Tip:</i> The touched point can sometimes be used in lieu of a
   * point selection capability (which GChart lacks). For example, a
   * dialog box that allowed users to choose data points by their
   * names could "touch" the point associated with a user-selected
   * name in order to highlight it on the chart.
   * 
   * @param pointToTouch this method will perform appropriate
   *   operations (as described above) in order to simulate the user
   *   "touching" this point with their mouse.
   *
   * @see #getTouchedPoint getTouchedPoint
   * @see #getTouchedCurve getTouchedCurve
   * @see HoverUpdateable#hoverUpdate hoverUpdate
   * @see HoverUpdateable#hoverCleanup hoverCleanup
   * @see Symbol#setHoverWidget setHoverWidget
   * @see Symbol#setHoverLocation setHoverLocation
   * @see Symbol#setHoverSelectionBorderColor
   * setHoverSelectionBorderColor
   * @see Axis#getMouseCoordinate getMouseCoordinate
   * @see Axis#clientToModel clientToModel
   * @see Axis#modelToClient modelToClient
   * @see Axis#pixelToModel pixelToModel
   * @see Axis#modelToPixel modelToPixel
   * 
   */
  public void touch(Curve.Point pointToTouch) {
    plotPanel.touch(pointToTouch);
  }

  /**
   * Builds a chart that reflects current user-specified chart specs (curve
   * data, symbol choices, etc.)
   * <p>
   * 
   * Before any of the chart specifications of the other methods of this class
   * will actually be visible on the chart, you must call this method.
   * <p>
   * 
   * Typically, for efficiency, you would call this method only after you had
   * made all of the desired chart specifications via the other methods.
   * 
   * <p>
   * 
   * By default, updates are optimized for speed, and this can end up wasting
   * (usually not too much, though there are exceptions) memory. To optimize for
   * memory instead, use the <tt>setOptimizeForMemory</tt> method.
   * <p>
   * 
   * For a discussion of Client-side GChart update times and how minimize them,
   * see <a href="{@docRoot}/com/googlecode/gchart/client/doc-files/tipsformakingupdatesfaster.html">
   * Tips for Making Client-side GChart Updates Faster</a>.
   * <p>
   * 
   * <i>Note</i> Hover feedback is disabled whenever the currently rendered
   * chart does not match current chart specs, that is, whenever
   * <tt>isUpdateNeeded</tt> returns <tt>true</tt>. Thus, to assure that hover
   * feedback remains operational once your code returns control to the browser,
   * be sure to call <tt>update()</tt> after making a series of changes to your
   * chart's properties.
   * <p>
   * 
   * Understanding how <tt>update</tt> impacts visibility and size:
   * <p>
   * <blockquote> <small> Due to an implementation-related limitation,
   * <tt>visibility: hidden</tt> won't hide a GChart (<tt>update</tt>
   * commandeers the visibility attribute). Instead use <tt>display: none</tt>
   * or, equivalently:
   * 
   * <pre>
   * myGChart.setVisible(false);
   * </pre>
   * 
   * If you need to avoid <tt>display: none</tt> (it can change page layout),
   * you can also hide a GChart via lines such as:
   * 
   * <pre>
   * DOM.setStyleAttribute(myGChart.getElement(), &quot;overflow&quot;, &quot;hidden&quot;);
   * myGChart.setPixelSize(0, 0);
   * </pre>
   * 
   * This later approach gives you the option of leaving the top corner of the
   * GChart visible, etc. Note that, with the next <tt>update</tt>, GChart will
   * overwrite your size (based on the GChart properties that define the size of
   * the the chart, such as <tt>setChartSize</tt> and <tt>set*Thickness</tt>)
   * and your <tt>overflow:hidden</tt> (based on
   * <tt>setClipToDecoratedChart</tt>) specifications. To preserve them (or in
   * other special cases) you may need to apply such settings to an enclosing
   * parent element.
   * 
   * </small> </blockquote>
   * 
   * 
   * @param option
   *          determines how the touched (or "hovered over") point changes as a
   *          result of this update. See <tt>TouchedPointUpdateOption</tt> for
   *          the available choices.
   * 
   * @see TouchedPointUpdateOption TouchedPointUpdateOption
   * @see #setOptimizeForMemory setOptimizeForMemory
   * @see #isUpdateNeeded isUpdateNeeded
   * 
   */
  public void update(TouchedPointUpdateOption option) {

    /*
     * This method defines each curve's default pie slice orientations, and also
     * separates each curve's points into the vertically or horizontally banded
     * bins, that GChart needs to perform the hit testing that allows it to
     * emulate "touching" points with the mouse. <p>
     * 
     * Therefore, this line must come first.
     */
    assembleChart();

    if (TouchedPointUpdateOption.TOUCHED_POINT_LOCKED == option) {
      // must re-touch (point position, hover-config can change)
      plotPanel.touch(plotPanel.touchedPoint);
    } else if (TouchedPointUpdateOption.TOUCHED_POINT_CLEARED == option) {
      // if needed, will clear out touched point & related feedback
      plotPanel.touch(null);
    } else if (TouchedPointUpdateOption.TOUCHED_POINT_UPDATED == option) {
      // re-determine which point is underneath the mouse now...
      plotPanel.retouchObjectAtMousePosition();
    }

    /*
     * Because only the hover feedback curves have changed since the
     * first call above, this second call only has to update these hover
     * feedback curves (so it's not like we are really assembling the
     * chart twice)
     */
    assembleChart();

  }

  /**
   * Updates the chart, using an appropriate default touched point update
   * option, depending on if hover touching is enabled or not.
   * <p>
   * 
   * A convenience method equivalent to:
   * <p>
   * 
   * <pre>
   * if (getHoverTouchingEnabled())
   *   update(TouchedPointUpdateOption.TOUCHED_POINT_UPDATED);
   * else
   *   update(TouchedPointUpdateOption.TOUCHED_POINT_LOCKED);
   * </pre>
   * 
   * 
   * @see #update(TouchedPointUpdateOption) update(TouchedPointUpdateOption)
   * @see #setHoverTouchingEnabled setHoverTouchingEnabled
   * 
   */
  public void update() {
    if (getHoverTouchingEnabled())
      update(TouchedPointUpdateOption.TOUCHED_POINT_UPDATED);
    else
      update(TouchedPointUpdateOption.TOUCHED_POINT_LOCKED);
  }

  // constructs the chart within the chart panel from current specs
  private void assembleChart() {

	  boolean yAxesLimitsChanged = false;
	  for(int i = 0; i < Y_AXES_MAX; i++){
		  if(yAxis[i].limitsChanged())
			  yAxesLimitsChanged = true;
	  }
    if (chartDecorationsChanged || xAxis.limitsChanged()
        || yAxesLimitsChanged) {
      plotPanel.reset(xChartSize, yChartSize,
          xAxis, yAxis);
      GChart.setFontFamily(this, getFontFamily());
      GChart.setBackgroundColor(this, getBackgroundColor());
      GChart.setBorderColor(this, getBorderColor());
      GChart.setBorderStyle(this, getBorderStyle());
      GChart.setBorderWidth(this, getBorderWidth());
      GChart.setPadding(this, getPadding());
      GChart.setOverflow(this, getClipToDecoratedChart() ? "hidden"
          : "visible");

      this.setPixelSize(plotPanel.getXChartSizeDecoratedQuickly(),
          plotPanel.getYChartSizeDecoratedQuickly());
      updateDecorations(plotPanel.getXChartSizeDecoratedQuickly());
      xAxis.rememberLimits();
      for(int i = 0; i < Y_AXES_MAX; i++){
    	  yAxis[i].rememberLimits();
      }
      invalidateEveryCurve();
      chartDecorationsChanged = false;
    }
    // actually renders chart, including internal curves used
    // to represent the decorations (title, axis labels, etc.)
    realizePlotPanel();

    // To avoid order-of-magnitude FF2 performance hit on busy pages,
    // first time, must add plotPanel only AFTER building chart
    if (plotPanel != chartPanel.getWidget()) {
      chartPanel.add(plotPanel);
      /*
       * Due to how GChart plays around with visible elements contained inside
       * hidden elements to align it's labels properly, if we allowed top level
       * <tt>visibility:hidden</tt> the result would be that everything
       * <i>except</i> annotations would be invisible. <p>
       * 
       * We can prevent such weird behavior by setting
       * <tt>visibility:visible</tt> on the top level element; this setting
       * effectively short-circuits any top level visibility setting the user
       * may have made. <p>
       * 
       * Users must either use <tt>display:none</tt> (as the Widget method
       * <tt>setVisible</tt> does) or create an enclosing 0-sized div with
       * <tt>overflow:hidden</tt>) to hide a GChart. <p>
       */
      DOM.setStyleAttribute(getElement(), "visibility", "visible");
    } else {
      /*
       * Without these 2 lines IE7 won't repaint GChart's annotations. The lines
       * are not needed in FF2; an IE7 bug is suspected.<p>
       * 
       * I got this workaround from <a href= "http://examples.roughian.com">Ian
       * Bambury</a> as part of <a href=
       * "http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/4c54d8b4aea7f98b/6efd1ab4e5fc0e7b?#6efd1ab4e5fc0e7b"
       * > this discussion on the GWT forum</a>. <p>
       * 
       * (Note comment regarding need for explicit visibility above).
       */
      DOM.setStyleAttribute(getElement(), "visibility", "hidden");
      DOM.setStyleAttribute(getElement(), "visibility", "visible");
    }
  }

  // create a Grid representing the chart legend.
  private Grid createLegend(PlotPanel pp) {
    Grid result = new Grid(getNVisibleCurvesOnLegend(), 2);
    int iVisible = 0;
    /*
     * Simply eliminating the border entirely is a valid transparency emulation
     * for the legend (no positional shifting is needed as is needed for the
     * images used to draw the main chart's curves) because the legend is always
     * positioned by its center point, and the border extends around the entire
     * legend key, so removing it does not result in any change to the legend
     * key's center position. <p>
     * 
     * If multiple legend locations (beyond the current "always centered in a
     * band along the right edge" option) were ever supported, appropriate
     * positional shifts would then have to be introduced to emulate transparent
     * borders.
     */
    GChart.setBorderWidth(result,
       TRANSPARENT_BORDER_COLOR == getLegendBorderColor() ? 0 : 
       Math.abs(getLegendBorderWidth()));
    GChart.setBorderColor(
        result,
        TRANSPARENT_BORDER_COLOR == getLegendBorderColor() ? "transparent"
        : getLegendBorderColor());
    GChart.setBorderStyle(result, getLegendBorderStyle());
    GChart.setBackgroundColor(result, getLegendBackgroundColor());
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      if (c.isVisible() && c.getLegendLabel() != null) {
        double symBorderFraction = 
           c.getSymbol().getBorderWidth() / 
           Math.max(Math.max(1.0, c.getSymbol().getFillThickness()), 
                    Math.max(c.getSymbol().getWidth(pp), 
                             c.getSymbol().getHeight(pp,c.getIndexForAxisID(c.getYAxis()))));
        Image icon = c.getSymbol().getSymbolType().createIconImage(
            c.getSymbol(), getLegendFontSize(), symBorderFraction);

        result.setWidget(iVisible, 0, icon);
        result.getCellFormatter().setAlignment(iVisible, 0,
            HasHorizontalAlignment.ALIGN_CENTER,
            HasVerticalAlignment.ALIGN_MIDDLE);

        HTML label = new HTML(c.getLegendLabel());
        GChart.setFontWeight(label, getLegendFontWeight());
        GChart.setFontFamily(label, (null == getLegendFontFamily()) ?
                             getFontFamily() : getLegendFontFamily());
        GChart.setFontStyle(label, getLegendFontStyle());
        GChart.setColor(label, getLegendFontColor());
        GChart.setFontSize(label, getLegendFontSize());

        result.setWidget(iVisible, 1, label);
        result.getCellFormatter().setAlignment(iVisible, 1,
            HasHorizontalAlignment.ALIGN_LEFT,
            HasVerticalAlignment.ALIGN_MIDDLE);

        iVisible++;
      }
    }
    return result;
  }

  // returns char-width-based default legend thickness
  private int getDefaultLegendThickness() {
    final int EXTRA_WIDTH = 5; // allow for padding & symbol
    int maxLen = 0;
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      if (c.isVisible() && null != c.getLegendLabel()) {
        maxLen = Math.max(maxLen, htmlWidth(c.getLegendLabel()));
      }
    }
    int result = (int) ((maxLen + EXTRA_WIDTH) * getLegendFontSize() * TICK_CHARWIDTH_TO_FONTSIZE_LOWERBOUND);
    return result;
  }

  private int getNVisibleCurvesOnLegend() {
    int result = 0;
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      if (getSystemCurve(i).isVisible()
          && getSystemCurve(i).getLegendLabel() != null)
        result++;
    }
    return result;
  }

  // Defines a default curve border color when curves first created
  private void setDefaultBorderColor(Curve curve, int index) {
    curve.getSymbol().setBorderColor(
        defaultSymbolBorderColors[index
        % defaultSymbolBorderColors.length]);
  }

  // Is the symbol type one of the special ANCHOR_MOUSE types,
  // whose position varies with the mouse cursor location?
  private boolean isMouseAnchored(SymbolType symbolType) {
    boolean result = false;
    if (SymbolType.ANCHOR_MOUSE == symbolType
        || SymbolType.ANCHOR_MOUSE_SNAP_TO_X == symbolType
        || SymbolType.ANCHOR_MOUSE_SNAP_TO_Y == symbolType)
      result = true;
    return result;
  }

  // renders the curve in the plot panel
  private void realizeCurve(Curve c) {
	  if(c.indexOf == 31 || c.indexOf == 31 || c.indexOf == 31 || c.indexOf == 31){
		  System.out.println();
	  }
    int internalIndex = 0;
    int rpIndex = 0;
    GraphicsRenderingPanel grp = null;
    AnnotationRenderingPanel arp = null;

    if (!c.isValidated() || c.xOrYShiftChanged) {
      internalIndex = getInternalCurveIndex(c);
      rpIndex = getRenderingPanelIndex(internalIndex);
      grp = plotPanel.getGraphicsRenderingPanel(rpIndex);
      arp = plotPanel.getAnnotationRenderingPanel(rpIndex);
    }

    if (!c.isValidated()) {
      currentCurveData = c.getCurveData();
      if (PlotPanel.DECORATIVE_RENDERING_PANEL_INDEX == rpIndex) {
        // background panel only gets initialized for first curve
        if (0 == internalIndex) {
          // background panel never uses canvas
          grp.beginRendering(null);
          arp.beginRendering();
        }
        c.setWasCanvasRendered(false);
      } else if (0 == c.getSymbol().getFillSpacing() && // continuous fill
          0 < c.getSymbol().getFillThickness() && // non-empty fill
          null != getCanvasFactory() && // canvas available
          c.isVisible()) {
        grp.maybeAddCanvas();
        Rectangle canvasRegion = c.getContainingRectangle(plotPanel);
        grp.beginRendering(canvasRegion);
        arp.beginRendering();
        c.setWasCanvasRendered(true);
      } else { // does not use canvas, or it is invisible
        grp.beginRendering(null);
        arp.beginRendering();
        c.setWasCanvasRendered(false);
      }

      if (c.isVisible()) {
        // Separate points into vertical/horizontal band-bins provided
        // 1) it is not a system curve and 2) it is not of a type whose
        // position follows the mouse (and thus has no fixed location
        // suitable for banding) and 3) at least one kind of hover
        // feedback is being provided for the curve.
        if (getCurveIndex(c) >= 0
            && !isMouseAnchored(c.getSymbol().getSymbolType())
            && (c.getSymbol().getHoverSelectionEnabled() || c
            .getSymbol().getHoverAnnotationEnabled()))
          c.bandSeparatePoints();
        else
          // hit test banding calcs unneeded; skip them for speed.
          c.clearBandList();

        // Note: these lines must come AFTER band separation lines above
        int nPoints = c.getNPoints();
        for (int j = 0; j < nPoints; j++) {
          c.realizePoint(plotPanel, grp, arp, j, c.indexOf);
        }
      }
      // only end background panel rendering w last background curve
      if (PlotPanel.DECORATIVE_RENDERING_PANEL_INDEX != rpIndex
          || internalIndex == N_PRE_SYSTEM_CURVES - 1) {
        grp.endRendering();
        arp.endRendering();
      }
      // else it's a background panel curve, and not the last one
      c.isValidated = true;
      currentCurveData = null;
    }

    if (c.xOrYShiftChanged) {
      plotPanel.setRenderingPosition(grp, arp, c.getXShift(), c.getYShift());
      c.xOrYShiftChanged = false;
    }

  }

  // marks every curve, including system curves, as needing an update
  private void invalidateEveryCurve() {
    for (int i = 0; i < curves.size(); i++) {
      curves.get(i).invalidate();
    }
  }

  // marks every developer-accessible curve as needing an update
  private void invalidateAccessibleCurves() {
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      getSystemCurve(i).invalidate();
    }
  }

  // invalidates every curve that has a pie slice type
  void invalidateAllSlices() {
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      if (c.getSymbol().getSymbolType() instanceof SymbolType.PieSliceSymbolType)
        c.invalidate();
    }
  }

  // Invalidates every pie slice curve whose orientation could
  // depend on the orientation of the given curve
  void invalidateDependentSlices(int iFirstCurve) {
    // only user defined curve can have slice dependency relationships
    if (isSystemCurveIndex(iFirstCurve))
      return;
    int nCurves = getNCurves();
    for (int i = iFirstCurve; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      if (c.getSymbol().getSymbolType() instanceof SymbolType.PieSliceSymbolType)
        c.invalidate();
      else if (i == iFirstCurve) // if first curve isn't a slice,
        break; // there are no dependent slices
    }
  }

  // Defines the default pie slice orientations for every pie-slice curve
  private void setDefaultPieSliceOrientations() {
    setLastPieSliceOrientation(getInitialPieSliceOrientation());
    int nCurves = getNCurves();
    for (int i = 0; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      // keep track of default next orientation for pie slices
      // (must do this even if we don't have to redraw slice)
      if (c.getSymbol().getSymbolType() instanceof SymbolType.PieSliceSymbolType) {
        c.getSymbol().setDefaultPieSliceOrientation(
            getLastPieSliceOrientation());
        setLastPieSliceOrientation(c.getSymbol().getDecodedPieSliceOrientation()
            + c.getSymbol().getPieSliceSize());
      }
    }
  }

  private void realizePlotPanel() {

    setDefaultPieSliceOrientations();
    /*
     * Render both system curves (those with negative ids that are used to
     * render title, ticks, etc.) and ordinary curves.
     */
    int nCurves = getNCurves();
    for (int i = -N_SYSTEM_CURVES; i < nCurves; i++) {
      Curve c = getSystemCurve(i);
      realizeCurve(c);
    }

  }

  /*
   * Returns true if the rendering panel index is associated with one of the
   * internal, hover-feedback curves. <p>
   * 
   * This method relies on the fact that rendering panels appear in this order:
   * <p>
   * 
   * <ol> <li> a single rp that renders all chart decorations <li> getNCurves()
   * rps (1 for each developer-defined curve) <li> the two rendering panels
   * associated with the two system-defined hover feedback curves </ol>
   */
  boolean isHoverFeedbackRenderingPanel(int rpIndex) {
    boolean result = rpIndex > getNCurves();
    return result;
  }

  /*
   * This code works around a bug in GWTCanvas that can cause (in IE) previously
   * rendered VML elements to have their fill and stroke color, and stroke
   * thickness properties revert to some sort of defaults (I saw white, black,
   * and 1px in my tests) when the canvas is re-inserted into the DOM.
   * 
   * See TestGChart55.java and TestGChart55a.java for more info on the GWTCanvas
   * bug that makes this code neccessary.
   * 
   * TODO: Implement technique of GWTCanvasIssue293.patch to override
   * removeFromParent and store/restore innerHTML as a more efficient workaround
   * for this problem. If a new GWTCanvas is released, you could remove this
   * workaround altogether.
   */

  // avoids inefficiency of re-rendering in most common case
  private boolean wasUnloaded = false;

  @Override
  protected void onUnload() {
    super.onUnload();
    wasUnloaded = true;
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    if (wasUnloaded && plotPanel.getRenderingPanelCount() > 0) {
      boolean isUpToDate = !isUpdateNeeded();
      int nCurves = getNCurves();
      for (int i = 0; i < nCurves; i++) {
        Curve c = getCurve(i);
        if (c.isCanvasRendered()) {
          c.invalidate();
          if (isUpToDate)
            realizeCurve(c);
          // else since chart needs update, presume they will
          // update later, no need to auto-patch things up
          // (and simple patch-rerender won't work anyway).
        }
      }
    }
    // else never inserted/rendered; skip patchup-rerendering
  }

} // end of class GChart
