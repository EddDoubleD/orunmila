import { HttpErrorResponse } from '@angular/common/http';
import { Input, Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
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

  constructor(
    private storageService: StorageService) { }
  

  ngOnInit(): void {
    this.employee = this.storageService.getUser();
  }

  public changeMenu(num: number) {
    var elements = ((document.body.querySelector("#menu") as HTMLDivElement)
      .querySelector("ul") as HTMLUListElement).querySelectorAll("li");
    
      for(var i = 0; i < elements.length; i++) {
        if (i == num) {
          elements[i].className="active"
        } else {
          elements[i].className=""
        }
        
     }     
  }
    
}
