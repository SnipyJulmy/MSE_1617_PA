package examples;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * GraphStream example :
 * How to add multiples labels on an edge
 * Created by Snipy on 21.10.16.
 */
public class SpriteEdgesExample
{
    public static void main(String[] args)
    {
        System.setProperty("org.graphstream.ui.renderer", 
                           "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new MultiGraph("Edge multiple label example");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("AB", "A", "B", true);

        /* Add some label using the sprite */

        SpriteManager sman = new SpriteManager(graph);

        // add label 1
        Sprite label1 = sman.addSprite("label1");
        label1.attachToEdge("AB");
        label1.setPosition(0.2, 0, 0);
        label1.addAttribute("ui.label", "Label 1");
        label1.addAttribute("ui.style", "fill-mode:none;");

        // add label 2
        Sprite label2 = sman.addSprite("label2");
        label2.attachToEdge("AB");
        label2.setPosition(0.8, 0, 0);
        label2.addAttribute("ui.label", "Label 2");
        label2.addAttribute("ui.style", "fill-mode:none;");

        graph.display(true);
    }
}
