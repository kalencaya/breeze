import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';

const routes: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
      // {
      //   path: 'getting-started',
      //   loadChildren: () => import('./getting-started/getting-started.module').then((m) => m.GettingStartedModule),
      // },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then((m) => m.AdminModule),
      },
      {
        path: '',
        redirectTo: 'admin',
        pathMatch: 'full',
      },
      {
        path: '**',
        redirectTo: 'admin',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}
