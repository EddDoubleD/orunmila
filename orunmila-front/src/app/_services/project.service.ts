import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


const API_URL = '/api/project/';


@Injectable({providedIn: 'root'})
export class ProjectService {
    constructor(private http: HttpClient){}

    public getProjectByName(name: String): Observable<any> {
        return this.http.get<any[]>(API_URL + `get/` + name);
    }

}