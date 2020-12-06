import { IApp } from 'app/shared/model/app.model';
import { ICubes } from 'app/shared/model/cubes.model';
import { IVariableName } from 'app/shared/model/variable-name.model';

export interface IConnection {
  id?: number;
  connection?: string;
  description?: string;
  uRL?: string;
  username?: string;
  password?: string;
  identityDomain?: string;
  connection?: IApp;
  connection?: ICubes;
  connection?: IVariableName;
}

export class Connection implements IConnection {
  constructor(
    public id?: number,
    public connection?: string,
    public description?: string,
    public uRL?: string,
    public username?: string,
    public password?: string,
    public identityDomain?: string,
    public connection?: IApp,
    public connection?: ICubes,
    public connection?: IVariableName
  ) {}
}
