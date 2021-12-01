import { Address } from "./Address";
import { RealEstateImage } from "./RealEstateImage";
import { RealEstateType } from "./RealEstateType";
import { User } from "./User";

export type RealEstate = {
  id?: number,
  name: string,
  description: string,
  rentPrice?: number,
  sellPrice?: number,
  creationDate?: Date,
  updateDate?: Date,
  rating?: number,
  quadrature: number,
  rooms?: number,
  baths?: number,
  forRent?: boolean,
  forSale?: boolean,
  images: RealEstateImage[],
  realEstateTypeDto: RealEstateType,
  address: Address,
  createdBy?: User
  updatedBy?: User
}