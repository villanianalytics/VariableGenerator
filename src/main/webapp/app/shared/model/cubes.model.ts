import { IVariableName } from 'app/shared/model/variable-name.model';

export interface ICubes {
  id?: number;
  variableNames?: IVariableName[];
}

export class Cubes implements ICubes {
  constructor(public id?: number, public variableNames?: IVariableName[]) {}
}
