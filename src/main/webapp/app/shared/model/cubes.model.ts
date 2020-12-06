import { IVariableName } from 'app/shared/model/variable-name.model';

export interface ICubes {
  id?: number;
  cube?: string;
  cubes?: IVariableName[];
}

export class Cubes implements ICubes {
  constructor(public id?: number, public cube?: string, public cubes?: IVariableName[]) {}
}
