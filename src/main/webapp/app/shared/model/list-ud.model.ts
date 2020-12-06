import { IVariableName } from 'app/shared/model/variable-name.model';

export interface IListUD {
  id?: number;
  listName?: string;
  listValue?: string;
  listName?: IVariableName;
  listNames?: IVariableName[];
}

export class ListUD implements IListUD {
  constructor(
    public id?: number,
    public listName?: string,
    public listValue?: string,
    public listName?: IVariableName,
    public listNames?: IVariableName[]
  ) {}
}
