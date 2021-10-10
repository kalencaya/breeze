import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DValidateRules, FormLayout } from 'ng-devui';
import { DictData } from 'src/app/@core/data/admin.data';
import { Dict } from 'src/app/@core/data/app.data';
import { DictDataService } from 'src/app/@core/services/dict-data.service';
import { DictTypeService } from 'src/app/@core/services/dict-type.service';

@Component({
  selector: 'app-dict-data-new',
  templateUrl: './dict-data-new.component.html',
  styleUrls: ['./dict-data-new.component.scss'],
})
export class DictDataNewComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  formLayout = FormLayout.Horizontal;
  formConfig: { [Key: string]: DValidateRules } = {
    rule: { message: this.translate.instant('app.error.formValidateError'), messageShowType: 'text' },
    dictTypeRules: {
      validators: [{ required: true }],
    },
    dictCodeRules: {
      validators: [
        { required: true },
        { maxlength: 30 },
        { pattern: /^[a-zA-Z0-9_]+$/, message: this.translate.instant('app.common.validate.characterWord') },
      ],
    },
    dictValueRules: {
      validators: [{ required: true }, { maxlength: 100 }],
    },
    remarkRules: {
      validators: [{ maxlength: 200 }],
    },
  };

  formData = {
    dictType: null,
    dictCode: null,
    dictValue: null,
    remark: null,
  };
  dictTypeList: Dict[] = [];

  constructor(
    private dictTypeService: DictTypeService,
    private dictDataService: DictDataService,
    private elr: ElementRef,
    private translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
    this.dictTypeService.listAll().subscribe((d) => {
      this.dictTypeList = d;
    });
  }

  submitForm({ valid }) {
    let dict: DictData = {
      dictCode: this.formData.dictCode,
      dictValue: this.formData.dictValue,
      remark: this.formData.remark,
      dictType: {
        dictTypeCode: this.formData.dictType?.value,
        dictTypeName: this.formData.dictType?.label,
      },
    };
    if (valid) {
      this.dictDataService.add(dict).subscribe((d) => {
        if (d.success) {
          this.data.onClose();
          this.data.refresh();
        }
      });
    }
  }
}
