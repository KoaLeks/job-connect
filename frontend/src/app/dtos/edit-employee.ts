import {ProfileDto} from './profile-dto';
import {Gender} from './gender_old';

export class EditEmployee {
  constructor(
    public profileDto: ProfileDto,
    public gender: Gender
  ) {}
}
