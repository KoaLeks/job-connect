import {ProfileDto} from './profile-dto';
import {Gender} from './gender.enum';
import {Interest} from './interest';

export class EditEmployee {
  constructor(
    public profileDto: ProfileDto,
    public interestDtos: Interest[],
    public gender: Gender,
    public birthDate: Date
  ) {}
}
