import { Card, CardContent, Typography } from "@mui/material"
import { useEffect, useState } from "react"
import { useLocation, useNavigate, useParams } from "react-router"
import RealEstateCard from "../../components/RealEstate/RealEstateCard"
import { RealEstate } from "../../interfaces/RealEstate"
import { RealEstateFilter } from "../../interfaces/RealEstate/RealEstateFilter"
import { User } from "../../interfaces/User"
import { RealEstateService } from "../../services/realestate.service"
import { userService } from "../../services/user.service"

const UserProfile = () => {

  const [userListings, setUserListings] = useState<RealEstate[]>([])
  const [user, setUser] = useState<User>()
  const params = useParams()
  const navigate = useNavigate()

  useEffect(() => {
    const paramsId = parseInt(params.userId as string)

    const filter: RealEstateFilter = {
      userIdList: [paramsId]
    }
    
    RealEstateService.getAllRealEstate(0, 1000, filter)
      .then(response => response.data.data.content as RealEstate[])
      .then((data: RealEstate[]) => {
        setUserListings(data)
        return userService.user(paramsId)
      })
      .then(response => response.data.data)
      .then(userData => {
        setUser(userData)
      })
      .catch(err => {
        navigate('/forbidden')
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

export default UserProfile