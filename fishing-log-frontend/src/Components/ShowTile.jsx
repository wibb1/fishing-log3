import React from 'react';
import { Link } from 'react-router-dom';

const ShowTile = ({ record, species }) => {

	const success_color = (success) => {
		let success_color;
		if (success === 'good') {
			success_color = `is-success`;
		} else if (success === 'bad') {
			success_color = `is-light`;
		}
		return success_color;
	};

	return (
		<div className="tile is-ancestor m-md">
			<div className="tile is-vertical m-md is-fullwidth">
				<div className="tile">
					<div className="tile is is-parent">
						<article
							className={`tile title is-child notification is-fullwidth ${success_color(record.success)} has-text-dark`}
						>
							<div className="box desktop-m-t-md is-fullwidth">
								<p className="has-text-centered center">{record.name}</p>
							</div>
						</article>
						<div className="tile is-white is-2 is-child is-vcentered is-centered p-sm">
							<div className="desktop-m-sm p-md">
								<Link to="/records/react" className="button is-fullwidth is-dark p-sm is-medium">
									Return to list
                </Link>
								</div>
								<div className="desktop-m-sm p-md">
									<a
										href={`/records/${record.id}/edit`}
										className="button is-fullwidth is-dark is-medium"
									>
										Edit Spot
									</a>
								</div>
							</div>
						</div>
					</div>
					<div className="tile is-verticle">
						<div className="tile is-vertical">
							<div className="tile">
								<div className="tile is-parent is-vertical">
									<article
										className={`tile is-child notification ${success_color(
											record.success
										)} has-text-centered`}
									>
										<div className="box">
											<p>
												{record.date}
											</p>
										</div>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>Species</td>
														<td>{species.common_name}</td>
														<td></td>
													</tr>
													<tr>
														<td>Latitude</td>
														<td>{record.latitude}</td>
														<td>N</td>
													</tr>
													<tr>
														<td>Longitude</td>
														<td>{record.longitude}</td>
														<td>E</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
								</div>
								<div className="tile is-parent">
									<article className={`tile is-child notification ${success_color(record.success)} center`}>
										<figure className="image is-fullwidth">
											<img src="../../public/images/480x640.png"></img>
										</figure>
									</article>
								</div>
							</div>
							<div className="tile">
								<div className="tile is-parent">
									<article className={`tile is-child center notification ${success_color(record.success)}`}>
										<div className="box is-fullheight center">
											<h1>{record.body}</h1>
										</div>
									</article>
								</div>
							</div>
							<div className="tile">
								<div className="tile is-parent">
									<article className={`tile is-child notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>Moon Fraction</td>
														<td>{record.moonFraction}</td>
													</tr>
													<tr>
														<td>Moon Phase</td>
														<td>{record.moonPhase}</td>
													</tr>
													<tr>
														<td>Moonrise</td>
														<td>{record.moonrise}</td>
													</tr>
													<tr>
														<td>Moonset</td>
														<td>{record.moonset}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
								</div>
								<div className="tile is-parent">
									<article className={`tile is-child notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>First Light</td>
														<td>{record.astronomicalDawn}</td>
													</tr>
													<tr>
														<td>Last Light</td>
														<td>{record.astronomicalDusk}</td>
													</tr>
													<tr>
														<td>Civil Dawn</td>
														<td>{record.civilDawn}</td>
													</tr>
													<tr>
														<td>Civil Dusk</td>
														<td>{record.civilDusk}</td>
													</tr>
													<tr>
														<td>Sunset</td>
														<td>{record.sunset}</td>
													</tr>
													<tr>
														<td>Sunrise</td>
														<td>{record.sunrise}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
								</div>
							</div>
						</div>
						<div className="tile is-vertical is-4">
							<div className="tile ">
								<div className="tile is-parent is-vertical ">
									<article className={`tile is-child  notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>Air Temperature</td>
														<td>{record.airTemperature}</td>
													</tr>
													<tr>
														<td>Barometric Pressure</td>
														<td>{record.pressure}</td>
													</tr>
													<tr>
														<td>Cloud Cover</td>
														<td>{record.cloudCover}</td>
													</tr>
													<tr>
														<td>Visibility</td>
														<td>{record.visibility}</td>
													</tr>
													<tr>
														<td>Wind Gusts</td>
														<td>{record.gust}</td>
													</tr>
													<tr>
														<td>Wind Direction</td>
														<td>{record.windDirection}</td>
													</tr>
													<tr>
														<td>Wind Speed</td>
														<td>{record.windSpeed}</td>
													</tr>
													<tr>
														<td>Relative Humidity</td>
														<td>{record.humidity}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
									<article className={`tile is-child notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>Wave Direction</td>
														<td>{record.waveDirection}</td>
													</tr>
													<tr>
														<td>Wave Height</td>
														<td>{record.waveHeight}</td>
													</tr>
													<tr>
														<td>Wave Period</td>
														<td>{record.wavePeriod}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
									<article className={`tile is-child notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>Sea Level</td>
														<td>{record.seaLevel}</td>
													</tr>
													<tr>
														<td>Current Direction</td>
														<td>{record.currentDirection}</td>
													</tr>
													<tr>
														<td>Current Speed</td>
														<td>{record.currentSpeed}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
									<article className={`tile is-child notification ${success_color(record.success)}`}>
										<div className="box center">
											<table className="table has-text-centered">
												<tbody>
													<tr>
														<td>{record.first_type}</td>
														<td>{record.first_height}</td>
														<td>{record.first_time}</td>
													</tr>
													<tr>
														<td>{record.second_type}</td>
														<td>{record.second_height}</td>
														<td>{record.second_time}</td>
													</tr>
													<tr>
														<td>{record.third_type}</td>
														<td>{record.third_height}</td>
														<td>{record.third_time}</td>
													</tr>
													<tr>
														<td>{record.fourth_type}</td>
														<td>{record.fourth_height}</td>
														<td>{record.fourth_time}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</article>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	};
	
	export default ShowTile;
	