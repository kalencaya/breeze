import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DValidateRules, FormLayout } from 'ng-devui';

@Component({
  selector: 'app-source-table-step',
  templateUrl: './source-table-step.component.html',
  styleUrls: ['../../workbench.component.scss'],
})
export class SourceTableStepComponent implements OnInit {
  idlist: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 12, 13];
  @Input() data;
  formLayout = FormLayout.Vertical;
  formConfig: { [Key: string]: DValidateRules } = {
    rule: { message: this.translate.instant('app.error.formValidateError'), messageShowType: 'text' },
    projectCodeRules: {
      validators: [
        { required: true },
        { maxlength: 30 },
        { pattern: /^[a-zA-Z0-9_]+$/, message: this.translate.instant('app.common.validate.characterWord') },
      ],
    },
    projectNameRules: {
      validators: [{ required: true }, { maxlength: 60 }],
    },
    remarkRules: {
      validators: [{ maxlength: 200 }],
    },
  };

  formData = {
    projectCode: null,
    projectName: null,
    remark: null,
  };

  constructor(private translate: TranslateService) {}

  ngOnInit(): void {}

  submitForm() {
    console.log('hello 111');
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
