var diagramsMap = {};
var displayStack = [];

var canvasIdGlobal = "gfx_holder1";
var crtShapes;

var extOutputGlobal;
var extInputGlobal;
var crtDiagram;

function getFromCrtShapes(name) {
    return crtShapes.filter(function (shape) {
        return shape.classLabel.text == name;
    })[0];
}

function addConn(canvas, conn) {
    var from = crtShapes[conn.from.name];
    var to = crtShapes[conn.to.name];

    canvas.add(
        newConnection(
            from.getPort(conn.from.port),
            to.getPort(conn.to.port)
        )
    );
}

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

function buildModel(model) {

    extInputGlobal = model.extInput;
    extOutputGlobal = model.extOutput;

    for (var i = 0; i < model.model.length; i++) {
        var diagram = model.model[i].diagram;
        if (diagram.isTopLevel == "true") {
            topLevel = diagram.name;
        }
        diagramsMap[diagram.name] = diagram;
    }
    buildDiagram(topLevel);
}

function clearDiagramsView() {
    delete crtShapes;
    var holder = document.getElementById(canvasIdGlobal);
    holder.innerHTML = "";
}

function viewStack() {
}

function pushAndDisplay(name) {
    displayStack.push(crtDiagram);
    clearDiagramsView();
    buildDiagram(name);
}

function popAndDisplay() {
    var name = displayStack.pop();
    clearDiagramsView();
    buildDiagram(name);
}

function buildDiagram(name) {

    crtDiagram = name;
    crtShapes = [];

    var diagram = diagramsMap[name];

    console.log(diagram);

    var graph = doLayout(diagram);

    var canvas = new draw2d.Canvas(canvasIdGlobal);

    canvas.installEditPolicy(new draw2d.policy.connection.DragConnectionCreatePolicy({
        createConnection: createConnection
    }));

    for (var itrComp = 0; itrComp < diagram.components.length; itrComp++) {
        var comp = diagram.components[itrComp];

        if (comp.type == "default") {
            var shape = new ComponentShape();
            shape.setName(comp.name);

            for (var portItr = 0; portItr < comp.ports.length; portItr++) {
                var port = comp.ports[portItr];
                shape.addPort(port.name, port.portType);
            }

            shape.setX(graph.node(comp.name).x);
            shape.setY(graph.node(comp.name).y);

            canvas.add(shape);

            if (!(comp.name in diagramsMap)) {
                shape.doubleClickCallBack = function () {
                    console.log("No children !")
                }
            } else {
                shape.doubleClickCallBack = function () {
                    pushAndDisplay(comp.name);
                    console.log(displayStack);
                };
            }

            crtShapes[comp.name] = shape;

        } else {
            var extI = new ComponentShape();
            extI.setName(extInputGlobal);
            var extO = new ComponentShape();
            extO.setName(extOutputGlobal);

            for (var portItrExt = 0; portItrExt < comp.ports.length; portItrExt++) {
                var portExt = comp.ports[portItrExt];
                if (portExt.portType == "input") {
                    extI.addPort(portExt.name, "output");
                } else {
                    extO.addPort(portExt.name, "input");
                }
            }

            extI.setX(graph.node(extInputGlobal).x);
            extI.setY(graph.node(extInputGlobal).y);
            extO.setX(graph.node(extOutputGlobal).x);
            extO.setY(graph.node(extOutputGlobal).y);
            canvas.add(extI);
            canvas.add(extO);

            extI.doubleClickCallBack = function () {
                popAndDisplay();
                console.log(displayStack);
            };

            extO.doubleClickCallBack = function () {
                popAndDisplay();
                console.log(displayStack);
            };

            crtShapes[extInputGlobal] = extI;
            crtShapes[extOutputGlobal] = extO;
        }
    }

    // brothers connections
    for (var bItr = 0; bItr < diagram.connections.length; bItr++) {
        var conn = diagram.connections[bItr];
        addConn(canvas, conn);
    }

    // outputs connections
    for (var oItr = 0; oItr < diagram.outputs.length; oItr++) {
        var output = diagram.outputs[oItr];
        addConn(canvas, output);
    }
    // brothers connections
    for (var iItr = 0; iItr < diagram.inputs.length; iItr++) {
        var input = diagram.inputs[iItr];
        addConn(canvas, input);
    }
}

function doLayout(diagram) {

    // Layout of the diagram
    var graph = new dagre.graphlib.Graph({
        rankdir: "RL",
        directed: true
    });

    graph.setGraph({});

    graph.setDefaultEdgeLabel(function () {
        return {};
    });

    for (var itrCompLayout = 0; itrCompLayout < diagram.components.length; itrCompLayout++) {
        var c = diagram.components[itrCompLayout];

        // TODO width + height
        if (c.type == "default") {
            graph.setNode(c.name, {label: "", width: 100, height: 100});
        } else {
            graph.setNode(extInputGlobal, {label: "", width: 100, height: 100});
            graph.setNode(extOutputGlobal, {label: "", width: 100, height: 100});
        }

    }

    // brothers connections
    for (var bItrLayout = 0; bItrLayout < diagram.connections.length; bItrLayout++) {
        var connLayout = diagram.connections[bItrLayout];
        graph.setEdge(connLayout.from.name, connLayout.to.name);
    }
    // outputs connections
    for (var oItrLayout = 0; oItrLayout < diagram.outputs.length; oItrLayout++) {
        var outputLayout = diagram.outputs[oItrLayout];
        graph.setEdge(outputLayout.from.name, outputLayout.to.name);
    }
    // brothers connections
    for (var iItrLayout = 0; iItrLayout < diagram.inputs.length; iItrLayout++) {
        var inputLayout = diagram.inputs[iItrLayout];
        graph.setEdge(inputLayout.from.name, inputLayout.to.name);
    }

    dagre.layout(graph);
    return graph;
}

function generateTreeView(model) {

    var tree = [
        model.tree
    ];

    $('#treeview').treeview({
        data: tree
    })
}

$(document).ready(function () {

    $.getJSON("../model.json", function (model) {

        buildModel(model);

        generateTreeView(model);
    })

});
