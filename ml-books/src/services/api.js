import axios from 'axios';

// URL base do back-end
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Obtém todos os livros.
 * @returns {Promise<Array>} Lista de livros.
 */
export const getBooks = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/books`);
        return response.data;
    } catch (error) {
        console.error('Erro ao buscar livros:', error.response?.data || error.message);
        throw new Error('Erro ao carregar os livros. Tente novamente mais tarde.');
    }
};

/**
 * Obtém o carrinho pelo ID.
 * @param {number} cartId - ID do carrinho.
 * @returns {Promise<Object>} Carrinho.
 */
export const getCart = async (cartId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/cart/${cartId}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao buscar carrinho:', error.response?.data || error.message);
        throw new Error('Erro ao carregar o carrinho. Tente novamente mais tarde.');
    }
};

/**
 * Adiciona um item ao carrinho.
 * @param {number} cartId - ID do carrinho.
 * @param {number} bookId - ID do livro.
 * @param {number} quantity - Quantidade a ser adicionada.
 * @returns {Promise<Object>} Carrinho atualizado.
 */
export const addItemToCart = async (cartId, bookId, quantity) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/cart/${cartId}/add`, null, {
            params: {
                bookId,
                quantity,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Erro ao adicionar item ao carrinho:', error.response?.data || error.message);
        throw new Error('Erro ao adicionar o item ao carrinho.');
    }
};

/**
 * Remove um item do carrinho.
 * @param {number} cartId - ID do carrinho.
 * @param {number} itemId - ID do item a ser removido.
 * @returns {Promise<Object>} Carrinho atualizado.
 */
export const removeItemFromCart = async (cartId, itemId) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/cart/${cartId}/remove/${itemId}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao remover item do carrinho:', error.response?.data || error.message);
        throw new Error('Erro ao remover item do carrinho.');
    }
};


/**
 * Faz login no sistema.
 * @param {Object} credentials - Objeto com username e password.
 * @returns {Promise<Object>} - Resposta do servidor com role e userId.
 */
export const login = async (credentials) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/users/login`, credentials);
        return response.data;
    } catch (error) {
        console.error('Erro ao fazer login:', error.response?.data || error.message);
        throw new Error('Erro ao fazer login. Verifique suas credenciais.');
    }
};

