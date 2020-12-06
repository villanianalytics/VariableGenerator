import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { CubesComponent } from './cubes.component';
import { CubesDetailComponent } from './cubes-detail.component';
import { CubesUpdateComponent } from './cubes-update.component';
import { CubesDeleteDialogComponent } from './cubes-delete-dialog.component';
import { cubesRoute } from './cubes.route';

@NgModule({
  imports: [VariableGeneratorSharedModule, RouterModule.forChild(cubesRoute)],
  declarations: [CubesComponent, CubesDetailComponent, CubesUpdateComponent, CubesDeleteDialogComponent],
  entryComponents: [CubesDeleteDialogComponent],
})
export class VariableGeneratorCubesModule {}
