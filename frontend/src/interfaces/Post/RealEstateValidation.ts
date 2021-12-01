import { RealEstateType } from "../RealEstateType";
import { RealEstateAddressPost } from "./RealEstateAddressPost";
import { RealEstateImagePost } from "./RealEstateImagePost";

export type RealEstateValidation = {
  name: string,
  description: string,
  quadrature: number,
  rooms: number,
  baths: number,
  rentPrice?: number,
  sellPrice?: number,
  forRent?: boolean,
  forSale?: boolean,
  images?: File[] | RealEstateImagePost[],
  realEstateTypeDto: RealEstateType,
  address: RealEstateAddressPost
}