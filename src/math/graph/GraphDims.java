package math.graph;

import java.util.LinkedList;

public class GraphDims implements GraphDimsPublisher {

   private double                          minX;
   private double                          maxX;
   private double                          minY;
   private double                          maxY;

   private LinkedList<GraphDimsSubscriber> subscribers;

   public GraphDims(double minX, double maxX, double minY, double maxY) {
      this.subscribers = new LinkedList<GraphDimsSubscriber>();
      set(minX, maxX, minY, maxY);
   }

   public GraphDims(GraphDims dims) {
      this(dims.getMinX(), dims.getMaxX(), dims.getMinY(), dims.getMaxY());
   }

   public void set(double minX, double maxX, double minY, double maxY) {
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;
      publish();
   }

   public double getMinX() {
      return minX;
   }

   public void setMinX(double newMinX) {
      this.minX = newMinX;
      publish();
   }

   public double getMaxX() {
      return maxX;
   }

   public void setMaxX(double newMaxX) {
      this.maxX = newMaxX;
      publish();
   }

   public double getMinY() {
      return minY;
   }

   public void setMinY(double newMinY) {
      this.minY = newMinY;
      publish();
   }

   public double getMaxY() {
      return maxY;
   }

   public void setMaxY(double newMaxY) {
      this.maxY = newMaxY;
      publish();
   }

   @Override
   public void addSubscriber(GraphDimsSubscriber s) {
      if (s != null)
         subscribers.add(s);
   }

   @Override
   public void removeSubscriber(GraphDimsSubscriber s) {
      subscribers.remove(s);
   }

   private void publish() {
      for (GraphDimsSubscriber s : subscribers)
         s.updateGraphDims();
   }

   public GraphDims copy() {
      return new GraphDims(this);
   }

}
