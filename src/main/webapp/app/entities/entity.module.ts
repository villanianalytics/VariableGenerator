import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'connection',
        loadChildren: () => import('./connection/connection.module').then(m => m.VariableGeneratorConnectionModule),
      },
      {
        path: 'app',
        loadChildren: () => import('./app/app.module').then(m => m.VariableGeneratorAppModule),
      },
      {
        path: 'cubes',
        loadChildren: () => import('./cubes/cubes.module').then(m => m.VariableGeneratorCubesModule),
      },
      {
        path: 'variable-name',
        loadChildren: () => import('./variable-name/variable-name.module').then(m => m.VariableGeneratorVariableNameModule),
      },
      {
        path: 'list-ud',
        loadChildren: () => import('./list-ud/list-ud.module').then(m => m.VariableGeneratorListUDModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class VariableGeneratorEntityModule {}
