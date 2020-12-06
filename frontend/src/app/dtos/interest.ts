import {InterestArea} from './interestArea';

export class Interest {
  constructor(
    public id: Number,
    public name: String,
    public description: String,
    public interestArea: InterestArea,
  ) {
  }
}
