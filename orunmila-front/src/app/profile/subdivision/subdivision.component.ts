import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'src/app/_services/employee.service';
import { StorageService } from 'src/app/_services/storage.service';

@Component({
  selector: 'app-subdivision',
  templateUrl: './subdivision.component.html',
  styleUrls: ['./subdivision.component.css']
})
export class SubdivisionComponent implements OnInit {

  employees?: any[];

  constructor(private storageService: StorageService,
    private employeeService: EmployeeService) { }

  ngOnInit(): void {
  }

}
