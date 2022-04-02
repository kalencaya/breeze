import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { STEP_ATTR_TYPE } from 'src/app/@core/data/studio.data';
import { DiJobService } from 'src/app/@core/services/di-job.service';

@Component({
  selector: 'app-step-propertity',
  templateUrl: './step-propertity.component.html',
  styleUrls: ['../workbench.component.scss'],
})
export class StepPropertityComponent implements OnInit {
  @ViewChild('step') step;
  @Input() fullScreen;
  @Input() close;
  @Input() refresh;
  @Input() cell;
  @Input() jobId;
  @Input() jobGraph;
  isFullScreen = false;
  stepTitle: string = '';
  stepType: string = '';
  constructor(private translate: TranslateService, private jobService: DiJobService) {}

  ngOnInit(): void {
    this.stepType = this.cell.data?.type + '-' + this.cell.data?.name;
    this.stepTitle = this.translate.instant('studio.step.' + this.stepType);
  }

  toggleFullScreen() {
    this.isFullScreen = !this.isFullScreen;
    this.fullScreen();
  }

  submitForm() {
    //触发子组件的submitForm方法
    this.step.submitForm();
  }

  saveStepInfo(map: Map<string, string>) {
    map.set(STEP_ATTR_TYPE.jobGraph, this.jobGraph);
    map.set(STEP_ATTR_TYPE.jobId, this.jobId);
    map.set(STEP_ATTR_TYPE.stepCode, this.cell.id);
    this.jobService.saveStepAttr(map).subscribe((d) => {
      if (d.success) {
        this.refresh();
        this.close();
      }
    });
  }
}
