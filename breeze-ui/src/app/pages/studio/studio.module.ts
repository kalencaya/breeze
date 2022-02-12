import { NgModule } from '@angular/core';
import { StudioComponent } from './studio.component';
import { SharedModule } from 'src/app/@shared/shared.module';
import { StudioRoutingModule } from './studio.routing.module';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WorkbenchComponent } from './workbench/workbench.component';
import { JobComponent } from './job/job.component';

@NgModule({
  declarations: [StudioComponent, WorkbenchComponent, JobComponent],
  imports: [SharedModule, StudioRoutingModule, TranslateModule, FormsModule, ReactiveFormsModule],
})
export class StudioModule {}
