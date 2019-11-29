import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {City} from "./city";

@Injectable()
export class CityService {

  constructor(private http: HttpClient) { }

  getCities() : Observable<Array<City>> {
    return this.http.get<Array<City>>('http://localhost:8090/api/cities');
    //return Observable.of([{name: 'Odessa', region: 'Odessa', district: ''}]);
  }

}
