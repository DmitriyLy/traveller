import { Component, OnInit } from '@angular/core';
import {CityService} from "../city.service";
import {City} from "../city";

@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.css']
})
export class CitiesComponent implements OnInit {

  rowsPerPage = 10;
  cities: City[];

  constructor(private cityService: CityService) {
    cityService.getCities().subscribe(response => this.cities = response);
  }

  ngOnInit() {
  }

  isRegionCenter(city: any): boolean {
    if (city.district) {
      return false;
    }
    return true;
  }
}
