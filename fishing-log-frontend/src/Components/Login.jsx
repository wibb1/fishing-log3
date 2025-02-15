import React, {useState} from 'react';
import axios from 'axios';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post('/login', {
        username, password
      })
    } catch (error) {
      
    }
  }
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
					type="text"
					value={password}
					onChange={(event) => setPassword(event.target.value)}
				/>
        <button type='submit'>Login</button>
			</form>
		</>
  );
}

export default Login;