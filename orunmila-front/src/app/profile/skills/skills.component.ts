import { Component, OnInit } from '@angular/core';
import { CardSkills, SkillProgres } from 'src/app/_models/cardSkills';
import { StorageService } from 'src/app/_services/storage.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.css']
})
export class SkillsComponent implements OnInit {

  data?: Array<CardSkills>;

  employee: any;

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
    // 
    this.employee = this.storageService.getUser();

    this.data = this.employee.skills;
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

