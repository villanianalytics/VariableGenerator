import { IListUD } from 'app/shared/model/list-ud.model';
import { ICubes } from 'app/shared/model/cubes.model';

export interface IVariableName {
  id?: number;
  variableName?: string;
  listUD?: IListUD;
  cube?: ICubes;
}

export class VariableName implements IVariableName {
  constructor(public id?: number, public variableName?: string, public listUD?: IListUD, public cube?: ICubes) {}
}
