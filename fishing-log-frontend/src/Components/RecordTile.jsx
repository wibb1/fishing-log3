import React from 'react';
import { Link } from 'react-router-dom';

const RecordTile = ({
	id,
	name,
	success,
	body,
	latitude,
	longitude,
	date,
	time,
	first_type,
	first_time,
	first_height,
	second_type,
	second_time,
	second_height,
	third_type,
	third_time,
	third_height,
	fourth_type,
	fourth_time,
	fourth_height,
	pressure,
	humidity,
	windDirection,
	windSpeed,
	moonFraction,
	moonPhase,
	sunrise,
	sunset,
  species
}) => {
	const success_color = (success) => {
		let success_color;
		if (success === 'good') {
			success_color = 'is-success';
		} else if (success === 'bad') {
			success_color = 'is-light';
		}
		return success_color;
	};

	return (
		<div className="tile is-ancestor p-sm">
			<div className="tile is-vertical">
				<div className="tile is-parent">
					<Link to={`/records/react/${id}`}>
						<div className={`tile is-child notification ${success_color(success)} is-12`}>
							<div className="box">
								<div className="columns">
									<div className="column">
										<p className="title is-size-3 is-size-4-touch">{name}</p>
									</div>
									<div className="column is-vcentered">
										<div className="subtitle is-size-3 is-size-4-touch">
											<p className="has-text-right">
												{date}
											</p>
										</div>
									</div>
								</div>
							</div>
							<div className="columns">
								<div className="column">
									<div className="box">
										<p className="subtitle is-size-4 is-5-touch has-text-weight-b">
											<strong className="">Whats on the menu:</strong> {species}
										</p>
									</div>
								</div>
								<hr />
							</div>
							<div className="box">
								<p className="is-6">{body}</p>
							</div>
							<div className="columns">
								<div className="column m-2">
									<div className="box center">
										<table className="table has-text-centered center">
											<tbody>
												<tr>
													<th>Tide</th>
													<th>Time</th>
													<th>aMSL</th>
												</tr>
												<tr>
													<td>{first_type}</td>
													<td>{first_time}</td>
													<td>{first_height} </td>
												</tr>
												<tr>
													<td>{second_type}</td>
													<td>{second_time}</td>
													<td>{second_height}</td>
												</tr>
												<tr>
													<td>{third_type}</td>
													<td>{third_time}</td>
													<td>{third_height}</td>
												</tr>
												<tr>
													<td>{fourth_type}</td>
													<td>{fourth_time}</td>
													<td>{fourth_height}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div className="column">
									<div className="box center">
										<table className="table has-text-centered center">
											<tbody>
												<tr>
													<th>Measurement</th>
													<th>Units</th>
													<th>Value</th>
												</tr>
												<tr>
													<td>Pressure</td>
													<td>(inHg):</td>
													<td>{pressure}</td>
												</tr>
												<tr>
													<td>Humidity</td>
													<td>(%):</td>
													<td>{humidity}</td>
												</tr>
												<tr>
													<td>Wind Direction </td>
													<td>(degrees): </td>
													<td>{windDirection}</td>
												</tr>
												<tr>
													<td>Wind Speed </td>
													<td>(mph): </td>
													<td>{windSpeed}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div className="column is-centered">
									<div className="box center">
										<table className="table has-text-centered center">
											<tbody>
												<tr>
													<th>Moon Info</th>
													<th>Value</th>
												</tr>
												<tr>
													<td>Moon Fraction</td>
													<td>{moonFraction}</td>
												</tr>
												<tr>
													<td>Moon Phase</td>
													<td>{moonPhase}</td>
												</tr>
												<tr>
													<td>Sunrise</td>
													<td>{sunrise}</td>
												</tr>
												<tr>
													<td>Sunset</td>
													<td>{sunset}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</Link>
				</div>
			</div>
		</div>
	);
};

export default RecordTile;
