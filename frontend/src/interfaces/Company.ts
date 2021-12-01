import { Address as CompanyAddress} from "./Address";

export type Company = {
  id: number,
  name: string,
  email: string,
  rating: number,
  webPage: string,
  address: CompanyAddress
}