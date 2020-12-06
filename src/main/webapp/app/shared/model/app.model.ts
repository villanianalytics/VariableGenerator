import { Moment } from 'moment';

export interface IApp {
  id?: number;
  derivedValue?: boolean;
  variableValue?: string;
  effDt?: Moment;
}

export class App implements IApp {
  constructor(public id?: number, public derivedValue?: boolean, public variableValue?: string, public effDt?: Moment) {
    this.derivedValue = this.derivedValue || false;
  }
}
