/**
 * Created by snipy on 16.12.16.
 */

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