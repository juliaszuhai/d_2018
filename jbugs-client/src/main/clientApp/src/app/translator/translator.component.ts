import {Component, Input, OnInit} from '@angular/core';
import {TranslatorService} from "./translator.service";
import {Router} from "@angular/router";


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


  constructor(private translatorService:TranslatorService){
  }

  ngOnInit(): void {

    this.allLanguages = [
      new Language(0, 'English'),
      new Language(1, 'Romana')
    ];

  }

  public changeLanguage(event, id){
    this.translatorService.getTranslationObservable(id);

  }

}
