import React, { useState } from 'react';
import { login } from '../services/api';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
    const [account, setAccount] = useState(''); // Renomear para 'account'
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            // Enviar com os nomes corretos das chaves esperados pelo back-end
            const response = await login({ account, password });

            const { role, userId } = response;

            // Armazena informações no localStorage
            localStorage.setItem('role', role);
            localStorage.setItem('userId', userId);

            // Redireciona com base no tipo de usuário
            if (role === 'ADMIN') {
                navigate('/admin');
            } else {
                navigate('/books');
            }
        } catch (err) {
            console.error('Erro durante o login:', err);
            setError('Credenciais inválidas. Tente novamente.');
        }
    };

    return (
        <div>
            <h1>Login</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleLogin}>
                <div>
                    <label>Usuário:</label>
                    <input
                        type="text"
                        value={account} // Alterado para 'account'
                        onChange={(e) => setAccount(e.target.value)} // Alterado para 'account'
                        required
                    />
                </div>
                <div>
                    <label>Senha:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Entrar</button>
            </form>
        </div>
    );
};

export default LoginPage;
