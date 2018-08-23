import {Component, Input, OnInit} from '@angular/core';
import {TranslatorService} from "./translator.service";
import {Observable} from "rxjs/Observable";


export class Language {

  Id: number;
  Name: string;


  constructor(lngId: number, lngName: string){
      this.Id = lngId;
      this.Name = lngName;
  }



}
@Component({
  selector: 'language-dropdown',
  templateUrl: './translator.component.html',

  styleUrls: ['./translator.component.css']
})
export class TranslatorComponent implements OnInit {

  @Input()
  allLanguages: Language[];
  //toggle:boolean = false;


  constructor(private translateService:TranslatorService){


  }

  ngOnInit(): void {

    this.allLanguages = [
      new Language(0, 'English'),
      new Language(1, 'Romana')
    ];
    this.translateService.getTranslationObservable(0);
  }

  public changeLanguage(event, id){
    sessionStorage.setItem("lng", String(id));
    this.translateService.getTranslationObservable(id);
  }






}
