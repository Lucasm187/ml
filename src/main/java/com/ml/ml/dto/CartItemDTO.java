package com.ml.ml.dto;

public class CartItemDTO {
    private Long id;
    private String bookTitle;
    private int quantity;
    private double subtotal;

    // Construtores
    public CartItemDTO() {}

    public CartItemDTO(Long id, String bookTitle, int quantity, double subtotal) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
