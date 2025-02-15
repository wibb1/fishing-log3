import { useState, useEffect } from "react";
import ShowTile from "../Components/ShowTile";
import { useParams } from "react-router-dom";

const RecordShowContainer = () => {
	const [recordState, setRecordState] = useState({});
	const [speciesState, setSpeciesState] = useState({});

	const id = useParams();

	useEffect(() => {
		fetch(`/api/v1/records/${id}`)
			.then((response) => {
				if (response.ok) {
					return response;
				} else {
					let errorMessage = `${response.status} (${response.statusText})`,
						error = new Error(errorMessage);
					throw error;
				}
			})
			.then((response) => response.json())
			.then((body) => {
				setRecordState(body.record);
				setSpeciesState(body.record.species);
			})
			.catch((error) =>
				console.error(`Error in fetch: ${error.message}`)
			);
	}, [id]);

	return <ShowTile record={recordState} species={speciesState} />;
};

export default RecordShowContainer;
