import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslatorComponent } from './translator.component';
import {FormsModule} from "@angular/forms";
import {MatMenuModule, MatIconModule, MatButtonModule} from '@angular/material';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {HttpLoaderFactory} from "../app.module";
import {HttpClient} from "@angular/common/http";
@NgModule({
  imports: [
    TranslateModule.forChild({

      loader: {

        provide: TranslateLoader,

        useFactory: HttpLoaderFactory,

        deps: [HttpClient]

      }

    }),
    CommonModule,
    FormsModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule
  ],
  declarations: [TranslatorComponent],
  exports:[TranslatorComponent]
})
export class TranslatorModule { }
