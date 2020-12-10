import { IVariableName } from 'app/shared/model/variable-name.model';

export interface ICubes {
  id?: number;
  cubes?: IVariableName[];
}

export class Cubes implements ICubes {
  constructor(public id?: number, public cubes?: IVariableName[]) {}
}
