import { useLocation } from "react-router";
import { UserRegisterResponse } from "../services/user.service";

const RegisterSuccess = () => {
  const location = useLocation()
  const userData = location.state as UserRegisterResponse

  return (
    <div className="flex justify-center text-center">
      {userData.data?.username}, welcome to ReTracker!

      
    </div>
  )
}

export default RegisterSuccess;