import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import ShowTile from "../Components/ShowTile";

const RecordShowContainer = () => {
	const [recordState, setRecordState] = useState({});
	const [speciesState, setSpeciesState] = useState({});
	const { id } = useParams();

  useEffect(() => {
		axios
			.get(`/api/v1/records/${id}`)
			.then((response) => {
				setRecordState(response.data.record);
				setSpeciesState(response.data.record.species);
			})
			.catch((error) => {
				console.error(`Error in fetch: ${error.message}`);
			});
  }, [id]);

	return <ShowTile record={recordState} species={speciesState} />;
};

export default RecordShowContainer;
