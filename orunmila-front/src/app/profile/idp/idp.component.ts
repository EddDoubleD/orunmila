import { Component, OnInit } from '@angular/core';

import * as gojs from 'gojs';

const $ = gojs.GraphObject.make;

@Component({
  selector: 'app-idp',
  templateUrl: './idp.component.html',
  styleUrls: ['./idp.component.css']
})
export class IdpComponent implements OnInit {
  public diagram?: go.Diagram;
  data: any;

  constructor() { }

  ngOnInit(): void {
  }

}
