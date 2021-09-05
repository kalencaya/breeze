import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DValidateRules } from 'ng-devui';
import { Role } from 'src/app/@core/data/admin.data';
import { Dict, DICT_TYPE } from 'src/app/@core/data/app.data';
import { DictDataService } from 'src/app/@core/services/dict-data.service';
import { RoleService } from 'src/app/@core/services/role.service';

@Component({
  selector: 'app-role-update',
  templateUrl: './role-update.component.html',
  styleUrls: ['./role-update.component.scss'],
})
export class RoleUpdateComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  roleStatusList: Dict[] = [];

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
    id: null,
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
    });
    this.formData = {
      id: this.data.item.id,
      roleCode: this.data.item.roleCode,
      roleName: this.data.item.roleName,
      roleStatus: this.data.item.roleStatus,
      roleType: this.data.item.roleType,
      roleDesc: this.data.item.roleDesc,
    };
  }

  submitForm({ valid }) {
    if (valid) {
      this.roleService.update(this.formData).subscribe((d) => {
        if (d.success) {
          this.data.onClose();
          this.data.refresh();
        }
      });
    }
  }
}
