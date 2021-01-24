import {SuperSimpleProfileDto} from './SuperSimpleProfileDto';

export class SuperSimpleEmployer {
  constructor(
    public superSimpleProfileDto: SuperSimpleProfileDto,
    public companyName: string,
    public companyDescription: string
  ) {}
}
