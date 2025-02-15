import { useState, useEffect } from 'react';

import RecordTile from '../Components/RecordTile';

const RecordsIndexContainer = () => {
	const [records, setRecord] = useState([]);
	const [user, setUser] = useState([]);

	useEffect(() => {
		fetch('http://localhost:8080/api/v1/records')
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
			.then((response) => {
				setRecord(response.records.records);
				setUser(response.user);
			})
			.catch((error) => console.error(`error in fetch: ${error.message}`));
	}, []);
	
	const recordTiles = records.map((record) => {
    
		return (
			<RecordTile
				key={record.id}
				id={record.id}
				name={record.name}
				success={record.success}
				body={record.body}
				latitude={record.latitude}
				longitude={record.longitude}
				date={record.date.slice(0,16).replace("T"," ")}
				time={record.time}			
				first_type={record.first_type}
				first_time={record.first_time}
				first_height={record.first_height}
				second_type={record.second_type}
				second_time={record.second_time}
				second_height={record.second_height}
				third_type={record.third_type}
				third_time={record.third_time}
				third_height={record.third_height}
				fourth_type={record.fourth_type}
				fourth_time={record.fourth_time}
				fourth_height={record.fourth_height}
				pressure={record.pressure}
				humidity={record.humidity}
				windDirection={record.windDirection}
				windSpeed={record.windSpeed}
				moonFraction={record.moonFraction}
				moonPhase={record.moonPhase}
				sunrise={record.sunrise}
				sunset={record.sunset}
        species={record.species.common_name}
			/>
		);
	});

	return (
		<div className="container">
			<div className="">
			<h1 className="title center m-md"> Your Spot List</h1>
			</div>
			{recordTiles}
		</div>
	);
};

export default RecordsIndexContainer;
