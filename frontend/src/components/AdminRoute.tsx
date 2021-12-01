import { Navigate, Outlet } from 'react-router';

export type AdminRouteProps = {
  isAdmin: boolean
  redirectPath: string
};

const AdminRoute = ({isAdmin, redirectPath}: AdminRouteProps) => {
  if(isAdmin) {
    return <Outlet />;
  } else {
    return <Navigate to={{ pathname: redirectPath }} />;
  }
}

export default AdminRoute
