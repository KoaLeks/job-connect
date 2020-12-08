import {ProfileDto} from './profile-dto';

export class SimpleEmployer {
  constructor(
    public simpleProfileDto: ProfileDto,
    public companyName: string,
    public companyDescription: string
  ) {}
}
