import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.GraphRenderer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class Main
{
    private static Graph graph;
    private static SpriteManager sman;

    public static void main(String[] args)
    {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph = new MultiGraph("GraphModel");
        sman = new SpriteManager(graph);


        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        String stylesheet = "node{shape:box; size: 50px, 30px; text-alignment : center; stroke-mode : dots; fill-color : white;}" +
                "edge{shape:freeplane;arrow-shape:arrow;}";

        graph.addAttribute("ui.stylesheet", stylesheet);

        Node crtNode;

        crtNode = graph.addNode("I");
        crtNode.addAttribute("ui.label", "Input");
        crtNode.addAttribute("x","0");
        crtNode.setAttribute("y","0");

        crtNode = graph.addNode("O");
        crtNode.addAttribute("ui.label", "Output");
        crtNode.addAttribute("x","4");
        crtNode.setAttribute("y","0");

        crtNode = graph.addNode("a");
        crtNode.addAttribute("ui.label", "AndGate");
        crtNode.addAttribute("x","2");
        crtNode.setAttribute("y","-2");

        crtNode = graph.addNode("o");
        crtNode.addAttribute("ui.label", "OrGate");
        crtNode.addAttribute("x","2");
        crtNode.setAttribute("y","2");

        addDirectedEdge("I","a","Bool","io.a");
        addDirectedEdge("I","a","Bool","io.b");
        addDirectedEdge("I","o","Bool","io.a");
        addDirectedEdge("I","o","Bool","io.b");
        addDirectedEdge("o","O","Bool","io.c");
        addDirectedEdge("a","O","Bool","io.c");

        int addEdges = 100;
        for(int i = 0; i < addEdges; i++)
        {
            addDirectedEdge("I","a","bool","io.a"+i);
        }

        graph.display();
    }

    private static void addDirectedEdge(String from, String to, String type, String signalName)
    {
        String id = String.format("%s%s.%s",from,to,signalName);
        graph.addEdge(id,from,to,true);

        // Add label with sprite

        Sprite sLabel = sman.addSprite("sL" + from + to + signalName.replaceAll("\\.","_"));
        sLabel.attachToEdge(id);
        sLabel.setPosition(0.2,0.1,0);
        sLabel.addAttribute("ui.label",signalName);
        sLabel.addAttribute("ui.style","fill-mode:none;");

        // Add type with sprite
        Sprite sType = sman.addSprite("sT" + from + to);
        sType.attachToEdge(id);
        sType.setPosition(0.8,0.1,0);
        sType.addAttribute("ui.label",type);
        sType.addAttribute("ui.style","fill-mode:none;");
    }
}
