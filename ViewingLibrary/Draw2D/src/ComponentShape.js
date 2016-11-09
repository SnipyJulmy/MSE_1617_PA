/**
 * Created by snipy on 09.11.16.
 */

ComponentShape = draw2d.shape.layout.VerticalLayout.extend({
    NAME: "ComponentShape",

    init: function (attr) {

        var _this = this;

        this._super($.extend({
            bgColor: "#ff0000",
            color: "#000000",
            stroke: 1,
            radius: 3
        }, attr));

        this.classLabel = new draw2d.shape.basic.Label({
            text: "ClassName",
            stroke: 1,
            fontColor: "#5856d6",
            bgColor: "#f7f7f7",
            radius: this.getRadius(),
            padding: 10,
            resizeable: true,
            editor: new draw2d.ui.LabelInplaceEditor(),
            onDoubleClick: function () {
                _this.doubleClickCallBack()
            }
        });

        this.add(this.classLabel);
    },

    setName: function (name) {
        this.classLabel.setText(name);
    },

    getPort: function (name) {
        return this.getPorts().find(function (entry) {
            return entry.name == name
        });
    },

    addPort: function (name, type) {
        var _this = this;
        var label = new draw2d.shape.basic.Label({
            text: name,
            stroke: 0,
            radius: 90,
            bgColor: null,
            padding: {left: 10, top: 3, right: 10, bottom: 5},
            fontColor: "#4a4a4a",
            resizeable: true,
            onDoubleClick: function () {
                _this.doubleClickCallBack()
            },
            editor: new draw2d.ui.LabelEditor()
        });

        var port = label.createPort(type);
        port.setName(name);

        this.add(label);

        return label;
    },

    doubleClickCallBack: function () {
        console.log("OK")
    }

});
