/**
 * @author (Herbert Wenisch)
 * @version (Breitensuche)
 * symmetrischer und gewichteter Graph
 * Aufgabe 2b: bfs fügt zusätzlich die Kanten des Spannbaums ein
 */

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.Arrays;

public class Graph{
    private final int V_MAX;
    private int V = 0;  // augenblickliche Knotenanzahl
    private String[] ids;  // BezeichnerFeld für die Knoten 
    private int[][] adj; // Adjazenzmatrix;  Buch: matrix
        
    // erzeugt einen leeren Graf:
    public Graph(int V_MAX){
        this.V_MAX = V_MAX;
        ids = new String [V_MAX];
        adj = new int [V_MAX][V_MAX];
    }
    
    // Buch: KnotenEinfügen(...)
    public void addNode(String id){
        if(V == V_MAX) return;
        ids[V] = id;
        V++;
    }

    // Gibt einen vollen Array ohne Freiplätze zurück.
    // Vorteil: for-each-Schleife anwendbar
    public String[] getIds(){
        return Arrays.copyOf(ids, V);
    }
     
    // Fügt eine Kante  v ---> w vom Gewicht weight ein
    // Buch: KanteEinfügen(...)
    public void addEdge(int v, int w, int weight){
      if(v >= V ||  w >= V) return;
      if(v < 0 ||  w < 0 ) return;
      adj[v][w] = weight;
      adj[w][v] = weight;
    }
    
    public void addEdge(String idv, String idw, int weight){
      addEdge(indexOf(idv), indexOf(idw), weight);
    }
    
    // für ungewichtete Graphen:
    public void addEdge(String idv, String idw){
      addEdge(indexOf(idv), indexOf(idw), 1);
    }
    
    // Gibt zur id den zughörigen Index zurück:
    // Buch KnotenNummerGeben(...)
    private int indexOf(String id){
        for(int v = 0; v < V; v++)
           if(id.equals(ids[v])) return v;
        return -1; // nicht gefunden: Fehler!
    }
    
    
    // Breitensuche:
    // ----------------------------------------------------------------------------------------
    
    private ArrayList<Integer> queue = new ArrayList<>();  // Warteliste
    private boolean[] marked; // markierte Knoten
    private int[] edgeTo;  // alle Kanten des Spannbaums; -1: kein Vorgänger
    
    // keine Kanten am Anfang:
    private void initEdgeTo(){
        edgeTo = new int[V];
        for(int i = 0; i < V; i++)
            edgeTo[i] = -1;
    }
    
  
   // verzögertes Einfügen des Knoten v in die Warteliste,
    // damit das menschliche Auge bei der Visualisierung mitkommt
    private void queue_addSlowly(int v){
        queue.add(v);
        StaticTools.warte(2000);  // 2000 ms Pause
    }
    
    // Breitensuche mit Startknoten s:
    public void bfs(int s){
        // Vorbereitungen:
        marked = new boolean[V];
        queue.clear();
        initEdgeTo();
        
        // Startbedingung:     
        marked[s] = true;
        queue_addSlowly(s);
        
        // Schleife:
        while(!queue.isEmpty()){
            int v = queue.remove(0); // v: aktuelle Knoten
            for(int w = 0; w < V; w++)   
               if(adj[v][w] > 0 && !marked[w]){
                   marked[w] = true;
                   edgeTo[w] = v;
                   queue_addSlowly(w);   
               }     
        }
        
    }
    
    // ---------------------------------------------------------------------
    // für Schüler nicht von Bedeutung:
    
    // Returns a String of given length len, but only the first n charactes 
    // of id, the rest is filled with withespace (len <= 10).
    // Usefull, when drawing the matrix
    private String shortId(String id, int n, int len){
        String whiteSpace10 = "          "; 
        String idPlus = id + whiteSpace10;
        return (idPlus.substring(0,n) + whiteSpace10).substring(0,len);
    }
    
    // gibt die Matrix aus:
    @Override
    public String toString(){ 
       String whiteSpace6 = "      ";  // Länge: 6
       StringBuilder out = new StringBuilder(whiteSpace6);
       // Spaltenbezeichner:
       for(String id: getIds())
          out.append(shortId(id,3,6));
       // Zeilen:   
       for(int v = 0; v < V; v++){
           out.append("\n" + shortId(ids[v],3,6)); // Zeilenbezeichner
           for(int w = 0; w < V; w++) 
              out.append( (adj[v][w] == 0)? whiteSpace6 : (adj[v][w] + whiteSpace6).substring(0,6));
       }      
       return out.toString();
    }
    // Grafen zeichnen:
    //-------------------------------------------------------------------------------------------
    private GraphStream graphStream = new GraphStream();
    
    private class GraphStream {
        private final org.graphstream.graph.Graph graph_ = new SingleGraph("showGraph");
        
        GraphStream(){
          setCSS_(); 
          graph_.display();
          setTimer(100);
        }
        
        private void actualiseGraph(){
           addNodes_();
           addEdges_();
        }
       
        private void addNodes_(){
          String id;
          org.graphstream.graph.Node node_;
          for(int v = 0; v < V; v++){
             id = ids[v];
             node_ = graph_.getNode(v);
             if(node_ == null){
                 node_ = graph_.addNode(id);
                 node_.setAttribute("ui.label", id);
                }
              else if(marked[v]) node_.setAttribute("ui.style", "fill-color: #1ce60e;");
           }
       }
       
       private String getEdgeId_(int v, int w){
           return ids[v] + "-" + ids[w];
        }
        
       private void addEdges_(){
       org.graphstream.graph.Edge edge_;
       for(int v = 0; v < V; v++)
          for(int w = 0; w < v; w++){
              if (adj[v][w] > 0){
                  String id = getEdgeId_(v,w);
                  edge_ = graph_.getEdge(id);
                  if(edge_ == null){
                      edge_ = graph_.addEdge(id, v, w);
                      int weight = adj[v][w];
                      if(weight > 1) edge_.setAttribute("ui.label", weight + "");
                    }
                  else if (edgeTo[v] == w || edgeTo[v] == w) 
                    edge_.setAttribute("ui.color", 1);
          }
       }
    }
       
       private void setCSS_(){
        graph_.addAttribute("ui.antialias");
        graph_.addAttribute("ui.quality");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String styleSheet =  "node{" +
            "   size: 30px, 30px;" +
            "   text-size: 30;" +
            "   fill-color: #eddeab;" +
            "}" +
            "edge{" + 
                "   fill-mode: dyn-plain;" +
                "   fill-color: black, #1ce60e;" +
                "   text-size: 25; }";
        graph_.addAttribute("ui.stylesheet", styleSheet);
    }
      
    
    private void setTimer(int delay) {
        Timer t = new Timer(delay,evt -> actualiseGraph());
        t.setRepeats(true);
        t.start();
    }
      
  }  
    
}
