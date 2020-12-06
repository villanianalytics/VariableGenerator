import { ICubes } from 'app/shared/model/cubes.model';

export interface IVariableName {
  id?: number;
  variableName?: string;
  cube?: ICubes;
}

export class VariableName implements IVariableName {
  constructor(public id?: number, public variableName?: string, public cube?: ICubes) {}
}
