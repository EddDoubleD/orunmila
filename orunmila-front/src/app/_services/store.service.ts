import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({providedIn: 'root'})
export class StoreService {
    // 
    public employee$ = new Subject<any>();

    public changeEmployee(employee: any) {
        this.employee$.next(employee);
    }
}