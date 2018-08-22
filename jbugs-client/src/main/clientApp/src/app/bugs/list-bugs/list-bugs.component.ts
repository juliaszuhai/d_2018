import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BugData, BugListService, RelatedUser} from "../bugs.service";

@Component({
  selector: 'app-list-bugs',
  templateUrl: './list-bugs.component.html',
  styleUrls: ['./list-bugs.component.css']
})
export class ListBugsComponent implements OnInit {

  bugData: BugData;
  relatedUser: RelatedUser;
  bugList: BugData[];
  bugsByTitle: BugData[];
  bugsByStatus: BugData[];
  bugsBySeverity: BugData[];
  bugsByDescription: BugData[];



  constructor(private bugService: BugListService) {

    this.bugData = {
      title: '',
      description: '',
      version: '',
      targetDate: null,
      status: '',
      fixedVersion: '',
      severity: '',
      createdByUser: null,
      assignedTo: null
    };

    this.relatedUser = {
      id: 0,
      username: '',


    };
  }

  // submitForm(){
  //   this.bugService.getBugsFromServer()
  //     .subscribe((response) => {
  //       console.log(response);
  //     });
  // }
  ngOnInit() {
    this.bugService.getBugsFromServer().subscribe(
        {
          next: (value: BugData[]) => {
            console.log('received: ' + JSON.stringify(value));
            this.bugList = value;
          }
        }
      );

  }

  getBugsByTitle(title: string){
    this.bugService.getBugsByTitle(title).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugsByTitle = value;
        }
      }
    );
  }

  getBugsByStatus(status: string){
    this.bugService.getBugsByStatus(status).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugsByStatus = value;
        }
      }
    );
  }

  getBugsBySeverity(severity: string){
    this.bugService.getBugsBySeverity(severity).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugsBySeverity = value;
        }
      }
    );
  }

  getBugsByDescription(description: string){
    this.bugService.getBugsByDescription(description).subscribe(
      {
        next: (value: BugData[]) => {
          console.log('received: ' + JSON.stringify(value));
          this.bugsByDescription = value;
        }
      }
    );
  }

  getDate(d){

    const correctSec = d * 1000;
    var expiresAt = new Date(correctSec);

    return expiresAt
  }


}
