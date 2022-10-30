export class MindMap {
    constructor(){}; 
    
    private class: string = 'go.TreeModel';
    private nodeDataArray: any;

    public setClass(c: string) : void {
        this.class = c;
    }

    public setNodeArray(arr: any) : void {
        this.nodeDataArray = arr;
    }
}