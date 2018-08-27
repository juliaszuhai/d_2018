import {Component, Input, OnInit} from '@angular/core';
import {TranslatorService} from "./translator.service";
import {Router} from "@angular/router";
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
  templateUrl: './translator.component.html',

  styleUrls: ['./translator.component.css']
})
export class TranslatorComponent implements OnInit {

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

 /* public switchLanguage(id){
    if(id == 1 && this.translatorService.switch == false)
      this.translatorService.switch = !this.translatorService.switch;
    else if(id == 0 && this.translatorService.switch == true)
      this.translatorService.switch = !this.translatorService.switch;

  }*/
}
