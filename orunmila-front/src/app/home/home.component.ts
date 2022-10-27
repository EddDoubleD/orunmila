import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EmployeeService } from '../_services/employee.service';
import { SearchQuery } from '../_models/searchQuery';
import { StoreService } from '../_services/store.service';
import { StorageService } from '../_services/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  q: SearchQuery = new SearchQuery(new String());

  constructor(private employeeService: EmployeeService, 
    private route: Router, 
    private storageService: StorageService) {}

  ngOnInit(): void {
    
  }

  public search() {
    this.employeeService.getEmployeeByLoginQ(this.q).subscribe({
      next: (response: any) => {
        this.storageService.saveUser(response);
        this.route.navigate(['profile/details'])
      },
      error: (error: HttpErrorResponse) => {
        alert("По запросу [text=" + this.q.text + "] ни чего не найдено");
        this.q.text = new String();
        console.log(error.message);
      },
      complete: () => console.info('complete') 
    });
  }

}
