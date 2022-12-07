import { HttpErrorResponse } from '@angular/common/http';
import { Expansion } from '@angular/compiler';
import { Input, Component, OnInit, ɵsetAllowDuplicateNgModuleIdsForTest } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { EmployeeService } from '../_services/employee.service';
import { StorageService } from '../_services/storage.service';
import { StoreService } from '../_services/store.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  employee: any;
  
  subscription!: Subscription;
  id: number = 0;
  private v?: any;


  constructor(
    private storageService: StorageService,
    private router: ActivatedRoute) { }
  

  ngOnInit(): void {
    // 
    this.subscription = this.router.queryParams.subscribe(params => {
      try {
        this.id = Number(params['id']);
        if (Number.isNaN(this.id)) {
           this.id = 0; 
        }
      } catch(e) {
         this.id = 0; 
      }
    }); 
    
    const elements = ((document.body.querySelector("#menu") as HTMLDivElement)
    .querySelector("ul") as HTMLUListElement).querySelectorAll("li");
      
    for(var i = 0; i < elements.length; i++){
      if (this.id == i) {
        elements[i].className = "active";
      } else {
        elements[i].className = "";
      }
    }
    
    this.employee = this.storageService.getUser();
  }

  public changeMenu(num: any) {
    
  }
    
}
