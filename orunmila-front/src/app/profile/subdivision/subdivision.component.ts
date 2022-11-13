import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'src/app/_services/employee.service';
import { StorageService } from 'src/app/_services/storage.service';

@Component({
  selector: 'app-subdivision',
  templateUrl: './subdivision.component.html',
  styleUrls: ['./subdivision.component.css']
})
export class SubdivisionComponent implements OnInit {
  employee?: any;

  p: number = 1;
  s: number = 3;
  total: number = 0;
  itemsSize: number = 0;
  users: any;

  constructor(private storageService: StorageService,
    private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employee = this.storageService.getUser();
  }

  public getUsers(): void {
    this.employeeService.getPageableEmployees(this.p, this.s).subscribe(
      (response: any) => {
          this.users = response.users;
          this.total = response.totalPages;
          this.itemsSize = response.itemsSize;
      }),
      (error: HttpErrorResponse) => {
        if (error.error) {
          alert(error.error.message);
        } else {
          alert(`Error with status: ${error.status}`);
        }
      }
  }
  
  /**
   * Write code on Method
   *
   * @return response()
   */
   pageChangeEvent(event: number) {
    this.p = event;
    this.getUsers();
  }

}
