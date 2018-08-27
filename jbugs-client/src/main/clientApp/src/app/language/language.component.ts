import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";



export class Language {

  Id: string;
  Name: string;

  constructor(lngId: string, lngName: string){
      this.Id = lngId;
      this.Name = lngName;
  }

}
@Component({
  selector: 'language-dropdown',
  templateUrl: './language.component.html',

  styleUrls: ['./language.component.css']
})
export class LanguageComponent implements OnInit {

  @Input()
  allLanguages: Language[];


  constructor(private translate: TranslateService){
  }

  ngOnInit(): void {

    this.allLanguages = [
      new Language('en', 'English'),
      new Language('ro', 'Romana')
    ];

  }

  public changeLanguage(event, id){
    this.translate.use(id);

  }

}
