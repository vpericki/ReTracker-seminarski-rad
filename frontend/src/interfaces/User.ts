import { Role } from "./Role";

export type User = {
  id: number,
  username: string,
  firstName: string,
  lastName: string,
  dateOfBirth: Date,
  email: string,
  rating: number | null,
  role: Role,
  token: string
}