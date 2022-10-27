import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from './_services/employee.service';
import { SearchQuery } from './_models/searchQuery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})



export class AppComponent {
  q: SearchQuery = new SearchQuery(new String());

  constructor(private employeeService: EmployeeService, private route: Router) {}

  public search() {
    this.employeeService.getEmployeeByLoginQ(this.q).subscribe({
      next: (response: any) => {
        //alert(response.name)
        //this.route.outlet("");
        this.route.navigate(['/home'])
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
