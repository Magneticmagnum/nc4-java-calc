package draw;

public interface GraphDimsPublisher {
	
	public void addSubscriber(GraphDimsSubscriber s);
	
	public void removeSubscriber(GraphDimsSubscriber s);

}
