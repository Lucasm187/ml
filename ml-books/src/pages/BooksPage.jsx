import React, { useState, useEffect } from 'react';
import { getBooks } from '../services/api';

const BooksPage = () => {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const data = await getBooks();
                setBooks(data);
            } catch (error) {
                console.error('Erro ao carregar os livros:', error);
            }
        };

        fetchBooks();
    }, []);

    return (
        <div>
            <h1>Livros Disponíveis</h1>
            <ul>
                {books.map((book) => (
                    <li key={book.id}>
                        <h2>{book.title}</h2>
                        <p>Autor: {book.author}</p>
                        <p>Categoria: {book.category}</p>
                        <p>Preço: R$ {book.price}</p>
                        <p>Estoque: {book.stock}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default BooksPage;
