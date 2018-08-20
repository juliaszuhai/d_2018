import {Component, Input, OnInit} from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
export class Language {

  Id: number
  Name: string

  constructor(lngId: number, lngName: string){
      this.Id = lngId;
      this.Name = lngName;
  }

  allLanguages = [
    new Language(1, 'English'),
    new Language(2, 'Romana')
  ]

}
@Component({
  selector: 'language-dropdown',
  templateUrl: './translator.component.html',
  styleUrls: ['./translator.component.css']
})
export class TranslatorComponent implements OnInit {

  @Input()
  allLanguages: Language[];
  constructor(){

  }
  translateForm() {
    console.log("Translator chosen");

  }
  ngOnInit(): void {

  }

}
