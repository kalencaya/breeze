import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DictComponent } from './dict/dict.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      { path: 'dict', component: DictComponent },
      { path: '', redirectTo: 'dict', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
