import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JobComponent } from './job/job.component';
import { ProjectComponent } from './project/project.component';
import { StudioComponent } from './studio.component';

const routes: Routes = [
  {
    path: '',
    component: StudioComponent,
    children: [
      { path: 'project', component: ProjectComponent },
      { path: 'job', component: JobComponent },
      { path: '', redirectTo: 'project', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudioRoutingModule {}
