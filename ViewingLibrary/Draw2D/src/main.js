/**
 * Created by snipy on 15.12.16.
 */

$(document).ready(function () {

    var g = new dagre.graphlib.Graph({
        rankdir: "RL",
        directed: true
    });

    g.setGraph({});

    g.setDefaultEdgeLabel(function () {
        return {};
    });

    g.setNode("I", {label: "", width: 100, height: 100});
    g.setNode("O", {label: "", width: 100, height: 100});
    g.setNode("a", {label: "", width: 100, height: 100});
    g.setNode("o", {label: "", width: 100, height: 100});
    g.setEdge("I", "a");
    g.setEdge("I", "a");
    g.setEdge("I", "o");
    g.setEdge("I", "o");
    g.setEdge("a", "O");
    g.setEdge("o", "O");

    dagre.layout(g);

    var canvas = new draw2d.Canvas("gfx_holder1");

    var andGate = new ComponentShape();
    var orGate = new ComponentShape();
    var input = new ComponentShape();
    var output = new ComponentShape();

    canvas.installEditPolicy(new draw2d.policy.connection.DragConnectionCreatePolicy({
        createConnection: createConnection
    }));

    andGate.setName("AndGate");
    orGate.setName("OrGate");
    input.setName("Input");
    output.setName("Output");

    andGate.addPort("io.a", "input");
    andGate.addPort("io.b", "input");
    andGate.addPort("io.c", "output");

    orGate.addPort("io.a", "input");
    orGate.addPort("io.b", "input");
    orGate.addPort("io.c", "output");

    input.addPort("io.a", "output");
    input.addPort("io.b", "output");

    output.addPort("io.c", "input");

    input.setX(g.node("I").x);
    input.setY(g.node("I").y);
    output.setX(g.node("O").x);
    output.setY(g.node("O").y);
    andGate.setX(g.node("a").x);
    andGate.setY(g.node("a").y);
    orGate.setX(g.node("o").x);
    orGate.setY(g.node("o").y);

    canvas.add(input);
    canvas.add(output);
    canvas.add(andGate);
    canvas.add(orGate);

    canvas.add(newConnection(input.getPort("io.a"), andGate.getPort("io.a")));
    canvas.add(newConnection(input.getPort("io.b"), andGate.getPort("io.b")));
    canvas.add(newConnection(input.getPort("io.a"), orGate.getPort("io.a")));
    canvas.add(newConnection(input.getPort("io.b"), orGate.getPort("io.b")));

    canvas.add(newConnection(andGate.getPort("io.c"), output.getPort("io.c")));

    var c = newConnection(orGate.getPort("io.c"), output.getPort("io.c"));
    canvas.add(c);


    generateTreeView();

});

function createConnection() {
    var con = new draw2d.Connection();
    con.setRouter(new draw2d.layout.connection.InteractiveManhattanConnectionRouter());
    con.setTargetDecorator(new draw2d.decoration.connection.ArrowDecorator());
    return con;
}

function newConnection(srcPort, targetPort) {
    var c = createConnection();
    c.setSource(srcPort);
    c.setTarget(targetPort);
    return c
}

function generateTreeView() {
    var tree = [
        {
            text: "Parent 1",
            nodes: [
                {
                    text: "child 1"
                },
                {
                    text: "child 2"
                }
            ]
        },
        {
            text: "Parent 2"
        },
        {
            text: "Parent 3"
        }
    ];

    $('#treeview').treeview({
        data: tree,
        levels: 2
    })
}

function setComponentName(title) {
    document.getElementById("title").innerHTML = title;
}