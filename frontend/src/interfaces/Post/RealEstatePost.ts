import { RealEstateType } from "../RealEstateType";
import { RealEstateAddressPost } from "./RealEstateAddressPost";
import { RealEstateImagePost } from "./RealEstateImagePost";

export type RealEstatePost = {
  name: string,
  description: string,
  quadrature: number,
  rooms: number,
  baths: number,
  rentPrice?: number,
  sellPrice?: number,
  forRent?: boolean,
  forSale?: boolean,
  images: RealEstateImagePost[],
  realEstateTypeDto: RealEstateType,
  address: RealEstateAddressPost
}