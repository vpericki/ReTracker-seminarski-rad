export type Address = {
  id: number,
  country: string,
  state: string,
  city: string,
  street: string,
  houseNumber: string,
  floor?: string,
  doorNumber?: string,
  longitude: number,
  latitude: number
}