import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as go from 'gojs';

import * as gojs from 'gojs';
import { MindMap } from 'src/app/_models/mindMap';
import { UpdateIdpRequest } from 'src/app/_models/updateIdpRequest';
import { EmployeeService } from 'src/app/_services/employee.service';
import { StorageService } from 'src/app/_services/storage.service';

const $ = gojs.GraphObject.make;



@Component({
  selector: 'app-idp',
  templateUrl: './idp.component.html',
  styleUrls: ['./idp.component.css']
})
export class IdpComponent implements OnInit {
  public diagram?: go.Diagram;
  employee: any;
  data: MindMap = new MindMap();
  

  constructor(private storageService: StorageService, private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employee = this.storageService.getUser();
    // We can get null fields from the service and they will be present in the JSON-object, which breaks the layout of the graph at its root
    // In order for the graph root to be displayed beautifully, it is necessary to remove the null tag "dir" 
    var data = JSON.stringify(this.employee.data).replace('"dir":null,', '');
    this.data.setNodeArray(JSON.parse(data));
  }

  public ngAfterViewInit() {
    this.diagram = $(go.Diagram, 'myDiagramDiv',
      {
        // when the user drags a node, also move/copy/delete the whole subtree starting with that node
        "commandHandler.copiesTree": true,
        "commandHandler.copiesParentKey": true,
        "commandHandler.deletesTree": true,
        "draggingTool.dragsTree": true,
        "undoManager.isEnabled": true
      }
    )

    if (!this.diagram) {
        return
    }

    this.load();

    this.diagram.addDiagramListener("Modified", e => {
      // 
      const pushBtn = document.getElementById("PushButton") as HTMLButtonElement;
      if (pushBtn && this.diagram) pushBtn.disabled = !this.diagram.isModified;
      var idx = document.title.indexOf("*");
      if (this.diagram && this.diagram.isModified) {
        if (idx < 0) document.title += "*";
      } else {
        if (idx >= 0) document.title = document.title.slice(0, idx);
      }
      
    });

    // a node consists of some text with a line shape underneath
    this.diagram.nodeTemplate =
    $(go.Node, "Vertical",
      { selectionObjectName: "TEXT" },
      $(go.TextBlock,
        {
          name: "TEXT",
          minSize: new go.Size(30, 15),
          editable: true
        },
        // remember not only the text string but the scale and the font in the node data
        new go.Binding("text", "text").makeTwoWay(),
        new go.Binding("scale", "scale").makeTwoWay(),
        new go.Binding("font", "font").makeTwoWay()),
      $(go.Shape, "LineH",
        {
          stretch: go.GraphObject.Horizontal,
          strokeWidth: 3, height: 3,
          // this line shape is the port -- what links connect with
          portId: "", fromSpot: go.Spot.LeftRightSides, toSpot: go.Spot.LeftRightSides
        },
        new go.Binding("stroke", "brush"),
        // make sure links come in from the proper direction and go out appropriately
        new go.Binding("fromSpot", "dir", d => this.spotConverter(d, true)),
        new go.Binding("toSpot", "dir", d => this.spotConverter(d, false))),
      // remember the locations of each node in the node data
      new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
      // make sure text "grows" in the desired direction
      new go.Binding("locationSpot", "dir", d => this.spotConverter(d, false))
    );

    // selected nodes show a button for adding children
      this.diagram.nodeTemplate.selectionAdornmentTemplate =
        $(go.Adornment, "Spot",
          $(go.Panel, "Auto",
            // this Adornment has a rectangular blue Shape around the selected node
            $(go.Shape, { fill: null, stroke: "dodgerblue", strokeWidth: 3 }),
            $(go.Placeholder, { margin: new go.Margin(4, 4, 0, 4) })
          ),
          // and this Adornment has a Button to the right of the selected node
          $("Button",
            {
              alignment: go.Spot.Right,
              alignmentFocus: go.Spot.Left,
              click: this.addNodeAndLink  // define click behavior for this Button in the Adornment
            },
            $(go.TextBlock, "+",  // the Button content
              { font: "bold 8pt sans-serif" })
          )
        );

      // the context menu allows users to change the font size and weight,
      // and to perform a limited tree layout starting at that node
      this.diagram.nodeTemplate.contextMenu =
        $("ContextMenu",
          $("ContextMenuButton",
            $(go.TextBlock, "Bigger"),
            { click: (e, obj) => this.changeTextSize(obj, 1.1) }),
          $("ContextMenuButton",
            $(go.TextBlock, "Smaller"),
            { click: (e, obj) => this.changeTextSize(obj, 1 / 1.1) }),
          $("ContextMenuButton",
            $(go.TextBlock, "Bold/Normal"),
            { click: (e, obj) => this.toggleTextWeight(obj) }),
          $("ContextMenuButton",
            $(go.TextBlock, "Copy"),
            { click: (e, obj) => e.diagram.commandHandler.copySelection() }),
          $("ContextMenuButton",
            $(go.TextBlock, "Delete"),
            { click: (e, obj) => e.diagram.commandHandler.deleteSelection() }),
          $("ContextMenuButton",
            $(go.TextBlock, "Undo"),
            { click: (e, obj) => e.diagram.commandHandler.undo() }),
          $("ContextMenuButton",
            $(go.TextBlock, "Redo"),
            { click: (e, obj) => e.diagram.commandHandler.redo() }),
          $("ContextMenuButton",
            $(go.TextBlock, "Layout"),
            {
              click: (e, obj) => {
                var adorn = obj.part;
                if (!adorn || !adorn.diagram) return;
                adorn.diagram.startTransaction("Subtree Layout");
                //this.layoutTree(adorn.ad .adornedPart);
                adorn.diagram.commitTransaction("Subtree Layout");
              }
            }
          )
        );

      // a link is just a Bezier-curved line of the same color as the node to which it is connected
      this.diagram.linkTemplate =
        $(go.Link,
          {
            curve: go.Link.Bezier,
            fromShortLength: -2,
            toShortLength: -2,
            selectable: false
          },
          $(go.Shape,
            { strokeWidth: 3 },
            new go.Binding("stroke", "toNode", n => {
              if (n.data.brush) return n.data.brush;
              return "black";
            }).ofObject())
        );

      // the Diagram's context menu just displays commands for general functionality
      this.diagram.contextMenu =
        $("ContextMenu",
          $("ContextMenuButton",
            $(go.TextBlock, "Paste"),
            { click: (e, obj) => e.diagram.commandHandler.pasteSelection(e.diagram.toolManager.contextMenuTool.mouseDownPoint) },
            new go.Binding("visible", "", o => o.diagram && o.diagram.commandHandler.canPasteSelection(o.diagram.toolManager.contextMenuTool.mouseDownPoint)).ofObject()),
          $("ContextMenuButton",
            $(go.TextBlock, "Undo"),
            { click: (e, obj) => e.diagram.commandHandler.undo() },
            new go.Binding("visible", "", o => o.diagram && o.diagram.commandHandler.canUndo()).ofObject()),
          $("ContextMenuButton",
            $(go.TextBlock, "Redo"),
            { click: (e, obj) => e.diagram.commandHandler.redo() },
            new go.Binding("visible", "", o => o.diagram && o.diagram.commandHandler.canRedo()).ofObject()),
          $("ContextMenuButton",
            $(go.TextBlock, "Save"),
            { click: (e, obj) => this.save() }),
          $("ContextMenuButton",
            $(go.TextBlock, "Load"),
            { click: (e, obj) => this.load() }),
          $("ContextMenuButton",
            $(go.TextBlock, "layoutAll"),
            { click: (e, obj) => this.layoutAll()})
        );

      this.diagram.addDiagramListener("SelectionMoved", e => {
        if (!this.diagram) return;
        if (!this.diagram.findNodeForKey) return;
        if (!this.diagram.findNodeForKey(0)) return;
        if (!this.diagram.findNodeForKey(0)?.location) return;

        var rootX = this.diagram.findNodeForKey(0)?.location.x;

        this.diagram.selection.each(node => {
          if (node.data.parent !== 0) return; // Only consider nodes connected to the root
          var nodeX = node.location.x;
          if (rootX && rootX < nodeX && node.data.dir !== "right") {
            this.updateNodeDirection(node, "right");
          } else if (rootX && rootX > nodeX && node.data.dir !== "left") {
            this.updateNodeDirection(node, "left");
          }
          this.layoutTree(node);
        });
      });

      // read in the predefined graph using the JSON format data held in the "mySavedModel" textarea
      this.load();
      // make the graph beautiful
      this.layoutAll();
    }

    spotConverter(dir: any, from: any): go.Spot {
      if (dir === "left") {
        return (from ? go.Spot.Left : go.Spot.Right);
      } else {
        return (from ? go.Spot.Right : go.Spot.Left);
      }
    }

    changeTextSize(obj: any, factor: any) {
      var adorn = obj.part;
      adorn.diagram.startTransaction("Change Text Size");
      var node = adorn.adornedPart;
      var tb = node.findObject("TEXT");
      tb.scale *= factor;
      adorn.diagram.commitTransaction("Change Text Size");
    }

    toggleTextWeight(obj: any) {
      var adorn = obj.part;
      adorn.diagram.startTransaction("Change Text Weight");
      var node = adorn.adornedPart;
      var tb = node.findObject("TEXT");
      // assume "bold" is at the start of the font specifier
      var idx = tb.font.indexOf("bold");
      if (idx < 0) {
        tb.font = "bold " + tb.font;
      } else {
        tb.font = tb.font.slice(idx + 5);
      }
      adorn.diagram.commitTransaction("Change Text Weight");
    }

    updateNodeDirection(node: any, dir: any) {
      if (!this.diagram) return;
      this.diagram.model.setDataProperty(node.data, "dir", dir);
      // recursively update the direction of the child nodes
      var chl = node.findTreeChildrenNodes(); // gives us an iterator of the child nodes related to this particular node
      while (chl.next()) {
        this.updateNodeDirection(chl.value, dir);
      }
    }

    addNodeAndLink(e: any, obj: any) {
      var adorn = obj.part;
      var diagram = adorn.diagram;
      diagram.startTransaction("Add Node");
      var oldnode = adorn.adornedPart;
      var olddata = oldnode.data;
      // copy the brush and direction to the new node data
      var newdata = { text: "idea", brush: olddata.brush, dir: olddata.dir, parent: olddata.key };
      diagram.model.addNodeData(newdata);
      this.layoutTree(oldnode);
      diagram.commitTransaction("Add Node");

      // if the new node is off-screen, scroll the diagram to show the new node
      var newnode = diagram.findNodeForData(newdata);
      if (newnode !== null) diagram.scrollToRect(newnode.actualBounds);
    }

    layoutTree(node: any) {
      if (node.data.key === 0) {  // adding to the root?
        this.layoutAll();  // lay out everything
      } else {  // otherwise lay out only the subtree starting at this parent node
        var parts = node.findTreeParts();
        this.layoutAngle(parts, node.data.dir === "left" ? 180 : 0);
      }
    }

    layoutAngle(parts: any, angle: any) {
      var layout = go.GraphObject.make(go.TreeLayout,
        {
          angle: angle,
          arrangement: go.TreeLayout.ArrangementFixedRoots,
          nodeSpacing: 5,
          layerSpacing: 20,
          setsPortSpot: false, // don't set port spots since we're managing them with our spotConverter function
          setsChildPortSpot: false
        });
      layout.doLayout(parts);
    }

    layoutAll() {
      if (!this.diagram) return
      var root = this.diagram.findNodeForKey(0);
      if (root === null) return;
      this.diagram.startTransaction("Layout");
      // split the nodes and links into two collections
      var rightward = new go.Set(/*go.Part*/);
      var leftward = new go.Set(/*go.Part*/);
      root.findLinksConnected().each(link => {
        var child = link.toNode;
        if (!child)  return;
        
        if (child.data.dir === "left") {
          leftward.add(root);  // the root node is in both collections
          leftward.add(link);
          leftward.addAll(child.findTreeParts());
        } else {
          rightward.add(root);  // the root node is in both collections
          rightward.add(link);
          rightward.addAll(child.findTreeParts());
        }
      });
      // do one layout and then the other without moving the shared root node
      this.layoutAngle(rightward, 0);
      this.layoutAngle(leftward, 180);
      this.diagram.commitTransaction("Layout");
    }

    // Show the diagram's model in JSON format
    save() {
      if (!this.diagram) return
      (document.getElementById("mySavedModel") as HTMLTextAreaElement).value = this.diagram.model.toJson();
      this.diagram.isModified = false;
    }

    // update the IDP schema for the current employee
    update() {
      if (!this.diagram) return
      // trying to send a put request to update the IPD schema
      this.employeeService.updateIDPSchema(new UpdateIdpRequest(this.employee.login, this.diagram.model.toJson()))
      .subscribe({
        next: (response: any) => {
         this.employee = response;
         this.storageService.saveUser(this.employee);
        },
        error: (error: HttpErrorResponse) => {
          console.log(error.message);
        },
        complete: () => console.info('complete') 
      });
    }

    onLoad() {
      if (!this.diagram) return
      this.diagram.model = go.Model.fromJson((document.getElementById("mySavedModel") as HTMLTextAreaElement).value);
    }
    
    load() {
      if (!this.diagram) return
      //this.diagram.model = go.Model.fromJson((document.getElementById("mySavedModel") as HTMLTextAreaElement).value);
      this.diagram.model = go.Model.fromJson(this.data);
    }

}
