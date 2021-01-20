import {ProfileDto} from './profile-dto';

export class EditEmployer {
  constructor(
    public id: number,
    public profileDto: ProfileDto,
    public companyName: String,
    public description: String
  ) {}
}
