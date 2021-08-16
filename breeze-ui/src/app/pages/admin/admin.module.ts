import { NgModule } from '@angular/core';
import { DictComponent } from './dict/dict.component';
import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin.routing.module';
import { SharedModule } from 'src/app/@shared/shared.module';
import { DictTypeNewComponent } from './dict/dict-type-new/dict-type-new.component';
import { DictTypeUpdateComponent } from './dict/dict-type-update/dict-type-update.component';
import { DictTypeDeleteComponent } from './dict/dict-type-delete/dict-type-delete.component';
import { DictDataNewComponent } from './dict/dict-data-new/dict-data-new.component';
import { DictDataUpdateComponent } from './dict/dict-data-update/dict-data-update.component';
import { DictDataDeleteComponent } from './dict/dict-data-delete/dict-data-delete.component';
import { UserComponent } from './user/user.component';
import { RoleComponent } from './role/role.component';
import { PrivilegeComponent } from './privilege/privilege.component';
import { DeptComponent } from './dept/dept.component';
import { RoleNewComponent } from './role/role-new/role-new.component';
import { RoleUpdateComponent } from './role/role-update/role-update.component';
import { RoleDeleteComponent } from './role/role-delete/role-delete.component';

@NgModule({
  declarations: [DictComponent, AdminComponent, DictTypeNewComponent, DictTypeUpdateComponent, DictTypeDeleteComponent, DictDataNewComponent, DictDataUpdateComponent, DictDataDeleteComponent, UserComponent, RoleComponent, PrivilegeComponent, DeptComponent, RoleNewComponent, RoleUpdateComponent, RoleDeleteComponent],
  imports: [SharedModule, AdminRoutingModule],
})
export class AdminModule {}
