import { useSelector } from "react-redux"
import { RootState, useAppThunkDispatch } from '../../state/store'
import { UserState } from '../../state/auth/auth.reducer'
import { Card, CardContent, Typography } from "@mui/material"
import { useEffect, useState } from "react"
import { RealEstate } from "../../interfaces/RealEstate"
import RealEstateCard from "../../components/RealEstate/RealEstateCard"
import { RealEstateState } from "../../state/real-estate/real-estate.action.reducer"
import { getUserListings } from "../../state/real-estate/real-estate.action.creators"


const Dashboard = () => {
  const dispatch = useAppThunkDispatch()
  const { user } = useSelector<RootState, UserState>((state: RootState) => state.auth)
  const { userListings } = useSelector<RootState, RealEstateState>((state: RootState) => state.realEstates)

  useEffect(() => {
    dispatch(getUserListings(user?.id!))
      .then(() => {
        console.log("Successfully fetched user listings")
      })

  }, [])

  return (
    <div className="flex flex-col mt-5 justify-center">
      <div className="w-10/12 lg:w-1/3 self-center flex flex-col gap-3">
        <section>
          <Card>
            <CardContent className="flex flex-col gap-3">
              <Typography variant="h5">User info</Typography>
              <Typography className="text-gray-700">{user?.email}</Typography>
              <Typography className="text-gray-700">{user?.username}</Typography>

              <Typography className="text-gray-700" variant="h6">Number of active listings: {userListings.length > 0 ? userListings.length : 'None'}</Typography>
            </CardContent>
          </Card>
        </section>
        {
          userListings.length > 0 ? (
          <section className="flex flex-col gap-3">  
            <Typography variant="h5">Listings</Typography>
            {userListings.map(re => (
              <RealEstateCard key={re.id} {...re} />
            ))}
          </section>
          ) : ''
        }
        
      </div>
    </div>
  )
}

export default Dashboard