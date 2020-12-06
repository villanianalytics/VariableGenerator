import { Moment } from 'moment';

export interface IApp {
  id?: number;
  variableValue?: string;
  effDt?: Moment;
}

export class App implements IApp {
  constructor(public id?: number, public variableValue?: string, public effDt?: Moment) {}
}
