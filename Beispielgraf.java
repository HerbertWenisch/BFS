
/**
 * @author (Herbert Wenisch)
 * @version (08-18-23)
 */

// unser Beispielgraf: Haus des Nikolaus

public class Beispielgraf {
    private Graph graph;
    
    public Beispielgraf(){
        graph = new Graph(9);
        fillUpNodes();
        fillUpEdges();
    }
    
    public Graph getGraph(){
        return graph;
    }
    
    private void fillUpNodes(){
        graph.addNode("A"); 
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
    }
    
    private void fillUpEdges(){
        graph.addEdge( "B","A");
        graph.addEdge( "C","A");
        graph.addEdge( "C","B");
        graph.addEdge( "E","B");
        graph.addEdge( "D","B");
        graph.addEdge( "D","E");
        graph.addEdge( "E","C");
        graph.addEdge( "D","C");
    }
}
