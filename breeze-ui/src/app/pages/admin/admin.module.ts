import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DictComponent } from './dict/dict.component';
import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin.routing.module';

@NgModule({
  declarations: [DictComponent, AdminComponent],
  imports: [CommonModule, AdminRoutingModule],
})
export class AdminModule {}
