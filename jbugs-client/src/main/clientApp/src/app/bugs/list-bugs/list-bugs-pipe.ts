import {Pipe, PipeTransform} from "@angular/core";
import {BugData} from "../bugs.service";

@Pipe({name: "sortBy", pure: false})
export class ListBugsPipe implements PipeTransform {

  bugs: BugData[];

  transform(bugs: BugData[], args, dummyNumber) {
    console.log(args);
    console.log(dummyNumber);
    args.forEach(arg => {

      //   bugs.sort((bug1: BugData, bug2: BugData)=> {
      //   if (bug1[arg.argument] > bug2[arg.argument]) {
      //     return arg.order == "asc" ? 1 : -1;
      //   } else if (bug1[arg.argument] < bug2[arg.argument]) {
      //     return arg.order == "asc" ? -1 : 1;
      //   }
      //   else return 0;
      // });
    });

    return bugs
  }

}
