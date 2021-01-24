export class SuperSimpleProfileDto {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public email: string,
    public password: string,
    public publicInfo: string
  ) {}
}
