package cs3500.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * Element to represent a rectangle.
 */
public class Rectangle extends AbstractElement {
  private double height;
  private double width;

  /**
   * Constructor for a rectangle.
   * @param id element id
   * @param c color
   * @param p posn
   * @param height height
   * @param width width
   */
  public Rectangle(String id, Color c, Posn p, double height, double width, double angle) {
    super(c, p, id, angle);
    this.height = height;
    this.width = width;
  }

  /**
   * Gets the height of an rectangle.
   * @return height
   */
  private double getHeight() {
    return this.height;
  }

  /**
   * Gets the width of an rectangle.
   * @return width
   */
  private double getWidth() {
    return this.width;
  }

  /**
   * Returns an array of doubles of length 2, in the following order: Height, Width.
   * @return  the array of dimensions.
   */
  @Override
  public double[] getDimensions() {
    return new double[]{getHeight(), getWidth()};
  }

  @Override
  public void setWidth(double w) {
    this.width = w;
  }

  @Override
  public void setHeight(double h) {
    this.height = h;
  }

  @Override
  public String getType() {
    return "rectangle";
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Rectangle)) {
      return false;
    }
    return (this.getID().equals(((Rectangle) that).getID()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getID());
  }

  @Override
  public void paint(Graphics2D g2d) {
    super.paint(g2d);
    Rectangle2D rect = new Rectangle2D.Double(getPosn().getX(), getPosn().getY(), width, height);
    AffineTransform a = new AffineTransform();
    a.rotate(Math.toRadians(angle % 360), rect.getX() + rect.getWidth() / 2,
            rect.getY() + rect.getHeight() / 2 );
    Shape rotatedRect = a.createTransformedShape(rect);
    g2d.fill(rotatedRect);
    g2d.draw(rotatedRect);
  }
}
