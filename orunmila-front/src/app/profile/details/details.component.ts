import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/_services/storage.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  employee: any;

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
    this.employee = this.storageService.getUser();
  }

}
