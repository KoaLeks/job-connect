export class Employer {
  constructor(
    public id: number,
    public companyName: string,
    public companyDescription: string,
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public publicInfo: string
  ) {}
}
