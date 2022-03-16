import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DValidateRules, FormLayout } from 'ng-devui';
import { Role } from 'src/app/@core/data/admin.data';
import { Dict, DICT_TYPE } from 'src/app/@core/data/app.data';
import { DictDataService } from 'src/app/@core/services/dict-data.service';
import { RoleService } from 'src/app/@core/services/role.service';

@Component({
  selector: 'app-role-new',
  templateUrl: './role-new.component.html',
  styleUrls: ['../role.component.scss'],
})
export class RoleNewComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  roleStatusList: Dict[] = [];
  formLayout = FormLayout.Horizontal;
  formConfig: { [Key: string]: DValidateRules } = {
    rule: { message: this.translate.instant('app.error.formValidateError'), messageShowType: 'text' },
    roleCodeRules: {
      validators: [
        { required: true },
        { maxlength: 30 },
        { pattern: /^[a-zA-Z0-9_]+$/, message: this.translate.instant('app.common.validate.characterWord') },
      ],
    },
    roleNameRules: {
      validators: [{ required: true }, { maxlength: 60 }],
    },
    roleStatusRules: {
      validators: [{ required: true }],
    },
    roleDescRules: {
      validators: [{ maxlength: 100 }],
    },
  };

  formData: Role = {
    roleCode: null,
    roleName: null,
    roleType: null,
    roleStatus: null,
    roleDesc: null,
  };

  constructor(
    private elr: ElementRef,
    private translate: TranslateService,
    private roleService: RoleService,
    private dictDataService: DictDataService
  ) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
    this.dictDataService.listByType(DICT_TYPE.roleStatus).subscribe((d) => {
      this.roleStatusList = d;
      this.formData.roleStatus = d.find((item, index) => {
        return item.value == '1';
      });
    });
  }

  submitForm({ valid }) {
    if (valid) {
      this.roleService.add(this.formData).subscribe((d) => {
        if (d.success) {
          this.data.onClose();
          this.data.refresh();
        }
      });
    }
  }
}
