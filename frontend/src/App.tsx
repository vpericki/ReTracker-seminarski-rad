import { Route, Routes, useNavigate } from 'react-router';
import Header from './components/header';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import HomeScreen from './Screens/HomeScreen';
import LoginScreen from './Screens/LoginScreen';
import { BrowserRouter } from 'react-router-dom';
import './App.css'
import 'react-leaflet-markercluster/dist/styles.min.css'
import 'leaflet-geosearch/dist/geosearch.css'
import 'react-dropzone-uploader/dist/styles.css'
import RegisterScreen from './Screens/RegisterScreen';
import RegisterSuccess from './Screens/RegisterSuccess';
import Forbidden from './Screens/Forbidden';
import ProtectedRoute, { ProtectedRouteProps } from './components/ProtectedRoute';
import { useSelector } from 'react-redux';
import { RootState } from './state/store';
import { UserState } from './state/auth/auth.reducer';
import Dashboard from './Screens/UserProfile/Dashboard';
import CreateRealEstate from './Screens/RealEstate/CreateRealEstate';
import Interceptors from './components/Interceptors';
import TestCard from './Screens/TestCard';
import { RealEstate } from './interfaces/RealEstate';
import { RealEstateImage } from './interfaces/RealEstateImage';
import UserProfile from './Screens/UserProfile/UserProfile';
import RealEstateDetails from './Screens/RealEstate/RealEstateDetails';
import ErrorScreen from './Screens/ErrorScreen';

const App = () => {
  const userData = useSelector<RootState, UserState>((state: RootState) => state.auth)
  const defaultProtectedRouteProps: ProtectedRouteProps = {
    isAuthenticated: !!userData.isLoggedIn,
    authenticationPath: '/login',
  }

  const testRealEstate: RealEstate = {
    address: {
      id: 1,
      city: 'Zagreb',
      country: 'Croatia',
      houseNumber: '1A',
      latitude: 45.3,
      longitude: 5.5,
      state: 'Zagreb',
      street: 'Selska',
    },
    description: 'This is a description',
    name: 'Bijela kuca',
    quadrature: 100,
    id: 1,
    images: [
      { id: 1, path: 'https://cdn.pixabay.com/photo/2019/10/13/20/07/house-4547140_1280.jpg', realEstateId: 1} as RealEstateImage
    ],
    realEstateTypeDto: {
      id: 2,
      type: "APARTMENT"
    },
    baths: 1,
    forSale: true,
    sellPrice: 125000,
    rating: 3.4,
    createdBy: {
      id: 1,
      dateOfBirth: new Date(),
      email: 'email@email.com',
      firstName: 'first',
      lastName: 'last',
      role: {
        id: 1,
        name: "USER"
      },
      rating: null,
      token: 'ababababa',
      username: 'username1'
    },
    creationDate: new Date(),
    rooms: 3,
  }

  return (
    <BrowserRouter>
      <Header />
      <main>
          <Routes>
            <Route path="/" element={<HomeScreen />} />
            <Route path="/login" element={<LoginScreen />} />
            <Route path="/register" element={<RegisterScreen />} />
            <Route path="/register-success" element={<RegisterSuccess />} />

            <Route path="/user/:userId" element={<UserProfile />} />
            <Route path="/real-estate/:id" element={<RealEstateDetails />} />

            <Route element={<ProtectedRoute {...defaultProtectedRouteProps} /> }>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/create-real-estate" element={<CreateRealEstate />} />
            </Route> 

            <Route path="/test-card" element={<TestCard {...testRealEstate} />} />
            <Route path="/forbidden" element={<Forbidden />} />
            <Route path="/error" element={<ErrorScreen />} />

          </Routes>
      </main>

      <Interceptors />
    </BrowserRouter>
  );
}

export default App;
