import { RealEstatePost } from '../interfaces/Post/RealEstatePost'
import { AxiosResponse } from 'axios'
import { RealEstate } from '../interfaces/RealEstate'
import api from './api.service';
import { RealEstateFilter } from '../interfaces/RealEstate/RealEstateFilter';

interface RealEstateResponse {
  data: {
    content: RealEstate[]
  },
  error: string
}

interface SingleRealEstateResponse{
  data: RealEstate,
  error: string
}

export const RealEstateService = {
  create: (realEstate: RealEstatePost) : Promise<AxiosResponse<SingleRealEstateResponse>> => {
    console.log(realEstate)
    return api.post(`/real-estate`, realEstate)
  },
  getRealEstateById: (id: number) : Promise<AxiosResponse<SingleRealEstateResponse>> => {
    return api.get(`/real-estate/${id}`)
  },
  getAllRealEstate: (page: number, size: number, filter: RealEstateFilter) : Promise<AxiosResponse<RealEstateResponse>> => {
    return api.post(`/real-estate/all-real-estate?page=${page}&size=${size}`, filter)
  },
  updateRealEstate: (realEstate: RealEstatePost) : Promise<AxiosResponse<RealEstate>> => {
    return api.put(`/real-estate`, realEstate)
  },
  deleteRealEstate: (id: number) : Promise<AxiosResponse<void>> => {
    return api.delete(`/real-estate/${id}`)
  },
  updateRealEstateRating: (realEstateId: number, rating: number) : Promise<AxiosResponse<SingleRealEstateResponse>> => {
    return api.patch(`/real-estate/rating/${realEstateId}/${rating}`)
  }
}