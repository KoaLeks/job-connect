import {ProfileDto} from './profile-dto';
import {Interest} from './interest';
import {SuperSimpleProfileDto} from './SuperSimpleProfileDto';

export class SuperSimpleEmployee {
  constructor(
    public superSimpleProfileDto: SuperSimpleProfileDto,
    public interestDtos: Interest[],
    public gender: string,
    public birthDate: Date
  ) {}
}
