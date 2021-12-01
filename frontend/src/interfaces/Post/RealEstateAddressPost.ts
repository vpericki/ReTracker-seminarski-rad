export interface RealEstateAddressPost {
  country: string,
  state: string,
  city: string,
  street: string,
  houseNumber: string,
  longitude: number,
  latitude: number,
  floor: string | null,
  doorNumber: string | null
}