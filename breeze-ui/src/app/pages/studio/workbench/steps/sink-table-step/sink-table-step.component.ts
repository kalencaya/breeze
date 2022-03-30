import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sink-table-step',
  templateUrl: './sink-table-step.component.html',
  styleUrls: ['../../workbench.component.scss'],
})
export class SinkTableStepComponent implements OnInit {
  @Input() data;
  constructor() {}

  ngOnInit(): void {
    console.log(this.data.item);
  }

  submitForm() {
    console.log('hello 222');
    this.data.onClose();

    // let ds: DiProject = {
    //   projectCode: this.formData.projectCode,
    //   projectName: this.formData.projectName,
    //   remark: this.formData.remark,
    // };
    // if (valid) {
    //   this.projectService.add(ds).subscribe((d) => {
    //     if (d.success) {
    //       this.data.onClose();
    //       this.data.refresh();
    //     }
    //   });
    // }
  }
}
