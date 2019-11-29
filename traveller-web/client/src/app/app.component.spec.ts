import {TestBed, async} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {CitiesComponent} from './cities/cities.component';
import {LanguageComponent} from './language/language.component';
import {TranslateModule, TranslateService} from '@ngx-translate/core';
import {CityService} from './city.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('AppComponent', () => {

  let httpController: HttpTestingController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent, CitiesComponent, LanguageComponent
      ],
      imports: [TranslateModule.forRoot(), HttpClientTestingModule],
      providers: [TranslateService, CityService]
    }).compileComponents();

    httpController = TestBed.get(HttpTestingController);
  }));

  afterEach(() => {
    httpController.verify();
  });

  it('should create the app', async(() => {

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;

    let cityRequest = httpController.expectOne('http://localhost:8090/api/cities');
    expect(app).toBeTruthy();

    cityRequest.flush({});
  }));
});
