import { AppBar, Toolbar, Typography, Button, Menu, IconButton, MenuItem } from '@mui/material';
import { Box } from '@mui/system';
import { MouseEvent, SyntheticEvent, useState } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Roles } from '../interfaces/Roles';
import { logout } from '../state/auth/auth.action.creators';
import { UserState } from '../state/auth/auth.reducer';
import { RootState, useAppThunkDispatch } from '../state/store';
import { useNavigate } from 'react-router'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/Logout';
import DashboardIcon from '@mui/icons-material/Dashboard';

const Header = () => {
  const dispatch = useAppThunkDispatch()
  const navigate = useNavigate()
  const { isLoggedIn } = useSelector<RootState, UserState>((state: RootState) => state.auth)
  const isAdmin = useSelector<RootState, UserState>((state: RootState) => state.auth).user?.role?.name === Roles.ROLE_ADMIN

  const [menuAnchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const open = Boolean(menuAnchorEl)

  const handleMenuClick = (event: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleMenuClose = () => {
    setAnchorEl(null)
  }

  const logoutHandler = async (e: SyntheticEvent) => {
    e.preventDefault()

    dispatch(logout()).then(() => {
      handleMenuClose()
      navigate('/')
    })
  }

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Link to="/" className="no-decoration">
            <Typography variant="h6" component="div">
              ReTracker
            </Typography>
          </Link>
          <Typography className="w-4"></Typography>

          {
            isLoggedIn && 
            <section>
              <Link to="/create-real-estate" className="no-decoration"><Button color="inherit">Create new RE listing</Button></Link> 
            </section>
          }

          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }} />
          {
            isLoggedIn ? (
              <section>
                <IconButton  onClick={handleMenuClick}><AccountCircleIcon className="text-white" /></IconButton>
                <Menu 
                  anchorEl={menuAnchorEl}
                  open={open}
                  onClose={handleMenuClose}
                >
                  <MenuItem onClick={() => { handleMenuClose(); }}><Link to="/dashboard" className="flex gap-2"><DashboardIcon /> Dashboard</Link></MenuItem>
                  <MenuItem className="flex gap-2"  onClick={logoutHandler}> <LogoutIcon />Logout</MenuItem>
                </Menu>
              </section>
             
            )
            :
            (
              <section>
                <Link to="/login" className="no-decoration"><Button color="inherit">Login</Button></Link> 
                <Link to="/register" className="no-decoration"><Button color="inherit">Register</Button></Link> 
              </section>
            )
          }
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default Header;