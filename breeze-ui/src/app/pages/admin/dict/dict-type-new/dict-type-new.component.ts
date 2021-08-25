import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { DValidateRules } from 'ng-devui';
import { DictType } from 'src/app/@core/data/admin.data';
import { DictTypeService } from 'src/app/@core/services/dict-type.service';

@Component({
  selector: 'app-dict-type-new',
  templateUrl: './dict-type-new.component.html',
  styleUrls: ['./dict-type-new.component.scss'],
})
export class DictTypeNewComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;

  formConfig: { [Key: string]: DValidateRules } = {
    rule: { message: this.translate.instant('app.error.formValidateError'), messageShowType: 'text' },
    dictTypeCodeRules: {
      validators: [
        { required: true },
        { maxlength: 30 },
        { pattern: /^[a-zA-Z0-9_]+$/, message: this.translate.instant('app.common.validate.characterWord') },
      ],
    },
    dictTypeNameRules: {
      validators: [{ required: true }, { maxlength: 100 }],
    },
    remarkRules: {
      validators: [{ maxlength: 200 }],
    },
  };

  formData: DictType = {
    dictTypeCode: null,
    dictTypeName: null,
    remark: null,
  };

  constructor(private dictTypeService: DictTypeService, private elr: ElementRef, private translate: TranslateService) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
  }

  submitForm({ valid }) {
    if (valid) {
      this.dictTypeService.add(this.formData).subscribe((d) => {
        if (d.success) {
          this.data.onClose();
          this.data.refresh();
        }
      });
    }
  }
}
