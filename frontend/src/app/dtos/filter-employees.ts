export class FilterEmployees {
  constructor(
    public interests: { id: number, description: string }[],
    public time: string,
    public date: string
  ) { }
}
