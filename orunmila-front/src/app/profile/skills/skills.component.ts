import { Component, OnInit } from '@angular/core';
import { CardSkills, SkillProgres } from 'src/app/_models/cardSkills';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.css']
})
export class SkillsComponent implements OnInit {

  data?: Array<CardSkills>;
  s: string = `[
    {
      "header": "Hard",
      "skills": [
        {
          "title": "Java",
          "progress": 5
        },
        {
          "title": "jopa",
          "progress": 10
        },
        {
          "title": "jopa",
          "progress": 15
        },
        {
          "title": "jopa",
          "progress": 25
        },
        {
          "title": "jopa",
          "progress": 55
        }  
      ]
    },
    {
      "header": "Soft",
      "skills": [
        {
          "title": "Java",
          "progress": 20
        },
        {
          "title": "polnaya jopa",
          "progress": 10
        },
        {
          "title": "jopa",
          "progress": 15
        },
        {
          "title": "jopa",
          "progress": 25
        },
        {
          "title": "jopa",
          "progress": 100
        }  
      ]
    }
  ]`;

  constructor() { }

  ngOnInit(): void {
    this.data = JSON.parse(this.s);
    if (this.data) {
      for (let d of this.data) {
        for (let k of d.skills) {
          if (k.progress < 20) {
            k.color = "bg-danger";
            //orange
          } else if (k.progress < 40) {
            k.color = "bg-warning";
          } else if (k.progress < 60) {
            k.color = " bg-info";
          } else if (k.progress < 80) {
            k.color = "bg-primary";
          } else {
            k.color = "bg-success";
          }
        }
       
        }
      }
    }
    
  }

