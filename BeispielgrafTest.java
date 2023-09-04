import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author  (Herbert Wenisch)
 * @version (08-20-23)
 */
public class BeispielgrafTest {
    private Beispielgraf beispielgraf;
    private Graph graph;
    
    @BeforeEach
    public void setUp(){
        beispielgraf = new Beispielgraf();
        graph = beispielgraf.getGraph(); 
    }

    // Startknoten: A
    @Test
    public void test_bfs(){
        graph.bfs(0);
        System.out.println("Pfad von D nach A:");
        System.out.println(graph.path("D"));
    }
    
    
}


