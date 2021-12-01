import { initializeApp } from "firebase/app";
import { getDownloadURL, getStorage, ref } from "firebase/storage"

const bucketUrl = "re-tracker-57e46.appspot.com"

const firebaseConfig = {
  apiKey: "AIzaSyDX4hnXKAfygLlaurjK2frc8Jc1Rnb3bcc",
  authDomain: "re-tracker-57e46.firebaseapp.com",
  projectId: "re-tracker-57e46",
  storageBucket: "re-tracker-57e46.appspot.com",
  messagingSenderId: "860000626523",
  appId: "1:860000626523:web:dff4ca31a8f9c588dfdc7e"
};

const firebaseApp = initializeApp(firebaseConfig);

const storage = getStorage(firebaseApp)

const fetchFullImageUrl = async (path: string) : Promise<string> => {
  const pathReference = ref(storage, path)

  return getDownloadURL(pathReference) 
    .then(url => {
      return Promise.resolve(url)
    })
}


export { storage, firebaseApp as default, bucketUrl, fetchFullImageUrl}