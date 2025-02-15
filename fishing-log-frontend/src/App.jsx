import './App.css'
import React from 'react'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import RecordsIndexContainer from "./Containers/RecordsIndexContainer";
import RecordShowContainer from "./Containers/RecordShowContainer";
import Login from "./Components/Login";

function App() {

  return (
		<BrowserRouter>
			<Routes>
				<Route
					exact
					path="/login"
					element={<Login/>}
				/>
				<Route
					exact
					path="/records/react"
					element={<RecordsIndexContainer/>}
				/>
				<Route
					exact
					path="/records/react/:id"
					element={<RecordShowContainer/>}
				/>
			</Routes>
		</BrowserRouter>
  );
}

export default App
