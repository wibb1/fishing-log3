import './App.css'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import RecordsIndexContainer from "./Containers/RecordsIndexContainer";
import RecordShowContainer from "./Containers/RecordShowContainer";

function App() {

  return (
		<BrowserRouter>
			<Routes>
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
