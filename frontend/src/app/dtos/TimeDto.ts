export class TimeDto {
  constructor(
    public id: number,
    public start: string,
    public end: string,
    public booleanDate: boolean,
    public visible: boolean
  ) {
  }
}
