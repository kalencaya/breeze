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
import { DeptNewComponent } from './dept/dept-new/dept-new.component';
import { DeptUpdateComponent } from './dept/dept-update/dept-update.component';
import { DeptDeleteComponent } from './dept/dept-delete/dept-delete.component';
import { UserNewComponent } from './user/user-new/user-new.component';
import { UserUpdateComponent } from './user/user-update/user-update.component';
import { UserDeleteComponent } from './user/user-delete/user-delete.component';
import { RoleGrantComponent } from './role/role-grant/role-grant.component';
import { DeptGrantComponent } from './dept/dept-grant/dept-grant.component';

@NgModule({
  declarations: [DictComponent, AdminComponent, DictTypeNewComponent, DictTypeUpdateComponent, DictTypeDeleteComponent, DictDataNewComponent, DictDataUpdateComponent, DictDataDeleteComponent, UserComponent, RoleComponent, PrivilegeComponent, DeptComponent, RoleNewComponent, RoleUpdateComponent, RoleDeleteComponent, DeptNewComponent, DeptUpdateComponent, DeptDeleteComponent, UserNewComponent, UserUpdateComponent, UserDeleteComponent, RoleGrantComponent, DeptGrantComponent],
  imports: [SharedModule, AdminRoutingModule],
})
export class AdminModule {}
