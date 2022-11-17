import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SearchQuery } from '../_models/searchQuery';

const API_URL = '/api/employee/';

/**
 * 
 */
@Injectable({providedIn: 'root'})
export class EmployeeService {
    
    /**
     * 
     * @param http 
     */
    constructor(private http: HttpClient){}

    public getEmployeeByLoginQ(q: SearchQuery): Observable<any> {
        return this.http.get<any[]>(API_URL + `findByLogin/` + q.text);
    }


    public getEmployeeByLogin(login: string): Observable<any> {
        return this.http.get<any[]>(API_URL + `findByLogin/` + login);
    }

    /**
     * @returns all find employees
     */
    public getEmployees(): Observable<any[]> {
        return this.http.get<any[]>(API_URL + `all`);
    }

    /**
     * Customizable selection of employees by portion
     * 
     * @param page page number (starts with one) 
     * @param size employee pack size
     * @returns fixed portion of employees
     */
    public getPageableEmployees(page: number, size: number) : Observable<any> {
        return this.http.get<void>(API_URL + `pageable?page=${page}&size=${size}`);
    }
}