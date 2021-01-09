
export class SearchEvent {
  constructor(
    public title: string,
    public interestAreaId: number,
    public employerId: number,
    public start: string,
    public end: string,
    public payment: number,
    public onlyAvailableTasks: boolean,
    public userId: number) {
  }
}
