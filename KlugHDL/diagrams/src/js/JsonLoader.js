var canvasIdGlobal = "gfx_holder1";

function displayJSON(canvas){
    var writer = new draw2d.io.json.Writer();
    writer.marshal(canvas,function(json){
        $("#json").text(JSON.stringify(json, null, 2));
    });
}

$(window).load(function () {

    var canvas = new draw2d.Canvas(canvasIdGlobal);

    $.getJSON("../model.json", function (model) {

        generateDiagram(model);

        generateTreeView(model);
    });

    var reader = new draw2d.io.json.Reader();
    reader.unmarshal(canvas, jsonDocument);

    // display the JSON document in the preview DIV
    //
    displayJSON(canvas);


    // add an event listener to the Canvas for change notifications.
    // We just dump the current canvas document into the DIV
    //
    canvas.getCommandStack().addEventListener(function(e){
        if(e.isPostChangeEvent()){
            displayJSON(canvas);
        }
    });

});


