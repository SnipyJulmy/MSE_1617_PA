package examples;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * GraphStream example :
 * How to label an edge
 * Created by Snipy on 21.10.16.
 */
public class EdgesLabel
{
    public static void main(String[] args)
    {
        System.setProperty("org.graphstream.ui.renderer",
                           "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new MultiGraph("Edges label example");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        graph.addNode("A");
        graph.addNode("B");
        Edge edge = graph.addEdge("AB", "A", "B", true);
        edge.addAttribute("ui.label", "A --> B");
        graph.display(false);
    }
}
