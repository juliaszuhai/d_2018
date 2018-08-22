import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from "ng2-translate";
import {TranslatorService} from "./translator.service";


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

  constructor(private translateService:TranslatorService){

  }
  translateForm() {
    console.log("Translator chosen");

  }
  ngOnInit(): void {

    this.allLanguages = [
      new Language(0, 'English'),
      new Language(1, 'Romana')
    ]
  }

  public open(event, id){
    this.translateService.getLocal(id);
      console.log("Selected language index", id);

  }


}
