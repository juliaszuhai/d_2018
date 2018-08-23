import {Component, Input, OnInit} from '@angular/core';
import {TranslatorService} from "./translator.service";
import {element} from "protractor";


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

  json:Object;


  constructor(private translateService:TranslatorService){

  }

  ngOnInit(): void {

    this.allLanguages = [
      new Language(0, 'English'),
      new Language(1, 'Romana')
    ]

  }

  public changeLanguage(event, id){
    console.log("Selected language : ", this.allLanguages[id].Name);
    sessionStorage.setItem("lng", id.toString());
    //this.translateService.getLanguageFile(id);
    this.getTranslation(id);

  }

  public getTranslation(id : number){
    this.translateService.getLanguageFile(id)
      .subscribe(lng =>this.json=lng)
  }
/*

  execute(){
    element = document.getElementById("title");
  }
*/

}
