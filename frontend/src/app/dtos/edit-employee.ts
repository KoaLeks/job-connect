import {ProfileDto} from './profile-dto';
import {Gender} from './gender.enum';

export class EditEmployee {
  constructor(
    public profileDto: ProfileDto,
    public gender: Gender
  ) {}
}
