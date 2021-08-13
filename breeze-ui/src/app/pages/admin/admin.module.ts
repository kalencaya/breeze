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

@NgModule({
  declarations: [DictComponent, AdminComponent, DictTypeNewComponent, DictTypeUpdateComponent, DictTypeDeleteComponent, DictDataNewComponent, DictDataUpdateComponent, DictDataDeleteComponent],
  imports: [SharedModule, AdminRoutingModule],
})
export class AdminModule {}
