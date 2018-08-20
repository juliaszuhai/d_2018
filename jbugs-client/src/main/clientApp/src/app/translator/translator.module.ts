import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslatorComponent } from './translator.component';
import {FormsModule} from "@angular/forms";
import {MatMenuModule, MatIconModule, MatButtonModule} from '@angular/material';
@NgModule({
  imports: [
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
