export class Address {
  constructor(
    public id: number,
    public city: string,
    public state: string,
    public zip: number,
    public addressLine: string,
    public additional: string) {
  }
}
