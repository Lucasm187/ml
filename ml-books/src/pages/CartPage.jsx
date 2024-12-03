import React, { useState, useEffect } from 'react';
import { getCart, addItemToCart, removeItemFromCart } from '../services/api';

const CartPage = () => {
    const [cart, setCart] = useState(null);
    const [error, setError] = useState(null);

    // Obtém o ID do carrinho armazenado no localStorage (ou usa um ID fixo para testes)
    const cartId = localStorage.getItem('cartId') || 1;

    useEffect(() => {
        const fetchCart = async () => {
            try {
                const data = await getCart(cartId);
                setCart(data);
            } catch (err) {
                console.error('Erro ao carregar o carrinho:', err);
                setError('Não foi possível carregar o carrinho.');
            }
        };

        fetchCart();
    }, [cartId]);

    const handleRemoveItem = async (itemId) => {
        try {
            const updatedCart = await removeItemFromCart(cartId, itemId);
            setCart(updatedCart);
        } catch (err) {
            console.error('Erro ao remover item do carrinho:', err);
            setError('Não foi possível remover o item.');
        }
    };

    if (error) {
        return <div>{error}</div>;
    }

    if (!cart) {
        return <div>Carregando carrinho...</div>;
    }

    return (
        <div>
            <h1>Carrinho de Compras</h1>
            {cart.items.length === 0 ? (
                <p>O carrinho está vazio.</p>
            ) : (
                <div>
                    <ul>
                        {cart.items.map((item) => (
                            <li key={item.id}>
                                <p>{item.bookTitle}</p>
                                <p>Quantidade: {item.quantity}</p>
                                <p>Subtotal: R${item.subtotal.toFixed(2)}</p>
                                <button onClick={() => handleRemoveItem(item.id)}>
                                    Remover
                                </button>
                            </li>
                        ))}
                    </ul>
                    <h3>Total: R${cart.totalAmount.toFixed(2)}</h3>
                </div>
            )}
        </div>
    );
};

export default CartPage;
