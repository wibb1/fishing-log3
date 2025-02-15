import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const navigate = useNavigate();

	const handleLogin = async (event) => {
		event.preventDefault();

    try {
		const response = await axios.post("/login", {
			username,
			password,
		});

		if (response.status === 200) {
			// Assuming the backend returns a token or some form of authentication
			const token = response.data.token;
			localStorage.setItem("token", token);
			navigate("/records/react"); // Redirect to the records page
		}
	} catch (error) {
		console.error("Login failed:", error);
		// Handle login failure (e.g., show an error message)
	}
	};

	return (
		<>
			<h1>Login</h1>
			<form onSubmit={handleLogin}>
				<label>Username</label>
				<input
					type="text"
					value={username}
					onChange={(event) => setUsername(event.target.value)}
				/>
				<label>Password</label>
				<input
					type="password"
					value={password}
					onChange={(event) => setPassword(event.target.value)}
				/>
				<button type="submit">Login</button>
			</form>
		</>
	);
};

export default Login;