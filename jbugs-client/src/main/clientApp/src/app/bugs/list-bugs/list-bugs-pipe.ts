import {Pipe, PipeTransform} from "@angular/core";
import {BugData} from "../bugs.service";

@Pipe({name: "sortBy"})
export class ListBugsPipe implements PipeTransform {


  transform(bugs: BugData[],args) {
    args.forEach(arg => {
      bugs.sort((bug1: BugData, bug2: BugData)=> {
        if (bug1[arg.argument] < bug2[arg.argument]){
          return arg.order == "desc"? -1: 1;
        }
        else return 0;
      });
    });

    return bugs
  }

}
