import {ProfileDto} from './profile-dto';
import {Interest} from './interest';

export class SimpleEmployee {
  constructor(
    public simpleProfileDto: ProfileDto,
    public interestDtos: Interest[],
    public gender: string,
    public birthDate: Date
  ) {}
}
