import { Navigate, Outlet } from 'react-router';

export type ProtectedRouteProps = {
  isAuthenticated: boolean
  authenticationPath: string
}

const ProtectedRoute = ({isAuthenticated, authenticationPath}: ProtectedRouteProps) => {
  if(isAuthenticated) {
    return <Outlet />;
  } else {
    return <Navigate to={{ pathname: authenticationPath }} />;
  }
}

export default ProtectedRoute