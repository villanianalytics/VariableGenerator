import { IApp } from 'app/shared/model/app.model';
import { ICubes } from 'app/shared/model/cubes.model';
import { IVariableName } from 'app/shared/model/variable-name.model';

export interface IConnectionMgr {
  id?: number;
  connectionName?: string;
  description?: string;
  uRL?: string;
  username?: string;
  password?: string;
  identityDomain?: string;
  connectionName?: IApp;
  connectionName?: ICubes;
  connectionName?: IVariableName;
}

export class ConnectionMgr implements IConnectionMgr {
  constructor(
    public id?: number,
    public connectionName?: string,
    public description?: string,
    public uRL?: string,
    public username?: string,
    public password?: string,
    public identityDomain?: string,
    public connectionName?: IApp,
    public connectionName?: ICubes,
    public connectionName?: IVariableName
  ) {}
}
