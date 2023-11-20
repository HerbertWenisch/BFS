
/**
 * @author (Herbert Wenisch)
 * @version (08-18-23)
 */

// Beispielgraf: Graph SKÄN

public class Beispielgraf {
    private Graph graph;
    
    public Beispielgraf(){
        graph = new Graph(14);
        fillUpNodes();
        fillUpEdges();
    }
    
    public Graph getGraph(){
        return graph;
    }
    
    private void fillUpNodes(){
        graph.addNode("Go");
        graph.addNode("A"); 
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("H");
        graph.addNode("I");
    }
    
    private void fillUpEdges(){
        graph.addEdge("Go","A");
        graph.addEdge("Go","B");
        graph.addEdge("Go","C");
        graph.addEdge( "A","B");
        graph.addEdge( "A","D");
        graph.addEdge( "A","E");
        graph.addEdge( "B","C");
        graph.addEdge( "B","F");
        graph.addEdge( "C","G");
        graph.addEdge( "D","E");
        graph.addEdge( "D","H");
        graph.addEdge( "E","F");
        graph.addEdge( "F","G");
        graph.addEdge( "F","I");
        graph.addEdge( "H","I");
    }
}
