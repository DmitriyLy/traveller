import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class CityService {

  constructor(private http: HttpClient) { }

  getCities() : Observable<any> {
    return this.http.get('api/cities');
    //return Observable.of([{name: 'Odessa', region: 'Odessa', district: ''}]);
  }

}
