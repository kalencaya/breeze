import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DataElementComponent } from './data-element/data-element.component';
import { DatasourceComponent } from './datasource/datasource.component';
import { MetaComponent } from './meta.component';
import { RefdataComponent } from './refdata/refdata.component';
import { SystemComponent } from './system/system.component';

const routes: Routes = [
  {
    path: '',
    component: MetaComponent,
    children: [{ path: 'datasource', component: DatasourceComponent }],
  },
  {
    path: '',
    component: MetaComponent,
    children: [{ path: 'refdata', component: RefdataComponent }],
  },
  {
    path: '',
    component: MetaComponent,
    children: [{ path: 'dataElement', component: DataElementComponent }],
  },
  {
    path: '',
    component: MetaComponent,
    children: [{ path: 'system', component: SystemComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MetaRoutingModule {}
