import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-step-propertity',
  templateUrl: './step-propertity.component.html',
  styleUrls: ['../workbench.component.scss'],
})
export class StepPropertityComponent implements OnInit {
  @ViewChild('step') step;
  @Input() fullScreen;
  @Input() close;
  @Input() item;
  isFullScreen = false;
  stepTitle: string = '';
  stepType: string = '';
  constructor(private translate: TranslateService) {}

  ngOnInit(): void {
    this.stepType = this.item.data?.type + '-' + this.item.data?.name;
    this.stepTitle = this.translate.instant('studio.step.' + this.stepType);
  }

  toggleFullScreen() {
    this.isFullScreen = !this.isFullScreen;
    this.fullScreen();
  }

  submitForm() {
    this.step.submitForm();
  }
}
