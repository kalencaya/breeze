import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DFormGroupRuleDirective, DValidateRules, FormLayout } from 'ng-devui';
import { DiJob } from 'src/app/@core/data/studio.data';
import { DiJobService } from 'src/app/@core/services/di-job.service';

@Component({
  selector: 'app-job-new',
  templateUrl: './job-new.component.html',
  styleUrls: ['../job.component.scss'],
})
export class JobNewComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  @ViewChild('form') formDir: DFormGroupRuleDirective;
  jobType: string;
  projectId: number;
  formLayout = FormLayout.Horizontal;
  formConfig: { [Key: string]: DValidateRules } = {
    rule: { message: this.translate.instant('app.error.formValidateError'), messageShowType: 'text' },
    jobCodeRules: {
      validators: [
        { required: true },
        { maxlength: 120 },
        { pattern: /^[a-zA-Z0-9_]+$/, message: this.translate.instant('app.common.validate.characterWord') },
      ],
    },
    jobNameRules: {
      validators: [{ required: true }, { maxlength: 200 }],
    },
    directoryRules: {
      validators: [{ required: true }],
    },
    remarkRules: {
      validators: [{ maxlength: 200 }],
    },
  };

  formData = {
    jobCode: null,
    jobName: null,
    directory: null,
    remark: null,
  };

  data1 = [
    {
      title: 'parent 1',
      id: 1,
    },
    {
      title: 'parent 2',
      children: [
        {
          title: 'parent 2-1',
          children: [
            {
              title: 'leaf 2-1-1',
              id: 3,
            },
            {
              title: 'leaf 2-1-2',
              id: 4,
            },
          ],
          id: 2,
        },
        {
          title: 'parent 2-2',
          children: [
            {
              title: 'leaf 2-2-1',
              id: 6,
            },
            {
              title: 'leaf 2-2-2',
              id: 7,
            },
          ],
          id: 5,
        },
      ],
      id: 18,
    },
    {
      title: 'parent 3',
      children: [
        {
          title: 'leaf 3-1',
          id: 9,
        },
        {
          title: 'leaf 3-2',
          id: 10,
        },
        {
          title: 'leaf 3-3',
          id: 11,
        },
      ],
      id: 8,
    },
    {
      title: 'parent 4',
      children: [
        {
          title: 'leaf 4-1',
          id: 13,
        },
        {
          title: 'leaf 4-2',
          id: 14,
        },
      ],
      id: 12,
    },
    {
      title: 'parent 5',
      children: [
        {
          title: 'leaf 5-1',
          id: 16,
        },
        {
          title: 'leaf 5-2',
          id: 17,
        },
      ],
      id: 15,
    },
  ];

  constructor(private elr: ElementRef, private translate: TranslateService, private jobService: DiJobService) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
  }

  submitForm({ valid }) {
    let job: DiJob = {
      projectId: this.projectId,
      jobCode: this.formData.jobCode,
      jobName: this.formData.jobName,
      directory: this.formData.directory,
      jobType: { label: '', value: this.jobType },
      remark: this.formData.remark,
    };
    if (valid) {
      this.jobService.add(job).subscribe((d) => {
        if (d.success) {
          this.data.onClose();
          this.data.refresh();
        }
      });
    }
  }
}
