var topLevel;
var diagrams = [];
var extOutputGlobal;
var extInputGlobal;

itrG = 0;

var canvasIdGlobal = "gfx_holder1";

var crtLevel;
var crtShapes = [];

var diagramsStack = [];

var canExecuteDisplayDiagram = true;

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

function generateTreeView(model) {

    var tree = [
        model.tree
    ];

    $('#treeview').treeview({
        data: tree
    })
}

function getDiagram(name) {
    return diagrams.filter(function (entry) {
        return entry.name == name;
    })[0];
}

function addConn(canvas, conn) {
    var from = getFromCrtShapes(conn.from.name);
    var to = getFromCrtShapes(conn.to.name);

    canvas.add(
        newConnection(
            from.getPort(conn.from.port),
            to.getPort(conn.to.port)
        )
    );
}

function getFromCrtShapes(name) {
    return crtShapes.filter(function (shape) {
        return shape.classLabel.text == name;
    })[0];
}

function displayDiagram(diagram) {

    if(diagram == null){
        return;
    }

    if (!canExecuteDisplayDiagram) {
        return;
    }

    canExecuteDisplayDiagram = false;

    crtLevel = diagram;
    crtShapes = [];

    var holder = document.getElementById(canvasIdGlobal);
    holder.innerHTML = "";

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

    var canvas = new draw2d.Canvas(canvasIdGlobal);

    canvas.installEditPolicy(new draw2d.policy.connection.DragConnectionCreatePolicy({
        createConnection: createConnection
    }));

    for (var itrComp = 0; itrComp < diagram.components.length; itrComp++) {
        var comp = diagram.components[itrComp];

        if (comp.type == "default") {
            var shape = new ComponentShape();
            shape.doubleClickCallBack = null;
            shape.setName(comp.name);


            for (var portItr = 0; portItr < comp.ports.length; portItr++) {
                var port = comp.ports[portItr];
                shape.addPort(port.name, port.portType);
            }

            shape.setX(graph.node(comp.name).x);
            shape.setY(graph.node(comp.name).y);
            canvas.add(shape);
            crtShapes.push(shape);

            console.log();

            var diagToDisplay = getDiagram(comp.name);

            if (diagToDisplay == null) {
                shape.doubleClickCallBack = function () {
                    console.log("No children !")
                }
            } else {
                shape.doubleClickCallBack = function () {
                    if(canExecuteGotoChildren){
                        canExecuteGotoChildren = false;
                        canExecuteDisplayDiagram = true;
                        diagramsStack.push(diagram);
                        displayDiagram(diagToDisplay);
                    }
                };
            }

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
                canExecuteDisplayDiagram = true;
                var diag = diagramsStack.pop();
                displayDiagram(diag);
            };

            extO.doubleClickCallBack = function () {
                canExecuteDisplayDiagram = true;
                var diag = diagramsStack.pop();
                displayDiagram(diag);
            };

            crtShapes.push(extI);
            crtShapes.push(extO);
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

function generateDiagram(model) {

    extInputGlobal = model.extInput;
    extOutputGlobal = model.extOutput;

    diagrams = [];

    for (var i = 0; i < model.model.length; i++) {
        var diagram = model.model[i].diagram;
        if (diagram.isTopLevel == "true") {
            topLevel = diagram;
        }
        diagrams.push(diagram);
    }
    displayDiagram(topLevel);
}

function displayStack() {
    diagramsStack.forEach(function (entry) {
        console.log(entry);
    })
}

$(document).ready(function () {

    $.getJSON("../model.json", function (model) {

        generateDiagram(model);

        generateTreeView(model);
    })

});
