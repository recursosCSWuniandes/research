/* 
 * The MIT License
 *
 * Copyright 2016 af.esguerra10.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/* global by, element */

module.exports = function () {
    // list
    this.bookList = element.all(by.repeater('record in records'));

    // buttons
    this.createButton = element(by.id('create-books'));
    this.saveButton = element(by.id('save-books'));
    this.refreshButton = element(by.id('save-books'));
    this.cancelButton = element(by.id('cancel-books'));

    // form inputs
    this.name = element(by.id('name'));
    this.description = element(by.id('description'));
    this.isbn = element(by.id('isbn'));
    this.image = element(by.id('image'));

    // list item
    this.bookListItem = function (index) {
        this.self = element.all(by.repeater('record in records')).get(index);
        this.name = element(by.id(index + '-name'));
        this.description = element(by.id(index + '-description'));
        this.isbn = element(by.id(index + '-isbn'));
        this.editButton = element(by.id(index + '-edit-btn'));
        this.deleteButton = element(by.id(index + '-delete-btn'));
        this.confirmDeleteButton = element(by.id('confirm-delete'));
    };
    this.bookDetails = function () {
        this.name = element(by.id('book-name'));
        this.description = element(by.id('book-description'));
        this.isbn = element(by.id('book-isbn'));
        this.editButton = element(by.id('book-edit-btn'));
        this.deleteButton = element(by.id('book-delete-btn'));
        this.confirmDeleteButton = element(by.id('confirm-delete'));
    };
    this.fillForm = function (book) {
        this.name.clear().sendKeys(book.name);
        this.description.clear().sendKeys(book.description);
        this.isbn.clear().sendKeys(book.isbn);
        this.image.clear().sendKeys(book.image);
    };
};