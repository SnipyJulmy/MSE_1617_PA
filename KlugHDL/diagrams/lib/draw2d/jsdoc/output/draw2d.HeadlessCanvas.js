Ext.data.JsonP.draw2d_HeadlessCanvas({"tagname":"class","name":"draw2d.HeadlessCanvas","autodetected":{},"files":[{"filename":"HeadlessCanvas.js","href":"HeadlessCanvas.html#draw2d-HeadlessCanvas"}],"inheritable":true,"author":[{"tagname":"author","name":"Andreas Herz","email":null}],"members":[{"name":"constructor","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-constructor","meta":{}},{"name":"add","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-add","meta":{"chainable":true}},{"name":"clear","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-clear","meta":{"chainable":true}},{"name":"fireEvent","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-fireEvent","meta":{}},{"name":"getAllPorts","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getAllPorts","meta":{}},{"name":"getCommandStack","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getCommandStack","meta":{}},{"name":"getFigure","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getFigure","meta":{}},{"name":"getFigures","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getFigures","meta":{"protected":true}},{"name":"getLine","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getLine","meta":{}},{"name":"getLines","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-getLines","meta":{"protected":true}},{"name":"hideDecoration","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-hideDecoration","meta":{"template":true}},{"name":"off","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-off","meta":{"chainable":true}},{"name":"on","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-on","meta":{"chainable":true}},{"name":"registerPort","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-registerPort","meta":{"chainable":true}},{"name":"showDecoration","tagname":"method","owner":"draw2d.HeadlessCanvas","id":"method-showDecoration","meta":{"template":true}}],"alternateClassNames":[],"aliases":{},"id":"class-draw2d.HeadlessCanvas","component":false,"superclasses":[],"subclasses":[],"mixedInto":[],"mixins":[],"parentMixins":[],"requires":[],"uses":[],"html":"<div><pre class=\"hierarchy\"><h4>Files</h4><div class='dependency'><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas' target='_blank'>HeadlessCanvas.js</a></div></pre><div class='doc-contents'><p>Required for Node.js draw2d model read/write operations.</p>\n</div><div class='members'><div class='members-section'><div class='definedBy'>Defined By</div><h3 class='members-title icon-method'>Methods</h3><div class='subsection'><div id='method-constructor' class='member first-child not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-constructor' target='_blank' class='view-source'>view source</a></div><strong class='new-keyword'>new</strong><a href='#!/api/draw2d.HeadlessCanvas-method-constructor' class='name expandable'>draw2d.HeadlessCanvas</a>( <span class='pre'>canvasId</span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"></span></div><div class='description'><div class='short'>Create a new canvas with the given HTML DOM references. ...</div><div class='long'><p>Create a new canvas with the given HTML DOM references.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>canvasId</span> : String<div class='sub-desc'><p>the id of the DOM element to use a parent container</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-add' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-add' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-add' class='name expandable'>add</a>( <span class='pre'>figure, [x], [y]</span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"><span class='chainable' >chainable</span></span></div><div class='description'><div class='short'>Add a figure at the given x/y coordinate. ...</div><div class='long'><p>Add a figure at the given x/y coordinate. This method fires an event.</p>\n\n<p>Example:</p>\n\n<pre><code> canvas.on(\"figure:add\", function(emitter, event){\n    alert(\"figure added:\");\n });\n\n // or more general if you want catch all figure related events\n //\n canvas.on(\"figure\", function(emitter, event){\n    // use event.figure.getCanvas()===null to determine if the \n    // figure part of the canvas\n\n    alert(\"figure added or removed:\");\n });\n</code></pre>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>figure</span> : <a href=\"#!/api/draw2d.Figure\" rel=\"draw2d.Figure\" class=\"docClass\">draw2d.Figure</a><div class='sub-desc'><p>The figure to add.</p>\n</div></li><li><span class='pre'>x</span> : Number/<a href=\"#!/api/draw2d.geo.Point\" rel=\"draw2d.geo.Point\" class=\"docClass\">draw2d.geo.Point</a> (optional)<div class='sub-desc'><p>The new x coordinate of the figure or the x/y coordinate if it is an <a href=\"#!/api/draw2d.geo.Point\" rel=\"draw2d.geo.Point\" class=\"docClass\">draw2d.geo.Point</a></p>\n</div></li><li><span class='pre'>y</span> : Number (optional)<div class='sub-desc'><p>The y position.</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'><p>this</p>\n</div></li></ul></div></div></div><div id='method-clear' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-clear' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-clear' class='name expandable'>clear</a>( <span class='pre'></span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"><span class='chainable' >chainable</span></span></div><div class='description'><div class='short'>Reset the canvas and delete all model elements. ...</div><div class='long'><p>Reset the canvas and delete all model elements.<br>\nYou can now reload another model to the canvas with a <a href=\"#!/api/draw2d.io.Reader\" rel=\"draw2d.io.Reader\" class=\"docClass\">draw2d.io.Reader</a></p>\n        <p>Available since: <b>1.1.0</b></p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'><p>this</p>\n</div></li></ul></div></div></div><div id='method-fireEvent' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-fireEvent' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-fireEvent' class='name expandable'>fireEvent</a>( <span class='pre'>event, [args]</span> )<span class=\"signature\"></span></div><div class='description'><div class='short'>Execute all handlers and behaviors attached to the canvas for the given event type. ...</div><div class='long'><p>Execute all handlers and behaviors attached to the canvas for the given event type.</p>\n        <p>Available since: <b>5.0.0</b></p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>event</span> : String<div class='sub-desc'><p>the event to trigger</p>\n</div></li><li><span class='pre'>args</span> : Object (optional)<div class='sub-desc'><p>optional parameters for the triggered event callback</p>\n</div></li></ul></div></div></div><div id='method-getAllPorts' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getAllPorts' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getAllPorts' class='name expandable'>getAllPorts</a>( <span class='pre'></span> )<span class=\"signature\"></span></div><div class='description'><div class='short'>Return all ports in the canvas ...</div><div class='long'><p>Return all ports in the canvas</p>\n</div></div></div><div id='method-getCommandStack' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getCommandStack' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getCommandStack' class='name expandable'>getCommandStack</a>( <span class='pre'></span> ) : <a href=\"#!/api/draw2d.command.CommandStack\" rel=\"draw2d.command.CommandStack\" class=\"docClass\">draw2d.command.CommandStack</a><span class=\"signature\"></span></div><div class='description'><div class='short'>Returns the command stack for the Canvas. ...</div><div class='long'><p>Returns the command stack for the Canvas. Required for undo/redo support.</p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.command.CommandStack\" rel=\"draw2d.command.CommandStack\" class=\"docClass\">draw2d.command.CommandStack</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-getFigure' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getFigure' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getFigure' class='name expandable'>getFigure</a>( <span class='pre'>id</span> ) : <a href=\"#!/api/draw2d.Figure\" rel=\"draw2d.Figure\" class=\"docClass\">draw2d.Figure</a><span class=\"signature\"></span></div><div class='description'><div class='short'>Returns the figure with the given id. ...</div><div class='long'><p>Returns the figure with the given id.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>id</span> : String<div class='sub-desc'><p>The id of the figure.</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.Figure\" rel=\"draw2d.Figure\" class=\"docClass\">draw2d.Figure</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-getFigures' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getFigures' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getFigures' class='name expandable'>getFigures</a>( <span class='pre'></span> ) : <a href=\"#!/api/draw2d.util.ArrayList\" rel=\"draw2d.util.ArrayList\" class=\"docClass\">draw2d.util.ArrayList</a><span class=\"signature\"><span class='protected' >protected</span></span></div><div class='description'><div class='short'>Returns the internal figures. ...</div><div class='long'><p>Returns the internal figures.<br></p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.ArrayList\" rel=\"draw2d.util.ArrayList\" class=\"docClass\">draw2d.util.ArrayList</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-getLine' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getLine' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getLine' class='name expandable'>getLine</a>( <span class='pre'>id</span> ) : <a href=\"#!/api/draw2d.shape.basic.Line\" rel=\"draw2d.shape.basic.Line\" class=\"docClass\">draw2d.shape.basic.Line</a><span class=\"signature\"></span></div><div class='description'><div class='short'>Returns the line or connection with the given id. ...</div><div class='long'><p>Returns the line or connection with the given id.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>id</span> : String<div class='sub-desc'><p>The id of the line.</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.shape.basic.Line\" rel=\"draw2d.shape.basic.Line\" class=\"docClass\">draw2d.shape.basic.Line</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-getLines' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-getLines' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-getLines' class='name expandable'>getLines</a>( <span class='pre'></span> ) : <a href=\"#!/api/draw2d.util.ArrayList\" rel=\"draw2d.util.ArrayList\" class=\"docClass\">draw2d.util.ArrayList</a><span class=\"signature\"><span class='protected' >protected</span></span></div><div class='description'><div class='short'>Returns all lines/connections in this workflow/canvas. ...</div><div class='long'><p>Returns all lines/connections in this workflow/canvas.<br></p>\n<h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.util.ArrayList\" rel=\"draw2d.util.ArrayList\" class=\"docClass\">draw2d.util.ArrayList</a></span><div class='sub-desc'>\n</div></li></ul></div></div></div><div id='method-hideDecoration' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-hideDecoration' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-hideDecoration' class='name expandable'>hideDecoration</a>( <span class='pre'></span> )<span class=\"signature\"><span class='template' >template</span></span></div><div class='description'><div class='short'>Callback for any kind of image export tools to trigger the canvas to hide all unwanted\ndecorations. ...</div><div class='long'><p>Callback for any kind of image export tools to trigger the canvas to hide all unwanted\ndecorations. The method is called e.g. from the <a href=\"#!/api/draw2d.io.png.Writer\" rel=\"draw2d.io.png.Writer\" class=\"docClass\">draw2d.io.png.Writer</a></p>\n        <p>Available since: <b>4.0.0</b></p>\n      <div class='rounded-box template-box'>\n      <p>This is a <a href=\"#!/guide/components\">template method</a>.\n         a hook into the functionality of this class.\n         Feel free to override it in child classes.</p>\n      </div>\n</div></div></div><div id='method-off' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-off' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-off' class='name expandable'>off</a>( <span class='pre'>eventOrFunction</span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"><span class='chainable' >chainable</span></span></div><div class='description'><div class='short'>The .off() method removes event handlers that were attached with on. ...</div><div class='long'><p>The .off() method removes event handlers that were attached with <a href=\"#!/api/draw2d.HeadlessCanvas-method-on\" rel=\"draw2d.HeadlessCanvas-method-on\" class=\"docClass\">on</a>.<br>\nCalling .off() with no arguments removes all handlers attached to the canvas.<br>\n<br>\nIf a simple event name such as \"reset\" is provided, all events of that type are removed from the canvas.</p>\n        <p>Available since: <b>5.0.0</b></p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>eventOrFunction</span> : String|Function<div class='sub-desc'><p>the event name of the registerd function</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'><p>this</p>\n</div></li></ul></div></div></div><div id='method-on' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-on' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-on' class='name expandable'>on</a>( <span class='pre'>event, callback</span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"><span class='chainable' >chainable</span></span></div><div class='description'><div class='short'>Attach an event handler function for one or more events to the canvas. ...</div><div class='long'><p>Attach an event handler function for one or more events to the canvas.\nTo remove events bound with .on(), see <a href=\"#!/api/draw2d.HeadlessCanvas-method-off\" rel=\"draw2d.HeadlessCanvas-method-off\" class=\"docClass\">off</a>.</p>\n\n<p>possible events are:<br></p>\n\n<ul>\n  <li>reset</li>\n  <li>select</li>\n</ul>\n\n\n<p>Example:</p>\n\n<pre><code> canvas.on(\"clear\", function(emitter, event){\n    alert(\"canvas.clear() called.\");\n });\n\n canvas.on(\"select\", function(emitter,event){\n     if(event.figure!==null){\n         alert(\"figure selected\");\n     }\n     else{\n         alert(\"selection cleared\");\n     }\n });\n</code></pre>\n        <p>Available since: <b>5.0.0</b></p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>event</span> : String<div class='sub-desc'><p>One or more space-separated event types</p>\n</div></li><li><span class='pre'>callback</span> : Function<div class='sub-desc'><p>A function to execute when the event is triggered.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>emitter</span> : <a href=\"#!/api/draw2d.Canvas\" rel=\"draw2d.Canvas\" class=\"docClass\">draw2d.Canvas</a><div class='sub-desc'><p>the emitter of the event</p>\n</div></li><li><span class='pre'>obj</span> : Object (optional)<div class='sub-desc'><p>optional event related data</p>\n</div></li></ul></div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'><p>this</p>\n</div></li></ul></div></div></div><div id='method-registerPort' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-registerPort' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-registerPort' class='name expandable'>registerPort</a>( <span class='pre'>port</span> ) : <a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a><span class=\"signature\"><span class='chainable' >chainable</span></span></div><div class='description'><div class='short'>Register a port to the canvas. ...</div><div class='long'><p>Register a port to the canvas. This is required for other ports to find a valid drop target.</p>\n<h3 class=\"pa\">Parameters</h3><ul><li><span class='pre'>port</span> : <a href=\"#!/api/draw2d.Port\" rel=\"draw2d.Port\" class=\"docClass\">draw2d.Port</a><div class='sub-desc'><p>The new port which has been added to the Canvas.</p>\n</div></li></ul><h3 class='pa'>Returns</h3><ul><li><span class='pre'><a href=\"#!/api/draw2d.HeadlessCanvas\" rel=\"draw2d.HeadlessCanvas\" class=\"docClass\">draw2d.HeadlessCanvas</a></span><div class='sub-desc'><p>this</p>\n</div></li></ul></div></div></div><div id='method-showDecoration' class='member  not-inherited'><a href='#' class='side expandable'><span>&nbsp;</span></a><div class='title'><div class='meta'><span class='defined-in' rel='draw2d.HeadlessCanvas'>draw2d.HeadlessCanvas</span><br/><a href='source/HeadlessCanvas.html#draw2d-HeadlessCanvas-method-showDecoration' target='_blank' class='view-source'>view source</a></div><a href='#!/api/draw2d.HeadlessCanvas-method-showDecoration' class='name expandable'>showDecoration</a>( <span class='pre'></span> )<span class=\"signature\"><span class='template' >template</span></span></div><div class='description'><div class='short'>callback method for any image export writer to reactivate the decoration\nof the canvas. ...</div><div class='long'><p>callback method for any image export writer to reactivate the decoration\nof the canvas. e.g. grids, rulers,...</p>\n        <p>Available since: <b>4.0.0</b></p>\n      <div class='rounded-box template-box'>\n      <p>This is a <a href=\"#!/guide/components\">template method</a>.\n         a hook into the functionality of this class.\n         Feel free to override it in child classes.</p>\n      </div>\n</div></div></div></div></div></div></div>","meta":{}});